package com.yjw.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.yjw.sql.UserSQL;
import com.yjw.tool.GenerateTool;
import com.yjw.ws.SendSoapMessage;

public class ToolTest {

	@Test
	public void testGetPhoneNumberList() {
		System.out.print(new GenerateTool().genValidateString());
	}
	
	

}
