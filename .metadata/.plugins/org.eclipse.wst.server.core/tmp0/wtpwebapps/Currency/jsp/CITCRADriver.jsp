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

<title> CIT/CRA Driver</title>

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

<script type="text/javascript" charset="utf-8">
     function getVehicleNumber(){
        $.getJSON(
             "vehicleList.json",
             {vendor: $('select#vendorName').val()},
             function(data) {
                  var html = '';
                  var len = data.length;
                  for(var i=0; i<len; i++){
                       html += '<option value="' + data[i].number + '">' + data[i].number + '</option>';
                   }
                  $('select#vehicleNumber').append(html);
             }
          );
 }

</script>

<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>
<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-8"
	data-genuitec-path="/Currency/src/main/webapp/jsp/CITCRADriver.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-8"
		data-genuitec-path="/Currency/src/main/webapp/jsp/CITCRADriver.jsp">
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
								<li><a href="././viewCITCRADriver"><i
										class="fa fa-table fa-fw"></i> View List of CIT/CRA Driver's</a></li>
							</ul>
							Add CIT/CRA Driver
						</div>

						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<!--<form role="form">-->
									<form:form id="userPage" name="userPage"
										action="addCITCRADriver" method="post" modelAttribute="user"
										enctype="multipart/form-data" autocomplete="off">
										<div align="center" style="color: red">
											<b>${errorMsg}</b>
										</div>
										<div class="form-group">
											<label>Vendor Name</label>
											<form:select id="vendorName" name="vendorName"
												path="vendorName" cssClass="form-control"
												onclick="getVehicleNumber()">
												<form:option value="">Select</form:option>
												<form:options items="${records}" itemValue="name"
													itemLabel="name" />

											</form:select>
										</div>

										<div class="form-group">
											<label>Vehicle Number</label>
											<form:select path="vehicleNumber" id="vehicleNumber"
												name="vehicleNumber" cssClass="form-control">
												<form:option value="">Select Number</form:option>
											</form:select>
										</div>

										<div class="form-group">
											<label>Vehicle Driver Name</label>
											<form:input path="driverName" id="driverName"
												name="driverName" maxlength="45" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>License Number</label>
											<form:input path="licenseNumber" id="licenseNumber"
												name="licenseNumber" maxlength="45" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>License Issued State</label>
											<form:input path="licenseIssuedState" id="licenseIssuedState"
												name="licenseIssuedState" maxlength="45"
												cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>License Issued Date</label>
											<form:input path="licenseIssuedDated" id="licenseIssuedDated"
												name="licenseIssuedDated" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>License Expiry Date</label>
											<form:input path="licenseExpiryDate" id="licenseExpiryDate"
												name="licenseExpiryDate" cssClass="form-control" />
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
		$('#licenseIssuedDated').datetimepicker({
			format : 'Y-m-d',

		});
		$('#licenseExpiryDate').datetimepicker({
			format : 'Y-m-d',

		});
		
	</script>


	<script type="text/javascript">
	$.validator.addMethod("loginRegexName", function(value, element) {
	    return this.optional(element) || /^[A-Za-z\s]+$/i.test(value);
	}, "Software must contain only letters,Space , dashes.");

	$.validator.addMethod("loginRegexlicNum", function(value, element) {
	    return this.optional(element) || /^[A-Za-z0-9\s]+$/i.test(value);
	}, "Software must contain only letters,Space , dashes.");

	
	$(function() {
	  $("form[name='userPage']").validate({
	    rules: {
	    	vendorName: {
	    		
	    		required:function(element){
	    			var fileValue = $('#file').val();
	    			if(fileValue == ''){
	    				return true;
	    			}else{
	    				return false;
	    			}
	    		}
	    	},
	    	vehicleNumber: {
	    		required:function(element){
	    			var fileValue = $('#file').val();
	    			if(fileValue == ''){
	    				return true;
	    			}else{
	    				return false;
	    			}
	    		}
	    	},
	    	driverName: {
	    		loginRegexName:true,
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
	    	licenseNumber: {
	    		loginRegexlicNum:true,
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
	    	licenseIssuedState: {
	    		loginRegexName:true,
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
	    	licenseIssuedDated: {
	    		required:function(element){
	    			var fileValue = $('#file').val();
	    			if(fileValue == ''){
	    				return true;
	    			}else{
	    				return false;
	    			}
	    		}
	    	},
	    	licenseExpiryDate: {
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
	    	vendorName: "Please Select Vendor Name",
            vehicleNumber: "Please Select Vehicle Number",
            driverName: {
            	loginRegexName:"Only Character Value Accepted",
	    		required:"Please Enter Driver Name",
	    		maxlength:"Driver Name can't have more than 45 characters",
	    	},
            licenseNumber: {
            	loginRegexlicNum:"Only Character Or Numeric Value Accepted",
	    		required:"Please Enter License Number",
	    		maxlength:"License Number can't have more than 45 characters",
	    	},
            licenseIssuedState: {
            	loginRegexName:"Only Character Value Accepted",
	    		required:"Please Specify License Issued State",
	    		maxlength:"License Issued State Name can't have more than 45 characters",
	    	},
            licenseIssuedDated: "Please Specify License Issued Date",
            licenseExpiryDate: "Please Specify License Expiry Date",
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