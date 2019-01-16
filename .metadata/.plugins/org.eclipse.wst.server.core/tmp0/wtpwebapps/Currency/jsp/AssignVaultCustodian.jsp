<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
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

<title> Assign Vault Custodian</title>

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
	data-genuitec-file-id="wc1-7"
	data-genuitec-path="/Currency/src/main/webapp/jsp/AssignVaultCustodian.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-7"
		data-genuitec-path="/Currency/src/main/webapp/jsp/AssignVaultCustodian.jsp">
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
								<li><sec:authorize
										access="hasRole('VIEW_ASSIGN_VAULT_CUSTODIAN')">
										<a href="././viewAssignVaultCustodian"><i
											class="fa fa-table fa-fw"></i> View Vault Custodian Details</a>
									</sec:authorize></li>
							</ul>
							Assign Vault Custodian
						</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<!--<form role="form">-->
									<form:form id="userPage" action="AddAssignVaultCustodian"
										method="post" name="userPage" modelAttribute="user"
										autocomplete="off">

										<div class="form-group">
											<label>User ID Of Taking Over Charge</label>
											<form:input path="userId" maxlength="45" id="userId"
												name="userId" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>User ID Of Handing Over Charge</label>
											<form:input path="handingOverCharge" maxlength="45"
												id="handingOverCharge" name="handingOverCharge"
												cssClass="form-control" />
										</div>

										<div class="form-group">
											<label> Custodian</label>
											<form:select path="custodian" cssClass="form-control">
												<form:option value="">Select Custodian</form:option>
												<form:option value="CUSTODIAN 1">CUSTODIAN 1</form:option>
												<form:option value="CUSTODIAN 2">CUSTODIAN 2</form:option>
											</form:select>
											<%-- <form:input path="keySet" id="keySet" maxlength="45" name="keySet" cssClass="form-control" /> --%>
										</div>

										<div class="form-group">
											<label> Remarks</label>
											<form:input path="remarks" maxlength="45" id="remarks"
												name="remarks" cssClass="form-control" />
										</div>

										<button type="submit" onclick=""
											class="btn btn-lg btn-success btn-block" value="Details">Submit</button>
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

$.validator.addMethod("loginRegexlicNum", function(value, element) {
    return this.optional(element) || /^[0-9]+$/i.test(value);
}, "Software must contain only letters,Space , dashes.");



	$(function() {
	  $("form[name='userPage']").validate({
	    rules: {
	    	userId:{
	    		required:true,
	    	       }, 
	    	
	    	       custodian: {
	    		required:true,
	    	},
	    	
         	    },
	    // Specify validation error messages
	    messages: {
	    	userId: {
	    		required:"Please Enter User Id.",
	    	},
	    	custodian:{
	    		required:"Please Select custodian",
	    	} ,
	    	   
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