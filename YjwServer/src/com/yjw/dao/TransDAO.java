package com.yjw.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.yjw.bean.DealBean;
import com.yjw.bean.TransBean;
import com.yjw.bean.UserBean;
import com.yjw.sql.TransSQL;
import com.yjw.tool.BeanPacker;
import com.yjw.tool.TemplateGetter;

public class TransDAO extends BaseDAO {
	
	public TransDAO() {
		super(new TransSQL());
	}

	@Override
	public Class<?> getBeanClass() {
		return TransBean.class;
	}
	
	public int confirm(int id){
		final int ID=id;
		try{
			TransactionTemplate tt=TemplateGetter.getttl();
			return (Integer)tt.execute(new TransactionCallback() {
				public Object doInTransaction(TransactionStatus ts) {
					JdbcTemplate jt=TemplateGetter.getJtl();
					BaseDAO dealDao=new DealDAO();
					UserDAO userDao=new UserDAO();
					jt.update(((TransSQL)sql).confirm(ID));
					TransBean tb=(TransBean)get(ID).getBean();
					DealBean db=(DealBean)dealDao.get(tb.getDeal_id()).getBean();
					UserBean fromb=(UserBean)userDao.get(db.getOwner_id()).getBean();
					UserBean tob=(UserBean)userDao.get(tb.getFrom_id()).getBean();
					if (fromb.getBalance()<db.getFee()) {
						ts.setRollbackOnly(); 
						return 1;
					}
					fromb.setBalance(fromb.getBalance()-db.getFee());
					tob.setBalance(tob.getBalance()+db.getFee());
					jt.update(new BeanPacker(fromb).update("yjw_user"));
					jt.update(new BeanPacker(tob).update("yjw_user"));
					return 0;			
				}
			}); 
		}catch(Exception e){
			return -1;
		}
	}
}
