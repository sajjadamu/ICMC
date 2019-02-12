<!DOCTYPE html>
<%@page import="com.chest.currency.enums.CurrencyType"%>
<%@page import="com.chest.currency.viewBean.CRAWrapper"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.mysema.query.Tuple"%>
<%@page import="java.util.List"%>
<html lang="en">
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
<title>ICICI : ACCEPT CRA PAYMENT</title>

<style type="text/css">
.craCancelButton {
	margin: 0;
	-webkit-appearance: button;
	padding: 3px 2px;
	cursor: pointer;
	font: inherit;
	color: inherit;
}
</style>


<script type="text/javascript">

function doAjaxPostCancel(id){
	addHeaderJson();
	var idFromUI = id;
	if (confirm('Are you sure you want to cancel this CRA Payment request?')) {
		$.ajax({
			type : "POST",
			url : "././cancelCRAPayment",
			data : "idFromUI=" + idFromUI,
			success : function(response) {
				alert('Record Cancelled.');
				window.location='././acceptCRAPayment';
			},
			error : function(e) {
				//alert('Error Occured: ' + e);
				alert('Error: ' + e.responseJSON.message);
				window.location='././acceptCRAPayment';
			}
		});
	} else {
	    // Do nothing!
	}
	
}
	
	function doAjaxPostInsert(id,count) {
		addHeaderJson();
		var craAllocations = [];
		var CRAPaymentWrapper = {
				"craAllocations" : craAllocations
		}
		
		for(i=count;i>0;i--)
			{
				craAllocations.push({
					"denomination" : $('#denomination' + id+i).text(),
					"bundle" : $('#bundle' +id+i).text(),
					"currencyType" : $("#category" + id+i).find("option:selected").text(),
					"vault" : $('#vault' +id+i).val(),
					"forward" : $('#forward'+ id+i).val(),
					"id" : $('#craAllocationId'+ id+i).val(),
				});
				
				var vault=parseInt(($('#vault'+id+i).val()));
				var forward=parseInt(($('#forward'+id+i).val()));
				var pending=parseInt(($('#pendingBundle'+id+i).text()));
				var vaultAndForwardSum=vault+forward;
				if(vaultAndForwardSum != pending)
					{
					alert("Sum of Vault bundle and Forward bundle Should be Equal to Requested Bundle");
					return true;
					}
			}
		
		$.ajax({
			type : "POST",
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			url : "././binRequestForCRAPayment",
			data : JSON.stringify(CRAPaymentWrapper),
			success : function(response) {
				alert('Record submit.');
				window.location='././acceptCRAPayment';
			},
			error : function(e) {
				//alert('Error Occured: ' + e);
				alert('Error: ' + e.responseJSON.message);
				window.location='././acceptCRAPayment';
			}
		});
	}
</script>

<script type="text/javascript">
function doAjaxPostUpdateStatus(str) {  
	  addHeaderJson();  
	// get the form values  
	  var id = $('#id'+str).val();
	  var craId = $('#craId'+str).val();
	  var bin = $('#bin'+str).text();
	  var bundle = $('#bundle'+str).text();
	  var denomination = $('#denomination'+str).text();
	  var category = $('#category'+str).text();
	 // var status=$('#status'+str+' :checked').val();
	  $.ajax({  
	    type: "POST", 
	    url: "././updateCRAStatus",
	    data: "id="+id+"&bin="+bin+"&bundle="+bundle+"&craId="+craId+"&denomination="+denomination,
	    success: function(response){  
	    	 alert(' success ');
	     $("#xy"+str).hide();
	    }, 
	    error: function (xhr, ajaxOptions, thrownError){ 
	    	if(xhr.responseText=="success"){
	    		 $("#xy"+str).hide();	
	    	}else{
	      alert(' Error: ' + thrownError.responseJSON.message);  
	      }
	    }  
	  }); 
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

<!--    DataTable  -->


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

<!--    DataTable  -->

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
</head>
<body oncontextmenu="return false;">
	<div id="wrapper">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />
		<div id="page-wrapper">
			<!-- /.row -->
			<div class="panel-body">

				<!-- <Table align="center"> -->
				<Table>
					<Tr>
						<th style="color: blue"><u>CRA INDENT SUMMARY</u></th>
					</Tr>
				</Table>
				<BR>
				<%
					int count = 0;
					List<CRAWrapper> craWraperList = (List<CRAWrapper>) request.getAttribute("craWrapperList");
					for (CRAWrapper cRAWrapper : craWraperList) {
						count = 0;
				%>
				<table class="table table-striped table-bordered table-hover">

					<thead>
						<tr>
							<th>Vender/MspName</th>
							<th><%=cRAWrapper.getCra().getVendor()%>/<%=cRAWrapper.getCra().getMspName()%></th>
						</tr>
						<tr>
							<th>Category</th>
							<th>Denomination</th>
							<th>Requested Bundle</th>
							<Th>Pending Bundle</Th>
							<!-- <Th>Vendor/MSP Name</Th> -->
							<th>Insert Date</th>
							<th>Vault</th>
							<!-- <th class="hidden">Forward</th> -->
						</tr>
					</thead>
					<tbody>

						<%
							for (Tuple tuple : cRAWrapper.getTupleList()) {
									count++;
						%>

						<tr>
							<td id="category<%=tuple.get(4, Integer.class)%><%=count%>">
								<select name="currencyType">
									<%
										if (tuple.get(3, CurrencyType.class).equals(CurrencyType.ATM)) {
									%>
									<option value="ATM" selected="selected">ATM</option>
									<option value="ISSUABLE">ISSUABLE</option>
									<option value="FRESH">FRESH</option>
									<%
										}
												if (tuple.get(3, CurrencyType.class).equals(CurrencyType.ISSUABLE)) {
									%>
									<option value="ISSUABLE" selected="selected">ISSUABLE</option>
									<option value="ATM">ATM</option>
									<option value="FRESH">FRESH</option>
									<%
										}
												if (tuple.get(3, CurrencyType.class).equals(CurrencyType.FRESH)) {
									%>
									<option value="FRESH" selected="selected">FRESH</option>
									<option value="ATM">ATM</option>
									<option value="ISSUABLE">ISSUABLE</option>
									<%
										}
									%>
							</select>
							</td>
							<td id="denomination<%=tuple.get(4, Integer.class)%><%=count%>"><%=tuple.get(0, Integer.class)%></td>
							<td id="bundle<%=tuple.get(4, Integer.class)%><%=count%>"><%=tuple.get(2, Integer.class)%></td>
							<td id="pendingBundle<%=tuple.get(4, Integer.class)%><%=count%>"><%=tuple.get(6, Integer.class)%></td>
							<%-- <td>
							 <c:forEach var="row" items="${craList}">
							 <tr id="xy${row.id}">
										<tr id="xy${row.id}">
										<!-- <td> -->${row.vendor}/${row.mspName}<!-- </td> -->
							<!-- </tr> -->
							</c:forEach> 
							
							</td> --%>

							<td><fmt:formatDate pattern="yyyy-MM-dd"
									value="<%=tuple.get(1, Calendar.class).getTime()%>" /></td>
							<td><input type="text" readonly="true"
								id="vault<%=tuple.get(4, Integer.class)%><%=count%>"
								value="<%=tuple.get(6, Integer.class)%>"></td>
							<td><input type="text" class="hidden" value="0"
								id="forward<%=tuple.get(4, Integer.class)%><%=count%>"></td>
							<td><input type="hidden"
								id="craAllocationId<%=tuple.get(4, Integer.class)%><%=count%>"
								value="<%=tuple.get(5, Integer.class)%>"></td>
						</tr>

						<%
							}
						%>
						<tr>
							<td><input type="button" class="btn btn-primary"
								value="Submit"
								onclick="doAjaxPostInsert('<%=cRAWrapper.getCra().getId()%>','<%=count%>');this.disabled=true"></td>


							<%-- <td><input type="button" value="Cancel" onclick="doAjaxPostCancel('<%=cRAWrapper.getCra().getId()%>')"></td> --%>


						</tr>
						<%
							}
						%>

					</tbody>
				</table>

				<table class="table table-striped table-bordered table-hover"
					id="tableValue">
					<thead>
						<tr>
							<!-- <th>Serial Number</th>  -->
							<th>Denomination</th>
							<th>Category</th>
							<th>Bundle</th>
							<th>Bin</th>
							<th>Accept</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="row" items="${craAllocation}">
							<tr id="xy${row.id}">
								<%-- <td id="id${row.id}">${row.id}</td>  --%>
								<input id="id${row.id}" type="hidden" value="${row.id}">
								<input id="craId${row.id}" type="hidden" value="${row.craId}">
								<td id="denomination${row.id}">${row.denomination}</td>
								<td id="category${row.id}">${row.currencyType}</td>
								<td id="bundle${row.id }">${row.bundle}
								<td id="bin${row.id}">${row.binNumber}</td>
								<td><input type="button" class="btn btn-primary" value="Ok"
									onclick="doAjaxPostUpdateStatus(${row.id});this.disabled=true;">
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

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
	<script type="text/javascript" src="./js/htmlInjection.js"></script>

</body>

</html>