 
//textarea 글자 수 체크하는 함수
$(document).ready(function() {
    $('#introduce1').on('keyup', function() {
        $('#cnt1').html("("+$(this).val().length+" / 500)");
 
        if($(this).val().length > 500) {
            $(this).val($(this).val().substring(0, 500));
            $('#cnt1').html("(500 / 500)");
        }
    });
    
    $('#introduce2').on('keyup', function() {
        $('#cnt2').html("("+$(this).val().length+" / 500)");
 
        if($(this).val().length > 500) {
            $(this).val($(this).val().substring(0, 500));
            $('#cnt2').html("(500 / 500)");
        }
    });
    
    $('#introduce3').on('keyup', function() {
        $('#cnt3').html("("+$(this).val().length+" / 500)");
 
        if($(this).val().length > 500) {
            $(this).val($(this).val().substring(0, 500));
            $('#cnt3').html("(500 / 500)");
        }
    });
    
    $('#introduce4').on('keyup', function() {
        $('#cnt4').html("("+$(this).val().length+" / 500)");
 
        if($(this).val().length > 500) {
            $(this).val($(this).val().substring(0, 500));
            $('#cnt4').html("(500 / 500)");
        }
    });
    
    $('#introduce5').on('keyup', function() {
        $('#cnt5').html("("+$(this).val().length+" / 500)");
 
        if($(this).val().length > 500) {
            $(this).val($(this).val().substring(0, 500));
            $('#cnt5').html("(500 / 500)");
        }
    });
    
    $('#introduce6').on('keyup', function() {
        $('#cnt6').html("("+$(this).val().length+" / 500)");
 
        if($(this).val().length > 500) {
            $(this).val($(this).val().substring(0, 500));
            $('#cnt6').html("(500 / 500)");
        }
    });
    
});
     
 function registerApplication(idx) {

	const uri = "/application/registerPersonalInfo";
	const headers = {"Content-Type": "application/json", "X-HTTP-Method-Override": "POST"};
	const joinCompany = $("#joinCompany option:selected").val();
	const joinField1 = $("#joinField1 option:selected").val();
	const joinArea1 = $("#joinArea1 option:selected").val();
	const imgFile = $("#fileName")[0];
	const nameHan = $("#nameHan").val();
	const nameEng = $("#nameEng").val();
	const sex = $("input[name='sex']:checked").val();
	const zip1 = $("#zip1").val();
	const addr11 = $("#addr11").val();
	const addr12 = $("#addr12").val();
	const famCount = $("#famCount").val();
	const bohun = $("input[name='bohun']:checked").val();
	const handicap = $("input[name='handicap']:checked").val();
	const milClass = $("input[name='milClass']:checked").val();
	const milArm = $("#milArm option:selected").val();
	const milType = $("#milType option:selected").val();
	const milLv = $("#milLv option:selected").val();
	const milSDate = $("#milSDate").val();
	const milEdate = $("#milEdate").val();
	const milTicket = $("#milTicket option:selected").val();
	
	var applicationDTO = {"idx": idx, "joinCompany": joinCompany, "joinField1": joinField1
						, "joinArea1": joinArea1, "fileName": fileName, "nameHan": nameHan
						, "nameEng": nameEng, "sex": sex, "zip1": zip1, "addr11": addr11
						, "addr12": addr12, "bohun": bohun, "handicap": handicap, "milClass": milClass
						, "milArm": milArm, "milType": milType, "milLv": milLv, "milSDate": milSDate
						, "milTicket": milTicket};
	
	if(imgFile.files.length != 0){
		applicationDTO.fileName =  fileName.files[0];
	}
	
	if(famCount >= 1) {
		for(var i=1; i<famCount; i++) {
			eval("const famCon" + i + " = $('#famCon"+ i +" option:selected').val()");
	   		eval("const famName" + i + " = $('#famCon"+ i +"').val()");
	   		eval("const famAge" + i + " = $('#famCon"+ i +"').val()");
   		}
	}
	
	if(famCount >= 1) {
		for(var i=1; i<famCount; i++) {
			eval("applicationDTO.famCon"+ i +" = famCon"+i);
			eval("applicationDTO.famName"+ i +" = famName"+i);
			eval("applicationDTO.famAge"+ i +" = famAge"+i);
		}
	}
	
	$.ajax({
		url: uri,
		type: "POST",
		headers: headers,
		dataType: "json",
		data: JSON.stringify(applicationDTO),
		success: function(response) {
			if (response.result == false) {
				alert("저장에 실패하였습니다.");
				return false;
			}
			//임시저장 토스트
			//printCommentList();
			alert("저장되었습니다.");
		},
		error: function(xhr, status, error) {
			alert("에러가 발생하였습니다.");
			return false;
		}
	});
	
}
/*[- end of function -]*/
     
