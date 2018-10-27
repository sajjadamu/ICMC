<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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

<title>ICICI : Edit Vendor</title>
<script type="text/javascript">
function pageSubmit(){
	alert('Vendor Record Submit');
	window.location='././viewCITCRAVendor';
} 
</script>

<script type="text/javascript">
function doAjaxForRegion() {
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
				option+='<option value="'+data[i].trim()+'">'+data[i].trim()+'</option>';
				}
			$('#region').html(option);
		},
		error : function(e) {
			alert('Region Error: ' + e);
		}
	});
}

function getICMC(){
    $.getJSON(
         "icmcList.json",
         {region: $('select#region').val()},
         function(data) {
              var html = '';
              var len = data.length;
              for(var i=0; i<len; i++){
                   html += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
               }
              $('select#icmcIds').empty();
              $('select#icmcIds').append(html);
         }
      );
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
						<div class="panel-heading">Modify Vendor</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<!--<form role="form">-->
									<form:form id="userPage" name="userPage"
										action="updateCITCRAVendor" method="post"
										modelAttribute="user" autocomplete="off">

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
												cssClass="form-control" onchange="getICMC()">
												<form:options items="${regionList}" itemLabel="region" />
											</form:select>
										</div>

										<div class="form-group">
											<label>ICMC</label>
											<form:select path="icmcIds" id="icmcIds" name="icmcIds"
												cssClass="form-control">
												<form:options items="${icmcList}" itemValue="id"
													itemLabel="name" />
											</form:select>
										</div>

										<div class="form-group">
											<label>Name</label>
											<form:input path="name" id="name" name="name" maxlength="45"
												cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Type One</label>
											<form:input path="typeOne" id="typeOne" name="typeOne"
												maxlength="45" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Type Two</label>
											<form:input path="typeTwo" id="typeTwo" name="typeTwo"
												maxlength="45" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Type Three</label>
											<form:input path="typeThree" id="typeThree" name="typeThree"
												maxlength="45" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>FPR Name</label>
											<form:input path="FPRName" id="FPRName" name="FPRName"
												maxlength="45" cssClass="form-control" readonly="true" />
										</div>

										<div class="form-group">
											<label>FPR Number</label>
											<form:input path="FPRNumber" id="FPRNumber" name="FPRNumber"
												maxlength="11" cssClass="form-control" readonly="true" />
										</div>
										<button type="submit" class="btn btn-lg btn-success btn-block"
											value="Details">Update</button>
										<form:hidden path="id" />
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
	
	$.validator.addMethod("loginRegexName", function(value, element) {
	    return this.optional(element) || /^[A-Za-z\s]+$/i.test(value);
	}, "Software must contain only letters,Space , dashes.");

	$.validator.addMethod("multipeFieldValidator", function(value) { 
	    var returnVal = false; 
	    if($("#typeOne").val() != '' || ($("#typeTwo").val() != '' || $("#typeThree").val() != '')) {
	        returnVal = true;
	    }
	    return returnVal; 
	}, 'Please specify at least one field among Type One, Type Two and Type Three');

	
	$(function() {
	  $("form[name='userPage']").validate({
	    rules: {
	    	zone: "required",
            region: "required",
            icmcIds: "required",
            name: {
            	loginRegexName:true,
            	required:true,
            	maxlength:44,
            },
            typeOne: {
            	multipeFieldValidator:true,
            	maxlength:44,
            },
            typeTwo: {
            	multipeFieldValidator:true,
            	maxlength:44,
            },
            typeThree: {
            	multipeFieldValidator:true,
            	maxlength:44,
            },
            FPRName:{
            	
            	required:true
            } ,
            FPRNumber:{
            	
            	 required:true,
            },
	    },
	    // Specify validation error messages
	    messages: {
	    	zone: "Please Select Zone",
	    	region: "Please Select Region",
	    	icmcIds: "Please Select ICMC",
	    	name: {
	    		loginRegexName:"Accept only Character  value",
	    		required:"Please Enter Vendor Name",
	    		maxlength:"Vendor Name can't have more than 45 characters",
	    	},
	    	typeOne: {
	    		multipeFieldValidator:"Please specify at least one field among Type One, Type Two and Type Three",
	    		maxlength:"Type One Value can't have more than 45 characters",
	    	},
	    	typeTwo: {
	    		multipeFieldValidator:"Please specify at least one field among Type One, Type Two and Type Three",
	    		maxlength:"Type Two Value can't have more than 45 characters",
	    	},
	    	typeThree: {
	    		multipeFieldValidator:"Please specify at least one field among Type One, Type Two and Type Three",
	    		maxlength:"Type Three Value can't have more than 45 characters",
	    	},
	    	FPRName:{
	    		
	    		 required:"Please Enter FPR Name",
	    	},
	    	FPRNumber:{
	    		
	    		 required:"Please Enter FPR Number",
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