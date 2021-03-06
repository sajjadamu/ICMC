<!DOCTYPE html>
<html lang="en">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="./resources/logo/yes_favicon.ico"
	type="image/x-icon">
<title> Machine Wise Status</title>

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
<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-166"
	data-genuitec-path="/Currency/src/main/webapp/jsp/machineWiseStatus.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-166"
		data-genuitec-path="/Currency/src/main/webapp/jsp/machineWiseStatus.jsp">
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
						<div class="panel-heading">Machine wise Status</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<form id="showAll">

									<table width="100%" border="1" align="center" cellpadding="0"
										cellspacing="0"
										class="table table-striped table-bordered table-hover">
										<tbody>
											<tr align="center">
												<td>
													<table width="100%" border="1" cellpadding="0"
														cellspacing="0">
														<tbody>
															<BR>
															<BR>
															<tr align="center">
																<td colspan="3">MACHINE</td>
															</tr>
															<c:forEach var="row" items="${machineList}">
																<tr align="center">
																	<td>${row.machineNo}</td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</td>
												<td>
													<table width="100%" border="1" cellpadding="0"
														cellspacing="0">
														<tbody>
															<tr>
																<td style="border: none;" colspan="3" align="center">INPUT</td>
															</tr>
															<tr align="center">
																<td colspan="3">&nbsp;</td>
															</tr>
															<tr align="center">
																<td>Deno</td>
																<td>Bundle</td>
																<td>Value</td>
															</tr>
															<tr align="center">
																<c:forEach var="row" items="${machineList}">
																	<c:set var="MachineAllocationResult"
																		value="${row.denomination * row.bundle*1000}" />
																	<tr>
																		<td>${row.denomination}</td>
																		<td>${row.bundle}</td>
																		<td>${MachineAllocationResult}</td>
																	</tr>
																</c:forEach>
															</tr>

														</tbody>
													</table>
												</td>
												<td style="vertical-align: top;"><table width="100%"
														border="0" cellpadding="0" cellspacing="0">
														<thead>OUTPUT
														</thead>
														<tbody>
															<c:set var="ATMresult" />
															<c:set var="IssuableResult" />
															<c:set var="SoiledResult" />
															<c:set var="TOTAL" value="${0}" />
															<tr>
																<td><table width="100%" border="1" cellpadding="0"
																		cellspacing="0">
																		<tbody>

																			<tr>
																				<td colspan="3" align="center">ATM</td>
																			</tr>
																			<tr align="center">
																				<td>Deno</td>
																				<td>Bundle</td>
																				<td>Value</td>
																			</tr>
																			<tr align="center">
																				<c:forEach var="row" items="${processListAtm }">
																					<c:set var="ATMresult"
																						value="${row.denomination * row.bundle*1000}" />
																					<tr>
																						<td>${row.denomination}</td>
																						<td>${row.bundle}</td>
																						<Td>${ATMresult}</Td>
																					</tr>
																				</c:forEach>
																		</tbody>
																	</table></td>
																<td><table width="100%" border="1" cellpadding="0"
																		cellspacing="0">
																		<tbody>
																			<tr>
																				<td colspan="3" align="center">ISSUABLE</td>
																			</tr>
																			<tr align="center">
																				<td>Deno</td>
																				<td>Bundle</td>
																				<td>Value</td>
																			<tr align="center">

																				<c:forEach var="row" items="${processListIssuable }">
																					<c:set var="IssuableResult"
																						value="${row.denomination * row.bundle*1000}" />
																					<tr>
																						<td>${row.denomination}</td>
																						<td>${row.bundle}</td>
																						<td>${IssuableResult}</td>
																					</tr>
																				</c:forEach>
																		</tbody>
																	</table></td>
																<td><table width="100%" border="1" cellpadding="0"
																		cellspacing="0">
																		<tbody>
																			<tr align="center">
																				<td colspan="3">SOILED</td>
																			<tr align="center">
																				<td>Deno</td>
																				<td>Bundle</td>
																				<td>Value</td>
																			<tr align="center">
																				<c:forEach var="row" items="${processListSoiled }">
																					<c:set var="SoiledResult"
																						value="${row.denomination * row.bundle*1000}" />
																					<tr>
																						<td>${row.denomination}</td>
																						<td>${row.bundle}</td>
																						<td>${SoiledResult}</td>
																					</tr>
																				</c:forEach>
																		</tbody>
																	</table></td>
																<td><table width="100%" border="1" cellpadding="0"
																		cellspacing="0">
																		<tbody>
																			<tr align="center">
																				<td colspan="2">DISCREPANCY</td>
																			</tr>
																			<tr align="center">
																				<td>Deno</td>
																				<td>Value</td>
																			<tr align="center">
																				<td colspan="3">&nbsp;&nbsp;</td>
																			</tr>
																			<tr align="center">
																				<td colspan="3">&nbsp;&nbsp;</td>
																			</tr>
																		</tbody>
																	</table></td>
															</tr>
														</tbody>
													</table></td>
												<td>
													<table width="100%" border="1" cellpadding="0"
														cellspacing="0">
														<tbody>
															<tr align="center">
																<td colspan="3">Total</td>
															</tr>
															<tr align="center">
																<td colspan="3">&nbsp;</td>
															<tr align="center">
																<td colspan="3">&nbsp;</td>

															</tr>

															<%-- <tr align="center">
																<c:set var="TOTAL"
																	value="${ATMresult+IssuableResult+SoiledResult}" />
																<td colspan="3">${TOTAL}</td>
															</tr> --%>

														</tbody>
													</table>
												</td>

											</tr>
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