<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
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

<script type="text/javascript" src="./js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="./js/jquery.validate.min.js"></script>

<title> Re-PrintQR</title>


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

<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-176"
	data-genuitec-path="/Currency/src/main/webapp/jsp/rePrintQR.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-176"
		data-genuitec-path="/Currency/src/main/webapp/jsp/rePrintQR.jsp">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<ul>
								<li><a href="././viewProcess"><i
										class="fa fa-table fa-fw"></i> Back</a></li>
							</ul>
							Re-Print QR
						</div>

						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<!--<form role="form">-->

									<form:form id="rePrintQR" name="rePrintQR" action="printingQR"
										method="post" modelAttribute="user" autocomplete="off">

										<div class="form-group">
											<label>Currency Type</label>
											<form:input path="currencyType" id="currencyType"
												name="currencyType" cssClass="form-control" readonly="true" />
										</div>

										<div class="form-group">
											<label>Denomination</label>
											<form:input path="denomination" id="denomination"
												name="denomination" cssClass="form-control" readonly="true" />
										</div>

										<div class="form-group">
											<label>Bundle</label>
											<form:input path="bundle" id="bundle" name="bundle"
												maxlength="4" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>BIN/BOX Name</label>
											<form:input path="binNumber" id="binNumber" name="binNumber"
												cssClass="form-control" readonly="true" />
										</div>

										<button type="submit" class="btn btn-lg btn-success btn-block"
											value="Details">Print QR</button>
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
		  $("form[name='rePrintQR']").validate({
		    rules: {
	            bundle: {
	            	required:true,
	            	maxlength:4,
	            },
		    },
		    // Specify validation error messages
		    messages: {
		    	bundle:{
	            	required:"Please Enter Bundle",
	            	maxlength:"Number of bundles can't be 9999",
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