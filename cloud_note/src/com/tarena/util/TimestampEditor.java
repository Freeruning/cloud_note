package com.tarena.util;

import java.beans.PropertyEditorSupport;

import com.sun.jmx.snmp.Timestamp;
/**
 * 日期类型转换器,可以将页面传入的long型的日期
 * @author Administrator
 *
 */
public class TimestampEditor extends PropertyEditorSupport{
/*
 * 接受页面传入的数据,将其转型后,注入给目标
 * text是页面传入的数据
*/
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if(text==null){
			setValue(null);//如果传入参数为null,则直接将null注入给目标
		}else{
			//如果传入参不为位空，则将其转型为日期,注入给目标
			Timestamp t=new Timestamp(Long.valueOf(text));
		 setValue(t);
		}
     		
	}

}
