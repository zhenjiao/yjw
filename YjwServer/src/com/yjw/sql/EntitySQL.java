package com.yjw.sql;

import com.yjw.bean.GetInfoBean;
import com.yjw.tool.BeanPacker;

public interface EntitySQL {
	public final int PAGE_SIZE = 20;
	public String add(BeanPacker packer);
	public String get(int id);
	public String del(int id);
	public String sync(GetInfoBean bean);
}
