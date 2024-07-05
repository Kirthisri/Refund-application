package com.refund.app.refund_application.testUtils;

import java.util.ArrayList;
import java.util.List;

import com.refund.app.refund_application.models.Item;

public class ItemDataUtil {

	public static List<Item> itemData(){
		List<Item> itemData = new ArrayList<Item>();
		
		itemData.add(new Item.Builder().id(1L).name("Product1").price(10.0).quantity(1).build());
		itemData.add(new Item.Builder().id(2L).name("Product2").price(20.0).quantity(1).build());
		itemData.add(new Item.Builder().id(3L).name("Product3").price(10.0).quantity(1).build());
		itemData.add(new Item.Builder().id(4L).name("Product4").price(20.0).quantity(1).build());
		itemData.add(new Item.Builder().id(5L).name("Product5").price(5.0).quantity(1).build());
		itemData.add(new Item.Builder().id(6L).name("Product6").price(30.0).quantity(1).build());
		itemData.add(new Item.Builder().id(7L).name("Product7").price(10.0).quantity(1).build());
		itemData.add(new Item.Builder().id(8L).name("Product8").price(5.0).quantity(1).build());
		
		return itemData;
	}
	
}
