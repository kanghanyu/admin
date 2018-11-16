<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../admin/common.jsp" />
<script type="text/javascript" src="/js/bill.js"></script>
</head>
<body>


	<!-- Content Wrapper. Contains page content -->
	<div>
		<!-- Content Header (Page header) -->
		<section class="content-header">
			<h1>
				账单列表 
			</h1>
			<ol class="breadcrumb">
				<li><a href="#">账单列表 </a></li>
			</ol>
		</section>
	<br>
	<br>
	<ul id="myTab" class="nav nav-tabs">
		<li class="active">
			<a href="#jizhang" data-toggle="tab">
				进账
			</a>
		</li>
		<li>
			<a href="#chuzhang" data-toggle="tab">
				出账
			</a>
		</li>
	</ul>

		<!-- Main content -->
		<div id="myTabContent" class="tab-content">
			<div class="tab-pane fade in active" id="jizhang">
				<section class="content">
					<div class="row">
						<div class="col-xs-12">
							<div class="box">
								<div class="box-body">
									<div class="row">
											手机号: <input type="text" maxlength="11"  id="phone">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											订单号: <input type="text" id="orderId">&nbsp;&nbsp;
											开始时间:<input id="startDate" style="width: 100px;height: 26px" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'endDate\');}'})" />&nbsp;&nbsp;
											结束时间:<input id="endDate" style="width: 100px;height: 26px" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'startDate\');}'})" />
											账单类型:
											<select id="billType">
												<option value="">-请选择-</option>
												<option value=1>VIP账单</option>
												<option value="2">稻粒</option>
												<option value="3">话费充值</option>
												<option value="4">购物消费</option>
											</select>
											<button type="button" name="search" onclick="search()" class="btn btn-primary">查询</button>
									</div>
								</div>
								<!-- /.box-header -->
								<div class="box-body"  style="overflow:scroll;">
									<input type="hidden" id="pageNum" value="${page.pageNum}">
									<input type="hidden" id="pageSize" value="${page.pageSize}">
									<input type="hidden" id="pages" value="${page.pages}">
									<table id="table" class="table table-bordered  table-striped table-hover" style="min-width:1300px;">
										<thead>
											<tr>
												<th>uid</th>
												<th>账户名</th>
												<th>账单类型</th>
												<th>付款方式</th>
												<th>订单标识</th>
												<th>账单金额</th>
												<th>账单描述</th>
												<th>时间</th>
												<th>其他</th>
											</tr>
										</thead>
										<tbody id="tbody">
											<c:forEach var="bill" items="${page.list}">
												<tr>
													<td width="4%">${bill.uid}</td>
													<td width="5%">${bill.phone}</td>
													<td width="5%">${bill.billType==1?"VIP账单":(bill.billType==2?"稻粒":(bill.billType==3?"话费充值":"购物消费"))}</td>
													<td width="5%">${bill.payType==3?"支付宝":"微信"}</td>
													<td width="5%">${bill.orderId}</td>
													<td width="5%">${bill.amount==null?0:bill.amount}</td>
													<td width="7%">${bill.description}</td>
													<td width="8%"><fmt:formatDate value="${bill.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
													<td width="20%">
														<button class="btn btn-primary btn-sm"
															onclick="detailBill('${bill.id}')">详情</button>
													</td>
												</tr>
											</c:forEach>
										</tbody>
												<tr>
													<td colspan="5">统计</td>
													<td id="totalAmount">${amount}(元)</td>
												</tr>
									</table>
									<tr><div id="page"></div> </tr>
								</div>
								<!-- /.box-body -->
							</div>
							<!-- /.box -->
						</div>
					</div>
				</section>
			</div>
			
			<div class="tab-pane fade" id="chuzhang">
				<section class="content">
					<div class="row">
						<div class="col-xs-12">
							<div class="box">
								<div class="box-body">
									<div class="row">
											手机号: <input type="text" maxlength="11"  id="phonec">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											订单号: <input type="text" id="orderIdc">&nbsp;&nbsp;
											开始时间:<input id="startDatec" style="width: 100px;height: 26px" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'endDate\');}'})" />&nbsp;&nbsp;
											结束时间:<input id="endDatec" style="width: 100px;height: 26px" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'startDate\');}'})" />
											账单类型:
											<select id="billTypec">
												<option value="">-请选择-</option>
												<option value="3">话费充值</option>
												<option value="4">商品进价</option>
												<option value="5">提现账单</option>
											</select>
											<button type="button" name="search" onclick="searchc()" class="btn btn-primary">查询</button>
									</div>
								</div>
								<!-- /.box-header -->
								<div class="box-body"  style="overflow:scroll;">
									<input type="hidden" id="pageNumc" value="${pagec.pageNum}">
									<input type="hidden" id="pageSizec" value="${pagec.pageSize}">
									<input type="hidden" id="pagesc" value="${pagec.pages}">
									<table id="tablec" class="table table-bordered  table-striped table-hover" style="min-width:1300px;">
										<thead>
											<tr>
												<th>uid</th>
												<th>账户名</th>
												<th>账单类型</th>
												<th>订单标识</th>
												<th>账单金额</th>
												<th>账单描述</th>
												<th>时间</th>
												<th>其他</th>
											</tr>
										</thead>
										<tbody id="tbodyc">
											<c:forEach var="bill" items="${pagec.list}">
												<tr>
													<td width="4%">${bill.uid}</td>
													<td width="5%">${bill.phone}</td>
													<td width="5%">${bill.billType==1?"VIP账单":(bill.billType==2?"稻粒":(bill.billType==3?"话费充值":"购物消费"))}</td>
													<td width="5%">${bill.orderId}</td>
													<td width="5%">${bill.amount==null?0:bill.amount}</td>
													<td width="7%">${bill.description}</td>
													<td width="8%"><fmt:formatDate value="${bill.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
													<td width="20%">
														<button class="btn btn-primary btn-sm"
															onclick="detailBill('${bill.id}')">详情</button>
													</td>
												</tr>
											</c:forEach>
										</tbody>
												<tr>
													<td colspan="4">统计</td>
													<td id="totalAmountc">${amountc}(元)</td>
												</tr>
									</table>
									<tr><div id="pagec"></div> </tr>
								</div>
								<!-- /.box-body -->
							</div>
							<!-- /.box -->
						</div>
					</div>
				</section>
			</div>
		</div>
		<!-- /.content -->
	</div>



	<div class='modal' id='detailModal'>
		<div class='modal-dialog'>
			<div class='modal-content'>
				<div class='modal-header'>
					<button type='button' class='close' data-dismiss='modal'>
						<span aria-hidden='true'>×</span><span class='sr-only'>Close</span>
					</button>
					<h3 align="center" class='modal-title'>账单信息</h4>
				</div>
				<div class='modal-body' style="min-width:700;max-height:500px; overflow:scroll;">
					<div>
						<h4 align="center">账单的基本信息</h3>
						<div class='form-group'>
							<label for='url'>uid：</label> 
								<input type="text" id="uidD" class="form-control flat"  disabled="disabled" style="width:120px;display:inline;"> 
							<label for='url'>账户名：</label> 
								<input type="text" id="phoneD" class="form-control flat" disabled="disabled" style="width:120px;display:inline;"> 
						</div>
						<div class='form-group'>
							<label for='url'>类别：</label> 
								<input type="text" id="typeD" class="form-control flat" disabled="disabled" style="width:120px;display:inline;"> 
							<label for='url'>账单类型：</label> 
								<input type="text" id="billTypeD" class="form-control flat"  disabled="disabled" style="width:180px;display:inline;"> 
						</div>
						<div class='form-group'>
							<label for='url'>订单标识：</label> 
								<input type="text" id="orderIdD" class="form-control flat"  disabled="disabled" style="width:160px;display:inline;"> 
							<label for='url'>时间：</label> 
								<input type="text" id="createTimeD" class="form-control flat"  disabled="disabled" style="width:180px;display:inline;"> 
						</div>
						<div class='form-group'>
							<label for='url'>账单金额：</label> 
								<input type="text" id="amountD" class="form-control flat"  disabled="disabled" style="width:120px;display:inline;"> 
							<label for='url'>账单描述：</label> 
								<input type="text" id="descriptionD" class="form-control flat"  disabled="disabled" style="width:120px;display:inline;"> 
						</div>
						<div class='form-group'>
							<label for='url'>折扣：</label> 
								<input type="text" id="discountD" class="form-control flat"  disabled="disabled" style="width:120px;display:inline;"> 
							<label for='url'>邮费：</label> 
								<input type="text" id="postageD" class="form-control flat"  disabled="disabled" style="width:120px;display:inline;"> 
						</div>
					</div>
					<table id="zd_table" class="table table-bordered  table-striped table-hover">
						<thead>
							<tr>
								<th>商品名称</th>
								<th>商品类别</th>
								<th>价格</th>
								<th>数量</th>
								<th>总价</th>
								<th>描述</th>
							</tr>
						</thead>
						<tbody id="zd_tbody">
						</tbody>
					</table>
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