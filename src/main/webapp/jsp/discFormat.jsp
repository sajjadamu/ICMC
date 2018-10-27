<!DOCTYPE html>
<%@page import="java.util.Date"%>
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
<link href="./resources/css/calendar.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.datetimepicker.css" />
<title>ICICI : Discrepancy Format</title>

<style type="text/css">
.dispencery_wrap {
	font-size: 12px;
}

.dispencery_wrap table tr th {
	border: 1px solid #000;
	padding: 3px;
}

.dispencery_wrap   table, td {
	border: 1px solid black;
	text-align: center;
	padding: 5px;
}

td, th {
	display: table-cell;
	vertical-align: inherit;
}

.dispencery_wrap tr td:nth-child(17) {
	text-align: left;
}

.dispencery_wrap tr td:nth-child(18) {
	text-align: left;
}

.dispencery_wrap tr:nth-child(1) {
	background: #bdf7f7;
}

.dispencery_wrap tr:nth-child(2) {
	background: #bdf7f7;
}

.dispencery_wrap tr:nth-child(3) {
	background: #bdf7f7;
}

.dispencery_wrap tr:nth-child(4) {
	background: #bdf7f7;
}

.dispencery_wrap tr:nth-child(48) {
	background: #f7f6bd;
}

.dispencery_wrap tr:nth-child(49) {
	background: #f7f6bd;
}

.dispencery_wrap tr:nth-child(50) {
	background: #f7f6bd;
}

.dispencery_wrap tr:nth-child(51) {
	background: #f7f6bd;
}

.dispencery_wrap tr:nth-child(52) {
	background: #f7f6bd;
}

.dispencery_wrap {
	font-size: 12px;
	overflow-x: scroll;
	width: 100%;
	float: left;
}

.fakeCls {
	font-size: 14px;
	text-align: center;
	font-weight: bold;
}

.dscTopCls {
	font-size: 14px;
	text-align: left;
	font-weight: bold;
}

tr.ttlDisc {
	background: #eee;
}

.ttlDisc td {
	font-size: 14px;
	padding: 5px;
	font-weight: bold;
}

.ttlDiscBtm td {
	text-align: left;
	border: none;
	padding: 6px;
}

tr.ttlDiscBtm td:nth-child(2), tr.ttlDiscBtm td:nth-child(3) {
	background: #f1f1c9;
	border: #d3d488 1px solid
}

.region-con h1 {
	font-size: 22px;
	font-weight: 200;
	margin-top: 20px;
	margin-bottom: 10px;
}

.region-con-sec {
	float: left;
	width: 100%;
	border-top: 1px solid #eee;
	border-bottom: 1px solid #eee;
	padding-top: 13px;
}

.region-con-drop {
	width: 50%; /* text-align: right; */
	float: left;
	padding-right: 30px;
	box-sizing: border-box;
}

.region-con-drop span {
	font-size: 18px;
	margin-right: 18px;
}

.region-con-drop select {
	width: 30%;
	padding: 7px;
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

.DTTT.btn-group {
	width: 100%;
	float: right;
	margin: 10px 0;
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
	href="dist/css/style.css">

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
<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
 -->
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$("#btnExport")
								.click(
										function(e) {
											e.preventDefault();

											//getting data from our table
											var data_type = 'data:application/vnd.ms-excel';
											var table_div = document
													.getElementById('table_wrapper');
											var table_html = table_div.outerHTML
													.replace(/ /g, '%20');

											var a = document.createElement('a');
											a.href = data_type + ', '
													+ table_html;
											a.download = 'Discripency-Formate_'
													+ Math
															.floor((Math
																	.random() * 9999999) + 1000000)
													+ '.xls';
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
						<div class="panel-heading">Discrepancy Format</div>
						<!-- /.panel-heading -->
						<div>
							<input type="button" class="btn btn-default qr-button"
								onclick="printDiv('printableArea')" value="Print" />
							<button id="btnExport" class="btn btn-default qr-button">Export
								to xls</button>
						</div>
						<div class="panel-body">

							<c:set var="dateRangeURL" value="discFormat" />
							<jsp:include page="reportDateRangeDiscrepancy.jsp" />

							<form id="showAll">
								<div class="dispencery_wrap">
									<div id="printableArea">
										<!-- <button id="btnExport" class="btn btn-default qr-button">Export to xls</button> -->
										<div id="table_wrapper">
											<table cellspacing="0">
												<tr>
													<th class="dscTopCls" colspan="24">DISCREPANCY SHEET
														FOR DTD ${currentDate}</th>
												</tr>

												<tr>
													<th class="fakeCls">Sr. No.</th>
													<th class="fakeCls">Time</th>
													<th class="fakeCls">Branch/EC</th>
													<th class="fakeCls"></th>
													<th class="fakeCls">Deno</th>
													<th class="fakeCls">Short</th>
													<th class="fakeCls">Excess</th>
													<th class="fakeCls" colspan="10">Note Serial Number &
														Discrepancy</th>
													<th class="fakeCls" colspan="2">Identification&nbsp;on
														Packet / Bundle</th>
													<th class="fakeCls">Accounting Transactions</th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
												</tr>

												<tr>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th class="fakeCls" colspan="5">Counterfeit</th>
													<th class="fakeCls" colspan="4">Mutilated</th>
													<th class="fakeCls">Others</th>
													<th class="fakeCls" colspan="2">Branch</th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
												</tr>

												<tr>
													<th>SR NO.</th>
													<th>Time</th>
													<th>Name of Branch</th>
													<th>Sol&nbsp;Id</th>
													<th>Deno</th>
													<th>Short</th>
													<th>Excess</th>
													<th>Customer Not Identified - No of Notes</th>
													<th>Customer Identified - No of Notes</th>
													<th>Easy/Hard to detect</th>
													<th>Parameter based on which classified&nbsp;as
														Counterfeit</th>
													<th>Serial Number of the Note</th>
													<th>Customer Not Identified - No of Notes</th>
													<th>Customer Identified - No of Notes</th>
													<th>Valuation (Mutilated 0.5 or 0)</th>
													<th>Serial Number of the Note</th>
													<th>If&nbsp;unknown then enter "Unknown"</th>
													<th>Name of Customer</th>
													<th>Account no</th>
													<th>SAIRREM (Counterfeit/ Shortage/Mutilated Value)</th>
													<th>SAMUTCUR (Mutilated Note Value)</th>
													<th>"SADSCASH (Write Off Account)"</th>
													<th>"SLIRREM (Excess)"</th>
													<th>PRINT YEAR</th>
													<th>Date On Shrink Wrap</th>
												</tr>

												<c:forEach var="row" items="${discrepancyList}">
													<c:forEach var="innerRow"
														items="${row.discrepancyAllocations}">
														<c:if test="${innerRow.status == 'RECEIVED'}">
															<c:set var="count" value="${count + 1}" scope="page" />
															<tr>
																<td>${count}</td>
																<td>${innerRow.timeOfDetection}</td>
																<td>${row.branch}</td>
																<td><fmt:formatNumber pattern="0000"
																		value="${row.solId}" /></td>
																<td>${innerRow.denomination}</td>
																<td><c:choose>
																		<c:when
																			test="${innerRow.discrepancyType == 'SHORTAGE'}">
												${innerRow.numberOfNotes}</c:when>
																		<c:otherwise></c:otherwise>
																	</c:choose></td>
																<td><c:choose>
																		<c:when test="${innerRow.discrepancyType == 'EXCESS'}">
												${innerRow.numberOfNotes}</c:when>
																		<c:otherwise></c:otherwise>
																	</c:choose></td>

																<!-- Fake Note Detail -->
																<td><c:choose>
																		<c:when
																			test="${innerRow.discrepancyType == 'FAKE' 
												&& (row.accountTellerCam == 'TELLER' || row.accountTellerCam == 'CAM')}">1</c:when>
																		<c:otherwise></c:otherwise>
																	</c:choose></td>
																<td><c:choose>
																		<c:when
																			test="${innerRow.discrepancyType == 'FAKE' 
												&& row.accountTellerCam == 'ACCOUNT'}">1</c:when>
																		<c:otherwise></c:otherwise>
																	</c:choose></td>
																<td><c:choose>
																		<c:when test="${innerRow.discrepancyType == 'FAKE'}"></c:when>
																		<c:otherwise></c:otherwise>
																	</c:choose></td>
																<td><c:choose>
																		<c:when test="${innerRow.discrepancyType == 'FAKE'}"></c:when>
																		<c:otherwise></c:otherwise>
																	</c:choose></td>
																<td><c:choose>
																		<c:when test="${innerRow.discrepancyType == 'FAKE'}">
												${innerRow.noteSerialNumber}</c:when>
																		<c:otherwise></c:otherwise>
																	</c:choose></td>

																<!-- Mutilated Note Detail -->
																<td><c:choose>
																		<c:when
																			test="${innerRow.discrepancyType == 'MUTILATED' 
												&& (row.accountTellerCam == 'TELLER' || row.accountTellerCam == 'CAM')}">1</c:when>
																		<c:otherwise></c:otherwise>
																	</c:choose></td>
																<td><c:choose>
																		<c:when
																			test="${innerRow.discrepancyType == 'MUTILATED' 
												&& row.accountTellerCam == 'ACCOUNT'}">1</c:when>
																		<c:otherwise></c:otherwise>
																	</c:choose></td>
																<td><c:choose>
																		<c:when
																			test="${innerRow.discrepancyType == 'MUTILATED' 
												&& innerRow.mutilType == 'HALF VALUE'}">0.5</c:when>
																		<c:when
																			test="${innerRow.discrepancyType == 'MUTILATED' 
												&& innerRow.mutilType == 'ZERO VALUE'}">0</c:when>
																		<c:otherwise></c:otherwise>
																	</c:choose></td>
																<td><c:choose>
																		<c:when
																			test="${innerRow.discrepancyType == 'MUTILATED'}">
												${innerRow.noteSerialNumber}</c:when>
																		<c:otherwise></c:otherwise>
																	</c:choose></td>

																<td><c:choose>
																		<c:when
																			test="${row.accountTellerCam == 'TELLER' 
												|| row.accountTellerCam == 'CAM'}">${row.customerName}/${row.accountNumber}</c:when>
																		<c:otherwise></c:otherwise>
																	</c:choose></td>
																<td><c:choose>
																		<c:when test="${row.accountTellerCam == 'ACCOUNT'}">
												${row.customerName}</c:when>
																		<c:otherwise></c:otherwise>
																	</c:choose></td>
																<td><c:choose>
																		<c:when
																			test="${row.accountTellerCam == 'ACCOUNT' || row.accountTellerCam == 'DSB'}">
												${row.accountNumber}</c:when>
																		<c:otherwise></c:otherwise>
																	</c:choose></td>
																<td>${innerRow.sairrem}</td>
																<td>${innerRow.samutcur}</td>
																<td>${innerRow.sadscash}</td>
																<td>${innerRow.excess}</td>
																<td>${innerRow.printYear}</td>
																<td><fmt:formatDate pattern="yyyy-MM-dd"
																		value="${innerRow.dateOnShrinkWrap}" /></td>
															</tr>
														</c:if>
													</c:forEach>

												</c:forEach>

												<tr class="ttlDisc">
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td>${sairremTotal}</td>
													<td>${samutcurTotal}</td>
													<td>${sadscashTotal}</td>
													<td>${excessTotal}</td>
													<td></td>
													<td></td>
												</tr>

												<tr class="ttlDiscBtm">
													<td colspan="14"></td>
													<td colspan="4">Amount to Parked by Link Branch in
														${linkBranchSolId}SAIRREM</td>
													<td>${sairremTotal+samutcurTotal}</td>
													<td colspan="5"></td>
												</tr>

												<tr class="ttlDiscBtm">
													<td colspan="14"></td>
													<td colspan="4">Amount to Parked by Link Branch in
														${linkBranchSolId}SADSCASH</td>
													<td>${sadscashTotal}</td>
													<td colspan="5"></td>
												</tr>

												<tr class="ttlDiscBtm">
													<td colspan="14"></td>
													<td colspan="4">Total Debit</td>
													<td>${sairremTotal+samutcurTotal+sadscashTotal}</td>
													<td colspan="5"></td>
												</tr>

												<tr class="ttlDiscBtm">
													<td colspan="14"></td>
													<td colspan="4">Amount to Parked by Link Branch in
														${linkBranchSolId}SLIRREM</td>
													<td>${excessTotal}</td>
													<td colspan="5"></td>
												</tr>

												<tr class="ttlDiscBtm">
													<td colspan="14"></td>
													<td colspan="4">Total Amount</td>
													<td>${sairremTotal+samutcurTotal+sadscashTotal-excessTotal}</td>
													<td colspan="5"></td>
												</tr>
											</table>

											<%--  <table cellspacing="0" cellpadding="10" border="1" style="text-align: center; font-family: arial; font-size: 12px;">
	                          <tr><td colspan="20" style="text-align: center; font-size: 16px; font-weight: bold;">Discrepancy Report as on -${currentDate}</td></tr>
	<tr><td>Sr. No.</td><td>Branch Name</td><td>Sol ID</td><td>Denomination</td><td>Time of detection</td><td>Total Amount</td><td colspan="13" style="text-align: center;">Discrepancies</td><td></td></tr>
	<tr><td></td><td></td><td></td><td></td><td></td><td></td><td colspan="2">Short</td><td colspan="2">Excess</td><td colspan="3">Mutilated</td><td colspan="6">Counterfeit /Fake</td><td>Customer category</td></tr>
	<tr><td></td><td></td><td></td><td></td><td></td><td></td><td >No. of Pieces</td><td>Amount (in Rs.)</td><td>No. of Pieces</td><td >Amount (in Rs.)</td><td>Serial No. of the Note</td><td>Amount (in Rs.)</td><td>Value (Full / Half / Reject)</td><td>Serial No. of the Note</td>
		<td>Amount (in Rs.)</td><td>Easy / Difficult to detect (500 / 1000)</td><td>Detectable under UV (Y / N) (500 / 1000)</td> <td>Parameter based on which classified as fake</td><td>Mapped to Customer Account No. / DSB / Branch Teller / SADSCASH</td><td>Bulk / Bullion / DSB / Retail*</td></tr>
		<tr><td></td><td></td><td></td><td></td><td></td><td></td><td ></td><td></td><td></td><td ></td><td></td><td></td><td></td><td></td>
		<td></td><td></td><td></td> <td></td><td></td><td></td></tr>
</table> --%>




										</div>
									</div>
								</div>
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

	<!-- DataTables JavaScript -->
	<script
		src="./resources/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="./resources/dist/js/sb-admin-2.js"></script>

	<!-- Page-Level Demo Scripts - Tables - Use for reference -->
	<!-- <script>
	 $(document).ready(function () {
		 var table = $('#tableValue').dataTable({
				"pagingType" : "full_numbers",
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
	</script> -->

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