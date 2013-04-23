package com.yjw.bean;

import java.io.Serializable;

public class TestObject implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8228592867676053476L;
	public String name;
	public TestObject(String n)
	{
		name = n;
	}
}