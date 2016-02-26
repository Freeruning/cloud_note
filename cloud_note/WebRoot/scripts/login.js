/**
 * 页面初始化后，绑定函数。
 */
$(function(){
	//注册
	$("#regist_button").click(function(){
		register();
	});
	
	//登录
	$("#login").click(function(){
		login();
	});
	
	//登出
	$("#logout").click(function(){
		logout();
	});
	
	//修改密码
	$("#changePassword").click(function(){
		changepwd();
	})
	
});

//注册
function register() {
	//1,验证客户端数据
	var user_name=$("#regist_username").val();
	var nickname=$("#nickname").val();
	var regist_password=$("#regist_password").val();
	var final_password=$("#final_password").val();
	var reg=/\w{3,20}/;
	if(!reg.test(user_name)){
	 $("#warning_1 span").text("请输入3~20位字母,数字,下划线");
	 $("#warning_1").show();
	 return;
	}else{
	$("#warning_1").hide();
	}
	if(regist_password.length<6){
		$("#warning_2 span").text("密码长度不能小于6位");
		$("#warning_2").show();
		return;
	}else{
		$("#warning_2").hide();
	}
	if(regist_password!=final_password){
		$("#warning_3 span").text("密码不一致");
		$("#warning_3").show();
		return;
	}else{
		$("#warning_3").hide();

	}
	$.post(path+"/login/register.do",
{"cn_user_name":user_name,"cn_user_password":regist_password},function(result){
		if(result.success){
			var data=result.data;
			if(!data){
				$("#warning_1 span").text("用户名已存在");
				$("#warning_1").show();
				return;
			}
	     $("#warning_1").hide();
	     alert("注册成功.");
	     $("#zc").attr("class","sig sig_out");
	     $("#dl").attr("class","log log_in");
		}
	});
}

//登陆
function login() {
	//客户端验证
	var user_name=$("#count").val();
	var password=$("#password").val();
	var reg=/\w{3,20}/;
	if(!reg.test(user_name)){
		alert("请输入3~20位字母,数字,下划线");
	return;
	}
	if(password.length<6){
		alert("密码长度不能小于6位");
		return;
	}
	$.post(path+"/login/checkLogin.do",{"username":user_name,"password":password},
			function(result){
		if(result.success){
			var data=result.data;
			var flag=data.flag;
			if(flag==0){
				addCookie("user_id",data.user.cn_user_id,10);
				addCookie("user_name",data.user.cn_user_name,10);
				location.href="edit.html";
				alert("登录成功");
				
			}else if(flag==1){
				alert("用户名不存在");
				return;
			}else{
				alert("密码错误");
				return;
			}
		}
	});
}

/**
 * 退出登录
 */
function logout(){
	$.post(path+"/login/logout.do",{},function(result){
		if(result.success){
			location.href="login.html";
		}else{
			alert(result.message);
		}
	});
}

/**
 * 修改密码
 */
function changepwd(){
	var last_password=$("#last_password").val();
	var new_password=$("#new_password").val();
	var final_password=$("#final_password").val();
	if(new_password.length<6){
		$("#warning_2 span").text("新密码长度小于6位");
		$("#warning_2").show();
		return;
	}else{
		$("#warning_2").hide();
	}
	if(final_password!=new_password){
		$("#warning_3 span").text("密码输入不一致");
		$("#warning_3").show();
		return;
	}else{
		$("#warning_3").hide();
	}
	$.post(path+"/login/change_password.do",{'last_password':last_password,'new_password':new_password},function(result){
		if(result.success){
			if(result.data){
				$("#warning_1").hide();
				alert("修改成功");
				logout();
			}else{
				$("#warning_1 span").text("原始密码输入错误");
				$("#warning_1").show();
				return;
			}
		}		
	});
}


