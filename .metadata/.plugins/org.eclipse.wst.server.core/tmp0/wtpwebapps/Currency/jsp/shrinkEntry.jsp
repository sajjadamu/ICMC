<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="en">

<head>
<link rel="shortcut icon" href="./resources/logo/favicon.ico"
	type="image/x-icon">
<script type="text/javascript" src="./js/jquery-1.12.0.min.js"></script>
<script src="./js/unprocess.js"></script>

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
<title>ICICI : Branch Receipt</title>

<script type="text/javascript">
$(document).ready(function(){
	   var readOnlyLength = $('#srNumber').val().length;
	 //$('#output').text(readOnlyLength);
	$('#srNumber').on('keypress, keydown', function(event) {
	    var $field = $(this);
	    if ((event.which != 37 && (event.which != 39) && (event.which ))
	        && ((this.selectionStart < readOnlyLength)
	        || ((this.selectionStart == readOnlyLength) && (event.which == 8)))) {
	        return false;
	    }
	});     
	
	
	 $("#srNumber").keydown(function (e) {
	        // Allow: backspace, delete, tab, escape, enter and .
	        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
	             // Allow: Ctrl+A, Command+A
	            (e.keyCode === 65 && (e.ctrlKey === true || e.metaKey === true)) || 
	             // Allow: home, end, left, right, down, up
	            (e.keyCode >= 35 && e.keyCode <= 40)) {
	                 // let it happen, don't do anything
	                 return;
	        }
	        // Ensure that it is a number and stop the keypress
	        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)){
	            e.preventDefault();
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

<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script></head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-190" data-genuitec-path="/Currency/src/main/webapp/jsp/shrinkEntry.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-190" data-genuitec-path="/Currency/src/main/webapp/jsp/shrinkEntry.jsp">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />
		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<ul>
								<li><a href="././viewShrink"><i
										class="fa fa-table fa-fw"></i>View Branch Receipt</a></li>
							</ul>
							Branch Receipt
						</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-10">
									<!--<form role="form">-->
									<form:form id="" action="" method="post" modelAttribute="user"
										autocomplete="off">

										<div class="form-group">
											<label id="err1" style="display: none; color: red">Please
												Enter solId</label> <label id="err11"
												style="display: none; color: red">Please Enter 4
												Digit Sol Id</label> <label>Sol ID</label>
											<form:input path="solId" name="solId" minlenght="4"
												maxlength="4" id="solId" cssClass="form-control"
												onkeyup="doAjaxPostForBranch()" />

											<label>Branch</label>
											<form:input path="branch" id="branch" cssClass="form-control"
												readonly="true" />
											<label id="err4" style="display: none; color: red">Please
												Enter Any valid Sol Id</label>
										</div>

										<div class="form-group">
											<label>SR Number</label>
											<form:input path="srNumber" id="srNumber" value="SR"
												minlenght="11" maxlength="11" cssClass="form-control" />
											<label id="err5" style="display: none; color: red">Please
												Enter SR Number</label> <label id="err6"
												style="display: none; color: red">SR Number should
												have 11 digits</label>
										</div>

										<div class="form-group">
											<label>Category</label><br>
											<form:radiobutton path="processedOrUnprocessed" id="processedOrUnprocessed" name="processedOrUnprocessed" checked="true" value="UNPROCESS" />
											<span class="deno-value"><b>UNPROCESS</b></span>
											<form:radiobutton path="processedOrUnprocessed" id="processedOrUnprocessed" name="processedOrUnprocessed" value="PROCESSED" />
											<span class="deno-value"><b>PROCESSED</b></span>
										</div> 

										<div class="form-group">
											<label>Number of Shrink Wraps</label> <input type="text"
												id="member" name="member" value="" class="form-control"><br />
											<div id="container">
												<table id="table1">
												</table>

												<label id="err2" style="display: none; color: red">Please
													Enter Valid Denomination</label> <label id="err3"
													style="display: none; color: red">Please Enter
													Packets</label>
											</div>
										</div>

										<div align="right">
											<button type="submit" class="btn btn-default" value="Details"
												style="width: 99px;" onclick="refresh()">Refresh</button>
										</div>

									</form:form>
									<div id="prncode" style="display: none;"></div>
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

	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>
