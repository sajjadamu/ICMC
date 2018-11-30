<!DOCTYPE html>
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
<script type="text/javascript" src="./resources/js/jquery-1.9.1.min.js"></script>

<link rel="shortcut icon" href="./resources/logo/favicon.ico"
	type="image/x-icon">
<title>ICICI : Bin Data</title>

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

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
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

<style>
.panel-body {
	padding-bottom: 0;
}

.panel-default>.panel-heading {
	background: #F7F7F7;
}

div.panel-default {
	border-radius: 0 !important;
}

.panel-heading {
	overflow: hidden;
	background: #F5F5F5;
}

.collapse .panel-default {
	background: none;
	border: none;
}

div.DesabilityContr div.panel-heading {
	background: none;
	padding: 10px 0;
}

.zone_Contnr1 {
	padding: 0 !important;
	border: 1px solid #ddd;
}

.zone_Contnr1>h3 {
	background: #053C6D;
	margin: 0;
	text-align: center;
	color: #fff;
	font-size: 14px;
	font-weight: 700;
	padding: 20px;
}

.zone_Contnr1>p {
	border: 1px solid #ddd;
	padding: 8px;
	font-weight: normal;
	text-align: center;
	font-size: 14px;
}

.zone_Contnr1>p>a {
	display: block;
}

.zone_Contnr1 label:last-child {
	border: none;
	font: normal;
}

.panel-heading table>thead {
	background: #053C6D;
	color: #fff;
}

.panel-group .panel+.panel {
	border: none;
	margin-top: 0;
}

.panel-group .panel+.panel>div {
	border: 1px solid #ddd;
	border-top: 0;
}

.collapse .panel-heading {
	border: 1px solid #ddd;
	border-radius: 0;
}

.table-bordered>tbody>tr>td:nth-child(1) {
	width: 26%;
}

.table-bordered>tbody>tr>td:nth-child(2) {
	width: 26.5%;
}
</style>


<style type="text/css">
div#mainClasDiv {
	width: 100%;
	float: left;
}

.zoneDiv {
	width: 20%;
	float: left;
}

.loc-sub-cat {
	width: 100%;
	float: left;
}

.zoneDivTable {
	width: 80%;
	float: left;
}

.regionDivTable {
	width: 80%;
	float: left;
}

.toggle {
	display: none;
}
</style>


<script type="text/javascript">
	
	function regionDeatils(sta){
		addHeaderJson();
		/* $.ajax({
			type : "POST",
			url : "././getRegion",
			data : "zone=" + sta,
			success : function(response) {
				var newStr = response.toString();
		        var data = newStr.split(",");
		        for(i=0;i<data.length;i++)
				{
				option='<tr><td></td><td><button onclick=icmcDeatils("'+data[i].trim()+'")>+</button><input type=text value="'+data[i].trim()+'" name="'+data[i].trim()+'"></td></tr>';
				//$('.'+sta).append(option); 
				}
			},
			error : function(e) {
				alert('Region Error: ' + e);
			}
		});
	 */
	}
	var val;
	function icmcDeatils(region){
		
			$.ajax({
				type : "POST",
				url : "././getIcmcList",
				data : "region=" + region,
				success : function(response) {
				
					var newStr = response.toString();
			        var data = newStr.split(",");
			    	if(data[0]!=''){
			    		 for(i=0;i<data.length;i++)
							{
							 option='<tr><td></td><td><button onclick=getParticularIcmc("'+data[i].trim()+'")>+</button><input type=text value="'+data[i].trim()+'" name="'+data[i].trim()+'"></td></tr>';
							 $('.icmc0').append(option); 
							}
			    	}
			       
				},
				error : function(e) {
					alert('Region Error: ' + e);
				}
			});	
		
	}
	
	
	function getParticularIcmc(nameIcmc){
		alert(nameIcmc)
		window.location='././viewBinFilter';	
	}
	
	
	$(document).ready(function(){
	    $(".toggle-btn").click(function(){
	        $(".toggle").slideToggle();
	    });
	});

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
						<div class="panel-heading">Bin Dashboard</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">

								<div class="panel-group" id="accordion">
									<div class="panel panel-default1">
										<div class="panel-heading">
											<div class="col-md-2 zone_Contnr1">
												<h3>Region</h3>
												<c:forEach var="regionValue" items="${regionList}">
													<p class="panel-title">
														<a data-toggle="collapse" data-parent="#accordion"
															href="#${regionValue}">${regionValue}</a>
													</p>

												</c:forEach>

											</div>
											<div class="col-md-10">
												<table class="table table-bordered">
													<thead>
														<tr>
															<th>ATM</th>
															<th>FRESH</th>
															<th>ISSUABLE</th>
															<th>SOILED</th>
															<th>UNPROCESSED</th>
															<th>TOTAL VALUE</th>
														</tr>
													</thead>
													<tbody>
														<tr>
															<td>${summaryRecordForNorthATM}</td>
															<td>${summaryRecordForNorthFresh}</td>
															<td>${summaryRecordForNorthIssuable}</td>
															<td>${summaryRecordForNorthSoiled}</td>
															<td>${summaryRecordForNorthUnprocess}</td>
															<td>${summaryRecordForNorthATM+summaryRecordForNorthFresh+summaryRecordForNorthIssuable+summaryRecordForNorthSoiled+summaryRecordForNorthUnprocess}</td>
														</tr>

														<tr>
															<td>${summaryRecordForSouthATM}</td>
															<td>${summaryRecordForSouthFresh}</td>
															<td>${summaryRecordForSouthIssuable}</td>
															<td>${summaryRecordForSouthSoiled}</td>
															<td>${summaryRecordForSouthUnprocess}</td>
															<td>${summaryRecordForSouthATM+summaryRecordForSouthFresh+summaryRecordForSouthIssuable+summaryRecordForSouthSoiled+summaryRecordForSouthUnprocess}</td>
														</tr>

														<tr>
															<td>${summaryRecordForEastATM}</td>
															<td>${summaryRecordForEastFresh}</td>
															<td>${summaryRecordForEastIssuable}</td>
															<td>${summaryRecordForEastSoiled}</td>
															<td>${summaryRecordForEastUnprocess}</td>
															<td>${summaryRecordForEastATM+summaryRecordForEastFresh+summaryRecordForEastIssuable+summaryRecordForEastSoiled+summaryRecordForEastUnprocess}</td>
														</tr>

														<tr>
															<td>${summaryRecordForWestATM}</td>
															<td>${summaryRecordForWestFresh}</td>
															<td>${summaryRecordForWestIssuable}</td>
															<td>${summaryRecordForWestSoiled}</td>
															<td>${summaryRecordForWestUnprocess}</td>
															<td>${summaryRecordForWestATM+summaryRecordForWestFresh+summaryRecordForWestIssuable+summaryRecordForWestSoiled+summaryRecordForWestUnprocess}</td>
														</tr>
													</tbody>
												</table>
											</div>
										</div>

									</div>

									<c:forEach var="regionValue" items="${regionList}">

										<div class="panel panel-default">
											<div id="${regionValue}" class="panel-collapse collapse">
												<div class="panel-body">
													<!--new_One-->
													<div class="panel-group" id="1st_Slider">
														<div class="panel panel-default3">
															<div class="panel-heading">



																<div class="col-md-2 zone_Contnr1">
																	<h3>ICMC</h3>

																</div>
																<div class="col-md-10">
																	<table class="table table-bordered">
																		<thead>
																			<tr>
																				<th>ATM</th>
																				<th>FRESH</th>
																				<th>ISSUABLE</th>
																				<th>SOILED</th>
																				<th>UNPROCESSED</th>
																				<th>TOTAL VALUE</th>
																			</tr>
																		</thead>
																		<tbody>
																			<tr>
																				<td>${summaryRecordForNorthATM}</td>
																				<td>${summaryRecordForNorthFresh}</td>
																				<td>${summaryRecordForNorthIssuable}</td>
																				<td>${summaryRecordForNorthSoiled}</td>
																				<td>${summaryRecordForNorthUnprocess}</td>
																				<td>${summaryRecordForNorthATM+summaryRecordForNorthFresh+summaryRecordForNorthIssuable+summaryRecordForNorthSoiled+summaryRecordForNorthUnprocess}</td>
																			</tr>

																			<tr>
																				<td>${summaryRecordForSouthATM}</td>
																				<td>${summaryRecordForSouthFresh}</td>
																				<td>${summaryRecordForSouthIssuable}</td>
																				<td>${summaryRecordForSouthSoiled}</td>
																				<td>${summaryRecordForSouthUnprocess}</td>
																				<td>${summaryRecordForSouthATM+summaryRecordForSouthFresh+summaryRecordForSouthIssuable+summaryRecordForSouthSoiled+summaryRecordForSouthUnprocess}</td>
																			</tr>

																			<tr>
																				<td>${summaryRecordForEastATM}</td>
																				<td>${summaryRecordForEastFresh}</td>
																				<td>${summaryRecordForEastIssuable}</td>
																				<td>${summaryRecordForEastSoiled}</td>
																				<td>${summaryRecordForEastUnprocess}</td>
																				<td>${summaryRecordForEastATM+summaryRecordForEastFresh+summaryRecordForEastIssuable+summaryRecordForEastSoiled+summaryRecordForEastUnprocess}</td>
																			</tr>

																			<tr>
																				<td>${summaryRecordForWestATM}</td>
																				<td>${summaryRecordForWestFresh}</td>
																				<td>${summaryRecordForWestIssuable}</td>
																				<td>${summaryRecordForWestSoiled}</td>
																				<td>${summaryRecordForWestUnprocess}</td>
																				<td>${summaryRecordForWestATM+summaryRecordForWestFresh+summaryRecordForWestIssuable+summaryRecordForWestSoiled+summaryRecordForWestUnprocess}</td>
																			</tr>
																		</tbody>
																	</table>
																</div>
															</div>




														</div>
													</div>
													<!--closed-->
												</div>
											</div>
										</div>




									</c:forEach>


								</div>
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