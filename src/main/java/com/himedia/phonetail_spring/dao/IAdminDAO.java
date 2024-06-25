package com.himedia.phonetail_spring.dao;

import com.himedia.phonetail_spring.dto.AdminDTO;
import com.himedia.phonetail_spring.dto.Paging;
import com.himedia.phonetail_spring.dto.ReportDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IAdminDAO {

    AdminDTO getAdmin(String adminid);

    int getAllCount(String tablename, String fieldname, String key);

    List<ReportDTO> getReportList(Paging paging, String key);
}
