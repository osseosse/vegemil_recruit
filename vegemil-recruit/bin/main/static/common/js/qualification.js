    
 //리피터
 $(function () {
  'use strict';
  window.id1 = $('#langCount').val();
  window.id2 = $('#licCount').val();
  window.id3 = $('#medalCount').val();
  window.id4 = $('#forCount').val();
  
  
  $('#field1').repeater({
	defaultValues: {
        'id': window.id1,
    },
    show: function () {
      if(window.id1 < 4) {
   	      $(this).slideDown();
   	      window.id1++;
   	      $('#langCount').val(window.id1);
      } else {
      	alert("어학은 4개까지 입력 가능합니다.");
      	return false;
      }
    },
    hide: function (deleteElement) {
      if (confirm('삭제하시겠습니까?')) {
    	window.id1--;
        $('#langCount').val(window.id1);
        $(this).slideUp(deleteElement);
      }
    }
  });
  
  $('#field3').repeater({
	defaultValues: {
        'id': window.id2,
    },
    show: function () {
      if(window.id2 < 5) {
   	      $(this).slideDown();
   	      window.id2++;
   	      $('#licCount').val(window.id2);
      } else {
      	alert("자격 및 면허는 5개까지 입력 가능합니다.");
      	return false;
      }
    },
    hide: function (deleteElement) {
      if (confirm('삭제하시겠습니까?')) {
    	window.id2--;
        $('#licCount').val(window.id2);
        $(this).slideUp(deleteElement);
      }
    }
  });
  
  $('#field4').repeater({
	defaultValues: {
        'id': window.id3,
    },
    show: function () {
      if(window.id3 < 3) {
   	      $(this).slideDown();
   	      window.id3++;
   	      $('#medalCount').val(window.id3);
      } else {
      	alert("수상경력은 3개까지 입력 가능합니다.");
      	return false;
      }
    },
    hide: function (deleteElement) {
      if (confirm('삭제하시겠습니까?')) {
    	window.id3--;
        $('#medalCount').val(window.id3);
        $(this).slideUp(deleteElement);
      }
    }
  });
  
  $('#field5').repeater({
	defaultValues: {
        'id': window.id4,
    },
    show: function () {
      if(window.id4 < 2) {
   	      $(this).slideDown();
   	      window.id4++;
   	      $('#forCount').val(window.id4);
      } else {
      	alert("연수 및 대외활동은 2개까지 입력 가능합니다.");
      	return false;
      }
    },
    hide: function (deleteElement) {
      if (confirm('삭제하시겠습니까?')) {
    	window.id4--;
        $('#forCount').val(window.id4);
        $(this).slideUp(deleteElement);
      }
    }
  });
  
  
});
/*[- end of function -]*/

$(".ls1").keyup(function() {
		
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
     
 });
 /*[- end of function -]*/

 $(".ls2").keyup(function() {
		
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
 
function categoryChange(e) {
 	var index = e.name.substring(8,9);
	var lang_e = ["선택","Cambridge CAE", "Cambridge FCE", "Cambridge FCE - 등급제", "Duolingo English Test", "ECL 유럽공인어학시험"
				, "G-TELP", "G-TELP Speaking", "G-TELP Writing", "GMAT", "GRE", "IELTS","NEW TEPS", "OPIc", "PET", "SNULT", "SPA", "TEPS", "TOEFL CBT", "TOEFL ITP", "TOEFL PBT"
				, "TOEFL iBT", "TOEIC", "TOEIC Speaking", "G-TELP Speaking", "G-TELP Speaking"];
	var lang_c = ["선택","BCT", "BCT Speaking", "CPT", "HSK", "ITT", "OPIc", "SNULT", "TSC", "新BCT", "新HSK", "新HSK 고급회화", "新HSK 중급회화"];
	var lang_j = ["선택","BJT", "EJU", "JLPT", "JPT", "JTRA", "OPIc", "SJPT", "SNULT"];
	var lang_f = ["선택","DALF", "DELF", "OPI", "SNULT", "TCF"];
	var lang_s = ["선택","DELE", "OPIc", "SNULT"];
	var lang_g = ["선택","DSH", "Deutsches Sprachdiplom(DSD)", "Goethe Zertifikat", "OPI", "SNULT", "TELC", "Test DaF", "Zertifikat Deutsch(ZD)"];
	var lang_i = ["선택","CELI", "CILS", "FLEX"];
	var lang_r = ["선택","FLEX", "OPIc", "SNULT", "TORFL(토르플)"];
	var target = document.getElementById("langLicName"+index);
	 
	if(e.value == "영어") {
		var d = lang_e;
	} else if(e.value == "중국어") {
		var d = lang_c;
	} else if(e.value == "일본어") {
		var d = lang_j;
	} else if(e.value == "프랑스어") {
		var d = lang_f;
	} else if(e.value == "스페인어") {
		var d = lang_s;
	} else if(e.value == "독일어") {
		var d = lang_g;
	} else if(e.value == "이탈리아어") {
		var d = lang_i;
	} else if(e.value == "러시아어") {
		var d = lang_r;
	}
	target.options.length = 0;
	 
	for (x in d) {
		var opt = document.createElement("option");
		opt.value = d[x];
		opt.innerHTML = d[x];
		target.appendChild(opt);
	}
}
/*[- end of function -]*/
     
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
     