<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">
	<link rel="shortcut icon" href="./resources/logo/favicon.ico" type="image/x-icon">
	<script type="text/javascript" src="./js/jquery.validate.min.js"></script>
	
	<title>ICICI : Add Printer</title>
	
	<!-- Bootstrap Core CSS -->
	<link href="./resources/bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
	
	<!-- MetisMenu CSS -->
	<link href="./resources/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">
	
	<!-- Custom CSS -->
	<link href="./resources/dist/css/sb-admin-2.css" rel="stylesheet">
	
	<!-- Custom Fonts -->
	<link href="./resources/bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
	
	<link rel="stylesheet" type="text/css" href="./resources/dist/css/style.css">
<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script></head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-56" data-genuitec-path="/Currency/src/main/webapp/jsp/addPrinter.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-56" data-genuitec-path="/Currency/src/main/webapp/jsp/addPrinter.jsp">
		<!-- Navigation -->
		<jsp:include page="common.jsp" /> 
		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<ul>
								<li><a href="././viewPrinter"><i class="fa fa-table fa-fw"></i> View Printer's List</a></li>
							</ul>Add New Printer
						</div>
						
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<!--<form role="form">-->
									<form:form id="userPage" name="userPage" action="savePrinter" method="post"
										modelAttribute="icmcPrinter" enctype="multipart/form-data" autocomplete="off">
										
										<div align="center" style="color: red"><b>${duplicatePrinter}</b></div>
										 
										<div class="form-group">
											<label>Printer Name</label>
											<form:input path="printerName" id="printerName" name="printerName" maxlength="45" cssClass="form-control" />
										</div>
										
										<div class="form-group">
											<label>Printer IP</label>
											<form:input path="printerIP" id="printerIP" name="printerIP" maxlength="16" cssClass="form-control" />
										</div>
										
										<div class="form-group">
											<label>Port</label>
											<form:input path="port" id="port" name="port" maxlength="4" cssClass="form-control" />
										</div>
										
										<div class="form-group">
											<label>ICMC Name</label>
											<form:select path="icmcId" id="icmcId" name="icmcId" cssClass="form-control">
												<form:option value="">Select ICMC</form:option>
												<form:options items="${icmcList}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
										
										<div class="form-group">
											<label>Status</label>
											<form:select path="status" id="status" name="status" cssClass="form-control">
											 	<option value="" label="Select Status"></option>
    											<form:options items="${statusList}" />
											</form:select>
										</div>
										
										<button type="submit" class="btn btn-lg btn-success btn-block" value="Details">Save</button>
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
	<script src="./resources/bower_components/jquery/dist/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="./resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script src="./resources/bower_components/metisMenu/dist/metisMenu.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="./resources/dist/js/sb-admin-2.js"></script>

<script type="text/javascript">

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
	  $("form[name='userPage']").validate({
	    rules: {
	    	name: {
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
	    	linkBranchSolId: {
	    		loginRegex:true,
	    		minlength:4,
	    		maxlength:4,
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
            pincode: {
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
	    	name: {
	    		loginRegexCity:"This special character is not allowed",
	    		required:"Please enter ICMC name",
	    		maxlength:"ICMC name can't have more than 45 characters",
	    	},
	    	linkBranchSolId: {
	    		login:"Please enter only digits",
	    		required:"Please enter Link Branch Sol ID",
	    		minlength:"Link Branch Sol ID should have min 4 digits",
	    		maxlength:"Link Branch Sol ID can't have more than 4 digits",
	    	 },
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
             pincode:{
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