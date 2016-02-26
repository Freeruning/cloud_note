package com.tarena.dao;

import java.util.List;

import com.tarena.entity.Share;

public interface ShareMapper {
   void save(Share share);
   void delete(String cn_note_id);
   //condition查询条件
   //begin查询起始位置
   //pageSize查询大小
   List<Share> findByPage(String condition,int begin,int pageSize);

}
