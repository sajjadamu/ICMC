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
<title>ICICI : Cash Book Deposit</title>

<!-- Bootstrap Core CSS -->
<link
	href="./resources/bower_components/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.datetimepicker.css" />
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

<style>
.currencychest {
	text-align: center;
	width: 100%;
	font-size: 12px;
}

table, td {
	border: 1px solid black;
}

td {
	padding: 5px;
	margin: 5px;
}

}
div {
	width: 1009px;
}
</style>
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
											a.download = 'Cash-Book-Deposit_'
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
						<div class="panel-heading">Cash Book Deposit</div>
						<div>
							<input type="button" class="btn btn-default qr-button"
								onclick="printDiv('printableArea')" value="Print" />
							<button id="btnExport" class="btn btn-default qr-button">Export
								to xls</button>
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">


							<div class="region-con">
								<form:form id="userPage" name="userPage"
									action="cashBookDeposit" method="post"
									modelAttribute="reportDate" autocomplete="off">
									<div class="region-con-sec">
										<ul class="region-drop">
											<li>
												<table>
													<tr>
														<td><B>Select Date</B></td>
														<td><form:input type="text" path="fromDate"
																id="fromDate" name="fromDate" cssClass="form-control" />
														</td>
														<td><button type="submit" class="btn btn-default"
																value="Details" style="width: 99px;">Search</button></td>
													</tr>
												</table>
											</li>
										</ul>
									</div>
								</form:form>
							</div>

							<div class="currencychest" id="printableArea">
								<div id="table_wrapper">
									<table cellspacing="0">
										<tbody>
											<!-- <tr>
										  <td colspan="22" bgcolor="#e6e6e6">*********</td>
										</tr> -->

											<tr>
												<td colspan="22" align="left" bgcolor="#e6e6e6">Currency
													Chest</td>
											</tr>

											<tr>
												<td colspan="22" align="left" bgcolor="#e6e6e6">ICICI
													BANK LTD. ${icmcName}, ${icmcAddress}</td>
											</tr>

											<!-- <tr>
										  <td colspan="22" align="left" bgcolor="#e6e6e6">5</td>
										</tr> -->

											<tr>
												<td>Date</td>
												<td colspan="2">${date}
												<%-- <fmt:formatDate type="date"
														pattern="dd-MM-yyyy" dateStyle="short" timeStyle="short"
														value="<%=new java.util.Date()%>" /> --%>
														
														</td>
												<td colspan="17">Deposits</td>
												<td></td>
												<td></td>
											</tr>

											<tr>
												<td>Chest Slip No.</td>
												<td>${chestSlipNo}</td>
												<td></td>
												<td colspan="10">Notes in Pieces</td>
												<td></td>
												<td colspan="4">Coins in pieces</td>
												<td></td>
												<td></td>
												<td>Total Value</td>
												<td colspan="2">Signature</td>
											</tr>

											<tr>
												<td>S.No.</td>
												<td>Name of Branch</td>
												<td>Time of receipt</td>
												<td>2</td>
												<td>5</td>
												<td>10</td>
												<td>20</td>
												<td>50</td>
												<td>100</td>
												<td>200</td>
												<td>500</td>
												<td>1000</td>
												<td>2000</td>
												<td>Total pieces</td>
												<td>Value</td>
												<td>1</td>
												<td>2</td>
												<td>5</td>
												<td>10</td>
												<td>Govt. of India Re.1 notes</td>
												<td>Coins Value</td>
												<td>Including Notes and coins</td>
												<td>Custodian-1</td>
												<td>Custodian-2</td>
											</tr>
                                      <c:set var="totalValueOfNotesIBIT" value="${0}"/>
                                      <c:set var="totalPiesesOfNotesIBIT" value="${0}"/>
											<c:forEach var="row" items="${branchDepositList}">
												
												<c:if test="${row.value.branch != null}">
												<c:set var="count" value="${count + 1}" scope="page" />
												<tr>
													<td>${count}</td>
													<td>${row.value.branch}</td>
													<td>${row.value.receiptTime}</td>
													<td>${row.value.denom2Pieces}</td>
													<td>${row.value.denom5Pieces}</td>
													<td>${row.value.denom10Pieces}</td>
													<td>${row.value.denom20Pieces}</td>
													<td>${row.value.denom50Pieces}</td>
													<td>${row.value.denom100Pieces}</td>
													<td>${row.value.denom200Pieces}</td>
													<td>${row.value.denom500Pieces}</td>
													<td>${row.value.denom1000Pieces}</td>
													<td>${row.value.denom2000Pieces}</td>
													<td>${row.value.totalInPieces}</td>
													<td>${row.value.totalValueOfBankNotes}</td>
													<td>0</td>
													<td>0</td>
													<td>0</td>
													<td>0</td>
													<td>${row.value.denom1Pieces}</td>
													<td>0</td>
													<td>${row.value.totalValueOfBankNotes + row.value.denom1Pieces}</td>
													<td></td>
													<td></td>
												</tr></c:if>
											</c:forEach>

											<c:forEach var="row" items="${dsbDepositList}">
												<c:set var="count" value="${count + 1}" scope="page" />
												<tr>
													<td>${count}</td>
													<td>${row.value.name}-${row.value.accountNumber}</td>
													<td>${row.value.receiptTime}</td>
													<td>${row.value.denom2Pieces}</td>
													<td>${row.value.denom5Pieces}</td>
													<td>${row.value.denom10Pieces}</td>
													<td>${row.value.denom20Pieces}</td>
													<td>${row.value.denom50Pieces}</td>
													<td>${row.value.denom100Pieces}</td>
													<td>${row.value.denom200Pieces}</td>
													<td>${row.value.denom500Pieces}</td>
													<td>${row.value.denom1000Pieces}</td>
													<td>${row.value.denom2000Pieces}</td>
													<td>${row.value.totalInPieces}</td>
													<td>${row.value.totalValueOfBankNotes}</td>
													<td>0</td>
													<td>0</td>
													<td>0</td>
													<td>0</td>
													<td>${row.value.denom1Pieces}</td>
													<td>0</td>
													<td>${row.value.totalValueOfBankNotes + row.value.denom1Pieces}</td>
													<td></td>
													<td></td>
												</tr>
											</c:forEach>

											<c:forEach var="row" items="${bankDepositList}">
												<c:set var="count" value="${count + 1}" scope="page" />
												<tr>
													<td>${count}</td>
													<td>${row.value.bankName}-${row.value.rtgsUTRNo}</td>
													<td>0.00</td>
													<td>${row.value.denom2Pieces}</td>
													<td>${row.value.denom5Pieces}</td>
													<td>${row.value.denom10Pieces}</td>
													<td>${row.value.denom20Pieces}</td>
													<td>${row.value.denom50Pieces}</td>
													<td>${row.value.denom100Pieces}</td>
													<td>${row.value.denom200Pieces}</td>
													<td>${row.value.denom500Pieces}</td>
													<td>${row.value.denom1000Pieces}</td>
													<td>${row.value.denom2000Pieces}</td>
													<td>${row.value.totalInPieces}</td>
													<td>${row.value.totalValueOfBankNotes}</td>
													<td>0</td>
													<td>0</td>
													<td>0</td>
													<td>0</td>
													<td>${row.value.denom1Pieces}</td>
													<td>0</td>
													<td>${row.value.totalValueOfBankNotes + row.value.denom1Pieces}</td>
													<td></td>
													<td></td>
												</tr>
											</c:forEach>
											<c:set var="totalCoins1" value="${0}" />
											<c:set var="totalCoins2" value="${0}"/>
											<c:set var="totalCoins5" value="${0}"/>
											<c:set var="totalCoins10" value="${0}"/>
                                            <c:set var="sumTotalValueOfCoins" value="${0}"/>
											<c:forEach var="row" items="${freshFromRBIList}">
												<c:set var="count" value="${count + 1}" scope="page" />
												<tr>
													<td>${count}</td>
													<td>Fresh Remittance-${row.value.rbiOrderNo}</td>
													<td>0.00</td>
													<td>${row.value.denom2Pieces}</td>
													<td>${row.value.denom5Pieces}</td>
													<td>${row.value.denom10Pieces}</td>
													<td>${row.value.denom20Pieces}</td>
													<td>${row.value.denom50Pieces}</td>
													<td>${row.value.denom100Pieces}</td>
													<td>${row.value.denom200Pieces}</td>
													<td>${row.value.denom500Pieces}</td>
													<td>${row.value.denom1000Pieces}</td>
													<td>${row.value.denom2000Pieces}</td>
													<td>${row.value.totalInPieces}</td>
													<td>${row.value.totalValueOfBankNotes}</td>
													<td>${row.value.denom1CoinsPieces}</td>
													<c:set var="totalCoins1" value="${totalCoins1+(row.value.denom1CoinsPieces)}"/>
													<td>${row.value.denom2CoinsPieces}</td>
													<c:set var="totalCoins2" value="${totalCoins2+(row.value.denom2CoinsPieces)}"/>
													<td>${row.value.denom5CoinsPieces}</td>
													<c:set var="totalCoins5" value="${totalCoins5+(row.value.denom5CoinsPieces)}"/>
													<td>${row.value.denom10CoinsPieces}</td>
													<c:set var="totalCoins10" value="${totalCoins10+(row.value.denom10CoinsPieces)}"/>
													<td>${row.value.denom1Pieces}</td>
													<td>${row.value.totalValueOfCoins}</td>
													<c:set var="sumTotalValueOfCoins" value="${sumTotalValueOfCoins +(row.value.totalValueOfCoins)}"/>
													<td>${row.value.totalValueOfBankNotes + row.value.denom1Pieces + row.value.totalValueOfCoins}</td>
													<td></td>
													<td></td>
												</tr>
											</c:forEach>

											<c:forEach var="row" items="${dirvDepositList}">
												<c:set var="count" value="${count + 1}" scope="page" />
												<tr>
													<td>${count}</td>
													<td>${row.value.bankName}-${row.value.rbiOrderNo}</td>
													<td>0.00</td>
													<td>${row.value.denom2Pieces}</td>
													<td>${row.value.denom5Pieces}</td>
													<td>${row.value.denom10Pieces}</td>
													<td>${row.value.denom20Pieces}</td>
													<td>${row.value.denom50Pieces}</td>
													<td>${row.value.denom100Pieces}</td>
													<td>${row.value.denom200Pieces}</td>
													<td>${row.value.denom500Pieces}</td>
													<td>${row.value.denom1000Pieces}</td>
													<td>${row.value.denom2000Pieces}</td>
													<td>${row.value.totalInPieces}</td>
													<td>${row.value.totalValueOfBankNotes}</td>
													<td>0</td>
													<td>0</td>
													<td>0</td>
													<td>0</td>
													<td>${row.value.denom1Pieces}</td>
													<td>0</td>
													<td>${row.value.totalValueOfBankNotes + row.value.denom1Pieces}</td>
													<td></td>
													<td></td>
												</tr>
											</c:forEach>

											<tr>
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
												<td></td>
												<td></td>
												<td></td>
												<td></td>
											</tr>
                                     
                                         <c:forEach var="row" items="${ibitListValues}">
												<c:set var="count" value="${count + 1}" scope="page" />
												<tr>
													<td></td>
													<td>${servicingICMC}-Processing</td>
													<td></td>
												    <td><b>${row.denom2Pieces}</b></td>
													<td><b>${row.denom5Pieces}</b></td>
													<td><b>${row.denom10Pieces}</b></td>
													<td><b>${row.denom20Pieces}</b></td>
													<td><b>${row.denom50Pieces}</b></td>
													<td><b>${row.denom100Pieces}</b></td>
													<td><b>${row.denom200Pieces}</b></td>
													<td><b>${row.denom500Pieces}</b></td>
													<td><b>${row.denom1000Pieces}</b></td>
													<td><b>${row.denom2000Pieces}</b></td> 
													<td>${row.denom2Pieces+row.denom5Pieces+row.denom10Pieces+row.denom20Pieces+row.denom50Pieces+row.denom100Pieces+row.denom200Pieces+row.denom500Pieces+row.denom1000Pieces+row.denom2000Pieces}</td>
													<c:set var="totalPiesesOfNotesIBIT" value="${totalPiesesOfNotesIBIT+row.denom2Pieces+row.denom5Pieces+row.denom10Pieces+row.denom20Pieces+row.denom50Pieces+row.denom100Pieces+row.denom200Pieces+row.denom500Pieces+row.denom1000Pieces+row.denom2000Pieces}" />
													<td>${row.denom2Pieces*2+row.denom5Pieces*5+row.denom10Pieces*10+row.denom20Pieces*20+row.denom50Pieces*50+row.denom100Pieces*100+row.denom200Pieces*200+row.denom500Pieces*500+row.denom1000Pieces*1000+row.denom2000Pieces*2000}</td>
													<c:set var="totalValueOfNotesIBIT" value="${totalValueOfNotesIBIT+(row.denom2Pieces*2+row.denom5Pieces*5+row.denom10Pieces*10+row.denom20Pieces*20+row.denom50Pieces*50+row.denom100Pieces*100+row.denom200Pieces*200+row.denom500Pieces*500+row.denom1000Pieces*1000+row.denom2000Pieces*2000)}" />
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td>${row.denom2Pieces*2+row.denom5Pieces*5+row.denom10Pieces*10+row.denom20Pieces*20+row.denom50Pieces*50+row.denom100Pieces*100+row.denom200Pieces*200+row.denom500Pieces*500+row.denom1000Pieces*1000+row.denom2000Pieces*2000}</td>
													<td></td><td></td><td></td>
												</tr>
											</c:forEach>
											<tr>
												<td></td>
												<td><b>TOTAL</b></td>
												<td></td>
												<td><b>${modelMap.denom2TotalPieces}</b></td>
												<td><b>${modelMap.denom5TotalPieces}</b></td>
												<td><b>${modelMap.denom10TotalPieces}</b></td>
												<td><b>${modelMap.denom20TotalPieces}</b></td>
												<td><b>${modelMap.denom50TotalPieces}</b></td>
												<td><b>${modelMap.denom100TotalPieces}</b></td>
												<td><b>${modelMap.denom200TotalPieces}</b></td>
												<td><b>${modelMap.denom500TotalPieces}</b></td>
												<td><b>${modelMap.denom1000TotalPieces}</b></td>
												<td><b>${modelMap.denom2000TotalPieces}</b></td>
												<td><b>${modelMap.totalInPieces+totalPiesesOfNotesIBIT}</b></td>
												<td><b>${modelMap.totalValueOfBankNotes+totalValueOfNotesIBIT}</b></td>
												<td><b>${totalCoins1}</b></td>
												<td><b>${totalCoins2}</b></td>
												<td><b>${totalCoins5}</b></td>
												<td><b>${totalCoins10}</b></td>
												<td><b>${modelMap.denom1TotalPieces}</b></td>
												<td><b>${sumTotalValueOfCoins}</b></td>
												<td><b>${modelMap.totalValueOfBankNotes + modelMap.denom1TotalPieces + totalValueOfNotesIBIT + sumTotalValueOfCoins}</b></td>
												<td></td>
												<td></td>
											</tr>
											<tr>
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
												<td></td>
												<td></td>
												<td></td>
												<td></td>
											</tr>

											
										</tbody>
									</table>
								</div>
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

	<!-- DataTables JavaScript -->
	<script
		src="./resources/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="./resources/dist/js/sb-admin-2.js"></script>

	<script type="text/javascript" src="./js/htmlInjection.js"></script>
	<script type="text/javascript" src="./js/print.js"></script>
	<script src="./resources/js/jquery.datetimepicker.js"></script>
	<script>
		$('#fromDate').datetimepicker({
			format : 'Y-m-d',
		});
	</script>
</body>

</html>