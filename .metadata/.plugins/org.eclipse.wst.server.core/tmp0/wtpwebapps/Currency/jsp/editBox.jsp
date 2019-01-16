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
<link href="./resources/css/calendar.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.datetimepicker.css" />

<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="./js/jquery.validate.min.js"></script>

<title> Edit Box</title>


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
	data-genuitec-file-id="wc1-99"
	data-genuitec-path="/Currency/src/main/webapp/jsp/editBox.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-99"
		data-genuitec-path="/Currency/src/main/webapp/jsp/editBox.jsp">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">

			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">Edit Box Details</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<!--<form role="form">-->
									<form:form id="userPage" action="updateBox" method="post"
										name="userPage" modelAttribute="user">

										<form:hidden path="id" />

										<div class="form-group">
											<label>Denomination</label>
											<form:select path="denomination" id="denomination"
												name="denomination" cssClass="form-control">
												<option>Select Denomination</option>
												<form:options items="${denominationList}"
													itemValue="denomination" itemLabel="denomination"
													name="denomination" />
											</form:select>
										</div>

										<div class="form-group">
											<label>Currency Type</label>
											<form:select path="currencyType" id="currencyType"
												name="currencyType" cssClass="form-control">
												<option value="">Select Currency Type</option>
												<%-- <form:options items="${currencyTypeList}" /> --%>
												<option value="UNPROCESS">UNPROCESS</option>
												<option value="ATM">ATM</option>
												<option value="FRESH">FRESH</option>
												<option value="SOILED">SOILED</option>
												<option value="ISSUABLE">ISSUABLE</option>
											</form:select>
										</div>

										<div class="form-group">
											<label>Maximum BOX Capacity</label>
											<form:input path="maxCapacity" id="maxCapacity"
												name="maxCapacity" maxlength="10" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>BOX Name</label>
											<form:input path="boxName" id="boxName" name="boxName"
												maxlength="10" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Status</label>
											<form:select path="status" id="status" name="status"
												cssClass="form-control">
												<option>Select Status</option>
												<form:options items="${statusList}" />
											</form:select>
										</div>

										<button type="submit" class="btn btn-lg btn-success btn-block"
											value="Details">Update</button>
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
	    return this.optional(element) || /^[0-9]+$/i.test(value);
	}, "Software must contain only letters,Space , dashes.");
	
	$.validator.addMethod("boxNameRegex", function(value, element) {
	    return this.optional(element) || /^[a-zA-Z0-9]+$/i.test(value);
	}, "Software must contain only letters,Space , dashes.");
	
	$(function() {
	  $("form[name='userPage']").validate({
	    rules: {
	    	denomination: {
            	required:true,
            	loginRegex:true,
            },
            currencyType: {
	    		required:true,
	    		maxlength:9,
	    	},
	    	maxCapacity: {
	    		required:true,
	    		number:true,
	    		maxlength:9,
	    	},
	    	boxName: {
	    		required:true,
	    		maxlength:9,
	    		boxNameRegex:true,
	    	},
	    	status: {
	    		required:true,
	    	},
	    },
	    // Specify validation error messages
	    messages: {
	    	denomination:{
            	required:"Please Select Denomination",
            	loginRegex:"Please Select Denomination",
            },
            currencyType: {
	    		required:"Please Select Currency Type",
	    		maxlength:"Maximum length of Currency Type can't have more than 10 characters",
	    	},
	    	maxCapacity: {
	    		required:"Please Enter Maximum Box Capacity",
	    		number:"Please Enter Valid Maximum Box Capacity",
	    		maxlength:"Maximum Box Capacity can't have more than 10 digits",
	    	},
	    	boxName: {
	    		required:"Please Enter Box Name",
	    		maxlength:"Length of Box Name can't have more than 10 characters",
	    		boxNameRegex:"Special characters are not allowed",
	    	},
	    	status: {
	    		required:"Please Select Status",
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