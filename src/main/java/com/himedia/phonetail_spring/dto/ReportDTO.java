package com.himedia.phonetail_spring.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ReportDTO {
    private int reseq;
    private int pseq;
    private String userid;
    private int retype;
    private String recontent;
    private String restate;
    private Timestamp indate;
    private String pid;
}
