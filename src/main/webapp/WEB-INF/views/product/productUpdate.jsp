<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../header.jsp" %>
<link rel="stylesheet" href="css/product.css">

<div id="productWrap">
    <div class="row">
        <div class="title">Product Update Form</div>
    </div>
    <form name="productUpdateForm" method="post" action="productUpdate">
        <div class="container">
            <div class="detailInfos">
                <div class="row">
                    <div class="coltitle">판매자</div>
                    <div class="col">${login.getUserid()}
                        <input type="hidden" name="userid" value="${login.getUserid()}"/>
                    </div>
                </div>
                <div class="row">
                    <div class="coltitle">브랜드</div>
                    <div class="col">
                        <select name="brand">
                            <option>선택하세요</option>
                            <c:forEach items="${brandList}" var="brand" varStatus="status">
                                <c:choose>
                                    <c:when test="${ProductDTO.brand eq brand}">
                                        <option value="${brand}" selected>${brand}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${brand}">${brand}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="row">
                    <div class="coltitle">모델명</div>
                    <div class="col"><input type="text" name="model" value="${ProductDTO.model}"/></div>
                </div>
                <div class="row">
                    <div class="coltitle">가격</div>
                    <div class="col"><input type="text" name="price" value="${ProductDTO.price}"/></div>
                </div>
                <div class="row">
                    <div class="coltitle">글 내용</div>
                    <div class="col"><textarea name="comment" rows="8">${ProductDTO.comment}</textarea></div>
                </div>
                <input type="hidden" name="pseq" value="${ProductDTO.pseq}">
                <input type="hidden" name="oldimage" value="${ProductDTO.image}">
                <input type="hidden" name="oldsaveimagefile" value="${ProductDTO.saveimagefile}">
            </div>
            <div class="detailImage">
                <div id="oldimageshow">
                    <img id="preview" src="/product_images/${ProductDTO.saveimagefile}" width="400" />
                </div>
                <input type="hidden" name="image" id="image"/> <%--전송될 파일이름--%>
                <input type="hidden" name="saveimagefile" id="saveimagefile"/> <%--전송될 파일이름--%>
                <div id="filename"></div>
            </div>
        </div>
        <div class="button_box">
            <input type="submit" value="상품수정">
            <input
                    type="button" value="목록으로"
                    onClick="location.href='productList'">
        </div>
        <div class="row">
            <div class="coltitle">에러메시지</div>
            <div class="col">${message}</div>
        </div>
    </form>
    <div style="position:relative; border:1px solid black; width:500px; margin:0 auto;">
        <form name="formm" id="fileupForm" method="post" enctype="multipart/form-data">
            <input type="file" name="fileimage"/>
            <input type="button" id="myButton" value="추가"/>
        </form>
    </div>
</div>


<%@ include file="../footer.jsp" %>