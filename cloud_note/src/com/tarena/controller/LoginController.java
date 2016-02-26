package com.tarena.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tarena.entity.Result;
import com.tarena.entity.User;
import com.tarena.service.LoginService;
import com.tarena.util.SystemConstant;

@Controller
@RequestMapping("/login")
public class LoginController implements SystemConstant{
	@Resource
	private LoginService loginService;
	
	@RequestMapping("/change_password.do")
	@ResponseBody
	public Result change_password(String last_password,String new_password,HttpSession session){
	User user=(User)(session.getAttribute("user"));
	//System.out.println(user);
	//System.out.println("我也是"+session);
	if(user==null){
		throw new RuntimeException("user为空");
	}
	String username=user.getCn_user_name();
    boolean b=loginService.updateUserPassword(username,last_password,new_password);
	return new Result(b);
	}
	
	@RequestMapping("/logout.do")
	@ResponseBody
	public Result logout(HttpSession session){
	//	System.out.println("logout 1");
		session.invalidate();
	//	System.out.println("logout 2");
		return new Result();
	}
	@RequestMapping("/checkLogin.do")
	@ResponseBody
	public Result checkLogin(String username,String password,HttpSession session){
		Map<String,Object> map=loginService.findUserForCheck(username, password);
		if(map.get("flag").equals(SUCCESS)){
			User user=(User) map.get("user");
			session.setAttribute("user", user);
		}
		//System.out.println("...."+session.getAttribute("user"));
		//System.out.println("hello"+session);
		return new Result(map);
	}
	@RequestMapping("/register.do")
	@ResponseBody
  public Result register(User user){
		boolean b=loginService.addUser(user);
	  return new Result(b);
  }
}
