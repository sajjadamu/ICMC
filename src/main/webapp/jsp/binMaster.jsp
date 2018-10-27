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
<link href="./resources/css/calendar.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.datetimepicker.css" />
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="./js/jquery.validate.min.js"></script>

<title>ICICI : Bin Master Entry</title>

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

						<div class="panel-heading">
							<ul>
								<li><sec:authorize access="hasRole('ADD_BIN')">
										<a href="././viewBinMaster"><i class="fa fa-table fa-fw"></i>
											View Bin Master</a>
									</sec:authorize></li>
							</ul>
							Add Data In Bin Master
						</div>

						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<!--<form role="form">-->
									<form:form id="userPage" name="userPage" action="saveDataInBin"
										method="post" modelAttribute="user"
										enctype="multipart/form-data" autocomplete="off">
										<div align="center" style="color: white; background: red;">
											<b>${errorMsg}</b>
										</div>
										<div align="center" style="color: white; background: red;">
											<b>${duplicateMsg}</b>
										</div>
										<br>
										<div class="form-group">
											<label>First Priority</label>
											<form:select path="firstPriority" id="firstPriority"
												onchange="javascript: check_val(this)"
												cssClass="form-control">
												<%-- <div align="center" style="color:white; background:red;"><b>${errorMsg}</b></div> --%>

												<form:option value="">Select First Priority</form:option>
												<form:options items="${ctype}" />
											</form:select>
										</div>



										<div class="form-group">
											<label>Second Priority</label>
											<form:select path="secondPriority" id="secondPriority"
												onchange="javascript: check_val(this)"
												cssClass="form-control">
												<form:option value="">Select Second Priority</form:option>
												<form:options items="${ctype}" />
											</form:select>
										</div>


										<div class="form-group">
											<label>Third Priority</label>
											<form:select path="thirdPriority" id="thirdPriority"
												onchange="javascript: check_val(this)"
												cssClass="form-control">
												<form:option value="">Select Third Priority</form:option>
												<form:options items="${ctype}" />
											</form:select>
										</div>

										<div class="form-group">
											<label>Fourth Priority</label>
											<form:select path="fourthPriority" id="fourthPriority"
												onchange="javascript: check_val(this)"
												cssClass="form-control">
												<form:option value="">Select Fourth Priority</form:option>
												<form:options items="${ctype}" />
											</form:select>
										</div>

										<div class="form-group">
											<label>Fifth Priority</label>
											<form:select path="fifthPriority" id="fifthPriority"
												onchange="javascript: check_val(this)"
												cssClass="form-control">
												<form:option value="">Select Fifth Priority</form:option>
												<form:options items="${ctype}" />
											</form:select>
										</div>


										<div class="form-group">
											<label>Priority Location</label>
											<form:select path="locationPriority" id="locationPriority"
												cssClass="form-control">
												<form:option value="${null}">Select Priority Location</form:option>
												<form:option value="1">1</form:option>
												<form:option value="2">2</form:option>
												<form:option value="3">3</form:option>
											</form:select>
										</div>


										<div class="form-group">
											<label>ICMC Name</label>
											<form:select id="icmcId" name="icmcId" path="icmcId"
												cssClass="form-control">
												<form:option value="">Select ICMC Name</form:option>
												<form:options items="${records}" itemValue="id"
													itemLabel="name" />

											</form:select>
										</div>

										<div class="form-group">
											<label>BIN Size</label>
											<form:select id="vaultSize" name="vaultSize" path="vaultSize"
												cssClass="form-control">
												<form:option value="">Select BIN Size</form:option>
												<form:options items="${binSizeList}" />

											</form:select>
										</div>


										<div class="form-group">
											<label>Bin Number</label>
											<form:input path="binNumber" id="binNumber"
												cssClass="form-control" />
										</div>

										<div class="form-group"></div>
										<div class="col-lg-6 form-group">
											<label>Choose File</label> <input type="file" id="file"
												name="file" Class="form-control" /> <a
												href="${documentFilePath}/binMaster.csv" download>Download
												CSV Format</a>
										</div>
										<div class="col-lg-6 form-group">
											<label>ICMC</label><br>
											<form:radiobutton path="oldNewIcmc" id="oldNewIcmc"
												name="oldNewIcmc" checked="true" value="OLD" />
											<span class="deno-value"><b>OLD</b></span>
											<form:radiobutton path="oldNewIcmc" id="oldNewIcmc"
												name="oldNewIcmc" value="NEW" />
											<span class="deno-value"><b>NEW</b></span>

										</div>

										<button type="submit" id='save'
											class="btn btn-lg btn-success btn-block" value="Details">Save</button>
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

$.validator.addMethod("matchValue", function (value, element) {
	var fileValue = $('#firstPriority').val();
	var fileValue1= $('#secondPriority').val();
	if(fileValue!=''|| fileValue1!=''){
		if(fileValue==fileValue1){
			return false;	
		}else{
			return true;
		}
		
	}else{
		return true;} }, 'First Priority and Second Priority can not be same');

$.validator.addMethod("matchValueThird", function (value, element) {
	var fileValue = $('#firstPriority').val();
	var fileValue1= $('#secondPriority').val();
	var fileValue2= $('#thirdPriority').val();
	
	if(fileValue !=''|| fileValue1!='' || fileValue2!=''){
	if(fileValue == fileValue1 || fileValue==fileValue2 || fileValue1==fileValue2){
		return false;	
	}else{
		return true;
	}
	}else{
		return true;} }, 'First Priority, Second Priority and Third Priority can not be same');


$.validator.addMethod("loginRegex", function(value, element) {
    return this.optional(element) || /^[0-9a-zA-Z]+$/i.test(value);
}, "Productivity Lost must contain only letters,Space , dashes.");

	$(function() {
	  $("form[name='userPage']").validate({
	    rules: {
	    	firstPriority:{
	    		
	    		required:function(element){
	    			var fileValue = $('#file').val();
	    			if(fileValue == ''){
	    				return true;
	    			}else{
	    				return false;
	    			}
	    		}
	    	} ,
	    	secondPriority: {
	    		required:function(element){
	    			var fileValue = $('#file').val();
	    			if(fileValue == ''){
	    				return true;
	    			}else{
	    				return false;
	    			}
	    		},
	    		matchValue:true,
					    	},
	    	thirdPriority:{
	    		required:function(element){
	    			var fileValue = $('#file').val();
	    			if(fileValue == ''){
	    				return true;
	    			}else{
	    				return false;
	    			}
	    		},
	    		matchValueThird:true,
	    					},
	    			fourthPriority:{
	    			required:function(element){
	    			var fileValue = $('#file').val();
	    			if(fileValue == ''){
	    				return true;
 			    			}else{
    				return false;
			    			}
	    			    },
	    			  //matchValueThird:true,
	    			 			},	
	    			 			fifthPriority:{
	   			required:function(element){
	    			var fileValue = $('#file').val();
			    			if(fileValue == ''){
				    				return true;
			 			    			}else{
	  		    				return false;
						    			}
	  			    			    },
 			 			},		 			
	    	locationPriority:{
	    		required:function(element){
	    			var fileValue = $('#file').val();
	    			if(fileValue == ''){
	    				return true;
	    			}else{
	    				return false;
	    			}
	    		}
	    	},
	    	icmcId:{
	    		required:function(element){
	    			var fileValue = $('#file').val();
	    			if(fileValue == ''){
	    				return true;
	    			}else{
	    				return false;
	    			}
	    		}
	    	},
	    	binNumber:{
	    		loginRegex:true,
	    		required:function(element){
	    			var fileValue = $('#file').val();
	    			if(fileValue == ''){
	    				return true;
	    			}else{
	    				return false;
	    			}
	    		}	
	    				}
	    },
	    // Specify validation error messages
	    messages: {
	    	firstPriority: "Please Select First Priority",
	    	secondPriority:{
	    		required: "Please Select Second Priority",
	    		matchValue:"First Priority and Second Priority can not be same",
	    					} ,
	    	thirdPriority:{
	    		required: "Please Select Third Priority",
	    		matchValueThird:"First Priority, Second Priority and Third Priority can not be same",
	    					},
	    	locationPriority:"Please Select Priority Location",
	    	icmcId:"Please Select ICMC Name",
	    	binNumber:{
	    		required:"Please Enter BinNumber ",
	    		loginRegex:"Accept Only Numeric"
	    				},
	    fourthPriority:{
	    	required: "Please Select fourth Priority",
	    },
	    fifthPriority:{
	    	required: "Please Select fifth Priority",
	    }
	    
	    },
	    // Make sure the form is submitted to the destination defined
	    // in the "action" attribute of the form when valid
	    submitHandler: function(form) {
	    	$('#save').prop('disabled',true);
	    	form.submit();
	    }
	  });
	});
	</script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>