<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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

<title>ICICI : Suspense Discrepancy</title>

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

<style type="text/css">
.form-control.input-margin {
	margin-bottom: 10px;
	width: 107px;
	margin: 2px;
}
</style>

<script type="text/javascript">
    var countrow=0;
    
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
	//$("#member").keyup(function(){
	$("#member").change(function() { 
		var rowCount = ($('#table1 tr').length);
		 if(rowCount>0){
			document.getElementById("table1").innerHTML = "";
		 }
		
		var val = $("#member").val();
    if(val>0)
	{
    	var html='<tr><th width="22%">Denomination</th><th width="22%">Discrepancy Type</th><th width="22%">Mutilated Type</th><th width="22%">Note Serial Number</th><th>No. of Notes</th><th>Value</th><th>Print Year</th><th>Date on Shrink Wrap</th><th>Remarks</th></tr>' ;
	for (i = 0; i < val; i++) {
html += '<tr id="main'+i+'">'+
'<td width="22%"><input type="text" id="Denomination'+i+'" class="form-control input-margin" name="Denomination" value="" autofocus></td>'+
'<td width="25%"><select name="discrepancyType" id="discrepancyType'+i+'" class="form-control input-margin" onchange="getMutilValue('+i+'); getFakeNotePrintYear('+i+');getSerialNumber('+i+')"><option value="-1">Select</option><option value="SHORTAGE">SHORTAGE</option><option value="EXCESS">EXCESS</option><option value="FAKE">FAKE</option><option value="MUTILATED">MUTILATED</option></select></td>'+
'<td width="22%"><div class="desc" id="mutil'+i+'" ><select name="mutilType'+i+'" disabled="disabled" id="mutilType'+i+'" class="form-control input-margin"><option value="">Select</option><option value="FULL VALUE">FULL VALUE</option><option value="HALF VALUE">HALF VALUE</option><option value="ZERO VALUE">ZERO VALUE</option></td></div>'+
'<td width="22%"><input type="text" id="NoteSerialNumber'+i+'" disabled="disabled" class="form-control input-margin" name="NoteSerialNumber" value=""></td>'+
'<td width="22%"><input type="text" id="NumberOfNotes'+i+'"  class="form-control input-margin" name="numberOfNotes" value="" onkeyup="doAjaxPost('+i+')" ></td>'+
'<td width="22%"><input type="text" id="Value'+i+'"  class="form-control input-margin" name="Value" value="" readonly="true" ></td>'+
'<td width="22%"><input type="text" id="printYear'+i+'" disabled="disabled" class="form-control input-margin" name="printYear" value="" ></td>'+
'<td width="22%"><input type="text" id="dateOnShrinkWrap'+i+'"  class="form-control input-margin" name="dateOnShrinkWrap" value="" onfocus="doAjaxForDate('+i+')" ></td>'+
'<td width="22%"><input type="text" id="remarks'+i+'"  class="form-control input-margin" name="remarks" value="" ></td>'+
'<td width="22%"><input type="hidden" id="recordID'+i+'" class="form-control input-margin" name="Bin" value="" ></td>'+
'<td><button type="button" class="delete" onclick="deleteRow('+i+')">-</button></td>'+
   	'</tr>';
countrow++;
} 
	$('#table1').append(html);
	$('#table1').append('<tr id=addmoreButton'+(i-1)+'><td><button type="button"  class="addmoreButton" onclick="replicateValue('+(i-1)+')">Replicate Value</button></td><td><button type="button"  class="addmoreButton" onclick="addRow('+(i-1)+')">Add Blank Row</button></td><td></td><td></td></tr>');
	}
    else
    	{
    	$('#table1').empty();
    	}
    });
});


function replicateValue(i){
	
    // alert(countrow)
	//dataId = countrow-1;
    // alert("value from ID="+dataId)
    var rowCount = ($('#table1 tr').length)-2;
	countrow=rowCount;
	dataId = countrow-1;
	var denomData = jQuery('#Denomination'+dataId).val();
	var discrepancyType = jQuery('#discrepancyType'+dataId).val();
	var mutilType = jQuery('#mutilType'+dataId).val();
	var noteSerialNumber = jQuery('#NoteSerialNumber'+dataId).val();
	var numberOfNotes = jQuery('#NumberOfNotes'+dataId).val();
	var value = jQuery('#Value'+dataId).val();
	var printYear = jQuery('#printYear'+dataId).val();
	var dateOnShrinkWrap = jQuery('#dateOnShrinkWrap'+dataId).val();
	var remarks = jQuery('#remarks'+dataId).val();
	
	if(typeof denomData === 'undefined'){
		denomData = "";
	}
	if(typeof discrepancyType === 'undefined'){
		discrepancyType = "";
	}
	if(typeof mutilType === 'undefined'){
		mutilType = "";
	}
	if(typeof notesSerialNumber == 'undefined')
		{
		notesSerialNumber="";
		}
	if(typeof numberOfNotes == 'undefined')
	{
		numberOfNotes="";
	}
	if(typeof value == 'undefined')
	{
		value="";
	}
	if(typeof printYear == 'undefined')
	{
		printYear="";
	}
	if(typeof dateOnShrinkWrap == 'undefined')
	{
		dateOnShrinkWrap="";
	}
	if(typeof remarks == 'undefined')
	{
		remarks="";
	}
	
	rowCount = ($('#table1 tr').length)-2;
	jQuery('#addmoreButton'+i).remove();
	i++;
	var data= '<tr id="main'+countrow+'">'+
	'<td width="22%"><input type="text" id="Denomination'+countrow+'" class="form-control input-margin" name="Denomination" value="'+denomData+'" ></td>'+
	'<td width="25%"><select name="discrepancyType" id="discrepancyType'+countrow+'" class="form-control input-margin" onchange="getMutilValue('+countrow+'); getFakeNotePrintYear('+countrow+')"><option value="'+discrepancyType+'">'+discrepancyType+'</option></select></td>'+
	'<td width="22%"><div class="desc" id="mutil'+countrow+'" ><select name="mutilType'+countrow+'"  id="mutilType'+countrow+'" class="form-control input-margin"><option value="'+mutilType+'">'+mutilType+'</option></select></td></div>'+
	'<td width="22%"><input type="text" id="NoteSerialNumber'+countrow+'" class="form-control input-margin" name="NoteSerialNumber" value=""></td>'+
	'<td width="22%"><input type="text" id="NumberOfNotes'+countrow+'"  class="form-control input-margin" name="numberOfNotes" value="'+numberOfNotes+'" onkeyup="doAjaxPost('+countrow+')" ></td>'+
	'<td width="22%"><input type="text" id="Value'+countrow+'"  class="form-control input-margin" name="Value" value="'+value+'" readonly="true" ></td>'+
	'<td width="22%"><input type="text" id="printYear'+countrow+'" disabled="disabled" class="form-control input-margin" name="printYear" value="'+printYear+'" ></td>'+
	'<td width="22%"><input type="text" id="dateOnShrinkWrap'+countrow+'"  class="form-control input-margin" name="dateOnShrinkWrap" value="'+dateOnShrinkWrap+'" onfocus="doAjaxForDate('+countrow+')" ></td>'+
	'<td width="22%"><input type="text" id="remarks'+countrow+'"  class="form-control input-margin" name="remarks" value="'+remarks+'" ></td>'+
	'<td width="22%"><input type="hidden" id="recordID'+countrow+'" class="form-control input-margin" name="Bin" value="" ></td>'+
	'<td><button type="button" class="delete" onclick="deleteRow('+countrow+')">-</button></td>'+
	   	'</tr>';
	$('#table1').append(data);
	countrow++;
	$('#table1').append('<tr id=addmoreButton'+rowCount+'><td><button type="button" class="addmoreButton" onclick="replicateValue('+rowCount+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button" class="addmoreButton" onclick="addRow('+rowCount+')">Add Blank Row</button></td></tr>');
}	
	
	
	
function addRow(i){
	/* countrow++;
	
	 var rowCount = ($('#table1 tr').length)-2; */
	 var rowCount = ($('#table1 tr').length)-2;
		countrow=rowCount;
	jQuery('#addmoreButton'+i).remove();
	i++;
	var data= '<tr id="main'+countrow+'">'+
	'<td width="22%"><input type="text" id="Denomination'+countrow+'" class="form-control input-margin" name="Denomination" value="" ></td>'+
	'<td width="25%"><select name="discrepancyType" id="discrepancyType'+countrow+'" class="form-control input-margin" onchange="getMutilValue('+countrow+'); getFakeNotePrintYear('+countrow+')"><option value="-1">Select</option><option value="SHORTAGE">SHORTAGE</option><option value="EXCESS">EXCESS</option><option value="FAKE">FAKE</option><option value="MUTILATED">MUTILATED</option></select></td>'+
	'<td width="22%"><div class="desc" id="mutil'+countrow+'" ><select name="mutilType'+countrow+'" disabled="disabled" id="mutilType'+countrow+'" class="form-control input-margin"><option value="">Select</option><option value="FULL VALUE">FULL VALUE</option><option value="HALF VALUE">HALF VALUE</option><option value="ZERO VALUE">ZERO VALUE</option></td></div>'+
	'<td width="22%"><input type="text" id="NoteSerialNumber'+countrow+'" class="form-control input-margin" name="NoteSerialNumber" value=""></td>'+
	'<td width="22%"><input type="text" id="NumberOfNotes'+countrow+'"  class="form-control input-margin" name="numberOfNotes" value="" onkeyup="doAjaxPost('+countrow+')" ></td>'+
	'<td width="22%"><input type="text" id="Value'+countrow+'"  class="form-control input-margin" name="Value" value="" readonly="true" ></td>'+
	'<td width="22%"><input type="text" id="printYear'+countrow+'" disabled="disabled" class="form-control input-margin" name="printYear" value="" ></td>'+
	'<td width="22%"><input type="text" id="dateOnShrinkWrap'+countrow+'"  class="form-control input-margin" name="dateOnShrinkWrap" value="" onfocus="doAjaxForDate('+countrow+')" ></td>'+
	'<td width="22%"><input type="text" id="remarks'+countrow+'"  class="form-control input-margin" name="remarks" value="" ></td>'+
	'<td width="22%"><input type="hidden" id="recordID'+countrow+'" class="form-control input-margin" name="Bin" value="" ></td>'+
	'<td><button type="button" class="delete" onclick="deleteRow('+countrow+')">-</button></td>'+
	   	'</tr>';
	   	$('#table1').append(data);
	$('#table1').append('<tr id=addmoreButton'+rowCount+'><td><button type="button" class="addmoreButton" onclick="replicateValue('+rowCount+')">Replicate Value</button></td><td><button type="button" class="addmoreButton" onclick="addRow('+rowCount+')">Add Blank Row</button></td><td></td><td></td></tr>');
}
	
	
function deleteRow(i){
	
	jQuery('#main'+i).remove();
	
};
	
</script>

<script type="text/javascript">
function doAjaxPostInsert(str) {
	addHeader();
	var discrepancyAllocations = [];
	//var val = $("#member").val();
	var val = ($('#table1 tr').length)-2;
	var isValid = true;
	//alert(($('#table1 tr').length)-2)
	for (i = 0; i <= countrow; i++) {
		
		if(($('#Denomination'+i).val()!=undefined) && $('#Denomination'+i).val()!=2000  && $('#Denomination'+i).val()!=1000 && $('#Denomination'+i).val()!=500 && $('#Denomination'+i).val()!=200 && $('#Denomination'+i).val()!=100 && $('#Denomination'+i).val()!=50 && $('#Denomination'+i).val()!=20 && $('#Denomination'+i).val()!=10 && $('#Denomination'+i).val()!=5 && $('#Denomination'+i).val()!=2 && $('#Denomination'+i).val()!=1){
			$('#err8').show();
				isValid = false;
			}
			if(($('#discrepancyType'+i).val()!=undefined) && $('#discrepancyType'+i).val() == ""){
				$('#err9').show();
				isValid = false;
			}
			/* if(($('#NoteSerialNumber'+i).val()!=undefined) && isNaN($('#NoteSerialNumber'+i).val())){
				$('#err10').show();
				isValid = false;
			} */
			if(($('#NumberOfNotes'+i).val()!=undefined) && $('#NumberOfNotes'+i).val() == ""){
				$('#err12').show();
				isValid = false;
			}
			if( ($('#Value'+i).val()!=undefined) &&  $('#Value'+i).val() == ""){
				$('#err11').show();
				isValid = false;
			}
			if(($('#printYear'+i).val() != undefined) && isNaN($('#printYear'+i).val())){
				$('#err15').show();
				isValid = false;
			}
			/* if(($('#remarks'+i).val()!=undefined) && $('#remarks'+i).val() == ""){
				$('#err13').show();
				isValid = false;
			} */
	if(($('#Denomination'+i).val()!=undefined) && isValid){
		discrepancyAllocations.push({"denomination":$('#Denomination'+i).val(),
			"discrepancyType":$('#discrepancyType'+i).val(),"mutilType":$('#mutilType'+i).val(),
			"noteSerialNumber":$('#NoteSerialNumber'+i).val(),"numberOfNotes":$('#NumberOfNotes'+i).val(),
			"value":$('#Value'+i).val(),"printYear":$('#printYear'+i).val(),
			"dateOnShrinkWrap":$('#dateOnShrinkWrap'+i).val(),"remarks":$('#remarks'+i).val()});
		}
	}
		
	var DiscrepancyWrapper = {
			"machineNumber":$('#machineNumber').val(),
			"discrepancyDate":$('#discrepancyDate').val(),
			"solId":$('#solId').val(),
			"branch":$('#branch').val(),
			"accountTellerCam":$('#accountTellerCam').val(),
			"customerName":$('#customerName').val(),
			"accountNumber":$('#accountNumber').val(),
			"discrepancyType":$('input[id=discrepancyType]:checked').val(),
			"mutilType":$('input[id=mutilType]:checked').val(),
			"discrepancyAllocations": discrepancyAllocations
	}
	
	if($('#machineNumber').val() == ""){
		$('#err1').show();
		isValid=false;
	}
	if($('#discrepancyDate').val() == ""){
		$('#err2').show();
		isValid = false;
	}
	if($('#solId').val() == ""){
		$('#err3').show();
		isValid = false;
	}else if($('#solId').val().length != 4){
		$('#err14').show();
		isValid = false;
	}
	/* if($('#solId').val() == ""){
		$('#err4').show();
		isValid = false;
	} */
	if($('#accountTellerCam').val() == ""){
		$('#err5').show();
		isValid = false;
	}
	if($('#customerName').val() == ""){
		$('#err6').show();
		isValid = false;
	}
	if($('#accountNumber').val() == "" && $('#accountTellerCam').val() == "ACCOUNT"){
		$('#err7').show();
		isValid = false;
	}
	
	
	if(isValid){
	$.ajax({  
		  type: "POST",
	      contentType : 'application/json; charset=utf-8',
	      dataType : 'json',
	    url: "././suspenseDiscrepancyAllocation",
	    data: JSON.stringify(DiscrepancyWrapper),
	    success: function(response){ 
	    	alert('Record submit.');
	    	window.location='././viewSuspenseDiscrepancy'; 
	    	$('#print'+str).prop('disabled', true);
	    }, 
	    error: function(e){  
	      alert('Error: ' + e);  
	    }  
	  });  
	}
}

</script>


<script type="text/javascript">
function getMutilValue(i) {
	//var val = $("#member").val();
	//for (i = 0; i < val; i++) {
		var dis = $('#discrepancyType'+i).val();
		if(dis == 'MUTILATED'){
			$("#mutilType"+i).removeAttr('disabled');
			//$("#mutil"+i).show();
    	}
		else
		{
   	    	//$("#mutil"+i).disable();
   	    	$("#mutilType"+i).attr('disabled', 'disabled');
   		}
	//}
}

function getFakeNotePrintYear(i) {
	var dis = $('#discrepancyType'+i).val();
	if(dis == 'FAKE'){
		$("#printYear"+i).removeAttr('disabled');
   	}else{
	    $("#printYear"+i).attr('disabled', 'disabled');
	}
}

function getSerialNumber(i) {
	var dis = $('#discrepancyType'+i).val();
	if(dis == 'FAKE' || dis == 'MUTILATED'){
		$("#NoteSerialNumber"+i).removeAttr('disabled');
   	}else{
	    $("#NoteSerialNumber"+i).attr('disabled', 'disabled');
	}
}
</script>

<script>
function doAjaxPost(str) {  
	  // get the form values  
	  var total = 0;
	  var Denomination = $('#Denomination'+str).val();
	  var NumberOfNotes = $('#NumberOfNotes'+str).val();
	  var dis = $('#discrepancyType'+str).val();
	  var mutil = $('#mutilType'+str).val();
	  if(dis == 'MUTILATED'){
		  if(mutil == 'FULL VALUE'){
			  total = Denomination*NumberOfNotes;
		  }
		  if(mutil == 'HALF VALUE'){
			  total = Denomination*NumberOfNotes*0.5;
		  }
		  if(mutil == 'ZERO VALUE'){
			  total = Denomination*NumberOfNotes*0;
		  }
	  }else{
		  total = Denomination*NumberOfNotes;
	  }
	  $('#Value'+str).val(total);
	}  
	
function doAjaxForTotal() { 
	var j = 0;
	var myTotalValue = null;
	var noOfRows = ($('#table1 tr').length-2);
	/* alert(noOfRows) */
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
								<li><a href="././viewSuspenseDiscrepancy"><i
										class="fa fa-table fa-fw"></i> View Discrepancy Data</a></li>
							</ul>
							Add Suspense Discrepancy
						</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-10">
									<!--<form role="form">-->
									<form:form id="#" action="#" method="post"
										modelAttribute="user" autocomplete="off">

										<div class="col-lg-6 form-group">
											<label>Machine Number</label>
											<form:select path="machineNumber" id="machineNumber"
												cssClass="form-control">
												<option value="" label="Select Machine Number"></option>
												<form:options items="${machineNumberList}" />
											</form:select>
											<label id="err1" style="display: none; color: red">Please
												Enter Machine Number</label>
										</div>

										<div class="col-lg-6 form-group">
											<label>Discrepancy Date</label>
											<form:input type="text" onkeyup="return false"
												path="discrepancyDate" id="discrepancyDate"
												name="discrepancyDate" cssClass="form-control" />
											<label id="err2" style="display: none; color: red">Please
												Enter Discrepancy Date</label>
										</div>

										<div class="col-lg-6 form-group">
											<label>SOL ID</label>
											<form:input path="solId" id="solId" name="solId"
												cssClass="form-control" onkeyup="doAjaxPostForBranch()"
												maxlength="4" />
											<label id="err3" style="display: none; color: red">Please
												Enter SOL ID</label> <label id="err13"
												style="display: none; color: red">Please Enter 4
												Digit SOL ID</label>
										</div>

										<div class="col-lg-6 form-group">
											<label>Branch</label>
											<form:input path="branch" id="branch" name="branch"
												cssClass="form-control" readonly="true" />
										</div>

										<div class="col-lg-6 form-group">
											<label>Upload Photo</label> <input type="file" name="file" />
											<label id="err4" style="display: none; color: red">Upload
												Photo</label>
										</div>

										<div class="col-lg-6 form-group">
											<label>Account/Teller/Cam</label>
											<form:select path="accountTellerCam" id="accountTellerCam"
												name="accountTellerCam" cssClass="form-control">
												<form:option value="">Select Option</form:option>
												<form:option value="ACCOUNT">ACCOUNT</form:option>
												<form:option value="TELLER">TELLER</form:option>
												<form:option value="CAM">CAM</form:option>
											</form:select>
											<label id="err5" style="display: none; color: red">Please
												Select Account/Teller/Cam</label>
										</div>

										<div class="col-lg-6 form-group">
											<label>Customer Name / Teller Name</label>
											<form:input path="customerName" id="customerName"
												cssClass="form-control" />
											<label id="err6" style="display: none; color: red">Please
												Enter Customer name</label>
										</div>

										<div class="col-lg-6 form-group">
											<label>Account Number / Teller</label>
											<form:input path="accountNumber" id="accountNumber"
												cssClass="form-control" />
											<label id="err7" style="display: none; color: red">Please
												Enter Account Number</label>
										</div>

										<div class="form-group">
											<label>Number of Entries</label> <input type="text"
												id="member" name="member" value="" class="form-control"><br />
											<div id="container">
												<table id="table1">
													<tr>

													</tr>
												</table>

												<label id="err8" style="display: none; color: red">Please
													Enter Valid Denomination, </label> <label id="err9"
													style="display: none; color: red">Please Select
													Discrepancy Type, </label> <label id="err10"
													style="display: none; color: red">Please Enter Note
													Serial Number, </label> <label id="err11"
													style="display: none; color: red">Please Enter
													Value, </label> <label id="err12" style="display: none; color: red">Please
													Enter Number of Notes, </label> <label id="err14"
													style="display: none; color: red">Please Enter
													Remark, </label> <label id="err15"
													style="display: none; color: red">Please Enter
													Valid Print Year</label>
											</div>
										</div>
										<div class="col-lg-12">
											<input type="button" id="save" value="Submit"
												onclick="doAjaxPostInsert('+i+');return false">
										</div>
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
		$('#discrepancyDate').datetimepicker({
			format : 'Y-m-d',
		});
	</script>

	<script type="text/javascript">
		function doAjaxForDate(i) {
			$('#dateOnShrinkWrap'+i+'').datetimepicker({
				format : 'Y-m-d',
			});
		}
	</script>

	<script type="text/javascript" src="./js/htmlInjection.js"></script>
	<script type="text/javascript" src="./js/print.js"></script>

</body>

</html>