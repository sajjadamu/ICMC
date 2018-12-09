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
<title>ICICI :Cash Processing</title>

<script type="text/javascript" src="../js/jquery-1.12.0.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#member").keyup(function(){
	//$("#member").change(function() { 
    var val = $("#member").val();

var html ;
	for (i = 0; i < val; i++) {
html += '<tr id="main'+i+'">'+
		'<td><input type="text" id="Denomination'+i+'" class="form-control input-margin" name="Denomination" value="" ></td>'+
		'<td><input type="text" id="NoteSerialNumber'+i+'" class="form-control input-margin" name="NoteSerialNumber" value=""></td>'+
		'<td><input type="text" id="Value'+i+'"  class="form-control input-margin" name="Value" value="" ></td>'+
		'<td><input type="text" id="SRNumber'+i+'"  class="form-control input-margin" name="SRNumber" value="" ></td>'+
		'<td><input type="hidden" id="recordID'+i+'" class="form-control input-margin" name="Bin" value="" ></td>'+
		/* '<td class="qr-button"><input type="button" id="print'+i+'" class="btn btn-default qr-button"  name="print"  value="Save and Print QR" onclick="#"></td>'+ */
 	'</tr>';
} 
	$('#table1').append(html);
    });
});


$(document).ready(function() {
    $("#descrip input[type ='radio']").click(function() {
      
    	var test = $(this).val();
    	if(test == 'mutilated'){
            $("#mutil").show();
    	}else{
    	     $("#mutil").hide();
    	}

    });
});
	
	
</script>
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

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>
<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-76"
	data-genuitec-path="/Currency/src/main/webapp/jsp/cashProcessing.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-76"
		data-genuitec-path="/Currency/src/main/webapp/jsp/cashProcessing.jsp">
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
						<div class="panel-heading">Discrepancy Processing</div>
						<div class="panel-body">
							<div class="row">
								<!--<form role="form">-->
								<form:form id="cash" action="saveCashProcessing" method="post"
									modelAttribute="user" enctype="multipart/form-data">
									<div class="col-lg-6 form-group">
										<label>Machine No</label>
										<form:input path="machineNo" id="machineNo"
											cssClass="form-control" />
									</div>
									<div class="col-lg-6 form-group">
										<label>Date</label>
										<form:input path="date" id="date" cssClass="form-control" />
									</div>
									<div class="col-lg-6 form-group">
										<label>Sol ID</label>
										<form:input path="solId" id="solId" cssClass="form-control"
											onkeyup="doAjaxPostForBranch()" />
									</div>
									<div class="col-lg-6 form-group">
										<label>Branch</label>
										<form:input path="branch" id="branch" cssClass="form-control"
											readonly="true" />
									</div>

									<div class="col-lg-6 form-group">
										<label>Upload Photo</label> <input type="file" name="file" />
									</div>
									<div class="col-lg-6 form-group">
										<label>Account/Teller/CAM</label> <select
											name="account_teller_cam" id="account_teller_cam"
											class="form-control deno-figure-select">
											<option value="Account">Select Option</option>
											<option value="Account">Account</option>
											<option value="Teller">Teller</option>
											<option value="CAM">CAM</option>
										</select>
									</div>
									<div class="col-lg-6 form-group">
										<label>Customer name OR Account number</label>
										<form:input path="account_teller_cam_value"
											id="account_teller_cam_value" cssClass="form-control" />
									</div>
									<div class="col-lg-6 form-group" id="descrip">
										<label>Discrepancy</label>
										<form:radiobutton path="descripancy" id="descripancy"
											value="shortage" />
										<span class="deno-value">Shortage</span>
										<form:radiobutton path="descripancy" id="descripancy"
											value="excess" />
										<span class="deno-value">Excess</span>
										<form:radiobutton path="descripancy" id="descripancy"
											value="fake" />
										<span class="deno-value">Fake</span>
										<form:radiobutton path="descripancy" id="descripancy"
											value="mutilated" name="mutilated" />
										<span class="deno-value">Mutilated</span>
									</div>
									<div class="desc" id="mutil" style="display: none;">
										<!-- <div class="col-lg-6 form-group"> -->
										<label>Discrepancy</label>
										<form:radiobutton path="descripancy" id="descripancy"
											value="shortage" />
										<span class="deno-value">Full Value</span>
										<form:radiobutton path="descripancy" id="descripancy"
											value="excess" />
										<span class="deno-value">Half Value</span>
										<form:radiobutton path="descripancy" id="descripancy"
											value="fake" />
										<span class="deno-value">Zero Value</span>
									</div>

									<div class="form-group">
										<label>Number of Entries</label> <input type="text"
											id="member" name="member" value="" class="form-control"><br />
										<div id="container">
											<table id="table1">
												<tr>
													<td><strong>Denomination</strong></td>
													<td><strong>Note Serial Number</strong></td>
													<td><strong>Value</strong></td>
													<td><strong>SR Number</strong></td>
												</tr>
											</table>
										</div>
									</div>

									<div class="col-lg-12">
										<button style="width: 99px;" value="Details"
											class="btn btn-default" type="submit">Save</button>
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
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>