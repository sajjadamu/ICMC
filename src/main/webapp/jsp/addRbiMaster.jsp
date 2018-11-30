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

<title>ICICI : Add/Upload RBI Master</title>


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
			var option;
			for(i=0;i<data.length;i++)
				{
				option+='<option value="'+data[i].trim()+'" >'+data[i].trim()+'</option>';
				}
			$('#region').html(option);
			
		},
		error : function(e) {
			alert('Region Error: ' + e);
		}
	});
}

</script>
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
								<li><sec:authorize access="hasRole('VIEW_RBI_MASTER')">
										<a href="././viewRbiMaster"><i class="fa fa-table fa-fw"></i>
											View RBI Master List</a>
									</sec:authorize></li>
							</ul>
							Add/Upload RBI Master
						</div>

						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<!--<form role="form">-->

									<form:form id="rbiMaster" name="rbiMaster"
										action="saveRbiMaster" method="post"
										modelAttribute="rbiMaster" enctype="multipart/form-data"
										autocomplete="off">

										<div align="center" style="color: red">
											<b>${duplicateName}</b>
										</div>
										<div align="center" style="color: red">
											<b>${errorMsg}</b>
										</div>
										<div class="form-group">
											<label>RBI Name</label>
											<form:input path="rbiname" id="rbiname" name="rbiname"
												maxlength="32" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Zone</label>
											<form:select path="zone" itemValue="zone"
												cssClass="form-control" onchange="doAjaxForRegion()">
												<form:option value="">Select Zone </form:option>
												<form:options items="${zoneList}" />
											</form:select>
										</div>

										<div class="form-group">
											<label>Region</label>
											<form:select path="region" id="region" name="region"
												cssClass="form-control">
												<form:option value="">Select Region</form:option>
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
												<form:option value="">Select State</form:option>
												<form:options items="${stateList}" />
											</form:select>
										</div>

										<div class="form-group">
											<label>Pin Code</label>
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

										<div class="col-lg-6 form-group">
											<label>Choose File</label> <input type="file" id="file"
												name="file" Class="form-control" /> <a
												href="${documentFilePath}/RBIMaster.csv" download>Download
												CSV Format</a>
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

$.validator.addMethod("nameRegex", function(value, element) {
    return this.optional(element) || /^[A-Za-z\s]+$/i.test(value);
}, "Software must contain only letters,Space , dashes.");

$.validator.addMethod("loginRegex", function(value, element) {
    return this.optional(element) || /^[0-9]+$/i.test(value);
}, "Software must contain only letters,Space , dashes.");

$.validator.addMethod("loginRegexCity", function(value, element) {
    return this.optional(element) || /^[A-Za-z\s]+$/i.test(value);
}, "Software must contain only letters,Space , dashes.");

$.validator.addMethod("loginRegexAddress", function(value, element) {
    return this.optional(element) || /^[0-9A-Za-z\s\(\)\-\,\/]+$/i.test(value);
}, "Software must contain only letters,Space , dashes.");

	$(function() {
	  $("form[name='rbiMaster']").validate({
	    rules: {
	    	rbiname: {
	    		nameRegex:true,
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
            zone: {
            	required:function(element){
            		var fileValue = $('#file').val();
            		if(fileValue == ''){
            			return true;
            		}else{
            			return false;
            		}
            	}
            },
            region: {
            	required:function(element){
            		var fileValue = $('#file').val();
            		if(fileValue == ''){
            			return true;
            		}else{
            			return false;
            		}
            	}
            },
            address: {
            	loginRegexAddress:true,
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
            city: {
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
            state: {
            	required:function(element){
            		var fileValue = $('#file').val();
            		if(fileValue == ''){
            			return true;
            		}else{
            			return false;
            		}
            	}
            },
            pinno: {
            	loginRegex:true,
            	number:true,
            	minlength:6,
            	maxlength:6,
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
	    	rbiname: {
	    		loginRegexCity:"This special character is not allowed",
	    		required:"Please enter RBI name",
	    		maxlength:"RBI name can't have more than 45 characters",
	    	 },
             zone: "Please select Zone",
             region: "Please select Region",
             address: {
            	loginRegexAddress:"This special character is not allowed",
	    		required:"Please enter Address",
	    		maxlength:"Address can't have more than 45 characters",
	    	 },
             city: {
            	loginRegexCity:"Accepts only characters",
	    		required:"Please enter City",
	    		maxlength:"City can't have more than 45 characters",
	    	 },
	    	 state: "Please select State",
	    	 pinno:{
            	 required:"Please enter Pincode",
            	 loginRegex:"Please enter valid PIN Code number",
            	 minlength:"PIN Code should have 6 digits",
            	 maxlength:"PIN Code can't have more than 6 digits",
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