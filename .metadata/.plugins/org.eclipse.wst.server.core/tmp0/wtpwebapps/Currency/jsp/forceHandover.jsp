<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html lang="en">
<head>
<link rel="shortcut icon" href="./resources/logo/favicon.ico"
	type="image/x-icon">
<script src="./js/jquery-1.12.0.min.js"></script>

<script type="text/javascript">
	function ajaxBundleDetails() {
		addHeaderJson();
		var id = $('input[name=solId]:checked').val()
		$
				.ajax({
					type : "POST",
					url : "././bundleDetails",
					data : "id=" + id,
					success : function(response) {
						var newStr = JSON.stringify(response).replace(/]|[[]/g,
								'') // Remove Array Brackets
						var trHTML = '';
						var trHTML = '<tr><th>Category</th><th width="10%"></th><th>Denomination</th><th width="10%"></th><th>Bundle</th><th width="10%"></th><th>QR</th><th>Status</th></tr>';
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
														+ '</td><td width="10%"></td><td><textarea id="qr'+count+'" value="'+item.category+'|'+item.denomination+'" rows=1 clos=2>'
														+ item.category
														+ '|'
														+ item.denomination
														+ '</textarea></td><td><input type="text" readonly="true" id="message'+count+'"></td></tr>';
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
	}

	function saveRecord() {
		addHeaderJson();
		var rowCount = $('table#records_table tr:last').index() + 1;
		var SASReleasedList = [];
		for (var i = 1; i < rowCount; i++) {
			var category = $('#category' + i).text();
			var denomination = $('#denomination' + i).text();
			var qr = $('#qr' + i).val();
			var id = $('#id').val();
			var splitData = qr.split('|');
			if (category == splitData[0] && denomination == splitData[1]) {
				var msg = "Success";
				$('#message' + i).val(msg).css({
					'backgroundColor' : '#4E9258',
					'color' : 'white'
				});
				var sasReleaseObj = {
					"category" : category,
					"denomination" : denomination,
					"bundle" : 1,
					"QR" : qr,
					"id" : id
				};
				SASReleasedList.push(sasReleaseObj);
			} else {
				var msg = "False";
				$('#message' + i).val(msg).css({
					'backgroundColor' : '#E55451',
					'color' : 'white'
				});
			}
		}
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
				window.location = '././forceHandover';
			},
			error : function(e) {
				alert('Error: ' + e);
			}
		});
	}

	function ajaxShowRadioButtons() {
		var radioOption = $('input[name=radioOption]:checked').val()

		if (radioOption == 'BRANCH') {
			$('#craDiv').hide();
			$('#diversionDiv').hide();
			$('#soiledDiv').hide();
			$('#otherDiv').hide();
			$('#craForwardDiv').hide();
			$('#branchDiv').show();
		}

		if (radioOption == 'DIVERSION') {
			$('#branchDiv').hide();
			$('#craDiv').hide();
			$('#soiledDiv').hide();
			$('#otherDiv').hide();
			$('#craForwardDiv').hide();
			$('#diversionDiv').show();
		}

		if (radioOption == 'CRA') {
			$('#diversionDiv').hide();
			$('#branchDiv').hide();
			$('#soiledDiv').hide();
			$('#otherDiv').hide();
			$('#craForwardDiv').hide();
			$('#craDiv').show();
		}

		if (radioOption == 'SOILED') {
			$('#diversionDiv').hide();
			$('#branchDiv').hide();
			$('#soiledDiv').show();
			$('#otherDiv').hide();
			$('#craForwardDiv').hide();
			$('#craDiv').hide();
		}

		if (radioOption == 'OTHERBANK') {
			$('#diversionDiv').hide();
			$('#branchDiv').hide();
			$('#soiledDiv').hide();
			$('#otherDiv').show();
			$('#craForwardDiv').hide();
			$('#craDiv').hide();
		}

		if (radioOption == 'FORWARD') {
			$('#diversionDiv').hide();
			$('#branchDiv').hide();
			$('#soiledDiv').hide();
			$('#otherDiv').hide();
			$('#craDiv').hide();
			$('#craForwardDiv').show();
		}

	}
</script>



<script type="text/javascript">
	function ajaxBundleDetailsForSAS() {
		addHeaderJson();
		var id = $('input[name=solId]:checked').val()
		$
				.ajax({
					type : "POST",
					url : "././bundleDetailsSAS",
					data : "id=" + id,
					success : function(response) {
						var newStr = JSON.stringify(response).replace(/]|[[]/g,
								'') // Remove Array Brackets
						var trHTML = '';
						var trHTML = '<tr><th>Denomination</th><th width="10%"></th><th>Bundle</th></tr>';
						if(response[0].totalValueOfCoinsRs1 != 0 ){
							trHTML += '<tr><td>Coins 1</td><td width="10%"></td><td>'
								+ response[0].totalValueOfCoinsRs1
								+ '</td></tr>';	
						}if(response[0].totalValueOfCoinsRs2 != 0 ){

							trHTML += '<tr><td>Coins 2</td><td width="10%"></td><td>'
									+ response[0].totalValueOfCoinsRs2
									+ '</td></tr>';
						}if(response[0].totalValueOfCoinsRs5 != 0 ){
							trHTML += '<tr><td>Coins 5</td><td width="10%"></td><td>'
								+ response[0].totalValueOfCoinsRs5
								+ '</td></tr>';
						}if(response[0].totalValueOfCoinsRs10 != 0 ){
							trHTML += '<tr><td>Coins 10</td><td width="10%"></td><td>'
									+ response[0].totalValueOfCoinsRs10
									+ '</td></tr>';
						}if(response[0].totalValueOfNotesRs2000I != 0 || response[0].totalValueOfNotesRs2000F  != 0 || response[0].totalValueOfNotesRs2000A != 0 || response[0].totalValueOfNotesRs2000U !=0){
							trHTML += '<tr><td>2000</td><td width="10%"></td><td>'
									+ (response[0].totalValueOfNotesRs2000I
											+ response[0].totalValueOfNotesRs2000F + response[0].totalValueOfNotesRs2000A + response[0].totalValueOfNotesRs2000U)
									+ '</td></tr>';	
						}
						if(response[0].totalValueOfNotesRs1000I != 0 || response[0].totalValueOfNotesRs1000F  != 0 || response[0].totalValueOfNotesRs1000A != 0){
							trHTML += '<tr><td>1000</td><td width="10%"></td><td>'
								+ (response[0].totalValueOfNotesRs1000I
										+ response[0].totalValueOfNotesRs1000F + response[0].totalValueOfNotesRs1000A)
								+ '</td></tr>';
						}
						if(response[0].totalValueOfNotesRs500I != 0 || response[0].totalValueOfNotesRs500F  != 0 || response[0].totalValueOfNotesRs500U != 0 || response[0].totalValueOfNotesRs500A != 0){
							trHTML += '<tr><td>500</td><td width="10%"></td><td>'
								+ (response[0].totalValueOfNotesRs500I
										+ response[0].totalValueOfNotesRs500F + response[0].totalValueOfNotesRs500A + response[0].totalValueOfNotesRs500U)
								+ '</td></tr>';
						}
						if(response[0].totalValueOfNotesRs200I != 0 || response[0].totalValueOfNotesRs200F  != 0 || response[0].totalValueOfNotesRs200A != 0 || response[0].totalValueOfNotesRs200U != 0){
							trHTML += '<tr><td>200</td><td width="10%"></td><td>'
								+ (response[0].totalValueOfNotesRs200I
										+ response[0].totalValueOfNotesRs200F + response[0].totalValueOfNotesRs200A + response[0].totalValueOfNotesRs200U)
								+ '</td></tr>';
						
						}if(response[0].totalValueOfNotesRs100I != 0 || response[0].totalValueOfNotesRs100F  != 0 || response[0].totalValueOfNotesRs100A != 0 || response[0].totalValueOfNotesRs100U != 0){
						

							
							trHTML += '<tr><td>100</td><td width="10%"></td><td>'
									+ (response[0].totalValueOfNotesRs100I
											+ response[0].totalValueOfNotesRs100F + response[0].totalValueOfNotesRs100A + response[0].totalValueOfNotesRs100U)
									+ '</td></tr>';
						}if(response[0].totalValueOfNotesRs50I != 0 || response[0].totalValueOfNotesRs50F  != 0 || response[0].totalValueOfNotesRs50U != 0){
					

							trHTML += '<tr><td>50</td><td width="10%"></td><td>'
									+ (response[0].totalValueOfNotesRs50I + response[0].totalValueOfNotesRs50F + response[0].totalValueOfNotesRs50U)
									+ '</td></tr>';
						}if(response[0].totalValueOfNotesRs20I != 0 || response[0].totalValueOfNotesRs20F  != 0 || response[0].totalValueOfNotesRs20U != 0){
						

							trHTML += '<tr><td>20</td><td width="10%"></td><td>'
									+ (response[0].totalValueOfNotesRs20I + response[0].totalValueOfNotesRs20F + response[0].totalValueOfNotesRs20U)
									+ '</td></tr>';
						}if(response[0].totalValueOfNotesRs10I != 0 || response[0].totalValueOfNotesRs10F  != 0 || response[0].totalValueOfNotesRs10U != 0){
						

							trHTML += '<tr><td>10</td><td width="10%"></td><td>'
									+ (response[0].totalValueOfNotesRs10I + response[0].totalValueOfNotesRs10F + response[0].totalValueOfNotesRs10U)
									+ '</td></tr>';
						}if(response[0].totalValueOfNotesRs5I != 0 || response[0].totalValueOfNotesRs5F  != 0 || response[0].totalValueOfNotesRs5U != 0){
							
							trHTML += '<tr><td>5</td><td width="10%"></td><td>'
								+ (response[0].totalValueOfNotesRs5I + response[0].totalValueOfNotesRs5F + response[0].totalValueOfNotesRs5U)
								+ '</td></tr>';
						}
						if(response[0].totalValueOfNotesRs2I != 0 || response[0].totalValueOfNotesRs2F  != 0 || response[0].totalValueOfNotesRs2U != 0){

							trHTML += '<tr><td>2</td><td width="10%"></td><td>'
									+ (response[0].totalValueOfNotesRs2I + response[0].totalValueOfNotesRs2F + response[0].totalValueOfNotesRs2U)
									+ '</td></tr>';
						
						}if(response[0].totalValueOfNotesRs1I != 0 || response[0].totalValueOfNotesRs1F  != 0 || response[0].totalValueOfNotesRs1U != 0){
						
							trHTML += '<tr><td>1</td><td width="10%"></td><td>'
								+ (response[0].totalValueOfNotesRs1I + response[0].totalValueOfNotesRs1F + response[0].totalValueOfNotesRs1U)
								+ '</td></tr>';
						}
						
						

						$('#records_table').empty();
						$('#records_table').append(trHTML);
					},
					error : function(e) {
						alert('Exception: ' + e);
					}
				});
	}
</script>



<script type="text/javascript">
	function ajaxBundleDetailsForCRA() {
		addHeaderJson();
		var id = $('input[name=mspName]:checked').val()
		$
				.ajax({
					type : "POST",
					url : "././bundleDetailsForCRA",
					data : "id=" + id,
					success : function(response) {
						if(response.length==0){
							$('#records_table1').hide();	
						}else{ $('#records_table1').show();}
						var newStr = JSON.stringify(response).replace(/]|[[]/g,
								'') // Remove Array Brackets
						var trHTML = '';
						var trHTML = '<tr><th><u>VAULT</u><th></tr><tr><th>Category</th><th width="10%"></th><th>Denomination</th><th width="10%"></th><th>Bundle</th></tr>';
						for (var i = 0; i < response.length; i++) {
							trHTML += '<tr><td>' + response[i].currencyType
									+ '</td><td width="10%"></td><td>'
									+ response[i].denomination
									+ '</td><td width="10%"></td><td>'
									+ response[i].bundle + '</td></tr>';
							$('#records_table1').empty();
							$('#records_table1').append(trHTML);
						}
					},
					error : function(e) {
						alert('Exception: ' + e);
					}
				});
	}
</script>

<script type="text/javascript">
	function ajaxBundleDetailsForForwardedCRA() {
		addHeaderJson();
		var id = $('input[name=mspName]:checked').val()
		$
				.ajax({
					type : "POST",
					url : "././forwardedBundleDetailsForCra",
					data : "id=" + id,
					success : function(response) {
						var newStr = JSON.stringify(response).replace(/]|[[]/g,
								'') // Remove Array Brackets
						var trHTML = '';
						var trHTML = '<tr><th><u>Processing Room</u></th></tr><tr><th>Category</th><th width="10%"><th>Denomination</th><th width="10%"></th><th>Bundle</th></tr>';
						for (var i = 0; i <= response.length; i++) {
							trHTML += '<tr><td>' + response[i].currencyType +'</td><td width="10%"></td><td>' + response[i].denomination
									+ '</td><td width="10%"></td><td>'
									+ response[i].bundle + '</td></tr>';
							$('#records_table5').empty();
							$('#records_table5').append(trHTML);
						}
					},
					error : function(e) {
						alert('Exception: ' + e);
					}
				});
	}
</script>

<script type="text/javascript">
	function ajaxBundleDetailsForDiversion() {
		addHeaderJson();
		var id = $('input[name=bankName]:checked').val()
		$
				.ajax({
					type : "POST",
					url : "././bundleDetailsForDiversion",
					data : "id=" + id,
					success : function(response) {
						var newStr = JSON.stringify(response).replace(/]|[[]/g,
								'') // Remove Array Brackets
						var trHTML = '';
						var trHTML = '<tr><th>Category</th><th width="10%"></th><th>Denomination</th><th width="10%"></th><th>Bundle</th><th width="10%"></th>';
						for (var i = 0; i <= response.length; i++) {
							trHTML += '<tr><td>' + response[i].currencyType
									+ '</td><td width="10%"></td><td>'
									+ response[i].denomination
									+ '</td><td width="10%"></td><td>'
									+ response[i].bundle + '</td></tr>';
							$('#records_table2').empty();
							$('#records_table2').append(trHTML);
						}
					},
					error : function(e) {
						alert('Exception: ' + e);
					}
				});
	}
</script>


<script type="text/javascript">
	function ajaxBundleDetailsForSoiled() {
		addHeaderJson();
		var id = $('input[name=remittanceOrderNo]:checked').val()
		$
				.ajax({
					type : "POST",
					url : "././bundleDetailsForSoiled",
					data : "id=" + id,
					success : function(response) {
						var newStr = JSON.stringify(response).replace(/]|[[]/g,
								'') // Remove Array Brackets
						var trHTML = '';
						var trHTML = '<tr><th>Denomination</th><th width="10%"></th><th>Bundle</th></tr>';
						for (var i = 0; i <= response.length; i++) {
							trHTML += '<tr><td>' + response[i].denomination
									+ '</td><td width="10%"></td><td>'
									+ response[i].bundle + '</td></tr>';
							$('#records_table4').empty();
							$('#records_table4').append(trHTML);
						}
					},
					error : function(e) {
						alert('Exception: ' + e);
					}
				});
	}
</script>



<script type="text/javascript">
	function ajaxBundleDetailsForOtherBank() {
		addHeaderJson();
		var id = $('input[name=bankName]:checked').val()
		$
				.ajax({
					type : "POST",
					url : "././bundleDetailsForOtherBank",
					data : "id=" + id,
					success : function(response) {
						var newStr = JSON.stringify(response).replace(/]|[[]/g,
								'') // Remove Array Brackets
						var trHTML = '';
						var trHTML = '<tr><th>Category</th><th width="10%"></th><th>Denomination</th><th width="10%"></th><th>Bundle</th></tr>';
						for (var i = 0; i <= response.length; i++) {
							trHTML += '<tr><td>' + response[i].currencyType
									+ '</td><td width="10%"></td><td>'
									+ response[i].denomination
									+ '</td><td width="10%"></td><td>'
									+ response[i].bundle + '</td></tr>';
							$('#records_table3').empty();
							$('#records_table3').append(trHTML);
						}
					},
					error : function(e) {
						alert('Exception: ' + e);
					}
				});
	}
</script>


<script type="text/javascript">
	function doAjaxPostUpdateStatusForOtherBank() {
		addHeaderJson();
		var id = $('input[name=bankName]:checked').val()
		$.ajax({
			type : "POST",
			url : "././updateOtherBankStatus",
			data : "id=" + id,
			success : function(response) {
				alert('Successfull : Record submit.');
				window.location = '././forceHandover';
			},
			error : function(e) {
				alert(' Error: ' + e);
			}
		});
	}
</script>


<script type="text/javascript">
	function doAjaxPostUpdateStatusForCraForward() {
		addHeaderJson();
		//var id = $('input[name=currencyType]:checked').val()
		var id = $('input[name=mspName]:checked').val()
		/* alert("iii"+id); */
		$.ajax({
			type : "POST",
			url : "././updateProcessbundleForCRAPayment",
			data : "id=" + id,
			success : function(response) {
				//alert('Successfull : Record submit.');
				window.location = '././forceHandover';
			},
			error : function(e) {
				alert(' Error: ' + e);
			}
		});
	}
</script>


<script type="text/javascript">
	function doAjaxPostSASStatus(str) {
		addHeaderJson();
		// get the form values  
		var id = $('input[name=solId]:checked').val()
		$.ajax({
			type : "POST",
			url : "././updateSASStatusForPayment",
			data : "id=" + id,
			success : function(response) {
				alert('Successfull : Record submit.');
				window.location = '././forceHandover';
			},
			error : function(e) {
				alert(' Error: ' + e);
			}
		});
	}
</script>


<script type="text/javascript">
	function doAjaxPostUpdateStatusForCRA(str) {
		addHeaderJson();
		// get the form values  
		var id = $('input[name=mspName]:checked').val()
		$.ajax({
			type : "POST",
			url : "././updateCRAStatusForPayment",
			data : "id=" + id,
			success : function(response) {
				alert('Successfull : Record submit.');
				window.location = '././forceHandover';
			},
			error : function(e) {
				alert(' Error: ' + e);
			}
		});
	}
</script>


<script type="text/javascript">
	function doAjaxPostUpdateStatusForDiversion() {
		addHeaderJson();
		// get the form values  
		var id = $('input[name=bankName]:checked').val()

		$.ajax({
			type : "POST",
			url : "././updateDiversionStatusForPayment",
			data : "id=" + id,
			success : function(response) {
				alert('Successfull : Record submit.');
				window.location = '././forceHandover';
			},
			error : function(e) {
				alert(' Error: ' + e);
			}
		});
	}
</script>


<script type="text/javascript">
	function doAjaxPostUpdateStatusForSoiled() {
		addHeaderJson();
		// get the form values  
		var id = $('input[name=remittanceOrderNo]:checked').val()

		$.ajax({
			type : "POST",
			url : "././updateSoiledStatusForPayment",
			data : "id=" + id,
			success : function(response) {
				alert('Successfull : Record submit.');
				window.location = '././forceHandover';
			},
			error : function(e) {
				alert(' Error: ' + e);
			}
		});
	}
</script>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>ICICI : Force Handover</title>
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
	data-genuitec-file-id="wc1-145"
	data-genuitec-path="/Currency/src/main/webapp/jsp/forceHandover.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-145"
		data-genuitec-path="/Currency/src/main/webapp/jsp/forceHandover.jsp">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">Force Handover</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-8">
									<!--<form role="form">-->
									<form:form id="cashPaymentReleased"
										action="cashPaymentReleased" method="post"
										modelAttribute="user">

										<div class="form-group">
											<label><input type="radio" name="radioOption"
												value="BRANCH" onclick="ajaxShowRadioButtons()">BRANCH</label>&nbsp;&nbsp;&nbsp;
											<label><input type="radio" name="radioOption"
												value="CRA" onclick="ajaxShowRadioButtons()">CRA</label>&nbsp;&nbsp;&nbsp;
											<label><input type="radio" name="radioOption"
												value="DIVERSION" onclick="ajaxShowRadioButtons()">DIVERSION</label>&nbsp;&nbsp;&nbsp;
											<label><input type="radio" name="radioOption"
												value="SOILED" onclick="ajaxShowRadioButtons()">SOILED</label>&nbsp;&nbsp;&nbsp;
											<label><input type="radio" name="radioOption"
												value="OTHERBANK" onclick="ajaxShowRadioButtons()">OTHER
												BANK</label>
											<!-- <label><input type="radio" name="radioOption"
												value="FORWARD" onclick="ajaxShowRadioButtons()">CRA
												FORWARD</label> -->
										</div>

										<div id="branchDiv" style="display: none;">

											<div class="form-group">
												<label>Branch :</label><br>
												<form:radiobuttons items="${sas}" itemLabel="branch"
													itemValue="id" path="solId" id="solId" name="solId"
													onclick="ajaxBundleDetailsForSAS()" />

												<div id="container">
													<table id="records_table">
													</table>
												</div>
											</div>
											<input type="button" value="Submit"
												onclick="doAjaxPostSASStatus()">
										</div>

										<div id="craDiv" style="display: none;">

											<c:forEach var="item" items="${cra}">
												<%-- <c:out value="${cra}"/> --%>
												<form:radiobutton id="mspName" name="mspName" path="mspName"
													value="${item.id}"
													onclick="ajaxBundleDetailsForCRA();ajaxBundleDetailsForForwardedCRA()" />
										    ${item.vendor}-${item.mspName}
										</c:forEach>

											<div id="container">
												<table id="records_table1">
												</table>
											</div>

											<div id="container">
												<table id="records_table5">
												</table>
											</div>

											<input type="button" value="Submit"
												onclick="doAjaxPostUpdateStatusForCRA();doAjaxPostUpdateStatusForCraForward()">

										</div>


										<div id="diversionDiv" style="display: none;">
											<!-- <input type="radio">Diversion -->

											<form:radiobuttons items="${diversion}"
												itemLabel="diversionORVDetails" itemValue="id"
												path="bankName" id="bankName" name="bankName"
												onclick="ajaxBundleDetailsForDiversion()" />
											<div id="container">
												<table id="records_table2">
												</table>
											</div>
											<input type="button" value="Submit"
												onclick="doAjaxPostUpdateStatusForDiversion()">
										</div>


										<div id="soiledDiv" style="display: none;">
											<form:radiobuttons items="${remittanceOrder}"
												itemLabel="remittanceOrderNo" itemValue="id"
												path="remittanceOrderNo" id="remittanceOrderNo"
												name="remittanceOrderNo"
												onclick="ajaxBundleDetailsForSoiled()" />
											<div id="container">
												<table id="records_table4">
												</table>
											</div>
											<input type="button" value="Submit"
												onclick="doAjaxPostUpdateStatusForSoiled()">
										</div>

										<div id="otherDiv" style="display: none;">
											<form:radiobuttons items="${otherBank}" itemLabel="bankName"
												itemValue="id" path="bankName" id="bankName" name="bankName"
												onclick="ajaxBundleDetailsForOtherBank()" />
											<div id="container">
												<table id="records_table3">
												</table>
											</div>
											<input type="button" value="Submit"
												onclick="doAjaxPostUpdateStatusForOtherBank()">
										</div>

										<%-- <div id="craForwardDiv" style="display: none;">
											<form:radiobuttons items="${craForwardedList}"
												itemLabel="currencyType" itemValue="id" path="currencyType"
												id="currencyType" name="currencyType"
												onclick="ajaxBundleDetailsForForwardedCRA()" />
											<div id="container">
												<table id="records_table5">
												</table>
											</div>
											<input type="button" value="Submit"
												onclick="doAjaxPostUpdateStatusForCraForward()">
										</div> --%>

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