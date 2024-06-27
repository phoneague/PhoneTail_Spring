<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../../header.jsp"%>

	<form action="adminQnaReply" method="post">
		<input type="hidden" name="qseq" value="${QuestionDTO.qseq}" />
		<div class="vifields-horizontal">
			<div class="vifield">
				<label>작성자</label>
				<div>${QuestionDTO.userid}</div>
			</div>
			<div class="vifield">
				<label>제 목</label>
				<div>${QuestionDTO.title}</div>
			</div>
			<div class="vifield">
				<label>등록일</label>
				<div>
					<fmt:formatDate value="${QuestionDTO.indate}" type="date" />
				</div>
			</div>
		</div>
		<div class="vicontent">
			<label>질문내용</label>
			<div>${QuestionDTO.content}</div>
		</div>

		<div class="vicontent">
			<label>답변내용</label>
			<textarea id="qreply" name="qreply" rows="8" cols="80">${QuestionDTO.qreply}</textarea>
		</div>

		<div class="vifields-horizontal">
			<div class="btn">
				<input type="button" value="목록으로" onclick="location.href='qnaList'">
				<input type="submit" value="답변완료">
			</div>
		</div>
	</form>
</div>
<%@ include file="../../footer.jsp"%>
