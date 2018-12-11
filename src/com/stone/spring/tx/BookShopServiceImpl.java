package com.stone.spring.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("bookShopService")
public class BookShopServiceImpl implements BookShopService {
	
	@Autowired
	private BookShopDao bookShopDao;
	
	//添加事物注解
	//1.使用propagation指定事物的传播行为，即当前的事物方法被另外一个事物方法调用时如何使用事物
	//默认取值为REQUIRED，即使用调用方法的事物
	//REQUIRED_NEW，使用自己的事物，调用方法的事物被挂起
	//2。使用isolation指定事物的隔离级别，最常用的取值为READ_COMMITTED
	//3.默认情况下Spring的声明式事务对所有的运行时异常进行回滚，也可以通过对应的属性进行设置。通常情况下默认即可
	//@Transactional(propagation=Propagation.REQUIRES_NEW, noRollbackFor= {UserAccountException.class})
	//4.使用readOnly指定事物是否为只读。表示这个事物只读取数据但不更新数据，可以帮助数据库引擎优化事物
	//若真的是一个只读取数据库值的方法，应设置readOnly=true
	//5.使用timeout指定强制回滚之前事物可以占用的时间
	@Transactional(propagation=Propagation.REQUIRES_NEW,
			isolation=Isolation.READ_COMMITTED,
			readOnly=false,
			timeout=10)
	@Override
	public void purchase(String username, int isbn) {
		
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
		//1.获取书的单价
		double price = bookShopDao.findBookPriceByIsbn(isbn);
		
		//2.更新书的库存
		bookShopDao.updateBookStock(isbn);
		
		//更新用户余额
		bookShopDao.updateUserAccount(username, price);
	}
}
