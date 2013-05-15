package com.yjw.sql;

import com.yjw.bean.AccountBean;
import com.yjw.bean.UserBean;
import com.yjw.bean.ValidationBean;
import com.yjw.tool.BeanPacker;

public class RegisterSQL {
	
	/**根据UserBean里面的信息生成一条插入语句*/
	public String register(UserBean userBean) {
		if (userBean.getCellphone()==null||userBean.getPassword()==null) return null;
		return new BeanPacker(userBean).insert("yjw_user");
	}
	
	/**判断手机号码是否重复
	 * @param cellphone 要判断的电话号码
	 * @return <b>int</b> 有多少个该号码存在
	 * */
	public String cellPhoneDup(String cellphone) {
		return "select count(id) from yjw_user where cellphone='" + cellphone + "'";
	}
	
	public String validate(String sid){
		return "select * from yjw_validation where sid='" + sid + "'";
	}
	
	public String insertValidateCode(ValidationBean bean){
		return new BeanPacker(bean).insert("yjw_validation");
	}
	
	public String Logon(AccountBean bean){
		return "select * from yjw_user where"+
				" cellphone='"+bean.getCellphone()+"'"+" and "+
				" password='" +bean.getPassword() +"'";
	}

	public String cellNameDup(String name) {
		return "select count(id) from yjw_user where name='" + name + "'";
	}
}
