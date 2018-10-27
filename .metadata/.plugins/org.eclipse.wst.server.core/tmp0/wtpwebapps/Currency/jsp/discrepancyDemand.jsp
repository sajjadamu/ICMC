<!DOCTYPE html>
<%@page import="com.mysema.query.Tuple"%>
<%@page import="java.util.List"%>
<html lang="en">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="./resources/logo/favicon.ico"
	type="image/x-icon">
<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.datetimepicker.css" />
<title>ICICI : Discrepancy Demand</title>

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
<script type="text/javascript" src="./resources/js/sum().js"></script>
<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.dataTables.min.css">
<link rel="stylesheet" type="text/css"
	href='./resources/css/dataTables.tableTools.min.css'>
<link rel="stylesheet" type="text/css"
	href='./resources/css/dataTables.tableTools.css'>
<!-- DataTable -->

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
	    a.download = 'Discrepancy-Demand_' + Math.floor((Math.random() * 9999999) + 1000000) + '.xls';
	    a.click();
	  });
	});
</script>

<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-91"
	data-genuitec-path="/Currency/src/main/webapp/jsp/discrepancyDemand.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-91"
		data-genuitec-path="/Currency/src/main/webapp/jsp/discrepancyDemand.jsp">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">Discrepancy Demand</div>
						<div>
							<input type="button" class="btn btn-default qr-button"
								onclick="printDiv('printableArea')" value="Print" />
							<button id="btnExport" class="btn btn-default qr-button">Export
								to xls</button>
						</div>

						<!-- /.panel-heading -->
						<div class="panel-body">
							<!-- <div class="dataTable_wrapper"> -->

							<div class="region-con">
								<form:form id="userPage" name="userPage"
									action="discrepancyDemand" method="post"
									modelAttribute="reportDate" autocomplete="off">
									<div class="region-con-sec">
										<ul class="region-drop">
											<li>
												<table>
													<tr>
														<td><B>Select Date</B></td>
														<td><form:input type="text" path="fromDate"
																id="fromDate" name="fromDate" cssClass="form-control" />
														</td>
														<td><B>Select Category</B></td>
														<td><form:select path="normalOrSuspense"
																id="normalOrSuspense" name="normalOrSuspense"
																class="form-control deno-figure-select"
																style="width: 140px;">
																<option value="All">All</option>
																<option value="NORMAL">NORMAL</option>
																<option value="SUSPENSE">SUSPENSE</option>
															</form:select></td>
														<td><button type="submit" class="btn btn-default"
																value="Details" style="width: 99px;">Search</button></td>
													</tr>
												</table>
											</li>
										</ul>
									</div>
								</form:form>
							</div>


							<form id="showAll">
								<!-- <table class="table table-striped table-bordered table-hover" id="dataTables-example"> -->
								<div id="printableArea">
									<div id="table_wrapper">
										<table class="table table-striped table-bordered table-hover"
											id="tableValue">
											<thead>
												<tr>
													<th>Denomination</th>
													<th>Withdrawal Notes</th>
													<th>Deposit Notes</th>
												</tr>
											</thead>

											<tbody>
												<%-- <%
												List<Tuple> listTuple = (List<Tuple>) request.getAttribute("totalNotesList");
												for (Tuple tuple : listTuple) {
											%><tr>
												<td><%=tuple.get(0, Integer.class)%></td>
												<td><%=tuple.get(1, Integer.class)%></td>
											</tr>
											<%
												}
											%> --%>
												<tr></tr>
												<tr>
													<td>2000</td>
													<td>${deno2000W}</td>
													<td>${deno2000D}</td>
												</tr>
												<tr>
													<td>1000</td>
													<td>${deno1000W}</td>
													<td>${deno1000D}</td>
												</tr>
												<tr>
													<td>500</td>
													<td>${deno500W}</td>
													<td>${deno500D}</td>
												</tr>
												<tr>
													<td>200</td>
													<td>${deno200W}</td>
													<td>${deno200D}</td>
												</tr>
												<tr>
													<td>100</td>
													<td>${deno100W}</td>
													<td>${deno100D}</td>
												</tr>
												<tr>
													<td>50</td>
													<td>${deno50W}</td>
													<td>${deno50D}</td>
												</tr>
												<tr>
													<td>20</td>
													<td>${deno20W}</td>
													<td>${deno20D}</td>
												</tr>
												<tr>
													<td>10</td>
													<td>${deno10W}</td>
													<td>${deno10D}</td>
												</tr>
												<tr>
													<td>5</td>
													<td>${deno5W}</td>
													<td>${deno5D}</td>
												</tr>
												<tr>
													<td>2</td>
													<td>${deno2W}</td>
													<td>${deno2D}</td>
												</tr>
												<tr>
													<td>1</td>
													<td>${deno1W}</td>
													<td>${deno1D}</td>
												</tr>
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
	<!-- /#wrapper -->

	<!-- Bootstrap Core JavaScript -->
	<script
		src="./resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script
		src="./resources/bower_components/metisMenu/dist/metisMenu.min.js"></script>
	<script
		src="./resources/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="./resources/dist/js/sb-admin-2.js"></script>

	<!-- Page-Level Demo Scripts - Tables - Use for reference -->
	<script>
		$(document).ready(
				function() {
					var table = $('#tableValue').dataTable(
							{
								"pagingType" : "full_numbers",
								drawCallback : function() {
									var api = this.api();
									$(api.table().footer()).html(
											'<th colspan="5" style="text-align:right">Current Page Total:'
													+ api.column(4, {
														page : 'current'
													}).data().sum()
															.toLocaleString(
																	'en-IN')
													+ '; All Pages Total: '
													+ api.column(4).data()
															.sum()
															.toLocaleString(
																	'en-IN')
													+ '</th>');
								}
							});
					var tableTools = new $.fn.dataTable.TableTools(table, {

						'sSwfPath' : './js/copy_csv_xls_pdf.swf',
						'aButtons' : [ 'copy', {
							'sExtends' : 'print',
							'bShowAll' : false
						}, 'csv', {
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
	<script src="./resources/js/jquery.datetimepicker.js"></script>
	<script>
		$('#fromDate').datetimepicker({
			format : 'Y-m-d',
		});
	</script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
	<script type="text/javascript" src="./js/print.js"></script>

</body>

</html>