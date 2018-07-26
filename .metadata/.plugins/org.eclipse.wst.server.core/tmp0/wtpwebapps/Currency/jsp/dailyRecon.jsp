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
<link rel="shortcut icon" href="./resources/logo/favicon.ico"
	type="image/x-icon">
<title>ICICI : User Data</title>

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
<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script></head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-87" data-genuitec-path="/Currency/src/main/webapp/jsp/dailyRecon.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-87" data-genuitec-path="/Currency/src/main/webapp/jsp/dailyRecon.jsp">
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
						<div class="panel-heading">Daily Recon</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<form id="showAll">

									<table class="table table-striped table-bordered table-hover">
										<thead>
											<tr>
												<th>DENOMINATION</th>
												<th>ATM</th>
												<th>FRESH</th>
												<th>ISSUABLE</th>
												<th>UNPROCESSED</th>
												<th>SOILED</th>
												<th>TOTAL</th>
												<th>VALUE</th>
											</tr>
										</thead>
										<tbody align="center">
											<c:set var="TotalATM" value="${0}" />
											<c:set var="TotalFresh" value="${0}" />
											<c:set var="TotalIssuable" value="${0}" />
											<c:set var="TotalUnprocess" value="${0}" />
											<c:set var="TotalSoiled" value="${0}" />

											<c:set var="Total" value="${0}" />

											<c:forEach var="summaryRecord" items="${summaryRecords}">
												<tr>
													<td>${summaryRecord.key}</td>
													<td>${summaryRecord.value.ATM}</td>
													<td>${summaryRecord.value.fresh}</td>
													<td>${summaryRecord.value.issuable}</td>
													<td>${summaryRecord.value.unprocess}</td>
													<td>${summaryRecord.value.soiled}</td>
													<td>${summaryRecord.value.total}</td>
													<td></td>
												</tr>
												<c:set var="TotalATM"
													value="${TotalATM + summaryRecord.value.ATM}" />
												<c:set var="TotalFresh"
													value="${TotalFresh + summaryRecord.value.fresh}" />
												<c:set var="TotalIssuable"
													value="${TotalIssuable + summaryRecord.value.issuable}" />
												<c:set var="TotalUnprocess"
													value="${TotalUnprocess + summaryRecord.value.unprocess}" />
												<c:set var="TotalSoiled"
													value="${TotalSoiled + summaryRecord.value.soiled}" />
											</c:forEach>

											<c:set var="Total"
												value="${Total+TotalATM+TotalFresh+TotalIssuable+TotalUnprocess+TotalSoiled}" />

											<Tr>
												<td>TOTAL</td>
												<td>${TotalATM}</td>
												<td>${TotalFresh}</td>
												<td>${TotalIssuable}</td>
												<td>${TotalUnprocess}</td>
												<td>${TotalSoiled}</td>
												<td>${Total}</td>
												<td></td>
											</Tr>

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