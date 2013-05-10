package com.yjw.dao;

import java.util.List;

import com.yjw.bean.GetInfoBean;
import com.yjw.tool.BeanPacker;

public interface EntityDAO {
	public int add(BeanPacker packer);
	public BeanPacker get(int id);
	public boolean del(int id);
	public List<Integer> sync(GetInfoBean bean);
}
