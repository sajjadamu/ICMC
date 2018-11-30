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

<title>ICICI : Edit RBI Master</title>


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
<script type="text/javascript">

function doAjaxForRegion() {
	addHeaderJson();
	var zone = $('#zone').val();
	$.ajax({
		type : "POST",
		url : "././getRegion",
		data : "zone=" + zone,
		success : function(response) {
			var newStr = response.toString();
	        var data = newStr.split(",");
			var option ;//= '<option value="">Select Region</option>';
			for(i=0;i<data.length;i++)
				{
				option+='<option value="'+data[i].trim()+'">'+data[i].trim()+'</option>';
				}
			$('#region').html(option);
			
		},
		error : function(e) {
			alert('Region Error: ' + e);
		}
	});
}

</script>
<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-128"
	data-genuitec-path="/Currency/src/main/webapp/jsp/editRbiMaster.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-128"
		data-genuitec-path="/Currency/src/main/webapp/jsp/editRbiMaster.jsp">
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
								<li><sec:authorize access="hasRole('VIEW_RBI_MASTER')">
										<a href="././viewRbiMaster"><i class="fa fa-table fa-fw"></i>
											View RBI Master List</a>
									</sec:authorize></li>
							</ul>
							Edit RBI Master
						</div>

						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<!--<form role="form">-->

									<form:form id="rbiMaster" name="rbiMaster"
										action="updateRbiMaster" method="post"
										modelAttribute="rbiMaster" autocomplete="off">
										<form:hidden path="id" />

										<div class="form-group">
											<label>RBI Name</label>
											<form:input path="rbiname" id="rbiname" name="rbiname"
												maxlength="45" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Zone</label>
											<form:select path="zone" id="zone" name="zone"
												cssClass="form-control" onchange="doAjaxForRegion()">
												<option value="Select" label="Select Zone"></option>
												<form:options items="${zoneList}" />
											</form:select>
										</div>

										<div class="form-group">
											<label>Region</label>
											<form:select path="region" id="region" name="region"
												cssClass="form-control">
												<form:options items="${regionList}" itemValue="region"
													itemLabel="region" />
											</form:select>
										</div>

										<div class="form-group">
											<label>Address</label><br>
											<form:input path="address" id="address" name="address"
												maxlength="80" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>City</label><br>
											<form:input path="city" id="city" name="city" maxlength="45"
												cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>State</label>
											<form:select path="state" itemValue="state"
												cssClass="form-control">
												<form:option value="">Select State </form:option>
												<form:options items="${stateList}" />
											</form:select>
										</div>

										<div class="form-group">
											<label>Pin Code</label><br>
											<form:input path="pinno" id="pinno" name="pinno"
												maxlength="6" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Status</label>
											<form:select path="status" id="status" name="status"
												maxlength="45" cssClass="form-control">
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

	
	$.validator.addMethod("nameRegex", function(value, element) {
	    return this.optional(element) || /^[A-Za-z\s]+$/i.test(value);
	}, "Software must contain only letters,Space , dashes.");

	$.validator.addMethod("loginRegexAddress", function(value, element) {
	    return this.optional(element) || /^[0-9A-Za-z\s\(\)\-\,\/\.]+$/i.test(value);
	}, "Software must contain only letters,Space , dashes.");

	$.validator.addMethod("loginRegex", function(value, element) {
	    return this.optional(element) || /^[0-9]+$/i.test(value);
	}, "Software must contain only letters,Space , dashes.");

	
	$(function() {
	  $("form[name='rbiMaster']").validate({
	    rules: {
	    	rbiname: {
            	required:true,
            	maxlength:45,
            	nameRegex:true,
            },
            zone: {
            	//nameRegex:true,
            	required:true,
            	maxlength:45,
            },
            region: {
            	//nameRegex:true,
            	required:true,
            	maxlength:45,
            },
            address: {
            	 loginRegexAddress:true,
         		required:true,
            	maxlength:80,
            },
            city: {
                required: true,
                maxlength:45,
                nameRegex:true
            },
            state: {
            	//mailRegex:true,
         		required:true,
            	maxlength:45,
            	
            },
            pinno: {
            	loginRegex:true,
         		required:true,
            	maxlength:6,
            	
            },
           
	    },
	    messages: {
	    	 rbiname: {
	    		required:"Please Enter RBI Name",
	    		maxlength:"RBI Name can't have more than 45 characters",
	    		nameRegex:"Special and Numeric characters are not allowed",
	    	},
             region: {
            	 nameRegex:"Special and Numeric characters are not allowed",
	    		required:"Please Enter Region",
	    		maxlength:"Region can't have more than 45 characters",
	    	},
             state: {
            	 //mailRegex:"Not a Valid Email ID, Only Accepts the type xxxxxx@icicibank.com",
            	 required:"Please Enter State",
            	 maxlength:"State can't have more than 45 characters",
            	
             },
             address: {
            	 loginRegexAddress:"This special character is not allowed",
            	 required:"Please Enter Address",
            	 maxlength:"Address can't have more than 80 characters",
             },
             pinno: {
            	 loginRegex:"Please enter valid pincode",
            	 required:"Please Enter Pin No",
            	 maxlength:"Pin No can't have more than 6 characters",
             },
             city: {
     	        required: "Please Enter City",
     	       maxlength:"City can't have more than 45 characters",
     	       nameRegex:"Special and Numeric characters are not allowed",
     	     },
	    },
	    submitHandler: function(form) {
	    	var isValid = $("form[name='userPage']").validate().form();
	      		form.submit();
	    }
	  });
	});
	</script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>