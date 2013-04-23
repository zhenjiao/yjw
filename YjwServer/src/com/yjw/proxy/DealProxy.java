package com.yjw.proxy;

import java.util.ArrayList;

import org.json.JSONObject;

import com.yjw.bean.DealBean;
import com.yjw.dao.DealDAO;
import com.yjw.impl.DealImpl;

/*对交易处理的代理类*/
public class DealProxy implements DealDAO {
	private DealDAO dealDAO;

	public DealProxy() {
		this.dealDAO = new DealImpl();
	}

	public boolean addDeal(DealBean dealBean,ArrayList<String> phoneNumber,String reqConfirm) {
		return this.dealDAO.addDeal(dealBean,phoneNumber,reqConfirm);
	}

	public boolean delDeal(int id) {
		return this.dealDAO.delDeal(id);
	}

	public JSONObject syncDeal(int user_id, int pageIndex) {
		return this.dealDAO.syncDeal(user_id,pageIndex);
	}

	public boolean updateDeal(DealBean dealBean) {
		return this.dealDAO.updateDeal(dealBean);
	}
	public boolean forwardDeal(int dealId,int for_user_id,ArrayList<String> phoneNumber){
		return this.dealDAO.forwardDeal(dealId, for_user_id,phoneNumber);
	}

	public String check(ArrayList<String> phoneNumber) {
		// TODO Auto-generated method stub
		return this.dealDAO.check(phoneNumber);
	}
}
