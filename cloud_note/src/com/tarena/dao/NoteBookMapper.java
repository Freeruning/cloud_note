package com.tarena.dao;

import java.util.List;

import com.tarena.entity.NoteBook;


public interface NoteBookMapper{
  void save(NoteBook nb);
  List<NoteBook> findNarmal(String userId);
  List<NoteBook> findSpecial(String userId);
  void update(NoteBook book);
  void  delete(String noteBookId);
  List<NoteBook> findNormalAndPush(String userId);
}
