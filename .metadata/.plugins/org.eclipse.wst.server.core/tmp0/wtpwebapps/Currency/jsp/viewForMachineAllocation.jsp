<!DOCTYPE html>
<%@page import="com.chest.currency.enums.CashSource"%>
<html lang="en">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
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
<title>ICICI : Machine Allocation</title>

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

<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>

<script type="text/javascript">

function doAjaxPostForMachineAllocation(str,source,denom) {  
	addHeaderJson();  
	var machineAllocation={
			  "denomination":$('#denomination'+str).text(),
			  "bundle":$('#bundle'+str).text(),
			  "machineNo":$('#selectOption'+str).val(),
			  "issuedBundle":$('#issuedBundle'+str).val(),
			  /* "id":$('#id'+str).val(), */
			  "cashSource":source,
			 "ismanual":$('#ismanual'+str).val(),
	  }
	  
	  if ($('input[name=group'+denom+']:checked').length > 0) {
		  
		}
	  else
		  {
		  alert("Please Select Machine OR Manual");
		  return false; 
		  }
	  
	 var isValid=true;
	 var bundle = parseFloat(($('#pendingBundleRequest'+str).text()));
	 var issuedBundle = parseFloat(($('#issuedBundle'+str).val()));
	 
	  if(issuedBundle<=bundle)
		  {
	  $.ajax({  
	    type: "POST", 
	    contentType : 'application/json; charset=utf-8',
	      dataType : 'json',
	    url: "././saveMachineAllocationData",
	    data: JSON.stringify(machineAllocation),
	    success: function(response){ 
	    	window.location='././viewForMachineAllocation';
	    }, 
	    error: function(e){  
	      alert('Error: ' + e);  
	    }  
	  }); 
		  }
	  else
		  {
		  alert("Please Enter Issue Bundle Less Than or Equal to Bundle")
		  return false;
		  }
	} 
	
	
	function vailidFunctionalityForMachine(Str,value,branch){
		addHeaderJson();
		if(value=='Manual'){
			$('#selectOption'+Str+branch).hide();
			$('#machine'+Str).show();
			$('#ismanual'+Str+branch).val('YES');
		}
		if(value=='Machine'){
			//$('#machine'+Str).val(value);
			 $.ajax({
	    		 type:"POST",
	    		 url:"././getMachineNumber",
	    		 success:function(response){
	    			 var option = '<option value="">Machine Number</option>';
	    			for(var i=0;i<response.length;i++){
	    				 option+='<option value="'+response[i].machineNo+'">'+response[i].machineNo+'</option>';
	    			 } 
	    			/* $('#selectOption'+Str+branch).show(); */
	    			$('#selectOption'+Str+branch).html(option);
	    			$('#ismanual'+Str+branch).val('NO');
	    		 }
		});
		}
	}
	
</script>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-226"
	data-genuitec-path="/Currency/src/main/webapp/jsp/viewForMachineAllocation.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-226"
		data-genuitec-path="/Currency/src/main/webapp/jsp/viewForMachineAllocation.jsp">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />
		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">Machine Allocation</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div>
								<form id="showAll">
									<table align="center">
										<tr>
											<td><font size="4"><u>BRANCH</u></font></td>
										</tr>
									</table>
									<!-- <table class="table table-striped table-bordered table-hover" id="dataTables-example"> -->
									<table class="table table-striped table-bordered table-hover"
										id="tableValue">
										<thead>
											<tr>
												<th></th>
												<th>Denomination</th>
												<th>Total Bundle</th>
												<th>Available Bundle</th>
												<th>Machine Number</th>
												<th>Bundle Issue</th>
												<th>Action</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="row" items="${records}">
												<c:set var="count" value="${count+1}" scope="page" />
												<tr>
													<td><input type="hidden" value="${count}"
														id="id${count}<%=CashSource.BRANCH%>">
													<td id="denomination${count}<%=CashSource.BRANCH%>">${row.denomination}</td>
													<td id="bundle${count}<%=CashSource.BRANCH%>">${row.bundle}</td>
													<td id="pendingBundleRequest${count}<%=CashSource.BRANCH%>">${row.pendingBundleRequest}</td>
													<td><c:set var="MachineDenom"
															value="${row.denomination}" /> <c:if
															test="${MachineDenom > 50}">
															<input type="radio" id="radio"
																name="group${row.denomination}" value="Machine"
																checked="checked"
																onchange="vailidFunctionalityForMachine(${count},'Machine','<%=CashSource.BRANCH%>')"> Machine
															<input type="radio" id="radio"
																name="group${row.denomination}" value="Manual"
																onchange="vailidFunctionalityForMachine(${count},'Manual','<%=CashSource.BRANCH%>')">Manual
														</c:if> <c:if test="${MachineDenom <= 50}">
															<input type="radio" id="radio"
																name="group${row.denomination}" value="Machine"
																onchange="vailidFunctionalityForMachine(${count},'Machine','<%=CashSource.BRANCH%>')"> Machine
															<input type="radio" id="radio"
																name="group${row.denomination}" value="Manual"
																checked="checked"
																onchange="vailidFunctionalityForMachine(${count},'Manual','<%=CashSource.BRANCH%>')">Manual 
														</c:if> <select id="selectOption${count}<%=CashSource.BRANCH%>"
														style="display: none"></select> <c:if
															test="${MachineDenom > 50}">
															<input type="text" name="ismanual"
																id="ismanual${count}<%=CashSource.BRANCH%>"
																style="display: none" value="NO">
														</c:if> <c:if test="${MachineDenom <= 50}">
															<input type="text" name="ismanual"
																id="ismanual${count}<%=CashSource.BRANCH%>"
																style="display: none" value="YES">
														</c:if></td>
													<td><input type="text" name="issuedBundle${count}"
														id="issuedBundle${count}<%=CashSource.BRANCH%>"></td>
													<td><sec:authorize
															access="hasRole('ADD_MACHINE_ALLOCATION')">
															<input type="button" value="Allocate"
																onclick="doAjaxPostForMachineAllocation('${count}<%=CashSource.BRANCH%>','<%=CashSource.BRANCH%>','${row.denomination}')">
														</sec:authorize></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>

									<table align="center">
										<tr>
											<td><font size="4"><u>DSB</u></font></td>
										</tr>
									</table>

									<table class="table table-striped table-bordered table-hover"
										id="tableValue">
										<thead>
											<tr>
												<th></th>
												<th>Denomination</th>
												<th>Total Bundle</th>
												<th>Available Bundle</th>
												<th>Machine Number</th>
												<th>Bundle Issue</th>
												<th>Action</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="row" items="${recordsDSB}">
												<c:set var="count" value="${count+1}" scope="page" />
												<tr>
													<td><input type="hidden" value="${count}"
														id="id${count}<%=CashSource.DSB%>">
													<td id="denomination${count}<%=CashSource.DSB%>">${row.denomination}</td>
													<td id="bundle${count}<%=CashSource.DSB%>">${row.bundle}</td>
													<td id="pendingBundleRequest${count}<%=CashSource.DSB%>">${row.pendingBundleRequest}</td>
													<td><c:set var="MachineDenom"
															value="${row.denomination}" /> <c:if
															test="${MachineDenom > 50}">
															<input type="radio" id="radio"
																name="group${row.denomination}" value="Machine"
																checked="checked"
																onchange="vailidFunctionalityForMachine('${count}','Machine','<%=CashSource.DSB%>')"> Machine
															<input type="radio" id="radio"
																name="group${row.denomination}" value="Manual"
																onchange="vailidFunctionalityForMachine('${count}','Manual','<%=CashSource.DSB%>')">Manual 
														</c:if> <c:set var="MachineDenom" value="${row.denomination}" />
														<c:if test="${MachineDenom <= 50}">
															<input type="radio" id="radio"
																name="group${row.denomination}" value="Machine"
																onchange="vailidFunctionalityForMachine('${count}','Machine','<%=CashSource.DSB%>')"> Machine
															<input type="radio" id="radio"
																name="group${row.denomination}" value="Manual"
																checked="checked"
																onchange="vailidFunctionalityForMachine('${count}','Manual','<%=CashSource.DSB%>')">Manual 
														</c:if> <select id="selectOption${count}<%=CashSource.DSB%>"
														style="display: none"></select> <c:if
															test="${MachineDenom > 50}">
															<input type="text" name="ismanual"
																id="ismanual${count}<%=CashSource.DSB%>"
																style="display: none" value="NO">
														</c:if> <c:if test="${MachineDenom <= 50}">
															<input type="text" name="ismanual"
																id="ismanual${count}<%=CashSource.DSB%>"
																style="display: none" value="YES">
														</c:if></td>
													<td><input type="text" name="issuedBundle${count}"
														id="issuedBundle${count}<%=CashSource.DSB%>"></td>
													<td><sec:authorize
															access="hasRole('ADD_MACHINE_ALLOCATION')">
															<input type="button" value="Allocate"
																onclick="doAjaxPostForMachineAllocation('${count}<%=CashSource.DSB%>','<%=CashSource.DSB%>','${row.denomination}')">
														</sec:authorize></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>

									<table align="center">
										<tr>
											<td><font size="4"><u>OTHER BANK</u></font></td>
										</tr>
									</table>

									<table class="table table-striped table-bordered table-hover"
										id="tableValue">
										<thead>
											<tr>
												<th></th>
												<th>Denomination</th>
												<th>Total Bundle</th>
												<th>Available Bundle</th>
												<th>Machine Number</th>
												<th>Bundle Issue</th>
												<th>Action</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="row" items="${recordsOtherBank}">
												<c:set var="count" value="${count+1}" scope="page" />
												<tr>
													<td><input type="hidden" value="${count}"
														id="id${count}<%=CashSource.OTHERBANK%>">
													<td id="denomination${count}<%=CashSource.OTHERBANK%>">${row.denomination}</td>
													<td id="bundle${count}<%=CashSource.OTHERBANK%>">${row.bundle}</td>
													<td
														id="pendingBundleRequest${count}<%=CashSource.OTHERBANK%>">${row.pendingBundleRequest}</td>
													<td><c:set var="MachineDenom"
															value="${row.denomination}" /> <c:if
															test="${MachineDenom > 50}">
															<input type="radio" id="radio"
																name="group${row.denomination}" value="Machine"
																checked="checked"
																onchange="vailidFunctionalityForMachine('${count}','Machine','<%=CashSource.OTHERBANK%>')"> Machine
															<input type="radio" id="radio"
																name="group${row.denomination}" value="Manual"
																onchange="vailidFunctionalityForMachine('${count}','Manual','<%=CashSource.OTHERBANK%>')">Manual 
														</c:if> <c:set var="MachineDenom" value="${row.denomination}" />
														<c:if test="${MachineDenom <= 50}">
															<input type="radio" id="radio"
																name="group${row.denomination}" value="Machine"
																onchange="vailidFunctionalityForMachine('${count}','Machine','<%=CashSource.OTHERBANK%>')"> Machine
															<input type="radio" id="radio"
																name="group${row.denomination}" value="Manual"
																checked="checked"
																onchange="vailidFunctionalityForMachine('${count}','Manual','<%=CashSource.OTHERBANK%>')">Manual 
														</c:if> <select
														id="selectOption${count}<%=CashSource.OTHERBANK%>"
														style="display: none"></select> <c:if
															test="${MachineDenom > 50}">
															<input type="text" name="ismanual"
																id="ismanual${count}<%=CashSource.OTHERBANK%>"
																style="display: none" value="NO">
														</c:if> <c:if test="${MachineDenom <= 50}">
															<input type="text" name="ismanual"
																id="ismanual${count}<%=CashSource.OTHERBANK%>"
																style="display: none" value="YES">
														</c:if></td>
													<td><input type="text" name="issuedBundle${count}"
														id="issuedBundle${count}<%=CashSource.OTHERBANK%>"></td>
													<td><sec:authorize
															access="hasRole('ADD_MACHINE_ALLOCATION')">
															<input type="button" value="Allocate"
																onclick="doAjaxPostForMachineAllocation('${count}<%=CashSource.OTHERBANK%>','<%=CashSource.OTHERBANK%>','${row.denomination}')">
														</sec:authorize></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>

									<table align="center">
										<tr>
											<td><font size="4"><u>DIVERSION</u></font></td>
										</tr>
									</table>

									<table class="table table-striped table-bordered table-hover"
										id="tableValue">
										<thead>
											<tr>
												<th></th>
												<th>Denomination</th>
												<th>Total Bundle</th>
												<th>Available Bundle</th>
												<th>Machine Number</th>
												<th>Bundle Issue</th>
												<th>Action</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="row" items="${recordsDiversion}">
												<c:set var="count" value="${count+1}" scope="page" />
												<tr>
													<td><input type="hidden" value="${count}"
														id="id${count}<%=CashSource.DIVERSION%>">
													<td id="denomination${count}<%=CashSource.DIVERSION%>">${row.denomination}</td>
													<td id="bundle${count}<%=CashSource.DIVERSION%>">${row.bundle}</td>
													<td
														id="pendingBundleRequest${count}<%=CashSource.DIVERSION%>">${row.pendingBundleRequest}</td>
													<td><c:set var="MachineDenom"
															value="${row.denomination}" /> <c:if
															test="${MachineDenom > 50}">
															<input type="radio" id="radio"
																name="group${row.denomination}" value="Machine"
																checked="checked"
																onchange="vailidFunctionalityForMachine(${count},'Machine','<%=CashSource.DIVERSION%>')"> Machine
															<input type="radio" id="radio"
																name="group${row.denomination}" value="Manual"
																onchange="vailidFunctionalityForMachine(${count},'Manual','<%=CashSource.DIVERSION%>')">Manual
														</c:if> <c:if test="${MachineDenom <= 50}">
															<input type="radio" id="radio"
																name="group${row.denomination}" value="Machine"
																onchange="vailidFunctionalityForMachine(${count},'Machine','<%=CashSource.DIVERSION%>')"> Machine
															<input type="radio" id="radio"
																name="group${row.denomination}" value="Manual"
																checked="checked"
																onchange="vailidFunctionalityForMachine(${count},'Manual','<%=CashSource.DIVERSION%>')">Manual 
														</c:if> <select
														id="selectOption${count}<%=CashSource.DIVERSION%>"
														style="display: none"></select> <c:if
															test="${MachineDenom > 50}">
															<input type="text" name="ismanual"
																id="ismanual${count}<%=CashSource.DIVERSION%>"
																style="display: none" value="NO">
														</c:if> <c:if test="${MachineDenom <= 50}">
															<input type="text" name="ismanual"
																id="ismanual${count}<%=CashSource.DIVERSION%>"
																style="display: none" value="YES">
														</c:if></td>
													<td><input type="text" name="issuedBundle${count}"
														id="issuedBundle${count}<%=CashSource.DIVERSION%>"></td>
													<td><sec:authorize
															access="hasRole('ADD_MACHINE_ALLOCATION')">
															<input type="button" value="Allocate"
																onclick="doAjaxPostForMachineAllocation('${count}<%=CashSource.DIVERSION%>','<%=CashSource.DIVERSION%>','${row.denomination}')">
														</sec:authorize></td>
												</tr>
											</c:forEach>
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
        	"pagingType": "full_numbers",
        });
    });
    </script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>
