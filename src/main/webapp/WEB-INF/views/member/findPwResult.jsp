<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="member/script/member.js"></script>
<link rel="stylesheet" href="css/login.css">
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap"
	rel="stylesheet">
<link rel="stylesheet" href="css/list.css">
<style type="text/css">
body {
	font-family: "Noto Sans KR", sans-serif;
	height: 200px;
}

input[type=text] {
	box-sizing: border-box;
	border: solid 2px black;
	border-radius: 5px;
	font-family: "Noto Sans KR", sans-serif;
}

input[type=submit], input[type=button] {
	font-family: "Noto Sans KR", sans-serif;
	background: black;
	color: white;
	border-radius: 5px;
}

p{
	font-family: "Noto Sans KR", sans-serif;
	font-size: 14px;
}
</style>
</head>
<body>
	<div id="popup">
		<h1>Find Pwd Result</h1>
		<form method="post" name="formm"
			action="phonetail.do?command=findPw">
			<p>아이디 : <input name="userid" type="text"></p>
			<p>이메일 : <input name="email" type="text"></p>
			<input type="submit" value="비밀번호찾기" class="submit">
		</form>
		<hr>
		<c:choose>
			<c:when test="${not empty pwd}">
				<p>당신의 비밀번호는 <strong>${pwd}</strong>입니다.</p>
			</c:when>
			<c:otherwise>
				<p>비밀번호를 찾지 못했습니다. 정확한 아이디와 이메일을 입력하세요.</p>
			</c:otherwise>
		</c:choose>
		<hr>
	</div>
</body>
</html>
    