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
<title>Suspense Cash Details</title>

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
						<div class="panel-heading">Suspense Cash Details</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<h5 style="font: bold;">From ICMC</h5>
							<%-- <form id="showAll"> --%>

							<div id="printableArea">
								<div id="table_wrapper">

									<form action="suspenseCashDetailFromIcmc" method="post">

										<c:forEach var="row" items="${openingBalance}">
											<input type="text" name="id" value="${row.id}"
												hidden="hidden">
											<table class="table table-striped table-bordered table-hover"
												id="tableValue">


												<thead>
													<tr>

														<th>Denomination</th>
														<th>Opening Balance</th>
														<th>Withdrawal</th>
														<th>Deposit</th>
														<th>Closing</th>
													</tr>
												</thead>

												<tbody>
													<%--  <c:forEach var="row" items="${openingBalance}"> --%>

													<tr>

														<td>2000</td>
														<td id="deno2000">${row.denomination2000}</td>
														<td><input type="number" name="withdrawal_2000"
															min="0" value="${row.withdrawal_2000}" id="with2000"></td>
														<td><input type="number" name="deposit_2000" min="0"
															value="${row.deposit_2000}" id="dep2000"></td>
														<td>${row.denomination2000 + row.deposit_2000 - row.withdrawal_2000}</td>
													</tr>
													<tr>

														<td>500</td>
														<td id="deno500">${row.denomination500}</td>
														<td><input type="number" name="withdrawal_500"
															id="with500" min="0" value="${row.withdrawal_500}"></td>
														<td><input type="number" name="deposit_500"
															id="dep500" min="0" value="${row.deposit_500}"></td>
														<td>${row.denomination500 + row.deposit_500 - row.withdrawal_500}</td>
													</tr>
													<tr>

														<td>200</td>
														<td id="deno200">${row.denomination200}</td>
														<td><input type="number" name="withdrawal_200"
															id="with200" min="0" value="${row.withdrawal_200}"></td>
														<td><input type="number" name="deposit_200"
															id="dep200" min="0" value="${row.deposit_200}"></td>
														<td>${row.denomination200 + row.deposit_200 - row.withdrawal_200}</td>
													</tr>
													<tr>

														<td>100</td>
														<td id="deno100">${row.denomination100}</td>
														<td><input type="number" name="withdrawal_100"
															id="with100" min="0" value="${row.withdrawal_100}"></td>
														<td><input type="number" name="deposit_100"
															id="dep100" min="0" value="${row.deposit_100}"></td>
														<td>${row.denomination100 + row.deposit_100 - row.withdrawal_100}</td>
													</tr>
													<tr>
														<td>50</td>
														<td id="deno50">${row.denomination50}</td>
														<td><input type="number" name="withdrawal_50"
															id="with50" min="0" value="${row.withdrawal_50}"></td>
														<td><input type="number" name="deposit_50" id="dep50"
															min="0" value="${row.deposit_50}"></td>
														<td>${row.denomination50 + row.deposit_50 - row.withdrawal_50}</td>
													</tr>
													<tr>

														<td>20</td>
														<td id="deno20">${row.denomination20}</td>
														<td><input type="number" name="withdrawal_20"
															id="with20" min="0" value="${row.withdrawal_20}"></td>
														<td><input type="number" name="deposit_20" id="dep20"
															min="0" value="${row.deposit_20}"></td>
														<td>${row.denomination20 + row.deposit_20 - row.withdrawal_20}</td>
													</tr>
													<tr>

														<td>10</td>
														<td id="deno10">${row.denomination10}</td>
														<td><input type="number" name="withdrawal_10"
															id="with10" min="0" value="${row.withdrawal_10}"></td>
														<td><input type="number" name="deposit_10" min="0"
															id="dep10" value="${row.deposit_10}"></td>
														<td>${row.denomination10 + row.deposit_10 - row.withdrawal_10}</td>
													</tr>
													<%-- </c:forEach> --%>
												</tbody>
											</table>
										</c:forEach>
										<input type="submit" value="submit" class="btn btn-default"
											style="width: 99px;" id="submit1">
									</form>
								</div>
							</div>
							<%-- </form> --%>
						</div>
						<!-- /.panel-body -->

						<div class="panel-body">
							<h5 style="font: bold;">From Link Branch</h5>
							<%-- <form id="showAll"> --%>

							<div id="printableArea">
								<div id="table_wrapper">



									<form action="suspenseCashFromLink" method="post">
										<c:forEach var="row" items="${openingBalance}">

											<input type="text" name="id" value="${row.id}"
												hidden="hidden">
									 Sr. Number : <input type="text" name="srNumber"
												value="${row.srNumber}">
											<br>
											<br>
											<table class="table table-striped table-bordered table-hover"
												id="tableValue">
												<thead>
													<tr>

														<th>Denomination</th>
														<th>Opening Balance</th>
														<th>Replenishment</th>
														<th>Depletion</th>
														<th>Closing</th>
													</tr>
												</thead>

												<tbody>

													<tr>

														<td>2000</td>
														<td>${row.denomination2000 + row.deposit_2000 - row.withdrawal_2000}</td>
														<td><input type="number" name="replenishment_2000"
															min="0" value="${row.replenishment_2000}"></td>
														<td><input type="number" name="depletion_2000"
															min="0" value="${row.depletion_2000}"></td>
														<td>${row.denomination2000 + row.deposit_2000 - row.withdrawal_2000 - row.depletion_2000 + row.replenishment_2000}</td>
													</tr>
													<tr>

														<td>500</td>
														<td>${row.denomination500 + row.deposit_500 - row.withdrawal_500}</td>
														<td><input type="number" name="replenishment_500"
															min="0" value="${row.replenishment_500}"></td>
														<td><input type="number" name="depletion_500" min="0"
															value="${row.depletion_500}"></td>
														<td>${row.denomination500 + row.deposit_500 - row.withdrawal_500 - row.depletion_500 + row.replenishment_500}</td>

													</tr>
													<tr>

														<td>200</td>
														<td>${row.denomination200 + row.deposit_200 - row.withdrawal_200}</td>
														<td><input type="number" name="replenishment_200"
															min="0" value="${row.replenishment_200}"></td>
														<td><input type="number" name="depletion_200" min="0"
															value="${row.depletion_200}"></td>
														<td>${row.denomination200 + row.deposit_200 - row.withdrawal_200 - row.depletion_200 + row.replenishment_200}</td>
													</tr>
													<tr>

														<td>100</td>
														<td>${row.denomination100 + row.deposit_100 - row.withdrawal_100}</td>
														<td><input type="number" name="replenishment_100"
															min="0" value="${row.replenishment_100}"></td>
														<td><input type="number" name="depletion_100" min="0"
															value="${row.depletion_100}"></td>
														<td>${row.denomination100 + row.deposit_100 - row.withdrawal_100 - row.depletion_100 + row.replenishment_100}</td>

													</tr>
													<tr>

														<td>50</td>
														<td>${row.denomination50 + row.deposit_50 - row.withdrawal_50}</td>
														<td><input type="number" name="replenishment_50"
															min="0" value="${row.replenishment_50}"></td>
														<td><input type="number" name="depletion_50" min="0"
															value="${row.depletion_50}"></td>
														<td>${row.denomination50 + row.deposit_50 - row.withdrawal_50 - row.depletion_50 + row.replenishment_50}</td>

													</tr>
													<tr>

														<td>20</td>
														<td>${row.denomination20 + row.deposit_20 - row.withdrawal_20}</td>
														<td><input type="number" name="replenishment_20"
															min="0" value="${row.replenishment_20}"></td>
														<td><input type="number" name="depletion_20" min="0"
															value="${row.depletion_20}"></td>
														<td>${row.denomination20 + row.deposit_20 - row.withdrawal_20 - row.depletion_20 + row.replenishment_20}</td>

													</tr>
													<tr>

														<td>10</td>
														<td>${row.denomination10 + row.deposit_10 - row.withdrawal_10}</td>
														<td><input type="number" name="replenishment_10"
															min="0" value="${row.replenishment_10}"></td>
														<td><input type="number" name="depletion_10" min="0"
															value="${row.depletion_10}"></td>
														<td>${row.denomination10 + row.deposit_10 - row.withdrawal_10 - row.depletion_10 + row.replenishment_10}</td>
													</tr>

												</tbody>

											</table>
											<h5 align="right">Balance : ${row.denomination10*10 + row.deposit_10*10 - row.withdrawal_10*10 - row.depletion_10*10 + row.replenishment_10*10
									        + row.denomination20*20 + row.deposit_20*20 - row.withdrawal_20*20 - row.depletion_20*20 + row.replenishment_20*20
									        + row.denomination50*50 + row.deposit_50*50 - row.withdrawal_50*50 - row.depletion_50*50 + row.replenishment_50*50
									        + row.denomination100*100 + row.deposit_100*100 - row.withdrawal_100*100 - row.depletion_100*100 + row.replenishment_100*100
									        + row.denomination200*200 + row.deposit_200*200 - row.withdrawal_200*200 - row.depletion_200*200 + row.replenishment_200*200
									        + row.denomination500*500 + row.deposit_500*500 - row.withdrawal_500*500 - row.depletion_500*500 + row.replenishment_500*500
									        + row.denomination2000*2000 + row.deposit_2000*2000 - row.withdrawal_2000*2000 - row.depletion_2000*2000 + row.replenishment_2000*2000}</h5>
										</c:forEach>
										<input type="submit" value="submit" class="btn btn-default"
											style="width: 99px;">
									</form>
									<!-- <h5 align="right"> Balance : 000</h5> -->


								</div>
							</div>

							<%-- </form> --%>

						</div>

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

	<script>
						
						$(document).ready(function()
								{
							        $("#submit1").click(function()
							        		{
							        	      var with2000 = parseFloat($("#with2000").val());
							        	      var dep2000 = parseFloat($("#dep2000").val());
							        	      var deno2000 = parseFloat($("#tableValue #deno2000").text());
							        	      
							        	      
							        	      var with500 = parseFloat($("#with500").val());
							        	      var dep500 = parseFloat($("#dep500").val());
							        	      var deno500 = parseFloat($("#tableValue #deno500").text());
							        	      
							        	      var with200 = parseFloat($("#with200").val());
							        	      var dep200 = parseFloat($("#dep200").val());
							        	      var deno200 = parseFloat($("#tableValue #deno200").text());
							        	      
							        	      var with100 = parseFloat($("#with100").val());
							        	      var dep100 = parseFloat($("#dep100").val());
							        	      var deno100 = parseFloat($("#tableValue #deno100").text());
							        	      
							        	      var with50 = parseFloat($("#with50").val());
							        	      var dep50 = parseFloat($("#dep50").val());
							        	      var deno50 = parseFloat($("#tableValue #deno50").text());
							        	      
							        	      var with20 = parseFloat($("#with20").val());
							        	      var dep20 = parseFloat($("#dep20").val());
							        	      var deno20 = parseFloat($("#tableValue #deno20").text());
							        	      
							        	      var with10 = parseFloat($("#with10").val());
							        	      var dep10 = parseFloat($("#dep10").val());
							        	      var deno10 = parseFloat($("#tableValue #deno10").text());
							        	     
							        	        if(with2000 > (deno2000 + dep2000))
							        	        	{
							        	        	alert(" withdrawal value can't greater then the sum of Opening balance and deposit");
							        	        	 return false;
							        	        	}
							        	         if(with500 > (deno500 + dep500))
							        	        	 {
							        	        	 alert(" withdrawal value can't greater then the sum of Opening balance and deposit");
							        	        	 return false;
							        	        	 }
							        	        if(with200 > (deno200 + dep200))
							        	        	{
							        	        	alert(" withdrawal value can't greater then the sum of Opening balance and deposit");
							        	        	 return false;
							        	        	}
							        	        if(with100 > (deno100 + dep100))
							        	        	{
							        	        	alert(" withdrawal value can't greater then the sum of Opening balance and deposit");
							        	        	 return false;
							        	        	}
							        	        if(with50 > (deno50 + dep50))
							        	        	{
							        	        	alert(" withdrawal value can't greater then the sum of Opening balance and deposit");
							        	        	 return false;
							        	        	}
							        	        if(with20 > (deno20 + dep20))
							        	        	{
							        	        	alert(" withdrawal value can't greater then the sum of Opening balance and deposit");
							        	        	 return false;
							        	        	}
							        	         if(with10 > (deno10 + dep10))
							        	        	 {
							        	        	 alert(" withdrawal value can't greater then the sum of Opening balance and deposit");
							        	        	 return false;
							        	        	 }
							        	        	 
							        	        	
							        		})
								})
						
						</script>



</body>



</html>