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
<link rel="shortcut icon" href="./resources/logo/favicon.ico" type="image/x-icon">
<title>ICICI : Update ICMC</title>

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="./js/jquery.validate.min.js"></script>

<script type="text/javascript">
function doAjaxForRBINameZoneAndRegion() {
	addHeader();
	var linkBranchSolId = $('#linkBranchSolId').val();
	if(linkBranchSolId.length == 4)
	{
		$.ajax({
			type : "POST",
			url : "././getRBINameZoneAndRegion",
			data : "linkBranchSolId=" + linkBranchSolId,
			
			success : function(response) {
				if(response.length == 0){
					alert("Please enter a valid Sol ID");
					//show error on field that In-Valid Sol ID
					return;
				}
				var rbiName = response[0].rbiName;
		        var zone = response[0].zone;
		        var region = response[0].region;
		        $('#rbiName').val(rbiName);
		        $('#zone').val(zone);
		        $('#rbiNameHidden').val(rbiName);
		        $('#zoneHidden').val(zone);
		        doAjaxForRegion(region);
			},
			error : function(e) {
				alert('Region Error: ' + e);
			}
		});
	}
	
}
</script> 

<script type="text/javascript">
function doAjaxForZoneAndRegion() {
	addHeader();
	var rbiName = $('#rbiName').val();
	$.ajax({
		type : "POST",
		url : "././getZoneAndRegion",
		data : "rbiName=" + rbiName,
		
		success : function(response) {
			var json = JSON.stringify(response);
			var newStr = json.substring(1, json.length - 1); // Remove Array Brackets
			
	        var data = newStr.split(",");
	        var zone = data[2].split(":");
	        var region = data[3].split(":");
	        
			var optionZone;
			var optionRegion;
			for(i=1; i<zone.length; i++)
				{
				optionZone+='<option value="'+zone[i].substring(1, zone[i].length-1)+'">'+zone[i].substring(1, zone[i].length-1)+'</option>';
				//optionZone+='<input type=text value="'+zone[i].substring(1, zone[i].length-1)+'"/>';
				}
			$('#zone').html(optionZone);
			
			for(i=1; i<region.length; i++)
			{
				optionRegion+='<option value="'+region[i].substring(1, region[i].length-1)+'">'+region[i].substring(1, region[i].length-1)+'</option>';
			}
		$('#region').html(optionRegion);
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
	var zone = $('#zone').val();
	
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
						<div class="panel-heading">Edit ICMC</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<!--<form role="form">-->
									<form:form id="userPage" name="userPage" action="updateICMC" method="post" 
										modelAttribute="user" autocomplete="off">
										
										<form:hidden path="id"/>
										
										<div class="form-group">
											<label>Name</label>
											<form:input path="name" id="name" name="name" cssClass="form-control" 
												readonly="true" maxlength="45" />
										</div>
												
										<div class="form-group">
											<label>Link Branch Sol ID</label>
											 <form:input path="linkBranchSolId" id="linkBranchSolId" name="linkBranchSolId" 
											 	cssClass="form-control" maxlength="45" onkeyup="doAjaxForRBINameZoneAndRegion()"/>
										</div>
										
										<form:hidden path="zoneHidden" id="zoneHidden" name="zoneHidden"/>
										<form:hidden path="rbiNameHidden" id="rbiNameHidden" name="rbiNameHidden"/>
										<form:hidden path="regionHidden" id="regionHidden" name="regionHidden"/>
										
										<div class="form-group js-rbi">
											<label>RBI Name</label>
											 <form:select path="rbiName" id="rbiName" name="rbiName" cssClass="form-control" maxlength="45"
											 	disabled="true" onchange="doAjaxForZoneAndRegion()">
											 	<form:option value="">Select RBI Name</form:option>
											 	<form:options items="${rbiNameList}" />
											 </form:select>
										</div>
										
										<div class="form-group js-zone">
											<label>Zone</label>
											 <form:select path="zone" id="zone" name="zone" cssClass="form-control" 
											 	disabled="true" onchange="doAjaxForRegion()">
    											<option value="" label="Select Zone"></option>
    											<form:options items="${zoneList}" />
											</form:select>
										</div>
										
										<div class="form-group js-region">
											<label>Region</label>
											 <form:select path="region" id="region" name="region" disabled="true" cssClass="form-control" >
												<form:option value="">Select Region</form:option>
												<form:options items="${regionList}" />
											 </form:select>
										</div>
										
										<div class="form-group">
											<label>Address</label>
											<form:input path="address" id="address" name="address" maxlength="45"
												cssClass="form-control" />
										</div>
										
										<div class="form-group">
											<label>City</label>
											<form:input path="city" id="city" name="city" maxlength="45"
												cssClass="form-control" />
										</div>
										
										<div class="form-group">
											<label>Pin Code</label>
											<form:input path="pincode" id="pincode" name="pincode" minlength="6" maxlength="6"
												cssClass="form-control" />
										</div>
										<div class="form-group">
											<label>Chest Code</label>
											<form:input path="chestCode" id="chestCode" name="chestCode"  maxlength="49"
												cssClass="form-control" />
										</div>
										
										
										<div class="form-group">
											<label>Status</label>
											 <form:select path="status" id="status" name="status" cssClass="form-control">
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
    return this.optional(element) || /^[0-9A-Za-z\s\(\)\-\,\/]+$/i.test(value);
}, "Software must contain only letters,Space , dashes.");

	$(function() {
	  $("form[name='userPage']").validate({
	    rules: {
	    	name: {
	    		loginRegexCity:true,
	    		maxlength:44,
	    		required:true,
	    	},
	    	linkBranchSolId: {
	    		loginRegex:true,
	    		minlength:4,
	    		maxlength:4,
	    		required:true,
	    	},
	    	rbiName: {
	    		required:true,
	    	},
            zone: {
            	required:true,
            },
            region: {
            	required:true,
            },
            address: {
            	loginRegexAddress:true,
            	maxlength:44,
            	required:true,
            },
            city: {
            	loginRegexCity:true,
            	maxlength:44,
            	required:true,
            },
            pincode: {
            	loginRegex:true,
            	number:true,
            	minlength:6,
            	maxlength:6,
            	required:true,
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
	    	 rbiName: "Please select RBI Name",
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