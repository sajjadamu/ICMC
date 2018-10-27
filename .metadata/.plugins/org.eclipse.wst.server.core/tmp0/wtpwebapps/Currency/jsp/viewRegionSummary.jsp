<!DOCTYPE html>
<%@page import="com.chest.currency.enums.Zone"%>
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
<script type="text/javascript" src="./js/jquery.validate.min.js"></script>
<link rel="shortcut icon" href="./resources/logo/favicon.ico"
	type="image/x-icon">
<title>ICICI : Dashboard</title>

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
<link
	href="./resources/bower_components/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="./resources/dist/css/style.css">

<script type="text/javascript" charset="utf-8">
     function getICMC(){
        $.getJSON(
             "icmcListForUserAdministration.json",
             {region: $('select#region').val()},
             function(data) {
                  var html = '';
                  var len = data.length;
                  var option = '<option value="">Select ICMC</option>';
                  for(var i=0; i<len; i++){
                	  option += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
                   }
                  $('select#icmcName').empty();
                  $('select#icmcName').append(option);
             }
          );
 }
</script>

<style type="text/css">
.main {
	margin: 0 auto;
}

.container_inn {
	width: 100%;
	float: left;
	box-sizing: border-box;
}

h1, h2, h3, h4, h5, p {
	margin: 0;
	padding: 0
}

ul, li {
	margin: 0;
	padding: 0;
	list-style-type: none;
}

body {
	font-family: arial;
	margin: 0;
	padding: 0;
}

.total-cash-sec {
	width: 50%;
	background: #feffb3;
	float: left;
}

.total-txt p {
	font-size: 17px;
	margin-left: 22px;
	padding-bottom: 10px;
}

.total-txt h2 {
	font-size: 27px;
	margin-left: 23px;
	padding-bottom: 14px;
}

.total-txt {
	background: #feffb3;
	padding-top: 17px;
	border-bottom: 1px solid #9c9c9c;
}

.total-txt-a p {
	font-size: 14px;
	margin-left: 23px;
	margin-top: 17px;
}

.total-txt-a {
	background: #feffb3;
	width: 40%;
	float: left;
}

.total-txt-a h2 {
	font-size: 19px;
	margin-left: 23px;
	margin-top: 9px;
	margin-bottom: 28px;
}

.calculate-bb {
	width: 50%;
	float: left;
}

.cal-img {
	width: 50%;
	float: left;
	text-align: center;
}

.total-txt-a.flo-none p {
	margin-top: 0px;
}

.total-txt-a.flo-none {
	width: 52%;
	background: transparent;
	height: 45px;
	margin-bottom: 10px;
	border-left: 9px solid #053c6d;
}

.cal-sec {
	width: 50%;
	margin-top: 37px;
	float: left;
}

.first-sec {
	margin-top: 20px;
}

.second-sec {
	width: 100%;
	float: left;
}

ul.countryy li {
	width: 23.4%;
	display: inline-block;
	height: 220px;
	margin-right: 11px;
	border: 1px solid gray;
	position: relative;
}

.down-sec {
	position: absolute;
	bottom: 0;
}

.upper-sec h1 {
	font-size: 27px;
	font-weight: 400;
	margin-top: 22px;
	margin-left: 20px;
}

.upper-sec p {
	font-size: 16px;
	margin-top: 16px;
	margin-left: 21px;
}

.upper-sec h2 {
	margin-top: 10px;
	margin-left: 23px;
	font-size: 27px;
}

.down-sec {
	position: absolute;
	background: #ff7f7e;
	height: 70px;
	width: 50%;
	float: left;
	bottom: 0;
}

.down-sec p {
	margin-left: 22px;
	margin-top: 14px;
	font-size: 11px;
}

.down-sec h2 {
	font-size: 16px;
	margin-left: 21px;
	margin-top: 9px;
}

.down-sec.down-d {
	position: absolute;
	right: 0;
}

ul.countryy {
	margin-top: 20px;
}

.zone-details h1 {
	font-size: 22px;
	font-weight: 200;
	margin-top: 20px;
	margin-bottom: 10px;
}

.t-table {
	width: 100%;
	text-align: left;
}

table.t-table tr th {
	background: #d2d2d2;
	border: 1px solid #b1b1b1;
}

table.t-table tr td {
	border: 1px solid #e8e7e7;
	background: whitesmoke;
}

tr.last-tr td {
	background: white !important;
	font-weight: bold;
}

td.campass {
	background: #ff7f7e !important;
}

td.campass-1 {
	background: #ffb180 !important;
}

td.campass-2 {
	background: #fee27e !important;
}

td.campass-3 {
	background: #7fbf82 !important;
}

.color-a {
	background: #ff7f7e;
}

.color-b {
	background: #ffb180;
}

.color-c {
	background: #fee27e;
}

.color-d {
	background: #7fbf82;
}

.region-con h1 {
	font-size: 22px;
	font-weight: 200;
	margin-top: 20px;
	margin-bottom: 10px;
}

.region-con-sec {
	float: left;
	width: 100%;
	border-top: 1px solid #eee;
	border-bottom: 1px solid #eee;
	padding-top: 13px;
}

.region-con-drop {
	width: 50%; /* text-align: right; */
	float: left;
	padding-right: 30px;
	box-sizing: border-box;
}

.region-con-drop span {
	font-size: 18px;
	margin-right: 18px;
}

.region-con-drop select {
	width: 30%;
	padding: 7px;
}

.text-leftt {
	text-align: left;
	padding-left: 30px;
	box-sizing: border-box;
}

.zone-tablee {
	margin-top: 0;
	width: 100%;
	float: left;
}

.radio-btn-con h2 {
	font-size: 14px;
	font-weight: 600;
	margin-top: 30px;
	margin-bottom: 10px;
}

ul.checkicon {
	margin-top: 20px;
	float: left;
	width: 100%;
	margin-bottom: 20px;
}

ul.checkicon li {
	float: left;
	width: 12%;
}

ul.countryy li:nth-child(1) {
	border: 1px solid #ff7f7e;
}

ul.countryy li:nth-child(2) {
	border: 1px solid #ffb180;
}

ul.countryy li:nth-child(3) {
	border: 1px solid #fee27e;
}

ul.countryy li:nth-child(4) {
	border: 1px solid #7fbf82;
}

.color-txt-1 {
	color: #ff7f7e;
}

.color-txt-2 {
	color: #ffb180;
}

.color-txt-3 {
	color: #e0bd3f;
}

.color-txt-4 {
	color: #7fbf82;
}

.flo-c {
	border-left: 9px solid #dc5e12 !important;
}

.zone-table td, .zone-tablee td, .zone-table th, .zone-tablee th {
	padding: 10px
}

.radio-btn-con {
	width: 100%;
	float: left;
}

.region-drop {
	width: 100%;
	float: left;
}

.region-drop li {
	float: left;
	margin-right: 20px;
}

.region-drop li label {
	float: left;
	margin-right: 10px;
	line-height: 34px;
}

.region-drop span {
	float: left;
}

.icmc_box span {
	width: 24%;
	float: left;
}

.radio-btn-con button {
	float: left;
	width: 19%;
	padding: 5px;
	margin-left: 14px;
}
</style>

<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-253"
	data-genuitec-path="/Currency/src/main/webapp/jsp/viewRegionSummary.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-253"
		data-genuitec-path="/Currency/src/main/webapp/jsp/viewRegionSummary.jsp">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />
		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">Dashboard</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper12">

								<div class="main">
									<div class="container_inn">
										<div class="first-sec">
											<div class="total-cash-sec">
												<div class="total-txt">
													<p>Total cash available in Region</p>
													<h2>Rs.${flatSummary.total}</h2>
												</div>
												<div class="total-txt-a">
													<p>Total Notes</p>
													<h2>${flatSummary.atm+flatSummary.fresh+flatSummary.issuable+flatSummary.unprocess+flatSummary.soiled}</h2>
												</div>
												<div class="total-txt-a">
													<p>Total Coins</p>
													<h2>${flatSummary.coins}</h2>
												</div>
											</div>
											<div class="calculate-bb">
												<div class="center-box">
													<div class="cal-img">
														<img src="img/cal.png">
													</div>
													<div class="cal-sec">
														<div class="total-txt-a flo-none">
															<p>Total Notes</p>
															<h2>${flatSummary.atm+flatSummary.fresh+flatSummary.issuable+flatSummary.unprocess+flatSummary.soiled}</h2>
														</div>
														<div class="total-txt-a flo-none flo-c">
															<p>Total Coins</p>
															<h2>${flatSummary.coins}</h2>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>

								<div class="main">
									<div class="container_inn">
										<div class="region-con">
											<h1>Region Detail</h1>
										</div>

										<div class="zone-tablee">
											<table cellpadding="10" cellspacing="0" border="1"
												class="t-table">
												<tr class="t-table-tr">
													<th>ATM</th>
													<th>Fresh</th>
													<th>Issuable</th>
													<th>Unprocess</th>
													<th>Soiled</th>
													<th>Coins</th>
													<th>Total Value</th>
												</tr>
												<tr>
													<td>${flatSummary.atm}</td>
													<td>${flatSummary.fresh}</td>
													<td>${flatSummary.issuable}</td>
													<td>${flatSummary.unprocess}</td>
													<td>${flatSummary.soiled}</td>
													<td>${flatSummary.coins}</td>
													<td>${flatSummary.total}</td>
												</tr>
											</table>
										</div>

										<form:form id="userPage" name="userPage" action="icmcSummary"
											method="post" modelAttribute="user" autocomplete="off">

											<div class="region-con-sec">
												<ul class="region-drop">
													<li><label>Select Region</label> <span> <form:select
																path="region" id="region" name="region"
																cssClass="form-control"
																onchange="getICMC($('select#region').val());">
																<form:option value="">Select Region</form:option>
																<form:option value="${regionId}" />
															</form:select>
													</span></li>
												</ul>
											</div>

											<div class="radio-btn-con" id="target">
												<h2>List of ICMC</h2>
												<div class="icmc_box">
													<span> <form:select path="icmcName" id="icmcName"
															name="icmcName" cssClass="form-control">
															<form:option value="">Select ICMC</form:option>
														</form:select>
													</span>
													<button type="submit"
														class="btn btn-lg btn-success btn-block" value="Details">Show
														ICMC Details</button>
												</div>
											</div>
										</form:form>

									</div>

								</div>
							</div>
							<!-- /.panel-body -->
						</div>
						<!-- /.panel -->
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /#page-wrapper -->
			</div>
			<!-- /#wrapper -->
		</div>
	</div>
	<!-- jQuery -->
	<!-- <script src="./resources/bower_components/jquery/dist/jquery.min.js"></script> -->

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
	            region: {
	            	required:true,
	            },
	            icmcName: {
	         		required:true,
	            },
		    },
		    // Specify validation error messages 
		    messages: {
		    	region: {
		    		required:"Please Select Region",
		    	},
		    	icmcName: {
	            	 required:"Please Select ICMC",
	             },
		    },
		    submitHandler: function(form) {
		    	var isValid = $("form[name='userPage']").validate().form();
		    	if(isValid){
		      		form.submit();
		    	}
		    }
		  });
		});
	</script>

	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>
