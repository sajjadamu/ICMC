<!DOCTYPE html>
<html lang="en">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="./resources/logo/favicon.ico" type="image/x-icon">
<title>ICICI : Discrepancy Report</title>

<!-- Bootstrap Core CSS -->
<link href="./resources/bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- MetisMenu CSS -->
<link href="./resources/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

<!-- DataTables CSS -->
<link href="./resources/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css" rel="stylesheet">

<!-- DataTables Responsive CSS -->
<link href="./resources/bower_components/datatables-responsive/css/responsive.dataTables.scss" rel="stylesheet">

<!-- Custom CSS -->
<link href="./resources/dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="./resources/bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link rel="./resources/stylesheet" type="text/css" href="./resources/dist/css/style.css">

<!-- DataTable -->
<script type="text/javascript"  src="./resources/dataTable/jquery.js"></script>
<script type="text/javascript" src="./resources/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="./resources/js/dataTables.tableTools.min.js"></script>
<link rel="stylesheet" type="text/css" href="./resources/css/jquery.dataTables.min.css">
<link rel="stylesheet" type="text/css" href='./resources/css/dataTables.tableTools.min.css'>
<link rel="stylesheet" type="text/css" href='./resources/css/dataTables.tableTools.css'>
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
						<div class="panel-heading">Discrepancy Reports</div>
						<!-- /.panel-heading -->
					<div><input type="button" class="btn btn-default qr-button" onclick="printDiv('printableArea')" value="Print" /></div>
						
						<div class="panel-body">
							
						<c:set var="dateRangeURL" value="discrepancyReports" />
						<jsp:include page="reportDateRangeDiscrepancy.jsp" />
							
							 <div class="dataTable_wrapper">
								<form id="showAll">
									<!-- <table class="table table-striped table-bordered table-hover" id="dataTables-example"> -->
									<div id="printableArea">
									<table class="table table-striped table-bordered table-hover" id="tableValue">
										<thead>
											<tr>
												<th>Date</th>
												<th>Discrepancy Type</th>
												<th>Sol Id</th>
												<th>Branch</th>
												<th>Account Teller Cam</th>
												<th>Account Number</th>
												<th>Denomination</th>
												<th>Value</th>
												<th>Note Serial Number</th>
												<th>SR</th>
												<!-- <th>Remark</th> -->
												<th>Category</th>
												
											</tr>
										</thead>
										<tbody>
											<c:forEach var="row" items="${discrepancyList}">
												<c:set var="count" value="${count + 1}" scope="page" />
												<c:forEach var="discrepancyAllocation" items="${row.discrepancyAllocations}">
													<tr>
														<td><fmt:formatDate pattern="dd-MMM-yy"  value="${row.discrepancyDate}" /></td>
														<td>${discrepancyAllocation.discrepancyType}</td>  
														<td>${row.solId}</td>
														<td>${row.branch}</td>
														<td>${row.accountTellerCam}</td>
														<td>${row.accountNumber}</td>
														<td>${discrepancyAllocation.denomination}</td> 
														<td>${discrepancyAllocation.value}</td> 
														<td>${discrepancyAllocation.noteSerialNumber}</td> 
														<td>${discrepancyAllocation.srNumber}</td> 
														<%-- <td>${discrepancyAllocation.remarks}</td>  --%>
														<td>${row.normalOrSuspense}</td>
													</tr>
												</c:forEach>
											</c:forEach>
											
										</tbody>
									</table></div>
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
	 $(document).ready(function () {
	    	var table=$('#tableValue').dataTable({
	    		 "pagingType": "full_numbers",
	    	});
	    	 var tableTools=new $.fn.dataTable.TableTools(table,{
	    		
	    		'sSwfPath':'./js/copy_csv_xls_pdf.swf',
	    		 /* 'sSwfPath':'//cdn.datatables.net/tabletools/2.2.4/swf/copy_csv_xls_pdf.swf', */
	    		 'aButtons':['copy',/* {
	    		'sExtends':'print',
	    		'bShowAll':false
	    		 }, */
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
<script type="text/javascript" src="./js/print.js"></script>

</body>

</html>