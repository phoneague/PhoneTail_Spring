<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../header.jsp"%>
<div id="loginwrap">
    <section>
        <article>
            <form name="writeQnaForm" method="post" action="writeQna">
                <h2>QnA Write</h2>
                <div class="field">
                    <label>작성자</label>
                    <input type="text" name="userid" value="${login.userid}" readonly />
                </div>
                <div class="field">
                    <label>제 &nbsp;&nbsp;목</label>
                    <input type="text" name="title" />
                </div>
                <label>질문내용</label>
                <div class="field">
                    <textarea name="content" rows="10" cols="150"></textarea>
                </div>
                <div>
                    <div>
                        <input type="checkbox" id="secretCheckbox" value="True"
                               name="secret" />
                        <label for="secretCheckbox">비밀글</label>
                    </div>
                </div>
                <div class="loginbtn">
                    <input type="submit" value="제출하기" onclick="go_write()"/>
                    <input type="button" value="목록으로" onclick="location.href='qnaList'" />
                </div>
            </form>
        </article>
    </section>
</div>

<%@ include file="../footer.jsp"%>