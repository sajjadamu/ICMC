<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page import="com.chest.currency.enums.BinCategoryType"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.mysema.query.Tuple"%>
<%@page import="java.util.List"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="./resources/logo/favicon.ico"
	type="image/x-icon">

<script src="./resources/bower_components/jquery/dist/jquery.min.js"></script>
<script type="text/javascript" src="./js/jquery.validate.min.js"></script>

<title>ICICI : Auditor Indent</title>

<!-- Bootstrap Core CSS -->
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

<script type="text/javascript">
/* 	function submitChecking() {
		var denomination = $('input[name=denomination]:checked').val();
		var currencyType = $('#currencyType').val();
		var binNumber = $('#binNumber').val();
		var bundle = $('#bundle').val();
		if (denomination == undefined || denomination == null
				|| currencyType == undefined || currencyType == 0
				|| binNumber == undefined || binNumber == null
				|| bundle == undefined || bundle == 0) {
			alert("Please Select All data");
			return false;
		} else {
			return true;
		}
	} */

	function binData() {
		//alert("hello");
		addHeaderJson();
		var denomination = $('input[name=denomination]:checked').val();
		var currencyType = $('#currencyType').val();
		if (denomination == undefined || denomination == null) {
			alert("Please Select Denomination:");

		} else {
			if (currencyType == undefined || currencyType == 0) {

			} else {
				$
						.ajax({
							type : "POST",
							url : "././getbinFromBinTransaction",
							data : "denomination=" + denomination
									+ "&currencyType=" + currencyType,

							success : function(response) {
								var newStr = response.toString();
								var data = newStr.split(",");
								var option = '<option value="">Select Bin</option>';
								for (i = 0; i < data.length; i++) {
									option += '<option value="'
											+ data[i].trim() + '">'
											+ data[i].trim() + '</option>';
								}
								if (data.length > 0) {
									$('#binNumber').html(option);
								} else {
									alert("Bin is not available for this Denomination and currency type");
								}

							},
							error : function(e) {
								alert('Bin Error: ' + e);
							}
						});
			}
		}
	}
</script>
<script>
	function ajaxViewShrinkBundle(denomination, binCategoryType) {
		addHeaderJson();
		var denomination = denomination;
		$
				.ajax({
					type : "POST",
					url : "././viewShrinkBundle",
					data : "denomination=" + denomination + "&binCategoryType="
							+ binCategoryType,
					success : function(response) {

						var res = JSON.stringify(response);

						var a = ({});
						if (JSON.stringify(a) == '{}') {
							var html = "";
							html += "No Wraps available";
							$('#modal-body').html(html);
							$('#myModal').modal('show');
						}

						var newStr = res.substring(1, res.length - 1); // Remove Array Brackets
						var newStr1 = newStr.substring(1, newStr.length - 1); // Remove Curly Brackets
						var data = newStr1.split("},{");

						var html = "";

						var denom = data[0].split(",")[4].split(":")[1]
								.toString();
						var binNum = data[0].split(",")[8].split(":")[1]
						         .toString();

						html += "Denomination : " + denom + ", Bin :"+ binNum.replace(/['"]+/g, '') +"</br>";

						html += "Available Wraps : "
						for (i = 0; i < data.length; i++) {
							var bundle = data[i].split(",")[5].split(":")[1]
									.toString();
							html += bundle + ", ";
						}
						$('#modal-body').html(html);
						$('#myModal').modal('show');
					},
					error : function(e) {
						alert('Avail capacity Error: ' + e.responseJSON.message);
					}
				});
	}
	
	function shrinkWrapBundle() {
		var processedOrUnprocessed = $('#currencyType').val();
		if(processedOrUnprocessed=="UNPROCESS"){
			$("#shrinkWrapTable").show();
			//$("#binORbox").show();
		}else {
			$("#shrinkWrapTable").hide();
			//$("#binORbox").hide();
		}
		
	}
</script>
</head>

<body oncontextmenu="return false;">
	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Shrink Wrap Bundle Detail</h4>
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
			<!--  <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Forms</h1>
                </div>
                /.col-lg-12
            </div> -->
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<!-- <div class="panel-heading">Add/Upload Branch</div> -->
						<div class="panel-heading">
							<ul>
								<li><a href="././viewAuditorIndent"><i
										class="fa fa-table fa-fw"></i> View Indent List</a></li>
							</ul>
							Indent Request
						</div>

						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<!--<form role="form">-->
									<div align="center" style="color: red;">
										<b>${errorMsg}</b>
									</div>
									<form:form id="userPage" name="userPage"
										action="saveAuditorIndent" method="post" modelAttribute="user"
										autocomplete="off">

										<div class="form-group">
											<label>Denomination</label><br>
											<form:radiobuttons items="${denominationList}"
												itemLabel="denomination" itemValue="denomination"
												path="denomination" id="denomination" name="denomination"
												onclick="binData()" checked="true" />
										</div>

										<div class="form-group">
											<label>Currency Type</label>
											<form:select path="binType" id="currencyType"
												name="currencyType" cssClass="form-control"
												onchange="binData();shrinkWrapBundle();">
												<option value="">Select Currency Type</option>
												<form:options items="${currencyTypeList}" />
											</form:select>
										</div>

										<%-- <div class="form-group">
											<label>Bin Number</label>
											<form:input path="binNumber" id="binNumber" name="binNumber"
												maxlength="45" cssClass="form-control" />
										</div> --%>

										<div class="form-group">
											<label>Bin Number</label>
											<form:select path="binNumber" id="binNumber"
												cssClass="form-control">
												<option value="">Select Bin</option>
											</form:select>
										</div>
										<div class="form-group">
											<label>Bundle</label>
											<form:input path="bundle" id="bundle" name="bundle"
												maxlength="45" cssClass="form-control" />
										</div>

										<button id="submt" type="submit"
											class="btn btn-lg btn-success btn-block" value="Details">Submit</button>
									</form:form>
								</div>
								<!-- /.col-lg-6 (nested) -->
								<div class="col-lg-6"></div>
								<!-- /.col-lg-6 (nested) -->
							</div>
							<!-- /.row (nested) -->
						</div>
						<!-- /.panel-body -->

						<div class="row" id="shrinkWrapTable" hidden="true">
							<div class="col-lg-12 form-group">
								<table class="table">
									<thead>
										<th class="col-lg-2">Denomination</th>
										<th class="col-lg-2">Category</th>
										<!-- <th class="col-lg-2">Bin Name</th> -->
										<th class="col-lg-2">Total Bundle</th>
										<th class="col-lg-2">Pending Bundle</th>
										<th class="col-lg-2">View Shrink bundle</th>
									</thead>
									<%
										List<Tuple> shrinkWrapBundle = (List<Tuple>) request.getAttribute("branchReceipts");
										for (Tuple tuple : shrinkWrapBundle) {
									%>
									<tr>
										<td><%=tuple.get(0, Integer.class)%></td>
										<td><%=tuple.get(4, BinCategoryType.class)%></td>
										<%-- <td><%=tuple.get(5, String.class)%></td> --%>
										<td><%=tuple.get(2, BigDecimal.class)%></td>
										<td><%=tuple.get(3, BigDecimal.class)%></td>
										<td><sec:authorize access="hasRole('VIEW_INDENT')">
												<a href="#"
													onclick="ajaxViewShrinkBundle(<%=tuple.get(0, Integer.class)%>,'<%=tuple.get(4, BinCategoryType.class)%>')">Details</a>
											</sec:authorize></td>
									</tr>
									<%
										}
									%>
								</table>
							</div>
						</div>
					</div>
					<!-- /.panel -->
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->
	<script type="text/javascript">

	$.validator.addMethod("loginRegex", function(value, element) {
	    return this.optional(element) || /^([1-9][0-9]{0,2}|1000)?$/i.test(value);
	}, "Productivity Lost must contain only letters,Space , dashes."); 
	$(function() {
		  $("form[name='userPage']").validate({
		    rules: {
		    	binType:{
		    		required:function(element){
			    		var fileValue=$('#currencyType').val();			
			    		if(fileValue == '')
		    				return true;
		    			else
		    				return false;
			    				}
			    	},
		    	bundle:{
		    		loginRegex:true,
		    		required:function(element){
		    			var fileValue = $('#bundle').val();
		    			if(fileValue == ''){
		    				return true;
		    			}else{
		    				return false;
		    			}
		    		}	
		    				},
		    	binNumber:{
		    		required:function(element){
		    		var fileValue=$('#binNumber').val();			
		    		if(fileValue == '')
	    				return true;
	    			else
	    				return false;
		    				}
		    	}				
		    				
		    },
		    messages: {
		    	bundle:{
		    		required:"Please Enter Bundle ",
		    		loginRegex:"Accept Only Numeric"
		    			},
		    	binNumber:{required:"Please Select Bin Number "},
		    	binType:{required:"Please Select Currency Type "}
		    },
		    submitHandler: function(form) {
		    	$('#submt').prop('disabled',true);
		    	form.submit();
		    }
		  });
		});
	
	
	</script>
	<!-- jQuery -->
	<!-- <script src="./resources/bower_components/jquery/dist/jquery.min.js"></script> -->

	<!-- Bootstrap Core JavaScript -->
	<script
		src="./resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script
		src="./resources/bower_components/metisMenu/dist/metisMenu.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="./resources/dist/js/sb-admin-2.js"></script>

	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>