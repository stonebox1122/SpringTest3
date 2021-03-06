package com.stone.spring.tx.xml;

import org.springframework.jdbc.core.JdbcTemplate;

public class BookShopDaoImpl implements BookShopDao {
	
	private JdbcTemplate jdbcTemplate;
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public double findBookPriceByIsbn(int isbn) {
		String sql = "select price from book where isbn=?";
		return jdbcTemplate.queryForObject(sql, Double.class, isbn);
	}

	@Override
	public void updateBookStock(int isbn) {
		//检查书的库存是否足够，若不够，则抛出异常
		String sql2 = "select stock from book_stock where isbn=?";
		int stock = jdbcTemplate.queryForObject(sql2, Integer.class, isbn);
		if (stock == 0) {
			throw new BookStockException("库存不足");
		}
		
		String sql = "update book_stock set stock=stock-1 where isbn=?";
		jdbcTemplate.update(sql, isbn);
		
	}

	@Override
	public void updateUserAccount(String username, double price) {
		//验证余额是否足够，若不足，则抛出异常
		String sql2 = "select balance from account where username=?";
		double balance = jdbcTemplate.queryForObject(sql2, double.class, username);
		if (balance < price) {
			throw new UserAccountException("余额不足");
		}
		
		String sql = "update account set balance=balance-? where username=?";
		jdbcTemplate.update(sql, price, username);		
	}
}
