package com.tarena.controller.interceptor;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tarena.entity.User;

public class LoginInterceptor implements HandlerInterceptor{

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		
	}

	@Override
	//Controller
	//返回true,允许程序继续进行
	//返回false,不再继续访问Controller
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		HttpSession session=arg0.getSession();
		User user=(User)session.getAttribute("user");
		if(user==null){
  //用户未登陆,不会调用Controller,因此没有返回值给页面
			//植草一个一个数据返回个页面
			arg1.setCharacterEncoding("utf-8");
			arg1.setContentType("application/json");
			Writer writer=arg1.getWriter();
			writer.write("{\"success\":false,\"message\":\"请先登录\",\"data\":null}");
			writer.close();
			return false;
		}else{
			return true;
		}
	}

}
