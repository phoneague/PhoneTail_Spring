<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../header.jsp" %>
<link rel="stylesheet" href="css/product.css">
<div id="productWrap">
    <div class="container">
        <div class="detailInfos">
            <div class="row">
                <div class="coltitle">상품번호</div>
                <div class="col">${productDTO.pseq}</div>
            </div>
            <div class="row">
                <div class="coltitle">판매자</div>
                <div class="col">${productDTO.userid}
                    <c:choose>
                        <c:when test="${userStates[productDTO.userid] eq 'B'}">
                                    <span style="color: black; font-weight: bold;">
                                       &nbsp; 블랙리스트
                                    </span>
                        </c:when>
                        <c:when test="${userStates[productDTO.userid] eq 'N'}">
                                	<span style="color: black; font-weight: bold;">
                                       &nbsp; 탈퇴(휴면)회원
                                    </span>
                        </c:when>
                    </c:choose>
                </div>
            </div>
            <div class="row">
                <div class="coltitle">브랜드</div>
                <div class="col">${productDTO.brand}</div>
            </div>
            <div class="row">
                <div class="coltitle">모델</div>
                <div class="col">${productDTO.model}</div>
            </div>
            <div class="row">
                <div class="coltitle">가격</div>
                <div class="col"><fmt:formatNumber value="${productDTO.price}" type="currency" /></div>
            </div>
            <div class="row">
                <div class="coltitle">글 내용</div>
                <div class="col">${productDTO.comment}</div>
            </div>
            <div class="row">
                <div class="coltitle">판매상태</div>
                <div class="col">
                    <c:choose>
                        <c:when test="${productDTO.sellstate eq 'Y'}">
                                    <span style="color: red; font-weight: bold;">
                                       &nbsp; 판매완료
                                    </span>
                        </c:when>
                        <c:when test="${productDTO.sellstate eq 'N'}">
                                	<span style="color: black; font-weight: bold;">
                                       &nbsp; 판매중
                                    </span>
                        </c:when>
                    </c:choose>
                </div>
            </div>
            <div class="row">
                <div class="coltitle">등록일</div>
                <div class="col"><fmt:formatDate value="${productDTO.indate}" pattern="yyyy/MM/dd hh:mm"/></div>
            </div>
            <div class="row">
                <div class="coltitle">조회수</div>
                <div class="col">${productDTO.readcount}</div>
            </div>
            <div class="row">
                <div class="coltitle">찜 수</div>
                <div class="col">${productDTO.wantcount}</div>
            </div>

        </div>
        <div class="detailImage">
            <img src="product_images/${productDTO.saveimagefile}" width="600" />
        </div>
    </div>
    <c:choose>
        <c:when test="${productDTO.userid eq login.userid}">
            <div class="button_box">
                <c:choose>
                    <c:when test="${productDTO.sellstate eq 'N'}">
                        <input type="button" value="판매확정" onClick="sold(${productDTO.pseq})" />
                        <input type="button" value="상품수정"
                               onClick="go_updateForm(${productDTO.pseq})" />
                    </c:when>
                </c:choose>
                <input type="button" value="상품삭제"
                       onClick="go_delete(${productDTO.pseq})" />
            </div>
        </c:when>
        <c:otherwise>
            <div class="button_box">
                <c:if test="${productDTO.sellstate eq 'N'}">
                    <input type="button" value="찜하기" onClick="go_want(${productDTO.pseq}, '${login.userid}')"/>
                </c:if>
                <input type="button" value="1:1 대화"
                       onClick="go_chat(${productDTO.pseq},'${login.userid}')" />
                <input type="button"
                       onClick="go_report(${productDTO.pseq},'${login.userid}')"
                       value="신고하기">
            </div>
        </c:otherwise>
    </c:choose>


</div>
<%@ include file="../footer.jsp" %>