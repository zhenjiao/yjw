<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'addDeal.jsp' starting page</title>
    
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
         <form action="AddDealAction" method="post">
sid:<input type="text" name="sid"/>
title：<input type="text" name="title"/>
content:<input type="text" name="content"/>
fee：<input type="text" name="fee"/>
<!-- commission：<input type="text" name="commission"/></br>-->
phoneToShare：<input type="text" name="phoneToShare"/>
expire_date：<input type="text" name="expire_date"/>
reqConfirm：<select name="reqConfirm">
<option value="Yes">Yes</option>
<option value="No">No</option>

</select>
 <input type="submit">
  </form></body>
</html>
