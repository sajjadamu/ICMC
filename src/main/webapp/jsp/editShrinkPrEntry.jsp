<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="en">

<head>
<link rel="shortcut icon" href="./resources/logo/favicon.ico" type="image/x-icon">
<script src="./js/jquery-1.12.0.min.js"></script>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<!-- /#wrapper -->

	<!-- jQuery -->
	<script src="./resources/bower_components/jquery/dist/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script
		src="./resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script
		src="./resources/bower_components/metisMenu/dist/metisMenu.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="./resources/dist/js/sb-admin-2.js"></script>

	<script src="./resources/js/jQuery.print.js"></script>
<title>ICICI : Edit Branch Receipt</title>

<script src="./resources/Currency/js/jquery.js"></script>
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

<link rel="stylesheet" type="text/css" href="./resources/dist/css/style.css">

<script type="text/javascript">

function doAjaxPost() {  
	  // get the form values  
	  var Denomination = $('#denomination').val();
	  var Bundle = $('#bundle').val();
	  var total = Denomination*100*Bundle;
	  $('#total').val(total);
}

function doAjaxPostForBranch() {  
	addHeader();  
	var solid = $('#solId').val();
	  if(solid.length==4)
	  {
	  $.ajax({  
	    type: "POST", 
	    url: "././branchName",
	    data: "solid=" + solid,
	    success: function(response){  
	      var res = JSON.stringify(response);
	      var newStr = res.substring(1, res.length - 1);
	      $('#info').html(response);
	      $('#solid').val(solid);
	      $('#branch').val(newStr);
	    }, 
	    error: function(e){  
	      //alert('branchName Error: ' + e);
	    }  
	  });  
	  }
	} 

function SavePrint() {
	addHeader();
	var isValid = true;
	var branchReceipt = {
			"id":$('#id').val(),
			"solId":$('#solId').val(),
			"branch":$('#branch').val(),
			"srNumber":$('#srNumber').val(),
			"denomination":$('#denomination').val(),
			"bundle":$('#bundle').val(),
			"total":$('#total').val(),
			"bin":$('#bin').val(),
			/* "binCategoryType":$('input[name=binCategoryType]:checked').val(), */
	      }
	
	
	if($('#solId').val() == ""){
		$('#err1').show();
		isValid=false;
	}
	if($('#branch').val() == ""){
		$('#err4').show();
		isValid=false;
	}
	if($('#srNumber').val() == ""){
		$('#err5').show();
		isValid=false;
	}
	if(($('#srNumber').val().length)  != 11  ){
		$('#err6').show();
		isValid = false;
	}
	if($('#denomination').val()!=2000 && $('#denomination').val()!=1000 && $('#denomination').val()!=500 && $('#denomination').val()!=200
			&& $('#denomination').val()!=100 && $('#denomination').val()!=50 && $('#denomination').val()!=20 
			&& $('#denomination').val()!=10 && $('#denomination').val()!=5 && $('#denomination').val()!=2 
			&& $('#denomination').val()!=1){
		$('#err2').show();
		isValid=false;
	}
	if($('#bundle').val() == ""){
		$('#err3').show();
		isValid=false;
	}
	
	
	if(isValid){
	     $.ajax({
				type : "POST",
				contentType : 'application/json; charset=utf-8',
			    dataType : 'json',
				url : "././updateShrinkEntry",
				data: JSON.stringify(branchReceipt),
				success : function(response) {
					
					window.location='././viewShrink';
					
					//For Print Text
						/* var str2 = '';
					   	$.each(response,function(index,element){
				    		str2+= '<div><div style="width:25%;float:left;" ><img src="./files'+element.filepath+'" alt="QRPrint"></div>'
				    		str2 += "<div>Bin : "
								+"<font size=50>"+ element.bin+"</font>"
								+ "</br>"
								+ "Name : "
								+ element.branch
								+ "</br>"
								+ "Deno. :"
								+ element.denomination
								+ "</br>"
								+ "Bundle : "
								+ element.bundle
								+ "</br>"
								+ "Total : "
								+ element.total 
									
								+ "</div></div><BR><HR>";
					    });
					    	//alert(str2);
					    $('#printSection').html(str2);
						$('#printSection').show();
						$.print("#printSection");
						$('#printSection').hide();
						$('#print').prop('disabled', true); */
					},
			error : function(e) {
				alert('Print Error: ' + e.responseJSON.message);
			}
		}); 
 	}
}
</script>

</head>

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
								<li><a href="././viewShrink"><i class="fa fa-table fa-fw"></i>View Branch Receipt</a></li>
							</ul>Edit Branch Receipt
						</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-8">
									<!--<form role="form">-->
									<form:form id="" action="" method="post" modelAttribute="user" autocomplete="off">

										<form:hidden path="id"/>
										
										<div class="form-group">
											<label id="err1" style="display: none;color: red">Please Enter solId</label>
											<label id="err11" style="display: none;color: red">Please Enter 4 Digit Sol Id</label>
											<label>Sol ID</label>
											<form:input path="solId" name="solId" minlenght="4" maxlength="4" id="solId" cssClass="form-control"
												onkeyup="doAjaxPostForBranch()" />
												
											<label>Branch</label>
											<form:input path="branch" id="branch" cssClass="form-control" readonly="true" />
											<label id="err4" style="display: none;color: red">Please Enter Any valid Sol Id</label>
										</div>
										
										<div class="form-group">
											<label>SR Number</label>
											<form:input path="srNumber" id="srNumber" minlenght="11" maxlength="11" cssClass="form-control"/>
											<label id="err5" style="display:none; color:red">Please Enter SR Number</label>
											<label id="err6" style="display:none; color:red">SR Number should have 11 digits</label>
										</div>

										<label id="err2" style="display: none; color: red">Please Enter Valid Denomination</label>
										<label id="err3" style="display: none; color: red">Please Enter Packets</label>
										
										<div class="form-group">
											<label>Denomination</label>
											<form:input path="denomination" id="denomination" name="denomination" cssClass="form-control"/>
										</div>
										
										<div class="form-group">
											<label>Packet</label>
											<form:input path="bundle" value="${packets}" id="bundle" name="bundle" cssClass="form-control"
												onkeyup="doAjaxPost()"/>
										</div>
										
										
										
										
										<div class="form-group">
											<label>Total</label>
											<form:input path="total" id="total" name="total" cssClass="form-control" readonly="true"/>
										</div>
										
										<div class="form-group">
											<label>Bin</label>
											<form:input path="bin" id="bin" name="bin" cssClass="form-control" readonly="true"/>
										</div>
										
										<%-- <div class="form-group">
											<label>Bin Category Type</label>
											<form:radiobutton path="binCategoryType" id="binCategoryType" name="binCategoryType"/>BIN
											<form:radiobutton path="binCategoryType" id="binCategoryType" name="binCategoryType"/>BOX
										</div> --%>
										
										<button type="button" name="print" class="btn btn-lg btn-success btn-block"
											onclick="SavePrint(); this.disabled=true" >Update AND Print QR</button>
											
									</form:form>
								</div>
								<div id="printSection" style="display: none;"></div>
								<!-- /.col-lg-6 (nested) -->
								<div class="col-lg-6"></div>
								<!-- /.col-lg-6 (nested) -->
							</div>
							<!-- /.row (nested) -->
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /#page-wrapper -->

	</div>
	<script src="./resources/js/jQuery.print.js"></script>
<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>