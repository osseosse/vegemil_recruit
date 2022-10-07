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


$(document).on("click", "#inputSchoolBtn", function(){

	alert($("#inSch").val());
	$("#schName").val($("#inSch").val());

});

$(document).on("click", "#inputMajorBtn", function(){

	alert($("#inMaj").val());
	$("#schMajor").val($("#inMaj").val());

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
	}
	
	$(".base option").prop("selected", false); 
	$("input[class='inType01 ']").val('');
	$("#searchArea").show();
	$("#gradArea").show();
	$("#schArea").show();
	$("#nightArea").show();
	$("#howLongArea").show();
	
 
});

 
 //학교검색
 function fnSchool() {
 
    const selectAcademy = $("#selectAcademy option:selected").val();
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
 function fnMajor() {
 
    const majorName = $("#majorName").val();
    const uri = '/recruit/majorList?majorName=' + majorName;
    var majorList;
    var timehtml;
    var size;
    
    $.ajax({
        type: 'GET',
        url: uri,
        contentType: "application/json; charset=utf-8",
	    dataType:'json',
        success: function (data) {
            
            if(data != null){
	            majorList = data.majorList;
	            timehtml = "";
	            size = majorList.length;
	            
	            for(var i=0;i<size; i++){
                	timehtml+='<li class="majLi"><a>'+majorList[i]+'</a></li>';
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

function registerAcademy() {

	const uri = "/application/updateAcademy";
	const headers = {"Content-Type": "application/json", "X-HTTP-Method-Override": "POST"};
	const idx = $("#idx").val();
	const selectAcademy = $("#selectAcademy option:selected").val();
	const lastAcademy = $("#lastAcademy").val();
	
	if(selectAcademy == 'high_list') {
		const schName1 = $("#schName1").val();
   		const schSite1 = $("#schSite1").val();
   		const schNight1 = $("#schNight1").val();
   		const schAdmis1 = $("#schAdmis1").val();
   		const schEnterYm1 = $("#schEnterYm1").val();
   		const schGradYm1 = $("#schGradYm1").val();
   		var academyDTO = {"idx": idx, "lastAcademy": lastAcademy
			, "schName1": schName1, "schSite1": schSite1, "schNight1": schNight1, "schAdmis1": schAdmis1, "schEnterYm1": schEnterYm1, "schGradYm1": schGradYm1
			};
   		
	} else if(selectAcademy == 'colg_list') {
		const schName2 = $("#schName2").val();
   		const schSite2 = $("#schSite2").val();
   		const schNight2 = $("#schNight2").val();
   		const schPointEvg2 = $("#schPointEvg2").val();
   		const schMajor2 = $("#schMajor2").val();
   		const schEnterYm2 = $("#schEnterYm2").val();
   		const schGradYm2 = $("#schGradYm2").val();
   		const schSubMajor2 = $("#schSubMajor2").val();
   		const schSubPointEvg2 = $("#schSubPointEvg2").val();
   		var academyDTO = {"idx": idx, "lastAcademy": lastAcademy
			, "schName2": schName2, "schSite2": schSite2, "schNight2": schNight2, "schPointEvg2": schPointEvg2, "schMajor2": schMajor2, "schEnterYm2": schEnterYm2, "schGradYm2": schGradYm2
			};
   		
	} else if(selectAcademy == 'univ_list') {
		const schName3 = $("#schName3").val();
   		const schSite3 = $("#schSite3").val();
   		const schNight3 = $("#schNight3").val();
   		const schMajor3 = $("#schMajor3").val();
   		const schPointEvg3 = $("#schPointEvg3").val();
   		const schEnterYm3 = $("#schEnterYm3").val();
   		const schGradYm3 = $("#schGradYm3").val();
   		const schSubMajor3 = $("#schSubMajor3").val();
   		const schSubPointEvg3 = $("#schSubPointEvg3").val();
   		var academyDTO = {"idx": idx, "lastAcademy": lastAcademy
			, "schName3": schName3, "schSite3": schSite3, "schNight3": schNight3, "schMajor3": schMajor3, "schPointEvg3": schPointEvg3, "schEnterYm3": schEnterYm3, "schGradYm3": schGradYm3, "schSubMajor3": schSubMajor3, "schSubPointEvg3": schSubPointEvg3
			};
	
	} else if(selectAcademy == 'mast_list') {
		const schName4 = $("#schName4").val();
   		const schSite4 = $("#schSite4").val();
   		const schNight4 = $("#schNight4").val();
   		const schEnterYm4 = $("#schEnterYm4").val();
   		const schGradYm4 = $("#schGradYm4").val();
   		const schMajor4 = $("#schMajor4").val();
   		const schPointEvg4 = $("#schPointEvg4").val();
   		var academyDTO = {"idx": idx, "lastAcademy": lastAcademy
			, "schName4": schName4, "schSite4": schSite4, "schNight4": schNight4, "schEnterYm4": schEnterYm4, "schGradYm4": schGradYm4, "schMajor4": schMajor4, "schPointEvg4": schPointEvg4
			};
   		
	} else if(selectAcademy == 'doct_list') {
		const schName5 = $("#schName5").val();
   		const schSite5 = $("#schSite5").val();
   		const schNight5 = $("#schNight5").val();
   		const schEnterYm5 = $("#schEnterYm5").val();
   		const schGradYm5 = $("#schGradYm5").val();
   		const schMajor5 = $("#schMajor5").val();
   		const schPointEvg5 = $("#schPointEvg5").val();
   		var academyDTO = {"idx": idx, "lastAcademy": lastAcademy
			, "schName5": schName5, "schSite5": schSite5, "schNight5": schNight5, "schEnterYm5": schEnterYm5, "schGradYm5": schGradYm5, "schMajor5": schMajor5, "schPointEvg5": schPointEvg5
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
				alert("저장에 실패하였습니다.");
				return false;
			}
			//printSchInfo(selectAcademy);
			alert("저장되었습니다.");
			
			if(selectAcademy == 'high_list') {
				
			} else if(selectAcademy == 'colg_list') {
			
			} else if(selectAcademy == 'univ_list') {
			
				htmlString+= "<li>";
            	htmlString+= "	<dl>";
            	htmlString+= "		<dt>";
            	htmlString+= "			<span class='gray3'>학사</span>";
				htmlString+= "		</dt>";
				htmlString+= "		<dd>";						
				htmlString+= "			<span class='sublist'>"+schName3+"</span>";
				htmlString+= "			<span class='sublist'>"+schSite3+"</span>";
				htmlString+= "			<span class='sublist'>"+schNight3+"</span>";
				htmlString+= "			<span class='sublist'>"+schMajor3+"</span>";
				htmlString+= "			<span class='sublist'>"+schPointEvg3+"</span>";
				htmlString+= "			<span class='sublist'>"+schSubMajor3+"</span>";
				htmlString+= "			<span class='sublist'>"+schSubPointEvg3+"</span>";
				htmlString+= "			<span class='sublist'>"+schEnterYm3+" ~ "+schGradYm3+"</span>
				htmlString+= "		</dd>";
				htmlString+= "		<dd>";
				htmlString+= "			<span><a href='javascript:editAcademy(univ);'><img src='/img/ico_edit.png' alt='수정'/></a></span>";
				htmlString+= "			<span><a href='javascript:deleteAcademy(univ);'><img src='/img/ico_delete.png' alt='삭제'/></a></span>"; 
				htmlString+= "		</dd><br class="clear">
				htmlString+= "	</dl>									
				htmlString+= "</li>
			
			} else if(selectAcademy == 'mast_list') {
			
			} else if(selectAcademy == 'doct_list') {
			
			}
			
			$("#con-list").append(htmlString).trigger('create');
			
		},
		error: function(xhr, status, error) {
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
     
