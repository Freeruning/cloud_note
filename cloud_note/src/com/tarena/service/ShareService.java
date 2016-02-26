package com.tarena.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tarena.dao.ShareMapper;
import com.tarena.entity.Share;
import com.tarena.util.SystemConstant;
@Service
public class ShareService implements SystemConstant{
   @Resource
	private ShareMapper shareMapper;
   public void save(Share share){
	   if(share==null){
		   throw new RuntimeException("参数为空");
	   }
	   shareMapper.save(share);
   }
   public void delete(String cn_note_id){
	   
   }
   public List<Share> findShares(String condition,int currentPage){
	   if(condition==null){
		   throw new RuntimeException("参数为空");
	   }
	  return shareMapper.findByPage(condition, (currentPage-1)*PAGE_SIZE, PAGE_SIZE);
	   
   }
}
