<!DOCTYPE html>
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
 
<title>ICICI : View RBI Master</title>

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

<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script></head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-252" data-genuitec-path="/Currency/src/main/webapp/jsp/viewRbiMaster.jsp">
    <div id="wrapper" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-252" data-genuitec-path="/Currency/src/main/webapp/jsp/viewRbiMaster.jsp">
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
                        			<sec:authorize access="hasRole('ADD_RBI_MASTER')">
                        				<a href="././addRbiMaster"><i class="fa fa-table fa-fw"></i> Add/Upload RBI Master</a>
                        			</sec:authorize>
                        		</li>
							</ul>RBI Master List
                        </div>
                        
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                            <form id="showAll">
                            
                            <div align="center" style="color:white; background:green;"><b>${successMsg}</b></div>
                            <div align="center" style="color:white; background:green;"><b>${uploadMsg}</b></div>
                            <div align="center" style="color:white; background:green;"><b>${updateMsg}</b></div><br>
                            
                                <!-- <table class="table table-striped table-bordered table-hover" id="dataTables-example"> -->
                                <table class="table table-striped table-bordered table-hover" id="tableValue">
                                    <thead>
                                       <tr>
											<th>RBI Name</th>
											<th>Zone</th>
											<th>Region</th>
											<th>State</th>
											<th>City</th>
											<th>Status</th>
											<th>Edit</th>
										</tr>
                                    </thead>
                                    <tbody>
                                      <c:forEach var="row" items="${records}">
										<tr>
											 <td>${row.rbiname}</td>
											<td>${row.zone}</td>
											<td>${row.region}</td>
											<td>${row.state}</td>
											<td>${row.city}</td>
											<td>${row.status}</td>
											<td><sec:authorize access="hasRole('UPDATE_RBI_MASTER')">
													<a href="editRbiMaster?id=${row.id}">Edit</a>
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