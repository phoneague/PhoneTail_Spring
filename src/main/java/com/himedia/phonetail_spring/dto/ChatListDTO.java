package com.himedia.phonetail_spring.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ChatListDTO {
    private int lseq;
    private String sid;
    private String bid;
    private int pseq;
    private String model;
    private int price;
    private String content;
    private Timestamp indate;
}
