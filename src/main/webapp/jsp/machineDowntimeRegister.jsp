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
						</div>

						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="region-con">
								<form:form id="userPage" name="userPage" action="chestSlip"
									method="post" modelAttribute="reportDate" autocomplete="off">
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

							<form>
								<div id="printableArea">
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

												<table width="100%" border="1">

													<tr class="eqq-con bb">
														<td class="eqquall"></td>
														<td class="eqquall"></td>
														<td class="eqquall"></td>
														<td class="eqquall">A</td>
														<td class="eqquall">B</td>
														<td class="eqquall">C</td>
														<td class="eqquall">D</td>
														<td class="eqquall"></td>
														<td class="eqquall"></td>
														<td class="eqquall"></td>
														<td class="eqquall"></td>
														<td class="eqquall"></td>
													</tr>

													<tr class="eqq-con bb">
														<td class="eqquall">Date</td>
														<td class="eqquall">Type of Machine</td>
														<td class="eqquall">Machine Serial No.</td>
														<td class="eqquall">Date & Time When the issue is
															reported to G&D Engineer</td>
														<td class="eqquall">Date & Time When the Engineer
															attended the Call</td>
														<td class="eqquall">Date & Time When the Call is
															completed and Machine is Up</td>
														<td class="eqquall">Total time taken for uptime of
															the machine (D=C-A)</td>
														<td class="eqquall">Problem Description</td>
														<td class="eqquall">loss (in Bundles) due to down
															time - As per BSC parameter</td>
														<td class="eqquall">Remarks</td>
														<td class="eqquall">Signature of Ibank Official</td>
														<td class="eqquall">Signature of G&D Engineer</td>
													</tr>

													<tr class="eqq-con bb">
														<td class="eqquall">.</td>
														<td class="eqquall"></td>
														<td class="eqquall"></td>
														<td class="eqquall"></td>
														<td class="eqquall"></td>
														<td class="eqquall"></td>
														<td class="eqquall"></td>
														<td class="eqquall"></td>
														<td class="eqquall"></td>
														<td class="eqquall"></td>
														<td class="eqquall"></td>
														<td class="eqquall"></td>
													</tr>

													<tr class="eqq-con bb">
														<td class="eqquall"></td>
														<td class="eqquall"></td>
														<td class="eqquall"></td>
														<td class="eqquall"></td>
														<td class="eqquall"></td>
														<td class="eqquall"></td>
														<td class="eqquall"></td>
														<td class="eqquall"></td>
														<td class="eqquall"></td>
														<td class="eqquall">.</td>
														<td class="eqquall"></td>
														<td class="eqquall"></td>
													</tr>

													<tr class="eqq-con bb">
														<td class="eqquall"></td>
														<td class="eqquall"></td>
														<td class="eqquall"></td>
														<td class="eqquall"></td>
														<td class="eqquall"></td>
														<td class="eqquall">`</td>
														<td class="eqquall"></td>
														<td class="eqquall"></td>
														<td class="eqquall"></td>
														<td class="eqquall"></td>
														<td class="eqquall"></td>
														<td class="eqquall"></td>
													</tr>


												</table>

											</td>
										</tr>

									</table>
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

	<script type="text/javascript" src="./js/htmlInjection.js"></script>
	<script type="text/javascript" src="./js/print.js"></script>

</body>

</html>