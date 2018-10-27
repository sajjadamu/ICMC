<!DOCTYPE html>
<%@page import="com.mysema.query.Tuple"%>
<%@page import="java.util.List"%>
<html lang="en">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="./resources/logo/favicon.ico"
	type="image/x-icon">
<title>ICICI : IRV Report</title>

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

<script type="text/javascript" src="./resources/dataTable/jquery.js"></script>
<script type="text/javascript"
	src="./resources/js/jquery.dataTables.min.js"></script>
<script type="text/javascript"
	src="./resources/js/dataTables.tableTools.min.js"></script>

<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.dataTables.min.css">
<link rel="stylesheet" type="text/css"
	href='./resources/css/dataTables.tableTools.min.css'>
<link rel="stylesheet" type="text/css"
	href='./resources/css/dataTables.tableTools.css'>

<!-- DataTable -->

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
						<div class="panel-heading">Daily Machine Output</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<form id="showAll">
									<!-- <table class="table table-striped table-bordered table-hover" id="dataTables-example"> -->
									<table class="table table-striped table-bordered table-hover"
										id="tableValue">
										<thead>
											<tr>
												<td>DENOMINATION</td>
												<td>BUNDLE</td>
												<Td>FRESH</Td>
												<Td>ATM</Td>
												<Td>ISSUABLE</Td>
												<Td>SOILED</Td>
												<Td>BACK TO VAULT</Td>
											</tr>
										</thead>
										<tbody class="table table-striped table-bordered table-hover">
											<tr>
												<td>2000</td>
												<td>
													<%
					List<Tuple> listTuple2000 = (List<Tuple>) request.getAttribute("machineInputOutputList2000");
					for (Tuple tuple : listTuple2000) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>
												<td>
													<%
					List<Tuple> listTuple2000F = (List<Tuple>) request.getAttribute("machineOutputListFresh2000");
					for (Tuple tuple : listTuple2000F) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>
												<td>
													<%
					List<Tuple> listTuple2000A = (List<Tuple>) request.getAttribute("machineOutputListATM2000");
					for (Tuple tuple : listTuple2000A) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>

												<td>
													<%
					List<Tuple> listTuple2000I = (List<Tuple>) request.getAttribute("machineOutputListISSUABLE2000");
					for (Tuple tuple : listTuple2000I) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>


												<td>
													<%
					List<Tuple> listTuple2000S = (List<Tuple>) request.getAttribute("machineOutputListSOILED2000");
					for (Tuple tuple : listTuple2000S) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>


											</tr>
											<tr>
												<td>1000</td>
												<td>
													<%
					List<Tuple> listTuple1000 = (List<Tuple>) request.getAttribute("machineInputOutputList1000");
					for (Tuple tuple : listTuple1000) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>
												<td>
													<%
					List<Tuple> listTuple1000F = (List<Tuple>) request.getAttribute("machineOutputListFresh1000");
					for (Tuple tuple : listTuple1000F) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>

												<td>
													<%
					List<Tuple> listTuple1000A = (List<Tuple>) request.getAttribute("machineOutputListATM1000");
					for (Tuple tuple : listTuple1000A) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>


												<td>
													<%
					List<Tuple> listTuple1000I = (List<Tuple>) request.getAttribute("machineOutputListISSUABLE1000");
					for (Tuple tuple : listTuple1000I) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>


												<td>
													<%
					List<Tuple> listTuple1000S = (List<Tuple>) request.getAttribute("machineOutputListSOILED1000");
					for (Tuple tuple : listTuple1000S) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>


											</tr>
											<tr>
												<td>500</td>
												<td>
													<%
					List<Tuple> listTuple500 = (List<Tuple>) request.getAttribute("machineInputOutputList500");
					for (Tuple tuple : listTuple500) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>
												<td>
													<%
					List<Tuple> listTuple500F = (List<Tuple>) request.getAttribute("machineOutputListFresh500");
					for (Tuple tuple : listTuple500F) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>

												<td>
													<%
					List<Tuple> listTuple500A = (List<Tuple>) request.getAttribute("machineOutputListATM500");
					for (Tuple tuple : listTuple500A) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>


												<td>
													<%
					List<Tuple> listTuple500I = (List<Tuple>) request.getAttribute("machineOutputListISSUABLE500");
					for (Tuple tuple : listTuple500I) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>


												<td>
													<%
					List<Tuple> listTuple500S = (List<Tuple>) request.getAttribute("machineOutputListSOILED500");
					for (Tuple tuple : listTuple500S) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>

											</tr>
											<tr>
												<td>100</td>
												<td>
													<%
					List<Tuple> listTuple100 = (List<Tuple>) request.getAttribute("machineInputOutputList100");
					for (Tuple tuple : listTuple100) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>

												<td>
													<%
					List<Tuple> listTuple100F = (List<Tuple>) request.getAttribute("machineOutputListFresh100");
					for (Tuple tuple : listTuple100F) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>


												<td>
													<%
					List<Tuple> listTuple100A = (List<Tuple>) request.getAttribute("machineOutputListATM100");
					for (Tuple tuple : listTuple100A) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>

												<td>
													<%
					List<Tuple> listTuple100I = (List<Tuple>) request.getAttribute("machineOutputListISSUABLE100");
					for (Tuple tuple : listTuple100I) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>

												<td>
													<%
					List<Tuple> listTuple100S = (List<Tuple>) request.getAttribute("machineOutputListSOILED100");
					for (Tuple tuple : listTuple100S) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>

											</tr>
											<Tr>
												<td>50</td>
												<td>
													<%
					List<Tuple> listTuple50 = (List<Tuple>) request.getAttribute("machineInputOutputList50");
					for (Tuple tuple : listTuple50) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>

												<td>
													<%
					List<Tuple> listTuple50F = (List<Tuple>) request.getAttribute("machineOutputListFresh50");
					for (Tuple tuple : listTuple50F) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>


												<td>
													<%
					List<Tuple> listTuple50A = (List<Tuple>) request.getAttribute("machineOutputListATM50");
					for (Tuple tuple : listTuple50A) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>


												<td>
													<%
					List<Tuple> listTuple50I = (List<Tuple>) request.getAttribute("machineOutputListISSUABLE50");
					for (Tuple tuple : listTuple50I) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>


												<td>
													<%
					List<Tuple> listTuple50S = (List<Tuple>) request.getAttribute("machineOutputListSOILED50");
					for (Tuple tuple : listTuple50S) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>
											</tr>
											<tr>
												<td>20</td>
												<td>
													<%
					List<Tuple> listTuple20 = (List<Tuple>) request.getAttribute("machineInputOutputList20");
					for (Tuple tuple : listTuple20) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>

												<td>
													<%
					List<Tuple> listTuple20F = (List<Tuple>) request.getAttribute("machineOutputListFresh20");
					for (Tuple tuple : listTuple20F) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>

												<td>
													<%
					List<Tuple> listTuple20A = (List<Tuple>) request.getAttribute("machineOutputListATM20");
					for (Tuple tuple : listTuple20A) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>

												<td>
													<%
					List<Tuple> listTuple20I = (List<Tuple>) request.getAttribute("machineOutputListISSUABLE20");
					for (Tuple tuple : listTuple20I) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>

												<td>
													<%
					List<Tuple> listTuple20S = (List<Tuple>) request.getAttribute("machineOutputListSOILED20");
					for (Tuple tuple : listTuple20S) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>
											</tr>
											<Tr>
												<td>10</td>

												<td>
													<%
					List<Tuple> listTuple10 = (List<Tuple>) request.getAttribute("machineInputOutputList10");
					for (Tuple tuple : listTuple10) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>

												<td>
													<%
					List<Tuple> listTuple10F = (List<Tuple>) request.getAttribute("machineOutputListFresh10");
					for (Tuple tuple : listTuple10F) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>

												<td>
													<%
					List<Tuple> listTuple10A = (List<Tuple>) request.getAttribute("machineOutputListATM10");
					for (Tuple tuple : listTuple10A) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>


												<td>
													<%
					List<Tuple> listTuple10I = (List<Tuple>) request.getAttribute("machineOutputListISSUABLE10");
					for (Tuple tuple : listTuple10I) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>


												<td>
													<%
					List<Tuple> listTuple10S = (List<Tuple>) request.getAttribute("machineOutputListSOILED10");
					for (Tuple tuple : listTuple10S) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>

											</tr>
											<tr>
												<td>5</td>
												<td>
													<%
					List<Tuple> listTuple5 = (List<Tuple>) request.getAttribute("machineInputOutputList5");
					for (Tuple tuple : listTuple5) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>

												<td>
													<%
					List<Tuple> listTuple5F = (List<Tuple>) request.getAttribute("machineOutputListFresh5");
					for (Tuple tuple : listTuple5F) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>


												<td>
													<%
					List<Tuple> listTuple5A = (List<Tuple>) request.getAttribute("machineOutputListATM5");
					for (Tuple tuple : listTuple5A) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>

												<td>
													<%
					List<Tuple> listTuple5I = (List<Tuple>) request.getAttribute("machineOutputListISSUABLE5");
					for (Tuple tuple : listTuple5I) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>

												<td>
													<%
					List<Tuple> listTuple5S = (List<Tuple>) request.getAttribute("machineOutputListSOILED5");
					for (Tuple tuple : listTuple5S) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>

											</tr>
											<tr>
												<td>2</td>

												<td>
													<%
					List<Tuple> listTuple2 = (List<Tuple>) request.getAttribute("machineInputOutputList2");
					for (Tuple tuple : listTuple2) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>

												<td>
													<%
					List<Tuple> listTuple2F = (List<Tuple>) request.getAttribute("machineOutputListFresh2");
					for (Tuple tuple : listTuple2F) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>


												<td>
													<%
					List<Tuple> listTuple2A = (List<Tuple>) request.getAttribute("machineOutputListATM2");
					for (Tuple tuple : listTuple2A) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>

												<td>
													<%
					List<Tuple> listTuple2I = (List<Tuple>) request.getAttribute("machineOutputListISSUABLE2");
					for (Tuple tuple : listTuple2I) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>

												<td>
													<%
					List<Tuple> listTuple2S = (List<Tuple>) request.getAttribute("machineOutputListSOILED2");
					for (Tuple tuple : listTuple2S) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>

											</tr>

											<tr>
												<td>1</td>

												<td>
													<%
					List<Tuple> listTuple1 = (List<Tuple>) request.getAttribute("machineInputOutputList1");
					for (Tuple tuple : listTuple1) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>

												<td>
													<%
					List<Tuple> listTuple1F = (List<Tuple>) request.getAttribute("machineOutputListFresh1");
					for (Tuple tuple : listTuple1F) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>

												<td>
													<%
					List<Tuple> listTuple1A = (List<Tuple>) request.getAttribute("machineOutputListATM1");
					for (Tuple tuple : listTuple1A) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>


												<td>
													<%
					List<Tuple> listTuple1I = (List<Tuple>) request.getAttribute("machineOutputListISSUABLE1");
					for (Tuple tuple : listTuple1I) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>


												<td>
													<%
					List<Tuple> listTuple1S = (List<Tuple>) request.getAttribute("machineOutputListSOILED1");
					for (Tuple tuple : listTuple1S) {
				%> <%=tuple.get(1, Integer.class)%> <%
 	}
 %>
												</td>
											</Tr>


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


	<!-- Bootstrap Core JavaScript -->
	<script
		src="./resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script
		src="./resources/bower_components/metisMenu/dist/metisMenu.min.js"></script>

	<script
		src="./resources/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="./resources/dist/js/sb-admin-2.js"></script>

	<!-- Page-Level Demo Scripts - Tables - Use for reference -->
	<script>
	 $(document).ready(function () {
	    	var table=$('#tableValue').dataTable({
	    		 "pagingType": "full_numbers",
	    	});
	    	 var tableTools=new $.fn.dataTable.TableTools(table,{
	    		
	    		 'sSwfPath':'//cdn.datatables.net/tabletools/2.2.4/swf/copy_csv_xls_pdf.swf',
	    		 'aButtons':['copy',{
	    		'sExtends':'print',
	    		'bShowAll':false
	    		 },
	    		 'csv',
	    		 {
	    			 'sExtends':'xls',
	    			 'sFileName':'*.xls',
	    			 'sButtonText':' Excel'
	    		 },
	    		 {
	    			 'sExtends':'pdf',
	    			 'bFooter':false
	    		 }
	    		 ] 
	    	 });
	    	$(tableTools.fnContainer()).insertBefore('.dataTable_wrapper');
	    });
	</script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>