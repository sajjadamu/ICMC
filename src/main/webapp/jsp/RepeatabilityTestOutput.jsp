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

<title>ICICI : Compatibility Testing</title>

<script type="text/javascript">
function doAjaxForMachineSerialNo() {
	addHeader();
	var machineNo = $('#machineNo').val();
	$.ajax({
		type : "POST",
		url : "././getMachineSerialNo",
		data : "machineNo=" + machineNo,
		success : function(response) {
			$('#machineSerialNo').val(response);
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});
}

function doAjaxPost() {
	  // get the form values  
	  var Bundle = $('#bundle').val();
	  var total = Bundle*1000;
	   $('#totalNotes').val(total);
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
						<div class="panel-heading">
							<ul>
								<li><a href="././viewRepeatabilityTestOutput"><i
										class="fa fa-table fa-fw"></i> View Repeatability Test Output
										Details</a></li>
							</ul>
							Add Repeatability Test Output Details
						</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<!--<form role="form">-->
									<form:form id="userPage" action="AddRepeatabilityTestInput"
										method="post" name="userPage" modelAttribute="user"
										autocomplete="off">

										<table class="table table-striped table-bordered table-hover"
											id="tableValue">

											<thead>
												<tr>
													<th>ICMC Name</th>
													<th>Date</th>
													<th>Machine No</th>
													<th>Machine SI No</th>
													<th>Denomination</th>
													<th>Test Category</th>
													<th>No of Bundle</th>
													<th>Total Notes Processed</th>
													<th>ATM</th>
													<th>Issuable</th>
													<th>Soiled</th>
													<th>Reject</th>
													<th>Average Compatibility Variance (in %)</th>
												</tr>
											</thead>

											<tbody>
												<tr>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td>1st Run</td>
													<td><form:input path="bundle" id="bundle"
															name="bundle" maxlength="10" cssClass="form-control"
															onkeyup="doAjaxPost()" /></td>
													<td><form:input path="totalNotes" id="totalNotes"
															name="totalNotes" maxlength="10" cssClass="form-control" />
													</td>
													<td><form:input path="currencyType" id="currencyType"
															name="currencyType" cssClass="form-control" /></td>
													<td><form:input path="currencyType" id="currencyType"
															name="currencyType" cssClass="form-control" /></td>
													<td><form:input path="currencyType" id="currencyType"
															name="currencyType" cssClass="form-control" /></td>
													<td></td>
													<td></td>
												</tr>
												<tr>
													<td>${repeatabilityTestInput.icmcId}</td>
													<td></td>
													<td><form:select path="machineNo" id="machineNo"
															name="machineNo" cssClass="form-control"
															onchange="doAjaxForMachineSerialNo()">
															<form:option value="">Select Machine No</form:option>
															<form:options items="${machineList}" />
														</form:select></td>
													<td><form:input path="machineSerialNo"
															id="machineSerialNo" name="machineSerialNo"
															maxlength="10" readonly="true" cssClass="form-control" />
													</td>
													<td><form:select path="denomination"
															cssClass="form-control">
															<form:option value="">Select Denomination</form:option>
															<form:options items="${denominationList}"
																itemLabel="denomination" itemValue="denomination" />
														</form:select></td>
													<td>2nd Run</td>
													<td><form:input path="bundle" id="bundle"
															name="bundle" maxlength="10" cssClass="form-control"
															onkeyup="doAjaxPost()" /></td>
													<td><form:input path="totalNotes" id="totalNotes"
															name="totalNotes" maxlength="10" cssClass="form-control" />
													</td>
													<td><form:input path="currencyType" id="currencyType"
															name="currencyType" cssClass="form-control" /></td>
													<td><form:input path="currencyType" id="currencyType"
															name="currencyType" cssClass="form-control" /></td>
													<td><form:input path="currencyType" id="currencyType"
															name="currencyType" cssClass="form-control" /></td>
													<td></td>
													<td></td>
												</tr>
												<tr>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td>% of Variation</td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
											</tbody>
										</table>

										<%-- <div class="form-group">
											<label>Machine No.</label>
											<form:input path="machineNo" id="machineNo" maxlength="45" name="machineNo" cssClass="form-control" />
										</div>
										
										<div class="form-group">
											<label>Currency Type</label>
											<form:radiobutton path="currencyType" id="currencyType"   label=""  value="ATM"/>ATM
											<form:radiobutton path="currencyType" id="currencyType" label="" value="SOILED"/>SOILED
											<form:radiobutton path="currencyType" id="currencyType" label="" value="ISSUABLE"/>ISSUABLE
										</div>
										
										 <div class="form-group">
											<label>Denomination</label>
											<form:radiobuttons path="denomination" items="${denominationList}" itemLabel="denomination" name="denomination" itemValue="denomination"/>
										</div>
										
										<div class="form-group">
											<label>Bundle</label>
											<form:input path="bundle" id="bundle"  maxlength="45" name="bundle" cssClass="form-control" onkeyup="" />
										</div>
										
										<div class="form-group">
											<label>Total Value</label>
											<form:input path="totalValue" id="totalValue" maxlength="45" name="totalValue" cssClass="form-control" />
										</div>
										
										<div class="form-group">
											<label>Bin No</label>
											<form:input path="binNo" id="binNo" maxlength="45" name="binNo" cssClass="form-control" />
										</div>
										
										<button type="submit" onclick="pageSubmit()" class="btn btn-lg btn-success btn-block"
											value="Details">Submit</button> --%>
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
	$(function() {
	  $("form[name='userPage']").validate({
	    rules: {
	    	machineNo: {
	    		required:true,
	    		rangelength: [0, 44]
	    	},
	    	denomination: "required",
	    	currencyType: "required",
	    	binNo:{
	    		required:true,
	    		rangelength: [0, 44]
	    	} ,
	    	bundle: {
	    	required:true,
	    	number:true,
	    	rangelength: [0, 44]
	    	},
	    	totalValue:{
	    		required:true,
	    		rangelength: [0, 44]
	    	} ,
         	    },
	    // Specify validation error messages
	    messages: {
	    	machineNo:{
	    		required: "Please Enter Machine No.",
	    		rangelength: "Machine No. can't have more than  45 character "
	    		
	    	},
	    	denomination: "Please Select Redio Button",
	    	bundle:{
	    		required:"Please Enter the bundle number",
	             number:"only Numeric value ",
	                rangelength: "Bundle can't have more than  45 character "
	    	} ,
	    	totalValue: {
	    		required:"Please Enter Total value",
	    		rangelength: "Total Value  can't have more than  45 character "
	    	},
	    	currencyType: "Please Enter the CurrencyType",
	    	binNo: {
	    		required:"Please Enter Bin No",
	    		 rangelength: "BinNo.  can't have more than  45 character "
	    	},
             
            	    },
	    // Make sure the form is submitted to the destination defined
	    // in the "action" attribute of the form when valid
	    submitHandler: function(form) {
	      form.submit();
	    }
	  });
	});
	
	</script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>







<%-- <!DOCTYPE html>
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

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="./js/jquery.validate.min.js"></script>

<title>ICICI : Compatibility Testing</title>

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
<body>
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
                        		<li>
	                        		<a href="././viewRepeatabilityTestOutput"><i
										class="fa fa-table fa-fw"></i> View Repeatability Test Output Details</a>
								</li>
							</ul>Add Repeatability Test Output Details
                        </div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<!--<form role="form">-->
									<form:form id="userPage" action="AddRepeatabilityTestOutput" method="post" name="userPage"
										modelAttribute="user" autocomplete="off">
										
										<div class="form-group">
											<label>Machine No.</label>
											<form:input path="machineNo"  maxlength="45" id="machineNo" name="machineNo" cssClass="form-control" />
										</div>
										
										<div class="form-group">
											<label>Currency Type</label>
											<form:radiobutton path="currencyType" id="currencyType"   label=""  value="ATM"/>ATM
											<form:radiobutton path="currencyType" id="currencyType" label="" value="SOILED"/>SOILED
											<form:radiobutton path="currencyType" id="currencyType" label="" value="ISSUABLE"/>ISSUABLE
										</div>
									
										 <div class="form-group">
											<label>Denomination</label>
											<form:radiobuttons path="denomination" items="${denominationList}" itemLabel="denomination" name="denomination" itemValue="denomination"/>
										</div>
										
										<div class="form-group">
											<label>Bundle</label>
											<form:input path="bundle" maxlength="45" id="bundle" name="bundle" cssClass="form-control" />
										</div>
										
										<div class="form-group">
											<label>Total Value</label>
											<form:input path="totalValue" maxlength="45" id="totalValue" name="totalValue" cssClass="form-control" />
										</div>
										
										<div class="form-group">
											<label>Bin No</label>
											<form:input path="binNo" id="binNo" maxlength="45" name="binNo" cssClass="form-control" />
										</div>
										
										<button type="submit" onclick="pageSubmit()" class="btn btn-lg btn-success btn-block"
											value="Details">Submit</button>
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
	$(function() {
	  $("form[name='userPage']").validate({
	    rules: {
	    	machineNo: {
	    		required:true,
	    		rangelength: [0, 44]
	    	},
	    	denomination: "required",
	    	currencyType: "required",
	    	binNo:{
	    		required:true,
	    		rangelength: [0, 44]
	    	} ,
	    	bundle: {
		    	required:true,
		    	number:true,
		    	rangelength: [0, 44]
		    	},
	    	totalValue:{
	    		required:true,
	    		rangelength: [0, 44]
	    	} ,
         	    },
	    // Specify validation error messages
	    messages: {
	    	machineNo:{
    		required: "Please Enter Machine No.",
    		rangelength: "Machine No. can't have more than  45 character "
    		
    	},
	    	denomination: "Please Select Redio Button",
	    	bundle:{
	    		required:"Please Enter the bundle number",
	            number:"only Numeric value ",
	             rangelength: "Bundle  can't have more than  45 character "
	    	} ,
	    	totalValue: {
	    		required:"Please Enter Total value",
	    		rangelength: "Total Value  can't have more than  45 character "
	    	},
	    	currencyType: "Please Enter the CurrencyType",
	    	binNo: {
	    		required:"Please Enter Bin No",
	    		 rangelength: "BinNo.  can't have more than  45 character "
	    	}
             
            	    },
	    // Make sure the form is submitted to the destination defined
	    // in the "action" attribute of the form when valid
	    submitHandler: function(form) {
	      form.submit();
	    }
	  });
	});
	</script>
</body>

</html> --%>