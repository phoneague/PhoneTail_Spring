<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@ include file="header.jsp"%>
<link rel="stylesheet" href="/css/main.css">
<link rel="stylesheet" href="/css/header_footer.css">
</div>
    <div class="container">
    <div class="box"><br>
        <h1>PhoneTail</h1><br>
        <h2>당신이 선택한 중고핸드폰 직거래 전문 플랫폼</h2><br>
    </div>
    </div>
<div id="wrap">
    <div class="box1">
        <div class="box-inner">
            <img src="images/1.png">
        </div>
        <div class="box-inner">
            <img src="images/2.png">      
        </div>
        <div class="box-inner">
            <img src="images/3.png">  
        </div>
    </div>
    
    <div class="box1">
        <div class="box-inner">
            <img src="images/4.png">     
        </div>
        <div class="box-inner">
            <img src="images/5.png" style="object-position: top;">      
        </div>
        <div class="box-inner">
            <img src="images/6.png">  
        </div>
    </div>
 </div>   
     <div class="container">
    <div class="box">
        <br><h2 style="font-size:36px;">New List</h2><br>
    </div>
    </div>
    <div id="wrap">                           
<div class="container new-list-container">
    <c:forEach items="${newList}" var="newlist" varStatus="status">
        <div class="box new-list-item">
            <div class="box-inner">
                <a href="productDetail&pseq=${newlist.pseq}">
                    <img src="product/images/${newlist.saveimagefile}">
                </a>
            </div>
            <h2>
                <a href="productDetail&pseq=${newlist.pseq}">
                    ${newlist.model} - <fmt:formatNumber value="${newlist.price}" type="currency" />
                </a>
            </h2>
        </div>
    </c:forEach>
</div>
<div id="wrap">
<%@ include file="footer.jsp"%>
