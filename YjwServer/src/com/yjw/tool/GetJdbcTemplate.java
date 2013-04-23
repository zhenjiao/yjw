package com.yjw.tool;

import org.springframework.beans.factory.BeanFactory;
//import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.*;
import org.springframework.context.support.*;
//import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

public class GetJdbcTemplate {

	private JdbcTemplate jtl;
	
	public JdbcTemplate getJtl() {
		
//		ClassPathResource resource = new ClassPathResource("applicationContext.xml");
//		BeanFactory factory = new XmlBeanFactory(resource);
//		this.jtl = (JdbcTemplate) factory.getBean("jdbcTemplate");
		
		ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
		this.jtl = (JdbcTemplate) application.getBean("jdbcTemplate");
		return this.jtl;
	}

	public void setJtl(JdbcTemplate jtl) {
		this.jtl = jtl;
	}

}
