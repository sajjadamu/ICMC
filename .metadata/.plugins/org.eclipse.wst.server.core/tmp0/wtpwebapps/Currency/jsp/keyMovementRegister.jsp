<!DOCTYPE html>
<html lang="en">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
<title>ICICI : Key Movement Register</title>

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

<!-- DataTable -->
	<script type="text/javascript"  src="./resources/dataTable/jquery.js"></script>
	<script type="text/javascript" src="./resources/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="./resources/js/dataTables.tableTools.min.js"></script>
	<script type="text/javascript" src="./resources/js/sum().js"></script>
	<link rel="stylesheet" type="text/css" href="./resources/css/jquery.dataTables.min.css">
	<link rel="stylesheet" type="text/css" href='./resources/css/dataTables.tableTools.min.css'>
	<link rel="stylesheet" type="text/css" href='./resources/css/dataTables.tableTools.css'>
<!-- DataTable -->
<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.datetimepicker.css" />
<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script></head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-159" data-genuitec-path="/Currency/src/main/webapp/jsp/keyMovementRegister.jsp">
    <div id="wrapper" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-159" data-genuitec-path="/Currency/src/main/webapp/jsp/keyMovementRegister.jsp">
        <!-- Navigation -->
        <jsp:include page="common.jsp" />
        <div id="page-wrapper">
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
	                        Key Movement Register
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        <div class="region-con">
								<form:form id="userPage" name="userPage"
									action="keyMovementRegister" method="post"
									modelAttribute="reportDate" autocomplete="off">
									<div class="region-con-sec">
										<ul class="region-drop">
											<li>
												<table>
													<tr>
														<td><B>Select Date</B></td>
														<td><form:input type="text" path="fromDate"
																id="fromDate" name="fromDate" cssClass="form-control" />
														</td>
														<td><B>Select Date</B></td>
														<td><form:input type="text" path="toDate"
																id="toDate" name="toDate" cssClass="form-control" />
														</td>
														<td><button type="submit" class="btn btn-default"
																value="Details" style="width: 99px;">Search</button></td>
													</tr>
												</table>
											</li>
										</ul>
									</div>
								</form:form>
							</div>
						
                            <div class="dataTable_wrapper">
                            <form id="showAll">
                             <div align="center" style="color:white ; background: green;"><b>${successMsg}</b></div><br>
                                <!-- <table class="table table-striped table-bordered table-hover" id="dataTables-example"> -->
                                <table class="table table-striped table-bordered table-hover" id="tableValue">
                                    <thead>
                                      <tr>
                                        <th>Date</th>
			 							<th>Charge Taken Over by(Name of Officials)</th>
			 							<th>User ID of Custodian taken Over Charge</th>
										<th>User ID of Custodian handing over Charge</th>
										<th>User ID of BOM/BM</th>
										<th>Custodian</th>
										<th>Remarks/Reason for Change</th>
									   </tr>
                                    </thead>
                                    <tbody>
                                      <c:forEach var="row" items="${records}">
										<tr>
											<td><fmt:formatDate pattern="dd-MMM-yy" value="${row.insertTime.time}" /></td>
											<td>${row.userName}</td>
											<td>${row.userId}</td>
											<td>${row.handingOverCharge}</td>
											<td>${row.userId}</td>
											<td>${row.custodian}</td>
											<td>${row.remarks}</td>
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

    <!-- Bootstrap Core JavaScript -->
    <script src="./resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="./resources/bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- DataTables JavaScript -->
    <script src="./resources/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="./resources/dist/js/sb-admin-2.js"></script>

    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
 
<script>
 $(document).ready(function () {
    	var table=$('#tableValue').dataTable({
    		 "pagingType": "full_numbers",
    		 "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]]
    	});
    	 var tableTools=new $.fn.dataTable.TableTools(table,{
    		 'sSwfPath':'./js/copy_csv_xls_pdf.swf',
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
<script src="./resources/js/jquery.datetimepicker.js"></script>
	<script>
		$('#fromDate').datetimepicker({
			format : 'Y-m-d',
		});
		$('#toDate').datetimepicker({
			format : 'Y-m-d',
		});
	</script>
</body>
</html>