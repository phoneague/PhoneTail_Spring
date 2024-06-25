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


function updateCheck(){
	if( document.joinForm.pwd.value==""){
		alert("비밀번호를 입력하세요");
		document.joinForm.pwd.focus();
		return false;
	}else if(  document.joinForm.pwdCheck.value != document.joinForm.pwd.value ){
		alert("비밀번호 확인이 일치하지 않습니다");
		document.joinForm.pwd_check.focus();
		return false;
	}else if(  document.joinForm.name.value==""){
		alert("이름을 입력하세요");
		document.joinForm.name.focus();
		return false;
	}else if(  document.joinForm.email.value==""){
		alert("이메일을 입력하세요");
		document.joinForm.email.focus();
		return false;
	}else if(  document.joinForm.phone.value==""){
		alert("전화번호를 입력하세요");
		document.joinForm.phone.focus();
		return false;
	}else{
		return true;
	}
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

