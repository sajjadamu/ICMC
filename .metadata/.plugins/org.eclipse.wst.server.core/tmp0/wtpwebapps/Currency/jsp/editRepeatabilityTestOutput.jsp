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

<title> Modify Repeatability Test Output</title>

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
	data-genuitec-file-id="wc1-130"
	data-genuitec-path="/Currency/src/main/webapp/jsp/editRepeatabilityTestOutput.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-130"
		data-genuitec-path="/Currency/src/main/webapp/jsp/editRepeatabilityTestOutput.jsp">
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
						<div class="panel-heading">Modify Repeatability Test Output</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<!--<form role="form">-->
									<div align="center" style="color: red">
										<b>${successMsg}</b>
									</div>
									<form:form id="userPage" name="userPage"
										action="updateRepeatabilityTestOutput" method="post"
										modelAttribute="user" autocomplete="off">

										<form:hidden path="id" />

										<div class="form-group">
											<label>Machine No.</label>
											<form:input path="machineNo" maxlength="45" id="machineNo"
												name="machineNo" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Currency Type</label>
											<form:radiobutton path="currencyType" id="currencyType"
												label="" value="ATM" />
											ATM
											<form:radiobutton path="currencyType" id="currencyType"
												label="" value="SOILED" />
											SOILED
											<form:radiobutton path="currencyType" id="currencyType"
												label="" value="ISSUABLE" />
											ISSUABLE
										</div>

										<div class="form-group">
											<label>Denomination</label>
											<form:radiobuttons path="denomination"
												items="${denominationList}" itemLabel="denomination"
												name="denomination" itemValue="denomination" />
										</div>

										<div class="form-group">
											<label>Bundle</label>
											<form:input path="bundle" id="bundle" maxlength="45"
												name="bundle" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Total Value</label>
											<form:input path="totalValue" id="totalValue" maxlength="45"
												name="totalValue" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Bin No</label>
											<form:input path="binNo" id="binNo" name="binNo"
												maxlength="45" cssClass="form-control" />
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
	$(function() {
	  $("form[name='userPage']").validate({
	    rules: {
	    	machineNo: {
	    		required:true,
	    		rangelength: [0, 44]
	    	},
	    	denomination: "required",
	    	bundle: {
		    	required:true,
		    	number:true,
		    	rangelength: [0, 44]
		    	},
	    	totalValue: {
	    		required:true,
	    		rangelength: [0, 44]
	    	},
	    	currencyType: "required",
	    	binNo:{
	    		required:true,
	    		rangelength: [0, 44]
	    	},
         	    },
	    // Specify validation error messages
	    messages: {
	    	machineNo:{
	    		required: "Please Enter Machine No.",
	    		rangelength: "Machine No. can't have more than  45 character "
	    		
	    	},
	    	denomination: "Please Select Redio Button",
	    	bundle:{
	    		required:"Please Enter the bundle number",
	            number:"only Numeric value ",
	             rangelength: "Bundle can't have more than  45 character"
	    	} ,
	    	totalValue: {
	    		required: "Please Enter Total value",
	    		rangelength: "Total Value. can't have more than  45 character "
	    	},
	    	currencyType: "Please Enter currency Type",
	    	binNo:{
	    		required:  "Please Enter Total Bin No",
	    		rangelength: "Bin No can't have more than  45 character "
	    	},
             
            	    },
	    // Make sure the form is submitted to the destination defined
	    // in the "action" attribute of the form when valid
	    submitHandler: function(form) {
	      form.submit();
	    }
	  });
	});
	</script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>