package com.tarena.test;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tarena.dao.NoteBookMapper;
import com.tarena.dao.UserMapper;
import com.tarena.entity.NoteBook;
import com.tarena.entity.User;
import com.tarena.service.LoginService;
import com.tarena.service.NoteBookService;

public class TestCase {
  
	ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
	@Test
	public void test1(){
		System.out.println(ac);
		UserMapper mapper=ac.getBean("userMapper",UserMapper.class);
		User user=mapper.findByName("zhenfeng");
		System.out.println(user);
		System.out.println("{\"success\":false,\"message\":\"请先登录\",\"data\":null}");
 }
	@Test
	public void test2(){
		UserMapper mapper=ac.getBean("userMapper",UserMapper.class);
		User user=new User();
		user.setCn_user_id(UUID.randomUUID().toString());
		user.setCn_user_name("张飞");
		user.setCn_user_password("123456");
		user.setCn_user_desc("大老黑");
		mapper.save(user);
	}
	@Test
	public void test3(){
		NoteBookMapper mapper=ac.getBean("noteBookMapper",NoteBookMapper.class);
		NoteBook nb=new NoteBook();
		nb.setCn_notebook_id(UUID.randomUUID()+"");
		nb.setCn_user_id("2273f742-61ec-4440-b88a-42cf48db19ff");
		nb.setCn_notebook_type_id("2");
		nb.setCn_notebook_name("jsd1412");
		nb.setCn_notebook_createtime(new Timestamp(System.currentTimeMillis()));
        mapper.save(nb);
	}
	@Test
	public void test4(){
		LoginService ls=ac.getBean("loginService",LoginService.class);
		User user=new User();
		user.setCn_user_name("阿旺");
		user.setCn_user_password("123");
		ls.addUser(user);
	}
	@Test
	public void test5(){
		LoginService ls=ac.getBean("loginService",LoginService.class);
		Map<String, Object> map=ls.findUserForCheck("猪尾", "123");
		System.out.println(map);
	}
	@Test
	public void test6(){
		UserMapper mapper=ac.getBean("userMapper",UserMapper.class);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("username", "zhenfeng");
		map.put("pwd", "222222");
		mapper.updatepwd(map);
	}
	@Test
	public void test7(){
		LoginService ls=ac.getBean("loginService",LoginService.class);
		User user=new User();
		user.setCn_user_id("d246cad1-b802-46e8-aac4-31213c7eb52e");
		user.setCn_user_name("zhenfeng");
		user.setCn_user_password("111111");
		ls.updateUserPassword("zhenfeng", "111111", "333333");
	}
	@Test
	public void test8(){
		NoteBookService nb=ac.getBean("noteBookService",NoteBookService.class);
	     List<NoteBook> list=nb.findNormal("5d5b9123-9b3e-41e9-96a6-c8baada96854");
	    for(NoteBook n:list){
		System.out.println(n);
     }
	}
}
