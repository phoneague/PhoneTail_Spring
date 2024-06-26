package com.himedia.phonetail_spring.service;

import com.himedia.phonetail_spring.dao.IMemberDAO;
import com.himedia.phonetail_spring.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    IMemberDAO mdao;

    public MemberDTO getMember(String userid) {
        return mdao.getMember(userid);
    }

    public void insertMember(MemberDTO mdto) {
        mdao.insertMember(mdto);
    }

    public int updateMember(MemberDTO mdto) {
        return mdao.updateMember(mdto);
    }

    public void deleteMember(String userid) {
        mdao.deleteMember(userid);
    }

    public String findId(String name, String email) {
        return mdao.findId(name, email);
    }

    public String findPw(String userid, String email) {
        return mdao.findPw(userid, email);
    }
}
