<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>
<link rel="shortcut icon" href="./resources/logo/yes_favicon.ico"
	type="image/x-icon">
<link href="./resources/css/calendar.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.datetimepicker.css" />
<script type="text/javascript" src="./js/jquery-1.12.0.min.js"></script>
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
	
	var row=${row};
	dynamicRow(row);
	var dateOrder=$("#orderDate").val();
	var dateAr = dateOrder.split(' ');
	 var orderDate=dateAr[0];
	 $("#orderDate").val(orderDate);
		
	 var d3=$("#approvedRemittanceDate").val();
	 var dateAr = d3.split(' ');
	  var approvedRemittanceDate=dateAr[0];
	  $("#approvedRemittanceDate").val(approvedRemittanceDate);
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
	//	var val = $("#member").val();
	
	 $("#member").val(row);
		 var val = row;
if(val>0)
	{
var html='<tr><th>Denomination</th><th>Bundle</th><th>Total</th></tr>' ;
	for (i = 0; i < val; i++) {
html += '<tr id="main'+i+'">'+
		'<td><input type="text" id="Denomination'+i+'" onkeyup="doAjaxForTotal('+i+')" class="form-control input-margin" name="Denomination" value="" autofocus></td>'+
		'<td><input type="text" id="Bundle'+i+'"  class="form-control input-margin" name="Bundle" value="" onkeyup="doAjaxPost('+i+');doAjaxForTotal('+i+')" ></td>'+
		'<td><input type="text" id="TotalWithFormatter'+i+'"  onkeyup="doAjaxForTotal('+i+')" class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
		'<td><input type="hidden" id="recordID'+i+'" class="form-control input-margin" name="Bin" value="" ></td>'+
		'<td><button type="button" onclick="deleteRow('+i+');doAjaxForTotal('+i+')">-</button></td>'+
		'<td><input type="hidden" id="Total'+i+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
 	'</tr> ';
countrow++;
	} 
	$('#table1').append(html);
 $('#table1').append('<tr id=addmoreButton'+i+'><td><button type="button" class="addmoreButton" onclick="replicateValue('+i+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button" class="addmoreButton" onclick="addRow('+i+');doAjaxForTotal()">Add Blank Row</button></td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly></td></tr>');
//$('#table1').append('<tr id=addmoreButton'+i+'><td></td><td></td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly></td></tr>');

	var rowCountHiddenTable = ($('#hiddenTable .trValue').length);
	var  totalValue=0.00;
	 $('#hiddenTable .trValue').each(function(index)
			   
			  {
		   
		   var Denomination = $(this).find("td").eq(0).html(); 
		   var Bundle = $(this).find("td").eq(1).html(); 
		   
				$("#Denomination"+index).val(Denomination);
				$("#Bundle"+index).val(Bundle);
			var	TotalWithFormatter = (1000* parseFloat(Denomination)).toFixed(2);
				$("#TotalWithFormatter"+index).val(TotalWithFormatter);
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
	
    //alert(countrow)
	//dataId = countrow-1;
    //alert("value from ID="+dataId)
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
	var  data= '<tr id="main'+countrow+'">'+
	'<td><input type="text" id="Denomination'+countrow+'" class="form-control input-margin" name="Denomination" value="'+denomData+'" ></td>'+
	'<td><input type="text" id="Bundle'+countrow+'" class="form-control input-margin" name="Bundle" value="'+bundleData+'" onkeyup="doAjaxPost('+countrow+')" ></td>'+
	'<td><input type="text" id="TotalWithFormatter'+countrow+'"  class="form-control input-margin" name="Total" value="'+totalData+'" readonly="true"></td>'+
	'<td><input type="hidden" id="recordID'+countrow+'" class="form-control input-margin" name="Bin" value="" ></td>'+
	'<td><button type="button" onclick="deleteRow('+countrow+')">-</button></td>'+
	'<td><input type="hidden" id="Total'+countrow+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
	'</tr>';
	$('#table1').append(data);
	countrow++;
	$('#table1').append('<tr id=addmoreButton'+rowCount+'><td><button type="button" class="addmoreButton" onclick="replicateValue('+rowCount+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button" class="addmoreButton" onclick="addRow('+rowCount+');doAjaxForTotal()">Add Blank Row</button></td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly ></td></tr>');
}
	
	
function addRow(i){
	/* countrow++;
	 var rowCount = ($('#table1 tr').length)-2; */
	
	 var rowCount = ($('#table1 tr').length)-2;
	 countrow=rowCount;
	 
	jQuery('#addmoreButton'+i).remove();
	i++;
	var  data= '<tr id="main'+countrow+'">'+
	'<td><input type="text" id="Denomination'+countrow+'" class="form-control input-margin" name="Denomination" value="" ></td>'+
	'<td><input type="text" id="Bundle'+countrow+'" class="form-control input-margin" name="Bundle" value="" onkeyup="doAjaxPost('+countrow+')" ></td>'+
	'<td><input type="text" id="TotalWithFormatter'+countrow+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
	'<td><input type="hidden" id="recordID'+countrow+'" class="form-control input-margin" name="Bin" value="" ></td>'+
	'<td><button type="button" onclick="deleteRow('+countrow+')">-</button></td>'+
	'<td><input type="hidden" id="Total'+countrow+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
	'</tr>';
	
	$('#table1').append(data);
	$('#table1').append('<tr id=addmoreButton'+rowCount+'><td><button type="button" class="addmoreButton" onclick="replicateValue('+rowCount+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button" class="addmoreButton" onclick="addRow('+rowCount+');doAjaxForTotal()">Add Blank Row</button></td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly></td></tr>');

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
function doAjaxPostInsert() {
	addHeaderJson();
	var remittanceAllocations = [];
	var val = $("#member").val();
	var isValid = true;
	var soiledWrapper = {
			"id":$('#id').val(),
			"orderDate":$('#orderDate').val(),
			"remittanceOrderNo":$('#remittanceOrderNo').val(),
			"approvedRemittanceDate":$('#approvedRemittanceDate').val(),
			"notes":$('input[name=notes]:checked').val(),
			"types":$('input[name=types]:checked').val(),
			"vehicleNumber":$('#vehicleNumber').val(),
			"location":$('#location').val(),
			"remittanceAllocations": remittanceAllocations
	 }
	if($('#orderDate').val() == ""){
		$('#err1').show();
		isValid = false;
	}
	if($('#remittanceOrderNo').val() == ""){
		$('#err2').show();
		isValid = false;
	}
	if($('#approvedRemittanceDate').val() == ""){
		$('#err3').show();
		isValid = false;
	}
	if($('input[name=notes]:checked').length==0){
		$('#err4').show();
		isValid = false;
	}
	if($('input[name=types]:checked').length==0){
		$('#err5').show();
		isValid = false;
	}
	
	if($('#vehicleNumber').val() == ""){
		 $('#err6').show();
		isValid = false;
	}
	if($('#location').val() == ""){
		 $('#err7').show();
		isValid = false;
	}
	
//For Denomination Validation	
for (i = 0; i <= countrow; i++) {
		
	if(($('#Denomination'+i).val()!=undefined) && $('#Denomination'+i).val()!=2000 && $('#Denomination'+i).val()!=1000 && $('#Denomination'+i).val()!=500 && $('#Denomination'+i).val()!=200 && $('#Denomination'+i).val()!=100 && $('#Denomination'+i).val()!=50 && $('#Denomination'+i).val()!=20 && $('#Denomination'+i).val()!=10 && $('#Denomination'+i).val()!=5 && $('#Denomination'+i).val()!=2 && $('#Denomination'+i).val()!=1){
			$('#err8').show();
			isValid = false;
		}
		if(($('#Bundle'+i).val()!=undefined) && $('#Bundle'+i).val() == ""){
			$('#err9').show();
			isValid = false;
		}
		
		if(($('#Denomination'+i).val()!=undefined) && isValid){
		remittanceAllocations.push({"denomination":$('#Denomination'+i).val(),
									"bundle":$('#Bundle'+i).val(),
									"total":$('#Total'+i).val()});
						}
				}
		
		//Close Denomination Validation
		
	if(isValid){
	$.ajax({  
		  type: "POST",
	      contentType : 'application/json; charset=utf-8',
	      dataType : 'json',
	    url: "././updateInsertSoiledRemittance",
	    data: JSON.stringify(soiledWrapper),
	    success: function(response){ 
	    	alert('record update and submit.');
	    	
	    	$('#btnsubmit').prop("disabled",true);
	    	window.location='././viewSoiled';
	    }, 
	    error: function(e){  
	      alert('Error: ' + e.responseJSON.message);
	      $('#btnsubmit').prop("disabled",false);
	    }  
	  });
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

</script>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title> Soiled</title>
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
function refresh() {
	//window.location = '././Soiled';
	window.location.reload(true);
}
</script>

<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>
<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-140"
	data-genuitec-path="/Currency/src/main/webapp/jsp/editViewSoiled.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-140"
		data-genuitec-path="/Currency/src/main/webapp/jsp/editViewSoiled.jsp">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">
			<!--  <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Forms</h1>
                </div>
                /.col-lg-12
            </div> -->
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<ul>
								<li><a href="././viewSoiled"><i
										class="fa fa-table fa-fw"></i>View Soiled</a></li>
							</ul>
							Soiled
						</div>
						<div class="panel-body">
							<div align="center" style="color: white; background: red;">
								<b>${errorMsg}</b>
							</div>
							<div class="row">
								<!--<form role="form">-->
								<form:form id="cash" action="" method="post"
									modelAttribute="user" autocomplete="off">
									<form:hidden path="id" />
									<div class="col-lg-6 form-group">
										<label>Order Date</label>
										<form:input type="text" onkeypress="return false"
											path="orderDate" id="orderDate" cssClass="form-control" />
										<label id="err1" style="display: none; color: red">Enter
											Order Date</label>
									</div>

									<div class="col-lg-6 form-group">
										<label>Remittance Order Number</label>
										<form:input path="remittanceOrderNo" id="remittanceOrderNo"
											cssClass="form-control" />
										<label id="err2" style="display: none; color: red">Enter
											Remittance Order Number</label>
									</div>

									<div class="col-lg-6 form-group">
										<label>Approved Remittance Date</label>
										<form:input type="text" onkeypress="return false"
											path="approvedRemittanceDate" id="approvedRemittanceDate"
											cssClass="form-control" />
										<label id="err3" style="display: none; color: red">Enter
											Approved Remittance Date</label>
									</div>

									<div class="form-group">
										<form:checkbox path="notes" id="notes" name="notes"
											value="Notes" />
										<span class="deno-value">Notes </span> <label id="err4"
											style="display: none; color: red"> Please Select
											Notes</label>
										<!-- <input type="radio" id="notes"> -->
									</div>

									<div class="form-group">
										<form:radiobutton path="types" id="types" name="types"
											value="Normal" />
										<span class="deno-value">Normal </span>
										<form:radiobutton path="types" id="types" name="types"
											value="Full Value Mutilated" />
										<span class="deno-value">Full Value Mutilated </span> <label
											id="err5" style="display: none; color: red"> Please
											Select at least One Radio Button</label>
									</div>

									<div class="col-lg-6 form-group">
										<label>Vehicle Number</label>
										<form:input path="vehicleNumber" id="vehicleNumber"
											cssClass="form-control" />
									</div>
									<label id="err6" style="display: none; color: red">
										Enter Vehicle Number</label>

									<div class="col-lg-6 form-group">
										<label>Location</label>
										<form:input path="location" id="location"
											cssClass="form-control" />
										<label id="err7" style="display: none; color: red">
											Enter Location</label>
									</div>
									<div class="form-group">
										<table id="hiddenTable" class="hidden">
											<tr>
												<th>Denomination</th>
												<th>Bundle</th>
												<th>Total</th>
											</tr>
											<c:forEach var="entry" items="${soiledRemitationRecord}">
												<tr class="trValue">
													<td class="tdValue"><c:out
															value="${entry.denomination}" /></td>
													<td class="tdValue"><c:out
															value="${entry.pendingBundle}" /></td>
												</tr>
											</c:forEach>

										</table>
									</div>

									<div class="form-group">
										<label>Number of Entries</label> <input type="text"
											id="member" name="member" value="" class="form-control"><br />
										<label id="err8" style="display: none; color: red">Please
											Enter Valid Denomination</label> <label id="err9"
											style="display: none; color: red">Please Enter Bundle</label>
										<div id="container">
											<table id="table1">
												<tr>
												</tr>
											</table>
										</div>
									</div>

									<!-- 	<div class="col-lg-12">
											<input type="button" id="btnSubmit" value="UpdateAndInsert"	onclick="doAjaxPostInsert()">
											
											 <button type="button" value="Update" id="btnsubmit"
											onclick="doAjaxPostInsert('+i+')" class="btn btn-primary">
  Update
</button>
                                     <div align="right">
											 <button type="submit" class="btn btn-default" value="Details"
												style="width: 99px;" onclick="refresh();">Refresh</button> 
										</div>
										</div> -->
									<c:choose>
										<c:when test="${status=='REQUESTED'}">
											<div class="col-lg-12">

												<button type="button" value="Update" id="btnsubmit"
													onclick="doAjaxPostInsert('+i+')" class="btn btn-primary">
													Update</button>
											</div>

											<div align="right">
												<button type="submit" class="btn btn-default"
													value="Details" style="width: 99px;" onclick="refresh()">Refresh</button>
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
								<br>
								<br>

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
		$('#approvedRemittanceDate').datetimepicker({
			format : 'Y-m-d',

		});
	</script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>