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
<title>ICICI : Welcome</title>

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

<style>
.chestslipreport {
	overflow-x: scroll;
	text-align: center;
	width: 1009px;
}

table, td {
	border: 1px solid black;
}

td {
	padding: 5px;
	margin: 5px;
}

td.tddatefmt {
	white-space: nowrap;
}

}
div {
	width: 100%;
}

.chestslipreport { /* overflow-x: auto; */
	text-align: center;
	font-size: 12px;
	width: 1009px;
}

.chestslipreport td {
	padding: 2px;
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

</head>
<body oncontextmenu="return false;">
	<div id="wrapper">
		<!-- Navigation -->
		<jsp:useBean id="now" class="java.util.Date" />
			
				<div><jsp:include page="logOut.jsp" /></div>
			
	
		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<h1 class="page-header">
							Welcome to
							<!-- e@ICMC -->
							Vault Management System
						</h1>
						<!-- /.panel-heading -->
						<div class="panel-body">

							status
							<c:out value="${status }" />
							<br /> message
							<c:out value="${message }" />
							<br /> Global Message
							<c:out value="${GlobalExceptionMessage }" />
							
							<br />


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

	<script src="./resources/js/jquery.datetimepicker.js"></script>
	<!-- 	<script>
		$('#fromDate').datetimepicker({
			format : 'Y-m-d',
		});
	</script> -->

	<script type="text/javascript" src="./js/htmlInjection.js"></script>
	<script type="text/javascript" src="./js/print.js"></script>

</body>

</html>