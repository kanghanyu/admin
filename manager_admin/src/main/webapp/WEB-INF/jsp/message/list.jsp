<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../admin/common.jsp" />
<script type="text/javascript" src="/js/message.js"></script>
</head>
<body>
	<div>
		<!-- Content Header (Page header) -->
		<section class="content-header">
			<h1>
				公告管理
			</h1>
			<ol class="breadcrumb">
				<li><a href="#">公告管理列表 </a></li>
			</ol>
		</section>

		<!-- Main content -->
		<section class="content">
			<div class="row">
				<div class="col-xs-12">
					<div class="box">
						<div class="box-body">
							<div class="row">
									标题名称: <input placeholder="标题" type="text" maxlength="50"  id="title">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									状态:
									<button type="button" name="search" onclick="search()" class="btn btn-primary">查询</button>
									<button type="button" id="add" onclick="add()"
													class="btn btn-primary">新增公告</button>
							</div>
						</div>
						<!-- /.box-header -->
						<div class="box-body">
							<input type="hidden" id="pageNum" value="${page.pageNum}">
							<input type="hidden" id="pageSize" value="${page.pageSize}">
							<input type="hidden" id="pages" value="${page.pages}">
							<table id="table1"
								class="table table-bordered  table-striped table-hover">
								<thead>
									<tr>
										<th>id</th>
										<th>公告的标题</th>
										<th>公告的内容</th>
										<th>创建时间</th>
										<th>其他操作</th>
									</tr>
								</thead>
								<tbody id="tbody">
									<c:forEach var="entity" items="${page.list}">
										<tr>
											<td width="3%">${entity.id}</td>
											<td width="9%">${entity.title}</td>
											<td width="40%">
												<c:if test="${fn:length(entity.content)>50 }">                         
													${fn:substring(entity.content, 0, 50)}...                   
												</c:if>                  
												<c:if test="${fn:length(entity.content)<=50 }"> 
												    ${entity.content }                   
												</c:if>
											</td>
											<td width="7%">${entity.createTimeStr}</td>
											<td width="20%">
												<button class="btn btn-primary btn-sm"
													onclick="detail('${entity.id}')">
													<i class="glyphicon glyphicon-edit">详情</i>
												</button> 
												<button class="btn btn-primary btn-sm"
													onclick="edit('${entity.id}')">
													<i class="glyphicon glyphicon-edit">修改</i>
												</button> 
												<button class="btn btn-warning btn-sm"
													onclick="del('${entity.id}')">删除</button>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<tr><div id="page"></div> </tr>
						</div>
						<!-- /.box-body -->
					</div>
					<!-- /.box -->
				</div>
			</div>
		</section>
		<!-- /.content -->
	</div>


	<div class='modal' id='addProduct'>
		<div class='modal-dialog'>
			<div class='modal-content'>
				<div class='modal-header'>
					<button type='button' class='close' data-dismiss='modal'>
						<span aria-hidden='true'>×</span><span class='sr-only'>Close</span>
					</button>
					<h4 class='modal-title' align="center" >添加公告内容</h4>
				</div>
				<div class='modal-body'>
						<div class='form-group'>
							<label for='url'>公告的标题</label> 
							<input type="text" class='form-control' id="titleSave" placeholder='公告的标题'>
						</div>
						<div class='form-group'>
							<label for='driver'>公告的内容:</label> 
							<textarea rows="5" id="contentSave"  cols=80   onpropertychange= "this.style.posHeight=this.scrollHeight "></textarea>
						</div>
						<div class='modal-footer'>
							<button type='button' class='btn btn-default'
								data-dismiss='modal'>关闭</button>
							<button type="button" onclick="save()" class='btn btn-primary' >保存</button>
						</div>
				</div>

			</div>
		</div>
	</div>
	
	<div class='modal' id='editProduct'>
		<div class='modal-dialog'>
			<div class='modal-content'>
				<div class='modal-header'>
					<button type='button' class='close' data-dismiss='modal'>
						<span aria-hidden='true'>×</span><span class='sr-only'>Close</span>
					</button>
					<h4 class='modal-title'>修改公告内容</h4>
				</div>
				<div class='modal-body'>
						<input type="hidden" id="idE">
						<div class='form-group'>
							<label for='url'>公告的标题</label> 
							<input type="text" class='form-control' id="titleE" placeholder='在线参数的key'>
						</div>
						<div class='form-group'>
							<label for='driver'>公告的内容:</label> 
							<textarea rows="5" id="contentE"  cols=80   onpropertychange= "this.style.posHeight=this.scrollHeight "></textarea>
						</div>
						<div class='modal-footer'>
							<button type='button' class='btn btn-default'
								data-dismiss='modal'>关闭</button>
							<button type="button" onclick="update()" class='btn btn-primary' >保存</button>
						</div>
				</div>
			</div>
		</div>
	</div>
	
	
	
	<div class='modal' id='detailProduct'>
		<div class='modal-dialog'>
			<div class='modal-content'>
				<div class='modal-header'>
					<button type='button' class='close' data-dismiss='modal'>
						<span aria-hidden='true'>×</span><span class='sr-only'>Close</span>
					</button>
					<h4 class='modal-title'>公告的详情</h4>
				</div>
				<div class='modal-body'>
						<div class='form-group'>
							<label for='url'>公告的标题</label> 
							<input type="text" class='form-control' id="titleD" disabled="disabled" >
						</div>
						<div class='form-group'>
							<label for='driver'>公告的内容:</label> 
							<textarea rows="5" id="contentD"  cols=80 disabled="disabled"  onpropertychange= "this.style.posHeight=this.scrollHeight "></textarea>
						</div>
						<div class='modal-footer'>
							<button type='button' class='btn btn-default'
								data-dismiss='modal'>关闭</button>
						</div>
				</div>
			</div>
		</div>
	</div>
</body>
 
<!-- /.content-wrapper -->
</html>