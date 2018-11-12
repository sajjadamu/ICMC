<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html lang="en">

<head>
<link rel="shortcut icon" href="./resources/logo/favicon.ico"
	type="image/x-icon">
<script type="text/javascript" src="./js/jquery-1.12.0.min.js"></script>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<style>
form#ORV {
	width: 60%;
}

.frmsub input[type="button"] {
	padding: 1px 15px;
	margin-bottom: 20px;
}

.frmsub, .frmsubdel {
	margin-left: 16px;
}
</style>

<title>VMS : ORV Branch</title>

<script src="./resources/Currency/js/jquery.js"></script>
<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
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



<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css" />

<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.datetimepicker.css" />




<style type="text/css">
.card {
	width: 100%;
	float: left;
	margin-top: 10px;
}

.card-body {
	-ms-flex: 1 1 auto;
	flex: 1 1 auto;
	padding: 1.25rem;
}

.card-body table {
	margin-bottom: 0;
}
</style>
</head>

<body>
	<link rel="stylesheet"
		href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

	<!-- Optional theme -->
	<link rel="stylesheet"
		href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
	<link rel="stylesheet"
		href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/css/bootstrap-datetimepicker.min.css" />

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.6/moment.min.js"></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>



<body oncontextmenu="return false;">

	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Bundle Status</h4>
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



<body>

	<div id="wrapper">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
				
					<!-- End Sidebar scroll-->
					<!-- Bottom points-->
					<div class="sidebar-footer">
						<!-- item-->
						<a href="" class="link" data-toggle="tooltip" title="Settings"><i
							class="ti-settings"></i></a>
						<!-- item-->
						<a href="" class="link" data-toggle="tooltip" title="Email"><i
							class="mdi mdi-gmail"></i></a>
						<!-- item-->
						<a href="" class="link" data-toggle="tooltip" title="Logout"><i
							class="mdi mdi-power"></i></a>
					</div>
					<!-- End Bottom points-->


					<div class="page-wrapper">
						<!-- ============================================================== -->
						<!-- Container fluid  -->
						<!-- ============================================================== -->
						<div class="container-fluid">
							<!-- ============================================================== -->
							<!-- Bread crumb and right sidebar toggle -->
							<!-- ============================================================== -->
							<div class="row page-titles">
								<div class="col-md-5 col-8 align-self-center">
									<h3 class="text-themecolor m-b-0 m-t-0">Indent Request
										History</h3>
									<ol class="breadcrumb">
										<li class="breadcrumb-item"><a href="javascript:void(0)">Branch</a></li>
										<li class="breadcrumb-item active">Amount Details</li>
									</ol>
								</div>

						
									</div>
				
									
									<div>
            
  <table class="table table-bordered">
    <thead>
      <tr>
        <th>DENOMINATION</th><th>ISUABLE</th><th>ATM</th><th>ISUABLE</th>
      </tr>
    </thead>
    <tbody >
      <tr>
        <td>2000</td>
        <td contenteditable>${requestPayment.totalValueOfNotesRs2000I}</td>
        <td contenteditable>${requestPayment.totalValueOfNotesRs2000A}</td>
        <td contenteditable>${requestPayment.totalValueOfNotesRs2000F}</td>
      </tr>
       <tr>
        <td>500</td>
        <td contenteditable>${requestPayment.totalValueOfNotesRs500I}</td>
        <td contenteditable>${requestPayment.totalValueOfNotesRs500A}</td>
        <td contenteditable>${requestPayment.totalValueOfNotesRs500F}</td>
      </tr>
       <tr>
        <td>200</td>
        <td contenteditable>${requestPayment.totalValueOfNotesRs200I}</td>
        <td contenteditable>${requestPayment.totalValueOfNotesRs200A}</td>
        <td contenteditable>${requestPayment.totalValueOfNotesRs200F}</td>
      </tr>
       <tr>
        <td>100</td>
        <td contenteditable>${requestPayment.totalValueOfNotesRs100I}</td>
        <td contenteditable>${requestPayment.totalValueOfNotesRs100A}</td>
        <td contenteditable>${requestPayment.totalValueOfNotesRs100F}</td>
      </tr>
       <tr>
        <td>50</td>
        <td contenteditable>${requestPayment.totalValueOfNotesRs50I}</td>
        <td>0</td>
        <td contenteditable>${requestPayment.totalValueOfNotesRs50F}</td>
      </tr>
       <tr>
        <td>20</td>
        <td contenteditable>${requestPayment.totalValueOfNotesRs20I}</td>
       <td>0</td>
        <td contenteditable>${requestPayment.totalValueOfNotesRs20F}</td>
      </tr>
       <tr>
        <td>10</td>
        <td contenteditable>${requestPayment.totalValueOfNotesRs10I}</td>
        <td>0</td>
        <td contenteditable>${requestPayment.totalValueOfNotesRs10F}</td>
      </tr>
       <tr>
        <td>5</td>
        <td contenteditable>${requestPayment.totalValueOfNotesRs5I}</td>
        <td>0</td>
        <td contenteditable>${requestPayment.totalValueOfNotesRs5F}</td>
      </tr>
       <tr>
        <td>2</td>
        <td contenteditable>${requestPayment.totalValueOfNotesRs2I}</td>
        <td>0</td>
        <td contenteditable>${requestPayment.totalValueOfNotesRs2F}</td>
      </tr>
       <tr>
        <td>1</td>
        <td contenteditable>${requestPayment.totalValueOfNotesRs1I}</td>
        <td>0</td>
        <td contenteditable>${requestPayment.totalValueOfNotesRs1F}</td>
      </tr>
      
    </tbody>
  </table>
  
</div>
								</div>
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
	</div>
	</div>

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

	<script src="./resources/js/jquery.datetimepicker.js"></script>
	
	<script type="text/javascript" src="./js/htmlInjection.js"></script>

</body>

</html>