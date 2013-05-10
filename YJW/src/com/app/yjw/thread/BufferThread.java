package com.app.yjw.thread;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.message.BasicNameValuePair;

import com.app.yjw.ctrl.YJWControler;
import com.app.yjw.net.NetworkConstants;
import com.app.yjw.util.BeanPacker;
import com.app.yjw.util.G;
import com.app.yjw.util.YJWMessage;
import com.yjw.bean.DealBean;
import com.yjw.bean.TransBean;
import com.yjw.bean.UserBean;

/** 当发现未缓冲或未更新数据时报告在这里 */
/**完成后发回BUFFER_USER或BUFFER_DEAL消息*/
public class BufferThread extends YJWBaseThread {
	
	private final int DELAY_TIME=1000; 
	static private Set<Integer> trans=new HashSet<Integer>();
	static private Set<Integer> user=new HashSet<Integer>();
	static private Set<String> userPhone=new HashSet<String>();
	static private Set<Integer> deal=new HashSet<Integer>();
	private Set<?> set;
	private static BufferThread instance=null;
	public static BufferThread getInstance(){
		if (instance==null) 
			instance=(BufferThread)YJWControler.Start(BufferThread.class, null);
		return instance;
	}
	
	synchronized public static void addUser(int id){
		getInstance();
		synchronized (user) {
			user.add(id);
		}		
	}
	
	synchronized public static void addUser(String cellphone){
		getInstance();
		synchronized (userPhone) {
			userPhone.add(cellphone);
		}		
	}
	
	synchronized public static void addDeal(int id){
		getInstance();
		synchronized (deal) {
			deal.add(id);
		}		
	}
	synchronized public static void addTrans(int id){
		getInstance();
		synchronized (trans) {
			trans.add(id);
		}		
	}
	
	@Override
	protected String generateURL() {
		if (set==user) return NetworkConstants.URL_GETUSER; else 
		if (set==deal) return NetworkConstants.URL_GETDEAL; else
		if (set==trans)return NetworkConstants.URL_GETTRANS;else
		if (set==userPhone)return NetworkConstants.URL_GETUSERBYPHONE;else
		return null;
	}
	
	@Override
	protected List<BasicNameValuePair> generateParameters() {
		if (set==null) return null; 
		List<BasicNameValuePair> ret = new ArrayList<BasicNameValuePair>();
		ret.add(new BasicNameValuePair("size", ((Integer)set.size()).toString()));
		Integer i=0;
		for (Object x:set)
			ret.add(new BasicNameValuePair((i++).toString(),x.toString()));
		return ret;
	}

	@Override
	protected void init() {
		if (trans.size()!=0)		set=trans;		else
		if (deal.size()!=0) 		set=deal;		else
		if (user.size()!=0) 		set=user;		else
		if (userPhone.size()!=0) 	set=userPhone;	else
			set=null;
	}
	
	@Override
	public void run() {
		while(G.getIsRun()){
			//Log.i("Buffer Thread", "check");
			try {
				sleep(DELAY_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (user.size()==0&&deal.size()==0&&trans.size()==0&&userPhone.size()==0) continue;
			super.run();
		}
	}
	
	@Override
	protected void OnSuccess() {
		if (set!=null){
			for (int i=1;i<back.length;++i){
				if (set==user){
					UserBean bean=(UserBean)new BeanPacker(back[i]).getBean();
					G.addUser(bean);
					msg.what=YJWMessage.BUFFER_USER.ordinal();
				}else if (set==deal){
					DealBean bean=(DealBean)new BeanPacker(back[i]).getBean();
					G.addDeal(bean);
					msg.what=YJWMessage.BUFFER_DEAL.ordinal();
				}else if (set==trans){
					TransBean bean=(TransBean)new BeanPacker(back[i]).getBean();
					G.addTrans(bean);
					msg.what=YJWMessage.BUFFER_DEAL.ordinal();
				}else if (set==userPhone){
					UserBean bean=(UserBean)new BeanPacker(back[i]).getBean();
					G.addUser(bean);
					msg.what=YJWMessage.BUFFER_USER.ordinal();
				}
			}
		}else{
			msg.what=YJWMessage.YJWMESSAGE_NULL.ordinal();
		}
		set.clear();		
		super.OnSuccess();
	}
	
	@Override
	protected void finalize() throws Throwable {
		instance=null;
		super.finalize();
	}

}


