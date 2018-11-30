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

<script type="text/javascript">
	function jsFunction(value, id) {
		var elem = document.getElementById("capacity"); // Get text field
		elem.value = value;
		var label = document.getElementById(id)[document.getElementById(id).selectedIndex].innerHTML;

		var BinHiddenNumberelem = document.getElementById("abchidden"); // Get text field
		BinHiddenNumberelem.value = label;

	}
</script>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>ICICI : IRV</title>

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

<script type="text/javascript">
    function ajaxCustodianName() {  
    	addHeaderJson();  
    	var vendor = $('#vendor').val();
		  var radio;
		  $.ajax({  
		    type: "POST", 
		    url: "././custodianName",
		    data: "vendor=" + vendor,
		    success: function(response){  
		      var newStr = response.substring(1, response .length-1); // Remove Array Brackets
		      radio=newStr.split(",");
		      var radioBtn ="<b>Custodian :</b> ";
		      for(var i=0;i<radio.length;i++){
		          $('#info').html(newStr);
			      $('#vendor').val(vendor);
			      $('#custodian').val(radio[i]);
	        	    radioBtn +=' <input type="radio" name="custodian" value="'+$.trim(radio[i])+'" >'+$.trim(radio[i]);
	        	    $('#custodian').html(radioBtn);
		      }
		    }, 
		    error: function(e){  
		      alert('Custodian Name: ' + e);  
		    }  
		  });  
		}  
    </script>

<script type="text/javascript">
    function ajaxvehicle() {  
    	addHeaderJson();  
    	var vendor = $('#vendor').val();
		  var radio;
		  $.ajax({  
		    type: "POST", 
		    url: "././vehicleNumber",
		    data: "vendor=" + vendor,
		    success: function(response){  
		    	var newStr = response.substring(1, response .length-1); // Remove Array Brackets
		    	radio=newStr.split(",");
		    	var radioBtn ="<b>Vehicle :</b> ";
		      for(var i=0;i<radio.length;i++){
		          $('#info').html(newStr);
			      $('#vendor').val(vendor);
			      $('#vehicle').val(radio[i]);
			      radioBtn +=' <input type="radio" name="vehicle" value="'+$.trim(radio[i])+'" >'+$.trim(radio[i]);
			      $('#vehicle').html(radioBtn);
		      }
		     
		    }, 
		    error: function(e){  
		      alert('Vehice No: ' + e);  
		    }  
		  });  
		}  
    
    </script>
<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-25"
	data-genuitec-path="/Currency/src/main/webapp/jsp/IRV.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-25"
		data-genuitec-path="/Currency/src/main/webapp/jsp/IRV.jsp">
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
						<div class="panel-heading">IRV</div>
						<div class="panel-body">

							<div class="row">

								<!--<form role="form">-->
								<form:form id="IRV" action="IRVEntry" method="post"
									modelAttribute="user">
									<div class="col-lg-6 form-group">
										<label>SR</label>
										<form:input path="SR" id="SR" cssClass="form-control" />
									</div>


									<div class="col-lg-6 form-group">
										<label>Sack Lock Number</label>
										<form:input path="sackLock" id="sackLock"
											cssClass="form-control" />
									</div>
									<div class="col-lg-6 form-group">
										<label>Account Number</label>
										<form:input path="accountNumber" id="accountNumber"
											cssClass="form-control" />
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
										<label>Vendor</label>
										<form:select path="vendor" id="vendor" cssClass="form-control"
											onchange="ajaxCustodianName(),ajaxvehicle()">
											<form:option value="">Select Vendor</form:option>
											<form:option value="Shiva Industrial Security Agency Pvt Ltd">Shiva Industrial Security Agency Pvt Ltd</form:option>
											<form:option value="Federal Security Pvt Ltd">Federal Security Pvt Ltd</form:option>
											<form:option value="SIS Prosegur Holdings Pvt Ltd">SIS Prosegur Holdings Pvt Ltd</form:option>
											<form:option value="Premier Shield Pvt Ltd">Premier Shield Pvt Ltd</form:option>
										</form:select>
									</div>
									<div class="col-lg-6 form-group" id="custodian"></div>
									<div class="col-lg-6 form-group" id="vehicle"></div>

									<div class="col-lg-6 form-group">

										<input type="text" id="orvdeno" name="orvdeno" value=""
											class="col-lg-6 form-group"><br />
										<div class="clearfix"></div>
										<div id="row">
											<div id="col-lg-12">
												<div id="container">

													<table id="table1">
														<tr>
															<td><strong>Denomination</strong></td>
															<td><strong>Bundle</strong></td>
															<td><strong>Total</strong></td>
														</tr>
													</table>
												</div>
											</div>
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