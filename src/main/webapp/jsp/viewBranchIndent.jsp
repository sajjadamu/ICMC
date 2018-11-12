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
<title>VMS : Branch Indent</title>
<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.datetimepicker.css" />
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

<script type="text/javascript">
	function bundleDetails(str) {
		addHeader();
		var srNumber = str;
		$.ajax({
			type : "POST",
			url : "././branchIndentBundleDetails",
			data : "srNumber=" + srNumber,
			success : function(response) {
				/* alert(response[0].id) */
				/* var id = response[0].id;
				alert(id) */
				var srNo = response[0].totalValueOfNotesRs200I;
				var res = JSON.stringify(response);
				var newStr = res.substring(1, res.length - 1); // Remove Array Brackets
				var data = newStr.split(",");
				var html = "";

				var issuable2000 = response[0].totalValueOfNotesRs2000I
				var atm2000 = response[0].totalValueOfNotesRs2000A
				var fresh2000 = response[0].totalValueOfNotesRs2000F
				var issuable500 = response[0].totalValueOfNotesRs500I
				var atm500 = response[0].totalValueOfNotesRs500A
				var fresh500 = response[0].totalValueOfNotesRs500F
				var issuable200 = response[0].totalValueOfNotesRs200I
				var atm200 = response[0].totalValueOfNotesRs200A
				var fresh200 = response[0].totalValueOfNotesRs200F
				var issuable100 = response[0].totalValueOfNotesRs100I
				var atm100 = response[0].totalValueOfNotesRs100A
				var fresh100 = response[0].totalValueOfNotesRs100F
				var issuable50 = response[0].totalValueOfNotesRs50I
				var fresh50 = response[0].totalValueOfNotesRs50F
				var issuable20 = response[0].totalValueOfNotesRs20I
				var fresh20 = response[0].totalValueOfNotesRs20F
				var issuable10 = response[0].totalValueOfNotesRs10I
				var fresh10 = response[0].totalValueOfNotesRs10F
				var issuable5 = response[0].totalValueOfNotesRs5I
				var fresh5 = response[0].totalValueOfNotesRs5F
				var issuable2 = response[0].totalValueOfNotesRs2I
				var fresh2 = response[0].totalValueOfNotesRs2F
				var issuable1 = response[0].totalValueOfNotesRs1I
				var fresh1 = response[0].totalValueOfNotesRs1F
				
				var totalSum2000=1000*2000*issuable2000+1000*2000*fresh2000+1000*2000*atm2000; 
				var totalSum500=1000*500*issuable500+1000*500*fresh500+1000*500*atm500;
				var totalSum200=1000*200*issuable200+1000*200*fresh200+1000*200*atm200;
				var totalSum100=1000*100*issuable100+1000*100*fresh100+1000*100*atm100;
				var totalSum50=1000*50*issuable50+1000*50*fresh50;
				var totalSum20=1000*20*issuable20+1000*20*fresh20;
				var totalSum10=1000*10*issuable10+1000*10*fresh10;
				var totalSum5=1000*5*issuable5+1000*5*fresh5;
				var totalSum2=1000*2*issuable2+1000*2*fresh2;
				var totalSum1=1000*1*issuable1+1000*1*fresh1;
				
				var total2000 = issuable2000 + fresh2000 + atm2000;
				var total500 = issuable500 + fresh500 + atm500;
				var total200 = issuable200 + fresh200 + atm200;
				var total100 = issuable100 + fresh100 + atm100;
				var total50 = issuable50 + fresh50;
				var total20 = issuable20 + fresh20;
				var total10 = issuable10 + fresh10;
				var total5 = issuable5 + fresh5;
				var total2 = issuable2 + fresh2;
				var total1 = issuable1 + fresh1;
				

				var sumOfTotalBulbles= total2000 + total500 + total200 + total100 + total50 + total20 + total10 + total5 + total1;
				
				var totalSumOfAmount = totalSum2000 + totalSum500 + totalSum200 + totalSum100 + totalSum50 + totalSum20 + totalSum5 + totalSum2 + totalSum1;
				
				html += "<table style='width:100%;text-align: center;' border='1'>";
				html += "<tr><td>DENOMINATION</td> " + "<td>ISSUABLE</td> "
						+ "<td>ATM </td>" + "<td>FRESH </td>"+"<td> Bundles </td>"+"<td> Amount </td></tr>";
					
						if(issuable2000 > 0 || fresh2000 > 0 ||  atm2000 > 0 ){
				html += "<tr><td>2000 </td>" + "<td>" + issuable2000 + "</td> "
						+ "<td>" + atm2000 + "</td>" + "<td>" + fresh2000
						+ "</td>"+ "<td>" + total2000 + "</td>"+"<td>" + totalSum2000 + "</td>";
				         }
						if(issuable500 > 0 || fresh500 > 0 ||  atm500 > 0 ){
				html += "<tr><td>500 </td>" + "<td>" + issuable500 + "</td> "
						+ "<td>" + atm500 + "</td>" + "<td>" + fresh500
						+ "</td>"+ "<td>" + total500 + "</td>"+"<td>" + totalSum500 + "</td>";
						}
						
						if(issuable200 > 0 || fresh200 > 0 ||  atm200 > 0 ){
				html += "<tr><td>200 </td>" + "<td>" + issuable200 + "</td> "
						+ "<td>" + atm200 + "</td>" + "<td>" + fresh200
						+ "</td>"+ "<td>" + total200 + "</td>"+"<td>" + totalSum200 + "</td>";
						}
						
						if(issuable100 > 0 || fresh100 > 0 ||  atm100 > 0 ){
				html += "<tr><td>100 </td>" + "<td>" + issuable100 + "</td> "
						+ "<td>" + atm100 + "</td>" + "<td>" + fresh100
						+ "</td>"+ "<td>" + total100 + "</td>"+"<td>" + totalSum100 + "</td>";
						}
						
						if(issuable50 > 0 || fresh50 > 0 ){
				html += "<tr><td>50 </td>" + "<td>" + issuable50 + "</td> "
						+ "<td>" + "0" + "</td>" + "<td>" + fresh50 + "</td>"+ "<td>" + total50 + "</td>"+"<td>" + totalSum50 + "</td>";
						}
						
						if(issuable20 > 0 || fresh20 > 0  ){
				html += "<tr><td>20 </td>" + "<td>" + issuable20 + "</td> "
						+ "<td>" + "0" + "</td>" + "<td>" + fresh20 + "</td>"+ "<td>" + total20 + "</td>"+"<td>" + totalSum20 + "</td>";
						}
						if(issuable10 > 0 || fresh10 > 0  ){
				html += "<tr><td>10 </td>" + "<td>" + issuable10 + "</td> "
						+ "<td>" + "0" + "</td>" + "<td>" + fresh10 + "</td>"+ "<td>" + total10 + "</td>"+"<td>" + totalSum10 + "</td>";
						}
						if(issuable5 > 0 || fresh5 > 0 ){
				html += "<tr><td>5 </td>" + "<td>" + issuable5 + "</td> "
						+ "<td>" + "0" + "</td>" + "<td>" + fresh5 + "</td>"+ "<td>" + total5 + "</td>"+"<td>" + totalSum5 + "</td>";
						}
						if(issuable2 > 0 || fresh2 > 0  ){
				html += "<tr><td>2 </td>" + "<td>" + issuable2 + "</td> "
						+ "<td>" + "0" + "</td>" + "<td>" + fresh2 + "</td>"+ "<td>" + total2 + "</td>"+"<td>" + totalSum2 + "</td>";
						}
						if(issuable1 > 0 || fresh1 > 0 ){
				html += "<tr><td>1 </td>" + "<td>" + issuable1 + "</td> "
						+ "<td>" + "0" + "</td>" + "<td>" + fresh1 + "</td>"+ "<td>" + total1 + "</td>"+"<td>" + totalSum1 + "</td>";
						}
				html += "<div class='cal-sm text-danger'>Total Bundles = "+ sumOfTotalBulbles + "</div><div class='cal-sm text-success'>   Total Amount = "+totalSumOfAmount+"</div>";
				
				//html +="<div class='row'><div class='col-sm-5 text-danger'>Total Bundles = "+ sumOfTotalBulbles + "</div><div class='col-sm-7 text-success'> Total Amount = "+totalSumOfAmount+"</div></div></div>";
				/* var data = capacity - receiveBundle;
				html += "Available :" + data + "</br>"; */
				$('#modal-body').html(html);
				$('#myModal').modal('show');
			},
			error : function(e) {
				alert(e)
			}
		})
	}
</script>

</head>

<body oncontextmenu="return false;">

	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Bundle Status</h4>
				</div>
				<div class="modal-body" id="modal-body">
					<p>This is a small modal.</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<div id="wrapper">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">

			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">Indent History</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<div>
									<input type="button" class="btn btn-default qr-button"
										onclick="printDiv('printableArea')" value="Print" />
									<button id="btnExport" class="btn btn-default qr-button">Export
										to xls</button>
								</div>

							</div>
							<!-- sajjad coading  start -->
							<div class="card">
								<div class="card-body">

									<table class="table table-striped table-bordered"
										id="editable-datatable">
										<thead>
											<tr>
												<th>From Date</th>
												<th>To Date</th>
												<th></th>
											</tr>
										</thead>
										<tbody>
											<form:form id="userPage" name="userPage"
												action="viewBranchIndent" method="post"
												modelAttribute="reportDate" autocomplete="off">

												<tr id="1" class="gradeX">
													<td><form:input type="text" path="fromDate"
															id="fromDate" name="fromDate" cssClass="form-control" />
													</td>
													<td><form:input type="text" path="toDate" id="toDate"
															name="toDate" cssClass="form-control" /></td>
													<td><button type="submit" class="btn btn-default"
															value="Details" style="width: 99px;">Search</button></td>
												</tr>
											</form:form>
										</tbody>
									</table>
								</div>
							</div>
							<!-- sajjad      coading end -->
							<form id="showAll">
								<!-- <table class="table table-striped table-bordered table-hover" id="dataTables-example"> -->
								<div id="printableArea">
									<div id="table_wrapper">

										<table class="table table-striped table-bordered table-hover"
											id="tableValue">

											<thead>
												<tr>
													<th>Date</th>
													<th>Sr Number</th>
													<th>Bundle</th>
													<th>Status</th>
												</tr>
											</thead>
											<tbody class="table table-striped table-bordered table-hover">
												<c:forEach var="row" items="${branchIndentRequestedData}">
													<tr>
														<td><fmt:formatDate pattern="yyyy-MM-dd dd:mm:ss"
																value="${row.insertTime.time}" /></td>
														<td>${row.srNo}</td>
														<td><a href="#"
															onclick="bundleDetails('${row.srNo}')">${row.totalValueOfNotesRs2000A+row.totalValueOfNotesRs2000I+row.totalValueOfNotesRs2000F+row.totalValueOfNotesRs500A+row.totalValueOfNotesRs500I+row.totalValueOfNotesRs500F+row.totalValueOfNotesRs200A+row.totalValueOfNotesRs200F+row.totalValueOfNotesRs200I+row.totalValueOfNotesRs100A+row.totalValueOfNotesRs100I+row.totalValueOfNotesRs100F+row.totalValueOfNotesRs50I+row.totalValueOfNotesRs50F+row.totalValueOfNotesRs20I+row.totalValueOfNotesRs20F+row.totalValueOfNotesRs10I+row.totalValueOfNotesRs10F+row.totalValueOfNotesRs5I+row.totalValueOfNotesRs5F+row.totalValueOfNotesRs2I+row.totalValueOfNotesRs2F+row.totalValueOfNotesRs1I+row.totalValueOfNotesRs1F}</a></td>
														<td>${row.branchIndentStatus}</td>
													</tr>
												</c:forEach>
												</tbody>
										</table>
									</div>
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
		$(document).ready(function() {
			var table = $('#tableValue').dataTable({
				"pagingType" : "full_numbers",
			});
			var tableTools = new $.fn.dataTable.TableTools(table, {

				/* 'sSwfPath':'//cdn.datatables.net/tabletools/2.2.4/swf/copy_csv_xls_pdf.swf', */
				/* 'sSwfPath' : './js/copy_csv_xls_pdf.swf', */
				'aButtons' : [ /* 'copy' */,/* {
																																																																					    		'sExtends':'print',
																																																																					    		'bShowAll':false
																																																																					    		 }, */
				'csv', {
					'sExtends' : 'xls',
					'sFileName' : '*.xls',
					'sButtonText' : ' Excel'
				}, {
					'sExtends' : 'pdf',
					'bFooter' : false
				} ]
			});
			$(tableTools.fnContainer()).insertBefore('.dataTable_wrapper');
		});
	</script>
	<script src="./resources/js/jquery.datetimepicker.js"></script>
	<script>
		$('#fromDate').datetimepicker({
			format : 'Y-m-d',
		});
		$('#toDate').datetimepicker({
			format : 'Y-m-d',
		});
	</script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
	<script type="text/javascript" src="./js/print.js"></script>
</body>

</html>