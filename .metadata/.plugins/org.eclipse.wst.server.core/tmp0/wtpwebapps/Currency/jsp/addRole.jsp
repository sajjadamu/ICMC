<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:htmlEscape defaultHtmlEscape="true" />
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

<script type="text/javascript" src="./js/crossSite.js"></script>

<title>ICICI : Add Role</title>

<style type="text/css">
.form-group.rolewrap {
	padding: 10px;
}

.rolewrap span {
	width: 30%;
	display: inline-block;
	vertical-align: top;
	font-size: 12px;
}

.panel-default>label {
	padding: 10px;
	border-bottom: #ccc 1px solid;
	display: block;
	background: #f1f1f1;
}
</style>
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
	data-genuitec-file-id="wc1-58"
	data-genuitec-path="/Currency/src/main/webapp/jsp/addRole.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-58"
		data-genuitec-path="/Currency/src/main/webapp/jsp/addRole.jsp">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">

						<div class="panel-heading">
							<ul>
								<li><sec:authorize access="hasRole('VIEW_ROLE')">
										<a href="././viewRole"><i class="fa fa-table fa-fw"></i>
											View Roles List</a>
									</sec:authorize></li>
							</ul>
							Add New Role
						</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-12">
									<!--<form role="form">-->

									<form:form id="userPage" name="userPage" action="saveRole"
										method="post" modelAttribute="role" autocomplete="off">

										<div align="center" style="color: red">
											<b>${duplicateRole}</b>
										</div>

										<div class="form-group">
											<label>New Role</label>
											<form:input path="id" id="id" name="id" maxlength="32"
												cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>ICMC Access</label><br>
											<form:radiobuttons items="${icmcAccessList}"
												path="icmcAccess" />
										</div>

										<div class="form-group">
											<label>Function Permissions</label><br>
										</div>

										<div class="panel panel-default">
											<label>Administration Module</label>
											<div class="form-group rolewrap">
												<form:checkboxes items="${ADMIN}" path="rolePermission" />
											</div>
										</div>

										<div class="panel panel-default">
											<label>Bin DashBoard Module</label>
											<div class="form-group rolewrap">
												<form:checkboxes items="${BIN_DASHBOARD}"
													path="rolePermission" />
											</div>
										</div>

										<div class="panel panel-default">
											<label>Vault Management Module</label>
											<div class="form-group rolewrap">
												<form:checkboxes items="${VAULT_MANAGEMENT}"
													path="rolePermission" />
											</div>
										</div>

										<div class="panel panel-default">
											<label>CIT/CRA Details Module</label>
											<div class="form-group rolewrap">
												<form:checkboxes items="${CIT_CRA_DETAILS}"
													path="rolePermission" />
											</div>
										</div>

										<div class="panel panel-default">
											<label>Cash Receipt Module</label>
											<div class="form-group rolewrap">
												<form:checkboxes items="${CASH_RECEIPT}"
													path="rolePermission" />
											</div>
										</div>

										<div class="panel panel-default">
											<label>Cash Payment Module</label>
											<div class="form-group rolewrap">
												<form:checkboxes items="${CASH_PAYMENT}"
													path="rolePermission" />
											</div>
										</div>

										<div class="panel panel-default">
											<label>Processing Room Module</label>
											<div class="form-group rolewrap">
												<form:checkboxes items="${PROCESSING_ROOM}"
													path="rolePermission" />
											</div>
										</div>

										<div class="panel panel-default">
											<label>Fake Note Management Module</label>
											<div class="form-group rolewrap">
												<form:checkboxes items="${FAKE_NOTE_MANAGEMENT}"
													path="rolePermission" />
											</div>
										</div>

										<div class="panel panel-default">
											<label>Reports Module</label>
											<div class="form-group rolewrap">
												<form:checkboxes items="${REPORTS}" path="rolePermission" />
											</div>
										</div>

										<div class="form-group">
											<label>Status</label>
											<form:select path="status" id="status" name="status"
												cssClass="form-control">
												<form:options items="${statusList}" />
											</form:select>
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
		    	id: {
	            	required:true,
	            	maxlength:31,
	            },
	            icmcAccess: {
	            	required:true,
	            },
	            rolePermission: {
	            	required:true,
	            },
		    },
		    // Specify validation error messages
		    messages: {
		    	 id: {
		    		required:"Please Enter New Role",
		    		maxlength:"User ID can't have more than 32 characters",
		    	},
		    	icmcAccess: {
		    		required:"Please select Icmc Access",
		    	},
		    	rolePermission: {
		    		required:"Please select  at least one check box",
		    	},
		    	
	           
		    },
		    submitHandler: function(form) {
		    	var isValid = $("form[name='userPage']").validate().form();
		    	//alert(isValid);
		    	if(isValid){
		      		form.submit();
		    	}
		    }
		  });
		});
	
	</script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>