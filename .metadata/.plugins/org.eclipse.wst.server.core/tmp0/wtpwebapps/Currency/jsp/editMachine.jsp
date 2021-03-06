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
<link rel="shortcut icon" href="./resources/logo/yes_favicon.ico"
	type="image/x-icon">

<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="./js/jquery.validate.min.js"></script>

<script type="text/javascript">
function doAjaxForProductivity() {
	addHeaderJson();
	var modelType = $('#modelType').val();
	$.ajax({
		type : "POST",
		url : "././getProductivity",
		data : "modelType=" + modelType,
		success : function(response) {
			var newStr = response.substring(1, response .length-1); // Remove Array Brackets
	        var data=newStr.split(",");
			$('#standardProductivity').val(data)
		},
		error : function(e) {
			alert('Productivity  Error: ' + e);
		}
	});
}

</script>

<title>Edit Machine</title>

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

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>
<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-120"
	data-genuitec-path="/Currency/src/main/webapp/jsp/editMachine.jsp">

	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-120"
		data-genuitec-path="/Currency/src/main/webapp/jsp/editMachine.jsp">

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
						<div class="panel-heading">Edit Machine Details</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<!--<form role="form">-->
									<form:form id="userPage" name="userPage" action="updateMachine"
										method="post" modelAttribute="user" autocomplete="off">
										<form:hidden path="id" />

										<div class="form-group">
											<label>Machine No</label>
											<form:input path="machineNo" id="machineNo" name="machineNo"
												cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Asset Code</label>
											<form:input path="assetCode" id="assetCode" name="assetCode"
												cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Machine Sr. No.</label>
											<form:input path="machineSINo" id="machineSINo"
												name="machineSINo" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label> Purchase Date</label>
											<form:input path="purchasedate" id="purchasedate"
												name="purchasedate" maxlength="32" onselect="ageingFind()"
												cssClass="form-control" />
										</div>


										<div class="form-group">
											<label>Make Company </label>
											<form:select path="companyname" id="companyname"
												name="companyname" cssClass="form-control">
												<form:option value="">Select Company </form:option>
												<form:options items="${machineCompanyNameList}"
													itemLabel="companyname" itemValue="companyname" />
											</form:select>


										</div>


										<div class="form-group">
											<label>Model Type</label>
											<form:select path="modelType" id="modelType" name="modelType"
												cssClass="form-control" onchange="doAjaxForProductivity()">
												<form:option value="">Select Model </form:option>
												<form:options items="${modelTypeList}"
													itemLabel="machineModelType" itemValue="machineModelType" />
											</form:select>
										</div>

										<div class="form-group">
											<label>Standard Productivity</label>
											<form:input path="standardProductivity"
												id="standardProductivity" name="standardProductivity"
												readonly="true" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Status</label>
											<form:select path="status" id="status" name="status"
												cssClass="form-control">
												<form:options items="${statusList}"></form:options>
											</form:select>
										</div>
										<button type="submit" onclick="pageSubmit()"
											class="btn btn-lg btn-success btn-block" value="Details">Update</button>
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
	<script src="./resources/js/jquery.datetimepicker.js"></script>
	<script>
		$('#purchasedate').datetimepicker({
			format : 'Y-m-d',

		});
		
		
	</script>

	<script type="text/javascript">

	
	$.validator.addMethod("nameRegex", function(value, element) {
	    return this.optional(element) || /^[A-Za-z\s]+$/i.test(value);
	}, "Software must contain only letters,Space , dashes.");
	
	
	
	 $(function() {
	  $("form[name='userPage']").validate({
	    rules: {
	    	
	    	icmcId: {
	            	required:true,
	            },
	            
	    	
	    	companyname: {
            	required:true,
            	maxlength:44,
            	nameRegex:true,
            },
            machineNo: {
            	required:true,
            	maxlength:44,
            },
            
            assetCode: {
         		required:true,
            },
            purchasedate:{
            	required:true,
            },
            machineSINo: {
            	required:true,
            },
            modelType: {
         		required:true,
            },
            standardProductivity:{
            	required:true,
            },
           
	    },
	    messages: {
	    	
	    	icmcId: {
	    		required:"Please Select Icmc",
	    	},
	    	
	    	companyname: {
	    		required:"Please Enter Name",
	    		maxlength:" Name can't have more than 45 characters",
	    		nameRegex:"Special and Numeric characters are not allowed",
	    	},
	    	machineNo: {
	    		required:"Please Enter Machine No.",
	    	},
	    	 assetCode: {
	         		required:"Please Enter Asset Code",
	            },
	    	purchasedate:{
	    		required:"Please Enter Purchase Date",
	    	},
	    	 machineSINo: {
	            	required:"Please Enter Machine Sr. No.",
	            },
	            modelType: {
	         		required:"Please Select Model Type",
	            },
	            standardProductivity:{
	            	required:"Please Enter Standard Productivity",
	            },
	    },
	    submitHandler: function(form) {
	    	var isValid = $("form[name='userPage']").validate().form();
	      		form.submit();
	    }
	  });
	});
	</script>
	<script src="./resources/js/jquery.datetimepicker.js"></script>
	<script>
		$('#softwareUpdationDate').datetimepicker({
			format : 'Y-m-d',

		});
		
	</script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>