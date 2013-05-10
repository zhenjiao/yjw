package com.yjw.dao;

import org.json.JSONObject;

/*作为所有获取关于一个用户的信息的接口*/
public interface InformationDAO {
	public JSONObject getInformation(int user_id, int pageIndex);
	
}
