package com.yjw.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServerTest {

	public static void main(String[] args){
		
		try {
			//��������
			System.out.println("I am listening\n");
			ServerSocket ss = new ServerSocket(8888);
			//������֪���пͻ�������
			Socket socket = ss.accept();
			
			//��ʼ��ȡ�ͻ��˷�������Ϣ
			InputStreamReader ist = new InputStreamReader(socket.getInputStream());
			BufferedReader br = new BufferedReader(ist);
			
			String ssString = br.readLine();
			
			System.out.println("The message:"+ssString);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
