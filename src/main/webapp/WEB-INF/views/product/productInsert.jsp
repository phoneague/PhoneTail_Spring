<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../header.jsp" %>

<div id="productWrap">
    <form name="productInsertForm" method="post"
          enctype="multipart/form-data">
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
                    <div class="col"><input type="text" name="model" /></div>
                </div>
                <div class="row">
                    <div class="coltitle">가격</div>
                    <div class="col"><input type="text" name="price" /></div>
                </div>
                <div class="row">
                    <div class="coltitle">글 내용</div>
                    <div class="col"><textarea name="comment" rows="8"></textarea></div>
                </div>
                <div class="row">
                    <div class="coltitle">이미지 등록</div>
                    <div class="col"><input type="file" name="image"  onChange="show_preview(event)"></div>
                </div>
            </div>
            <div class="detailImage">
                <img id="preview" src="" width="600" />
            </div>
        </div>
        <div class="button_box">
            <input type="button" value="상품등록" onClick="go_insert()">
            <input type="button" value="목록으로"
                   onClick="location.href='productList'">
        </div>
    </form>
</div>

<%@ include file="../footer.jsp" %>