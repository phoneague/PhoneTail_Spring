<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>findZipNum</title>
<script src="member/script/member.js"></script>
<link rel="stylesheet" href="css/login.css">
<!-- admin,일반 로그인 폼들 css 통합 -->
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap"
	rel="stylesheet">
<link rel="stylesheet" href="css/list.css">
<style type="text/css">
body {
	/* display: flex; flex-direction:column; height:100vh; align-items:center; padding:20px; */
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
</style>
</head>
<body>
	<div id="popup">
		<form method="post" name="formm"
			action="phonetail.do?command=findZipnum">
			동 이름 : <input name="dong" type="text"> <input type="submit"
				value="찾기" class="submit">
		</form>
<hr>
		<div class="litb">
			<div class="row">
				<div class="coltitle">우편번호</div>
				<div class="coltitle">주소</div>
			</div>
			<c:forEach items="${addressList}" var="add">
				<div class="row">
					<div class="col"><a style="color:blue;" href="#"
						onClick="addressOK('${add.zip_num}', '${add.sido}' , '${add.gugun}' , '${add.dong}')">
							${add.zip_num} </a></div>
				<div class="col"><a href="#" style="color:blue;"
						onClick="addressOK('${add.zip_num}', '${add.sido}' , '${add.gugun}' , '${add.dong}')">
							${add.sido} ${add.gugun} ${add.dong} </a></div>
				</div>
			</c:forEach>
			</table>
		</div>
</body>
</html>



