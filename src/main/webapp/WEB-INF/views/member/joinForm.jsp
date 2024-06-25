<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<%@ include file="../header.jsp"%>
<div id="loginwrap">
<section>
<h2>Join Us</h2>
		<article>
				<form action="join" method="post" name="joinForm">
					<div class="field">
						<label>User ID</label>
						<div>
							<input type="text" name="userid" size="12">
							<input type="hidden" name="reid" >
				        	<input class="checkbtn" type="button" value="중복 체크" onclick="idcheck()">
				        </div>
					</div>
					<div class="field"><label>Password</label><input type="password"  name="pwd"></div>
					<div class="field">
						<label>Retype Password</label><input type="password"  name="pwdCheck"></div>
					<div class="field"><label>Name</label><input type="text"  name="name" ></div>
					<div class="field"><label>Phone</label><input type="text"  name="phone"  /></div>
					<div class="field"><label>E-Mail</label><input type="text"  name="email"  /></div>
					<div class="field">
						<label>Zip Code</label>
						<div>
							 <input type="text" name="zip_num" readonly>
							 <input class="checkbtn" type="button" value="우편번호 찾기" onclick="post_zip()">
						</div>
					</div>
					<div class="field">
						<label>Address</label><input type="text"  name="address1"  readonly />
					</div>
					<div class="field">
						<label>detail Address</label><input type="text" name="address2" />
					</div>
					
					<div class="loginbtn">
			   			<input type="button" value="Join" onclick="go_save()">
			   			<input type="button" value="Main" onClick="location.href='phonetail.do?command=main'">	
			   		</div>
				</form>
		</article>
	</div>
</section>
<%@ include file="../footer.jsp"%>
</div>

