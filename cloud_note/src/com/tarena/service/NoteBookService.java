package com.tarena.service;

import org.springframework.stereotype.Service;

import com.tarena.dao.NoteBookMapper;
import com.tarena.entity.NoteBook;
import com.tarena.util.SystemConstant;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
@Service
public class NoteBookService implements SystemConstant{
   @Resource
	private NoteBookMapper noteBookMapper;
   
   /*
    * 查询所有用户的普通笔记本
    */
	public List<NoteBook> findNormal(String userId){
		if(userId==null){
			throw new RuntimeException("参数不能为空");
		}
		List<NoteBook> list=noteBookMapper.findNarmal(userId);
		return list;
	}
	/*
	    * 查询所有用户的特殊笔记本
	    */
	public List<NoteBook> findSpecial(String userId){
		if(userId==null){
			throw new RuntimeException("参数不能为空");
		}
		List<NoteBook> list=noteBookMapper.findSpecial(userId);
		return list;
	}
	public NoteBook addNoteBook(String userId,String noteBookName){
		if(userId==null||noteBookName==null){
			throw new RuntimeException("参数为空");
		}
		NoteBook nb=new NoteBook();
		nb.setCn_user_id(userId);
		nb.setCn_notebook_name(noteBookName);
		nb.setCn_notebook_type_id(NB_TYPE_ID_NORMAL);
		nb.setCn_notebook_id(UUID.randomUUID().toString());
		nb.setCn_notebook_createtime(new Timestamp(System.currentTimeMillis()));
		noteBookMapper.save(nb);
		return nb;
	}
	public void updateNoteBook(NoteBook nb){
		if(nb==null){
			throw new RuntimeException("参数为空");
		}
		noteBookMapper.update(nb);
	}
	public void deleteNoteBook(String noteBookId){
		if(noteBookId==null){
			throw new RuntimeException("参数为空");
		}
		noteBookMapper.delete(noteBookId);
	}
}
