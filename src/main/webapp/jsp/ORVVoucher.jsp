<!DOCTYPE html>
<html lang="en">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="./resources/logo/favicon.ico"
	type="image/x-icon">
<title>ICICI : Search ORV Voucher</title>

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

<script type="text/javascript">
	function srNumber() {
		//var solId = $('#idSolId').text();
		var Id = $("#idSolId option:selected").val();
		//var ids= $("#idSolId option:selected" ).text();
		$.ajax({
			type : "POST",
			url : "././getSRNumberBySolId",
			data : "Id=" + Id,
			success : function(response) {
				var newStr = response.toString();
				var data = newStr.split(",");
				var option = '<option value="">Select Sr Number</option>';
				for (i = 0; i < data.length; i++) {
					option += '<option value="' + data[i].trim() + '">'
							+ data[i].trim() + '</option>';
				}
				$('#srNo').html(option);
			},
			error : function(e) {
				alert('SR Error: ' + e);
			}
		});
	}
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
						<div class="panel-heading">ORV</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<%-- <form id="showAll"> --%>
								<form:form id="userPage" action="viewORVVoucher" method="post"
									modelAttribute="user">
									<form:hidden path="id" />
									<form:hidden path="solID" />
									<div class="row">
										<div class="col-sm-12">
											<div class="col-sm-2 no-margin">
												<div class="form-group">
													<form:select id="idSolId" name="id" path="id"
														class="form-control deno-figure-select"
														style="width: 140px;" onchange="srNumber()">
														<form:option value="">Select Sol Id </form:option>
														<form:options items="${records}" itemValue="id"
															itemLabel="solID" />
													</form:select>
													<form:select path="srNo" id="srNo" name="srNo"
														style="width: 140px;"
														class="form-control deno-figure-select">
														<form:option value="">Select SR Number</form:option>
													</form:select>
												</div>
											</div>

											<div class="col-sm-2 no-margin">
												<div class="button">
													<button type="submit" class="btn btn-default">Search</button>
												</div>
											</div>
											<div class="col-sm-2 no-margin"></div>
										</div>
										<div class="col-sm-12"></div>
										<!-- /.row (nested) -->

									</div>
								</form:form>

								<form:form id="userPage" action="printAllVoucher" method="post"
									modelAttribute="user">
									<div class="col-sm-2 no-margin">
										<div class="button">
											<button type="submit" class="btn btn-default">Show
												All</button>
										</div>
									</div>
								</form:form>


								<form:form id="userPage" action="viewSelectedORVVoucher"
									method="post" modelAttribute="user">
									<div class="row">
										<div class="col-lg-12">
											<div class="col-sm-10 no-margin">
												<div class="form-group">
													<div class="form-group rolewrap">
														<form:checkboxes name="sasId" items="${records}"
															itemValue="id" itemLabel="solID" path="id" />
													</div>
												</div>
											</div>

											<div class="col-sm-2 no-margin">
												<div class="button">
													<button type="submit" class="btn btn-default">Search</button>
												</div>
											</div>
										</div>
									</div>
								</form:form>

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
		$(document).ready(function() {
			$('#dataTables-example').DataTable({
				responsive : true
			});
		});
	</script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>