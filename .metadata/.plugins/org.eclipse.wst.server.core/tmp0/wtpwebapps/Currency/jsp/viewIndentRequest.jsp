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
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
function doAjaxPostUpdateStatus(str) {  
	addHeader();  
	// get the form values  
	  var id = $('#id'+str).text();
	  $.ajax({  
	    type: "POST", 
	    url: "././updateIndentStatusForCancel",
	    data: "id="+id,
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




<script type="text/javascript">

function doAjaxPostInsert(str,source) { 
	addHeader();  
	var indent={
			  "denomination":$('#denomination'+str).text(),
			  "bundle":$('#bundle'+str).text(),
			  "requestBundle":$('#requestBundle'+str).val(),
			  "binCategoryType":$('#category'+str).text(),
			  "cashSource":source,
	  } 
	 var isValid=true;
	 var bundle = parseInt(($('#bundle'+str).text()));
	 var issuedBundle = parseInt(($('#requestBundle'+str).val()));
	 var source = $(source);
	 
	  if(issuedBundle<=bundle)
		  {
	  $.ajax({  
	    type: "POST", 
	    contentType : 'application/json; charset=utf-8',
	    dataType : 'json',
	    url: "././indentRequest",
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
		    	window.location='././viewIndentRequest';
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

<script type="text/javascript">

function doAjaxPostInsertForFresh(str,source) { 
	addHeader();   
	var indent={
			  "denomination":$('#denomination'+str).text(),
			  "bundle":$('#bundle'+str).text(),
			  "requestBundle":$('#requestBundle'+str).val(),
			  "binCategoryType":$('#category'+str).text(),
			  "rbiOrderNo":$('#rbiOrderNo'+str).text(),
			  "cashSource":source,
	  } 
	 var isValid=true;
	 var bundle = parseInt(($('#bundle'+str).text()));
	 var issuedBundle = parseInt(($('#requestBundle'+str).val()));
	 var source = $(source);
	 
	  if(issuedBundle<=bundle)
		  {
	  $.ajax({  
	    type: "POST", 
	    contentType : 'application/json; charset=utf-8',
	    dataType : 'json',
	    url: "././indentRequestForFresh",
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
		    	window.location='././viewIndentRequest';
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
  <script type="text/javascript" charset="utf8" src="./resources/js/jquery.dataTables.min.js"></script>
  <link rel="stylesheet" href="https://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
  <script type="text/javascript" charset="utf8" src="./resources/js/dataTables.jqueryui.js"></script>
  <link rel="stylesheet" type="text/css" href="./resources/css/dataTables.jqueryui.css">
  <link rel="stylesheet" type="text/css" href="./resources/css/jquery.dataTables.css">
<!-- DataTable -->

<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script></head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-234" data-genuitec-path="/Currency/src/main/webapp/jsp/viewIndentRequest.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-234" data-genuitec-path="/Currency/src/main/webapp/jsp/viewIndentRequest.jsp">
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
			<th style="color: blue"><u>BRANCH INDENT SUMMARY</u></th>
		</Tr>
	</Table>
	<BR>
	<table class="table table-striped table-bordered table-hover">

		<thead>
			<tr>
				<th>Denomination</th>
				<th>Category</th>
				<th>Total Bundle</th>
				<th>Pending Indented Bundle</th>
				<th>Old Date</th>
				<th>Ageing</th>
				<th>Indent</th>
				<th>Action</th>
			</tr>

		</thead>
		
		<tbody>
		
			<%
				List<Tuple> listTuple = (List<Tuple>) request.getAttribute("summaryRecords");
				for (Tuple tuple : listTuple) {
			%>
             
			<tr>
				<td id="denomination<%=tuple.get(0, Integer.class)%><%=tuple.get(4, BinCategoryType.class)%><%=CashSource.BRANCH%>"><%=tuple.get(0, Integer.class)%></td>
				<td id="category<%=tuple.get(0, Integer.class)%><%=tuple.get(4, BinCategoryType.class)%><%=CashSource.BRANCH%>"><%=tuple.get(4, BinCategoryType.class)%></td>
				<td id="bundle<%=tuple.get(0,Integer.class)%><%=tuple.get(4, BinCategoryType.class)%><%=CashSource.BRANCH%>"><%=tuple.get(2, BigDecimal.class)%></td>
				<td id="pendingBundleRequest<%=tuple.get(0,Integer.class)%><%=tuple.get(4, BinCategoryType.class)%><%=CashSource.BRANCH%>"><%=tuple.get(3, BigDecimal.class)%></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd" value="<%=tuple.get(1, Calendar.class).getTime()%>" /></td>
				<td></td>
				<td><input type="text" name="bundle" id="requestBundle<%=tuple.get(0,Integer.class)%><%=tuple.get(4, BinCategoryType.class)%><%=CashSource.BRANCH%>"></td>
	            <td><input type="button" value="Request" onclick="doAjaxPostInsert('<%=tuple.get(0, Integer.class)%><%=tuple.get(4, BinCategoryType.class)%><%=CashSource.BRANCH%>','<%=CashSource.BRANCH%>')"></td>
			</tr>
			<%
				}
			%>
		</tbody>
						
						</table></div>
						
						
						<div class="panel-body">
						
							<Table align="center">
		<Tr>
			<th style="color: blue"><u>DSB INDENT SUMMARY</u></th>
		</Tr>
	</Table>
	<BR>
	<table class="table table-striped table-bordered table-hover">

		<thead>
			<tr>
			<th>Category</th>
				<th>Denomination</th>
				<th>Total Bundle</th>
				<th>Pending Indented Bundle</th>
				<th>Old Date</th>
				<th>Ageing</th>
				<th>Indent</th>
				<th>Action</th>
			</tr>

		</thead>
		<tbody>
			<%
				List<Tuple> listTupleForDSB = (List<Tuple>) request.getAttribute("dsbRecords");
				for (Tuple tuple : listTupleForDSB) {
			%>
             
			<tr>
			    <td id="category<%=tuple.get(0, Integer.class)%><%=tuple.get(4, BinCategoryType.class)%><%=CashSource.DSB%>"><%=tuple.get(4, BinCategoryType.class)%></td>
				<td id="denomination<%=tuple.get(0, Integer.class)%><%=tuple.get(4, BinCategoryType.class)%><%=CashSource.DSB%>"><%=tuple.get(0, Integer.class)%></td>
				<td id="bundle<%=tuple.get(0,Integer.class)%><%=tuple.get(4, BinCategoryType.class)%><%=CashSource.DSB%>"><%=tuple.get(2, BigDecimal.class)%></td>
				<td id="pendingBundleRequest<%=tuple.get(0,Integer.class)%><%=tuple.get(4, BinCategoryType.class)%><%=CashSource.DSB%>"><%=tuple.get(3, BigDecimal.class)%></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd" value="<%=tuple.get(1, Calendar.class).getTime()%>" /></td>
				<td></td>
				<td><input type="text" name="bundle" id="requestBundle<%=tuple.get(0,Integer.class)%><%=tuple.get(4, BinCategoryType.class)%><%=CashSource.DSB%>"></td>
				<td><input type="button" value="Request" onclick="doAjaxPostInsert('<%=tuple.get(0, Integer.class)%><%=tuple.get(4, BinCategoryType.class)%><%=CashSource.DSB%>','<%=CashSource.DSB%>')"></td>
			</tr>
			<%
				}
			%>
                        
						</tbody></table></div>
						
						<div class="panel-body">
						
							<Table align="center">
		<Tr>
			<th style="color: blue"><u>FRESH INDENT SUMMARY</u></th>
		</Tr>
	</Table>
	<BR>
	<table class="table table-striped table-bordered table-hover">

		<thead>
			<tr>
				<th>RBI Order No</th>
			    <th>Category</th>
				<th>Denomination</th>
				<th>Total Bundle</th>
				<th>Pending Indented Bundle</th>
				<th>Old Date</th>
				<th>Ageing</th>
				<th>Indent</th>
				<th>Action</th>
			</tr>

		</thead>
		
		<tbody>
		
			<%
				List<Tuple> listTupleForFresh = (List<Tuple>) request.getAttribute("freshFromRBIRecords");
				for (Tuple tuple : listTupleForFresh) {
			%>
             
			<tr>
				<td id="rbiOrderNo<%=tuple.get(2, Integer.class)%><%=tuple.get(0, String.class)%><%=tuple.get(1, BinCategoryType.class)%><%=CashSource.RBI%>"><%=tuple.get(0, String.class)%></td>
			    <td id="category<%=tuple.get(2, Integer.class)%><%=tuple.get(0, String.class)%><%=tuple.get(1, BinCategoryType.class)%><%=CashSource.RBI%>"><%=tuple.get(1, BinCategoryType.class)%></td>
				<td id="denomination<%=tuple.get(2, Integer.class)%><%=tuple.get(0, String.class)%><%=tuple.get(1, BinCategoryType.class)%><%=CashSource.RBI%>"><%=tuple.get(2, Integer.class)%></td>
				<td id="bundle<%=tuple.get(2,Integer.class)%><%=tuple.get(0, String.class)%><%=tuple.get(1, BinCategoryType.class)%><%=CashSource.RBI%>"><%=tuple.get(3, BigDecimal.class)%></td>
				<td id="pendingBundleRequest<%=tuple.get(2,Integer.class)%><%=tuple.get(0, String.class)%><%=tuple.get(1, BinCategoryType.class)%><%=CashSource.RBI%>"><%=tuple.get(4, BigDecimal.class)%></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd" value="<%=tuple.get(5, Calendar.class).getTime()%>" /></td>
				<Td></td>
				<td><input type="text" name="bundle" id="requestBundle<%=tuple.get(2,Integer.class)%><%=tuple.get(0, String.class)%><%=tuple.get(1, BinCategoryType.class)%><%=CashSource.RBI%>"></td>
	            <td><input type="button" value="Request" onclick="doAjaxPostInsertForFresh('<%=tuple.get(2, Integer.class)%><%=tuple.get(0, String.class)%><%=tuple.get(1, BinCategoryType.class)%><%=CashSource.RBI%>','<%=CashSource.RBI%>')"></td>
			</tr>
			<%
				}
			%>
		</tbody>
						
						</table></div>
					
						
 <div class="panel-body">
						
							<Table align="center">
		<Tr>
			<th style="color: blue"><u>DIVERSION INDENT SUMMARY</u></th>
		</Tr>
	</Table>
	<BR>
	<table class="table table-striped table-bordered table-hover">

		<thead>
			<tr>
			<th>Category</th>
				<th>Denomination</th>
				<th>Total Bundle</th>
				<th>Pending Indented Bundle</th>
				<th>Old Date</th>
				<th>Ageing</th>
				<th>Indent</th>
				<th>Action</th>
			</tr>

		</thead>
		<tbody>
			<%
				List<Tuple> listTupleForDiversion = (List<Tuple>) request.getAttribute("diversionRecords");
				for (Tuple tuple : listTupleForDiversion) {
			%>
             
			<tr>
			   <td id="category<%=tuple.get(0, Integer.class)%><%=tuple.get(4, BinCategoryType.class)%><%=CashSource.DIVERSION%>"><%=tuple.get(4, BinCategoryType.class)%></td>
				<td id="denomination<%=tuple.get(0, Integer.class)%><%=tuple.get(4, BinCategoryType.class)%><%=CashSource.DIVERSION%>"><%=tuple.get(0, Integer.class)%></td>
				<td id="bundle<%=tuple.get(0,Integer.class)%><%=tuple.get(4, BinCategoryType.class)%><%=CashSource.DIVERSION%>"><%=tuple.get(2, BigDecimal.class)%></td>
				<td id="pendingBundleRequest<%=tuple.get(0,Integer.class)%><%=tuple.get(4, BinCategoryType.class)%><%=CashSource.DIVERSION%>"><%=tuple.get(3, BigDecimal.class)%></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd" value="<%=tuple.get(1, Calendar.class).getTime()%>" /></td>
				<td></td>
				<td><input type="text" name="bundle" id="requestBundle<%=tuple.get(0,Integer.class)%><%=tuple.get(4, BinCategoryType.class)%><%=CashSource.DIVERSION%>"></td>
				<td><input type="button" value="Request" onclick="doAjaxPostInsert('<%=tuple.get(0, Integer.class)%><%=tuple.get(4, BinCategoryType.class)%><%=CashSource.DIVERSION%>','<%=CashSource.DIVERSION%>')"></td>
			</tr>
			<%
				}
			%>
                          
						</tbody></table></div> 
						


<div class="panel-body">
						
		<Table align="center">
		<Tr>
			<th style="color: blue"><u>OTHER BANK INDENT SUMMARY</u></th>
		</Tr>
	</Table>
	<BR>
	<table class="table table-striped table-bordered table-hover">

		<thead>
			<tr>
			<th>Category</th>
				<th>Denomination</th>
				<th>Total Bundle</th>
				<th>Pending Indented Bundle</th>
				<th>Old Date</th>
				<th>Ageing</th>
				<th>Indent</th>
				<th>Action</th>
			</tr>

		</thead>
		<tbody>
			<%
				List<Tuple> listTupleForOtherBank = (List<Tuple>) request.getAttribute("otherBankRecords");
				for (Tuple tuple : listTupleForOtherBank) {
			%>
             
			<tr>
			<td id="category<%=tuple.get(0, Integer.class)%><%=tuple.get(4, BinCategoryType.class)%><%=CashSource.OTHERBANK%>"><%=tuple.get(4, BinCategoryType.class)%></td>
				<td id="denomination<%=tuple.get(0, Integer.class)%><%=tuple.get(4, BinCategoryType.class)%><%=CashSource.OTHERBANK%>"><%=tuple.get(0, Integer.class)%></td>
				<td id="bundle<%=tuple.get(0,Integer.class)%><%=tuple.get(4, BinCategoryType.class)%><%=CashSource.OTHERBANK%>"><%=tuple.get(2, BigDecimal.class)%></td>
				<td id="pendingBundleRequest<%=tuple.get(0,Integer.class)%><%=tuple.get(4, BinCategoryType.class)%><%=CashSource.OTHERBANK%>"><%=tuple.get(3, BigDecimal.class)%></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd" value="<%=tuple.get(1, Calendar.class).getTime()%>" /></td>
				<td></td>
				<td><input type="text" name="bundle" id="requestBundle<%=tuple.get(0,Integer.class)%><%=tuple.get(4, BinCategoryType.class)%><%=CashSource.OTHERBANK%>"></td>
				<td><input type="button" value="Request" onclick="doAjaxPostInsert('<%=tuple.get(0, Integer.class)%><%=tuple.get(4, BinCategoryType.class)%><%=CashSource.OTHERBANK%>','<%=CashSource.OTHERBANK%>')"></td>
			</tr>
			<%
				}
			%>
                          
						</tbody></table></div> 
						
						
						
						
									<%-- <div class="panel-body">
							<div class="dataTable_wrapper">
							
							<table align="center"><tr><th style="color: blue"><u>MACHINE SOFTWARE UPDATION</u></th></tr></table>
							
								<form id="showAll">
									<!-- <table class="table table-striped table-bordered table-hover" id="dataTables-example"> -->
									<table class="table table-striped table-bordered table-hover"  id="tableValue">
										<thead>
											<tr>
												<th>Machine No</th>
												<th>Asset Code</th>
												<th>Machine SI No</th>
												<th>Model Type</th>
												<th>Standard Productivity</th>
												<th>PM Date</th>
												<th>Ageing</th>
												<th>SAP</th>
												<th>MAP</th>
												<th>OSAP</th>
												<th>ACTION</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="row" items="${machineSoftwareUpdation}">
											<input type="hidden" value="${row.id}"
														id="id${row.id}">
												<tr>
													<td id="machineNo${row.id}">${row.machineNo}</td>
													<td id="assetCode${row.id }">${row.assetCode}</td>
													<td id="machineSINo${row.id }">${row.machineSINo}</td>
													<td id="modelType${row.id }">${row.modelType}</td>
													<td id="standardProductivity${row.id }">${row.standardProductivity}</td>
													<td><input style="width:80px;" type="text" value="${row.pmDate}" name="pmDate" value="${fmtDate}"
														id="pmDate${row.id}" onclick="SaveDate(${row.id})"></td>
														<td></td>
													<td><input type="text" name="sap" value="${row.sap}"
														id="sap${row.id}"></td>
													<Td><input type="text" name="map${row.id}" value="${row.map}"
														id="map${row.id}"></Td>
														<Td><input type="text" name="osap${row.id}"
														id="osap${row.id}" value="${row.osap}"></Td>
													<td><input type="button" value="Save"
														onclick="doAjaxPostInsert(${row.id})"></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</form>
							</div>

						</div> --%>
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
        	
        	"pagingType": "full_numbers",
        	
        });
    });
    </script>
<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>