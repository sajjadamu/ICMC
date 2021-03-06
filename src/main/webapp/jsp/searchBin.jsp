<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="./resources/logo/favicon.ico"
	type="image/x-icon">

<title>ICICI : Search Bin</title>

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

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->


<script type="text/javascript">    
function doAjaxForBin() {  
	addHeaderJson();  
	// get the form values  
  	  var denomination = $('#denomination').val();
  	  $.ajax({  
  	    type: "POST", 
  	    url: "././binByDenomination",
  	    data: "denomination=" + denomination,
  	    success: function(response){ 
  	    	var newStr = response.substring(1, response .length-1); // Remove Array Brackets
  	    	var data=newStr.split(",");
  	    	var radioBtn ="<b>Bin :</b> ";
  	    	//alert("split==="+ss[0]);
  	    	for(var i=0;i<data.length;i++)
  	    		{
  	      $('#info').html(response);
  	      $('#denomination').val(denomination);
  	      $('#bin').val(data[i]);
  	    radioBtn +=' &nbsp;&nbsp;'+$.trim(data[i]);
	      $('#bin').html(radioBtn);
  	        //alert("Response =="+response);  
  	    		}
  	    }, 
  	    error: function(e){  
  	      alert('Denomination Error: ' + e);  
  	    }  
  	  });  
  	}  
  	
</script>
</head>

<body oncontextmenu="return false;">
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
						<div class="panel-heading">Search Bin</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<!--<form role="form">-->
									<%-- <form:form id="userPage" action="saveUser" method="post"
										modelAttribute="user"> --%>
									<form id="showAll">

										<div class="form-group">
											<label>Denomination</label> <input type="text"
												name="denomination" id="denomination"
												onkeyup="doAjaxForBin()" />
										</div>

										<div class="form-group" id="bin"></div>

										<button type="submit" class="btn btn-lg btn-success btn-block"
											value="Details">Search</button>
										<%-- </form:form> --%>
									</form>
								</div>
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
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>