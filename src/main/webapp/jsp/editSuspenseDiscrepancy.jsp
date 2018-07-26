<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="en">

<head>
<link rel="shortcut icon" href="./resources/logo/favicon.ico" type="image/x-icon">
<script src="./js/jquery-1.12.0.min.js"></script>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<!-- /#wrapper -->

	<!-- jQuery -->
	<script src="./resources/bower_components/jquery/dist/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script
		src="./resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script
		src="./resources/bower_components/metisMenu/dist/metisMenu.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="./resources/dist/js/sb-admin-2.js"></script>

	<script src="./resources/js/jQuery.print.js"></script>
<title>ICICI : Edit Discrepancy</title>

<script src="./resources/Currency/js/jquery.js"></script>
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

<link rel="stylesheet" type="text/css" href="./resources/dist/css/style.css">

<script type="text/javascript">

function doAjaxPost() {  
	  // get the form values  
	  var total = 0;
	  var Denomination = $('#denomination').val();
	  var NumberOfNotes = $('#numberOfNotes').val();
	  var dis = $('#discrepancyType').val();
	  var mutil = $('#mutilType').val();
	  if(dis == 'MUTILATED'){
		  if(mutil == 'FULL VALUE'){
			  total = Denomination*NumberOfNotes;
		  }
		  if(mutil == 'HALF VALUE'){
			  total = Denomination*NumberOfNotes*0.5;
		  }
		  if(mutil == 'ZERO VALUE'){
			  total = Denomination*NumberOfNotes*0;
		  }
	  }else{
		  total = Denomination*NumberOfNotes;
	  }
	  $('#value').val(total);
	}  
</script>

</head>

<body oncontextmenu="return false;">
	<div id="wrapper">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />
		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-8">
									<!--<form role="form">-->
									<form:form id="" action="updateDiscrepancy" method="post" modelAttribute="discrepancyAllocation" autocomplete="off">

										<form:hidden path="id" />
										
										<form:hidden path="normalOrSuspense" value="SUSPENSE"/>

										<div class="col-lg-4 form-group">
											<label>Machine Number</label>
											<form:select path="machineNumber" id="machineNumber"
												cssClass="form-control">
												<option value="" label="Select Machine Number"></option>
												<form:options items="${machineNumberList}" />
											</form:select>
											<label id="err1" style="display: none; color: red">Please
												Enter Machine Number</label>
										</div>

										<%-- <div class="col-lg-4 form-group">
											<label>Discrepancy Date</label>
											<form:input type="text" onkeyup="return false"
												path="discrepancyDate" id="discrepancyDate"
												name="discrepancyDate" cssClass="form-control" />
											<label id="err2" style="display: none; color: red">Please
												Enter Discrepancy Date</label>
										</div> --%>

										<div class="col-lg-4 form-group">
											<label>SOL ID</label>
											<form:input path="solId" id="solId" name="solId"
												cssClass="form-control" onkeyup="doAjaxPostForBranch()"
												maxlength="4" />
											<label id="err3" style="display: none; color: red">Please
												Enter SOL ID</label> <label id="err13"
												style="display: none; color: red">Please Enter 4
												Digit SOL ID</label>
										</div>

										<div class="col-lg-4 form-group">
											<label>Branch</label>
											<form:input path="branch" id="branch" name="branch"
												cssClass="form-control" readonly="true" />
										</div>

										<div class="col-lg-4 form-group">
											<label>Account/Teller/Cam</label>
											<form:select path="accountTellerCam" id="accountTellerCam"
												name="accountTellerCam" cssClass="form-control">
												<form:option value="">Select Option</form:option>
												<form:option value="ACCOUNT">ACCOUNT</form:option>
												<form:option value="TELLER">TELLER</form:option>
												<form:option value="CAM">CAM</form:option>
											</form:select>
											<label id="err5" style="display: none; color: red">Please
												Select Account/Teller/Cam</label>
										</div>

										<div class="col-lg-4 form-group">
											<label>Customer / Teller Name</label>
											<form:input path="customerName" id="customerName"
												cssClass="form-control" />
											<label id="err6" style="display: none; color: red">Please
												Enter Customer name</label>
										</div>

										<div class="col-lg-4 form-group">
											<label>Account Number / Teller</label>
											<form:input path="accountNumber" id="accountNumber"
												cssClass="form-control" />
											<label id="err7" style="display: none; color: red">Please
												Enter Account Number</label>
										</div>

										<div class="col-lg-4 form-group">
											<label id="err1" style="display: none; color: red">Please
												Enter Denomination</label> <label>Denomination</label>
											<form:input path="denomination" name="denomination"
												minlenght="4" maxlength="4" id="denomination"
												cssClass="form-control" />
										</div>
										
                                         <div class="col-lg-4 form-group">
											<label>Discrepancy Type</label>
											<form:select path="discrepancyType" id="discrepancyType"
												name="discrepancyType" cssClass="form-control">
												<form:option value="">Select Option</form:option>
												<form:option value="SHORTAGE">SHORTAGE</form:option>
												<form:option value="EXCESS">EXCESS</form:option>
												<form:option value="FAKE">FAKE</form:option>
												<form:option value="MUTILATED">MUTILATED</form:option>
											</form:select>
										</div>

										<%-- <div class="col-lg-4 form-group">
											<label>Mutil Type</label>
											<form:input path="mutilType" id="mutilType" name="mutilType" cssClass="form-control"/>
										</div> --%>
										
										<div class="col-lg-4 form-group">
											<label>Mutil Type</label>
											<form:select path="mutilType" id="mutilType"
												name="mutilType" cssClass="form-control">
												<form:option value="">Select Option</form:option>
												<form:option value="FULL VALUE">FULL VALUE</form:option>
												<form:option value="HALF VALUE">HALF VALUE</form:option>
												<form:option value="ZERO VALUE">ZERO VALUE</form:option>
											</form:select>
										</div>
										
										<div class="col-lg-4 form-group">
											<label>Note Serial Number</label>
											<form:input path="noteSerialNumber" id="noteSerialNumber"
												cssClass="form-control" />
											<label id="err4" style="display: none; color: red">Please
												Enter Note Serial Number</label>
										</div>

										<div class="col-lg-4 form-group">
											<label>Number of Notes</label>
											<form:input path="numberOfNotes" id="numberOfNotes"
												onkeyup="doAjaxPost()" cssClass="form-control" />
										</div>

										<div class="col-lg-4 form-group">
											<label>Discrepancy Value</label>
											<form:input path="value" id="value" cssClass="form-control" />
										</div>
                                         
                                         <div class="col-lg-4 form-group">
											<label>Print year</label>
											<form:input path="printYear" id="printYear" name="printYear"
												cssClass="form-control" />
										</div>

										<div class="col-lg-4 form-group">
											<label>Remarks</label>
											<form:input path="remarks" id="remarks" name="remarks"
												cssClass="form-control" />
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
	<script src="./resources/js/jQuery.print.js"></script>
<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>
