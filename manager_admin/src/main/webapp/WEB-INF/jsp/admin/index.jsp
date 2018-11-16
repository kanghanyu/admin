<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>项目中心</title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<jsp:include page="common.jsp"></jsp:include>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<jsp:include page="header.jsp" />
		<jsp:include page="left.jsp" />
		<div class="content-wrapper">
			<iframe name="mainFrame" id="mainFrame" frameborder="0" src="user/toUserList?pageSize=10&pageNum=1"
				style="margin: 0 auto; width: 100%; height: 100%;" ></iframe>
		</div>
	</div>
	<script type="text/javascript">
		function cmainFrame() {
			var hmain = document.getElementById("mainFrame");
			var bheight = document.documentElement.clientHeight;
			hmain.style.width = '100%';
			hmain.style.height = (bheight - 60) + 'px';

		}
		cmainFrame();
		window.onresize = function() {
			cmainFrame();
		}
	</script>
</body>

</html>
