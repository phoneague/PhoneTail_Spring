package com.himedia.phonetail_spring.dao;

import com.himedia.phonetail_spring.dto.Paging;
import com.himedia.phonetail_spring.dto.ProductDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IWantDAO {
    Integer checkWant(int pseq, String userid);

    void insertWant(int pseq, String userid);

    void deleteWant(int wseq);

    int getMyAllCount(String want, String myid, String key);

    List<ProductDTO> myWantProductList(Paging paging, String key, String myid);
}
