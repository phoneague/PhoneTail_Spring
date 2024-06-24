<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:url var="action" value="${param.address}" />
   <!-- 페이지의 시작 -->
<div class="paging">
      <c:if test="${paging.prev}">
         <a href="${action}&page=${paging.beginPage-1}">◀</a>&nbsp;
                              </c:if>
      <c:forEach begin="${paging.beginPage}" end="${paging.endPage}"
         var="index">
         <c:if test="${index==paging.page && index!=0}">
            <%-- <span style="color: red">${index}&nbsp;</span> --%>
            <a style="font-weight:bold; border:1px solid black;" href="${action}&page=${index}">${index}&nbsp;</a>
         </c:if>
         <c:if test="${index!=paging.page && index!=0}">
            <a href="${action}&page=${index}">${index}&nbsp;</a>
         </c:if>
      </c:forEach>
      <c:if test="${paging.next}">
                                 &nbsp;<a href="${action}&page=${paging.endPage+1}">▶</a>&nbsp;
                              </c:if>
</div>
<!-- 페이지의 끝 -->