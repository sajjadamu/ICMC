<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">
	<link rel="shortcut icon" href="./resources/logo/favicon.ico" type="image/x-icon">
	<script type="text/javascript" src="./js/jquery.validate.min.js"></script>
	
	<title>ICICI : Printer Setting</title>
	
	<!-- Bootstrap Core CSS -->
	<link href="./resources/bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
	
	<!-- MetisMenu CSS -->
	<link href="./resources/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">
	
	<!-- Custom CSS -->
	<link href="./resources/dist/css/sb-admin-2.css" rel="stylesheet">
	
	<!-- Custom Fonts -->
	<link href="./resources/bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
	
	<link rel="stylesheet" type="text/css" href="./resources/dist/css/style.css">
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
						<div class="panel-heading">
							<ul>
								<!-- <li><a href="././viewPrinter"><i class="fa fa-table fa-fw"></i> View Printer's List</a></li> -->
							</ul>Change Printer Setting
						</div>
						
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<!--<form role="form">-->
									<form:form id="userPage" name="userPage" action="updatePrinterSetting" method="post"
										modelAttribute="icmcPrinter" enctype="multipart/form-data" autocomplete="off">
										
										<div class="form-group">
											<label>ICMC Printer</label>
											<form:select path="printerName" id="printerName" name="icmcPrinter" cssClass="form-control">
												<option value="" label="Select Printer"></option>
												<form:options items="${icmcPrinterList}"/>
											</form:select>
										</div>
										
										<button type="submit" class="btn btn-lg btn-success btn-block" value="Details">Update</button>
									</form:form>
								</div>
								<!-- /.col-lg-6 (nested) -->
								<div class="col-lg-6"></div>
								<!-- /.col-lg-6 (nested) -->
							</div>
							<!-- /.row (nested) -->
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->

	<!-- jQuery -->
	<script src="./resources/bower_components/jquery/dist/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="./resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script src="./resources/bower_components/metisMenu/dist/metisMenu.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="./resources/dist/js/sb-admin-2.js"></script>

<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>
</html>