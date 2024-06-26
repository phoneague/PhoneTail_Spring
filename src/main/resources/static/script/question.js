function go_search(command){
    var url="phonetail.do?command=" +command+ "&page=1"
    document.frm.action = url;
    document.frm.submit();
}
function go_write(){
    let Form = document.writeQnaForm;
    if (Form.title.value == "") {
        alert('제목을 입력하세요');
        Form.title.focus();
    } else if (Form.content.value == "") {
        alert('내용을 입력하세요.');
        Form.content.focus();
    } else {
        Form.action="writeQna"
        Form.submit();
    }
}

