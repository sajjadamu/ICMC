<!DOCTYPE html>
<html lang="en">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="./resources/logo/favicon.ico"
	type="image/x-icon">
<title>ICICI : Coin Distribution Register</title>

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
	href="./resources/dist/css/style.css">

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
<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-80"
	data-genuitec-path="/Currency/src/main/webapp/jsp/coinDistributionRegister.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-80"
		data-genuitec-path="/Currency/src/main/webapp/jsp/coinDistributionRegister.jsp">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">

			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">Coin Distribution Register</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<div>
									<input type="button" class="btn btn-default qr-button"
										onclick="printDiv('printableArea')" value="Print" />
								</div>
								<!-- 							<div><input type="button" id="btnExport" value=" Export Table data into Excel " />
 -->
							</div>
							<form id="showAll">
								<!-- <table class="table table-striped table-bordered table-hover" id="dataTables-example"> -->
								<div id="printableArea">
									<table class="table table-striped table-bordered table-hover"
										id="tableValue">

										<thead>
											<tr>
												<th>Date</th>
												<th>Branch Name/Bulk Customer Name</th>
												<th>SOL ID</th>
												<th>Denomination</th>
												<th>Total Value of Coins</th>
												<th>SR Number</th>
											</tr>
										</thead>
										<tbody class="table table-striped table-bordered table-hover">
											<%-- <tr><td><fmt:formatDate type="both" value="${row.insertTime.time}"/></td> --%>

											<c:forEach var="row" items="${coinsList1}">
												<tr>
													<td><fmt:formatDate type="both"
															value="${row.insertTime.time}" /></td>
													<td>${row.branch}</td>
													<td>${row.solID}</td>
													<td>1</td>
													<td>${row.totalValueOfCoinsRs1}</td>
													<td>${row.srNo}</td>
												</tr>
											</c:forEach>

											<c:forEach var="row" items="${coinsList1}">
												<tr>
													<td><fmt:formatDate type="both"
															value="${row.insertTime.time}" /></td>
													<td>${row.branch}</td>
													<td>${row.solID}</td>
													<td>2</td>
													<td>${row.totalValueOfCoinsRs2}</td>
													<td>${row.srNo}</td>
												</tr>
											</c:forEach>

											<c:forEach var="row" items="${coinsList1}">
												<tr>
													<td><fmt:formatDate type="both"
															value="${row.insertTime.time}" /></td>
													<td>${row.branch}</td>
													<td>${row.solID}</td>
													<td>5</td>
													<td>${row.totalValueOfCoinsRs5}</td>
													<td>${row.srNo}</td>
												</tr>
											</c:forEach>

											<c:forEach var="row" items="${coinsList1}">
												<tr>
													<td><fmt:formatDate type="both"
															value="${row.insertTime.time}" /></td>
													<td>${row.branch}</td>
													<td>${row.solID}</td>
													<td>10</td>
													<td>${row.totalValueOfCoinsRs10}</td>
													<td>${row.srNo}</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</form>
							<!-- </div> DataTable_wrapper bundle div close -->

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
	    		 "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]]
	    	});
	    	 var tableTools=new $.fn.dataTable.TableTools(table,{
	    		
	    		 /* 'sSwfPath':'//cdn.datatables.net/tabletools/2.2.4/swf/copy_csv_xls_pdf.swf', */
	    		 /* 'sSwfPath':'./js/copy_csv_xls_pdf.swf', */
	    		 'aButtons':[/* 'copy' */,/* {
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