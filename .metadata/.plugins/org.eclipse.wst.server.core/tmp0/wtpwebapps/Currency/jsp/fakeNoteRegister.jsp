<!DOCTYPE html>
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
<title> Fake Note Register</title>

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
<script type="text/javascript" src="./resources/js/sum().js"></script>
<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.dataTables.min.css">
<link rel="stylesheet" type="text/css"
	href='./resources/css/dataTables.tableTools.min.css'>
<link rel="stylesheet" type="text/css"
	href='./resources/css/dataTables.tableTools.css'>
<!-- DataTable -->

<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-143"
	data-genuitec-path="/Currency/src/main/webapp/jsp/fakeNoteRegister.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-143"
		data-genuitec-path="/Currency/src/main/webapp/jsp/fakeNoteRegister.jsp">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />
		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">Fake Note Register</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="region-con">
								<form:form id="userPage" name="userPage" action="chestSlip"
									method="post" modelAttribute="reportDate" autocomplete="off">
									<div class="region-con-sec">
										<ul class="region-drop">
											<li><label>Select Date</label> <span> <input
													type="text" id="fromDate" name="fromDate" />
											</span> <span>
													<button type="submit" class="btn btn-default"
														value="Details" style="width: 99px;">Search</button>
											</span></li>


										</ul>

									</div>
								</form:form>
							</div>

							<div class="dataTable_wrapper">
								<form id="showAll">
									<!-- <table class="table table-striped table-bordered table-hover" id="dataTables-example"> -->
									<table class="table table-striped table-bordered table-hover"
										id="tableValue">
										<thead>
											<tr>
												<th>FakeTransDate</th>
												<th>FakeHeaderId</th>
												<th>AttemptCount</th>
												<th>SERV_ICMC</th>
												<th>BranchName</th>
												<th>State</th>
												<th>SolID</th>
												<th>Denomination</th>
												<th>No. of Notes</th>
												<th>Value</th>
												<th>SerialNo</th>
												<th>Prefix</th>
												<th>AccountNo</th>
												<th>JurisdictionFlag</th>
												<th>JurisdictionICMC</th>
												<th>DetectionLevel</th>
												<th>PrintQuality</th>
												<th>Paper Quality</th>
												<th>GovernerSign</th>
												<th>Design in Color</th>
												<th>FCRMSrNo</th>
												<th>Print Year</th>
												<th>Absent Security Features</th>
												<th>CustomerName</th>
												<th>Remarks</th>
												<th>Branch_Det</th>
												<th>Branch_det_date</th>
												<th>ISFIR</th>
												<th>CreatedBy</th>
												<th>CheckedBy</th>
												<th>Transaction No</th>
												<th>No. Of Attempts</th>
												<th>Attempt Status</th>
												<th>Attempt made by Branch/ICMC</th>
												<th>Name of ICICI Bank Official</th>
												<th>Name of Police Official</th>
												<th>Name of Police Station</th>
												<th>FIR / Consolidated Report Accepted</th>
												<th>Fake Notes Accepted</th>
												<th>FIR With Fake Notes accepted</th>
												<th>Interaction details</th>
												<th>Type of Acceptance</th>
												<th>Submitted By</th>
												<th>First Attempt Date</th>
												<th>Second attempt Date</th>
												<th>Second Attempt Status</th>
												<th>Send to Branch</th>
												<th>BM approval</th>
												<th>RH approval</th>
												<th>Date if movement</th>
												<th>CIT Vendor</th>
												<th>Vehicle No</th>
												<th>Custodian Name</th>
												<th>Date of Receipt at Branch</th>
												<th>Emp Id of Sender</th>
												<th>Emp Id of Receiver</th>
												<th>Received From Branch</th>
												<th>Remarks/Reason for Change</th>
											</tr>
										</thead>
										<tbody>
											<%-- <tr>
											<td><fmt:formatDate pattern="dd-MMM-yy" value="${row.insertTime.time}" /></td>
											<td>${row.userName}</td>
											<td>${row.userId}</td>
											<td>${row.handingOverCharge}</td>
											<td>${row.userId}</td>
											<td>${row.custodian}</td>
											<td>${row.remarks}</td>
										</tr> --%>
										</tbody>
									</table>
								</form>
							</div>
							<!-- /.table-responsive -->

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

	<script>
 $(document).ready(function () {
    	var table=$('#tableValue').dataTable({
    		 "pagingType": "full_numbers",
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
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>
</html>