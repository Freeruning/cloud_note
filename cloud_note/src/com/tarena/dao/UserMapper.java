package com.tarena.dao;

import java.util.Map;

import com.tarena.entity.User;

public interface UserMapper {
  User findByName(String username);
  void save(User user);
  /**
   * Map封装username pwd
   * @param username
   * @param pwd
   */
  void updatepwd(Map<String,Object> param);
  void update2(User user);
  void update3(String username,String password);
  void update4(User user);
}
