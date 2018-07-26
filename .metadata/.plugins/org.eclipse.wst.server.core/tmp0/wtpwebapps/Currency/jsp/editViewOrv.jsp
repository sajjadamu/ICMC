<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">

<head>
<link rel="shortcut icon" href="./resources/logo/favicon.ico"
	type="image/x-icon">
<script type="text/javascript" src="./js/jquery-1.12.0.min.js"></script>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<style>
form#ORV {
	width: 60%;
}

.frmsub input[type="button"] {
	padding: 1px 15px;
	margin-bottom: 20px;
}

.frmsub, .frmsubdel {
	margin-left: 16px;
}
</style>

<title>ICICI : ORV Branch</title>

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

<script type="text/javascript">

var countrow=0;
var countrow = 0;
var rowCount = 0;
var dataId=0;


$(document).ready(function() {
	 $('#waitingImg').hide();
	var row=${row};
	dynamicRow(row);
	/* $("#member").change(function() {  */
	$("#member").change(function() { 
		var val = $("#member").val();
		dynamicRow(val);
	});
	$('input[name=processedOrUnprocessed]').change(function() { 
		var val = $("#member").val();
		dynamicRow(val);
	});
	function dynamicRow(row){
											
var rowCount = ($('#table1 tr').length);
 if(rowCount>0){
document.getElementById("table1").innerHTML = "";
 }  
/* var val = $("#member").val(); */
$("#member").val(row);
var val = row;
//$('input[name=processedOrUnprocessed]').checked = true;
var unprocessedOrProcessed =  $('input[name=processedOrUnprocessed]:checked').val();
if(unprocessedOrProcessed=="PROCESSED")
{
	$('input[name=processedOrUnprocessed]').checked = true;
	if(val>0)
          	{
var html='<tr><th>Category</th><th>Denomination</th><th>Bundle</th><th>Total</th></tr>';
for (i = 0; i < val; i++) {
	/* onClick="doAjaxPost('+i+') onkeyup="doAjaxForTotal('+i+')" */
html += '<tr id="main'+i+'">'
+'<td width="22%"><select name="Category" id="Category'+i+'"  class="form-control input-margin" onClick="doAjaxPost('+i+') onkeyup="doAjaxForTotal('+i+')"><option value="ISSUABLE">ISSUABLE</option><option value="FRESH">FRESH</option><option value="ATM">ATM</option><option value="SOILED">SOILED</option><option value="COINS">COINS</option></td>'
+'<td><input type="text" id="Denomination'+i+'" onkeyup="doAjaxForTotal('+i+')" class="form-control input-margin" name="Denomination" value="" autofocus></td>'
+'<td><input type="text" id="Bundle'+i+'" class="form-control input-margin" name="Bundle" value="" onkeyup="doAjaxPost('+i+')" onkeyup="doAjaxForTotal('+i+')"></td>'
+'<td><input type="text" id="TotalWithFormatter'+i+'" onkeyup="doAjaxForTotal('+i+')" class="form-control input-margin" name="Total" value="" readonly="true"></td>'
+'<td><input type="hidden" id="Total'+i+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'
+'<td><button type="button" onclick="deleteRow('+countrow+')" onkeyup="doAjaxForTotal('+i+')">-</button></td>'+'</tr>';
countrow++;
}												       
$('#table1').append(html);
$('#table1').append('<tr id=addmoreButton'+(i-1)+'><td><button type="button"  class="addmoreButton" onclick="replicateValue('+(i-1)+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button"  class="addmoreButton" onclick="addRow('+(i-1)+');doAjaxForTotal()">Add Blank Row</button></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly></td></tr>');
//$('#table1').append('<tr id=addmoreButton'+(i-1)+'></td><td></td></td><td></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly></td></tr>');

var r=1;
var rowCountHiddenTable = ($('#hiddenTable .trValue').length);
//alert(rowCountHiddenTable);
var totalValue=0.00;
 $('#hiddenTable .trValue').each(function(index)
		   
		  {
	   
	   var Category = $(this).find("td").eq(0).html(); 
	   var Denomination = $(this).find("td").eq(1).html(); 
	   var Bundle = $(this).find("td").eq(2).html(); 
		   
		    $("#Category"+index).prepend('<option value='+Category+' selected="selected">'+Category+'</option>');
			$("#Denomination"+index).val(Denomination);
			$("#Bundle"+index).val(Bundle);
			var Category=$.trim(Category);
			console.log("Category"+Category);
			if(Category == "COINS"){
				totalValue= Denomination*Bundle*2500;
			}else {
				totalValue= Denomination*Bundle*1000;
			}
			
			$("#TotalWithFormatter"+index).val(totalValue);
			
			
		  }); 
 
 $("#totalValue").val('${total}');
          	}
    else
    	{
    	$('#table1').empty();
    	}
  
	}
	if(unprocessedOrProcessed=="UNPROCESS")
	{
		$('input[name=processedOrUnprocessed]').checked = true;
		var html='<tr><th>Cash Source</th><th>Denomination</th><th>Bundle/Bages</th><th>Total</th></tr>';
		for (i = 0; i < val; i++) {
			
			html += '<tr id="main'+i+'">'
			        +'<td width="22%"><select name="cashSource" id="cashSource'+i+'" class="form-control input-margin" onClick="doAjaxPost('+i+')"><option value="BRANCH">BRANCH</option></td>'
					+'<td><input type="text" id="Denomination'+i+'" class="form-control input-margin" name="Denomination" onkeyup="doAjaxForTotal('+i+')" value="" autofocus></td>'
					+'<td><input type="text" id="Bundle'+i+'" class="form-control input-margin" name="Bundle" value="" onkeyup="doAjaxPost('+i+')" onkeyup="doAjaxForTotal('+i+')"></td>'
					+'<td><input type="text" id="TotalWithFormatter'+i+'"  onkeyup="doAjaxForTotal('+i+')" class="form-control input-margin" name="Total" value="" readonly="true"></td>'
					+'<td><button type="button" onclick="deleteRow('+i+')">-</button></td>'
					+'<td><input type="hidden" id="Total'+i+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'
					+'<td><button type="button" onclick="deleteRow('+countrow+')" onkeyup="doAjaxForTotal('+i+')">-</button></td>'+'</tr>';
			countrow++;
		}
		$('#table1').append(html);
$('#table1').append('<tr id=addmoreButton'+(i-1)+'><td><button type="button"  class="addmoreButton" onclick="replicateValue('+(i-1)+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button"  class="addmoreButton" onclick="addRow('+(i-1)+');doAjaxForTotal()">Add Blank Row</button></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly></td></tr>');
var r=1;
var rowCountHiddenTable = ($('#hiddenTable .trValue').length);
//alert(rowCountHiddenTable);
var totalValue=0.00;
 $('#hiddenTable .trValue').each(function(index)
		   
		  {
	   
	   var Category = $(this).find("td").eq(0).html(); 
	   var Denomination = $(this).find("td").eq(1).html(); 
	   var Bundle = $(this).find("td").eq(2).html(); 
		   
		    $("#Category"+index).prepend('<option value='+Category+' selected="selected">'+Category+'</option>');
			$("#Denomination"+index).val(Denomination);
			$("#Bundle"+index).val(Bundle);
			totalValue= Denomination*Bundle*1000;
			$("#TotalWithFormatter"+index).val(totalValue);
			
			
		  }); 
 
 $("#totalValue").val('${total}');
													
												}
										}
					});
						
					
					function replicateValue(i){
						
					    // alert(countrow)
						//dataId = countrow-1;
					    // alert("value from ID="+dataId)
					    var rowCount = ($('#table1 tr').length)-2;
						countrow=rowCount;
						dataId = countrow-1;
						
						var selectedCategory = jQuery('#Category'+dataId).val();
						//alert(selectedCategory)
						var denomData = jQuery('#Denomination'+dataId).val();
						var bundleData = jQuery('#Bundle'+dataId).val();
						var totalData = jQuery('#TotalWithFormatter'+dataId).val();
						
						if(typeof selectedCategory === 'undefined'){
							selectedCategory = "";
						}
						
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
						data = '<tr id="main'+countrow+'">'
				        +'<td width="22%"><select name="Category" id="Category'+countrow+'" class="form-control input-margin"><option value="'+selectedCategory+'">'+selectedCategory+'</option></td>'
						+'<td><input type="text" id="Denomination'+countrow+'" class="form-control input-margin" name="Denomination" value="'+denomData+'" ></td>'
						+'<td><input type="text" id="Bundle'+countrow+'" class="form-control input-margin" name="Bundle" value="'+bundleData+'" onkeyup="doAjaxPost('+countrow+')" ></td>'
						+'<td><input type="text" id="TotalWithFormatter'+countrow+'"  class="form-control input-margin" name="Total" value="'+totalData+'" readonly="true"></td>'
						+'<td><button type="button" onclick="deleteRow('+countrow+')">-</button></td>'
						+'<td><input type="hidden" id="Total'+countrow+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'
						+ '</tr>';
						$('#table1').append(data);
						countrow++;
						$('#table1').append('<tr id=addmoreButton'+rowCount+'><td><button type="button" class="addmoreButton" onclick="replicateValue('+rowCount+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button" class="addmoreButton" onclick="addRow('+rowCount+');doAjaxForTotal()">Add Blank Row</button></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly ></td></tr>');
					}

						
						
						
						
					 function addRow(i){
	                        /* countrow++;
	                         var rowCount = ($('#table1 tr').length)-2; */
	                        
	                         var rowCount = ($('#table1 tr').length)-2;
	                         countrow=rowCount;
	                         
	                        jQuery('#addmoreButton'+i).remove();
	                        i++;
	                        data = '<tr id="main'+countrow+'">'
	                        +'<td width="22%"><select name="Category" id="Category'+countrow+'" class="form-control input-margin"><option value="ISSUABLE">ISSUABLE</option><option value="FRESH">FRESH</option><option value="ATM">ATM</option><option value="SOILED">SOILED</option><option value="COINS">COINS</option></td>'
	                        +'<td><input type="text" id="Denomination'+countrow+'" class="form-control input-margin" name="Denomination" value="" ></td>'
	                        +'<td><input type="text" id="Bundle'+countrow+'" class="form-control input-margin" name="Bundle" value="" onkeyup="doAjaxPost('+countrow+')" ></td>'
	                        +'<td><input type="text" id="TotalWithFormatter'+countrow+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'
	                        +'<td><button type="button" onclick="deleteRow('+countrow+')">-</button></td>'
	                        +'<td><input type="hidden" id="Total'+countrow+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'
	                        + '</tr>';
	                    
	                        $('#table1').append(data);
	                        $('#table1').append('<tr class="header" id=addmoreButton'+rowCount+'><td><button type="button" class="addmoreButton" onclick="replicateValue('+rowCount+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button" class="addmoreButton" onclick="addRow('+rowCount+');doAjaxForTotal()">Add Blank Row</button></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly></td></tr>');

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
						
					
					function doAjaxForTotal() { 
						var j = 0;
						var myTotalValue = null;
						var noOfRows = ($('#table1 tr').length-2);
						
					    for(var p=0; p<noOfRows; p++){
					    	var Category = $('#Category'+p).val();
					    	if(Category=='COINS'){
					    		var Denomination = $('#Denomination'+p).val();
								var Bundle = $('#Bundle'+p).val();
								var total = Denomination*2500*Bundle;
								$('#Total'+p).val(total);
						    	var totalValue = parseFloat($('#Total'+p).val());
					    	}else{
					    	var Denomination = $('#Denomination'+p).val();
							var Bundle = $('#Bundle'+p).val();
							var total = Denomination*1000*Bundle;
							$('#Total'+p).val(total);
					    	var totalValue = parseFloat($('#Total'+p).val());
					    	}
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
					    
					   /*  $('#totalValue').val(123); */
					} 
					  
</script>

<script type="text/javascript">
	function doAjaxPostInsert(str) {
		addHeader();
		var orvAllocations = [];
		var val = $("#member").val();
		var isValid = true;
		
		var orvBranchWrapper = {
		    "id" : $('#id').val(),
		    "sr" : $('#srNo').val(),
			"sackLockNumber" : $('#sackLockNumber').val(),
			"accountNumber" : $('#accountNumber').val(),
			"solId" : $('#solId').val(),
			"branch" : $('#branch').val(),
			"vendor" : $('#vendor').val(),
			"custodian" : $('input[name=custodian]:checked').val(),
			"vehicle" : $('input[name=vehicle]:checked').val(),
			"processedOrUnprocessed":$('input[name=processedOrUnprocessed]:checked').val(),
			"orvAllocations" : orvAllocations
		}
		var isValid = true;
		/* if($('#sr').val() == ""){
			$('#err1').show();
			isValid = false;
		}
		if(($('#sr').val().length)  != 11  ){
			$('#err11').show();
			isValid = false;
		} */
		if($('#sackLockNumber').val() == ""){
			$('#err2').show();
			isValid = false;
		}
		if($('#accountNumber').val() == ""){
			$('#err3').show();
			isValid = false;
		}
		if($('#solId').val() == ""){
			$('#err4').show();
			isValid = false;
		}else if(($('#solId').val().length)  != 4  ){
			$('#err10').show();
			isValid = false;
		}
		
		 /* if($('#vendor').val() == ""){
			 $('#err5').show();
			 isValid = false;
		} */
		
		
for (i = 0; i <= countrow; i++) {
			
	 if(($('#Denomination'+i).val()!=undefined) && $('#Denomination'+i).val()!=2000 
			 && $('#Denomination'+i).val()!=1000 && $('#Denomination'+i).val()!=500 && $('#Denomination'+i).val()!=200 
			 && $('#Denomination'+i).val()!=100 && $('#Denomination'+i).val()!=50 
			 && $('#Denomination'+i).val()!=20 && $('#Denomination'+i).val()!=10 
			 && $('#Denomination'+i).val()!=5 && $('#Denomination'+i).val()!=2 
			 && $('#Denomination'+i).val()!=1){
				$('#err6').show();
				isValid = false;
			}
			if(($('#Bundle'+i).val() != undefined) && $('#Bundle'+i).val() == ""){
				$('#err7').show();
				isValid = false;
			}

			if(($('#Denomination'+i).val()!=undefined) && isValid){
			orvAllocations.push({
				"currencyType" : $('#Category' + i).val(),
				"cashSource" : $('#cashSource' + i).val(),
				"denomination" : $('#Denomination' + i).val(),
				"bundle" : $('#Bundle' + i).val(),
				"total" : $('#Total' + i).val(),
				"bin" : $('#Bin' + i).val()
			});
			}
		}
		 if(orvAllocations[0]['total']==null || orvAllocations[0]['total']==""){
			isValid=false;
			alert("Please Change Something then update");
			} 
		
		if(isValid){
			$('#waitingImg').show();
		$.ajax({
			type : "POST",
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			url : "././insertUpdateOrvBranchAllocation",
			data : JSON.stringify(orvBranchWrapper),
			success : function(response) {
				if(response){
					$('#waitingImg').hide();
					alert('Record Updated.');
				}
				
				window.location='././viewORV';
			},
			error : function(e) {
				alert('Error: ' + e.responseJSON.message); 
				 $('#btnsubmit').prop("disabled",false);
			}
		});
		}
	}
	
	function doAjaxPost(str) {  
		  // get the form values  
		var Category = $('#Category'+str).val();
		  
		if(Category=='COINS'){
			var Denomination = $('#Denomination'+str).val();
			var noOfBags = $('#Bundle'+str).val();
				  
			if(Denomination==1){
			  total = 1*noOfBags*2500;
			}
			if(Denomination==2){
			  total = 2*noOfBags*2500;
			}
			if(Denomination==5){
			  total = 5*noOfBags*2500;
			}
			if(Denomination==10){
			  total = 10*noOfBags*2000;
			}
			$('#Total'+str).val(total);
			$('#TotalWithFormatter'+str).val(total.toLocaleString('en-IN'));
		}
		 else
		 	{
			  var Denomination = $('#Denomination'+str).val();
			  var Bundle = $('#Bundle'+str).val();
			  var total=Denomination*1000*Bundle;
			  $('#Total'+str).val(total);
			  $('#TotalWithFormatter'+str).val(total.toLocaleString('en-IN'));
		 }
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
</script>

<script type="text/javascript">
$(document).ready(function(){
	   /* var readOnlyLength = $('#srNo').val().length; */
	   
	   var readOnlyLength=2;
	 //$('#output').text(readOnlyLength);
	$('#srNo').on('keypress, keydown', function(event) {
	    var $field = $(this);
	    if ((event.which != 37 && (event.which != 39) && (event.which ))
	        && ((this.selectionStart < readOnlyLength)
	        || ((this.selectionStart == readOnlyLength) && (event.which == 8)))) {
	        return false;
	    }
	});     
	
	
	 $("#srNo").keydown(function (e) {
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


<script type="text/javascript">
function refresh() {
	window.location = '././ORVBranch';
}
</script>

<script>
    $(function()
{
  $('#btnsubmit').on('click',function()
  {
    $(this).val('Update')
      .attr('disabled','disabled');
   /*  $("#waitingImg").show(); */
    
   // $("#refresh").prop( "disabled", false );
   
    
   // $('#saveORV').submit();
  });
  
  
});
    </script>
<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script></head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-138" data-genuitec-path="/Currency/src/main/webapp/jsp/editViewOrv.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-138" data-genuitec-path="/Currency/src/main/webapp/jsp/editViewOrv.jsp">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<ul>
								<li><a href="././viewORV"><i class="fa fa-table fa-fw"></i>View
										Branch Payment</a></li>
							</ul>
							Branch Payment
						</div>
						<div class="panel-body">
							<div class="row">
								<!--<form role="form">-->
								<form:form id="" action="" method="post" modelAttribute="user"
									autocomplete="off">
									<%-- <form:hidden path="id" id="id" name="id" /> --%>
									<form:hidden path="id" />
									<div class="col-lg-12 form-group">
										<label>Sol ID</label>
										<form:input path="solID" maxlength="4" id="solId" name="solId"
											cssClass="form-control" onkeyup="doAjaxPostForBranch()" />
										<label id="err4" style="display: none; color: red">Enter
											Sol ID</label> <label id="err10" style="display: none; color: red">Sol
											ID must be of four digit</label>
									</div>

									<div class="col-lg-12 form-group">
										<label>Branch</label>
										<form:input path="branch" id="branch" cssClass="form-control"
											readonly="true" />

									</div>

									<div class="col-lg-12 form-group">
										<label>SR Number</label>
										<form:input type="text" path="srNo" id="srNo" name="srNo"
											minlenght="11" maxlength="11" cssClass="form-control" />
										<label id="err1" style="display: none; color: red">Please
											Enter SR Number</label> <label id="err11"
											style="display: none; color: red">SR Number should be
											of 11 digits</label>
									</div>

									<div class="col-lg-12 form-group">
										<label>Category</label><br>
										<form:radiobutton path="processedOrUnprocessed"
											id="processedOrUnprocessed" name="processedOrUnprocessed"
											value="PROCESSED" />

										<span class="deno-value"><b>PROCESSED</b></span>
										<form:radiobutton path="processedOrUnprocessed"
											id="processedOrUnprocessed" name="processedOrUnprocessed"
											value="UNPROCESS" />

										<span class="deno-value"><b>UNPROCESS</b></span> <img
											id="waitingImg" style="width: 150px; height: 60px;"
											src="./resources/logo/response-waiting.gif">
									</div>
									<div class="form-group hidden">
										<table id="hiddenTable">
											<tr>
												<th>Category</th>
												<th>Denomination</th>
												<th>Bundle</th>
											</tr>
											<c:forEach var="entry" items="${sasPayment}">
												<tr class="trValue">
													<td class="tdValue">
													<c:if test="${entry.cashType == 'NOTES' }">
													<c:out value="${entry.binType}" />
													</c:if>
													<c:if test="${entry.cashType == 'COINS' }">
													<c:out value="${entry.cashType}" />
													</c:if>
													
													</td>
													<td class="tdValue"><c:out
															value="${entry.denomination}" /></td>
													<td class="tdValue"><c:out value="${entry.bundle}" /></td>
												</tr>
											</c:forEach>

										</table>
									</div>

									<div class="col-lg-12 form-group">
										<label>Number of Entries</label> <input type="text"
											id="member" name="member" value="" class="form-control"><br />
										<label id="err6" style="display: none; color: red">Please
											Enter Valid Denomination</label> <label id="err7"
											style="display: none; color: red">Please Enter Bundle</label>
										<div id="container">
											<table id="table1">
												<tr>
												</tr>
											</table>
										</div>
									</div>
									<label id="err7" style="display: none; color: red">Please
										Enter Valid Denomination</label>
									<label id="err8" style="display: none; color: red">Please
										Enter Bundle</label>
										<c:choose>
									<c:when test="${status!='ACCEPTED'}">
										<div class="frmsub">
											<button type="submit" id="refresh" class="btn btn-default"
												value="Details" style="width: 99px;" onclick="refresh()">Refresh</button>
											<button type="button" value="Update" id="btnsubmit"
												onclick="doAjaxPostInsert('+i+')" class="btn btn-primary">
												Update</button>


											<!-- <input class="text-success" style=" padding: 10px 16px;" type="button" value="Update" id="btnsubmit"
											onclick="doAjaxPostInsert('+i+')"> -->
										</div>
									</c:when><c:otherwise>
									<div class="text-center">
							<label  class="bg-primary  text-white">Accepted by vault Management Can not be edited </label>
						                 </div>
									</c:otherwise>
									</c:choose>
								</form:form>

								<div class="frmsubdel">


									<div>

										<!-- /.col-lg-6 (nested) -->

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
		</div>
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