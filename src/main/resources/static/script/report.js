function go_report(pseq, userid) {
    if (userid == "") {
        location.href = 'loginForm';
    } else {
        var reportForm = document.getElementById('reportForm');
        if (reportForm) {
            reportForm.style.display = 'block';
        }
        // var url = "reportForm?pseq="+pseq;
        // var opt = "menubar=no, scrollbars=no, width=500, height=400, top=300, left=300";
        // window.open( url, "reportForm", opt);
    }
}

function report() {
    let Form = document.reportViewForm;
    if (Form.retype.value == -1) {
        alert("신고 타입을 선택하세요");
    } else if (Form.recontent.value == "") {
        alert("신고 내용을 입력해주세요");
    } else {
        Form.action = "report";
        Form.submit();
    }
}

function reportCancel() {
    var reportForm = document.getElementById('reportForm');
    if (reportForm) {
        reportForm.style.display = 'none';
    }
}

function processReport() {
    if (confirm("처리하시겠습니까")) {
        $(function () {
            var formselect = $("#reportViewForm")[0]; 	//지목된 폼을 변수에 저장
            var formdata = new FormData(formselect);	// 전송용 폼객체에 폼과 안의 데이터(이미지)를 저장
            $.ajax({	//
                url: "/processReport",
                type: "POST",
                async: false,
                data: formdata,
                timeout: 10000,
                contentType: false,
                processData: false,
                success: function (data) {
                    if (data.status == 0) {
                        alert("이미 처리 되었거나 신고처리를 선택하지 않았습니다.");
                    }else if(data.status == 1){
                        alert("처리가 완료되었습니다.");
                    } else {
                        alert("알 수 없는 에러 발생.");
                    }
                },
                error: function () {
                    alert("실패");
                }
            });
        });
    }
}

function closeWindow() {
    window.close();
}

$(function () {
    $('#reportButton').click(function () {
        var formselect = $("#reportViewForm")[0]; 	//지목된 폼을 변수에 저장
        var formdata = new FormData(formselect);	// 전송용 폼객체에 폼과 안의 데이터(이미지)를 저장

        // var pseq = formdata.get('pseq');
        // console.log(pseq);

        $.ajax({	//
            url: "/report",
            type: "POST",
            async: false,
            data: formdata,
            timeout: 10000,
            contentType: false,
            processData: false,
            success: function (data) {	//controller에서 리턴된 해쉬맵이 data로 전달됨
                if (data.status == 1) {
                    alert("신고 타입을 선택하세요");
                } else if (data.status == 2) {
                    alert("신고 내용을 입력하세요");
                } else if (data.status == 3) {
                    $('#reportForm').css('display', 'none');
                    alert("신고가 접수되었습니다.");
                } else {
                    alert("알 수 없는 에러 발생.");
                }
            },
            error: function () {
                alert("실패");
            }
        });
    });
});