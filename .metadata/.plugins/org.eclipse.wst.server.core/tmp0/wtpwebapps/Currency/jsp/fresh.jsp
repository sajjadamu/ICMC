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
<title>ICICI : Fresh Entry</title>

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
    function ajaxBinForFresh() {  
    	addHeader();  
    	// get the form values  
		  var bundle = $('#bundle').val();
		  var denomination = $('input[name=denomination]:checked').val();
		  var total=bundle*1000*denomination;
		 // alert(denomination);
		  $.ajax({  
		    type: "POST", 
		    url: "././binForFresh",
		    data: "bundle=" + bundle+"&denomination="+denomination,
		    success: function(response){  
		      // we have the response  
		      $('#info').html(response);
		      $('#bundle').val(bundle);
		      $('#denomination').val(denomination);
		      $('#total').val(total);
		      $('#bin').val(response);
		      // alert("Response =="+response);
		    }, 
		    error: function(e){  
		      alert('Bin For Fresh Error: ' + e);  
		    }  
		  });  
		}  
    </script>
<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-146"
	data-genuitec-path="/Currency/src/main/webapp/jsp/fresh.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-146"
		data-genuitec-path="/Currency/src/main/webapp/jsp/fresh.jsp">
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
						<div class="panel-heading">Fresh Entry</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-12">
									<!--<form role="form">-->
									<form:form id="fresh" action="freshEntry" method="post"
										modelAttribute="user">

										<div class="form-group">
											<label>Denomination</label>
											<form:radiobutton path="denomination" id="denomination"
												value="1000" />
											<span class="deno-value">1000</span>
											<form:radiobutton path="denomination" id="denomination"
												value="500" />
											<span class="deno-value">500</span>
											<form:radiobutton path="denomination" id="denomination"
												value="100" />
											<span class="deno-value">100</span>
											<form:radiobutton path="denomination" id="denomination"
												value="50" />
											<span class="deno-value">50</span>
											<form:radiobutton path="denomination" id="denomination"
												value="20" />
											<span class="deno-value">20</span>
											<form:radiobutton path="denomination" id="denomination"
												value="10" />
											<span class="deno-value">10</span>
											<form:radiobutton path="denomination" id="denomination"
												value="5" />
											<span class="deno-value">5</span>
											<form:radiobutton path="denomination" id="denomination"
												value="2" />
											<span class="deno-value">2</span>
											<form:radiobutton path="denomination" id="denomination"
												value="1" />
											<span class="deno-value">1</span>
										</div>

										<div class="form-group">
											<label>Coins</label>
											<form:radiobutton path="coins" id="coins" value="1" />
											<span class="deno-value">1</span>
											<form:radiobutton path="coins" id="coins" value="2" />
											<span class="deno-value">2</span>
											<form:radiobutton path="coins" id="coins" value="5" />
											<span class="deno-value">5</span>
											<form:radiobutton path="coins" id="coins" value="10" />
											<span class="deno-value">10</span>
										</div>

										<div class="col-lg-6 form-group">
											<label>Order Date</label>
											<form:input path="order_date" id="order_date"
												Class="form-control" />
										</div>

										<div class="col-lg-6 form-group">
											<label>RBI Order Number</label>
											<form:input path="rbiOrderNo" id="rbiOrderNo"
												Class="form-control" />
										</div>
										<div class="col-lg-6 form-group">
											<label>Vehicle Number</label>
											<form:input path="vehicleNumber" id="vehicleNumber"
												Class="form-control" />
										</div>
										<div class="col-lg-6 form-group">
											<label>Potdar Name And PF Index Number</label>
											<form:input path="potdarName" id="potdarName"
												Class="form-control" />
										</div>
										<div class="col-lg-6 form-group">
											<label>Escort Officer Name</label>
											<form:input path="escort_officer_name"
												id="escort_officer_name" Class="form-control" />
										</div>

										<div class="col-lg-6 form-group">
											<div class="form-group">
												<label>Bundle</label>

												<form:input path="bundle" id="bundle" Class="form-control"
													onkeyup="ajaxBinForFresh()" />
											</div>
										</div>
										<div class="col-lg-6 form-group">
											<div class="form-group">
												<label>Bin Number</label>

												<form:input path="bin" id="bin" Class="form-control" />
											</div>
										</div>
										<div class="col-lg-6 form-group">
											<div class="form-group">
												<label>Total</label>

												<form:input path="total" id="total" Class="form-control" />
											</div>
										</div>
										<div class="col-lg-4 form-group">
											<button type="submit"
												class="btn btn-lg btn-success btn-block" value="Details">Save
												And Print QR</button>
										</div>
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
	<script src="./resources/bower_components/jquery/dist/jquery.min.js"></script>

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