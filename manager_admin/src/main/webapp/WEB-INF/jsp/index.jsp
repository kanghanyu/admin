<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();///layer 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
System.out.print("path:"+path+"-----basePath:"+basePath);
%>
 
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>首页页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
	</head>
	<body>
	<script type="text/javascript">
		$(function (){
			//alert(111111)
		})
	</script>
		<h1>欢迎来到后台管理的首页</h1>
		<br> <a href="/login" >点击登录</a>
  </body>
</html>
