<!DOCTYPE html>
<%@page import="com.chest.currency.entity.model.Discrepancy"%>
<%@page import="java.util.Map"%>
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
<link href="./resources/css/calendar.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.datetimepicker.css" />
<title>FCRM Format</title>

<style type="text/css">
.region-con-sec {
	float: left;
	width: 100%;
	border-top: 1px solid #eee;
	border-bottom: 1px solid #eee;
	padding-top: 13px;
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
</style>

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
						<div class="panel-heading">FCRM Uploadable Format</div>
						<div>
							<input type="button" class="btn btn-default qr-button"
								onclick="printDiv('printableArea')" value="Print" />
							<button id="btnExport" class="btn btn-default qr-button">Export
								to xls</button>
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">

							<div class="region-con">
								<form:form id="userPage" name="userPage" action="fcrmFormat"
									method="post" modelAttribute="reportDate" autocomplete="off">
									<div class="region-con-sec">
										<ul class="region-drop">
											<li><label>Select Date</label> <span> <form:input
														type="text" path="fromDate" id="fromDate" name="fromDate"
														cssClass="form-control" />
											</span></li>

											<li><label></label> <span>
													<button type="submit" class="btn btn-default"
														value="Details" style="width: 99px;">Search</button>
											</span></li>
										</ul>

									</div>
								</form:form>
							</div>

							<div class="dataTable_wrapper">

								<form id="showAll">
									<!-- <table class="table table-striped table-bordered table-hover" id="dataTables-example"> -->
									<div id="printableArea">
										<div id="table_wrapper">
											<table class="table table-striped table-bordered table-hover"
												id="tableValue">
												<thead>
													<tr>
														<th>Account Number</th>
														<th>Notes</th>
														<th>ICMC Name</th>
														<th>Branch Name with Sol ID</th>
														<th>Type of Discrepancy - Fake</th>
														<th>No of notes of Rs. 2000</th>
														<th>No of notes of Rs. 500</th>
														<th>No of notes of Rs. 100</th>
														<th>Total Value of Fake notes</th>
														<th>Type of Discrepancy - Mutilated</th>
														<th>No of notes of Rs. 2000</th>
														<th>No of notes of Rs. 500</th>
														<th>No of notes of Rs. 100</th>
														<th>Total Value of Mutilated notes</th>
														<th>Type of Discrepancy - Shortage</th>
														<th>No of notes of Rs. 2000</th>
														<th>No of notes of Rs. 500</th>
														<th>No of notes of Rs. 100</th>
														<th>Total Value of Shortage amount</th>
														<th>Type of Discrepancy - Excess</th>
														<th>No of notes of Rs. 2000</th>
														<th>No of notes of Rs. 500</th>
														<th>No of notes of Rs. 100</th>
														<th>Total Value of Excess amount</th>
														<th>Date of remittance</th>
														<th>Value of remittance</th>
													</tr>
												</thead>

												<tbody>
													<%-- <c:forEach var="row" items="${fcrmList}"> --%>

													<%
												Map<String, Discrepancy> fcrmList = (Map<String, Discrepancy>) request.getAttribute("fcrmList");
												for (Map.Entry<String, Discrepancy> entry : fcrmList.entrySet()){ 
											%>

													<tr>
														<td><%= entry.getValue().getAccountNumber() %></td>
														<td>Kindly find the discrepancy details.</td>
														<td>ICMC-${icmcName}</td>
														<td><%= entry.getValue().getBranch() %>-<%=entry.getValue().getSolId() %></td>
														<td></td>
														<td><%= entry.getValue().getDiscrepancyAllocations().get(0).getFakeNotes2000Total() %></td>
														<td><%= entry.getValue().getDiscrepancyAllocations().get(0).getFakeNotes500Total() %></td>
														<td><%= entry.getValue().getDiscrepancyAllocations().get(0).getFakeNotes100Total() %></td>
														<td><%= entry.getValue().getDiscrepancyAllocations().get(0).getFakeNotesTotalValue() %></td>
														<td></td>
														<td><%= entry.getValue().getDiscrepancyAllocations().get(0).getMutilatedNotes2000Total() %></td>
														<td><%= entry.getValue().getDiscrepancyAllocations().get(0).getMutilatedNotes500Total() %></td>
														<td><%= entry.getValue().getDiscrepancyAllocations().get(0).getMutilatedNotes100Total() %></td>
														<td><%= entry.getValue().getDiscrepancyAllocations().get(0).getMutilatedNotesTotalValue() %></td>
														<td></td>
														<td><%= entry.getValue().getDiscrepancyAllocations().get(0).getShortageNotes2000Total() %></td>
														<td><%= entry.getValue().getDiscrepancyAllocations().get(0).getShortageNotes500Total() %></td>
														<td><%= entry.getValue().getDiscrepancyAllocations().get(0).getShortageNotes100Total() %></td>
														<td><%= entry.getValue().getDiscrepancyAllocations().get(0).getShortageNotesTotalValue() %></td>
														<td></td>
														<td><%= entry.getValue().getDiscrepancyAllocations().get(0).getExcessNotes2000Total() %></td>
														<td><%= entry.getValue().getDiscrepancyAllocations().get(0).getExcessNotes500Total() %></td>
														<td><%= entry.getValue().getDiscrepancyAllocations().get(0).getExcessNotes100Total() %></td>
														<td><%= entry.getValue().getDiscrepancyAllocations().get(0).getExcessNotesTotalValue() %></td>
														<td>${currentDate}</td>
														<td>0</td>
													</tr>

													<%} %>
													<%-- </c:forEach> --%>
												</tbody>
											</table>
										</div>
									</div>
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

	<script src="./resources/js/jquery.datetimepicker.js"></script>
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
	    a.download = 'FCRM-Formate-' + Math.floor((Math.random() * 9999999) + 1000000) + '.xls';
	    a.click();
	  });
	});

</script>
	<script>
		$('#fromDate').datetimepicker({
			format : 'Y-m-d',
		});
	</script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
	<script type="text/javascript" src="./js/print.js"></script>

</body>

</html>