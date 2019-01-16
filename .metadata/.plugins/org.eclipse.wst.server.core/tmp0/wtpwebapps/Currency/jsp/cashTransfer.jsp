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
<title> Cash Transfer</title>

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
<script>
    $(function()
{
  $('#btnsubmit').on('click',function()
  {
    $(this).val('Submit')
      .attr('disabled','disabled');
  
  });
  
  
});
    </script>

<script type="text/javascript">
	function getRadioButtonValue() {
		addHeaderJson();
		
		var radioButtonValue = $('input[name=cashTransfer]:checked').val();

		//clear values after click on radio button
		$('#bundle').val('');
		$('#bin').val('');
		$('#binFromMaster').val('');
		$('#remarks').val('');
		//End clear value code
		$.ajax({
			type : "POST",
			url : "././getbinOrBox",
			data : "radioButtonValue=" + radioButtonValue,
			success : function(response) {
				var newStr = response.toString();
				console.log("newStr  "+newStr);
				var data = newStr.split(",");
				var option = '<option value="">Select Bin/Box</option>';
				for (i = 0; i < data.length; i++) {
					option += '<option value="' + data[i].trim() + '">'
							+ data[i].trim() + '</option>';
				}
				$('#bin').html(option);
			},
			error : function(e) {
				alert('Bin Error: ' + e);
			}
		});
	}
	
	function binOrBox() {
		addHeaderJson();
		var radioButtonValue = $('input[name=cashTransfer]:checked').val();
		var binOrBox = $('#bin').val();
		var bundle = $('#bundle').val();
		$.ajax({
			type : "POST",
			url : "././getbinOrBoxFromMaster",
			data : "binOrBox=" + binOrBox + "&bundle=" + bundle
					+ "&radioButtonValue=" + radioButtonValue,
			success : function(response) {
				var newStr = response.toString();
				var data = newStr.split(",");
				var option = '<option value="">Select Bin/Box</option>';
				for (i = 0; i < data.length; i++) {
					option += '<option value="' + data[i].trim() + '">'
							+ data[i].trim() + '</option>';
				}
				$('#binFromMaster').html(option);
			},
			error : function(e) {
				alert('Bin Error: ' + e);
			}
		});
	}
	function getBindetails() {
		addHeaderJson();
		var binOrBox = $('#bin').val();
		$.ajax({
			type:"POST",
			url:"././getBinDetailsFromcashTransfer",
			data:"binOrBox="+binOrBox,
			success:function(response){
				
				$("#category").html(response.binType);
				
			},
			error:function(e){
				alert('Bin Details Error: ' + e);
			}
			
		});
		
	} 
	

	function getbundle() {
		addHeaderJson();
		var binOrBox = $('#bin').val();
		$.ajax({
			type : "POST",
			url : "././getBundleFromDBForCashTransfer",
			data : "binOrBox=" + binOrBox,
			success : function(response) {
				var newStr = response.toString();
				//alert(newStr)
				$('#bundle').val(newStr);
			},
			error : function(e) {
				alert('Bundle Error: ' + e);
			}
		});
	}

	function cashTransferFromMasterToTransaction() {
		addHeaderJson();
		var binOrBox = $('#bin').val();
		var bundle = $('#bundle').val();
		var binFromMaster = $('#binFromMaster').val();
		var remarks = $('#remarks').val();
		var reason = $('input[name=reason]:checked').val();
		var radioButtonValue = $('input[name=cashTransfer]:checked').val();
		
		if(binOrBox != "" && bundle != "" && binFromMaster != "" && reason != ""  && radioButtonValue != ""){
			 	$.ajax({
			type : "POST",
			url : "././transferCash",
			data : "binOrBox=" + binOrBox + "&bundle=" + bundle
					+ "&binFromMaster=" + binFromMaster + "&remarks=" + remarks
					+ "&reason=" + reason + "&radioButtonValue="
					+ radioButtonValue,
			success : function(response) {
				alert("success")
			},
			error : function(e) {
				alert('Bin Error: ' + e);
			}
		}); 
		}
		else{
			alert("Please Select proper data");
		}
		 
	

	}
</script>

<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-78"
	data-genuitec-path="/Currency/src/main/webapp/jsp/cashTransfer.jsp">

	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-78"
		data-genuitec-path="/Currency/src/main/webapp/jsp/cashTransfer.jsp">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">Cash Transfer</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<!--<form role="form">-->
									<div class="form-group">
										<table>
											<tr>
												<td><input name="cashTransfer" id="binToBox"
													type="radio" value="binToBox"
													onclick="getRadioButtonValue()"></td>
												<td><b> Bin To Box </b></td>
												<td><input name="cashTransfer" id="boxToBin"
													type="radio" value="boxToBin"
													onclick="getRadioButtonValue()"></td>
												<td><b> Box To Bin </b></td>
												<td><input name="cashTransfer" id="binToBin"
													type="radio" value="binToBin"
													onclick="getRadioButtonValue()"></td>
												<td><b> Bin To Bin </b></td>
												<td><input name="cashTransfer" id="boxToBox"
													type="radio" value="boxToBox"
													onclick="getRadioButtonValue()"></td>
												<td><b> Box To Box </b></td>
											</tr>
										</table>
									</div>

									<div class="form-group">
										<label>Choose Reason</label> <input type="radio" name="reason"
											id="reason" checked="checked" value="defaulty">Faulty

										<input type="radio" name="reason" id="reason" value="partial">Partially
									</div>

									<div class="form-group">
										<label>Bin/Box</label> <select id="bin"
											onchange="binOrBox();getbundle();getBindetails()">
											<option value="">Select Bin/Box</option>
										</select>
									</div>
									<div class="form-group">
										<label>Category</label>
										<div id="category"></div>

									</div>
									<div class="form-group">
										<label>Bundle</label> <input type="text" name="bundle"
											id="bundle" readonly="readonly">
									</div>

									<div class="form-group">
										<label>Bin/Box</label> <select id="binFromMaster">
											<option value="">Select Bin/Box</option>
										</select>
									</div>

									<div class="form-group">
										<label>Remarks</label> <input type="text" name=remarks
											id="remarks">
									</div>

									<div class="form-group">
										<!-- 										<input type="button" value="Submit"
											onclick="cashTransferFromMasterToTransaction()"> -->
										<input type="button" value="Submit" id="btnsubmit"
											onclick="cashTransferFromMasterToTransaction()">
									</div>

								</div>
								<div class="col-lg-6"></div>
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

	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>