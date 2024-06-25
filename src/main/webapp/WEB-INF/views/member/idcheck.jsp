<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>idcheck</title>
<link rel="stylesheet" href="/css/login.css"> <!-- admin,일반 로그인 폼들 css 통합 -->
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">
<script type="text/javascript">
	function idok(userid){
		opener.document.joinForm.userid.value = userid;
		opener.document.joinForm.reid.value = userid;
		self.close();
	}
</script>

<style type="text/css">
body{
display: flex; flex-direction:column; height:100vh; align-items:center; padding:20px;
font-family: "Noto Sans KR", sans-serif;
height:200px;
}
input[type=text]{
box-sizing: border-box;
border: solid 2px black;
border-radius: 5px;
font-family: "Noto Sans KR", sans-serif;
}
input[type=submit], input[type=button]{
font-family: "Noto Sans KR", sans-serif;
background:black;
color:white;
border-radius: 5px;
}
</style>

</head>
<body>
<!-- 재검색창 -->
<form style="margin:20px 0; ">
	<input type="hidden" name="command" value="idcheck" />
	아이디 : <input type="text" name="userid" value="${userid}" /><!-- value 에 확인된 아이디 표시 -->
	<input type="submit" value="중복체크" />
</form>

<!-- 사용 가능/불가능 -->
<div>
	<c:choose>
		<c:when test="${result==1}" >
			<script type="text/javascript">
				opener.document.join.userid.value="";
				opener.document.join.reid.value="";
			</script>
			${userid}는 이미 사용 중인 아이디입니다.
		</c:when>
		<c:otherwise>
			${userid}는 사용 가능한 아이디입니다. <input type="button" value="사용" onClick="idok('${userid}')" />
		</c:otherwise>
	</c:choose>
</div>
</body>
</html>



