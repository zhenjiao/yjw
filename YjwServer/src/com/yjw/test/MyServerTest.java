package com.yjw.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServerTest {

	public static void main(String[] args){
		
		try {
			//创建监听
			System.out.println("I am listening\n");
			ServerSocket ss = new ServerSocket(8888);
			//监听，知道有客户端连接
			Socket socket = ss.accept();
			
			//开始读取客户端发来的信息
			InputStreamReader ist = new InputStreamReader(socket.getInputStream());
			BufferedReader br = new BufferedReader(ist);
			
			String ssString = br.readLine();
			
			System.out.println("The message:"+ssString);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
