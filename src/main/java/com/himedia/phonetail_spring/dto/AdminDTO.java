package com.himedia.phonetail_spring.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdminDTO {
    @NotEmpty(message = "아이디를 입력하세요")
    @NotNull(message = "아이디를 입력하세요")
    private String adminid;
    @NotEmpty(message = "비밀번호를 입력하세요")
    @NotNull(message = "비멀번호를 입력하세요")
    private String pwd;
    private String name;
    private String phone;
}
