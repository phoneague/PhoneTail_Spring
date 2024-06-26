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

/*
$(function imagePreview() {
	$('#myButton').click(function () {
		var formselect = $("#fileupForm")[0]; 	//지목된 폼을 변수에 저장
		var formdata = new FormData(formselect);	// 전송용 폼객체에 폼과 안의 데이터(이미지)를 저장
		$.ajax({	//
			url: "<%=request.getContextPath()%>/fileup",
			type: "POST",
			enctype: "multipart/form-data",
			async: false,
			data: formdata,
			timeout: 10000,
			contentType: false,
			processData: false,
			success: function (data) {	//controller에서 리턴된 해쉬맵이 data로 전달됨
				if (data.STATUS == 1) {
					$("#filename").append("<div>" + data.SAVEIMAGEFILE + "</div>")
					$("#image").val(data.IMAGE);
					$("#saveimagefile").val(data.SAVEIMAGEFILE);
					$("#filename").append("<img src='product_images/" + data.SAVEIMAGEFILE + "'height='150'/>")
				}
			},
			error: function () {
				alert("실패");
			}
		});
	});
});
*/


