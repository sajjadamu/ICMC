<!DOCTYPE html>
<%@page import="java.util.Date"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.mysema.query.Tuple"%>
<%@page import="java.util.List"%>
<html lang="en">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="./resources/logo/yes_favicon.ico"
	type="image/x-icon">
<title> Branch Report</title>

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

<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-13"
	data-genuitec-path="/Currency/src/main/webapp/jsp/CashOutReport.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-13"
		data-genuitec-path="/Currency/src/main/webapp/jsp/CashOutReport.jsp">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">Daily Payment Reports</div>
						<div>
							<input type="button" class="btn btn-default qr-button"
								onclick="printDiv('printableArea')" value="Print" />
						</div>

						<!-- /.panel-heading -->
						<div class="panel-body">

							<c:set var="dateRangeURL" value="CashOutReport" />
							<jsp:include page="reportDateRange.jsp" />

							<div class="dataTable_wrapper">
								<form id="showAll">
									<!-- <table class="table table-striped table-bordered table-hover" id="dataTables-example"> -->
									<div id="printableArea">

										<table class="table table-striped table-bordered table-hover"
											id="tableValue">
											<thead>
												<tr>
													<th>SR No.</th>
													<th>Sol ID</th>
													<th>Branch</th>
													<th>Total Value</th>
													<th>Remarks</th>
													<!-- <th>Coins 10</th> -->
													<!-- <th>Coins 5</th>
												<th>Coins 2</th>
												<th>Coins 1</th>
												<th>2000</th>
												<th>1000</th>
												<th>500</th>
												<th>100</th>
												<th>50</th>
												<th>20</th>
												<th>10</th>
												<th>5</th>
												<th>2</th>
												<th>1</th> -->
												</tr>
											</thead>
											<tbody>
												<%
												List<Tuple> listTuple = (List<Tuple>) request.getAttribute("records");
												for (Tuple tuple : listTuple) {
											%>
												<tr>
													<td><%=tuple.get(1, String.class)%></td>
													<td><%=tuple.get(0, String.class)%></td>
													<td><%=tuple.get(2, String.class)%></td>
													<td><%=tuple.get(3, BigDecimal.class)%></td>
													<%-- <td><input type="text" value="Cash sent to the branch Rs <%=tuple.get(3, BigDecimal.class)%>"></td> --%>
													<td><textarea rows="1" cols="70">Cash sent to the branch Rs <%=tuple.get(3, BigDecimal.class)%></textarea></td>
													<%-- <td><%=tuple.get(4,BigDecimal.class) %></td>
												<td><%=tuple.get(5,BigDecimal.class) %></td>
												<td><%=tuple.get(6,BigDecimal.class) %></td>
												<td><%=tuple.get(7,BigDecimal.class) %></td>
												<td><%=tuple.get(17,BigDecimal.class) %></td>
												<td><%=tuple.get(8,BigDecimal.class) %></td>
												<td><%=tuple.get(9,BigDecimal.class) %></td>
												<td><%=tuple.get(10,BigDecimal.class) %></td>
												<td><%=tuple.get(11,BigDecimal.class) %></td>
												<td><%=tuple.get(12,BigDecimal.class) %></td>
												<td><%=tuple.get(13,BigDecimal.class) %></td>
												<td><%=tuple.get(14,BigDecimal.class) %></td>
												<td><%=tuple.get(15,BigDecimal.class) %></td>
												<Td><%=tuple.get(16,BigDecimal.class) %></Td> --%>
												</tr>
												<%
												}
											%>
											</tbody>
										</table>
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

		<!-- Page-Level Demo Scripts - Tables - Use for reference -->
		<script>
		$(document)
				.ready(
						function() {
							var table = $('#tableValue').dataTable({
								"pagingType" : "full_numbers",
							});
							var tableTools = new $.fn.dataTable.TableTools(
									table,
									{
									 'sSwfPath' : './js/copy_csv_xls_pdf.swf',
/* 									 'sSwfPath':'//cdn.datatables.net/tabletools/2.2.4/swf/copy_csv_xls_pdf.swf',
 */										'aButtons' : [ 'copy', /* {
											'sExtends' : 'print',
											'bShowAll' : false
										}, */ 'csv', {
											'sExtends' : 'xls',
											'sFileName' : '*.xls',
											'sButtonText' : ' Excel'
										}, {
											'sExtends' : 'pdf',
											'bFooter' : false
										} ]
									});
							$(tableTools.fnContainer()).insertBefore(
									'.dataTable_wrapper');
						});
	</script>
		<script type="text/javascript" src="./js/htmlInjection.js"></script>
		<script type="text/javascript" src="./js/print.js"></script>
</body>

</html>