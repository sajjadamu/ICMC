<!DOCTYPE html>
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
<title>ICICI : View Prepared Soiled Boxes</title>
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
</head>

<script type="text/javascript">

function doAjaxPostCancel(id){
	addHeaderJson();
	var idFromUI = id;

	if (confirm('Are you sure you want to cancel this prepered soiled box?')) {
		$("#cancel"+idFromUI).prop('disabled',true);
		
		$.ajax({
			type : "POST",
			url : "././",
			data : "idFromUI=" + idFromUI,
			success : function(response) {
				//alert('response: ' +response.responseJSON.message);
				alert('Record Cancelled.');
				window.location='././viewORV';
			},
			error : function(e) {
				//alert('Error Occured: ' + e);
				alert(e.responseJSON.message);
				//window.location='././viewORV';
			}
		});
	} else {
	    // Do nothing!
	}
	
}</script>

<body oncontextmenu="return false;">
	<div id="wrapper">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />
		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<ul>
								<li>
									<%-- <sec:authorize access="hasRole('ADD_DIVERSION_PAYMENT')"> --%>
									<a href="././soiledPreparation"><i
										class="fa fa-table fa-fw"></i> Preparation for Soiled
										Remittance</a> <%-- </sec:authorize> --%>
								</li>
							</ul>
							View Prepared Soiled Boxes
						</div>
						<div>
							<input type="button" class="btn btn-default qr-button"
								onclick="printDiv('printableArea')" value="Print" />
							<button id="btnExport" class="btn btn-default qr-button">Export
								to xls</button>
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<form id="showAll">
									<div class="containerr" id="printableArea">
										<div id="table_wrapper">
											<table class="table table-striped table-bordered table-hover"
												id="tableValue">
												<thead>
													<tr>
														<th>Box No.</th>
														<th>BOX Name</th>
														<th>Denomination</th>
														<th>Available Bundle</th>
														<th>Category</th>
														<th>Prepared Date</th>
														<th>Total Value</th>
														<th>Cancel</th>
													</tr>
												</thead>
												<tfoot>
													<tr>
														<th style="text-align: right">Total:</th>
													</tr>
												</tfoot>
												<tbody>
													<c:forEach var="row" items="${records}">
														<c:set var="count" value="${count + 1}" scope="page" />
														<tr>
															<td>${count}</td>
															<td>${row.binNumber}</td>
															<td>${row.denomination}</td>
															<td>${row.receiveBundle}</td>
															<td>${row.binType}</td>
															<td><fmt:formatDate pattern="dd-MMM-yy"
																	value="${row.insertTime.time}" /></td>
															<td>${row.value}</td>
															<td><input id="cancel${row.id}" type="button"
																value="Cancel" onclick="doAjaxPostCancel(${row.id});"></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>

									</div>
								</form>
							</div>
							<!-- /.table-responsive -->
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
		$(document).ready(
				function() {
					$('#tableValue').dataTable(
							{
								"pagingType" : "full_numbers",
								drawCallback : function() {
									var api = this.api();
									$(api.table().footer()).html(
											'<th colspan="6" style="text-align:right">Current Page Total: '
													+ api.column(6, {
														page : 'current'
													}).data().sum()
															.toLocaleString(
																	'en-IN')
													+ '; All Pages Total: '
													+ api.column(6).data()
															.sum()
															.toLocaleString(
																	'en-IN')
													+ '</th>');
								}
							});
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
	<script type="text/javascript" src="./js/print.js"></script>
</body>

</html>