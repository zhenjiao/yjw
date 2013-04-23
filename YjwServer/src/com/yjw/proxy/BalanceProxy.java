package com.yjw.proxy;

import com.yjw.dao.BalanceDAO;
import com.yjw.impl.BalanceImpl;

public class BalanceProxy implements BalanceDAO{
	
	private BalanceDAO balancedao;

	public BalanceProxy(){
		
		this.balancedao=new BalanceImpl();
	}
	
	
	public float getBalance(String sid) {
		// TODO Auto-generated method stub
		return this.balancedao.getBalance(sid);
	}

}
