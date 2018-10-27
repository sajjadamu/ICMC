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
<title>ICICI : View Diversion ORV</title>
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
								<li><sec:authorize
										access="hasRole('ADD_DIVERSION_PAYMENT')">
										<a href="././Dorv"><i class="fa fa-table fa-fw"></i> Add
											Diversion Payment</a>
									</sec:authorize></li>
							</ul>
							Diversion Data
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<form id="showAll">
									<!-- <table class="table table-striped table-bordered table-hover" id="dataTables-example"> -->
									<table class="table table-striped table-bordered table-hover"
										id="tableValue">
										<thead>
											<tr>
												<th>Order Date</th>
												<th>RBI Order number</th>
												<th>Bank Name</th>
												<th>Location</th>
												<th>Approved CC</th>
												<th>Total Value</th>
												<th>Edit</th>
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
												<tr>
													<td><fmt:formatDate pattern="dd-MMM-yy"
															value="${row.orderDate}" /></td>
													<td>${row.rbiOrderNo}</td>
													<td>${row.bankName}</td>
													<td>${row.location}</td>
													<td>${row.approvedCC}</td>
													<td>${row.totalValue}</td>

													<td><sec:authorize
															access="hasRole('UPDATE_DIVERSION_PAYMENT')">
															<a href="editDiversionORV?id=${row.id}">Edit</a>
														</sec:authorize></td>
													<c:if test="${row.otherStatus=='REQUESTED'}">
														<td><sec:authorize
																access="hasRole('UPDATE_DIVERSION_PAYMENT')">
																<a href="cancelDiversionORV?id=${row.id}"
																	onclick="return confirm('Are you sure you want to cancel this Diversion ORV record?');">Cancel</a>
															</sec:authorize></td>
													</c:if>
													<c:if
														test="${row.otherStatus=='ACCEPTED' or row.otherStatus=='RELEASED'}">
														<td><button class="btnCancel">Cancel</button></td>

													</c:if>
												</tr>
											</c:forEach>
										</tbody>
									</table>
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
	 $(document).ready(function () {
	    	$('#tableValue').dataTable({
    		 "pagingType": "full_numbers",
    		 drawCallback: function () {
	    	        var api = this.api();
	    	        $( api.table().footer() ).html('<th colspan="6" style="text-align:right">Current Page Total: '+
	    	        		api.column( 5, {page:'current'} ).data().sum().toLocaleString('en-IN')+'; All Pages Total: '+
	    	        		api.column( 5 ).data().sum().toLocaleString('en-IN')+'</th>'
	    	        );
	    	      }
	    	});
	    });
	 $(document).ready(function(){
			/* $(".btnEdit").click(function(e){
				alert("PROCESSED data can't be edited");
				e.preventDefault();
			}); */

			$(".btnCancel").click(function(e){
				alert("Accepted  data can't be Cancel");
				e.preventDefault();
			});

		});
	</script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>