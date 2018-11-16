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
    		url : "/message/dataList",
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
    				     htmlStr += '<td width="40%">'+content+'</td>';
    				     htmlStr += '<td width="7%">'+item.createTimeStr+'</td>';
    					 htmlStr += '<td width="30%">';
    					 htmlStr += '<button class="btn btn-primary btn-sm" onclick="detail(\''+item.id+'\')"> <i class="glyphicon glyphicon-edit">详情</i></button> '
    					 htmlStr += '<button class="btn btn-primary btn-sm" onclick="edit(\''+item.id+'\')"> <i class="glyphicon glyphicon-edit">修改</i></button> '
    					 htmlStr += '<button class="btn btn-info btn-sm" onclick="del(\''+item.id+'\')">删除</button>'
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
		url : "/message/dataList",
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
				     htmlStr += '<td width="40%">'+content+'</td>';
				     htmlStr += '<td width="7%">'+item.createTimeStr+'</td>';
					 htmlStr += '<td width="30%">';
					 htmlStr += '<button class="btn btn-primary btn-sm" onclick="detail(\''+item.id+'\')"> <i class="glyphicon glyphicon-edit">详情</i></button> '
					 htmlStr += '<button class="btn btn-primary btn-sm" onclick="edit(\''+item.id+'\')"> <i class="glyphicon glyphicon-edit">修改</i></button> '
					 htmlStr += '<button class="btn btn-info btn-sm" onclick="del(\''+item.id+'\')">删除</button>'
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
	if(null == title || title == ''){
		alert("公告的标题不能为空");
		return false;
	}
	if(null == content || content == ''){
		alert("公告的内容不能为空");
		return false;
	}
	var data = {
			 "title":title,"content":content
		}
	$.ajax({
		type : "post",
		data : JSON.stringify(data),
		url : "/message/saveMessage",
		dataType : "json",
		contentType : 'application/json',
		success : function(data) {
			if(null != data && data.code == 1000){
				 window.location.href = "/message/toMessageList?pageSize=10&pageNum=1"; 
			}else{
				alert(data.msg);
				return false;
			}
		}
	});
}


function edit(id){
	if(null == id || id == ''){
		alert("请求参数id不为空");
		return false;
	}
	var data = {
			 "id":id
		}
	$.ajax({
		type : "post",
		data : JSON.stringify(data),
		url : "/message/findById",
		dataType : "json",
		contentType : 'application/json',
		success : function(data) {
			if(null != data && data.code == 1000){
				$('#editProduct').modal('toggle');
				$("#idE").val(data.entity.id);
				$("#titleE").val(data.entity.title);
				$("#contentE").val(data.entity.content);
			}else{
				alert(data.msg);
				return false;
			}
		}
	});
	
	
}

function update(){
	var id = $("#idE").val();
	var title = $("#titleE").val();
	var content = $("#contentE").val();
	if(null == title || title == ''){
		alert("公告的标题不能为空");
		return false;
	}
	if(null == content || content == ''){
		alert("公告的内容不能为空");
		return false;
	}
		var data = {
		"title" : title,"content" : content,"id" : id
		}
	$.ajax({
		type : "post",
		data : JSON.stringify(data),
		url : "/message/update",
		dataType : "json",
		contentType : 'application/json',
		success : function(data) {
			if (null != data && data.code == 1000) {
				window.location.href = "/message/toMessageList?pageSize=10&pageNum=1";
			} else {
				alert(data.msg);
				return false;
			}
		}
	});
}



function del(id) {
	if (null == id || id == '') {
		alert("请求参数id不为空");
		return false;
	}
	var data = {
		"id" : id
	}
	$.ajax({
			type : "post",
			data : JSON.stringify(data),
			url : "/message/delete",
			dataType : "json",
			contentType : 'application/json',
			success : function(data) {
				if (null != data && data.code == 1000) {
					alert(data.msg);
					window.location.href = "/message/toMessageList?pageSize=10&pageNum=1";
				}
			}
		});
}

	
function detail(id){
	if(null == id || id == ''){
		alert("在线参数的title不能为空");
		return false;
	}
	var data = {
			 "id":id
		}
	$.ajax({
		type : "post",
		data : JSON.stringify(data),
		url : "/message/findById",
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
