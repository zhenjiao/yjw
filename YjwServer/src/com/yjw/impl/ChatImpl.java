package com.yjw.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.yjw.bean.ChatBean;
import com.yjw.dao.ChatDAO;
import com.yjw.sql.ChatSQL;
import com.yjw.tool.GetJdbcTemplate;

public class ChatImpl implements ChatDAO {

	private JdbcTemplate jdbcTemplate;
	private ChatSQL chatSQL;

	public ChatImpl() {
		this.jdbcTemplate = new GetJdbcTemplate().getJtl();
		this.chatSQL = new ChatSQL();
	}

	/* 加入一条chat */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yjw.impl.ChatDAO#setChat(com.yjw.bean.ChatBean)
	 */
	public boolean setChat(ChatBean chatBean) {
		boolean flag = false;
		int i = jdbcTemplate.update(this.chatSQL.setChat(chatBean));
		if (i >= 0) {
			flag = true;
		}

		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yjw.impl.ChatDAO#getUnderReadMsg(java.lang.String)
	 */
	public HashMap<String, Object> getUnderReadMsg(String phoneNumber) {
		JSONObject object = new JSONObject();
		List<?> list = this.jdbcTemplate.queryForList(this.chatSQL
				.getUnderReadMsh(phoneNumber));
		// 存储需要设置为已读的信息Id
		ArrayList<Integer> arrayList = new ArrayList<Integer>();

		int i = 1;
		Iterator<?> it = list.iterator();
		while (it.hasNext()) {
			Map<?, ?> map = (Map<?, ?>) it.next();
			arrayList.add(Integer.parseInt(map.get("chat_id").toString()));
			try {
				object.put(i + "", map);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				i++;
			}
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("object", object);
		map.put("list", arrayList);
		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yjw.impl.ChatDAO#getUnderReadMsgSize(java.lang.String)
	 */
	public int getUnderReadMsgSize(String phoneNumber) {
		int size = 0;
		size = this.jdbcTemplate.queryForInt(this.chatSQL
				.getUnderReadMsgSize(phoneNumber));
		return size;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yjw.impl.ChatDAO#setIsRead()
	 */
	public boolean setIsRead(ArrayList<Integer> list) {
		boolean flag = false;
		final ArrayList<Integer>  finalList = list;
		String sql = "update yjw_chat set is_read='1' where chat_id=?";
		int[] i = this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				// TODO Auto-generated method stub
				ps.setInt(1,finalList.get(i));
			}
			
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return finalList.size();
			}
		});
		flag = true;
		for (int a = 0; a < i.length; a++) {
			if (i[a] < 1) {
				flag = false;
				System.out.println(a + " Chat_ID Share Problem");
				break;
			}
		}
		return flag;
	}
}
