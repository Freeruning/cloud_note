package com.tarena.controller;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tarena.entity.Result;
import com.tarena.entity.Share;
import com.tarena.service.ShareService;
@Controller
@RequestMapping("/share")
public class ShareController {
   @Resource
	private ShareService shareService;
   @RequestMapping("/search.do")
   @ResponseBody
   public Result search(String condition,int currentPage){
	   List<Share> list=shareService.findShares(condition, currentPage);
	   return new Result(list);
   }
   @RequestMapping("/save.do")
   @ResponseBody
	public Result save(String title,String body,String noteId){
		Share share=new Share();
		share.setCn_share_id(UUID.randomUUID().toString());
		share.setCn_share_title(title);
		share.setCn_share_body(body);
		share.setCn_note_id(noteId);
		shareService.save(share);
		return new Result();
	}
}
