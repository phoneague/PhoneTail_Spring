package com.himedia.phonetail_spring.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class QuestionDTO {
    private int qseq;
    private String title;
    private String content;
    private Timestamp indate;
    private String userid;
    private String qreply;
    private boolean secret;
    private int readCount;
}
