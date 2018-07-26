<!DOCTYPE html>
<%@page import="com.chest.currency.enums.BinCategoryType"%>
<%@page import="com.chest.currency.enums.CashSource"%>
<%@page import="java.util.Date"%>
<html lang="en">
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.List"%>
<%@page import="com.mysema.query.Tuple"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="./resources/logo/favicon.ico"
	type="image/x-icon">
<title>ICICI : Indent Request</title>

<script type="text/javascript">

function SavePrint(str,source) {
	addHeader();
	var fresh={
			"denomination" : $('#denomination' + str).text(),
			"bundleFromDB" : $('#bundleFromDB' + str).text(),
			"bundle" : $('#requestBundle' + str).val(),
			"pendingBundleRequest":$('#pendingBundle'+str).val(),
			"binCategoryType":$('input[name=binCategoryType]:checked').val(),
			"cashSource" : source,
	       }
	var source = $(source);
	var bundle = parseInt(($('#requestBundle'+str).val()));
	 var pendingBundle = parseInt(($('#pendingBundle'+str).text()));
	  if(bundle<=pendingBundle)
		  {
	     $.ajax({
				type : "POST",
				 contentType : 'application/json; charset=utf-8',
			      dataType : 'json',
				url : "././RBIQRPathForProcess",
				 data: JSON.stringify(fresh),
				 success : function(response) {
					alert("Success..");
					window.location='././processFreshFromRBI';
					/* //For Print Text
     				  
					var str2 = '';
			    	$.each(response,function(index,element){
			    		for(var i=0;i<element.bundle;i++)
		    			{
			    		str2+= '<div><div style="width:25%;float:left;" ><img src="./files'+element.filepath+'" alt="QRPrint"></div>'
			    		str2 += "<div>Bin : "
							+"<font size=50>"+ element.bin+"</font>"
							+ "</br>"
							+ "Deno. :"
							+ element.denomination
							+ "</br>"
							+ "</div></div><BR>";
		    			}
			    	});
			    	//alert(str2);
			    	$('#printSection').html(str2);
					$('#printSection').show();
					$.print("#printSection");
					$('#printSection').hide();
					$('#print'+str).prop('disabled', true);
					$("#member").prop('disabled', true); */
				},
				error : function(e) {
					//alert('Print Error: '  +e);
					alert('Error: ' + e.responseJSON.message);
					window.location='././processFreshFromRBI';
				}
			});
		  }
	  else
		  {
		  alert("Please Enter Issue Bundle Less Than or Equal to Pending Bundle");
		  window.location='././processFreshFromRBI';
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
	href="dist/css/style.css">

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

						<%-- <div class="panel-heading">
							<ul>
								<li>
									<sec:authorize access="hasRole('ADD_INDENT')">
										<a href="././indentRequest"><i class="fa fa-table fa-fw"></i> Add indent Request</a>
									</sec:authorize>
								</li>
							</ul>Indent Request List
						</div> --%>

						<!-- /.panel-heading -->

						<div class="panel-body">
							<Table align="center">
								<Tr>
									<th style="color: blue"><u>INDENT SUMMARY FOR FRESH</u></th>
								</Tr>
							</Table>
							<BR>
							<table class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th>Denomination</th>
										<th>Bundle</th>
										<th>Pending Bundle</th>
										<th>Require Bundle</th>
										<th>Action</th>
									</tr>

								</thead>
								<tbody>
									<%
										List<Tuple> listTuple = (List<Tuple>) request.getAttribute("freshList");
										for (Tuple tuple : listTuple) {
									%>

									<tr>
										<td id="denomination<%=tuple.get(0, Integer.class)%>"><%=tuple.get(0, Integer.class)%></td>
										<td id="bundleFromDB<%=tuple.get(0, Integer.class)%>"><%=tuple.get(1, BigDecimal.class)%></td>
										<td id="pendingBundle<%=tuple.get(0, Integer.class)%>"><%=tuple.get(2, BigDecimal.class)%></td>
										<td><input type="text"
											id="requestBundle<%=tuple.get(0, Integer.class)%>"></td>
											<td><input id="binCategoryType<%=tuple.get(0, Integer.class)%>" type="radio" checked="true" name="binCategoryType" value="BIN">BIN</td>
											<td><input id="binCategoryType<%=tuple.get(0, Integer.class)%>" type="radio" name="binCategoryType" value="BOX">BOX</td>
										<td><input type="button" value="Generate QR"
											onclick="SavePrint('<%=tuple.get(0, Integer.class)%>','<%=CashSource.RBI%>');this.disabled=true"></td>
									</tr>
									<%
										}
									%>
								</tbody>
							</table>
						</div>
<div id="printSection" style="display: none;"></div>




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
	
	<script src="./resources/js/jQuery.print.js"></script>

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