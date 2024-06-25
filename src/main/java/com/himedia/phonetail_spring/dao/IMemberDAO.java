package com.himedia.phonetail_spring.dao;

import com.himedia.phonetail_spring.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IMemberDAO {
    MemberDTO getMember(String userid);
}
