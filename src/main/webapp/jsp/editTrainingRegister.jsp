<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="./resources/logo/favicon.ico"
	type="image/x-icon">

<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.datetimepicker.css" />
<script type="text/javascript" src="./js/jquery.validate.min.js"></script>

<title>ICICI : Training Register</title>

<!-- Bootstrap Core CSS -->
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

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body oncontextmenu="return false;">
	<div id="wrapper">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">
			<!--  <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Forms</h1>
                </div>
                /.col-lg-12
            </div> -->
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<!-- <div class="panel-heading">Add/Upload Branch</div> -->
						<div class="panel-heading">
							<ul>
								<li><a href="././viewTrainingRegister"><i
										class="fa fa-table fa-fw"></i> View List of Training Register</a>
								</li>
							</ul>
							Training Register
						</div>

						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<!--<form role="form">-->
									<form:form id="userPage" name="userPage"
										action="updateTrainingRegister" method="post"
										modelAttribute="user" autocomplete="off">
										<form:hidden path="id" />

										<div class="form-group">
											<label>Employee Name</label>
											<form:input path="employeeName" id="employeeName"
												name="employeeName" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Employee Id</label>
											<form:input path="employeeName" id="employeeId"
												name="employeeId" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Location</label>
											<form:input path="location" id="location" name="location"
												cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Training Date and Time</label>
											<form:input path="trainingDate" id="trainingDate"
												name="trainingDate" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Subject</label>
											<form:input path="Subject" id="Subject" name="Subject"
												cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Name of Trainer</label>
											<form:input path="nameOfTrainer" id="nameOfTrainer"
												name="nameOfTrainer" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Remarks</label>
											<form:input path="remarks" id="remarks" name="remarks"
												cssClass="form-control" />
										</div>
										<button type="submit" class="btn btn-lg btn-success btn-block"
											value="Details">Submit</button>
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
	<!-- <script src="./resources/bower_components/jquery/dist/jquery.min.js"></script> -->

	<!-- Bootstrap Core JavaScript -->
	<script
		src="./resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script
		src="./resources/bower_components/metisMenu/dist/metisMenu.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="./resources/dist/js/sb-admin-2.js"></script>

	<script type="text/javascript" src="./js/htmlInjection.js"></script>
	<script src="./resources/js/jquery.datetimepicker.js"></script>
	<script>
		$('#trainingDate').datetimepicker({
			format : 'Y-m-d H:i',
		});
	</script>
</body>

</html>