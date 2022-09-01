

/*================================ 
    foot dorpmenu
================================*/
function myFunction() {
  document.getElementById("myDropdown").classList.toggle("show");
}

// Close the dropdown if the user clicks outside of it
window.onclick = function(event) {
  if (!event.target.matches('.dropbtn')) {
    var dropdowns = document.getElementsByClassName("dropdown-content");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
  }
}




/*================================ 
    select  
================================*/
window.onload = function(){
//$(document).ready(function () {
//$(window).load(function(){
//$(function() {
	var x, i, j, l, ll, selElmnt, a, b, c;
	/*look for any elements with the class "custom-sel":*/
	x = document.getElementsByClassName("custom-sel");
	l = x.length;
	for (i = 0; i < l; i++) {
	  selElmnt = x[i].getElementsByTagName("select")[0];
	  ll = selElmnt.length;
	  /*for each element, create a new DIV that will act as the selected item:*/
	  a = document.createElement("DIV");
	  a.setAttribute("class", "select-selected");
	  a.innerHTML = selElmnt.options[selElmnt.selectedIndex].innerHTML;
	  x[i].appendChild(a);
	  /*for each element, create a new DIV that will contain the option list:*/
	  b = document.createElement("DIV");
	  b.setAttribute("class", "select-items select-hide");
	  for (j = 1; j < ll; j++) {
		/*for each option in the original select element,
		create a new DIV that will act as an option item:*/
		c = document.createElement("DIV");
		c.innerHTML = selElmnt.options[j].innerHTML;
		c.addEventListener("click", function(e) {
			/*when an item is clicked, update the original select box,
			and the selected item:*/
			var y, i, k, s, h, sl, yl;
			s = this.parentNode.parentNode.getElementsByTagName("select")[0];
			sl = s.length;
			h = this.parentNode.previousSibling;
			for (i = 0; i < sl; i++) {
			  if (s.options[i].innerHTML == this.innerHTML) {
				s.selectedIndex = i;
				h.innerHTML = this.innerHTML;
				y = this.parentNode.getElementsByClassName("same-as-selected");
				yl = y.length;
				for (k = 0; k < yl; k++) {
				  y[k].removeAttribute("class");
				}
				this.setAttribute("class", "same-as-selected");
				break;
			  }
			}
			h.click();
		});
		b.appendChild(c);
	  }
	  x[i].appendChild(b);
	  console.log('custom-sel');
	  a.addEventListener("click", function(e) {
		  /*when the select box is clicked, close any other select boxes,
		  and open/close the current select box:*/
		  e.stopPropagation();
		  closeAllSelect(this);
		  this.nextSibling.classList.toggle("select-hide");
		  this.classList.toggle("select-arrow-active");
		});
	}
	function closeAllSelect(elmnt) {
	  /*a function that will close all select boxes in the document,
	  except the current select box:*/
	  var x, y, i, xl, yl, arrNo = [];
	  x = document.getElementsByClassName("select-items");
	  y = document.getElementsByClassName("select-selected");
	  xl = x.length;
	  yl = y.length;
	  for (i = 0; i < yl; i++) {
		if (elmnt == y[i]) {
		  arrNo.push(i)
		} else {
		  y[i].classList.remove("select-arrow-active");
		}
	  }
	  for (i = 0; i < xl; i++) {
		if (arrNo.indexOf(i)) {
		  x[i].classList.add("select-hide");
		}
	  }
	}
	/*if the user clicks anywhere outside the select box,
	then close all select boxes:*/
	document.addEventListener("click", closeAllSelect);
};




/*================================ 
    �Է��� COPY  
================================*/
  
//����, ����, ����
function add_item1(){
	  // pre_set �� �ִ� ������ �о�ͼ� ó��..
	  var div = document.createElement('div');
	  div.innerHTML = document.getElementById('pre_set1').innerHTML;
	  document.getElementById('field1').appendChild(div);
 }
 function remove_item1(obj){
	  // obj.parentNode �� �̿��Ͽ� ����
	  document.getElementById('field1').removeChild(obj.parentNode);
 }


 //���б�, IT�ɷ�
function add_item2(){
	  // pre_set �� �ִ� ������ �о�ͼ� ó��..
	  var div = document.createElement('div');
	  div.innerHTML = document.getElementById('pre_set2').innerHTML;
	  document.getElementById('field2').appendChild(div);
 }
 function remove_item2(obj){
	  // obj.parentNode �� �̿��Ͽ� ����
	  document.getElementById('field2').removeChild(obj.parentNode);
 }


//���п�, �ڰ� �� ����
function add_item3(){
	  // pre_set �� �ִ� ������ �о�ͼ� ó��..
	  var div = document.createElement('div');
	  div.innerHTML = document.getElementById('pre_set3').innerHTML;
	  document.getElementById('field3').appendChild(div);
 }
 function remove_item3(obj){
	  // obj.parentNode �� �̿��Ͽ� ����
	  document.getElementById('field3').removeChild(obj.parentNode);
 }    
 
 //�ڻ�, ������
function add_item4(){
	  // pre_set �� �ִ� ������ �о�ͼ� ó��..
	  var div = document.createElement('div');
	  div.innerHTML = document.getElementById('pre_set4').innerHTML;
	  document.getElementById('field4').appendChild(div);
 }
 function remove_item4(obj){
	  // obj.parentNode �� �̿��Ͽ� ����
	  document.getElementById('field4').removeChild(obj.parentNode);
 }   

 //�ؿܿ���/���Ȱ��
function add_item5(){
	  // pre_set �� �ִ� ������ �о�ͼ� ó��..
	  var div = document.createElement('div');
	  div.innerHTML = document.getElementById('pre_set5').innerHTML;
	  document.getElementById('field5').appendChild(div);
 }
 function remove_item5(obj){
	  // obj.parentNode �� �̿��Ͽ� ����
	  document.getElementById('field5').removeChild(obj.parentNode);
 }   

