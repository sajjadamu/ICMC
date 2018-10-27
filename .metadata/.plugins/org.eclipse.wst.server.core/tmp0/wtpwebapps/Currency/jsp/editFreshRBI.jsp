<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="en">

<head>
<link rel="shortcut icon" href="./resources/logo/favicon.ico"
	type="image/x-icon">
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

<script>
	function doAjaxPost() {  
	  // get the form values  
	  var Denomination = $('#denomination').val();
	  var Bundle = $('#bundle').val();
	  var total = Denomination*1000*Bundle;
	  $('#total').val(total);
}</script>

<script src="./resources/js/jQuery.print.js"></script>
<title>ICICI : Edit Fresh Data RBI</title>

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

<link rel="stylesheet" type="text/css"
	href="./resources/dist/css/style.css">

<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-116"
	data-genuitec-path="/Currency/src/main/webapp/jsp/editFreshRBI.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-116"
		data-genuitec-path="/Currency/src/main/webapp/jsp/editFreshRBI.jsp">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />
		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<ul>
								<li><a href="././viewFresh"><i
										class="fa fa-table fa-fw"></i>View Edit Fresh Data RBI</a></li>
							</ul>
							Edit Fresh Data RBI
						</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-8">
									<!--<form role="form">-->
									<form:form id="" action="updateFreshRBI" method="post"
										modelAttribute="user" autocomplete="off">

										<form:hidden path="id" />

										<%--  <div class="form-group">
											<label>Order Date</label>
											<form:input type="text" path="orderDate" id="orderDate" name="orderDate" 
												readonly="true" cssClass="form-control" />
										</div> 
									 --%>

										<%-- 	<div class=" form-group">
											<label>Order Date</label>
											 <form:input type="text"  path="orderDate"  readonly="true" name="orderDate" cssClass="form-control"/>
											 
										</div> --%>


										<div class="form-group">
											<label>RBI Order Number</label>
											<form:input path="rbiOrderNo" id="rbiOrderNo"
												name="rbiOrderNo" maxlength="45" readonly="true"
												cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Vehicle Number</label>
											<form:input path="vehicleNumber" id="vehicleNumber"
												name="vehicleNumber" cssClass="form-control" />

										</div>
										<div class="form-group">
											<label>Potdar Name And PF Index Number</label>
											<form:input path="potdarName" id="potdarName"
												name="potdarName" cssClass="form-control" />
											<label id="err4" style="display: none; color: red">Please
												Enter Potdar Name and PF Index Number</label>
										</div>

										<div class="form-group">
											<label>Escort Officer Name</label>
											<form:input path="escortOfficerName" id="escortOfficerName"
												name="escortOfficerName" cssClass="form-control" />

										</div>

										<div class="form-group">
											<label>Denomination</label>
											<form:input path="denomination" id="denomination"
												name="denomination" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Bundle</label>
											<form:input path="bundle" id="bundle" onkeyup="doAjaxPost()"
												name="bundle" cssClass="form-control" />
										</div>

										<div class="form-group">
											<label>Total</label>
											<form:input path="total" id="total" name="total"
												cssClass="form-control" readonly="true" />
										</div>



										<div class="form-group">
											<label>BOX</label>
											<form:input path="bin" id="bin" name="bin"
												cssClass="form-control" readonly="true" />
										</div>

										<%-- <div class="form-group">
											<label>Status</label>
											<form:select path="status" id="status" name="status" cssClass="form-control">
												<option value="RECEIVED">RECEIVED</option>
												<option value="CANCELLED">CANCELLED</option>
											</form:select>
										</div>
										 --%>
										<button type="submit" value="Details"
											class="btn btn-lg btn-success btn-block">Update</button>

									</form:form>
								</div>
								<div id="printSection" style="display: none;"></div>
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