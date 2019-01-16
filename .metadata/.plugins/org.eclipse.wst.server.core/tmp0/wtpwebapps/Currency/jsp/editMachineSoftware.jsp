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

<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="./js/jquery.validate.min.js"></script>

<title> Edit Machine Software</title>

<link href="./resources/css/calendar.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.datetimepicker.css" />


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
	data-genuitec-file-id="wc1-125"
	data-genuitec-path="/Currency/src/main/webapp/jsp/editMachineSoftware.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-125"
		data-genuitec-path="/Currency/src/main/webapp/jsp/editMachineSoftware.jsp">
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
						<div class="panel-heading">Modify Machine Software Details</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<!--<form role="form">-->
									<div align="center" style="color: red">
										<b>${successMsg}</b>
									</div>
									<form:form id="userPage" name="userPage"
										action="updateMachineSoftware" method="post"
										modelAttribute="user" autocomplete="off">

										<form:hidden path="id" />

										<div class="form-group">
											<label>Region</label>
											<form:select path="region" id="region" name="region"
												cssClass="form-control">
												<form:options items="${region}" />
											</form:select>
										</div>

										<div class="form-group">
											<label>Date</label>
											<form:input path="softwareUpdationDate"
												id="softwareUpdationDate" name="softwareUpdationDate"
												cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Software Details</label>
											<form:input path="softwaredetails" id="softwaredetails"
												name="softwaredetails" maxlength="45"
												cssClass="form-control" />
										</div>

										<button type="submit" onclick="pageSubmit()"
											class="btn btn-lg btn-success btn-block" value="Details">Update</button>
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
$.validator.addMethod("loginRegex", function(value, element) {
    return this.optional(element) || /^[a-zA-Z\s\\-]+$/i.test(value);
}, "Software must contain only letters,Space , dashes.");


	$(function() {
	  $("form[name='userPage']").validate({
	    rules: {
	    	region: "required",
	    	softwareUpdationDate: "required",
	    	softwaredetails:{
	    		 required: true,
	             loginRegex: true,
	                rangelength: [0, 44]
	    	} ,
         },
	    // Specify validation error messages
	    messages: {
	    	region: "Please Select Region",
	    	softwareUpdationDate: "Please Select Date ",
	    	softwaredetails:{
	    		 required: "You must enter a Software Details",
	              loginRegex: "only character value  accepted",
	                rangelength: "Software Details can't have more than  45 character"
        		
	    	} ,
          },
	    // Make sure the form is submitted to the destination defined
	    // in the "action" attribute of the form when valid
	    submitHandler: function(form) {
	      form.submit();
	    }
	  });
	});
	</script>
	<script src="./resources/js/jquery.datetimepicker.js"></script>
	<script>
		$('#softwareUpdationDate').datetimepicker({
			format : 'Y-m-d',

		});
		
	</script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>