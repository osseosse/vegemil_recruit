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

$(function () {
	createTable();
	
	$('#btnDel').click(function(e){
		console.log('e', e)
	    var form = document.form;
	    var table = $('#noticeList').DataTable();
	    console.log('table', table)
	
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
	
	      
	    // Output form data to a console
	    console.log("Form submission", decodeURIComponent($(form).serialize())); 
	      
	    if(confirm('삭제하시겠습니까?')){
			$.ajax({
				url : '/admin/recruit/deleteQnaList',
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
});

var createTable = function() {
	console.log('carateTable')
	var dt_basic_table = $('.datatables-basic'),
    dt_date_table = $('.dt-date');
    const table = $('.datatables-basic').DataTable();
    table.destroy();

  // DataTable with buttons
  // --------------------------------------------------------------------

  if (dt_basic_table.length) {
	const range = $('#fp-range').val().split(' to ');
	const startdate = range[0];
	const enddate = range[1];
	$('#sStartdate').attr("value",startdate);
	$('#sEnddate').attr("value",enddate);
    var dt_basic = dt_basic_table.DataTable({
      ajax: {
        url : '/admin/recruit/qnaList/table',
        dataType : 'json',
        contentType : "application/json; charset=utf-8",
        data:function(params){   
			var json = $("#frm").serializeObject();
			console.log('json', json);
			$.each(json,function(e){
				params[e] = json[e];
			});
		},
		error : function(xhr, ajaxSettings, thrownError) { 
			console.log('error');
		}
	  },
      columns: [
      	{ data: 'qId' },
        { data: 'qId' },
        { data: 'qId' },
        { data: 'rAnswerYn' },
        { data: 'rCompany' },
        { data: 'rTitle' },
        { data: 'memName' },
        { data: 'rInsertDate' }
      ],
      columnDefs: [
      	{
      		targets: 0,
      		className: "align-center",
      		orderable: false,
          	render: function (data, type, full, meta) {
	            return (
	              '<div class="form-check">' +
		              '<input type="checkbox" class="form-check-input" id="customCheck2" name="checkList" value="'+full['qId']+'" />' +
		              '<label class="form-check-label" for="customCheck2"></label>' +
	              '</div>'
	            );
	        }
      	},
      	{
			// For Checkboxes
            targets: 1,
            orderable: false,
            render: function (data, type, full, meta) {
              return (
                '<div class="form-check"> <input class="form-check-input dt-checkboxes" type="checkbox" name="checkList" value="'+data+'" id="checkbox' +
                data +
                '" /><label class="form-check-label" for="checkbox' +
                data +
                '"></label></div>'
              );
            },
            checkboxes: {
              selectAllRender:
                '<div class="form-check"> <input class="form-check-input" type="checkbox" value="" id="checkboxSelectAll" /><label class="form-check-label" for="checkboxSelectAll"></label></div>'
            }
      	},
      	{
      		targets: 2,
      		orderable: false,
          	render: function (data, type, full, meta) {
	            return '<button type="button" class="btn btn-primary btn-sm  waves-effect waves-float waves-light" onclick="location.href = \'qnaDetail?qId='+full['qId']+ '\'">답변하기</button>';
	        }
      	},
      	{
      		targets: 3,
      		orderable: false,
          	render: function (data, type, full, meta) {
	            if(full['rAnswerYn']=='Y')	return '<span class="badge rounded-pill badge-light-info ">완료</span>';
      			else	return '<span class="badge rounded-pill badge-light-warning ">미완료</span>';
	        }
      	},
      	{
      		targets: 4,
      		orderable: false,
          	render: function (data, type, full, meta) {
				return(full['rCompany']);
	        }
      	},
      	{
      		targets: 5,
      		orderable: false,
          	render: function (data, type, full, meta) {
				return(full['rTitle']);
	        }
      	},
      	{
      		targets: 6,
      		orderable: false,
          	render: function (data, type, full, meta) {
				return(full['memName']);
	        }
	    },
	    {
      		targets: 7,
      		orderable: false,
          	render: function (data, type, full, meta) {
				return(full['rInsertDate']);
	        }
	    }
      ],
      order: [[3, 'desc']],
      dom:
        '<"head-label"><"d-flex justify-content-between align-items-center mx-0 row"<"col-sm-12 col-md-6"l><"col-sm-12 col-md-6"f>>t<"card-body"<"col-sm-12 col-md-6"p>>',
      displayLength: 10,
      lengthMenu: [10, 25, 50, 75, 100],
      language: {
      	search : '검색',
      	emptyTable:     "표에서 사용할 수있는 데이터가 없습니다.",
      	zeroRecords: "해당 조건에 대한 검색 결과가 없습니다.",
      	lengthMenu: "페이지당 _MENU_ 개씩 보기",
        paginate: {
          previous: '&nbsp;',
          next: ' '
        }
      },
      info: false,
      searching: false
    });
    
    $('div.head-label').html('<h4 class="card-title">1:1 글 목록 <button type="button" id="btnDel" class="btn btn-outline-danger btn-sm me-1">선택삭제</button></h4>');
    
    $('input.dt-input').on('keyup', function () {
	    filterColumn($(this).val());
	  });
	  
	 dt_basic.on( 'order.dt', function () {
		dt_basic.column(0, {search:'applied'}).nodes().each( function (cell, i) {
			cell.innerHTML = i+1;
		} );
	 } ).draw(); 
  }
  //$(dt_basic_table).removeClass("no-footer dataTable");
}

