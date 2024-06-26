<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../header.jsp" %>

<div class="ViewContainer">
	<div class="vititle">
		<h2>'${reportDTO.userid}' 님의 신고글</h2>
	</div>
	<form name="reportViewForm" id="reportViewForm" method="post">
		<div class="vifields-horizontal"
			style="border-bottom: 2px solid #3f4549;">
			<input name="reseq" type="hidden" value="${reportDTO.reseq}" />
			<div class="vifield">
				<label>사유:<c:choose>
						<c:when test="${reportDTO.retype==0}">광고성 콘텐츠(거래와 관련없는 글)</c:when>
						<c:when test="${reportDTO.retype==1}">상품정보 부정확</c:when>
						<c:when test="${reportDTO.retype==2}">안전거래를 거부해요</c:when>
						<c:when test="${reportDTO.retype==3}">사기가 의심돼요(외부 채널 유도)</c:when>
						<c:when test="${reportDTO.retype==4}">전문업자 같아요</c:when>
						<c:when test="${reportDTO.retype==5}">기타</c:when>
					</c:choose>
				</label>
				<fmt:formatDate value="${reportDTO.indate}" type="both" />
			</div>
			
			<div class="vifield">
				<label><c:choose>
						<c:when test="${reportDTO.restate=='N'}">처리 대기중</c:when>
						<c:when test="${reportDTO.restate=='Y'}">처리완료</c:when>
						<c:when test="${reportDTO.restate=='S'}">보류상태</c:when>
					</c:choose>
				</label>
			</div>
		</div>
		<div class="vicontent">
				<div>${reportDTO.recontent}</div>
		</div>
		<div style="display:flex; justify-content:center;">
		<input type="button"
			style="width: 300px; margin-top: 5px; margin-left: 15px;"
			value="신고 대상 게시글 확인하기"
			onclick="location.href='productDetail?pseq=${reportDTO.pseq}'" />
		</div>
		<input name="pid" type="hidden" value="${reportDTO.pid}" />
		<div class="vifield">
			<c:if test="${!empty adminUser.adminid}">
			<div style="display:flex; justify-content:right;">
				<select name="newRestate">
					<option value="${reportDTO.restate}">신고처리</option>
					<option value="Y">블랙처리</option>
					<option value="S">보류하기</option>
				</select>
				<input type="hidden" value="${reportDTO.restate}" name="oldRestate">
			</div>
			</c:if>
			<div class="vifields-horizontal" style="border: none;">
				<input type="button" value="목록으로"
					onClick="location.href='adminReportList'">
				<c:if test="${!empty adminUser.adminid}">
					<input type="button" value="처리하기" onClick="processReport()">
				</c:if>
			</div>
		</div>
	</form>
</div>
<%@ include file="../footer.jsp"%>
