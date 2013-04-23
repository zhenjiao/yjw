package com.yjw.proxy;

import org.json.JSONObject;

import com.yjw.dao.InformationDAO;
import com.yjw.impl.GetForwardDealImpl;
import com.yjw.impl.GetPublishedDeal;
import com.yjw.impl.GetReceivedDeal;
import com.yjw.servlet.GetDealAction;

/*获取各种信息的代理类,可以扩展*/
public class InformationProxy implements InformationDAO{
	private InformationDAO informationDAO;
	public static final int _PUBLISHED_DEAL = 0X000010;
	public static final int _FORWORD_DEAL = 0X000020;
	public static final int _RECEIVED_DEAL = 0X000030;
	
	public InformationProxy(int key){
		switch (key) {
		case InformationProxy._FORWORD_DEAL:
			this.informationDAO = new GetForwardDealImpl();
			break;
		case InformationProxy._PUBLISHED_DEAL:
			this.informationDAO = new GetPublishedDeal();
			break;
		case InformationProxy._RECEIVED_DEAL:
			this.informationDAO = new GetReceivedDeal();
			break;
		default:
			break;
		}
	}
	public JSONObject getInformation(int user_id,int pageIndex){
		return informationDAO.getInformation(user_id,pageIndex);
	}
}
