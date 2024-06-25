<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>PhoneTail</title>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Roboto+Mono:ital,wght@0,100..700;1,100..700&display=swap"
          rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Roboto+Condensed:ital,wght@0,100..900;1,100..900&display=swap"
          rel="stylesheet">

    <link rel="stylesheet" href="css/submenu.css"> <!-- submenu들 css 통합  -->
    <link rel="stylesheet" href="css/header_footer.css"> <!-- header footer의 css 통합 -->
    <link rel="stylesheet" href="css/list.css"> <!-- product, chat제외 list 들 css 통합 -->
    <link rel="stylesheet" href="css/view.css"><!-- product, chat제외 view 들 css 통합 -->
    <link rel="stylesheet" href="css/login.css"> <!-- admin,일반 로그인 폼들 css 통합 -->
    <link rel="stylesheet" href="css/chat.css"> <!-- chat css 통합 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="script/jquery-3.7.1.min.js"></script>
    <script src="script/phonetail.js"></script>
    <script src="script/product.js"></script>
    <script src="admin/script/admin.js"></script>
    <script src="report/script/report.js"></script>
    <script src="chat/ChatList.js"></script>
    <script src="member/script/member.js"></script>
    <script src="customer/script/customer.js"></script>
    <script type="text/javascript">
        $(function () {
            $('#myButton').click(function () {
                var formselect = $("#fileupForm")[0]; 	//지목된 폼을 변수에 저장
                var formdata = new FormData(formselect);	// 전송용 폼객체에 폼과 안의 데이터(이미지)를 저장
                $.ajax({	//
                    url: "<%=request.getContextPath()%>/fileup",
                    type: "POST",
                    enctype: "multipart/form-data",
                    async: false,
                    data: formdata,
                    timeout: 10000,
                    contentType: false,
                    processData: false,
                    success: function (data) {	//controller에서 리턴된 해쉬맵이 data로 전달됨
                        if (data.STATUS == 1) {
                            $("#filename").append("<div>" + data.SAVEIMAGEFILE + "</div>")
                            $("#image").val(data.IMAGE);
                            $("#saveimagefile").val(data.SAVEIMAGEFILE);
                            $("#filename").append("<img src='product_images/" + data.SAVEIMAGEFILE + "'height='150'/>")
                        }
                    },
                    error: function () {
                        alert("실패");
                    }
                });
            });
        });
    </script>

</head>
<body>

<div id="wrap">
    <header>
        <nav id="top_menu">
            <div id="logo"><img src="images/phonetail_logo2.png" height="150" onClick="location.href='/'"></div>
            <div class="gnb">
                <c:choose>
                    <c:when test="${empty login}">
                        <a href="loginForm">LOGIN</a>
                        <a href="joinForm">JOIN</a>
                    </c:when>
                    <c:otherwise>
                        <a href="mypage">${login.name}(${login.userid})</a>
                        <a href="logout">LOGOUT</a>
                    </c:otherwise>
                </c:choose>
                <a href="productInsertForm">SELL</a>
                <a href="mypage">MY PAGE</a>
                <a href="qnaList?page=1">Q&A</a>
                <a href="chatList">CHAT</a>
            </div>
            <div class="hmenu">
                <div></div>
                <div></div>
                <div></div>
            </div>
        </nav>
        <br>
        <nav id="category_menu">
            <a href="productList?page=1&brand=">All Products</a>
            <a href="productList?page=1&brand=Apple">Apple</a>
            <a href="productList?page=1&brand=Samsung">Galaxy</a>
            <a href="productList?page=1&brand=LG">Others</a>
        </nav>
        <hr style="margin:10px;">
    </header>


