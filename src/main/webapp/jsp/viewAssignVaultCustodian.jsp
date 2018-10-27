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
<title>ICICI : View Vault Custodian Details</title>

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
										access="hasRole('ADD_ASSIGN_VAULT_CUSTODIAN')">
										<a href="././AssignVaultCustodian"><i
											class="fa fa-table fa-fw"></i> Assign Vault Custodian</a>
									</sec:authorize></li>
							</ul>
							View Vault Custodian Details
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<form id="showAll">
									<div align="center" style="color: white; background: green;">
										<b>${successMsg}</b>
									</div>
									<div align="center" style="color: white; background: red;">
										<b>${errorMsg}</b>
									</div>
									<div align="center" style="color: white; background: red;">
										<b>${takingOverCharge}</b>
									</div>
									<div align="center" style="color: white; background: red;">
										<b>${HandingOverCharge}</b>
									</div>
									<br>
									<!-- <table class="table table-striped table-bordered table-hover" id="dataTables-example"> -->
									<table class="table table-striped table-bordered table-hover"
										id="tableValue">
										<thead>
											<tr>
												<th>User ID Handing Over By</th>
												<th>User Name Handing Over By</th>
												<th>Custodian</th>
												<th>User ID Handing Over Charge</th>
												<th>User Name Handing Over Charge</th>
												<th>Remarks</th>
												<th>Assigned Date</th>
												<th>Edit</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="row" items="${records}">
												<tr>
													<td>${row.userId}</td>
													<td>${row.userName}</td>
													<td>${row.custodian}</td>
													<td>${row.handingOverCharge}</td>
													<td>${row.handOveredUserName}</td>
													<td>${row.remarks}</td>
													<td><fmt:formatDate pattern="dd-MMM-yy"
															value="${row.insertTime.time}" /></td>
													<td><sec:authorize
															access="hasRole('UPDATE_ASSIGN_VAULT_CUSTODIAN')">
															<a href="editAssignVaultCustodian?id=${row.id}">Edit</a>
														</sec:authorize></td>
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
    	var table=$('#tableValue').dataTable({
    		 "pagingType": "full_numbers",
    	});
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
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>
</html>