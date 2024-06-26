package com.himedia.phonetail_spring.dao;

import com.himedia.phonetail_spring.dto.Paging;
import com.himedia.phonetail_spring.dto.QuestionDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IQuestionDAO {

    void deleteQna(int qseq);

    void updateReadCount(int qseq);

    Object getQna(int qseq);

    int getMyAllCount(String tablename, String myid, String fieldname, String key);

    List<QuestionDTO> getMyQnaList(Paging paging, String key, String myid);

    List<QuestionDTO> getAllQuestions(Paging paging, String key, String fieldname);

    int getAllCount(String fieldname, String key);

    void writeQna(String userid, String title, String content, boolean secret);
}
