<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="en">

<head>
<link rel="shortcut icon" href="./resources/logo/favicon.ico"
	type="image/x-icon">
<script type="text/javascript" src="./js/jquery-1.12.0.min.js"></script>
<!-- <script src="./js/unprocess.js"></script> -->

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">


<script src="./resources/js/jQuery.print.js"></script>
<title>ICICI : Branch Receipt</title>
<script type="text/javascript">

var countrow=0;
function doAjaxPost(str) {  
	  // get the form values  
	  var Denomination = $('#Denomination'+str).val();
	  var Bundle = $('#Bundle'+str).val();
	  var total=Denomination*100*Bundle;
	   $('#Total'+str).val(total);
	  $.ajax({  
	    type: "POST", 
	    url: "././getBinForUnprocess",
	    data: "denominations=" + Denomination + "&bundle=" + Bundle,
	    success: function(response){  
	      // we have the response  
	      $('#info').html(response);
	     /*  $('#denominations').val('');
	      $('#bundle').val(''); */
	      $('#denominations'+str).val(Denomination);
	      $('#bundle'+str).val(Bundle);
	      $('#total'+str).val(total);
	      $('#binNumber'+str).val(response);
	      // alert("Response =="+response); 
	    }, 
	    error: function(e){  
	      alert('dedectBin 1 Error: ' + e);  
	    }  
	  });  
	}  
 var unprocessedData;
 function doAjaxPost1(str) {  
	 
	   var rowCount = $('#table1 tr').length-2;
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
		     }  
		 }
   	}  
 function doAjaxPost(str) {  
	 
	   var rowCount = $('#table1 tr').length-2;
	   var Denomination = $('#Denomination'+str).val();
	   var Bundle = $('#Bundle'+str).val();
	   var total=Denomination*100*Bundle;
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
		     }  
		 }
 	}  	
 var countrow=0;
 var rowCount=0;
 var dataId=0;	
 
 function doAjaxPostForBranch() {  
	  addHeader();
	  // get the form values  
	  var solid = $('#solId').val();
	  if(solid.length==4)
	  {
	  $.ajax({  
	    type: "POST", 
	    url: "././branchName",
	    data: "solid=" + solid,
	    success: function(response){  
	      // we have the response  
	      var res = JSON.stringify(response);
	      var newStr = res.substring(1, res.length - 1); // Remove Array Brackets
	      $('#info').html(response);
	      $('#solid').val(solid);
	      $('#branch').val(newStr);
	       // alert("Response =="+response);
	    }, 
	    error: function(e){  
	      //alert('branchName Error: ' + e);  
	    }  
	  });  
	  }
	}  

$(document).ready(function(){
	$("#member").change(function() { 
		 var rowCount = ($('#table1 tr').length);
		 if(rowCount>0){
			document.getElementById("table1").innerHTML = "";
		 }
		var val = $("#member").val();
		
		 var unprocessedOrProcessed =  $('input[name=processedOrUnprocessed]:checked').val();
		if(unprocessedOrProcessed=="UNPROCESS")
			{
    if(val>0)
	{
var html='<tr class="header"><th>Denomination</th><th>Packets</th><th>Total</th><th>Bin</th></tr>' ;
	for (i = 0; i < val; i++) {
html += '<tr id="main'+i+'">'+
        
		'<td><input type="text" id="Denomination'+i+'" class="form-control input-margin" maxlength="4" minlength="1" name="Denomination"  onkeyup="doAjaxPost('+i+')" value="" autofocus></td>'+
		'<td><input type="text" id="Bundle'+i+'" class="form-control input-margin" name="Bundle" value="" onkeyup="doAjaxPost('+i+')" ></td>'+
		'<td width="20%"><input type="text" id="TotalWithFormatter'+i+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
		'<td><input type="text" id="binNumber'+i+'" class="form-control input-margin" name="Bin" value="" readonly="true" ></td>'+
		'<td><input type="radio"  name="binOrBox['+i+']" value="BIN" checked="checked"></td><td>BIN</td>'+
		'<td><input type="radio"  name="binOrBox['+i+']" value="BOX"></td><td>BOX</td>'+
		'<td><input type="hidden" id="recordID'+i+'" class="form-control input-margin" name="Bin" value="" ></td>'+
		'<td class="qr-button"><input type="button" id="print'+i+'" class="btn btn-default qr-button"  name="print"  value="Save AND Print QR" onclick="SavePrint('+i+');this.disabled=true"></td>'+
		'<td><button type="button" class="delete" onclick="deleteRow('+i+')">-</button></td>'+
		'<td><input type="hidden" id="Total'+i+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
 	'</tr>';
countrow++;
} 

	$('#table1').append(html);
	$('#table1').append('<tr class="header" id=addmoreButton'+(i-1)+'><td><button type="button"  class="addmoreButton" onclick="replicateValue2('+(i-1)+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button"  class="addmoreButton" onclick="addRow2('+(i-1)+');doAjaxForTotal()">Add Blank Row</button></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly></td></tr>');
	}
    else
    	{
    	$('#table1').empty();
    	}
	}
	
	if(unprocessedOrProcessed=="PROCESSED")
		{
		
		if(val>0)
		{
			var html='<tr class="header"><th>Category</th><th>Denomination</th><th>Bundle</th><th>Total</th><th>Bin</th></tr>' ;
			for (i = 0; i < val; i++) {
		    html += '<tr id="main'+i+'">'+
		       '<td width="15%"><select name="Category" id="Category'+i+'" class="form-control input-margin"><option value="ISSUABLE">ISSUABLE</option><option value="FRESH">FRESH</option><option value="ATM">ATM</option><option value="SOILED">SOILED</option></select></td>'+
				'<td><input type="text" id="Denomination'+i+'" class="form-control input-margin" maxlength="4" minlength="1" name="Denomination" onkeyup="doAjaxPost1('+i+')" value="" autofocus></td>'+
				'<td><input type="text" id="Bundle'+i+'" class="form-control input-margin" name="Bundle" value="" onkeyup="doAjaxPost1('+i+')" ></td>'+
				'<td width="15%"><input type="text" id="TotalWithFormatter'+i+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
				'<td><input type="text" id="binNumber'+i+'" class="form-control input-margin" name="Bin" value="" readonly="true" ></td>'+
				'<td><input type="radio"  name="binOrBox['+i+']" value="BIN" checked="checked"></td><td>BIN</td>'+
				'<td><input type="radio"  name="binOrBox['+i+']" value="BOX"></td><td>BOX</td>'+
				'<td><input type="hidden" id="recordID'+i+'" class="form-control input-margin" name="Bin" value="" ></td>'+
				'<td class="qr-button"><input type="button" id="print'+i+'" class="btn btn-default qr-button"  name="print"  value="Save AND Print QR" onclick="SavePrint('+i+');this.disabled=true"></td>'+
				'<td><button type="button" class="delete" onclick="deleteRow('+i+')">-</button></td>'+
				'<td><input type="hidden" id="Total'+i+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
		 	'</tr>';
		countrow++;
		} 

			$('#table1').append(html);
		 $('#table1').append('<tr id=addmoreButton'+(i-1)+'><td><button type="button"  class="addmoreButton" onclick="replicateValue1('+(i-1)+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button"  class="addmoreButton" onclick="addRow1('+(i-1)+');doAjaxForTotal()">Add Blank Row</button></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly></td></tr>');
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
	'<td><input type="text" id="Denomination'+countrow+'" maxlength="4" minlength="1" class="form-control input-margin" name="Denomination" onkeyup="doAjaxPost1('+countrow+')" value="" ></td>'+
	'<td><input type="text" maxlength="45" id="Bundle'+countrow+'" class="form-control input-margin" name="Bundle" value="" onkeyup="doAjaxPost1('+countrow+')" ></td>'+
	'<td><input type="text" maxlength="45" id="TotalWithFormatter'+countrow+'"  class="form-control input-margin" name="Total" value="" readonly="true" ></td>'+
	'<td><input type="text" maxlength="45" id="binNumber'+countrow+'" class="form-control input-margin" name="Bin" value="" readonly="true" ></td>'+
	'<td><input type="radio"  name="binOrBox['+countrow+']" value="BIN" checked="checked"></td><td>BIN</td>'+
	'<td><input type="radio"  name="binOrBox['+countrow+']" value="BOX"></td><td>BOX</td>'+
	'<td><input type="hidden" id="recordID'+countrow+'" class="form-control input-margin" name="Bin" value="" ></td>'+
	'<td class="qr-button"><input type="button" id="print'+countrow+'" class="btn btn-default qr-button"  name="print"  value="Save AND Print QR" onclick="SavePrint('+countrow+');this.disabled=true; return false"></td>'+
	'<td><button type="button" onclick="deleteRow('+countrow+')">-</button></td>'+
	'<td><input type="hidden" maxlength="45" id="Total'+countrow+'"  class="form-control input-margin" name="Total" value="" readonly="true" ></td>'+
	'</tr>';
	$('#table1').append(data);
	$('#table1').append('<tr class="header" id=addmoreButton'+rowCount+'><td><button type="button" class="addmoreButton" onclick="replicateValue('+rowCount+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button" class="addmoreButton" onclick="addRow('+rowCount+');doAjaxForTotal()">Add Blank Row</button></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly></td></tr>');
	}

function addRow1(i){
    /* countrow++;
     var rowCount = ($('#table1 tr').length)-2; */
     var rowCount = ($('#table1 tr').length)-2;
     countrow=rowCount;
    jQuery('#addmoreButton'+i).remove();
    i++;
    var data = '<tr id="main'+countrow+'">'+
    '<td width="15%"><select name="Category" id="Category'+i+'" class="form-control input-margin"><option value="ISSUABLE">ISSUABLE</option><option value="FRESH">FRESH</option><option value="ATM">ATM</option><option value="SOILED">SOILED</option></select></td>'+
    '<td><input type="text" id="Denomination'+countrow+'" maxlength="4" minlength="1" class="form-control input-margin" name="Denomination" onkeyup="doAjaxPost1('+countrow+')" value="" ></td>'+
    '<td><input type="text" maxlength="45" id="Bundle'+countrow+'" class="form-control input-margin" name="Bundle" value="" onkeyup="doAjaxPost1('+countrow+')" ></td>'+
    '<td><input type="text" maxlength="45" id="TotalWithFormatter'+countrow+'"  class="form-control input-margin" name="Total" value="" readonly="true" ></td>'+
    '<td><input type="text" maxlength="45" id="binNumber'+countrow+'" class="form-control input-margin" name="Bin" value="" readonly="true" ></td>'+
    '<td><input type="radio"  name="binOrBox['+countrow+']" value="BIN" checked="checked"></td><td>BIN</td>'+
    '<td><input type="radio"  name="binOrBox['+countrow+']" value="BOX"></td><td>BOX</td>'+
    '<td><input type="hidden" id="recordID'+countrow+'" class="form-control input-margin" name="Bin" value="" ></td>'+
    '<td class="qr-button"><input type="button" id="print'+countrow+'" class="btn btn-default qr-button"  name="print"  value="Save AND Print QR" onclick="SavePrint('+countrow+');this.disabled=true; return false"></td>'+
    '<td><button type="button" onclick="deleteRow('+countrow+')">-</button></td>'+
    '<td><input type="hidden" maxlength="45" id="Total'+countrow+'"  class="form-control input-margin" name="Total" value="" readonly="true" ></td>'+
    '</tr>';
    $('#table1').append(data);
    $('#table1').append('<tr class="header" id=addmoreButton'+rowCount+'><td><button type="button" class="addmoreButton" onclick="replicateValue1('+rowCount+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button" class="addmoreButton" onclick="addRow1('+rowCount+');doAjaxForTotal()">Add Blank Row</button></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly></td></tr>');
    }


function addRow2(i){
	 var rowCount = ($('#table1 tr').length)-2;
	 countrow=rowCount;
	jQuery('#addmoreButton'+i).remove();
	i++;
	var data = '<tr id="main'+countrow+'">'+
	'<td><input type="text" id="Denomination'+countrow+'" maxlength="4" minlength="1" class="form-control input-margin" name="Denomination" onkeyup="doAjaxPost('+countrow+')" value="" ></td>'+
	'<td><input type="text" maxlength="45" id="Bundle'+countrow+'" class="form-control input-margin" name="Bundle" value="" onkeyup="doAjaxPost('+countrow+')" ></td>'+
	'<td><input type="text" maxlength="45" id="TotalWithFormatter'+countrow+'"  class="form-control input-margin" name="Total" value="" readonly="true" ></td>'+
	'<td><input type="text" maxlength="45" id="binNumber'+countrow+'" class="form-control input-margin" name="Bin" value="" readonly="true" ></td>'+
	'<td><input type="radio"  name="binOrBox['+countrow+']" value="BIN" checked="checked"></td><td>BIN</td>'+
	'<td><input type="radio"  name="binOrBox['+countrow+']" value="BOX"></td><td>BOX</td>'+
	'<td><input type="hidden" id="recordID'+countrow+'" class="form-control input-margin" name="Bin" value="" ></td>'+
	'<td class="qr-button"><input type="button" id="print'+countrow+'" class="btn btn-default qr-button"  name="print"  value="Save AND Print QR" onclick="SavePrint('+countrow+');this.disabled=true; return false"></td>'+
	'<td><button type="button" onclick="deleteRow('+countrow+')">-</button></td>'+
	'<td><input type="hidden" maxlength="45" id="Total'+countrow+'"  class="form-control input-margin" name="Total" value="" readonly="true" ></td>'+
	'</tr>';
	$('#table1').append(data);
	$('#table1').append('<tr class="header" id=addmoreButton'+rowCount+'><td><button type="button" class="addmoreButton" onclick="replicateValue2('+rowCount+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button" class="addmoreButton" onclick="addRow2('+rowCount+');doAjaxForTotal()">Add Blank Row</button></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly></td></tr>');
	}






function replicateValue(i){
	
    // alert(countrow)
	//dataId = countrow-1;
    // alert("value from ID="+dataId)
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
	'<td><input type="text"  id="Denomination'+countrow+'" maxlength="4" minlength="1" class="form-control input-margin" name="Denomination" value="'+denomData+'" onkeyup="doAjaxPost('+countrow+')" autofocus></td>'+
	'<td><input type="text" maxlength="45" id="Bundle'+countrow+'" class="form-control input-margin" name="Bundle" value="'+bundleData+'" onkeyup="doAjaxPost('+countrow+')" ></td>'+
	'<td><input type="text" maxlength="45" id="TotalWithFormatter'+countrow+'"  class="form-control input-margin" name="Total" value="'+totalData+'" readonly="true"></td>'+
	'<td><input type="text" maxlength="45" id="binNumber'+countrow+'" class="form-control input-margin" name="Bin" value="" readonly="true"></td>'+
	'<td><input type="radio"  name="binOrBox['+countrow+']" value="BIN" checked="checked"></td><td>BIN</td>'+
	'<td><input type="radio"  name="binOrBox['+countrow+']" value="BOX"></td><td>BOX</td>'+
	'<td><input type="hidden" id="recordID'+countrow+'" class="form-control input-margin" name="Bin" value="" ></td>'+
	'<td class="qr-button"><input type="button" id="print'+countrow+'" class="btn btn-default qr-button"  name="print"  value="Save AND Print QR" onclick="SavePrint('+countrow+');this.disabled=true"></td>'+
	'<td><button type="button" onclick="deleteRow('+countrow+')">-</button></td>'+
	'<td><input type="hidden" maxlength="45" id="Total'+countrow+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
	'</tr>';
	$('#table1').append(data);
	countrow++;
	$('#table1').append('<tr class="header" id=addmoreButton'+rowCount+'><td><button type="button" class="addmoreButton" onclick="replicateValue('+rowCount+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button" class="addmoreButton" onclick="addRow('+rowCount+');doAjaxForTotal()">Add Blank Row</button></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly ></td></tr>');
}

function replicateValue1(i){
    
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
    '<td width="15%"><select name="Category" id="Category'+i+'" class="form-control input-margin"><option value="ISSUABLE">ISSUABLE</option><option value="FRESH">FRESH</option><option value="ATM">ATM</option><option value="SOILED">SOILED</option></select></td>'+
    '<td><input type="text"  id="Denomination'+countrow+'" maxlength="4" minlength="1" class="form-control input-margin" name="Denomination" onkeyup="doAjaxPost('+countrow+')"  value="'+denomData+'" autofocus></td>'+
    '<td><input type="text" maxlength="45" id="Bundle'+countrow+'" class="form-control input-margin" name="Bundle" value="'+bundleData+'" onkeyup="doAjaxPost('+countrow+')" ></td>'+
    '<td><input type="text" maxlength="45" id="TotalWithFormatter'+countrow+'"  class="form-control input-margin" name="Total" value="'+totalData+'" readonly="true"></td>'+
    '<td><input type="text" maxlength="45" id="binNumber'+countrow+'" class="form-control input-margin" name="Bin" value="" readonly="true"></td>'+
    '<td><input type="radio"  name="binOrBox['+countrow+']" value="BIN" checked="checked"></td><td>BIN</td>'+
    '<td><input type="radio"  name="binOrBox['+countrow+']" value="BOX"></td><td>BOX</td>'+
    '<td><input type="hidden" id="recordID'+countrow+'" class="form-control input-margin" name="Bin" value="" ></td>'+
    '<td class="qr-button"><input type="button" id="print'+countrow+'" class="btn btn-default qr-button"  name="print"  value="Save AND Print QR" onclick="SavePrint('+countrow+');this.disabled=true"></td>'+
    '<td><button type="button" onclick="deleteRow('+countrow+')">-</button></td>'+
    '<td><input type="hidden" maxlength="45" id="Total'+countrow+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
    '</tr>';
    $('#table1').append(data);
    countrow++;
    $('#table1').append('<tr class="header" id=addmoreButton'+rowCount+'><td><button type="button" class="addmoreButton" onclick="replicateValue1('+rowCount+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button" class="addmoreButton" onclick="addRow1('+rowCount+');doAjaxForTotal()">Add Blank Row</button></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly ></td></tr>');
}


function replicateValue2(i){
	
    // alert(countrow)
	//dataId = countrow-1;
    // alert("value from ID="+dataId)
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
	'<td><input type="text"  id="Denomination'+countrow+'" maxlength="4" minlength="1" class="form-control input-margin" name="Denomination" onkeyup="doAjaxPost('+countrow+')" value="'+denomData+'" autofocus></td>'+
	'<td><input type="text" maxlength="45" id="Bundle'+countrow+'" class="form-control input-margin" name="Bundle" value="'+bundleData+'" onkeyup="doAjaxPost('+countrow+')" ></td>'+
	'<td><input type="text" maxlength="45" id="TotalWithFormatter'+countrow+'"  class="form-control input-margin" name="Total" value="'+totalData+'" readonly="true"></td>'+
	'<td><input type="text" maxlength="45" id="binNumber'+countrow+'" class="form-control input-margin" name="Bin" value="" readonly="true"></td>'+
	'<td><input type="radio"  name="binOrBox['+countrow+']" value="BIN" checked="checked"></td><td>BIN</td>'+
	'<td><input type="radio"  name="binOrBox['+countrow+']" value="BOX"></td><td>BOX</td>'+
	'<td><input type="hidden" id="recordID'+countrow+'" class="form-control input-margin" name="Bin" value="" ></td>'+
	'<td class="qr-button"><input type="button" id="print'+countrow+'" class="btn btn-default qr-button"  name="print"  value="Save AND Print QR" onclick="SavePrint('+countrow+');this.disabled=true"></td>'+
	'<td><button type="button" onclick="deleteRow('+countrow+')">-</button></td>'+
	'<td><input type="hidden" maxlength="45" id="Total'+countrow+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
	'</tr>';
	$('#table1').append(data);
	countrow++;
	$('#table1').append('<tr class="header" id=addmoreButton'+rowCount+'><td><button type="button" class="addmoreButton" onclick="replicateValue2('+rowCount+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button" class="addmoreButton" onclick="addRow2('+rowCount+');doAjaxForTotal()">Add Blank Row</button></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly ></td></tr>');
}


function deleteRow(i){

	var MytotalValue=null;
  	var fetchTotalValue=null;
	var t1=parseFloat($('#totalValue').val());
	var t2=parseFloat($('#Total'+i).val());
	
	jQuery('#main'+i).remove();
	if(i==0){
		jQuery('.header').remove();
	}
	
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
	addHeader();
	var isValid = true;
	var branchReceipt={
			"solId":$('#solId').val(),
			"denomination":$('#Denomination'+str).val(),
			"bundle":$('#Bundle'+str).val(),
			"bin":$('#binNumber'+str).val(),
			"total":$('#Total'+str).val(),
			"branch":$('#branch').val(),
			"srNumber":$('#srNumber').val(),
			"binCategoryType":$('#main'+str).find('input[type=radio]:checked').val(),
			"processedOrUnprocessed":$('input[name=processedOrUnprocessed]:checked').val(),
			"currencyType":$('#Category'+str).val(),
	}
	if($('#solId').val() == ""){
		$('#err1').show();
		isValid=false;
	}
	if($('#solId').val().length != 4){
		$('#err11').show();
		isValid=false;
	}
	if($('#Denomination'+str).val()!=2000 && $('#Denomination'+str).val()!=1000 && $('#Denomination'+str).val()!=500 && $('#Denomination'+str).val()!=200 && $('#Denomination'+str).val()!=100 && $('#Denomination'+str).val()!=50 && $('#Denomination'+str).val()!=20 && $('#Denomination'+str).val()!=10 && $('#Denomination'+str).val()!=5 && $('#Denomination'+str).val()!=2 && $('#Denomination'+str).val()!=1){
		$('#err2').show();
		isValid=false;
	}
	 if($('#Bundle'+str).val() == "" || $('#Bundle'+str).val() <= 0){
		$('#err3').show();
		isValid=false;
	}
	if($('#branch').val() == ""){
		$('#err4').show();
		isValid=false;
	}
	
	/*if($('#srNumber').val() == ""){
		$('#err5').show();
		isValid=false;
	}
	if(($('#srNumber').val().length)  != 11  ){
		$('#err6').show();
		isValid = false;
	}*/
	
	if(isValid){
	$.ajax({
				type : "POST",
				 contentType : 'application/json; charset=utf-8',
			      dataType : 'json',
				  url : "././QRPath",
				 data: JSON.stringify(branchReceipt),
				success : function(response) {
					var html =""
					for(var i=1; i < response.length; i++){
						html += "<textarea id=prntextarea"+i+">'"+response[i]+"'</textarea>"
						$('#prncode').append(html);
					}
					var binNum=response[0];
					$('#binNumber'+str).val(binNum);
				},
				error : function(e) {
					//alert('Error In Print, Please Try Again: ' + e);
					alert('Error: ' + e.responseJSON.message);
					jQuery('#print'+str).attr("disabled", false);
				}
			});
}
}

function refresh() {
	window.location = '././Addshrink';
}
	
function doAjaxForTotal() { 
	var j = 0;
	var myTotalValue = null;
	var noOfRows = ($('#table1 tr').length-2);
    for(var p=0; p<noOfRows; p++){
    	var Denomination = $('#Denomination'+p).val();
		var Bundle = $('#Bundle'+p).val();
		var total = Denomination*100*Bundle;
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
    $('#totalValue').val(myTotalValue.toLocaleString('en-IN'));
} 
</script>
<script type="text/javascript">
$(document).ready(function(){
	   var readOnlyLength = $('#srNumber').val().length;
	 //$('#output').text(readOnlyLength);
	$('#srNumber').on('keypress, keydown', function(event) {
	    var $field = $(this);
	    if ((event.which != 37 && (event.which != 39) && (event.which ))
	        && ((this.selectionStart < readOnlyLength)
	        || ((this.selectionStart == readOnlyLength) && (event.which == 8)))) {
	        return false;
	    }
	});     
	
	
	 $("#srNumber").keydown(function (e) {
	        // Allow: backspace, delete, tab, escape, enter and .
	        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
	             // Allow: Ctrl+A, Command+A
	            (e.keyCode === 65 && (e.ctrlKey === true || e.metaKey === true)) || 
	             // Allow: home, end, left, right, down, up
	            (e.keyCode >= 35 && e.keyCode <= 40)) {
	                 // let it happen, don't do anything
	                 return;
	        }
	        // Ensure that it is a number and stop the keypress
	        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)){
	            e.preventDefault();
	        }
	    });
	});
</script>
<script src="./resources/js/jquery.js"></script>
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
								<li><a href="././viewShrink"><i
										class="fa fa-table fa-fw"></i>View Branch Receipt</a></li>
							</ul>
							Branch Receipt
						</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-10">
									<!--<form role="form">-->
									<form:form id="" action="" method="post" modelAttribute="user"
										autocomplete="off">

										<div class="form-group">
											<label>Sol ID</label>
											<form:input path="solId" name="solId" minlenght="4"
												maxlength="4" id="solId" cssClass="form-control"
												onkeyup="doAjaxPostForBranch()" />
											<label id="err1" style="display: none; color: red">Please
												Enter solId</label> <label id="err11"
												style="display: none; color: red">Please Enter 4
												Digit Sol Id</label>
										</div>
										<div class="form-group">
											<label>Branch</label>
											<form:input path="branch" id="branch" cssClass="form-control"
												readonly="true" />
											<label id="err4" style="display: none; color: red">Please
												Enter Any valid Sol Id</label>
										</div>

										<div class="form-group">
											<label>SR Number</label>
											<form:input path="srNumber" id="srNumber" value="SR"
												minlenght="11" maxlength="11" cssClass="form-control" />
											<label id="err5" style="display: none; color: red">Please
												Enter SR Number</label> <label id="err6"
												style="display: none; color: red">SR Number should
												have 11 digits</label>
										</div>

										<div class="form-group">
											<label>Category</label><br>
											<form:radiobutton path="processedOrUnprocessed"
												id="processedOrUnprocessed" name="processedOrUnprocessed"
												checked="true" value="UNPROCESS" />
											<span class="deno-value"><b>UNPROCESS</b></span>
											<form:radiobutton path="processedOrUnprocessed"
												id="processedOrUnprocessed" name="processedOrUnprocessed"
												value="PROCESSED" />
											<span class="deno-value"><b>PROCESSED</b></span>
										</div>

										<div class="form-group">
											<label>Number of Shrink Wraps</label> <input type="text"
												id="member" name="member" value="" class="form-control"><br />
											<div id="container">
												<table id="table1">
												</table>

												<label id="err2" style="display: none; color: red">Please
													Enter Valid Denomination</label> <label id="err3"
													style="display: none; color: red">Please Enter
													Valid Packets</label>
											</div>
										</div>

										<div align="right">
											<button type="submit" class="btn btn-default" value="Details"
												style="width: 99px;" onclick="refresh()">Refresh</button>
										</div>

									</form:form>
									<div id="prncode" style="display: none;"></div>
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

	
	<!-- jQuery -->
	<script src="./resources/bower_components/jquery/dist/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script
		src="./resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script
		src="./resources/bower_components/metisMenu/dist/metisMenu.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="./resources/dist/js/sb-admin-2.js"></script>

	<script src="./resources/js/jQuery.print.js"></script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>
