package com.himedia.phonetail_spring.service;

import com.himedia.phonetail_spring.dao.IQuestionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    IQuestionDAO qdao;

    public void updateReadCount(int qseq) {
        qdao.updateReadCount(qseq);
    }

    public Object getQna(int qseq) {
        return qdao.getQna(qseq);
    }
}
