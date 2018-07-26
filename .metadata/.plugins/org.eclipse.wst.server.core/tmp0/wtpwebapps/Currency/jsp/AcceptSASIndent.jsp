<!DOCTYPE html>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.chest.currency.enums.CashType"%>
<%@page import="com.chest.currency.enums.CurrencyType"%>
<%@page import="com.mysema.query.Tuple"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<html lang="en">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="./resources/logo/favicon.ico" type="image/x-icon">
<title>ICICI : Indent</title>

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

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

<!--    DataTable  -->
<script type="text/javascript" charset="utf8" src="./resources/js/jquery.dataTables.min.js"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
<script type="text/javascript" charset="utf8" src="./resources/js/dataTables.jqueryui.js"></script>
<link rel="stylesheet" type="text/css" href="./resources/css/dataTables.jqueryui.css">
<link rel="stylesheet" type="text/css" href="./resources/css/jquery.dataTables.css">
<!--    DataTable  -->


<script type="text/javascript">
    function doAjaxPostUpdateStatus(str) {  
    	addHeader();
		// get the form values  
		//alert(str);
  	  var sasAccept={
  	  //"id":$('#id'+str).val(),
  	  "denomination":$('#denomination'+str).text(),
  	  "bundle":$('#bundle'+str).text(),
  	  "binNumber": $('#binNumber'+str).text(),
  	  "binType": $('#binType'+str).text(),
  	  "cashType": $('#cashType'+str).text(),
  	  }
  	 // var status=$('#status'+str+' :checked').val();
  	  $.ajax({ 
  		type : "POST",
		contentType : 'application/json; charset=utf-8',
	      dataType : 'json',
		url : "././acceptSASIndent",
		data: JSON.stringify(sasAccept),
  	    success: function(response){  
  	     // $('#info').html(response);
  	    // $("#xy"+str).hide();
  	   window.location='././AcceptSASIndent';
  	    }, 
  	    error: function(e){  
  	     /*  alert(' Error: ' + e);   */
  	    	 window.location='././AcceptSASIndent';
  	    }  
  	  }); 
  	}  	
  </script>
  
  <script type="text/javascript">
    function doAjaxCancelBranchPayment(str) {  
    	addHeader();
		// get the form values 
		/* alert(str);
		alert($('#binType'+str).text()) */
  	  var sasCancel={
  	  "id":$('#id'+str).val(),
  	  "denomination":$('#denomination'+str).text(),
  	  "bundle":$('#bundle'+str).text(),
  	  "binNumber": $('#binNumber'+str).text(),
  	  "binType": $('#binType'+str).text(),
  	  "cashType": $('#cashType'+str).text(),
  	  "parentId":str,
  	  }
  	 // var status=$('#status'+str+' :checked').val();
  	  $.ajax({ 
  		type : "POST",
		contentType : 'application/json; charset=utf-8',
	      dataType : 'json',
		url : "././cancelSASIndent",
		data: JSON.stringify(sasCancel),
  	    success: function(response){  
  	     // $('#info').html(response);
  	     $("#xy"+str).hide();
  	    }, 
  	    error: function(e){  
  	      alert(' Error: ' + e);  
  	    }  
  	  }); 
  	}  	
  </script>

<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script></head>
<body oncontextmenu="return false;" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-4" data-genuitec-path="/Currency/src/main/webapp/jsp/AcceptSASIndent.jsp">

	<div id="wrapper" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-4" data-genuitec-path="/Currency/src/main/webapp/jsp/AcceptSASIndent.jsp">

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
								<li></li>
							</ul>
							Accept Branch Payment
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<form id="showAll">
									<!-- <table class="table table-striped table-bordered table-hover" id="dataTables-example"> -->
									<table class="table table-striped table-bordered table-hover"
										id="tableValue">
										<thead>
											<tr>
												<!-- <th>Serial Number</th> -->
												<th>Bin</th>
												<th>Denomination</th>
												<th>Cash Type</th>
												<th>Type</th>
												<th>Bundle</th>
												<th>Accept</th>
												<!-- <th>Cancel</th> -->
											</tr>
										</thead>
										<tbody>
											<%-- <c:forEach var="row" items="${records}">
												<tr id="xy${row.id}">
													<td id="id${row.id}">${row.id}</td>
													<td class="hidden"><input type="hidden" id="id${row.id}" value="${row.id}"></td>
													<td id="denomination${row.id}">${row.denomination}</td>
													<td id="binType${row.id}">${row.binType}</td>
													<td id="cashType${row.id}">${row.cashType}</td>
													<td id="bundle${row.id}">${row.bundle}</td>
													<td id="binNumber${row.id}">${row.binNumber}</td>
													<td><input type="button" value="Accept"
														onclick="doAjaxPostUpdateStatus(${row.id})"></td>
													<td><input type="button" value="Cancel" onClick="doAjaxCancelBranchPayment(${row.id})"></td>
												</tr>
											</c:forEach> --%>
											
											<%
											
											List<Tuple> tupleForSASAllocation = (List<Tuple>) request.getAttribute("records");
											for(Tuple tuple:tupleForSASAllocation)
											{
											%>
											
											<tr>
											<td id="binNumber<%=tuple.get(0,String.class)%>"><%=tuple.get(0, String.class)%></td>
											<td id="denomination<%=tuple.get(0,String.class)%>"><%=tuple.get(1, Integer.class) %></td>
											<td id="cashType<%=tuple.get(0,String.class)%>"><%=tuple.get(2, CashType.class)%></td>
											<td id="binType<%=tuple.get(0,String.class)%>"><%=tuple.get(3, CurrencyType.class) %></td>
											<td id="bundle<%=tuple.get(0,String.class)%>"><%=tuple.get(4, BigDecimal.class) %></td>
											<td><input type="button" value="Accept"
														onclick="doAjaxPostUpdateStatus('<%=tuple.get(0, String.class)%>')"></td>
											</tr>
											
											<%} %>
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
    $(document).ready(function () {
        $('#tableValue').dataTable({
        	"lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
        	"pagingType": "full_numbers",
        	
        });
    });
    </script>
<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>