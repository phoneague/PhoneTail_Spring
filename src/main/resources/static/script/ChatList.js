function replyCheck(){
	if( document.chating.content.value==""){
		alert("내용을 입력하세요");
		document.chating.content.focus();
		return false;
	}
	return true;
}

function go_chat(pseq,userid){
	if(userid==""){
		location.href='loginForm';
	}else{
		location.href='insertChatList?pseq='+pseq;
	}
}

function go_searcha(command) {
    let Form = document.forms["chatListForm"];
    if (Form) {
        var url = command + "?page=1"
        Form.action = url;
        Form.submit();
    } else {
        console.error("폼이 정의되지 않았습니다.");
    }
}
