package com.tarena.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tarena.entity.Result;
import com.tarena.entity.User;

/**
 * 异常日志记录工具,在服务端代码中发生任何错误时
 * 记录该错误的日志
 * @author Administrator
 *
 */
@Component
@Aspect
public class ExceptionLogger {
	@Around("within(com.tarena.controller.*)")
  public Object log(ProceedingJoinPoint p){
		Object obj=null;
		try {
			obj=p.proceed();
		} catch (Throwable e) {
       //目标组件发生异常时,记录日志
			ServletRequestAttributes attr=(ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
			HttpServletRequest request=attr.getRequest();
		  HttpSession session=request.getSession();
		  StringBuffer sb=new StringBuffer();
		  User user=(User)session.getAttribute("user");
		  if(user!=null){
			  sb.append("用户[").append(user.getCn_user_name()).append("],");
		  }
		  sb.append("IP[").append(request.getRemoteHost()).append("],");
		 String now=new SimpleDateFormat("yyy-MM-hh HH:mm:ss").format(new Date());
		 sb.append("在[").append(now).append("],");
		 sb.append("调用[").append(p.getTarget().getClass().getName()).append(".").append(p.getSignature().getName()).append("]时,发生如下异常:");
		 Logger logger=Logger.getLogger(ExceptionLogger.class);
		 logger.error(sb.toString());
		 StackTraceElement[] element=e.getStackTrace();
		 for(StackTraceElement elem:element){
			 logger.error("\t"+elem.toString());
		 }
		 //发生异常时,不要返回Controller自己的Result数据,构造一个新的Result,封装错误信息返回
		 Result result=new Result(false,"系统报错,请联系管理员",null);
		 return result;
		}
	  return obj;
  }
}
