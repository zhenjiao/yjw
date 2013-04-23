<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'sendMessage.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
   <form action="http://service2.winic.org/Service.asmx/SendMessages" method="post">
   			uid:
			<input type="text" name="uid" />
			<br>
			pwd：
			<input type="text" name="pwd" />
			<br>
			tos：
			<input type="text" name="tos" />
			<br>
			msg：
			<input type="text" name="msg" />
			<br>
			otime：
			<input type="text" name="otime" />
			<br>
			<input type="submit">
   </form>
  </body>
</html>
