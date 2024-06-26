<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../header.jsp" %>
<%--<h2>하위~~~~${loginUser} , ${chatingList.indate}</h2>--%>
<div id="main_container">
	<h2> ${chatList.sid} , ${chatList.bid.toString()} 의 채팅방</h2>
		<div class="close-button">
        	<button type="button" class="btn-close" aria-label="Close" onClick="location.href='chatList'"></button>
        </div>
	</div>
	<div class="reply">
		<div class="mydiv3">
		<c:forEach items= "${chatingList}" var="chating">
		    <c:choose>
		        <c:when test="${not empty chating.content}">
		            <!-- 채팅 내용이 있는 경우에만 표시 -->
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
			            <!-- 채팅 내용이 없는 경우는 숨김 -->
			            <!-- 아무 작업도 하지 않음 -->
			        </c:otherwise>
    </c:choose>
</c:forEach>
		
		
		&lt;%&ndash; <c:forEach items="${chatingList}" var="chating">
				<div class="reply_row" >
					<div class="chat_reply_col"></div>
					<c:set var="justify_contentValue" value="flex-start" />
							<c:if test="${chating.userid eq loginUser}" >
							    <c:set var="justify_contentValue" value="flex-end" />
							</c:if>
					<div class="chat_reply_col" style="justify-content: ${justify_contentValue};">
						<c:set var="backgroundColor" value="#1E90FF" />
							<c:if test="${chating.userid eq loginUser}" >
							    <c:set var="backgroundColor" value="#00acee" />
							</c:if>
							<div class="plz" style="background-color: ${backgroundColor};">${chating.content}</div>
							<div class="chatdate">
								<fmt:formatDate value="${chating.indate}" pattern="MM/dd hh:mm"/>
							</div>
					</div>
					<div class="chat_reply_col">
						
					</div>
				</div>
			</c:forEach> &ndash;%&gt;
		
			
	
		</div>
		<form name="chating">
		<div class="comment-form">
				<input type="hidden" name="command" value="insertChat" /> 
				<input type="hidden" name="loginUser" value="${loginUser}" />
				<input type="hidden" name="lseq" value="${chatList.lseq}" />
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