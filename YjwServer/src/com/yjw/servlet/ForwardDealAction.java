package com.yjw.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
import com.yjw.proxy.DealProxy;
import com.yjw.tool.GenerateTool;

public class ForwardDealAction extends HttpServlet {
	
	private DealProxy dealProxy;
	private GenerateTool generateTool;
	/**
	 * Constructor of the object.
	 */
	public ForwardDealAction() {
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
		PrintWriter out = response.getWriter();
		String sid = request.getParameter("sid");
		int user_id = new GenerateTool().getUserId(sid);
		
		int deal_id = Integer.parseInt(request.getParameter("dealid"));
		String phoneNumber = request.getParameter("phoneToForward");
		ArrayList<String> list = generateTool.getPhoneNumberList(phoneNumber);
		
		if(this.dealProxy.forwardDeal(deal_id, user_id, list)){
			out.print("success");
		}else{
			out.print("fail");
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
		this.generateTool = new GenerateTool();
		this.dealProxy = new DealProxy();
	}

}
