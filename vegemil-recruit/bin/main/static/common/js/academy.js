 /*<![CDATA[*/
 
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

function inputSchool(){
   $('#inSch').prop("disabled", false); // Element(s) are now enabled.
}

function inputMajor(){
   $('#inMaj').prop("disabled", false); // Element(s) are now enabled.
}

$(document).on("click", "#inputSchoolBtn", function(){

	$("#schName").val($("#inSch").val());

});

$(document).on("click", "#inputMajorBtn", function(){

	$("#schMajor").val($("#inMaj").val());

});

$(document).on("click", "#inputSubMajorBtn", function(){

	alert($("#inSubMaj").val());
	$("#schSubMajor").val($("#inSubMaj").val());

});
 
$(document).on("click", ".schLi a", function(){
  	$(".schLi").removeClass("active");
	$(this).parent().addClass("active");
	$("#schBtn").addClass('active');
	$("#schBtn").attr("disabled", false); //설정
	$("#schName").val($(this).text());
	modal1.close();
});

$(document).on("click", ".majLi a", function(){
	$(".majLi").removeClass("active");
	$(this).parent().addClass("active");
	$("#majBtn").addClass('active');
	$("#majBtn").attr("disabled", false); //설정
	$("#schMajor").val($(this).text());
	modal2.close();
});

$(document).on("click", ".subMajLi a", function(){
	$(".subMajLi").removeClass("active");
	$(this).parent().addClass("active");
	$("#subMajBtn").addClass('active');
	$("#subMajBtn").attr("disabled", false); //설정
	$("#schSubMajor").val($(this).text());
	modal3.close();
});
 
$("#selectAcademy").change( function() {

	const selectAcademy = $("#selectAcademy option:selected").val();
	
	if(selectAcademy == "high_list") {
		$("#majorArea").hide();
		$("#schChangeArea").hide();
	} else if(selectAcademy == "colg_list") {
		$("#majorArea").show();
		$("#schChangeArea").hide();
		$("#majorAddArea1").hide();
		$("#majorAddArea2").hide();
	} else if(selectAcademy == "univ_list") {
		$("#majorArea").show();
		$("#schChangeArea").show();
		$("#majorAddArea1").show();
		$("#majorAddArea2").show();
	} else {
		$("#schChangeArea").hide();
		$("#majorArea").show();
		$("#majorAddArea1").hide();
		$("#majorAddArea2").hide();
	}
	
	//$(".base option").prop("selected", false); 
	$("input[class='inType01 w180']").val('');
	$("input[class='inType01 w180 ls1']").val('');
	$("input[class='inType01 w120 bgGray2']").val('');
	$("input[class='inType01 w70 text-right']").val('');
	$("#searchArea").show();
	$("#gradArea").show();
	$("#schArea").show();
	$("#schAdmisArea").show();
	$("#nightArea").show();
	$("#howLongArea").show();
	
 
});

//학력 수정
function editAcademy(schType) {

	if(schType == 'high_list') {
	
		$("#selectAcademy").val('high_list').prop("selected", true);
		$("#schName").val($("#schName1").text());
   		$("#schSite").val($("#schSite1").text());
   		$("#schNight").val($("#schNight1").text()).prop("selected", true);
   		$("#schAdmis").val($("#schAdmis1").val());
   		$("#schGrad").val($("#schGrad1").val()).prop("selected", true);
   		$("#schEnterYm").val($("#schEnterYm1").val());
   		$("#schGradYm").val($("#schGradYm1").val());
   		
   		$("#majorArea").hide();
		$("#schChangeArea").hide();
		
	} else if(schType == 'colg_list') {
	
		$("#selectAcademy").val('colg_list').prop("selected", true);
		$("#schName").val($("#schName2").text());
   		$("#schSite").val($("#schSite2").text());
   		$("#schNight").val($("#schNight2").text()).prop("selected", true);
   		$("#schAdmis").val($("#schAdmis2").val());
   		$("#schGrad").val($("#schGrad2").val()).prop("selected", true);
   		$("#schMajor").val($("#schMajor2").text());
   		$("#schPointEvg").val($("#schPointEvg2").text());
   		$("#schEnterYm").val($("#schEnterYm2").val());
   		$("#schGradYm").val($("#schGradYm2").val());
   		
   		$("#majorArea").show();
   		$("#schChangeArea").hide();
		$("#majorAddArea1").hide();
		$("#majorAddArea2").hide();
		
	} else if(schType == 'univ_list') {
	
		$("#selectAcademy").val('univ_list').prop("selected", true);
		$("#schName").val($("#schName3").text());
   		$("#schSite").val($("#schSite3").text());
   		$("#schNight").val($("#schNight3").text()).prop("selected", true);
   		$("#schGrad").val($("#schGrad3").val()).prop("selected", true);
   		$("#schTransfer").val($("#schTransfer3").val()).prop("selected", true);
   		$("#schAdmis").val($("#schAdmis3").val());
   		$("#schMajor").val($("#schMajor3").text());
   		$("#schPointEvg").val($("#schPointEvg3").text());
   		$("#schSubMajor").val($("#schSubMajor3").text());
   		$("#schDepType").val($("#schDepType3").text()).prop("selected", true);
   		$("#schEnterYm").val($("#schEnterYm3").val());
   		$("#schGradYm").val($("#schGradYm3").val());
		
		$("#majorArea").show();
		$("#schChangeArea").show();
		$("#majorAddArea1").show();
		$("#majorAddArea2").show();
		
	} else if(schType == 'mast_list') {
	
		$("#selectAcademy").val('mast_list').prop("selected", true);
		$("#schName").val($("#schName4").text());
   		$("#schSite").val($("#schSite4").text());
   		$("#schNight").val($("#schNight4").text()).prop("selected", true);
   		$("#schAdmis").val($("#schAdmis4").val());
   		$("#schGrad").val($("#schGrad4").val()).prop("selected", true);
   		$("#schMajor").val($("#schMajor4").text());
   		$("#schPointEvg").val($("#schPointEvg4").text());
   		$("#schEnterYm").val($("#schEnterYm4").val());
   		$("#schGradYm").val($("#schGradYm4").val());
   		
   		$("#schChangeArea").hide();
		$("#majorArea").show();
		$("#majorAddArea1").hide();
		$("#majorAddArea2").hide();
		
	} else if(schType == 'doct_list') {
	
		$("#selectAcademy").val('doct_list').prop("selected", true);
		$("#schName").val($("#schName5").text());
   		$("#schSite").val($("#schSite5").text());
   		$("#schNight").val($("#schNight5").text()).prop("selected", true);
   		$("#schAdmis").val($("#schAdmis5").val());
   		$("#schGrad").val($("#schGrad5").val()).prop("selected", true);
   		$("#schMajor").val($("#schMajor5").text());
   		$("#schPointEvg").val($("#schPointEvg5").text());
   		$("#schEnterYm").val($("#schEnterYm5").val());
   		$("#schGradYm").val($("#schGradYm5").val());
   		
   		$("#schChangeArea").hide();
		$("#majorArea").show();
		$("#majorAddArea1").hide();
		$("#majorAddArea2").hide();
		
	}
	
	$("#searchArea").show();
	$("#gradArea").show();
	$("#schArea").show();
	$("#schAdmisArea").show();
	$("#nightArea").show();
	$("#howLongArea").show();
	

}

 
//학교검색
function fnSchool() {
 
    var selectAcademy = $("#selectAcademy option:selected").val();
    
    if(selectAcademy == 'mast_list' || selectAcademy == 'doct_list') {
    	selectAcademy = 'univ_list';
    }
    
    const shName = $("#shName").val();
    const uri = 'https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=817ec3a515c2007eaa94cf32acea21cb&svcType=api&svcCode=SCHOOL&contentType=json&gubun=' + selectAcademy + '&searchSchulNm=' + shName;
    var htmlString = "";
    
    $.ajax({
        type: 'GET',
        url: uri,
        datatype : "json",
        success: function (data) {
            
            $.each(data.dataSearch.content, function(index, item) { // 데이터 =item
            	htmlString+= "<li class='schLi'><a>"+item.schoolName+"</a></li>";
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
 function fnMajor(type) {
 
    var majorName;
    
    if(type == 'main') {
    	majorName = $("#majorName").val();
    } else {
    	majorName = $("#subMajorName").val();
    }
    
    const uri = '/recruit/majorList?majorName=' + majorName;
    var majorList;
    var subMajorList;
    var timehtml;
    var size;
    
    $.ajax({
        type: 'GET',
        url: uri,
        contentType: "application/json; charset=utf-8",
	    dataType:'json',
        success: function (data) {
            
            if(data != null){
            	
            	timehtml = "";
            	majorList = data.majorList;
	            size = majorList.length;
	            
            	if(type == 'main') {
            		
            		for(var i=0;i<size; i++){
	                	timehtml+='<li class="majLi"><a>'+majorList[i]+'</a></li>';
	                	timehtml+='<input type="hidden" value='+majorList[i]+' >';
		            }//for	
            	
		            $("#majorList").html(timehtml).trigger('create');
		            
		       	} else {
		       	
		       		for(var i=0;i<size; i++){
	                	timehtml+='<li class="subMajLi"><a>'+majorList[i]+'</a></li>';
	                	timehtml+='<input type="hidden" value='+majorList[i]+' >';
		            }//for	
		            $("#subMajorList").html(timehtml).trigger('create');
		       	}
	            
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


$("#schName").change(function(){
	
	const selectAcademy = $("#selectAcademy option:selected").val();
	
	if(selectAcademy == 'high_list') {
		$('#schName1').val($(this).val());
	} else if(selectAcademy == 'colg_list') {
		$('#schName2').val($(this).val());
	} else if(selectAcademy == 'univ_list') {
		$('#schName3').val($(this).val());
	} else if(selectAcademy == 'mast_list') {
		$('#schName4').val($(this).val());
	} else if(selectAcademy == 'doct_list') {
		$('#schName5').val($(this).val());
	}
    
});

$("#schGrad").change(function(){
	
	const selectAcademy = $("#selectAcademy option:selected").val();
	
	if(selectAcademy == 'high_list') {
		$('#schGrad1').val($(this).val());
	} else if(selectAcademy == 'colg_list') {
		$('#schGrad2').val($(this).val());
	} else if(selectAcademy == 'univ_list') {
		$('#schGrad3').val($(this).val());
	} else if(selectAcademy == 'mast_list') {
		$('#schGrad4').val($(this).val());
	} else if(selectAcademy == 'doct_list') {
		$('#schGrad5').val($(this).val());
	}
    
});

$("#schNight").change(function(){
	
	const selectAcademy = $("#selectAcademy option:selected").val();
	
	if(selectAcademy == 'high_list') {
		$('#schNight1').val($(this).val());
	} else if(selectAcademy == 'colg_list') {
		$('#schNight2').val($(this).val());
	} else if(selectAcademy == 'univ_list') {
		$('#schNight3').val($(this).val());
	} else if(selectAcademy == 'mast_list') {
		$('#schNight4').val($(this).val());
	} else if(selectAcademy == 'doct_list') {
		$('#schNight5').val($(this).val());
	}
    
});

$("#schSite").keydown(function(){
	
	const selectAcademy = $("#selectAcademy option:selected").val();
	
	if(selectAcademy == 'high_list') {
		$('#schSite1').val($(this).val());
	} else if(selectAcademy == 'colg_list') {
		$('#schSite2').val($(this).val());
	} else if(selectAcademy == 'univ_list') {
		$('#schSite3').val($(this).val());
	} else if(selectAcademy == 'mast_list') {
		$('#schSite4').val($(this).val());
	} else if(selectAcademy == 'doct_list') {
		$('#schSite5').val($(this).val());
	}
    
});

$("#schSite").keydown(function(){
	
	const selectAcademy = $("#selectAcademy option:selected").val();
	
	if(selectAcademy == 'high_list') {
		$('#schSite1').val($(this).val());
	} else if(selectAcademy == 'colg_list') {
		$('#schSite2').val($(this).val());
	} else if(selectAcademy == 'univ_list') {
		$('#schSite3').val($(this).val());
	} else if(selectAcademy == 'mast_list') {
		$('#schSite4').val($(this).val());
	} else if(selectAcademy == 'doct_list') {
		$('#schSite5').val($(this).val());
	}
    
});

$("#schEnterYm").keydown(function(){
	
	const selectAcademy = $("#selectAcademy option:selected").val();
	
	if(selectAcademy == 'high_list') {
		$('#schEnterYm1').val($(this).val());
	} else if(selectAcademy == 'colg_list') {
		$('#schEnterYm2').val($(this).val());
	} else if(selectAcademy == 'univ_list') {
		$('#schEnterYm3').val($(this).val());
	} else if(selectAcademy == 'mast_list') {
		$('#schEnterYm4').val($(this).val());
	} else if(selectAcademy == 'doct_list') {
		$('#schEnterYm5').val($(this).val());
	}
    
});

$("#schGradYm").keydown(function(){
	
	const selectAcademy = $("#selectAcademy option:selected").val();
	
	if(selectAcademy == 'high_list') {
		$('#schGradYm1').val($(this).val());
	} else if(selectAcademy == 'colg_list') {
		$('#schGradYm2').val($(this).val());
	} else if(selectAcademy == 'univ_list') {
		$('#schGradYm3').val($(this).val());
	} else if(selectAcademy == 'mast_list') {
		$('#schGradYm4').val($(this).val());
	} else if(selectAcademy == 'doct_list') {
		$('#schGradYm5').val($(this).val());
	}
    
});

$("#schMajor").change(function(){
	
	const selectAcademy = $("#selectAcademy option:selected").val();
	
	if(selectAcademy == 'colg_list') {
		$('#schMajor2').val($(this).val());
	} else if(selectAcademy == 'univ_list') {
		$('#schMajor3').val($(this).val());
	} else if(selectAcademy == 'mast_list') {
		$('#schMajor4').val($(this).val());
	} else if(selectAcademy == 'doct_list') {
		$('#schMajor5').val($(this).val());
	}
    
});

$("#schPointEvg").keydown(function(){
	
	const selectAcademy = $("#selectAcademy option:selected").val();
	
	if(selectAcademy == 'colg_list') {
		$('#schPointEvg2').val($(this).val());
	} else if(selectAcademy == 'univ_list') {
		$('#schPointEvg3').val($(this).val());
	} else if(selectAcademy == 'mast_list') {
		$('#schPointEvg4').val($(this).val());
	} else if(selectAcademy == 'doct_list') {
		$('#schPointEvg5').val($(this).val());
	}
    
});

$("#shName").on("keyup",function(key){
    if(key.keyCode==13) {
        fnSchool();
    }
});

$("#majorName").on("keyup",function(key){
    if(key.keyCode==13) {
        fnMajor('main');
    }
});

$("#subMajorName").on("keyup",function(key){
    if(key.keyCode==13) {
        fnMajor('sub');
    }
});

function registerAcademy() {

	var uri = "/app/updateAcademy";
	var headers = {"Content-Type" : "application/json", "X-HTTP-Method-Override" : "POST"};
	var idx = Number($("#idx").val());
	var selectAcademy = $("#selectAcademy option:selected").val();
	var lastAcademy = $("#lastAcademy").val();
	var academyInfoDTO;
	
	if(selectAcademy == 'high_list') {
		var schName1 = "";
   		var schSite1 = "";
   		var schNight1 = "";
   		var schAdmis1 = "";
   		var schGrad1 = "";
   		var schEnterYm1 = "";
   		var schGradYm1 = "";
		schName1 = $("#schName").val();
   		schSite1 = $("#schSite").val();
   		schNight1 = $("#schNight").val();
   		schAdmis1 = $("#schAdmis option:selected").val();
   		schGrad1 = $("#schGrad").val();
   		schEnterYm1 = $("#schEnterYm").val();
   		schGradYm1 = $("#schGradYm").val();
   		academyInfoDTO = {"idx": idx, "lastAcademy": lastAcademy, "selectAcademy" : selectAcademy, "schAdmis1": schAdmis1
			, "schName1": schName1, "schSite1": schSite1, "schNight1": schNight1, "schGrad1": schGrad1, "schEnterYm1": schEnterYm1, "schGradYm1": schGradYm1
			};
			
	} else if(selectAcademy == 'colg_list') {
		var schName2 = "";
   		var schSite2 = "";
   		var schNight2 = "";
   		var schAdmis2 = "";
   		var schGrad2 = "";
   		var schPointEvg2 = "";
   		var schMajor2 = "";
   		var schEnterYm2 = "";
   		var schGradYm2 = "";
   		var schSubMajor2 = "";
   		var schSubPointEvg2 = "";
		schName2 = $("#schName").val();
   		schSite2 = $("#schSite").val();
   		schNight2 = $("#schNight").val();
   		schAdmis2 = $("#schAdmis option:selected").val();
   		schGrad2 = $("#schGrad").val();
   		schPointEvg2 = $("#schPointEvg").val();
   		schMajor2 = $("#schMajor").val();
   		schEnterYm2 = $("#schEnterYm").val();
   		schGradYm2 = $("#schGradYm").val();
   		schSubMajor2 = $("#schSubMajor").val();
   		schSubPointEvg2 = $("#schSubPointEvg").val();
   		academyInfoDTO = {"idx": idx, "lastAcademy": lastAcademy, "selectAcademy" : selectAcademy, "schAdmis2": schAdmis2
			, "schName2": schName2, "schSite2": schSite2, "schNight2": schNight2, "schGrad2": schGrad2, "schPointEvg2": schPointEvg2, "schMajor2": schMajor2, "schEnterYm2": schEnterYm2, "schGradYm2": schGradYm2
			};
   		
	} else if(selectAcademy == 'univ_list') {
		var schName3 = "";
   		var schSite3 = "";
   		var schNight3 = "";
   		var schGrad3 = "";
   		var schTransfer3 = "";
   		var schAdmis3 = "";
   		var schMajor3 = "";
   		var schPointEvg3 = "";
   		var schDepType3 = "";
   		var schEnterYm3 = "";
   		var schGradYm3 = "";
   		var schSubMajor3 = "";
   		var schSubPointEvg3 = "";
		schName3 = $("#schName").val();
   		schSite3 = $("#schSite").val();
   		schNight3 = $("#schNight option:selected").val();
   		schGrad3 = $("#schGrad option:selected").val();
   		schTransfer3 = $("#schTransfer option:selected").val();
   		schAdmis3 = $("#schAdmis option:selected").val();
   		schMajor3 = $("#schMajor").val();
   		schPointEvg3 = $("#schPointEvg").val();
   		schDepType3 = $("#schDepType option:selected").val();
   		schEnterYm3 = $("#schEnterYm").val();
   		schGradYm3 = $("#schGradYm").val();
   		schSubMajor3 = $("#schSubMajor").val();
   		schSubPointEvg3 = $("#schSubPointEvg").val();
   		academyInfoDTO = {"idx": idx, "lastAcademy": lastAcademy, "selectAcademy" : selectAcademy, "schDepType3" : schDepType3, "schTransfer3" : schTransfer3
			, "schName3": schName3, "schSite3": schSite3, "schNight3": schNight3, "schAdmis3": schAdmis3, "schGrad3": schGrad3, "schMajor3": schMajor3, "schPointEvg3": schPointEvg3
			, "schEnterYm3": schEnterYm3, "schGradYm3": schGradYm3, "schSubMajor3": schSubMajor3, "schSubPointEvg3": schSubPointEvg3
			};
			
		//필수입력 검증
		if(	$("#schMajor").val().length < 1
		 || $("#schAdmis option:selected").val() < 1
		 || $("#schPointEvg").val().length < 1)
		{
			alert("필수입력값을 모두 입력해 주세요");
			return false;
		}
	
	} else if(selectAcademy == 'mast_list') {
		var schName4 = "";
   		var schSite4 = "";
   		var schNight4 = "";
   		var schAdmis4 = "";
   		var schGrad4 = "";
   		var schEnterYm4 = "";
   		var schGradYm4 = "";
   		var schMajor4 = "";
   		var schPointEvg4 = "";
		schName4 = $("#schName").val();
   		schSite4 = $("#schSite").val();
   		schNight4 = $("#schNight").val();
   		schAdmis4 = $("#schAdmis option:selected").val();
   		schGrad4 = $("#schGrad").val();
   		schEnterYm4 = $("#schEnterYm").val();
   		schGradYm4 = $("#schGradYm").val();
   		schMajor4 = $("#schMajor").val();
   		schPointEvg4 = $("#schPointEvg").val();
   		academyInfoDTO = {"idx": idx, "lastAcademy": lastAcademy, "selectAcademy" : selectAcademy, "schAdmis4": schAdmis4
			, "schName4": schName4, "schSite4": schSite4, "schNight4": schNight4, "schGrad4": schGrad4, "schEnterYm4": schEnterYm4, "schGradYm4": schGradYm4, "schMajor4": schMajor4, "schPointEvg4": schPointEvg4
			};
			
		//필수입력 검증
		if(	$("#schMajor").val().length < 1
		 || $("#schPointEvg").val().length < 1)
		{
			alert("필수입력값을 모두 입력해 주세요");
			return false;
		}
   		
	} else if(selectAcademy == 'doct_list') {
		var schName5 = "";
   		var schSite5 = "";
   		var schNight5 = "";
   		var schAdmis5 = "";
   		var schGrad5 = "";
   		var schEnterYm5 = "";
   		var schGradYm5 = "";
   		var schMajor5 = "";
   		var schPointEvg5 = "";
		schName5 = $("#schName").val();
   		schSite5 = $("#schSite").val();
   		schNight5 = $("#schNight").val();
   		schAdmis5 = $("#schAdmis option:selected").val();
   		schGrad5 = $("#schGrad").val();
   		schEnterYm5 = $("#schEnterYm").val();
   		schGradYm5 = $("#schGradYm").val();
   		schMajor5 = $("#schMajor").val();
   		schPointEvg5 = $("#schPointEvg").val();
   		academyInfoDTO = {"idx": idx, "lastAcademy": lastAcademy, "selectAcademy" : selectAcademy, "schAdmis5": schAdmis5
			, "schName5": schName5, "schSite5": schSite5, "schNight5": schNight5, "schGrad5": schGrad5, "schEnterYm5": schEnterYm5, "schGradYm5": schGradYm5, "schMajor5": schMajor5, "schPointEvg5": schPointEvg5
			};
		
		//필수입력 검증
		if(	$("#schMajor").val().length < 1
		 || $("#schPointEvg").val().length < 1) {
			alert("필수입력값을 모두 입력해 주세요");
			return false;
		}	
			
	}
	
	//필수입력 검증
	if(	$("#schName").val().length < 1
	 || $("#schSite").val().length < 1
	 || $("#schNight").val().length < 1
	 || $("#schGrad").val().length < 1
	 || $("#schEnterYm").val().length < 1
	 || $("#schGradYm").val().length < 1)
	{
		alert("필수입력값을 모두 입력해 주세요");
		return false;
	}
	
	var htmlString = "";
	
	$.ajax({
		url: uri,
		type: "POST",
		headers: headers,
		dataType: "json",
		data: JSON.stringify(academyInfoDTO),
		success: function(response) {
			if (response.result == false) {
				alert("저장에 실패하였습니다.");
				return false;
			}
			alert("저장되었습니다.");
			
			if(selectAcademy == 'high_list') {
				
				$('#high_list').remove();
				htmlString+= "<li id='high_list'>";
            	htmlString+= "	<dl>";
            	htmlString+= "		<dt>";
            	htmlString+= "			<span class='gray3'>고등학교</span>";
				htmlString+= "		</dt>";
				htmlString+= "		<dd>";						
				htmlString+= "			<span class='sublist' id='schName1'>"+schName1+"</span>";
				htmlString+= "			<span class='sublist' id='schSite1'>"+schSite1+"</span>";
				htmlString+= "			<span class='sublist' id='schAdmis1'>"+schAdmis1+"</span>";
				htmlString+= "			<span class='sublist'>"+schEnterYm1+" "+schGradYm1+" "+schGrad1+"</span>";
				htmlString+= "		</dd>";
				htmlString+= "		<dd>";
				htmlString+= '			<span><a href="javascript:editAcademy(\'high_list\');"><img src="/img/ico_edit.png" alt="수정"/></a></span>';
				htmlString+= '			<span><a href="javascript:deleteAcademy(\'high_list\');"><img src="/img/ico_delete.png" alt="삭제"/></a></span>'; 
				htmlString+= "		</dd><br class='clear'>";
				htmlString+= "	</dl>";									
				htmlString+= "</li>";
				
			} else if(selectAcademy == 'colg_list') {
				
				$('#colg_list').remove();
				htmlString+= "<li id='colg_list'>";
            	htmlString+= "	<dl>";
            	htmlString+= "		<dt>";
            	htmlString+= "			<span class='gray3'>전문대학</span>";
				htmlString+= "		</dt>";
				htmlString+= "		<dd>";						
				htmlString+= "			<span class='sublist' id='schName2'>"+schName2+"</span>";
				htmlString+= "			<span class='sublist' id='schSite2'>"+schSite2+"</span>";
				htmlString+= "			<span class='sublist' id='schAdmis2'>"+schAdmis2+"</span>";
				htmlString+= "			<span class='sublist' id='schMajor2'>"+schMajor2+"</span>";
				htmlString+= "			<span class='sublist' id='schPointEvg2'>"+schPointEvg2+"</span>";
				htmlString+= "			<span class='sublist' id='schSubMajor2'>"+schSubMajor2+"</span>";
				htmlString+= "			<span class='sublist' id='schSubPointEvg2'>"+schSubPointEvg2+"</span>";
				htmlString+= "			<span class='sublist'>"+schEnterYm2+" ~ "+schGradYm2+" "+schGrad2+"</span>";
				htmlString+= "		</dd>";
				htmlString+= "		<dd>";
				htmlString+= '			<span><a href="javascript:editAcademy(\'colg_list\');"><img src="/img/ico_edit.png" alt="수정"/></a></span>';
				htmlString+= '			<span><a href="javascript:deleteAcademy(\'colg_list\');"><img src="/img/ico_delete.png" alt="삭제"/></a></span>'; 
				htmlString+= "		</dd><br class='clear'>";
				htmlString+= "	</dl>";									
				htmlString+= "</li>";
			
			} else if(selectAcademy == 'univ_list') {
			
				$('#univ_list').remove();
				htmlString+= "<li id='univ_list'>";
            	htmlString+= "	<dl>";
            	htmlString+= "		<dt>";
            	htmlString+= "			<span class='gray3'>대학교</span>";
				htmlString+= "		</dt>";
				htmlString+= "		<dd>";						
				htmlString+= "			<span class='sublist' id='schName3'>"+schName3+"</span>";
				htmlString+= "			<span class='sublist' id='schSite3'>"+schSite3+"</span>";
				htmlString+= "			<span class='sublist' id='schAdmis3'>"+schAdmis3+"</span>";
				htmlString+= "			<span class='sublist' id='schMajor3'>"+schMajor3+"</span>";
				htmlString+= "			<span class='sublist' id='schPointEvg3'>"+schPointEvg3+"</span>";
				htmlString+= "			<span class='sublist' id='schDepType3'>"+schDepType3+"</span>";
				htmlString+= "			<span class='sublist' id='schSubMajor3'>"+schSubMajor3+"</span>";
				htmlString+= "			<span class='sublist'>"+schEnterYm3+" ~ "+schGradYm3+" "+schGrad3+"</span>";
				htmlString+= "		</dd>";
				htmlString+= "		<dd>";
				htmlString+= '			<span><a href="javascript:editAcademy(\'univ_list\');"><img src="/img/ico_edit.png" alt="수정"/></a></span>';
				htmlString+= '			<span><a href="javascript:deleteAcademy(\'univ_list\');"><img src="/img/ico_delete.png" alt="삭제"/></a></span>'; 
				htmlString+= "		</dd><br class='clear'>";
				htmlString+= "	</dl>";									
				htmlString+= "</li>";
			
			} else if(selectAcademy == 'mast_list') {
			
				$('#mast_list').remove();
				htmlString+= "<li id='mast_list'>";
            	htmlString+= "	<dl>";
            	htmlString+= "		<dt>";
            	htmlString+= "			<span class='gray3'>석사</span>";
				htmlString+= "		</dt>";
				htmlString+= "		<dd>";						
				htmlString+= "			<span class='sublist' id='schName4'>"+schName4+"</span>";
				htmlString+= "			<span class='sublist' id='schSite4'>"+schSite4+"</span>";
				htmlString+= "			<span class='sublist' id='schAdmis4'>"+schAdmis4+"</span>";
				htmlString+= "			<span class='sublist' id='schMajor4'>"+schMajor4+"</span>";
				htmlString+= "			<span class='sublist' id='schPointEvg4'>"+schPointEvg4+"</span>";
				htmlString+= "			<span class='sublist'>"+schEnterYm4+" ~ "+schGradYm4+" "+schGrad4+"</span>";
				htmlString+= "		</dd>";
				htmlString+= "		<dd>";
				htmlString+= '			<span><a href="javascript:editAcademy(\'mast_list\');"><img src="/img/ico_edit.png" alt="수정"/></a></span>';
				htmlString+= '			<span><a href="javascript:deleteAcademy(\'mast_list\');"><img src="/img/ico_delete.png" alt="삭제"/></a></span>'; 
				htmlString+= "		</dd><br class='clear'>";
				htmlString+= "	</dl>";									
				htmlString+= "</li>";
			
			} else if(selectAcademy == 'doct_list') {
			
				$('#doct_list').remove();
				htmlString+= "<li id='doct_list'>";
            	htmlString+= "	<dl>";
            	htmlString+= "		<dt>";
            	htmlString+= "			<span class='gray3'>박사</span>";
				htmlString+= "		</dt>";
				htmlString+= "		<dd>";						
				htmlString+= "			<span class='sublist' id='schName5'>"+schName5+"</span>";
				htmlString+= "			<span class='sublist' id='schSite5'>"+schSite5+"</span>";
				htmlString+= "			<span class='sublist' id='schAdmis5'>"+schAdmis5+"</span>";
				htmlString+= "			<span class='sublist' id='schMajor5'>"+schMajor5+"</span>";
				htmlString+= "			<span class='sublist' id='schPointEvg5'>"+schPointEvg5+"</span>";
				htmlString+= "			<span class='sublist'>"+schEnterYm5+" ~ "+schGradYm5+" "+schGrad5+"</span>";
				htmlString+= "		</dd>";
				htmlString+= "		<dd>";
				htmlString+= '			<span><a href="javascript:editAcademy(\'doct_list\');"><img src="/img/ico_edit.png" alt="수정"/></a></span>';
				htmlString+= '			<span><a href="javascript:deleteAcademy(\'doct_list\');"><img src="/img/ico_delete.png" alt="삭제"/></a></span>'; 
				htmlString+= "		</dd><br class='clear'>";
				htmlString+= "	</dl>";									
				htmlString+= "</li>";
				
			}
			
			$("#con-list").append(htmlString).trigger('create');
			
		},
		error: function(request, status, error) {
			alert("에러가 발생하였습니다.");
			return false;
		}
	});
	
}
/*[- end of function -]*/

function deleteAcademy(selectAcademy) {

	var uri = "/app/updateAcademy";
	const headers = {"Content-Type": "application/json", "X-HTTP-Method-Override": "POST"};
	const idx = $("#idx").val();
	const lastAcademy = $("#lastAcademy").val();
	var academyDTO;
	
	if(selectAcademy == 'high_list') {
		const schName1 = "";
   		const schSite1 = "";
   		const schNight1 = "";
   		const schAdmis1 = "";
   		const schGrad1 = "";
   		const schEnterYm1 = "";
   		const schGradYm1 = "";
   		academyDTO = {"idx": idx, "lastAcademy": lastAcademy, "selectAcademy" : selectAcademy, "schAdmis1": schAdmis1
			, "schName1": schName1, "schSite1": schSite1, "schNight1": schNight1, "schGrad1": schGrad1, "schEnterYm1": schEnterYm1, "schGradYm1": schGradYm1
			};
   		
	} else if(selectAcademy == 'colg_list') {
		const schName2 = "";
   		const schSite2 = "";
   		const schNight2 = "";
   		const schAdmis2 = "";
   		const schGrad2 = "";
   		const schPointEvg2 = "";
   		const schMajor2 = "";
   		const schEnterYm2 = "";
   		const schGradYm2 = "";
   		const schSubMajor2 = "";
   		const schSubPointEvg2 = "";
   		academyDTO = {"idx": idx, "lastAcademy": lastAcademy, "selectAcademy" : selectAcademy, "schAdmis2": schAdmis2
			, "schName2": schName2, "schSite2": schSite2, "schNight2": schNight2, "schGrad2": schGrad2, "schPointEvg2": schPointEvg2, "schMajor2": schMajor2, "schEnterYm2": schEnterYm2, "schGradYm2": schGradYm2
			};
   		
	} else if(selectAcademy == 'univ_list') {
		const schName3 = "";
   		const schSite3 = "";
   		const schNight3 = "";
   		const schGrad3 = "";
   		const schAdmis3 = "";
   		const schMajor3 = "";
   		const schPointEvg3 = "";
   		const schDepType3 = "";
   		const schEnterYm3 = "";
   		const schGradYm3 = "";
   		const schSubMajor3 = "";
   		const schSubPointEvg3 = "";
   		academyDTO = {"idx": idx, "lastAcademy": lastAcademy, "selectAcademy" : selectAcademy, "schDepType3" : schDepType3
			, "schName3": schName3, "schSite3": schSite3, "schNight3": schNight3, "schAdmis3": schAdmis3, "schGrad3": schGrad3, "schMajor3": schMajor3, "schPointEvg3": schPointEvg3
			, "schEnterYm3": schEnterYm3, "schGradYm3": schGradYm3, "schSubMajor3": schSubMajor3, "schSubPointEvg3": schSubPointEvg3
			};
	
	} else if(selectAcademy == 'mast_list') {
		const schName4 = "";
   		const schSite4 = "";
   		const schNight4 = "";
   		const schAdmis4 = "";
   		const schGrad4 = "";
   		const schEnterYm4 = "";
   		const schGradYm4 = "";
   		const schMajor4 = "";
   		const schPointEvg4 = "";
   		academyDTO = {"idx": idx, "lastAcademy": lastAcademy, "selectAcademy" : selectAcademy, "schAdmis4": schAdmis4
			, "schName4": schName4, "schSite4": schSite4, "schNight4": schNight4, "schGrad4": schGrad4, "schEnterYm4": schEnterYm4, "schGradYm4": schGradYm4, "schMajor4": schMajor4, "schPointEvg4": schPointEvg4
			};
   		
	} else if(selectAcademy == 'doct_list') {
		const schName5 = "";
   		const schSite5 = "";
   		const schNight5 = "";
   		const schAdmis5 = "";
   		const schGrad5 = "";
   		const schEnterYm5 = "";
   		const schGradYm5 = "";
   		const schMajor5 = "";
   		const schPointEvg5 = "";
   		academyDTO = {"idx": idx, "lastAcademy": lastAcademy, "selectAcademy" : selectAcademy, "schAdmis5": schAdmis5
			, "schName5": schName5, "schSite5": schSite5, "schNight5": schNight5, "schGrad5": schGrad5, "schEnterYm5": schEnterYm5, "schGradYm5": schGradYm5, "schMajor5": schMajor5, "schPointEvg5": schPointEvg5
			};
	}
	
	var htmlString = "";
	
	$.ajax({
		url: uri,
		type: "POST",
		headers: headers,
		dataType: "json",
		data: JSON.stringify(academyDTO),
		success: function(response) {
			if (response.result == false) {
				alert("삭제에 실패하였습니다.");
				return false;
			}
			alert("삭제되었습니다.");
			
			if(selectAcademy == 'high_list') {
				$("#high_list").remove();
				
			} else if(selectAcademy == 'colg_list') {
				$("#colg_list").remove();
			
			} else if(selectAcademy == 'univ_list') {
				$("#univ_list").remove();
			
			} else if(selectAcademy == 'mast_list') {
				$("#mast_list").remove();
			
			} else if(selectAcademy == 'doct_list') {
				$("#doct_list").remove();
			}
			
			
		},
		error: function(request, status, error) {
			alert("에러가 발생하였습니다.");
			return false;
		}
	});
	
}
/*[- end of function -]*/
   	
function printSchInfo(selectAcademy) {

	if(selectAcademy == 'high_list') {
		
	} else if(selectAcademy == 'colg_list') {
	
	} else if(selectAcademy == 'univ_list') {
	
	} else if(selectAcademy == 'mast_list') {
	
	} else if(selectAcademy == 'doct_list') {
	
	}

}
/*[- end of function -]*/
     
/*]]>*/