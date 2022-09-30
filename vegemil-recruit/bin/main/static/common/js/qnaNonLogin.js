/*<![CDATA[*/

function btnAvtive() {
	const rTitle = $('#rTitle').val();
	const rContent = $('#rContent').val();
	const rEmail = $('#rEmail').val();
	const rPhoneNo = $('#rPhoneNo').val();
	
	if($("#rAgree").is(":checked") == true
	&& $.trim(rTitle).length > 1 
	&& $.trim(rContent).length > 1
	&& $.trim(rEmail).length > 1
	&& $.trim(rPhoneNo).length > 1) 
	{
		$("#saveBtn").attr("disabled", false); //해제
		$("#saveBtn").addClass('active');
	} else {
		$("#saveBtn").attr("disabled", true); //설정
		$("#saveBtn").removeClass('active');		
	}
}

$("#rAgree").on("click", function () {
	btnAvtive();
});

//회사 선택 체크
$("#SelectBoxID").change( function() {

	const rCompany = $("select[name=rCompany]").val();
	
	if ($.trim(rCompany).length == 0) {
		$("#companyCheck").css('display', 'inline-block');
		$("#rCompany").addClass('error');
	} else {
		$("#companyCheck").css('display', 'none');
		$("#rCompany").removeClass('error');
	}
	
	btnAvtive();

});

//제목 체크
$('#rTitle').keyup(function () {

	const rTitle = $('#rTitle').val();
	
	if ($.trim(rTitle).length == 0) {
		$("#titleCheck").css('display', 'inline-block');
		$("#rTitle").addClass('error');
	} else {
		$("#titleCheck").css('display', 'none');
		$("#rTitle").removeClass('error');
	}
	
	btnAvtive();
	
});

//내용 체크
$('#rContent').keyup(function () {

	const rTitle = $('#rContent').val();
	
	if ($.trim(rContent).length == 0) {
		$("#contentCheck").css('display', 'inline-block');
		$("#rContent").addClass('error');
	} else {
		$("#contentCheck").css('display', 'none');
		$("#rContent").removeClass('error');
	}
	
	btnAvtive();
	
});

function validateEmail(sEmail) {
	var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
	
	if (filter.test(sEmail)) {
		return true;
	} else {
		return false;
	}
}

//이메일 정규식
$('#rEmail').keyup(function () {
	const rTitle = $('#rTitle').val();
	const rContent = $('#rContent').val();
	const rEmail = $('#rEmail').val();
	const rPhoneNo = $('#rPhoneNo').val();
	
	if ($.trim(rEmail).length == 0) {
		$("#emailMatch").css('display', 'none');
		$("#emailCheck").css('display', 'inline-block');
		$("#txtEmail").addClass('error');
	} else {
		if (validateEmail(rEmail)) {
			$("#emailMatch").css('display', 'none');
			$("#txtEmail").removeClass('error');
		} else {
			$("#emailCheck").css('display', 'none');
			$("#emailMatch").css('display', 'inline-block');
			$("#txtEmail").addClass('error');
		}
	}
	
	btnAvtive();
	
});

//휴대폰번호 체크
$("#rPhoneNo").keyup(function() {
	const rTitle = $('#rTitle').val();
	const rContent = $('#rContent').val();
	const rEmail = $('#rEmail').val();
	const rPhoneNo = $('#rPhoneNo').val();
	
	if( this.value.length == 0){
		$("#phoneNoCheck").css('display', 'inline-block');
		$("#rPhoneNo").addClass('error');
    } else {
    	$("#phoneNoCheck").css('display', 'none');
    	$("#rPhoneNo").removeClass('error');
    }
    if( this.value.length > 13){
         this.value = this.value.substr(0, 13);
     }
     var val         = this.value.replace(/\D/g, '');
     var original    = this.value.replace(/\D/g, '').length;
     var conversion  = '';
     for(i=0;i<2;i++){
         if (val.length > 3 && i===0) {
             conversion += val.substr(0, 3) + '-';
             val         = val.substr(3);
         }
         else if(original>7 && val.length > 4 && i===1){
             conversion += val.substr(0, 4) + '-';
             val         = val.substr(4);
         }
     }
     conversion += val;
     this.value = conversion;
     
     btnAvtive();
     
 });


/*]]>*/