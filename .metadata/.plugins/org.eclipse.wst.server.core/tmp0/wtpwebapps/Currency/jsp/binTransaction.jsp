<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="en">

<head>
<link rel="shortcut icon" href="./resources/logo/yes_favicon.ico"
	type="image/x-icon">
<script src="./js/jquery-1.12.0.min.js"></script>
<script src="./js/unprocess.js"></script>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Bin Transaction Upload</title>

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
	data-genuitec-file-id="wc1-70"
	data-genuitec-path="/Currency/src/main/webapp/jsp/binTransaction.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-70"
		data-genuitec-path="/Currency/src/main/webapp/jsp/binTransaction.jsp">
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
						<div class="panel-heading">Upload Chest Master Data</div>
						<div class="panel-body">
							<div class="row">
								<!--<form role="form">-->
								<form:form id="chestMaster" name="chestMaster"
									action="uploadChestMaster" method="post"
									enctype="multipart/form-data" modelAttribute="user">
									<div align="center" style="color: white; background: green;">
										<b>${uploadMsg}</b>
									</div>
									<br>
									<div align="center" style="color: red;">
										<b>${errorMsgChestMaster}</b>
									</div>
									<br>

									<div class="col-lg-6 form-group">
										<label>Choose Chest Master CSV File</label> <input type="file"
											id="file" name="file" Class="form-control" /> <a
											href="${documentFilePath}/ChestMaster.csv" download>Download
											CSV Format</a>
									</div>

									<div class="col-lg-12">
										<button style="width: 99px;" value="Details"
											class="btn btn-default" type="submit">Upload</button>
									</div>
								</form:form>
								<!-- /.col-lg-6 (nested) -->
								<!-- /.col-lg-6 (nested) -->
							</div>
							<!-- /.row (nested) -->
						</div>
						<!-- /.panel-body -->
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">Upload Bin Transaction Data</div>
						<div class="panel-body">
							<div class="row">
								<!--<form role="form">-->
								<form:form id="bintransaction" name="bintransaction"
									action="uploadBinTransaction" method="post"
									enctype="multipart/form-data" modelAttribute="user">

									<div align="center" style="color: white; background: green;">
										<b>${successMsg}</b>
									</div>
									<br>
									<div align="center" style="color: red;">
										<b>${errorMsg}</b>
									</div>
									<br>

									<div class="col-lg-6 form-group">
										<label>Choose Bin Transaction CSV File</label> <input
											type="file" id="file" name="file" Class="form-control" /> <a
											href="${documentFilePath}/BinTransaction.csv" download>Download
											CSV Format</a>
									</div>

									<div class="col-lg-12">
										<button style="width: 99px;" value="Details"
											class="btn btn-default" type="submit">Upload</button>
									</div>
								</form:form>
								<!-- /.col-lg-6 (nested) -->
								<!-- /.col-lg-6 (nested) -->
							</div>
							<!-- /.row (nested) -->
						</div>
						<!-- /.panel-body -->
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">Upload Branch Receipt Data</div>
						<div class="panel-body">
							<div class="row">
								<!--<form role="form">-->
								<form:form id="branchReceipt" name="branchReceipt"
									action="uploadBranchReceipt" method="post"
									enctype="multipart/form-data" modelAttribute="user">

									<div align="center" style="color: white; background: green;">
										<b>${successMsgForBR}</b>
									</div>
									<br>
									<div align="center" style="color: red;">
										<b>${errorMsgForBR}</b>
									</div>
									<br>

									<div class="col-lg-6 form-group">
										<label>Choose Branch Receipt CSV File</label> <input
											type="file" id="file" name="file" Class="form-control" /> <a
											href="${documentFilePath}/branchReceipt.csv" download>Download
											CSV Format</a>
									</div>

									<div class="col-lg-12">
										<button style="width: 99px;" value="Details"
											class="btn btn-default" type="submit">Upload</button>
									</div>
								</form:form>
								<!-- /.col-lg-6 (nested) -->
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

	<script type="text/javascript">
	$(function() {
	  $("form[name='bintransaction']").validate({
	    rules: {
	    	file: "required",
	    },
	    messages: {
	    	file: "Please Choose a File for Upload",
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