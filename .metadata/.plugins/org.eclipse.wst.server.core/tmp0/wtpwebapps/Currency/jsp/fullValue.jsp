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
<title>ICICI : FULL VALUE</title>

<script type="text/javascript" src="./js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="./js/jquery.validate.min.js"></script>
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

<script type="text/javascript">
	function SaveMutilated() {
		addHeader();
		var machine = {
			"binCategoryType" : $('input[name=binCategoryType]:checked').val(),
			"denomination" : $('input[name=denomination]:checked').val(),
			"bundle" : $('#bundle').val(),
		}
		$.ajax({
			type : "POST",
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			url : "././MutilatedProcess",
			data : JSON.stringify(machine),
			success : function(response) {
				alert("Success..");
				window.location = '././fullValue';
			},
			error : function(e) {
				alert('Error: ' + e.responseJSON.message);
			}
		});
	}

	function returnBackToVault() {
		if (confirm('Are you sure you want to return unprocessed cash, back to vault?')) {
			window.location = '././returnBackToVault';
		}
	}
</script>

<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script></head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-148" data-genuitec-path="/Currency/src/main/webapp/jsp/fullValue.jsp">

	<div id="wrapper" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-148" data-genuitec-path="/Currency/src/main/webapp/jsp/fullValue.jsp">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<ul>
								<li><a href="././viewMutilatedData"><i
										class="fa fa-table fa-fw"></i> View Mutilated Data</a></li>
							</ul>
							Mutilated Full Value
						</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<!--<form role="form">-->

									<form:form id="mitulatedFullValue" name="mitulatedFullValue"
										action="saveMutilatedFullValue" method="post"
										modelAttribute="user" autocomplete="off">
										<input type="hidden" id="recordID">

										<%-- <div class="form-group">
											<label>Category</label><br>
											<form:radiobutton path="binCategoryType" id="binCategoryType"
												name="binCategoryType" checked="true" value="BIN" />
											<span class="deno-value"><b>BIN</b></span>

											<form:radiobutton path="binCategoryType" id="binCategoryType"
												name="binCategoryType" value="BOX" />
											<span class="deno-value"><b>BOX</b></span>
										</div> --%>

										<div class="form-group">
											<label>Denomination</label><br>
											<form:radiobuttons items="${denominationList}"
												itemLabel="denomination" itemValue="denomination"
												path="denomination" id="denomination" name="denomination" />
										</div>

										<div class="form-group">
											<label>Pieces</label>
											<form:input path="bundle" id="bundle" name="bundle"
												cssClass="form-control" />
										</div>

										<!-- <input type="button" name="print" value="Save"
											onclick="SaveMutilated()" class="btn btn-default qr-button"> -->
											
											<!-- <input type="button" name="print" value="Save"
											class="btn btn-default qr-button"> -->
											
											<button type="submit" 
											class="btn btn-lg btn-success btn-block" value="Details">Submit</button>
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
	<!-- /#wrapper -->

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
			$("form[name='processEntry']").validate(
					{
						rules : {
							processAction : "required",
							binCategoryType : "required",
							currencyType : "required",
							denomination : "required",
							bundle : "required",
						},
						messages : {
							processAction : "Please Select Processed By",
							binCategoryType : "Please Select BIN or BOX",
							currencyType : "Please Select Currency Type",
							denomination : "Please Select Denomination",
							bundle : "Please Enter Bundle",
						},
						submitHandler : function(form) {
							var isValid = $("form[name='processEntry']")
									.validate().form();
							if (isValid) {
								form.submit();
							}
						}
					});
		});
	</script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>