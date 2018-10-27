<!DOCTYPE html>
<%@page import="com.chest.currency.enums.BinCategoryType"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.mysema.query.Tuple"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.math.BigDecimal"%>
<html lang="en">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="./resources/logo/favicon.ico"
	type="image/x-icon">
<title>ICICI : mutilated Full Value Payment</title>


<script type="text/javascript">

function doAjaxPostRequestBundleForMutilatedPayment(str) {
	addHeader();
	var indent={
			  "denomination":$('#denomination'+str).text(),
			  "bundle":$('#bundle'+str).text(),
			  "requestBundle":$('#requestBundle'+str).val(),
			  "binCategoryType":$('#category'+str).text(),
	  } 
	 var bundle = parseInt(($('#bundle'+str).text()));
	 var issuedBundle = parseInt(($('#requestBundle'+str).val()));
	  if(issuedBundle<=bundle)
		  {
	  $.ajax({  
	    type: "POST", 
	    contentType : 'application/json; charset=utf-8',
	    dataType : 'json',
	    url: "././indentRequestForMutilate",
	    data: JSON.stringify(indent),
	    success: function(response){
	    	if(response.status === 'CANCELLED'){
	    		var errorMessage = "Error : Total Available bundle are:"+response.availableBundle;
	    		if(response.message !== null && response.message.length > 0){
	    			errorMessage = errorMessage + ", in unit of:"+response.message;
	    		}
	    		alert(errorMessage);
	    	}else{
		    	alert('Successfull : Record submit.');
		    	window.location='././mutilatedPayment';
	    	}
	    }, 
	    error: function(e){
	      alert('Error:'+e);  
	    }  
	  }); 
		  }
	  else
		  {
		  alert("Please Enter Issue Bundle Less Than or Equal to Bundle")
		  return false;
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
	href="./resources/dist/css/style.css">

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
							<div class="dataTable_wrapper">
								<form id="showAll">
									<!-- <table class="table table-striped table-bordered table-hover" id="dataTables-example"> -->
									<table class="table table-striped table-bordered table-hover"
										id="tableValue">
										<thead>
											<tr>
												<th>Denomination</th>
												<!-- <th>Bin</th> -->
												<th>Category</th>
												<th>Bundle</th>
												<th>Pending Bundle</th>
												<th>Request Bundle</th>
												<th>Action</th>
											</tr>
										</thead>
										<tbody>

											<%
				List<Tuple> listTuple = (List<Tuple>) request.getAttribute("mutilatedList");
				for (Tuple tuple : listTuple) {
			%>
											<tr>
												<td id="denomination<%=tuple.get(2, String.class)%>"><%=tuple.get(0, Integer.class)%></td>
												<%-- <td id="bin<%=tuple.get(2, String.class)%>"><%=tuple.get(2, String.class)%></td> --%>
												<td id="category<%=tuple.get(2, String.class)%>"><%=tuple.get(3,BinCategoryType.class)%></td>
												<%-- <td><fmt:formatDate pattern="yyyy-MM-dd" value="<%=tuple.get(4, Calendar.class).getTime()%>" /></td> --%>
												<td id="bundle<%=tuple.get(2, String.class)%>"><%=tuple.get(1, BigDecimal.class)%></td>
												<td id="pendingBundle<%=tuple.get(2, String.class)%>"><%=tuple.get(4, BigDecimal.class)%></td>
												<td><input type="text"
													id="requestBundle<%=tuple.get(2, String.class)%>"></td>
												<td><input type="button" value="Request"
													onclick="doAjaxPostRequestBundleForMutilatedPayment('<%=tuple.get(2,String.class)%>')"></td>

											</tr>
											<%
				}
			%>
										</tbody>
									</table>
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
			$('#tableValue').dataTable({

				"pagingType" : "full_numbers",

			});
		});
	</script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>

</body>

</html>