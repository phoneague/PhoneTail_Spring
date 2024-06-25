package com.himedia.phonetail_spring.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class MemberDTO {

    private String userid;
    private String pwd;
    private String name;
    private String phone;
    private String email;
    private String address1;
    private String address2;
    private String address3;
    private String userstate;
    private Timestamp indate;

}
