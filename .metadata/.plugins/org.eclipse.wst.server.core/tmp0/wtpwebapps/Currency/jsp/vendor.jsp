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

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="./js/jquery.validate.min.js"></script>

<title>ICICI : Add Vendor</title>

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
<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script></head>
<body oncontextmenu="return false;" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-197" data-genuitec-path="/Currency/src/main/webapp/jsp/vendor.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-197" data-genuitec-path="/Currency/src/main/webapp/jsp/vendor.jsp">
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
						<div class="panel-heading">
                          <ul>
                          	<li><a href="././viewVendor"><i class="fa fa-table fa-fw"></i> View Vendor's List</a></li>
                          </ul>Add Vendor
                        </div>
                        
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<!--<form role="form">-->
									<form:form id="userPage" name = "userPage" action="saveVendor" method="post"
										modelAttribute="user" autocomplete="off">
										
										<div class="form-group">
											<label>Vendor Name</label>
											<form:input path="name" id="name" name="name" maxlength="45"
												cssClass="form-control" />
										</div>
										
										<div class="form-group">
											<label>Address</label>
											<form:input path="address" id="address" name="address" maxlength="45"
												cssClass="form-control" />
										</div>
										
										<div class="form-group">
											<label>Location</label>
											<form:input path="location" id="location" name="location" maxlength="45"
												cssClass="form-control" />
										</div>
										
										<div class="form-group">
											<label>City</label>
											<form:input path="city" id="city" name="city" maxlength="45"
												cssClass="form-control" />
										</div>
										
										<div class="form-group">
											<label>Pin Code</label>
											<form:input path="pincode" id="pincode" name="pincode" maxlength="6"
												cssClass="form-control" />
										</div>
										
										<div class="form-group">
											<label>Phone Number</label>
											<form:input path="phoneNumber" id="phoneNumber" name="phoneNumber"
												maxlength="10" cssClass="form-control" />
										</div>
										
										<div class="form-group">
											<label>Email ID</label>
											<form:input path="emailID" id="emailID" name="emailID" maxlength="45"
												cssClass="form-control" />
										</div>
										
										<div class="form-group">
											<label>PF Reg Number</label>
											<form:input path="pfRegNumber" id="pfRegNumber" name="pfRegNumber"
												maxlength="45" cssClass="form-control" />
										</div>
										
										<div class="form-group">
											<label>ESIC Reg Number</label>
											<form:input path="esicRegNumber" id="esicRegNumber" name="esicRegNumber"
												maxlength="45" cssClass="form-control" />
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
	
	<script type="text/javascript">
	$.validator.addMethod("loginRegexCity", function(value, element) {
	    return this.optional(element) || /^[A-Za-z\s]+$/i.test(value);
	}, "Software must contain only letters,Space , dashes.");

	$.validator.addMethod("loginRegexAddress", function(value, element) {
	    return this.optional(element) || /^[0-9A-Za-z\s\(\)\-\,\/\.]+$/i.test(value);
	}, "Software must contain only letters,Space , dashes.");

	$.validator.addMethod("loginRegex", function(value, element) {
	    return this.optional(element) || /^[0-9]+$/i.test(value);
	}, "Software must contain only letters,Space , dashes.");

	$.validator.addMethod("loginRegexFpr", function(value, element) {
	    return this.optional(element) || /^[A-Za-z0-9]+$/i.test(value);
	}, "Software must contain only letters,Space , dashes.");

	
	$(function() {
	  $("form[name='userPage']").validate({
	    rules: {
            name: {
            	required:true,
            	loginRegexCity:true,
            	maxlength:44,
            },
            address: {
            	loginRegexAddress:true,
            	required:true,
            	maxlength:44,
            },
            location: {
            	loginRegexAddress:true,
            	required:true,
            	maxlength:44,
            },
            city: {
            	loginRegexCity:true,
            	required:true,
            	maxlength:44,
            },
            pincode: { 
            	loginRegex:true,
            	required:true,
            	//number:true,
            	minlength:6,
            	maxlength:6,
            },
            phoneNumber: {
            	required:true,
            	loginRegex:true,
            	maxlength:10,
            },
            emailID: {
            	required:true,
            	email:true,
            	maxlength:44,
            },
            pfRegNumber: {
            	loginRegexFpr:true,
            	required:true,
            	maxlength:44,
            },
            esicRegNumber: {
            	loginRegexFpr:true,
            	required:true,
            	maxlength:44,
            },
	    },
	    // Specify validation error messages
	    messages: {
             name: {
            	 
	    		required:"Please Enter Vendor Name",
	    		loginRegexCity:"Accept only Character value",
	    		maxlength:"Vendor Name can't have more than 45 characters",
	    	 },
             address: {
            	 loginRegexAddress:"This Special Character is not allowed",
	    		required:"Please Enter Address",
	    		maxlength:"Address can't have more than 45 characters",
	    	 },
             location: {
            	 loginRegexAddress:"This Special Character is not allowed",
	    		required:"Please Enter Location",
	    		maxlength:"Location can't have more than 45 characters",
	    	 },
             city: {
            	 loginRegexCity:"Accept only Character value",
	    		required:"Please Enter City",
	    		maxlength:"City can't have more than 45 characters",
	    	 },
             pincode: {
            	 required:"Please Enter PIN Code",
            	 loginRegex:"Please Enter Valid PIN Code",
            	 minlength:"PIN Code should have minimum 6 digits",
            	 maxlength:"PIN Code can't have more than 6 digits",
             },
             phoneNumber: {
            	 required:"Please Enter Phone Number",
            	 loginRegex:"Please Enter Valid Phone Number",
            	 maxlength:"Phone Number can't have more than 10 digits",
             },
             emailID: {
            	 required:"Please Enter Email ID",
            	 email:"Please Enter a valid Email ID",
            	 maxlength:"Email ID can't have more than 45 characters",
             },
             pfRegNumber: {
            	 loginRegexFpr:"Accept only Character and Numerical value",
	    		required:"Please Enter PF Reg Number",
	    		maxlength:"PF Reg Number can't have more than 45 characters",
	    	 },
             esicRegNumber: {
            	 loginRegexFpr:"Accept only Character and Numerical value",
	    		required:"Please Enter ESIC Reg Number",
	    		maxlength:"ESIC Reg Number can't have more than 45 characters",
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