<!DOCTYPE html>
<%@page import="com.chest.currency.entity.model.MachineCompany"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<html lang="en">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
	<link rel="shortcut icon" href="./resources/logo/favicon.ico" type="image/x-icon">
 
<title>ICICI : Machine Company List</title>

    <!-- Bootstrap Core CSS -->
    <link href="./resources/bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="./resources/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- DataTables CSS -->
    <link href="./resources/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css" rel="stylesheet">

    <!-- DataTables Responsive CSS -->
    <link href="./resources/bower_components/datatables-responsive/css/responsive.dataTables.scss" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="./resources/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="./resources/bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    
    <link rel="./resources/stylesheet" type="text/css" href="dist/css/style.css">

   
	<link href="./resources/bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- MetisMenu CSS -->
    <link href="./resources/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

<!-- Custom CSS -->
	<link href="./resources/dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Custom Fonts -->
	<link  href="./resources/bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

	<link rel="stylesheet" type="text/css" href="./resources/dist/css/style.css">
	
<!-- DataTable -->

  <script type="text/javascript" charset="utf8" src="./resources/js/jquery.dataTables.min.js"></script>
  <link rel="stylesheet" href="https://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
  <script type="text/javascript" charset="utf8" src="./resources/js/dataTables.jqueryui.js"></script>
  <link rel="stylesheet" type="text/css" href="./resources/css/dataTables.jqueryui.css">
  <link rel="stylesheet" type="text/css" href="./resources/css/jquery.dataTables.css">

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
                        
                        <div class="panel-heading">
                        	<ul>
                        		<li>
                        			<sec:authorize access="hasRole('ADD_MACHINE_COMPANY')">
                        				<a href="././addMachineCompany"><i class="fa fa-table fa-fw"></i> Add Machine Company</a>
                        			</sec:authorize>
                        		</li>
							</ul>List of Machine Company
                        </div>
                        
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                            <form id="showAll">
                            
                            <div align="center" style="color:white; background:green;"><b>${successMsg}</b></div>
                            <div align="center" style="color:white; background:green;"><b>${uploadMsg}</b></div>
                            <div align="center" style="color:white; background:green;"><b>${updateMsg}</b></div>
                            <div align="center" style="color:white; background:green;"><b>${removeMsg}</b></div>
                            
                           
                                <!-- <table class="table table-striped table-bordered table-hover" id="dataTables-example"> -->
                                <table class="table table-striped table-bordered table-hover" id="tableValue">
                                    <thead>
                                       <tr>
											<th>Make Company</th>
											<th>Email</th>
											<th>Phone Number</th>
											<th>Address</th>
											<th>Edit</th>
										</tr>
                                    </thead>
                                    <tbody>
                                      <c:forEach var="row" items="${records}">
										<tr>
											 <td>${row.companyname}</td>
											<td>${row.email}</td>
											<td>${row.phonenumber}</td>
											<td>${row.address}</td>
											<td><sec:authorize access="hasRole('UPDATE_MACHINE_COMPANY')">
													<a href="editMachineCompany?id=${row.id}">Edit</a>
												</sec:authorize>
											</td>
										</tr>
									</c:forEach>
                                   </tbody>
                                </table>
                                </form>
                            </div>
                            <!-- /.table-responsive -->
                            
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
    <script src="./resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="./resources/bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- DataTables JavaScript -->
    <script src="./resources/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
    <script src="./resources/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

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