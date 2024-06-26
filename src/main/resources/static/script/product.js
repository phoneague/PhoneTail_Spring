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
        window.location.href = "wantToggle?pseq=" + pseq + "&userid=" + userid;
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
    window.location.href = 'productList?page=1&brand=&sellstate=' + selectedValue;
}

$(function previewImg() {
    $('#myButton').click(function () {
        var formselect = $("#fileupForm")[0]; 	//지목된 폼을 변수에 저장
        var formdata = new FormData(formselect);	// 전송용 폼객체에 폼과 안의 데이터(이미지)를 저장
        $.ajax({	//
            url: "/fileup",
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

function wantToggle(pseq, userid) {
    $(function () {
        $.ajax({
            url: "/wantToggle",
            type: "POST",
            async: true,
            data: `pseq=${pseq}&userid=${userid}`,
            timeout: 10000,
            success: function (result) {
                if (result.STATUS === 'Y') {
                    alert("상품을 찜했습니다");
                } else if (result.STATUS === "N") {
                    alert("상품의 찜을 해제했습니다.");
                } else {
                    alert("알 수 없는 오류가 발생했습니다. 관리자에게 문의하세요");
                }
            },
            error: function () {
                alert("실패");
            }
        });
    });
}


function toggleWishlist(productId) {
    $.ajax({
        url: contextPath + "/wishlist/toggle",
        type: "POST",
        data: {productId: productId},
        success: function (response) {
            if (response.status === "success") {
                if (response.isWished) {
                    alert("상품을 찜했습니다.");
                    // UI 업데이트 로직: 찜 버튼을 '찜 취소'로 변경, 찜 횟수 증가 등
                } else {
                    alert("상품 찜을 취소했습니다.");
                    // UI 업데이트 로직: 찜 버튼을 '찜하기'로 변경, 찜 횟수 감소 등
                }
            } else {
                alert("에러가 발생했습니다: " + response.message);
            }
        },
        error: function () {
            alert("서버와의 통신 중 오류가 발생했습니다.");
        }
    });
}
