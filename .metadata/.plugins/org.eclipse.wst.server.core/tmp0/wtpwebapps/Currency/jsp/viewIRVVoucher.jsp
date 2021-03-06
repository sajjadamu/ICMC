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
<link rel="shortcut icon" href="./resources/logo/yes_favicon.ico"
	type="image/x-icon">
<title> IRV Voucher</title>

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

<!-- DataTable -->
<script type="text/javascript" charset="utf8"
	src="./resources/js/jquery.dataTables.min.js"></script>
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
<script type="text/javascript" charset="utf8"
	src="./resources/js/dataTables.jqueryui.js"></script>
<link rel="stylesheet" type="text/css"
	href="./resources/css/dataTables.jqueryui.css">
<link rel="stylesheet" type="text/css"
	href="./resources/css/iRvstyle.css">
<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.dataTables.css">
<!-- DataTable -->

<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-233"
	data-genuitec-path="/Currency/src/main/webapp/jsp/viewIRVVoucher.jsp">
	<div class="modal fade" id="myModal" role="dialog"
		data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-233"
		data-genuitec-path="/Currency/src/main/webapp/jsp/viewIRVVoucher.jsp">
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
						<!-- <div class="panel-heading">

							<ul>
								<li><a href="#"><i class="fa fa-table fa-fw"></i> Add
										New Bin</a></li>
								<li><a href="#"><i class="fa fa-table fa-fw"></i>
										Remove Bin</a></li>
							</ul>
							Bin Data
						</div> -->
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<form id="showAll">
									<div class="container Outer_Cont">
										<header>
											<div class="row">
												<div class="col-md-12">
													<div>
														<div class="sackLock_Contnr">sack Lock No :</div>
														<h1>
															ICICI<span>Bank</span>
														</h1>
													</div>
													<h2>ICICI Bank Ltd.,</h2>
													<h3>INTEGRATED CURRENCY MANAGEMENT CENTER,</h3>
													<div class="Date_Contnr">
														<table>
															<tr>
																<td>Date:</td>
																<td>&nbsp;</td>
															</tr>
															<tr>
																<td>Vehicle no:</td>
																<td>&nbsp;</td>
															</tr>
														</table>
													</div>
												</div>
											</div>
										</header>
										<div class="main_contnr space"></div>
										<main class="space">
										<div class="row">
											<div class="col-md-12">
												<table class="bundle-controling">
													<c:forEach var="record" items="${records}">

														<tr>
															<td>${record.denomination}</td>
															<td>X</td>
															<td>${record.total}</td>
															<td></td>
															<Td>${record.bundleAndDenominationTotal}</Td>
														</tr>
													</c:forEach>
													<tr>
														<td></td>
														<td></td>
														<td>
														<td></td>
														<td>${iRVVoucherWrapper.total}</td>
													</tr>
												</table>
											</div>
										</div>
										<div class="main_contnr">
											<div class="col-md-12">
												<h3>Amount In Words: Rs</h3>
												<p>
													The above mentioned currency is handed over in wired
													bundles which are placed in trunck duly locked by Issuing
													bank official keys of <BR> which are with concerned
													banks.
												</p>
											</div>
										</div>
										<div class="space">
											<div class="col-md-6 col-sm-6 main_contnr">
												<div class="lable_Contnr">
													<span>HANDED OVER BY :</span> <span>NAME :</span>
												</div>
												<table>
													<tr>
														<td width="40%">SIGNATURE AND SEAL</td>
														<td>DATE</td>
														<td>TIME</td>
													</tr>
												</table>
											</div>
											<div class="col-md-6 col-sm-6 main_contnr">
												<div class="lable_Contnr">
													<span>HANDED OVER BY :</span>
												</div>
												<table>
													<tr>
														<td width="40%">SIGNATURE AND SEAL</td>
														<td>DATE</td>
														<td>TIME</td>
													</tr>
												</table>
											</div>
										</div>
										<div class="main_contnr space">
											<center>AUTHORISED PERSON OF ICICI BANK</center>
										</div>
										<div class="space">
											<div class="col-md-6 col-sm-6 main_contnr">
												<div class="lable_Contnr">
													<span>HANDED OVER BY :</span> <span>NAME :</span>
												</div>
												<table>
													<tr>
														<td width="40%">SIGNATURE AND SEAL</td>
														<td>DATE</td>
														<td>TIME</td>
													</tr>
												</table>
											</div>
											<div class="col-md-6 col-sm-6 main_contnr">
												<div class="lable_Contnr">
													<span>HANDED OVER BY :</span>
												</div>
												<table>
													<tr>
														<td width="40%">SIGNATURE AND SEAL</td>
														<td>DATE</td>
														<td>TIME</td>
													</tr>
												</table>
											</div>
										</div>
										<div class="main_contnr space">
											<P>From &nbsp;:&nbsp; ICICI</P>
											<P>TO&nbsp; : &nbsp;ICICI</P>
										</div>
										</main>
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
    $(document).ready(function () {
        $('#tableValue').dataTable({
        	
        	"pagingType": "full_numbers",
        	
        });
    });
    </script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>