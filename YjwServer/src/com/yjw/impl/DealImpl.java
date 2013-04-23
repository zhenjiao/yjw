package com.yjw.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.text.DefaultEditorKit.InsertBreakAction;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.yjw.bean.DealBean;
import com.yjw.dao.DealDAO;
import com.yjw.sql.InformationSQL;
import com.yjw.sql.prepareSQL;
import com.yjw.tool.GetJdbcTemplate;

/*关于交易的具体实现封装*/
public class DealImpl implements DealDAO {

	private JdbcTemplate jdbcTemplate;
	private InformationSQL sql;
	private final int PAGE_SIZE = 20;

	public DealImpl() {
		this.jdbcTemplate = new GetJdbcTemplate().getJtl();
		this.sql = new InformationSQL();
	}

	/* Add Deal */
	public boolean addDeal(DealBean dealBean, ArrayList<String> phoneNumber,String reqConfirm) {
		boolean flag = false;
		if (this.jdbcTemplate.update(this.sql.addDeal(dealBean)) >= 1) {

			// 如果成功,继续加入需要分享的电话号码
			if (phoneNumber.size() != 0) {

				// 获取刚才插入的Deal的ID
				final int deal_id = this.jdbcTemplate
						.queryForInt("Select MAX(id) from yjw_deal");
				// 插入需要分享的电话号码
				String sql ="";
				if(reqConfirm.equals("Yes")){
					 sql = "Insert into yjw_deal_intr(user_id,deal_id,phone_number,type,rec_status) " +
					 		"values(?,?,?,'NEW','new')";
				}else{
					 sql = "Insert into yjw_deal_intr(user_id,deal_id,phone_number,type,rec_status) " +
				 		"values(?,?,?,'NEW','none')";
				}
				final ArrayList<String> list = phoneNumber;
				final DealBean bean = dealBean;

				int[] i = this.jdbcTemplate.batchUpdate(sql,
						new BatchPreparedStatementSetter() {

							public void setValues(PreparedStatement ps, int i)
									throws SQLException {
								// TODO Auto-generated method stub
								ps.setInt(1, bean.getUser_id());
								ps.setInt(2, deal_id);
								ps.setString(3, list.get(i).toString());
							}

							public int getBatchSize() {
								// TODO Auto-generated method stub
								return list.size();
							}
						});
				flag = true;
				for (int a = 0; a < i.length; a++) {
					if (i[a] < 1) {
						flag = false;
						System.out.println(a + " PhoneNumber Share Problem");
						break;
					}
				}

			}
		}
		return flag;
	}

	/* Delete Deal */
	public boolean delDeal(int id) {
		boolean flag = false;
		if (this.jdbcTemplate.update(this.sql.delDeal(id)) != 0) {
			flag = true;
		}
		return flag;
	}

	/* Get User's Deal */
	public JSONObject syncDeal(int user_id, int pageIndex) {

		// 获取分页信息
		int pageSize = PAGE_SIZE;
		int startCount = pageIndex * pageSize;
		int endCount = (pageIndex + 1) * pageSize;
		/* 查看还剩下几个生意 */
		int flag = jdbcTemplate.queryForInt(this.sql.getSyncCount(user_id,
				startCount, endCount));
		List<?> list;
		JSONObject object = new JSONObject();
		if (flag != 0) {
			list = jdbcTemplate.queryForList(this.sql.syncDeal(user_id,
					startCount, endCount));
			object = listToJSON(list);
			for (int i = 1; i <= list.size(); i++) {
				try {
					JSONObject jsonObject = object.getJSONObject(i + "");
					System.out.println(jsonObject);
					JSONObject chatUserObject = getDealSharedUser(jsonObject
							.getString("id").toString());
					jsonObject.put("chatUsers", chatUserObject);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			Map<String, String> map = new HashMap<String, String>();
			map.put("message", "empty");
			object = new JSONObject(map);
		}
		return object;
	}

	/* Modify a Deal */
	public boolean updateDeal(DealBean dealBean) {
		boolean flag = false;
		return flag;
	}

	// 把从数据库查询得到的LIST转换为JSON
	public JSONObject listToJSON(List<?> list) {
		JSONObject object = new JSONObject();
		Iterator<?> it = list.iterator();
		int i = 1;
		while (it.hasNext()) {
			try {
				object.put(i + "", (Map<?, ?>) it.next());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				i++;
			}
		}
		return object;
	}

	// 转发消息
	public boolean forwardDeal(int dealId, int for_user_id,
			ArrayList<String> phoneNumber) {
		boolean flag = false;
		String sql = "insert into yjw_deal_intr(deal_id,user_id,phone_number,type) values(?,?,?,'FWD')";
		final ArrayList<String> list = phoneNumber;
		final int deal_id = dealId;
		final int user_id = for_user_id;

		int[] i = jdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						// TODO Auto-generated method stub
						ps.setInt(1, deal_id);
						ps.setInt(2, user_id);
						ps.setString(3, list.get(i));
					}

					public int getBatchSize() {
						// TODO Auto-generated method stub
						return list.size();
					}
				});
		flag = true;
		for (int a = 0; a < i.length; a++) {
			if (i[a] < 1) {
				flag = false;
				System.out.println(a + " PhoneNumber Share Problem");
				break;
			}
		}
		return flag;
	}

	// 获取生意分享到的用户列表
	public JSONObject getDealSharedUser(String dealId) {
		JSONObject object;
		List<?> list = jdbcTemplate.queryForList(sql.getSharedUser(dealId));
		if (list.size() != 0) {
			object = listToJSON(list);
		} else {
			Map<String, String> map = new HashMap<String, String>();
			map.put("message", "empty");
			object = new JSONObject(map);
		}
		return object;
	}
	

	//获取手机上没有客户端的用户
	@SuppressWarnings("unchecked")
	public String check(ArrayList<String> phoneNumber) {
		// TODO Auto-generated method stub
		String donthave ="" ;
		int flag=0;
		for(int i=0;i<phoneNumber.size();i++)
			if(!this.isDuplicate(phoneNumber.get(i)))
				if(flag==0){
					donthave=new String(phoneNumber.get(i));
					flag++;
					}
				else
					donthave=new String(donthave+","+phoneNumber.get(i));
		return donthave;
	}
	// 判断该手机号是否已经存在
	private boolean isDuplicate(String cellphone) {
		boolean flag = false;
		int i = jdbcTemplate.queryForInt(new prepareSQL()
				.cellPhoneDup(cellphone));
		if (i != 0) {
			flag = true;
		}
		return flag;
	}
	
	
	

}
