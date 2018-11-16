<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<header class="main-header">
	<!-- Logo -->
	<a href="" class="logo"> <!-- mini logo for sidebar mini 50x50 pixels -->
		<span class="logo-mini"><b>P</b></span> <!-- logo for regular state and mobile devices -->
		<span class="logo-lg"><b>后台管理中心</b></span>
	</a>
	<!-- Header Navbar: style can be found in header.less -->
	<nav class="navbar navbar-static-top" role="navigation">
		<!-- Sidebar toggle button-->
		<a href="#" class="sidebar-toggle" data-toggle="offcanvas"
			role="button"> <span class="sr-only">Toggle navigation</span> <span
			class="icon-bar"></span> <span class="icon-bar"></span> <span
			class="icon-bar"></span>
		</a>
		<div class="navbar-custom-menu">
			<ul class="nav navbar-nav">
				<!-- User Account: style can be found in dropdown.less -->
				<li class="dropdown user user-menu"><a href="#"
					class="dropdown-toggle" data-toggle="dropdown"> <img
						src="/webjars/adminlte/2.3.2/dist/img/user2-160x160.jpg" class="user-image"
						alt="User Image"> <span class="hidden-xs">${loginUser.phone}</span>
				</a>
					<ul class="dropdown-menu">
						<!-- User image -->
						<li class="user-header"><img
							src="/webjars/adminlte/2.3.2/dist/img/user2-160x160.jpg" class="img-circle"
							alt="User Image">
							<p>
								${loginUser.phone}
								<small>管理员</small>
							</p></li>
						<!-- Menu Body -->
						<li class="user-body">
						<!-- 	<div class="col-xs-4 text-center">
								<a href="#">Followers</a>
							</div>
							<div class="col-xs-4 text-center">
								<a href="#">Sales</a>
							</div>
							<div class="col-xs-4 text-center">
								<a href="#">Friends</a>
							</div> -->
						</li>
						<!-- Menu Footer-->
						<li class="user-footer">
							<div class="pull-right">
								<a href="${pageContext.request.contextPath }/user/loginOut" class="btn btn-default btn-flat">退出登录</a>
							</div>
						</li>
					</ul></li>
			</ul>
		</div>
	</nav>
</header>
