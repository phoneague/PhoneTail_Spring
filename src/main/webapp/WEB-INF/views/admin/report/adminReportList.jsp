<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../../header.jsp"%>
<div class="lititle">
    <h2>Report List</h2>
</div>
<div class="content-wrapper">
    <%@ include file="../admin_submenu.jsp"%>
    <div class="container">
        <form method="post" name="frm">
            <div class="litb">
                <div class="row">
                    <div class="col" style="display: flex; align-items: center;">
                        아이디 &nbsp; <input type="text" name="key" value="${key}" /> &nbsp;
                        &nbsp; &nbsp; <input class="searchbtn" type="button"
                                             name="btn_search" value="검색"
                                             onClick="go_search('adminReportList')" /> &nbsp;&nbsp;&nbsp;
                    </div>
                </div>
                <div class="row">
                    <div class="coltitle">번호</div>
                    <div class="coltitle">아이디</div>
                    <div class="coltitle">타입</div>
                    <div class="coltitle">신고날짜</div>
                    <div class="coltitle">상태</div>
                </div>
                <c:forEach items="${reportList}" var="reportDTO">
                    <div class="row">
                        <div class="col">${reportDTO.reseq}</div>
                        <div class="col">
                            <a
                                    href="reportView?reseq=${reportDTO.reseq}">${reportDTO.userid}님의
                                신고글</a>
                        </div>
                        <div class="col">
                            <c:choose>
                                <c:when test="${reportDTO.retype==0}">광고성 콘텐츠(거래와 관련없는 글)</c:when>
                                <c:when test="${reportDTO.retype==1}">상품정보 부정확</c:when>
                                <c:when test="${reportDTO.retype==2}">안전거래를 거부해요</c:when>
                                <c:when test="${reportDTO.retype==3}">사기가 의심돼요(외부 채널 유도)</c:when>
                                <c:when test="${reportDTO.retype==4}">전문업자 같아요</c:when>
                                <c:when test="${reportDTO.retype==5}">기타</c:when>
                            </c:choose>
                        </div>
                        <div class="col">
                            <fmt:formatDate value="${reportDTO.indate}" type="date" />
                        </div>
                        <div class="col">
                            <c:choose>
                                <c:when test="${reportDTO.restate=='N'}">처리 대기중</c:when>
                                <c:when test="${reportDTO.restate=='Y'}">처리완료</c:when>
                                <c:when test="${reportDTO.restate=='S'}">보류상태</c:when>
                            </c:choose>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <jsp:include page="../../paging.jsp">
                <jsp:param value="adminReportList"
                           name="address" />
            </jsp:include>
        </form>
    </div>
</div>
<%@ include file="../../footer.jsp"%>