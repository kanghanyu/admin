<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../admin/common.jsp" />
<script type="text/javascript" src="/js/user.js"></script>
</head>
<body>


	<!-- Content Wrapper. Contains page content -->
	<div>
		<!-- Content Header (Page header) -->
		<section class="content-header">
			<h1>
				用户列表 
			</h1>
			<ol class="breadcrumb">
				<li><a href="#">用户列表 </a></li>
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
									邀请人手机号: <input type="text" maxlength="11" id="inviterPhone">&nbsp;&nbsp;
									开始时间:<input id="startDate" style="width: 100px;height: 26px" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'endDate\');}'})" />&nbsp;&nbsp;
									结束时间:<input id="endDate" style="width: 100px;height: 26px" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'startDate\');}'})" />
									角色:
									<select id="isManager">
										<option value="">-请选择-</option>
										<option value=0>普通用户</option>
										<option value="1">管理员</option>
									</select>
									用户类型:
									<select id="isVip">
										<option value="">-请选择-</option>
										<option value=0>普通用户</option>
										<option value="1">VIP用户</option>
									</select>
									<button type="button" name="search" onclick="search()" class="btn btn-primary">查询</button>
							</div>
						</div>
						<!-- /.box-header -->
						<div class="box-body"  style="overflow:scroll;">
							<input type="hidden" id="pageNum" value="${page.pageNum}">
							<input type="hidden" id="pageSize" value="${page.pageSize}">
							<input type="hidden" id="pages" value="${page.pages}">
							<table id="table1" class="table table-bordered  table-striped table-hover" style="min-width:2500px;">
								<thead>
									<tr>
										<th>uid</th>
										<th>手机号</th>
										<th>余额</th>
										<th>稻粒</th>
										<th>佣金</th>
										<th>头像</th>
										<th>邀请人</th>
										<th>角色</th>
										<th>用户类型</th>
										<th>转账记录</th>
										<th>佣金记录</th>
										<th>提现记录</th>
										<th>通讯录</th>
										<th>我的账单</th>
										<th>我的订单</th>
										<th>话费充值记录</th>
										<th>其他操作</th>
									</tr>
								</thead>
								<tbody id="tbody">
									<c:forEach var="user" items="${page.list}">
										<tr>
											<td width="4.5%">${user.uid}</td>
											<td width="5%">${user.phone}</td>
											<td width="3.5%">${user.money==null?0:user.money}</td>
											<td width="3.5%">${user.cardMoney==null?0:user.cardMoney}</td>
											<td width="3.5%">${user.commission==null?0:user.commission}</td>
											<td width="4%"><img src='${user.img==null?"":user.img}' height="50px" width="50px"></td>
											<td width="5%">${user.inviterPhone==null?"无":user.inviterPhone}</td>
											<td width="3.5%">${user.isManager==0?"普通用户":"管理员"}</td>
											<td width="4%">${user.isVip==0?"普通用户":"VIP用户"}</td>
											<td width="4%" class="move"><a onclick="userRecord(${user.uid},2)" />转账记录</td>
											<td width="4%" class="move"><a onclick="userRecord(${user.uid},3)" />佣金记录</td>
											<td width="4%" class="move"><a onclick="userCash(${user.uid})" />提现记录</td>
											<td width="4%" class="move"><a onclick="userInviter(${user.uid},1)"/>通讯录</td>
											<td width="4%" class="move"><a onclick="userBill(${user.uid})" />我的账单</td>
											<td width="4%" class="move"><a onclick="userOrder(${user.uid})" />我的订单</td>
											<td width="5%" class="move"><a onclick="userPhoneRecord(${user.uid})" />话费充值记录</td>
											<td width="20%">
												<button class="btn btn-primary btn-sm"
													onclick="detailUserInfo('${user.uid }')">详情</button>
											<c:choose>
												<c:when test="${user.status == 0}">
													<button class="btn btn-danger btn-sm" 
														onclick="setUserStatus('${user.uid }','1')">冻结</button>
												</c:when>
												<c:otherwise>
													<button class="btn btn-warning btn-sm" 
														onclick="setUserStatus('${user.uid }','4')">解冻</button>
												</c:otherwise>
											</c:choose>
											<c:choose>
												<c:when test="${user.isManager == 0}">
													<button class="btn btn-danger btn-sm"
														onclick="setUserStatus('${user.uid }','2')">升为管理员</button>
												</c:when>
												<c:otherwise>
													<button class="btn btn-warning btn-sm"
													onclick="setUserStatus('${user.uid }','5')">取消管理员</button>
												</c:otherwise>
											</c:choose>
											<c:choose>
												<c:when test="${user.isVip == 0}">
													<button class="btn btn-danger btn-sm"
														onclick="setUserStatus('${user.uid }','3')">手动添加VIP</button>
												</c:when>
												<c:otherwise>
													<button class="btn btn-warning btn-sm"
														onclick="setUserStatus('${user.uid }','6')">取消VIP</button>
												</c:otherwise>
											</c:choose>
											</td>
										</tr>
									</c:forEach>
								</tbody>
										<tr>
											<td colspan="2">统计</td>
											<td id="countTotalMoney">${count.totalMoney}(元)</td>
											<td id="countTotalCardMoney">${count.totalCardMoney}(元)</td>
											<td id="countTotalCommission">${count.totalCommission}(元)</td>
											<td ></td>
											<td colspan="2"></td>
											<td id="countUserAmount">${count.vipNum}人/${count.amount}人</td>
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


	<div class='modal' id='detailModal'>
		<div class='modal-dialog'>
			<div class='modal-content'>
				<div class='modal-header'>
					<button type='button' class='close' data-dismiss='modal'>
						<span aria-hidden='true'>×</span><span class='sr-only'>Close</span>
					</button>
					<h4 class='modal-title'>用户详情内容</h4>
				</div>
				<div class='modal-body'>
						<input type="hidden" id="id" name="id" value="">
					<div>
						<h3 align="center">基础信息</h3>
						<div class='form-group'>
							<label for='url'>uid：&nbsp;</label> 
								<input type="text" id="uidD" class="form-control flat"  disabled="disabled" style="width:160px;display:inline;"> 
							<label for='url'>手机号：</label> 
								<input type="text" id="phoneD" class="form-control flat" disabled="disabled" style="width:160px;display:inline;"> 
						</div>
						<div class='form-group'>
							<label for='url'>邀请人uid：</label> 
								<input type="text" id="inviterUidD" class="form-control flat"  disabled="disabled" style="width:160px;display:inline;"> 
							<label for='url'>邀请人手机号：</label> 
								<input type="text" id="inviterPhoneD" class="form-control flat"  disabled="disabled" style="width:160px;display:inline;"> 
						</div>
						<div class='form-group'>
							<label for='url'>余额：</label> 
								<input type="text" id="moneyD" class="form-control flat"  disabled="disabled" style="width:100px;display:inline;"> 
							<label for='url'>稻粒：</label> 
								<input type="text" id="cardMoneyD" class="form-control flat"  disabled="disabled" style="width:100px;display:inline;"> 
							<label for='url'>佣金：</label> 
								<input type="text" id="commissionD" class="form-control flat"  disabled="disabled" style="width:100px;display:inline;"> 
						</div>
						
						<div class='form-group'>
							<label for='url'>头像：</label> 
									<img src="" id="imgD" height="50px" width="50px">
							<label for='url'>角色：</label> 
								<input type="text" id="isManagerD" class="form-control flat"  disabled="disabled" style="width:100px;display:inline;"> 
							<label for='url'>用户类型：</label> 
								<input type="text" id="isVipD" class="form-control flat"  disabled="disabled" style="width:100px;display:inline;"> 
						</div>
						<div class='form-group'>
							<label for='url'>邀请二维码地址：</label> 
								<input type="text" id="imgUrlD" class="form-control flat"  disabled="disabled" style="width:300px;display:inline;"> 
						</div>
					</div>
					<div>
						<h3 align="center">银行卡信息</h3>
						<div class='form-group'>
							<label for='url'>银行名：</label> 
								<input type="text" id="bankNameD" class="form-control flat"  disabled="disabled" style="width:160px;display:inline;"> 
							<label for='url'>银行卡号：</label> 
								<input type="text" id="bankNumD" class="form-control flat" disabled="disabled" style="width:160px;display:inline;"> 
						</div>
						<div class='form-group'>
							<label for='url'>户主：</label> 
								<input type="text" id="userNameD" class="form-control flat"  disabled="disabled" style="width:180px;display:inline;"> 
							<label for='url'>联系号码：</label> 
								<input type="text" id="phoneD1" class="form-control flat"  disabled="disabled" style="width:160px;display:inline;"> 
						</div>
						<div class='form-group'>
							<label for='url'>开户地址：</label> 
								<input type="text" id="bankAdressD" class="form-control flat"  disabled="disabled" style="width:320px;display:inline;"> 
						</div>
					</div>
					<div>
						<h3 align="center">收货地址信息</h3>
						<div class='form-group'>
							<label for='url'>收件人姓名：</label> 
								<input type="text" id="userNameDD" class="form-control flat"  disabled="disabled" style="width:160px;display:inline;"> 
							<label for='url'>收件人电话：</label> 
								<input type="text" id="phoneDD" class="form-control flat" disabled="disabled" style="width:160px;display:inline;"> 
						</div>
						<div class='form-group'>
							<label for='url'>邮编：</label> 
								<input type="text" id="postCodeDD" class="form-control flat"  disabled="disabled" style="width:180px;display:inline;"> 
						</div>
						<div class='form-group'>
							<label for='url'>收货地址：</label> 
								<input type="text" id="addressDD" class="form-control flat"  disabled="disabled" style="width:320px;display:inline;"> 
						</div>
					</div>
					<div class='modal-footer'>
						<button type='button' class='btn btn-default'
							data-dismiss='modal'>关闭</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 转账记录 /佣金记录 -->
	<div class='modal' id='zzjl_Modal'>
		<div class='modal-dialog'>
			<div class='modal-content'>
				<div class='modal-header'>
					<button type='button' class='close' data-dismiss='modal'>
						<span aria-hidden='true'>×</span><span class='sr-only'>Close</span>
					</button>
					<h4 align="center" class='modal-title-zz-yj'></h4>
				</div>
				<div class='modal-body' style="min-width:700;max-height:500px; overflow:scroll;">
					<table id="zzjl_table" class="table table-bordered  table-striped table-hover">
						<thead>
							<tr>
								<th>类型</th>
								<th>描述</th>
								<th class="daoli">稻粒</th>
								<th>时间</th>
							</tr>
						</thead>
						<tbody id="zzjl_tbody">
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
	
	<!--提现记录  -->
	<div class='modal' id='txjl_Modal'>
		<div class='modal-dialog'>
			<div class='modal-content'>
				<div class='modal-header'>
					<button type='button' class='close' data-dismiss='modal'>
						<span aria-hidden='true'>×</span><span class='sr-only'>Close</span>
					</button>
					<h4 align="center" class='modal-title'>提现记录</h4>
				</div>
				<div class='modal-body' style="min-width:700;max-height:500px; overflow:scroll;">
					<table id="txjl_table" class="table table-bordered  table-striped table-hover">
						<thead>
							<tr>
								<th>时间</th>
								<th>金额</th>
								<th>状态</th>
							</tr>
						</thead>
						<tbody id="txjl_tbody">
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
	
	<!--我的通讯录  -->
	<div class='modal' id='txl_Modal'>
		<div class='modal-dialog'>
			<div class='modal-content'>
				<div class='modal-header'>
					<button type='button' class='close' data-dismiss='modal'>
						<span aria-hidden='true'>×</span><span class='sr-only'>Close</span>
					</button>
					<h4 align="center" class='modal-title'>我的通讯录</h4>
				</div>
				<div class='modal-body' style="min-width:700;max-height:500px; overflow:scroll;">
				
					<h5 align="center" class='modal-title_fs'>粉丝:0人数</h4>
					<table id="txl_table" class="table table-bordered  table-striped table-hover">
						<thead>
							<tr>
								<th>头像</th>
								<th>用户信息</th>
								<th>身份类别</th>
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
	
	<!--我的账单内容  -->
	<div class='modal' id='zd_Modal'>
		<div class='modal-dialog'>
			<div class='modal-content'>
				<div class='modal-header'>
					<button type='button' class='close' data-dismiss='modal'>
						<span aria-hidden='true'>×</span><span class='sr-only'>Close</span>
					</button>
					<h4 align="center" class='modal-title'>我的账单</h4>
				</div>
				<div class='modal-body' style="min-width:700;max-height:500px; overflow:scroll;">
					<table id="zd_table" class="table table-bordered  table-striped table-hover">
						<thead>
							<tr>
								<th>时间</th>
								<th>金额</th>
								<th>类型</th>
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
	
	
	<!--话费充值记录  -->
	<div class='modal' id='hf_Modal'>
		<div class='modal-dialog'>
			<div class='modal-content'>
				<div class='modal-header'>
					<button type='button' class='close' data-dismiss='modal'>
						<span aria-hidden='true'>×</span><span class='sr-only'>Close</span>
					</button>
					<h4 align="center" class='modal-title'>话费充值记录</h4>
				</div>
				<div class='modal-body' style="min-width:700;max-height:500px; overflow:scroll;">
					<table id="hf_table" class="table table-bordered  table-striped table-hover">
						<thead>
							<tr>
								<th>提交时间</th>
								<th>充值号码</th>
								<th>充值金额</th>
								<th>折扣金额</th>
								<th>状态</th>
							</tr>
						</thead>
						<tbody id="hf_tbody">
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
	
	<!--我的订单  -->
	<div class='modal' id='dd_Modal'>
		<div class='modal-dialog'>
			<div class='modal-content'>
				<div class='modal-header'>
					<button type='button' class='close' data-dismiss='modal'>
						<span aria-hidden='true'>×</span><span class='sr-only'>Close</span>
					</button>
					<h4 align="center" class='modal-title'>我的订单</h4>
				</div>
				<div class='modal-body' style="min-width:700;max-height:500px; overflow:scroll;">
					<table id="dd_table" class="table table-bordered  table-striped table-hover">
						<thead>
							<!-- <tr>
								<th>提交时间</th>
								<th>充值号码</th>
								<th>充值金额</th>
								<th>折扣金额</th>
								<th>状态</th>
							</tr> -->
						</thead>
						<tbody id="dd_tbody">
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