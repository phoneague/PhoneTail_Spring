function go_psearch(command) {
	let Form = document.productListForm;
	var url = command + "&page=1"
	Form.action = url;
	Form.submit();
}
function go_updateForm(pseq) {
	alert("상품을 수정하시겠습니까?");
	window.location.href = "productUpdateForm?pseq=" + pseq;
}

function go_delete(pseq) {
	let answer = confirm("정말로 삭제하시겠습니까?");
	if (answer) {
		window.location.href = "productDelete?pseq=" + pseq;
	}
}

function go_want(pseq, userid) {
	if (userid == "") {
		alert("로그인해야 찜을 할 수 있습니다.");
	} else {
		window.location.href = "productWant&pseq=" + pseq + "&userid=" + userid;
	}
}


function sold(pseq) {
	let answer = confirm("거래를 완료하셨나요?");
	if (answer) {
		window.location.href = "productSold?pseq=" + pseq;
	}
}


function handleSellstateChange() {
	var selectBox = document.getElementById('sellstateSelect');
	var selectedValue = selectBox.value;
	window.location.href = 'productList?page=1&sellstate=' + selectedValue;
}

