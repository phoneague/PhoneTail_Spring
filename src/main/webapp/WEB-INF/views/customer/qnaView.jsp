<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../header.jsp"%>
<div class="ViewContainer">
    <div class="vititle">
        <h2>${QuestionDTO.title}</h2>
    </div>
    <form>
        <div class="vifields-horizontal" style="border-bottom: 1px solid #3f4549;">
            <div class="vifield">
                <label>작성자 : ${QuestionDTO.userid}</label>
                <fmt:formatDate value="${QuestionDTO.indate}" type="both" />
            </div>
            <div class="vifield">
                <label>${QuestionDTO.qreply == '' ? '답변대기' : '답변완료'} | ${QuestionDTO.secret == true ? '비밀글' : '공개글'}</label>
            </div>
        </div>
        <div class="vicontent">
            <div>${QuestionDTO.content}</div>
        </div>
        <hr>
        <div class="vicontent">
            <label>답변</label><div style="padding:10px;">${QuestionDTO.qreply == '' ? '답변 대기중' : QuestionDTO.qreply}</div>
        </div>
        <div class="vifields-horizontal">
            <div class="btn" >
                <input type="button" value="목록으로" onClick="location.href='qnaList'">
                <c:if test="${QuestionDTO.userid eq login.userid}">
                    <input type="button" value="삭제하기"  onClick ="if(confirm('정말로 삭제하시겠습니까?')) location.href='deleteQna?qseq=${QuestionDTO.qseq}'">
                </c:if>
                <c:if test="${!empty adminUser.adminid}">
                    <input type="button" value="답변하기" onClick="location.href='adminQnaReplyForm?qseq=${QuestionDTO.qseq}'">
                </c:if>
            </div>
        </div>
    </form>
</div>
<%@ include file="../footer.jsp"%>