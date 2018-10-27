<!DOCTYPE html>
<%@page import="com.chest.currency.entity.model.CRA"%>
<%@page import="java.util.Date"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.List"%>
<%@page import="com.mysema.query.Tuple"%>
<html lang="en">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.datetimepicker.css" />
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="./resources/logo/favicon.ico"
	type="image/x-icon">
<title>ICICI : Cash Book Withdrawal</title>

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
											a.download = 'Cash-Book-Withdrawal_'
													+ Math
															.floor((Math
																	.random() * 9999999) + 1000000)
													+ '.xls';
											a.click();
										});
					});
</script>
<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>
<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-74"
	data-genuitec-path="/Currency/src/main/webapp/jsp/cashBookWithdrawal.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-74"
		data-genuitec-path="/Currency/src/main/webapp/jsp/cashBookWithdrawal.jsp">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">Cash Book Withdrawal</div>
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
									action="cashBookWithdrawal" method="post"
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

											<tr>
												<td colspan="22" align="left" bgcolor="#e6e6e6">Currency
													Chest</td>
											</tr>
											<tr>
												<td colspan="22" align="left" bgcolor="#e6e6e6">ICICI
													BANK LTD. ${icmcName}, ${icmcAddress}</td>
											</tr>

											<tr>
												<td>Date</td>
												<td colspan="2"><fmt:formatDate type="date"
														pattern="dd-MM-yyyy" dateStyle="short" timeStyle="short"
														value="<%=new java.util.Date()%>" /></td>
												<td colspan="17">Withdrawals</td>
												<td></td>
												<td></td>
											</tr>

											<tr>
												<td>Chest Slip No.</td>
												<td>0</td>
												<td></td>
												<td colspan="9">Notes in Pieces</td>
												<td>Notes value</td>
												<td colspan="4">Coins in pieces</td>
												<td></td>
												<td></td>
												<td>Total Value</td>
												<td colspan="2">Signature of vault Custodian</td>
											</tr>

											<tr>
												<td>S.No.</td>
												<td>Name of Branch</td>
												<td>Time of Withdrawal</td>
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
												<td>Vault Custodian-1</td>
												<td>Vault Custodian-2</td>
											</tr>

											<c:set var="notes1" value="${0}" />
											<c:set var="notes2" value="${0}" />
											<c:set var="notes5" value="${0}" />
											<c:set var="notes10" value="${0}" />
											<c:set var="notes20" value="${0}" />
											<c:set var="notes50" value="${0}" />
											<c:set var="notes100" value="${0}" />
											<c:set var="notes200" value="${0}" />
											<c:set var="notes500" value="${0}" />
											<c:set var="notes1000" value="${0}" />
											<c:set var="totalPieces" value="${0}" />
											<c:set var="totalValues" value="${0}" />
											<c:set var="totalValueNotes" value="${0}" />
											<c:set var="total1RsPieces" value="${0}" />
											<c:set var="coins1" value="${0}" />
											<c:set var="coins2" value="${0}" />
											<c:set var="coins5" value="${0}" />
											<c:set var="coins10" value="${0}" />
											<c:set var="totalValueAllCoins" value="${0}" />

											<c:forEach var="row" items="${sasList}">
												<c:set var="count" value="${count + 1}" scope="page" />
												<tr>
													<td>${count}</td>
													<td>${row.branch}</td>
													<td><fmt:formatDate pattern="hh:mm"
															value="${row.insertTime.time}" /></td>
													<td>${(row.totalValueOfNotesRs5F + row.totalValueOfNotesRs5I+row.totalValueOfNotesRs5U)*1000}</td>
													<td>${(row.totalValueOfNotesRs10F + row.totalValueOfNotesRs10I)*1000}</td>
													<td>${(row.totalValueOfNotesRs20F + row.totalValueOfNotesRs20I)*1000}</td>
													<td>${(row.totalValueOfNotesRs50F + row.totalValueOfNotesRs50I)*1000}</td>
													<td>${(row.totalValueOfNotesRs100A + row.totalValueOfNotesRs100F + row.totalValueOfNotesRs100I)*1000}</td>
													<td>${(row.totalValueOfNotesRs200A + row.totalValueOfNotesRs200F + row.totalValueOfNotesRs200I)*1000}</td>
													<td>${(row.totalValueOfNotesRs500A + row.totalValueOfNotesRs500F + row.totalValueOfNotesRs500I)*1000}</td>
													<td>${(row.totalValueOfNotesRs1000A + row.totalValueOfNotesRs1000F + row.totalValueOfNotesRs1000I)*1000}</td>
													<td>${(row.totalValueOfNotesRs2000A + row.totalValueOfNotesRs2000F + row.totalValueOfNotesRs2000I)*1000}</td>
													<td>${(row.totalValueOfNotesRs5F + row.totalValueOfNotesRs5I+row.totalValueOfNotesRs5U+
											   row.totalValueOfNotesRs10F + row.totalValueOfNotesRs10I+row.totalValueOfNotesRs10U+
											   row.totalValueOfNotesRs20F + row.totalValueOfNotesRs20I+row.totalValueOfNotesRs20U+
											   row.totalValueOfNotesRs50F + row.totalValueOfNotesRs50I+row.totalValueOfNotesRs50U+
											   row.totalValueOfNotesRs100A + row.totalValueOfNotesRs100F + row.totalValueOfNotesRs100I+row.totalValueOfNotesRs100U+
											   row.totalValueOfNotesRs500A + row.totalValueOfNotesRs500F + row.totalValueOfNotesRs500I+row.totalValueOfNotesRs500U+
											   row.totalValueOfNotesRs1000A + row.totalValueOfNotesRs1000F + row.totalValueOfNotesRs1000I+
											   row.totalValueOfNotesRs2000A + row.totalValueOfNotesRs2000F + row.totalValueOfNotesRs2000I+ row.totalValueOfNotesRs2000U)*1000}
													</td>
													<c:set var="totalValue1"
														value="${row.totalValueOfCoinsRs1*2500}" />
													<c:set var="totalValue2"
														value="${(row.totalValueOfCoinsRs2*2500)*2}" />
													<c:set var="totalValue5"
														value="${(row.totalValueOfCoinsRs5*2500)*5}" />
													<c:set var="totalValue10"
														value="${(row.totalValueOfCoinsRs10*2500)*10}" />
													<%-- <td>${row.totalValue}</td> --%>
													<c:set var="totalValueNotes"
														value="${row.totalValue-(totalValue1+totalValue2+totalValue5+totalValue10)}" />
													<td>${totalValueNotes}</td>
													<td>${row.totalValueOfCoinsRs1*2500}</td>
													<td>${row.totalValueOfCoinsRs2*2500}</td>
													<td>${row.totalValueOfCoinsRs5*2500}</td>
													<td>${row.totalValueOfCoinsRs10*2500}</td>
													<td>${(row.totalValueOfNotesRs1F + row.totalValueOfNotesRs1I + row.totalValueOfNotesRs1U)*1000}</td>
													<td>${totalValue1+totalValue2+totalValue5+totalValue10}</td>
													<c:set var="totalValueAllCoins"
														value="${totalValueAllCoins+(totalValue1+totalValue2+totalValue5+totalValue10)}" />
													<td>${row.totalValue}</td>
													<td></td>
													<td></td>
												</tr>
												<c:set var="notes1"
													value="${notes1+(row.totalValueOfNotesRs1F + row.totalValueOfNotesRs1I + row.totalValueOfNotesRs1U)*1000}" />
												<c:set var="notes5"
													value="${notes5+(row.totalValueOfNotesRs5F + row.totalValueOfNotesRs5I+row.totalValueOfNotesRs5U)*1000}" />
												<c:set var="notes10"
													value="${notes10+(row.totalValueOfNotesRs10F + row.totalValueOfNotesRs10I+ row.totalValueOfNotesRs10U)*1000}" />
												<c:set var="notes20"
													value="${notes20+(row.totalValueOfNotesRs20F + row.totalValueOfNotesRs20I+ row.totalValueOfNotesRs20U)*1000}" />
												<c:set var="notes50"
													value="${notes50+(row.totalValueOfNotesRs50F + row.totalValueOfNotesRs50I+row.totalValueOfNotesRs50U)*1000}" />
												<c:set var="notes100"
													value="${notes100+(row.totalValueOfNotesRs100A + row.totalValueOfNotesRs100F + row.totalValueOfNotesRs100I+ row.totalValueOfNotesRs100U)*1000}" />
												<c:set var="notes200"
													value="${notes200+(row.totalValueOfNotesRs200A + row.totalValueOfNotesRs200F + row.totalValueOfNotesRs200I + row.totalValueOfNotesRs200U)*1000}" />
												<c:set var="notes500"
													value="${notes500+(row.totalValueOfNotesRs500A + row.totalValueOfNotesRs500F + row.totalValueOfNotesRs500I+ row.totalValueOfNotesRs500U)*1000}" />
												<c:set var="notes1000"
													value="${notes1000+(row.totalValueOfNotesRs1000A + row.totalValueOfNotesRs1000F + row.totalValueOfNotesRs1000I)*1000}" />
												<c:set var="notes2000"
													value="${notes2000+(row.totalValueOfNotesRs2000A + row.totalValueOfNotesRs2000F + row.totalValueOfNotesRs2000I+ row.totalValueOfNotesRs2000U)*1000}" />
												<c:set var="coins1"
													value="${coins1+(row.totalValueOfCoinsRs1)*2500}" />
												<c:set var="coins2"
													value="${coins2+(row.totalValueOfCoinsRs2)*2500}" />
												<c:set var="coins5"
													value="${coins5+(row.totalValueOfCoinsRs5)*2500}" />
												<c:set var="coins10"
													value="${coins10+(row.totalValueOfCoinsRs10)*2500}" />
												<c:set var="totalPieces"
													value="${totalPieces+(row.totalValueOfNotesRs5F + row.totalValueOfNotesRs5I+row.totalValueOfNotesRs5U+
											   row.totalValueOfNotesRs10F + row.totalValueOfNotesRs10I+row.totalValueOfNotesRs10U+
											   row.totalValueOfNotesRs20F + row.totalValueOfNotesRs20I+row.totalValueOfNotesRs20U+
											   row.totalValueOfNotesRs50F + row.totalValueOfNotesRs50I+row.totalValueOfNotesRs50U+
											   row.totalValueOfNotesRs100A + row.totalValueOfNotesRs100F + row.totalValueOfNotesRs100I+row.totalValueOfNotesRs100U+
											    row.totalValueOfNotesRs200A + row.totalValueOfNotesRs200F + row.totalValueOfNotesRs200I+row.totalValueOfNotesRs200U+
											   row.totalValueOfNotesRs500A + row.totalValueOfNotesRs500F + row.totalValueOfNotesRs500I+row.totalValueOfNotesRs500U+
											   row.totalValueOfNotesRs1000A + row.totalValueOfNotesRs1000F + row.totalValueOfNotesRs1000I+
											   row.totalValueOfNotesRs2000A + row.totalValueOfNotesRs2000F + row.totalValueOfNotesRs2000I+ row.totalValueOfNotesRs2000U)*1000}" />

												<c:set var="total1RsPieces"
													value="${total1RsPieces+(row.totalValueOfNotesRs1F + row.totalValueOfNotesRs1I + row.totalValueOfNotesRs1U)*1000}" />
												<c:set var="totalValues"
													value="${totalValues+row.totalValue}" />

											</c:forEach>



											<c:forEach var="row" items="${craPaymentList}">
												<c:set var="count" value="${count + 1}" scope="page" />
												<tr>
													<td>${count}</td>
													<%--  <td><%=cra.getVendor()%>-<%=cra.getMspName()%></td> --%>
													<td>${row.value.branchName}-${row.value.vendor}-${row.value.msp}</td>
													<td><fmt:formatDate pattern="hh:mm"
															value="${row.value.insertTime.time}" /></td>
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
													<td>${row.value.totalValueOfBankNotes+row.value.denom1Pieces}</td>
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

											<c:forEach var="row" items="${dirvList}">
												<c:set var="count" value="${count + 1}" scope="page" />
												<tr>
													<td>${count}</td>
													<td>${row.value.branchName}</td>
													<td><fmt:formatDate pattern="hh:mm"
															value="${row.value.insertTime.time}" /></td>
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
													<td>${row.value.totalValueOfBankNotes+row.value.denom1Pieces}</td>
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


											<c:forEach var="row" items="${otherBankAllocationList}">
												<c:set var="count" value="${count + 1}" scope="page" />
												<tr>
													<td>${count}</td>
													<td>${row.value.branchName}-${row.value.bankName}</td>
													<td><fmt:formatDate pattern="hh:mm"
															value="${row.value.insertTime.time}" /></td>
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
													<td>${row.value.totalValueOfBankNotes+row.value.denom1Pieces}</td>
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

											<c:forEach var="row"
												items="${soiledAllocationAllocationList}">
												<c:set var="count" value="${count + 1}" scope="page" />
												<tr>
													<td>${count}</td>
													<td>Soiled Remittance</td>
													<td>0.00</td>
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
													<td>${row.value.totalValueOfBankNotes+row.value.denom1Pieces}</td>
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

											<c:forEach var="row" items="${ibitListValues}">
												<c:set var="count" value="${count + 1}" scope="page" />
												<tr>
													<td>${count}</td>
													<td>${servicingICMC}-Processing</td>
													<td></td>
													<td>${row.denom5Pieces}</td>
													<td>${row.denom10Pieces}</td>
													<td>${row.denom20Pieces}</td>
													<td>${row.denom50Pieces}</td>
													<td>${row.denom100Pieces}</td>
													<td>${row.denom200Pieces}</td>
													<td>${row.denom500Pieces}</td>
													<td>${row.denom1000Pieces}</td>
													<td>${row.denom2000Pieces}</td>
													<td>${row.denom5Pieces+row.denom10Pieces+row.denom20Pieces+row.denom50Pieces+row.denom100Pieces+row.denom500Pieces+row.denom1000Pieces+row.denom2000Pieces}</td>
													<td>${row.denom5Pieces*5+row.denom10Pieces*10+row.denom20Pieces*20+row.denom50Pieces*50+row.denom100Pieces*100+row.denom500Pieces*500+row.denom1000Pieces*1000+row.denom2000Pieces*2000}</td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
											</c:forEach>

											<tr>
												<td></td>
												<td><b>TOTAL</b></td>
												<td></td>
												<td><b>${modelMap.denom5TotalPieces+notes5}</b></td>
												<td><b>${modelMap.denom10TotalPieces+notes10}</b></td>
												<td><b>${modelMap.denom20TotalPieces+notes20}</b></td>
												<td><b>${modelMap.denom50TotalPieces+notes50}</b></td>
												<td><b>${modelMap.denom100TotalPieces+notes100}</b></td>
												<td><b>${modelMap.denom200TotalPieces+notes200}</b></td>
												<td><b>${modelMap.denom500TotalPieces+notes500}</b></td>
												<td><b>${modelMap.denom1000TotalPieces+notes1000}</b></td>
												<td><b>${modelMap.denom2000TotalPieces+notes2000}</b></td>
												<td><b>${modelMap.totalInPieces+totalPieces}</b></td>
												<td><b>${modelMap.totalValueOfBankNotes+totalValues+modelMap.denom1TotalPieces-totalValueAllCoins}</b></td>
												<td><b>${modelMap.coin1TotalPieces+coins1}</b></td>
												<td><b>${modelMap.coin2TotalPieces+coins2}</b></td>
												<td><b>${modelMap.coin5TotalPieces+coins5}</b></td>
												<td><b>${modelMap.coin10TotalPieces+coins10}</b></td>
												<%-- <td><b>${modelMap.denom1TotalPieces+totalPieces}</b></td> --%>
												<td><b>${modelMap.denom1TotalPieces+total1RsPieces}</b></td>
												<td><b>${totalValueAllCoins}</b></td>
												<%-- <td><b>${modelMap.totalValueOfBankNotes + modelMap.denom1TotalPieces}</b></td> --%>
												<td><b>${modelMap.totalValueOfBankNotes+totalValues+modelMap.denom1TotalPieces}</b></td>
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