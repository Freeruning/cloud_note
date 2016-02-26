package com.tarena.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tarena.entity.Note;
import com.tarena.entity.NoteBook;
import com.tarena.entity.Result;
import com.tarena.entity.Share;
import com.tarena.entity.User;
import com.tarena.service.NoteService;

@Controller
@RequestMapping("/note")
public class NoteController {

	@Resource
	private NoteService noteService;
	@RequestMapping("/find.do")
	@ResponseBody
	public Result find(String noteBookId) {
		List<Note> list = 
			noteService.findNotes(noteBookId);
		return new Result(list);
	}
	@RequestMapping("/add.do")
	@ResponseBody
	public Result add(String noteBookId,String cn_note_title,HttpSession session) {
		User user=(User) session.getAttribute("user");
		String userId=user.getCn_user_id();
		Note note=noteService.addNote(noteBookId, cn_note_title, userId);
		return new Result(note);
	}
	@RequestMapping("/update.do")
	@ResponseBody
	public Result update(Note note) {
		noteService.updateNote(note);
		return new Result();
	}
	@RequestMapping("/findnap.do")
	@ResponseBody
	public Result findNormalAndPush(HttpSession session) {
		User user=(User)session.getAttribute("user");
		List<NoteBook> list=noteService.findNormalAndPush(user.getCn_user_id());
		return new Result(list);
	}
	@RequestMapping("/likeNote.do")
	@ResponseBody
	public Result likeNote(Share share,String favorites,HttpSession session) {
		User user=(User)session.getAttribute("user");
          noteService.addNoteFromfavorites(share, favorites, user.getCn_user_id());
		return new Result();
	}
}
