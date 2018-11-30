<!DOCTYPE html>
<html lang="en">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<head>
<link href="./resources/css/calendar.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.datetimepicker.css" />
<script src="./resources/Currency/js/jquery.js"></script>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="./resources/logo/favicon.ico"
	type="image/x-icon">
<title>ICICI : Machine Software Updation</title>
<style type="text/css">
table.table-bordered.dataTable thead tr th {
	width: 50px;
}

table.table-bordered.dataTable tbody tr td {
	width: 50px;
}

table.table-bordered.dataTable tbody tr td input {
	width: 40px;
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
	href="dist/css/style.css">

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

<!--    DataTable  -->
<script type="text/javascript" charset="utf8"
	src="./resources/js/jquery.dataTables.min.js"></script>
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
<script type="text/javascript" charset="utf8"
	src="./resources/js/dataTables.jqueryui.js"></script>
<link rel="stylesheet" type="text/css"
	href="./resources/css/dataTables.jqueryui.css">
<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.dataTables.css">
<!--    DataTable  -->

</head>
<script type="text/javascript">

function doAjaxPostInsert(str) {  
	addHeaderJson();  
	//alert(str)
	var machineSoftwareUpdation={
			  "machineNo":$('#machineNo'+str).text(),
			  "assetCode":$('#assetCode'+str).text(),
			  "machineSINo":$('#machineSINo'+str).text(),
			  "modelType":$('#modelType'+str).text(),
			  "standardProductivity":$('#standardProductivity'+str).text(),
			  "pmDate":$('#pmDate'+str).val(),
			  "sap":$('#sap'+str).val(),
			  "map":$('#map'+str).val(),
			  "osap":$('#osap'+str).val(),
			  "id":str,
	  }
	  //alert($('#id'+str).val())
	   //alert($('#pmDate'+str).val())
	  $.ajax({  
	    type: "POST", 
	    contentType : 'application/json; charset=utf-8',
	      dataType : 'json',
	    url: "././AddMachineSoftware",
	    data: JSON.stringify(machineSoftwareUpdation),
	    success: function(response){ 
	    	alert('Record submit.');
	    	window.location='././MachineSoftwareUpdation';
	    }, 
	    error: function(e){  
	      alert('Error: ' + e);  
	    }  
	  }); 
	} 
	
</script>
<body oncontextmenu="return false;">

	<div id="wrapper">

		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">
			<!-- <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Tables</h1>
                </div>
                /.col-lg-12
            </div> -->
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">Machine Software Updation</div>

						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<form id="showAll">
									<!-- <table class="table table-striped table-bordered table-hover" id="dataTables-example"> -->
									<table class="table table-striped table-bordered table-hover"
										id="tableValue">
										<thead>
											<tr>
												<th>Sr.No</th>
												<th>Machine No</th>
												<th>Asset Code</th>
												<th>Machine SI No</th>
												<th>Model Type</th>
												<th>Standard Productivity</th>
												<th>PM Date</th>
												<th>Aeging</th>
												<th>SAP</th>
												<th>MAP</th>
												<th>OSAP</th>
												<th>ACTION</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="row" items="${records}">
												<input type="hidden" value="${row.id}" id="id${row.id}">
												<tr>
													<td id="id${row.id}">${row.id}</td>
													<td id="machineNo${row.id}">${row.machineNo}</td>
													<td id="assetCode${row.id }">${row.assetCode}</td>
													<td id="machineSINo${row.id }">${row.machineSINo}</td>
													<td id="modelType${row.id }">${row.modelType}</td>
													<td id="standardProductivity${row.id }">${row.standardProductivity}</td>
													<td><input style="width: 80px;" type="text"
														value="${row.pmDate}" name="pmDate" value="${fmtDate}"
														id="pmDate${row.id}" onclick="SaveDate(${row.id})"></td>
													<td></td>
													<td><input type="text" name="sap" value="${row.sap}"
														id="sap${row.id}"></td>
													<Td><input type="text" name="map${row.id}"
														value="${row.map}" id="map${row.id}"></Td>
													<Td><input type="text" name="osap${row.id}"
														id="osap${row.id}" value="${row.osap}"></Td>
													<td><c:if test="${row.pmDate eq null}">
															<input type="button" value="Save"
																onclick="doAjaxPostInsert(${row.id})">
														</c:if></td>
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

	<!-- jQuery -->
	<script src="./resources/bower_components/jquery/dist/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script
		src="./resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script
		src="./resources/bower_components/metisMenu/dist/metisMenu.min.js"></script>

	<!-- DataTables JavaScript -->
	<script
		src="./resources/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
	<script
		src="./resources/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="./resources/dist/js/sb-admin-2.js"></script>

	<!-- Page-Level Demo Scripts - Tables - Use for reference -->
	<script>
    $(document).ready(function () {
        $('#tableValue').dataTable({
        	
        	"pagingType": "full_numbers",
        	
        });
    });
    </script>
	<script src="./resources/js/jquery.datetimepicker.js"></script>
	<script>
	function SaveDate(str)
	{
		$('#pmDate'+str).datetimepicker({
			format : 'Y-m-d',

		});
	}
	</script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>