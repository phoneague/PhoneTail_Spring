<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../header.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<div id="main_container">
		<div class="close-button">
        	<button type="button" class="btn-close" aria-label="Close" onClick="location.href='chatList'"></button>
        </div>
	</div>
	<div class="reply">
		<div class="mydiv3">
		<c:forEach items="${chatingList}" var="chating">
		    <c:choose>
		        <c:when test="${not empty chating.content}">
		            <div class="reply_row">
		                <div class="chat_reply_col"></div>
		                <c:set var="justify_contentValue" value="flex-start" />
		                <c:if test="${chating.userid eq loginUser}">
		                    <c:set var="justify_contentValue" value="flex-end" />
		                </c:if>
		                <div class="chat_reply_col" style="justify-content: ${justify_contentValue};">
		                    <c:set var="backgroundColor" value="#1E90FF" />
		                    <c:if test="${chating.userid eq loginUser}">
		                        <c:set var="backgroundColor" value="#00acee" />
		                    </c:if>
		                    <div class="plz" style="background-color: ${backgroundColor};">${chating.content}</div>
		                    <div class="chatdate">
		                        <fmt:formatDate value="${chating.indate}" pattern="MM/dd hh:mm" />
		                    </div>
		                </div>
		                <div class="chat_reply_col"></div>
		            </div>
		        </c:when>
			        <c:otherwise>

			        </c:otherwise>
    </c:choose>
</c:forEach>
			

		</div>
		<form name="chating" action="/insertChat" method="post">
		<div class="comment-form">
				<input type="hidden" name="userid" value="${loginUser}" />
				<input type="hidden" name="lseq" value="${lseq}" />
				<div class="chat-textarea">
						<div class="content1">
							<input type="text" name="content" autofocus>
						</div>
						<div class="chatsubmit">
							<button type="submit" class="btn btn-light" onClick="return replyCheck();">Send</button>
						</div>
				</div>
			</div>
		</form>
	</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
<%@ include file="../footer.jsp" %>