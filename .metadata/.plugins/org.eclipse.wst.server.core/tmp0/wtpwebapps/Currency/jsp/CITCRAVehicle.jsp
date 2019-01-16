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
<script type="text/javascript">
function pageSubmit(){
	alert('Vehicle Record Submit');
	window.location='././viewCITCRAVehicle';
} 
</script>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="./js/jquery.validate.min.js"></script>

<title> CIT/CRA Vehicle</title>

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
	data-genuitec-file-id="wc1-9"
	data-genuitec-path="/Currency/src/main/webapp/jsp/CITCRAVehicle.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-9"
		data-genuitec-path="/Currency/src/main/webapp/jsp/CITCRAVehicle.jsp">
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
						<!-- <div class="panel-heading">Add Vehicle</div> -->

						<div class="panel-heading">
							<ul>
								<li><a href="././viewCITCRAVehicle"><i
										class="fa fa-table fa-fw"></i> View List of CIT/CRA Vehicle's</a>
								</li>
							</ul>
							Add Vehicle
						</div>

						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<!--<form role="form">-->
									<form:form id="userPage" name="userPage"
										action="addCITCRAVehicle" method="post" modelAttribute="user"
										enctype="multipart/form-data" autocomplete="off">
										<div align="center" style="color: red">
											<b>${errorMsg}</b>
										</div>
										<div class="form-group">
											<label>Vendor Name</label>
											<form:select id="name" name="name" path="name"
												cssClass="form-control">
												<form:option value="">Select Vendor Name</form:option>
												<form:options items="${records}" itemValue="name"
													itemLabel="name" />
											</form:select>
										</div>

										<div class="form-group">
											<label>Vehicle Number</label>
											<form:input path="number" id="number" name="number"
												maxlength="45" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Bought Date</label>
											<form:input path="boughtDate" id="boughtDate"
												name="boughtDate" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Registration City</label>
											<form:input path="regCityName" id="regCityName"
												name="regCityName" maxlength="45" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Vehicle Insurance</label>
											<form:input path="insurance" id="insurance" name="insurance"
												maxlength="45" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Fitness Expiry Date</label>
											<form:input path="fitnessExpiryDate" id="fitnessExpiryDate"
												name="fitnessExpiryDate" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Pollution Expiry Date</label>
											<form:input path="pollutionExpiryDate"
												id="pollutionExpiryDate" name="pollutionExpiryDate"
												cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Permit Date</label>
											<form:input path="permitDate" id="permitDate"
												name="permitDate" cssClass="form-control" />
										</div>

										<div class="col-lg-6 form-group">
											<label>Choose File</label> <input type="file" id="file"
												name="file" Class="form-control" />
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

	<script src="./resources/js/jquery.datetimepicker.js"></script>
	<script>
		$('#pollutionExpiryDate').datetimepicker({
			format : 'Y-m-d',

		});
		$('#fitnessExpiryDate').datetimepicker({
			format : 'Y-m-d',

		});
		$('#permitDate').datetimepicker({
			format : 'Y-m-d',

		});
		$('#boughtDate').datetimepicker({
			format : 'Y-m-d',

		});
		
	</script>


	<script type="text/javascript">
	$.validator.addMethod("loginRegexVehNum", function(value, element) {
	    return this.optional(element) || /^[A-Za-z0-9\s]+$/i.test(value);
	}, "Software must contain only letters,Space , dashes.");

	$.validator.addMethod("loginRegexCity", function(value, element) {
	    return this.optional(element) || /^[A-Za-z\s]+$/i.test(value);
	}, "Software must contain only letters,Space , dashes.");

	
	$(function() {
	  $("form[name='userPage']").validate({
	    rules: {
	    	name: {
	    		required:function(element){
	    			var fileValue = $('#file').val();
	    			if(fileValue == ''){
	    				return true;
	    			}else{
	    				return false;
	    			}
	    		}
	    	},
            number: {
            	loginRegexVehNum:true,
            	maxlength:44,
	    		required:function(element){
	    			var fileValue = $('#file').val();
	    			if(fileValue == ''){
	    				return true;
	    			}else{
	    				return false;
	    			}
	    		}
	    	},
            boughtDate: {
	    		required:function(element){
	    			var fileValue = $('#file').val();
	    			if(fileValue == ''){
	    				return true;
	    			}else{
	    				return false;
	    			}
	    		}
	    	},
            regCityName: {
            	loginRegexCity:true,
            	maxlength:44,
	    		required:function(element){
	    			var fileValue = $('#file').val();
	    			if(fileValue == ''){
	    				return true;
	    			}else{
	    				return false;
	    			}
	    		}
	    	},
            insurance: {
            	maxlength:44,
	    		required:function(element){
	    			var fileValue = $('#file').val();
	    			if(fileValue == ''){
	    				return true;
	    			}else{
	    				return false;
	    			}
	    		}
	    	},
            fitnessExpiryDate: {
	    		required:function(element){
	    			var fileValue = $('#file').val();
	    			if(fileValue == ''){
	    				return true;
	    			}else{
	    				return false;
	    			}
	    		}
	    	},
            pollutionExpiryDate: {
	    		required:function(element){
	    			var fileValue = $('#file').val();
	    			if(fileValue == ''){
	    				return true;
	    			}else{
	    				return false;
	    			}
	    		}
	    	},
            permitDate: {
	    		required:function(element){
	    			var fileValue = $('#file').val();
	    			if(fileValue == ''){
	    				return true;
	    			}else{
	    				return false;
	    			}
	    		}
	    	},
	    },
	    // Specify validation error messages
	    messages: {
	    	name: "Please Select Vendor Name",
	    	number: {
	    		loginRegexVehNum:"Only Character And Numerical Value Allow",
	    		required:"Please Enter Vehicle Number",
	    		maxlength:"Vehicle Number can't have more than 45 characters",
	    	},
	    	boughtDate: "Please Provide Bought Date",
	    	regCityName: {
	    		loginRegexCity:"Only Character Value Allow",
	    		required:"Please Enter Registration City",
	    		maxlength:"Registration City Name can't have more than 45 characters",
	    	},
	    	insurance: {
	    		required:"Please Enter Vehicle Insurance ",
	    		maxlength:"Insurance can't have more than 45 characters",
	    	},
	    	fitnessExpiryDate: "Please Provide Expiry Date",
	    	pollutionExpiryDate: "Please Provide Pollution Expiry Date",
	    	permitDate:"Please Provide Permit Date",
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