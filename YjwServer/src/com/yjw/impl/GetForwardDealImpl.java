package com.yjw.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;

import com.yjw.dao.InformationDAO;
import com.yjw.tool.GenerateTool;

/**
 * 接受到的别人转发的生意
 * @author Glorabit
 *
 */
public class GetForwardDealImpl implements InformationDAO {

	private final int PAGE_SIZE = 20;

	public JSONObject getInformation(int user_id, int pageIndex) {
		// 获取分页信息
		int pageSize = PAGE_SIZE;
		int startCount = pageIndex * pageSize;
		int endCount = (pageIndex + 1) * pageSize;
		/* 查看还剩下几个生意 */
		int flag = jdbcTemplate.queryForInt(InformationDAO.userSQL.getForwardDealCount(
				user_id, startCount, endCount));
		List<?> list;
		JSONObject object = new JSONObject();
		if (flag != 0) {
			list = jdbcTemplate.queryForList(InformationDAO.userSQL.getForwardDeal(
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
