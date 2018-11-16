<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- Left side column. contains the logo and sidebar -->
<aside class="main-sidebar">
	<!-- sidebar: style can be found in sidebar.less -->
	<section class="sidebar">
		<!-- Sidebar user panel -->
		<div class="user-panel">
			<div class="pull-left image">
				<img src="/webjars/adminlte/2.3.2/dist/img/user2-160x160.jpg"
					class="img-circle" alt="User Image">
			</div>
			<div class="pull-left info">
				<p>管理员:${loginUser.phone}</p>
				<a href="#"><i class="fa fa-circle text-success"></i> Online</a>
			</div>
		</div>
		
		<ul class="sidebar-menu">
			<li class="header">MAIN NAVIGATION</li>
			<li class="treeview">
				<a href="#"> 
						<span> 菜单管理</span> <i class="fa fa-angle-left pull-right"></i>
				</a>
				<ul class="treeview-menu">
					<li><a href="${pageContext.request.contextPath }/user/toUserList?pageSize=10&pageNum=1" target="mainFrame"><i class="fa fa-circle-o"></i> 用户管理</a></li>
				</ul>
				<ul class="treeview-menu">
					<li><a href="${pageContext.request.contextPath }/product/toProductList?pageSize=10&pageNum=1" target="mainFrame"><i class="fa fa-circle-o"></i> 商品管理</a></li>
				</ul>
				<ul class="treeview-menu">
					<li><a href="${pageContext.request.contextPath }/order/toOrderList?pageSize=10&pageNum=1" target="mainFrame"><i class="fa fa-circle-o"></i>订单管理</a></li>
				</ul>
				<ul class="treeview-menu">
					<li><a href="${pageContext.request.contextPath }/user/toUserCash?pageSize=10&pageNum=1" target="mainFrame"><i class="fa fa-circle-o"></i>用户提现</a></li>
				</ul>
				<ul class="treeview-menu">
					<li><a href="${pageContext.request.contextPath }/bill/toBill?pageSize=10&pageNum=1" target="mainFrame"><i class="fa fa-circle-o"></i>账单管理</a></li>
				</ul>
				<ul class="treeview-menu">
					<li><a href="${pageContext.request.contextPath }/online/toOnline?pageSize=10&pageNum=1" target="mainFrame"><i class="fa fa-circle-o"></i>在线参数管理</a></li>
				</ul>
				<ul class="treeview-menu">
					<li><a href="${pageContext.request.contextPath }/message/toMessageList?pageSize=10&pageNum=1" target="mainFrame"><i class="fa fa-circle-o"></i>公告管理</a></li>
				</ul>
			</li>
		</ul>
	</section>
	<!-- /.sidebar -->
</aside>
