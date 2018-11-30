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

<title>ICICI : DSB</title>

<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script> -->

<script type="text/javascript">
	var countrow = 0;
	var rowCount = 0;
	var dataId = 0;

	function doAjaxForAccountNumber() {
		//var token = $("meta[name='_csrf']").attr("content");
		//var header = $("meta[name='_csrf_header']").attr("content");
		addHeader();
		var name = $('#name').val();
		$.ajax({
			type : "POST",
			url : "././getAccountNumber",
			data : "name=" + name,

			/* beforeSend: function(xhr) {
			    xhr.setRequestHeader(header, token);
			}, */

			success : function(response) {
				var newStr = response.substring(1, response.length - 1); // Remove Array Brackets
				var data = newStr.split(",");
				$('#accountNumber').val(data)
			},
			error : function(e) {
				alert('accountNumber  Error: ' + e);
			}
		});
	}

	function getReceiptSequence() {
		addHeaderJson();
		var name = $('#name').val();
		//var accountNumber = $('#accountNumber').val();
		$.ajax({
			type : "POST",
			url : "././getReceiptSequence",
			data : "name=" + name, //+ "&accountNumber="+accountNumber,
			success : function(response) {
				var data = response;
				$('#receiptSequence').val(data)
				//alert(data);
			},
			error : function(e) {
				alert('Receipt Sequence  Error: ' + e);
			}
		});
	}
</script>

<script src="./resources/js/jquery.js"></script>
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

<style type="text/css">
.form-control.input-margin {
	margin-bottom: 10px;
	width: 122px;
	margin: 2px;
}

input[type=checkbox], input[type=radio] {
	margin: 5px;
}

.form-group {
	margin-bottom: 15px;
	padding: 6px 12px;
}
</style>

<script type="text/javascript">
	$(document)
			.ready(
					function() {
						//$("#member").keyup(function(){
						$("#member")
								.change(
										function() {

											var rowCount = ($('#table1 tr').length);
											if (rowCount > 0) {
												document
														.getElementById("table1").innerHTML = "";
											}
											var val = $("#member").val();
											if (val > 0) {
												var html = '<tr><th>Denomination</th><th>Bundle</th><th>Total</th><th>Bin</th></tr>';
												for (i = 0; i < val; i++) {

													html += '<tr id="main'+i+'">'
															+ '<td><input type="text" id="Denomination'
															+ i
															+ '" maxlength="4" minlength="1"  class="form-control input-margin" name="Denomination" value="" onkeyup="doAjaxPost('
															+ i
															+ ')" autofocus></td>'
															+ '<td><input type="text" id="Bundle'
															+ i
															+ '" class="form-control input-margin" name="Bundle" value="" onkeyup="doAjaxPost('
															+ i
															+ ')" ></td>'
															+ '<td><input type="text" id="TotalWithFormatter'+i+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'
															+ '<td><input type="text" id="binNumber'+i+'" class="form-control input-margin" name="Bin" value="" readonly="true"></td>'
															+ '<td><input type="radio"   name="processingOrVault['+i+']" value="processing" checked="checked"></td><td>Processing</td>'
															+ '<td><input type="radio"  name="processingOrVault['+i+']" value="vault"></td><td>Bin</td>'
															+ '<td><input type="radio"  name="processingOrVault['+i+']" value="Box"></td><td>Box</td>'
															+ '<td><input type="hidden" id="recordID'+i+'" class="form-control input-margin" name="Bin" value="" ></td>'
															+ '<td class="qr-button"><input type="button" id="print'
															+ i
															+ '" class="btn btn-default qr-button"  name="print"  value="Save AND Print QR" onclick="SavePrint('
															+ i
															+ '); this.disabled=true"></td>'
															+ '<td><button type="button" onclick="deleteRow('
															+ i
															+ ');doAjaxForTotal()">-</button></td>'
															+ '<td><input type="hidden" id="Total'+i+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'
															+ '</tr>';
													countrow++;
												}
												$('#table1').append(html);
												$('#table1')
														.append(
																'<tr id=addmoreButton'
																		+ (i - 1)
																		+ '><td><button type="button"  class="addmoreButton" onclick="replicateValue('
																		+ (i - 1)
																		+ ');doAjaxForTotal()">Replicate Value</button></td><td><button type="button"  class="addmoreButton" onclick="addRow('
																		+ (i - 1)
																		+ ');doAjaxForTotal()">Add Blank Row</button></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly></td></tr>');
											} else {
												$('#table1').empty();
											}
										});
					});

	function replicateValue(i) {

		// alert(countrow)
		//dataId = countrow-1;
		// alert("value from ID="+dataId)
		var rowCount = ($('#table1 tr').length) - 2;
		countrow = rowCount;
		dataId = countrow - 1;
		var denomData = jQuery('#Denomination' + dataId).val();
		var bundleData = jQuery('#Bundle' + dataId).val();
		var totalData = jQuery('#TotalWithFormatter' + dataId).val();

		if (typeof denomData === 'undefined') {
			denomData = "";
		}
		if (typeof bundleData === 'undefined') {
			bundleData = "";
		}
		if (typeof totalData === 'undefined') {
			totalData = "";
		}

		rowCount = ($('#table1 tr').length) - 2;
		jQuery('#addmoreButton' + i).remove();
		i++;
		var data = '<tr id="main'+countrow+'">'
				+ '<td><input type="text" id="Denomination'
				+ countrow
				+ '" maxlength="4" minlength="1"  class="form-control input-margin" name="Denomination" value="'
				+ denomData
				+ '"  onkeyup="doAjaxPost('
				+ countrow
				+ ')" autofocus="autofocus" ></td>'
				+ '<td><input type="text" id="Bundle'
				+ countrow
				+ '" class="form-control input-margin" name="Bundle" value="'
				+ bundleData
				+ '" onkeyup="doAjaxPost('
				+ countrow
				+ ')" ></td>'
				+ '<td><input type="text" id="TotalWithFormatter'+countrow+'"  class="form-control input-margin" name="Total" value="'+totalData+'" readonly="true"></td>'
				+ '<td><input type="text" id="binNumber'+countrow+'" class="form-control input-margin" name="Bin" value="" readonly="true"></td>'
				+ '<td><input type="radio"   name="processingOrVault['+countrow+']" value="processing" checked="checked"></td><td>Processing</td>'
				+ '<td><input type="radio"   name="processingOrVault['+countrow+']" value="vault"></td><td>Bin</td>'
				+ '<td><input type="radio"  name="processingOrVault['+countrow+']" value="Box"></td><td>Box</td>'
				+ '<td><input type="hidden" id="recordID'+countrow+'" class="form-control input-margin" name="Bin" value="" ></td>'
				+ '<td class="qr-button"><input type="button" id="print'
				+ countrow
				+ '" class="btn btn-default qr-button"  name="print"  value="Save AND Print QR" onclick="SavePrint('
				+ countrow
				+ '); this.disabled=true"></td>'
				+ '<td><button type="button" onclick="deleteRow('
				+ countrow
				+ ');doAjaxForTotal()">-</button></td>'
				+ '<td><input type="hidden" id="Total'+countrow+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'
				+ '</tr>';
		$('#table1').append(data);
		countrow++;
		$('#table1')
				.append(
						'<tr id=addmoreButton'+rowCount+'><td><button type="button" class="addmoreButton" onclick="replicateValue('
								+ rowCount
								+ ');doAjaxForTotal()">Replicate Value</button></td><td><button type="button" class="addmoreButton" onclick="addRow('
								+ rowCount
								+ ');doAjaxForTotal()">Add Blank Row</button></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly ></td></tr>');
	}

	function addRow(i) {

		//countrow++;
		//alert(countrow)
		var rowCount = ($('#table1 tr').length) - 2;
		countrow = rowCount;
		//countrow++;
		//var trLength=($('#table1 tr').length);
		// alert("trLength=="+rowCount)
		jQuery('#addmoreButton' + i).remove();
		i++;
		var data = '<tr id="main'+countrow+'">'
				+ '<td><input type="text" id="Denomination'
				+ countrow
				+ '" maxlength="4" minlength="1"  class="form-control input-margin" name="Denomination" value="" onkeyup="doAjaxPost('
				+ countrow
				+ ')"  autofocus="autofocus" ></td>'
				+ '<td><input type="text" id="Bundle'
				+ countrow
				+ '" class="form-control input-margin" name="Bundle" value="" onkeyup="doAjaxPost('
				+ countrow
				+ ')" ></td>'
				+ '<td><input type="text" id="TotalWithFormatter'+countrow+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'
				+ '<td><input type="text" id="binNumber'+countrow+'" class="form-control input-margin" name="Bin" value="" readonly="true"></td>'
				+ '<td><input type="radio"   name="processingOrVault['+countrow+']" value="processing" checked="checked"></td><td>Processing</td>'
				+ '<td><input type="radio"   name="processingOrVault['+countrow+']" value="vault"></td><td>Bin</td>'
				+ '<td><input type="radio"  name="processingOrVault['+countrow+']" value="Box"></td><td>Box</td>'
				+ '<td><input type="hidden" id="recordID'+countrow+'" class="form-control input-margin" name="Bin" value="" ></td>'
				+ '<td class="qr-button"><input type="button" id="print'
				+ countrow
				+ '" class="btn btn-default qr-button"  name="print"  value="Save AND Print QR" onclick="SavePrint('
				+ countrow
				+ '); this.disabled=true"></td>'
				+ '<td><button type="button" onclick="deleteRow('
				+ countrow
				+ ')">-</button></td>'
				+ '<td><input type="hidden" id="Total'+countrow+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'
				+ '</tr>';
		$('#table1').append(data);
		$('#table1')
				.append(
						'<tr id=addmoreButton'+rowCount+'><td><button type="button" class="addmoreButton" onclick="replicateValue('
								+ rowCount
								+ ');doAjaxForTotal()">Replicate Value</button></td><td><button type="button" class="addmoreButton" onclick="addRow('
								+ rowCount
								+ ');doAjaxForTotal()">Add Blank Row</button></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly ></td></tr>');
	}

	function deleteRow(i) {
		var MytotalValue = null;
		var fetchTotalValue = null;
		var t1 = parseFloat($('#totalValue').val());
		var t2 = parseFloat($('#Total' + i).val());

		jQuery('#main' + i).remove();

		for (var p = 0; p <= countrow; p++) {
			var me = parseFloat($('#Total' + p).val());
			if (!isNaN(me)) {
				fetchTotalValue = $('#Total' + p).val();
				MytotalValue += parseFloat(fetchTotalValue);
				$('#totalValue').val(MytotalValue.toLocaleString('en-IN'));
			}
		}

		if (($('#table1 tr').length) - 2 == 0) {
			$('#totalValue').val(0)
		}

	};

	function SavePrint(str) {
		addHeaderJson();
		var isValid = true;
		var dsb = {
			"name" : $('#name').val(),
			"accountNumber" : $('#accountNumber').val(),
			"denomination" : $('#Denomination' + str).val(),
			"bundle" : $('#Bundle' + str).val(),
			"bin" : $('#binNumber' + str).val(),
			"total" : $('#Total' + str).val(),
			"processingOrVault" : $('#main' + str).find(
					'input[type=radio]:checked').val(),
			"receiptSequence" : parseInt($('#receiptSequence').val())
		}
		if ($('#name').val() == "") {
			//jQuery('#print'+str).attr("disabled", false);
			$('#print' + str).prop('disabled', false);
			$('#err1').show();
			isValid = false;
		}
		if ($('#Denomination' + str).val() != 2000
				&& $('#Denomination' + str).val() != 1000
				&& $('#Denomination' + str).val() != 500
				&& $('#Denomination' + str).val() != 200
				&& $('#Denomination' + str).val() != 100
				&& $('#Denomination' + str).val() != 50
				&& $('#Denomination' + str).val() != 20
				&& $('#Denomination' + str).val() != 10
				&& $('#Denomination' + str).val() != 5
				&& $('#Denomination' + str).val() != 2
				&& $('#Denomination' + str).val() != 1) {
			$('#err2').show();
			isValid = false;
		}
		if ($('#Bundle' + str).val() == "" || $('#Bundle' + str).val() <= 0) {
			$('#err3').show();
			isValid = false;
		}
		if ($('input[type=radio]:checked').val() == undefined) {
			$('#err4').show();
			isValid = false;
		}
		if (isValid) {
			$.ajax({
				type : "POST",
				contentType : 'application/json; charset=utf-8',
				dataType : 'json',
				url : "././DSBQRPath",
				data : JSON.stringify(dsb),
				success : function(response) {
					console.log(response);
					var html = ""
					for (var i = 1; i < response.length; i++) {
						html += "<textarea id=prntextarea"+i+">'" + response[i]
								+ "'</textarea>"
						$('#prncode').append(html);
					}

					var binNum = response[0];
					$('#binNumber' + str).val(binNum);

				},
				error : function(e) {
					alert('Error: ' + e.responseJSON.message);
					jQuery('#print' + str).attr("disabled", false);
				}
			});
		}
	}

	// get the form values  
	function doAjaxPost(str) {
		// get the form values  
		var Denomination = $('#Denomination' + str).val();
		var Bundle = $('#Bundle' + str).val();
		var total = Denomination * 1000 * Bundle;
		$('#Total' + str).val(total);
		$('#TotalWithFormatter' + str).val(total.toLocaleString('en-IN'));

		var MytotalValue = null;
		var fetchTotalValue = null;

		for (var p = 0; p <= countrow; p++) {
			var me = parseFloat($('#Total' + p).val());

			if (!isNaN(me)) {
				fetchTotalValue = $('#Total' + p).val();
				MytotalValue += parseFloat(fetchTotalValue);
				$('#totalValue').val(MytotalValue.toLocaleString('en-IN'));
			}
		}
	}

	function doAjaxForTotal() {
		var j = 0;
		var myTotalValue = null;
		var noOfRows = ($('#table1 tr').length - 2);
		/* alert(noOfRows) */
		for (var p = 0; p < noOfRows; p++) {
			var Denomination = $('#Denomination' + p).val();
			var Bundle = $('#Bundle' + p).val();
			var total = Denomination * 1000 * Bundle;
			$('#Total' + p).val(total);

			var totalValue = parseFloat($('#Total' + p).val());
			if (noOfRows == j) {
				break;
			}
			if (!isNaN(totalValue) && totalValue != "" && totalValue != null
					&& totalValue != 'undefined') {
				myTotalValue += parseFloat(totalValue);
				j++;
			} else {
				//noOfRows++;
			}
		}
		$('#totalValue').val(myTotalValue.toLocaleString('en-IN'));
	}
</script>

<script type="text/javascript">
	function refresh() {
		window.location = '././DSB';
	}
</script>

</head>
<body>
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
								<li><a href="././viewDSB"><i class="fa fa-table fa-fw"></i>View
										DSB</a></li>
							</ul>
							DSB
						</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-10">
									<!--<form role="form">-->
									<form:form id="" action="" method="post" modelAttribute="user"
										autocomplete="off">

										<div class="form-group">
											<label>Name</label>
											<form:select path="name" id="name" name="name"
												cssClass="form-control"
												onchange="doAjaxForAccountNumber(); getReceiptSequence()">
												<form:option value="">Select Name </form:option>
												<form:options items="${dsbAccount}"
													itemValue="dsbVendorName" itemLabel="dsbVendorName" />
											</form:select>
											<label id="err1" style="display: none; color: red">Please
												Select Name</label>
										</div>

										<div class="form-group">
											<label>Account Number</label>
											<form:input path="accountNumber" readonly="true"
												id="accountNumber" name="accountNumber"
												cssClass="form-control" />
										</div>

										<!-- <div class="form-group"> -->
										<!-- <label>Receipt Sequence</label> -->
										<form:hidden path="receiptSequence" readonly="true"
											id="receiptSequence" name="receiptSequence"
											cssClass="form-control" />
										<!-- </div> -->

										<div class="form-group">
											<label>Number of Shrink Wraps</label> <input type="text"
												id="member" name="member" value="" class="form-control"><br />
											<div id="container">
												<table id="table1">
												</table>
											</div>
										</div>

										<!-- <button type="submit" class="btn btn-default" value="Details"
											style="width: 99px;">Save All</button>
											
											<button type="submit" class="btn btn-default" value="Details"
											style="width: 99px;">Print All</button> -->

										<div align="right">
											<button type="submit" class="btn btn-default" value="Details"
												style="width: 99px;" onclick="refresh()">Refresh</button>
										</div>

										<label id="err2" style="display: none; color: red">Enter
											Valid Denomination</label>
										<label id="err3" style="display: none; color: red">Enter
											Valid Bundle</label>
										<label id="err4" style="display: none; color: red">Select
											Option Vault OR Processing</label>
										<div id="prncode" style="display: none;"></div>
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