package com.yjw.test;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.yjw.bean.UserBean;
import com.yjw.impl.RegisterImpl;
import com.yjw.tool.GetJdbcTemplate;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class RegisterTest {
	public UserBean userBean;
	
	@BeforeClass
	public static void beforeClass(){
		System.out.println("Run BeforeClass Method\n");
	}
	
	@org.junit.Before
	public void befores(){
		 System.out.println("Run Before Method\n");
		 userBean = new UserBean();
		 userBean.setCellphone("13917774194");
		 userBean.setEmail("xiaoguo415@gmail.com");
		 userBean.setPassword("1234");
		 userBean.setSid("sdasuidashjd1");
	}
	@Test(timeout=2000)
	public void RegisterTest(){
		
		
		System.out.println("Run Test1\n");
		assertThat(new RegisterImpl(new GetJdbcTemplate().getJtl()).register(this.userBean), is(true));
	}
	@Test(timeout=2000)
	public void RegisterTest2(){
		System.out.println("Run Test2\n");
		assertThat(new RegisterImpl(new GetJdbcTemplate().getJtl()).register(this.userBean), is(true));
	}
	@Test(timeout=2000)
	public void RegisterTest3(){
		System.out.println("Run Test3\n");
		assertThat(new RegisterImpl(new GetJdbcTemplate().getJtl()).register(this.userBean), is(true));
	}
	@org.junit.After
	public void after(){
		System.out.println("Run After Method\n");
	}
	@AfterClass
	public static void afterClass(){
		System.out.println("Run AfterClass\n");
	}
	
}
