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
<title>ICICI : View Discrepancy</title>

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

function doPostDelete(id)
{
var id = id;
	$.ajax({
		type : "POST",
		url : "././deleteDiscrepancy",
		data : "id="+id,
		success : function(response)
		{
			alert("RECORD DELETED")
			window.location='././viewSuspenseDiscrepancy';
		},
		error : function(e)
		{
			alert("ERROR : "+e)
		}
		
	});
	
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
								<li><sec:authorize access="hasRole('ADD_DISCREPANCY')">
										<a href="././addSuspenseDiscrepancy"><i
											class="fa fa-table fa-fw"></i> Add Suspense Discrepancy</a>
									</sec:authorize></li>
							</ul>
							Suspense Discrepancy Data
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
												<!-- <th>Machine Number</th> -->
												<th>Date</th>
												<th>Sol ID</th>
												<th>Branch</th>
												<th>Account/Teller/Cam</th>
												<th>Customer Name</th>
												<th>Account Number</th>
												<th>Denomination</th>
												<th>Discrepancy Type</th>
												<th>Edit</th>


											</tr>
										</thead>
										<%-- <tbody>
                                    	<c:forEach var="row" items="${records}">
                                    		 <c:forEach var="innerRow" items="${row.discrepancyAllocations}">
												<tr>
													<td>${row.machineNumber}</td>
													<td><fmt:formatDate pattern="yyyy-MM-dd" value="${row.discrepancyDate}"/></td>
													<td>${row.branch}</td>
													<td>${row.accountTellerCam}</td>
													<td>${row.customerName}</td>
													<td>${row.accountNumber}</td>
												     <td>${innerRow.denomination}</td>
													 <td>${innerRow.discrepancyType}</td>
													 <Td>
													 <a href="editSuspenseDiscrepancy?allocationId=${innerRow.id}">Edit</a>
													 </Td>
													 <td>
													<a href="editDiscrepancy?id=${row.id}&allocationId=${innerRow.id}">Edit</a>
												</td>
												</tr>
											 </c:forEach>
										</c:forEach>
                                    </tbody> --%>

										<tbody>
											<c:forEach var="row" items="${records}">
												<tr>
													<td><fmt:formatDate pattern="yyyy-MM-dd"
															value="${row.discrepancyDate}" /></td>
													<td>${row.solId}</td>
													<td>${row.branch}</td>
													<td>${row.accountTellerCam}</td>
													<td>${row.customerName}</td>
													<td>${row.accountNumber}</td>
													<td>${row.denomination}</td>
													<td>${row.discrepancyType}</td>
													<Td><a
														href="editSuspenseDiscrepancy?allocationId=${row.id}">Edit</a>
													</Td>
													<td><input type="button" value="DELETE"
														onclick="doPostDelete(${row.id})"></td>
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