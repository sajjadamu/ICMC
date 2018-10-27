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
<title>ICICI : View BOX Details</title>

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

</head>
<body oncontextmenu="return false;">
	<div id="wrapper">
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

						<div class="panel-heading">
							<ul>
								<li><a href="././createBox"><i
										class="fa fa-table fa-fw"></i> Add New Box</a></li>
							</ul>
							View Box Details
						</div>

						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<form id="showAll">
									<div align="center" style="color: white; background: red;">
										<b>${errorMsg}</b>
									</div>
									<br>
									<div align="center" style="color: white; background: green;">
										<b>${successMsg}</b>
									</div>
									<br>
									<div align="center" style="color: white; background: green;">
										<b>${updateMsg}</b>
									</div>
									<br>

									<table class="table table-striped table-bordered table-hover"
										id="tableValue">
										<thead>
											<tr>
												<th>Box name</th>
												<th>Capacity</th>
												<th>Denomination</th>
												<!-- <th>Cash Source</th> -->
												<th>Currency Type</th>
												<th>Status</th>
												<th>C Status</th>
												<th>Edit</th>
											</tr>
										</thead>
										<tbody>
											<c:set var="freeBox" value="${0}" />
											<c:set var="locatedBox" value="${0}" />
											<c:forEach var="row" items="${records}">
												<tr>
													<td>${row.boxName}</td>
													<td>${row.maxCapacity}</td>
													<td>${row.denomination}</td>
													<%-- <td>${row.cashSource}</td> --%>
													<td>${row.currencyType}</td>
													<td>${row.status}</td>
													<c:if test="${row.isAllocated eq 0}">
														<td style="color: white; background: green;">Free</td>
														<c:set var="freeBox" value="${freeBox+1}" />
													</c:if>
													<c:if test="${row.isAllocated eq 1}">
														<td style="color: white; background: rgb(230, 230, 0);">Located</td>
														<c:set var="locatedBox" value="${locatedBox+1}" />
													</c:if>
													<th><a href="editBox?id=${row.id}">Edit</a></th>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</form>
							</div>
							<!-- /.table-responsive -->
							<div style="color: white; background: blue;">
								<b style="margin: 20px 50px;">Free Box:-</b><b>${freeBox}</b><b
									style="margin: 20px 50px;">Located Box:- </b><b>${locatedBox}
								</b>
							</div>
							<br>

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
        $('#tableValue').dataTable({
        	
        	"pagingType": "full_numbers",
        	
        });
    });
    </script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>
</html>