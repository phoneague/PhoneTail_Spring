package com.himedia.phonetail_spring.dao;

import com.himedia.phonetail_spring.dto.Paging;
import com.himedia.phonetail_spring.dto.ProductDTO;
import com.himedia.phonetail_spring.dto.ReportDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IProductDAO {
    List<ProductDTO> getBrandList(String brand);
    List<ProductDTO> getNewList();

    ProductDTO getProduct(int pseq);

    int getAllCount(String brand, String sellstate, String key, String fieldname);

    List<ProductDTO> getProductList(Paging paging, String brand, String sellstate, String key, String fieldname);

    void insertProduct(ProductDTO productdto);

    void updateProduct(ProductDTO productdto);

    void deleteProduct(int pseq);



    List<ProductDTO> myWantProductList(Paging paging, String myid);

    void plusReadcount(int pseq);

    void sold(int pseq);

    void plusWantcount(int pseq);

    void minusWantcount(int pseq);

    List<ProductDTO> getMyProductList(Paging paging, String myid);

    int getMyAllCount(String wantlistView, String wuserid, String myid);
}
