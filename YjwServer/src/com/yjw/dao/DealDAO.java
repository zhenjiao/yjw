package com.yjw.dao;

import com.yjw.bean.DealBean;
import com.yjw.sql.DealSQL;

/*关于交易的具体实现封装*/
public class DealDAO extends BaseDAO{

	public DealDAO() {
		super(new DealSQL());
	}

	@Override
	public Class<?> getBeanClass() {
		return DealBean.class;
	}

	
/*	public boolean addDeal(BeanPacker packer) {
		try{
			if (jdbcTemplate.update(sql.addDeal(packer))!=-1) return true;
			return false;
		}catch(Exception e){
			return false;
		}
		
	}
	
	public boolean delDeal(int id) {
		boolean flag = false;
		if (this.jdbcTemplate.update(this.sql.delDeal(id)) != 0) {
			flag = true;
		}
		return flag;
	}

	
	public List<Integer> syncDeal(GetInfoBean bean) {

		// 获取分页信息
		int pageSize = PAGE_SIZE;
		int startCount = bean.getPage() * pageSize;
		List<Map<String,?>> list=jdbcTemplate.queryForList(sql.syncDeal(bean.getId(),startCount,PAGE_SIZE));
		List<Integer> ret=new ArrayList<Integer>();
		for (Map<String,?> map:list){
			ret.add((Integer)map.get("id"));
		}
		return ret;
	}
	
	public BeanPacker getDeal(int id) {
		try{
		Map<String,?> map = jdbcTemplate.queryForMap(sql.getDeal(id));
		return new BeanPacker(map,DealBean.class);
		}catch(Exception e){
			return null;
		}
	}*/
	/*	boolean flag = false;
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
	}*/



	/* Modify a Deal */
/*	public boolean updateDeal(DealBean dealBean) {
		boolean flag = false;
		return flag;
	}*/

	// 转发消息
/*	public boolean forwardDeal(int dealId, int for_user_id,
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
		int i = jdbcTemplate.queryForInt(new RegisterSQL()
				.cellPhoneDup(cellphone));
		if (i != 0) {
			flag = true;
		}
		return flag;
	}*/

	
}
