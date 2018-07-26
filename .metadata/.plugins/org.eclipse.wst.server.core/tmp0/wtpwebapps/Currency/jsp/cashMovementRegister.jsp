<!DOCTYPE html>
<%@page import="com.chest.currency.enums.BinCategoryType"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.mysema.query.Tuple"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.math.BigDecimal"%>
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
<link rel="shortcut icon" href="./resources/logo/favicon.ico"
	type="image/x-icon">
<title>ICICI : Cash Movement Register</title>

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
}

.reportpad {
	padding: 0 !important;
	margin: 0;
	border-top: 0 !important;
	border-left: 0 !important;
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
</style>
<link href="./resources/css/calendar.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.datetimepicker.css" />
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
	href="./resources/dist/css/style.css">

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

<!-- DataTable -->

<script type="text/javascript" charset="utf8"
	src="./resources/js/jquery.dataTables.min.js"></script>
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
<script type="text/javascript" charset="utf8"
	src="./resources/js/dataTables.jqueryui.js"></script>
<link rel="stylesheet" type="text/css"
	href="./resources/css/dataTables.jqueryui.css">
<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.dataTables.css">

<!-- DataTable -->

<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script></head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-75" data-genuitec-path="/Currency/src/main/webapp/jsp/cashMovementRegister.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-75" data-genuitec-path="/Currency/src/main/webapp/jsp/cashMovementRegister.jsp">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">

			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">Cash Movement Register</div>
						<div>
							<input type="button" class="btn btn-default qr-button"
								onclick="printDiv('printableArea')" value="Print" />
							<button id="btnExport" class="btn btn-default qr-button">Export
								to xls</button>
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="region-con">
								<form:form id="userPage" name="userPage"
									action="cashMovementRegister" method="post"
									modelAttribute="reportDate" autocomplete="off">
									<div class="region-con-sec">
										<ul class="region-drop">
											<li><label>Select Date</label> <span> <input
													type="text" id="fromDate" name="fromDate" />
											</span> <span>
													<button type="submit" class="btn btn-default"
														value="Details" style="width: 99px;">Search</button>
											</span></li>
										</ul>
									</div>
								</form:form>
							</div>

							<!-- <div class="dataTable_wrapper"> -->
							<div>
								<form id="showAll">
									<!-- <table class="table table-striped table-bordered table-hover" id="dataTables-example"> -->
									<div class="containerr" id="printableArea">
										<div id="table_wrapper">

											<table border="none" class="reportbox" cellpadding="0"
												cellspacing="0">
												<tr>
													<td colspan="8"><b>CASH MOVEMENT REGISTER (for
															cash movement between Vault and Receipt & Payment room)</b></td>
												</tr>
												<tr>
													<td></td>
													<td></td>
													<td>Processed Cash (Cash remitted to Branches/CRA)</td>
													<td>Sign of Vault Custodians</td>
													<td>Sign of ICMC Official</td>
													<td>Unprocessed Cash (cash received from branches)</td>
													<td>Sign of ICMC Official</td>
													<td>Sign of Vault Custodians</td>


												</tr>
												<tr>
													<td class="reportpad">
														<table class="reportinnbox" cellpadding="0"
															cellspacing="0" style="white-space: nowrap;">
															<tr>
																<td style="vertical-align: bottom; height: 57px">Date</td>
															</tr>
															<tr>
																<td><fmt:formatDate pattern="yyyy-MM-dd"
																		value="${currenctDate}" /></td>
															</tr>
															<tr>
																<td><fmt:formatDate pattern="yyyy-MM-dd"
																		value="${currenctDate}" /></td>
															</tr>
															<tr>
																<td><fmt:formatDate pattern="yyyy-MM-dd"
																		value="${currenctDate}" /></td>
															</tr>
															<tr>
																<td><fmt:formatDate pattern="yyyy-MM-dd"
																		value="${currenctDate}" /></td>
															</tr>
															<tr>
																<td><fmt:formatDate pattern="yyyy-MM-dd"
																		value="${currenctDate}" /></td>
															</tr>
															<tr>
																<td><fmt:formatDate pattern="yyyy-MM-dd"
																		value="${currenctDate}" /></td>
															</tr>
															<tr>
																<td><fmt:formatDate pattern="yyyy-MM-dd"
																		value="${currenctDate}" /></td>
															</tr>
															<tr>
																<td><fmt:formatDate pattern="yyyy-MM-dd"
																		value="${currenctDate}" /></td>
															</tr>
															<tr>
																<td><fmt:formatDate pattern="yyyy-MM-dd"
																		value="${currenctDate}" /></td>
															</tr>


														</table>
													</td>
													<td class="reportpad">
														<table class="reportinnbox" cellpadding="0"
															cellspacing="0">
															<tr>
																<td style="vertical-align: bottom; height: 57px">Denomination</td>
															</tr>
															<tr>
																<td>2000</td>
															</tr>
															<tr>
																<td>1000</td>
															</tr>
															<tr>
																<td>500</td>
															</tr>
															<tr>
																<td>200</td>
															</tr>
															<tr>
																<td>100</td>
															</tr>
															<tr>
																<td>50</td>
															</tr>
															<tr>
																<td>20</td>
															</tr>
															<tr>
																<td>10</td>
															</tr>
															<tr>
																<td>5</td>
															</tr>
														</table>
													</td>

													<td class="reportpad">
														<table class="reportinnbox" cellpadding="0"
															cellspacing="0;">

															<tr>
																<td>ATM</td>
																<td>Issuable</td>
																<td>Total Bundles</td>
																<td>Total Value</td>
															</tr>
															<tr>
																<td>${summaryListForATM.denomination2000}</td>
																<td>${summaryListForISSUABLE.denomination2000}</td>
																<td>${atmAndIssuableBundleSum.denomination2000}</td>
																<td>${atmAndIssuableBundleSum.denomination2000*1000*2000}
																</td>
															</tr>
															<tr>
																<td>${summaryListForATM.denomination1000}</td>
																<td>${summaryListForISSUABLE.denomination1000}</td>
																<td>${atmAndIssuableBundleSum.denomination1000}</td>
																<td>${atmAndIssuableBundleSum.denomination1000*1000*1000}
																</td>
															</tr>
															<tr>
																<td>${summaryListForATM.denomination500}</td>
																<td>${summaryListForISSUABLE.denomination500}</td>
																<td>${atmAndIssuableBundleSum.denomination500}</td>
																<td>${atmAndIssuableBundleSum.denomination500*1000*500}
																</td>
															</tr>
															<tr>
																<td>${summaryListForATM.denomination200}</td>
																<td>${summaryListForISSUABLE.denomination200}</td>
																<td>${atmAndIssuableBundleSum.denomination200}</td>
																<td>${atmAndIssuableBundleSum.denomination200*1000*200}
																</td>
															</tr>
															<tr>
																<td>${summaryListForATM.denomination100}</td>
																<td>${summaryListForISSUABLE.denomination100}</td>
																<td>${atmAndIssuableBundleSum.denomination100}</td>
																<td>${atmAndIssuableBundleSum.denomination100*1000*100}
																</td>
															</tr>
															<tr>
																<td>${summaryListForATM.denomination50}</td>
																<td>${summaryListForISSUABLE.denomination50}</td>
																<td>${atmAndIssuableBundleSum.denomination50}</td>
																<td>${atmAndIssuableBundleSum.denomination50*1000*50}
																</td>
															</tr>
															<tr>
																<td>${summaryListForATM.denomination20}</td>
																<td>${summaryListForISSUABLE.denomination20}</td>
																<td>${atmAndIssuableBundleSum.denomination20}</td>
																<td>${atmAndIssuableBundleSum.denomination20*1000*20}
																</td>
															</tr>
															<tr>
																<td>${summaryListForATM.denomination10}</td>
																<td>${summaryListForISSUABLE.denomination10}</td>
																<td>${atmAndIssuableBundleSum.denomination10}</td>
																<td>${atmAndIssuableBundleSum.denomination10*1000*10}
																</td>
															</tr>
															<tr>
																<td>${summaryListForATM.denomination5}</td>
																<td>${summaryListForISSUABLE.denomination5}</td>
																<td>${atmAndIssuableBundleSum.denomination5}</td>
																<td>${atmAndIssuableBundleSum.denomination5*1000*5}
																</td>
															</tr>

														</table>
													</td>

													<td class="reportpad">
														<table class="reportinnbox" cellpadding="0"
															cellspacing="0;">
															<tr>
																<td style="vertical-align: bottom; height: 57px">Cust1</td>
																<td style="vertical-align: bottom; height: 57px">Cust2</td>
															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
																<td style="vertical-align: bottom; height: 39px"></td>
															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
																<td style="vertical-align: bottom; height: 39px"></td>
															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
																<td style="vertical-align: bottom; height: 39px"></td>
															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
																<td style="vertical-align: bottom; height: 39px"></td>
															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
																<td style="vertical-align: bottom; height: 39px"></td>
															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
																<td style="vertical-align: bottom; height: 39px"></td>
															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
																<td style="vertical-align: bottom; height: 39px"></td>
															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
																<td style="vertical-align: bottom; height: 39px"></td>
															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
																<td style="vertical-align: bottom; height: 39px"></td>
															</tr>
														</table>
													</td>
													<td class="reportpad">
														<table class="reportinnbox" cellpadding="0"
															cellspacing="0;">

															<tr>
																<td style="vertical-align: bottom; height: 57px"></td>
															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
															</tr>

														</table>
													</td>
													<td class="reportpad">
														<table class="reportinnbox" cellpadding="0"
															cellspacing="0;">

															<tr>
																<td>No of Bundles</td>
																<td>Total Value</td>
															</tr>
															<tr>
																<td>${branchDeposit.denomination2000}</td>
																<td>${branchDeposit.denomination2000*1000*2000}</td>
															</tr>
															<tr>
																<td>${branchDeposit.denomination1000}</td>
																<td>${branchDeposit.denomination1000*1000*1000}</td>
															</tr>
															<tr>
																<td>${branchDeposit.denomination500}</td>
																<td>${branchDeposit.denomination500*1000*1000}</td>
															</tr>
															<tr>
																<td>${branchDeposit.denomination200}</td>
																<td>${branchDeposit.denomination200*1000*200}</td>
															</tr>
															<tr>
																<td>${branchDeposit.denomination100}</td>
																<td>${branchDeposit.denomination100*1000*100}</td>
															</tr>
															<tr>
																<td>${branchDeposit.denomination50}</td>
																<td>${branchDeposit.denomination50*1000*50}</td>
															</tr>
															<tr>
																<td>${branchDeposit.denomination20}</td>
																<td>${branchDeposit.denomination20*1000*20}</td>
															</tr>
															<tr>
																<td>${branchDeposit.denomination10}</td>
																<td>${branchDeposit.denomination10*1000*10}</td>
															</tr>
															<tr>
																<td>${branchDeposit.denomination5}</td>
																<td>${branchDeposit.denomination5*1000*5}</td>
															</tr>

														</table>
													</td>
													<td class="reportpad">
														<table class="reportinnbox" cellpadding="0"
															cellspacing="0;">

															<tr>
																<td style="height: 57px"></td>
															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
															</tr>

														</table>
													</td>
													<td class="reportpad">
														<table class="reportinnbox" cellpadding="0"
															cellspacing="0;">

															<tr>
																<td>Cust 1</td>
																<td>Cust 2</td>
															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
																<td style="vertical-align: bottom; height: 39px"></td>
															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
																<td style="vertical-align: bottom; height: 39px"></td>

															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
																<td style="vertical-align: bottom; height: 39px"></td>

															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
																<td style="vertical-align: bottom; height: 39px"></td>

															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
																<td style="vertical-align: bottom; height: 39px"></td>

															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
																<td style="vertical-align: bottom; height: 39px"></td>

															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
																<td style="vertical-align: bottom; height: 39px"></td>

															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
																<td style="vertical-align: bottom; height: 39px"></td>

															</tr>
															<tr>
																<td style="vertical-align: bottom; height: 39px"></td>
																<td style="vertical-align: bottom; height: 39px"></td>

															</tr>

														</table>
													</td>




												</tr>

											</table>
										</div>
									</div>
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

	<!-- Page-Level Demo Scripts - Tables - Use for reference -->

	<script type="text/javascript" src="./js/print.js"></script>
	<script src="./resources/js/jquery.datetimepicker.js"></script>
	<script>
		$('#fromDate').datetimepicker({
			format : 'Y-m-d',
		});
	</script>

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

												var a = document
														.createElement('a');
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
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>