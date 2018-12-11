package com.stone.spring.tx.xml;

import java.util.List;

public class CashierImpl implements Cashier {
	
	private BookShopService bookShopService;
	
	public void setBookShopService(BookShopService bookShopService) {
		this.bookShopService = bookShopService;
	}
	
	@Override
	public void checkout(String username, List<Integer> isbns) {
		for (Integer isbn : isbns) {
			bookShopService.purchase(username, isbn);
		}	
	}
}
