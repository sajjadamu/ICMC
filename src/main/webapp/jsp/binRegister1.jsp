<!DOCTYPE html>
<%@page import="java.util.Calendar"%>
<%@page import="com.mysema.query.Tuple"%>
<%@page import="java.util.List"%>
<%@page import="java.math.BigDecimal"%>
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
<title>ICICI : Bin Register</title>

<!-- Bootstrap Core CSS -->
<link
	href="./resources/bower_components/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- MetisMenu CSS -->
<link
	href="./resources/bower_components/metisMenu/dist/metisMenu.min.css"
	rel="stylesheet">

<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.datetimepicker.css" />

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
<style>
.icicibox {
	width: 1024px;
	margin: 0 auto;
	font-family: arial;
}

.icicwrap {
	width: 100%;
}

.icicwrap th {
	text-align: center;
}

.icicibox h2 {
	margin: 0;
	padding: 0;
}

.icicwrap td:first-child {
	width: 25%
}

.icicwrap td:nth-child(2) {
	width: 50%;
	text-align: center;
	line-height: 28px;
}

.icicwrap td:nth-child(3) {
	width: 25%;
}

.border_box {
	border: #ccc 1px solid;
}

.border_box th {
	border: #ccc 1px solid;
	padding: 8px;
}

.border_box td {
	padding: 10px;
	border: #ccc 1px solid;
}
</style>

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
						<div class="panel-heading">Bin Register</div>
						<!-- /.panel-heading -->
						<div class="panel-body">

							<div class="region-con">
								<form:form id="userPage" name="userPage" action="binRegister1"
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
														<td><B>Bin Number</B></td>
														<td><form:input type="text" path="binNumber"
																id="binNumber" name="binNumber" cssClass="form-control" /></td>
														<td><button type="submit" class="btn btn-default"
																value="Details" style="width: 99px;">Search</button></td>
													</tr>
												</table>
											</li>
										</ul>
									</div>
								</form:form>
							</div>


							<div class="dataTable_wrapper">
								<div>
									<input type="button" class="btn btn-default qr-button"
										onclick="printDiv('printableArea')" value="Print" />
									<button id="btnExport" class="btn btn-default qr-button">Export
										to xls</button>
								</div>

							</div>
							<form id="showAll">
								<!-- <table class="table table-striped table-bordered table-hover" id="dataTables-example"> -->
								<div id="printableArea">

									<div class="icicibox">
										<table class="icicwrap" cellpadding="0" cellspacing="0"
											style="text-align: center;">
											<tr>
												<td><img src="./resources/logo/logobw.png"></td>
												<td style="text-align: center;"><h2>ICICI BANK
														LIMITED</h2> Currency Chest:___________________<br> Bin
													Wise Register</td>
												<td></td>
											</tr>
											<tr>
												<td colspan="3">
													<table style="width: 100%;" cellpadding="0" cellspacing="0"
														class="border_box">
														<tr>
															<th></th>
															<th></th>
															<th colspan="3">Number of Bundles</th>
															<th>Value</th>
															<th colspan="2">Signature</th>
															<th></th>
														</tr>
														<tr>
															<th>Date</th>
															<th>Denomination</th>
															<th>Diposite</th>
															<th>Withdrawal</th>
															<th>Balance</th>
															<th>Value</th>
															<th>Cash Officer</th>
															<th style="white-space: nowrap;">Chest <br>In-charge
															</th>
															<th>Remarks</th>
														</tr>
														<%
													List<Tuple> binRegisterList = (List<Tuple>) request.getAttribute("deposit");

													BigDecimal balance = BigDecimal.ZERO;
													BigDecimal finalBalance = BigDecimal.ZERO;
                                                    BigDecimal values = BigDecimal.ZERO; 
													
													for (Tuple tuple : binRegisterList) {

														balance = balance.add(tuple.get(1, BigDecimal.class).subtract(tuple.get(2, BigDecimal.class)));
												%>

												<tr>
													<td><fmt:formatDate pattern="yyyy-MM-dd"
															value="<%=tuple.get(3, Calendar.class).getTime()%>" /></td>
													<td><%=tuple.get(0, Integer.class)%></td>
													<td><%=tuple.get(1, BigDecimal.class)%></td>
													<td><%=tuple.get(2, BigDecimal.class)%> 
													<td><%=balance%></td>
													<td><%=balance.multiply(new BigDecimal(tuple.get(0, Integer.class)).multiply(new BigDecimal(1000)))%></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
												<%
													}
												%>
													</table>

												</td>
											</tr>
										</table>
									</div>



								</div>
							</form>
							<!-- </div> DataTable_wrapper bundle div close -->
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
</body>

</html>