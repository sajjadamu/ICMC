<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@include file="include.jsp"%>
<html lang="en">

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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

<title> ResetPassword</title>

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
<link rel="stylesheet" type="text/css"
	href="./resources/bower_components/font-awesome/css/font-awesome.min.css">

<link rel="stylesheet" type="text/css"
	href="./resources/dist/css/style.css">


<script src="./resources/bower_components/jquery/dist/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script
	src="./resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script
	src="./resources/bower_components/metisMenu/dist/metisMenu.min.js"></script>

<!-- Custom Theme JavaScript -->
<script src="./resources/dist/js/sb-admin-2.js"></script>
<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-186"
	data-genuitec-path="/Currency/src/main/webapp/jsp/resetPasswordRequest.jsp">
	<div class="container" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-186"
		data-genuitec-path="/Currency/src/main/webapp/jsp/resetPasswordRequest.jsp">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<div class="login-panel panel panel-default">
					<div class="panel-heading signin">
						<img src="./resources/logo/logo.png">
						<!--<h3 class="panel-title">Please Sign In</h3>-->
					</div>
					<div class="panel-body">

						<!--<form role="form">-->
						<form:form id="userPage" name="userPage"
							action="resetPasswordRequest" method="post"
							modelAttribute="passwordReset" autocomplete="off">

							<div align="center" style="color: white; background: green;">${successMsg}</div>
							<div align="center" style="color: white; background: red;">${errorMsg}</div>

							<div class="form-group">
								<Strong>Reset Password Request</Strong>
								<form:input path="userId" id="id" name="id" maxlength="45"
									placeholder="User Id" cssClass="form-control" />
							</div>
							<button type="submit" class="btn btn-lg btn-success btn-block"
								value="Details">Submit</button>
						</form:form>
					</div>
				</div>
				<a href="././login"> Click here for login</a>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="./js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>

</body>
</html>