package com.tarena.entity;

import java.io.Serializable;
/**
 * Controller返回的数据对象
 * @author Administrator
 *
 */
public class Result implements Serializable {
	//程序执行是否成功,默认为是
  private boolean success=true;
  //程序执行结果的提示信息,一般执行成功不提示,执行失败给予错误提示
  //目前不处理错误,学习AOP时,用AOP统一处理
  private String message;
  //用来封装业务数据 
  private Object data;
  /**
   * 使用默认构造器创建Result,其sucess为true
   * message为null,Object为null
   */
  
  public Result(){
  }
  /**
   * 使用此构造器创建Result
   * sucess为true
   * message为null,Object为传入的data
   * @param data
   */
  public Result(Object data){
	  this.data=data;
  }
  public Result(boolean success, String message, Object data) {
	this.success = success;
	this.message = message;
	this.data = data;
}
public boolean isSuccess() {
	return success;
}
public void setSuccess(boolean success) {
	this.success = success;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public Object getData() {
	return data;
}
public void setData(Object data) {
	this.data = data;
}
  
}
