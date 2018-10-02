<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
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

<title>ICICI : Add Other Bank Receipt</title>

<link href="./resources/css/calendar.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.datetimepicker.css" />
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
	var countrow = 0;
	var rowCount = 0;
	var dataId = 0;

	$(document)
			.ready(
					function() {
						//$("#member").keyup(function(){
						$("#member")
								.change(
										function() {
											rowCount = ($('#table1 tr').length);
											if (rowCount > 0) {
												document
														.getElementById("table1").innerHTML = "";
											}
											var val = $("#member").val();
											if (val > 0) {
												var html = '<tr><th>Denomination</th><th>Bundle</th><th>Total</th><th>Bin</th></tr>';
												for (i = 0; i < val; i++) {
													html += '<tr id="main'+i+'">'
															+ '<td><input type="text"  id="Denomination'
															+ i
															+ '" maxlength="4" minlength="1" class="form-control input-margin" name="Denomination" value="" onkeyup="doAjaxPost('
															+ i
															+ ')"  autofocus></td>'
															+ '<td><input type="text" maxlength="45" id="Bundle'
															+ i
															+ '" class="form-control input-margin" name="Bundle" value="" onkeyup="doAjaxPost('
															+ i
															+ ')" ></td>'
															+ '<td><input type="text" maxlength="45" id="TotalWithFormatter'+i+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'
															+ '<td><input type="text" maxlength="45" id="binNumber'+i+'" class="form-control input-margin" name="Bin" value="" readonly="true" ></td>'
															+ '<td><input type="radio"   name="binOrBox['+countrow+']" value="PROCESSING" checked="checked"></td><td>Processing</td>'
															+ '<td><input type="radio"  name="binOrBox['+i+']" value="BIN" ></td><td>BIN</td>'
															+ '<td><input type="radio"  name="binOrBox['+i+']" value="BOX"></td><td>BOX</td>'
															+ '<td><input type="hidden" id="recordID'+i+'" class="form-control input-margin" name="Bin" value="" ></td>'
															+ '<td class="qr-button"><input type="button" id="print'
															+ i
															+ '" class="btn btn-default qr-button"  name="print"  value="Save AND Print QR" onclick="SavePrint('
															+ i
															+ ');this.disabled=true"></td>'
															+ '<td><button type="button" onclick="deleteRow('
															+ i
															+ ')">-</button></td>'
															+ '<td><input type="hidden" maxlength="45" id="Total'+i+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'
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

	function addRow(i) {
		/* countrow++;
		 var rowCount = ($('#table1 tr').length)-2; */
		var rowCount = ($('#table1 tr').length) - 2;
		countrow = rowCount;
		jQuery('#addmoreButton' + i).remove();
		i++;
		var data = '<tr id="main'+countrow+'">'
				+ '<td><input type="text"  id="Denomination'
				+ countrow
				+ '" maxlength="4" minlength="1" class="form-control input-margin" name="Denomination" value="" onkeyup="doAjaxPost('
				+ countrow
				+ ')" autofocus></td>'
				+ '<td><input type="text" maxlength="45" id="Bundle'
				+ countrow
				+ '" class="form-control input-margin" name="Bundle" value="" onkeyup="doAjaxPost('
				+ countrow
				+ ')" ></td>'
				+ '<td><input type="text" maxlength="45" id="TotalWithFormatter'+countrow+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'
				+ '<td><input type="text" maxlength="45" id="binNumber'+countrow+'" class="form-control input-margin" name="Bin" value="" readonly="true"></td>'
				+ '<td><input type="radio"   name="binOrBox['+countrow+']" value="PROCESSING" checked="checked"></td><td>Processing</td>'
				+ '<td><input type="radio"  name="binOrBox['+countrow+']" value="BIN"></td><td>BIN</td>'
				+ '<td><input type="radio"  name="binOrBox['+countrow+']" value="BOX"></td><td>BOX</td>'
				+ '<td><input type="hidden" id="recordID'+countrow+'" class="form-control input-margin" name="Bin" value="" ></td>'
				+ '<td class="qr-button"><input type="button" id="print'
				+ countrow
				+ '" class="btn btn-default qr-button"  name="print"  value="Save AND Print QR" onclick="SavePrint('
				+ countrow
				+ ');this.disabled=true"></td>'
				+ '<td><button type="button" onclick="deleteRow('
				+ countrow
				+ ')">-</button></td>'
				+ '<td><input type="hidden" maxlength="45" id="Total'+countrow+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'
				+ '</tr>';
		$('#table1').append(data);
		$('#table1')
				.append(
						'<tr id=addmoreButton'+rowCount+'><td><button type="button" class="addmoreButton" onclick="replicateValue('
								+ rowCount
								+ ');doAjaxForTotal()">Replicate Value</button></td><td><button type="button" class="addmoreButton" onclick="addRow('
								+ rowCount
								+ ');doAjaxForTotal()">Add Blank Row</button></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly></td></tr>');

	}

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
				+ '<td><input type="text"  id="Denomination'
				+ countrow
				+ '" maxlength="4" minlength="1" class="form-control input-margin" name="Denomination" value="'
				+ denomData
				+ '" onkeyup="doAjaxPost('
				+ countrow
				+ ')" autofocus></td>'
				+ '<td><input type="text" maxlength="45" id="Bundle'
				+ countrow
				+ '" class="form-control input-margin" name="Bundle" value="'
				+ bundleData
				+ '" onkeyup="doAjaxPost('
				+ countrow
				+ ')" ></td>'
				+ '<td><input type="text" maxlength="45" id="TotalWithFormatter'+countrow+'"  class="form-control input-margin" name="Total" value="'+totalData+'" readonly="true"></td>'
				+ '<td><input type="text" maxlength="45" id="binNumber'+countrow+'" class="form-control input-margin" name="Bin" value="" readonly="true"></td>'
				+ '<td><input type="radio"   name="binOrBox['+countrow+']" value="PROCESSING" checked="checked"></td><td>Processing</td>'
				+ '<td><input type="radio"  name="binOrBox['+countrow+']" value="BIN"></td><td>BIN</td>'
				+ '<td><input type="radio"  name="binOrBox['+countrow+']" value="BOX"></td><td>BOX</td>'
				+ '<td><input type="hidden" id="recordID'+countrow+'" class="form-control input-margin" name="Bin" value="" ></td>'
				+ '<td class="qr-button"><input type="button" id="print'
				+ countrow
				+ '" class="btn btn-default qr-button"  name="print"  value="Save AND Print QR" onclick="SavePrint('
				+ countrow
				+ ');this.disabled=true"></td>'
				+ '<td><button type="button" onclick="deleteRow('
				+ countrow
				+ ')">-</button></td>'
				+ '<td><input type="hidden" maxlength="45" id="Total'+countrow+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'
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

	function doAjaxForTotal() {
		var j = 0;
		var myTotalValue = null;
		var noOfRows = ($('#table1 tr').length - 2);

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
		addHeader();
		var dirv = {
			"bankName" : $('#bankName').val(),
			"solId" : $('#solId').val(),
			"branch" : $('#branch').val(),
			"denomination" : $('#Denomination' + str).val(),
			"bundle" : $('#Bundle' + str).val(),
			"category" : $('#Category' + str).val(),
			"binNumber" : $('#binNumber' + str).val(),
			"total" : $('#Total' + str).val(),
			"rtgsUTRNo" : $('#rtgsUTRNo').val(),
			"binCategoryType" : $('#main' + str).find(
					'input[type=radio]:checked').val(),
		}
		var isValid = true;
		if ($('#bankName').val() == "") {
			$('#err1').show();
			isValid = false;
		}
		if ($('#solId').val() == "") {
			$('#err2').show();
			isValid = false;
		} else if ($('#solId').val().length != 4) {
			$('#err12').show();
			isValid = false;
		}

		if ($('#rtgsUTRNo').val() == "") {
			$('#err3').show();
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
			$('#err4').show();
			isValid = false;
		}

		if ($('#Bundle' + str).val() == "" || $('#Bundle' + str).val() <= 0) {
			$('#err5').show();
			isValid = false;
		}

		if (isValid) {
			$.ajax({
				type : "POST",
				contentType : 'application/json; charset=utf-8',
				dataType : 'json',
				url : "././bankReceipt",
				data : JSON.stringify(dirv),
				success : function(response) {

					var binNum = response[0];
					$('#binNumber' + str).val(binNum);

				},
				error : function(e) {
					//alert('Print Error: ' + e);
					alert('Error: ' + e.responseJSON.message);
					console.log('Error: ' + e);
					jQuery('#print' + str).attr("disabled", false);
				}
			});
		}
	}

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

	function doAjaxPostForBranch() {
		addHeader();
		// get the form values  
		var solid = $('#solId').val();
		if (solid.length == 4) {
			$.ajax({
				type : "POST",
				url : "././branchName",
				data : "solid=" + solid,
				success : function(response) {
					// we have the response  
					var res = JSON.stringify(response);
					var newStr = res.substring(1, res.length - 1); // Remove Array Brackets
					$('#info').html(response);
					$('#solid').val(solid);
					$('#branch').val(newStr);
					// alert("Response =="+response);
				},
				error : function(e) {
					//alert('branchName Error: ' + e);  
				}
			});
		}
	}
</script>

<script type="text/javascript">
	function refresh() {
		window.location = '././AddOtherBankReceipt';
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
								<li><sec:authorize
										access="hasRole('VIEW_OTHER_BANK_RECEIPT')">
										<a href="././viewBankReceipt"><i class="fa fa-table fa-fw"></i>
											View Other Bank Receipt Data</a>
									</sec:authorize></li>
							</ul>
							Add Other Bank Receipt
						</div>

						<div class="panel-body">
							<div class="row">
								<div class="col-lg-10">
									<!--<form role="form">-->
									<form:form id="" action="" method="post" modelAttribute="user"
										autocomplete="off">

										<div class="form-group">
											<label>Bank Name</label>
											<form:input path="bankName" id="bankName" name="bankName"
												cssClass="form-control" />
											<label id="err1" style="display: none; color: red">Please
												Enter Bank Name</label>
										</div>

										<div class="form-group">
											<label>Sol ID</label>
											<form:input path="solId" name="solId" maxlength="4"
												id="solId" cssClass="form-control"
												onkeyup="doAjaxPostForBranch()" />
											<label id="err2" style="display: none; color: red">Please
												Enter Sol ID</label> <label id="err12"
												style="display: none; color: red">Please Enter Four
												Digit Sol ID </label>
										</div>

										<div class="form-group">
											<label>Branch</label>
											<form:input path="branch" id="branch" cssClass="form-control"
												readonly="true" />
										</div>

										<div class="form-group">
											<label>RTGS UTR no'/I-core Tran ID.</label>
											<form:input path="rtgsUTRNo" id="rtgsUTRNo" name="rtgsUTRNo"
												cssClass="form-control" />
											<label id="err3" style="display: none; color: red">Please
												Enter RTGS UTR No.</label>
										</div>

										<div class="form-group">
											<label>Number of Shrink Wraps</label> <input type="text"
												id="member" maxlength="45" name="member" value=""
												class="form-control"><br />
											<div id="container">
												<table id="table1"></table>
											</div>
										</div>

										<div align="right">
											<button type="submit" class="btn btn-default" value="Details"
												style="width: 99px;" onclick="refresh()">Refresh</button>
										</div>

										<label id="err4" style="display: none; color: red">Enter
											Valid Denomination</label>
										<label id="err5" style="display: none; color: red">Enter
											Bundle</label>

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
	<script src="./resources/js/jquery.datetimepicker.js"></script>
	<script>
		$('#receiptDate').datetimepicker({
			format : 'Y-m-d',
		});
	</script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>