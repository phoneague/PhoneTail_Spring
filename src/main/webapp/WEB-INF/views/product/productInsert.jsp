<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../header.jsp" %>
<link rel="stylesheet" href="css/product.css">

<div id="productWrap">
    <form name="productInsertForm" method="post"
          action="productInsert">
        <div class="container">
            <div class="detailInfos">
                <div class="row">
                    <div class="coltitle">판매자</div>
                    <div class="col">${loginUser}
                        <input type="hidden" name="userid" value="${loginUser}" />
                    </div>
                </div>
                <div class="row">
                    <div class="coltitle">브랜드</div>
                    <div class="col">
                        <select name="brand">
                            <option>선택하세요</option>
                            <c:forEach items="${brandList}" var="brand" varStatus="status">
                                <option value="${brand}">${brand}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="row">
                    <div class="coltitle">모델명</div>
                    <div class="col"><input type="text" name="model" value="${dto.model != null ? dto.model : ''}" /></div>
                </div>
                <div class="row">
                    <div class="coltitle">가격</div>
                    <div class="col"><input type="text" name="price" value="${dto.price != null ? dto.price : 0}" /></div>
                </div>
                <div class="row">
                    <div class="coltitle">글 내용</div>
                    <div class="col"><textarea name="comment" rows="8">${dto.comment}</textarea></div>
                </div>
            </div>
            <div class="detailImage">
                <input type="hidden" name="image" id="image"/> <%--전송될 파일이름--%>
                <input type="hidden" name="saveimagefile" id="saveimagefile"/> <%--전송될 파일이름--%>
                <div id="filename"></div>
            </div>
        </div>
        <div class="button_box">
            <input type="submit" value="상품등록">
            <input type="button" value="목록으로"
                   onClick="location.href='productList'">
        </div>
        <div class="row">
            <div class="coltitle">에러메시지</div>
            <div class="col">${message}</div>
        </div>
    </form>
    <div style="position:relative; border:1px solid black; width:500px; margin:0 auto;">
        <form name="formm" id="fileupForm" method="post" enctype="multipart/form-data">
            <input type="file" name="fileimage" />
            <input type="button" id="myButton" value="추가" />
        </form>
    </div>
</div>

<%@ include file="../footer.jsp" %>