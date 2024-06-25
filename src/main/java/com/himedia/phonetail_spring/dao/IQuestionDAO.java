package com.himedia.phonetail_spring.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IQuestionDAO {

    void updateReadCount(int qseq);

    Object getQna(int qseq);
}
