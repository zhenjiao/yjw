package com.yjw.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yjw.bean.ChatBean;
import com.yjw.dao.ChatDAO;
import com.yjw.impl.ChatImpl;
import com.yjw.tool.BeanPacker;
import com.yjw.tool.ErrorCode;

public class ChatAction extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 676954535058785782L;
	//private GenerateTool generateTool;
	private ChatDAO chatDao;

	/**
	 * Constructor of the object.
	 */
	public ChatAction() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	@Override
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
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		String flag = request.getParameter("flag");

		if (flag.equals("setMsg")) {
			//chatBean.setDeal_id(deal_id);
			//chatBean.setContent(content);
			//chatBean.setTo_phone(to_user);
			//chatBean.setFrom_phone(chatDao.getCellphoneById(id));
			ChatBean bean = (ChatBean)new BeanPacker(request.getParameter("bean")).getBean();
			if (chatDao.setChat(bean)) {
				out.print(ErrorCode.E_SUCCESS);
			} else {
				out.print(ErrorCode.E_SET_CHAT_FAILED);				
			}

		} else if (flag.equals("getMsg")) {
			String id = request.getParameter("id");
			HashMap<String, Object> map;
			map = chatDao.getUnreadMsg(id);
			if (map!=null){
				ArrayList<Integer> list=(ArrayList<Integer>)map.get("list");
				if (list.size()>0){
					this.chatDao.setIsRead(list);
					out.print(ErrorCode.E_SUCCESS);
					out.print(map.get("object"));
				}else{
					out.print(ErrorCode.E_NULL_MSG);
				}
			}else{
				out.print(ErrorCode.E_NULL_MSG);
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
	@Override
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
	@Override
	public void init() throws ServletException {
		//this.generateTool = new GenerateTool();
		this.chatDao = new ChatImpl();
	}

}
