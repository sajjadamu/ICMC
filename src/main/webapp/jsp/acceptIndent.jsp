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
<title>ICICI : Accept Indent</title>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.7.1.min.js"></script>

<script src="./resources/bower_components/jquery/dist/jquery.min.js"></script>
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
<script type="text/javascript">

function doAjaxForBinInDropdown(str) {
	addHeaderJson();
	// get the form values  
	var denomination = $('#denomination'+str).text();
	$.ajax({
		type : "POST",
		url : "././getBinInDropDown",
		data : "denomination=" + denomination,
		success : function(response) {
			var newStr = response.substring(1, response .length-1); // Remove Array Brackets
	        var data=newStr.split(",");
	        var option = '<select name = bin ><option value="">Select Bin</option>';
			for(i=0;i<data.length;i++)
				{
				option+='<option value="'+data[i]+'">'+data[i]+'</option>';
				}
			option+='</select><input type="text" name="desc" value="" >';
			$('#bin'+str).html(option);
		},
		error : function(e) {
			alert('Bin Error: ' + e);
		}
	});
}
	
	
function doAjaxPostUpdateStatus(str) {  
	addHeaderJson();
	// get the form values  
	//var id = $('#id'+str).text();
	var bin = $('#bin'+str).text();
	var bundle = $('#bundle'+str).text();
	var denomination = $('#denomination'+str).text();
	 // var status=$('#status'+str+' :checked').val();
	 
	  $.ajax({  
	    type: "POST", 
	    url: "././updateIndentStatus",
	    data: "bin="+bin+"&bundle="+bundle+"&denomination="+denomination,
	    success: function(response){  
	     // $('#info').html(response);
	     $("#xy"+str).hide();
	    }, 
	    error: function(xhr, ajaxOptions, thrownError){  
	    	console.log("json"+xhr.responseJSON.message);
	    	console.log(' Error: ' + xhr);
	    	console.log(' Error: ' + xhr.status);
	    	console.log(' Error: ' + ajaxOptions);
	    	console.log(' Error: ' + thrownError);
	      alert('Msg Error: ' + xhr.responseJSON.message);  
	    }  
	  }); 
	}  	
</script>

<script type="text/javascript">
	function ajaxViewBinDetail(denomination, bin) {
		addHeaderJson();
		var denomination = denomination;
		var bin = bin;
		  
		$.ajax({
			type : "POST",
			url : "././viewBinDetail",
			data : "denomination=" + denomination+ "&bin=" + bin,
			success : function(response) {
				
				// we have the response  
				var res = JSON.stringify(response);
				
				var a = ({});
				if (JSON.stringify(a) == '{}') {
					var html = "";
					html+="No Wraps available";
					$('#modal-body').html(html);
					$('#myModal').modal('show');
				}

				var newStr = res.substring(1, res.length - 1); // Remove Array Brackets
				var newStr1 = newStr.substring(1, newStr.length - 1); // Remove Curly Brackets
				var data = newStr1.split("},{");
				
				var html = "";
				
				var denom = data[0].split(",")[1].split(":")[1].toString();
				var bin = data[0].split(",")[3].split(":")[1].toString();
				var newBin = bin.substring(1, bin.length - 1);
				
				html += "Denomination : " + denom + "</br>";
				html += "Bin Number : " + newBin + "</br>";
				html += "Available Wraps : "
				
				for(i=0; i<data.length; i++){
					var bundle = data[i].split(",")[2].split(":")[1].toString();
					html += bundle + ", ";
				}
				$('#modal-body').html(html);
				$('#myModal').modal('show');
			},
			error : function(e) {
				alert('Avail capacity Error: ' + e);
			}
		});
	}
</script>

<body oncontextmenu="return false;">

	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Bin Detail</h4>
				</div>
				<div class="modal-body" id="modal-body">
					<p>This is a small modal.</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

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
								<li></li>
							</ul>
							Accept Indent Request
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
												<th>Source</th>
												<th>Denomination</th>
												<th>Bundle</th>
												<th>Bin</th>
												<th>View Bin Detail</th>
												<th>Accept</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="row" items="${records}">
												<tr id="xy${row.bin}">
													<%-- <td id="id${row.id}">${row.id}</td> --%>
													<td id="cashSource${row.bin}">${row.cashSource}</td>
													<td id="denomination${row.bin}">${row.denomination}</td>
													<td id="bundle${row.bin}">${row.bundle}</td>
													<td id="bin${row.bin}">${row.bin}</td>

													<td><sec:authorize access="hasRole('VIEW_INDENT')">
															<a href="#"
																onclick="ajaxViewBinDetail('${row.denomination}','${row.bin}')">View
																Bin Detail </a>
														</sec:authorize></td>

													<td><sec:authorize access="hasRole('ACCEPT_INDENT')">
															<input type="button" value="Accept"
																onclick="doAjaxPostUpdateStatus('${row.bin}');this.disabled=true;">
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
        	"lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
        	"pagingType": "full_numbers",
        	
        });
    });
    </script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>