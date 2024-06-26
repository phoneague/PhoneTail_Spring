package com.himedia.phonetail_spring.dao;

import com.himedia.phonetail_spring.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IAdminDAO {

    AdminDTO getAdmin(String adminid);

    int getAllCount(String tablename, String fieldname, String key);

    List<ReportDTO> getReportList(Paging paging, String key);

    List<QuestionDTO> getQnaList(Paging paging, String key);

    List<MemberDTO> getMemberList(Paging paging, String key, String userstate);

    int getMemberAllCount(String member, String name, String key, String userstate);
}
