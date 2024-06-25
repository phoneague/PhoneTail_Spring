package com.himedia.phonetail_spring.dao;

import com.himedia.phonetail_spring.dto.Paging;
import com.himedia.phonetail_spring.dto.ReportDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IReportDAO {
    int getMyAllCount(String report, String myid);

    List<ReportDTO> getMyReportList(Paging paging, String myid);
}
