package com.himedia.phonetail_spring.service;

import com.himedia.phonetail_spring.dao.IWantDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WantService {

    @Autowired
    IWantDAO wdao;

    public int checkWant(int pseq, String userid) {
        Integer wseq = wdao.checkWant(pseq, userid);
        return (wseq != null) ? wseq : 0;
    }

    public void insertWant(int pseq, String userid) {
        wdao.insertWant(pseq, userid);
    }

    public void deleteWant(int wseq) {
        wdao.deleteWant(wseq);
    }
}
