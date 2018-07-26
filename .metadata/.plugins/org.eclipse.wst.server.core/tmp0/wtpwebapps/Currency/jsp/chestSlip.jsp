<!DOCTYPE html>
<%@page import="java.util.Date"%>
<html lang="en">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="./resources/logo/favicon.ico" type="image/x-icon">
<link href="./resources/css/calendar.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="./resources/css/jquery.datetimepicker.css" />
<title>ICICI : Chest Slip</title>

<!-- Bootstrap Core CSS -->
<link href="./resources/bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- MetisMenu CSS -->
<link href="./resources/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

<!-- DataTables CSS -->
<link href="./resources/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css" rel="stylesheet">

<!-- DataTables Responsive CSS -->
<link href="./resources/bower_components/datatables-responsive/css/responsive.dataTables.scss" rel="stylesheet">

<!-- Custom CSS -->
<link href="./resources/dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="./resources/bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link rel="./resources/stylesheet" type="text/css" href="dist/css/style.css">

<!-- DataTable -->
<script type="text/javascript"  src="./resources/dataTable/jquery.js"></script>
<script type="text/javascript" src="./resources/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="./resources/js/dataTables.tableTools.min.js"></script>
<link rel="stylesheet" type="text/css" href="./resources/css/jquery.dataTables.min.css">
<link rel="stylesheet" type="text/css" href='./resources/css/dataTables.tableTools.min.css'>
<link rel="stylesheet" type="text/css" href='./resources/css/dataTables.tableTools.css'>
<!-- DataTable -->

<style>
.chestslipreport {overflow-x: scroll; text-align: center; width: 1009px;}
table, td { border: 1px solid black;}
 td {padding: 5px; margin: 5px;}
 td.tddatefmt {white-space: nowrap;}
}
div{width: 100%;}
.chestslipreport { /* overflow-x: auto; */  text-align: center;  font-size: 12px;  width: 1009px;}
.chestslipreport td {padding: 2px;}
</style>

<style type="text/css">
	.region-con h1 { font-size: 22px; font-weight: 200; margin-top: 20px; margin-bottom: 10px;}
	.region-con-sec { float: left; width: 100%; border-top: 1px solid #eee; border-bottom: 1px solid #eee; padding-top: 13px;}
	.region-con-drop {width: 50%;  /* text-align: right; */ float: left; padding-right: 30px; box-sizing: border-box; }
	.region-con-drop span {font-size: 18px; margin-right: 18px; }
	.region-con-drop select {width: 30%; padding: 7px; }
	.region-drop{width:100%; float:left;}
	.region-drop li{  float:left;margin-right:20px;}
	.region-drop li label {  float: left;  margin-right: 10px;  line-height: 34px;}
	.region-drop span {  float: left;}
	.DTTT.btn-group {width: 100%; float: right; margin: 10px 0;}
</style>

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
	    a.download = 'Chest-Slip_' + Math.floor((Math.random() * 9999999) + 1000000) + '.xls';
	    a.click();
	  });
	});

</script>

<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script></head>
<body oncontextmenu="return false;" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-79" data-genuitec-path="/Currency/src/main/webapp/jsp/chestSlip.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-79" data-genuitec-path="/Currency/src/main/webapp/jsp/chestSlip.jsp">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">Chest Slip</div>
					<div><input type="button" class="btn btn-default qr-button" onclick="printDiv('printableArea')" value="Print" />
					<button id="btnExport" class="btn btn-default qr-button">Export to xls</button>
					</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
						
						<%-- <c:set var="dateRangeURL" value="chestSlip" />
						<jsp:include page="reportDateRange.jsp" /> --%>
						
							<div class="region-con">
								<form:form id="userPage" name="userPage" action="chestSlip" method="post" modelAttribute="reportDate" autocomplete="off">
									<div class="region-con-sec">
										<ul class="region-drop">
											<li>
											<label>Select Date</label>
											<span>
												<form:input type="text"  path="fromDate" id="fromDate" name="fromDate" cssClass="form-control"/>
											</span>
											</li>
											
											<li>
											<span>
												<button type="submit" class="btn btn-default" value="Details" style="width: 99px;" >Search</button>
											</span>
											</li>
										</ul>						
									
									</div>
								</form:form>		
							</div>
						
							<div class="chestslipreport" id="printableArea">
							<div id="table_wrapper">
							<table width="100%"><tr><Td><b>Net Position for ICCI Bank Ltd, ________________________________ ICMC's for Currency Transfer of Chests</b></Td></tr></table>
								<table cellspacing="0">
								<tbody>
									 <tr>
										 <td colspan="7" rowspan="2" align="left">The Dy General Manager,<br> Reserve Bank of India,<br> Issue Department, 6 Pr Street, New Delhi -110001</td>
										 <td colspan="12" align="center">ICICI BANK LTD</td>
									 </tr>
									 
									 <tr>
									 	<td colspan="12" align="left">ICICI BANK LTD. ${icmcName}, ${icmcAddress}</td>
									 </tr>
									 
									 <tr>
										 <td></td>
										 <td colspan="5" align="left">CURRENCY TRANSFER OF CHESTS</td>
										 <td></td>
										 <td></td>
										 <td></td>
										 <td></td>
										 <td></td>
										 <td></td>
										 <td colspan="2">Statement No</td>
										 <td></td>
										 <td></td>
										 <td></td>
										 <td>0</td>
										 <td></td>
									 </tr>
									
									 <tr>
										 <td rowspan="2">Chest Slip No.</td>
										 <td rowspan="2">Date of Tran</td>
										 <td></td>
										 <td colspan="5" align="center">DEPOSIT</td>
										 <td align="left">Name of the chest</td>
										 <td colspan="5">Withdrawal</td>
										 <td colspan="2">RBI</td>
										 <td></td>
										 <td colspan="2">Net Amount</td>
									 </tr>
									 
									 <tr>
										 <td>Opening Balance</td>
										 <td>Notes</td>
										 <td>One Rs Note</td>
										 <td colspan="2">Coins Value</td>
										 <td>Total Value</td>
										 <td rowspan="11" valign="top" align="left">ICICI BANK LTD. ${icmcName}, ${icmcAddress}</td>
										 <td>Notes</td>
										 <td>One Rs Note</td>
										 <td colspan="2">Coins Value</td>
										 <td>Total Value</td>
										 <td>REMITTANCE / DIVERSION</td>
										 <td>FRESH REMITTANCE</td>
										 <td>Closing Balance</td>
										 <td>Deposit Rs.</td>
										 <td align="left">Withdrawn Rs.</td> 
									 </tr>
									 
									 <tr>
										 <td rowspan="10">0</td>
										 <%-- <td rowspan="10"><fmt:formatDate type="date" pattern="dd-MM-yyyy" dateStyle="short" 
										 	timeStyle="short" value="<%=new java.util.Date()%>" /></td> --%>
										 <td rowspan="10" class="tddatefmt">${currentDate}</td>
										 <td rowspan="10">${summaryListForOpeningBalance.totalValueOfBankNotes + summaryListForCoins.totalValueOfCoins}</td>
										 <td rowspan="10">${summaryListForDeposit.totalValueOfBankNotes}</td>
										 <td rowspan="10">0</td>
										 <td>Re1</td>
										 <td>0</td>
										 <td rowspan="10">${summaryListForDeposit.totalValueOfBankNotes}</td>
										 <td rowspan="10">${summaryListForWithdrawal.totalValueOfBankNotes}</td>
										 <td rowspan="10">0</td>
										 <td>Re.1</td>
										 <td>${summaryListForCoinWithdrawal.denomination1*1}</td>
										 <td rowspan="11">${summaryListForWithdrawal.totalValueOfBankNotes + summaryListForCoinWithdrawal.totalValueOfCoins}</td>
										 <td rowspan="10">${summaryListForRemittanceSoiled.totalValueOfBankNotes}</td>
										 <td rowspan="10">${summaryListForRemittance.totalValueOfBankNotes}</td>
										 <td rowspan="10">${closingBalanceList.totalValueOfBankNotes + coinClosingBalanceList.totalValueOfCoins}</td>
										 <td rowspan="10">${depositeValue}</td>
										 <td rowspan="10">${withdrawalValue}</td>
									 </tr>
									 
									 <tr>
									   <td>Rs.2</td>
									   <td>0</td>
									   <td>Rs.2</td>
									   <td>${summaryListForCoinWithdrawal.denomination2*2}</td>
									 </tr>
									 
									 <tr>
									   <td rowspan="8">Rs.5</td>
									   <td rowspan="8">0</td>
									   <td rowspan="8">Rs.5</td>
									   <td rowspan="8">${summaryListForCoinWithdrawal.denomination5*5}</td>
									 </tr>
								</tbody>
								 
								<tfoot> 
									 <tr>
										 <td></td>
										 <td></td>
										 <td></td>
										 <td></td>
										 <td></td>
										 <td>Rs.10</td>
										 <td>0</td>
										 <td></td>
										 <td colspan="2"></td>
										 <td></td>
										 <td>Rs.10</td>
										 <td>${summaryListForCoinWithdrawal.denomination10*10}</td>
										
										 <td></td>
										 <td></td>
										 <td></td>
										 <td></td>
										 <td colspan="2"></td>
									 </tr>
								</tfoot>
							  </table> </div>
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
	<script src="./resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script src="./resources/bower_components/metisMenu/dist/metisMenu.min.js"></script>

	<!-- DataTables JavaScript -->
	<script src="./resources/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="./resources/dist/js/sb-admin-2.js"></script>
	
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