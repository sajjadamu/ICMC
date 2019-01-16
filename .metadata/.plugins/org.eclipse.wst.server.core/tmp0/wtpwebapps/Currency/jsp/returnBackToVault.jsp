<!DOCTYPE html>
<html lang="en">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.chest.currency.enums.CashSource"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="./resources/logo/yes_favicon.ico"
	type="image/x-icon">
<title> Machine Allocation</title>

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

<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>

<script type="text/javascript">

function doAjaxPostForSaveAndPrintBranch(str,source,denom) {  
	addHeaderJson();
	var fromProcessingRoom = true;  
	var branchReceipt = {
		"denomination":$('#denomination'+str).text(),
		"bundle":$('#pendingBundle'+str).text(),
		"cashSource":source,
		"binCategoryType":$('input[name=group'+denom+']:checked').val(),
		"fromProcessingRoom":fromProcessingRoom,
		"processedOrUnprocessed":'UNPROCESS',
		/* "solId":$('#solId').val(),
		"bin":$('#binNumber'+str).val(),
		"total":$('#Total'+str).val(),
		"branch":$('#branch').val(),
		"srNumber":$('#srNumber').val(), */
		
	  }
	  
	  $.ajax({  
	    type: "POST", 
	    contentType : 'application/json; charset=utf-8',
	    dataType : 'json',
	    url: "././QRPath",
	    data: JSON.stringify(branchReceipt),
	    success: function(response){
	    	alert("Success..")
	    	window.location='././returnBackToVault';
	    }, 
	    error: function(e){  
	      //alert('Error: ' + e);  
	    	alert('Error: ' + e.responseJSON.message);
	    }  
	 }); 
} 

function doAjaxPostForSaveAndPrintDSB(str,source,denom) {  
	addHeaderJson();  
	var fromProcessingRoom = true;
	  var dsb = {
		"denomination":$('#denomination'+str).text(),
		"bundle":$('#pendingBundle'+str).text(),
		"cashSource":source,
		"processingOrVault":$('input[name=group'+denom+']:checked').val(),
		"fromProcessingRoom":fromProcessingRoom,
		
		/* "name":$('#name').val(),
		"accountNumber":$('#accountNumber').val(),
		"bin":$('#binNumber'+str).val(),
		"total":$('#Total'+str).val(), */
	  }
	  
	  $.ajax({  
	    type: "POST", 
	    contentType : 'application/json; charset=utf-8',
	    dataType : 'json',
	    url: "././DSBQRPath",
	    data: JSON.stringify(dsb),
	    success: function(response){
	    	alert("Success..")
	    	window.location='././returnBackToVault';
	    }, 
	    error: function(e){  
	     // alert('Error: ' + e);
	      alert('Error: ' + e.responseJSON.message);
	    }  
	 }); 
} 

function doAjaxPostForSaveAndPrintOtherBank(str,source,denom) {  
	addHeaderJson(); 
	var fromProcessingRoom = true;
	var bankReceipt = {
		"denomination":$('#denomination'+str).text(),
		"bundle":$('#pendingBundle'+str).text(),
		"cashSource":source,
		"binCategoryType":$('input[name=group'+denom+']:checked').val(),
		"fromProcessingRoom":fromProcessingRoom,
		
		/* "bankName":$('#bankName').val(),
		"solId":$('#solId').val(),
		"branch":$('#branch').val(),
		"category":$('#Category'+str).val(),
		"binNumber":$('#binNumber'+str).val(),
		"total":$('#Total'+str).val(),
		"rtgsUTRNo":$('#rtgsUTRNo').val(),
		"binCategoryType":$('#main'+str).find('input[type=radio]:checked').val(), */
	  }
	  
	  $.ajax({  
	    type: "POST", 
	    contentType : 'application/json; charset=utf-8',
	    dataType : 'json',
	    url: "././bankReceipt",
	    data: JSON.stringify(bankReceipt),
	    success: function(response){
	    	alert("Success..")
	    	window.location='././returnBackToVault';
	    }, 
	    error: function(e){  
	     // alert('Error: ' + e); 
	    	alert('Error: ' + e.responseJSON.message);
	    }  
	 }); 
} 

function doAjaxPostForSaveAndPrintDiversion(str,source,denom) {  
	addHeaderJson();
	var fromProcessingRoom = true;  
	var dirv = {
		"denomination":$('#denomination'+str).text(),
		"bundle":$('#pendingBundle'+str).text(),
		"cashSource":source,
		"fromProcessingRoom":fromProcessingRoom,
		
		/* "orderDate":$('#orderDate').val(),
		"rbiOrderNo":$('#rbiOrderNo').val(),
		"expiryDate":$('#expiryDate').val(),
		"bankName":$('#bankName').val(),
		"approvedCC":$('#approvedCC').val(),
		"location":$('#location').val(),
		"denomination":$('#Denomination'+str).val(),
		"bundle":$('#Bundle'+str).val(),
		"category":$('#Category'+str).val(),
		"binNumber":$('#binNumber'+str).val(),
		"total":$('#Total'+str).val(),
		"binCategoryType":$('#main'+str).find('input[type=radio]:checked').val(), */
		
	  }
	  
	  $.ajax({  
	    type: "POST", 
	    contentType : 'application/json; charset=utf-8',
	    dataType : 'json',
	    url: "././dirvQRPath",
	    data: JSON.stringify(dirv),
	    success: function(response){
	    	alert("Success..")
	    	window.location='././returnBackToVault';
	    }, 
	    error: function(e){  
	      //alert('Error: ' + e);
	    	alert('Error: ' + e.responseJSON.message);
	    }  
	 }); 
} 

</script>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-187"
	data-genuitec-path="/Currency/src/main/webapp/jsp/returnBackToVault.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-187"
		data-genuitec-path="/Currency/src/main/webapp/jsp/returnBackToVault.jsp">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">
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
						<div class="panel-heading">Return Back To Vault</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div>
								<form id="showAll">
									<table align="center">
										<tr>
											<td><font size="4"><u>BRANCH</u></font></td>
										</tr>
									</table>
									<table class="table table-striped table-bordered table-hover"
										id="tableValue">
										<thead>
											<tr>
												<!-- <th></th> -->
												<th>Denomination</th>
												<th>Total Bundle</th>
												<th>Bin Category Type</th>
												<th>Action</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="row" items="${branchBundleReturned}">
												<c:set var="count" value="${count+1}" scope="page" />
												<tr>
													<%-- <td><input type="hidden" value="${count}" id="id${count}<%=CashSource.BRANCH%>"> --%>
													<td id="denomination${count}<%=CashSource.BRANCH%>">${row.denomination}</td>
													<td id="pendingBundle${count}<%=CashSource.BRANCH%>">${row.pendingBundle}</td>

													<td id="binCategoryType${count}<%=CashSource.BRANCH%>">
														<input type="radio" id="radio"
														name="group${row.denomination}" value="BIN"
														checked="checked">BIN <input type="radio"
														id="radio" name="group${row.denomination}" value="BOX">BOX
													</td>

													<td><input type="button" value="ReturnBackToVault"
														onclick="doAjaxPostForSaveAndPrintBranch('${count}<%=CashSource.BRANCH%>','<%=CashSource.BRANCH%>','${row.denomination}')"></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>


									<table align="center">
										<tr>
											<td><font size="4"><u>DSB</u></font></td>
										</tr>
									</table>

									<table class="table table-striped table-bordered table-hover"
										id="tableValue">
										<thead>
											<tr>
												<!-- <th></th> -->
												<th>Denomination</th>
												<th>Total Bundle</th>
												<th>Bin Category Type</th>
												<th>Action</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="row" items="${dsbBundleReturned}">
												<c:set var="count" value="${count+1}" scope="page" />
												<tr>
													<%-- <td><input type="hidden" value="${count}" id="id${count}<%=CashSource.DSB%>"> --%>
													<td id="denomination${count}<%=CashSource.DSB%>">${row.denomination}</td>
													<td id="pendingBundle${count}<%=CashSource.DSB%>">${row.pendingBundle}</td>
													<td id="processingOrVault${count}<%=CashSource.DSB%>">
														<input type="radio" id="radio"
														name="group${row.denomination}" value="BIN"
														checked="checked">BIN <input type="radio"
														id="radio" name="group${row.denomination}" value="BOX">BOX
													</td>
													<td><input type="button" value="ReturnBackToVault"
														onclick="doAjaxPostForSaveAndPrintDSB('${count}<%=CashSource.DSB%>','<%=CashSource.DSB%>','${row.denomination}')"></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>

									<table align="center">
										<tr>
											<td><font size="4"><u>OTHER BANK</u></font></td>
										</tr>
									</table>

									<table class="table table-striped table-bordered table-hover"
										id="tableValue">
										<thead>
											<tr>
												<!-- <th></th> -->
												<th>Denomination</th>
												<th>Total Bundle</th>
												<th>Bin Category Type</th>
												<th>Action</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="row" items="${otherBankBundleReturned}">
												<c:set var="count" value="${count+1}" scope="page" />
												<tr>
													<%-- <td><input type="hidden" value="${count}" id="id${count}<%=CashSource.OTHERBANK%>"> --%>
													<td id="denomination${count}<%=CashSource.OTHERBANK%>">${row.denomination}</td>
													<td id="pendingBundle${count}<%=CashSource.OTHERBANK%>">${row.pendingBundle}</td>
													<td id="binCategoryType${count}<%=CashSource.OTHERBANK%>">
														<input type="radio" id="radio"
														name="group${row.denomination}" value="BIN"
														checked="checked">BIN <input type="radio"
														id="radio" name="group${row.denomination}" value="BOX">BOX
													</td>
													<td><input type="button" value="ReturnBackToVault"
														onclick="doAjaxPostForSaveAndPrintOtherBank('${count}<%=CashSource.OTHERBANK%>','<%=CashSource.OTHERBANK%>','${row.denomination}')"></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>



									<table align="center">
										<tr>
											<td><font size="4"><u>DIVERSION</u></font></td>
										</tr>
									</table>

									<table class="table table-striped table-bordered table-hover"
										id="tableValue">
										<thead>
											<tr>
												<!-- <th></th> -->
												<th>Denomination</th>
												<th>Total Bundle</th>
												<th>Bin Category Type</th>
												<th>Action</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="row" items="${diversionBundleReturned}">
												<c:set var="count" value="${count+1}" scope="page" />
												<tr>
													<%-- <td><input type="hidden" value="${count}" id="id${count}<%=CashSource.DIVERSION%>"> --%>
													<td id="denomination${count}<%=CashSource.DIVERSION%>">${row.denomination}</td>
													<td id="pendingBundle${count}<%=CashSource.DIVERSION%>">${row.pendingBundle}</td>
													<td id="binCategoryType${count}<%=CashSource.DIVERSION%>">
														<input type="radio" id="radio"
														name="group${row.denomination}" value="BIN"
														checked="checked">BIN <input type="radio"
														id="radio" name="group${row.denomination}" value="BOX">BOX
													</td>
													<td><input type="button" value="ReturnBackToVault"
														onclick="doAjaxPostForSaveAndPrintDiversion('${count}<%=CashSource.DIVERSION%>','<%=CashSource.DIVERSION%>','${row.denomination}')"></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>

								</form>
							</div>

						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
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

	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>