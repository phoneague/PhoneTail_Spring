package com.himedia.phonetail_spring.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ChatingDTO {
    private int cseq;
    private String content;
    private Timestamp indate;
    private String userid;
    private String model;
    private int lseq;
}
