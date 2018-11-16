$(function(){
	var pages = $("#pages").val();
	var pageNum = $("#pageNum").val();
	var pageSize = $("#pageSize").val();
	var options={
            bootstrapMajorVersion:1,    //版本
            currentPage:pageNum,    //当前页数
            numberOfPages:5,    //最多显示Page页
            totalPages:pages,    //所有数据可以显示的页数
            onPageClicked:function(e,originalEvent,type,page){
            	addHtml(page);
            }
        }
        $("#page").bootstrapPaginator(options);
	
	$(".move").mouseover(function(e){
		$(this).addClass("info");
	})
	$(".move").mouseout(function(){
		$(".move").removeClass("info");
	})
	
})


function addHtml(pageNum){
	var pageSize = $("#pageSize").val();
	var phone = $("#phone").val();
	var inviterPhone = $("#inviterPhone").val();
	var isManager = $("#isManager").val();
	var isVip = $("#isVip").val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var data = {
			"pageNum":pageNum,"pageSize":pageSize,"phone":phone,"inviterPhone":inviterPhone,
			"isManager":isManager,"isVip":isVip,"startDate":startDate,"endDate":endDate
	}
	
	$.ajax({
		type : "post",
		data : JSON.stringify(data),
		url : "/user/dataList",
		dataType : "json",
		contentType : 'application/json',
		success : function(data) {
			if(data != null&& null != data.page){
				$("#tbody").html("");
				var htmlStr="";
				$.each(data.page.list, function (index, item) {
					var moneyStr = item.moneyStr!=null?item.moneyStr:"0.00";
					var cardMoney =(item.cardMoney!=null&&item.cardMoney!=0)?item.cardMoney:"0.00"
					var commission =(item.commission!=null&&item.commission!=0)?item.commission:"0.00"
					var img = item.img!=null?item.img:"";
					var inviterUid = item.inviterUid!=null?item.inviterUid:"无";
					var inviterPhone = item.inviterPhone!=null?item.inviterPhone:"无";
					var isManager = item.isManager==0?"普通用户":"管理员";
					var isVip = item.isVip==0?"普通用户":"VIP用户";
					htmlStr += "<tr>";
					htmlStr += '<td width="4.5%">'+item.uid+'</td>';
					htmlStr += '<td width="5%">'+item.phone+'</td>';
					htmlStr += '<td width="3.5%">'+moneyStr+'</td>';
					htmlStr += '<td width="3.5%">'+cardMoney+'</td>';
					htmlStr += '<td width="3.5%">'+commission+'</td>';
					htmlStr += '<td width="4%"><img src="'+img+'" height="50px" width="50px"></td>';
					htmlStr += '<td width="5%">'+inviterPhone+'</td>';
					htmlStr += '<td width="3.5%">'+isManager+'</td>';
					htmlStr += '<td width="4%">'+isVip+'</td>';
					htmlStr += '<td width="4%" class="move"><a onclick="userRecord('+item.uid+',2)" >转账记录</a></td>';
					htmlStr += '<td width="4%" class="move"><a onclick="userRecord('+item.uid+',3)" >佣金记录</a></td>';
					htmlStr += '<td width="4%" class="move"><a onclick="userCash('+item.uid+')" >提现记录</a></td>';
					htmlStr += '<td width="4%" class="move"><a onclick="userInviter('+item.uid+',1)">通讯录</a></td>';
					htmlStr += '<td width="4%" class="move"><a onclick="userBill('+item.uid+')" >我的账单</a></td>';
					htmlStr += '<td width="4%" class="move"><a onclick="userOrder('+item.uid+')" >我的订单</a></td>';
					htmlStr += '<td width="5%" class="move"><a onclick="userPhoneRecord('+item.uid+')">话费充值记录</a></td>';
					htmlStr += '<td width="20%">';
					htmlStr += '<button class="btn btn-primary btn-sm" onclick="detailUserInfo('+item.uid+')">详情</button>'
					if(item.status == 0){
						htmlStr += '<button class="btn btn-danger btn-sm" onclick="setUserStatus('+item.uid+',1)">冻结</button>'
					}else{
						htmlStr += '<button class="btn btn-warning btn-sm" onclick="setUserStatus('+item.uid+',4)">解冻</button>'
					}
					if(item.isManager == 0){
						htmlStr += '<button class="btn btn-danger btn-sm" onclick="setUserStatus('+item.uid+',2)">升为管理员</button>'
					}else{
						htmlStr += '<button class="btn btn-warning btn-sm" onclick="setUserStatus('+item.uid+',5)">取消管理员</button>'
					}
					if(item.isVip == 0){
						htmlStr += '<button class="btn btn-danger btn-sm" onclick="setUserStatus('+item.uid+',3)">手动添加VIP</button>'
					}else{
						htmlStr += '<button class="btn btn-warning btn-sm" onclick="setUserStatus('+item.uid+',6)">取消VIP</button>'
					}
					htmlStr += '</td>'
					htmlStr += '</tr>';
				});
				$("#tbody").html(htmlStr);
				if(null != data.count){
					$("#countTotalMoney").text(data.count.totalMoney+"(元)");
					$("#countTotalCardMoney").text(data.count.totalCardMoney+"(元)");
					$("#countTotalCommission").text(data.count.totalCommission+"(元)");
					$("#countUserAmount").text(data.count.vipNum+"人/"+data.count.amount+"人");
				}
			}
		}
	});
}

function search(){
	var pages = 0;
	var pageNum = 1
	var pageSize = $("#pageSize").val();
	var phone = $("#phone").val();
	var inviterPhone = $("#inviterPhone").val();
	var isManager = $("#isManager").val();
	var isVip = $("#isVip").val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var data = {
			"pageNum":pageNum,"pageSize":pageSize,"phone":phone,"inviterPhone":inviterPhone,
			"isManager":isManager,"isVip":isVip,"startDate":startDate,"endDate":endDate
	}
	
	$.ajax({
		type : "post",
		data : JSON.stringify(data),
		url : "/user/dataList",
		dataType : "json",
		contentType : 'application/json',
		success : function(data) {
			if(data != null && null != data.page){
				pageNum = data.page.pageNum
				pages = data.page.pages;
				$("#tbody").html("");
				var htmlStr="";
				$.each(data.page.list, function (index, item) {
					var moneyStr = item.moneyStr!=null?item.moneyStr:"0.00";
					var cardMoney =(item.cardMoney!=null&&item.cardMoney!=0)?item.cardMoney:"0.00"
					var commission =(item.commission!=null&&item.commission!=0)?item.commission:"0.00"
					var img = item.img!=null?item.img:"";
					var inviterUid = item.inviterUid!=null?item.inviterUid:"无";
					var inviterPhone = item.inviterPhone!=null?item.inviterPhone:"无";
					var isManager = item.isManager==0?"普通用户":"管理员";
					var isVip = item.isVip==0?"普通用户":"VIP用户";
					htmlStr += "<tr>";
					htmlStr += '<td width="4.5%">'+item.uid+'</td>';
					htmlStr += '<td width="5%">'+item.phone+'</td>';
					htmlStr += '<td width="3.5%">'+moneyStr+'</td>';
					htmlStr += '<td width="3.5%">'+cardMoney+'</td>';
					htmlStr += '<td width="3.5%">'+commission+'</td>';
					htmlStr += '<td width="4%"><img src="'+img+'" height="50px" width="50px"></td>';
					htmlStr += '<td width="5%">'+inviterPhone+'</td>';
					htmlStr += '<td width="3.5%">'+isManager+'</td>';
					htmlStr += '<td width="4%">'+isVip+'</td>';
					htmlStr += '<td width="4%" class="move"><a onclick="userRecord('+item.uid+',2)" >转账记录</a></td>';
					htmlStr += '<td width="4%" class="move"><a onclick="userRecord('+item.uid+',3)" >佣金记录</a></td>';
					htmlStr += '<td width="4%" class="move"><a onclick="userCash('+item.uid+')" >提现记录</a></td>';
					htmlStr += '<td width="4%" class="move"><a onclick="userInviter('+item.uid+',1)">通讯录</a></td>';
					htmlStr += '<td width="4%" class="move"><a onclick="userBill('+item.uid+')" >我的账单</a></td>';
					htmlStr += '<td width="4%" class="move"><a onclick="userOrder('+item.uid+')" >我的订单</a></td>';
					htmlStr += '<td width="5%" class="move"><a onclick="userPhoneRecord('+item.uid+')">话费充值记录</a></td>';
					htmlStr += '<td width="20%">';
					htmlStr += '<button class="btn btn-primary btn-sm" onclick="detailUserInfo('+item.uid+')">详情</button>'
					if(item.status == 0){
						htmlStr += '<button class="btn btn-danger btn-sm" onclick="setUserStatus('+item.uid+',1)">冻结</button>'
					}else{
						htmlStr += '<button class="btn btn-warning btn-sm" onclick="setUserStatus('+item.uid+',4)">解冻</button>'
					}
					if(item.isManager == 0){
						htmlStr += '<button class="btn btn-danger btn-sm" onclick="setUserStatus('+item.uid+',2)">升为管理员</button>'
					}else{
						htmlStr += '<button class="btn btn-warning btn-sm" onclick="setUserStatus('+item.uid+',5)">取消管理员</button>'
					}
					if(item.isVip == 0){
						htmlStr += '<button class="btn btn-danger btn-sm" onclick="setUserStatus('+item.uid+',3)">手动添加VIP</button>'
					}else{
						htmlStr += '<button class="btn btn-warning btn-sm" onclick="setUserStatus('+item.uid+',6)">取消VIP</button>'
					}
					htmlStr += '</td>'
					htmlStr += '</tr>';
				});
				$("#tbody").html(htmlStr);
				if(null != data.count){
					$("#countTotalMoney").text(data.count.totalMoney+"(元)");
					$("#countTotalCardMoney").text(data.count.totalCardMoney+"(元)");
					$("#countTotalCommission").text(data.count.totalCommission+"(元)");
					$("#countUserAmount").text(data.count.vipNum+"人/"+data.count.amount+"人");
				}
			}
			var options={
		            bootstrapMajorVersion:1,    //版本
		            currentPage:pageNum,    //当前页数
		            numberOfPages:5,    //最多显示Page页
			        totalPages:pages,    //所有数据可以显示的页数
		            onPageClicked:function(e,originalEvent,type,page){
		            	addHtml(page);
		            }
		        }
			if(pages >0){
				$("#page").bootstrapPaginator(options);
				$("#page").show();
			}else{
				$("#page").hide();
			}
		}
	});
}


function setUserStatus(uid,type){
		var msg = "您真的确定要";
		var s= "";
		var data ="";
		if(type == 1){
			s = "冻结当前用户";
			data = {
		    		 "uid":uid,"status":1
		    	}
		}else if(type == 2){
			s = "升为管理员";			
			data = {
					"uid":uid,"isManager":1
			}
		}else if(type == 3){
			s = "手动添加VIP";			
			data = {
					"uid":uid,"isVip":1
			}
		}else if(type == 4){
			s = "解冻当前用户";			
			data = {
					"uid":uid,"status":0
			}
		}else if(type == 5){
			s = "取消管理员";			
			data = {
					"uid":uid,"isManager":0
			}
		}else if(type == 6){
			s = "取消VIP";			
			data = {
					"uid":uid,"isVip":0
			}
		}
		msg = msg+s+"吗？\n\n请确认！";
		if (confirm(msg) == true) {
			$.ajax({
				type : "post",
				data : JSON.stringify(data),
				url : "/user/setUserStatus",
				dataType : "json",
				contentType : 'application/json',
				success : function(data) {
					if(null != data && data.code == 1000){
						alert(s+data.msg);
						window.location.href = "/user/toUserList?pageSize=10&pageNum=1";
					}
				}
			});
			return true;
		} else {
			return false;
		}
	}


function detailUserInfo(uid){
	var data = {
			"uid":uid
	}
	$.ajax({
		type : "post",
		data : JSON.stringify(data),
		url : "/user/getUserInfo",
		dataType : "json",
		contentType : 'application/json',
		success : function(data) {
			if(null != data && data.code == 1000){
				$('#detailModal').modal('toggle');
				var user = data.user;
				if(null != user){
					$("#uidD").val(user.uid);
					$("#phoneD").val(user.phone);
					$("#inviterUidD").val(user.inviterUid);
					$("#inviterPhoneD").val(user.inviterPhone);
					$("#moneyD").val(user.money);
					$("#cardMoneyD").val(user.cardMoney);
					$("#commissionD").val(user.commission);
					$("#imgUrlD").val(user.imgUrl);
					if(null != user.isManager){
						$("#isManagerD").val(user.isManager==0?"普通用户":"管理员");
					}
					if(null != user.isVip){
						$("#isVipD").val(user.isVip==0?"普通用户":"VIP用户");
					}
					if(null != user.img){
						$("#imgD").attr("src",user.img);
					}
				}
				
				var bank = data.bank;
				if(null != bank){
					$("#bankNameD").val(bank.bankName);
					$("#bankNumD").val(bank.bankNum);
					$("#userNameD").val(bank.userName);
					$("#phoneD1").val(bank.phone);
					$("#bankAdressD").val(bank.bankAdress);
				}
				var address = data.address;
				if(null != address){
					$("#userNameDD").val(address.userName);
					$("#phoneDD").val(address.phone);
					$("#postCodeDD").val(address.postCode);
					$("#addressDD").val(address.address);
				}
			}
		}
	});
}


function userRecord(uid,type){
	var data = {
			"uid":uid,"type":type
		}
	
	if(type == 2){
		$(".modal-title-zz-yj").text("转账记录");
		$(".daoli").text("稻粒");
	}else{
		$(".modal-title-zz-yj").text("佣金记录");
		$(".daoli").text("佣金");
	}
	$.ajax({
		type : "post",
		data : JSON.stringify(data),
		url : "/user/listUserRecord",
		dataType : "json",
			contentType : 'application/json',
		success : function(data) {
			$("#zzjl_tbody").html("");
			var htmlStr="";
			var total =0;
			$.each(data, function (index, item) {
				var amount = (item.amount!=null&&item.amount!=0)?item.amount:"0.00";
				total = total+amount;
				var type = item.payType ==1?"收入":"支出";
				htmlStr += "<tr>";
				htmlStr += '<td width="3%">'+type+'</td>';
				htmlStr += '<td width="10%">'+item.description+'</td>';
				if(amount>0){
					htmlStr += '<td width="4%">+'+amount+'</td>';
				}else{
					htmlStr += '<td width="4%">'+amount+'</td>';
				}
				htmlStr += '<td width="5%">'+item.createTime+'</td>';
				htmlStr += "</tr>";
			});
			htmlStr += '<tr> <td align="left" valign="middle" colspan="2">总金额</td> <td align="left" valign="middle" colspan="2">'+total+'元</td> </tr>'
			$("#zzjl_tbody").html(htmlStr);
			$('#zzjl_Modal').modal('toggle');
		}
	})
}

function userCash(uid){
	var data = {
			"uid":uid
	}
	
	$.ajax({
		type : "post",
		data : JSON.stringify(data),
		url : "/user/listUserCash",
		dataType : "json",
		contentType : 'application/json',
		success : function(data) {
			$('#txjl_Modal').modal('toggle');
			$("#txjl_tbody").html("");
			var htmlStr="";
			var total =0;
			$.each(data, function (index, item) {
				var amount = (item.amount!=null&&item.amount!=0)?item.amount:"0.00";
				total = total+amount;
				var status = item.status == 1?"未审核":"已通过";
				htmlStr += "<tr>";
				htmlStr += '<td width="5%">'+item.applyTime+'</td>';
				htmlStr += '<td width="4%">'+amount+'</td>';
				htmlStr += '<td width="3%">'+status+'</td>';
				htmlStr += "</tr>";
			});
			htmlStr += '<tr> <td align="left" valign="middle">总金额</td> <td align="left" valign="middle" colspan="2">'+total+'元</td> </tr>'
			$("#txjl_tbody").html(htmlStr);
		}
	})
}


function userInviter(uid,num){
	var data = {
			"uid":uid
	}
	if(num <4){
		num = num+1;
		$.ajax({
			type : "post",
			data : JSON.stringify(data),
			url : "/user/listUserInviter",
			dataType : "json",
			contentType : 'application/json',
			success : function(data) {
				if(num == 2){
					$('#txl_Modal').modal('toggle');
				}
				$("#txl_tbody").html("");
				var htmlStr="";
				var total =0;
				$.each(data, function (index, item) {
					var commission = (item.commission!=null&&item.commission!=0)?item.commission:"0.00";
					total = total+1;
					htmlStr += "<tr>";
					htmlStr += '<td width="5%"><img src="'+item.img+'" height="60px" width="60px" onclick="userInviter('+item.invitedUid+','+num+')" ></td>';
					htmlStr += '<td width="5%">账号 : '+item.phone+'</br>注册时间: '+item.createTime+'</br>佣金: '+commission+'(元)</td>';
					htmlStr += '<td width="3%">'+item.isVip+'</td>';
					htmlStr += "</tr>";
				});
				$(".modal-title_fs").text("粉丝:"+total+"人数");
				$("#txl_tbody").html(htmlStr);
			}
		})
		
	}
}


function userBill(uid){
	var data = {
			"uid":uid
	}
	
	$.ajax({
		type : "post",
		data : JSON.stringify(data),
		url : "/user/listUserBill",
		dataType : "json",
		contentType : 'application/json',
		success : function(data) {
			$('#zd_Modal').modal('toggle');
			$("#zd_tbody").html("");
			var htmlStr="";
			var total =0;
			$.each(data, function (index, item) {
				var amount = (item.amount!=null&&item.amount!=0)?item.amount:"0.00";
				total = total+amount;
				htmlStr += "<tr>";
				htmlStr += '<td width="5%">'+item.time+'</td>';
				htmlStr += '<td width="4%">'+item.totalDesc+'</td>';
				htmlStr += '<td width="3%">'+item.description+'</td>';
				htmlStr += "</tr>";
			});
			htmlStr += '<tr> <td align="left" valign="middle">总金额</td> <td align="left" valign="middle" colspan="2">'+total+'元</td> </tr>'
			$("#zd_tbody").html(htmlStr);
		}
	})
}


function userPhoneRecord(uid){
	var data = {
			"uid":uid
	}
	
	$.ajax({
		type : "post",
		data : JSON.stringify(data),
		url : "/user/listUserPhoneRecord",
		dataType : "json",
		contentType : 'application/json',
		success : function(data) {
			$('#hf_Modal').modal('toggle');
			$("#hf_tbody").html("");
			var htmlStr="";
			var total =0;
			$.each(data, function (index, item) {
				var amount = (item.totalMoney!=null&&item.totalMoney!=0)?item.totalMoney:"0.00";
				total = total+amount;
				htmlStr += "<tr>";
				htmlStr += '<td width="5%">'+item.createTime+'</td>';
				htmlStr += '<td width="5%">'+item.phone+'</td>';
				htmlStr += '<td width="4%">'+item.totalMoney+'</td>';
				htmlStr += '<td width="4%">'+item.discountMoney+'</td>';
				htmlStr += '<td width="3%">'+item.status+'</td>';
				htmlStr += "</tr>";
			});
			htmlStr += '<tr> <td align="left" valign="middle">总金额</td> <td align="left" valign="middle" colspan="2">'+total+'元</td> </tr>'
			$("#hf_tbody").html(htmlStr);
		}
	})
}


function userOrder(uid){
	var data = {
			"uid":uid
	}
	
	$.ajax({
		type : "post",
		data : JSON.stringify(data),
		url : "/user/listUserOrderInfo",
		dataType : "json",
		contentType : 'application/json',
		success : function(data) {
			$('#dd_Modal').modal('toggle');
			$("#dd_tbody").html("");
			var htmlStr="";
			var total =0;
			$.each(data, function (index, item) {
				var amount = (item.totalMoney!=null&&item.totalMoney!=0)?item.totalMoney:"0.00";
				total = total+amount;
				htmlStr += "<tr class=info>";
				htmlStr += '<td width="5%">订单id:'+item.orderId+'</td>';
				htmlStr += '<td width="5%">创建时间:'+item.createTime+'</td>';
				htmlStr += '<td width="4%">状态:'+item.statusStr+'</td>';
				htmlStr += "</tr>";
				var num=0;
				var discount = (item.discount!= null&&item.discount!=0)?(item.discount)*100:"暂无";
				$.each(item.products, function (index, product) {
					htmlStr += "<tr>";
					htmlStr += '<td width="5%"><img src="'+product.img+'" height="60px" width="60px"></td>';
					htmlStr += '<td width="5%">'+product.productName+'</td>';
					htmlStr += '<td width="4%">￥'+product.productPrice+'&nbsp;&nbsp;X&nbsp;&nbsp;'+product.amount+'<br/>总价格:&nbsp;&nbsp;'+product.total+'(元)</td>';
					htmlStr += "</tr>";
					num++;
				});
				htmlStr += '<tr><td align="left" valign="middle" colspan="3">共计&nbsp;&nbsp;'+num+'&nbsp;&nbsp;件商品合计:&nbsp;&nbsp;￥'+item.totalMoney+'<br/>享受&nbsp;&nbsp;'+discount+'&nbsp;&nbsp;折扣;&nbsp;&nbsp;&nbsp;&nbsp;折后价:&nbsp;&nbsp;￥'+item.discountMoney+'&nbsp;&nbsp;&nbsp;&nbsp;运费:&nbsp;&nbsp;￥'+item.postage+'</td></tr>'
			});
			$("#dd_tbody").html(htmlStr);
		}
	})
}


