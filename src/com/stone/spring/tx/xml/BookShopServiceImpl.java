package com.stone.spring.tx.xml;

public class BookShopServiceImpl implements BookShopService {
	
	private BookShopDao bookShopDao;
	public void setBookShopDao(BookShopDao bookShopDao) {
		this.bookShopDao = bookShopDao;
	}
	
	@Override
	public void purchase(String username, int isbn) {
		
		//1.获取书的单价
		double price = bookShopDao.findBookPriceByIsbn(isbn);
		
		//2.更新书的库存
		bookShopDao.updateBookStock(isbn);
		
		//更新用户余额
		bookShopDao.updateUserAccount(username, price);
	}
}
