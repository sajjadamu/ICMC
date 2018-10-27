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
<title>ICICI : IRV Report</title>

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

<script type="text/javascript">
$("#btnExport").click(function(e) {
    window.open('data:application/vnd.ms-excel,' + $('#dvData').html());
    e.preventDefault();
});
</script>
<script type="text/javascript">
$(document).ready(function() {
	  $("#btnExport").click(function(e) {
	    e.preventDefault();

	    //getting data from our table
	    var data_type = 'data:application/vnd.ms-excel';
	  //  document.getElementById('table_wrapper').deleteTFoot();
	    var table_div = document.getElementById('table_wrapper');
	    $('.dataTables_info').remove();
	    $('.pagination').remove();
	    var table_html = table_div.outerHTML.replace(/ /g, '%20');
	    var a = document.createElement('a');
	    a.href = data_type + ', ' + table_html;
	    a.download = 'IRV-Report_' + Math.floor((Math.random() * 9999999) + 1000000) + '.xls';
	    a.click();
	  });
	});
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
						<div class="panel-heading">IRV Reports</div>
						<!-- /.panel-heading -->
						<div class="panel-body">

							<c:set var="dateRangeURL" value="IRVReports" />
							<jsp:include page="reportDateRange.jsp" />

							<div class="dataTable_wrapper">
								<div>
									<input type="button" class="btn btn-default qr-button"
										onclick="printDiv('printableArea')" value="Print" />
									<button id="btnExport" class="btn btn-default qr-button">Export
										to xls</button>
								</div>

								<form id="showAll">
									<!-- <table class="table table-striped table-bordered table-hover" id="dataTables-example"> -->
									<div id="printableArea">
										<div id="table_wrapper">
											<table class="table table-striped table-bordered table-hover"
												id="tableValue">
												<thead>
													<tr>
														<!-- <th>Sr. No</th> -->
														<th>Branch</th>
														<th>Sol Id</th>
														<th>CR A/C OR .IB.TRANDR A/C NO.</th>
														<th>SR No.</th>
														<th>Total</th>
													</tr>
												</thead>
												<tfoot>
													<tr>
														<th style="text-align: right">Total:</th>
													</tr>
												</tfoot>
												<tbody>
													<c:forEach var="row" items="${branchReceipts}">
														<c:set var="count" value="${count + 1}" scope="page" />
														<tr>
															<%-- <td>${count}</td> --%>
															<td>${row.branch}</td>
															<td>${row.solId}</td>
															<td>${row.solId}IBTRANDR</td>
															<td>${row.srNumber}</td>
															<td>${row.total}</td>
														</tr>
													</c:forEach>

													<c:forEach var="row" items="${dsbs}">
														<c:set var="count" value="${count + 1}" scope="page" />
														<tr>
															<%-- <td>${count}</td> --%>
															<td>${row.name}</td>
															<td>${row.linkBranchSolId}</td>
															<td>${row.accountNumber}</td>
															<td></td>
															<td>${row.total}</td>
														</tr>
													</c:forEach>
													<c:forEach var="row" items="${bankReceipts}">
														<c:set var="count" value="${count + 1}" scope="page" />
														<tr>
															<%-- <td>${count}</td> --%>
															<td>${row.branch}- ${row.bankName}</td>
															<td>${row.solId}</td>
															<td>${row.rtgsUTRNo}</td>
															<td></td>
															<td>${row.total}</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
											<table class="table table-striped table-bordered table-hover"
												id="tableValue">
												<Tr>
													<td>${servicingICMC}- Processing</td>
													<td>${linkBranchSolID}</td>
													<td>${linkBranchSolID}IBTRANDR</td>
													<td>Processing</td>
													<td id="processingTotal">${sum}</td>
												</Tr>
											</table>
											<table class="table table-striped table-bordered table-hover">
												<Tr>
													<td>Branch and Processing</td>
													<td></td>
													<td></td>
													<td><b>Total</b></td>
													<td id="sumBranchTotalProcessingTotal"></td>
												</Tr>
											</table>

											<!-- <div id="sumBranchTotalProcessingTotal" class="col-sm-8 col-sm-pull-3 col-md-4 col-md-push-8">
									</div> -->
											<%-- <lable>Total:  ${sum}</lable> --%>

										</div>
									</div>


									<br>
								</form>
							</div>

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
	var sumTotalRow=0;
	 $(document).ready(function () {
	    var table=$('#tableValue').dataTable({
	    	     "lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
	    		 "pagingType": "full_numbers",
	    		 drawCallback: function () {
		    	        var api = this.api();
		    	        $( api.table().footer() ).html('<th colspan="5" style="text-align:right" id="totalSum">Current Page Total:'+
		    	          api.column( 4, {page:'current'}).data().sum().toLocaleString('en-IN')+'; All Pages Total: '+
		    	          api.column( 4 ).data().sum().toLocaleString('en-IN')+'</th>'
		    	        );
		    	        sumTotalRow=api.column( 4 ).data().sum().toLocaleString('en-IN');
		    	      },
	    "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]]
	    
	    	});
	    
	    var processingTotal=$("#tableValue #processingTotal").text();
	    var processingTotalNumber = Number(processingTotal.replace(/[^0-9\.-]+/g,""));
  	    var sumTotalRowNumber = Number(sumTotalRow.replace(/[^0-9\.-]+/g,""));
  	    var allAmount=processingTotalNumber+sumTotalRowNumber;
  	          //allAmount = allAmount.toLocaleString('en-IN');
  	    $("#sumBranchTotalProcessingTotal").append(allAmount);
	    	 var tableTools=new $.fn.dataTable.TableTools(table,{
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
	 var tab=$('#totalSum').text;
 	console.log(tab);
	 
	</script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
	<script type="text/javascript" src="./js/print.js"></script>

</body>

</html>