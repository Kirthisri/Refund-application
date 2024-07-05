package com.refund.app.refund_application.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refund.app.refund_application.models.Item;
import com.refund.app.refund_application.models.Order;
import com.refund.app.refund_application.models.Refund;
import com.refund.app.refund_application.models.RefundItems;
import com.refund.app.refund_application.models.RefundRequest;
import com.refund.app.refund_application.repo.OrderRepository;
import com.refund.app.refund_application.repo.RefundRepository;

@Service
public class RefundService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private RefundRepository refundRepository;

	public Refund createRefund(RefundRequest refundRequest) throws Exception {

		Optional<Order> optionalOrder = orderRepository.findById(refundRequest.getOrderId());

		if (!optionalOrder.isPresent()) {
			throw new Exception("Order not found");
		}

		Order order = optionalOrder.get();
		if (!order.isCompleted()) {
			throw new Exception("Order is not completed");
		}

		// Check any items ordered are already refunded
		// find by id from refund repository
		Optional<RefundRequest> existingRefundData = refundRepository.findById(refundRequest.getOrderId());

		// if no data present in refund repository then save the refund request to repo
		if (!existingRefundData.isPresent()) {
			saveFirstRefundItemsInRefundRepo(refundRequest, order);
		} else {
			saveRefundItemsInRefundRepo(existingRefundData.get(), refundRequest, order);
		}

		Refund refund = getRefundResponse(refundRequest);

		return refund;
	}

	/**
	 * 
	 * @param refundRequest - request refund items
	 * @return - refund response containing total amount to refund
	 */
	private Refund getRefundResponse(RefundRequest refundRequest) {
		// consider itemData is the data from item DB
		List<Item> itemData = new ArrayList<>();
		Refund refund = new Refund();
		refund.setOrderId(refundRequest.getOrderId());
		refund.setRefundAmount(calculateRefundAmount(refundRequest, itemData));
		refund.setPaymentReferenceId(refundRequest.getPaymentReferenceId());

		return refund;
	}

	/**
	 * 
	 * @param existingRefundData - existing return item data of a orderId in DB
	 * @param refundRequest      - requested refund item data
	 * @param order              - ordered item data
	 * 
	 *                           Function converts the refund items to map -
	 *                           key:itemId, value:refundItems Iterates the
	 *                           requested refund items, compare requested itemId
	 *                           with key. If requested item already exist in DB and
	 *                           it is partially refunded then, count existing item
	 *                           quantity with requested item quantity and update
	 *                           the status. if count matched the ordered count then
	 *                           set fully refunded = true if count is less than
	 *                           ordered count then set partially refunded = true
	 *                           else consider existing count = 0
	 * 
	 */
	private void saveRefundItemsInRefundRepo(RefundRequest existingRefundData, RefundRequest refundRequest,
			Order order) {
		Map<Long, RefundItems> existingRefundItems = existingRefundData.getReturnItems().stream()
				.filter(e -> e.isPartiallyRefunded() || !e.isFullyRefunded())
				.collect(Collectors.toMap(RefundItems::getId, item -> item));

		refundRequest.getReturnItems().forEach(refundData -> {
			if (existingRefundItems.keySet().contains(refundData.getId())
					&& existingRefundItems.get(refundData.getId()).isPartiallyRefunded()) {
				updateRefundStatusOnItems(refundData, order, existingRefundItems.get(refundData.getId()).getQuantity());
			} else {
				int existingQuantity = 0;
				updateRefundStatusOnItems(refundData, order, existingQuantity);
			}
		});
		refundRepository.save(refundRequest);
	}

	/**
	 * 
	 * @param refundRequest
	 * @param order
	 * 
	 *                      since the requested item does not exist in DB, consider
	 *                      existing quantity = 0 if requested quantity matches
	 *                      ordered quantity set fully refunded = true if it is less
	 *                      than ordered quantity set partially refunded = true
	 * 
	 */
	private void saveFirstRefundItemsInRefundRepo(RefundRequest refundRequest, Order order) {
		// since no item in repo, consider existing quantity of items in DB = 0
		int existingQuantity = 0;
		// iterate refund request items and update refund status for every item
		refundRequest.getReturnItems().forEach(refundItem -> {
			updateRefundStatusOnItems(refundItem, order, existingQuantity);
		});
		refundRepository.save(refundRequest);
	}

	/**
	 * 
	 * @param refundItem       - refund request item data
	 * @param order            - ordered item data
	 * @param existingQuantity - refunded item quantity in DB
	 * 
	 *                         Function streams the ordered items, pick ordered item
	 *                         matches the refund item if ordered item equals refund
	 *                         item quantity and refund item in DB -> set fully
	 *                         refunded is true, else -> set partially refunded is
	 *                         true
	 * 
	 *                         hence data in Db either fully or partially refunded
	 *                         be true
	 * 
	 */
	public void updateRefundStatusOnItems(RefundItems refundItem, Order order, int existingQuantity) {
		// Match refundItem id with orderedItems id
		order.getOrderedItems().stream().filter(orderedItem -> orderedItem.getId().equals(refundItem.getId()))
				.findFirst().ifPresent(orderedItem -> {
					// Check if quantity matches
					if (orderedItem.getQuantity() == refundItem.getQuantity() + existingQuantity) {
						// Set fullyRefunded to true
						refundItem.setFullyRefunded(true);
					} else {
						// Set partiallyRefunded to true
						refundItem.setPartiallyRefunded(true);
					}
				});
	}

	/**
	 * 
	 * @param refundRequest - Refund request object
	 * @param itemData      - item details from item repository
	 * @return total refund amount
	 * 
	 *         create item data map -> key:id, value:item object iterate refund
	 *         requested data, get the unit price of the item. multiple the unit
	 *         price with refund request quantity sum all the refund price.
	 * 
	 */
	public double calculateRefundAmount(RefundRequest refundRequest, List<Item> itemData) {
		double totalRefund = 0d;

		Map<Long, Item> itemDetails = itemData.stream().collect(Collectors.toMap(Item::getId, item -> item));

		for (RefundItems returnItem : refundRequest.getReturnItems()) {
			double itemUnitPrice = itemDetails.get(returnItem.getId()).getPrice();
			double refundItemPrice = itemUnitPrice * returnItem.getQuantity();
			totalRefund = totalRefund + refundItemPrice;
		}

		return totalRefund;
	}

	/**
	 * 
	 * @param refundRequest - requested data of refund
	 * @return items that are available for refund
	 * 
	 *         Function returns the items that are not refunded yet or partially
	 *         refunded quantity items
	 */
	public Refund showItemsCanRefund(RefundRequest refundRequest) {
		//TODO RETURN ITEMS THAT HAS PARTIALLY REFUNDED = TRUE OR FULLY REFUNDED = FALSE
		return null;
	}
}