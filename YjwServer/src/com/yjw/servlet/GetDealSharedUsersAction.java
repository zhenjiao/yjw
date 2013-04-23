package com.yjw.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.yjw.impl.DealImpl;
import com.yjw.tool.GenerateTool;

public class GetDealSharedUsersAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GenerateTool generateTool;
	private DealImpl dealImpl;

	/**
	 * Constructor of the object.
	 */
	public GetDealSharedUsersAction() {
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
		PrintWriter out = response.getWriter();

		String sid = request.getParameter("sid");
		String dealId = request.getParameter("dealId");
		String user_id = "-1";
		try {
			user_id = "" + generateTool.getUserId(sid);
		} catch (Exception e) {
			// TODO: handle exception
			out.print("No the selected user!");
		}

		if (generateTool.isUserDeal(dealId, user_id)) {
			JSONObject object = this.dealImpl.getDealSharedUser(dealId);
			out.print(object);
		} else {
			if (user_id.equals("-1")) {
				out.print("No the selected user!");
			} else {
				out.print("This deal is not from selected user.");
			}
		}

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
		this.generateTool = new GenerateTool();
		this.dealImpl = new DealImpl();
	}

}
