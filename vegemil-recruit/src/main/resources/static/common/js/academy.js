 $(".ls1").keyup(function() {
	
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
 
 //학교검색
 function fnSchool() {
 
    const lastAcademy = $("#lastAcademy option:selected").val();
    const shName = $("#shName").val();
    const uri = 'https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=817ec3a515c2007eaa94cf32acea21cb&svcType=api&svcCode=SCHOOL&contentType=json&gubun=' + lastAcademy + '&searchSchulNm=' + shName;
    var htmlString = "";
    
    $.ajax({
        type: 'GET',
        url: uri,
        datatype : "json",
        success: function (data) {
            
            $.each(data.dataSearch.content, function(index, item) { // 데이터 =item
            	htmlString+= "<li><a href='javascript:'>"+item.schoolName+"</a></li>";
            	htmlString+= "<input type='hidden' value='"+item.region+"' >";
            });
            
            $("#schList").html(htmlString).trigger('create');
        },
        error: function (msg) {
            alert(msg.responseText);
        }
    });
    
}
 /*[- end of function -]*/
 
 //학과 검색
 function fnMajor() {
 
    const majorName = $("#majorName").val();
    const uri = '/recruit/majorList?majorName=' + majorName;
    
    $.ajax({
        type: 'GET',
        url: uri,
        contentType: "application/json; charset=utf-8",
	    dataType:'json',
        success: function (data) {
            
            if(data != null){
	            var majorList = data.majorList;
	            var timehtml = "";
	            var size = majorList.length;
	            
	            for(var i=0;i<size; i++){
                	timehtml+='<li><a href="javascript:">'+majorList[i]+'</a></li>';
                	timehtml+='<input type="hidden" value='+majorList[i]+' >';
	            }//for		            
	           
	            $("#majorList").html(timehtml).trigger('create');
	            
	        }else{
	          //alert("품절입니다.");
	        }
            
        },
        error: function (msg) {
            alert(msg.responseText);
        }
    });
    
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
     
