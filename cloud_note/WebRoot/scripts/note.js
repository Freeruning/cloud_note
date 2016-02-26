/***
 * 加载普通笔记
 */
function getNormalNoteList(){
	var li = $("#first_side_right .checked").parent();
	var nb = li.data("notebook");
	var id = nb.cn_notebook_id;
	$.post(
			path + "/note/find.do",
			{"noteBookId":id},
			function(result) {
				if(result.success) {
					//先清除之前显示的笔记
					$("#second_side_right ul").empty();
					//获取查询到的笔记
					var list = result.data;
					//遍历笔记，插入笔记列表
					$(list).each(function(){
						var li = '<li class="online"><a><i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i> '+this.cn_note_title+'<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button></a><div class="note_menu" tabindex="-1"><dl><dt><button type="button" class="btn btn-default btn-xs btn_move" title="移动至..."><i class="fa fa-random"></i></button></dt><dt><button type="button" class="btn btn-default btn-xs btn_share" title="分享"><i class="fa fa-sitemap"></i></button></dt><dt><button type="button" class="btn btn-default btn-xs btn_delete" title="删除"><i class="fa fa-times"></i></button></dt></dl></div></li>';
						$("#second_side_right ul").append(li);
						$("#second_side_right ul li:last").data("note",this);
					});
				} else {
					alert(result.message);
				}
			}
		);
	
	console.log("加载普通笔记");
}

/***
 * 查询普通笔记内容
 */
function getNoteDetail(){
	var li=$("#second_side_right .checked").parent();
	var note=li.data("note");
	$("#input_note_title").val(note.cn_note_title);
	um.setContent(note.cn_note_body==null?"":note.cn_note_body);
	um.focus();
	//console.log("查询普通笔记内容");
}

/***
 * 创建普通笔记
 */
function createNormalNote(){
	var cn_note_title=$("#input_note").val();
	if(cn_note_title==""){
     return;		
	}
	var li = $("#first_side_right .checked").parent();
	var nb=li.data("notebook");
	$.post(path+"/note/add.do",{"cn_note_title":cn_note_title,"noteBookId":nb.cn_notebook_id},
			
			function(result){
		if(result.success){
			var note=result.data;
			var li='<li class="online"><a class="checked"><i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>'+note.cn_note_title+'<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button></a><div class="note_menu" tabindex="-1"><dl><dt><button type="button" class="btn btn-default btn-xs btn_move" title="移动至..."><i class="fa fa-random"></i></button></dt><dt><button type="button" class="btn btn-default btn-xs btn_share" title="分享"><i class="fa fa-sitemap"></i></button></dt><dt><button type="button" class="btn btn-default btn-xs btn_delete" title="删除"><i class="fa fa-times"></i></button></dt></dl></div></li>';
			$("#second_side_right ul").append(li);
			$("#second_side_right ul li:last").data("note",note);
			$("#second_side_right ul li:last").trigger("click");
		    $(".cancle").trigger("click");
		}else{
			alert(result.message);
		}
	});
}

/***
 * 更新普通笔记
 */
function updateNormalNote(){
	//获取选中的笔记
	var li=$("#second_side_right .checked").parent();
	var note=li.data("note");
	//获取笔记标题和内容
	var title=$("#input_note_title").val();
	var body=um.getContent();//编辑器生成的HTML
	//更新对象
	note.cn_note_title=title;
	note.cn_note_body=body;
	$.post(path+"/note/update.do",note,function(result){
		if(result.success){
		$("#second_side_right .checked").html('<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>'+title+'<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>');
		
		$("#second_side_right .checked").parent().data("note",note);
    $("footer strong").text("修改成功").parent().fadeIn(500).fadeOut(3000);
		}else{
			alert(result.message);
			
		}
	});
	//alert("更新普通笔记");
}

/***
 * 删除普通笔记
 */
function deleteNormalNote(){
	var nb = $("#rollback_button").data("notebook");
	moveNote(nb.cn_notebook_id);
	//alert("删除普通笔记");
}

/***
 * 移动笔记
 */
function moveNote(notebook_id){
	var li=$("#second_side_right .checked").parent();
	var note=li.data("note");
	var notebook_id=$("#moveSelect").val();
	if(notebook_id==""||note.cn_notebook_id==notebook_id){
		return;
	}
	note.cn_notebook_id=notebook_id;
	$.post(path+"/note/update.do",note,function(result){
		if(result.success){
			li.remove();
			$("#input_note_title").val("");
			um.setContent("");
			$(".cancle").trigger("click");
		}else{
		alert(result.message);
		}
	});
	//alert("移动笔记");
}

/***
 * 分享笔记
 */
function createShareNote(){
	var li=$("#second_side_right .checked").parent();
	var note=li.data("note");
	//#second_side_right .btn_share
	$.post(path+"/share/save.do",{'title':note.cn_note_title,'body':note.cn_note_body,'noteId':note.cn_note_id},function(result){
		if(result.success){
			$("#second_side_right .btn_share").remove();
			$("footer div strong").text("分享成功").parent().fadeIn(100);
			setTimeout(function(){
				$("footer div").fadeOut(500);
			}, 1500);
		}else{
			alert(result.message);
		}
		
	});
}

/***
 * 查询回收站笔记列表
 */
function getRecycleNoteList(){
	alert("查询回收站笔记列表");
}

/***
 * 查看回收站笔记内容
 */
function getRecycleNoteDetail() {
	console.log("查看回收站笔记内容");
}

/***
 * 删除回收站笔记
 */
function deleteRecycleNote(){
	alert("删除回收站笔记");
}

/***
 * 搜索分享笔记列表
 */
function getShareNoteList(condition,current_page){
	$.post(path+"/share/search.do",{"condition":condition,"currentPage":current_page},function(result){
		if(result.success){
			$(result.data).each(function(){
				var li='<li class="online"><a href="#"><i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>'+this.cn_share_title+'<button type="button" class="btn btn-default btn-xs btn_position btn_like"><i class="fa fa-star-o"></i></button><div class="time"></div></a></li>';
				$("#sixth_side_right ul").append(li);
				$("#sixth_side_right ul li:last").data('share',this);
			});
		}else{
			alert(result.message);
		}
		
	});
	
	//alert("搜索分享笔记列表");
}

/***
 * 查询分享笔记内容
 */
function getShareNoteDetail(){
	var li=$("#sixth_side_right .checked").parent();
	var share=li.data("share");
	$("#fifth_side_right #noput_note_title").text(share.cn_share_title);
	$("#fifth_side_right #noput_note_body").html(share.cn_share_body);
	//alert("查询分享笔记内容");
}

/***
 * 收藏分享笔记
 */
function likeShareNote(shareId,dom){
	var li=$("#sixth_side_right .checked").parent();
	var share=li.data("share");
	var notebook=$("#like_button").data("notebook");
	$.post(path+"/note/likeNote.do",{"cn_share_id":share.cn_share_id,"cn_share_title":share.cn_share_title,
		"cn_share_body":share.cn_share_body,"cn_note_id":share.cn_note_id,"favorites":notebook.cn_notebook_type_id},function(result){
			if(result.success){
				 $(".cancle").trigger("click");
				 $("footer strong").text("收藏成功").parent().fadeIn(500).fadeOut(3000);
			}else{
				alert(result.message);
			}
		});
	//alert("收藏分享笔记");
}

/***
 * 加载收藏笔记
 */
function getLikeNoteList(likeNoteId){
	alert("加载收藏笔记");
}

/***
 * 查看收藏笔记内容
 */
function getLikeNoteDetail(noteId) {
	console.log("查看收藏笔记内容");
}

/***
 * 删除收藏笔记
 */
function deleteLikeNote(noteId,dom){
	alert("删除收藏笔记");
}

/***
 * 加载本用户参加活动笔记列表
 */
function getNoteActivityList(noteBookId){
	alert("加载本用户参加活动笔记列表");
}

/***
 * 查询参加活动的笔记内容
 */
function getActivityNoteDetail(noteId) {
	console.log("查询参加活动的笔记内容");
}