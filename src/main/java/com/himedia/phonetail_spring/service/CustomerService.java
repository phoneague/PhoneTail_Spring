package com.himedia.phonetail_spring.service;

import com.himedia.phonetail_spring.dao.IQuestionDAO;
import com.himedia.phonetail_spring.dto.Paging;
import com.himedia.phonetail_spring.dto.QuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    IQuestionDAO qdao;

    public void updateReadCount(int qseq) {
        qdao.updateReadCount(qseq);
    }

    /*
    public void writeQna(String userid, String title, String content, boolean secret) {
        qdao.writeQna(userid, title, content, secret);
    }
    */

    public Object getQna(int qseq) {
        return qdao.getQna(qseq);
    }

    public int getAllCount(String tableName, String columnName, String key) {
        return qdao.getAllCount(tableName, columnName, key);
    }

    public List<QuestionDTO> getAllQuestions(Paging paging, String key) {
        return qdao.getAllQuestions(paging, key);
    }

    public void insertQna(QuestionDTO qdto) {
        qdao.insertQna(qdto);
    }

    public void deleteQna(int qseq) {
        qdao.deleteQna(qseq);
    }


}
