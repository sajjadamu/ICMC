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
<title>ICICI : Accept Soiled Indent</title>

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

<script type="text/javascript">
    function doAjaxPostUpdateStatus(str) {  
    	addHeader();
    	// get the form values  
  	  var soiledAccept={
  	  "id":$('#id'+str).text(),
  	  "soiledRemittanceId":$('#soiledRemittanceId'+str).text(),
  	  "box":$('#box'+str).val(),
  	  "weight":$('#weight'+str).val(),
  	  "denomination":$('#denomination'+str).text(),
  	  "binNumber":$('#binNumber'+str).text(),
  	  "bundle":$('#bundle'+str).text(),
  	  }
  	 // var status=$('#status'+str+' :checked').val();
  	  $.ajax({  
  	    type: "POST", 
  	  contentType : 'application/json; charset=utf-8',
  	    url: "././acceptSoiledIndent",
  	    data: JSON.stringify(soiledAccept),
  	    success: function(response){  
  	     // $('#info').html(response);
  	     $("#xy"+str).hide();
  	    }, 
  	    error: function(e){  
  	      alert(' Error: ' + e);  
  	    }  
  	  }); 
  	}  	
  </script>

<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-5"
	data-genuitec-path="/Currency/src/main/webapp/jsp/AcceptSoiledIndent.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-5"
		data-genuitec-path="/Currency/src/main/webapp/jsp/AcceptSoiledIndent.jsp">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<ul>
								<li></li>
							</ul>
							Accept Soiled Indent Request
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<form id="showAll">
									<!-- <table class="table table-striped table-bordered table-hover" id="dataTables-example"> -->
									<table class="table table-striped table-bordered table-hover"
										id="tableValue">
										<thead>
											<tr>
												<th>Box Number</th>
												<th style="display: none;">Soiled Remittance ID</th>
												<th>Denomination</th>
												<th>Bundle</th>
												<th>Value</th>
												<!-- <th>Total</th> -->
												<!-- <th>Bin</th> -->
												<th>Category</th>
												<th>Weight</th>
												<th>Action</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="row" items="${records}">
												<tr id="xy${row.id}">
													<td id="id${row.id}">${row.id}</td>
													<%-- <td >${row.binTxnId}</td> --%>
													<td id="soiledRemittanceId${row.id}" style="display: none;">${row.soiledRemittanceId}</td>
													<td id="denomination${row.id}">${row.denomination}</td>
													<td id="bundle${row.id}">${row.pendingBundle}</td>
													<td>${row.denomination*row.pendingBundle*1000}</td>
													<%-- <td id="total${row.id}">${row.total}</td> --%>
													<%-- <td id="binNumber${row.id}">${row.binNumber}</td> --%>
													<td id="box${row.id}">${row.box}</td>
													<td><input type="text" name="weight"
														id="weight${row.id}"></td>
													<!-- <td><input type="submit" value="Accept"> -->
													<td><sec:authorize access="hasRole('ACCEPT_INDENT')">
															<input type="button" value="Accept"
																onclick="doAjaxPostUpdateStatus(${row.id})">
														</sec:authorize></td>
													<%-- <td><sec:authorize access="hasRole('ACCEPT_INDENT')">
				   			<input type="button" value="Reject" />
				   		</sec:authorize>
				   	</td> --%>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</form>
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

	<!-- Page-Level Demo Scripts - Tables - Use for reference -->
	<script>
    $(document).ready(function () {
        $('#tableValue').dataTable({
        	"lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
        	"pagingType": "full_numbers",
        	
        });
    });
    </script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>