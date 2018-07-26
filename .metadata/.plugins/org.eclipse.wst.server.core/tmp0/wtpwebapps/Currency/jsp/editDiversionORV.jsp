<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

<title>ICICI : Edit Outward Diversion</title>

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
	//$("#member").keyup(function(){
		 $('#waitingImg').hide();
		var row=${row};
		valueGenerator(row);
		
		//alert(member1+"  row value")
		
	//$("#member").change(function() { 
		
		$("#member").change(function() { 
		var val = $("#member").val();
		valueGenerator(val);
	});
		
		
		function valueGenerator(row)
		{
			
		 //alert(${member}+"  row value")
		 
		
		var rowCount = ($('#table1 tr').length);
		 if(rowCount>0){
			document.getElementById("table1").innerHTML = "";
		 }  
		  $("#member").val(row);
		
		var val=row;
		
		
    if(val>0)
	{
var html='<tr><th>Category</th><th>Denomination</th><th>Bundle</th><th>Total</th></tr>' ;
	for (i = 0; i < val; i++) {
	
html += '<tr id="main'+i+'">'+
'<td width="22%"><select name="Category" id="Category'+i+'" class="form-control input-margin"><option value="ISSUABLE">ISSUABLE</option><option value="ATM">ATM</option><option value="FRESH">FRESH</option></td>'+
		'<td><input type="text" id="Denomination'+i+'" onkeyup="doAjaxForTotal('+i+')" class="form-control input-margin" name="Denomination" value="" autofocus></td>'+
		'<td><input type="text" id="Bundle'+i+'" class="form-control input-margin" name="Bundle" value="" onkeyup="doAjaxPost('+i+');doAjaxForTotal('+i+')"></td>'+
		'<td><input type="text" id="TotalWithFormatter'+i+'" onkeyup="doAjaxForTotal('+i+')" class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
		'<td><input type="hidden" id="recordID'+i+'" class="form-control input-margin" name="Bin" value="" ></td>'+
		'<td><button type="button" onclick="deleteRow('+i+');doAjaxForTotal('+i+')">-</button></td>'+
		'<td><input type="hidden" maxlength="45" id="Total'+i+'"  class="form-control input-margin" name="Total" value="" autofocus readonly="true"></td>'+
   	'</tr>';
countrow++;
} 
	$('#table1').append(html);
	$('#table1').append('<tr id=addmoreButton'+(i-1)+'><td><button type="button"  class="addmoreButton" onclick="replicateValue('+(i-1)+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button"  class="addmoreButton" onclick="addRow('+(i-1)+');doAjaxForTotal()">Add Blank Row</button></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly></td></tr>');

	
	
	
	var rowCountHiddenTable = ($('#hiddenTable .trValue').length);
	var  totalValue=0.00;
	 $('#hiddenTable .trValue').each(function(index)
			   
			  {
		   
		   var Category = $(this).find("td").eq(0).html(); 
		   var Denomination = $(this).find("td").eq(1).html(); 
		   var Bundle = $(this).find("td").eq(2).html(); 
		   var TotalWithFormatter = $(this).find("td").eq(3).html(); 
		  
			   
			    $("#Category"+index).prepend('<option value='+Category+' selected="selected">'+Category+'</option>');
				$("#Denomination"+index).val(Denomination);
				$("#Bundle"+index).val(Bundle);
				$("#TotalWithFormatter"+index).val(TotalWithFormatter);
				totalValue = (parseFloat(totalValue) +  parseFloat(TotalWithFormatter)).toFixed(2);
				
			  }); 
	 $("#totalValue").val(totalValue);
	 
			  }
	
	
	//}
    else
    	{
    	$('#table1').empty();
    	}
  //  });
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
	var data= '<tr id="main'+countrow+'">'+
	'<td width="22%"><select name="Category" id="Category'+countrow+'" class="form-control input-margin"><option value="'+selectedCategory+'">'+selectedCategory+'</option></select></td>'+
			'<td><input type="text" id="Denomination'+countrow+'"  onkeyup="doAjaxForTotal('+i+')" class="form-control input-margin" name="Denomination" value="'+denomData+'" ></td>'+
			'<td><input type="text" id="Bundle'+countrow+'"  onkeyup="doAjaxForTotal('+i+')" class="form-control input-margin" name="Bundle" value="'+bundleData+'" onkeyup="doAjaxPost('+countrow+')" ></td>'+
			'<td><input type="text" id="TotalWithFormatter'+countrow+'"   onkeyup="doAjaxForTotal('+i+')" class="form-control input-margin" name="Total" value="'+totalData+'" readonly="true"></td>'+
			'<td><input type="hidden" id="recordID'+countrow+'" class="form-control input-margin" name="Bin" value="" ></td>'+
			'<td><button type="button" onclick="deleteRow('+countrow+')">-</button></td>'+
			'<td><input type="hidden" maxlength="45" id="Total'+countrow+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
	   	'</tr>';
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
	var data= '<tr id="main'+countrow+'">'+
	'<td width="22%"><select name="Category" id="Category'+countrow+'" class="form-control input-margin"><option value="ISSUABLE">ISSUABLE</option><option value="ATM">ATM</option><option value="FRESH">FRESH</option></td>'+
			'<td><input type="text" id="Denomination'+countrow+'" class="form-control input-margin" name="Denomination" value="" ></td>'+
			'<td><input type="text" id="Bundle'+countrow+'" class="form-control input-margin" name="Bundle" value="" onkeyup="doAjaxPost('+countrow+')" ></td>'+
			'<td><input type="text" id="TotalWithFormatter'+countrow+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
			'<td><input type="hidden" id="recordID'+countrow+'" class="form-control input-margin" name="Bin" value="" ></td>'+
			'<td><button type="button" onclick="deleteRow('+countrow+')">-</button></td>'+
			'<td><input type="hidden" maxlength="45" id="Total'+countrow+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
	   	'</tr>';
	
	
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
function doAjaxPostInsert(str) {
 
	
			
 	 var d2=$("#orderDate").val();
     var dateAr = d2.split('-');
  var datelast=dateAr[0];
  var midleDate=dateAr[1];
  var lastData=dateAr[2];
  
  var lastDateone=lastData.split(" ");
  var lastDateOne1=lastDateone[0];
   var orderDateActual = datelast + '-' + midleDate + '-' + lastDateOne1;

   
   
   
    var d3=$("#expiryDate").val();
    var dateAr1 = d3.split('-');
  var datelast1=dateAr[0];
  var midleDate1=dateAr[1];
  var lastData1=dateAr[2];
  
  var lastDateone1=lastData1.split(" ");
  var lastDateOne11=lastDateone1[0];
     var orderDateActual = datelast + '-' + midleDate + '-' + lastDateOne1;

	addHeader();
	var diversionAllocations = [];
	var val = $("#member").val();
	var isValid = true;
   //alert("1")
   var id1;
	var DorvWrapper = {
	      
			"id":$('#id').val(),
		    //"orderDate":$('#orderDate').val(),
		    "orderDate":orderDateActual,
			"rbiOrderNo":$('#rbiOrderNo').val(),
			//"expiryDate":$('#expiryDate').val(),
			"expiryDate":orderDateActual,
			"bankName":$('#bankName').val(),
			"approvedCC":$('#approvedCC').val(),
			"location":$('#location').val(),
			"diversionAllocations": diversionAllocations
	}
	
	//var isValid = true;
	if($('#orderDate').val() == ""){
		$('#err1').show();
		isValid=false;
	}
	if($('#rbiOrderNo').val() == ""){
		$('#err2').show();
		isValid = false;
	}
	if($('#expiryDate').val() == ""){
		$('#err3').show();
		isValid = false;
	}
	if($('#bankName').val() == ""){
		$('#err4').show();
		isValid = false;
	}
	if($('#approvedCC').val() == ""){
		$('#err5').show();
		isValid = false;
	}
	if($('#location').val() == ""){
		$('#err6').show();
		isValid = false;
	}
	
	for (i = 0; i <= countrow; i++) {
		 if(($('#Denomination'+i).val()!=undefined) && $('#Denomination'+i).val()!=2000 
				 && $('#Denomination'+i).val()!=1000 && $('#Denomination'+i).val()!=500 && $('#Denomination'+i).val()!=200 
				 && $('#Denomination'+i).val()!=100 && $('#Denomination'+i).val()!=50 
				 && $('#Denomination'+i).val()!=20 && $('#Denomination'+i).val()!=10 
				 && $('#Denomination'+i).val()!=5 && $('#Denomination'+i).val()!=2 
				 && $('#Denomination'+i).val()!=1){
			$('#err7').show();
			isValid = false;
		}
		if(($('#Bundle'+i).val() != undefined) &&   $('#Bundle'+i).val() == ""){
			$('#err8').show();
			isValid = false;
		}
		if(($('#Denomination'+i).val()!=undefined) &&  isValid){
			diversionAllocations.push({
				"currencyType":$('#Category'+i).val(),
			  "denomination":$('#Denomination'+i).val(),
			  "bundle":$('#Bundle'+i).val(),
				"total":$('#Total'+i).val()
				});
			}
		}
	if(diversionAllocations[0]['total']==null || diversionAllocations[0]['total']==""){
		isValid=false;
		alert("Please Change Something before update");
		} 
	if(isValid){
		 $('#waitingImg').show();
	$.ajax({  
		type: "POST",
	    contentType : 'application/json; charset=utf-8',
	    
	    dataType : 'json',
	   url: "././UpdateDorvAllocation",
	    data: JSON.stringify(DorvWrapper),
	    success: function(response){ 
	    	 $('#waitingImg').hide();
	    	alert('Record Updated.');
	    	window.location='././viewDorv'; 
	    	//$('#print'+str).prop('disabled', true);
	    }, 
	    error: function(e){  
	      //alert('Error: ' + e);  
	      alert('Reason: ' + e.responseJSON.message); 
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
		  	}  
		} 
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
	//window.location = '././Dorv';
	window.location.reload(true);
}
</script>

<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script></head>
<body oncontextmenu="return false;" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-113" data-genuitec-path="/Currency/src/main/webapp/jsp/editDiversionORV.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-113" data-genuitec-path="/Currency/src/main/webapp/jsp/editDiversionORV.jsp">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<ul>
								<li><a href="././viewDorv"><i class="fa fa-table fa-fw"></i>View
										Diversion Payment</a></li>
							</ul>
							Outward Diversion
						</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-8">
									<!--<form role="form">-->

									<form:form id="" action="" method="post" modelAttribute="user"
										autocomplete="off">

										<form:hidden path="id" />
										<%-- <div class="col-lg-6 form-group">
											<label>ID</label>
											<form:input type="text" onkeypress="return false" path="id" id="id" name="id" cssClass="form-control"/>
											<label id="err1" style="display: none;color: red">ID</label>
										</div> --%>

										<div class="col-lg-6 form-group">
											<label>Order Date</label>

											<form:input type="text" onkeypress="return false"
												path="orderDate" id="orderDate" name="orderDate"
												maxlength="32" cssClass="form-control" />
											<label id="err1" style="display: none; color: red">Select
												Order Date</label>
										</div>
										<div class="col-lg-6 form-group">
											<label>RBI Order Number</label>
											<form:input path="rbiOrderNo" id="rbiOrderNo"
												cssClass="form-control" onkeypress="return isNumber(event)" />
											<label id="err2" style="display: none; color: red">Enter
												RBI ORder Number</label>
										</div>
										<div class="col-lg-6 form-group">
											<label>Expiry Date</label>
											<form:input type="text" onkeypress="return false"
												path="expiryDate" id="expiryDate" name="expiryDate"
												cssClass="form-control" />
											<label id="err3" style="display: none; color: red">Select
												Expiry Date</label>
										</div>
										<div class="col-lg-6 form-group">
											<label>Bank Name</label>
											<form:input path="bankName" id="bankName" name="bankName"
												cssClass="form-control" />
											<label id="err4" style="display: none; color: red">Enter
												Bank Name</label>
										</div>

										<div class="col-lg-6 form-group">
											<label>Branch Name</label>
											<form:input path="approvedCC" id="approvedCC"
												name="approvedCC" cssClass="form-control" />
											<label id="err5" style="display: none; color: red">Enter
												Approved CC</label>
										</div>
										<div class="col-lg-6 form-group">
											<label>Location</label>
											<form:input path="location" id="location" name="location"
												cssClass="form-control" />
											<label id="err6" style="display: none; color: red">Enter
												Location</label>
										</div>
										<img id="waitingImg" style="width: 150px; height: 60px;"
											src="./resources/logo/response-waiting.gif">
										<div class="form-group">

											<%-- <h6>Please Enter ${member} in Number of Entries Text field </h6> --%>
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
										<c:choose>
											<c:when test="${status=='REQUESTED'}">
												<div class="col-lg-12">
													<!-- <input type="button" id="save" value="Submit"	onclick="doAjaxPostInsert('+i+');return false">
											 -->
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



									<div class="form-group">
										<table id="hiddenTable" class="hidden">
											<tr>
												<th>Category</th>
												<th>Denomination</th>
												<th>Bundle</th>
												<th>Total</th>

											</tr>
											<c:forEach var="entry" items="${diversionAllocations}">
												<tr class="trValue">
													<td class="tdValue"><c:out
															value="${entry.currencyType}" /></td>
													<td class="tdValue"><c:out
															value="${entry.denomination}" /></td>
													<td class="tdValue"><c:out value="${entry.bundle}" /></td>
													<td class="tdValue"><c:out value="${entry.total}" /></td>

												</tr>
											</c:forEach>

										</table>
									</div>

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