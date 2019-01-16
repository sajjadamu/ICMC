<!DOCTYPE html>
<%@page import="com.mysema.query.Tuple"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<html lang="en">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<link href="./resources/css/calendar.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.datetimepicker.css" />
<title> Suspense Cash Register</title>

<style type="text/css">
.dispencery_wrap {
	font-size: 12px;
}

.dispencery_wrap table tr th {
	border: 1px solid #000;
	padding: 3px;
}

.dispencery_wrap   table, td {
	border: 1px solid black;
	text-align: center;
	padding: 5px;
}

td, th {
	display: table-cell;
	vertical-align: inherit;
}

.dispencery_wrap tr td:nth-child(17) {
	text-align: left;
}

.dispencery_wrap tr td:nth-child(18) {
	text-align: left;
}

.dispencery_wrap tr:nth-child(1) {
	background: #bdf7f7;
}

.dispencery_wrap tr:nth-child(2) {
	background: #bdf7f7;
}

.dispencery_wrap tr:nth-child(3) {
	background: #bdf7f7;
}

.dispencery_wrap tr:nth-child(4) {
	background: #bdf7f7;
}

.dispencery_wrap tr:nth-child(48) {
	background: #f7f6bd;
}

.dispencery_wrap tr:nth-child(49) {
	background: #f7f6bd;
}

.dispencery_wrap tr:nth-child(50) {
	background: #f7f6bd;
}

.dispencery_wrap tr:nth-child(51) {
	background: #f7f6bd;
}

.dispencery_wrap tr:nth-child(52) {
	background: #f7f6bd;
}

.dispencery_wrap {
	font-size: 12px;
	overflow-x: scroll;
	width: 100%;
	float: left;
}

.fakeCls {
	font-size: 14px;
	text-align: center;
	font-weight: bold;
}

.dscTopCls {
	font-size: 14px;
	text-align: left;
	font-weight: bold;
}

tr.ttlDisc {
	background: #eee;
}

.ttlDisc td {
	font-size: 14px;
	padding: 5px;
	font-weight: bold;
}

.ttlDiscBtm td {
	text-align: left;
	border: none;
	padding: 6px;
}

tr.ttlDiscBtm td:nth-child(2), tr.ttlDiscBtm td:nth-child(3) {
	background: #f1f1c9;
	border: #d3d488 1px solid
}

.region-con h1 {
	font-size: 22px;
	font-weight: 200;
	margin-top: 20px;
	margin-bottom: 10px;
}

.region-con-sec {
	float: left;
	width: 100%;
	border-top: 1px solid #eee;
	border-bottom: 1px solid #eee;
	padding-top: 13px;
}

.region-con-drop {
	width: 50%; /* text-align: right; */
	float: left;
	padding-right: 30px;
	box-sizing: border-box;
}

.region-con-drop span {
	font-size: 18px;
	margin-right: 18px;
}

.region-con-drop select {
	width: 30%;
	padding: 7px;
}

.region-drop {
	width: 100%;
	float: left;
}

.region-drop li {
	float: left;
	margin-right: 20px;
}

.region-drop li label {
	float: left;
	margin-right: 10px;
	line-height: 34px;
}

.region-drop span {
	float: left;
}

.DTTT.btn-group {
	width: 100%;
	float: right;
	margin: 10px 0;
}
</style>
<style type="text/css">
.reportbox {
	border: none;
	font-family: arial;
	font-size: 13px;
	text-align: center;
}

.reportbox td {
	border: #ccc 1px solid;
	margin: 0;
	padding: 10px;
	vertical-align: top;
	white-space: nowrap;
}

.reportpad {
	padding: 0 !important;
	margin: 0;
	border-top: 0 !important;
	border-left: 0 !important;
	width: 100%;
	text-align: center;
}

.reportinnbox {
	margin: 0;
	padding: 0;
	width: 100%;
	text-align: center;
}

.reportinnbox td {
	padding: 10px;
	border: 0;
	border: 1px solid #ccc;
}
}
}
</style>
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

<!-- DataTable -->
<script type="text/javascript" src="./resources/dataTable/jquery.js"></script>
<script type="text/javascript"
	src="./resources/js/jquery.dataTables.min.js"></script>
<script type="text/javascript"
	src="./resources/js/dataTables.tableTools.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.dataTables.min.css">
<link rel="stylesheet" type="text/css"
	href='./resources/css/dataTables.tableTools.min.css'>
<link rel="stylesheet" type="text/css"
	href='./resources/css/dataTables.tableTools.css'>
<!-- DataTable -->
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$("#btnExport")
								.click(
										function(e) {
											e.preventDefault();

											//getting data from our table
											var data_type = 'data:application/vnd.ms-excel';
											var table_div = document
													.getElementById('table_wrapper');
											var table_html = table_div.outerHTML
													.replace(/ /g, '%20');

											var a = document.createElement('a');
											a.href = data_type + ', '
													+ table_html;
											a.download = 'exported_table_'
													+ Math
															.floor((Math
																	.random() * 9999999) + 1000000)
													+ '.xls';
											a.click();
										});
					});
</script>

<script type="text/javascript">
	function EOD() {
		addHeaderJson();
		var suspenseOpeningBalance = {
			"denomination5" : $("#deno5").text(),
			"denomination10" : $("#deno10").text(),
			"denomination20" : $("#deno20").text(),
			"denomination50" : $("#deno50").text(),
			"denomination100" : $("#deno100").text(),
			"denomination200" : $("#deno200").text(),
			"denomination500" : $("#deno500").text(),
			"denomination2000" : $("#deno2000").text(),
		}
		
		$.ajax({  
		    type: "POST", 
		    contentType : 'application/json; charset=utf-8',
		      dataType : 'json',
		    url: "././generateSuspenseEOD",
		    data: JSON.stringify(suspenseOpeningBalance),
		    success: function(response){
		    	 alert("SUCCESS")
		    }, 
		   /*  error: function(e){  
		      alert('Error: ' + e);  
		    }   */
		  }); 
	}
</script>

<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>
<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-192"
	data-genuitec-path="/Currency/src/main/webapp/jsp/suspenseCashRegister.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-192"
		data-genuitec-path="/Currency/src/main/webapp/jsp/suspenseCashRegister.jsp">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<ul>
								<li><input type="submit" onclick="EOD()" value="EOD"
									class="btn btn-lg btn-success btn-block"></li>
							</ul>
							Suspense Cash Register
						</div>
						<!-- /.panel-heading -->
						<div>
							<input type="button" class="btn btn-default qr-button"
								onclick="printDiv('printableArea')" value="Print" />
							<button id="btnExport" class="btn btn-default qr-button">Export
								to xls</button>
						</div>

						<div class="panel-body">

							<div class="region-con">
								<form:form id="userPage" name="userPage"
									action="suspenseCashRegister" method="post"
									modelAttribute="reportDate" autocomplete="off">
									<div class="region-con-sec">
										<ul class="region-drop">
											<li><label>Start Date</label> <span> <form:input
														type="text" path="fromDate" id="fromDate" name="fromDate"
														cssClass="form-control" />
											</span></li>

											<li><label>End Date</label> <span> <form:input
														type="text" path="toDate" id="toDate" name="toDate"
														cssClass="form-control" />
											</span></li>

											<li><label></label> <span>
													<button type="submit" class="btn btn-default"
														value="Details" style="width: 99px;">Search</button>
											</span></li>
										</ul>

									</div>
								</form:form>
							</div>
							<form id="showAll">
								<div class="dispencery_wrap">
									<div id="printableArea">
										<div id="table_wrapper">



											<table border="none" class="reportbox" cellpadding="0"
												cellspacing="0">
												<tr>
													<td></td>
													<td>Opening Balance (No. of Notes)</td>
													<td>Withdrawals (No. of Notes)</td>
													<td>Replenishment (No. of Notes)</td>
													<td>Deposit (No. of Notes</td>
													<td>Depletion (No. of Notes)</td>
													<td>Closing Balance (No. of Notes)</td>
													<td style="border-bottom: 0"></td>
													<td style="border-bottom: 0"></td>
													<td>Signature</td>
													<td style="border-bottom: 0"></td>
												</tr>
												<tr>
													<td class="reportpad">
														<table class="reportinnbox" cellpadding="0"
															cellspacing="0">
															<tr>
																<td style="height: 57px; vertical-align: bottom;">Date</td>
															</tr>
															<c:forEach var="row" items="${openingBalance}">
																<tr>
																	<td><fmt:formatDate pattern="yyyy-MM-dd"
																			value="${row.insertTime.time}" /></td>
																</tr>
															</c:forEach>
														</table>
													</td>
													<td class="reportpad">
														<table class="reportinnbox" cellpadding="0"
															cellspacing="0;">

															<tr>
																<td>Rs <br> 5
																</td>
																<td>Rs <br> 10
																</td>
																<td>Rs <br> 20
																</td>
																<td>Rs <br> 50
																</td>
																<td>Rs <br> 100
																</td>
																<td>Rs <br> 200
																</td>
																<td>Rs <br> 500
																</td>
																<td>Rs <br> 2000
																</td>
															</tr>
															<c:forEach var="row" items="${openingBalance}">
																<tr>
																	<td align="center">${row.denomination5}</td>
																	<td align="center">${row.denomination10}</td>
																	<td align="center">${row.denomination20}</td>
																	<td align="center">${row.denomination50}</td>
																	<td align="center">${row.denomination100}</td>
																	<td align="center">${row.denomination200}</td>
																	<td align="center">${row.denomination500}</td>
																	<td align="center">${row.denomination2000}</td>
																</tr>
															</c:forEach>
														</table>
													</td>
													<td class="reportpad">
														<table class="reportinnbox" cellpadding="0"
															cellspacing="0;">
															<tr>
																<td>Rs <br> 5
																</td>
																<td>Rs <br> 10
																</td>
																<td>Rs <br> 20
																</td>
																<td>Rs <br> 50
																</td>
																<td>Rs <br> 100
																</td>
																<td>Rs <br> 200
																</td>
																<td>Rs <br> 500
																</td>
																<td>Rs <br> 2000
																</td>
															</tr>
															<c:forEach var="row" items="${withdrawalList}">
																<tr>
																	<td align="center">${row.denom5Pieces}</td>
																	<td align="center">${row.denom10Pieces}</td>
																	<td align="center">${row.denom20Pieces}</td>
																	<td align="center">${row.denom50Pieces}</td>
																	<td align="center">${row.denom100Pieces}</td>
																	<td align="center">${row.denom200Pieces}</td>
																	<td align="center">${row.denom500Pieces}</td>
																	<td align="center">${row.denom2000Pieces}</td>
																</tr>
															</c:forEach>

														</table>
													</td>
													<td class="reportpad">
														<table class="reportinnbox" cellpadding="0"
															cellspacing="0;">
															<tr>
																<td>Rs <br> 5
																</td>
																<td>Rs <br> 10
																</td>
																<td>Rs <br> 20
																</td>
																<td>Rs <br> 50
																</td>
																<td>Rs <br> 100
																</td>
																<td>Rs <br> 200
																</td>
																<td>Rs <br> 500
																</td>
																<td>Rs <br> 2000
																</td>
															</tr>
															<c:forEach var="row" items="${withdrawalPrevious}">
																<tr>
																	<td align="center">${row.denom5Pieces}</td>
																	<td align="center">${row.denom10Pieces}</td>
																	<td align="center">${row.denom20Pieces}</td>
																	<td align="center">${row.denom50Pieces}</td>
																	<td align="center">${row.denom100Pieces}</td>
																	<td align="center">${row.denom200Pieces}</td>
																	<td align="center">${row.denom500Pieces}</td>
																	<td align="center">${row.denom2000Pieces}</td>
																</tr>
															</c:forEach>
														</table>
													</td>
													<td class="reportpad">
														<table class="reportinnbox" cellpadding="0"
															cellspacing="0;">
															<tr>
																<td>Rs <br> 5
																</td>
																<td>Rs <br> 10
																</td>
																<td>Rs <br> 20
																</td>
																<td>Rs <br> 50
																</td>
																<td>Rs <br> 100
																</td>
																<td>Rs <br> 200
																</td>
																<td>Rs <br> 500
																</td>
																<td>Rs <br> 2000
																</td>
															</tr>
															<c:forEach var="row" items="${depositList}">
																<tr>
																	<td align="center">${row.denom5Pieces}</td>
																	<td align="center">${row.denom10Pieces}</td>
																	<td align="center">${row.denom20Pieces}</td>
																	<td align="center">${row.denom50Pieces}</td>
																	<td align="center">${row.denom100Pieces}</td>
																	<td align="center">${row.denom200Pieces}</td>
																	<td align="center">${row.denom500Pieces}</td>
																	<td align="center">${row.denom2000Pieces}</td>
																</tr>
															</c:forEach>
														</table>
													</td>
													<td class="reportpad">
														<table class="reportinnbox" cellpadding="0"
															cellspacing="0;">
															<tr>
																<td>Rs <br> 5
																</td>
																<td>Rs <br> 10
																</td>
																<td>Rs <br> 20
																</td>
																<td>Rs <br> 50
																</td>
																<td>Rs <br> 100
																</td>
																<td>Rs <br> 200
																</td>
																<td>Rs <br> 500
																</td>
																<td>Rs <br> 2000
																</td>
															</tr>
															<c:forEach var="row" items="${depositListPrevious}">
																<tr>
																	<td align="center">${row.denom5Pieces}</td>
																	<td align="center">${row.denom10Pieces}</td>
																	<td align="center">${row.denom20Pieces}</td>
																	<td align="center">${row.denom50Pieces}</td>
																	<td align="center">${row.denom100Pieces}</td>
																	<td align="center">${row.denom200Pieces}</td>
																	<td align="center">${row.denom500Pieces}</td>
																	<td align="center">${row.denom2000Pieces}</td>
																</tr>
															</c:forEach>

														</table>
													</td>
													<td class="reportpad">
														<table class="reportinnbox" cellpadding="0"
															cellspacing="0;">
															<tr>
																<td>Rs <br> 5
																</td>
																<td>Rs <br> 10
																</td>
																<td>Rs<br> 20
																</td>
																<td>Rs<br> 50
																</td>
																<td>Rs <br> 100
																</td>
																<td>Rs <br> 200
																</td>
																<td>Rs<br> 500
																</td>
																<td>Rs <br> 2000
																</td>
															</tr>
															<tr>

																<td align="center" id="deno5">${closingBalDeno5}</td>
																<td align="center" id="deno10">${closingBalDeno10}</td>
																<td align="center" id="deno20">${closingBalDeno20}</td>
																<td align="center" id="deno50">${closingBalDeno50}</td>
																<td align="center" id="deno100">${closingBalDeno100}</td>
																<td align="center" id="deno200">${closingBalDeno200}</td>
																<td align="center" id="deno500 ">${closingBalDeno500}</td>
																<td align="center" id="deno2000">${closingBalDeno2000}</td>

															</tr>

														</table>
													</td>
													<td style="border-top: 0; padding: 0 10px;"
														class="reportpad">
														<table cellpadding="0" cellspacing="0">
															<tr>
																<td style="height: 57px;">Closing Balance Value</td>
															</tr>
															<tr>
																<td align="center">${totalClosingBalance}</td>
															</tr>
														</table>
													</td>
													<td style="border-top: 0; padding: 0 10px;"
														class="reportpad">
														<table cellpadding="0" cellspacing="0">
															<tr>
																<td style="height: 57px;">SR Number (Deposit/
																	Withdrawal)</td>
															</tr>
															<tr>
																<td>12345</td>
															</tr>
														</table>
													</td>

													<td class="reportpad">
														<table cellpadding="0" cellspacing="0">
															<tr>
																<td style="height: 57px;">Custodian 1</td>
																<td>Custodian 2</td>
															</tr>
														</table>
													</td>
													<td style="border-top: 0; padding: 0 10px;"
														class="reportpad">
														<table cellpadding="0" cellspacing="0">
															<tr>
																<td style="height: 57px;">Remarks</td>
															</tr>
															<tr>
																<td style="height: 57px;"></td>
															</tr>
														</table>

													</td>

												</tr>

											</table>


										</div>
									</div>
								</div>
							</form>

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

	<!-- Bootstrap Core JavaScript -->
	<script
		src="./resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script
		src="./resources/bower_components/metisMenu/dist/metisMenu.min.js"></script>

	<!-- DataTables JavaScript -->
	<script
		src="./resources/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="./resources/dist/js/sb-admin-2.js"></script>

	<script src="./resources/js/jquery.datetimepicker.js"></script>
	<script>
		$('#fromDate').datetimepicker({
			format : 'Y-m-d',
		});
		
		$('#toDate').datetimepicker({
			format : 'Y-m-d',
		});
	</script>

	<script type="text/javascript" src="./js/htmlInjection.js"></script>
	<script type="text/javascript" src="./js/print.js"></script>

</body>

</html>