package com.tarena.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tarena.dao.NoteBookMapper;
import com.tarena.dao.NoteMapper;
import com.tarena.entity.Note;
import com.tarena.entity.NoteBook;
import com.tarena.entity.Share;

@Service
public class NoteService {

	@Resource
	private NoteMapper noteMapper;
	@Resource
	private NoteBookMapper noteBookMapper;
	public List<Note> findNotes(String noteBookId) {
		if(noteBookId==null)
			throw new RuntimeException("参数为空");
		return noteMapper.findByNoteBookId(noteBookId);
	}
	public Note addNote(String noteBookId,String cn_note_title,String userId){
		if(noteBookId==null||cn_note_title==null)
			throw new RuntimeException("参数为空");
		Note note=new Note();
		note.setCn_note_id(UUID.randomUUID().toString());
		note.setCn_note_title(cn_note_title);
		note.setCn_notebook_id(noteBookId);
		note.setCn_note_create_time(new Date().getTime());
         note.setCn_user_id(userId);
         note.setCn_note_last_modify_time(new Date().getTime());
		noteMapper.save(note);
         return note;
	}
	public void updateNote(Note note){
		if(note==null){
			throw new RuntimeException("参数为空");
		}
		noteMapper.update(note);
	}
	public List<NoteBook> findNormalAndPush(String userId){
		if(userId==null){
			throw new RuntimeException("参数为空");
		}
		List<NoteBook> list=noteBookMapper.findNormalAndPush(userId);
	 return list;
	}
	
	public void addNoteFromfavorites(Share share,String favorites,String userId){
		if(userId==null||favorites==null||share==null){
			throw new RuntimeException("参数为空");
		}
		Note note=new Note();
		note.setCn_note_id(UUID.randomUUID().toString());
		note.setCn_user_id(userId);
		note.setCn_notebook_id(favorites);
		note.setCn_note_body(share.getCn_share_body());
		note.setCn_note_title(share.getCn_share_title());
		note.setCn_note_last_modify_time(System.currentTimeMillis());
		note.setCn_note_create_time(System.currentTimeMillis());
		noteMapper.save(note);
	}
}
