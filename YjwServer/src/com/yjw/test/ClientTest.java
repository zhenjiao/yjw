package com.yjw.test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientTest {
	
	public static void main(String[] args){
		//Á¬½Ó
		try {
			Socket socket = new Socket("180.160.39.185",8888);
			
			OutputStreamWriter ops = new OutputStreamWriter(socket.getOutputStream());
			BufferedWriter bw = new BufferedWriter(ops);
			
			PrintWriter pw = new  PrintWriter(bw,true);
			
			pw.append("Wo lo haha!!");
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
