package com.yjw.dao;

import com.yjw.bean.Bean;
import com.yjw.bean.DealBean;
import com.yjw.sql.DealSQL;

/*���ڽ��׵ľ���ʵ�ַ�װ*/
public class DealDAO extends BaseDAO{

	public DealDAO() {
		super(new DealSQL());
	}

	@Override
	public Class<? extends Bean> getBeanClass() {
		return DealBean.class;
	}	
}
