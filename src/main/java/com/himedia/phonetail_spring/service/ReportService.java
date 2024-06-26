package com.himedia.phonetail_spring.service;

import com.himedia.phonetail_spring.dao.IReportDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    @Autowired
    IReportDAO rdao;

    public void insertReport(int pseq, String userid, int retype, String recontent) {
        rdao.insertReport(pseq,userid,retype,recontent);
    }
}
