package com.himedia.phonetail_spring.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ProductDTO {
    private int pseq;

    @NotEmpty(message = "브랜드를 선택하세요")
    @NotNull(message = "브랜드를 선택하세요")
    private String brand;

    @NotEmpty(message = "모델명을 입력하세요")
    @NotNull(message = "모델명을 입력하세요")
    private String model;

    @NotNull(message = "가격을 입력하세요")
    @Min(value=0, message="상품가격은 음수일 수 없습니다.")
    private int price;

    @NotEmpty(message = "글내용을 입력하세요")
    @NotNull(message = "글내용을 입력하세요")
    private String comment;

    @NotEmpty(message = "상품이미지를 입력하세요")
    @NotNull(message = "상품이미지를 입력하세요")
    private String image;

    @NotEmpty(message = "상품이미지를 입력하세요")
    @NotNull(message = "상품이미지를 입력하세요")
    private String saveimagefile;
    private String sellstate;

    @NotEmpty(message = "로그인 하세요")
    @NotNull(message = "로그인 하세요")
    private String userid;
    private Timestamp indate;
    private int readcount;
    private int wantcount;
}
