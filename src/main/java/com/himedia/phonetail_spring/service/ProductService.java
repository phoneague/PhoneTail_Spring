package com.himedia.phonetail_spring.service;

import com.himedia.phonetail_spring.dao.IProductDAO;
import com.himedia.phonetail_spring.dto.Paging;
import com.himedia.phonetail_spring.dto.ProductDTO;
import com.himedia.phonetail_spring.dto.ReportDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        String sellstate="";
        if(request.getParameter("sellstate")!=null){
            sellstate=request.getParameter("sellstate");
            session.setAttribute("sellstate",sellstate);
        }else if(session.getAttribute("sellstate")!=null){
            sellstate = (String)session.getAttribute("sellstate");
        }else{
            session.removeAttribute("sellstate");
        }

        // paging을 위한 상품전체 갯수 세기 + 현재 페이지 표시
        Paging paging = new Paging();
        paging.setPage(page);
        int count = pdao.getAllCount(brand, sellstate, key, "model");
        paging.setTotalCount(count);
        paging.calPaing();
        paging.setStartNum(paging.getStartNum()-1);
        List<ProductDTO> productList = pdao.getProductList(paging, brand, sellstate, key,"model");
        result.put("productList", productList);
        result.put("paging", paging);
        result.put("key", key);
        result.put("brand", brand);
        result.put("sellstate", sellstate);

        // 몇 시간 전 게시글인지 확인하는 기능
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        Map<Integer, String> timeList = new HashMap<>();
        for(ProductDTO product : productList) {
            Timestamp timestamp = product.getIndate();
            LocalDateTime productIndate = timestamp.toLocalDateTime();
            Duration duration = Duration.between(productIndate, now);
            int minutes = (int) duration.toMinutes();
            int hours = (int) duration.toHours();
            int days = (int) duration.toDays();
            if(minutes<60) {
                timeList.put(product.getPseq(), minutes+"분 전");
            } else if (hours < 24) {
                timeList.put(product.getPseq(), hours+"시간 전");
            }else {
                timeList.put(product.getPseq(), days+"일 전");
            }
        }
        result.put("timeList", timeList);

        return result;

    }

    public void insertProduct(ProductDTO productdto) {
        pdao.insertProduct(productdto);
    }

    public void updateProduct(ProductDTO productdto) {
        pdao.updateProduct(productdto);
    }

    public void deleteProduct(int pseq) {
        pdao.deleteProduct(pseq);
    }

    public void plusReadcount(int pseq) {
        pdao.plusReadcount(pseq);
    }

    public void soldProduct(int pseq) {
        pdao.sold(pseq);
    }

}
