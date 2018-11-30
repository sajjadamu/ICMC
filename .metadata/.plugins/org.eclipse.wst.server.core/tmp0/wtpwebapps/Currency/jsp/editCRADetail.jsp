<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>
<link href="./resources/css/calendar.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.datetimepicker.css" />
<link rel="shortcut icon" href="./resources/logo/favicon.ico"
	type="image/x-icon">
<script src="./js/jquery-1.12.0.min.js"></script>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>ICICI : CRA Payment</title>

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
var rowCount = 0;
var dataId=0;
$(document).ready(function(){
	var row=${row};
	/* for(i = 1; i <= row; i++){
		var currencyType +i ='${currencyType1}';	
	} */
     
	dynamicRow(row);
	function dynamicRow(row){
	var rowCount = ($('#table1 tr').length);
	 if(rowCount>0){
		document.getElementById("table1").innerHTML = ""; }  
	//var val = $("#member").val()
	$("#member").val(row);
	var val = row;
if(val>0)
{
var html='<tr><th>Category</th><th>Denomination</th><th>Bundle</th><th>Total</th></tr>' ;
for (i = 0; i < val; i++) {
html += '<tr id="main'+i+'">'+
'<td width="22%"><select name="Category" id="Category'+i+'" class="form-control input-margin"><option value="ATM">ATM</option><option value="ISSUABLE">ISSUABLE</option><option value="FRESH">FRESH</option></select></td>'+
	'<td><input type="text" id="Denomination'+i+'" onkeyup="doAjaxForTotal('+i+')" class="form-control input-margin" name="Denomination" value="" autofocus></td>'+
	'<td><input type="text" id="Bundle'+i+'" class="form-control input-margin" name="Bundle" value="" onkeyup="doAjaxPost('+i+');doAjaxForTotal('+i+')" ></td>'+
	'<td><input type="text" id="TotalWithFormatter'+i+'" onkeyup="doAjaxForTotal('+i+')" class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
	'<td><input type="hidden" id="recordID'+i+'" class="form-control input-margin" name="Bin" value="" ></td>'+
	'<td><button type="button" onclick="deleteRow('+i+');doAjaxForTotal('+i+')" >-</button></td>'+
	'<td><input type="hidden" maxlength="45" id="Total'+i+'"  class="form-control input-margin" name="Total" value="" autofocus readonly="true"></td>'+
	'</tr>';
countrow++;
} 
$('#table1').append(html);
$('#table1').append('<tr id=addmoreButton'+i+'><td><button type="button" class="addmoreButton" onclick="replicateValue('+i+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button" class="addmoreButton" onclick="addRow('+i+');doAjaxForTotal()">Add Blank Row</button></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly></td></tr>');
//$('#table1').append('<tr id=addmoreButton'+(i-1)+'></td><td></td></td><td></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly></td></tr>');
  var r=1;
  var rowCountHiddenTable = ($('#hiddenTable .trValue').length);
  //alert(rowCountHiddenTable);
  var totalValue=0.00;
   $('#hiddenTable .trValue').each(function(index)
		   
		  {
	   
	   var customerId1 = $(this).find("td").eq(0).html(); 
	   var customerId2 = $(this).find("td").eq(1).html(); 
	   var customerId3 = $(this).find("td").eq(2).html(); 
	   var customerId4 = $(this).find("td").eq(3).html(); 
		  // alert("customerId1 "+customerId1+" customerId2 "+customerId2+"customerId3 "+customerId3);
		   
		    $("#Category"+index).prepend('<option value='+customerId1+' selected="selected">'+customerId1+'</option>');
			$("#Denomination"+index).val(customerId2);
			$("#Bundle"+index).val(customerId3);
			$("#TotalWithFormatter"+index).val(customerId4);
			totalValue= (totalValue + parseFloat(customerId4));
			
		  }); 
   
   $("#totalValue").val(totalValue);
}
else
	{
	$('#table1').empty();
	}
	}
	$("#member").change(function() { 
		var val = $("#member").val();
		dynamicRow(val);
   
   
	
	});

	
	});


function replicateValue(i){
	
    // alert(countrow)
	//dataId = countrow-1;
    // alert("value from ID="+dataId)
    var rowCount = ($('#table1 tr').length)-2;
	countrow=rowCount;
	dataId = countrow-1;
	
	var selectedCategory = jQuery('#Category'+dataId).val();
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
	var data='<tr id="main'+countrow+'">'+
	'<td width="22%"><select name="Category" id="Category'+countrow+'" class="form-control input-margin"><option value="'+selectedCategory+'">'+selectedCategory+'</option></select></td>'+
			'<td><input type="text" id="Denomination'+countrow+'" class="form-control input-margin" name="Denomination" value="'+denomData+'" ></td>'+
			'<td><input type="text" id="Bundle'+countrow+'" class="form-control input-margin" name="Bundle" value="'+bundleData+'" onkeyup="doAjaxPost('+countrow+')" ></td>'+
			'<td><input type="text" id="TotalWithFormatter'+countrow+'"  class="form-control input-margin" name="Total" value="'+totalData+'" readonly="true"></td>'+
			'<td><input type="hidden" id="recordID'+countrow+'" class="form-control input-margin" name="Bin" value="" ></td>'+
			'<td><button type="button" onclick="deleteRow('+countrow+')">-</button></td>'+
			'<td><input type="hidden" maxlength="45" id="Total'+countrow+'"  class="form-control input-margin" name="Total" value="" autofocus readonly="true"></td>'+
			'</tr>'
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
	var data='<tr id="main'+countrow+'">'+
	'<td width="22%"><select name="Category" id="Category'+countrow+'" class="form-control input-margin"><option value="ATM">ATM</option><option value="ISSUABLE">ISSUABLE</option><option value="FRESH">FRESH</option></select></td>'+
			'<td><input type="text" id="Denomination'+countrow+'" class="form-control input-margin" name="Denomination" value="" ></td>'+
			'<td><input type="text" id="Bundle'+countrow+'" class="form-control input-margin" name="Bundle" value="" onkeyup="doAjaxPost('+countrow+')" ></td>'+
			'<td><input type="text" id="TotalWithFormatter'+countrow+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
			'<td><input type="hidden" id="recordID'+countrow+'" class="form-control input-margin" name="Bin" value="" ></td>'+
			'<td><button type="button" onclick="deleteRow('+countrow+')">-</button></td>'+
			'<td><input type="hidden" maxlength="45" id="Total'+countrow+'"  class="form-control input-margin" name="Total" value="" autofocus readonly="true"></td>'+
			'</tr>'
	$('#table1').append(data);
	$('#table1').append('<tr id=addmoreButton'+rowCount+'><td><button type="button" class="addmoreButton" onclick="replicateValue('+rowCount+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button" class="addmoreButton" onclick="addRow('+rowCount+');doAjaxForTotal()">Add Blank Row</button></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly></td></tr>');

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
    $('#totalValue').val(myTotalValue.toLocaleString('en-IN'));
} 
  
	
</script>

<script type="text/javascript">
function doAjaxPostInsert(str) {
	addHeaderJson();
	var craAllocations = [];
	var val = $("#member").val();
	var isValid = true;
	var craWrapper = {
			"id":$('#id').val(),
			"srNo":$('#srNo').val(),
			"solId":$('#solId').val(),
			"branch":$('#branch').val(),
			"vendor":$('#vendor').val(),
			"mspName":$('#mspName').val(),
			"accountNumber":$('#accountNumber').val(),
			"craAllocations": craAllocations
	              }
	
	/* if($('#srNo').val() == ""){
		$('#err1').show();
		isValid = false;
	}
	if(($('#srNo').val().length)  != 11  ){
		$('#err9').show();
		isValid = false;
	} */
	if($('#solId').val() == ""){
		$('#err2').show();
		isValid = false;
	}
	else if($('#solId').val().length != 4){
		$('#err12').show();
		isValid = false;
	}
	if($('#branch').val() == ""){
		$('#err3').show();
		isValid = false;
	}
	if($('select#vendor').val() == ""){
		$('#err4').show();
		isValid = false;
	}
	
	if($('select#mspName').val() == ""){
		$('#err5').show();
		isValid = false;
	}
	
	if($('#accountNumber').val() == ""){
		$('#err6').show();
		isValid = false;
	}
	
		
//For Denomination Validation	
for (i = 0; i <= countrow; i++) {
	var isRowValid = true;	
	var denom = $('#Denomination'+i).val();
	var bun = $('#Bundle'+i).val();
	
		if(denom == "" || denom == undefined){
			//$('#err7').show();
			isRowValid = false;
		}else if(denom != 2000 && denom != 1000 && denom != 500 && denom != 200 && denom != 100 && denom != 50 && denom != 20 && denom != 10 && denom != 5 && denom != 2 && denom != 1){
			$('#err7').show();
			isValid = false;
		}
		if(bun == "" || bun == undefined){
			//$('#err8').show();
			isRowValid = false;
			//return false;
		}else if(bun == ""){
			$('#err8').show();
			isValid = false;
		}
		
		if(isRowValid)
	{
			craAllocations.push({"currencyType":$('#Category'+i).val(),"denomination":$('#Denomination'+i).val(),"bundle":$('#Bundle'+i).val(),"total":$('#Total'+i).val(),"bin":$('#Bin'+i).val(),"box":$('#Box'+i).val(),"weight":$('#Weight'+i).val()});
	}
		}

if(craAllocations[0]['total']==null || craAllocations[0]['total']==""){
	isValid=false;
	alert("Please Change Something before update");
	} 
		
	if(isValid){
		$('#waitingImg').show();
		$.ajax({  
		  type: "POST",
	      contentType : 'application/json; charset=utf-8',
	      dataType : 'json',
	    url: "././insertUpdateCRAAllocationCRA",
	    data: JSON.stringify(craWrapper),
	    success: function(response){ 
	    	$('#waitingImg').hide();
	    	alert('record submited and update.');
	    	window.location='././viewCRA';
	    }, 
	    error: function(e){  
	      alert('Error: ' + e);  
	      $('#btnsubmit').prop("disabled",false);
	    }  
	  });  }
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
				 }  
			} 
		}  



function doAjaxPostForBranch() {  
	addHeaderJson();  
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
<script>
    $(function()
{
  $('#btnsubmit').on('click',function()
  {
	  $(this).val('Update')
      .attr('disabled','disabled');
  });
  
  
});
    </script>

<script type="text/javascript">
function doAjaxForAccountNumber() {
	addHeaderJson();
	var vendor = $('#vendor').val();
	var mspName = $('#mspName').val();
	$.ajax({
		type : "POST",
		url : "././getAccountNumberForCRA",
		data : "mspName=" + mspName +"&vendor= "+vendor,
		success : function(response) {
	        var data=response.split(",");
			$('#accountNumber').val(data)
		},
		error : function(e) {
			alert('account Number Error: ' + e);
		}
	});
}
</script>

<script type="text/javascript">
$(document).ready(function(){
	   /* var readOnlyLength = $('#srNo').val().length; */
	   $('#waitingImg').hide();
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
	window.location = '././CRAPayment';
}
</script>

<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>
<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-105"
	data-genuitec-path="/Currency/src/main/webapp/jsp/editCRADetail.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-105"
		data-genuitec-path="/Currency/src/main/webapp/jsp/editCRADetail.jsp">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<ul>
								<li><a href="././viewCRA"><i class="fa fa-table fa-fw"></i>View
										CRA Payment</a></li>
							</ul>
							CRA Payment
						</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-8">

									<!--<form role="form">-->
									<form:form id="" action="" method="post" modelAttribute="user"
										autocomplete="off">
										<form:hidden path="id" id="id" name="id" />
										<%-- <div path="id" id="craId" hidden><%= request.getParameter("id") %></div> --%>
										<div class="col-lg-6 form-group">
											<label>SR No.</label>
											<form:input type="text" path="srNo" id="srNo" name="srNo"
												minlength="11" maxlength="11" cssClass="form-control" />
											<label id="err1" style="display: none; color: red">Please
												Enter SR Number</label> <label id="err9"
												style="display: none; color: red">SR Number should
												have 11 digits</label>
										</div>
										<div class="col-lg-6 form-group">
											<label>SOL ID</label>
											<form:input path="solId" id="solId" maxlength="4"
												cssClass="form-control" onkeyup="doAjaxPostForBranch()" />
											<label id="err2" style="display: none; color: red">Please
												Enter Sol Id</label> <label id="err12"
												style="display: none; color: red">Enter 4 Digit Sol
												ID</label>
										</div>
										<div class="col-lg-6 form-group">
											<label>Branch Name</label>
											<form:input type="text" path="branch" id="branch"
												name="branch" cssClass="form-control" readonly="true" />
											<label id="err3" style="display: none; color: red">Enter
												Branch Name</label>
										</div>
										<div class="col-lg-6 form-group">
											<label>Vendor</label>
											<form:select path="vendor" id="vendor" name="vendor"
												cssClass="form-control" onchange="doAjaxForAccountNumber()">
												<form:options items="${vendorName}"
													itemValue="craVendorName" itemLabel="craVendorName" />
											</form:select>
											<label id="err4" style="display: none; color: red">Select
												Vendor</label>
										</div>

										<div class="col-lg-6 form-group">
											<label>MSP Name</label>
											<form:select path="mspName" id="mspName" name="mspName"
												cssClass="form-control" onchange="doAjaxForAccountNumber()">
												<form:options items="${vendorName}" itemValue="mspName"
													itemLabel="mspName" />
											</form:select>
											<label id="err5" style="display: none; color: red">Select
												MSP Name</label>
										</div>

										<div class="col-lg-6 form-group">
											<label>Account Number</label>
											<form:input path="accountNumber" readonly="true"
												id="accountNumber" name="accountNumber"
												cssClass="form-control" />
											<label id="err6" style="display: none; color: red">Enter
												Location</label>
										</div>
										<div class="form-group" hidden="true">
											<table id="hiddenTable">
												<tr>
													<th>Category</th>
													<th>Denomination</th>
													<th>Bundle</th>
													<th>Total</th>
												</tr>
												<c:forEach var="entry" items="${cradata}">
													<c:if test="${entry.total != null}">
														<tr class="trValue">
															<td class="tdValue"><c:out
																	value="${entry.currencyType}" /></td>
															<td class="tdValue"><c:out
																	value="${entry.denomination}" /></td>
															<td class="tdValue"><c:out value="${entry.bundle}" /></td>
															<td class="tdValue"><c:out value="${entry.total}" /></td>
														</tr>
													</c:if>
												</c:forEach>

											</table>
										</div>
										<img id="waitingImg" style="width: 150px; height: 60px;"
											src="./resources/logo/response-waiting.gif">
										<div class="form-group">
											<label>Number of Entries</label> <input type="text"
												id="member" name="member" value="" class="form-control">
											<br />
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
										<!-- <label id="err8" style="display: none; color: red">Please Enter Category</label> -->
										<c:choose>
											<c:when test="${status =='REQUESTED'}">
												<div class="col-lg-12">
													<!-- <input type="button" id="save btnsubmit" value="Submit"	onclick="doAjaxPostInsert('+i+'); this.disabled=true; return false">
                       						 -->
													<!-- <input type="button" id="save" value="Update"
												onclick="doAjaxPostInsert('+i+');"> -->
													<button type="button" value="Update" id="btnsubmit"
														onclick="doAjaxPostInsert('+i+')" class="btn btn-primary">Update</button>
												</div>

												<div align="right">
													<button type="submit" class="btn btn-default"
														value="Details" style="width: 99px;" onclick="refresh();">Refresh</button>
												</div>
											</c:when>
											<c:otherwise>
												<div class="text-center">
													<label class="bg-primary  text-white">Accepted by
														vault Management Can not be edited </label>
												</div>
											</c:otherwise>
										</c:choose>

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
	<script src="./resources/js/jquery.datetimepicker.js"></script>
	<script>
		$('#orderDate').datetimepicker({
			format : 'Y-m-d',

		});
		$('#expiryDate').datetimepicker({
			format : 'Y-m-d',

		});
	</script>

	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>