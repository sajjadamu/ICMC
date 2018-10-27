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
<link rel="shortcut icon" href="./resources/logo/favicon.ico"
	type="image/x-icon">

<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="./js/jquery.validate.min.js"></script>

<title>ICICI : Edit Machine Company</title>

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
</head>

<body oncontextmenu="return false;">
	<div id="wrapper">
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
								<li><sec:authorize access="hasRole('VIEW_MACHINE_COMPANY')">
										<a href="././viewMachineCompany"><i
											class="fa fa-table fa-fw"></i> View Machine Company List</a>
									</sec:authorize></li>
							</ul>
							Edit Machine Company
						</div>

						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<!--<form role="form">-->

									<form:form id="rbiMaster" name="rbiMaster"
										action="updateMachineCompany" method="post"
										modelAttribute="user" autocomplete="off">
										<form:hidden path="id" />
										<%--  	<div align="center" style="color: red"><b>${duplicateUser}</b></div> --%>

										<div class="form-group">
											<label>Make Company</label>
											<form:input path="companyname" id="companyname"
												name="companyname" maxlength="32" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Email</label>
											<form:input path="email" id="email" name="email"
												maxlength="32" cssClass="form-control" />

										</div>

										<div class="form-group">
											<label>Phone No.</label>
											<form:input path="phonenumber" id="phonenumber"
												name="phonenumber" maxlength="11" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Address</label>
											<form:input path="address" id="address" name="address"
												maxlength="45" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Status</label>
											<form:select path="status" id="status" name="status"
												cssClass="form-control">
												<form:options items="${statusList}"></form:options>
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
	<script src="./resources/js/jquery.datetimepicker.js"></script>
	<script>
		$('#purchasedate').datetimepicker({
			format : 'Y-m-d',

		});
		
		
	</script>

	<script src="./resources/dist/js/sb-admin-2.js"></script>
	<script type="text/javascript">

	
	$.validator.addMethod("nameRegex", function(value, element) {
	    return this.optional(element) || /^[A-Za-z\s]+$/i.test(value);
	}, "Software must contain only letters,Space , dashes.");
	
	$.validator.addMethod("Phone", function(value, element) {
	    return this.optional(element) || /^[0-9]+$/i.test(value);
	}, "Software must contain only letters,Space , dashes.");

	$.validator.addMethod("loginRegexAddress", function(value, element) {
	    return this.optional(element) || /^[0-9A-Za-z\s\(\)\-\,\/\.]+$/i.test(value);
	}, "Software must contain only letters,Space , dashes.");

	
	 $(function() {
	  $("form[name='rbiMaster']").validate({
	    rules: {
	    	companyname: {
            	required:true,
            	maxlength:44,
            	nameRegex:true,
            },
            email: {
            	required:true,
            	email:true,
            	maxlength:44,
            },
            purchasedate:{
            	required:true,
            },
            phonenumber: {
            	Phone:true,
            	required:true,
            	maxlength:11,
            },
            address: {
            	loginRegexAddress:true,
         		required:true,
            	maxlength:80,
            },
           
	    },
	    messages: {
	    	companyname: {
	    		required:"Please Enter Name",
	    		maxlength:" Name can't have more than 45 characters",
	    		nameRegex:"Special and Numeric characters are not allowed",
	    	},
	    	email: {
	    		email:"not vaild formate",
            	// Phone:"Special and Alphabetic characters are not allowed",
	    		required:"Please Enter Email",
	    		maxlength:"Region can't have more than 45 characters",
	    	},
	    	purchasedate:{
	    		required:"Please Enter Purchase Date",
	    	},
	    	phonenumber: {
	    		Phone:"Special and Alphabetic characters are not allowed",
            	 required:"Please Enter Phone Number",
            	 maxlength:"State can't have more than 11 characters",
            	
             },
             address: {
            	 loginRegexAddress:"This Special Character is not allowed",
     	        required: "Please Enter Address",
     	       maxlength:"City can't have more than 80 characters",
     	     },
	    },
	    submitHandler: function(form) {
	    	var isValid = $("form[name='rbiMaster']").validate().form();
	      		form.submit();
	    }
	  });
	});
	</script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>