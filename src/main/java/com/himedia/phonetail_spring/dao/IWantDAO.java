package com.himedia.phonetail_spring.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IWantDAO {
    Integer checkWant(int pseq, String userid);

    void insertWant(int pseq, String userid);

    void deleteWant(int wseq);
}
