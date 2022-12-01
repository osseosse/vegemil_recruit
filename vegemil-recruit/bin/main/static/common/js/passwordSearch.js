 /*<![CDATA[*/
	
//이메일 정규식
$('#txtEmail').keyup(function () {
	
	var sEmail = $('#txtEmail').val();
	var sName = $("#txtName").val();
	
	if ($.trim(sEmail).length == 0) {
		$("#emailMatch").css('display', 'none');
		$("#emailCheck").css('display', 'inline-block');
		$("#txtEmail").addClass('error');
	} else {
		if (validateEmail(sEmail)) {
			$("#emailMatch").css('display', 'none');
			$("#txtEmail").removeClass('error');
		} else {
			$("#emailCheck").css('display', 'none');
			$("#emailMatch").css('display', 'inline-block');
			$("#txtEmail").addClass('error');
		}
		//$("#emailCheck").css('display', 'none');
	}
	
	if(validateEmail(sEmail) && $.trim(sName).length != 0) {
		$("#sendBtn").attr("disabled", false); //해제
		$("#sendBtn").addClass('active');
	} else {
		$("#sendBtn").attr("disabled", true); //설정
		$("#sendBtn").removeClass('active');
	}
	 
});

$('#txtName').keyup(function () {
	
	var sEmail = $('#txtEmail').val();
    var sName = $("#txtName").val();

    if ($.trim(sName).length == 0) {
		$("#nameCheck").css('display', 'inline-block');
		$("#txtName").addClass('error');
	} else {
		$("#nameCheck").css('display', 'none');
		$("#txtName").removeClass('error');
	}
    
    if(validateEmail(sEmail) && $.trim(sName).length != 0) {
		$("#sendBtn").attr("disabled", false); //해제
		$("#sendBtn").addClass('active');
	} else {
		$("#sendBtn").attr("disabled", true); //설정
		$("#sendBtn").removeClass('active');
	}
    
});


function validateEmail(sEmail) {
	var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
	
	if (filter.test(sEmail)) {
		return true;
	} else {
		return false;
	}
}

function sendCheck() {
    
	//이름 공백 확인
    if($("#txtName").val() == ""){
      alert("이름을 입력해 주세요");
      $("#txtName").focus();
      return false;
    }
	
  	//이메일 공백 확인
    if($("#txtEmail").val() == ""){
      alert("이메일을 입력해주세요");
      $("#txtEmail").focus();
      return false;
    }
	
	return true;
}

function sendPwResetEmail() {

	const uri = "/email/sendPwResetEmail";
	const headers = {"Content-Type": "multipart/form-data", "X-HTTP-Method-Override": "POST"};
	
	$.ajax({
		url: uri,
		type: "POST",
		//headers: headers,
		dataType: "json",
		contentType: false,
  		processData: false,
        data: new FormData($("#pwSeachform")[0]),
		success: function(response) {
			if (response.result == false) {
				alert("이름과 이메일을 다시 확인바랍니다.");
				return false;
			}
			alert("비밀번호 재설정 메일이 발송 되었습니다.");
		},
		error: function(xhr, status, error) {
			alert("에러가 발생하였습니다.");
			return false;
		}
	});
	
}	
/*[- end of function -]*/

/*]]>*/   
