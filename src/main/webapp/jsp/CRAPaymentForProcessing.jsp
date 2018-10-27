<!DOCTYPE html>
<%@page import="com.chest.currency.enums.BinCategoryType"%>
<%@page import="com.chest.currency.enums.CashSource"%>
<%@page import="java.util.Date"%>
<html lang="en">
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.List"%>
<%@page import="com.mysema.query.Tuple"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="./resources/logo/favicon.ico"
	type="image/x-icon">
<title>ICICI : CRA Payment For Processing</title>


<script type="text/javascript">
	function SavePrint(str) {
		/* alert($('#craId'+str).text()); */
		addHeader();
		var craPaymentForProcessing={
				"id":$('#id'+str).val(),
				"craId":$('#craId'+str).text(),
				"currencyType":$('#currencyType'+str).val(),
				"denomination":$('#denomination'+str).text(),
				"pendingRequestedBundle":$('#pendingBundle'+str).text(),
				"bundle":$('#requestBundle'+str).val(),
		}
		
		 var pendingBundle = parseInt(($('#pendingBundle'+str).text()));
		 var requestBundle = parseInt(($('#requestBundle'+str).val()));
		 if(requestBundle<=pendingBundle)
			 {
		
		$
				.ajax({
					type : "POST",
					 contentType : 'application/json; charset=utf-8',
				      dataType : 'json',
					url : "././QRPathProcessForCRAPayment",
					data: JSON.stringify(craPaymentForProcessing),
					success : function(response) {
						var str2 = '';
				    		for(var i=0;i<response.bundle;i++)
				    			{
				    		str2+= '<div><div style="width:25%;float:left;" ><img src="./files'+response.filepath+'" alt="QRPrint"></div>'
				    		str2 += "<div>Type : "
								+response.currencyType
								+ "</br>"
								 + "Deno. : "
								+ response.denomination
								+ "</div></div><BR>";
								}
				    	$('#printSection').html(str2);
						$('#printSection').show();
						$.print("#printSection");
						$('#printSection').hide();
						$('#print').prop('disabled', true);
						
						alert('Record submit.');
						window.location='././CRAPaymentForProcessing';
					},
					error : function(e) {
						//alert('Print Error: ' + e);
						alert('Error: ' + e.responseJSON.message);
					}
				});
			 }
		 else
			 {
			 alert("Require Bundle Must be less than or equal to available Bundle")
			 }
	}
</script>

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
	href="./resources/css/jquery.dataTables.css">

<!-- DataTable -->

</head>

<body oncontextmenu="return false;">
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

						<!-- /.panel-heading -->

						<div class="panel-body">
							<!-- <Table align="center">
								<Tr>
									<th style="color: blue"><u>INDENT SUMMARY FOR CRA</u></th>
								</Tr>
							</Table> -->
							<BR>
							<table class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<td>CRA ID</td>
										<th>Denomination</th>
										<th>Bundle</th>
										<th>Category</th>
										<th>Require Bundle</th>
										<th>Action</th>
									</tr>

								</thead>

								<tbody>
									<c:forEach var="row" items="${records}">
										<tr>
											<%-- <td id="id${row.id}">${row.id}</td> --%>
											<input type="hidden" id="id${row.id}" value="${row.id}">
											<td id="craId${row.id}">${row.craId}</td>
											<td id="denomination${row.id}">${row.denomination}</td>
											<td id="pendingBundle${row.id}">${row.pendingRequestedBundle}</td>
											<td><select name="currencyType"
												id="currencyType${row.id}">
													<option value="ATM">ATM</option>
													<option value="ISSUABLE">ISSUABLE</option>
											</select></td>
											<td><input type="text" id="requestBundle${row.id}"></td>
											<td><input type="button" value="Generate QR"
												onclick="SavePrint(${row.id});this.disabled=true"></td>
										</tr>
									</c:forEach>
								</tbody>

							</table>
						</div>
						<div id="printSection" style="display: none;"></div>




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

	<script src="./resources/js/jQuery.print.js"></script>

	<!-- Page-Level Demo Scripts - Tables - Use for reference -->
	<script>
		$(document).ready(function() {
			$('#tableValue').dataTable({

				"pagingType" : "full_numbers",

			});
		});
	</script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>