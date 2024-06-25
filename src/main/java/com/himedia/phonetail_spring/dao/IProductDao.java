package com.himedia.phonetail_spring.dao;

import com.himedia.phonetail_spring.dto.ProductVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IProductDao {
    List<ProductVO> getBrandList(String brand);
    List<ProductVO> getNewList();

    ProductVO getProduct(int pseq);
}
