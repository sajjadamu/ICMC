<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
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

<script type="text/javascript" src="./js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="./js/jquery.validate.min.js"></script>

<title>ICICI : Add User</title>

<script type="text/javascript">
function doAjaxForRegion() {
	addHeaderJson();
	var zone = $('#zoneId').val();
	if(zone != ''){
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
			$('#regionId').html(option);
		},
		error : function(e) {
			alert('Region Error: ' + e);
		}
	});
 }else{
	$('#regionId').html('<option value="">Select Region</option>');
	$('#icmcId').html('<option value="">Select ICMC</option>');
} 
}

</script>

<script type="text/javascript" charset="utf-8">
     function getICMC(){
        $.getJSON(
             "icmcListForUserAdministration.json",
             {region: $('select#regionId').val()},
             function(data) {
                  var html = '';
                  var len = data.length;
                  var option = '<option value="">Select ICMC</option>';
                  for(var i=0; i<len; i++){
                	  option += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
                   }
                  $('select#icmcId').empty();
                  $('select#icmcId').append(option);
             }
          );
 		}
 </script>

<script type="text/javascript" charset="utf-8"> 
   		function getPrinter(){
   			$.getJSON(
   	             "icmcPrinterList.json",
   	             {icmc: $('select#icmcId').val()},
   	             function(data) {
   	                  var html = '';
   	                  var len = data.length;
   	                  var option = '<option value="">Select Printer</option>';
   	                  for(var i=0; i<len; i++){
   	                	  option += '<option value="' + data[i].id + '">' + data[i].printerName +" : "+ data[i].printerIP +" : "+ data[i].port + '</option>';
   	                   }
   	                  $('select#icmcPrinterId').empty();
   	                  $('select#icmcPrinterId').append(option);
   	             }
   	          );
     	}
 </script>

<script type="text/javascript" charset="utf-8">    
     function vailidRegionZoneIcmc(){
    	 addHeaderJson();
    	// alert("vailidRegionZoneIcmc   :"+$("input[name=roleId]:checked").val())
         var roleType= $("input[name=roleId]:checked").val();
    	 $.ajax({
    		 type:"POST",
    		 url:"././getRoleType",
    		 data:"roleType="+roleType,
    		 success:function(response){
    			
    			   var newStr = response.toString();
    		       var data = newStr.split(",");
    		       var roleCheck=data[0];
				if(roleCheck=='ALL'){
					 $("#zoneId").attr("disabled", "disabled");
				     $("#regionId").attr("disabled", "disabled");  
				     $("#icmcId").attr("disabled", "disabled");
				     $("#icmcPrinterId").attr("disabled", "disabled");
				}
				else if(roleCheck=='ICMC'){
					 $("#zoneId").removeAttr("disabled"); 
					 $("#regionId").removeAttr("disabled");  
					 $("#icmcId").removeAttr("disabled");
					 $("#icmcPrinterId").removeAttr("disabled");
				}
				else if(roleCheck=='REGION'){
					 $("#zoneId").removeAttr("disabled"); 
					 $("#regionId").removeAttr("disabled");  
					 $("#icmcId").removeAttr("disabled");
					 $("#icmcPrinterId").removeAttr("disabled");
				     $("#icmcId").attr("disabled", "disabled"); 
				     $("#icmcPrinterId").attr("disabled", "disabled"); 
				}
				else if(roleCheck=='ZONE'){
					$("#zoneId").removeAttr("disabled"); 
					$("#regionId").removeAttr("disabled");  
					$("#icmcId").removeAttr("disabled");
					$("#icmcPrinterId").removeAttr("disabled");
				    $("#regionId").attr("disabled", "disabled");  
				    $("#icmcId").attr("disabled", "disabled");
				    $("#icmcPrinterId").attr("disabled", "disabled"); 
				}
				else if(roleCheck=='IT_ADMIN'){
					 $("#zoneId").attr("disabled", "disabled");
				     $("#regionId").attr("disabled", "disabled");  
				     $("#icmcId").attr("disabled", "disabled");
				     $("#icmcPrinterId").removeAttr("disabled");
				}else {
					 $("#zoneId").removeAttr("disabled"); 
					 $("#regionId").removeAttr("disabled");  
					 $("#icmcId").removeAttr("disabled");
					 $("#icmcPrinterId").removeAttr("disabled");
				}
					  
    		 },
    		 error: function(e) {
    				alert('RegionZoneIcmc Error: ' + e);
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

<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-59"
	data-genuitec-path="/Currency/src/main/webapp/jsp/addUSER.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-59"
		data-genuitec-path="/Currency/src/main/webapp/jsp/addUSER.jsp">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">

						<div class="panel-heading">
							<ul>
								<li><a href="././viewAll"><i class="fa fa-table fa-fw"></i>
										View Users List</a></li>
							</ul>
							Add User
						</div>

						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<!--<form role="form">-->

									<form:form id="userPage" name="userPage" action="saveUser"
										method="post" modelAttribute="user" autocomplete="off">

										<div align="center" style="color: red">
											<b>${duplicateUser}</b>
										</div>

										<div class="form-group">
											<label>User ID</label>
											<form:input path="id" id="id" name="id" maxlength="32"
												cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>User Name</label>
											<form:input path="name" id="name" name="name" maxlength="45"
												cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Email</label>
											<form:input path="email" id="email" name="email"
												maxlength="45" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Roles</label><br>
											<form:radiobuttons items="${rolesList}" itemValue="id"
												itemLabel="id" path="roleId" id="roleId" name="role"
												onchange="vailidRegionZoneIcmc()" />
										</div>

										<div class="form-group">
											<label>Zone</label>
											<form:select path="zoneId" id="zoneId" name="zoneId"
												cssClass="form-control" onchange="doAjaxForRegion()">
												<option value="" label="Select Zone"></option>
												<form:options items="${zoneList}" />
											</form:select>
										</div>

										<div class="form-group">
											<label>Region</label>
											<form:select path="regionId" id="regionId" name="regionId"
												cssClass="form-control" onchange="getICMC()">
												<form:option value="">Select Region</form:option>
											</form:select>
										</div>

										<div class="form-group">
											<label>ICMC Name</label>
											<form:select path="icmcId" id="icmcId" name="icmcId"
												cssClass="form-control" onchange="getPrinter()">
												<form:option value="">Select ICMC</form:option>
											</form:select>
										</div>

										<div class="form-group">
											<label>ICMC Printer</label>
											<form:select path="icmcPrinterId" id="icmcPrinterId"
												name="icmcPrinter" cssClass="form-control">
												<option value="" label="Select Printer"></option>
											</form:select>
										</div>

										<div class="form-group">
											<label>Status</label>
											<form:select path="status" id="status" name="status"
												cssClass="form-control">
												<form:option value="ENABLED">Enabled</form:option>
												<form:option value="DISABLED">Disabled</form:option>
												<form:option value="DELETED">Deleted</form:option>
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
	
	$.validator.addMethod("loginRegex", function(value, element) {
			    return this.optional(element) || /^[0-9]+$/i.test(value);
			}, "Software must contain only letters,Space , dashes.");
	
	$.validator.addMethod("nameRegex", function(value, element) {
	    return this.optional(element) || /^[A-Za-z\s]+$/i.test(value);
	}, "Software must contain only letters,Space , dashes.");

	$.validator.addMethod("mailRegex", function(value, element) {
	    return this.optional(element) || /^[a-zA-Z0-9._-]+@icicibank.com+$/i.test(value);
	}, "Software must contain only letters,Space , dashes.");

	
	$(function() {
	  $("form[name='userPage']").validate({
	    rules: {
	    	id: {
            	required:true,
            	maxlength:31,
            	loginRegex:true,
            },
            name: {
            	nameRegex:true,
            	required:true,
            	maxlength:44,
            },
            email: {
            	//mailRegex:true,
         		required:true,
            	maxlength:44,
            	email:true,
            },
            roleId: {
                required: true,
               
            },
            zoneId:{
            	 required: true,
            },
            regionId:{
           	 required: true,
           },
           icmcId:{
          	 required: true,
          },
          icmcPrinterId:{
           	 required: true,
           },
            status: "required",
	    },
	    // Specify validation error messages 
	    messages: {
	    	 id: {
	    		required:"Please Enter User ID",
	    		maxlength:"User ID can't have more than 32 characters",
	    		loginRegex:"Please Enter only Numeric Value",
	    	},
             name: {
            	 nameRegex:"Special and Numeric characters are not allowed",
	    		required:"Please Enter User Name",
	    		maxlength:"User Name can't have more than 45 characters",
	    	},
             email: {
            	 //mailRegex:"Not a Valid Email ID, Only Accepts the type xxxxxx@icicibank.com",
            	 required:"Please Enter Email",
            	 maxlength:"Email can't have more than 45 characters",
            	 email:"Please enter a valid Email-ID",
             },
             roleId: {
     	        required: "You must select Role",
     	     },
     	    zoneId:{
           	 required: "Please Select Zone",
           },
           regionId:{
          	 required: "Please Select Region",
          },
          icmcId:{
         	 required: "Please Select Icmc",
         },
         icmcPrinterId:{
         	 required: "Please Select Printer",
         },
             status: "Please Select Status",
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