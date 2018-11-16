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
	var phone = $("#phone").val();
	var status = $("#status").val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var data = {
			"pageNum":pageNum,"pageSize":pageSize,"phone":phone,"status":status
	}
	
	$.ajax({
		type : "post",
		data : JSON.stringify(data),
		url : "/user/pageUserCash",
		dataType : "json",
		contentType : 'application/json',
		success : function(data) {
			if(data != null){
				$("#tbody").html("");
				var htmlStr="";
				$.each(data.list, function (index, item) {
					var amount =(item.amount!=null&&item.amount!=0)?item.amount:"0.00"
					var feeAmount =(item.feeAmount!=null&&item.feeAmount!=0)?item.feeAmount:"0.00"
					var realAmount =(item.realAmount!=null&&item.realAmount!=0)?item.realAmount:"0.00"
					var status = item.status==0?"未审核":"提现完成";
					htmlStr += "<tr>";
					htmlStr += '<td width="2%">'+item.id+'</td>';
					htmlStr += '<td width="4%">'+item.uid+'</td>';
					htmlStr += '<td width="4%">'+item.accountPhone+'</td>';
					htmlStr += '<td width="4%">'+amount+'</td>';
					htmlStr += '<td width="4%">'+feeAmount+'</td>';
					htmlStr += '<td width="4%">'+realAmount+'</td>';
					htmlStr += '<td width="4%">'+item.bankName+'</td>';
					htmlStr += '<td width="4%">'+item.bankNum+'</td>';
					htmlStr += '<td width="4%">'+item.userName+'</td>';
					htmlStr += '<td width="4%">'+item.phone+'</td>';
					htmlStr += '<td width="8%">'+item.bankAdress+'</td>';
					htmlStr += '<td width="4%">'+status+'</td>';
					htmlStr += '<td width="5%">'+item.applyTimeStr+'</td>';
					htmlStr += '<td width="10%">';
					if(item.status==0){
						htmlStr += '<button class="btn btn-primary btn-sm" onclick="audit('+item.id+',1)">提现</button>'
					}
					htmlStr += '</td>'
					htmlStr += '</tr>';
				});
				$("#tbody").html(htmlStr);
			}
		}
	});
}

function search(){
	var pages = 0;
	var pageNum = 1
	var pageSize = $("#pageSize").val();
	var phone = $("#phone").val();
	var status = $("#status").val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var data = {
			"pageNum":pageNum,"pageSize":pageSize,"phone":phone,"status":status
	}
	
	$.ajax({
		type : "post",
		data : JSON.stringify(data),
		url : "/user/pageUserCash",
		dataType : "json",
		contentType : 'application/json',
		success : function(data) {
			if(data != null){
				pageNum = data.pageNum
				pages = data.pages;
				$("#tbody").html("");
				var htmlStr="";
				$.each(data.list, function (index, item) {
					var amount =(item.amount!=null&&item.amount!=0)?item.amount:"0.00"
					var feeAmount =(item.feeAmount!=null&&item.feeAmount!=0)?item.feeAmount:"0.00"
					var realAmount =(item.realAmount!=null&&item.realAmount!=0)?item.realAmount:"0.00"
					var status = item.status==0?"未审核":"提现完成";
					htmlStr += "<tr>";
					htmlStr += '<td width="2%">'+item.id+'</td>';
					htmlStr += '<td width="4%">'+item.uid+'</td>';
					htmlStr += '<td width="4%">'+item.accountPhone+'</td>';
					htmlStr += '<td width="4%">'+amount+'</td>';
					htmlStr += '<td width="4%">'+feeAmount+'</td>';
					htmlStr += '<td width="4%">'+realAmount+'</td>';
					htmlStr += '<td width="4%">'+item.bankName+'</td>';
					htmlStr += '<td width="4%">'+item.bankNum+'</td>';
					htmlStr += '<td width="4%">'+item.userName+'</td>';
					htmlStr += '<td width="4%">'+item.phone+'</td>';
					htmlStr += '<td width="8%">'+item.bankAdress+'</td>';
					htmlStr += '<td width="4%">'+status+'</td>';
					htmlStr += '<td width="5%">'+item.applyTimeStr+'</td>';
					htmlStr += '<td width="10%">';
					if(item.status==0){
						htmlStr += '<button class="btn btn-primary btn-sm" onclick="audit('+item.id+',1)">提现</button>'
					}
					htmlStr += '</td>'
					htmlStr += '</tr>';
				});
				$("#tbody").html(htmlStr);
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


function audit(id){
		var msg = "您确定该提现记录金额已发放了吗？\n\n请确认！";
		var data={"id":id}
		if (confirm(msg) == true) {
			$.ajax({
				type : "post",
				data : JSON.stringify(data),
				url : "/user/auditUserCash",
				dataType : "json",
				contentType : 'application/json',
				success : function(data) {
					if(null != data && data.code == 1000){
						alert(data.msg);
						window.location.href = "/user/toUserCash?pageSize=10&pageNum=1";
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