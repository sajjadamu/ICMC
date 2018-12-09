<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="./resources/logo/favicon.ico"
	type="image/x-icon">
<title>ICICI : Update Role</title>

<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="./js/jquery.validate.min.js"></script>
<style type="text/css">
.form-group.rolewrap {
	padding: 10px;
}

.rolewrap span {
	width: 30%;
	display: inline-block;
	vertical-align: top;
	font-size: 12px;
}

.panel-default>label {
	padding: 10px;
	border-bottom: #ccc 1px solid;
	display: block;
	background: #f1f1f1;
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
</head>
<body oncontextmenu="return false;">
	<div id="wrapper">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />
		<div id="page-wrapper">
			<!--  <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Forms</h1>
                </div>
                /.col-lg-12
            </div> -->
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">Edit Role</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-12">
									<!--<form role="form">-->
									<form:form id="userPage" name="userPage" action="updateRole"
										method="post" modelAttribute="user" autocomplete="off">

										<div class="form-group">
											<label>New Role</label>
											<form:input path="id" id="id" name="id" maxlength="32"
												cssClass="form-control" readonly="true" />
										</div>

										<div class="form-group">
											<label>ICMC Access</label><br>
											<form:radiobuttons items="${icmcAccessList}"
												path="icmcAccess" />
										</div>

										<div class="form-group">
											<label>Function Permissions</label><br>
										</div>

										<div class="panel panel-default">
											<label>Administration Module</label> Select All <input
												type="checkbox" id="checkAll">
											<div class="form-group rolewrap rolebox1">
												<form:checkboxes items="${ADMIN}" path="rolePermission" />
											</div>
										</div>

										<div class="panel panel-default">
											<label>Bin DashBoard Module</label>
											<div class="form-group">
												<form:checkboxes items="${BIN_DASHBOARD}"
													path="rolePermission" />
											</div>
										</div>

										<div class="panel panel-default">
											<label>Vault Management Module</label> Select All <input
												type="checkbox" id="checkAllVMM">
											<div class="form-group rolewrap rolebox2">
												<form:checkboxes items="${VAULT_MANAGEMENT}"
													path="rolePermission" />
											</div>
										</div>

										<div class="panel panel-default">
											<label>CIT/CRA Details Module</label>Select All <input
												type="checkbox" id="checkAllCCD">
											<div class="form-group rolewrap rolebox3">
												<form:checkboxes items="${CIT_CRA_DETAILS}"
													path="rolePermission" />
											</div>
										</div>

										<div class="panel panel-default">
											<label>Cash Receipt Module</label> Select All <input
												type="checkbox" id="checkAllCRM">
											<div class="form-group rolewrap rolebox4">
												<form:checkboxes items="${CASH_RECEIPT}"
													path="rolePermission" />
											</div>
										</div>

										<div class="panel panel-default">
										<label>Cash Payment Module</label>
											Select All <input type="checkbox" id="checkAllCPM">
											<div class="form-group rolewrap rolebox5">
												<div class="form-group">
													<form:checkboxes items="${CASH_PAYMENT}"
														path="rolePermission" />
												</div>
											</div>

											<div class="panel panel-default">
												<label>Processing Room Module</label> Select All <input
													type="checkbox" id="checkAllPRM">
												<div class="form-group rolewrap rolebox6">
													<form:checkboxes items="${PROCESSING_ROOM}"
														path="rolePermission" />
												</div>
											</div>

											<div class="panel panel-default">
												<label>Fake Note Management Module</label> Select All <input
													type="checkbox" id="checkAllFMM">
												<div class="form-group rolewrap rolebox7">
													<form:checkboxes items="${FAKE_NOTE_MANAGEMENT}"
														path="rolePermission" />
												</div>
											</div>
											<div class="panel panel-default">
												<label>MIgrations</label>
												<div class="form-group rolewrap">
													<form:checkboxes items="${MIGRATION}" path="rolePermission" />
												</div>
											</div>
											<div class="panel panel-default">
												<label>Reports Module</label>
												<div class="form-group">
													<form:checkboxes items="${REPORTS}" path="rolePermission" />
												</div>
											</div>
											<div class="panel panel-default">
												<label>Verification</label>
												<div class="form-group rolewrap">
													<form:checkboxes items="${VERIFICATION}"
														path="rolePermission" />
												</div>
											</div>

											<div class="form-group">
												<label>Status</label>
												<form:select path="status" id="status" name="status"
													cssClass="form-control">
													<form:options items="${statusList}" />
												</form:select>
											</div>

											<button type="submit"
												class="btn btn-lg btn-success btn-block" value="Details">Update</button>
									</form:form>
								</div>
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
	<!-- <script src="./resources/bower_components/jquery/dist/jquery.min.js"></script> -->

	<!-- Bootstrap Core JavaScript -->
	<script
		src="./resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script
		src="./resources/bower_components/metisMenu/dist/metisMenu.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="./resources/dist/js/sb-admin-2.js"></script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
	<script>
		$("#checkAll").click(
				function() {
					$('.rolebox1 input:checkbox').not(this).prop('checked',
							this.checked);
				});
		$("#checkAllVMM").click(
				function() {
					$('.rolebox2 input:checkbox').not(this).prop('checked',
							this.checked);
				});
		$("#checkAllCCD").click(
				function() {
					$('.rolebox3 input:checkbox').not(this).prop('checked',
							this.checked);
				});
		$("#checkAllCRM").click(
				function() {
					$('.rolebox4 input:checkbox').not(this).prop('checked',
							this.checked);
				});
		$("#checkAllCPM").click(
				function() {
					$('.rolebox5 input:checkbox').not(this).prop('checked',
							this.checked);
				});
		$("#checkAllPRM").click(
				function() {
					$('.rolebox6 input:checkbox').not(this).prop('checked',
							this.checked);
				});
		$("#checkAllFMM").click(
				function() {
					$('.rolebox7 input:checkbox').not(this).prop('checked',
							this.checked);
				});
	</script>
</body>

</html>