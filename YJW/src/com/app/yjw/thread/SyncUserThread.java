package com.app.yjw.thread;

import com.app.yjw.net.NetworkConstants;
import com.app.yjw.util.BeanPacker;
import com.app.yjw.util.G;
import com.app.yjw.util.YJWMessage;
import com.yjw.bean.ContactBean;
import com.yjw.bean.ContactsBean;

public class SyncUserThread extends YJWBaseThread {

	@Override
	protected String generateURL() {
		return NetworkConstants.URL_SYNCUSER;
	}

	@Override
	protected void init() {}
	
	@Override
	protected void OnSuccess() {
		BeanPacker packer=new BeanPacker(back[1]);
		ContactsBean cbs=(ContactsBean)packer.getBean();
		ContactBean[] beans=cbs.getContacts();
		if (beans!=null){
			for (ContactBean bean:beans){
				if (!G.cellphones.contains(bean.getCellphone())){
					G.cellphones.add(bean.getCellphone());
				}
				BufferThread.addUser(bean.getCellphone());
			}
		}
		msg.what=YJWMessage.SYNC_USER_SUCCESS.ordinal();
		super.OnSuccess();
	}

}
