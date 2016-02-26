package com.tarena.dao;
import java.util.List;

import com.tarena.entity.Note;
public interface NoteMapper {

	List<Note> findByNoteBookId(String noteBookId);
	void save(Note note);
	void update(Note note); 
}
