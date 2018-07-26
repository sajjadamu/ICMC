<!DOCTYPE html>
<%@page import="com.chest.currency.entity.model.Discrepancy"%>
<%@page import="java.util.Map"%>
<html lang="en">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="./resources/logo/favicon.ico" type="image/x-icon">
<link href="./resources/css/calendar.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="./resources/css/jquery.datetimepicker.css" />
<title>ICore Format</title>

<style type="text/css">
	.region-con-sec { float: left; width: 100%; border-top: 1px solid #eee; border-bottom: 1px solid #eee; padding-top: 13px;}
	.region-drop{width:100%; float:left;}
	.region-drop li{  float:left;margin-right:20px;}
	.region-drop li label {  float: left;  margin-right: 10px;  line-height: 34px;}
	.region-drop span {  float: left;}
</style>

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
<link rel="./resources/stylesheet" type="text/css" href="./resources/dist/css/style.css">

<!-- DataTable -->
<script type="text/javascript"  src="./resources/dataTable/jquery.js"></script>
<script type="text/javascript" src="./resources/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="./resources/js/dataTables.tableTools.min.js"></script>
<script type="text/javascript" src="./resources/js/sum().js"></script>
<link rel="stylesheet" type="text/css" href="./resources/css/jquery.dataTables.min.css">
<link rel="stylesheet" type="text/css" href='./resources/css/dataTables.tableTools.min.css'>
<link rel="stylesheet" type="text/css" href='./resources/css/dataTables.tableTools.css'>
<!-- DataTable -->
<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script></head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-150" data-genuitec-path="/Currency/src/main/webapp/jsp/iCoreFormat.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-150" data-genuitec-path="/Currency/src/main/webapp/jsp/iCoreFormat.jsp">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">ICORE Uploadable Format</div>
						<!-- /.panel-heading -->
						<div><input type="button" class="btn btn-default qr-button" onclick="printDiv('printableArea')" value="Print" />
						<button id="btnExport" class="btn btn-default qr-button">Export to xls</button></div>
						
						<div class="panel-body">
						
						<div class="region-con">
							<form:form id="userPage" name="userPage" action="iCoreFormat" method="post" modelAttribute="reportDate" autocomplete="off">
								<div class="region-con-sec">
									<ul class="region-drop">
										<li>
										<label>Select Date</label>
										<span>
											<form:input type="text"  path="fromDate" id="fromDate" name="fromDate" cssClass="form-control"/>
										</span>
										</li>
										
										<li>
										<label></label>
										<span>
											<button type="submit" class="btn btn-default" value="Details" style="width: 99px;" >Search</button>
										</span>
										</li>
									</ul>						
								
								</div>
							</form:form>		
						</div>
						
							<!-- <div class="dataTable_wrapper"> -->
								<form id="showAll">
									<!-- <table class="table table-striped table-bordered table-hover" id="dataTables-example"> -->
									<div id="printableArea">
									<div id="table_wrapper">
									<table class="table table-striped table-bordered table-hover" id="tableValue">
										<thead>
											<tr>
												<th>Sol-ID with A/C No</th>
												<th>INR Sol-ID</th>
												<th>Debit/Credit</th>
												<th>Total Amount with Discription</th>
											</tr>
										</thead>
										
										<tbody>
											<tr>
												<td>${linkBranchSolId}SL0IRREM</td>
												<td>INR${linkBranchSolId}</td>
												<td>D</td>
												<td>${excessTotal}EXCESS CASH</td>
											</tr>
											<tr>
												<td>${linkBranchSolId}SA0IRREM</td>
												<td>INR${linkBranchSolId}</td>
												<td>C</td>
												<td>${sairremTotal+samutcurTotal}SHORT/DISC CASH</td>
											</tr>
											<tr>
												<td>${linkBranchSolId}SADSCASH</td>
												<td>INR${linkBranchSolId}</td>
												<td>C</td>
												<td>${sadscashTotal}Counterfeit/Mutilated</td>
											</tr>
											
											<c:forEach var="row" items="${discrepancyList}">
											 <c:forEach var="innerRow" items="${row.discrepancyAllocations}">
												<tr>
												<!-- If Account is selected -->
													<c:choose>
														<c:when test="${row.accountTellerCam == 'ACCOUNT'}">
															<td><fmt:formatNumber pattern="0000" value="${row.solId}" />${row.accountNumber}</td>
															<td>INR<fmt:formatNumber pattern="0000" value="${row.solId}" /></td>
															<td>D</td>
															<td>
															 <c:choose>
																<c:when test="${innerRow.discrepancyType == 'FAKE'}">
																	${innerRow.value} Counterfeit/<fmt:formatDate pattern="yyyy-MM-dd" value="${row.discrepancyDate}" /></c:when>                          <%-- <fmt:formatDate pattern="dd.MM.yyyy" value="${row.discrepancyDate}" /> --%>
															 </c:choose>
															 <c:choose>
																<c:when test="${innerRow.discrepancyType == 'MUTILATED'}">
																	${innerRow.value} Mutilated/<fmt:formatDate pattern="yyyy-MM-dd" value="${row.discrepancyDate}" /></c:when>
															 </c:choose>
															 <c:choose>
																<c:when test="${innerRow.discrepancyType == 'SHORTAGE'}">
																	${innerRow.value} Shortage/<fmt:formatDate pattern="yyyy-MM-dd" value="${row.discrepancyDate}" /></c:when>
															  </c:choose>
															  <c:choose>
																<c:when test="${innerRow.discrepancyType == 'EXCESS'}">
																	${innerRow.value} Excess/<fmt:formatDate pattern="yyyy-MM-dd" value="${row.discrepancyDate}" /></c:when>
															  </c:choose>
															</td>
														</c:when>
													</c:choose>
													
													<!-- If Teller is selected -->
													<c:choose>
														<c:when test="${row.accountTellerCam == 'TELLER'}">
															<td><fmt:formatNumber pattern="0000" value="${row.solId}" />SAMUTCUR</td>
															<td>INR<fmt:formatNumber pattern="0000" value="${row.solId}" /></td>
															<td>D</td>
															<td>
															 <c:choose>
																<c:when test="${innerRow.discrepancyType == 'MUTILATED'}">
																	${innerRow.value} Counterfeit/<fmt:formatDate pattern="yyyy-MM-dd" value="${row.discrepancyDate}" /></c:when>                          <%-- <fmt:formatDate pattern="dd.MM.yyyy" value="${row.discrepancyDate}" /> --%>
															 </c:choose>
															 <c:choose>
																<c:when test="${innerRow.discrepancyType == 'FAKE'}">
																	${innerRow.value} Mutilated/<fmt:formatDate pattern="yyyy-MM-dd" value="${row.discrepancyDate}" /></c:when>
															 </c:choose>
															 <c:choose>
																<c:when test="${innerRow.discrepancyType == 'SHORTAGE'}">
																	${innerRow.value} Shortage/<fmt:formatDate pattern="yyyy-MM-dd" value="${row.discrepancyDate}" /></c:when>
															  </c:choose>
															  <c:choose>
																<c:when test="${innerRow.discrepancyType == 'EXCESS'}">
																	${innerRow.value} Excess/<fmt:formatDate pattern="yyyy-MM-dd" value="${row.discrepancyDate}" /></c:when>
															  </c:choose>
															</td>
														</c:when>
													</c:choose>
												</tr>
											 </c:forEach>
											</c:forEach>
										</tbody>
									</table></div></div>
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
	<script src="./resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script src="./resources/bower_components/metisMenu/dist/metisMenu.min.js"></script>
	<script src="./resources/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="./resources/dist/js/sb-admin-2.js"></script>
	
	<!-- Page-Level Demo Scripts - Tables - Use for reference -->
	<script>
	 $(document).ready(function () {
	    	var table=$('#tableValue').dataTable({
	    		 "pagingType": "full_numbers",
	    		 "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
	    		 drawCallback: function () {
		    	        var api = this.api();
		    	        /* $( api.table().footer() ).html('<th colspan="5" style="text-align:right">Current Page Total:'+
		    	          api.column( 4, {page:'current'}).data().sum().toLocaleString('en-IN')+'; All Pages Total: '+
	    	        	  api.column( 4 ).data().sum().toLocaleString('en-IN')+'</th>'
		    	        ); */
		    	      }
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
	</script>
	
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
	    a.download = 'ICore-Formate-List' + Math.floor((Math.random() * 9999999) + 1000000) + '.xls';
	    a.click();
	  });
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