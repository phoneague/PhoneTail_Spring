package com.himedia.phonetail_spring.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ProductDTO {
    private int pseq;
    private String brand;
    private String model;
    private int price;
    private String comment;
    private String image;
    private String saveimagefile;
    private String sellstate;
    private String userid;
    private Timestamp indate;
    private int readcount;
    private int wantcount;
}
