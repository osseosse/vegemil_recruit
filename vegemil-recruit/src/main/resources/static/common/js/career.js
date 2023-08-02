 //리피터
 $(function () {
  'use strict';
  window.id1 = $('#hisCount').val();
  
  $('#field2').repeater({
	defaultValues: {
        'id': window.id1,
    },
    show: function () {
      if(window.id1 < 6) {
   	      $(this).slideDown();
   	      window.id1++;
   	      $('#hisCount').val(window.id1);
      } else {
      	alert("경력은 6개까지 입력 가능합니다.");
      	return false;
      }
    },
    hide: function (deleteElement) {
      if (confirm('삭제하시겠습니까?')) {
    	window.id1--;
        $('#hisCount').val(window.id1);
        $(this).slideUp(deleteElement);
      }
    }
  });
  
  
});
/*[- end of function -]*/

 $(document).on('keyup', '.ls2',function() {
		
    if( this.value.length > 8){
         this.value = this.value.substr(0, 8);
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
     
 });
 /*[- end of function -]*/
 
 $(document).on('keyup', '.text-right',function() {
      $(this).val($(this).val().replace(/[^0-9]/g,""));
 });
 
function endDateChange(e) {

 	var index = e.name.substring(8,9);
	var target = document.getElementById("langLicName"+index);
	 
	if(e.value == "영어") {
		var d = lang_e;
	} else {
	
	}
	
}
/*[- end of function -]*/
     
 function updateCareer() {

	const uri = "/application/updateCareer";
	const headers = {"Content-Type": "multipart/form-data", "X-HTTP-Method-Override": "POST"};
	
	$.ajax({
		url: uri,
		type: "POST",
		//headers: headers,
		dataType: "json",
		contentType: false,
  		processData: false,
        data: new FormData($("#careerForm")[0]),
		enctype: "multipart/form-data",
		success: function(response) {
			if (response.result == false) {
				alert("저장에 실패하였습니다.");
				return false;
			}
			alert("저장되었습니다.");
		},
		error: function(xhr, status, error) {
			alert("에러가 발생하였습니다.");
			return false;
		}
	});
	
}
/*[- end of function -]*/
     
