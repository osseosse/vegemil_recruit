 
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
     
 function updateIntroduce() {

	const uri = "/application/updateIntroduce";
	const headers = {"Content-Type": "application/json", "X-HTTP-Method-Override": "POST"};
	
	$.ajax({
		url: uri,
		type: "POST",
		//headers: headers,
		dataType: "json",
		contentType: false,
  		processData: false,
        data: new FormData($("#introduceForm")[0]),
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
     
