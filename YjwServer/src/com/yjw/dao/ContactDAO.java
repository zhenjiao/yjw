package com.yjw.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yjw.bean.ContactBean;
import com.yjw.bean.ContactsBean;
import com.yjw.bean.GetInfoBean;
import com.yjw.bean.UserBean;
import com.yjw.sql.BaseSQL;
import com.yjw.sql.ContactSQL;
import com.yjw.tool.BeanPacker;

public class ContactDAO extends BaseDAO {

	public ContactDAO() {
		super(new ContactSQL());
	}

	@Override
	public Class<?> getBeanClass() {
		return ContactBean.class;
	}
	
	@Override
	public BeanPacker get(int id) {
		List<Map<String,Object>> list=jdbcTemplate.queryForList(sql.get(id));
		List<ContactBean> tl=new ArrayList<ContactBean>();
		for (Map<String,?> map:list){
			ContactBean bean = (ContactBean)new BeanPacker(map,ContactBean.class).getBean();
			tl.add(bean);
		}
		ContactBean[] beans=new ContactBean[tl.size()];
		tl.toArray(beans);
		ContactsBean ret=new ContactsBean();
		ret.setContacts(beans);
		return new BeanPacker(ret);
	}

	@Override
	public List<Integer> sync(GetInfoBean bean) {
		return null;
	}
	
	@Override
	public int add(BeanPacker packer) {
		super.add(packer);
		return addBack(packer);
	}
	
	public int addBack(BeanPacker packer){
		UserDAO userDao=new UserDAO();
		ContactBean bean=(ContactBean)packer.getBean();
		BeanPacker tarpacker=userDao.getByCellphone(bean.getCellphone());
		if (tarpacker==null) return -1;
		UserBean self=(UserBean)userDao.get(bean.getId()).getBean();
		UserBean target=(UserBean)tarpacker.getBean();
		ContactBean back=new ContactBean();
		back.setCellphone(self.getCellphone());
		back.setId(target.getId());
		return super.add(new BeanPacker(back));	
	}
	
	public int addAllBack(String cellphone){
		List<Map<String,Object>> list=jdbcTemplate.queryForList(((ContactSQL)sql).getByCellphone(cellphone));
		for (Map<String,?> map:list) addBack(new BeanPacker(map,ContactBean.class));
		return 0;
	}
}
