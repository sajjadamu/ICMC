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
<link rel="shortcut icon" href="./resources/logo/yes_favicon.ico"
	type="image/x-icon">

<script src="./resources/bower_components/jquery/dist/jquery.min.js"></script>
<script type="text/javascript" src="./js/jquery.validate.min.js"></script>

<title> Add Account Detail</title>

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

<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-47"
	data-genuitec-path="/Currency/src/main/webapp/jsp/addAccountDetail.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-47"
		data-genuitec-path="/Currency/src/main/webapp/jsp/addAccountDetail.jsp">
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
						<div class="panel-heading">Add Account Detail</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<!--<form role="form">-->

									<form:form id="userPage" name="userPage"
										action="saveAccountDetails" method="post"
										modelAttribute="user" autocomplete="off">

										<div class="form-group">
											<label>Activity</label>
											<form:input path="activity" id="activity" name="activity"
												maxlength="45" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Usage</label>
											<form:input path="usage" id="usage" name="usage"
												maxlength="45" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Dr/Cr</label>
											<form:select path="debitOrCredit" id="debitOrCredit"
												name="debitOrCredit" cssClass="form-control">
												<form:option value="error">Select Dr/Cr</form:option>
												<form:option value="Dr">Dr</form:option>
												<form:option value="Cr">Cr</form:option>
											</form:select>
										</div>

										<div class="form-group">
											<label>Account Details</label>
											<form:input path="accountDetails" id="accountDetails"
												name="accountDetails" maxlength="45" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Branch Details</label>
											<form:input path="branchDetails" id="branchDetails"
												name="branchDetails" maxlength="45" cssClass="form-control" />
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

	<!-- <script type="text/javascript">
	$(function() {
	  $("form[name='userPage']").validate({
	    rules: {
	    	id: {
            	required:true,
            	maxlength:31,
            },
            name: {
            	required:true,
            	maxlength:44,
            },
            email: {
            	required:true,
            	email:true,
            	maxlength:44,
            },
            role: {
                required: true,
                role: true,
            },
            status: "required",
	    },
	    // Specify validation error messages
	    messages: {
	    	 id: {
	    		required:"Please Enter User ID",
	    		maxlength:"User ID can't have more than 32 characters",
	    	},
             name: {
	    		required:"Please Enter User Name",
	    		maxlength:"User Name can't have more than 45 characters",
	    	},
             email: {
            	 required:"Please Enter Email",
            	 email:"Please Enter a Valid Email ID",
            	 maxlength:"Email can't have more than 45 characters",
             },
             role: {
     	        required: "You must select Role",
     	     },
             status: "Please Select Status",
	    },
	    submitHandler: function(form) {
	      form.submit();
	    }
	  });
	});
	</script> -->
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>