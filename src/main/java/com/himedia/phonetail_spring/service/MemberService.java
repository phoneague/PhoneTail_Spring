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
}
