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
<link rel="shortcut icon" href="./resources/logo/yes_favicon.ico"
	type="image/x-icon">
<link href="./resources/css/calendar.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.datetimepicker.css" />
<title> Currency Chest Book Reports</title>

<style type="text/css">
body {
	font-family: arial;
}

.container {
	width: 100%;
}

table, th, td {
	border: 1px solid black;
	border-collapse: collapse;
	padding: 5px;
}

.lg {
	width: 40%;
}

.sm {
	width: 7%;
	text-align: center;
	font-family: arial;
	font-size: 14px;
}

td.first {
	width: 20%;
}

td.a {
	width: 12%;
}

td.c {
	width: 120px;
}

td.a.cc {
	text-align: center;
}

td.a.none-border-1 {
	border-top: 1px solid #fff;
}

td.a.none-border {
	border-bottom: 1px solid #fff;
}

tr.eqq-con td {
	text-align: center;
	font-size: 11px;
}

td.a.none-border-1.no {
	text-align: left;
}

td.a.none-border-1.no.no-1 {
	text-align: center;
}

td.z-1 {
	width: 72.6%;
	text-align: center;
}

td.z-2 {
	width: 20.1%;
	text-align: center;
	border-bottom: 1px solid white;
}

td.qr {
	width: 6.9%;
}

td.center-c {
	width: 48.3%;
	text-align: center;
}

td.qrr {
	width: 7%;
}

tr.larg {
	height: 76px;
}

tr.eqq-con.bb td {
	padding: 0px;
}

td.nonee {
	border-top: 1px solid #fff;
}

td.a.cc.none-border-1.line {
	position: relative;
	top: -27px;
}

td.eqquall {
	width: 122px;
}

.containerr tr td {
	padding: 5px;
}
</style>

<style type="text/css">
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

<script type="text/javascript">
$(document).ready(function() {
	  $("#btnExport").click(function(e) {
	    e.preventDefault();

	    //getting data from our table
	    var data_type = 'data:application/vnd.ms-excel';
	    var table_div = document.getElementById('table_wrapper');
	    var table_html = table_div.outerHTML.replace(/ /g, '%20');

	    var a = document.createElement('a');
	    a.href = data_type + ', ' + table_html;
	    a.download = 'Currency-Chest-Book-Report_' + Math.floor((Math.random() * 9999999) + 1000000) + '.xls';
	    a.click();
	  });
	});

</script>

<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>
<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-85"
	data-genuitec-path="/Currency/src/main/webapp/jsp/currencyChestBook.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-85"
		data-genuitec-path="/Currency/src/main/webapp/jsp/currencyChestBook.jsp">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">Currency Chest Book</div>
						<div>
							<input type="button" class="btn btn-default qr-button"
								onclick="printDiv('printableArea')" value="Print" />
							<button id="btnExport" class="btn btn-default qr-button">Export
								to xls</button>
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">

							<%-- <c:set var="dateRangeURL" value="IO2Reports" />
						<jsp:include page="reportDateRange.jsp" /> --%>

							<div class="region-con">
								<form:form id="userPage" name="userPage" action="TE2Reports"
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

							<form id="showAll">
								<!-- <table class="table table-striped table-bordered table-hover" id="dataTables-example"> -->

								<div class="containerr" id="printableArea">
									<div id="table_wrapper">
										<table>
											<tr>
												<!-- <td class="lg">Form TE - 2</td> -->
												<td class="sm">Chest Slip Number</td>
												<td class="sm">
													<%-- ${chestSlipNumber} --%>
												</td>
												<td class="sm"></td>
												<td class="sm">Date</td>
												<td class="sm">${currentDate}</td>
											</tr>
											<tr>
												<td colspan="6">*Specify name and code of issue
													Office/Chest/STO/Mint : ICICI BANK LTD. ${icmcName},
													${icmcAddress}</td>
											</tr>
											<tr>
												<td colspan="6" style="text-align: center;">DETAILS OF
													NOTES (PIECES)</td>
											</tr>
										</table>

										<table width="100%">
											<tr>
												<td class="a none-border"></td>
												<td class="c"></td>
												<td style="text-align: center;">Number Of Pieces</td>
												<td class="a none-border"></td>
											</tr>
										</table>

										<table width="100%">
											<tr class="eqq-con">
												<td class="a none-border-1 no">Transaction In Notes</td>
												<td class="eqquall">Rs 1</td>
												<td class="eqquall">Rs 2</td>
												<td class="eqquall">Rs 5</td>
												<td class="eqquall">Rs 10</td>
												<td class="eqquall">Rs 20</td>
												<td class="eqquall">Rs 50</td>
												<td class="eqquall">Rs 100</td>
												<td class="eqquall">Rs 200</td>
												<td class="eqquall">Rs 500</td>
												<td class="eqquall">Rs 1000</td>
												<td class="eqquall">Rs 2000</td>
												<td class="eqquall">Total Pieces</td>
												<td class="a cc none-border-1 line">Total Value of Bank
													Notes (Rs)</td>
											</tr>

											<tr class="eqq-con">
												<td class="a none-border-1 no no-1">(1)</td>
												<td class="eqquall">(2)</td>
												<td class="eqquall">(3)</td>
												<td class="eqquall">(4)</td>
												<td class="eqquall">(5)</td>
												<td class="eqquall">(6)</td>
												<td class="eqquall">(7)</td>
												<td class="eqquall">(8)</td>
												<td class="eqquall">(9)</td>
												<td class="eqquall">(10)</td>
												<td class="eqquall">(25)</td>
												<td class="eqquall">(11)</td>
												<td class="a cc none-border-1">(12)</td>
											</tr>

											<tr class="eqq-con">
												<td>i) Opening Balance</td>
												<td class="eqquall">${summaryListForOpeningBalance.denomination1}</td>
												<td class="eqquall">${summaryListForOpeningBalance.denomination2}</td>
												<td class="eqquall">${summaryListForOpeningBalance.denomination5}</td>
												<td class="eqquall">${summaryListForOpeningBalance.denomination10}</td>
												<td class="eqquall">${summaryListForOpeningBalance.denomination20}</td>
												<td class="eqquall">${summaryListForOpeningBalance.denomination50}</td>
												<td class="eqquall">${summaryListForOpeningBalance.denomination100}</td>
												<td class="eqquall">${summaryListForOpeningBalance.denomination200}</td>
												<td class="eqquall">${summaryListForOpeningBalance.denomination500}</td>
												<td class="eqquall">${summaryListForOpeningBalance.denomination1000}</td>
												<td class="eqquall">${summaryListForOpeningBalance.denomination2000}</td>
												<td class="eqquall">${summaryListForOpeningBalance.totalInPieces}</td>
												<td class="eqquall">${summaryListForOpeningBalance.totalValueOfBankNotes}</td>

											</tr>

											<tr class="eqq-con">
												<td>ii) Deposit</td>
												<td class="eqquall">${summaryListForTotalDeposit.denomination1}</td>
												<td class="eqquall">${summaryListForTotalDeposit.denomination2}</td>
												<td class="eqquall">${summaryListForTotalDeposit.denomination5}</td>
												<td class="eqquall">${summaryListForTotalDeposit.denomination10}</td>
												<td class="eqquall">${summaryListForTotalDeposit.denomination20}</td>
												<td class="eqquall">${summaryListForTotalDeposit.denomination50}</td>
												<td class="eqquall">${summaryListForTotalDeposit.denomination100}</td>
												<td class="eqquall">${summaryListForTotalDeposit.denomination200}</td>
												<td class="eqquall">${summaryListForTotalDeposit.denomination500}</td>
												<td class="eqquall">0</td>
												<td class="eqquall">${summaryListForTotalDeposit.denomination2000}</td>
												<td class="eqquall">${summaryListForTotalDeposit.totalInPieces}</td>
												<td class="eqquall">${summaryListForTotalDeposit.totalValueOfBankNotes}</td>
											</tr>

											<tr class="eqq-con">
												<td>iii) Withdrawal</td>
												<td class="eqquall">${summaryListForTotalWithdrawal.denomination1}</td>
												<td class="eqquall">${summaryListForTotalWithdrawal.denomination2}</td>
												<td class="eqquall">${summaryListForTotalWithdrawal.denomination5}</td>
												<td class="eqquall">${summaryListForTotalWithdrawal.denomination10}</td>
												<td class="eqquall">${summaryListForTotalWithdrawal.denomination20}</td>
												<td class="eqquall">${summaryListForTotalWithdrawal.denomination50}</td>
												<td class="eqquall">${summaryListForTotalWithdrawal.denomination100}</td>
												<td class="eqquall">${summaryListForTotalWithdrawal.denomination200}</td>
												<td class="eqquall">${summaryListForTotalWithdrawal.denomination500}</td>
												<td class="eqquall">0</td>
												<td class="eqquall">${summaryListForTotalWithdrawal.denomination2000}</td>
												<td class="eqquall">${summaryListForTotalWithdrawal.totalInPieces}</td>
												<td class="eqquall">${summaryListForTotalWithdrawal.totalValueOfBankNotes}</td>
											<tr class="eqq-con">
												<td>iii) Total</td>
												<td class="eqquall">${summaryListTotalForNotes.denomination1}</td>
												<td class="eqquall">${summaryListTotalForNotes.denomination2}</td>
												<td class="eqquall">${summaryListTotalForNotes.denomination5}</td>
												<td class="eqquall">${summaryListTotalForNotes.denomination10}</td>
												<td class="eqquall">${summaryListTotalForNotes.denomination20}</td>
												<td class="eqquall">${summaryListTotalForNotes.denomination50}</td>
												<td class="eqquall">${summaryListTotalForNotes.denomination100}</td>
												<td class="eqquall">${summaryListTotalForNotes.denomination200}</td>
												<td class="eqquall">${summaryListTotalForNotes.denomination500}</td>
												<td class="eqquall">${summaryListTotalForNotes.denomination1000}</td>
												<td class="eqquall">${summaryListTotalForNotes.denomination2000}</td>
												<td class="eqquall">${summaryListTotalForNotes.totalInPieces}</td>
												<td class="eqquall">${summaryListTotalForNotes.totalValueOfBankNotes}</td>
											</tr>
										</table>

										<table width="100%">
											<tr>
												<td class="z-1">DETAILS OF RUPEE COINS</td>
												<!-- <td></td> -->
												<!-- <td class="z-2">PART C (SUMMARY)</td> -->
											</tr>
										</table>

										<table width="100%">

											<tr class="eqq-con bb">
												<td class="a none-border-1 no ">Transaction in Rupee
													Coins</td>
												<td class="eqquall">to @ Remittance</td>
												<td class="eqquall">Re 1 Notes</td>
												<td class="eqquall">Re 1 Coins</td>
												<td class="eqquall">Re 2 Coins</td>
												<td class="eqquall">Re 5 Coins</td>
												<td class="eqquall">Re 10 Coins</td>
												<td class="eqquall">Any Other Coins</td>
												<td class="eqquall">Total Pieces</td>
												<td class="eqquall">Total Value Of Coins</td>
												<td></td>

											</tr>

											<tr class="eqq-con">
												<td class="a none-border-1 no no-1">13</td>
												<td class="eqquall">14</td>
												<td class="eqquall">15</td>
												<td class="eqquall">16</td>
												<td class="eqquall">17</td>
												<td class="eqquall">18</td>
												<td class="eqquall">19</td>
												<td class="eqquall">20</td>
												<td class="eqquall">21</td>
												<td class="eqquall">22</td>
											</tr>

											<tr class="eqq-con">
												<td>Opening Balance (Coins)</td>
												<%-- <c:forEach var="row"
												items="${summaryListForCoins}"> --%>
												<td class="eqquall"></td>
												<td class="eqquall">0</td>
												<td class="eqquall">${summaryListForCoins.denomination1}</td>
												<td class="eqquall">${summaryListForCoins.denomination2}</td>
												<td class="eqquall">${summaryListForCoins.denomination5}</td>
												<td class="eqquall">${summaryListForCoins.denomination10}</td>
												<td class="eqquall">${summaryListForCoins.anyOtherCoin}</td>
												<td class="eqquall">${summaryListForCoins.totalCoinsPieces}</td>
												<td class="eqquall">${summaryListForCoins.totalValueOfCoins}</td>
												<%-- </c:forEach> --%>
											</tr>

											<tr class="eqq-con">
												<td class="a none-border-1 no">Deposit</td>
												<td class="eqquall"></td>
												<td class="eqquall">0</td>
												<td class="eqquall">${summaryListForCoinsDeposit.denomination1}</td>
												<td class="eqquall">${summaryListForCoinsDeposit.denomination2}</td>
												<td class="eqquall">${summaryListForCoinsDeposit.denomination5}</td>
												<td class="eqquall">${summaryListForCoinsDeposit.denomination10}</td>
												<td class="eqquall">0</td>
												<td class="eqquall">${summaryListForCoinsDeposit.totalCoinsPieces}</td>
												<td class="eqquall">${summaryListForCoinsDeposit.totalValueOfCoins}</td>
											</tr>


											<tr class="eqq-con">
												<td class="a none-border-1 no">Withdrawal</td>
												<td class="eqquall"></td>
												<td class="eqquall">0</td>
												<td class="eqquall">${summaryListForCoinWithdrawal.denomination1}</td>
												<td class="eqquall">${summaryListForCoinWithdrawal.denomination2}</td>
												<td class="eqquall">${summaryListForCoinWithdrawal.denomination5}</td>
												<td class="eqquall">${summaryListForCoinWithdrawal.denomination10}</td>
												<td class="eqquall">0</td>
												<td class="eqquall">${summaryListForCoinWithdrawal.totalCoinsPieces}</td>
												<td class="eqquall">${summaryListForCoinWithdrawal.totalValueOfCoins}</td>
											</tr>

											<tr class="eqq-con">
												<td class="a none-border-1 no">Coins Total</td>
												<td></td>
												<td>0</td>
												<td class="eqquall">${summaryListTotalForCoins.denomination1}</td>
												<td class="eqquall">${summaryListTotalForCoins.denomination2}</td>
												<td class="eqquall">${summaryListTotalForCoins.denomination5}</td>
												<td class="eqquall">${summaryListTotalForCoins.denomination10}</td>
												<td class="eqquall">0</td>
												<td class="eqquall">${summaryListTotalForCoins.totalCoinsPieces}</td>
												<td class="eqquall">${summaryListTotalForCoins.totalValueOfCoins}</td>
											</tr>
										</table>
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

	<script src="./resources/js/jquery.datetimepicker.js"></script>
	<script>
		$('#fromDate').datetimepicker({
			format : 'Y-m-d',
		});
	</script>

	<script type="text/javascript" src="./js/htmlInjection.js"></script>
	<script type="text/javascript" src="./js/print.js"></script>

</body>

</html>