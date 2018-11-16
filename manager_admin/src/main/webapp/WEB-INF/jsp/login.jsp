<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();///layer 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
System.out.print("path:"+path+"-----basePath:"+basePath);
%>

<html>
<head>
<base href="<%=basePath%>">
<title>登录页面</title>
<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
</head>
<script type="text/javascript">
	var network = 
		$(function (){
			$("#error").attr("hide",true);
		})
			
	function submit() {
		var username = $("#username").val();
		var password = $("#password").val();
		if (username == null || username == '') {
			error("username不能为空")
			return;
		}
		if (password == null || password == '') {
			error("password不能为空")
			return;
		}

		var data = {
				"userName":username,"password":password
		}
		 
	$.ajax({
			type : "post",
			data : JSON.stringify(data),
			url : "/user/login",
			dataType : "json",
			contentType : 'application/json',
			success : function(data) {
				alert(data.msg);
			}
		});
	}

	function error(mes) {
		var error = $("#error");
		error.text(mes);
		error.attr("hide", false)
	}
</script>
<body>
	<div>
		<div>
			<h1 class="logo-name">U P</h1>
		</div>
		<h3 style="color: #f0f0f0">欢迎来到小康admin</h3>
		<p style="color: #f0f0f0">小康康测试内容编写</p>
		<div class="form-group">
			用户名:<input type="text" name="username" id="username" placeholder="用户名"
				maxlength="10" />
		</div>
		<div class="form-group">
			密码:<input type="password" name="password" id="password" maxlength="12">
		</div>
		<input type="button" onclick="submit()" value="登录"> 
		<div id="error" style="color: red;">
		
		</div>
	</div>
</body>
</html>

