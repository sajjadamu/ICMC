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
<link rel="shortcut icon" href="./resources/logo/yes_favicon.ico"
	type="image/x-icon">

<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="./js/jquery.validate.min.js"></script>

<title> Auditor Indent</title>

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

<script type="text/javascript">
    function submitChecking(){
    	var denomination = $('input[name=denomination]:checked').val();
		var currencyType = $('#currencyType').val();
		var binNumber = $('#binNumber').val();
		var bundle = $('#bundle').val();
		if(denomination == undefined ||denomination==null || currencyType==undefined || currencyType==0 || binNumber == undefined ||binNumber==null || bundle==undefined || bundle==0){
			alert("Please Select All data");
			return false;
		}else{
			return true;
		}
    }
    
    
    function binData() {
    	//alert("hello");
		addHeaderJson();
		var denomination = $('input[name=denomination]:checked').val();
		var currencyType = $('#currencyType').val();
		if(denomination == undefined ||denomination==null){
			alert("Please Select Denomination:");
			
		}else{
		if(currencyType == undefined || currencyType == 0){
			
		}else{
		$.ajax({
			type : "POST",
			url : "././getbinFromBinTransaction",
			data : "denomination=" + denomination + "&currencyType=" + currencyType,
					
			success : function(response) {
				var newStr = response.toString();
				var data = newStr.split(",");
				var option = '<option value="">Select Bin</option>';
				for (i = 0; i < data.length; i++) {
					option += '<option value="' + data[i].trim() + '">'
							+ data[i].trim() + '</option>';
				}if(data.length-1 >0){
					$('#binNumber').html(option);
				}else{
					alert("Bin is not available for this Denomination and currency type");
				}
				
			},
			error : function(e) {
				alert('Bin Error: ' + e);
			}
		});
		}
    }
	}
    
    
    </script>

<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-60"
	data-genuitec-path="/Currency/src/main/webapp/jsp/auditorIndent.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-60"
		data-genuitec-path="/Currency/src/main/webapp/jsp/auditorIndent.jsp">
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
						<!-- <div class="panel-heading">Add/Upload Branch</div> -->
						<div class="panel-heading">
							<ul>
								<li><a href="././viewAuditorIndent"><i
										class="fa fa-table fa-fw"></i> View Indent List</a></li>
							</ul>
							Indent Request
						</div>

						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<!--<form role="form">-->
									<form:form id="userPage" name="userPage"
										action="saveAuditorIndent" method="post" modelAttribute="user"
										autocomplete="off">

										<div class="form-group">
											<label>Denomination</label><br>
											<form:radiobuttons items="${denominationList}"
												itemLabel="denomination" itemValue="denomination"
												path="denomination" id="denomination" name="denomination"
												onclick="binData()" />
										</div>

										<div class="form-group">
											<label>Currency Type</label>
											<form:select path="binType" id="currencyType"
												cssClass="form-control" onchange="binData()">
												<option value="0">Select Currency Type</option>
												<form:options items="${currencyTypeList}" />
											</form:select>
										</div>

										<%-- <div class="form-group">
											<label>Bin Number</label>
											<form:input path="binNumber" id="binNumber" name="binNumber"
												maxlength="45" cssClass="form-control" />
										</div> --%>

										<div class="form-group">
											<label>Bin Number</label>
											<form:select path="binNumber" id="binNumber"
												cssClass="form-control">
												<option>Select Bin</option>
											</form:select>
										</div>

										<div class="form-group">
											<label>Bundle</label>
											<form:input path="bundle" id="bundle" name="bundle"
												maxlength="45" cssClass="form-control" />
										</div>

										<button type="submit" class="btn btn-lg btn-success btn-block"
											onclick="return submitChecking()" value="Details">Submit</button>
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

	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>