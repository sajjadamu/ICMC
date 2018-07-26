<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<html lang="en">

<head>
<link rel="shortcut icon" href="./resources/logo/favicon.ico" type="image/x-icon">
<script src="./js/jquery-1.12.0.min.js"></script>
<script src="./js/unprocess.js"></script>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>ICICI :Bin Transaction Upload</title>

<script src="./resources/Currency/js/jquery.js"></script>
<!-- Bootstrap Core CSS -->
<link	href="./resources/bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- MetisMenu CSS -->
<link href="./resources/bower_components/metisMenu/dist/metisMenu.min.css"	rel="stylesheet">

<!-- Custom CSS -->
<link href="./resources/dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Custom Fonts -->
<link	href="./resources/bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

<link rel="stylesheet" type="text/css"	href="./resources/dist/css/style.css">
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
						<div class="panel-heading">Upload Bin Transaction Data</div>
						<div class="panel-body">
							<div class="row">
								<!--<form role="form">-->
								<form:form id="bintransaction" name="bintransaction" action="uploadBinTransaction" method="post"
									enctype="multipart/form-data" modelAttribute="user">

									<div align="center" style="color:white; background:green;"><b>${successMsg}</b></div><br>
									<div align="center" style="color:red; "><b>${errorMsg}</b></div><br>
									
									<div class="col-lg-6 form-group">
										<label>Choose Bin Transaction CSV File</label> 
										<c:if test="${migratedDataNotes eq 0 and migratedDataNotes eq 0 }">
										<input type="file" id="file" name="file" Class="form-control" /></c:if>
											<c:if test="${migratedDataNotes ne 0 or migratedDataNotes ne 0}">
											<input type="file" id="file" name="file" Class="form-control" readonly="readonly" disabled="disabled"/>
											<b style="color: red">Bin Transaction has been already uploaded</b></c:if>
										<a href="${documentFilePath}/BinTransaction.csv" download>Download CSV Format</a>
									</div>
									
									<div class="col-lg-12">
					<c:if test="${migratedDataNotes eq 0 and migratedDataNotes eq 0 }">
					<button id="uploadBinTransaction" style="width: 99px;" value="Details" class="btn btn-default" type="submit">Upload</button></c:if>
									</div>
								</form:form>
								<!-- /.col-lg-6 (nested) -->
								<!-- /.col-lg-6 (nested) -->
							</div>
							<!-- /.row (nested) -->
						</div>
						<!-- /.panel-body -->
					</div>
					
					<div class="panel panel-default">
						<div class="panel-heading">Upload Branch Receipt Data</div>
						<div class="panel-body">
							<div class="row">
								<!--<form role="form">-->
								<form:form id="branchReceipt" name="branchReceipt" action="uploadBranchReceipt" method="post"
									enctype="multipart/form-data" modelAttribute="user">
									
									<div align="center" style="color:white; background:green;"><b>${successMsgForBR}</b></div><br>
									<div align="center" style="color:red; "><b>${errorMsgForBR}</b></div><br>
									
									<div class="col-lg-6 form-group">
										<label>Choose Branch Receipt CSV File</label> 
										<c:if test="${branchData eq 0 }"><input type="file" id="file" name="file"
											Class="form-control" /></c:if>
											<c:if test="${branchData ne 0 }">
											<input type="file" id="file" name="file" Class="form-control" readonly="readonly" disabled="disabled"/><b style="color: red">Branch Receipt has been already uploaded</b></c:if>
										<a href="${documentFilePath}/branchReceipt.csv" download>Download CSV Format</a>
									</div>
									
									<div class="col-lg-12">
									<c:if test="${branchData eq 0 }">
									<button  id="uploadBranchReceipt" style="width: 99px;" value="Details" class="btn btn-default" type="submit">Upload</button></c:if>
									</div>
								</form:form>
								<!-- /.col-lg-6 (nested) -->
								<!-- /.col-lg-6 (nested) -->
							</div>
							<!-- /.row (nested) -->
						</div>
						<!-- /.panel-body -->
						</div>
						<div class="panel panel-default">
						<div class="panel-heading">Upload Chest Master Data</div>
						<div class="panel-body">
							<div class="row">
								<!--<form role="form">-->
								<form:form id="chestMaster" name="chestMaster" action="uploadChestMaster" method="post"
									enctype="multipart/form-data" modelAttribute="user">
									<div align="center" style="color:white; background:green;"><b>${uploadMsg}</b></div><br>
									<div align="center" style="color:red; "><b>${errorMsgChestMaster}</b></div><br>
									
									<div class="col-lg-6 form-group">
										<label>Choose Chest Master CSV File</label> 
										<input type="file" id="file" name="file" Class="form-control" />
										<a href="${documentFilePath}/ChestMaster.csv" download>Download CSV Format</a>
									</div>
									
									<div class="col-lg-12">
										<button style="width: 99px;" value="Details"
											class="btn btn-default" type="submit" id="uploadChestMaster">Upload</button>
									</div>
								</form:form>
								<!-- /.col-lg-6 (nested) -->
								<!-- /.col-lg-6 (nested) -->
							</div>
							<!-- /.row (nested) -->
						</div>
						<!-- /.panel-body -->
						
					</div>
					
					
					
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
	<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
	
		<script type="text/javascript">
	$(function() {
	  $("form[name='bintransaction']").validate({
	    rules: {
	    	file: "required",
	    },
	    messages: {
	    	file: "Please Choose a File for Upload",
	    },
	    submitHandler: function(form) {
	      $('#uploadBinTransaction').prop('disabled',true);
	    	form.submit();
	    }
	  });
	});
	
	$(function() {
		  $("form[name='branchReceipt']").validate({
		    rules: {
		    	file: "required",
		    },
		    messages: {
		    	file: "Please Choose a File for Upload",
		    },
		    submitHandler: function(form) {
		      $('#uploadBranchReceipt').prop('disabled',true);
		    	form.submit();
		    }
		  });
		});
	
	$(function() {
		  $("form[name='chestMaster']").validate({
		    rules: {
		    	file: "required",
		    },
		    messages: {
		    	file: "Please Choose a File for Upload",
		    },
		    submitHandler: function(form) {
		      $('#uploadChestMaster').prop('disabled',true);
		    	form.submit();
		    }
		  });
		});
	</script>
	
<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>