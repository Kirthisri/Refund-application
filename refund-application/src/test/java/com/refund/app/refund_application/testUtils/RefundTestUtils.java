package com.refund.app.refund_application.testUtils;

import java.util.ArrayList;
import java.util.List;

import com.refund.app.refund_application.models.RefundItems;

public class RefundTestUtils {
	
	public static List<RefundItems> CreateRefundItemListSample1() {
		
		List<RefundItems> refundItemList = new ArrayList<RefundItems>();
		
		refundItemList.add(new RefundItems.Builder().id(1L).name("Product1").quantity(1).build());
		refundItemList.add(new RefundItems.Builder().id(2L).name("Product2").quantity(1).build());
		refundItemList.add(new RefundItems.Builder().id(3L).name("Product3").quantity(1).build());
		
		return refundItemList;
	}
	
	public static List<RefundItems> CreateRefundItemListSample2() {
		
		List<RefundItems> refundItemList = new ArrayList<RefundItems>();
		
		refundItemList.add(new RefundItems.Builder().id(4L).name("Product4").quantity(2).build());
		
		return refundItemList;
	}
	
	public static RefundItems CreateRefundItemListSample3() {
		
		return new RefundItems.Builder().id(7L).name("Product7").quantity(1).build();
	}
	
	public static RefundItems CreateRefundItemListSample4() {
		
		return new RefundItems.Builder().id(6L).name("Product6").quantity(1).build();
	}
	
	public static List<RefundItems> CreateRefundItemListSample5() {
		
		List<RefundItems> refundItemList = new ArrayList<RefundItems>();		
		refundItemList.add(new RefundItems.Builder().id(5L).name("Product5").quantity(2).build());
		return refundItemList;
	}
	
}
