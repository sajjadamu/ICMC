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
<title>ICICI : IndentVsPayment</title>

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
						<div class="panel-heading">Indent Vs Payment</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div>
								<div>
									<input type="button" class="btn btn-default qr-button"
										onclick="printDiv('printableArea')" value="Print" />
									<button id="btnExport" class="btn btn-default qr-button">Export
										to xls</button>
								</div>
							</div>
							<form id="showAll">
								<div id="printableArea">
									<div id="table_wrapper">
										<table border="1" style="text-align: center; width: 50%">
											<tr>
												<th>Denomination</th>
												<th>2000</th>
												<th>1000</th>
												<th>500</th>
												<th>200</th>
												<th>100</th>
												<th>50</th>
												<th>20</th>
												<th>10</th>
												<th>5</th>
												<th>2</th>
												<th>1</th>
											</tr>
											<tr>
												<c:forEach var="row" items="${craProcessedDataList}">
													<tr>
														<td>Receive bundle</td>
														<td>${row.denom2000Pieces}</td>
														<td>${row.denom1000Pieces}</td>
														<td>${row.denom500Pieces}</td>
														<td>${row.denom200Pieces}</td>
														<td>${row.denom100Pieces}</td>
														<td>${row.denom50Pieces}</td>
														<td>${row.denom20Pieces}</td>
														<td>${row.denom10Pieces}</td>
														<td>${row.denom5Pieces}</td>
														<td>${row.denom2Pieces}</td>
														<td>${row.denom1Pieces}</td>
													</tr>
												</c:forEach>
											</tr>
											
											<tr>
												<c:forEach var="row" items="${craReleased}">
													<tr>
														<td>Released Bundle</td>
														<td>${row.denom2000Pieces}</td>
														<td>${row.denom1000Pieces}</td>
														<td>${row.denom500Pieces}</td>
														<td>${row.denom200Pieces}</td>
														<td>${row.denom100Pieces}</td>
														<td>${row.denom50Pieces}</td>
														<td>${row.denom20Pieces}</td>
														<td>${row.denom10Pieces}</td>
														<td>${row.denom5Pieces}</td>
														<td>${row.denom2Pieces}</td>
														<td>${row.denom1Pieces}</td>
													</tr>
												</c:forEach>
											</tr>
											
											<tr>
													<tr>
														<td>Pending Bundle</td>
														<td>${pendingBundle2000}</td>
														<td>${pendingBundle1000}</td>
														<td>${pendingBundle500}</td>
														<td>${pendingBundle200}</td>
														<td>${pendingBundle100}</td>
														<td>${pendingBundle50}</td>
														<td>${pendingBundle20}</td>
														<td>${pendingBundle10}</td>
														<td>${pendingBundle5}</td>
														<td>${pendingBundle2}</td>
														<td>${pendingBundle1}</td>
													</tr>
											</tr>
										</Table>
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

	<script
		src="./resources/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="./resources/dist/js/sb-admin-2.js"></script>

	<!-- Page-Level Demo Scripts - Tables - Use for reference -->
	<script>
		$(document).ready(function() {
			var table = $('#tableValue').dataTable({
				"pagingType" : "full_numbers",
			});
			var tableTools = new $.fn.dataTable.TableTools(table, {

				/* 'sSwfPath':'//cdn.datatables.net/tabletools/2.2.4/swf/copy_csv_xls_pdf.swf', */
				'sSwfPath' : './js/copy_csv_xls_pdf.swf',
				'aButtons' : [ 'copy',/* {
									    		'sExtends':'print',
									    		'bShowAll':false
									    		 }, */
				'csv', {
					'sExtends' : 'xls',
					'sFileName' : '*.xls',
					'sButtonText' : ' Excel'
				}, {
					'sExtends' : 'pdf',
					'bFooter' : false
				} ]
			});
			$(tableTools.fnContainer()).insertBefore('.dataTable_wrapper');
		});
	</script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
	<script type="text/javascript" src="./js/print.js"></script>
</body>

</html>