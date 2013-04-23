package com.yjw.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.core.JdbcTemplate;

import com.yjw.bean.UserBean;
import com.yjw.proxy.RegisterProxy;
import com.yjw.tool.GenerateTool;
import com.yjw.tool.GetJdbcTemplate;

public class RegisterAction extends HttpServlet {
	private RegisterProxy proxy;
	private JdbcTemplate jdbcTemplate;
	private GenerateTool gnerateTool;
	/**
	 * Constructor of the object.
	 */
	public RegisterAction() {
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
		String msg = "";

		// ��ȡ��Ҫ����Ϣ
		String cellphone = request.getParameter("cellphone");
		if (this.proxy.isDuplicate(cellphone)) {
			msg = "�Ѿ�ע����ʺ�";
		} else {
			UserBean userBean = new UserBean();
			String sid = this.gnerateTool.generateSid();
			
			//��ע����Ϣ���뵽Bean��
			userBean.setCellphone(cellphone);
			userBean.setSid(sid);

			
			if(proxy.register(userBean)){
				//������֤��Ϣ
				String validateString = this.gnerateTool.genValidateString();
				if(this.proxy.insertValidateCode(sid, validateString)){
					//msg = sid;
					msg = sid+","+validateString;	
				}else{
					msg = "��ȡ��֤��Ϣʧ��";
				}
				
			}else{
				msg = "ע��ʧ��";
			}
		}
		out.print(msg);
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
		this.jdbcTemplate = new GetJdbcTemplate().getJtl();
		this.proxy = new RegisterProxy(jdbcTemplate);
		this.gnerateTool = new GenerateTool();
	}

}
