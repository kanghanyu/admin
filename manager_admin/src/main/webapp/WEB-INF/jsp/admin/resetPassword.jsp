<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%
String path = request.getContextPath();///layer 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>注册</title>
<jsp:include page="common.jsp"></jsp:include>
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<jsp:include page="common.jsp" />
</head>
<body class="hold-transition login-page">
	<div class="login-box">
		<div class="login-logo">
			<b>修改密码</b>
		</div>
		<!-- /.login-logo -->
		<div class="login-box-body">
			<p class="login-box-msg"></p>
			<form action="/user/resetPassword" method="post" onsubmit="return check()">
				<div class="form-group has-feedback">
					<label>手机号:</label><input type="text" class="form-control" id="phone" name="phone" placeholder="手机号" maxlength="11">
				</div>
				<div class="form-group has-feedback">
					<label>密码:</label><input type="password" class="form-control" id="password" name="password" placeholder="密码" maxlength="12">
				</div>
				<div class="form-group has-feedback">
					<label>确认密码</label><input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="确认密码" maxlength="12">
				</div>
				<div class="form-group has-feedback">
					<!-- <label>短信验证码:</label><input type="password" class="form-control" id="code" name="code" placeholder="验证码" maxlength="4" style="width: 100px">
					-->
					<!-- <img onclick="geNewCode()" class="vc-pic" width="80" height="37" title="点击刷新验证码" src="/user/validate/img?time=new Date().getTime()"> -->
					<input type="text" id="code" name="code" class="form-control flat" placeholder="手机验证码" style="width:160px;display:inline;" maxlength="6"> 
					<input type="button" value="获取短信验证码" id="msgButton" style="color: red" > 
				</div>
				<div class="row">
					<div class="col-xs-4">
						<a href="/login"/>去登录
					</div>
					<div class="col-xs-4">
						<button type="submit" class="btn btn-primary btn-block btn-flat">修改密码</button>
					</div>
				</div>
				<input type="hidden" value="${msg}" id="msg">
			</form>
		</div>
	</div>
	<!-- /.login-box -->
	<script>
	$(function(){
		var msg = $("#msg").val();
		if( msg != null && msg !=""){
			alert(msg);
		}
		
		$("#msgButton").click(function(){
			var phone = $("#phone").val()+"";
			var myreg = /^(((13[0-9]{1})|(14[0-9]{1})|(17[0]{1})|(15[0-3]{1})|(15[5-9]{1})|(18[0-9]{1}))+\d{8})$/;             
			if(phone == null || phone==''){
				alert("手机号不能为空")
				return false;
			}else if(!myreg.test(phone)){
				alert("手机号不合法")
				return false;
			}
			var data = {
					"phone":phone,"type":2
			}
			$.ajax({
				type : "post",
				data : JSON.stringify(data),
				url : "/user/getMessageCode",
				dataType : "json",
				contentType : 'application/json',
				success : function(data) {
					if(!(null != data && data.code == 1000)){
						alert(data.msg);
						var code = $('#msgButton');
						code.attr("disabled", true);
						code.attr("style","");
						var time = 60;
						var set=setInterval(function(){	    
						  code.val("("+--time+")秒后重新获取");	    
						}, 1000);
						  
					  setTimeout(function(){	    
						  code.attr("disabled",false).val("重新获取验证码");	
						  code.css("color","red")
						  clearInterval(set);	    
						  }, 60000);
						
					}else{
						alert(data.msg);
					}
				}
			});
			
			
			
			
		})
		
	})
		function check(){
			var phone = $("#phone").val()+"";
			var password = $("#password").val()+"";
			var confirmPassword = $("#confirmPassword").val();
			var code = $("#code").val();
			if(phone == null || phone==''){
				alert("手机号不能为空")
				return false;
			}
			if (password == null || password == '') {
				alert("密码不能为空")
				return false;
			}
			if (confirmPassword == null || confirmPassword == '') {
				alert("确认密码不能为空")
				return false;
			}
			if (confirmPassword != password) {
				alert("两次密码不一致")
				return false;
			}
			if (code == null || code == '') {
				alert("验证码不能为空")
				return false;
			}
		}
		
		function geNewCode(){
			var _url = "/user/validate/img?time="+new Date().getTime();
			$(".vc-pic").attr('src',_url);
		}
		
		
	</script>
</body>
</html>
