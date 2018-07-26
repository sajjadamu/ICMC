<!DOCTYPE html>
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
<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.datetimepicker.css" />
<link rel="shortcut icon" href="./resources/logo/favicon.ico"
	type="image/x-icon">
<title>ICICI : Machine Down Time Register</title>

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
<style type="text/css">
		.reportbox{ border: none; font-family: arial; font-size: 13px; text-align: center;  }
		.reportbox td{ border: #ccc 1px solid; margin: 0; padding: 10px; vertical-align: top;}
		.reportpad{ padding: 0 !important; margin: 0; border-top: 0 !important; border-left: 0 !important; text-align: center; }
		.reportinnbox{ margin: 0; padding: 0;width: 100%; text-align: center; }
		.reportinnbox td{ padding: 10px; border:0; border: 1px solid #ccc; }
	
	
	</style>
<style type="text/css">
body {
	margin: 0;
	padding: 0;
}

.datewisebin {
	margin: 0;
	padding: 0;
}

.wisespace {
	padding: 0 30px;
	width: 120px;
}

.wisespace_opt {
	width: 100%;
}

.wisespace_opt td {
	width: 19.5%;
	display: table-cell;
}

table.treecol td:nth-child(1) {
	width: 67px;
}

table.treecol td:nth-child(2) {
	width: 78px;
}

table.twocol td:nth-child(1) {
	width: 90px;
}

table span {
	display: block;
	padding: 10px 0;
	font-weight: normal;
	font-size: 15px;
}
</style>

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
						<div class="panel-heading">Machine Down Time Register</div>
						<div>
							<input type="button" class="btn btn-default qr-button"
								onclick="printDiv('printableArea')" value="Print" />
								<button id="btnExport" class="btn btn-default qr-button">Export to xls</button>
						</div>

						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="region-con">
								<form:form id="userPage" name="userPage"
									action="machineDowntimeReport" method="post"
									modelAttribute="reportDate" autocomplete="off">
									<div class="region-con-sec">
										<ul class="region-drop">
											<li>
												<table>
													<tr>
														<td><B>Select From Date</B></td>
														<td><form:input type="text" path="fromDate"
																id="fromDate" name="fromDate" cssClass="form-control" />
														</td>
														<td><B>Select To Date</B></td>
														<td><form:input type="text" path="toDate"
																id="toDate" name="toDate" cssClass="form-control" />
														</td>
														<td><button type="submit" class="btn btn-default"
																value="Details" style="width: 99px;">Search</button></td>
													</tr>
												</table>
											</li>
										</ul>
									</div>
								</form:form>
							</div>

							<form>
								<div id="printableArea">
								<div id="table_wrapper">
									<table width="100%" cellpadding="0" cellspacing="0"
										style="font-family: arial;" class="datewisebin">
										<tr>
											<td>
												<table cellspacing="0" cellpadding="0" border="0" wi>
													<tr>
														<td width="25%"><img
															src="./resources/logo/logobw.png"></td>
														<td style="text-align: center;" width="90%">
															<table cellspacing="0" cellpadding="5"
																style="margin: 0 auto;">
																<tr>
																	<td colspan="2"><h2 style="margin: 0; padding: 0;">ICICI
																			BANK LIMITED</h2></td>
																</tr>
																<tr>
																	<td style="font-size: 18px; text-align: right;"><b>Currency
																			Chest:</b></td>
																	<td
																		style="width: 150px; border-bottom: #ccc 1px solid;"></td>
																</tr>
																<tr>
																	<td colspan="2"><h2
																			style="margin: 0; padding: 0;">MACHINE DOWN TIME
																			REGISTER</h2></td>
																</tr>
															</table>
														</td>

													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td>

												<table class="reportbox" cellspacing="0" cellpadding="0">
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td>A</td>
		<td>B</td>
		<td>C</td>
		<td>D</td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>

	</tr>
	<tr>
		<td>Date</td>
		<td>Type of<br>Machine</td>
		<td>Machine <br>Serial No.</td>
		<td>Date & Time <br>when the <br>Issue is<br> reported to <br>G&D Engineer</td>
		<td>Date & Time <br>when the <br>Engineer <br>attended the <br>call</td>
		<td>Date & Time <br>when the call <br>is completed <br>and Machine <br>is up</td>
		<td>Total time <br>taken for up-<br>time of the <br>Machine (D = <br>C - A)</td>
		<td>Problem <br>description</td>
		<td>Productivity loss <br>(in Bundles) due <br>to down time - <br>As per BSC <br>parameter</td>
		<td>Remarks</td>
		<td>Signature of <br>Ibank Official</td>
		<td> Signature of <br>G&D Engineer</td>
	</tr>
	<c:forEach var="row" items="${records}">
														<tr class="eqq-con bb">
															<td><fmt:formatDate pattern="dd-MMM-yy hh:mm:ss"
																	value="${row.machineDownDateFrom}" /></td>
																	<td>${row.machineType}</td>
															<td>${row.machineNo}</td>
															<td><fmt:formatDate pattern="dd-MMM-yy hh:mm:ss"
																	value="${row.machineDownDateFrom}" /></td>
																	<td><fmt:formatDate pattern="dd-MMM-yy HH:mm:ss"
																	value="${row.engineerAttendedCall}" /></td>
																	<td><fmt:formatDate pattern="dd-MMM-yy HH:mm:ss"
																	value="${row.machineDownDateTo}" /></td>
																	<td>${row.hours}</td>
																	<td>${row.downtimeReason}</td>
																	<td>${row.hours * 25}</td>
																	<td>${row.remarks}</td>
																	<td></td>
																	<Td></Td>
														</tr>
													</c:forEach>
</table>

											</td>
										</tr>

									</table>
								</div></div>

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

	<script type="text/javascript" src="./js/htmlInjection.js"></script>
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
</body>

</html>