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
										<!-- <label>Select Date</label> -->
										<label>Start Date</label>
										<span>
											<form:input type="text"  path="fromDate" id="fromDate" name="fromDate" cssClass="form-control"/>
										</span>
										</li>
										
										<li>
										<label>End Date</label>
										<span>
											<form:input type="text"  path="toDate" id="toDate" name="toDate" cssClass="form-control"/>
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
						
							
								<form id="showAll">
									
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
											
											<tr>
												<td>${linkBranchSolId}SL0IRREM</td>
												<td>INR${linkBranchSolId}</td>
												<td>D</td>
												<td>${excessTotal}EXCESS CASH</td>
											</tr>
											
											<c:forEach var="row" items="${discrepancyList}">
											 <c:forEach var="innerRow" items="${row.discrepancyAllocations}">
											 
											       <c:if test="${innerRow.status != 'CANCELLED'}">
												<tr>
												
													<c:choose>
														<c:when test="${row.accountTellerCam == 'ACCOUNT'}">
														
															<%-- <td><fmt:formatNumber pattern="0000" value="${row.solId}" />${row.accountNumber}</td> --%>
															
															
															<td><fmt:formatNumber pattern="0000" value="" />'${row.accountNumber}</td>
															<td>INR<fmt:formatNumber pattern="0000" value="${row.accountNumber.substring(0,4)}" /></td>
															
															<c:if test="${innerRow.discrepancyType == 'EXCESS'}">
															<td>C</td>
															</c:if>
															
															<c:if test="${innerRow.discrepancyType != 'EXCESS'}">
															<td>D</td>
															</c:if>
															
															
															
															 <c:choose>
																<c:when test="${innerRow.discrepancyType == 'FAKE'}">
																	<td>${innerRow.value}.00${innerRow.accountNumber}/Counterfeit</td></c:when>                          <%-- <fmt:formatDate pattern="dd.MM.yyyy" value="${row.discrepancyDate}" /> --%>
															 </c:choose>
															 <c:choose>
																<c:when test="${innerRow.discrepancyType == 'MUTILATED'}">
																    
																    <c:choose>
																      <c:when test="${innerRow.mutilType=='HALF VALUE'}">
																	<td>${innerRow.value}.00${innerRow.accountNumber}/Mutilated</td>
																	</c:when>
																	</c:choose>
																	
																	<c:choose>
																      <c:when test="${innerRow.mutilType=='ZERO VALUE'}">
																	<td>${innerRow.denomination * innerRow.numberOfNotes}.00${innerRow.accountNumber}/Mutilated</td>
																	</c:when>
																	</c:choose>
																	
																	
																	</c:when>
															 </c:choose>
															 <c:choose>
																<c:when test="${innerRow.discrepancyType == 'SHORTAGE'}">
																	<td>${innerRow.value}.00${innerRow.accountNumber}/Shortage</td></c:when>
															  </c:choose>
															  <c:choose>
																<c:when test="${innerRow.discrepancyType == 'EXCESS'}">
																	<td>${innerRow.value}.00${innerRow.accountNumber}/Excess</td></c:when>
															  </c:choose>
															
															
														</c:when>
													</c:choose>
												
												
												
													
													<!-- If Teller is selected -->
													
													<c:choose>
														<c:when test="${row.accountTellerCam == 'TELLER' && (innerRow.denomination==2000 || innerRow.denomination==500 || innerRow.denomination==200 ||  innerRow.denomination==100)}">
														  
														     <c:choose>
														     <c:when test="${innerRow.discrepancyType=='EXCESS'}">
															<td><fmt:formatNumber pattern="0000" value="${row.solId}" />SLDISCUR</td>
															<td>INR<fmt:formatNumber pattern="0000" value="${row.solId}" /></td>
															<td>C</td>
															</c:when>
															</c:choose>
															
															<c:choose>
															<c:when test="${innerRow.discrepancyType=='MUTILATED'}">
															
															<c:if test="${innerRow.mutilType=='HALF VALUE'}">
															<td><fmt:formatNumber pattern="0000" value="${row.solId}" />SADISCUR</td>
															<td>INR<fmt:formatNumber pattern="0000" value="${row.solId}" /></td>
															<td>D</td>
															</c:if>
															
															
															<c:if test="${innerRow.mutilType=='ZERO VALUE'}">
															<td><fmt:formatNumber pattern="0000" value="${row.solId}" />SADISCUR</td>
															<td>INR<fmt:formatNumber pattern="0000" value="${row.solId}" /></td>
															<td>D</td>
															</c:if>
					
															</c:when>
															</c:choose>
														
														
															
															<c:choose>
															<c:when test="${innerRow.discrepancyType=='SHORTAGE'}">
															<td><fmt:formatNumber pattern="0000" value="${row.solId}" />SADISCUR</td>
															<td>INR<fmt:formatNumber pattern="0000" value="${row.solId}" /></td>
															<td>D</td>
															</c:when>
															</c:choose>
															
															<c:choose>
															<c:when test="${innerRow.discrepancyType=='FAKE'}">
															<td><fmt:formatNumber pattern="0000" value="${row.solId}" />SADISCUR</td>
															<td>INR<fmt:formatNumber pattern="0000" value="${row.solId}" /></td>
															<td>D</td>
															</c:when>
															</c:choose>
															
															
															 <c:choose>
																<c:when test="${innerRow.discrepancyType == 'MUTILATED'}">
																
																   
																	  <c:if test="${innerRow.mutilType=='HALF VALUE'}">                                                      
																	<td>${innerRow.value}.00${innerRow.customerName}/Mutilated</td>
																	</c:if>
																	
																	 <c:if test="${innerRow.mutilType=='ZERO VALUE'}">                                                      
																	<td>${innerRow.denomination * innerRow.numberOfNotes}.00${innerRow.customerName}/Mutilated</td>
																	</c:if>
																	
																	</c:when>
																	                    
															 </c:choose>
															 
															 <c:choose>
																<c:when test="${innerRow.discrepancyType == 'FAKE'}">
																<td>	${innerRow.value}.00${innerRow.customerName}/Counterfeit</td></c:when>
															 </c:choose>
															 
															 <c:choose>
																<c:when test="${innerRow.discrepancyType == 'SHORTAGE'}">
																	<td>${innerRow.value}.00${innerRow.customerName}/Shortage</td></c:when>
															  </c:choose>
															  
															  <c:choose>
																<c:when test="${innerRow.discrepancyType == 'EXCESS'}">
																	<td>${innerRow.value}.00${innerRow.customerName}/Excess</td></c:when>
															  </c:choose>
															  
															
														
														</c:when>
													</c:choose>
													
													<!-- code for the cam case and no other condition for denomination -->
													
													<c:choose>
														<c:when test="${row.accountTellerCam == 'CAM'}">
														  
														     <c:choose>
														     <c:when test="${innerRow.discrepancyType=='EXCESS'}">
															<td><fmt:formatNumber pattern="0000" value="${row.solId}" />SLDISCUR</td>
															<td>INR<fmt:formatNumber pattern="0000" value="${row.solId}" /></td>
															<td>C</td>
															</c:when>
															</c:choose>
															
															<c:choose>
															<c:when test="${innerRow.discrepancyType=='MUTILATED'}">
															
															<c:if test="${innerRow.mutilType=='HALF VALUE'}">
															<td><fmt:formatNumber pattern="0000" value="${row.solId}" />SADISCUR</td>
															<td>INR<fmt:formatNumber pattern="0000" value="${row.solId}" /></td>
															<td>D</td>
															</c:if>
															
															
															<c:if test="${innerRow.mutilType=='ZERO VALUE'}">
															<td><fmt:formatNumber pattern="0000" value="${row.solId}" />SADISCUR</td>
															<td>INR<fmt:formatNumber pattern="0000" value="${row.solId}" /></td>
															<td>D</td>
															</c:if>
					
															</c:when>
															</c:choose>
														
														
															
															<c:choose>
															<c:when test="${innerRow.discrepancyType=='SHORTAGE'}">
															<td><fmt:formatNumber pattern="0000" value="${row.solId}" />SADISCUR</td>
															<td>INR<fmt:formatNumber pattern="0000" value="${row.solId}" /></td>
															<td>D</td>
															</c:when>
															</c:choose>
															
															<c:choose>
															<c:when test="${innerRow.discrepancyType=='FAKE'}">
															<td><fmt:formatNumber pattern="0000" value="${row.solId}" />SADISCUR</td>
															<td>INR<fmt:formatNumber pattern="0000" value="${row.solId}" /></td>
															<td>D</td>
															</c:when>
															</c:choose>
															
															
															 <c:choose>
																<c:when test="${innerRow.discrepancyType == 'MUTILATED'}">
																
																   
																	  <c:if test="${innerRow.mutilType=='HALF VALUE'}">                                                      
																	<td>${innerRow.value}${innerRow.customerName}/Mutilated</td>
																	</c:if>
																	
																	 <c:if test="${innerRow.mutilType=='ZERO VALUE'}">                                                      
																	<td>${innerRow.denomination * innerRow.numberOfNotes}.00${innerRow.customerName}/Mutilated</td>
																	</c:if>
																	
																	</c:when>
																	                    
															 </c:choose>
															 
															 <c:choose>
																<c:when test="${innerRow.discrepancyType == 'FAKE'}">
																<td>	${innerRow.value}.00${innerRow.customerName}/Counterfeit</td></c:when>
															 </c:choose>
															 
															 <c:choose>
																<c:when test="${innerRow.discrepancyType == 'SHORTAGE'}">
																	<td>${innerRow.value}.00${innerRow.customerName}/Shortage</td></c:when>
															  </c:choose>
															  
															  <c:choose>
																<c:when test="${innerRow.discrepancyType == 'EXCESS'}">
																	<td>${innerRow.value}.00${innerRow.customerName}/Excess</td></c:when>
															  </c:choose>
															  
															
														
														</c:when>
													</c:choose>
					
													
													<!-- end code for the cam case and there is no condition for the denomination -->												
													
													<!-- code for teller less then 100 denomination -->
													
													<c:choose>
														<c:when test="${row.accountTellerCam == 'TELLER' && (innerRow.denomination==50 || innerRow.denomination==20 || innerRow.denomination==10 ||  innerRow.denomination==5
														|| innerRow.denomination==2 || innerRow.denomination==1)}">
														  
														     <c:choose>
														     <c:when test="${innerRow.discrepancyType=='EXCESS'}">
															<td><fmt:formatNumber pattern="0000" value="${row.solId}" />SLDISCUR</td>
															<td>INR<fmt:formatNumber pattern="0000" value="${row.solId}" /></td>
															<td>C</td>
															</c:when>
															</c:choose>
															
															<c:choose>
															<c:when test="${innerRow.discrepancyType=='MUTILATED'}">
															
															<c:if test="${innerRow.mutilType=='HALF VALUE'}">
															<td><fmt:formatNumber pattern="0000" value="${row.solId}" />SAMUTCUR</td>
															<td>INR<fmt:formatNumber pattern="0000" value="${row.solId}" /></td>
															<td>D</td>
															</c:if>
															
															
															<c:if test="${innerRow.mutilType=='ZERO VALUE'}">
															<td><fmt:formatNumber pattern="0000" value="${row.solId}" />SADCASH</td>
															<td>INR<fmt:formatNumber pattern="0000" value="${row.solId}" /></td>
															<td>D</td>
															</c:if>
					
															</c:when>
															</c:choose>
														
														
															
															<c:choose>
															<c:when test="${innerRow.discrepancyType=='SHORTAGE'}">
															<td><fmt:formatNumber pattern="0000" value="${row.solId}" />SADISCUR</td>
															<td>INR<fmt:formatNumber pattern="0000" value="${row.solId}" /></td>
															<td>D</td>
															</c:when>
															</c:choose>
															
															<c:choose>
															<c:when test="${innerRow.discrepancyType=='FAKE'}">
															<td><fmt:formatNumber pattern="0000" value="${row.solId}" />SADCASH</td>
															<td>INR<fmt:formatNumber pattern="0000" value="${row.solId}" /></td>
															<td>D</td>
															</c:when>
															</c:choose>
															
															
															 <c:choose>
																<c:when test="${innerRow.discrepancyType == 'MUTILATED'}">
																
																   
																	  <c:if test="${innerRow.mutilType=='HALF VALUE'}">                                                      
																	<td>${innerRow.value}.00${innerRow.customerName}/Mutilated</td>
																	</c:if>
																	
																	 <c:if test="${innerRow.mutilType=='ZERO VALUE'}">                                                      
																	<td>${innerRow.denomination * innerRow.numberOfNotes}.00${innerRow.customerName}/Mutilated</td>
																	</c:if>
																	
																	</c:when>
																	                    
															 </c:choose>
															 
															 <c:choose>
																<c:when test="${innerRow.discrepancyType == 'FAKE'}">
																<td>	${innerRow.value}.00${innerRow.customerName}/Counterfeit</td></c:when>
															 </c:choose>
															 
															 <c:choose>
																<c:when test="${innerRow.discrepancyType == 'SHORTAGE'}">
																	<td>${innerRow.value}.00${innerRow.customerName}/Shortage</td></c:when>
															  </c:choose>
															  
															  <c:choose>
																<c:when test="${innerRow.discrepancyType == 'EXCESS'}">
																	<td>${innerRow.value}.00${innerRow.customerName}/Excess</td></c:when>
															  </c:choose>
															  
															
														
														</c:when>
													</c:choose>
													
													<!-- end code for teller less then 100 denomination -->
													
											</tr>
											</c:if>
												       	 </c:forEach>
										                       	</c:forEach>
											    		
												<!-- code by shahabuddin -->
												
												<c:forEach var="row" items="${discrepancyList}">
											 <c:forEach var="innerRow" items="${row.discrepancyAllocations}">
											 
											<c:if test="${innerRow.status != 'CANCELLED'}">
												
												<c:choose>
														<c:when test="${row.accountTellerCam == 'TELLER' && (innerRow.denomination==2000 || innerRow.denomination==500 || innerRow.denomination==200 ||  innerRow.denomination==100)}">
														   
														   <c:if test="${innerRow.discrepancyType=='MUTILATED'}">
															<c:if test="${innerRow.mutilType=='HALF VALUE'}">
															<tr>
															<td><fmt:formatNumber pattern="0000" value="0071" />SAMUTCUR</td>
															<td>INR<fmt:formatNumber pattern="0000" value="${row.solId}" /></td>
															
															<td>D</td>
															     <td>${innerRow.value}.00${innerRow.customerName}/Counterfeit</td>
															     
															     </tr>
														         </c:if>
														</c:if>
														
														</c:when>
												</c:choose>	
												
												<!-- code for teller case in which denomination si less then 100 -->
												<c:choose>
														<c:when test="${row.accountTellerCam == 'TELLER' && (innerRow.denomination==50 || innerRow.denomination==20 || innerRow.denomination==10 ||  innerRow.denomination==5
														|| innerRow.denomination==2 || innerRow.denomination==1)}">
														  
														   <c:if test="${innerRow.discrepancyType=='MUTILATED'}">
															<c:if test="${innerRow.mutilType=='HALF VALUE'}">
															<tr>
															<td><fmt:formatNumber pattern="0000" value="0071" />SADCASH</td>
															<td>INR<fmt:formatNumber pattern="0000" value="${row.solId}" /></td>
															<td>D</td>
														
															     <td>${innerRow.value}.00${innerRow.customerName}/Counterfeit</td>
															     </tr>
														         </c:if>
														</c:if>
														
														</c:when>
												</c:choose>	
												<!-- end code for teller case in which denomination si less then 100 -->
												
												
												<!-- code for the cam case and there is no condition for denomination -->
												
												<c:choose>
														<c:when test="${row.accountTellerCam == 'CAM'}">
														  
														   <c:if test="${innerRow.discrepancyType=='MUTILATED'}">
															<c:if test="${innerRow.mutilType=='HALF VALUE'}">
															<tr>
															<td><fmt:formatNumber pattern="0000" value="0071" />SAMUTCUR</td>
															<td>INR<fmt:formatNumber pattern="0000" value="${row.solId}" /></td>
															<td>D</td>
														
															     <td>${innerRow.value}.00${innerRow.customerName}/Counterfeit</td>
															     </tr>
														         </c:if>
														</c:if>
														
														</c:when>
												</c:choose>
												
												<!-- end code for the cam case and here is no condition for the denomination -->
												
												
												
												
												
												<!-- Today code  -->
												
												<c:choose>
														<c:when test="${row.accountTellerCam == 'ACCOUNT'}">
														
														   <c:if test="${innerRow.discrepancyType=='MUTILATED'}">
															<c:if test="${innerRow.mutilType=='HALF VALUE'}">
															<tr>
															<td><fmt:formatNumber pattern="0000" value="0071" />SAMUTCUR</td>
															<td>INR<fmt:formatNumber pattern="0000" value="${row.solId}" /></td>
															 <td>D</td>
																									
															<td>${innerRow.value}.00${innerRow.accountNumber}/Counterfeit</td>
															</tr>
															</c:if>
														</c:if>
														
														</c:when>
												</c:choose>	
												
												<!-- Today end -->
												
												
												<!-- end by shahabuddin -->
												 </c:if>
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
	<!--   this code is comment by shahabuddin and this code is writtern for xls file upload for pagination
	 <script>
	 $(document).ready(function () {
	    	var table=$('#tableValue').dataTable({
	    		 "pagingType": "full_numbers",
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
	</script> -->
	
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
	    a.download = 'exported_table_' + Math.floor((Math.random() * 9999999) + 1000000) + '.xls';
	    a.click();
	  });
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