<!DOCTYPE html>
<html lang="en">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="./resources/logo/favicon.ico"
	type="image/x-icon">
<link rel="shortcut icon" href="./resources/logo/favicon.ico"
	type="image/x-icon">
<link href="./resources/css/calendar.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.datetimepicker.css" />
<script type="text/javascript" src="./js/jquery-1.12.0.min.js"></script>
<script>
	$(function() {
		$('#btnsubmit').on('click', function() {
			$(this).val('Save').attr('disabled', 'disabled');
		});

	});
</script>
<script type="text/javascript">
	function collectData() {
		var bundle = 0, totalValue = 0;
		$("input:checkbox[class=chkbx]:checked").each(function() {
			//  alert("boxId: " + $(this).val()+"denomination: " + $(this).attr('data-id') + "bundle: " + $(this).attr('data-id1')+ "total: " + $(this).attr('data-id2'));
			bundle = bundle + parseInt($(this).attr('data-id1'));
			totalValue = totalValue + parseInt($(this).attr('data-id2'));
		});
		/* $('#totalBundle').empty();
		$('#totalValue').empty(); */
		$('#totalBundle').html("Selected Total Bundle:" + bundle);
		$('#totalValue').html("Selected Total Value:" + totalValue);
	}
	$(document).ready(function() {
		normalMutilated('SOILED');
	});
	function normalMutilated(Currencytype) {
		$('.chkbx').prop('checked', false);
		$('#totalBundle').html("Selected Total Bundle:");
		$('#totalValue').html("Selected Total Value:");
		if (Currencytype == "SOILED") {
			 $('.SOILED').show();
			 $('.MUTILATED').hide(); 
			/*  $('.SOILED').attr('disabled',false);
			 $('.MUTILATED').attr('disabled',true);  */
		} else {
			 $('.MUTILATED').show();
			 $('.SOILED').hide(); 
			/*  $('.MUTILATED').attr('disabled',false);
			$('.SOILED').attr('disabled',true);  */
		}

	}
	function doAjaxPostInsert() {
		addHeaderJson();
		var remittanceAllocations = [];
		var val = $("#member").val();
		var isValid = true;
		var soiledWrapper = {
			"orderDate" : $('#orderDate').val(),
			"remittanceOrderNo" : $('#remittanceOrderNo').val(),
			"approvedRemittanceDate" : $('#approvedRemittanceDate').val(),
			"notes" : $('input[name=notes]:checked').val(),
			"types" : $('input[name=types]:checked').val(),
			"vehicleNumber" : $('#vehicleNumber').val(),
			"location" : $('#location').val(),
			"remittanceAllocations" : remittanceAllocations
		}

		if ($('#orderDate').val() == "") {
			$('#err1').show();
			isValid = false;
		}
		if ($('#remittanceOrderNo').val() == "") {
			$('#err2').show();
			isValid = false;
		}
		if ($('#approvedRemittanceDate').val() == "") {
			$('#err3').show();
			isValid = false;
		}
		if ($('input[name=notes]:checked').length == 0) {
			$('#err4').show();
			isValid = false;
		}
		if ($('input[name=types]:checked').length == 0) {
			$('#err5').show();
			isValid = false;
		}

		if ($('#vehicleNumber').val() == "") {
			$('#err6').show();
			isValid = false;
		}
		if ($('#location').val() == "") {
			$('#err7').show();
			isValid = false;
		}

		//For Denomination Validation
		if (isValid) {
			$("input:checkbox[class=chkbx]:checked").each(function() {
				// alert("boxId: " + $(this).val()+"denomination: " + $(this).attr('data-id') + "bundle: " + $(this).attr('data-id1')+ "total: " + $(this).attr('data-id2'));
				remittanceAllocations.push({
					"id" : +$(this).val(),
					"denomination" : $(this).attr('data-id'),
					"bundle" : $(this).attr('data-id1'),
					"total" : $(this).attr('data-id2')
					//"currencyType":$(this).attr('data-id3')
				});

			});
		}
		if ($(".chkbx").val() == "" || $(".chkbx").val() == undefined) {
			// alert("Box is not available, Please prepare Box");
			$('#err10').show();
			isValid = false;
		}
		/* for (i = 0; i <= countrow; i++) {
		
		 if(($('#Denomination'+i).val()!=undefined) && $('#Denomination'+i).val()!=2000 && $('#Denomination'+i).val()!=1000 && $('#Denomination'+i).val()!=500 && $('#Denomination'+i).val()!=200 && $('#Denomination'+i).val()!=100 && $('#Denomination'+i).val()!=50 && $('#Denomination'+i).val()!=20 && $('#Denomination'+i).val()!=10 && $('#Denomination'+i).val()!=5 && $('#Denomination'+i).val()!=2 && $('#Denomination'+i).val()!=1){
		 $('#err8').show();
		 isValid = false;
		 }
		 if(($('#Bundle'+i).val()!=undefined) && $('#Bundle'+i).val() == ""){
		 $('#err9').show();
		 isValid = false;
		 }
		
		 if(($('#Denomination'+i).val()!=undefined) && isValid){
		 remittanceAllocations.push({"denomination":$('#Denomination'+i).val(),
		 "bundle":$('#Bundle'+i).val(),
		 "total":$('#Total'+i).val()});
		 }
		 } */

		//Close Denomination Validation
		if (isValid) {
			$('#btnsubmit').prop("disabled", true);
			$.ajax({
				type : "POST",
				contentType : 'application/json; charset=utf-8',
				dataType : 'json',
				url : "././soiledRemittance",
				data : JSON.stringify(soiledWrapper),
				success : function(response) {
					alert('record submit.');

					$('#btnsubmit').prop("disabled", true);
					window.location = '././Soiled';
				},
				error : function(e) {
					alert('Problem: ' + e.responseJSON.message);
					$('#btnsubmit').prop("disabled", false);
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
</script>
<title>ICICI : View Prepared Soiled Boxes</title>

<!-- Bootstrap Core CSS -->
<link
	href="./resources/bower_components/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- MetisMenu CSS -->
<link
	href="./resources/bower_components/metisMenu/dist/metisMenu.min.css"
	rel="stylesheet">

<!-- DataTables CSS -->
<link
	href="./resources/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css"
	rel="stylesheet">

<!-- DataTables Responsive CSS -->
<link
	href="./resources/bower_components/datatables-responsive/css/responsive.dataTables.scss"
	rel="stylesheet">

<!-- Custom CSS -->
<link href="./resources/dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Custom Fonts -->
<link
	href="./resources/bower_components/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
<link rel="./resources/stylesheet" type="text/css"
	href="dist/css/style.css">

<!-- DataTable -->
<script type="text/javascript" src="./resources/dataTable/jquery.js"></script>
<script type="text/javascript"
	src="./resources/js/jquery.dataTables.min.js"></script>
<script type="text/javascript"
	src="./resources/js/dataTables.tableTools.min.js"></script>
<script type="text/javascript" src="./resources/js/sum().js"></script>
<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.dataTables.min.css">
<link rel="stylesheet" type="text/css"
	href='./resources/css/dataTables.tableTools.min.css'>
<link rel="stylesheet" type="text/css"
	href='./resources/css/dataTables.tableTools.css'>
<!-- DataTable -->
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
								<li><a href="././viewSoiled"><i
										class="fa fa-table fa-fw"></i>View Soiled</a></li>
							</ul>
							Soiled
						</div>
						<!--  <div>
					<input type="button" class="btn btn-default qr-button" onclick="printDiv('printableArea')" value="Print" />
						<button id="btnExport" class="btn btn-default qr-button">Export to xls</button>
						</div> -->
						<form:form id="cash" action="saveDorv" method="post"
							modelAttribute="user" autocomplete="off">

							<div class="panel-body">
								<div align="center" style="color: white; background: red;">
									<b>${errorMsg}</b>
								</div>
								<div class="row">
									<!--<form role="form">-->

									<div class="col-lg-6 form-group">
										<label>Order Date</label>
										<form:input type="text" onkeypress="return false"
											path="orderDate" id="orderDate" cssClass="form-control" />
										<label id="err1" style="display: none; color: red">Enter
											Order Date</label>
									</div>

									<div class="col-lg-6 form-group">
										<label>Remittance Order Number</label>
										<form:input path="remittanceOrderNo" id="remittanceOrderNo"
											cssClass="form-control" type="Number" />
										<label id="err2" style="display: none; color: red">Enter
											Remittance Order Number</label>
									</div>

									<div class="col-lg-6 form-group">
										<label>Approved Remittance Date</label>
										<form:input type="text" onkeypress="return false"
											path="approvedRemittanceDate" id="approvedRemittanceDate"
											cssClass="form-control" />
										<label id="err3" style="display: none; color: red">Enter
											Approved Remittance Date</label>
									</div>

									<div class="form-group">
										<form:checkbox path="notes" id="notes" name="notes"
											value="Notes" />
										<span class="deno-value">Notes </span> <label id="err4"
											style="display: none; color: red"> Please Select
											Notes</label>
										<!-- <input type="radio" id="notes"> -->
									</div>

									<div class="form-group">
										<form:radiobutton path="types" id="types" name="types"
											value="Normal" onclick="normalMutilated('SOILED');" />
										<span class="deno-value">Normal </span>
										<form:radiobutton path="types" id="types" name="types"
											value="Full Value Mutilated"
											onclick="normalMutilated('MUTILATED');" />
										<span class="deno-value">Full Value Mutilated </span> <label
											id="err5" style="display: none; color: red"> Please
											Select at least One Radio Button</label>
									</div>

									<div class="col-lg-6 form-group">
										<label>Vehicle Number</label>
										<form:input path="vehicleNumber" id="vehicleNumber"
											cssClass="form-control" />
									</div>
									<label id="err6" style="display: none; color: red">
										Enter Vehicle Number</label>

									<div class="col-lg-6 form-group">
										<label>Location</label>
										<form:input path="location" id="location"
											cssClass="form-control" />
										<label id="err7" style="display: none; color: red">
											Enter Location</label>
									</div>

									<!-- 	<div class="form-group">
                                        	<label>Number of Entries</label>
											<input type="text" id="member" name="member" value="" class="form-control"><br />
											<label id="err8" style="display: none;color: red">Please  Enter  Valid Denomination</label>
										    <label id="err9" style="display: none; color: red">Please Enter Bundle</label>
											<div id="container">
												<table id="table1">
													<tr>
													</tr>
												</table>
											</div>
										</div> -->




									<!-- /.col-lg-6 (nested) -->
									<!-- /.col-lg-6 (nested) -->
								</div>
								<!-- /.row (nested) -->
							</div>
							<!-- /.panel-body -->
							<!-- /.panel-heading -->
							<div class="panel-body SOILED">
								<div class="dataTable_wrapper">
									<form id="showAll">
										<div class="containerr" id="printableArea">
											<label id="err10" style="display: none; color: red">Please
												Select Box</label>
											<div class="row col-lg-12 text-center">
												<div class="col-lg-1"></div>
												<div class="col-lg-1"></div>
												<label id="totalBundle" class="col-sm-3"
													style="color: green">Selected Total Bundle:</label> <label
													id="totalValue" class="col-sm-pull-8" style="color: green">Selected
													Total Value:</label>
											</div>
											<div id="table_wrapper">
												<table
													class="table table-striped table-bordered table-hover"
													id="tableValue">
													<thead>
														<tr>
															<th>Box No.</th>
															<th>BOX Name</th>
															<th>Denomination</th>
															<th>Bundle</th>
															<th>CurrencyType</th>
															<!-- <th>Prepared Date</th> -->
															<th>Total Value</th>
															<th>Select</th>
														</tr>
													</thead>
													<tfoot>
														<tr>
															<th style="text-align: right">Total:</th>
														</tr>
													</tfoot>
													<tbody>
														<%--  <form:checkboxes items="${records}" path=""/> --%>


														<c:forEach var="row" items="${soiledList}">
															<c:set var="count" value="${count + 1}" scope="page" />
															<tr>
																<td class="boxId ${row.binType}">${row.id}</td>
																<td class="${row.binType}">${row.binNumber}</td>
																<td class="Denomination ${row.binType}">${row.denomination}</td>
																<td class="Bundle ${row.binType}">${row.receiveBundle}</td>
																<td class="${row.binType}">${row.binType}</td>
																<%-- <td>${row.binType}</td>
											<td><fmt:formatDate pattern="dd-MMM-yy" value="${row.insertTime.time}"/></td>
											 --%>
																<td class="Total ${row.binType}">${row.value}</td>
																<td class="${row.binType}"><input type="checkbox"
																	value="${row.id}" data-id="${row.denomination}"
																	data-id1="${row.receiveBundle}" data-id2="${row.value}"
																	name="chekdata[]" class="chkbx"
																	onclick="collectData();"></td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>

										</div>
									</form>
								</div>
								<!-- /.table-responsive -->
							</div>
							<div class="panel-body MUTILATED">
								<div class="dataTable_wrapper">
									<form id="showAll">
										<div class="containerr" id="printableArea">
											<label id="err10" style="display: none; color: red">Please
												Select Box</label>
											<div class="row col-lg-12 text-center">
												<div class="col-lg-1"></div>
												<div class="col-lg-1"></div>
												<label id="totalBundle" class="col-sm-3"
													style="color: green">Selected Total Bundle:</label> <label
													id="totalValue" class="col-sm-pull-8" style="color: green">Selected
													Total Value:</label>
											</div>
											<div id="table_wrapper">
												<table
													class="table table-striped table-bordered table-hover"
													id="mutilatedTableValue">
													<thead>
														<tr>
															<th>Box No.</th>
															<th>BOX Name</th>
															<th>Denomination</th>
															<th>Bundle</th>
															<th>CurrencyType</th>
															<!-- <th>Prepared Date</th> -->
															<th>Total Value</th>
															<th>Select</th>
														</tr>
													</thead>
													<tfoot>
														<tr>
															<th style="text-align: right">Total:</th>
														</tr>
													</tfoot>
													<tbody>
														<%--  <form:checkboxes items="${records}" path=""/> --%>


														<c:forEach var="row" items="${mutilatedList}">
															<c:set var="count" value="${count + 1}" scope="page" />
															<tr>
																<td class="boxId ${row.binType}">${row.id}</td>
																<td class="${row.binType}">${row.binNumber}</td>
																<td class="Denomination ${row.binType}">${row.denomination}</td>
																<td class="Bundle ${row.binType}">${row.receiveBundle}</td>
																<td class="${row.binType}">${row.binType}</td>
																<%-- <td>${row.binType}</td>
											<td><fmt:formatDate pattern="dd-MMM-yy" value="${row.insertTime.time}"/></td>
											 --%>
																<td class="Total ${row.binType}">${row.value}</td>
																<td class="${row.binType}"><input type="checkbox"
																	value="${row.id}" data-id="${row.denomination}"
																	data-id1="${row.receiveBundle}" data-id2="${row.value}"
																	name="chekdata[]" class="chkbx"
																	onclick="collectData();"></td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>

										</div>
									</form>
								</div>
								<!-- /.table-responsive -->
							</div>

							<!-- /.panel-body -->
							<div class="col-lg-12">
								<!-- <input type="button" id="btnSubmit" value="Submit"	onclick="doAjaxPostInsert()"> -->
								<button type="button" class="btn btn-primary" id="btnsubmit"
									onclick="doAjaxPostInsert();">Save</button>
							</div>
						</form:form>
						<br> <br>
					</div>

					<!-- /.panel -->
				</div>
				<!-- /.col-lg-12 -->
			</div>

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

	<!-- DataTables JavaScript -->
	<script
		src="./resources/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="./resources/dist/js/sb-admin-2.js"></script>

	<!-- Page-Level Demo Scripts - Tables - Use for reference -->
	<script>
		$(document).ready(
				function() {
					$('#tableValue').dataTable(
							{
								"pagingType" : "full_numbers",
								drawCallback : function() {
									var api = this.api();
									$(api.table().footer()).html(
											'<th colspan="6" style="text-align:right">Current Page Total: '
													+ api.column(4, {
														page : 'current'
													}).data().sum()
															.toLocaleString(
																	'en-IN')
													+ '; All Pages Total: '
													+ api.column(4).data()
															.sum()
															.toLocaleString(
																	'en-IN')
													+ '</th>');
								}
							});
				});
	</script>
	<script>
		$(document).ready(
				function() {
					$('#mutilatedTableValue').dataTable(
							{
								"pagingType" : "full_numbers",
								drawCallback : function() {
									var api = this.api();
									$(api.table().footer()).html(
											'<th colspan="6" style="text-align:right">Current Page Total: '
													+ api.column(4, {
														page : 'current'
													}).data().sum()
															.toLocaleString(
																	'en-IN')
													+ '; All Pages Total: '
													+ api.column(4).data()
															.sum()
															.toLocaleString(
																	'en-IN')
													+ '</th>');
								}
							});
				});
	</script>
	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							$("#btnExport")
									.click(
											function(e) {
												e.preventDefault();

												//getting data from our table
												var data_type = 'data:application/vnd.ms-excel';
												var table_div = document
														.getElementById('table_wrapper');
												var table_html = table_div.outerHTML
														.replace(/ /g, '%20');

												var a = document
														.createElement('a');
												a.href = data_type + ', '
														+ table_html;
												a.download = 'exported_table_'
														+ Math
																.floor((Math
																		.random() * 9999999) + 1000000)
														+ '.xls';
												a.click();
											});
						});
	</script>
	<script src="./resources/js/jquery.datetimepicker.js"></script>
	<script>
		$('#orderDate').datetimepicker({
			format : 'Y-m-d',

		});
		$('#approvedRemittanceDate').datetimepicker({
			format : 'Y-m-d',

		});
	</script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
	<script type="text/javascript" src="./js/print.js"></script>
</body>

</html>