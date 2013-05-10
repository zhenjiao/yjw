package com.app.yjw.util;

import java.util.ArrayList;

import com.app.yjw.database.DBProxy;
import com.app.yjw.database.DBStatic;
import com.app.yjw.thread.BufferThread;
import com.yjw.bean.DealBean;
import com.yjw.bean.TransBean;
import com.yjw.bean.UserBean;

/**缓冲的数据和全局变量*/
public class G {
	static public ArrayList<Integer> transes=new ArrayList<Integer>();
	static public ArrayList<Integer> deals=new ArrayList<Integer>();
	static public ArrayList<String> cellphones=new ArrayList<String>();
	
	public static void addTrans(TransBean bean){
		DBProxy.insertPacker(new BeanPacker(bean), DBStatic.TransTableName);
	}
	
	public static void addUser(UserBean bean){
		DBProxy.insertPacker(new BeanPacker(bean), DBStatic.UserTableName);
	}
	
	public static void addDeal(DealBean bean){
		DBProxy.insertPacker(new BeanPacker(bean), DBStatic.DealTableName);
	}
	
	public static TransBean getTrans(Integer id){		
		BeanPacker bean=DBProxy.SelectPacker(DBStatic.TransTableName, "id="+id, TransBean.class);
		if (bean==null) {
			BufferThread.addTrans(id);
			return null;
		}
		return (TransBean)bean.getBean();
	}
	
	public static UserBean getUser(Integer id){
		BeanPacker bean=DBProxy.SelectPacker(DBStatic.UserTableName, "id="+id, UserBean.class);
		if (bean==null) {
			BufferThread.addUser(id);
			return null;
		}
		return (UserBean)bean.getBean();
	}
	
	public static UserBean getUser(String cellphone){
		BeanPacker bean=DBProxy.SelectPacker(DBStatic.UserTableName, "cellphone="+cellphone, UserBean.class);
		if (bean==null) {
			BufferThread.addUser(cellphone);
			return null;
		}
		return (UserBean)bean.getBean();
	}
	
	public static DealBean getDeal(Integer id){
		BeanPacker bean=DBProxy.SelectPacker(DBStatic.DealTableName, "id="+id, DealBean.class);
		if (bean==null){
			BufferThread.addDeal(id);
			return null;
		}
		return (DealBean)bean.getBean();
	}
	
	private static Boolean isRun = true;	
	public static Boolean getIsRun() {
		boolean ret=false;
		synchronized (isRun) {
			ret=isRun;
		}
		return ret;
	}
	
	public static void setIsRun(Boolean isRun) {
		synchronized (isRun) {
			G.isRun = isRun;		
		}		
	}
}
