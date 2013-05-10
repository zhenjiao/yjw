package com.yjw.tool;

public class Util {
	public static boolean isEmpty(Object s){
		if (s==null) return true;
		if (s instanceof String&&((String)s).length()==0) return true;
		return false;
	}
}
