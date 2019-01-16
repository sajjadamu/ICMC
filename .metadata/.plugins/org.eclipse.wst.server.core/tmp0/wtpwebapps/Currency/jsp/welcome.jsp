<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="./resources/logo/yes_favicon.ico"
	type="image/x-icon">
<title>Welcome</title>

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
</head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-267"
	data-genuitec-path="/Currency/src/main/webapp/jsp/welcome.jsp">

	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-267"
		data-genuitec-path="/Currency/src/main/webapp/jsp/welcome.jsp">

		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">
						Welcome to
						<!-- e@ICMC -->
						Vault Management System
					</h1>

					<div style="color: blue">
						<c:out value="${CRAmsg}"></c:out>
					</div>
					<div style="color: blue">
						<c:out value="${SASmsg}"></c:out>
					</div>
					<div style="color: blue">
						<c:out value="${soiledMsg}"></c:out>
					</div>
					<div style="color: blue">
						<c:out value="${otherBankMsg}"></c:out>
					</div>
					<div style="color: blue">
						<c:out value="${indentMsg}"></c:out>
					</div>
					<div style="color: blue">
						<c:out value="${mutilatedFullValueMsg}"></c:out>
					</div>
					<div style="color: blue">
						<c:out value="${diversionMsg}"></c:out>
					</div>
					<div style="color: blue">
						<c:forEach var="row" items="${machineMessage}">
							<tr>
								<td>Your machine Number ${row.machineNumber}</td>
								<td>and Maintenance Date is <fmt:formatDate
										pattern="dd-MMM-yy" value="${row.nextMaintainanceDate}" /></td>
							</tr>
						</c:forEach>

					</div>
					<%--  <div style="color:blue"><c:out value="${forwardMsg}"></c:out></div>  --%>

				</div>
			</div>
		</div>
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
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>