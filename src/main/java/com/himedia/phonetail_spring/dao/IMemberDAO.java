package com.himedia.phonetail_spring.dao;

import com.himedia.phonetail_spring.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IMemberDAO {
    MemberDTO getMember(String userid);

    void insertMember(MemberDTO mdto);

    int updateMember(MemberDTO mdto);

    void deleteMember(String userid);

    void stateChangeBtoY(String userid);

    void stateChangeNtoY(String userid);

    void stateChangeYtoB(String userid);

    String findId(String name, String email);

    String findPw(String userid, String email);
}
