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
	
	
	///table 页面切换的效果
	$('#myTab a').click(function (e) {
		  e.preventDefault()
		  $(this).tab('show')
	})
})


function addHtml(pageNum){
	var pageSize = $("#pageSize").val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var phone = $("#phone").val();
	var orderId = $("#orderId").val();
	var billType = $("#billType").val();
	var data = {
			"pageNum":pageNum,"pageSize":pageSize,"startDate":startDate,"endDate":endDate,
			"phone":phone,"orderId":orderId,"billType":billType,"type":1
	}
	$.ajax({
		type : "post",
		data : JSON.stringify(data),
		url : "/bill/dataList",
		dataType : "json",
		contentType : 'application/json',
		success : function(data) {
			if(data != null&& null != data.page){
				$("#tbody").html("");
				var htmlStr="";
				$.each(data.page.list, function (index, item) {
					var billType = item.billType==1?"VIP账单":(item.billType==2?"稻粒":(item.billType==3?"话费充值":"购物消费"))
					var amount =item.amount!=null?item.amount:"0";
					var payType = item.payType == 3?"支付宝":"微信";
					htmlStr += "<tr>";
					htmlStr += '<td width="4%">'+item.uid+'</td>';
					htmlStr += '<td width="5%">'+item.phone+'</td>';
					htmlStr += '<td width="5%">'+billType+'</td>';
					htmlStr += '<td width="5%">'+payType+'</td>';
					htmlStr += '<td width="5%">'+item.orderId+'</td>';
					htmlStr += '<td width="5%">'+amount+'</td>';
					htmlStr += '<td width="7%">'+item.description+'</td>';
					htmlStr += '<td width="8%">'+timeStamp2String(item.createTime)+'</td>';
					htmlStr += '<td width="20%">';
					htmlStr += '<button class="btn btn-primary btn-sm" onclick="detailBill('+item.id+')">详情</button>'
					htmlStr += '</td>'
					htmlStr += '</tr>';
				});
				$("#tbody").html(htmlStr);
				if(null != data.amount){
					$("#totalAmount").text(data.amount+"(元)");
				}else{
					$("#totalAmount").text("0(元)");
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
	var billType = $("#billType").val();
	var data = {
			"pageNum":pageNum,"pageSize":pageSize,"startDate":startDate,"endDate":endDate,
			"phone":phone,"orderId":orderId,"billType":billType,"type":1
	}
	
	$.ajax({
		type : "post",
		data : JSON.stringify(data),
		url : "/bill/dataList",
		dataType : "json",
		contentType : 'application/json',
		success : function(data) {
			if(data != null && null != data.page){
				pageNum = data.page.pageNum
				pages = data.page.pages;
				$("#tbody").html("");
				var htmlStr="";
				$.each(data.page.list, function (index, item) {
					var billType = item.billType==1?"VIP账单":(item.billType==2?"稻粒":(item.billType==3?"话费充值":"购物消费"))
					var amount =item.amount!=null?item.amount:"0";
					var payType = item.payType == 3?"支付宝":"微信";
					htmlStr += "<tr>";
					htmlStr += '<td width="4%">'+item.uid+'</td>';
					htmlStr += '<td width="5%">'+item.phone+'</td>';
					htmlStr += '<td width="5%">'+billType+'</td>';
					htmlStr += '<td width="5%">'+payType+'</td>';
					htmlStr += '<td width="5%">'+item.orderId+'</td>';
					htmlStr += '<td width="5%">'+amount+'</td>';
					htmlStr += '<td width="7%">'+item.description+'</td>';
					htmlStr += '<td width="8%">'+timeStamp2String(item.createTime)+'</td>';
					htmlStr += '<td width="20%">';
					htmlStr += '<button class="btn btn-primary btn-sm" onclick="detailBill('+item.id+')">详情</button>'
					htmlStr += '</td>'
					htmlStr += '</tr>';
				});
				$("#tbody").html(htmlStr);
				if(null != data.amount){
					$("#totalAmount").text(data.amount+"(元)");
				}else{
					$("#totalAmount").text("0(元)");
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



////////////////切换table 到出账页面

$(function(){
	var pages = $("#pagesc").val();
	var pageNum = $("#pageNumc").val();
	var pageSize = $("#pageSizec").val();
	var options={
            bootstrapMajorVersion:1,    //版本
            currentPage:pageNum,    //当前页数
            numberOfPages:5,    //最多显示Page页
            totalPages:pages,    //所有数据可以显示的页数
            onPageClicked:function(e,originalEvent,type,page){
            	addHtmlc(page);
            }
        }
        $("#pagec").bootstrapPaginator(options);
	
})



function addHtmlc(pageNum){
	var pageSize = $("#pageSizec").val();
	var startDate = $("#startDatec").val();
	var endDate = $("#endDatec").val();
	var phone = $("#phonec").val();
	var orderId = $("#orderIdc").val();
	var billType = $("#billTypec").val();
	var data = {
			"pageNum":pageNum,"pageSize":pageSize,"startDate":startDate,"endDate":endDate,
			"phone":phone,"orderId":orderId,"billType":billType,"type":2
	}
	$.ajax({
		type : "post",
		data : JSON.stringify(data),
		url : "/bill/dataList",
		dataType : "json",
		contentType : 'application/json',
		success : function(data) {
			if(data != null&& null != data.page){
				$("#tbodyc").html("");
				var htmlStr="";
				$.each(data.page.list, function (index, item) {
					var billType = item.billType==3?"话费充值":(item.billType==4?"商品进价":"体现账单")
					var amount =item.amount!=null?item.amount:"0";
					htmlStr += "<tr>";
					htmlStr += '<td width="4%">'+item.uid+'</td>';
					htmlStr += '<td width="5%">'+item.phone+'</td>';
					htmlStr += '<td width="5%">'+billType+'</td>';
					htmlStr += '<td width="5%">'+item.orderId+'</td>';
					htmlStr += '<td width="5%">'+amount+'</td>';
					htmlStr += '<td width="7%">'+item.description+'</td>';
					htmlStr += '<td width="8%">'+timeStamp2String(item.createTime)+'</td>';
					htmlStr += '<td width="20%">';
					htmlStr += '<button class="btn btn-primary btn-sm" onclick="detailBill('+item.id+')">详情</button>'
					htmlStr += '</td>'
					htmlStr += '</tr>';
				});
				$("#tbodyc").html(htmlStr);
				if(null != data.amount){
					$("#totalAmountc").text(data.amount+"(元)");
				}else{
					$("#totalAmountc").text("0(元)");
				}
			}
		}
	});
}


function searchc(){
	var pages = 0;
	var pageNum = 1
	var pageSize = $("#pageSizec").val();
	var startDate = $("#startDatec").val();
	var endDate = $("#endDatec").val();
	var phone = $("#phonec").val();
	var orderId = $("#orderIdc").val();
	var billType = $("#billTypec").val();
	var data = {
			"pageNum":pageNum,"pageSize":pageSize,"startDate":startDate,"endDate":endDate,
			"phone":phone,"orderId":orderId,"billType":billType,"type":2
	}
	
	$.ajax({
		type : "post",
		data : JSON.stringify(data),
		url : "/bill/dataList",
		dataType : "json",
		contentType : 'application/json',
		success : function(data) {
			if(data != null && null != data.page){
				pageNum = data.page.pageNum
				pages = data.page.pages;
				$("#tbodyc").html("");
				var htmlStr="";
				$.each(data.page.list, function (index, item) {
					var billType = item.billType==3?"话费充值":(item.billType==4?"商品进价":"体现账单")
					var amount =item.amount!=null?item.amount:"0";
					htmlStr += "<tr>";
					htmlStr += '<td width="4%">'+item.uid+'</td>';
					htmlStr += '<td width="5%">'+item.phone+'</td>';
					htmlStr += '<td width="5%">'+billType+'</td>';
					htmlStr += '<td width="5%">'+item.orderId+'</td>';
					htmlStr += '<td width="5%">'+amount+'</td>';
					htmlStr += '<td width="7%">'+item.description+'</td>';
					htmlStr += '<td width="8%">'+timeStamp2String(item.createTime)+'</td>';
					htmlStr += '<td width="20%">';
					htmlStr += '<button class="btn btn-primary btn-sm" onclick="detailBill('+item.id+')">详情</button>'
					htmlStr += '</td>'
					htmlStr += '</tr>';
				});
				$("#tbodyc").html(htmlStr);
				if(null != data.amount){
					$("#totalAmountc").text(data.amount+"(元)");
				}else{
					$("#totalAmountc").text("0(元)");
				}
			}
			var options={
		            bootstrapMajorVersion:1,    //版本
		            currentPage:pageNum,    //当前页数
		            numberOfPages:5,    //最多显示Page页
			        totalPages:pages,    //所有数据可以显示的页数
		            onPageClicked:function(e,originalEvent,type,page){
		            	addHtmlc(page);
		            }
		        }
			if(pages >0){
				$("#pagec").bootstrapPaginator(options);
				$("#pagec").show();
			}else{
				$("#pagec").hide();
			}
		}
	});
}

///////////////////////////////////

function detailBill(id){
	var data = {
			"id":id
	}
	$.ajax({
		type : "post",
		data : JSON.stringify(data),
		url : "/bill/getEntityById",
		dataType : "json",
		contentType : 'application/json',
		success : function(data) {
			if(null != data && data.code == 1000){
				$('#detailModal').modal('toggle');
				var bill = data.bill;
				if(null != bill){
					$("#uidD").val(bill.uid);
					$("#phoneD").val(bill.phone);
					$("#typeD").val(bill.type==1?"进账":"出账");
					$("#billTypeD").val(bill.billType==1?"VIP账单":(bill.billType==2?"稻粒":(bill.billType==3?"话费充值":((bill.billType==4&&bill.type==1)?"购物消费":(bill.billType==4&&bill.type==2)?"商品进价":"体现账单"))));
					$("#orderIdD").val(bill.orderId);
					$("#amountD").val(bill.amount);
					$("#descriptionD").val(bill.description);
					$("#postageD").val(bill.postage);
					$("#discountD").val(bill.discount);
					$("#createTimeD").val(timeStamp2String(bill.createTime));
					if(bill.bills!= null){
						var htmlStr="";
						$.each(bill.bills, function (index, dto) {
							htmlStr += "<tr>";
							htmlStr += '<td width="5%">'+dto.productName+'</td>';
							htmlStr += '<td width="4%">'+dto.productType+'</td>';
							htmlStr += '<td width="3%">'+dto.price+'</td>';
							htmlStr += '<td width="3%">'+dto.amount+'</td>';
							htmlStr += '<td width="3%">'+dto.total+'</td>';
							htmlStr += '<td width="5%">'+dto.description+'</td>';
							htmlStr += "</tr>";
						});
						$("#zd_tbody").html(htmlStr);
					}
				}
			}
		}
	});																																																																												
}
