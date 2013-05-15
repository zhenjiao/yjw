package com.app.yjw.ctrl;

import android.os.Message;

import com.app.yjw.TestActivity;
import com.app.yjw.YJWActivity;
import com.app.yjw.thread.SyncDealThread;
import com.app.yjw.thread.SyncTransThread;
import com.app.yjw.thread.SyncUserThread;
import com.app.yjw.util.YJWMessage;
import com.yjw.bean.GetInfoBean;

public class GetDealCtrl extends BaseCtrl{
	
	YJWMessage[] getKey() {
		YJWMessage[] s={YJWMessage.SYNC_TRANS_SUCCESS,YJWMessage.BUFFER_USER,YJWMessage.BUFFER_DEAL};
		return s;
	}
	
	@Override
	public boolean handleMessage(Message msg) {
		if (getActivity() instanceof TestActivity){
			((TestActivity)getActivity()).refresh();
		}
		return false;
	}

	public void syncTrans(int page){
		GetInfoBean bean = new GetInfoBean();
		bean.setId(YJWActivity.user.getId());
		bean.setPage(page);
		YJWControler.Start(SyncTransThread.class, bean);
	}
	
	public void syncDeal(int page){
		GetInfoBean bean = new GetInfoBean();
		bean.setId(YJWActivity.user.getId());
		bean.setPage(page);
		YJWControler.Start(SyncDealThread.class, bean);
	}
	
	public void syncUser(){
		YJWControler.Start(SyncUserThread.class, YJWActivity.user.getId());
	}

}
