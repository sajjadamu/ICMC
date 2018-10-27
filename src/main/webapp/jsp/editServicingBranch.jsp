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
<script type="text/javascript" src="./js/jquery.validate.min.js"></script>

<title>ICICI : Edit Servicing Branch</title>

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
						<!-- <div class="panel-heading"> Update Servicing Branch</div> -->

						<div class="panel-heading">
							<ul>
								<li><a href="././viewServicingBranch"><i
										class="fa fa-table fa-fw"></i> Servicing Branch List</a></li>
							</ul>
							Update Servicing Branch
						</div>

						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<!--<form role="form">-->
									<form:form id="userPage" name="userPage"
										action="updateServicingBranch" method="post"
										modelAttribute="user" autocomplete="off">

										<table>
											<tr>
												<td style="visibility: hidden;">id</td>
												<td style="visibility: hidden;"><form:input path="id"
														id="id" /></td>
											</tr>
										</table>

										<div class="form-group">
											<label>Sol ID</label>
											<form:input path="SolId" id="SolId" name="SolId"
												minlength="4" maxlength="4" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>ICMC Name</label>
											<form:input path="icmcName" id="icmcName" name="icmcName"
												maxlength="45" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Branch Name</label>
											<form:input path="branchName" id="branchName"
												name="branchName" maxlength="45" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>RBI JI</label>
											<form:input path="rbiJI" id="rbiJI" name="rbiJI"
												maxlength="45" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>RBI SI</label>
											<form:input path="rbiSI" id="rbiSI" name="rbiSI"
												maxlength="45" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Category</label>
											<form:input path="Category" id="Category" name="Category"
												maxlength="45" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>RBI ICMC</label>
											<form:input path="rbiICMC" id="rbiICMC" name="rbiICMC"
												maxlength="45" cssClass="form-control" />
										</div>

										<button type="submit" class="btn btn-lg btn-success btn-block"
											value="Details">Save</button>
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

	<script type="text/javascript">
	$(function() {
	  $("form[name='userPage']").validate({
	    rules: {
	    	SolId: {
            	required:true,
            	number:true,
            	minlength:4,
            	maxlength:4,
            },
            icmcName: {
            	required:true,
            	maxlength:44,
            },
            branchName: {
            	required:true,
            	maxlength:44,
            },
            rbiJI: {
            	required:true,
            	maxlength:44,
            },
            rbiSI: {
            	required:true,
            	maxlength:44,
            },
            Category: {
            	required:true,
            	maxlength:44,
            },
            rbiICMC: {
            	required:true,
            	maxlength:44,
            },
            
	    },
	    // Specify validation error messages
	    messages: {
             solId: {
            	 required:"Please Enter Sol ID",
            	 number:"Please Enter Valid Sol ID",
            	 minlength:"Sol ID should have 4 digits",
            	 maxlength:"Sol ID can't have more than 4 digits",
             },
             icmcName: {
	    		required:"Please Enter ICMC Name",
	    		maxlength:"ICMC Name can't have more than 45 characters",
	    	 },
             branchName: {
	    		required:"Please Enter Branch Name",
	    		maxlength:"Branch Name can't have more than 45 characters",
	    	 },
             rbiJI: {
	    		required:"Please Enter RBIJI",
	    		maxlength:"RBIJI can't have more than 45 characters",
	    	 },
             rbiSI: {
	    		required:"Please Enter RBISI",
	    		maxlength:"RBISI can't have more than 45 characters",
	    	 },
             Category: {
	    		required:"Please Enter Category",
	    		maxlength:"Category can't have more than 45 characters",
	    	 },
             rbiICMC: {
	    		required:"Please Enter RBI ICMC",
	    		maxlength:"RBI ICMC can't have more than 45 characters",
	    	 },
	    },
	    submitHandler: function(form) {
	      form.submit();
	    }
	  });
	});
	</script>

	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>