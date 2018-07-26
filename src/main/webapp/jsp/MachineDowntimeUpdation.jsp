<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<script src="./resources/bower_components/jquery/dist/jquery.min.js"></script>
<script type="text/javascript" src="./js/jquery.validate.min.js"></script>

<title>ICICI : Machine Down Time</title>

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
						<div class="panel-heading">
							<ul>
								<li><a href="././viewMachineDownTime"><i
										class="fa fa-table fa-fw"></i> View Machine Downtime Details</a></li>
							</ul>
							Add Machine Downtime
						</div>

						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<!--<form role="form">-->
									<form:form id="userPage" name="userPage"
										action="AddMachineDowntimeUpdation" method="post"
										modelAttribute="user" autocomplete="off">

										<jsp:useBean id="now" class="java.util.Date" />
										<fmt:formatDate var="nowDate" value="${now}"
											pattern="yyyy-MM-dd" />

										<div class="form-group">
											<label>Date From</label>
											<form:input path="machineDownDateFrom"
												id="machineDownDateFrom" name="machineDownDateFrom"
												cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Date To</label>
											<form:input path="machineDownDateTo" id="machineDownDateTo"
												name="machineDownDateTo" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Engineer Attended Call</label>
											<form:input path="engineerAttendedCall"
												id="engineerAttendedCall" name="engineerAttendedCall"
												cssClass="form-control" />
										</div>


										<div class="form-group">
											<label>Machine No</label><br>
											<form:radiobuttons items="${machineList}"
												itemLabel="machineNo" itemValue="machineNo" path="machineNo"
												id="machineNo" name="machineNo" />
										</div>

										<%-- <div class="form-group">
											<label>Time From</label>
                                        	<form:input path="timeFrom" id="timeFrom" name="timeFrom" cssClass="form-control" />
										</div>
										
										<div class="form-group">
											<label>Time To</label>
                                        	<form:input path="timeTo" id="timeTo" name="timeTo" cssClass="form-control" />
										</div> --%>

										<div class="form-group">
											<label>Downtime Reason</label>
											<form:select path="downtimeReason" id="downtimeReason"
												name="downtimeReason" cssClass="form-control"
												onchange="doAjaxForRegion()">
												<option value="" label="Select Downtime Reason"></option>
												<form:options items="${reasonList}" />
											</form:select>
										</div>

										<div class="form-group">
											<label>Machine Type</label>
											<form:select path="machineType" id="machineType"
												name="machineType" cssClass="form-control"
												onchange="doAjaxForRegion()">
												<option value="" label="Select Machine Type"></option>
												<option value="BPS" label="BPS"></option>
												<option value="Glory" label="Glory"></option>
												<option value="Numeron" label="Numeron"></option>
											</form:select>
										</div>

										<div class="form-group">
											<label>Remarks</label>
											<form:input path="remarks" id="remarks" name="remarks"
												cssClass="form-control" />
										</div>

										<button id="submit" type="submit" class="btn btn-lg btn-success btn-block"
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

		$.validator.addMethod("loginRegex", function(value, element) {
			return this.optional(element) || /^[a-zA-Z0-9._-]+$/i.test(value);
		}, "Software must contain only letters,Space , dashes.");

		$(function() {
			$("form[name='userPage']").validate({
				rules : {
					machineDownDateFrom : {
						required : true,
					},
					machineDownDateTo : {
						required : true,
					},
					engineerAttendedCall : {
						required : true,
					},
					machineNo : {
						required : true,
					},
					downtimeReason : {
						required : true,
					},
					machineType : {
						required : true,
					}
				},
				messages : {
					machineDownDateFrom : {
						required : "Please put from Date"
					},
					machineDownDateTo : {
						required : "Please put T0 Date",
					},
					engineerAttendedCall : "Please put engineering call",
					machineNo : "Please Select Machine number",
					downtimeReason : {
						required : "You must select a  Reason",
					},
					machineType : "Please Select Machine Type",
				},

				submitHandler : function(form) {
					$("#submit").attr('disabled',true);
					form.submit();
				}
			});
		});
	</script>
	<script src="./resources/js/jquery.datetimepicker.js"></script>
	<script>
		$('#machineDownDateFrom').datetimepicker({
			format : 'Y-m-d h:m:s',

		});
		$('#machineDownDateTo').datetimepicker({
			format : 'Y-m-d H:m:s',

		});

		$('#engineerAttendedCall').datetimepicker({
			format : 'Y-m-d H:m:s',

		});
	</script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>