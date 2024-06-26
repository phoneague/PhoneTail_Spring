package com.himedia.phonetail_spring.service;

import com.himedia.phonetail_spring.dao.IMemberDAO;
import com.himedia.phonetail_spring.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public int deleteMember(String userid) {
        return mdao.deleteMember(userid);
    }
}
