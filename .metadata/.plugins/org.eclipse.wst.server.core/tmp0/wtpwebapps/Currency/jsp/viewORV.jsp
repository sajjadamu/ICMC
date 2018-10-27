<!DOCTYPE html>
<html lang="en">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
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
<title>ICICI : View ORV</title>

<script type="text/javascript">
	function doAjaxPostUpdateStatus(str) {
		addHeader();
		// get the form values  
		var id = $('#id' + str).text();
		//alert("id=="+id)
		// var status=$('#status'+str+' :checked').val();
		if ($('#status' + str).is(':checked')) {
			// alert("hjhj")
			var status = 1;

			$.ajax({
				type : "POST",
				url : "././updateORVStatus",
				data : "id=" + id,
				success : function(response) {
					// $('#info').html(response);
					$("#xyz" + str).hide();
				},
				error : function(e) {
					alert(' Error: ' + e);
				}
			});
		} else {
			alert("hello");
		}
	}
</script>
<script type="text/javascript">

function doAjaxPostCancel(id){
	addHeader();
	var idFromUI = id;

	if (confirm('Are you sure you want to cancel this Branch Payment request?')) {
		$("#cancel"+idFromUI).prop('disabled',true);
		
		$.ajax({
			type : "POST",
			url : "././cancelBranchPaymentSas",
			data : "idFromUI=" + idFromUI,
			success : function(response) {
				//alert('response: ' +response.responseJSON.message);
				alert('Record Cancelled.');
				window.location='././viewORV';
			},
			error : function(e) {
				//alert('Error Occured: ' + e);
				alert(e.responseJSON.message);
				//window.location='././viewORV';
			}
		});
	} else {
	    // Do nothing!
	}
	
}</script>

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
	data-genuitec-file-id="wc1-245"
	data-genuitec-path="/Currency/src/main/webapp/jsp/viewORV.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-245"
		data-genuitec-path="/Currency/src/main/webapp/jsp/viewORV.jsp">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />
		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<ul>
								<li><sec:authorize access="hasRole('ADD_BRANCH_PAYMENT')">
										<a href="././ORVBranch"><i class="fa fa-table fa-fw"></i>
											Add ORV Data</a>
									</sec:authorize></li>
							</ul>
							ORV Data List
						</div>
						<div align="center" style="color: white; background: red;">
							<b>${sasDataMsg}</b>
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<form id="showAll">
									<table class="table table-striped table-bordered table-hover"
										id="tableValue">
										<thead>
											<tr>
												<th>SR No</th>
												<th>Sol ID</th>
												<th>Branch</th>
												<th>Total Value</th>
												<th>Edit</th>
												<th>Cancel</th>
											</tr>
										</thead>
										<tfoot>
											<tr>
												<th style="text-align: right">Total:</th>
											</tr>
										</tfoot>
										<tbody>
											<c:forEach var="row" items="${records}">
												<tr>
													<td>${row.srNo}</td>
													<td>${row.solID}</td>
													<td>${row.branch}</td>
													<td>${row.totalValue}</td>
													<td><a href="editViewOrv?id=${row.id}">Edit</a></td>
													<td><input id="cancel${row.id}" type="button"
														value="Cancel" onclick="doAjaxPostCancel(${row.id});"></td>
												</tr>
											</c:forEach>
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
	    	$('#tableValue').dataTable({
    		 "pagingType": "full_numbers",
    		 drawCallback: function () {
	    	        var api = this.api();
	    	        $( api.table().footer() ).html('<th colspan="4" style="text-align:right">Current Page Total: '+
	    	        		api.column( 3, {page:'current'} ).data().sum().toLocaleString('en-IN')+'; All Pages Total: '+
	    	        		api.column( 3 ).data().sum().toLocaleString('en-IN')+'</th>'
	    	        );
	    	      }
	    	});
	    });
	</script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>