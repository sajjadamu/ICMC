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
<title>ICICI : Process Entry</title>

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
	var cashSource = $('input[name=cashSource]:checked').val();

	function SavePrint() {
		$(':input[type="button"]').prop('disabled', true);
		addHeaderJson();
		var cashSource = $('input[name=cashSource]:checked').val();
		var bundle = $('#bundle').val();
		if (bundle == undefined || bundle == "") {
			$(':input[type="button"]').prop('disabled', false);
			alert("Please Inter Bundle");
			return false;
		}
		if (cashSource == undefined || cashSource == null) {
			$(':input[type="button"]').prop('disabled', false);
			alert("Please Select Bundle");
			return false;
		} else {
			var machine = {
				"machineNo" : $('input[name=machineNo]:checked').val(),
				"processAction" : $('input[name=processAction]:checked').val(),
				"currencyType" : $('input[name=currencyType]:checked').val(),
				"denomination" : $('input[name=denomination]:checked').val(),
				"binCategoryType" : $('input[name=binCategoryType]:checked')
						.val(),
				"bundle" : $('#bundle').val(),
				"bin" : $('#bin').val(),
				"total" : $('#total').val(),
				"cashSource" : $('input[name=cashSource]:checked').val(),
			}
			$.ajax({
				type : "POST",
				contentType : 'application/json; charset=utf-8',
				dataType : 'json',
				url : "././QRPathProcess",
				data : JSON.stringify(machine),
				success : function(response) {
					console.log(machine);
					alert("Success..");
					window.location = '././processEntry';
				},
				error : function(e) {
					alert('Error: ' + e.responseJSON.message);
					window.location = '././processEntry';
				}
			});
		}
	}

	function returnBackToVault() {
		if (confirm('Are you sure you want to return unprocessed cash, back to vault?')) {
			window.location = '././returnBackToVault';
		}
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
						<div class="panel-heading">
							<ul>
								<li><a href="././viewProcess"><i
										class="fa fa-table fa-fw"></i> View Processed Data</a></li>
							</ul>
							Processing Output
						</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<!--<form role="form">-->
									<form:form id="processEntry" name="processEntry" action="#"
										method="post" modelAttribute="user" autocomplete="off">

										<input type="hidden" id="recordID">

										<div class="form-group">
											<label>Cash Processed By</label><br>
											<form:radiobuttons items="${processActionList}"
												path="processAction" id="processAction" name="processAction"
												checked="true" />
										</div>

										<div class="form-group">
											<label>Category</label><br>
											<form:radiobutton path="binCategoryType" id="binCategoryType"
												name="binCategoryType" checked="true" value="BIN" />
											<span class="deno-value"><b>BIN</b></span>

											<form:radiobutton path="binCategoryType" id="binCategoryType"
												name="binCategoryType" value="BOX" />
											<span class="deno-value"><b>BOX</b></span>
										</div>

										<div class="form-group">
											<label>Currency Type</label><br>
											<form:radiobutton path="currencyType" id="currencyType"
												name="currencyType" value="ATM" checked="true" />
											<span class="deno-value"><b>ATM</b></span>

											<form:radiobutton path="currencyType" id="currencyType"
												name="currencyType" value="ISSUABLE" />
											<span class="deno-value"><b>ISSUABLE</b></span>

											<form:radiobutton path="currencyType" id="currencyType"
												name="currencyType" value="SOILED" />
											<span class="deno-value"><b>SOILED</b></span>

											<form:radiobutton path="currencyType" id="currencyType"
												name="currencyType" value="UNPROCESS"
												onclick="returnBackToVault()" />
											<span class="deno-value"><b>Return Back To Vault</b></span>
										</div>

										<div class="form-group">
											<label>Denomination</label><br>
											<form:radiobuttons items="${denominationList}"
												itemLabel="denomination" itemValue="denomination"
												path="denomination" id="denomination" name="denomination"
												checked="true" />
										</div>

										<div class="form-group">
											<label>Bundle</label>
											<form:input path="bundle" id="bundle" name="bundle"
												cssClass="form-control" />
										</div>

										<input type="button" id="print" name="print"
											value="Save AND Print QR"
											onclick="SavePrint();"
											class="btn btn-default qr-button">
									</form:form>
								</div>
								<div id="printSection" style="display: none;"></div>
								<!-- /.col-lg-6 (nested) -->

								<div class="col-lg-6"></div>
								<!-- /.col-lg-6 (nested) -->
							</div>
							<jsp:include page="pendingBundleSummary.jsp" />
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
							processAction : {
								required : true,
							},
							binCategoryType : {
								required : true,
							},
							currencyType : {
								required : true,
							},
							denomination : {
								required : true,
							},
							bundle : {
								required : true,
							}
						},

						messages : {
							processAction : {
								required : "Please Select Processed By",
							},
							binCategoryType : {
								required : "Please Select BIN or BOX",
							},
							currencyType : {
								required : "Please Select Currency Type",
							},
							denomination : {
								required : "Please Select Denomination",
							},
							bundle : {
								required : "Please Enter Bundle",
							}
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