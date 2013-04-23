package com.yjw.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.yjw.dao.InformationDAO;
import com.yjw.sql.InformationSQL;
import com.yjw.tool.GenerateTool;
/*��ȡ�������Ľ���*/
public class GetPublishedDeal implements InformationDAO{
	private final int PAGE_SIZE = 20;
	 private InformationSQL sql = new InformationSQL();
	public JSONObject getInformation(int user_id,int pageIndex){
		// ��ȡ��ҳ��Ϣ
		int pageSize = PAGE_SIZE;
		int startCount = pageIndex * pageSize;
		int endCount = (pageIndex + 1) * pageSize;
		/* �鿴��ʣ�¼������� */
		/*int flag = jdbcTemplate.queryForInt(this.sql.getSyncCount(user_id,
				startCount, endCount));*/
		int flag = jdbcTemplate.queryForInt(InformationDAO.userSQL.getPublishedDealCount(
				user_id, startCount, endCount));
		List<?> list;
		JSONObject object = new JSONObject();
		if (flag != 0) {
			/*list = jdbcTemplate.queryForList(this.sql.syncDeal(user_id,
					startCount, endCount));*/
			list = jdbcTemplate.queryForList(InformationDAO.userSQL.getPublishedDeal(
					user_id, startCount, endCount));
			object = new GenerateTool().listToJSON(list);
		} else {
			Map<String, String> map = new HashMap<String, String>();
			map.put("message", "empty");
			object = new JSONObject(map);
		}
		return object;
	}
}
