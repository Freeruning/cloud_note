package com.tarena.util;

public interface SystemConstant {
  //声明笔记本类型id
	//接口中属性  默认是public static final修饰的 
	String NB_TYPE_ID_FAVORITES="1";
	String NB_TYPE_ID_RECYCLE="2";
	String NB_TYPE_ID_ACTION="3";
	String NB_TYPE_ID_PUSH="4";
	String NB_TYPE_ID_NORMAL="5";
	//声明笔记本类型编码
	String NB_TYPE_CODE_FAVORITES="favorites";
	String NB_TYPE_CODE_RECYCLE="recycle";
	String NB_TYPE_CODE_ACTION="action";
	String NB_TYPE_CODE_PUSH="push";
	String NB_TYPE_CODE_NORMAL="normal";
	
  //登录检验结果
   int USER_NAME_ERROR=1;
   int PASSWORD_ERROR=2;
   int SUCCESS=0;
   int PAGE_SIZE=5;//每页显示最大行
}
