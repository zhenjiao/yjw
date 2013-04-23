package com.yjw.bean;
/*
 * 
 * by Jinyuan Yang
 *
 * */
 
import java.io.Serializable;



public class Version implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5475825529831520805L;
	public String VersionNumber="1.0.0.24"; //android:versionName="1.0.0.1" use the same format
	public String MinimalVersionNumber="1.0.0.0"; // the minimal version should the client be
	public boolean ForceUpdate=false; // if true force update
	public String UpdateURL="sselab.tongji.edu.cn/YjwServer/YJW.apk"; // should be legal. Client wont check
	//public String UpdateURL="192.168.1.110:8080/YjwServer/YJW_1.0.0.10.apk";

	
	//public Version(String UpdateURL){
	//	this.UpdateURL=UpdateURL;
	//}
	
}

