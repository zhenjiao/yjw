package com.yjw.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.yjw.bean.ChatBean;
import com.yjw.proxy.ChatProxy;
import com.yjw.tool.GenerateTool;

public class ChatAction extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 676954535058785782L;
	private GenerateTool generateTool;
	private ChatProxy chatProxy;

	/**
	 * Constructor of the object.
	 */
	public ChatAction() {
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
		String flag = request.getParameter("flag");

		if (flag.equals("setMsg")) {
			String to_user = request.getParameter("to_user");
			String content = request.getParameter("content");
			int deal_id = Integer.parseInt(request.getParameter("deal_id"));
			/* 初始化DTO */
			ChatBean chatBean = new ChatBean();
			chatBean.setDeal_id(deal_id);
			chatBean.setContent(content);
			chatBean.setTo_phone(to_user);
			chatBean.setFrom_phone(this.generateTool.getPhoneNumber(sid));

			if (this.chatProxy.setChat(chatBean)) {
				out.print("success");
			} else {
				out.print("fail");
			}

		} else if (flag.equals("getMsg")) {
			String user_id = "" + this.generateTool.getUserId(sid);
			String phone  = this.generateTool.getPhoneNumber(sid);
			// 获取的未读项目存在JSON里
			JSONObject object = new JSONObject();
			
			int size = this.chatProxy.getUnderReadMsgSize(phone);
			HashMap<String, Object> map = new HashMap<String, Object>();
			if (size > 0) {
				try {
					map = this.chatProxy.getUnderReadMsg(phone);
					// 把消息置为已读
					this.chatProxy.setIsRead((ArrayList<Integer>) map
							.get("list"));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				} finally {
					out.print((JSONObject) map.get("object"));
				}
				// chatProxy.setIsRead();
			} else {
				out.print("Null");
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
		this.chatProxy = new ChatProxy();
	}

}
