package com.yjw.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Util {
	public static String KEY = "yjw_pw";
	
	public static boolean isEmpty(Object s){
		if (s==null) return true;
		if (s instanceof String&&((String)s).length()==0) return true;
		return false;
	}
	
	// 整数到字节数组转换
	public static byte[] int2bytes(int n) {
		byte[] ab = new byte[4];
		ab[0] = (byte) (0xff & n);
		ab[1] = (byte) ((0xff00 & n) >> 8);
		ab[2] = (byte) ((0xff0000 & n) >> 16);
		ab[3] = (byte) ((0xff000000 & n) >> 24);
		return ab;
	}

	// 字节数组到整数的转换
	public static int bytes2int(byte b[]) {
		int s = 0;
		s = ((((b[3] & 0xff) << 8 | (b[2] & 0xff)) << 8) | (b[1] & 0xff)) << 8 | (b[0] & 0xff);
		return s;
	}
	
	@SuppressWarnings("unchecked")
	public static<T> T[] list2Arr(List<Object> list,Class<T> type){
		T[] ret=(T[])Array.newInstance(type,list.size());
		list.toArray(ret);
		return ret;		
	}
	
	public static<T> List<T> arr2List(T[] array){
		List<T> ret=new ArrayList<T>();
		for (T e:array){
			ret.add(e);
		}
		return ret;
	}
}
