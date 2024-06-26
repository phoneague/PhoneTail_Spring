<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../header.jsp" %>
<link rel="stylesheet" href="css/product.css">
<div id="productWrap">
    <div class="container">
        <div class="detailInfos">
            <div class="row">
                <div class="coltitle">상품번호</div>
                <div class="col">${productDTO.pseq}</div>
                <input type="hidden" name="pseq" value="${productDTO.pseq}" />
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
                    <input type="button" id="wantToggle" value="찜" onClick="wantToggle(${productDTO.pseq}, '${login.userid}')"/>
                </c:if>
                <input type="button" value="1:1 대화"
                       onClick="go_chat(${productDTO.pseq},'${login.userid}')" />
                <input type="button"
                       onClick="go_report(${productDTO.pseq},'${login.userid}')"
                       value="신고하기">
            </div>
        </c:otherwise>
    </c:choose>

    <section id="reportForm" style="display:none">
        <article>
            <form id="reportViewForm" method="post" style="margin:0;">
                <h2>상품 신고</h2>
                <div class="field">
                    <select name="retype">
                        <option value="-1">신고사유를 선택하세요</option>
                        <option value="0">광고성 콘텐츠(거래와 관련없는 글)</option>
                        <option value="1">상품정보 부정확</option>
                        <option value="2">안전거래를 거부해요</option>
                        <option value="3">사기가 의심돼요(외부 채널 유도)</option>
                        <option value="4">전문업자 같아요</option>
                        <option value="5">기타</option>
                    </select>
                </div>
                <h5>상세 내용</h5>
                <div class="field">
                    <textarea name="recontent" rows="8" cols="40"></textarea>
                </div>
                <div class="loginbtn" style="width:120px;">
                    <input type="hidden" name="pseq" value="${productDTO.pseq}">
                    <input type="hidden" name="userid" value="${login.userid}">
                    <input type="button" id="reportButton" style=" background:black;" value="확인" >
                    <input type="button" style=" background:black;" value="취소" onClick="reportCancel()">
                </div>
            </form>
        </article>
    </section>

</div>
<%@ include file="../footer.jsp" %>