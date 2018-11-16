<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../admin/common.jsp" />
<script type="text/javascript" src="/js/order.js"></script>
</head>
<body>
	<div>
		<!-- Content Header (Page header) -->
		<section class="content-header">
			<h1>
				订单列表 
			</h1>
			<ol class="breadcrumb">
				<li><a href="#">订单列表 </a></li>
			</ol>
		</section>

		<!-- Main content -->
		<section class="content">
			<div class="row">
				<div class="col-xs-12">
					<div class="box">
						<div class="box-body">
							<div class="row">
									手机号: <input type="text" maxlength="11"  id="phone">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									订单号: <input type="text" id="orderId">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									开始时间:<input id="startDate" style="width: 100px;height: 26px" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'endDate\');}'})" />&nbsp;&nbsp;
									结束时间:<input id="endDate" style="width: 100px;height: 26px" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'startDate\');}'})" />
									订单状态:
									<select id="status">
										<option value="">-请选择-</option>
										<option value="1">未付款</option>
										<option value="2">已付款</option>
										<option value="3">已取消</option>
									</select>
									是否发货:
									<select id="isSend">
										<option value="">-请选择-</option>
										<option value="1">未发货</option>
										<option value="2">已发货</option>
									</select>
									<button type="button" name="search" onclick="search()" class="btn btn-primary">查询</button>
							</div>
						</div>
						<!-- /.box-header -->
						<div class="box-body"  style="overflow:scroll;">
							<input type="hidden" id="pageNum" value="${page.pageNum}">
							<input type="hidden" id="pageSize" value="${page.pageSize}">
							<input type="hidden" id="pages" value="${page.pages}">
							<table id="table1" class="table table-bordered  table-striped table-hover" style="min-width:3000px;">
								<thead>
									<tr >
										<th>订单号</th>
										<th>订单状态</th>
										<th>是否发货</th>
										<th>创建时间</th>
										<th>账户名</th>
										<th>商品总金额</th>
										<th>邮费</th>
										<th>订单应付金额</th>
										<th>折扣</th>
										<th>商品折扣价</th>
										<th>订单实付金额</th>
										<th>付款方式</th>
										<th>RMB金额</th>
										<th>余额抵扣</th>
										<th>付款时间</th>
										<th>收件人姓名</th>
										<th>收件人电话</th>
										<th>收件人地址</th>
										<th>其他</th>
									</tr>
								</thead>
								<tbody id="tbody">
									<c:forEach var="order" items="${page.list}" >
										<tr>
											<td width="1%" >${order.orderId}</td>
											<td width="1%" >${order.statusStr}</td>
											<td width="1%" >${order.isSend==1?"未发货":"已发货"}</td>
											<td width="2%" ><fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											<td width="1%" >${order.amountPhone}</td>
											<td width="1%" >${order.totalMoney}</td>
											<td width="1%" >${order.postage}</td>
											<td width="1%" >${order.totalPayable}</td>
											<td width="1%" >${order.discount==null?"暂无":order.discount}</td>
											<td width="1%" >${order.discountMoney!=null?order.discountMoney:order.totalMoney}</td>
											<td width="1%" >${order.totalPay}</td>
											<td width="1%" >${order.payType==1?"稻粒":(order.payType==3?"支付宝":"微信")}</td>
											<td width="1%" >${order.rmb!=null?order.rmb:0}</td>
											<td width="1%" >${order.cornMoney!=null?order.cornMoney:0}</td>
											<td width="1%" ><fmt:formatDate value="${order.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											<td width="1%" >${order.userName}</td>
											<td width="1%" >${order.phone}</td>
											<td width="2%" >${order.address}</td>
											<td width="2%" >
												<button class="btn btn-primary btn-sm" onclick="detailOrder('${order.orderId}')">详情</button>
												<c:choose>
													<c:when test="${order.isSend==1}">
														<button class="btn btn-primary btn-sm" onclick="isSend('${order.orderId}',2)">未发货</button>
													</c:when>
													<c:otherwise>
														<button class="btn btn-primary btn-sm" onclick="isSend('${order.orderId}',1)">已发货</button>
													</c:otherwise>
												</c:choose>
												<button class="btn btn-danger btn-sm" onclick="deleteOrder('${order.orderId}')">删除</button>
											</td>
										</tr>
									</c:forEach>
								</tbody>
										<tr>
											<td colspan="7">统计</td>
											<td id="countTotalMoney">${count.totalMoney}(元)</td>
											<td ></td>
											<td ></td>
											<td id="countTotalPay">${count.totalPay}(元)</td>
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
		<!-- /.content -->
	</div>


	
	<!--我的通讯录  -->
	<div class='modal' id='detailModal'>
		<div class='modal-dialog'>
			<div class='modal-content'>
				<div class='modal-header'>
					<button type='button' class='close' data-dismiss='modal'>
						<span aria-hidden='true'>×</span><span class='sr-only'>Close</span>
					</button>
					<h4 align="center" class='modal-title'>订单商品的详情</h4>
				</div>
				<div class='modal-body' style="min-width:700;max-height:500px; overflow:scroll;">
				
					<table id="txl_table" class="table table-bordered  table-striped table-hover">
						<thead>
							<tr>
								<th>商品名称</th>
								<th>缩略图</th>
								<th>价格</th>
								<th>数量</th>
								<th>总价</th>
							</tr>
						</thead>
						<tbody id="txl_tbody">
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