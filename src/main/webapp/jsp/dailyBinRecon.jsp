<!DOCTYPE html>
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
<title>ICICI : Daily Bin Recon.</title>

<style type="text/css">
.panel.panel-default .btn-success {
	margin-top: 20px;
	position: absolute;
	right: 20px;
	font-size: 14px;
	width: 82px;
	background: #d85600;
	padding: 7px;
	top: -15px;
}
</style>
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
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
</head>
<script type="text/javascript">
/* $(function() {
	if($('.pending').val()==null || $('.pending').val()==""){
		alert("not pending");
	}else{
		alert("pending");
		 $("#eod1").prop("disabled",true);
	}
  
  
	
});  */
 

function doAjaxPostForBinRecon(str) {  
	addHeader();  
	var binTransaction={
			  "id":$('#id'+str).text()
	  }
	  $.ajax({  
	    type: "POST", 
	    contentType : 'application/json; charset=utf-8',
	    dataType : 'json',
	    url: "././getDailyBinRecordData",
	    data: JSON.stringify(binTransaction),
	    success: function(response){
	    	//alert("parseFloat(response) "+parseFloat(response) +"response "+response +"parseFloat($('#receiveBundle'+str).val() "+parseFloat($('#receiveBundle'+str).val()));
	    	if(parseFloat(response)==parseFloat($('#receiveBundle'+str).val()))
	    		 {
	    		var message="OK";
	            var OK=0;
	            $('#message'+str).val(message).css({'backgroundColor':'#4E9258','color':'white'}) 
	    		 $("#tableValue .trValue").each(function(index){
	    			//alert($(this).find("input[name=message]").val());
	    			if($(this).find("input[name=message]").val()==='NOT OK' || $(this).find("input[name=message]").val()===''){
	    				OK = 1;
	    				return false;
	    			}
		    		});
	    		 if(OK == 1){
	    			 $("#eod").prop("disabled",true); 
	    		 }else{
	    			 if($('.pending').text()!=null || $('.pending').text()!=""){
	    				 $("#eod").prop("disabled",false);
	    				}else{
	    					 $("#eod").prop("disabled",true);
	    				}
	    		 } 
	    		 }
	    	
	    	 else
	    		 {
		    	 $("#eod").prop("disabled",true);
	    		 $('#message'+str).val('NOT OK').css({'backgroundColor':'#FF0000','color':'white'})
	    		 }
	    }, 
	    error: function(e){  
	      alert('Error: ' + e);  
	    }  
	  }); 
	  
	} 
	
</script>
<body oncontextmenu="return false;">
	<div id="wrapper">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">

					<div class="panel panel-default">
						<div class="panel-heading">

							<ul>
								<li><form:form id="user" name="user"
										action="saveDataInBinTransactionBOD" method="post">
										<button type="submit" id="eod"
											class="btn btn-lg btn-success btn-block" value="Details"
											disabled="disabled">EOD</button>

										<!-- <button type="submit" id="eod1" class="btn btn-lg btn-success btn-block"
										value="Details"  >EOD</button> -->
									</form:form></li>
							</ul>
							Daily Bin Recon

						</div>
						<div>
							<input type="button" class="btn btn-default qr-button"
								onclick="printDiv('printableArea')" value="Print" />
							<button id="btnExport" class="btn btn-default qr-button">Export
								to xls</button>
							<div hidden>
								total Bin= <b id="totalBin">${totalBin}</b>
							</div>
							<!-- <div>initialized Bin= <b id="initializeBin">0</b></div> -->
							<div hidden>
								checked Bin= <b id="checkedBin">0</b>
							</div>
							<div hidden>
								flag Bin= <b id="flag">0</b>
							</div>
						</div>
						<div align="center" style="color: white; background: green;">
							<b>${successMsgForEOD}</b>
						</div>
						<!-- /.panel-heading -->
						<div class="row">
							<div class="col-lg-12">
								<div style="color: red" class="pending">
									<c:out value="${SASmsg}"></c:out>
								</div>
								<div style="color: red" class="pending">
									<c:out value="${craMsg}"></c:out>
								</div>
								<div style="color: red" class="pending">
									<c:out value="${soiledMsg}"></c:out>
								</div>
								<div style="color: red" class="pending">
									<c:out value="${otherBankMsg}"></c:out>
								</div>
								<div style="color: red" class="pending">
									<c:out value="${indentMsg}"></c:out>
								</div>
								<div style="color: red" class="pending">
									<c:out value="${mutilatedFullValueMsg}"></c:out>
								</div>
								<div style="color: red" class="pending">
									<c:out value="${diversionMsg}"></c:out>
								</div>

								<div style="color: red" class="pending">
									<c:out value="${mBranchMsg}"></c:out>
								</div>
								<div style="color: red" class="pending">
									<c:out value="${mDSBMsg}"></c:out>
								</div>
								<div style="color: red" class="pending">
									<c:out value="${mRBIMsg}"></c:out>
								</div>
								<div style="color: red" class="pending">
									<c:out value="${mOtherBankMsg}"></c:out>
								</div>
								<div style="color: red" class="pending">
									<c:out value="${mDiversionMsg}"></c:out>
								</div>

								<div style="color: red" class="pending">
									<c:out value="${pOutPutMachineMsg}"></c:out>
								</div>
								<div style="color: red" class="pending">
									<c:out value="${pOutPutManualMsg}"></c:out>
								</div>


							</div>
						</div>
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<form id="showAll">
									<div class="containerr" id="printableArea">
										<div id="table_wrapper">
											<!-- <table class="table table-striped table-bordered table-hover" id="dataTables-example"> -->
											<table class="table table-striped table-bordered table-hover"
												id="tableValue">
												<thead>
													<tr>
														<th style="display: none;">Bin</th>
														<th>Bin</th>
														<th>Denomination</th>
														<th>Category</th>
														<th>No. Of Bundle</th>
														<th>Remark</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach var="row" items="${dailyBinRecon}"
														varStatus="loop">
														<tr class="trValue">
															<td id="id${row.id}" style="display: none;">${row.id}</td>
															<td>${row.binNumber}</td>
															<td>${row.denomination}</td>
															<td>${row.binType}</td>
															<%-- 	<td style="display:none;" id="bundleFromDB${row.id}">${row.receiveBundle}</td> --%>
															<td><input type="text" id="receiveBundle${row.id}"
																onChange="doAjaxPostForBinRecon(${row.id})" id=""></td>
															<td id="message${loop.index}"><input
																readonly="readonly" name="message" type="text"
																id="message${row.id}"></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>
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
        	"lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
        	"pagingType": "full_numbers",
        });
    });
    </script>
	<script type="text/javascript">

   $(document).ready(function() {
	  $("#btnExport").click(function(e) {
	    e.preventDefault();

	    //getting data from our table
	    var data_type = 'data:application/vnd.ms-excel';
	    var table_div = document.getElementById('table_wrapper');
	    var table_html = table_div.outerHTML.replace(/ /g, '%20');

	    var a = document.createElement('a');
	    a.href = data_type + ', ' + table_html;
	    a.download = 'exported_table_' + Math.floor((Math.random() * 9999999) + 1000000) + '.xls';
	    a.click();
	  });
	});

</script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
	<script type="text/javascript" src="./js/print.js"></script>
</body>

</html>