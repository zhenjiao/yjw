package com.yjw.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.yjw.proxy.DealProxy;
import com.yjw.proxy.InformationProxy;
import com.yjw.tool.GenerateTool;

public class GetDealAction extends HttpServlet {
	
	private InformationProxy proxy;
	private DealProxy dealProxy;
	/**
	 * Constructor of the object.
	 */
	public GetDealAction() {
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
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String sid = request.getParameter("sid");
		int user_id = new GenerateTool().getUserId(sid);
		
		String dealType = request.getParameter("dealType");
		int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
		
		if(dealType.equals("REC")){
			proxy = new InformationProxy(InformationProxy._RECEIVED_DEAL);
			JSONObject object = proxy.getInformation(user_id, pageIndex);
			out.print(object);
		}else if(dealType.equals("PUB")){
			proxy = new InformationProxy(InformationProxy._PUBLISHED_DEAL);
			JSONObject object = proxy.getInformation(user_id, pageIndex);
			//JSONObject object = dealProxy.syncDeal(user_id, pageIndex);
			out.print(object);
			
		}else if(dealType.equals("FRW")){
			proxy = new InformationProxy(InformationProxy._FORWORD_DEAL);
			JSONObject object = proxy.getInformation(user_id, pageIndex);
			out.print(object);
			
		}
		
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
		this.dealProxy = new DealProxy();
	}

}
