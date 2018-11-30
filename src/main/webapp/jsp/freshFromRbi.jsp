<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="en">
<head>
<link rel="shortcut icon" href="./resources/logo/favicon.ico"
	type="image/x-icon">
<script type="text/javascript" src="./js/jquery-1.12.0.min.js"></script>
<script type="text/javascript" src="./js/jquery.validate.min.js"></script>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>ICICI : Fresh From RBI</title>
<link href="./resources/css/calendar.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.datetimepicker.css" />
<!-- <script src="./resources/js/jquery.js"></script> -->
<!-- <script src="./resources/Currency/js/jquery.js"></script> -->


<!-- Bootstrap Core CSS -->
<link
	href="./resources/bower_components/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- MetisMenu CSS -->
<link
	href="./resources/bower_components/metisMenu/dist/metisMenu.min.css"
	rel="stylesheet">

<!-- Custom CSS -->
<link href="./resources/dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Custom Fonts -->
<link
	href="./resources/bower_components/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">

<link rel="stylesheet" type="text/css"
	href="./resources/dist/css/style.css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->


<script type="text/javascript">
var countrow=0;
var rowCount=0;
var dataId=0; 
$(document).ready(function(){
	//$("#member").keyup(function(){
	$("#member").change(function() { 
		
		 var rowCount = ($('#table1 tr').length);
		 if(rowCount>0){
			document.getElementById("table1").innerHTML = "";
		 }
		
    var val = $("#member").val();
    var notesOrCoins =  $('input[name=notesOrCoins]:checked').val();
    if(notesOrCoins=="Notes")
    	{
if(val>0)
	{
var html='<tr><th>Denomination</th><th>Bundle</th><th>Total</th><th>BOX</th></tr>' ;
	for (i = 0; i < val; i++) {
html += '<tr id="main'+i+'">'+
		'<td><input type="text" id="Denomination'+i+'" maxlength="4" minlength="1"  class="form-control input-margin"  onkeyup="doAjaxPost('+i+')" value="" ></td>'+
		'<td><input type="text" id="Bundle'+i+'" name="Bundle" class="form-control input-margin"  value="" onkeyup="doAjaxPost('+i+')" ></td>'+
		'<td><input type="text" id="TotalWithFormatter'+i+'"   class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
		'<td width="25%"><input type="text"  id="binNumber'+i+'"  class="form-control input-margin" name="Bin" value="" readonly="true" ></td>'+
		/* '<td><input type="radio"   name="binOrBox['+i+']" value="BIN"></td><td>BIN</td>'+ */
		'<td><input type="radio"   name="binOrBox['+i+']" value="BOX" checked="checked"></td><td>BOX</td>'+
		'<td><input type="hidden" id="recordID'+i+'" class="form-control input-margin" name="Bin" value="" ></td>'+
		'<td class="qr-button"><input type="button" id="print'+i+'" class="btn btn-default qr-button"  name="print"  value="Save AND Print QR" onclick="SavePrint('+i+'); this.disabled=true"></td>'+
		'<td><button type="button" onclick="deleteRow('+i+')">-</button></td>'+
		'<td><input type="hidden" id="Total'+i+'"   class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
		'</tr>';
countrow++;
} 
	$('#table1').append(html);
	$('#table1').append('<tr id=addmoreButton'+(i-1)+'><td><button type="button"  class="addmoreButton" onclick="replicateValue('+(i-1)+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button"  class="addmoreButton" onclick="addRow('+(i-1)+');doAjaxForTotal()">Add Blank Row</button></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly></td></tr>');
	}
else
	{
	$('#table1').empty();
	}
    	}
    
    
    else
    	{
    	if(val>0)
    	{
    var html='<tr><th>Denomination</th><th>No Of Bags</th><th>Total</th></tr>' ;
    	for (i = 0; i < val; i++) {
    html += '<tr id="main'+i+'">'+
    		'<td><input type="text" id="Denomination'+i+'" maxlength="4" minlength="1"  class="form-control input-margin" onkeyup="doAjaxCoinsCal('+i+')" value="" ></td>'+
    		'<td><input type="text" id="Bags'+i+'" name="bags" class="form-control input-margin"  value="" onkeyup="doAjaxCoinsCal('+i+')" ></td>'+
    		'<td><input type="text" id="TotalWithFormatter'+i+'"   class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
    		'<td width="25%"><input type="text"  id="binNumber'+i+'"  class="form-control input-margin" name="Bin" value="" readonly="true" ></td>'+
    		'<td><input type="hidden" id="recordID'+i+'" class="form-control input-margin" name="Bin" value="" ></td>'+
    		'<td class="qr-button"><input type="button" id="print'+i+'" class="btn btn-default qr-button"  name="print"  value="Save AND Print QR" onclick="SavePrint('+i+'); this.disabled=true"></td>'+
    		'<td><button type="button" onclick="deleteCoinRow('+i+')">-</button></td>'+
    		'<td><input type="hidden" id="Total'+i+'"   class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
    		'</tr>';
    countrow++;
    } 
    	$('#table1').append(html);
    	$('#table1').append('<tr id=addmoreButton'+(i-1)+'><td><button type="button"  class="addmoreButton" onclick="replicateValueForCoins('+(i-1)+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button"  class="addmoreButton" onclick="addCoinRow('+(i-1)+');doAjaxForTotal()">Add Blank Row</button></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly></td></tr>');

    	}
    else
    	{
    	$('#table1').empty();
    	}
    	}
    
    });
});

	
function addRow(i){
	
	 /* countrow++;
	 var rowCount = ($('#table1 tr').length)-2; */
	 var rowCount = ($('#table1 tr').length)-2;
	 countrow=rowCount;
	jQuery('#addmoreButton'+i).remove();
	i++;
	var data = '<tr id="main'+countrow+'">'+
	'<td><input type="text" id="Denomination'+countrow+'" maxlength="4" minlength="1"  class="form-control input-margin"  value="" ></td>'+
	'<td><input type="text" id="Bundle'+countrow+'" name="Bundle" class="form-control input-margin"  value="" onkeyup="doAjaxPost('+countrow+')" ></td>'+
	'<td><input type="text" id="TotalWithFormatter'+countrow+'"   class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
	'<td><input type="text" id="binNumber'+countrow+'"  class="form-control input-margin" name="Bin" value="" readonly="true"></td>'+
	/* '<td><input type="radio"   name="binOrBox'+countrow+'" value="BIN"></td><td>BIN</td>'+ */
	'<td><input type="radio"   name="binOrBox'+countrow+'" value="BOX" checked="checked"></td><td>BOX</td>'+
	'<td><input type="hidden" id="recordID'+countrow+'" class="form-control input-margin" name="Bin" value="" ></td>'+
	'<td class="qr-button"><input type="button" id="print'+countrow+'" class="btn btn-default qr-button"  name="print"  value="Save AND Print QR" onclick="SavePrint('+countrow+'); this.disabled=true"></td>'+
	'<td><button type="button" onclick="deleteRow('+countrow+')">-</button></td>'+
	'<td><input type="hidden" id="Total'+countrow+'"   class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
	'</tr>';
	$('#table1').append(data);
	$('#table1').append('<tr id=addmoreButton'+rowCount+'><td><button type="button" class="addmoreButton" onclick="replicateValue('+rowCount+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button" class="addmoreButton" onclick="addRow('+rowCount+');doAjaxForTotal()">Add Blank Row</button></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly></td></tr>');

}




function replicateValueForCoins(i){

    var rowCount = ($('#table1 tr').length)-2;
	countrow=rowCount;
	dataId = countrow-1;
	var denomData = jQuery('#Denomination'+dataId).val();
	var bagData = jQuery('#Bags'+dataId).val();
	var totalData = jQuery('#TotalWithFormatter'+dataId).val();
	
	if(typeof denomData === 'undefined'){
		denomData = "";
	}
	if(typeof bagData === 'undefined'){
		bagData = "";
	}
	if(typeof totalData === 'undefined'){
		totalData = "";
	}
	
	rowCount = ($('#table1 tr').length)-2;
	jQuery('#addmoreButton'+i).remove();
	i++;
	var data = '<tr id="main'+countrow+'">'+
	'<td><input type="text" id="Denomination'+countrow+'" maxlength="4" minlength="1"  class="form-control input-margin"  onkeyup="doAjaxCoinsCal('+countrow+')" value="'+denomData+'" ></td>'+
	'<td><input type="text" id="Bags'+countrow+'" name="bags" class="form-control input-margin"  value="'+bagData+'" onkeyup="doAjaxCoinsCal('+countrow+')" ></td>'+
	'<td><input type="text" id="TotalWithFormatter'+countrow+'"   class="form-control input-margin" name="Total" value="'+totalData+'" readonly="true"></td>'+
	'<td width="25%"><input type="text"  id="binNumber'+countrow+'"  class="form-control input-margin" name="Bin" value="" readonly="true" ></td>'+
	'<td><input type="hidden" id="recordID'+countrow+'" class="form-control input-margin" name="Bin" value="" ></td>'+
	'<td class="qr-button"><input type="button" id="print'+countrow+'" class="btn btn-default qr-button"  name="print"  value="Save AND Print QR" onclick="SavePrint('+countrow+'); this.disabled=true"></td>'+
	'<td><button type="button" onclick="deleteCoinRow('+countrow+')">-</button></td>'+
	'<td><input type="hidden" id="Total'+countrow+'"   class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
	'</tr>';
	$('#table1').append(data);
	countrow++;
	$('#table1').append('<tr id=addmoreButton'+rowCount+'><td><button type="button" class="addmoreButton" onclick="replicateValueForCoins('+rowCount+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button" class="addmoreButton" onclick="addCoinRow('+rowCount+');doAjaxForTotal()">Add Blank Row</button></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly ></td></tr>');
}




function replicateValue(i){
	
    var rowCount = ($('#table1 tr').length)-2;
	countrow=rowCount;
	dataId = countrow-1;
	var denomData = jQuery('#Denomination'+dataId).val();
	var bundleData = jQuery('#Bundle'+dataId).val();
	var totalData = jQuery('#TotalWithFormatter'+dataId).val();
	
	if(typeof denomData === 'undefined'){
		denomData = "";
	}
	if(typeof bundleData === 'undefined'){
		bundleData = "";
	}
	if(typeof totalData === 'undefined'){
		totalData = "";
	}
	
	rowCount = ($('#table1 tr').length)-2;
	jQuery('#addmoreButton'+i).remove();
	i++;
	var data = '<tr id="main'+countrow+'">'+
	'<td><input type="text" id="Denomination'+countrow+'" maxlength="4" minlength="1"  class="form-control input-margin"  onkeyup="doAjaxPost('+countrow+')"  value="'+denomData+'" ></td>'+
	'<td><input type="text" id="Bundle'+countrow+'" name="Bundle" class="form-control input-margin"  value="'+bundleData+'" onkeyup="doAjaxPost('+countrow+')" ></td>'+
	'<td><input type="text" id="TotalWithFormatter'+countrow+'"   class="form-control input-margin" name="Total" value="'+totalData+'" readonly="true"></td>'+
	'<td><input type="text" id="binNumber'+countrow+'"  class="form-control input-margin" name="Bin" value="" readonly="true"></td>'+
	/* '<td><input type="radio"   name="binOrBox'+countrow+'" value="BIN"></td><td>BIN</td>'+ */
	'<td><input type="radio"   name="binOrBox'+countrow+'" value="BOX" checked="checked"></td><td>BOX</td>'+
	'<td><input type="hidden" id="recordID'+countrow+'" class="form-control input-margin" name="Bin" value="" ></td>'+
	'<td class="qr-button"><input type="button" id="print'+countrow+'" class="btn btn-default qr-button"  name="print"  value="Save AND Print QR" onclick="SavePrint('+countrow+'); this.disabled=true"></td>'+
	'<td><button type="button" onclick="deleteRow('+countrow+')">-</button></td>'+
	'<td><input type="hidden" id="Total'+countrow+'"   class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
	'</tr>';
	$('#table1').append(data);
	countrow++;
	$('#table1').append('<tr id=addmoreButton'+rowCount+'><td><button type="button" class="addmoreButton" onclick="replicateValue('+rowCount+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button" class="addmoreButton" onclick="addRow('+rowCount+');doAjaxForTotal()">Add Blank Row</button></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly ></td></tr>');
}


function deleteRow(i){
	var MytotalValue=null;
  	var fetchTotalValue=null;
	var t1=parseFloat($('#totalValue').val());
	var t2=parseFloat($('#Total'+i).val());
	
	jQuery('#main'+i).remove();
	
	for(var p=0;p<=countrow;p++){
		  var me=parseFloat($('#Total'+p).val());
			 if(!isNaN(me)){
				 fetchTotalValue=$('#Total'+p).val();
				  MytotalValue+=parseFloat(fetchTotalValue);
				$('#totalValue').val(MytotalValue.toLocaleString('en-IN'));
			                  }  }
	
	if( ($('#table1 tr').length)-2==0){$('#totalValue').val(0)}
};

function addCoinRow(i){
	
	/* countrow++;
	 var rowCount = ($('#table1 tr').length)-2; */
	 
	 /* countrow++;
	 var rowCount = ($('#table1 tr').length)-2; */
	 var rowCount = ($('#table1 tr').length)-2;
	 countrow=rowCount;
	 
	jQuery('#addmoreButton'+i).remove();
	i++;
	var data = '<tr id="main'+countrow+'">'+
	'<td><input type="text" id="Denomination'+countrow+'" maxlength="4" minlength="1"  class="form-control input-margin"  onkeyup="doAjaxCoinsCal('+countrow+')" value="" ></td>'+
	'<td><input type="text" id="Bags'+countrow+'" name="bags" class="form-control input-margin"  value="" onkeyup="doAjaxCoinsCal('+countrow+')" ></td>'+
	'<td><input type="text" id="TotalWithFormatter'+countrow+'"   class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
	'<td width="25%"><input type="text"  id="binNumber'+countrow+'"  class="form-control input-margin" name="Bin" value="" readonly="true" ></td>'+
	'<td><input type="hidden" id="recordID'+countrow+'" class="form-control input-margin" name="Bin" value="" ></td>'+
	'<td class="qr-button"><input type="button" id="print'+countrow+'" class="btn btn-default qr-button"  name="print"  value="Save AND Print QR" onclick="SavePrint('+countrow+'); this.disabled=true"></td>'+
	'<td><button type="button" onclick="deleteCoinRow('+countrow+')">-</button></td>'+
	'<td><input type="hidden" id="Total'+countrow+'"   class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
	'</tr>';
	$('#table1').append(data);
	$('#table1').append('<tr id=addmoreButton'+rowCount+'><td><button type="button" class="addmoreButton" onclick="replicateValueForCoins('+rowCount+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button" class="addmoreButton" onclick="addCoinRow('+rowCount+');doAjaxForTotal()">Add Blank Row</button></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly></td></tr>');

}


function doAjaxForTotal() { 
	var j = 0;
	var myTotalValue = null;
	var noOfRows = ($('#table1 tr').length-2);
	
	var notesOrCoins=$('input[name=notesOrCoins]:checked').val();
	if(notesOrCoins=="Notes"){
    for(var p=0; p<noOfRows; p++){
    	var Denomination = $('#Denomination'+p).val();
		var Bundle = $('#Bundle'+p).val();
		var total = Denomination*1000*Bundle;
		$('#Total'+p).val(total);
    	
    	var totalValue = parseFloat($('#Total'+p).val());
    	if(noOfRows == j){
    		break;
    	}
    	if(!isNaN(totalValue) && totalValue != "" && totalValue != null && totalValue != 'undefined'){
    		myTotalValue += parseFloat(totalValue);
    		j++;
    	}else{
    		//noOfRows++;
    	}
	}
    }else { for(var p=0; p<noOfRows; p++){
    	var Denomination = $('#Denomination'+p).val();
		var Bags = $('#Bags'+p).val();
		if(Denomination !=10){
		var total = Denomination*2500*Bags;
		}else {
		var total = Denomination*2000*Bags;
		}
		$('#Total'+p).val(total);
    	
    	var totalValue = parseFloat($('#Total'+p).val());
    	if(noOfRows == j){
    		break;
    	}
    	if(!isNaN(totalValue) && totalValue != "" && totalValue != null && totalValue != 'undefined'){
    		myTotalValue += parseFloat(totalValue);
    		j++;
    	}else{
    		//noOfRows++;
    	}
	}
    }
    $('#totalValue').val(myTotalValue.toLocaleString('en-IN'));
} 
  

function deleteCoinRow(i){
	
	var MytotalValue=null;
  	var fetchTotalValue=null;
	var t1=parseFloat($('#totalValue').val());
	var t2=parseFloat($('#Total'+i).val());
	
	jQuery('#main'+i).remove();
	
	for(var p=0;p<=countrow;p++){
		  var me=parseFloat($('#Total'+p).val());
			 if(!isNaN(me)){
				 fetchTotalValue=$('#Total'+p).val();
				  MytotalValue+=parseFloat(fetchTotalValue);
				$('#totalValue').val(MytotalValue.toLocaleString('en-IN'));
			                  }  }
	
	if( ($('#table1 tr').length)-2==0){$('#totalValue').val(0)}

};
	

function SavePrint(str) {
	addHeaderJson();
	var fresh={
			"orderDate":$('#orderDate').val(),
			"rbiOrderNo":$('#rbiOrderNo').val(),
			"vehicleNumber":$('#vehicleNumber').val(),
			"potdarName":$('#potdarName').val(),
			"escortOfficerName":$('#escortOfficerName').val(),
			//"notesOrCoins":$('#main').find('input[type = radio]:checked').val(),
			"notesOrCoins":$('input[name=notesOrCoins]:checked').val(),
			"denomination":$('#Denomination'+str).val(),
			"bundle":$('#Bundle'+str).val(),
			"noOfBags":$('#Bags'+str).val(),
			"bin":$('#binNumber'+str).val(),
			"total":$('#Total'+str).val(),
			"binOrBox":$('#main'+str).find('input[type=radio]:checked').val(),
	}
	
	if($('input[name=notesOrCoins]:checked').val()=='Notes')
	{
		var isValid = true;
		if($('#orderDate').val() == ""){
			$('#err1').show();
			isValid = false;
		}
		if($('#rbiOrderNo').val() == ""){
			$('#err2').show();
			isValid = false;
		}
		if($('#vehicleNumber').val() == ""){
			$('#err3').show();
			isValid = false;
		}
		if($('#potdarName').val() == ""){
			$('#err4').show();
			isValid = false;
		}
		if($('#escortOfficerName').val() == ""){
			$('#err5').show();
			isValid = false;
		}
		 if($('input[name=notesOrCoins]:checked').length==0){
			 $('#err6').show();
			 isValid = false;
		} 
		if($('#Denomination'+str).val()!=2000 &&  $('#Denomination'+str).val()!=1000 && $('#Denomination'+str).val()!=500 && $('#Denomination'+str).val()!=200 && $('#Denomination'+str).val()!=100 && $('#Denomination'+str).val()!=50 && $('#Denomination'+str).val()!=20 && $('#Denomination'+str).val()!=10 && $('#Denomination'+str).val()!=5 && $('#Denomination'+str).val()!=2 && $('#Denomination'+str).val()!=1){
			$('#err7').show();
			isValid = false;
		}
		if($('#Bundle'+str).val() == "" || $('#Bundle'+str).val() <= 0){
			$('#err8').show();
			isValid = false;
		}
		
		 if($('#main'+str).find('input[type=radio]:checked').val() !='BOX'){
			 $('#err9').show();
			 isValid = false;
		 } 
		if(isValid){
	           $.ajax({
				type : "POST",
				contentType : 'application/json; charset=utf-8',
			    dataType : 'json',
				url : "././RBIQRPath",
				data: JSON.stringify(fresh),
				success : function(response) {
					console.log("response Notes"+response);
					 var binNum=response[0];
					 $('#binNumber'+str).val(binNum);
				},
				error : function(e) {
					alert('Print Error: ' + e.responseJSON.message);
					jQuery('#print'+str).attr("disabled", false);
					console.log("Print Error "+e);
				}
			});
	}else {
		alert("Please Select Proper Notes con not put on Bags");
	}
		}
	
	else
		{
		
		var isValid = true;
		if($('#orderDate').val() == ""){
			$('#err1').show();
			isValid = false;
		}
		if($('#rbiOrderNo').val() == ""){
			$('#err2').show();
			isValid = false;
		}
		if($('#vehicleNumber').val() == ""){
			$('#err3').show();
			isValid = false;
		}
		if($('#potdarName').val() == ""){
			$('#err4').show();
			isValid = false;
		}
		if($('#escortOfficerName').val() == ""){
			$('#err5').show();
			isValid = false;
		}
		if( $('#Denomination'+str).val()!=10 && $('#Denomination'+str).val()!=5 && $('#Denomination'+str).val()!=2 && $('#Denomination'+str).val()!=1){
			$('#err7').show();
			isValid = false;
		}
	  if($('#Bags'+str).val() == ""){
			$('#err10').show();
			isValid = false;
		}
		if(isValid){
		
		$.ajax({
			type : "POST",
			contentType : 'application/json; charset=utf-8',
		    dataType : 'json',
			url : "././RBIQRPath",
			data: JSON.stringify(fresh),
			success : function(response) {
				console.log("response Coins"+response)
				 var binNum=response[0];
				 $('#binNumber'+str).val(binNum);
			},
			error : function(e) {
				alert('Print Error: ' + e);
				jQuery('#print'+str).attr("disabled", false);
			}
		});
		}else {
			alert("Please Select Proper coins can not put on Box");
		}
		}
}
	
function doAjaxPost(str) {  
	  // get the form values  
	  var Denomination = $('#Denomination'+str).val();
	  var Bundle = $('#Bundle'+str).val();
	  var total=Denomination*1000*Bundle;
	  $('#Total'+str).val(total);
	  $('#TotalWithFormatter'+str).val(total.toLocaleString('en-IN'));
	   var MytotalValue=null;
   	var fetchTotalValue=null;
	 
   	for(var p=0;p<=countrow;p++){
		  var me=parseFloat($('#Total'+p).val());
		 
			 if(!isNaN(me)){
				 fetchTotalValue=$('#Total'+p).val();
				  MytotalValue+=parseFloat(fetchTotalValue);
				  $('#totalValue').val(MytotalValue.toLocaleString('en-IN'));
			                  }  } 
}  


function doAjaxCoinsCal(str) {  
	  // get the form values  
	  var Denomination = $('#Denomination'+str).val();
	  var noOfBags = $('#Bags'+str).val();
	  var total;
	  
	  if( Denomination!=10 && Denomination!=5 && Denomination!=2 && Denomination!=1){
	 		$('#err11').show();
	 	}
	  
	  if(Denomination==1)
		  {
		  total = 1*noOfBags*2500;
		  }
	  if(Denomination==2)
	  {
	  
	  total = 2*noOfBags*2500;
	  }
	  if(Denomination==5)
	  {
	  
	  total = 5*noOfBags*2500;
	  }
	  if(Denomination==10)
	  {
	  
	  total = 10*noOfBags*2000;
	  }
	  $('#Total'+str).val(total);
	  $('#TotalWithFormatter'+str).val(total.toLocaleString('en-IN'));
	   var MytotalValue=null;
  	var fetchTotalValue=null;
	 
  	for(var p=0;p<=countrow;p++){
		  var me=parseFloat($('#Total'+p).val());
		 
			 if(!isNaN(me)){
				 fetchTotalValue=$('#Total'+p).val();
				  MytotalValue+=parseFloat(fetchTotalValue);
				  $('#totalValue').val(MytotalValue.toLocaleString('en-IN'));
			                  }  } 
}  


function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}
	
</script>

<script type="text/javascript">
function refresh() {
	window.location = '././freshFromRbi';
}
</script>

</head>
<body oncontextmenu="return false;">
	<div id="wrapper">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<ul>
								<li><a href="././viewFresh"><i
										class="fa fa-table fa-fw"></i>View Fresh From RBI</a></li>
							</ul>
							Fresh From RBI
						</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-10">
									<!--<form role="form">-->
									<form:form id="fresh" action="" method="post"
										modelAttribute="user" autocomplete="off">

										<div class="col-lg-6 form-group">
											<label>Order Date</label>
											<form:input type="text" path="orderDate" id="orderDate"
												name="orderDate" cssClass="form-control" />
											<label id="err1" style="display: none; color: red">Please
												Select Date</label>
										</div>
										<div class="col-lg-6 form-group">
											<label>RBI Order Number</label>
											<form:input path="rbiOrderNo" id="rbiOrderNo"
												name="rbiOrderNo" cssClass="form-control"
												onkeypress="return isNumber(event)" />
											<label id="err2" style="display: none; color: red">Please
												Enter RBI Order Number</label>
										</div>
										<div class="col-lg-6 form-group">
											<label>Vehicle Number</label>
											<form:input path="vehicleNumber" id="vehicleNumber"
												name="vehicleNumber" cssClass="form-control" />
											<label id="err3" style="display: none; color: red">Please
												Enter Vehicle Number</label>
										</div>
										<div class="col-lg-6 form-group">
											<label>Potdar Name And PF Index Number</label>
											<form:input path="potdarName" id="potdarName"
												name="potdarName" cssClass="form-control" />
											<label id="err4" style="display: none; color: red">Please
												Enter Potdar Name and PF Index Number</label>
										</div>
										<div class="col-lg-6 form-group">
											<label>Escort Officer Name</label>
											<form:input path="escortOfficerName" id="escortOfficerName"
												name="escortOfficerName" cssClass="form-control" />
											<label id="err5" style="display: none; color: red">Please
												Enter Escort Officer Name</label>
										</div>
										<div class="col-lg-6 form-group">
											<form:radiobutton checked="checked" path="notesOrCoins"
												id="notesOrCoins" name="notesOrCoins" value="Notes" />
											<span class="deno-value">Notes </span>
											<form:radiobutton path="notesOrCoins" id="notesOrCoins"
												name="notesOrCoins" value="Coins" />
											<span class="deno-value">Coins </span> <label id="err6"
												style="display: none; color: red">Please Select
												Notes or Coins</label>
										</div>
										<div class="col-lg-6 form-group"></div>
										<div class="col-lg-6 form-group"></div>
										<div class="col-lg-6 form-group"></div>
										<div class="form-group">
											<label>Number of Entries</label> <input type="text"
												id="member" name="member" value="" class="form-control"><br />
											<div id="container">
												<table id="table1">
													<!-- <tr>
														<td><strong>Denomination</strong></td>
														<td><strong>Bundle</strong></td>
														<td><strong>Total</strong></td>
														<td><strong>Bin</strong></td>
													</tr> -->
												</table>
											</div>
										</div>

										<!-- <button type="submit" class="btn btn-default" value="Details"
											style="width: 99px;">Save All</button>
											
											<button type="submit" class="btn btn-default" value="Details"
											style="width: 99px;">Print All</button> -->

										<div align="right">
											<button type="submit" class="btn btn-default" value="Details"
												style="width: 99px;" onclick="refresh()">Refresh</button>
										</div>

										<label id="err10" style="display: none; color: red">Please
											Enter No of Bags</label>
										<label id="err7" style="display: none; color: red">Please
											Enter Valid Denomination</label>
										<label id="err8" style="display: none; color: red">Please
											Enter Valid Bundle</label>
										<label id="err9" style="display: none; color: red">Please
											Select Bin Or Box</label>
										<label id="err11" style="display: none; color: red">Please
											Enter Valid Denomination 10 ,5 , 2, 1</label>

									</form:form>
								</div>
								<div id="printSection" style="display: none;"></div>
								<!-- /.col-lg-6 (nested) -->
								<div class="col-lg-6"></div>
								<!-- /.col-lg-6 (nested) -->
							</div>
							<!-- /.row (nested) -->
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->

	<!-- jQuery -->
	<!-- <script src="./resources/bower_components/jquery/dist/jquery.min.js"></script> -->

	<!-- Bootstrap Core JavaScript -->
	<script
		src="./resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script
		src="./resources/bower_components/metisMenu/dist/metisMenu.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="./resources/dist/js/sb-admin-2.js"></script>

	<script src="./resources/js/jQuery.print.js"></script>

	<script src="./resources/dist/js/sb-admin-2.js"></script>


	<script src="./resources/js/jquery.datetimepicker.js"></script>
	<script>
		$('#orderDate').datetimepicker({
			format : 'Y-m-d',
		});
	</script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>