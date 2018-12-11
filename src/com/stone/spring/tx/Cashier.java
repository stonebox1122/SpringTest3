package com.stone.spring.tx;

import java.util.List;

public interface Cashier {
	public void checkout(String username, List<Integer> isbns);
}
