<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../admin/common.jsp" />
<script type="text/javascript" src="/js/online.js"></script>
</head>
<body>
	<div>
		<!-- Content Header (Page header) -->
		<section class="content-header">
			<h1>
				在线参数列表 
			</h1>
			<ol class="breadcrumb">
				<li><a href="#">在线参数列列表 </a></li>
			</ol>
		</section>

		<!-- Main content -->
		<section class="content">
			<div class="row">
				<div class="col-xs-12">
					<div class="box">
						<div class="box-body">
							<div class="row">
									title名称: <input type="text" maxlength="50"  id="title">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									状态:
									<button type="button" name="search" onclick="search()" class="btn btn-primary">查询</button>
									<button type="button" id="add" onclick="add()"
													class="btn btn-primary">新增记录</button>
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
										<th>在线参数title</th>
										<th>在线参数描述</th>
										<th>在线参数content</th>
										<th>创建时间</th>
										<th>其他操作</th>
									</tr>
								</thead>
								<tbody id="tbody">
									<c:forEach var="entity" items="${page.list}">
										<tr>
											<td width="3%">${entity.id}</td>
											<td width="9%">${entity.title}</td>
											<td width="20%">${entity.description}</td>
											<td width="20%">
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
													onclick="detail('${entity.title }')">
													<i class="glyphicon glyphicon-edit">详情</i>
												</button> 
												<button class="btn btn-primary btn-sm"
													onclick="edit('${entity.title }')">
													<i class="glyphicon glyphicon-edit">修改</i>
												</button> 
												<button class="btn btn-warning btn-sm"
													onclick="del('${entity.title }')">删除</button>
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
					<h4 class='modal-title' align="center" >添加在线参数</h4>
				</div>
				<div class='modal-body'>
						<div class='form-group'>
							<label for='url'>在线参数title</label> 
							<input type="text" class='form-control' id="titleSave" placeholder='在线参数title'>
						</div>
						<div class='form-group'>
							<label for='url'>在线参数描述</label> 
							<input type="text" class='form-control' id="descriptionSave" placeholder='在线参数描述'>
						</div>
						<div class='form-group'>
							<label for='driver'>在线参数内容:</label> 
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
					<h4 class='modal-title'>修改在线参数</h4>
				</div>
				<div class='modal-body'>
						<div class='form-group'>
							<label for='url'>记录名称</label> 
							<input type="text" class='form-control' id="titleE" disabled="disabled" placeholder='在线参数的key'>
						</div>
						<div class='form-group'>
							<label for='url'>在线参数描述</label> 
							<input type="text" class='form-control' id="descriptionE" placeholder='在线参数描述'>
						</div>
						<div class='form-group'>
							<label for='driver'>在线参数内容:</label> 
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
					<h4 class='modal-title'>详情内容</h4>
				</div>
				<div class='modal-body'>
						<div class='form-group'>
							<label for='url'>记录名称</label> 
							<input type="text" class='form-control' id="titleD" disabled="disabled" >
						</div>
						<div class='form-group'>
							<label for='url'>在线参数描述</label> 
							<input type="text" class='form-control' id="descriptionD" disabled="disabled">
						</div>
						<div class='form-group'>
							<label for='driver'>在线参数内容:</label> 
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