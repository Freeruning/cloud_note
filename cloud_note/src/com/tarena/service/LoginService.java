package com.tarena.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tarena.dao.NoteBookMapper;
import com.tarena.dao.UserMapper;
import com.tarena.entity.NoteBook;
import com.tarena.entity.User;
import com.tarena.util.Md5Util;
import com.tarena.util.SystemConstant;
//transaction(rollbackfor=Exception.class)
@Service
public class LoginService implements SystemConstant{
  /**
   *创建用户 
   *@param user
   *包含用户名,密码,昵称
   */
	 @Resource
	private UserMapper userMapper;
	@Resource
	private NoteBookMapper nb;
	//登录校验
	public boolean updateUserPassword(String username, String last_password,String new_password){
		if(username==null||last_password==null||new_password==null){
			throw new RuntimeException("参数不能为空");
		}
		User user=userMapper.findByName(username);
		if(user.getCn_user_password().equals(Md5Util.md5(last_password))){
			user.setCn_user_password(Md5Util.md5(new_password));
			userMapper.update4(user);
			System.out.println("updateUserPassword="+user);
			return true;
		}else{
			return false;	
		}
	}
	public Map<String,Object> findUserForCheck(String username,String password){
		if(username==null||password==null){
			throw new RuntimeException("参数不能为空");
		}
		User user=userMapper.findByName(username);
		System.out.println("LoginService"+user);
		Map<String,Object> map=new HashMap<String,Object>();
		if(user==null){
			map.put("flag", USER_NAME_ERROR);
	
		}else if(!Md5Util.md5(password).equals(user.getCn_user_password())){
			map.put("flag", PASSWORD_ERROR);
			
		}else{
			map.put("flag", SUCCESS);
			map.put("user", user);
		}
		System.out.println(map);
		return map;
		
	}
	public boolean addUser(User user){
			if(user==null){
				throw new RuntimeException("参数不能为空");
			}
		//1，验证用户名是否重复
		User u=userMapper.findByName(user.getCn_user_name());
		if(u==null){
			//用户名不重复
			
			//2，对密码加密
			String md5Password=Md5Util.md5(user.getCn_user_password());
			//3，创建用户
			user.setCn_user_password(md5Password);
			user.setCn_user_id(UUID.randomUUID().toString());
			userMapper.save(user);
			//4，初始化笔记本
			noteBookCon(user.getCn_user_id());
			return true;
		}else{
			//用户名重复
			return false;
		}
  }
	private void noteBookCon(String u_id) {
		NoteBook n1=new NoteBook();
		n1.setCn_notebook_id(UUID.randomUUID().toString());
		n1.setCn_user_id(u_id);
		n1.setCn_notebook_type_id(NB_TYPE_ID_FAVORITES);
		n1.setCn_notebook_name(NB_TYPE_CODE_FAVORITES);
		n1.setCn_notebook_createtime(new Timestamp(System.currentTimeMillis()));
		nb.save(n1);
		NoteBook n2=new NoteBook();
		n2.setCn_notebook_id(UUID.randomUUID().toString());
		n2.setCn_user_id(u_id);
		n2.setCn_notebook_type_id(NB_TYPE_ID_RECYCLE);
		n2.setCn_notebook_name(NB_TYPE_CODE_RECYCLE);
		n2.setCn_notebook_createtime(new Timestamp(System.currentTimeMillis()));
		nb.save(n2);
		NoteBook n3=new NoteBook();
		n3.setCn_notebook_id(UUID.randomUUID().toString());
		n3.setCn_user_id(u_id);
		n3.setCn_notebook_type_id(NB_TYPE_ID_ACTION);
		n3.setCn_notebook_name(NB_TYPE_CODE_ACTION);
		n3.setCn_notebook_createtime(new Timestamp(System.currentTimeMillis()));
		nb.save(n3);
		NoteBook n4=new NoteBook();
		n4.setCn_notebook_id(UUID.randomUUID().toString());
		n4.setCn_user_id(u_id);
		n4.setCn_notebook_type_id(NB_TYPE_ID_PUSH);
		n4.setCn_notebook_name(NB_TYPE_CODE_PUSH);
		n4.setCn_notebook_createtime(new Timestamp(System.currentTimeMillis()));
		nb.save(n4);
	}
}
