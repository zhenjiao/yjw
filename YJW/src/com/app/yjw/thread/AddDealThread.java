package com.app.yjw.thread;

import com.app.yjw.net.NetworkConstants;
import com.app.yjw.util.YJWMessage;

public class AddDealThread extends YJWBaseThread {

	@Override
	protected String generateURL() {
		return NetworkConstants.URL_ADDDEAL;
	}

	@Override
	protected void init() {
				
	}
	
	@Override
	protected void OnSuccess() {
		msg.what=YJWMessage.ADD_DEAL_SUCCESS.ordinal();
		super.OnSuccess();
	}

/*	private DealInfo deal;
	private String[] phones;
	private boolean shouldConfirm;

	public AddDealThread(int Id, String title, String content,
			String refer, String commission, Date date, String details,
			String[] _phones, boolean sc) {
		deal = new DealInfo();
		deal.setTitle(title);
		deal.setDate(date);
		deal.setReferFee(refer);
		deal.setCommissionFee(commission);
	//	deal.setCreator(sid);
		deal.setDetails(details);
		phones = _phones;
		shouldConfirm = sc;
	}

	@Override
	public void run() {
		String phoneStr = ((UnregisteredPhoneList) NetworkFactory.getInstance()
				.doPostObject(generateURL(), generateParameters(),true)).phoneList;
		msg = Message.obtain();
		Log.d("Unregistered Phone", phoneStr);
		if (phoneStr.equals(""))
		{
			msg.what = 0;
		}
		else {
			msg.what = 1;
			msg.obj = phoneStr;
		}
		this.sendMessage();

	}

	@Override
	protected List<BasicNameValuePair> generateParameters() {
		List<BasicNameValuePair> parameters = new LinkedList<BasicNameValuePair>();
		parameters.add(new BasicNameValuePair("sid", deal.getCreator()));
		parameters.add(new BasicNameValuePair("title", deal.getTitle()));
		parameters.add(new BasicNameValuePair("content", ""));
		parameters.add(new BasicNameValuePair("fee", deal.getReferFee()));
		parameters.add(new BasicNameValuePair("commission", deal
				.getCommissionFee()));
		String dateString = deal.getDate().getYear() + "-"
				+ deal.getDate().getMonth() + "-" + deal.getDate().getDay();
		parameters.add(new BasicNameValuePair("expire_date", dateString));
		String phone_str = "";
		for (int i = 0; i < phones.length; ++i)
			phone_str = phone_str + phones[i] + ",";
		phone_str = phone_str.substring(0, phone_str.length() - 1);
		phone_str.replace(" ", "");
		phone_str.replace("-", "");
		parameters.add(new BasicNameValuePair("phoneToShare", phone_str));
		parameters.add(new BasicNameValuePair("reqConfirm",
				shouldConfirm ? "Yes" : "No"));
		return parameters;
	}

	@Override
	protected String generateURL() {
		return NetworkConstants.URL_ADDDEAL;
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
	}*/
}
