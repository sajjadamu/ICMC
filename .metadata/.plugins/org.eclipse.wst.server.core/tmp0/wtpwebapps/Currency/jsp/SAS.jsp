<!DOCTYPE html>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<html lang="en">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="./resources/logo/favicon.ico"
	type="image/x-icon">
<title>ICICI : SAS</title>
<script type="text/javascript">
	function ajaxCustodianName() {
		addHeaderJson();
		var vendor = $('#vendor').val();
		var radio;
		$.ajax({
			type : "POST",
			url : "././custodianName",
			data : "vendor=" + vendor,
			success : function(response) {
				var newStr = response.substring(1, response.length - 1); // Remove Array Brackets
				radio = newStr.split(",");
				var radioBtn = "<b>Custodian :</b> ";
				for (var i = 0; i < radio.length; i++) {
					$('#info').html(newStr);
					$('#vendor').val(vendor);
					$('#custodian').val(radio[i]);
					radioBtn += ' <input type="radio" name="custodian" id="custodian" value="'
							+ $.trim(radio[i]) + '" >' + $.trim(radio[i]);
					$('#custodian').html(radioBtn);
				}
			},
			error : function(e) {
				alert('Custodian Name: ' + e);
			}
		});
	}
</script>

<script type="text/javascript">
	function ajaxvehicle() {
		addHeaderJson();
		var vendor = $('#vendor').val();
		var radio;
		$.ajax({
			type : "POST",
			url : "././vehicleNumber",
			data : "vendor=" + vendor,
			success : function(response) {
				var newStr = response.substring(1, response.length - 1); // Remove Array Brackets
				radio = newStr.split(",");
				var radioBtn = "<b>Vehicle :</b> ";
				for (var i = 0; i < radio.length; i++) {
					$('#info').html(newStr);
					$('#vendor').val(vendor);
					$('#vehicle').val(radio[i]);
					radioBtn += ' <input type="radio" name="vehicle" id="vehicle" value="'
							+ $.trim(radio[i]) + '" >' + $.trim(radio[i]);
					$('#vehicle').html(radioBtn);
				}

			},
			error : function(e) {
				alert('Vehice No: ' + e);
			}
		});
	}
</script>
<!-- Bootstrap Core CSS -->
<link
	href="./resources/bower_components/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- MetisMenu CSS -->
<link
	href="./resources/bower_components/metisMenu/dist/metisMenu.min.css"
	rel="stylesheet">

<!-- DataTables CSS -->
<link
	href="./resources/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css"
	rel="stylesheet">

<!-- DataTables Responsive CSS -->
<link
	href="./resources/bower_components/datatables-responsive/css/responsive.dataTables.scss"
	rel="stylesheet">

<!-- Custom CSS -->
<link href="./resources/dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Custom Fonts -->
<link
	href="./resources/bower_components/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">

<link rel="./resources/stylesheet" type="text/css"
	href="dist/css/style.css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
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
table.fresh-main tr td {
	padding: 5px;
	width: 44px !important;
	text-align: center;
}

table.fresh-main td.fresh span, table.fresh-main td.freshi span {
	font-weight: bold;
	color: #F5821F;
}

.denote {
	color: #F5821F;
}

tr.hover-none, tr.hover-none:hover {
	background: #053C6D !important;
}

.currency-value tr td {
	border: 1px solid #ddd;
	padding: 5px;
}

table.currency-value {
	float: left;
}

.currency-value tr td table tr td {
	padding: 5px;
	width: 85px;
}

/* Custom CSS  */
.currency-value tr td td {
	border-left: none;
	border-bottom: none;
	border-top: none;
}

.currency-value tr>td {
	padding-bottom: 0 !Important;
	padding-top: 0 !important;
}

input[type="button"], .button-design a {
	margin: 4px;
	background: #F5821F;
	border: 2px solid #F5821F !important;
	padding: 7px 15px;
	color: white !Important;
	font-weight: bold;
	border-radius: 5px;
	white-space: nowrap;
}

input[type="button"]:hover, .button-design a:hover {
	background: white;
	color: #F5821F !important
}

.valbox {
	width: 35px;
	text-align: center;
}

.cancelButton {
	float: right;
}

.borNon {
	border: none !important;
	position: relative;
}

.borNon:before {
	content: "";
	position: absolute;
	top: -6px;
	width: 49.5%;
	height: 10px;
	right: 0;
	background: #fff;
}
/* .cancelButton {float: right; width: 62%; margin-top: -91px;} */

/* end Custom CSS */
</style>

<script type="text/javascript">
function doAjaxPostUpdateSAS(str) {
	
	addHeaderJson();
	var newSumVal1=parseInt($('#notes1F'+str).val()|| 0)+parseInt($('#notes1I'+str).val()|| 0);
	var oldSumVal1 = parseInt($('#hidden_notes1F'+str).val()|| 0)+parseInt($('#hidden_notes1I'+str).val()|| 0);
	
	var newSumVal10=parseInt($('#notes10F'+str).val()|| 0)+parseInt($('#notes10I'+str).val()|| 0);
	var oldSumVal10 = parseInt($('#hidden_notes10F'+str).val()|| 0)+parseInt($('#hidden_notes10I'+str).val()|| 0);
	
	var newSumVal100=parseInt($('#notes100F'+str).val()|| 0)+parseInt($('#notes100I'+str).val()|| 0);
	var oldSumVal100 = parseInt($('#hidden_notes100F'+str).val()|| 0)+parseInt($('#hidden_notes100I'+str).val()|| 0);
	var newSumVal1000=parseInt($('#notes1000F'+str).val()|| 0)+parseInt($('#notes1000I'+str).val()|| 0);
	var oldSumVal1000 = parseInt($('#hidden_notes1000F'+str).val()|| 0)+parseInt($('#hidden_notes1000I'+str).val()|| 0);
	
	var newSumVal2=parseInt($('#notes2F'+str).val()|| 0)+parseInt($('#notes2I'+str).val()|| 0);
	var oldSumVal2 = parseInt($('#hidden_notes2F'+str).val()|| 0)+parseInt($('#hidden_notes2I'+str).val()|| 0);
	
	var newSumVal20=parseInt($('#notes20F'+str).val()|| 0)+parseInt($('#notes20I'+str).val()|| 0);
	var oldSumVal20 = parseInt($('#hidden_notes20F'+str).val()|| 0)+parseInt($('#hidden_notes20I'+str).val()|| 0);
	
	var newSumVal5=parseInt($('#notes5F'+str).val()|| 0)+parseInt($('#notes5I'+str).val()|| 0);
	var oldSumVal5 = parseInt($('#hidden_notes5F'+str).val()|| 0)+parseInt($('#hidden_notes5I'+str).val()|| 0);
	
	var newSumVal50=parseInt($('#notes50F'+str).val()|| 0)+parseInt($('#notes50I'+str).val()|| 0);
	var oldSumVal50 = parseInt($('#hidden_notes50F'+str).val()|| 0)+parseInt($('#hidden_notes50I'+str).val()|| 0);
	
	var newSumVal500=parseInt($('#notes500F'+str).val()|| 0)+parseInt($('#notes500I'+str).val()|| 0);
	var oldSumVal500 = parseInt($('#hidden_notes500F'+str).val()|| 0)+parseInt($('#hidden_notes500I'+str).val()|| 0);
	
	
	/* if(newSumVal1 != oldSumVal1){
		alert("Row "+str+" Updated value does not match for field Denomination 1 I | F")
		return false;
	}
	
	else if(newSumVal10 != oldSumVal10){
		alert("Row "+str+" Updated value does not match for field Denomination 10 I | F")
		return false;
	}
	
	else if(newSumVal100 != oldSumVal100){
		alert("Row "+str+" Updated value does not match for field Denomination 100 I | F")
		return false;
	}
	
	else if(newSumVal1000 != oldSumVal1000){
		alert("Row "+str+" Updated value does not match for field Denomination 1000 I | F")
		return false;
	}
	
	else if(newSumVal2 != oldSumVal2){
		alert("Row "+str+" Updated value does not match for field Denomination 2 I | F")
		return false;
	}
	
	else if(newSumVal20 != oldSumVal20){
		alert("Row "+str+" Updated value does not match for field Denomination 20 I | F")
		return false;
	}
	else if(newSumVal5 != oldSumVal5){
		alert("Row "+str+" Updated value does not match for field Denomination 5 I | F")
		return false;
	}
	else if(newSumVal50 != oldSumVal50){
		alert("Row "+str+" Updated value does not match for field Denomination 50 I | F")
		return false;
	}
	else if(newSumVal500 != oldSumVal500){
		alert("Row "+str+" Updated value does not match for field Denomination 500 I | F")
		return false;
	}
	else
		{ */
	  var search = {
				"totalValueOfCoinsRs10" :$('#coins10'+str).val(),
				"totalValueOfCoinsRs5" :$('#coins5'+str).val(),
				"totalValueOfCoinsRs2" :$('#coins2'+str).val(),
				"totalValueOfCoinsRs1" :$('#coins1'+str).val(),
	    "totalValueOfNotesRs1F" :$('#notes1F'+str).val(),
	    "totalValueOfNotesRs1I" :$('#notes1I'+str).val(),
	   	"totalValueOfNotesRs10F" : $('#notes10F'+str).val(),
	    "totalValueOfNotesRs10I" :$('#notes10I'+str).val(),
	    "totalValueOfNotesRs100A" : $('#notes100A'+str).val(),
	    "totalValueOfNotesRs100F" :$('#notes100F'+str).val(),
	    "totalValueOfNotesRs100I" :$('#notes100I'+str).val(),
	    "totalValueOfNotesRs1000A" : $('#notes1000A'+str).val(),
	    "totalValueOfNotesRs1000F" : $('#notes1000F'+str).val(),
	    "totalValueOfNotesRs1000I" :$('#notes1000I'+str).val(),
	    "totalValueOfNotesRs2F" : $('#notes2F'+str).val(),
	    "totalValueOfNotesRs2I" :$('#notes2I'+str).val(),
	    "totalValueOfNotesRs20F" : $('#notes20F'+str).val(),
	    "totalValueOfNotesRs20I" :$('#notes20I'+str).val(),
	    "totalValueOfNotesRs5F" : $('#notes5F'+str).val(),
	    "totalValueOfNotesRs5I" :$('#notes5I'+str).val(),
	    "totalValueOfNotesRs50F" :$('#notes50F'+str).val(),
	    "totalValueOfNotesRs50I" :$('#notes50I'+str).val(),
	    "totalValueOfNotesRs500A" : $('#notes500A'+str).val(),
	    "totalValueOfNotesRs500F" : $('#notes500F'+str).val(),
	    "totalValueOfNotesRs500I" :$('#notes500I'+str).val(),
	    "totalValueOfNotesRs2000A" : $('#notes2000A'+str).val(),
	    "totalValueOfNotesRs2000F" : $('#notes2000F'+str).val(),
	    "totalValueOfNotesRs2000I" :$('#notes2000I'+str).val(),
	    
	    "totalValueOfNotesRs200A" : $('#notes200A'+str).val(),
	    "totalValueOfNotesRs200F" : $('#notes200F'+str).val(),
	    "totalValueOfNotesRs200I" :$('#notes200I'+str).val(),
	    
	    "totalValue":$('#totalIndentedValue'+str).text(),
	    "id":$('#id'+str).val()
		   }
	//alert("s"+search);
	  $.ajax({  
		  type: "POST",
	      contentType : 'application/json; charset=utf-8',
	      dataType : 'json',
	      url: "././updateSAS",
	      data: JSON.stringify(search),
	       success: function(response){
	    	//show success message
	    	alert('record saved.');
	    	window.location='././SAS';
	    	
	    	$('#update'+str).attr('disabled', false);

	    }, 
	    error: function(e){  
	      alert('Error Alert: ' + e);  
	    }  
	  });  
		/* } */
	} 
</script>


<script type="text/javascript">

function doAjaxPostRemoveBranch(str)
{
	var remove =  {
		
		"id":$('#id'+str).val()
	}

	$.ajax({  
		  type: "POST",
	      contentType : 'application/json; charset=utf-8',
	      dataType : 'json',
	      url: "././removeBranch",
	      data: JSON.stringify(remove),
	       success: function(response){
	    	alert('record Removed.');
	    	window.location='././SAS';
	    }, 
	    error: function(e){  
	      alert('Error Alert: ' + e);  
	    }  
	  });  
	
}

</script>

<script type="text/javascript">
function doAjaxPostInsert() {
	addHeaderJson();
	var sasWrapper = {
			"totalFresh":$('#TotalFresh').text(),
			"totalIssuable":$('#TotalIssuable').text(),
			"sasAllocationList":
				[
                {"coinsBag":$('#coins10').text(),"denomination":$('#coinsDenomination10').text(),"cashType":'COINS'},
                {"coinsBag":$('#coins5').text(),"denomination":$('#coinsDenomination5').text(),"cashType":'COINS'},
                {"coinsBag":$('#coins2').text(),"denomination":$('#coinsDenomination2').text(),"cashType":'COINS'},
                {"coinsBag":$('#coins1').text(),"denomination":$('#coinsDenomination1').text(),"cashType":'COINS'},
				{"atmBundle":$('#notes1A').text(),"freshBundle":$('#notes1F').text(),"issuableBundle":$('#notes1I').text(),"denomination":$('#denomination1').text(),"cashType":'NOTES'},
				{"atmBundle":$('#notes10A').text(),"freshBundle":$('#notes10F').text(),"issuableBundle":$('#notes10I').text(),"denomination":$('#denomination10').text(),"cashType":'NOTES'},
				{"atmBundle":$('#notes100A').text(),"freshBundle":$('#notes100F').text(),"issuableBundle":$('#notes100I').text(),"denomination":$('#denomination100').text(),"cashType":'NOTES'},
				{"atmBundle":$('#notes1000A').text(),"freshBundle":$('#notes1000F').text(),"issuableBundle":$('#notes1000I').text(),"denomination":$('#denomination1000').text(),"cashType":'NOTES'},
				{"atmBundle":$('#notes2A').text(),"freshBundle":$('#notes2F').text(),"issuableBundle":$('#notes2I').text(),"denomination":$('#denomination2').text(),"cashType":'NOTES'},
				{"atmBundle":$('#notes20A').text(),"freshBundle":$('#notes20F').text(),"issuableBundle":$('#notes20I').text(),"denomination":$('#denomination20').text(),"cashType":'NOTES'},
				{"atmBundle":$('#notes5A').text(),"issuableBundle":$('#notes5I').text(),"denomination":$('#denomination5').text(),"cashType":'NOTES'},
				{"atmBundle":$('#notes50A').text(),"freshBundle":$('#notes50F').text(),"issuableBundle":$('#notes50I').text(),"denomination":$('#denomination50').text(),"cashType":'NOTES'},
				{"atmBundle":$('#notes500A').text(),"freshBundle":$('#notes500F').text(),"issuableBundle":$('#notes500I').text(),"denomination":$('#denomination500').text(),"cashType":'NOTES'},
				{"atmBundle":$('#notes2000A').text(),"freshBundle":$('#notes2000F').text(),"issuableBundle":$('#notes2000I').text(),"denomination":$('#denomination2000').text(),"cashType":'NOTES'},
				{"atmBundle":$('#notes200A').text(),"freshBundle":$('#notes200F').text(),"issuableBundle":$('#notes200I').text(),"denomination":$('#denomination200').text(),"cashType":'NOTES'}

				]
			}
	console.log("sasWrapper"+sasWrapper);
	  $.ajax({  
		  type: "POST",
	      contentType : 'application/json; charset=utf-8',
	      dataType : 'json',
	    url: "././AssignBinForBulkIndent",
	    data: JSON.stringify(sasWrapper),
	    success: function(response){ 
	    	alert('record submit.');
	    	window.location='././SAS';
	    }, 
	    error: function(e){  
	      alert('Error: ' + e.responseJSON.message);  
	    }  
	  });  
	} 
	
	
	
function valueChanged(dop)
{
    if($('#checkbox'+dop).is(":checked")) {
    	 $("#update"+dop).show();
    	    $("#checkbox"+dop).hide();
    }  
    else
        $("#update"+dop).hide();
}
</script>

<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-37"
	data-genuitec-path="/Currency/src/main/webapp/jsp/SAS.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-37"
		data-genuitec-path="/Currency/src/main/webapp/jsp/SAS.jsp">

		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div style="min-height: 390px;" id="page-wrapper">
			<!-- <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Tables</h1>
                </div>
                /.col-lg-12
            </div> -->
			<!-- /.row -->

			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<ul>
								<li><a href="././UploadSAS"><i
										class="fa fa-table fa-fw"></i> Upload SAS</a></li>
							</ul>
							Cash Indent Summary
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<form:form id="SAS" action="#" method="post"
									modelAttribute="user" autocomplete="off">
									<div align="center" style="color: white; background: green;">
										<b>${successMsg}</b>
									</div>
									<br>
									<table
										class="fresh-main table-striped table-bordered table-hover"
										border="1" cellpadding="0" cellspacing="0" width="100%">
										<tbody align="left">
											<tr class="hover-none" bgcolor="#053C6D">
												<td>&nbsp;</td>
												<td>&nbsp;</td>
												<td>&nbsp;</td>
												<td colspan="4" style="color: #fff;" bgcolor="#F5821F">Coins
													(in Bag)</td>
												<td colspan="10" style="color: #fff; text-align: center;">Notes
													(in Bundle)</td>

												<!-- <td colspan="3" style="color: #fff; text-align: center;">SCD</td> -->
											</tr>

											<tr style="color: #fff;" class="hover-none" bgcolor="#053C6D">
												<td>SolId</td>
												<td>Branch name</td>
												<td>Total Value</td>
												<td style="color: #fff;" bgcolor="#F5821F">10</td>
												<td style="color: #fff;" bgcolor="#F5821F">5</td>
												<td style="color: #fff;" bgcolor="#F5821F">2</td>
												<td style="color: #fff;" bgcolor="#F5821F">1</td>
												<td>2000</td>
												<td>1000</td>
												<td>500</td>
												<td>200</td>
												<td>100</td>
												<td>50</td>
												<td>20</td>
												<td>10</td>
												<td>5</td>
												<td>2</td>
												<td>1</td>
												<td></td>
												<!-- <td>Re.0.50</td>
											<td colspan="2">Value</td> -->
											</tr>


											<tr>
												<td></td>
												<td></td>
												<td>&nbsp;</td>
												<td>&nbsp;</td>
												<td>&nbsp;</td>
												<td>&nbsp;</td>
												<td>&nbsp;</td>
												<td class="remove-padding">
													<table>
														<tbody>
															<tr>
																<td>A</td>
																<td>F</td>
																<td>I</td>
															</tr>
														</tbody>
													</table>
												</td>
												<td class="remove-padding">
													<table>
														<tbody>
															<tr>
																<td>A</td>
																<td>F</td>
																<td>I</td>
															</tr>
														</tbody>
													</table>
												</td>
												<td class="remove-padding">
													<table>
														<tbody>
															<tr>
																<td>A</td>
																<td>F</td>
																<td>I</td>
															</tr>
														</tbody>
													</table>
												</td>

												<td class="remove-padding">
													<table>
														<tbody>
															<tr>
																<td>A</td>
																<td>F</td>
																<td>I</td>
															</tr>
														</tbody>
													</table>
												</td>
												<td class="remove-padding">
													<table>
														<tbody>
															<tr>
																<td>A</td>
																<td>F</td>
																<td>I</td>
															</tr>
														</tbody>
													</table>
												</td>
												<td class="remove-padding">
													<table>
														<tbody>
															<tr>
																<td>F</td>
																<td>I</td>
															</tr>
														</tbody>
													</table>
												</td>
												<td class="remove-padding">
													<table>
														<tbody>
															<tr>
																<td>F</td>
																<td>I</td>
															</tr>
														</tbody>
													</table>
												</td>
												<td class="remove-padding">
													<table>
														<tbody>
															<tr>
																<td>F</td>
																<td>I</td>
															</tr>
														</tbody>
													</table>
												</td>
												<td class="remove-padding">
													<table>
														<tbody>
															<tr>
																<td>F</td>
																<td>I</td>
															</tr>
														</tbody>
													</table>
												</td>
												<td class="remove-padding">
													<table>
														<tbody>
															<tr>
																<td>F</td>
																<td>I</td>
															</tr>
														</tbody>
													</table>
												</td>
												<td class="remove-padding">
													<table>
														<tbody>
															<tr>
																<td>F</td>
																<td>I</td>
															</tr>
														</tbody>
													</table>
												</td>
												<!-- <td>&nbsp;</td>
											<td colspan="2">&nbsp;</td> -->
											</tr>

											<c:set var="coins1" value="${0}" />
											<c:set var="coins2" value="${0}" />
											<c:set var="coins5" value="${0}" />
											<c:set var="coins10" value="${0}" />

											<c:set var="notes1" value="${0}" />
											<c:set var="notes2" value="${0}" />
											<c:set var="notes5" value="${0}" />
											<c:set var="notes10" value="${0}" />
											<c:set var="notes20" value="${0}" />
											<c:set var="notes50" value="${0}" />
											<c:set var="notes100" value="${0}" />
											<c:set var="notes500" value="${0}" />
											<c:set var="notes1000" value="${0}" />
											<c:set var="notes10F" value="${0}" />
											<c:set var="notes10I" value="${0}" />
											<c:set var="notes100F" value="${0}" />
											<c:set var="notes100I" value="${0}" />
											<c:set var="notes1000F" value="${0}" />
											<c:set var="notes1000I" value="${0}" />
											<c:set var="notes2F" value="${0}" />
											<c:set var="notes2I" value="${0}" />
											<c:set var="notes20F" value="${0}" />
											<c:set var="notes20I" value="${0}" />
											<c:set var="notes5F" value="${0}" />
											<c:set var="notes5I" value="${0}" />
											<c:set var="notes50F" value="${0}" />
											<c:set var="notes50I" value="${0}" />
											<c:set var="notes500F" value="${0}" />
											<c:set var="notes500I" value="${0}" />

											<c:set var="notes2000F" value="${0}" />
											<c:set var="notes2000I" value="${0}" />

											<c:set var="notes200F" value="${0}" />
											<c:set var="notes200I" value="${0}" />

											<c:set var="TotalFresh" value="${0}" />
											<c:set var="TotalIssuable" value="${0}" />

											<c:set var="TotalBundlePerBranch" value="${0}" />

											<c:forEach var="row" items="${records}">
												<%-- <c:set var="str" value="${row.branch}" />
											<c:set var="splittedString" value="${fn:split(str, '_')}" />--%>
												<c:set var="enableDisableVar" value="${ row.enabledisable}" />

												<tr>
													<td>${row.solID}</td>
													<td>${row.branch}</td>
													<%--  <td id="totalIndentedValue${row.id}">${row.totalValue}</td> --%>
													<td id="totalIndentedValue${row.id}">
														${row.totalValue} <%-- ${((row.totalValueOfCoinsRs1)*(1*2500))
													+((row.totalValueOfCoinsRs10)*(10*2000))
													+((row.totalValueOfCoinsRs2)*(2*2500))
													+((row.totalValueOfCoinsRs5)*(5*2500))
													+((row.totalValueOfNotesRs2000A+row.totalValueOfNotesRs2000F+row.totalValueOfNotesRs2000I)*(2000*1000))
													+((row.totalValueOfNotesRs1000A+row.totalValueOfNotesRs1000F+row.totalValueOfNotesRs1000I)*(1000*1000))
													+((row.totalValueOfNotesRs500A+row.totalValueOfNotesRs500F+row.totalValueOfNotesRs500I)*(500*1000))
													+((row.totalValueOfNotesRs200A+row.totalValueOfNotesRs200F+row.totalValueOfNotesRs200I)*(200*1000))
													+((row.totalValueOfNotesRs100A+row.totalValueOfNotesRs100F+row.totalValueOfNotesRs100I)*(100*1000))
													+((row.totalValueOfNotesRs50F+row.totalValueOfNotesRs50I)*(50*1000))
													+((row.totalValueOfNotesRs20F+row.totalValueOfNotesRs20I)*(20*1000))
													+((row.totalValueOfNotesRs10F+row.totalValueOfNotesRs10I)*(10*1000))
													+((row.totalValueOfNotesRs5F+row.totalValueOfNotesRs5I)*(5*1000))
													+((row.totalValueOfNotesRs2F+row.totalValueOfNotesRs2I)*(2*1000))
													+((row.totalValueOfNotesRs1F+row.totalValueOfNotesRs1I)*(1*1000))} --%>
													</td>
													<td><input type="text" class="valbox"
														value="${row.totalValueOfCoinsRs10}" id="coins10${row.id}"></td>
													<td><input type="text" class="valbox"
														value="${row.totalValueOfCoinsRs5}" id="coins5${row.id}"></td>
													<td><input type="text" class="valbox"
														value="${row.totalValueOfCoinsRs2}" id="coins2${row.id}"></td>
													<td><input type="text" class="valbox"
														value="${row.totalValueOfCoinsRs1}" id="coins1${row.id}"></td>

													<td class="remove-padding">
														<table>
															<tbody>
																<tr>
																	<td><input type="text" id="notes2000A${row.id}"
																		value="${row.totalValueOfNotesRs2000A}"
																		style="width: 20px;"></td>
																	<td><input type="text" id="notes2000F${row.id}"
																		value="${row.totalValueOfNotesRs2000F}"
																		style="width: 20px;"></td>
																	<td><input type="text" id="notes2000I${row.id}"
																		value="${row.totalValueOfNotesRs2000I}"
																		style="width: 20px;"></td>
																</tr>
															</tbody>
														</table>
													</td>

													<td class="remove-padding">
														<table>
															<tbody>
																<tr>
																	<input type="hidden" value="${row.id}" id="id${row.id}">

																	<td><input type="text" id="notes1000A${row.id}"
																		value="${row.totalValueOfNotesRs1000A}"
																		style="width: 20px;"></td>

																	<td><input type="text" id="notes1000F${row.id}"
																		value="${row.totalValueOfNotesRs1000F}"
																		style="width: 20px;"></td>
																	<td><input type="text" id="notes1000I${row.id}"
																		value="${row.totalValueOfNotesRs1000I}"
																		style="width: 20px;"></td>
																</tr>
															</tbody>
														</table>
													</td>

													<td class="remove-padding">
														<table>
															<tbody>
																<tr>

																	<td><input type="text" id="notes500A${row.id}"
																		value="${row.totalValueOfNotesRs500A}"
																		style="width: 20px;"></td>
																	<td><input type="text" id="notes500F${row.id}"
																		value="${row.totalValueOfNotesRs500F}"
																		style="width: 20px;"></td>
																	<td><input type="text" id="notes500I${row.id}"
																		value="${row.totalValueOfNotesRs500I}"
																		style="width: 20px;"></td>
																</tr>
															</tbody>
														</table>
													</td>


													<td class="remove-padding">
														<table>
															<tbody>
																<tr>

																	<td><input type="text" id="notes200A${row.id}"
																		value="${row.totalValueOfNotesRs200A}"
																		style="width: 20px;"></td>
																	<td><input type="text" id="notes200F${row.id}"
																		value="${row.totalValueOfNotesRs200F}"
																		style="width: 20px;"></td>
																	<td><input type="text" id="notes200I${row.id}"
																		value="${row.totalValueOfNotesRs200I}"
																		style="width: 20px;"></td>
																</tr>
															</tbody>
														</table>
													</td>

													<td class="remove-padding">
														<table>
															<tbody>
																<tr>
																	<td><input type="text" id="notes100A${row.id}"
																		value="${row.totalValueOfNotesRs100A}"
																		style="width: 20px;"></td>

																	<td><input type="text" id="notes100F${row.id}"
																		value="${row.totalValueOfNotesRs100F}"
																		style="width: 20px;"></td>
																	<td><input type="text" id="notes100I${row.id}"
																		value="${row.totalValueOfNotesRs100I}"
																		style="width: 20px;"></td>
																</tr>
															</tbody>
														</table>
													</td>

													<td class="remove-padding">
														<table>
															<tbody>
																<tr>
																	<td><input type="text" id="notes50F${row.id}"
																		value="${row.totalValueOfNotesRs50F}"
																		style="width: 20px;"></td>
																	<td><input type="text" id="notes50I${row.id}"
																		value="${row.totalValueOfNotesRs50I}"
																		style="width: 20px;"></td>
																</tr>
															</tbody>
														</table>
													</td>

													<td class="remove-padding">
														<table>
															<tbody>
																<tr>
																	<td><input type="text" id="notes20F${row.id}"
																		value="${row.totalValueOfNotesRs20F}"
																		style="width: 20px;"></td>
																	<td><input type="text" id="notes20I${row.id}"
																		value="${row.totalValueOfNotesRs20I}"
																		style="width: 20px;"></td>
																</tr>
															</tbody>
														</table>
													</td>

													<td class="remove-padding">
														<table>
															<tbody>
																<tr>
																	<td><input type="text" id="notes10F${row.id}"
																		value="${row.totalValueOfNotesRs10F}"
																		style="width: 20px;"></td>
																	<td><input type="text" id="notes10I${row.id}"
																		value="${row.totalValueOfNotesRs10I}"
																		style="width: 20px;"></td>
																</tr>
															</tbody>
														</table>
													</td>

													<td class="remove-padding">
														<table>
															<tbody>
																<tr>
																	<td><input type="text" id="notes5F${row.id}"
																		value="${row.totalValueOfNotesRs5F}"
																		style="width: 20px;"></td>
																	<td><input type="text" id="notes5I${row.id}"
																		value="${row.totalValueOfNotesRs5I}"
																		style="width: 20px;"></td>
																</tr>
															</tbody>
														</table>
													</td>

													<td class="remove-padding">
														<table>
															<tbody>
																<tr>
																	<td><input type="text" id="notes2F${row.id}"
																		value="${row.totalValueOfNotesRs2F}"
																		style="width: 20px;"></td>
																	<td><input type="text" id="notes2I${row.id}"
																		value="${row.totalValueOfNotesRs2I}"
																		style="width: 20px;"></td>
																</tr>
															</tbody>
														</table>
													</td>

													<td class="remove-padding">
														<table>
															<tbody>
																<tr>
																	<td><input type="text" id="notes1F${row.id}"
																		value="${row.totalValueOfNotesRs1F}"
																		style="width: 20px;"></td>
																	<td><input type="text" id="notes1I${row.id}"
																		value="${row.totalValueOfNotesRs1I}"
																		style="width: 20px;"></td>
																</tr>
															</tbody>
														</table>
													</td>

													<td><c:if test="${enableDisableVar==1 }">
															<sec:authorize access="hasAnyRole('UPDATE_SAS')">
																<input type="button" id="update${row.id}" value="Update"
																	onclick="doAjaxPostUpdateSAS(${row.id})">
															</sec:authorize>
														</c:if> <c:if test="${enableDisableVar==0 }">
															<sec:authorize access="hasAnyRole('UPDATE_SAS')">
																<input type="button" id="update${row.id}" value="Update"
																	onclick="doAjaxPostUpdateSAS(${row.id})"
																	style="display: none;">
																<input type="checkbox" name="${row.id}"
																	id="checkbox${row.id}" value="1"
																	onchange="valueChanged(${row.id})" />
															</sec:authorize>
														</c:if> <input type="button" id="removeBranchRecord${row.id}"
														value="Remove" onclick="doAjaxPostRemoveBranch(${row.id})">

													</td>

													<!-- <td>
												
												<label><input type="checkbox" class="agree">Enable</label>
												</td> -->

													<!-- <td colspan="2">Edit</td> -->

													<c:set var="coins10"
														value="${coins10+ row.totalValueOfCoinsRs10}" />

													<c:set var="coins5"
														value="${coins5+ row.totalValueOfCoinsRs5}" />

													<c:set var="coins2"
														value="${coins2+ row.totalValueOfCoinsRs2}" />

													<c:set var="coins1"
														value="${coins1+ row.totalValueOfCoinsRs1}" />

													<c:set var="notes1"
														value="${notes1+ row.totalValueOfNotesRs1I+row.totalValueOfNotesRs1F}" />
													<c:set var="notes2"
														value="${notes2+ row.totalValueOfNotesRs2I+row.totalValueOfNotesRs2F}" />
													<c:set var="notes5"
														value="${notes5+ row.totalValueOfNotesRs5I+row.totalValueOfNotesRs5F}" />
													<c:set var="notes10"
														value="${notes10+row.totalValueOfNotesRs10I+row.totalValueOfNotesRs10F}" />
													<c:set var="notes20"
														value="${notes20+ row.totalValueOfNotesRs20I+row.totalValueOfNotesRs20F}" />
													<c:set var="notes50"
														value="${notes50+row.totalValueOfNotesRs50I+row.totalValueOfNotesRs50F}" />
													<c:set var="notes100"
														value="${notes100+ row.totalValueOfNotesRs100A+row.totalValueOfNotesRs100I+row.totalValueOfNotesRs100F}" />
													<c:set var="notes500"
														value="${notes500+ row.totalValueOfNotesRs500A+row.totalValueOfNotesRs500I+row.totalValueOfNotesRs500F}" />

													<c:set var="notes200"
														value="${notes200+ row.totalValueOfNotesRs200A+row.totalValueOfNotesRs200I+row.totalValueOfNotesRs200F}" />

													<c:set var="notes1000"
														value="${notes1000+row.totalValueOfNotesRs1000A+ row.totalValueOfNotesRs1000I+row.totalValueOfNotesRs1000F}" />

													<c:set var="notes2000"
														value="${notes2000+row.totalValueOfNotesRs2000A+ row.totalValueOfNotesRs2000I+row.totalValueOfNotesRs2000F}" />


													<c:set var="notes1F"
														value="${notes1F+row.totalValueOfNotesRs1F}" />

													<c:set var="notes1I"
														value="${notes1I+row.totalValueOfNotesRs1I}" />

													<c:set var="notes10F"
														value="${notes10F+row.totalValueOfNotesRs10F}" />

													<c:set var="notes10I"
														value="${notes10I+row.totalValueOfNotesRs10I}" />

													<c:set var="notes100A"
														value="${notes100A+row.totalValueOfNotesRs100A}" />

													<c:set var="notes100F"
														value="${notes100F+row.totalValueOfNotesRs100F}" />

													<c:set var="notes100I"
														value="${notes100I+row.totalValueOfNotesRs100I}" />

													<c:set var="notes1000A"
														value="${notes1000A+row.totalValueOfNotesRs1000A}" />

													<c:set var="notes1000F"
														value="${notes1000F+row.totalValueOfNotesRs1000F}" />

													<c:set var="notes1000I"
														value="${notes1000I+row.totalValueOfNotesRs1000I}" />

													<c:set var="notes2000A"
														value="${notes2000A+row.totalValueOfNotesRs2000A}" />

													<c:set var="notes2000F"
														value="${notes2000F+row.totalValueOfNotesRs2000F}" />

													<c:set var="notes2000I"
														value="${notes2000I+row.totalValueOfNotesRs2000I}" />

													<c:set var="notes2F"
														value="${notes2F+row.totalValueOfNotesRs2F}" />

													<c:set var="notes2I"
														value="${notes2I+row.totalValueOfNotesRs2I}" />

													<c:set var="notes20F"
														value="${notes20F+row.totalValueOfNotesRs20F}" />

													<c:set var="notes20I"
														value="${notes20I+row.totalValueOfNotesRs20I}" />

													<c:set var="notes5F"
														value="${notes5F+row.totalValueOfNotesRs5F}" />

													<c:set var="notes5I"
														value="${notes5I+row.totalValueOfNotesRs5I}" />


													<c:set var="notes50F"
														value="${notes50F+row.totalValueOfNotesRs50F}" />

													<c:set var="notes50I"
														value="${notes50I+row.totalValueOfNotesRs50I}" />


													<c:set var="notes500A"
														value="${notes500A+row.totalValueOfNotesRs500A}" />

													<c:set var="notes500F"
														value="${notes500F+row.totalValueOfNotesRs500F}" />

													<c:set var="notes500I"
														value="${notes500I+row.totalValueOfNotesRs500I}" />


													<c:set var="notes200A"
														value="${notes200A+row.totalValueOfNotesRs200A}" />

													<c:set var="notes200F"
														value="${notes200F+row.totalValueOfNotesRs200F}" />

													<c:set var="notes200I"
														value="${notes200I+row.totalValueOfNotesRs200I}" />

												</tr>
											</c:forEach>

											<c:set var="TotalATM"
												value="${notes100A+notes1000A+notes500A+notes200A+notes2000A}" />

											<c:set var="TotalFresh"
												value="${notes1F+notes10F+notes100F+notes1000F+notes2F+notes20F+notes5F+notes50F+notes500F+notes200F+notes2000F}" />

											<c:set var="TotalIssuable"
												value="${notes1I+notes10I+notes100I+notes1000I+notes2I+notes20I+notes5I+notes50I+notes500I+notes200I+notes2000I}" />

											<tr>
												<td>TOTAL</td>
												<td>&nbsp;</td>
												<td>&nbsp;</td>

												<td>${coins10}</td>
												<td>${coins5}</td>
												<td>${coins2}</td>
												<td>${coins1}</td>
												<td>${notes2000}</td>
												<td>${notes1000}</td>
												<td>${notes500}</td>
												<td>${notes200}</td>
												<td>${notes100}</td>
												<td>${notes50}</td>
												<td>${notes20}</td>
												<td>${notes10}</td>
												<td>${notes5}</td>
												<td>${notes2}</td>
												<td>${notes1}</td>
											</tr>
										</tbody>
									</table>
								</form:form>
							</div>
							<br>
							<div class="pull-right">
								<span class="denote"><strong>A</strong> -ATM<br></span> <span
									class="denote"><strong>F</strong> -Fresh<br></span> <span
									class="denote"><strong>I</strong> -Issuable</span>
							</div>

						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
					<div>

						<table class="currency-value" border="0" cellpadding="0"
							cellspacing="0" width="50%" style="margin: 0 15px 0 0;">

							<h4>ORV Balance</h4>


							<tbody>
								<tr style="color: #fff;" bgcolor="#053C6D">
									<td align="center">Denomination</td>
									<td align="center">ATM</td>
									<td align="center">Fresh</td>
									<td align="center">Issuable</td>
									<td align="center">Total Bundles</td>
								</tr>

								<tr>
									<td id="denomination2000">2000</td>
									<td id="notes2000A">${notes2000A}</td>
									<td id="notes2000F">${notes2000F}</td>
									<td id="notes2000I">${notes2000I}</td>
									<td>${notes2000}</td>
								</tr>

								<tr>
									<td id="denomination1000">1000</td>
									<td id="notes1000A">${notes1000A}</td>
									<td id="notes1000F">${notes1000F}</td>
									<td id="notes1000I">${notes1000I}</td>
									<td>${notes1000}</td>
								</tr>

								<tr>
									<td id="denomination500">500</td>
									<td id="notes500A">${notes500A}</td>
									<td id="notes500F">${notes500F}</td>
									<td id="notes500I">${notes500I}</td>
									<td>${notes500}</td>
								</tr>

								<tr>
									<td id="denomination200">200</td>
									<td id="notes200A">${notes200A}</td>
									<td id="notes200F">${notes200F}</td>
									<td id="notes200I">${notes200I}</td>
									<td>${notes200}</td>
								</tr>

								<tr>
									<td id="denomination100">100</td>
									<td id="notes100A">${notes100A}</td>
									<td id="notes100F">${notes100F}</td>
									<td id="notes100I">${notes100I}</td>
									<td>${notes100}</td>
								</tr>

								<tr>
									<td id="denomination50">50</td>
									<td>0.00</td>
									<td id="notes50F">${notes50F}</td>
									<td id="notes50I">${notes50I}</td>
									<td>${notes50}</td>
								</tr>

								<tr>
									<td id="denomination20">20</td>
									<td>0.00</td>
									<td id="notes20F">${notes20F}</td>
									<td id="notes20I">${notes20I}</td>
									<td>${notes20}</td>
								</tr>

								<tr>
									<td id="denomination10">10</td>
									<td>0.00</td>
									<td id="notes10F">${notes10F}</td>
									<td id="notes10I">${notes10I}</td>
									<td>${notes10}</td>
								</tr>

								<tr>
									<td id="denomination5">5</td>
									<td>0.00</td>
									<td id="notes5F">${notes5F}</td>
									<td id="notes5I">${notes5I}</td>
									<td>${notes5}</td>
								</tr>

								<tr>
									<td id="denomination2">2</td>
									<td>0.00</td>
									<td id="notes2F">${notes2F}</td>
									<td id="notes2I">${notes2I}</td>
									<td>${notes2}</td>
								</tr>

								<tr>
									<td id="denomination1">1</td>
									<td>0.00</td>
									<td id="notes1F">${notes1F}</td>
									<td id="notes1I">${notes1I}</td>
									<td>${notes1}</td>
								</tr>

								<tr>
									<td>Total</td>
									<td id="TotalATM">${TotalATM}</td>
									<td id="TotalFresh">${TotalFresh}</td>
									<td id="TotalIssuable">${TotalIssuable}</td>
									<td>${TotalATM+TotalFresh+TotalIssuable}</td>
								</tr>

								<!--Coins  -->
								<tr style="color: #fff;" bgcolor="#053C6D">
									<td align="center">Coins Denomination</td>
									<td align="center">Bags</td>
									<td align="center">Total Bags</td>
								</tr>

								<tr>
									<td id="coinsDenomination10">10</td>
									<td style="padding: 0;"><table border="0" cellpadding="0"
											cellspacing="0" width="100%">
											<tbody>
												<tr>
													<td id="coins10">${coins10}</td>
												</tr>
											</tbody>
										</table></td>
									<td>${coins10}</td>
								</tr>
								<tr>
									<td id="coinsDenomination5">5</td>
									<td style="padding: 0;"><table border="0" cellpadding="0"
											cellspacing="0" width="100%">
											<tbody>
												<tr>
													<td id="coins5">${coins5}</td>
												</tr>
											</tbody>
										</table></td>
									<td>${coins5}</td>
								</tr>

								<tr>
									<td id="coinsDenomination2">2</td>
									<td style="padding: 0;"><table border="0" cellpadding="0"
											cellspacing="0" width="100%">
											<tbody>
												<tr>
													<td id="coins2">${coins2}</td>
												</tr>
											</tbody>
										</table></td>
									<td>${coins2}</td>
								</tr>


								<tr>
									<td id="coinsDenomination1">1</td>
									<td style="padding: 0;"><table border="0" cellpadding="0"
											cellspacing="0" width="100%">
											<tbody>
												<tr>
													<td id="coins1">${coins1}</td>
												</tr>
											</tbody>
										</table></td>
									<td>${coins1}</td>
								</tr>

							</tbody>
							<tr>
								<td><sec:authorize access="hasAnyRole('ADD_SAS')">
										<input type="button" value="Generate ORV"
											onclick="doAjaxPostInsert()">
									</sec:authorize></td>
								<td colspan="5" class="borNon">
									<div class="cancelButton">
										<sec:authorize access="hasAnyRole('UPDATE_SAS')">
											<div class="button-design">
												<%
										List obj=(List)request.getAttribute("autoIdList");
										Iterator itr = obj.iterator(); 
										String sasList="";
while(itr.hasNext()) 
{ 
/* String key = itr.next();  */
//System.out.print("key"+itr.next()); 
sasList=itr.next()+":"+sasList;
//display logic. 
} 
 %>
												<a href="cancelSAS?sasIdList=<%= sasList%>"
													onclick="return confirm('Are you sure you want to cancel this SAS File?');">Cancel
													ORV</a>
											</div>
										</sec:authorize>
									</div>
								</td>
							</tr>
							<jsp:include page="binSummaryForSAS.jsp" />

						</table>
					</div>
				</div>
				<!-- /.col-lg-12 -->
			</div>

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

	<!-- DataTables JavaScript -->
	<script
		src="./resources/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
	<script
		src="./resources/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="./resources/dist/js/sb-admin-2.js"></script>

	<!-- Page-Level Demo Scripts - Tables - Use for reference -->
	<script>
	$(document).ready(function() {
		$('#dataTables-example').DataTable({
			responsive : true
		});
	});
</script>

	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>
</html>