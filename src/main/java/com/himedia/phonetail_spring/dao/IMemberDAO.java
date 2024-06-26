package com.himedia.phonetail_spring.dao;

import com.himedia.phonetail_spring.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IMemberDAO {
    MemberDTO getMember(String userid);

    void insertMember(MemberDTO mdto);

    int updateMember(MemberDTO mdto);

    int deleteMember(String userid);

    void stateChangeBtoY(String userid);

    void stateChangeNtoY(String userid);

    void stateChangeYtoB(String userid);
}
