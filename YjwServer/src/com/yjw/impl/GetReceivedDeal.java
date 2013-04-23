package com.yjw.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;

import com.yjw.dao.InformationDAO;
import com.yjw.sql.InformationSQL;
import com.yjw.tool.GenerateTool;
import com.yjw.tool.GetJdbcTemplate;

/*获取收到的推荐交易
 * 包括别人发的和转发的
 */
public class GetReceivedDeal implements InformationDAO {

	private final int PAGE_SIZE = 20;

	public JSONObject getInformation(int user_id, int pageIndex) {
		// 获取分页信息
		int pageSize = PAGE_SIZE;
		int startCount = pageIndex * pageSize;
		int endCount = (pageIndex + 1) * pageSize;
		/* 查看还剩下几个生意 */
		int flag = jdbcTemplate.queryForInt(InformationDAO.userSQL.getRecDealCount(
				user_id, startCount, endCount));
		List<?> list;
		JSONObject object = new JSONObject();
		if (flag != 0) {
			list = jdbcTemplate.queryForList(InformationDAO.userSQL.getReceivedDeal(
					user_id, startCount, endCount));
			object = listToJSON(list);
		} else {
			Map<String, String> map = new HashMap<String, String>();
			map.put("message", "empty");
			object = new JSONObject(map);
		}

		return object;
	}

	/**
	 * Set status of recieved deal.
	 */
	public boolean setStatus(String deal_id, String phoneNumber, String status) {
		boolean flag = false;
		String sql = "update yjw_deal_intr set rec_status='" + status
				+ "' where deal_id='" + deal_id + "' and" + " phone_number='"
				+ phoneNumber + "'";
		int i = jdbcTemplate.update(sql);
		if (i > 0) {
			flag = true;
		}
		return flag;
	}

	// 把从数据库查询得到的LIST转换为JSON
	public JSONObject listToJSON(List<?> list) {
		JSONObject object = new JSONObject();
		Iterator<?> it = list.iterator();
		int i = 1;
		while (it.hasNext()) {
			try {
				Map<?, ?> map = (Map<?, ?>) it.next();
				// System.out.println(map.get("req_confirm"));
			
				if (map.get("rec_status").equals("decline")) {

				} else {
					if (map.get("req_confirm").equals("No")) {
						map.remove("rec_status");
					}
					object.put(i + "", map);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				i++;
			}
		}
		return object;
	}

}
