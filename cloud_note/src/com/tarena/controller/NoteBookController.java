package com.tarena.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import com.tarena.entity.NoteBook;
import com.tarena.entity.Result;
import com.tarena.entity.User;
import com.tarena.service.NoteBookService;
import com.tarena.service.NoteService;
import com.tarena.util.TimestampEditor;

@Controller
@RequestMapping("/find")
//哪个Controller置换参数,就实现该接口
public class NoteBookController implements WebBindingInitializer{
	@Resource
	private NoteBookService noteBookService;
	@RequestMapping("/findNormal.do")
	@ResponseBody
    public Result findNormal(HttpSession session){
	  User user=(User)session.getAttribute("user");
	  List<NoteBook> list=noteBookService.findNormal(user.getCn_user_id());
	 
	  return new Result(list);
	}
	@RequestMapping("/findSpecial.do")
	@ResponseBody
    public Result findSpecial(HttpSession session){
	  User user=(User)session.getAttribute("user");
	  List<NoteBook> list=noteBookService.findSpecial(user.getCn_user_id());
	 
	  return new Result(list);
	}
	@RequestMapping("/add.do")
	@ResponseBody
    public Result add(String notebookName,HttpSession session){
	  User user=(User)session.getAttribute("user");
	  NoteBook nb=noteBookService.addNoteBook(user.getCn_user_id(), notebookName);
	  return new Result(nb);
	}
	@RequestMapping("/update.do")
	@ResponseBody
	public Result update(NoteBook nb){
	    noteBookService.updateNoteBook(nb);
	  //  System.out.println(nb);
		return new  Result();
	}
	@RequestMapping("/delete.do")
	@ResponseBody
	public Result delete(String cn_notebook_id){
	    noteBookService.deleteNoteBook(cn_notebook_id);
	    System.out.println(cn_notebook_id);
		return new  Result();
	}
	//每次访问这个controller的方法前,先执行该方法initBider
	@InitBinder
	public void initBinder(WebDataBinder bider, WebRequest request) {
		bider.registerCustomEditor(java.sql.Timestamp.class, new TimestampEditor());
	}
}
