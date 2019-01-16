<!DOCTYPE html>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.List"%>
<%@page import="com.mysema.query.Tuple"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="./resources/logo/yes_favicon.ico"
	type="image/x-icon">

<script type="text/javascript" src="./js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="./js/jquery.validate.min.js"></script>

<title> Soiled Remittance Preparation</title>

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
function doAjaxPostForDeduction(str) { 
	addHeaderJson();  
	var soiledIndent={
	  "denomination":$('#denomination'+str).text(),
	  "bundle":$('#bundle'+str).text(),
	  "requestBundle":$('#requestBundle'+str).val(),
	 }
	 var isValid=true;
	 var bundle = parseInt(($('#bundle'+str).text()));
	 var requestBundle = parseInt(($('#requestBundle'+str).val()));
	 
	 if(requestBundle<=bundle)
	 {
	  $.ajax({  
	    type: "POST", 
	    contentType : 'application/json; charset=utf-8',
	    dataType : 'json',
	    url: "././getBinForSoiledAndBoxPreparation",
	    data: JSON.stringify(soiledIndent),
	    success: function(response){
	    	var bin = "";
	    	var bundle = "";
	    	var extractFromBin = "";
	    	$.each(response,function(index,element){
	    		if(element.filepath == null){
		    		bin = element.binNumber;
		    		bundle = element.bundle;
		    		extractFromBin += bin+" / "+bundle+", ";
	    		}});
	    	
	    	$('#binBundle'+str).val(extractFromBin);
    		$("#binBundle"+str).prop('disabled', true);
    		$("#buttonId"+str).prop('disabled', true);
    		
    		//For Print Text
    		
    		var str2 = '';
		    $.each(response,function(index,element){
		    	if(element.filepath != null){
			    	str2+= '<div><div style="width:25%;float:left;" ><img src="./files'+element.filepath+'" alt="QRPrint"></div>'
			    	str2 += "<div>BOX : Soiled "
						+ "</br>"
						+ "Denomination : "
						+ element.denomination
						+ "</br>"
						+ "Total Bundle :"
						+ element.bundle
						+ "</div></div><BR><HR>";
		    	}});
		    	//alert(str);
		    	$('#printSection').html(str2);
				$('#printSection').show();
				$.print("#printSection");
				$('#printSection').hide();
				$('#print'+str).prop('disabled', true);
				$("#member").prop('disabled', true);
	    	
	    	if(response.status === 'CANCELLED'){
	    		var errorMessage = "Error : Total Available bundle are:"+response.availableBundle;
	    		alert(errorMessage);
	    	}else{
		    	alert('Successfull : Record submit.');
		    	//window.location='././soiledPreparation';
	    	}
	    }, 
	    error: function(e){
	      alert('Error:'+e);  
	    }  
	  }); 
		  }
	  else
		  {
		  	alert("Please Enter Extract Bundle Less Than or Equal to Total Bundle");
		 	return false;
		  }
	} 
	
</script>

<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-191"
	data-genuitec-path="/Currency/src/main/webapp/jsp/soiledRemittancePreparation.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-191"
		data-genuitec-path="/Currency/src/main/webapp/jsp/soiledRemittancePreparation.jsp">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<ul>
								<li><a href="././viewPreparedSoiledBoxes"><i
										class="fa fa-table fa-fw"></i> View Prepared BOX's</a></li>
							</ul>
							Preparation for Soiled Remittance
						</div>

						<table class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th>DENOMINATION</th>
									<th>AVAILABLE BUNDLE</th>
									<th>OLD DATE</th>
									<th>EXTRACT BUNDLE</th>
									<th>BIN/BUNDLE</th>
									<th>ACTION</th>
								</tr>
							</thead>
							<tbody>
								<%
										List<Tuple> listTupleForSoiled = (List<Tuple>) request.getAttribute("soiledBinSummary");
										for (Tuple tuple : listTupleForSoiled) {
									%>
								<tr>
									<td id="denomination<%=tuple.get(0, Integer.class)%>"><%=tuple.get(0, Integer.class)%></td>
									<td id="bundle<%=tuple.get(0,Integer.class)%>"><%=tuple.get(1, BigDecimal.class)%></td>
									<td><fmt:formatDate pattern="dd-MMM-yy"
											value="<%=tuple.get(2, Calendar.class).getTime()%>" /></td>
									<td><input type="text" name="bundle"
										id="requestBundle<%=tuple.get(0,Integer.class)%>"></td>
									<td><input type="text" name="binBundle"
										id="binBundle<%=tuple.get(0, Integer.class)%>"></td>
									<td><input type="button"
										id="buttonId<%=tuple.get(0, Integer.class)%>"
										value="Create BOX & Generate QR"
										onclick="doAjaxPostForDeduction(<%=tuple.get(0, Integer.class)%>)"></td>
								</tr>
								<%
										}
									%>
							</tbody>
						</table>

						<div id="printSection" style="display: none;"></div>

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
	<!-- /#wrapper -->

	<!-- jQuery -->
	<!-- <script src="./resources/bower_components/jquery/dist/jquery.min.js"></script> -->

	<!-- Bootstrap Core JavaScript -->
	<script
		src="./resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<script src="./resources/js/jQuery.print.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script
		src="./resources/bower_components/metisMenu/dist/metisMenu.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="./resources/dist/js/sb-admin-2.js"></script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>