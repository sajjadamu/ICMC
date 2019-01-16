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
<link rel="shortcut icon" href="./resources/logo/yes_favicon.ico"
	type="image/x-icon">
<title> DN2 Report</title>

<!-- Bootstrap Core CSS -->
<link
	href="./resources/bower_components/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.datetimepicker.css" />
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
<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-15"
	data-genuitec-path="/Currency/src/main/webapp/jsp/DN2Report.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-15"
		data-genuitec-path="/Currency/src/main/webapp/jsp/DN2Report.jsp">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">

			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">DN2 Register</div>
						<div>
							<input type="button" class="btn btn-default qr-button"
								onclick="printDiv('printableArea')" value="Print" />
							<button id="btnExport" class="btn btn-default qr-button">Export
								to xls</button>
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">

							<div class="region-con">
								<form:form id="userPage" name="userPage" action="DN2Report"
									method="post" modelAttribute="reportDate" autocomplete="off">
									<div class="region-con-sec">
										<ul class="region-drop">
											<li>
												<table>
													<tr>
														<td><B>Select Date</B></td>
														<td><form:input type="text" path="fromDate"
																id="fromDate" name="fromDate" cssClass="form-control" />
														</td>
														<%-- <td><form:input type="text" path="toDate"
																id="toDate" name="toDate" cssClass="form-control" />
														</td> --%>
														<td><button type="submit" class="btn btn-default"
																value="Details" style="width: 99px;">Search</button></td>
													</tr>
												</table>
											</li>
										</ul>
									</div>
								</form:form>
							</div>
							<jsp:useBean id="date" class="java.util.Date" />
							<!-- <div class="dataTable_wrapper"> -->
							<div>
								<form id="showAll">
									<!-- <table class="table table-striped table-bordered table-hover" id="dataTables-example"> -->
									<div class="containerr" id="printableArea">
										<div id="table_wrapper">
											<table width="100%" border="1">
												<tr>
													<td align="center">Date</td>
													<td align="center">1</td>
													<td align="center">2</td>
													<td align="center">5</td>
													<td align="center">10</td>
													<td align="center">20</td>
													<td align="center">50</td>
													<td align="center">100</td>
													<td align="center">200</td>
													<td align="center">500</td>
													<td align="center">2000</td>
													<td align="center">Total pieces</td>
													<td align="center">Value</td>
												</tr>
												<tbody>
													<tr>
														<td align="center">${currentDate}</td>
														<td align="center">${denom1Pieces}</td>
														<td align="center">${denom2Pieces}</td>
														<td align="center">${denom5Pieces}</td>
														<td align="center">${denom10Pieces}</td>
														<td align="center">${denom20Pieces}</td>
														<td align="center">${denom50Pieces}</td>
														<td align="center">${denom100Pieces}</td>
														<td align="center">${denom200Pieces}</td>
														<td align="center">${denom500Pieces}</td>
														<td align="center">${denom2000Pieces}</td>
														<td align="center">${totalInPieces}</td>
														<td align="center">${totalValues}</td>
													</tr>

													<%-- </c:forEach> --%>
													<%-- <Tr>
												<td align="center">Total</td>
												<td align="center"><c:set var="totalValue" value="${0}" /> <c:forEach
														begin="0" end="9" step="1" var="summaryRecord"
														items="${mutilatedRecords}">
														<c:set var="totalValue"
															value="${totalValue + summaryRecord.value.denom1Pieces}" />
													</c:forEach> <c:out value="${totalValue}" /></td>
													
													<td align="center"><c:set var="totalValue" value="${0}" /> <c:forEach
														begin="0" end="9" step="1" var="summaryRecord"
														items="${mutilatedRecords}">
														<c:set var="totalValue"
															value="${totalValue + summaryRecord.value.denom2Pieces}" />
													</c:forEach> <c:out value="${totalValue}" /></td>
													
													
													<td align="center"><c:set var="totalValue" value="${0}" /> <c:forEach
														begin="0" end="9" step="1" var="summaryRecord"
														items="${mutilatedRecords}">
														<c:set var="totalValue"
															value="${totalValue + summaryRecord.value.denom5Pieces}" />
													</c:forEach> <c:out value="${totalValue}" /></td>
													
													
													<td align="center"><c:set var="totalValue" value="${0}" /> <c:forEach
														begin="0" end="9" step="1" var="summaryRecord"
														items="${mutilatedRecords}">
														<c:set var="totalValue"
															value="${totalValue + summaryRecord.value.denom10Pieces}" />
													</c:forEach> <c:out value="${totalValue}" /></td>
													
													<td align="center"><c:set var="totalValue" value="${0}" /> <c:forEach
														begin="0" end="9" step="1" var="summaryRecord"
														items="${mutilatedRecords}">
														<c:set var="totalValue"
															value="${totalValue + summaryRecord.value.denom20Pieces}" />
													</c:forEach> <c:out value="${totalValue}" /></td>
													
													<td align="center"><c:set var="totalValue" value="${0}" /> <c:forEach
														begin="0" end="9" step="1" var="summaryRecord"
														items="${mutilatedRecords}">
														<c:set var="totalValue"
															value="${totalValue + summaryRecord.value.denom50Pieces}" />
													</c:forEach> <c:out value="${totalValue}" /></td>
													
													<td align="center"><c:set var="totalValue" value="${0}" /> <c:forEach
														begin="0" end="9" step="1" var="summaryRecord"
														items="${mutilatedRecords}">
														<c:set var="totalValue"
															value="${totalValue + summaryRecord.value.denom100Pieces}" />
													</c:forEach> <c:out value="${totalValue}" /></td>
													
													<td align="center"><c:set var="totalValue" value="${0}" /> <c:forEach
														begin="0" end="9" step="1" var="summaryRecord"
														items="${mutilatedRecords}">
														<c:set var="totalValue"
															value="${totalValue + summaryRecord.value.denom200Pieces}" />
													</c:forEach> <c:out value="${totalValue}" /></td>
													
													<td align="center"><c:set var="totalValue" value="${0}" /> <c:forEach
														begin="0" end="9" step="1" var="summaryRecord"
														items="${mutilatedRecords}">
														<c:set var="totalValue"
															value="${totalValue + summaryRecord.value.denom500Pieces}" />
													</c:forEach> <c:out value="${totalValue}" /></td>
													
													<td align="center"><c:set var="totalValue" value="${0}" /> <c:forEach
														begin="0" end="9" step="1" var="summaryRecord"
														items="${mutilatedRecords}">
														<c:set var="totalValue"
															value="${totalValue + summaryRecord.value.denom1000Pieces}" />
													</c:forEach> <c:out value="${totalValue}" /></td>
													
													<td align="center"><c:set var="totalValue" value="${0}" /> <c:forEach
														begin="0" end="9" step="1" var="summaryRecord"
														items="${mutilatedRecords}">
														<c:set var="totalValue"
															value="${totalValue + summaryRecord.value.denom2000Pieces}" />
													</c:forEach> <c:out value="${totalValue}" /></td>
													
													<td align="center"><c:set var="totalValue" value="${0}" /> <c:forEach
														begin="0" end="9" step="1" var="summaryRecord"
														items="${mutilatedRecords}">
														<c:set var="totalValue"
															value="${totalValue + summaryRecord.value.totalInPieces}" />
													</c:forEach> <c:out value="${totalValue}" /></td>
											</Tr> --%>
												</tbody>
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
	<script type="text/javascript">
$(document).ready(function() {
	  $("#btnExport").click(function(e) {
	    e.preventDefault();

	    //getting data from our table
	    var data_type = 'data:application/vnd.ms-excel';
	    var table_div = document.getElementById('table_wrapper');
	    var table_html = table_div.outerHTML.replace(/ /g, '%20');

	    var a = document.createElement('a');
	    a.href = data_type + ', ' + table_html;
	    a.download = 'exported_table_' + Math.floor((Math.random() * 9999999) + 1000000) + '.xls';
	    a.click();
	  });
	});
</script>
	<script>
		$(document).ready(function() {
			$('#tableValue').dataTable({

				"pagingType" : "full_numbers",

			});
		});
	</script>
	<script type="text/javascript" src="./js/print.js"></script>
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
</body>

</html>