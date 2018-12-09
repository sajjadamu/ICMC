<!DOCTYPE html>
<html lang="en">
<%@page import="java.math.BigDecimal"%>
<%@page import="com.mysema.query.Tuple"%>
<%@page import="java.util.List"%>
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
<title>ICICI : Charge Report</title>

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

<style>
.totalICMCBal {
	text-align: center;
	font-size: 26px;
	background: #f1f1f1;
	padding: 3px 0;
	margin-top: -11px;
	position: relative;
}
</style>

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
						<div class="panel-heading">Charge Report</div>
						<div>
							<input type="button" class="btn btn-default qr-button"
								onclick="printDiv('printableArea')" value="Print" />
							<button id="btnExport" class="btn btn-default qr-button">Export
								to xls</button>
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<%-- <form id="showAll"> --%>
							<form:form id="userPage" name="userPage" action="#" method="post"
								modelAttribute="user">
								<div class="row">
									<div class="col-sm-12">
										<div class="col-sm-2 no-margin"></div>
									</div>
									<div class="col-sm-12"></div>
									<!-- /.row (nested) -->
									<div class="row bins" id="printableArea">
										<div class="totalICMCBal">
											<label>CHARGE REPORT</label>
										</div>
										<div class="col-sm-12">
											<div class="col-sm-6">
												<span>Date: <b>${currentDate}</b></span> <br> <br>
												The Currency Management Unit <br> Chandivali <br>
												Mumbai <br>
											</div>
											<br>

										</div>
										<div class="col-sm-12">
											<div class="col-sm-10">
												<br> <br> Dear Sir, <br> <br>
												<p>
													I, the undersigned, Reeta Bhatt has taken over the charge
													of the Currency Chest from Anurag Bajpai ,on
													${currentDate}. I have checked the cash balance amounting
													to <b> Rs <strong> <fmt:formatNumber
																type="number" maxFractionDigits="3"
																value="${totalICMCBalance}" />
													</strong>/-
													</b> at the opening of ${currentDate} by verifying the bundles
													and packets are found correct. The details are given below
													:-
												</p>
											</div>
										</div>

										<div id="converter">
											<table class="table table-striped table-bordered table-hover">
												<thead>
													<tr>
														<th>DENOMINATION</th>
														<th>ISSUABLE</th>
														<th>Non-ISSUABLE</th>
														<th>TOTAL</th>
														<th class="tbl-txt">VALUE</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach var="summaryRecord" items="${summaryRecords}">
														<tr>
															<td>${summaryRecord.key}</td>
															<td>${summaryRecord.value.issuable}</td>
															<fmt:parseNumber var="soiled"
																value="${summaryRecord.value.soiled}" type="number" />
															<fmt:parseNumber var="mutilated"
																value="${summaryRecord.value.mutilated}" type="number" />
															<td>${soiled + mutilated}</td>
															<td>${summaryRecord.value.total}</td>
															<td class="tbl-txt">${summaryRecord.key*1000*summaryRecord.value.total}</td>
														</tr>
													</c:forEach>
													<tr>
														<th>Coins</th>
													</tr>
													<%!Integer denomination = 0;
	BigDecimal numberOfBags = new BigDecimal(0);
	BigDecimal totalValueFor10 = new BigDecimal(0);
	BigDecimal totalValueForOthers = new BigDecimal(0);%>

													<%
														List<Tuple> listTuple = (List<Tuple>) request.getAttribute("summaryListForCoins");
															for (Tuple tuple : listTuple) {
																denomination = tuple.get(1, Integer.class);
																numberOfBags = tuple.get(2, BigDecimal.class);
													%><tr>
														<td><%=tuple.get(1, Integer.class)%></td>
														<td><%=tuple.get(2, BigDecimal.class)%></td>
														<td>0</td>
														<td><%=tuple.get(2, BigDecimal.class)%></td>
														<%
															if (denomination == 10) {
																		totalValueFor10 = numberOfBags
																				.multiply(new BigDecimal(denomination).multiply(new BigDecimal(2000)));
														%>
														<td class="tbl-txt"><%=totalValueFor10.setScale(3)%></td>
														<%
															} else {
																		totalValueForOthers = numberOfBags
																				.multiply(new BigDecimal(denomination).multiply(new BigDecimal(2500)));
														%>
														<td class="tbl-txt"><%=totalValueForOthers.setScale(3)%></td>
														<%
															}
														%>
													</tr>
													<%
														}
													%>
													<tr>
														<td>Total value</td>
														<td></td>
														<td></td>
														<th style="text-align: right;">
														<strong> <fmt:formatNumber
																	type="number" maxFractionDigits="3"
																	value="${totalICMCBalance}" />
															</strong></th>
													</tr>
												</tbody>
											</table>
											<div>${totalICMCBalanceInWords}</div>
										</div>
										<div class="col-sm-12">
											<div class="col-sm-8">
												I have taken possession of <br> A.Secret Documents <br>B.Steel
												Sealer <br>C. Keys of Strong Room Door, Grill Door and
												Cash Bins (No. of keys)
											</div>
											<div class="col-sm-4">
												Yes <br>Not Issued By RBI <br>56 Keys, 11 Moving
												Bin & 9 FICN Boxes Keys.
											</div>
										</div>

										<div class="col-sm-4">Yours faithfully,</div>
										<div class="col-sm-12">
											<div class="col-sm-8">
												Reeta Bhatt <br>Name & Signature of the <br>In-Coming
												Officer
											</div>
											<div class="col-sm-4">
												Anurag Bajpai <br>Name & Signature of the <br>Out-going
												Officer
											</div>
										</div>
									</div>
								</div>
							</form:form>
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

	<!-- Custom Theme JavaScript -->
	<script src="./resources/dist/js/sb-admin-2.js"></script>

	<script type="text/javascript" src="./js/htmlInjection.js"></script>
	<script type="text/javascript" src="./js/print.js"></script>
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

												var a = document
														.createElement('a');
												a.href = data_type + ', '
														+ table_html;
												a.download = 'ICMC-ChargeReport_'
														+ Math
																.floor((Math
																		.random() * 9999999) + 1000000)
														+ '.xls';
												a.click();
											});
						});
	</script>
</body>

</html>