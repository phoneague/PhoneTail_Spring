<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../header.jsp"%>
<link rel="stylesheet" href="/css/product.css">
<div class="lititle">
    <h2>My WantList</h2>
</div>
<div class="content-wrapper">
    <%@ include file="mypage_submenu.jsp"%>
    <div class="container">
        <form name="productListForm" method="post">
            <div class="row">
                <div class="col" style="display: flex; align-items: center; border:none;">
                    &nbsp; <input type="text" name="key" value="${key}" style="border: 1px solid black;"/> &nbsp;
                    &nbsp; &nbsp; <input class="btn" type="button" name="btn_search"
                                         value="검색" onClick="go_search('productList')" />
                    &nbsp;&nbsp;&nbsp;
                </div>
            </div>
            <div class="tb">
                <div class="products">
                    <c:forEach items="${productList}" var="productDTO">
                        <div class="product">
                            <div class="productImage">
                                <a
                                        href="productDetail?pseq=${productDTO.pseq}"><img
                                        src="/product/images/${productDTO.saveimagefile}" width="350px" /></a>
                            </div>
                            <div class="productModel">
                                <a
                                        href="productDetail?pseq=${productDTO.pseq}">모델명
                                    : ${productDTO.model}</a>
                            </div>
                            <div class="productPrice">가격 : ${productDTO.price}</div>
                            <div class="productUserid">판매자 : ${productDTO.userid}
                                <c:choose>
                                    <c:when test="${userStates[productDTO.userid] eq 'B'}">
                                    <span style="color: black; font-weight: bold;">
                                        블랙리스트
                                    </span>
                                    </c:when>
                                    <c:when test="${userStates[productDTO.userid] eq 'N'}">
                                	<span style="color: black; font-weight: bold;">
                                        탈퇴(휴면)회원
                                    </span>
                                    </c:when>
                                </c:choose>
                            </div>
                            <div class="productSold">
                                <c:choose>
                                    <c:when test="${productDTO.sellstate eq 'Y'}">
                                        판매완료
                                    </c:when>
                                    <c:otherwise>
                                        판매중
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <jsp:include page="../paging.jsp">
                <jsp:param value="myWantList" name="address" />
            </jsp:include>
        </form>
    </div>
</div>
<%@ include file="../footer.jsp"%>