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

function withDraw(){
	var ans = confirm("정말로 탈퇴할까요?");
	if(ans){
		location.href='deleteMember';
	}else{
		return;
	}
}



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

