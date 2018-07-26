<!DOCTYPE html>
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
	<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.datetimepicker.css" />
<title>ICICI : Training Report</title>

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

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<link
	href="./resources/bower_components/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- MetisMenu CSS -->
<link
	href="./resources/bower_components/metisMenu/dist/metisMenu.min.css"
	rel="stylesheet">

<!-- Custom CSS -->
<link href="./resources/dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Custom Fonts -->
<link
	href="./resources/bower_components/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">

<link rel="stylesheet" type="text/css"
	href="./resources/dist/css/style.css">

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
	    $('.dataTables_info').remove();
	    $('.pagination').remove();
	    var table_html = table_div.outerHTML.replace(/ /g, '%20');

	    var a = document.createElement('a');
	    a.href = data_type + ', ' + table_html;
	    a.download = 'Training-Reports' + Math.floor((Math.random() * 9999999) + 1000000) + '.xls';
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
						<div class="panel-heading">Training Reports</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<div>
									<input type="button" class="btn btn-default qr-button"
										onclick="printDiv('printableArea')" value="Print" />
										<button id="btnExport" class="btn btn-default qr-button">Export to xls</button>
								</div>
								<!-- 							<div><input type="button" id="btnExport" value=" Export Table data into Excel " />
 -->
 
 <div class="region-con">
								<form:form id="userPage" name="userPage"
									action="trainingRegisterReport" method="post"
									modelAttribute="reportDate" autocomplete="off">
									<div class="region-con-sec">
										<ul class="region-drop">
											<li>
												<table>
													<tr>
														<td><B>Select From Date</B></td>
														<td><form:input type="text" path="fromDate"
																id="fromDate" name="fromDate" cssClass="form-control" />
														</td>
														<td><B>Select To Date</B></td>
														<td><form:input type="text" path="toDate"
																id="toDate" name="toDate" cssClass="form-control" />
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
 
							</div>
							<form id="showAll">
								<!-- <table class="table table-striped table-bordered table-hover" id="dataTables-example"> -->
								<div id="printableArea">
								 <div id="table_wrapper">
									<table class="table table-striped table-bordered table-hover" id="tableValue">
                                    <thead>
                                       <tr>
											
											<th>Location</th>
											<th>Subject</th>
											<th>Training Date</th>
											<th>Employee Name</th>
											<th>Employee Id</th>
                                       <th>Trainer/Speaker</th>
                                       <th>Start Time</th>
							           <th>Duration</th>
							        <th>Signature</th>
							       
										</tr>
                                    </thead>
                                    <tbody>
									
									 <c:forEach var="row" items="${records}">
									 <tr>
   
  
   <td>${row.location}</td>
   <td>${row.subject}</td>
   <td> ${(row.trainingDate).split("\\s+")[0]}</td>
   <td>${row.employeeName}</td>
   <th>${row.employeeId}</th>
   <td>${row.nameOfTrainer}</td>
   <th>${(row.trainingDate).split("\\s+")[1]}</th>
   <td>${row.duration}</td>
   <td></td>

   </tr>
   </c:forEach>
   
                                   </tbody>
                                </table></div>
								</div>
							</form>
							<!-- </div> DataTable_wrapper bundle div close -->

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
	<script>
		$(document).ready(function() {
			var table = $('#tableValue').dataTable({
				"pagingType" : "full_numbers",
				"lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]]
			});
			var tableTools = new $.fn.dataTable.TableTools(table, {

				/* 'sSwfPath':'//cdn.datatables.net/tabletools/2.2.4/swf/copy_csv_xls_pdf.swf', */
				/* 'sSwfPath' : './js/copy_csv_xls_pdf.swf', */
				'aButtons' : [ /* 'copy' */,/* {
					    		'sExtends':'print',
					    		'bShowAll':false
					    		 }, */
				'csv', {
					'sExtends' : 'xls',
					'sFileName' : '*.xls',
					'sButtonText' : ' Excel'
				}, {
					'sExtends' : 'pdf',
					'bFooter' : false
				} ]
			});
			$(tableTools.fnContainer()).insertBefore('.dataTable_wrapper');
		});
	</script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
	<script type="text/javascript" src="./js/print.js"></script>
	<script src="./resources/js/jquery.datetimepicker.js"></script>
	<script>
		$('#fromDate').datetimepicker({
			format : 'Y-m-d',
		});
		$('#toDate').datetimepicker({
			format : 'Y-m-d',
		});
	</script>
</body>

</html>