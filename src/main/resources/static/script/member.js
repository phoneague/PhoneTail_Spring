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

/*

$(function() {
	$('#findIdForm').submit(function(event) {
		event.preventDefault();
		// 폼 데이터 가져오기
		var formData = {
			name: $('#name').val(),
			email: $('#email').val()
		};

		// Ajax 요청
		$.ajax({
			type: 'POST',
			url: '/findId', // 서버 요청 URL
			data: formData, // 보낼 데이터
			dataType: 'json', // 응답 데이터 타입
			success: function(response) {
				if (response.success) {
					// 결과 표시
					$('#findIdResult').html('<p>당신의 아이디는 <strong>' + response.userid + '</strong>입니다.</p>');
				} else {
					// 오류 메시지 표시
					$('#findIdResult').html('<p>아이디를 찾지 못했습니다. 정확한 이름과 이메일을 입력하세요.</p>');
				}
			},
			error: function() {
				// 오류 처리
				$('#findIdResult').html('<p>서버 오류가 발생했습니다.</p>');
			}
		});
	});
});
*/


/*
$(function() {
	$('#findPwForm').submit(function(event) {
		event.preventDefault();

		// 폼 데이터 가져오기
		var formData = {
			userid: $('#userid').val(),
			email: $('#email').val()
		};

		// Ajax 요청
		$.ajax({
			type: 'POST',
			url: '/findPw', // 서버 요청 URL
			data: formData, // 보낼 데이터
			dataType: 'json', // 응답 데이터 타입
			success: function(response) {
				if (response.success) {
					// 결과 표시
					$('#findPwResult').html('<p>당신의 비밀번호는 <strong>' + response.pwd + '</strong>입니다.</p>');
				} else {
					// 오류 메시지 표시
					$('#findPwResult').html('<p>비밀번호를 찾지 못했습니다. 정확한 아이디와 이메일을 입력하세요.</p>');
				}
			},
			error: function() {
				// 오류 처리
				$('#findPwResult').html('<p>서버 오류가 발생했습니다.</p>');
			}
		});
	});
});
*/


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
