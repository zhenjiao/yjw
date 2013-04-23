package com.yjw.dao;

import java.util.ArrayList;

import org.json.JSONObject;

import com.yjw.bean.DealBean;


public interface DealDAO {
	public boolean addDeal(DealBean dealBean,ArrayList<String> phoneNumber,String reqConfirm);
	public boolean delDeal(int id);
	public JSONObject syncDeal(int user_id,int pageIndex);
	public boolean updateDeal(DealBean dealBean);
	public boolean forwardDeal(int dealId,int for_user_id,ArrayList<String> phoneNumber);
	public String check(ArrayList<String> phoneNumber);
}
