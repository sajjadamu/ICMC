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
<title>ICICI : Opening Balance For Suspense</title>

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
	var denomination = $('#denom'+str).text();
	var notes = $('#denomination'+str).val();
	var result = denomination*notes;
	$('#result'+str).val(result);
}

function doAjaxForTotal() { 
	var totalValue = null;
	
	var denom2000 = parseFloat($('#result'+2000).val());
	var denom1000 = parseFloat($('#result'+1000).val());
	var denom500 = parseFloat($('#result'+500).val());
	var denom200 = parseFloat($('#result'+200).val());
	var denom100 = parseFloat($('#result'+100).val());
	var denom50 = parseFloat($('#result'+50).val());
	var denom20 = parseFloat($('#result'+20).val());
	var denom10 = parseFloat($('#result'+10).val());
	var denom5 = parseFloat($('#result'+5).val());
	var denom2 = parseFloat($('#result'+2).val());
	var denom1 = parseFloat($('#result'+1).val());
	var noOfRows=11;
  
    	if(!isNaN(denom2000) && denom2000 != "" && denom2000 != null && denom2000 != 'undefined'){
    		totalValue=denom2000;
    	}if(!isNaN(denom1000) && denom1000 != "" && denom1000 != null && denom1000 != 'undefined'){
    		totalValue=totalValue+denom1000;
    	}if(!isNaN(denom500) && denom500 != "" && denom500 != null && denom500 != 'undefined'){
    		totalValue=totalValue+denom500;
    	}if(!isNaN(denom200) && denom200 != "" && denom200 != null && denom200 != 'undefined'){
    		totalValue=totalValue+denom200;
    	}if(!isNaN(denom100) && denom100 != "" && denom100 != null && denom100 != 'undefined'){
    		totalValue=totalValue+denom100;
    	}if(!isNaN(denom50) && denom50 != "" && denom50 != null && denom50 != 'undefined'){
    		totalValue=totalValue+denom50;
    	}if(!isNaN(denom20) && denom20 != "" && denom20 != null && denom20 != 'undefined'){
    		totalValue=totalValue+denom20;
    	}if(!isNaN(denom10) && denom10 != "" && denom10 != null && denom10 != 'undefined'){
    		totalValue=totalValue+denom10;
    	}if(!isNaN(denom5) && denom5 != "" && denom5 != null && denom5 != 'undefined'){
    		totalValue=totalValue+denom5;
    	}if(!isNaN(denom2) && denom2 != "" && denom2 != null && denom2 != 'undefined'){
    		totalValue=totalValue+denom5;
    	}if(!isNaN(denom1) && denom1 != "" && denom1 != null && denom1 != 'undefined'){
    		totalValue=totalValue+denom1;
    	}
	
	$('#totalAmount').val(totalValue.toLocaleString('en-IN'));
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
						<div class="panel-heading">Suspense Opening Balance</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<form:form id="supenseOpeningBalancePage"
								name="supenseOpeningBalancePage"
								action="saveSuspenseOpeningBalance" method="post"
								modelAttribute="user" autocomplete="off">
								<table style="width: 0%" id="table1">
									<tr>
										<th>Denomination</th>
										<th>Notes</th>
										<th>Total</th>
									</tr>
									<tr>
										<td id="denom${2000}">2000</td>
										<td><form:input path="denomination2000"
												id="denomination${2000}" name="denomination2000"
												cssClass="form-control"
												onChange="notesCalcuation(${2000});doAjaxForTotal()" /></td>
										<td><input class="form-control input-margin" type="text"
											id="result${2000}" value="" readonly></td>
									</tr>
									<tr>
										<td id="denom${1000}">1000</td>
										<td id="notes${1000}"><form:input path="denomination1000"
												id="denomination${1000}" name="denomination1000"
												cssClass="form-control"
												onChange="notesCalcuation(${1000});doAjaxForTotal()" /></td>
										<td><input class="form-control input-margin" type="text"
											id="result${1000}" value="" readonly></td>

									</tr>
									<tr>
										<td id="denom${500}">500</td>
										<td id="notes${500}"><form:input
												path="denomination${500}" id="denomination500"
												name="denomination500" cssClass="form-control"
												onChange="notesCalcuation(${500});doAjaxForTotal()" /></td>
										<td><input class="form-control input-margin" type="text"
											id="result${500}" value="" readonly></td>
									</tr>

									<tr>
										<td id="denom${200}">200</td>
										<td id="notes${200}"><form:input
												path="denomination${200}" id="denomination200"
												name="denomination200" cssClass="form-control"
												onChange="notesCalcuation(${200});doAjaxForTotal()" /></td>
										<td><input class="form-control input-margin" type="text"
											id="result${200}" value="" readonly></td>
									</tr>

									<tr>
										<td id="denom${100}">100</td>
										<td id="notes${100}"><form:input
												path="denomination${100}" id="denomination100"
												name="denomination100" cssClass="form-control"
												onChange="notesCalcuation(${100});doAjaxForTotal()" /></td>
										<td><input class="form-control input-margin" type="text"
											id="result${100}" value="" readonly></td>
									</tr>

									<tr>
										<td id="denom${50}">50</td>
										<td id="notes${50}"><form:input path="denomination${50}"
												id="denomination50" name="denomination50"
												cssClass="form-control"
												onChange="notesCalcuation(${50});doAjaxForTotal()" /></td>
										<td><input class="form-control input-margin" type="text"
											id="result${50}" value="" readonly></td>
									</tr>

									<tr>
										<td id="denom${20}">20</td>
										<td id="notes${20}"><form:input path="denomination${20}"
												id="denomination20" name="denomination20"
												cssClass="form-control"
												onChange="notesCalcuation(${20});doAjaxForTotal()" /></td>
										<td><input class="form-control input-margin" type="text"
											id="result${20}" value="" readonly></td>
									</tr>

									<tr>
										<td id="denom${10}">10</td>
										<td id="notes${10}"><form:input path="denomination${10}"
												id="denomination10" name="denomination10"
												cssClass="form-control"
												onChange="notesCalcuation(${10});doAjaxForTotal()" /></td>
										<td><input class="form-control input-margin" type="text"
											id="result${10}" value="" readonly></td>
									</tr>

									<tr>
										<td id="denom${5}">5</td>
										<td id="notes${5}"><form:input path="denomination${5}"
												id="denomination5" name="denomination5"
												cssClass="form-control"
												onChange="notesCalcuation(${5});doAjaxForTotal()" /></td>
										<td><input class="form-control input-margin" type="text"
											id="result${5}" value="" readonly></td>
									</tr>

									<tr>
										<td id="denom${2}">2</td>
										<td id="notes${2}"><form:input path="denomination${2}"
												id="denomination2" name="denomination2"
												cssClass="form-control"
												onChange="notesCalcuation(${2});doAjaxForTotal()" /></td>
										<td><input class="form-control input-margin" type="text"
											id="result${2}" value="" readonly></td>
									</tr>
									<tr>
										<td id="denom${1}">1</td>
										<td id="notes${1}"><form:input path="denomination${1}"
												id="denomination1" name="denomination1"
												cssClass="form-control"
												onChange="notesCalcuation(${1});doAjaxForTotal()" /></td>
										<td><input class="form-control input-margin" type="text"
											id="result${1}" value="" readonly></td>
									</tr>
									<tr>
										<td></td>
										<td>Total Amount</td>
										<td><input class="form-control input-margin" type="text"
											id="totalAmount" value="" readonly></td>
									</tr>
									<tr></tr>
									<Tr>
										<td></td>
										<td><button type="submit" value="Details">Save
												Opening Balance</button></td>
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