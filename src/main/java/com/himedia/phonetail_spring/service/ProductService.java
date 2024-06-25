package com.himedia.phonetail_spring.service;

import com.himedia.phonetail_spring.dao.IProductDAO;
import com.himedia.phonetail_spring.dto.Paging;
import com.himedia.phonetail_spring.dto.ProductDTO;
import com.himedia.phonetail_spring.dto.ReportDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    IProductDAO pdao;


    public List<ProductDTO> getBrandList(String brand) {
        return pdao.getBrandList(brand);
    }

    public HashMap<String, Object> getNewList() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("newList", pdao.getNewList());
        return result;
    }

    public ProductDTO getProduct(int pseq) {
        return pdao.getProduct(pseq);
    }

    public HashMap<String, Object> getProductList(HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap<>();
        HttpSession session = request.getSession();

        // brand 별 구분
        String brand="";
        if(request.getParameter("brand")!=null){
            brand=request.getParameter("brand");
            session.setAttribute("brand",brand);
        }else if(session.getAttribute("brand")!=brand){
            brand = (String)session.getAttribute("brand");
        }else{
            session.removeAttribute("brand");
        }

        // page 구분
        int page =1;
        if(request.getParameter("page")!=null){
            page=Integer.parseInt(request.getParameter("page"));
            session.setAttribute("page",page);
        }else if(session.getAttribute("page")!=null){
            page = (Integer)session.getAttribute("page");
        }else{
            session.removeAttribute("page");
        }

        // 검색어 구분(model)
        String key="";
        if(request.getParameter("key")!=null){
            key=request.getParameter("key");
            session.setAttribute("key",key);
        }else if(session.getAttribute("key")!=null){
            key = (String)session.getAttribute("key");
        }else{
            session.removeAttribute("key");
        }

        // paging을 위한 상품전체 갯수 세기 + 현재 페이지 표시
        Paging paging = new Paging();
        paging.setPage(page);
        int count = pdao.getAllCount(brand, key, "model");
        paging.setTotalCount(count);
        paging.calPaing();
        paging.setStartNum(paging.getStartNum()-1);
        List<ReportDTO> productList = pdao.getProductList(paging,brand,key,"model");
        result.put("productList", productList);
        result.put("paging", paging);
        result.put("key", key);
        result.put("brand", brand);
        return result;

    }
}
