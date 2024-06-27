
$(function (){
	$("#idcheck").click(function(){
		var formselect = $("#joinForm")[0]; // 지목된 폼을 변수에 저장
		var formdata = new FormData(formselect);
		var userid = formdata.get("userid"); // 폼 데이터에서 사용자 ID 값 가져오기

		if (!userid) {
			// 사용자 ID가 비어있는 경우 경고 메시지 표시
			alert("사용하려는 아이디를 입력하고 중복체크를 진행해주세요");
			return; // AJAX 요청을 중단
		}
		$.ajax({
			type: "POST", // POST 방식으로 요청
			url: "/idcheck", // 아이디 중복 체크 요청을 처리할 URL
			async: false,
			data: formdata,
			contentType: false,
			processData: false,
			success: function(response) {
				// 서버에서 정상적으로 처리된 경우
				if (response.result === -1) {
					alert("사용 가능한 아이디입니다.");
				} else if (response.result === 1) {
					alert("이미 사용 중인 아이디입니다. 다른 아이디를 입력해주세요.");
				}
			},
			error: function(xhr, status, error) {
				// 서버에서 오류가 발생한 경우
				alert("아이디 중복 체크를 실패하였습니다. 다시 시도해주세요.");
				console.error("Error details:", status, error);
			}
		});
	});
});


$(function() {
	$("#withDraw").click(function() {
		var formselect = $("#updateMemberForm")[0]; // 지목된 폼을 변수에 저장
		var formdata = new FormData(formselect);
		var confirmation = confirm("정말로 탈퇴하시겠습니까?");
		if (confirmation) {
			$.ajax({
				type: "POST", // POST 방식으로 요청
				url: "/deleteMember", // 회원 삭제 요청을 처리할 URL
				async: false,
				data: formdata, // 회원 아이디를 데이터로 전송
				contentType: false,
				processData: false,
				success: function() {
					// 서버에서 정상적으로 처리된 경우
					alert("회원 탈퇴가 완료되었습니다.");
					window.location.href = "/loginForm"; // 로그인 페이지로 이동
				},
				error: function(xhr, status, error) {
					// 서버에서 오류가 발생한 경우
					alert("회원 탈퇴를 실패하였습니다. 다시 시도해주세요.");
					console.error("Error details:", status, error);
				}
			});
		}
	});
});

function find_id(){
	var url = "findIdResult";
	var opt = "toolbar=no, menubar=no, resizable=no, width=500, height=300, scrollbars=no";

	window.open(url, "findId", opt);
}

function find_pw(){
	var url = "findPwResult";
	var opt = "toolbar=no, menubar=no, resizable=no, width=500, height=300, scrollbars=no";

	window.open(url, "findPw", opt);
}
