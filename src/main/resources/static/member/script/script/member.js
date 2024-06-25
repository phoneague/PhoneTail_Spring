function idcheck(){
	if( document.joinForm.userid.value==""){
		alert("아이디를 입력하고 중복체크를 진행하세요" );
		document.joinForm.userid.focus();
		return;
	}
	var url = "phonetail.do?command=idcheck&userid=" + document.joinForm.userid.value;
	var opt = "toolbar=no, menubar=no, resizable=no, width=500, height=300, scrollbars=no";
	
	window.open(url, "idcheck", opt);
}


function post_zip(){
	var url = "phonetail.do?command=findZipnum";
	var opt = "menubar=no, scrollbars=no, width=550, height=300, top=300, left=300";
	window.open( url, "findZipNum", opt);
}

function addressOK( zip_num, sido, gugun, dong ){
	opener.document.joinForm.zip_num.value=zip_num;
	opener.document.joinForm.address1.value=sido+" "+gugun+" "+dong;
	self.close();
}


function go_save(){
	if ( document.joinForm.userid.value == "") {
		alert("아이디를 입력하여 주세요."); 
		document.joinForm.userid.focus();
	}else if( document.joinForm.reid.value != document.joinForm.userid.value){
		alert("아이디 중복확인을 하지 않았습니다");		
		document.joinForm.userid.focus();
	}else if( document.joinForm.pwd.value == "") {
	    alert("비밀번호를 입력해 주세요.");	    
	    document.joinForm.pwd.focus();
	} else if( document.joinForm.pwd.value != document.joinForm.pwdCheck.value) {
	    alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");	    
	    document.joinForm.pwd.focus();
	} else if( document.joinForm.name.value == "") {
	    alert("이름을 입력해 주세요.");	    
	    document.joinForm.name.focus();
	} else if( document.joinForm.phone.value == "") {
	    alert("전화번호를 입력해 주세요.");	   
	    document.joinForm.phone.focus();
	}else if( document.joinForm.email.value == "") {
	    alert("이메일을 입력해 주세요.");	   
	    document.joinForm.email.focus();
	} else{
	    document.joinForm.submit();
	}
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
		location.href='phonetail.do?command=deleteMember';
	}else{
		return;
	}
}

function find_id(){
	var url = "phonetail.do?command=findId";
	var opt = "toolbar=no, menubar=no, resizable=no, width=500, height=300, scrollbars=no";
	
	window.open(url, "findId", opt);
}

function find_pw(){
	var url = "phonetail.do?command=findPw";
	var opt = "toolbar=no, menubar=no, resizable=no, width=500, height=300, scrollbars=no";
	
	window.open(url, "findPw", opt);
}

