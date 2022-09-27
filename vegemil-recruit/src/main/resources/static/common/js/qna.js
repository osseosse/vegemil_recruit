/*<![CDATA[*/

function btnAvtive() {
	const rTitle = $('#rTitle').val();
	const rContent = $('#rContent').val();
	
	if($.trim(rTitle).length > 1 
	&& $.trim(rContent).length > 1) 
	{
		$("#saveBtn").attr("disabled", false); //해제
		$("#saveBtn").addClass('active');
	} else {
		$("#saveBtn").attr("disabled", true); //설정
		$("#saveBtn").removeClass('active');		
	}
}

//회사 선택 체크
$("#SelectBoxID").change( function() {

	const rCompany = $("select[name=rCompany]").val();
	
	if ($.trim(rCompany).length == 0) {
		$("#companyCheck").css('display', 'inline-block');
		$("#rCompany").addClass('error');
	} else {
		$("#companyCheck").css('display', 'none');
		$("#rCompany").removeClass('error');
	}
	
	btnAvtive();

});

//제목 체크
$('#rTitle').keyup(function () {

	const rTitle = $('#rTitle').val();
	
	if ($.trim(rTitle).length == 0) {
		$("#titleCheck").css('display', 'inline-block');
		$("#rTitle").addClass('error');
	} else {
		$("#titleCheck").css('display', 'none');
		$("#rTitle").removeClass('error');
	}
	
	btnAvtive();
	
});

//내용 체크
$('#rContent').keyup(function () {

	const rTitle = $('#rContent').val();
	
	if ($.trim(rContent).length == 0) {
		$("#contentCheck").css('display', 'inline-block');
		$("#rContent").addClass('error');
	} else {
		$("#contentCheck").css('display', 'none');
		$("#rContent").removeClass('error');
	}
	
	btnAvtive();
	
});


/*]]>*/