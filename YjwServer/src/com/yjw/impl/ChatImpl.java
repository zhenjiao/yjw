package com.yjw.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.yjw.bean.ChatBean;
import com.yjw.dao.ChatDAO;
import com.yjw.sql.ChatSQL;
import com.yjw.tool.BeanPacker;
import com.yjw.tool.TemplateGetter;

public class ChatImpl implements ChatDAO {

	private JdbcTemplate jdbcTemplate;
	private ChatSQL sql;

	public ChatImpl() {
		this.jdbcTemplate = TemplateGetter.getJtl();
		this.sql = new ChatSQL();
	}

	/* 加入一条chat */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yjw.impl.ChatDAO#setChat(com.yjw.bean.ChatBean)
	 */
	public boolean setChat(ChatBean chatBean) {
		int i = jdbcTemplate.update(sql.setChat(chatBean));
		return i>=0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yjw.impl.ChatDAO#getUnderReadMsg(java.lang.String)
	 */
	public HashMap<String, Object> getUnreadMsg(String phoneNumber) {
		List list = null;
		try{
			 list = jdbcTemplate.queryForList(sql.getUnreadMsg(phoneNumber));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		// 存储需要设置为已读的信息Id
		ArrayList<Integer> arrayList = new ArrayList<Integer>();

		String beans="";
		Iterator<Map> it = list.iterator();
		while (it.hasNext()) {
			Map map = it.next();
			arrayList.add(Integer.parseInt(map.get("id").toString()));
			beans+="&"+new BeanPacker(map,ChatBean.class);
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("object", beans);
		map.put("list", arrayList);
		return map;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.yjw.impl.ChatDAO#getUnderReadMsgSize(java.lang.String)
	 */
	public int getUnreadMsgCount(String id) {
		int size = 0;
		size = this.jdbcTemplate.queryForInt(sql.getUnreadMsgCount(id));
		return size;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.yjw.impl.ChatDAO#setIsRead()
	 */
	public boolean setIsRead(ArrayList<Integer> list) {
		boolean flag = false;
		final ArrayList<Integer>  finalList = list;
		String sql = "update yjw_chat set is_read='1' where id=?";
		int[] i = this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {			
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1,finalList.get(i));
			}			
			public int getBatchSize() {
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

	public String getCellphoneById(String id) {
		String ret = null;
		try{
			jdbcTemplate.queryForInt(sql.getPhoneNumber(id));
		}catch(IncorrectResultSizeDataAccessException e){
			System.out.println("Can't find 'cellphone'");
		}catch(DataAccessException e){
			System.out.println("Unable to access the database.");
		}
		return ret;
	}
}
