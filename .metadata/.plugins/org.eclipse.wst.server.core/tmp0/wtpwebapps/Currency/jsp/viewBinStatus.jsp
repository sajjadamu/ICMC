<!DOCTYPE html>
<html lang="en">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="./resources/logo/favicon.ico"
	type="image/x-icon">
<title>ICICI : Bin Status</title>

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
					html += "Bin Not In Use";
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
				html += "Receive Bundle :" + receiveBundle + "</br>";
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

<script>
	function goBack() {
		window.history.back();
	}
</script>
<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-209"
	data-genuitec-path="/Currency/src/main/webapp/jsp/viewBinStatus.jsp">

	<div class="modal fade" id="myModal" role="dialog"
		data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-209"
		data-genuitec-path="/Currency/src/main/webapp/jsp/viewBinStatus.jsp">
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
			<!-- <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Tables</h1>
                </div>
                /.col-lg-12
            </div> -->
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<ul>
								<li><a href="viewBin"><i class="fa fa-table fa-fw"></i>Go
										Back</a></li>
							</ul>
							Bin Detail
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div>
								<form id="showAll">

									<div class="row">
										<div class="col-sm-12"></div>
										<!-- /.row (nested) -->
										<div>
											<div>
												<table style="width: 100%" border="1">
													<tr>
														<th>Bin Number</th>
														<th>Denomination</th>
														<th>Bin Type</th>
														<Th>Capacity</Th>
														<th>Bundle</th>
														<th>Available</th>
													</tr>
													<c:forEach var="row" items="${records}">
														<tr>
															<td>${row.binNumber}</td>
															<td>${row.denomination}</td>
															<td>${row.binType}</td>
															<td>${row.maxCapacity}</td>
															<td>${row.receiveBundle}</td>
															<td>${row.maxCapacity-row.receiveBundle}</td>
														</tr>
													</c:forEach>
													<c:if test="${empty records}">
														<p style="color: green">
															<b>No data available, Click on Back Button</b>
														</p>
														<BR>
														<!-- <button onclick="goBack()">Go Back</button> -->
													</c:if>
												</table>
											</div>
										</div>
									</div>
								</form>
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
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>