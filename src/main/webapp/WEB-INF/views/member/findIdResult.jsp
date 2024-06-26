<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="/script/member.js"></script>
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
	<h1>Find ID Result</h1>
	<form method="post" id="findIdResult" action="/findId">
		<p>이&nbsp;&nbsp;&nbsp;름 : <input name="name" id="name" type="text"></p>
		<p>이메일 : <input name="email" id="email" type="text"></p>
		<input type="submit" value="아이디찾기" class="submit">
	</form>
	<hr>
	<c:choose>
		<c:when test="${not empty userid}">
			<p>당신의 아이디는 <strong>${userid}</strong>입니다.</p>
		</c:when>
		<c:otherwise>
			<p>아이디를 찾지 못했습니다. 정확한 이름과 이메일을 입력하세요.</p>
		</c:otherwise>
	</c:choose>
	<hr>
</div>

</body>
</html>
    