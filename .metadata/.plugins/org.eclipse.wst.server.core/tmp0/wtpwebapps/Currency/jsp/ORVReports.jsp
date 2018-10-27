<!DOCTYPE html>
<%@page import="com.chest.currency.entity.model.User"%>
<html lang="en">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="./resources/logo/favicon.ico"
	type="image/x-icon">
<title>ICICI : ORV Report</title>

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
$(document).ready(function() {
	  $("#btnExport").click(function(e) {
	    e.preventDefault();

	    //getting data from our table
	    var data_type = 'data:application/vnd.ms-excel';
	    var table_div = document.getElementById('table_wrapper');
	    $('.dataTables_info').remove();
	    $('.pagination').remove();
	    var table_html = table_div.outerHTML.replace(/ /g, '%20');

	    var a = document.createElement('a');
	    a.href = data_type + ', ' + table_html;
	    a.download = 'exported_table_' + Math.floor((Math.random() * 9999999) + 1000000) + '.xls';
	    a.click();
	  });
	});
</script>

<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-32"
	data-genuitec-path="/Currency/src/main/webapp/jsp/ORVReports.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-32"
		data-genuitec-path="/Currency/src/main/webapp/jsp/ORVReports.jsp">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />
		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">ORV Reports</div>
						<div>
							<input type="button" class="btn btn-default qr-button"
								onclick="printDiv('printableArea')" value="Print" />
							<button id="btnExport" class="btn btn-default qr-button">Export
								to xls</button>
						</div>

						<!-- /.panel-heading -->
						<div class="panel-body">

							<c:set var="dateRangeURL" value="ORVReports" />
							<jsp:include page="reportDateRange.jsp" />

							<div class="dataTable_wrapper">
								<form id="showAll">
									<div id="printableArea">
										<div id="table_wrapper">
											<table class="table table-striped table-bordered table-hover"
												id="tableValue">
												<thead>
													<tr>
														<th>Sr. No</th>
														<th>Sol ID</th>
														<th>Branch</th>
														<th>DR A/C OR IB.IBIT A/C NO</th>
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
													<c:forEach var="row" items="${records}">
														<c:set var="count" value="${count + 1}" scope="page" />
														<%-- <c:set var="str" value="${row.branchEcName}" /> --%>
														<%-- <c:set var="splittedString" value="${fn:split(str, '_')}" /> --%>
														<tr>
															<c:set var="val" value="${row.status}" />
															<td>${count}</td>
															<td>${row.solID}</td>
															<td>${row.branch}</td>
															<td>${linkBranchSolID}IBTRANDR</td>
															<td>${row.srNo}</td>
															<td>${row.totalValue}</td>
														</tr>
													</c:forEach>

													<c:forEach var="row" items="${craRecords}">
														<c:set var="count" value="${count + 1}" scope="page" />
														<tr>
															<td>${count}</td>
															<td>${row.solId}</td>
															<td>${row.branch}- ${row.vendor} - ${row.mspName}</td>
															<!-- Show Vendor Name as well as MSP Name alongwith Branch Name -->
															<td>${row.accountNumber}</td>
															<td>${row.srNo}</td>
															<td>${row.totalValue}</td>
														</tr>
													</c:forEach>

													<c:forEach var="row" items="${otherBankRecords}">
														<c:set var="count" value="${count + 1}" scope="page" />
														<tr>
															<td>${count}</td>
															<td>${row.solId}</td>
															<td>${row.branch}- ${row.bankName}</td>
															<td>${row.solId}IBTRANDR</td>
															<td>${row.rtgsUTRNo}</td>
															<td>${row.totalValue}</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>

											<table class="table table-striped table-bordered table-hover"
												id="tableValue">
												<Tr>
													<td style="widh: 4px;">${servicingICMC}- Processing</td>
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

											<%-- <div id="sumBranchTotalProcessingTotal" class="col-sm-8 col-sm-pull-3 col-md-4 col-md-push-8">
									<lable>Total:  ${sum}</lable>
									
									</div> --%>

										</div>
									</div>
									<br>
								</form>
							</div>
							<!-- </div> -->

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
	 var sumTotalRow=0;
    $(document).ready(function () {
    	var table=$('#tableValue').dataTable({
   		 "pagingType": "full_numbers",
   		"lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
   		 drawCallback: function () {
	    	        var api = this.api();
	    	        $( api.table().footer() ).html('<th colspan="6" style="text-align:right">Total:'+
	    	          api.column( 5, {page:'current'}).data().sum().toLocaleString('en-IN')+'; All Pages Total: '+
    	        	  api.column( 5 ).data().sum().toLocaleString('en-IN')+'</th>'
	    	        );
	    	        sumTotalRow=api.column( 5 ).data().sum().toLocaleString('en-IN');
	    	      }
   			});
    	
    	 var processingTotal=$("#tableValue #processingTotal").text();
 	    var processingTotalNumber = Number(processingTotal.replace(/[^0-9\.-]+/g,""));
   	    var sumTotalRowNumber = Number(sumTotalRow.replace(/[^0-9\.-]+/g,""));
   	 var allAmount=processingTotalNumber+sumTotalRowNumber;
      // allAmount = allAmount.toLocaleString('en-IN');
   	    $("#sumBranchTotalProcessingTotal").append(allAmount);
 	    	
   	 		var tableTools=new $.fn.dataTable.TableTools(table,{
   		 
	   		 /*  'sSwfPath':'./js/copy_csv_xls_pdf.swf', */
	   		/* 'sSwfPath':'//cdn.datatables.net/tabletools/2.2.4/swf/copy_csv_xls_pdf.swf', */
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