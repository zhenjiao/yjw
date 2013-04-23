package com.yjw.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.yjw.bean.DealBean;
import com.yjw.bean.TestObject;
import com.yjw.bean.UnregisteredPhoneList;
import com.yjw.proxy.DealProxy;
import com.yjw.tool.GenerateTool;

public class AddDealAction extends HttpServlet {
	
	private DealProxy dealProxy;
	private GenerateTool generateTool;
	/**
	 * Constructor of the object.
	 */
	public AddDealAction() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		//PrintWriter out = response.getWriter();
		ServletOutputStream out = response.getOutputStream();
		String sid = request.getParameter("sid");
		
		// 获取需要分享的电话号码信息
		String phoneNumber = request.getParameter("phoneToShare");
		ArrayList<String> list = generateTool.getPhoneNumberList(phoneNumber);
		
		// 获取Deal的信息
		DealBean dealBean = new DealBean();
		dealBean.setTitle(request.getParameter("title"));
		dealBean.setContent(request.getParameter("content"));
		dealBean.setFee(Float.parseFloat(request.getParameter("fee")));
		//dealBean.setCommission(Float.parseFloat(request.getParameter("commission")));
		
		dealBean.setexpire_date(generateTool.StringToDate(request.getParameter("expire_date")));
		
		
		String reqConfirm = request.getParameter("reqConfirm");
		dealBean.setReq_confirm(reqConfirm);
		UnregisteredPhoneList object = new UnregisteredPhoneList();
		String temp=dealProxy.check(list);
		object.phoneList=temp;
		byte[] bytData = new byte[] {};
		System.out.println(dealBean.getTitle());
	
		try {
			int user_id = new GenerateTool().getUserId(sid);
			dealBean.setUser_id(user_id);

			if(dealProxy.addDeal(dealBean,list,reqConfirm)){
				System.out.print("success");
				
			}else{
				System.out.print("fail");
			}
			bytData = getBytesFromObject(object);
		} catch (Exception e) {
			// TODO: handle exception
			out.print("Problem on Database");
		}
		out.write(bytData);
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
		this.dealProxy = new DealProxy();
		this.generateTool = new GenerateTool();
	}
	
	public static byte[] getBytesFromObject(Serializable obj) throws Exception {
        if (obj == null) {
            return null;
        }
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(obj);
        return bo.toByteArray();
	}

}
