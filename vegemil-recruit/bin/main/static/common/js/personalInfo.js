function execPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                addr = addr+' '+extraAddr;
            
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('zip1').value = data.zonecode;
            document.getElementById("addr11").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("addr12").focus();
        }
        }).open();
    }
    /*[- end of function -]*/
    
    function getImageFiles(e) {
        const uploadFiles = [];
        const files = e.currentTarget.files;
        const imagePreview = document.querySelector('.IDpicture');
        const docFrag = new DocumentFragment();
        const  child =  document .getElementById( "photo" );

        if ([...files].length >= 2) {
          alert('이미지는 1개만 업로드가 가능합니다.');
          return;
        }

        // 파일 타입 검사
        [...files].forEach(file => {
          if (!file.type.match("image/.*")) {
            alert('이미지 파일만 업로드가 가능합니다.');
            return
          }

          // 파일 갯수 검사
          if ([...files].length < 2) {
            uploadFiles.push(file);
            const reader = new FileReader();
            reader.onload = (e) => {
              const preview = createElement(e, file);
              imagePreview.removeChild(child);
              imagePreview.appendChild(preview);
            };
            reader.readAsDataURL(file);
          }
        });
      }
      /*[- end of function -]*/

      function createElement(e, file) {
        const img = document.createElement('img');
        img.setAttribute('src', e.target.result);
        img.setAttribute('data-file', file.name);
        img.setAttribute('id', 'photo');

        return img;
      }
      const realUpload = document.querySelector('.real-upload');
      const upload = document.querySelector('.photoBox');

      upload.addEventListener('click', () => realUpload.click());

      realUpload.addEventListener('change', getImageFiles);
      /*[- end of function -]*/
    
      //리피터
      $(function () {
   	  'use strict';
   	  window.id = $('#famCount').val();
   	  
   	  $('.fam-repeater').repeater({
   		defaultValues: {
            'id': window.id,
        },
   	    show: function () {
   	      if(window.id < 4) {
	   	      $(this).slideDown();
	   	      window.id++;
	   	      $('#famCount').val(window.id);
   	      } else {
   	      	alert("가족은 4명까지 입력 가능합니다.");
   	      	return false;
   	      }
   	    },
   	    hide: function (deleteElement) {
   	      if (confirm('삭제하시겠습니까?')) {
   	    	window.id--;
            $('#famCount').val(window.id);
   	        $(this).slideUp(deleteElement);
   	      }
   	    }
   	  });
   	});
   	/*[- end of function -]*/

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
	 
	 $("input[name='milClass']:radio").change(function () {
        //라디오 버튼 값
        var clickVal = this.value;
        
        if(clickVal == '비대상') {
        	$("#milSapce").hide();
        } else {
        	$("#milSapce").show();
        }
                        
	});
	 
     
     function updatePersonalInfo() {

   		const uri = "/application/updatePersonalInfo";
   		const headers = {"Content-Type": "multipart/form-data", "X-HTTP-Method-Override": "POST"};
   		
   		$.ajax({
   			url: uri,
   			type: "POST",
   			//headers: headers,
   			dataType: "json",
   			contentType: false,
      		processData: false,
            data: new FormData($("#personalform")[0]),
			enctype: "multipart/form-data",
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
     
