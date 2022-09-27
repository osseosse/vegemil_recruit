/*<![CDATA[*/

function btnAvtive() {
	const sName = $('#txtName').val();
	const sEmail = $('#txtEmail').val();
	const sHp = $('#txtHp').val();
	const sPw1 = $('#pw1').val();
	const sPw2 = $('#pw2').val();
	const sBirth = $('#txtBirthday').val();
	
	if($("#agree").is(":checked") == true
	&& $.trim(sName).length > 1 
	&& $.trim(sEmail).length > 1
	&& $.trim(sHp).length > 11
	&& $.trim(sPw1).length > 1
	&& $.trim(sPw2).length > 1
	&& $.trim(sBirth).length > 8) 
	{
		$("#joinBtn").attr("disabled", false); //해제
		$("#joinBtn").addClass('active');
	} else {
		$("#joinBtn").attr("disabled", true); //설정
		$("#joinBtn").removeClass('active');		
	}
}

$("#agree").on("click", function () {
	btnAvtive();
});

//이름 체크
$('#txtName').keyup(function () {
	const sName = $('#txtName').val();
	const sEmail = $('#txtEmail').val();
	const sHp = $('#txtHp').val();
	const sPw1 = $('#pw1').val();
	const sPw2 = $('#pw2').val();
	const sBirth = $('#txtBirthday').val();
	
	if ($.trim(sName).length == 0) {
		$("#nameCheck").css('display', 'inline-block');
		$("#txtName").addClass('error');
	} else {
		$("#nameCheck").css('display', 'none');
		$("#txtName").removeClass('error');
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
	
//이메일 정규식
$('#txtEmail').keyup(function () {
	const sName = $('#txtName').val();
	const sEmail = $('#txtEmail').val();
	const sHp = $('#txtHp').val();
	const sPw1 = $('#pw1').val();
	const sPw2 = $('#pw2').val();
	const sBirth = $('#txtBirthday').val();
	
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
	}
	
	btnAvtive();
	
});


//비밀번호 정규식 검사
$('#pw1').keyup(function () {
	const sName = $('#txtName').val();
	const sEmail = $('#txtEmail').val();
	const sHp = $('#txtHp').val();
	const sPw1 = $('#pw1').val();
	const sPw2 = $('#pw2').val();
	const sBirth = $('#txtBirthday').val();
	var num = sPw1.search(/[0-9]/g);
	var eng = sPw1.search(/[a-z]/ig);
	var spe = sPw1.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
	 
	if( sPw1.length < 8 || sPw1.length > 20 ){
		$("#pwCheck").css('display', 'inline-block');
		return false;
	} else if( sPw1.search(/\s/) != -1 ){
		$("#pwCheck").css('display', 'inline-block');
		return false;
	} else if( num < 0 || eng < 0 || spe < 0 ){
		$("#pwCheck").css('display', 'inline-block');
		return false;
	} else {
		$("#pwCheck").css('display', 'none');
		return true;
	}
	
	btnAvtive();
	 
});

//비밀번호 일치 검사
$('input[name=pw]').keyup(function () {
	const sName = $('#txtName').val();
	const sEmail = $('#txtEmail').val();
	const sHp = $('#txtHp').val();
	const sPw1 = $('#pw1').val();
	const sPw2 = $('#pw2').val();
	const sBirth = $('#txtBirthday').val();

    if ( sPw1 != '' && sPw2 == '' ) {
        null;
    } else if (sPw1 != "" || sPw2 != "") {
        if (sPw1 == sPw2) {
            $("#match").css('display', 'inline-block');
            $("#nonMatch").css('display', 'none');
            $("#pw1").removeClass('error');
            $("#pw2").removeClass('error');
        } else {
            $("#match").css('display', 'none');
            $("#nonMatch").css('display', 'inline-block');
            $("#pw1").addClass('error');
            $("#pw2").addClass('error');
        }
    }
    
    btnAvtive();
    
});

//휴대폰번호 체크
$("#txtHp").keyup(function() {
	const sName = $('#txtName').val();
	const sEmail = $('#txtEmail').val();
	const sHp = $('#txtHp').val();
	const sPw1 = $('#pw1').val();
	const sPw2 = $('#pw2').val();
	const sBirth = $('#txtBirthday').val();
	
	if( this.value.length == 0){
		$("#hpCheck").css('display', 'inline-block');
		$("#txtHp").addClass('error');
    } else {
    	$("#hpCheck").css('display', 'none');
    	$("#txtHp").removeClass('error');
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

$("#txtBirthday").keyup(function() {
	const sName = $('#txtName').val();
	const sEmail = $('#txtEmail').val();
	const sHp = $('#txtHp').val();
	const sPw1 = $('#pw1').val();
	const sPw2 = $('#pw2').val();
	const sBirth = $('#txtBirthday').val();
	
    if( this.value.length > 10){
         this.value = this.value.substr(0, 10);
     }
     var val         = this.value.replace(/\D/g, '');
     var original    = this.value.replace(/\D/g, '').length;
     var conversion  = '';
     for(i=0;i<2;i++){
         if (val.length > 4 && i===0) {
             conversion += val.substr(0, 4) + '-';
             val         = val.substr(4);
         }
         else if(original>6 && val.length > 2 && i===1){
             conversion += val.substr(0, 2) + '-';
             val         = val.substr(2);
         }
     }
     conversion += val;
     this.value = conversion;
     
     btnAvtive();
     
 });

//생년월일 유효성검사
/*
function joinCheck() {
	
	
	var getMail = RegExp(/^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/);
    var getName= RegExp(/^[가-힣]+$/);
    var fmt = RegExp(/^\d{6}[1234]\d{6}$/); //형식 설정
    
    var pw = $("#pw1").val();
	var num = pw.search(/[0-9]/g);
	var eng = pw.search(/[a-z]/ig);
	var spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
	var birth = $("#txtBirthday").val();
	
	//동의 검사
	if($("#agree").is(":checked") == false) {
		alert("개인정보 수집 이용동의 체크해주세요");
		$("#agree").focus();
		return false;
	}
	
  	//이름 공백 검사
    if($("#name").val() == ""){
      alert("이름을 입력해주세요");
      $("#name").focus();
      return false;
    }

    //이름 유효성 검사
    if(!getName.test($("#name").val())){
      alert("이름형식에 맞게 입력해주세요")
      $("#name").val("");
      $("#name").focus();
      return false;
    }
    
  	//핸드폰 공백 확인
    if($("#hp2").val() == ""){
      alert("휴대폰 번호를 입력해주세요");
      $("#hp2").focus();
      return false;
    }
	if($("#hp3").val() == ""){
	   alert("휴대폰 번호를 입력해주세요");
	   $("#hp3").focus();
	   return false;
	}
    
  	//이메일 공백 확인
    if($("#txtEmail").val() == ""){
      alert("이메일을 입력해주세요");
      $("#txtEmail").focus();
      return false;
    }

    //비밀번호 공백 확인
    if($("#password").val() == ""){
      alert("패스워드를 입력해 주세요");
      $("#password").focus();
      return false;
    }
         
    //비밀번호 유효성검사
    if( pw.length < 8 || pw.length > 20 ){
    	alert("비밀번호는 8자 ~ 20자 내외로 입력해주세요");
    	$("#password").focus();
		return false;
	} else if( pw.search(/\s/) != -1 ){
		alert("공백이 없게 입력해주세요");
		$("#password").focus();
		return false;
	} else if( num < 0 || eng < 0 || spe < 0 ){
		alert("영문,숫자,특수문자를 혼합하여 입력해주세요");
		$("#password").focus();
		return false;
	} else {
	}
         
    //비밀번호 확인란 공백 확인
    if($("#pw2").val() == ""){
      alert("패스워드 확인란을 입력해주세요");
      $("#pw2").focus();
      return false;
    }
         
    //비밀번호 서로확인
    if($("#pw1").val() != $("#pw2").val()){
        alert("비밀번호가 상이합니다");
        $("#pw2").val("");
        $("#pw2").focus();
        return false;
    }
    
	if( birth.length < 1){ 
		alert("생년월일을 입력해주세요");
		$("#txtBirthday").focus();
		return false;
	}
	
	return true;
}
*/

/*]]>*/