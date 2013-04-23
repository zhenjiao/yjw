<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
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
   <a href="jsp/register.jsp">Register</a><br>
     <a href="jsp/registerValidate.jsp">RegisterValidate</a><br>
      <a href="jsp/reSendValidateMsg.jsp">ReSendValidateMsg</a><br>
    <a href="jsp/finishRegister.jsp">FinishRegister</a><br>
   <a href="jsp/login.jsp">Login</a><br>
   <a href="jsp/addDeal.jsp">AddDeal</a><br>
   <a href="jsp/delDeal.jsp">DelDeal</a><br>
   <a href="jsp/synvDeal.jsp">SyncDeal</a><br>
    <a href="jsp/getReceivedDeal.jsp">getReceivedDeal</a><br>
   
   <a href="jsp/forwardDeal.jsp">forwardDeal</a><br>
    <a href="jsp/setChatMsg.jsp">setChatMsg</a><br>
      <a href="jsp/getMsg.jsp">getMsg</a><br>
        <a href="jsp/getSharedUsers.jsp">getSharedUsers</a><br>
         <a href="jsp/declineDeal.jsp">Decline Deal</a><br>
          <a href="jsp/acceptDeal.jsp">Accept Deal</a><br>
          	<a href="jsp/Version.jsp">Version</a><br>
          		<a href="jsp/getBalance.jsp">getBalance</a><br>
  </body>
</html>
