package com.yjw.bean;

import java.io.Serializable;

public class Version implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5475825529831520805L;

	public String VersionNumber; //android:versionName="1.0.0.1" use the same format
	public String MinimalVersionNumber; // the minimal version should the client be
	public boolean ForceUpdate; // if true force update
	public String UpdateURL; // should be legal. Client wont check
}
