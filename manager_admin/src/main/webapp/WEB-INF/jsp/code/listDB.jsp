<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../admin/common.jsp" />
</head>
<body>
	<!-- Content Wrapper. Contains page content -->
	<div>
		<!-- Content Header (Page header) -->
		<section class="content-header">
			<h1>
				数据源管理<small>数据源列表</small>
			</h1>
			<ol class="breadcrumb">
				<li><a href="#"><i class="fa fa-dashboard"></i> 数据源管理</a></li>
				<li><a href="#">数据源列表</a></li>
			</ol>
		</section>

		<!-- Main content -->
		<section class="content">
			<div class="row">
				<div class="col-xs-12">
					<div class="box">
						<div class="box-body">
							<div class="row">
								<div class="col-xs-3">
									<form
										action="${pageContext.request.contextPath }/db/searchUser"
										method="get" class="sidebar-form">

										<div class="input-group">
											<input name="userName" value="" class="form-control"
												placeholder="Search..." type="text"> <span
												class="input-group-btn">
												<button type="submit" name="search" id="search-btn"
													class="btn btn-flat">
													<i class="fa fa-search"></i>
												</button>
												<button type="button" id="add" onclick="addDBPop()"
													class="btn btn-primary">新增数据源</button>
											</span>
										</div>
									</form>
								</div>

							</div>

						</div>
						<!-- /.box-header -->
						<div class="box-body">
							<table id="table1"
								class="table table-bordered  table-striped table-hover">
								<thead>
									<tr>
										<th>ID</th>
										<th>Url</th>
										<th>Username</th>
										<th>Driver</th>
										<th>Operation</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="datasource" items="${datasources }">
										<tr>
											<td>${datasource.id }</td>
											<td>${datasource.jdbcUrl }</td>
											<td>${datasource.username }</td>
											<td>${datasource.driver }</td>
											<td>
												<button class="btn btn-primary btn-sm"
													onclick="editDB('${datasource.id }')">
													<i class="glyphicon glyphicon-edit">编辑</i>
												</button>
												<button class="btn btn-danger btn-sm"
													onclick="delDB('${datasource.id }')" type="button">
													<i class="fa fa-trash-o">删除</i>
												</button>
												<button class="btn btn-info btn-sm"
													onclick='genneratorModalPop("${datasource.id }")'>生成Dubbo项目</button>
												<button class="btn btn-warning btn-sm"
													onclick='genneratorWebModalPop("${datasource.id }")'>生成web项目</button>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<!-- /.box-body -->
					</div>
					<!-- /.box -->
				</div>
			</div>
		</section>
		<!-- /.content -->
	</div>

	<div class='modal' id='genneratorModalPop'>
		<div class='modal-dialog'>
			<div class='modal-content'>
				<div class='modal-header'>
					<button type='button' class='close' data-dismiss='modal'>
						<span aria-hidden='true'>×</span><span class='sr-only'>Close</span>
					</button>
					<h4 class='modal-title'>生成项目</h4>
				</div>
				<div class='modal-body'>
					<form action="${pageContext.request.contextPath }/db/gennerator"
						method="post" role='form'>
						<input id="genDubboId" type="hidden" name="id">
						<div class='form-group'>
							<label for='project'>项目名称：</label> <input type='text'
								class='form-control' name="project" id='project' value=''
								placeholder='example: boss-pic'>
						</div>
						<div class='form-group'>
							<label for='packagePath'>包路径:</label> <input type='text'
								class='form-control' name="packagePath" value=''
								id='packagePath' placeholder='example: com.vcg'>
						</div>
						<div class='form-group'>
							<label for='packagePath'>表名:</label> <input type='text'
								class='form-control' name="tbls" value='' id='packagePath'
								placeholder='table1,table2,table3.不填写默认是所有'>
						</div>
						<div class='modal-footer'>
							<button type='button' class='btn btn-default'
								data-dismiss='modal'>关闭</button>
							<button type="submit" id='execute' class='btn btn-primary'>生成</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>


	<div class='modal' id='genneratorWebModalPop'>
		<div class='modal-dialog'>
			<div class='modal-content'>
				<div class='modal-header'>
					<button type='button' class='close' data-dismiss='modal'>
						<span aria-hidden='true'>×</span><span class='sr-only'>Close</span>
					</button>
					<h4 class='modal-title'>生成项目</h4>
				</div>
				<div class='modal-body'>
					<form action="${pageContext.request.contextPath }/db/genneratorWeb"
						method="post" role='form'>
						<input id="genWebId" type="hidden" name="id">
						<div class='form-group'>
							<label for='project'>项目名称：</label> <input type='text'
								class='form-control' name="project" id='project' value=''
								placeholder='example: boss-pic'>
						</div>
						<div class='form-group'>
							<label for='packagePath'>包路径:</label> <input type='text'
								class='form-control' name="packagePath" value=''
								id='packagePath' placeholder='example: com.vcg'>
						</div>
						<div class='form-group'>
							<label for='packagePath'>表名:</label> <input type='text'
								class='form-control' name="tbls" value='' id='packagePath'
								placeholder='table1,table2,table3.不填写默认是所有'>
						</div>
						<div class="form-group">
							<label for="exportType">容器类型:</label>
							<div class="form-group">
								<label class="radio-inline"> <input type="radio"
									name="containerType" checked="checked" value="1">内置容器
								</label> <label class="radio-inline"> <input type="radio"
									name="containerType" value="2">外置容器
								</label>
							</div>
						</div>
						<div class='modal-footer'>
							<button type='button' class='btn btn-default'
								data-dismiss='modal'>关闭</button>
							<button type="submit" id='execute' class='btn btn-primary'>生成</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<div class='modal' id='editDBModal'>
		<div class='modal-dialog'>
			<div class='modal-content'>
				<div class='modal-header'>
					<button type='button' class='close' data-dismiss='modal'>
						<span aria-hidden='true'>×</span><span class='sr-only'>Close</span>
					</button>
					<h4 class='modal-title'>添加/修改数据源</h4>
				</div>
				<div class='modal-body'>
					<form action="${pageContext.request.contextPath }/db/addOrUpdate"
						method="post">
						<input type="hidden" id="id" name="id" value="">
						<div class='form-group'>
							<label for='url'>连接地址：</label> <input type='url'
								class='form-control' name="jdbcUrl" id='jdbcUrl' value=''
								placeholder='jdbcUrl'>
						</div>
						<div class='form-group'>
							<label for='username'>用户名:</label> <input type='text'
								class='form-control' name="username" value='' id='username'
								placeholder='username'>
						</div>
						<div class='form-group'>
							<label for='password'>密码:</label> <input type='password'
								class='form-control' value='' id='password' name="password"
								placeholder='password'>
						</div>
						<div class='form-group'>
							<label for='driver'>驱动:</label> <input type='text'
								class='form-control' name="driver" value='com.mysql.jdbc.Driver'
								id='driver' placeholder='driverClass'>
						</div>
						<div class='modal-footer'>
							<button type='button' class='btn btn-default'
								data-dismiss='modal'>关闭</button>
							<button type="submit" id='execute' class='btn btn-primary'>保存</button>
						</div>
					</form>
				</div>

			</div>
		</div>
	</div>

</body>
<script type="text/javascript">
	function addDBPop() {
		$('#editDBModal').modal('toggle')
	}

	function editDB(id) {
		var param = {
			id : id
		};
		var result = requestJson(
				"${pageContext.request.contextPath }/db/getById", param);
		$("#id").val(id);
		$("#jdbcUrl").val(result.jdbcUrl);
		$("#username").val(result.username);
		$("#password").val(result.password);
		$("#driver").val(result.driver);
		$('#editDBModal').modal('toggle');
	}

	function delDB(id) {
		var param = {
			id : id
		};
		var result = requestJson(
				"${pageContext.request.contextPath }/db/delDB", param);
		location.reload()
	}

	function genneratorModalPop(id) {
		$("#genDubboId").val(id);
		$('#genneratorModalPop').modal('toggle')
	}
	
	function genneratorWebModalPop(id) {
		$("#genWebId").val(id);
		$('#genneratorWebModalPop').modal('toggle')
	}

	function requestJson(url, param) {
		var result;
		$.ajax({
			type : "post",
			async : false,
			dataType : "json",
			//私有资源地址,用于验证用户是否登录
			url : url,
			data : param,
			success : function(data) {
				result = data;
			},
			error : function() {
			}
		});
		return result;
	}
</script>
<!-- /.content-wrapper -->
</html>