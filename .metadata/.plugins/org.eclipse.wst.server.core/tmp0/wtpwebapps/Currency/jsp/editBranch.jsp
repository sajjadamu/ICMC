<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="./resources/logo/favicon.ico"
	type="image/x-icon">
<title>ICICI : Update Branch</title>

<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="./js/jquery.validate.min.js"></script>

<script type="text/javascript">
function doAjaxForZoneAndRegion() {
	addHeader();
	var rbiName = $('#rbiName').val();
	$.ajax({
		type : "POST",
		url : "././getZoneAndRegion",
		data : "rbiName=" + rbiName,
		
		success : function(response) {
	        var zone = response[0].zone;
	        var region = response[0].region;
	        $('#zone').val(zone);
	        $('#zoneHidden').val(zone);
	        doAjaxForRegion(region);
		},
		error : function(e) {
			alert('Region Error: ' + e);
		}
	});
}
</script>

<script type="text/javascript">
function doAjaxForRegion(region) {
	addHeader();
	var zone = $('#zoneHidden').val();
	if(zone != 'error'){
	$.ajax({
		type : "POST",
		url : "././getRegion",
		data : "zone=" + zone,
		success : function(response) {
			var newStr = response.toString();
	        var data = newStr.split(",");
			var option = '<option value="">Select Region</option>';
			for(i=0;i<data.length;i++)
				{
				option+='<option value="'+data[i]+'">'+data[i]+'</option>';
				}
			$('#region').html(option);
			if(region){
				$('#region').val(region);
				$('#regionHidden').val(region);
			}
		},
		error : function(e) {
			alert('Region Error: ' + e);
		}
	});
}else{
	$('#region').html('<option value="">Select Region</option>');
}
}
</script>

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
	data-genuitec-file-id="wc1-100"
	data-genuitec-path="/Currency/src/main/webapp/jsp/editBranch.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-100"
		data-genuitec-path="/Currency/src/main/webapp/jsp/editBranch.jsp">
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
						<div class="panel-heading">Edit Branch</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<!--<form role="form">-->
									<form:form id="userPage" name="userPage" action="updateBranch"
										method="post" modelAttribute="user" autocomplete="off">

										<form:hidden path="id" />

										<div class="form-group">
											<label>Sol Id</label>
											<form:input path="solId" id="solId" name="solId"
												minlength="4" maxlength="4" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Branch Name</label>
											<form:input path="branch" id="branch" name="branch"
												readonly="true" maxlength="45" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Servicing ICMC (SI)</label>
											<form:select path="servicingICMC" id="servicingICMC"
												name="servicingICMC" maxlength="45" cssClass="form-control">
												<form:option value="">Select Servicing ICMC Name</form:option>
												<form:options items="${icmcList}" itemValue="name"
													itemLabel="name" />
											</form:select>
										</div>

										<div class="form-group">
											<label>Jurisdiction ICMC (JI)</label>
											<form:select path="jurisdictionICMC" id="jurisdictionICMC"
												name="jurisdictionICMC" maxlength="45"
												cssClass="form-control">
												<form:option value="">Select Jurisdiction ICMC Name</form:option>
												<form:options items="${icmcList}" itemValue="name"
													itemLabel="name" />
											</form:select>
										</div>

										<div class="form-group">
											<label>RBI Name</label>
											<form:select path="rbiName" id="rbiName" name="rbiName"
												onchange="doAjaxForZoneAndRegion()" maxlength="45"
												cssClass="form-control">
												<form:option value="">Select RBI Name</form:option>
												<form:options items="${rbiNameList}" itemValue="rbiname"
													itemLabel="rbiname" />
											</form:select>
										</div>

										<form:hidden path="zoneHidden" id="zoneHidden"
											name="zoneHidden" />
										<form:hidden path="regionHidden" id="regionHidden"
											name="regionHidden" />

										<div class="form-group">
											<label>Zone</label>
											<form:select path="zone" id="zone" name="zone"
												onchange="doAjaxForRegion()" maxlength="45" disabled="true"
												cssClass="form-control">
												<form:option value="">Select Zone</form:option>
												<form:options items="${zoneList}" />
											</form:select>
										</div>

										<div class="form-group">
											<label>Region</label>
											<form:select path="region" id="region" name="region"
												maxlength="45" disabled="true" cssClass="form-control">
												<form:option value="">Select Region</form:option>
												<form:options items="${regionList}" itemValue="region"
													itemLabel="region" />
											</form:select>
										</div>

										<div class="form-group">
											<label>Address</label>
											<form:input path="address" id="address" name="address"
												maxlength="45" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>City</label>
											<form:input path="city" id="city" name="city" maxlength="45"
												cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Pin Code</label>
											<form:input path="pincode" id="pincode" name="pincode"
												maxlength="6" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Status</label>
											<form:select path="status" id="status" name="status"
												cssClass="form-control">
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
	
	$.validator.addMethod("loginRegex", function(value, element) {
			    return this.optional(element) || /^[0-9]+$/i.test(value);
			}, "Software must contain only letters,Space , dashes.");

	$.validator.addMethod("loginRegexCity", function(value, element) {
	    return this.optional(element) || /^[A-Za-z\s]+$/i.test(value);
	}, "Software must contain only letters,Space , dashes.");

	$.validator.addMethod("loginRegexAddress", function(value, element) {
	    return this.optional(element) || /^[0-9A-Za-z\s\(\)\-\,\/\.]+$/i.test(value);
	}, "Software must contain only letters,Space , dashes.");

	
	$(function() {
	  $("form[name='userPage']").validate({
	    rules: {
	    	solId: {
            	loginRegex:true,
            	minlength:4,
            	maxlength:4,
            	required:function(element)
	    		{
	    			var fileValue = $('#file').val();
	    			if (fileValue==''){
	    		           return true; 
	    		      }else{
	    				 return false; 
	    			}
	    		}
            },
	    	branch: {
	    		loginRegexAddress:true,
	    		maxlength:44,
	    		required:function(element)
	    		{
	    			var fileValue = $('#file').val();
	    			if (fileValue==''){
	    		           return true; 
	    		    }else{
	    				return false; 
	    			}
	    		}
	    	},
	    	servicingICMC: {
	    		required:function(element)
	    		{
	    			var fileValue = $('#file').val();
	    			if (fileValue==''){
	    		        return true; 
	    		    }else{
	    				return false; 
	    			}
	    		}
	    	},
	    	jurisdictionICMC: {
	    		loginRegexAddress:true,
	    		maxlength:44,
	    		required:function(element)
	    		{
	    			var fileValue = $('#file').val();
	    			if (fileValue==''){
	    		         return true; 
	    		    }else{
	    				return false; 
	    			}
	    		}
	    	},
	    	rbiName: {
	    		required:function(element)
	    		{
	    			var fileValue = $('#file').val();
	    			if (fileValue==''){
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
            address:{
            	loginRegexAddress:true,
            	maxlength:44,
	    		required:function(element)
	    		{
	    			var fileValue = $('#file').val();
	    			if (fileValue==''){
	    		         return true; 
	    		    }else{
	    				return false; 
	    			}
	    		}
	    	},
            city: {
            	loginRegexCity:true,
            	maxlength:44,
	    		required:function(element)
	    		{
	    			var fileValue = $('#file').val();
	    			if (fileValue==''){
	    		         return true; 
	    		    }else{
	    				return false; 
	    			}
	    		}
	    	},
            pincode:{
            	required:true,
            	loginRegex:true,
            	minlength:6,
            	maxlength:6,
	    		required:function(element)
	    		{
	    			var fileValue = $('#file').val();
	    			if (fileValue==''){
	    		         return true; 
	    		    }else{
	    				return false; 
	    			}
	    		}
	    	},
	    },
	    // Specify validation error messages
	    messages: {
	    	solId: {
           	 required:"Please enter Sol ID",
           	 loginRegex:"Please enter valid Sol ID",
           	 minlength:"Sol ID should have minimum 4 digits",
           	 maxlength:"Sol ID can't have more than 4 digits",
            },
	    	branch: {
	    		loginRegexAddress:"This special character is not allowed",
	    		required:"Please enter branch name",
	    		maxlength:"Branch name can't have more than 45 characters",
	    	 },
	    	 servicingICMC: "Please select servicing ICMC name",
	    	 jurisdictionICMC: {
		    		loginRegexAddress:"This special sharacter is not allowed",
		    		required:"Please enter jurisdiction ICMC name",
		    		maxlength:"Jurisdiction ICMC Name can't have more than 45 characters",
		    },
		  	rbiName: "Please select RBI name",
            zone: "Please select Zone",
            region: "Please select Region",
            address: {
            	loginRegexAddress:"This special character is not allowed",
	    		required:"Please enter address",
	    		maxlength:"Address can't have more than 45 characters",
	    	},
            city: {
            	loginRegexCity:"Please enter only characters",
	    		required:"Please enter city",
	    		maxlength:"City can't have more than 45 characters",
	    	},
            pincode: {
            	 required:"Please enter pincode",
            	 loginRegex:"Please enter valid pincode",
            	 minlength:"PIN Code should have minimum 6 digits",
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