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
$('input[name=password]').keyup(function () {
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

/*]]>*/