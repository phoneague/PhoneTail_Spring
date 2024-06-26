function idcheck(){
	if( document.joinForm.userid.value==""){
		alert("아이디를 입력하고 중복체크를 진행하세요" );
		document.joinForm.userid.focus();
		return;
	}
	var url = "idcheckForm?userid=" + document.joinForm.userid.value;
	var opt = "toolbar=no, menubar=no, resizable=no, width=500, height=300, scrollbars=no";

	window.open(url, "idcheck", opt);
}

$(document).ready(function() {
	$("#withDraw").click(function() {
		var userid = "${login.userid}"; // 로그인된 사용자의 아이디
		var confirmation = confirm("정말로 탈퇴하시겠습니까?");
		if (confirmation) {
			$.ajax({
				type: "POST", // POST 방식으로 요청
				url: "/deleteMember", // 회원 삭제 요청을 처리할 URL
				data: { userid: userid }, // 회원 아이디를 데이터로 전송
				success: function(response) {
					// 서버에서 정상적으로 처리된 경우
					alert("회원 탈퇴가 완료되었습니다.");
					window.location.href = "/loginForm"; // 로그인 페이지로 이동
				},
				error: function(xhr, status, error) {
					// 서버에서 오류가 발생한 경우
					alert("회원 탈퇴를 실패하였습니다. 다시 시도해주세요.");
				}
			});
		}
	});
});



function find_id(){
	var url = "findId";
	var opt = "toolbar=no, menubar=no, resizable=no, width=500, height=300, scrollbars=no";
	
	window.open(url, "findId", opt);
}

function find_pw(){
	var url = "findPw";
	var opt = "toolbar=no, menubar=no, resizable=no, width=500, height=300, scrollbars=no";
	
	window.open(url, "findPw", opt);
}

