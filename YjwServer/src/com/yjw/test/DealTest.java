package com.yjw.test;

import java.util.ArrayList;

import org.junit.Test;

import com.yjw.bean.DealBean;
import com.yjw.impl.ChatImpl;
import com.yjw.tool.GenerateTool;


public class DealTest {
	
	public static DealBean dealBean;
	@Test
	public void newTest(){
	//	JSONObject object =new GetForwardDealImpl().getInformation(2, 0);
	//	System.out.println(object);
	}
	@Test
	public void addDeal(){
		DealBean dealBean = new DealBean();
		//dealBean.setUser_id(2);
		dealBean.setTitle("sssss");
		dealBean.setContent("dsadsad");
		//dealBean.setCommission(12);
		//dealBean.setFee(12);
		
		String phoneNumber = "1231,321312,31231,3123";
		
		GenerateTool tool = new GenerateTool();
		ArrayList<String> list = tool.getPhoneNumberList(phoneNumber);
		

	}
	@Test
	public void testChat(){
		ChatImpl chatImpl = new ChatImpl();
	//	HashMap<String, Object> map = chatImpl.getUnderReadMsg("13818554170");
	//	System.out.println(map.get("object"));
//		for(Integer a : (ArrayList<Integer>)map.get("list")){
	//		System.out.println(a);
	//	}
	}
	@Test
	public void chatCountTest(){
		ChatImpl chatImpl = new ChatImpl();
		//System.out.println(chatImpl.getUnderReadMsgSize("13818554170"));
	}
	@Test
	public void testIsRead(){
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		ChatImpl chatImpl = new ChatImpl();
		chatImpl.setIsRead(list);
	}
	
}
