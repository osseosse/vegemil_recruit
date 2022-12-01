 /*<![CDATA[*/
	
$('#txtName').keyup(function () {
	
	var sBirth = $('#txtBirth').val();
    var sName = $("#txtName").val();

    if ($.trim(sName).length == 0) {
		$("#nameCheck").css('display', 'inline-block');
		$("#txtName").addClass('error');
	} else {
		$("#nameCheck").css('display', 'none');
		$("#txtName").removeClass('error');
	}
    
    if($.trim(sName).length > 0 && $.trim(sBirth).length > 0) {
		$("#searchBtn").attr("disabled", false); //해제
		$("#searchBtn").addClass('active');
	} else {
		$("#searchBtn").attr("disabled", true); //설정
		$("#searchBtn").removeClass('active');
	}
    
});

$('#txtBirth').keyup(function () {
	
	var sBirth = $('#txtBirth').val();
    var sName = $("#txtName").val();

    if ($.trim(sName).length == 0) {
		$("#birthCheck").css('display', 'inline-block');
		$("#txtBirth").addClass('error');
	} else {
		$("#birthCheck").css('display', 'none');
		$("#txtBirth").removeClass('error');
	}
    
    if($.trim(sBirth).length > 0 && $.trim(sName).length > 0) {
		$("#searchBtn").attr("disabled", false); //해제
		$("#searchBtn").addClass('active');
	} else {
		$("#searchBtn").attr("disabled", true); //설정
		$("#searchBtn").removeClass('active');
	}
    
});


function sendCheck() {
    
	//이름 공백 확인
    if($("#txtName").val() == ""){
      alert("이름을 입력해 주세요");
      $("#txtName").focus();
      return false;
    }
	
  	//생년월일 공백 확인
    if($("#txtBirth").val() == ""){
      alert("생년월일을 입력해주세요");
      $("#txtBirth").focus();
      return false;
    }
	
	return true;
}
/*[- end of function -]*/


/*]]>*/   
