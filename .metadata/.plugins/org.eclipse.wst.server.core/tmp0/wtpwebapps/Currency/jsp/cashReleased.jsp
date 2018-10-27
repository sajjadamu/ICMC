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
<title>ICICI : Cash Released</title>
<script src="./resources/js/jquery.js"></script>
<script src="./js/qrCode_scan.js"></script>


<!-- ------------------------ Style for Error -------------------------- -->
<style type="text/css">
.msg-info, .msg-success, .msg-warning, .msg-error, .msg-validation {
	border: 1px solid;
	margin: 10px 0px;
	padding: 15px 10px 15px 50px;
	background-repeat: no-repeat;
	background-position: 10px center;
}

.msg-info {
	color: #00529B;
	background-color: #BDE5F8;
	background-image: url('img/info.png');
}

.msg-success {
	color: #4F8A10;
	background-color: #DFF2BF;
	background-image: url('img/success.png');
}

.msg-warning {
	color: #9F6000;
	background-color: #FEEFB3;
	background-image: url('img/warning.png');
}

.msg-error {
	color: #D8000C;
	background-color: #FFBABA;
	background-image: url('img/error.png');
}

.msg-validation {
	color: #D63301;
	background-color: #FFCCBA;
	background-image: url('img/validation.png');
}
</style>
<!-- ------------------------ Style for Error -------------------------- -->
<script type="text/javascript">

	function load_BranchList_BundleDetails() {
		hideAllMsgDiv();
		addHeader();
		$("#temp").focus();
		var id = $('input[name=solId]:checked').val()
				$.ajax({
					type : "POST",
					url : "././bundleDetails",
					data : "id=" + id,
					success : function(response) {
						var newStr = JSON.stringify(response).replace(/]|[[]/g,
								'') // Remove Array Brackets
								console.log("sajjad");
								console.log(newStr);
						var trHTML = '';
						var trHTML = '<tr><td><input type="text" id="temp" onChange="comparePrice()" autofocus="autofocus" name="temp"></td></tr><tr><th>Category</th><th width="10%"></th><th>Denomination</th><th width="10%"></th><th>Bundle</th><th width="10%"></th><th>QR</th><th></th></tr>';
						
						var count = 0;
						$
								.each(
										response,
										function(i, item) {
											for (var i = 0; i < item.bundle; i++) {
												count++;
												trHTML += '<tr><input type="hidden" value="'+item.id+'" id="id"><td id="category'+count+'">'
														+ item.category
														+ '</td><td width="10%"></td><td id="denomination'+count+'">'
														+ item.denomination
														+ '</td><td width="10%"></td><td>'
														+ 1
														+ '</td><td width="10%"></td><td><input type=text  readonly="true"  id="qr'+count+'"></td><td><span class="tick-msg"></td></tr>';
											}
										});
						/* trHTML += '<tr><td><input type="submit" value="Save" onclick="saveRecord('+i+');></td></tr>'; */
						$('#records_table').empty();
						$('#records_table').append(trHTML);
					},
					error : function(e) {
						alert('Exception: ' + e);
					}
				});
		
		$("#temp").focus();
	}
	
	function saveRecord() {
		addHeader();
		var rowCount = $('table#records_table tr:last').index() + 1;
		var SASReleasedList = [];
		var id = $('input[name=solId]:checked').val();
		
		var tablnObj = $("#records_table").find("tr:gt(1)");
		var tablRowLen = $(tablnObj).length;
		var counter = 0;
		var rowTraverse =1;
		$("#records_table").find("tr:gt(1)").each(function(){
			var category = $(this).find("td:eq(0)").text();
			var denomination = $(this).find("td:eq(2)").text();
			var outputTxt= category+"|"+denomination;
			var bundle= $(this).find("td:eq(4)").text();
			var output = $(this).find("td:eq(6)").find("input").val();

			var sasReleaseObj = {
					"category" : category,
					"denomination" : denomination,
					"bundle" : bundle,
					"QR" : output,
					"id" : id
				};
				SASReleasedList.push(sasReleaseObj);
		});
		
		var sasReleaseWrapper = {
			"SASReleasedList" : SASReleasedList
		}
		
			$.ajax({
				type : "POST",
				contentType : 'application/json; charset=utf-8',
				dataType : 'json',
				url : "././updateSASBundleDetails",
				data : JSON.stringify(sasReleaseWrapper),
				success : function(response) {
					alert('record submit.');
					window.location = '././cashReleased';
				},
				error : function(e) {
					alert('Error: ' + e);
				}
			});
		
		/* if(flag==true)
			{
		$.ajax({
			type : "POST",
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			url : "././updateSASBundleDetails",
			data : JSON.stringify(sasReleaseWrapper),
			success : function(response) {
				alert('record submit.');
				window.location = '././cashReleased';
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	} */
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
	data-genuitec-file-id="wc1-77"
	data-genuitec-path="/Currency/src/main/webapp/jsp/cashReleased.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-77"
		data-genuitec-path="/Currency/src/main/webapp/jsp/cashReleased.jsp">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">Cash Released</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-8">
									<!--<form role="form">-->
									<form:form id="cashPaymentReleased"
										action="cashPaymentReleased" method="post"
										modelAttribute="user">

										<div class="form-group">
											<label>Branch :</label><br>
											<form:radiobuttons items="${sas}" itemLabel="branch"
												itemValue="id" path="solId" id="solId" name="solId"
												onclick="load_BranchList_BundleDetails()" />
										</div>
										<div id="container">
											<table id="records_table">
											</table>
										</div>
										<input type="button" id="buttonID" value="Submit" disabled
											onclick="saveRecord()">
										<div class="msg-info">Info message</div>
										<div class="msg-success">Successful operation message</div>
										<div class="msg-warning">Warning message</div>
										<div class="msg-error">Error message</div>
										<div class="msg-validation">Validation Message</div>

										<!-- <button type="submit" class="btn btn-default" value="Details"
											style="width: 99px;">Save</button> -->
									</form:form>
								</div>
								<!-- <div id="printSection" style="display: none;"></div> -->
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
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>