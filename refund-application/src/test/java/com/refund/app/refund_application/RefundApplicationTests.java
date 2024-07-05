package com.refund.app.refund_application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.refund.app.refund_application.models.Item;
import com.refund.app.refund_application.models.Order;
import com.refund.app.refund_application.models.Refund;
import com.refund.app.refund_application.models.RefundItems;
import com.refund.app.refund_application.models.RefundRequest;
import com.refund.app.refund_application.repo.OrderRepository;
import com.refund.app.refund_application.repo.RefundRepository;
import com.refund.app.refund_application.services.RefundService;
import com.refund.app.refund_application.testUtils.ItemDataUtil;
import com.refund.app.refund_application.testUtils.RefundTestUtils;

@SpringBootTest
class RefundApplicationTests {
	

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RefundRepository refundRepository;

    @InjectMocks
    private RefundService refundService;
    
    Order order;
    
    RefundRequest refundRequest;
    
	@BeforeEach
	public void setUp() {

		List<Item> orderItems = new ArrayList<Item>();
		orderItems.add(new Item.Builder().id(1L).name("Product1").quantity(1).build());
		orderItems.add(new Item.Builder().id(2L).name("Product2").quantity(1).build());
		orderItems.add(new Item.Builder().id(3L).name("Product3").quantity(1).build());
		orderItems.add(new Item.Builder().id(4L).name("Product4").quantity(2).build());
		orderItems.add(new Item.Builder().id(5L).name("Product5").quantity(5).build());
		orderItems.add(new Item.Builder().id(6L).name("Product6").quantity(1).build());
		orderItems.add(new Item.Builder().id(7L).name("Product7").quantity(2).build());

		order = new Order.Builder().id(12345L).completed(true).orderedItems(orderItems).build();

		refundRequest = new RefundRequest.Builder().orderId(12345L).paymentReferenceId("transac0909")
				.returnItems(RefundTestUtils.CreateRefundItemListSample1()).build();

	}
	
	
	/**
	 * 
	 */
    @Test
    public void testCreateRefund_OrderNotFound() {
    	
    	refundRequest.setOrderId(908L);
    	
        when(orderRepository.findById(refundRequest.getOrderId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            refundService.createRefund(refundRequest);
        });

        assertEquals("Order not found", exception.getMessage());
    }

	@Test
    public void testCreateRefund_OrderNotCompleted() {
		
		order.setCompleted(false);
		
        order.setCompleted(false);
        when(orderRepository.findById(refundRequest.getOrderId())).thenReturn(Optional.of(order));

        Exception exception = assertThrows(Exception.class, () -> {
            refundService.createRefund(refundRequest);
        });

        assertEquals("Order is not completed", exception.getMessage());
    }

	@Test
    public void testCreateRefund_FirstRefund() throws Exception {
		
        when(orderRepository.findById(refundRequest.getOrderId())).thenReturn(Optional.of(order));
        when(refundRepository.findById(refundRequest.getOrderId())).thenReturn(Optional.empty());

        Refund refund = refundService.createRefund(refundRequest);

        verify(refundRepository, times(1)).save(refundRequest);
        assertEquals(refundRequest.getOrderId(), refund.getOrderId());
    }

    @Test
    public void testCreateRefund_PartialOrderRefund() throws Exception {
    	
        when(orderRepository.findById(refundRequest.getOrderId())).thenReturn(Optional.of(order));
        when(refundRepository.findById(refundRequest.getOrderId())).thenReturn(Optional.of(refundRequest));
        
    	RefundRequest newRefundRequest = refundRequest.clone();
    	newRefundRequest.setReturnItems(RefundTestUtils.CreateRefundItemListSample2());

        Refund refund = refundService.createRefund(newRefundRequest);

        verify(refundRepository, times(1)).save(newRefundRequest);
        assertEquals(newRefundRequest.getOrderId(), refund.getOrderId());
    }
    
    @Test
    public void testUpdateRefundStatusOnItems_FullRefund() throws Exception {
    	
    	RefundItems refundItems = RefundTestUtils.CreateRefundItemListSample4();
    	
    	refundService.updateRefundStatusOnItems(refundItems, order, 0);
    	
    	assertTrue(refundItems.isFullyRefunded());
    	
    }

    @Test
    public void testUpdateRefundStatusOnItems_PartialRefund() throws Exception {
    	
    	RefundItems refundItems = RefundTestUtils.CreateRefundItemListSample3();
    	
    	refundService.updateRefundStatusOnItems(refundItems, order, 1);
    	
    	assertTrue(refundItems.isPartiallyRefunded());
    	
    }
    
    @Test
    public void testCalculateRefundAmount_FullItemRefund() {
        
    	List<Item> itemData = ItemDataUtil.itemData();

        double refundAmount = refundService.calculateRefundAmount(refundRequest, itemData);

        assertEquals(40.0, refundAmount);
    }
    
    @Test
    public void testCalculateRefundAmount_PartialItemRefund() {
        
    	List<Item> itemData = ItemDataUtil.itemData();
    	RefundRequest newRefundRequest = refundRequest.clone();
    	newRefundRequest.setReturnItems(RefundTestUtils.CreateRefundItemListSample5());

        double refundAmount = refundService.calculateRefundAmount(newRefundRequest, itemData);

        assertEquals(10.0, refundAmount);
    }
}