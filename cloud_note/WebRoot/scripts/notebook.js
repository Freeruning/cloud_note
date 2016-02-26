/***
 * 加载普通笔记本
 */
function loadNormalNoteBook(){
	//alert("加载普通笔记本");
	$.post(path+"/find/findNormal.do",{},function(result){
		if(result.success){
			$(result.data).each(function(){
				var li="<li class='online'><a class='unchecked'><i class='fa fa-book' title='笔记本' rel='tooltip-bottom'></i>"+this.cn_notebook_name+"<button type='button' class='btn btn-default btn-xs btn_position btn_delete'><i class='fa fa-times'></i></button></a></li>";
			//	alert(this.cn_notebook_name);
				$("#first_side_right ul").append(li);
				//将笔记本数据绑定到li上
				//为后续的修改,删除功能做准本
				$("#first_side_right ul li:last").data("notebook",this);
		});
			}else{
				if(result.message=="请先登录"){
				location.href="login.html";
				}
			alert(result.message);
		}
		
	});
}

/***
 * 加载特殊笔记本
 */
function loadSpecialNoteBook(){
	//alert("加载特殊笔记本");
	$.post(path+"/find/findSpecial.do",{},function(result){
		if(result.success){
			$(result.data).each(function(){
			var type=this.cn_notebook_type_id;
			if(type==1){
				//favorites
				$("#like_button").data("notebook",this);
			}else if(type==2){
				$("#rollback_button").data("notebook",this);
			}else if(type==3){
				$("action_button").data("notebook",this);
			}else{
				$("#first_side_right ul li:first").data("notebook",this);
				//推送 默认
			}
		});
			}else{
			alert(result.message);
		}
		
	});
}

/****
 * 添加笔记本
 */
function addNoteBook(){
	var name=$("#input_notebook").val();
	if(name==""){
     return;		
	}
	$.post(path+"/find/add.do",{"notebookName":name},
			function(result){
		if(result.success){
			var nb=result.data;
			var li="<li class='online'><a class='unchecked'><i class='fa fa-book' title='笔记本' rel='tooltip-bottom'></i>"+nb.cn_notebook_name+"<button type='button' class='btn btn-default btn-xs btn_position btn_delete'><i class='fa fa-times'></i></button></a></li>";
			$("#first_side_right ul").append(li);
			$("#first_side_right ul li:last").data("notebook",nb);
		    $(".cancle").trigger("click");
		}else{
			alert(result.message);
		}
	});
}

/***
 * 重命名笔记本
 */
function updateNoteBook(){
	var rname=$("#input_notebook_rename").val();
	if(rname==""){
		return;
	}
	var li = $("#first_side_right .checked").parent();
	var nb=li.data("notebook");
	if(rname==nb.cn_notebook_name) {
		return;
	}
	nb.cn_notebook_name=rname;
	var param={"cn_notebook_desc":nb.cn_notebook_desc,"cn_notebook_name":rname,"cn_notebook_id":nb.cn_notebook_id,
			"cn_user_id":nb.cn_user_id,"cn_notebook_type_id":nb.cn_notebook_type_id};
	$.post(path+"/find/update.do",param,function(result){
		if(result.success){
			$(".cancle").trigger("click");
			var inner = '<i class="fa fa-book" title="笔记本" rel="tooltip-bottom"></i> '+rname+'<button type="button" class="btn btn-default btn-xs btn_position btn_delete"><i class="fa fa-times"></i></button>';
			$("#first_side_right .checked").html(inner);
			li.data("notebook",nb);
		}else{
			alert(result.message);
		}
	});
}
//400传参框架报错

/***
 * 删除笔记本
 */
function deleteNoteBook(){
	var li = $("#first_side_right .checked").parent();
	var nb=li.data("notebook");
	var param={"cn_notebook_desc":nb.cn_notebook_desc,"cn_notebook_name":nb.cn_notebook_name,"cn_notebook_id":nb.cn_notebook_id,
			"cn_user_id":nb.cn_user_id,"cn_notebook_type_id":nb.cn_notebook_type_id};
	$.post(path+"/find/delete.do",{"cn_notebook_id":nb.cn_notebook_id},function(result){
		if(result.success){
			$(".cancle").trigger("click");
			$("#first_side_right .checked").parent().remove();
		}else{
			alert(result.message);
		}
	});
}

/**
 * 将笔记本列表放置到select组件中
 */
function setNoteBookToSelect(){
	
	$.post(path+"/note/findnap.do",{},function(result){
		if(result.success){
			$(result.data).each(function(){
				var name=this.cn_notebook_name;
				if(this.cn_notebook_type_id=='4'){
					name="默认笔记本";
				}
				var option='<option value="'+this.cn_notebook_id+'">'+name+'</option>';
				$("#moveSelect").append(option);
			});
		}else{
			alert(result.message);
		}
	});
	
	console.log("将笔记本列表放置到select组件中");
}