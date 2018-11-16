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
	
})


function addHtml(pageNum){
	var pageSize = $("#pageSize").val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var phone = $("#phone").val();
	var orderId = $("#orderId").val();
	var status = $("#status").val();
	var isSend = $("#isSend").val();
	var data = {
			"pageNum":pageNum,"pageSize":pageSize,"startDate":startDate,"endDate":endDate,
			"phone":phone,"orderId":orderId,"status":status,"isSend":isSend
	}
	
	$.ajax({
		type : "post",
		data : JSON.stringify(data),
		url : "/order/dataList",
		dataType : "json",
		contentType : 'application/json',
		success : function(data) {
			if(data != null&& null != data.page){
				$("#tbody").html("");
				var htmlStr="";
				$.each(data.page.list, function (index, order) {
					var num = order.products.length;
					var discount = order.discount!=null?order.discount:"暂无";
					var discountMoney = order.discountMoney!=null?order.discountMoney:order.totalMoney;
					var payType = order.payType==1?"稻粒":(order.payType==3?"支付宝":"微信");
					var rmb =(order.rmb!=null&&order.rmb!=0)?order.rmb:"0";
					var cornMoney =(order.cornMoney!=null&&order.cornMoney!=0)?order.cornMoney:"0";
					var payTime = order.payTime != null ?timeStamp2String(order.payTime):"";
					var userName = order.userName != null ? order.userName:"";
					var phone = order.phone != null ? order.phone:"";
					var address = order.address != null ? order.address:"";
					var isSend = order.isSend == 1?"未发货":"已发货";
					htmlStr += "<tr>";
					htmlStr += '<td width="1%">'+order.orderId+'</td>';
					htmlStr += '<td width="1%">'+order.statusStr+'</td>';
					htmlStr += '<td width="1%">'+isSend+'</td>';
					htmlStr += '<td width="2%">'+timeStamp2String(order.createTime)+'</td>';
					htmlStr += '<td width="1%">'+order.amountPhone+'</td>';
					htmlStr += '<td width="1%">'+order.totalMoney+'</td>';
					htmlStr += '<td width="1%">'+order.postage+'</td>';
					htmlStr += '<td width="1%">'+order.totalPayable+'</td>';
					htmlStr += '<td width="1%">'+discount+'</td>';
					htmlStr += '<td width="1%">'+discountMoney+'</td>';
					htmlStr += '<td width="1%">'+order.totalPay+'</td>';
					htmlStr += '<td width="1%">'+payType+'</td>';
					htmlStr += '<td width="1%">'+rmb+'</td>';
					htmlStr += '<td width="1%">'+cornMoney+'</td>';
					htmlStr += '<td width="1%">'+payTime+'</td>';
					htmlStr += '<td width="1%">'+userName+'</td>';
					htmlStr += '<td width="1%">'+phone+'</td>';
					htmlStr += '<td width="3%">'+address+'</td>';
					htmlStr += '<td width="2%" >';
					htmlStr += '<button class="btn btn-primary btn-sm" onclick="detailOrder('+order.orderId+')">详情</button>';
					if(order.isSend == 1){
						htmlStr += '<button class="btn btn-primary btn-sm" onclick="isSend('+order.orderId+',2)">未发货</button>'
					}else{
						htmlStr += '<button class="btn btn-primary btn-sm" onclick="isSend('+order.orderId+',1)">已发货</button>'
					}
					htmlStr += '<button class="btn btn-danger btn-sm" onclick="deleteOrder('+order.orderId+')">删除</button>'
					htmlStr += '</td>';
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

function timeStamp2String(time){
    var datetime = new Date();
    datetime.setTime(time);
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
    var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
    var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
    return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second;
}

function search(){
	var pages = 0;
	var pageNum = 1
	var pageSize = $("#pageSize").val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var phone = $("#phone").val();
	var orderId = $("#orderId").val();
	var status = $("#status").val();
	var isSend = $("#isSend").val();
	var data = {
			"pageNum":pageNum,"pageSize":pageSize,"startDate":startDate,"endDate":endDate,
			"phone":phone,"orderId":orderId,"status":status,"isSend":isSend
	}
	
	$.ajax({
		type : "post",
		data : JSON.stringify(data),
		url : "/order/dataList",
		dataType : "json",
		contentType : 'application/json',
		success : function(data) {
			if(data != null && null != data.page){
				pageNum = data.page.pageNum
				pages = data.page.pages;
				$("#tbody").html("");
				var htmlStr="";
				$.each(data.page.list, function (index, order) {
					var num = order.products.length;
					var discount = order.discount!=null?order.discount:"暂无";
					var discountMoney = order.discountMoney!=null?order.discountMoney:order.totalMoney;
					var payType = order.payType==1?"稻粒":(order.payType==3?"支付宝":"微信");
					var rmb =(order.rmb!=null&&order.rmb!=0)?order.rmb:"0";
					var cornMoney =(order.cornMoney!=null&&order.cornMoney!=0)?order.cornMoney:"0";
					var payTime = order.payTime != null ?timeStamp2String(order.payTime):"";
					var userName = order.userName != null ? order.userName:"";
					var phone = order.phone != null ? order.phone:"";
					var address = order.address != null ? order.address:"";
					var isSend = order.isSend == 1?"未发货":"已发货";
					htmlStr += "<tr>";
					htmlStr += '<td width="1%">'+order.orderId+'</td>';
					htmlStr += '<td width="1%">'+order.statusStr+'</td>';
					htmlStr += '<td width="1%">'+isSend+'</td>';
					htmlStr += '<td width="2%">'+timeStamp2String(order.createTime)+'</td>';
					htmlStr += '<td width="1%">'+order.amountPhone+'</td>';
					htmlStr += '<td width="1%">'+order.totalMoney+'</td>';
					htmlStr += '<td width="1%">'+order.postage+'</td>';
					htmlStr += '<td width="1%">'+order.totalPayable+'</td>';
					htmlStr += '<td width="1%">'+discount+'</td>';
					htmlStr += '<td width="1%">'+discountMoney+'</td>';
					htmlStr += '<td width="1%">'+order.totalPay+'</td>';
					htmlStr += '<td width="1%">'+payType+'</td>';
					htmlStr += '<td width="1%">'+rmb+'</td>';
					htmlStr += '<td width="1%">'+cornMoney+'</td>';
					htmlStr += '<td width="1%">'+payTime+'</td>';
					htmlStr += '<td width="1%">'+userName+'</td>';
					htmlStr += '<td width="1%">'+phone+'</td>';
					htmlStr += '<td width="3%">'+address+'</td>';
					htmlStr += '<td width="2%" >';
					htmlStr += '<button class="btn btn-primary btn-sm" onclick="detailOrder('+order.orderId+')">详情</button>';
					if(order.isSend == 1){
						htmlStr += '<button class="btn btn-primary btn-sm" onclick="isSend('+order.orderId+',2)">未发货</button>'
					}else{
						htmlStr += '<button class="btn btn-primary btn-sm" onclick="isSend('+order.orderId+',1)">已发货</button>'
					}
					htmlStr += '<button class="btn btn-danger btn-sm" onclick="deleteOrder('+order.orderId+')">删除</button>'
					htmlStr += '</td>';
					htmlStr += '</tr>';
				});
				$("#tbody").html(htmlStr);
				if(null != data.count){
					$("#countTotalMoney").text(data.count.totalMoney+"(元)");
					$("#countTotalPay").text(data.count.totalPay+"(元)");
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


function detailOrder(orderId){
	var data = {
			"orderId":orderId
	}
	$.ajax({
		type : "post",
		data : JSON.stringify(data),
		url : "/order/getEntityById",
		dataType : "json",
		contentType : 'application/json',
		success : function(data) {
			if(null != data && data.code == 1000){
				$('#detailModal').modal('toggle');
				var order = data.order;
				if(null != order){
					if(order.products!=null){
						var total = 0;
						var htmlStr="";
						$.each(order.products, function (index, product) {
							total = total + product.total;
							htmlStr += "<tr>";
							htmlStr += '<td width="3%" >'+product.productName+'</td>';
							htmlStr += '<td width="1%" ><img src="'+product.img+'" height="40px" width="40px"></td>';
							htmlStr += '<td width="1%" >'+product.productPrice+'</td>';
							htmlStr += '<td width="1%" >'+product.amount+'</td>';
							htmlStr += '<td width="1%" >'+product.total+'</td>';
							htmlStr += '</tr>';
						});
						htmlStr += '<tr> <td align="left" valign="middle">统计</td> <td></td> <td></td><td></td><td align="left" valign="middle">'+total+'元</td> </tr>'
						$("#txl_tbody").html(htmlStr);	
					}
				}
			}
		}
	});																																																																												
}






function deleteOrder(orderId) {
	var msg = "您真的确定要删除当前订单吗?,该操作不可恢复的,\n\n请确认";
	if (confirm(msg) == true) {
		var data = {
			"orderId" : orderId
		}
		$.ajax({
					type : "post",
					data : JSON.stringify(data),
					url : "/order/deleteByOrderId",
					dataType : "json",
					contentType : 'application/json',
					success : function(data) {
						if (null != data && data.code == 1000) {
							alert(data.msg);
							window.location.href = "/order/toOrderList?pageSize=10&pageNum=1";
						}
					}
				});
		return true;
	} else {
		return false;
	}
}

function isSend(orderId,isSend) {
	var msg = "您真的确定设置该订单为已发货吗?,\n\n请确认";
	if(isSend == 1){
		msg = "您真的确定设置该订单为未发货吗?,\n\n请确认";
	}
	if (confirm(msg) == true) {
		var data = {
			"orderId" : orderId,"isSend":isSend
		}
		$.ajax({
				type : "post",
				data : JSON.stringify(data),
				url : "/order/isSend",
				dataType : "json",
				contentType : 'application/json',
				success : function(data) {
					if (null != data && data.code == 1000) {
						alert(data.msg);
						window.location.href = "/order/toOrderList?pageSize=10&pageNum=1";
					}
				}
			});
		return true;
	} else {
		return false;
	}
}
