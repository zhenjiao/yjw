package com.yjw.dao;

import org.json.JSONObject;

/*��Ϊ���л�ȡ����һ���û�����Ϣ�Ľӿ�*/
public interface InformationDAO {
	public JSONObject getInformation(int user_id, int pageIndex);
	
}
