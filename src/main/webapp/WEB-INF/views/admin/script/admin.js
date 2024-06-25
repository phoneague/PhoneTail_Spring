function loginCheck(){
	if(document.loginForm.adminid.value==""){
		alert("아이디를 입력하세요");
		document.loginForm.adminid.focus();
		return false;
	}else if(document.loginForm.pwd.value==""){
		alert("패스워드를 입력하세요");
		document.loginForm.pwd.focus();
		return false;
	}else{
		return true;
	}
}

function go_search(command) {
    var form = document.forms[0];
    form.action = "phonetail.do?command=" + command+"&page=1"
    form.submit();
}

function userstate_YtoB() {
    var count = 0;
    var checkboxes = document.getElementsByName('userstate');
    console.log(checkboxes); // 디버깅 로그 추가

    // 체크박스가 한 개만 있는 경우 처리
    if (checkboxes.length === 1) {
        if (checkboxes[0].checked == true) {
            if (checkboxes[0].dataset.userstate === "B") {
                alert("해당 회원은 이미 블랙회원입니다");
                return;
            } else if (checkboxes[0].dataset.userstate === "N") {
                alert("해당 회원은 탈퇴(휴면)회원입니다");
                return;
            }
            count++;
        }
    } else { // 체크박스가 여러 개 있는 경우 처리
        for (var i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].checked == true) {
                if (checkboxes[i].dataset.userstate === "B") {
                    alert("해당 회원은 이미 블랙회원입니다");
                    return;
                } else if (checkboxes[i].dataset.userstate === "N") {
                    alert("해당 회원은 탈퇴(휴면)회원입니다");
                    return;
                } else {
                    count++;
                }
            }
        }
    }

    // 선택된 회원이 없는 경우 경고 메시지 출력
    if (count == 0) {
        alert("블랙할 회원을 선택하세요");
    } else { // 선택된 회원이 있는 경우 확인 메시지 출력
        var ans = confirm("선택한 회원을 블랙할까요?");
        if (ans) {
            document.frm.action = "phonetail.do?command=adminUserStateChangeYtoB";
            document.frm.submit();
        }
    }
}

function userstate_BtoY(){
	
	var count = 0;
	var checkboxes = document.getElementsByName('userstate');

    // 체크박스가 한 개만 있는 경우 처리
    if (checkboxes.length === 1) {
        if (checkboxes[0].checked == true) {
            if (checkboxes[0].dataset.userstate === "Y") {
                alert("해당 회원은 이미 활성회원입니다");
                return;
            } else if(checkboxes[0].dataset.userstate==="N"){
				alert("해당 회원은 탈퇴(휴면)회원입니다");
                return;
			}
                count++;
            }
    } else { // 체크박스가 여러 개 있는 경우 처리
        for (var i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].checked == true) {
                if (checkboxes[i].dataset.userstate === "Y") {
                    alert("해당 회원은 이미 활성회원입니다");
                    return;
                } else if (checkboxes[i].dataset.userstate === "N") {
                    alert("해당 회원은 탈퇴(휴면)회원입니다");
                    return;
                } else {
                    count++;
                }
            }
        }
    }
	if( document.frm.userstate.length == undefined ){
		if( document.frm.userstate.checked==true) {
			count++;
		}		
	}else{
		for(var i=0; i<document.frm.userstate.length; i++){
			if( document.frm.userstate[i].checked== true){
				count++;
			}
		}
	}	
	if( count == 0){
		alert("블랙해제할 회원을 선택하세요");
	}else{
		var ans = confirm("선택한 회원을 블랙해제할까요?");
		if( ans ){
			document.frm.action = "phonetail.do?command=adminUserStateChangeBtoY";
			document.frm.submit();
		}
	}
}

function userstate_NtoY(){
		var count = 0;
		var checkboxes = document.getElementsByName('userstate');

    // 체크박스가 한 개만 있는 경우 처리
    if (checkboxes.length === 1) {
        if (checkboxes[0].checked == true) {
            if (checkboxes[0].dataset.userstate === "Y") {
                alert("해당 회원은 이미 활성회원입니다");
                return;
            } else if(checkboxes[0].dataset.userstate==="B"){
				alert("해당 회원은 블랙회원입니다. 블랙해제를 하시려면 블랙해제 버튼을 눌러주세요");
                return;
			}
                count++;
            }
    } else { // 체크박스가 여러 개 있는 경우 처리
        for (var i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].checked == true) {
                if (checkboxes[i].dataset.userstate === "Y") {
                    alert("해당 회원은 이미 활성회원입니다");
                    return;
                } else if (checkboxes[i].dataset.userstate === "B") {
                    alert("해당 회원은 블랙회원입니다. 블랙해제를 하시려면 블랙해제 버튼을 눌러주세요");
                    return;
                } else {
                    count++;
                }
            }
        }
    }
		
	if( document.frm.userstate.length == undefined ){
		if( document.frm.userstate.checked==true) {
			count++;
		}		
	}else{
		for(var i=0; i<document.frm.userstate.length; i++){
			if( document.frm.userstate[i].checked== true){
				count++;
			}
		}
	}	
	if( count == 0){
		alert("휴면해제할 회원을 선택하세요");
	}else{
		var ans = confirm("선택한 회원을 휴면해제할까요?");
		if( ans ){
			document.frm.action = "phonetail.do?command=adminUserStateChangeNtoY";
			document.frm.submit();
		}
	}
}