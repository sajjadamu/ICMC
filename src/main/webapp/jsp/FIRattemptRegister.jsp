<!DOCTYPE html>
<%@page import="java.util.Date"%>
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
<link href="./resources/css/calendar.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.datetimepicker.css" />
<title>ICICI : FIR Attempt Register</title>


<style type="text/css">
body {
	font-family: arial;
}

.container {
	width: 100%;
}

table, th, td {
	border: 1px solid black;
	border-collapse: collapse;
	padding: 5px;
}

.lg {
	width: 40%;
}

.sm {
	width: 7%;
	text-align: center;
	font-family: arial;
	font-size: 14px;
}

td.first {
	width: 20%;
}

td.a {
	width: 12%;
}

td.c {
	width: 120px;
}

td.a.cc {
	text-align: center;
}

td.a.none-border-1 {
	border-top: 1px solid #fff;
}

td.a.none-border {
	border-bottom: 1px solid #fff;
}

tr.eqq-con td {
	text-align: center;
	font-size: 11px;
}

td.a.none-border-1.no {
	text-align: left;
}

td.a.none-border-1.no.no-1 {
	text-align: center;
}

td.z-1 {
	width: 72.6%;
	text-align: center;
}

td.z-2 {
	width: 20.1%;
	text-align: center;
	border-bottom: 1px solid white;
}

td.qr {
	width: 6.9%;
}

td.center-c {
	width: 48.3%;
	text-align: center;
}

td.qrr {
	width: 7%;
}

tr.larg {
	height: 76px;
}

tr.eqq-con.bb td {
	padding: 0px;
}

td.nonee {
	border-top: 1px solid #fff;
}

td.a.cc.none-border-1.line {
	position: relative;
	top: -27px;
}

td.eqquall {
	width: 122px;
}

.containerr tr td {
	padding: 5px;
}
</style>

<style type="text/css">
.region-con h1 {
	font-size: 22px;
	font-weight: 200;
	margin-top: 20px;
	margin-bottom: 10px;
}

.region-con-sec {
	float: left;
	width: 100%;
	border-top: 1px solid #eee;
	border-bottom: 1px solid #eee;
	padding-top: 13px;
}

.region-con-drop {
	width: 50%; /* text-align: right; */
	float: left;
	padding-right: 30px;
	box-sizing: border-box;
}

.region-con-drop span {
	font-size: 18px;
	margin-right: 18px;
}

.region-con-drop select {
	width: 30%;
	padding: 7px;
}

.region-drop {
	width: 100%;
	float: left;
}

.region-drop li {
	float: left;
	margin-right: 20px;
}

.region-drop li label {
	float: left;
	margin-right: 10px;
	line-height: 34px;
}

.region-drop span {
	float: left;
}

.DTTT.btn-group {
	width: 100%;
	float: right;
	margin: 10px 0;
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
						<div class="panel-heading">FIR ATTEMPT REGISTER</div>
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

							<form id="showAll">
								<!-- <table class="table table-striped table-bordered table-hover" id="dataTables-example"> -->

								<div class="containerr" id="printableArea">

									<table width="100%">

										<tr class="eqq-con bb">
											<td class="eqquall">SR.No.</td>
											<td class="eqquall">Date</td>
											<td class="eqquall">Details of Tenderer</td>
											<td class="eqquall">Deno.</td>
											<td class="eqquall">Number of Pieces</td>
											<td class="eqquall">Value</td>
											<td class="eqquall">Officer with whom met</td>
											<td class="eqquall">Reference Number Covering letter</td>
											<td class="eqquall">FIR accept/Not accept</td>
											<td class="eqquall">Reason for Non-acceptance of FIR</td>
											<td class="eqquall">Name and Address of the Police
												station</td>
											<td class="eqquall">ICMC Officer who visited the Police
												station</td>

										</tr>

										<tr class="eqq-con">
											<td class="eqquall">01</td>
											<td class="eqquall">31.08.2012</td>
											<td class="eqquall">As per Annexature</td>
											<td class="eqquall">As per Annexature</td>
											<td class="eqquall">7198</td>
											<td class="eqquall">38,12,400</td>
											<td class="eqquall">S I Jagrup Singh</td>
											<td class="eqquall">As Per Annexature</td>
											<td class="eqquall">Yes</td>
											<td class="eqquall">FIR 173 Dated 31.08.12 20120173</td>
											<td class="eqquall">P.S Pahar Ganj</td>
											<td class="eqquall">Mr. Tarunesh Sharan</td>
										</tr>

									</table>
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
	 $(document).ready(function () {
	    	 var tableTools=new $.fn.dataTable.TableTools(table,{
	    		
	    		 'sSwfPath':'./js/copy_csv_xls_pdf.swf',
	    		 'aButtons':['copy',{
	    		'sExtends':'print',
	    		'bShowAll':false
	    		 },
	    		 'csv',
	    		 {
	    			 'sExtends':'xls',
	    			 'sFileName':'*.xls',
	    			 'sButtonText':' Excel'
	    		 },
	    		 {
	    			 'sExtends':'pdf',
	    			 'bFooter':false
	    		 }
	    		 ] 
	    	 });
	    	$(tableTools.fnContainer()).insertBefore('.dataTable_wrapper');
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