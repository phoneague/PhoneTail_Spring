package com.himedia.phonetail_spring.service;

import com.himedia.phonetail_spring.dao.IProductDao;
import com.himedia.phonetail_spring.dto.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    IProductDao pdao;


    public List<ProductVO> getBrandList(String brand) {
        return pdao.getBrandList(brand);

    }

    public HashMap<String, Object> getNewList() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("newList", pdao.getNewList());

        return result;
    }

    public ProductVO getProduct(int pseq) {
        return pdao.getProduct(pseq);
    }
}
