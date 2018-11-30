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
<script type="text/javascript" src="./js/jquery-1.12.0.min.js"></script>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>ICICI : Other Bank Payment</title>

<!-- <script src="./resources/js/jquery.js"></script> -->

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

var countrow=0;
var rowCount = 0;
var dataId=0;

$(document).ready(function(){
	 $('#waitingImg').hide();
	 var row=${row};
	dynamicRow(row);
	/* $("#member").change(function() {  */
	$("#member").change(function() { 
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
    if(val>0)
	{
var html='<tr><th>Category</th><th>Denomination</th><th>Bundle</th><th>Total</th><th>Bin</th></tr>' ;
	for (i = 0; i < val; i++) {
html += '<tr id="main'+i+'">'+
'<td width="22%"><select name="Category" id="Category'+i+'" class="form-control input-margin"><option value="ISSUABLE">ISSUABLE</option><option value="FRESH">FRESH</option><option value="ATM">ATM</option></select></td>'+
		'<td><input type="text" id="Denomination'+i+'" onkeyup="doAjaxForTotal('+i+')"  class="form-control input-margin" name="Denomination" value="" ></td>'+
		'<td><input type="text" id="Bundle'+i+'"   class="form-control input-margin" name="Bundle" value="" onkeyup="doAjaxPost('+i+')" onkeyup="doAjaxForTotal('+i+')" ></td>'+
		'<td><input type="text" id="TotalWithFormatter'+i+'" onkeyup="doAjaxForTotal('+i+')"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
		'<td><input type="text" id="binNumber'+i+'" class="form-control input-margin" name="Bin" value="" readonly="true"></td>'+
		'<td><input type="hidden" id="recordID'+i+'" class="form-control input-margin" name="Bin" value="" ></td>'+
		'<td><input type="hidden" id="Total'+i+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
		'</tr>';
countrow++;
} 
	$('#table1').append(html);
	$('#table1').append('<tr id=addmoreButton'+i+'><td><button type="button" class="addmoreButton" onclick="replicateValue('+i+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button" class="addmoreButton" onclick="addRow('+i+');doAjaxForTotal()">Add Blank Row</button></td><td></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly></td></tr>');
	//$('#table1').append('<tr id=addmoreButton'+i+'><td></td><td></td><td></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly></td></tr>');

	var r=1;
	var rowCountHiddenTable = ($('#hiddenTable .trValue').length);
	var  totalValue=0.00;
	 $('#hiddenTable .trValue').each(function(index)
			   
			  {
		   
		   var Category = $(this).find("td").eq(0).html(); 
		   var Denomination = $(this).find("td").eq(1).html(); 
		   var Bundle = $(this).find("td").eq(2).html(); 
		   var TotalWithFormatter = $(this).find("td").eq(3).html(); 
		   var binNumber = $(this).find("td").eq(4).html(); 
			   
			    $("#Category"+index).prepend('<option value='+Category+' selected="selected">'+Category+'</option>');
				$("#Denomination"+index).val(Denomination);
				$("#Bundle"+index).val(Bundle);
				$("#TotalWithFormatter"+index).val(TotalWithFormatter);
				$("#binNumber"+index).val(binNumber);
				totalValue = (parseFloat(totalValue) +  parseFloat(TotalWithFormatter)).toFixed(2);
				
			  }); 
	 $("#totalValue").val(totalValue);
	 
			  }
    else
    	{
    	$('#table1').empty();
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
			'<td><input type="text" id="Denomination'+countrow+'" maxlength="4" minlength="1" class="form-control input-margin" name="Denomination" value="'+denomData+'" ></td>'+
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
	'<td width="22%"><select name="Category" id="Category'+countrow+'" class="form-control input-margin"><option value="ATM">ATM</option><option value="FRESH">FRESH</option><option value="ISSUABLE">ISSUABLE</option><option value="FRESH">FRESH</option></select></td>'+
			'<td><input type="text" id="Denomination'+countrow+'" maxlength="4" minlength="1" class="form-control input-margin" name="Denomination" value="" ></td>'+
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
	
	var otherBankAllocations = [];
	var val = $("#member").val();
	var isValid = true;
	var otherBankWrapper = {
			"id":$('#id').val(),
			"bankName":$('#bankName').val(),
			"solId":$('#solId').val(),
			"branch":$('#branch').val(),
			"rtgsUTRNo":$('#rtgsUTRNo').val(),
			"otherBankAllocations": otherBankAllocations
	}
	console.log(otherBankWrapper);
	if($('#bankName').val() == ""){
		$('#err1').show();
		isValid = false;
	}
	if($('#solId').val() == ""){
		$('#err2').show();
		isValid = false;
	}else if($('#solId').val().trim().length != 4){
		$('#err12').show();
		isValid = false;
	}
	if($('#rtgsUTRNo').val() == ""){
		$('#err4').show();
		isValid = false;
	}
	
//For Denomination Validation	
for (i = 0; i <= countrow; i++) {
		
	 if(($('#Denomination'+i).val()!=undefined) && $('#Denomination'+i).val()!=2000 && $('#Denomination'+i).val()!=1000 && $('#Denomination'+i).val()!=500 && $('#Denomination'+i).val()!=200 && $('#Denomination'+i).val()!=100 && $('#Denomination'+i).val()!=50 && $('#Denomination'+i).val()!=20 && $('#Denomination'+i).val()!=10 && $('#Denomination'+i).val()!=5 && $('#Denomination'+i).val()!=2 && $('#Denomination'+i).val()!=1){
			$('#err7').show();
			isValid = false;
		}
		if(($('#Bundle'+i).val() != undefined) &&   $('#Bundle'+i).val() == ""){
			
			$('#err8').show();
			isValid = false;
		}
		
		if(($('#Denomination'+i).val()!=undefined) &&  isValid)
	{
			otherBankAllocations.push({"currencyType":$('#Category'+i).val(),"denomination":$('#Denomination'+i).val(),"bundle":$('#Bundle'+i).val(),"total":$('#Total'+i).val(),"bin":$('#Bin'+i).val()});
	}
		}
		
if(otherBankAllocations[0]['total']==null || otherBankAllocations[0]['total']==""){
	isValid=false;
	alert("Please Change Something before update");
	} 
		
	if(isValid){
		 $('#waitingImg').show();
		$.ajax({  
		  type: "POST",
	      contentType : 'application/json; charset=utf-8',
	      dataType : 'json',
	    url: "././updateInsertOtherBankAllocation",
	    data: JSON.stringify(otherBankWrapper),
	    success: function(response){ 
	    	 $('#waitingImg').hide();
	    	alert('record Submit Update.');
	    	
	    	//$("#btnSubmit").prop('disabled',true);
	    	window.location='././viewOtherBankPayment';
	    }, 
	    error: function(e){  
	     // alert('Error: ' + e);  
	    	alert('Error: ' + e.responseJSON.message); 
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
				                  }  } 
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
function doAjaxForAccountNumber() {
	addHeaderJson();
	var mspName = $('#mspName').val();
	$.ajax({
		type : "POST",
		url : "././getAccountNumberForCRA",
		data : "mspName=" + mspName,
		success : function(response) {
			var newStr = response.substring(1, response .length-1); // Remove Array Brackets
	        var data=newStr.split(",");
			$('#accountNumber').val(data)
		},
		error : function(e) {
			alert('account Number Error: ' + e);
		}
	});
}

</script>

<script type="text/javascript">
function refresh() {
	window.location = '././otherBankPayment';
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
								<li><a href="././viewOtherBankPayment"><i
										class="fa fa-table fa-fw"></i>View Other Bank Payment</a></li>
							</ul>
							Other Bank Payment
						</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-8">
									<!--<form role="form">-->
									<form:form id="" action="" method="post" modelAttribute="user"
										autocomplete="off">
										<form:hidden path="id" id="id" name="id" />
										<div class="form-group"">
											<label>Bank Name</label>
											<form:input path="bankName" id="bankName" name="bankName"
												cssClass="form-control" />
											<label id="err1" style="display: none; color: red">Please
												Enter Bank Name</label>
										</div>
										<div class="col-lg-6 form-group">
											<label>SOL ID</label>
											<form:input path="solId" maxlength="4" id="solId"
												cssClass="form-control" onkeyup="doAjaxPostForBranch()" />
											<label id="err2" style="display: none; color: red">Please
												Enter Sol Id</label> <label id="err12"
												style="display: none; color: red">Please Enter Four
												Digit Sol Id</label>
										</div>
										<div class="col-lg-6 form-group">
											<label>Branch Name</label>
											<form:input type="text" readonly="true" path="branch"
												id="branch" name="branch" cssClass="form-control" />
											<!-- <label id="err3" style="display: none;color: red">Please Enter Branch Name</label>  -->
										</div>
										<div class="form-group">
											<label>RTGS UTR No.</label>
											<form:input path="rtgsUTRNo" id="rtgsUTRNo" name="rtgsUTRNo"
												cssClass="form-control" />
											<label id="err4" style="display: none; color: red">Please
												Enter RTGS UTR No.</label>
										</div>
										<div class="form-group">
											<table id="hiddenTable" class="hidden">
												<tr>
													<th>Category</th>
													<th>Denomination</th>
													<th>Bundle</th>
													<th>Total</th>
													<th>BinNumber</th>
												</tr>
												<c:forEach var="entry" items="${otherBankAloocationRecord}">
													<tr class="trValue">
														<td class="tdValue"><c:out
																value="${entry.currencyType}" /></td>
														<td class="tdValue"><c:out
																value="${entry.denomination}" /></td>
														<td class="tdValue"><c:out value="${entry.bundle}" /></td>
														<td class="tdValue"><c:out value="${entry.total}" /></td>
														<td class="tdValue"><c:out value="${entry.binNumber}" /></td>
													</tr>
												</c:forEach>

											</table>
										</div>
										<img id="waitingImg" style="width: 150px; height: 60px;"
											src="./resources/logo/response-waiting.gif">

										<div class="form-group">

											<label>Number of Entries</label> <input type="text"
												id="member" name="member" value="" class="form-control"><br />
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
										<c:if test="${status=='REQUESTED' }">
											<div class="col-lg-12">
												<!-- <input type="button" id="btnSubmit" value="Submit" onclick="doAjaxPostInsert('+i+'); return false">
											 -->
												<button type="button" value="Update" id="btnsubmit"
													onclick="doAjaxPostInsert('+i+')" class="btn btn-primary">Update</button>
											</div>

											<div align="right">
												<button type="submit" class="btn btn-default"
													value="Details" style="width: 99px;" onclick="refresh()">Refresh</button>
											</div>
										</c:if>
										<c:if test="${status=='ACCEPTED' or status=='RELEASED'}">
											<div class="text-center">
												<label class="bg-primary  text-white">Accepted by
													vault Management Can not be edited </label>
											</div>

										</c:if>

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