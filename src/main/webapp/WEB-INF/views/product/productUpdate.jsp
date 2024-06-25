<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../header.jsp" %>

<div id="productWrap">
    <div class="row">
        <div class="title">Product Update Form</div>
    </div>
    <form name="productUpdateForm" method="post" enctype="multipart/form-data">
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
                    <div class="col"><input type="text" name="model"  value="${ProductDTO.model}" /></div>
                </div>
                <div class="row">
                    <div class="coltitle">가격</div>
                    <div class="col"><input type="text" name="price" value="${ProductDTO.price}" /></div>
                </div>
                <div class="row">
                    <div class="coltitle">글 내용</div>
                    <div class="col"><textarea name="comment" rows="8">${ProductDTO.comment}</textarea></div>
                </div>
                <div class="row">
                    <div class="coltitle">이미지 등록</div>
                    <div class="col"><input type="file" name="image"  onChange="show_preview(event)"></div>
                </div>
                <input type="hidden" name="pseq" value="${ProductDTO.pseq}">
                <input type="hidden" name="oldimage" value="${ProductDTO.image}">
                <input type="hidden" name="oldsaveimagefile" value="${ProductDTO.saveimagefile}">
            </div>
            <div class="detailImage">
                <img id="preview" src="product/images/${ProductDTO.saveimagefile}" width="600" />
            </div>
        </div>
        <div class="button_box">
            <input type="button" value="상품수정" onClick="go_update()">
            <input
                    type="button" value="목록으로"
                    onClick="location.href='productList'">
        </div>
    </form>
</div>


<%@ include file="../footer.jsp" %>