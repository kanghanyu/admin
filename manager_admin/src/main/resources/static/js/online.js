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
		var title = $("#title").val();
		var data = {
    		 "pageNum":pageNum,"pageSize":pageSize,"title":title
    	}
		$.ajax({
    		type : "post",
    		data : JSON.stringify(data),
    		url : "/online/dataList",
    		dataType : "json",
    		contentType : 'application/json',
    		success : function(data) {
    			if(data != null){
    				$("#tbody").html("");
    				var htmlStr="";
    				 $.each(data.list, function (index, item) {
    					 var content = item.content!=null?item.content:"";
    					 if(content.length>50){
    						 content = content.substring(0,50)+"...";
    					 }
    					 htmlStr += "<tr>";
    					 htmlStr += '<td width="3%">'+item.id+'</td>';
    					 htmlStr += '<td width="9%">'+item.title+'</td>';
    					 htmlStr += '<td width="20%">'+item.description+'</td>';
    				     htmlStr += '<td width="20%">'+content+'</td>';
    				     htmlStr += '<td width="7%">'+item.createTimeStr+'</td>';
    					 htmlStr += '<td width="30%">';
    					 htmlStr += '<button class="btn btn-primary btn-sm" onclick="detail(\''+item.title+'\')"> <i class="glyphicon glyphicon-edit">详情</i></button> '
    					 htmlStr += '<button class="btn btn-primary btn-sm" onclick="edit(\''+item.title+'\')"> <i class="glyphicon glyphicon-edit">修改</i></button> '
    					 htmlStr += '<button class="btn btn-info btn-sm" onclick="del(\''+item.title+'\')">删除</button>'
    					 htmlStr += '</td>';
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
	var title = $("#title").val();
	var data = {
		 "pageNum":pageNum,"pageSize":pageSize,"title":title
	}
	$.ajax({
		type : "post",
		data : JSON.stringify(data),
		url : "/online/dataList",
		dataType : "json",
		contentType : 'application/json',
		success : function(data) {
			if(data != null){
				pageNum = data.pageNum
				pages = data.pages;
				$("#tbody").html("");
				var htmlStr="";
				 $.each(data.list, function (index, item) {
					 var content = item.content!=null?item.content:"";
					 if(content.length>50){
						 content = content.substring(0,50)+"...";
					 }
					 htmlStr += "<tr>";
					 htmlStr += '<td width="3%">'+item.id+'</td>';
					 htmlStr += '<td width="9%">'+item.title+'</td>';
					 htmlStr += '<td width="20%">'+item.description+'</td>';
				     htmlStr += '<td width="20%">'+content+'</td>';
				     htmlStr += '<td width="7%">'+item.createTimeStr+'</td>';
					 htmlStr += '<td width="30%">';
						 htmlStr += '<button class="btn btn-primary btn-sm" onclick="detail(\''+item.title+'\')"> <i class="glyphicon glyphicon-edit">详情</i></button> '
					 htmlStr += '<button class="btn btn-primary btn-sm" onclick="edit(\''+item.title+'\')"> <i class="glyphicon glyphicon-edit">修改</i></button> '
					 htmlStr += '<button class="btn btn-info btn-sm" onclick="del(\''+item.title+'\')">删除</button>'
					 htmlStr += '</td>';
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


function add(){
	$('#addProduct').modal('toggle');
}

function save(){
	var title = $("#titleSave").val();
	var content = $("#contentSave").val();
	var description = $("#descriptionSave").val();
	if(null == title || title == ''){
		alert("在线参数的title不能为空");
		return false;
	}
	if(null == content || content == ''){
		alert("在线参数的content不能为空");
		return false;
	}
	if(null == description || description == ''){
		alert("在线参数的描述不能为空");
		return false;
	}
	var data = {
			 "title":title,"content":content,"description":description
		}
	$.ajax({
		type : "post",
		data : JSON.stringify(data),
		url : "/online/saveOnlineParame",
		dataType : "json",
		contentType : 'application/json',
		success : function(data) {
			if(null != data && data.code == 1000){
				 window.location.href = "/online/toOnline?pageSize=10&pageNum=1"; 
			}else{
				alert(data.msg);
				return false;
			}
		}
	});
}


function edit(title){
	if(null == title || title == ''){
		alert("在线参数的title不能为空");
		return false;
	}
	var data = {
			 "title":title
		}
	$.ajax({
		type : "post",
		data : JSON.stringify(data),
		url : "/online/findByKey",
		dataType : "json",
		contentType : 'application/json',
		success : function(data) {
			if(null != data && data.code == 1000){
				$('#editProduct').modal('toggle');
				$("#titleE").val(data.entity.title);
				$("#descriptionE").val(data.entity.description);
				$("#contentE").val(data.entity.content);
			}else{
				alert(data.msg);
				return false;
			}
		}
	});
	
	
}

function update(){
	var title = $("#titleE").val();
	var content = $("#contentE").val();
	var description = $("#descriptionE").val();
	if(null == title || title == ''){
		alert("在线参数的title不能为空");
		return false;
	}
	if(null == content || content == ''){
		alert("在线参数的content不能为空");
		return false;
	}
	var msg = "修改在线参数，它会对所有用户或资金造成巨大影响，您真的确定要修改这条数据吗？？\n\n请确认！";
	if (confirm(msg) == true) {
		var data = {
				 "title":title,"content":content,"description":description
			}
		$.ajax({
			type : "post",
			data : JSON.stringify(data),
			url : "/online/updateOnlineParame",
			dataType : "json",
			contentType : 'application/json',
			success : function(data) {
				if(null != data && data.code == 1000){
					 window.location.href = "/online/toOnline?pageSize=10&pageNum=1"; 
				}else{
					alert(data.msg);
					return false;
				}
			}
		});
		return true;
	}else{
		return false;
	}
}



function del(title){
	var msg = "删除在线参数，它会对所有用户或资金造成巨大影响，您真的确定要删除这条数据吗？？\n\n请确认！";
	var data = {
			"title":title
	}
	if (confirm(msg) == true) {
		$.ajax({
			type : "post",
			data : JSON.stringify(data),
			url : "/online/deleteOnlineParame",
			dataType : "json",
			contentType : 'application/json',
			success : function(data) {
				if(null != data && data.code == 1000){
					alert(data.msg);
					 window.location.href = "/online/toOnline?pageSize=10&pageNum=1"; 
				}
			}
		});
		return true;
	} else {
		return false;
	}
}

	
function detail(title){
	if(null == title || title == ''){
		alert("在线参数的title不能为空");
		return false;
	}
	var data = {
			 "title":title
		}
	$.ajax({
		type : "post",
		data : JSON.stringify(data),
		url : "/online/findByKey",
		dataType : "json",
		contentType : 'application/json',
		success : function(data) {
			if(null != data && data.code == 1000){
				$('#detailProduct').modal('toggle');
				$("#titleD").val(data.entity.title);
				$("#contentD").val(data.entity.content);
				$("#descriptionD").val(data.entity.description);
			}else{
				alert(data.msg);
				return false;
			}
		}
	});
}
