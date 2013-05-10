package com.yjw.dao;

import com.yjw.tool.BeanPacker;

public interface UserDAO extends EntityDAO {

	BeanPacker getByCellphone(String cellphone);

}
