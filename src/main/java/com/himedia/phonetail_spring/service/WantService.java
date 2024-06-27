package com.himedia.phonetail_spring.service;

import com.himedia.phonetail_spring.dao.IProductDAO;
import com.himedia.phonetail_spring.dao.IWantDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WantService {

    @Autowired
    IWantDAO wdao;

    @Autowired
    IProductDAO pdao;

    public int checkWant(int pseq, String userid) {
        Integer wseq = wdao.checkWant(pseq, userid);
        return (wseq != null) ? wseq : 0;
    }

    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void insertWant(int pseq, String userid) {
        wdao.insertWant(pseq, userid);
        pdao.plusWantcount(pseq);
    }

    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void deleteWant(int wseq, int pseq) {
        wdao.deleteWant(wseq);
        pdao.minusWantcount(pseq);
    }
}
