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

/*���ڽ��׵ľ���ʵ�ַ�װ*/
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

			// ����ɹ�,����������Ҫ����ĵ绰����
			if (phoneNumber.size() != 0) {

				// ��ȡ�ղŲ����Deal��ID
				final int deal_id = this.jdbcTemplate
						.queryForInt("Select MAX(id) from yjw_deal");
				// ������Ҫ����ĵ绰����
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

		// ��ȡ��ҳ��Ϣ
		int pageSize = PAGE_SIZE;
		int startCount = pageIndex * pageSize;
		int endCount = (pageIndex + 1) * pageSize;
		/* �鿴��ʣ�¼������� */
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

	// �Ѵ����ݿ��ѯ�õ���LISTת��ΪJSON
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

	// ת����Ϣ
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

	// ��ȡ����������û��б�
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
	

	//��ȡ�ֻ���û�пͻ��˵��û�
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
	// �жϸ��ֻ����Ƿ��Ѿ�����
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
