/**
 * DataTables Basic
 */
 
 'use strict';

// Advanced Search Functions Starts
// --------------------------------------------------------------------

// Filter column wise function
 
function filterColumn(val) {

    var startDate = $('.start_date').val(),
      endDate = $('.end_date').val();
      
      if (startDate !== '' && endDate !== '') {
        filterByDate(5, startDate, endDate); // We call our filter function
      }

    $('.datatables-basic').dataTable().fnDraw();

}

function copyNotice(sTh){
	if(confirm('복사하시겠습니까?')){
		$.ajax({
			url : '/admin/recruit/copyNotice?sTh=' + sTh,
			type : "get",
			dataType : "json",
			success : function(data) {
				if(data == "false"){
					alert("채용공고 복사에 실패했습니다.");
				}
				else{
					alert("채용공고 복사에 성공했습니다.");
					window.location.reload();
					$('.datatables-basic').DataTable().ajax.reload();
				}
				
			},
			error : function(){
			}
		});
	}
}

// Datepicker for advanced filter
var separator = ' - ',
  rangePickr = $('.flatpickr-range'),
  dateFormat = 'yyyy-MM-dd';
var options = {
  autoUpdateInput: false,
  autoApply: true,
  locale: {
    format: dateFormat,
    separator: separator
  },
  opens: $('html').attr('data-textdirection') === 'rtl' ? 'left' : 'right'
};

//
if (rangePickr.length) {
  rangePickr.flatpickr({
    mode: 'range',
    dateFormat: 'Y-m-d',
    onClose: function (selectedDates, dateStr, instance) {
      var startDate = '',
        endDate = new Date();
        
      var month_s,date_s,month_e,date_e;
      
      if((selectedDates[0].getMonth() + 1) < 10) month_s = '0'+(selectedDates[0].getMonth() + 1);
      else month_s = (selectedDates[0].getMonth() + 1);
      if(selectedDates[0].getDate() < 10) date_s = '0'+selectedDates[0].getDate();
      else date_s = selectedDates[0].getDate();
        
      if (selectedDates[0] != undefined) {
        startDate =
          selectedDates[0].getFullYear() + '-' + month_s + '-' + date_s;
        $('.start_date').val(startDate);
      }

      if((selectedDates[1].getMonth() + 1) < 10) month_e = '0'+(selectedDates[1].getMonth() + 1);
      else month_e = (selectedDates[1].getMonth() + 1);
      if(selectedDates[1].getDate() < 10) date_e = '0'+selectedDates[1].getDate();
      else date_e = selectedDates[1].getDate();
      
      if (selectedDates[1] != undefined) {
        endDate =
          selectedDates[1].getFullYear() + '-' + month_e + '-' + date_e;
        $('.end_date').val(endDate);
      }
      $(rangePickr).trigger('change').trigger('keyup');
    }
  });
}

// Advance filter function
// We pass the column location, the start date, and the end date
var filterByDate = function (column, startDate, endDate) {
  // Custom filter syntax requires pushing the new filter to the global filter array
  $.fn.dataTableExt.afnFiltering.push(function (oSettings, aData, iDataIndex) {
    var rowDate = normalizeDate(aData[column]),
      start = normalizeDate(startDate),
      end = normalizeDate(endDate);
	
    // If our date from the row is between the start and end
    if (start <= rowDate && rowDate <= end) {
      return true;
    } else if (rowDate >= start && end === '' && start !== '') {
      return true;
    } else if (rowDate <= end && start === '' && end !== '') {
      return true;
    } else {
      return false;
    }
  });
};

// converts date strings to a Date object, then normalized into a YYYYMMMDD format (ex: 20131220). Makes comparing dates easier. ex: 20131220 > 20121220
var normalizeDate = function (dateString) {
  //var date = new Date(dateString);
  var normalized = dateString.slice(0,4) + dateString.slice(5,7) + dateString.slice(8,10);
    //date.getFullYear() + '' + ('0' + (date.getMonth() + 1)).slice(-2) + '' + ('0' + date.getDate()).slice(-2);
  return normalized;
};
// Advanced Search Functions Ends

//$(function () {

function createTable(){
	var dt_basic_table = $('.datatables-basic'),
    dt_date_table = $('.dt-date');
    console.log('dt_basic_table.length', dt_basic_table.length);
    const table = $('#volunteerList').DataTable();
    table.destroy();

  // DataTable with buttons
  // --------------------------------------------------------------------

  if (dt_basic_table.length) {
	const range = $('#volunteerRange').val().split(' to ');
	console.log('range', range)
	const startdate = range[0];
	const enddate = range[1];
	$('#sStartdate').attr("value",startdate);
	$('#sEnddate').attr("value",enddate);
	$('#setupTh').attr("value", $('#sTh').val());
	console.log('sStartdate', sStartdate)
	
    var dt_basic = dt_basic_table.DataTable({
      ajax:  {
		url : '/admin/recruit/volunteerList/table',  //+ sTh,
		dataType : "json",
		contentType: "application/json; charset=utf-8",
		data:function(params){   
			params.sTh = $('#sTh').val();
			const json = $("#frm").serializeObject();
			console.log('json', json);
			$.each(json,function(e){
				params[e] = json[e];
			});
		},
		/*
		success : function(data) { 
			console.log('data', data.data.length);
		},
		*/
		error : function(xhr, ajaxSettings, thrownError) { 
			alert('error');
		}
	  },
      columns: [
      	{ data: 'idx' },
      	{ data: 'setupTh' },
        { data: 'memName' },
        { data: 'joinOk' },
        { data: 'birthday' },
        { data: 'sex' },
        { data: 'joinCompany' },
        { data: 'joinField1' },
        { data: 'joinArea1' },
        { data: 'schName4' },
        { data: 'schMajor4' },
        { data: 'idx' },
        { data: 'rPortFile' },
        { data: 'joinDate' },
      ],
      columnDefs: [
      	{
      		targets: 0,
      		className: "align-center",
      		orderable: false,
      		/*
      		checkboxes: {
			            'selectRow': true
			            }
			*/
          	render: function (data, type, full, meta) {
	            return (
	              '<div class="form-check">' +
		              '<input type="checkbox" class="form-check-input" id="customCheck2" name="checkList" value="'+full['idx']+'" />' +
		              '<label class="form-check-label" for="customCheck2"></label>' +
	              '</div>'
	            );
	        }
      	},
      	{
      		targets: 1,
      		className: "text-center",
      		orderable: true
      	},
      	{
      		targets: 2,
      		className: "text-center",
      		orderable: false
      	},
      	{
      		targets: 3,
      		className: "text-center",
      		orderable: false,
          	render: function (data, type, full, meta) {
				let checked0 = '';
          		let checked1 = '';
          		let checked2 = '';
          		let checked3 = '';
          		
          		
          		if(full['joinOk'] == '0')   	checked0 = 'checked';
          		if(full['joinOk'] == '1')		checked1 = 'checked';
          		if(full['joinOk'] == '2')		checked2 = 'checked';
          		if(full['joinOk'] == '3')		checked3 = 'checked';
          		
	            return (
				  '<div class="form-check form-check-inline">' +
		              '<input class="form-check-input" type="radio" name="radioName' + full['idx'] + '" id="radioId01' + full['idx'] + '" value="0" ' + checked0 + ' onclick=javascript:joinOkUpdate('+full['idx']+',"0"); />' +
		              '<label class="form-check-label" for="radioId01' + full['idx'] + '">불합격</label>' +
	              '</div>' + 
	              '<div class="form-check form-check-inline">' +
		              '<input class="form-check-input" type="radio" name="radioName' + full['idx'] + '" id="radioId01' + full['idx'] + '" value="1" ' + checked1 + ' onclick=javascript:joinOkUpdate('+full['idx']+',"1"); />' +
		              '<label class="form-check-label" for="radioId01' + full['idx'] + '">서류</label>' +
	              '</div>' + 
	              '<div class="form-check form-check-inline">' +
		              '<input class="form-check-input" type="radio" name="radioName' + full['idx'] + '" id="radioId02' + full['idx'] + '" value="2" ' + checked2 + ' onclick=javascript:joinOkUpdate('+full['idx']+',"2"); />' +
		              '<label class="form-check-label" for="radioId02' + full['idx'] + '">1차</label>' +
	              '</div>' +
	              '<div class="form-check form-check-inline">' +
		              '<input class="form-check-input" type="radio" name="radioName' + full['idx'] + '" id="radioId03' + full['idx'] + '" value="3" ' + checked3 + ' onclick=javascript:joinOkUpdate('+full['idx']+',"3"); />' +
		              '<label class="form-check-label" for="radioId03' + full['idx'] + '">2차</label>' +
	              '</div>'
	            );
	        }
      	},
      	{
      		targets: 4,
      		orderable: false
      	},
      	{
      		targets: 5,
      		orderable: false,
          	render: function (data, type, full, meta) {
          		var sex;
          		
          		if(full['sex'] == '1') sex='남'
          		else if(full['sex'] == '2') sex='여'
          		else sex = null;
          			
	            return (sex);
	        }
      	},
      	{
      		targets: 6,
      		orderable: true,
      		render: function (data, type, full, meta) {
          		if(full['joinCompany'] == null) return null;
          		else return data;
	        }
      	},
      	{
      		targets: 7,
      		orderable: false,
      		render: function (data, type, full, meta) {
          		if(full['joinField1'] == null) return null;
          		else return data;
	        }
      	},
      	{
      		targets: 8,
      		orderable: false,
      		render: function (data, type, full, meta) {
          		if(full['joinArea1'] == null) return null;
          		else return data;
	        }
      	},
      	{
      		targets: 9,
      		orderable: false,
          	render: function (data, type, full, meta) {
          		if(full['schName4'] == null) return null;
          		else return data;
	        }
      	},
      	{
      		targets: 10,
      		orderable: false,
          	render: function (data, type, full, meta) {
          		if(full['schMajor4'] == null) return null;
          		else return data
	        }
      	},
      	{
      		targets: 11,
      		orderable: false,
          	render: function (data, type, full, meta) {
	            return (
//	            	'<a href="javascript:openRecruitDt('+data+', '+full['setupTh']+');" >' +
					'<a href="/admin/recruit/recruitDetail?setupTh='+full['setupTh']+'&idx='+data+ '" target=_blank>' + 
	            		'<button type="button" class="btn btn-outline-primary btn-sm">보기</button>' +
	            	'</a>'
	            );
	        }
      	},
      	{
      		targets: 12,
      		orderable: false,
          	render: function (data, type, full, meta) {
          		var tag='';
          		
          		if(full['rPortFile'] == null || full['rPortFile'] == '') tag='-';
          		else tag='<button type="button" class="btn btn-outline-primary btn-sm">다운로드</button>';
          			
	            return (tag);
	        }
      	},
      	{
      		targets: 13,
      		orderable: true,
          	render: function (data, type, full, meta) {
				return(full['joinDate'].substr(0,10));
	        }
      	}
      ],
      select: {
	     style:    'os',
	     selector: 'td:first-child'
	  },
      order: [[1, 'desc']],
      dom:
        '<"card-header border-bottom p-1"><"d-flex justify-content-between align-items-center mx-0 row"<"col-sm-12 col-md-6"l><"col-sm-12 col-md-6"f>>t<"card-body"<"col-sm-12 col-md-6"p>>',
      displayLength: 10,
      lengthMenu: [10, 25, 50, 75, 100],
      language: {
      	search : '검색',
      	emptyTable:     "표에서 사용할 수있는 데이터가 없습니다.",
      	zeroRecords: "검색된 데이터가 없습니다.",
      	lengthMenu: "페이지당 _MENU_ 개씩 보기",
        paginate: {
          previous: '&nbsp;',
          next: '&nbsp;'
        },
        aria: {
            "sortAscending":  ": 오름차순으로 정렬",
            "sortDescending": ": 내림차순으로 정렬"
        }
      },
      info: false
    });
    $('input.dt-input').on('keyup', function () {
	    filterColumn($(this).val());
	  });
  }
  //$(dt_basic_table).removeClass("no-footer dataTable");
}

//});

function joinOkUpdate(idx, joinOk) {
	console.log('idx', idx + ' ' + joinOk);
	$.ajax({
		url : '/admin/recruit/updateVolunteerList?idx=' + idx+'&joinOk='+joinOk,
		type : "get",
		dataType : "json",
		success : function(data) {
			if(data == "false"){
				alert("저장에 실패했습니다.");
			}
			else{
				alert("수정되었습니다.");
				//window.location.reload();
				$('.datatables-basic').DataTable().ajax.reload();
			}
			
		},
		error : function(){
		}
	});
}

function volunteerDelete() {
	console.log('volunteerDelete');
	var table = $('#volunteerList').DataTable();
	console.log('table', table.data().count())
}

$(function () {

	// 전체 체크 하는 부분
	$("[type=checkbox][name=allCheck]").on("change", function(){ //0
        var check = $(this).prop("checked"); //1
        //전체 체크
        if($(this).hasClass("form-check-input")){ //2
            $("[type=checkbox][name=checkList]").prop("checked", check);
     	}
    });
     
});

$('#btnDel').click(function(e){
	console.log('e', e)
      var form = document.form;
      var table = $('#volunteerList').DataTable();

      // Iterate over all checkboxes in the table
      table.$('input[type="checkbox"]').each(function(){
         // If checkbox doesn't exist in DOM
         if(!$.contains(document, this)){
            // If checkbox is checked
            if(this.checked){
               // Create a hidden element 
               $(form).append(
                  $('<input>')
                     .attr('type', 'hidden')
                     .attr('name', this.name)
                     .val(this.value)
               );
            }
         } 
      });

      // FOR TESTING ONLY
      
      // Output form data to a console
      $('#example-console').text($(form).serialize()); 
      console.log("Form submission", decodeURIComponent($(form).serialize())); 
    let objs;
	objs = document.createElement('input'); // 값이 들어있는 녀석의 형식
	objs.setAttribute('type', 'text'); // 값이 들어있는 녀석의 type
	objs.setAttribute('name', 'setupTh'); // 객체이름
	objs.setAttribute('value', $('#sTh').val()); //객체값
	form.appendChild(objs);
      
      if(confirm('삭제하신 내용은 복구가 불가능합니다.\n정말 삭제하시겠습니까?')){
		$.ajax({
			url : '/admin/recruit/deleteVolunteerList',
			type : "post",
			data : $(form).serialize(),
			dataType : "json",
			success : function(data) {
				console.log('data============',data);
				if(data){
					
					alert("삭제되었습니다.");
					//window.location.reload();
					$('.datatables-basic').DataTable().ajax.reload();
				}
				else{
					alert("실패했습니다.");
				}
				
			},
			error : function(){
			}
		});
	}
      
       
      // Prevent actual form submission
      e.preventDefault();
});

var openRecruitDt = function(idx, setupTh) {
	
	let url = "/admin/recruit/recruitDetail";
	let target = '지원자 이력서';
	let from = document.createElement('form');
	let objs;
	objs =  document.createElement('input'); // 값이 들어있는 녀석의 형식
	objs.setAttribute('type', 'text'); // 값이 들어있는 녀석의 type
	objs.setAttribute('name', 'idx'); // 객체이름
	objs.setAttribute('value', idx); //객체값
	form.appendChild(objs);
	window.open(url);
	form.setAttribute('onsubmit', "false");
	form.submit();
}

function reset() {
	console.log('reset')
	$('#sStartdate').attr("value", '');
	$('#sEnddate').attr("value", '');
}



