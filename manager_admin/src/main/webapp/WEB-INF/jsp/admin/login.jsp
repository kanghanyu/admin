<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>登录</title>
<jsp:include page="common.jsp"></jsp:include>
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
</head>
<script>
	$(function(){
		var msg = $("#msg").val();
		if( msg != null && msg !=""){
			alert(msg);
		}
		
		//$(".login-box-body").hidden();
	})
		function check(){
			var phone = $("#phone").val();
			var password = $("#password").val();
			var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;     
			if(phone == null || phone==''){
				alert("手机号不能为空")
				return false;
			}else if(!myreg.test(phone)){
				alert("手机号不合法")
				return false;
			}
			if (password == null || password == '') {
				alert("password不能为空")
				return false;
			}
			return true;
		}
	</script>
	
<body class="hold-transition login-page">
	<div class="login-box" >
		<div class="login-logo">
			<b>登陆</b>
		</div>
		<!-- /.login-logo -->
		<div class="login-box-body">
			<p class="login-box-msg">Sign in to start your session</p>
			<form action="/user/login" method="post" onsubmit="return check()">
				<div class="form-group has-feedback">
					<input type="text" class="form-control" id="phone" name="phone" placeholder="手机号" maxlength=""11>
					<span class="glyphicon glyphicon-envelope form-control-feedback"></span>
				</div>
				<div class="form-group has-feedback">
					<input type="password" class="form-control" id="password" name="password" placeholder="密码" maxlength="12">
					<span class="glyphicon glyphicon-lock form-control-feedback"></span>
				</div>
				<div class="row">
					<div class="col-xs-4">
						<a href="/register"/>点击注册
					</div>
					<div class="col-xs-4">
						<a href="/resetPassword"/>忘记密码
					</div>
					<div class="col-xs-4">
						<button type="submit" class="btn btn-primary btn-block btn-flat">登录</button>
					</div>
					<!-- /.col -->
				</div>
				<input type="hidden" value="${msg}" id="msg">
			</form>
		</div>
	</div>
</body>
</html>
