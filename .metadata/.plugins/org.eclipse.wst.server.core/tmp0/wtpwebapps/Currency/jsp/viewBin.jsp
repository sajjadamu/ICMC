<!DOCTYPE html>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.math.BigInteger"%>
<%@page import="com.chest.currency.entity.model.User"%>
<html lang="en">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="./resources/logo/yes_favicon.ico"
	type="image/x-icon">
<title> Bin Data</title>
<style type="text/css">
.titleheading {
	font-size: 18px;
	text-align: left;
	border-bottom: #ccc 2px solid;
	margin-bottom: 10px;
}
</style>
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

<script type="text/javascript">
	function ajaxAvailableCapacity(str) {
		addHeaderJson();
		// get the form values  
		var binNumber = str
		$.ajax({
			type : "POST",
			url : "././availableCapacity",
			data : "binNumber=" + binNumber,
			success : function(response) {
				// we have the response  
				var res = JSON.stringify(response);
				var a = ({});

				if (JSON.stringify(a) == '{}') {
					var html = "";
					html+="Bin Not In Use";
					$('#modal-body').html(html);
					$('#myModal').modal('show');
				}

				var newStr = res.substring(1, res.length - 1); // Remove Array Brackets
				var data = newStr.split(",");
				var html = "";
				var bin = data[1].split(":")[1].toString().replace(/"/g, "");
				var denomination = data[2].split(":")[1];
				var binType = data[6].split(":")[1].toString()
						.replace(/"/g, "");
				var capacity = data[3].split(":")[1];
				var receiveBundle = data[4].split(":")[1];
				html += "Bin Number :" + bin + "</br>";
				html += "Denomination : " + denomination + "</br>";
				html += "Bin Type : " + binType + "</br>";
				html += "Capacity :" + capacity + "</br>";
				html += "Bundle :" + receiveBundle + "</br>";
				var data = capacity - receiveBundle;
				html += "Available :" + data + "</br>";
				$('#modal-body').html(html);
				$('#myModal').modal('show');
			},
			error : function(e) {
				alert('Avail capacity Error: ' + e);
			}
		});
	}
</script>


<script type="text/javascript">
	function dropdownValidation(){
		var denomination = $('#denomination').val();
		if(denomination==-1){
			alert("select Denomination");
			return false;
		}
		var type = $('#binType').val();
		if(type==-1){
			alert("Select Type");
			return false;
		}
		return true;
	}
</script>

<!-- <script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
 --></head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-203"
	data-genuitec-path="/Currency/src/main/webapp/jsp/viewBin.jsp">
	<div class="modal fade" id="myModal" role="dialog"
		data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-203"
		data-genuitec-path="/Currency/src/main/webapp/jsp/viewBin.jsp">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Bin's Status</h4>
				</div>
				<div class="modal-body" id="modal-body">
					<p>This is a small modal.</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>


	<div id="wrapper">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">Bin Dashboard</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<%-- <form id="showAll"> --%>
								<form:form id="userPage" name="userPage" action="viewBinStatus"
									method="post" modelAttribute="user"
									onsubmit="return dropdownValidation()">
									<div class="row">
										<div class="col-sm-12">
											<div class="col-sm-2 no-margin">
												<div class="form-group">
													<div class="col-sm-6 col-md-4">
														<form:select path="denomination" id="denomination"
															name="denomination"
															class="form-control deno-figure-select"
															style="width: 140px;">
															<option value="-1">Denomination</option>
															<form:options items="${denominationList}"
																itemValue="denomination" itemLabel="denomination"
																name="denomination" />
														</form:select>
													</div>
												</div>
											</div>
											<div class="col-sm-2 no-margin">
												<div class="form-group">
													<div class="col-sm-6 col-md-4">
														<form:select path="binType" id="binType" name="binType"
															class="form-control deno-figure-select"
															style="width: 140px;">
															<option value="-1">Type</option>
															<form:options items="${currencyProcessType}" />
														</form:select>

													</div>
												</div>
											</div>

											<%-- <% User user = (User) session.getAttribute("login"); 
//BigInteger icmcid = new BigInteger(0);

//user.getIcmcId();
if(user.getIcmcId().equals(new Long(10))){
	System.out.print("iffffffffff   "+user.getIcmcId());
}else{
	System.out.print("elsssssssss   "+user.getIcmcId());	
}



%> --%>
											<div class="col-sm-2 no-margin">
												<div class="button">
													<button type="submit" class="btn btn-default">Search</button>
												</div>
											</div>
											<!-- <div class="col-sm-2 no-margin">
												<div class="button">
													<a href="deleteBinMasterByIcmcId" >Delete BinMaster</a>
												</div>
											</div> -->
											<!-- <div class="col-sm-2 no-margin">
												<div class="button">
													<a href="deleteBinTransactionByIcmcId">Delete BinTransaction</a>
												</div>
											</div> -->
											<!-- <div class="col-sm-2 no-margin">
												<div class="button">
													<a href="deleteBranchReceiptByIcmcId" >Delete BranchReceipt</a>
												</div>
											</div> -->

											<div class="col-sm-2 no-margin"></div>
										</div>
										<div class="col-sm-12"></div>
										<!-- /.row (nested) -->
										<div class="row bins">
											<div class="col-sm-12">
												<table
													class="table table-striped table-bordered table-hover">

													<jsp:include page="binSummary.jsp" />
													<jsp:include page="binSummaryForCoins.jsp" />
												</table>
												<div align="center" class="titleheading">
													<b>Boxes</b>
												</div>
												<table class="bundle-controling">

													<tr>
														<c:forEach var="row" items="${recordsListBox}">
															<td bgcolor="${row.color}"><a href="#"
																onclick="ajaxAvailableCapacity('${row.binNumber}')"
																id="'${row.binNumber}'">${row.binNumber} </a></td>
														</c:forEach>
													</tr>
												</table>
												<div align="center" class="titleheading">
													<b>Bins</b>
												</div>
												<table class="bundle-controling">
													<tr>
														<c:forEach var="row" items="${recordsListBin}">
															<td bgcolor="${row.color}"><a href="#"
																onclick="ajaxAvailableCapacity('${row.binNumber}')"
																id="'${row.binNumber}'">${row.binNumber} </a></td>
														</c:forEach>
													</tr>

												</table>
												<div align="center" class="titleheading">
													<b>Bags</b>
												</div>
												<table>
													<tr>
														<c:forEach var="row" items="${recordsListBag}">
															<td bgcolor="${row.color}"><a href="#"
																onclick="ajaxAvailableCapacity('${row.binNumber}')"
																id="'${row.binNumber}'">${row.binNumber} </a></td>
														</c:forEach>
													</tr>

												</table>
												<div align="center" class="titleheading">
													<b>Record For Null</b>
												</div>
												<table>

													<tr>
														<c:forEach var="row" items="${recordsForNull}">
															<td bgcolor="${row.color}">
															<td bgcolor="D0D3D4">&nbsp${row.binNumber}&nbsp</td>
														</c:forEach>
													</tr>
												</table>
											</div>
										</div>
									</div>
								</form:form>

								<div>
									<!-- <img src="./resources/logo/icici-Bin-Color.png"> -->

									<img src="./resources/logo/icmc-bin-color.png">

									<!-- <img src="./resources/logo/icici-color.png"> -->
								</div>

							</div>

						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				<!-- /.col-lg-12 -->
			</div>

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

	<!-- DataTables JavaScript -->
	<script
		src="./resources/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
	<script
		src="./resources/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="./resources/dist/js/sb-admin-2.js"></script>

	<!-- Page-Level Demo Scripts - Tables - Use for reference -->
	<script>
		$(document).ready(function() {
			$('#dataTables-example').DataTable({
				responsive : true
			});
		});
	</script>

	<script type="text/javascript">
	$(function() {
	  $("form[name='userPage']").validate({
	    rules: {
	    	denomination: {
            	required:true,
            },
            binType: {
	    		required:true,
	    	},
           
	    },
	    // Specify validation error messages
	    messages: {
	    	denomination: {
           	 required:"Please select Denomination",
            },
            binType: {
	    		required:"Please select Currency Type",
	    	 },
	    	 
	    },
	    submitHandler: function(form) {
	      form.submit();
	    }
	  });
	});
	</script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>