<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../header.jsp" %>
<link rel="stylesheet" href="css/product.css">
<div id="productWrap">
    <form name="productListForm" method="get">
        <div class="row">
            <div class="title">
                <h1>Product List</h1>
            </div>
        </div>
        <div class="row">
            <div class="col" style="border: none;">
                <div class="btn">
                    <input type="button" value="상품등록" id="sellButton"
                           onClick="location.href='productInsertForm'"/>
                </div>

                <div class="select_btn">
                    <select id="sellstateSelect" onchange="handleSellstateChange()" style="border: 1px solid black;">
                        <option value="">-- Select State --</option>
                        <option value="">전체 상품보기</option>
                        <option value="N">판매중 상품보기</option>
                        <option value="Y">판매완료 상품보기</option>
                    </select>
                </div>
            </div>
            <div class="col"
                 style="display: flex; align-items: center; border: none;">
                &nbsp; <input type="text" name="key" value="${key}" style="border: 1px solid black;" /> &nbsp;
                &nbsp; &nbsp; <input class="btn" type="button" name="btn_search"
                                     value="검색" onClick="go_psearch('productList')" />
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
                                src="product_images/${productDTO.saveimagefile}" width="350px" /></a>
                    </div>
                    <div class="productModel">
                        <a
                                href="productDetail?pseq=${productDTO.pseq}">모델명
                            : ${productDTO.model}</a>
                    </div>
                    <div class="productPrice">가격 : <fmt:formatNumber value="${productDTO.price}" type="currency" /></div>
                    <div class="productUserid"> 판매자 : ${productDTO.userid}</div>

                    <c:choose>
                    <c:when test="${productDTO.sellstate eq 'Y'}">
                    <div class="productSold">
                        판매완료
                        </c:when>
                        <c:otherwise>
                        <div class="productSold" style="color:#CD426B;">
                            판매중
                            </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="productChat" float="left">

                            <c:if test="${productChatList[productDTO.pseq]!=null}">
                                💬 ${productChatList[productDTO.pseq]}
                            </c:if>
                        </div>
                        <div class="productWantcount" style="color:red;">
                            <c:if test="${productDTO.wantcount != 0}">
                                ♥ ${productDTO.wantcount}
                            </c:if>
                        </div>
                        <div class="productDate" style="color: gray;">
                                ${timeList[productDTO.pseq]}
                        </div>
                    </div>
                    </c:forEach>
                </div>

            </div>
    </form>

    <div class="paging" >
        <!-- prev 버튼 표시 여부 -->
        <c:if test="${paging.prev}">
            <a href="productList?brand=${brand}&page=${paging.beginPage-1}">◀</a>&nbsp;
        </c:if>

        <!--  beginPage 부터  endPage 까지 일렬로 페이지를 표시 -->
        <c:forEach begin="${paging.beginPage}" end="${paging.endPage}"  var="index">
            <c:if test="${paging.page == index}">
                <span style="color:red">&nbsp;${index}&nbsp;</span>
            </c:if>
            <c:if test="${paging.page != index}">
                <a href="productList?brand=${brand}&page=${index}">&nbsp;${index}&nbsp;</a>
            </c:if>
        </c:forEach>

        <!-- next 버튼 표시 여부 -->
        <c:if test="${paging.next}">
            <a href="productList?brand=${brand}&page=${paging.endPage+1}">▶</a>
        </c:if>
    </div>
</div>
<%@ include file="../footer.jsp" %>
