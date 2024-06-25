<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<%@ include file="../header.jsp"%>
<div id="loginwrap">
	<section>
		<h2>Update Member</h2>
		<article>
			<form name="updateMemberForm" method="post" action="updateMember">
				<div class="field">
					<label>User ID</label>
					<input type="text" name="userid" value="${login.userid}" readonly />
				</div>
				<div class="field"><label>Password</label><input type="password" name="pwd"></div>
				<div class="field"><label>Retype Password</label><input type="password" name="pwdCheck"></div>
				<div class="field"><label>Name</label><input type="text" name="name" value="${login.name}"></div>
				<div class="field"><label>Phone</label><input type="text" name="phone" value="${login.phone}"></div>
				<div class="field"><label>Email</label><input type="text" name="email" value="${login.email}"></div>
				<div class="field">
					<label>Zip Code</label>
					<div>
						<input type="text" id="sample6_postcode" name="zip_num"
							   value="${login.zip_num != null ? login.zip_num : ''}" readonly/>
						<input class="checkbtn" type="button" value="우편번호 찾기" onclick="sample6_execDaumPostcode()">
					</div>
				</div>
				<div class="field">
					<label>Address</label><input type="text" name="address1" id="sample6_address"
												 value="${login.address1 != null ? login.address1 : ''}" readonly/>
				</div>
				<div class="field">
					<label>Detail Address</label><input type="text" name="address2" id="sample6_detailAddress"
														value="${login.address2 != null ? login.address2 : ''}"/>
				</div>
				<div class="field">
					<label>Extra Address</label><input type="text" name="address3" id="sample6_extraAddress"
													   value="${login.address3 != null ? login.address3 : ''}" readonly>
				</div>
				<!-- 다음 카카오 도로명 주소 검색을 위한 자바스크립트 코드들 -->
				<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
				<script>
					function sample6_execDaumPostcode() {
						new daum.Postcode({
							oncomplete: function(data) {
								var addr = '';
								var extraAddr = '';
								if (data.userSelectedType === 'R') {
									addr = data.roadAddress;
								} else {
									addr = data.jibunAddress;
								}
								if (data.userSelectedType === 'R') {
									if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
										extraAddr += data.bname;
									}
									if (data.buildingName !== '' && data.apartment === 'Y') {
										extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
									}
									if (extraAddr !== '') {
										extraAddr = ' (' + extraAddr + ')';
									}
									document.getElementById("sample6_extraAddress").value = extraAddr;
								} else {
									document.getElementById("sample6_extraAddress").value = '';
								}
								document.getElementById('sample6_postcode').value = data.zonecode;
								document.getElementById("sample6_address").value = addr;
								document.getElementById("sample6_detailAddress").focus();
							}
						}).open();
					}
				</script>
				<br>
				<div class="field">
					<div>${message}</div>
				</div>
				<div class="loginbtn">
					<input type="submit" value="정보수정">
					<input type="button" value="목록으로" onClick="location.href='/'">
				</div>
			</form>
		</article>
	</section>
</div>
<%@ include file="../footer.jsp"%>
