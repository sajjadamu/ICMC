<!DOCTYPE html>
<html lang="en">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
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
<title>ICICI : FULL VALUE</title>

<style type="text/css">
.panel.panel-default .btn-success {
	margin-top: 20px;
	position: absolute;
	right: 20px;
	font-size: 14px;
	width: 82px;
	background: #d85600;
	padding: 7px;
	top: -15px;
}
</style>
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

<script type="text/javascript">
	function notesCalcuation(str) {
		var denomination = $('#denom' + str).text();
		var notes = $('#denomination' + str).val();
		var result = denomination * notes;
		$('#result' + str).val(result);
	}

	function doAjaxForTotal() {
		var myTotalValue = null;

		var denom2000 = parseFloat($('#result' + 2000).val());
		var denom1000 = parseFloat($('#result' + 1000).val());
		var denom500 = parseFloat($('#result' + 500).val());
		var denom500 = parseFloat($('#result' + 100).val());
		var denom200 = parseFloat($('#result' + 200).val());
		var denom100 = parseFloat($('#result' + 100).val());
		var denom50 = parseFloat($('#result' + 50).val());
		var denom20 = parseFloat($('#result' + 20).val());
		var denom10 = parseFloat($('#result' + 10).val())
		var denom5 = parseFloat($('#result' + 5).val())
		var denom2 = parseFloat($('#result' + 2).val())
		var denom1 = parseFloat($('#result' + 1).val());
	}
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
						<div class="panel-heading">
							<ul>
								<li><a href="././viewMutilatedData"><i
										class="fa fa-table fa-fw"></i> View Mutilated Data</a></li>
							</ul>
							Mutilated Full Value
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<form:form id="supenseOpeningBalancePage"
								name="supenseOpeningBalancePage" action="saveMutilatedFullValue"
								method="post" modelAttribute="user" autocomplete="off">
								<table style="width: 0%" id="table1">
									<tr>
										<th>Denomination&nbsp;&nbsp;&nbsp;</th>
										<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pieces</th>

									</tr>
									<tr>

										<td>2000</td>
										<td><input type="number" name="deno2000" min="1"
											class="form-control input-margin"></td>
									</tr>
									<tr>

										<td>1000</td>
										<td><input type="number" name="deno1000" min="1"
											class="form-control input-margin"></td>
									</tr>
									<tr>

										<td>500</td>
										<td><input type="number" name="deno500" min="1"
											class="form-control input-margin"></td>
									</tr>
									<tr>

										<td>200</td>
										<td><input type="number" name="deno200" min="1"
											class="form-control input-margin"></td>
									</tr>
									<tr>

										<td>100</td>
										<td><input type="number" name="deno100" min="1"
											class="form-control input-margin"></td>
									</tr>
									<tr>
										<td>50</td>
										<td><input type="number" name="deno50" min="1"
											class="form-control input-margin"></td>
									</tr>
									<tr>

										<td>20</td>
										<td><input type="number" name="deno20" min="1"
											class="form-control input-margin"></td>
									</tr>
									<tr>

										<td>10</td>
										<td><input type="number" name="deno10" min="1"
											class="form-control input-margin"></td>
									</tr>
									<tr>

										<td>5</td>
										<td><input type="number" name="deno5" min="1"
											class="form-control input-margin"></td>
									</tr>
									<tr>

										<td>2</td>
										<td><input type="number" name="deno2" min="1"
											class="form-control input-margin"></td>
									</tr>
									<tr>

										<td>1</td>
										<td><input type="number" name="deno1" min="1"
											class="form-control input-margin"></td>
									</tr>
									<tr></tr>
									<Tr>
										<td></td>
										<td><button type="submit" value="Details"
												class="btn btn-default">Save Mutilated Details</button></td>
									</Tr>
								</table>
							</form:form>
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
			$('#tableValue').dataTable({
				"pagingType" : "full_numbers",
			});
		});
	</script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>