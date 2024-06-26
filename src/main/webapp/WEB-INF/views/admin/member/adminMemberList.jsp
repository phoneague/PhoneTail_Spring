<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../../header.jsp" %>
<div class="lititle">
    <h2>Member List</h2>
</div>
<div class="content-wrapper">
    <%@ include file="../admin_submenu.jsp" %>
    <div class="container">
        <form name="frm">
            <div class="litb">
                <div class="row">
                    <div class="col" style="display: flex; align-items: center;">
                        아이디&nbsp;&nbsp;&nbsp; <input type="text" name="key" value="${key}"/>&nbsp;&nbsp;&nbsp;
                        <select class="userstate" name="userstate">
                            <option value="">전체</option>
                            <option value="B">블랙회원</option>
                            <option value="N">탈퇴(휴면)회원</option>
                            <option value="Y">일반회원</option>
                        </select>&nbsp;&nbsp;&nbsp; <input class="searchbtn" type="button"
                                                           name="btn_search" value="검색"
                                                           onClick="go_search('adminMemberList')"/> &nbsp;&nbsp;&nbsp;
                    </div>
                </div>
                <div class="row"
                     style="justify-content: right; border-top: none; border-bottom: none;">
                    선택한 회원을 &nbsp;&nbsp; <input class="searchbtn" type="button"
                                                value="블랙리스트" name="userstateYtoB" onClick="userstate_YtoB()"/>&nbsp;&nbsp;
                    <input class="searchbtn" type="button" value="블랙해제"
                           name="userstateBtoY" onClick="userstate_BtoY()"/>&nbsp;&nbsp; <input
                        class="searchbtn" type="button" value="휴면해제" name="userstateNtoY"
                        onClick="userstate_NtoY()"/>&nbsp;&nbsp;
                </div>
                <div class="mrow">
                    <div class="coltitle">아이디</div>
                    <div class="coltitle">이름</div>
                    <div class="coltitle">이메일</div>
                    <div class="coltitle">주소</div>
                    <div class="coltitle">전화</div>
                    <div class="coltitle">가입일</div>
                </div>
                <c:forEach items="${memberList}" var="memberDTO" varStatus="status">
                <div class="mrow">
                    <div class="col"
                    ">
                        ${memberDTO.userid}(${memberDTO.userstate}) <input
                        type="checkbox" name="userstate" value="${memberDTO.userid}"
                        data-userstate="${memberDTO.userstate}"/><br>
                </div>
                <div class="col">${memberDTO.name}</div>
                <div class="col">${memberDTO.email}</div>
                <div class="col">${memberDTO.address1}${memberDTO.address2}</div>
                <div class="col">${memberDTO.phone}</div>
                <div class="col">
                    <fmt:formatDate value="${memberDTO.indate}"/>
                </div>
            </div>
            </c:forEach>
    </div>
    <jsp:include page="../../paging.jsp">
        <jsp:param value="adminMemberList"
                   name="address"/>
    </jsp:include>
    </form>
</div>
</div>
<%@ include file="../../footer.jsp" %>