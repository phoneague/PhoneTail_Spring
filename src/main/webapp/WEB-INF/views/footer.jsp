<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<script src="script/jquery-3.7.1.min.js"></script>
<link  rel="stylesheet" href="/css/header_footer.css">
	<div class="clear"></div>
	<footer>
		<hr>
		<div id="copy">
			All contents Copyright 2024  PhoneTail.co  Inc. all rights reserved<br>
			Contact mail : phonetail@abc.com  Tel : +82 02 1234 1234	Fax : +82 02 1233 1233 &nbsp;
		</div>
		<div id="admin"><br>
			관리자 접속상태:
			<c:choose>
				<c:when test="${!empty adminUser.adminid}">O(${adminUser.adminid})&nbsp;
					<a href="adminReportList?page=1">관리자 페이지로 드가자</a>
					&nbsp;/&nbsp;<a href="adminLogout">로그아웃</a>
					
				</c:when>
			<c:otherwise>
				X /<a href="admin">관리자로그인</a>
			</c:otherwise>
			</c:choose> 
		</div><br><br><br>
	</footer>
	</div>
</body>
</html>

    