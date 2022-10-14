/*<![CDATA[*/
	$(document).ready(function(){

		var langCount = /*[[ ${app.langCount} ]]*/;
		var licCount = /*[[ ${app.licCount} ]]*/;
		var medalCount = /*[[ ${app.medalCount} ]]*/;
		var forCount = /*[[ ${app.forCount} ]]*/;
		
		var langName1 = /*[[ ${app.langName1} ]]*/;
		var langLicName1 = /*[[ ${app.langLicName1} ]]*/;
		var langName2 = /*[[ ${app.langName2} ]]*/;
		var langLicName2 = /*[[ ${app.langLicName2} ]]*/;
		var langName3 = /*[[ ${app.langName3} ]]*/;
		var langLicName3 = /*[[ ${app.langLicName3} ]]*/;
		var langName4 = /*[[ ${app.langName4} ]]*/;
		var langLicName4 = /*[[ ${app.langLicName4} ]]*/;
		
		if(langCount == 1) {
			$("select[name='langName1").append("<option value='"+langName1+"'>"+langName1+"</option>");
			$("select[name='langLicName1").append("<option value='"+langLicName1+"'>"+langLicName1+"</option>");
			$("select[name='langName1']").val(langName1).prop("selected", true);
			$("select[name='langLicName1']").val(langLicName1).prop("selected", true);
			$("input[name='langPoint1']").val(/*[[ ${app.langPoint1} ]]*/);
			$("input[name='langLv1']").val(/*[[ ${app.langLv1} ]]*/);
			$("input[name='langLicNum1']").val(/*[[ ${app.langLicNum1} ]]*/);
			$("input[name='langGet1']").val(/*[[ ${app.langGet1} ]]*/);
		} else if(langCount == 2) {
			$("select[name='langName1").append("<option value='"+langName1+"'>"+langName1+"</option>");
			$("select[name='langLicName1").append("<option value='"+langLicName1+"'>"+langLicName1+"</option>");
			$("select[name='langName1']").val(langName1).prop("selected", true);
			$("select[name='langLicName1']").val(langLicName1).prop("selected", true);
			$("input[name='langPoint1']").val(/*[[ ${app.langPoint1} ]]*/);
			$("input[name='langLv1']").val(/*[[ ${app.langLv1} ]]*/);
			$("input[name='langLicNum1']").val(/*[[ ${app.langLicNum1} ]]*/);
			$("input[name='langGet1']").val(/*[[ ${app.langGet1} ]]*/);
			
			$("select[name='langName2").append("<option value='"+langName2+"'>"+langName2+"</option>");
			$("select[name='langLicName2").append("<option value='"+langLicName2+"'>"+langLicName2+"</option>");
			$("select[name='langName2']").val(langName2).prop("selected", true);
			$("select[name='langLicName2']").val(langLicName2).prop("selected", true);
			$("input[name='langPoint2']").val(/*[[ ${app.langPoint2} ]]*/);
			$("input[name='langLv2']").val(/*[[ ${app.langLv2} ]]*/);
			$("input[name='langLicNum2']").val(/*[[ ${app.langLicNum2} ]]*/);
			$("input[name='langGet2']").val(/*[[ ${app.langGet2} ]]*/);
			
		} else if(langCount == 3) {
			$("select[name='langName1']").val(/*[[ ${app.langName1} ]]*/).prop("selected", true);
			$("select[name='langLicName1']").val(/*[[ ${app.langLicName1} ]]*/).prop("selected", true);
			$("input[name='langPoint1']").val(/*[[ ${app.langPoint1} ]]*/);
			$("input[name='langLv1']").val(/*[[ ${app.langLv1} ]]*/);
			$("input[name='langLicNum1']").val(/*[[ ${app.langLicNum1} ]]*/);
			$("input[name='langGet1']").val(/*[[ ${app.langGet1} ]]*/);
			$("select[name='langName2").append("<option value='"+langName2+"'>"+langName2+"</option>");
			$("select[name='langLicName2").append("<option value='"+langLicName2+"'>"+langLicName2+"</option>");
			$("select[name='langName2']").val(langName2).prop("selected", true);
			$("select[name='langLicName2']").val(langLicName2).prop("selected", true);
			$("input[name='langPoint2']").val(/*[[ ${app.langPoint2} ]]*/);
			$("input[name='langLv2']").val(/*[[ ${app.langLv2} ]]*/);
			$("input[name='langLicNum2']").val(/*[[ ${app.langLicNum2} ]]*/);
			$("input[name='langGet2']").val(/*[[ ${app.langGet2} ]]*/);
			$("select[name='langName3").append("<option value='"+langName3+"'>"+langName3+"</option>");
			$("select[name='langLicName3").append("<option value='"+langLicName3+"'>"+langLicName3+"</option>");
			$("select[name='langName3']").val(langName3).prop("selected", true);
			$("select[name='langLicName3']").val(langLicName3).prop("selected", true);
			$("input[name='langPoint3']").val(/*[[ ${app.langPoint3} ]]*/);
			$("input[name='langLv3']").val(/*[[ ${app.langLv3} ]]*/);
			$("input[name='langLicNum3']").val(/*[[ ${app.langLicNum3} ]]*/);
			$("input[name='langGet3']").val(/*[[ ${app.langGet3} ]]*/);
			
		} else if(langCount == 4) {
			$("select[name='langName1']").val(/*[[ ${app.langName1} ]]*/).prop("selected", true);
			$("select[name='langLicName1']").val(/*[[ ${app.langLicName1} ]]*/).prop("selected", true);
			$("input[name='langPoint1']").val(/*[[ ${app.langPoint1} ]]*/);
			$("input[name='langLv1']").val(/*[[ ${app.langLv1} ]]*/);
			$("input[name='langLicNum1']").val(/*[[ ${app.langLicNum1} ]]*/);
			$("input[name='langGet1']").val(/*[[ ${app.langGet1} ]]*/);
			$("select[name='langName2").append("<option value='"+langName2+"'>"+langName2+"</option>");
			$("select[name='langLicName2").append("<option value='"+langLicName2+"'>"+langLicName2+"</option>");
			$("select[name='langName2']").val(langName2).prop("selected", true);
			$("select[name='langLicName2']").val(langLicName2).prop("selected", true);
			$("input[name='langPoint2']").val(/*[[ ${app.langPoint2} ]]*/);
			$("input[name='langLv2']").val(/*[[ ${app.langLv2} ]]*/);
			$("input[name='langLicNum2']").val(/*[[ ${app.langLicNum2} ]]*/);
			$("input[name='langGet2']").val(/*[[ ${app.langGet2} ]]*/);
			$("select[name='langName3").append("<option value='"+langName3+"'>"+langName3+"</option>");
			$("select[name='langLicName3").append("<option value='"+langLicName3+"'>"+langLicName3+"</option>");
			$("select[name='langName3']").val(langName3).prop("selected", true);
			$("select[name='langLicName3']").val(langLicName3).prop("selected", true);
			$("input[name='langPoint3']").val(/*[[ ${app.langPoint3} ]]*/);
			$("input[name='langLv3']").val(/*[[ ${app.langLv3} ]]*/);
			$("input[name='langLicNum3']").val(/*[[ ${app.langLicNum3} ]]*/);
			$("input[name='langGet3']").val(/*[[ ${app.langGet3} ]]*/);
			
			$("select[name='langName4").append("<option value='"+langName4+"'>"+langName4+"</option>");
			$("select[name='langLicName4").append("<option value='"+langLicName4+"'>"+langLicName4+"</option>");
			$("select[name='langName4']").val(langName4).prop("selected", true);
			$("select[name='langLicName4']").val(langLicName4).prop("selected", true);
			$("input[name='langPoint4']").val(/*[[ ${app.langPoint4} ]]*/);
			$("input[name='langLv4']").val(/*[[ ${app.langLv4} ]]*/);
			$("input[name='langLicNum4']").val(/*[[ ${app.langLicNum4} ]]*/);
			$("input[name='langGet4']").val(/*[[ ${app.langGet4} ]]*/);
			
		}
		
		if(licCount == 1) {
			$("input[name='licName1']").val(/*[[ ${app.licName1} ]]*/);
			$("input[name='licNum1']").val(/*[[ ${app.licNum1} ]]*/);
			$("input[name='licGetDate1']").val(/*[[ ${app.licGetDate1} ]]*/);
			$("input[name='licPub1']").val(/*[[ ${app.licPub1} ]]*/);
		} else if(licCount == 2) {
			$("input[name='licName1']").val(/*[[ ${app.licName1} ]]*/);
			$("input[name='licNum1']").val(/*[[ ${app.licNum1} ]]*/);
			$("input[name='licGetDate1']").val(/*[[ ${app.licGetDate1} ]]*/);
			$("input[name='licPub1']").val(/*[[ ${app.licPub2} ]]*/);
			$("input[name='licName2']").val(/*[[ ${app.licName2} ]]*/);
			$("input[name='licNum2']").val(/*[[ ${app.licNum2} ]]*/);
			$("input[name='licGetDate2']").val(/*[[ ${app.licGetDate2} ]]*/);
			$("input[name='licPub2']").val(/*[[ ${app.licPub2} ]]*/);
		} else if(licCount == 3) {
			$("input[name='licName1']").val(/*[[ ${app.licName1} ]]*/);
			$("input[name='licNum1']").val(/*[[ ${app.licNum1} ]]*/);
			$("input[name='licGetDate1']").val(/*[[ ${app.licGetDate1} ]]*/);
			$("input[name='licPub1']").val(/*[[ ${app.licPub1} ]]*/);
			$("input[name='licName2']").val(/*[[ ${app.licName2} ]]*/);
			$("input[name='licNum2']").val(/*[[ ${app.licNum2} ]]*/);
			$("input[name='licGetDate2']").val(/*[[ ${app.licGetDate2} ]]*/);
			$("input[name='licPub2']").val(/*[[ ${app.licPub2} ]]*/);
			$("input[name='licName3']").val(/*[[ ${app.licName3} ]]*/);
			$("input[name='licNum3']").val(/*[[ ${app.licNum3} ]]*/);
			$("input[name='licGetDate3']").val(/*[[ ${app.licGetDate3} ]]*/);
			$("input[name='licPub3']").val(/*[[ ${app.licPub3} ]]*/);
		} else if(licCount == 4) {
			$("input[name='licName1']").val(/*[[ ${app.licName1} ]]*/);
			$("input[name='licNum1']").val(/*[[ ${app.licNum1} ]]*/);
			$("input[name='licGetDate1']").val(/*[[ ${app.licGetDate1} ]]*/);
			$("input[name='licPub1']").val(/*[[ ${app.licPub1} ]]*/);
			$("input[name='licName2']").val(/*[[ ${app.licName2} ]]*/);
			$("input[name='licNum2']").val(/*[[ ${app.licNum2} ]]*/);
			$("input[name='licGetDate2']").val(/*[[ ${app.licGetDate2} ]]*/);
			$("input[name='licPub2']").val(/*[[ ${app.licPub2} ]]*/);
			$("input[name='licName3']").val(/*[[ ${app.licName3} ]]*/);
			$("input[name='licNum3']").val(/*[[ ${app.licNum3} ]]*/);
			$("input[name='licGetDate3']").val(/*[[ ${app.licGetDate3} ]]*/);
			$("input[name='licPub3']").val(/*[[ ${app.licPub3} ]]*/);
			$("input[name='licName4']").val(/*[[ ${app.licName4} ]]*/);
			$("input[name='licNum4']").val(/*[[ ${app.licNum4} ]]*/);
			$("input[name='licGetDate4']").val(/*[[ ${app.licGetDate4} ]]*/);
			$("input[name='licPub4']").val(/*[[ ${app.licPub4} ]]*/);
		}
		
		if(medalCount == 1) {
			$("input[name='medalName1']").val(/*[[ ${app.medalName1} ]]*/);
			$("input[name='medalDate1']").val(/*[[ ${app.medalDate1} ]]*/);
			$("input[name='medalContent1']").val(/*[[ ${app.medalContent1} ]]*/);
		} else if(medalCount == 2) {
			$("input[name='medalName1']").val(/*[[ ${app.medalName1} ]]*/);
			$("input[name='medalDate1']").val(/*[[ ${app.medalDate1} ]]*/);
			$("input[name='medalContent1']").val(/*[[ ${app.medalContent1} ]]*/);
			$("input[name='medalName2']").val(/*[[ ${app.medalName2} ]]*/);
			$("input[name='medalDate2']").val(/*[[ ${app.medalDate2} ]]*/);
			$("input[name='medalContent2']").val(/*[[ ${app.medalContent2} ]]*/);
		} else if(medalCount == 3) {
			$("input[name='medalName1']").val(/*[[ ${app.medalName1} ]]*/);
			$("input[name='medalDate1']").val(/*[[ ${app.medalDate1} ]]*/);
			$("input[name='medalContent1']").val(/*[[ ${app.medalContent1} ]]*/);
			$("input[name='medalName2']").val(/*[[ ${app.medalName2} ]]*/);
			$("input[name='medalDate2']").val(/*[[ ${app.medalDate2} ]]*/);
			$("input[name='medalContent2']").val(/*[[ ${app.medalContent2} ]]*/);
			$("input[name='medalName3']").val(/*[[ ${app.medalName3} ]]*/);
			$("input[name='medalDate3']").val(/*[[ ${app.medalDate3} ]]*/);
			$("input[name='medalContent3']").val(/*[[ ${app.medalContent3} ]]*/);
		}
		
		if(forCount == 1) {
			$("input[name='forState1']").val(/*[[ ${app.forState1} ]]*/);
			$("input[name='forSDate1']").val(/*[[ ${app.forSDate1} ]]*/);
			$("input[name='forEDate1']").val(/*[[ ${app.forEDate1} ]]*/);
			$("input[name='forName1']").val(/*[[ ${app.forName1} ]]*/);
		} else if(forCount == 2) {
			$("input[name='forState1']").val(/*[[ ${app.forState1} ]]*/);
			$("input[name='forSDate1']").val(/*[[ ${app.forSDate1} ]]*/);
			$("input[name='forEDate1']").val(/*[[ ${app.forEDate1} ]]*/);
			$("input[name='forName1']").val(/*[[ ${app.forName1} ]]*/);
			$("input[name='forState2']").val(/*[[ ${app.forState2} ]]*/);
			$("input[name='forSDate2']").val(/*[[ ${app.forSDate2} ]]*/);
			$("input[name='forEDate2']").val(/*[[ ${app.forEDate2} ]]*/);
			$("input[name='forName2']").val(/*[[ ${app.forName2} ]]*/);
		}

	});
/*]]>*/
