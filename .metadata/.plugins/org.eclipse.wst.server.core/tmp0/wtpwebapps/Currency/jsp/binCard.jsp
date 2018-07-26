<!DOCTYPE html>
<html lang="en">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="./resources/logo/favicon.ico" type="image/x-icon">
<title>ICICI : BIN CARD</title>

<!-- Bootstrap Core CSS -->
<link href="./resources/bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- MetisMenu CSS -->
<link href="./resources/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

<!-- DataTables CSS -->
<link href="./resources/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css" rel="stylesheet">

<!-- DataTables Responsive CSS -->
<link href="./resources/bower_components/datatables-responsive/css/responsive.dataTables.scss" rel="stylesheet">

<!-- Custom CSS -->
<link href="./resources/dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="./resources/bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link rel="./resources/stylesheet" type="text/css" href="./resources/dist/css/style.css">
	
<!-- DataTable -->
<script type="text/javascript"  src="./resources/dataTable/jquery.js"></script>
<script type="text/javascript" src="./resources/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="./resources/js/dataTables.tableTools.min.js"></script>
<link rel="stylesheet" type="text/css" href="./resources/css/jquery.dataTables.min.css">
<link rel="stylesheet" type="text/css" href='./resources/css/dataTables.tableTools.min.css'>
<link rel="stylesheet" type="text/css" href='./resources/css/dataTables.tableTools.css'>
<!-- DataTable -->

<style type="text/css">
	body{ margin: 0; padding: 0; }
	.datewisebin{ margin: 0; padding: 0; }
	.wisespace {padding: 0 30px; width: 120px; } 
	.wisespace_opt{ width: 100%; }
	.wisespace_opt td{ width: 19.5%;  display: table-cell; }
	table.treecol td:nth-child(1) {
    width: 67px;
}
table.treecol td:nth-child(2) {
    width: 78px;
}
table.twocol td:nth-child(1) {
    width: 90px;
}
table span {
    display: block;
    padding: 10px 0;
    font-weight: normal;
    font-size: 15px;
}
</style>

<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script></head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-63" data-genuitec-path="/Currency/src/main/webapp/jsp/binCard.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-63" data-genuitec-path="/Currency/src/main/webapp/jsp/binCard.jsp">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">
			
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">BIN CARD</div>
						<div><input type="button" class="btn btn-default qr-button" onclick="printDiv('printableArea')" value="Print" /></div>
						
						<!-- /.panel-heading -->
						<div class="panel-body">
						<div class="region-con">
								<form:form id="userPage" name="userPage" action="chestSlip" method="post" modelAttribute="reportDate" autocomplete="off">
									<div class="region-con-sec">
										<ul class="region-drop">
											<li>
											<label>Select Date</label>
											<span>
												<input type="text" id="fromDate" name="fromDate"/>
											</span>
											<span>
												<button type="submit" class="btn btn-default" value="Details" style="width: 99px;" >Search</button>
											</span>
											</li>
											
											
										</ul>						
									
									</div>
								</form:form>		
							</div>
						
								<form>
									<div id="printableArea">
<table width="800"  cellpadding="0" cellspacing="0" style="font-family: arial;"  class="datewisebin">
<tr>
<td>
<table  cellspacing="0" cellpadding="0" border="0">
	<tr>
		<td width="50%"></td>
		<td style="text-align: center;" width="90%">
		<table cellspacing="0" cellpadding="5" style="margin: 0 auto;" >
			<tr><td colspan="2"><h2 style="margin: 0; padding: 0;">ICICI BANK LIMITED</h2></td></tr>
			<tr><td style="font-size: 18px; text-align: right;"><b>Currency Chest:</b></td><td style="width: 150px; border-bottom: #ccc 1px solid;"></td></tr>
			<tr><td colspan="2"	><h2 style="margin: 0; padding: 0;">BIN CARD</h2></td></tr>		
		</table>
		</td>
	</tr>
</table>
</td>
</tr>

<tr>
	<td>
		<table cellpadding="0" cellspacing="0" border="1" style="text-align: center; font-size: 18px; font-weight: bold;">
			<tr>
				<td class="wisespace">
					Date
				</td>
				
				<td>
					<table border="0" cellpadding="0" cellspacing="0" >
						<tr>
							<td colspan="3" style="border-bottom: #000 1px solid; padding: 10px;">No. in thousand</td>
						</tr>
						<tr >
							<td style="border-right: #000 1px solid; padding: 0 20px">Deposit</td>
							<td style="border-right: #000 1px solid; padding:0 20px">Withdrawal</td>
							<td style="padding:0 20px">Balance</td>
						</tr>
					</table>
				</td>
				
				<td >
					<table cellpadding="0" cellspacing="0" border="0" >	
						<tr>
							<td colspan="3" style="border-bottom: #000 1px solid; padding: 10px;" >Signature</td>
						</tr>					
						<tr>
							<td style="border-right: #000 1px solid; padding: 0 20px">Treasurer</td>
							<td style="padding:0 20px; width: 120px; white-space: nowrap;">Chest Incharge</td>						
						</tr>
					</table>
					
				</td>
				<td class="wisespace">
					Remarks
				</td>
			</tr>
			<tr>
				<td class="wisespace">
					<span>25.07.2017</span>
				</td>
				
				<td>
					<table border="0" cellpadding="0" cellspacing="0" class="treecol">
						
						<tr>
							<td style="border-right: #000 1px solid; padding: 0 44px"><span>Bal</span></td>
							<td style="border-right: #000 1px solid; padding: 0 58px"><span>B/F</span></td>
							<td style="padding:0 20px"><span>39</span></td>
						</tr>
					</table>
				</td>
				
				<td >
					<table cellpadding="0" cellspacing="0" border="0" class="twocol" >	
											
						<tr>
							<td style="border-right: #000 1px solid; padding: 0 20px"><span>${userID}</span></td>
							<td style="padding:0 20px; width: 120px; white-space: nowrap;"><span>${userID }</span></td>						
						</tr>
					</table>
					
				</td>
				<td class="wisespace">
					<span>U</span>
				</td>
			</tr>
			<tr>
				<td class="wisespace">
					<span>25.07.2017</span>
				</td>
				
				<td>
					<table border="0" cellpadding="0" cellspacing="0" class="treecol">
						
						<tr>
							<td style="border-right: #000 1px solid; padding: 0 52px"><span>-</span></td>
							<td style="border-right: #000 1px solid; padding:0 61px"><span>39</span></td>
							<td style="padding:0 20px"><span>NIL</span></td>
						</tr>
					</table>
				</td>
				
				<td >
					<table cellpadding="0" cellspacing="0" border="0" class="twocol" >	
											
						<tr>
							<td style="border-right: #000 1px solid; padding: 0 20px"><span>${userID}</span></td>
							<td style="padding:0 20px; width: 120px; white-space: nowrap;"><span>${userID}</span></td>						
						</tr>
					</table>
					
				</td>
				<td class="wisespace">
					<span>U</span>
				</td>
			</tr>
			<tr>
				<td class="wisespace">
					<span>25.07.2017</span>
				</td>
				
				<td>
					<table border="0" cellpadding="0" cellspacing="0" class="treecol">
						
						<tr>
							<td style="border-right: #000 1px solid; padding: 0 47px"><span>10</span></td>
							<td style="border-right: #000 1px solid; padding:0 66px"><span>-</span></td>
							<td style="padding:0 20px"><span>10</span></td>
						</tr>
					</table>
				</td>
				
				<td >
					<table cellpadding="0" cellspacing="0" border="0" class="twocol" >	
											
						<tr>
							<td style="border-right: #000 1px solid; padding: 0 20px"><span>${userID}</span></td>
							<td style="padding:0 20px; width: 120px; white-space: nowrap;"><span>${userID}</span></td>						
						</tr>
					</table>
					
				</td>
				<td class="wisespace">
					<span>U</span>
				</td>
			</tr>
			<tr>
				<td class="wisespace">
					<span>26.07.2017</span>
				</td>
				
				<td>
					<table border="0" cellpadding="0" cellspacing="0" class="treecol">
						
						<tr>
							<td style="border-right: #000 1px solid; padding: 0 52px"><span>-</span></td>
							<td style="border-right: #000 1px solid; padding:0 61px"><span>10</span></td>
							<td style="padding:0 20px"><span>NIL</span></td>
						</tr>
					</table>
				</td>
				
				<td >
					<table cellpadding="0" cellspacing="0" border="0" class="twocol" >	
											
						<tr>
							<td style="border-right: #000 1px solid; padding: 0 20px"><span>${userID}</span></td>
							<td style="padding:0 20px; width: 120px; white-space: nowrap;"><span>${userID}</span></td>						
						</tr>
					</table>
					
				</td>
				<td class="wisespace">
					<span>U</span>
				</td>
			</tr>
			<tr>
				<td class="wisespace">
					<span>26.07.2017</span>
				</td>
				
				<td>
					<table border="0" cellpadding="0" cellspacing="0" class="treecol">
						
						<tr>
							<td style="border-right: #000 1px solid; padding: 0 46px"><span>73</span></td>
							<td style="border-right: #000 1px solid; padding:0 67px"><span>-</span></td>
							<td style="padding:0 20px"><span>73</span></td>
						</tr>
					</table>
				</td>
				
				<td >
					<table cellpadding="0" cellspacing="0" border="0" class="twocol" >	
											
						<tr>
							<td style="border-right: #000 1px solid; padding: 0 20px"><span>${userID}</span></td>
							<td style="padding:0 20px; width: 120px; white-space: nowrap;"><span>${userID}</span></td>						
						</tr>
					</table>
					
				</td>
				<td class="wisespace">
					<span>U</span>
				</td>
			</tr>
			<tr>
				<td class="wisespace">
					<span>26.07.2017</span>
				</td>
				
				<td>
					<table border="0" cellpadding="0" cellspacing="0" class="treecol">
						
						<tr>
							<td style="border-right: #000 1px solid; padding: 0 46px"><span>01</span></td>
							<td style="border-right: #000 1px solid; padding:0 67px"><span>-</span></td>
							<td style="padding:0 20px"><span>74</span></td>
						</tr>
					</table>
				</td>
				
				<td >
					<table cellpadding="0" cellspacing="0" border="0" class="twocol" >	
											
						<tr>
							<td style="border-right: #000 1px solid; padding: 0 20px"><span>${userID}</span></td>
							<td style="padding:0 20px; width: 120px; white-space: nowrap;"><span>${userID}</span></td>						
						</tr>
					</table>
					
				</td>
				<td class="wisespace">
					<span>U</span>
				</td>
			</tr>
			<tr>
				<td class="wisespace">
					<span>27.07.2017</span>
				</td>
				
				<td>
					<table border="0" cellpadding="0" cellspacing="0" class="treecol">
						
						<tr>
							<td style="border-right: #000 1px solid; padding: 0 42px"><span>130</span></td>
							<td style="border-right: #000 1px solid; padding:0 67px"><span>-</span></td>
							<td style="padding:0 20px"><span>204</span></td>
						</tr>
					</table>
				</td>
				
				<td >
					<table cellpadding="0" cellspacing="0" border="0" class="twocol" >	
											
						<tr>
							<td style="border-right: #000 1px solid; padding: 0 20px"><span>${userID}</span></td>
							<td style="padding:0 20px; width: 120px; white-space: nowrap;"><span>${userID}</span></td>						
						</tr>
					</table>
					
				</td>
				<td class="wisespace">
					<span>U</span>
				</td>
			</tr>
			
			
			<tr>
				<td class="wisespace">
					<span>29.07.2017</span>
				</td>
				
				<td>
					<table border="0" cellpadding="0" cellspacing="0" class="treecol">
						
						<tr>
							<td style="border-right: #000 1px solid; padding: 0 52px"><span>-</span></td>
							<td style="border-right: #000 1px solid; padding:0 57px"><span>204</span></td>
							<td style="padding:0 20px"><span>NIL</span></td>
						</tr>
					</table>
				</td>
				
				<td >
					<table cellpadding="0" cellspacing="0" border="0" class="twocol" >	
											
						<tr>
							<td style="border-right: #000 1px solid; padding: 0 20px"><span>${userID}</span></td>
							<td style="padding:0 20px; width: 120px; white-space: nowrap;"><span>${userID}</span></td>						
						</tr>
					</table>
					
				</td>
				<td class="wisespace">
					<span>U</span>
				</td>
			</tr>
			
			
			
			<tr>
				<td class="wisespace">
					<span>02.08.2017</span>
				</td>
				
				<td>
					<table border="0" cellpadding="0" cellspacing="0" class="treecol">
						
						<tr>
							<td style="border-right: #000 1px solid; padding: 0 46px"><span>73</span></td>
							<td style="border-right: #000 1px solid; padding:0 67px"><span>-</span></td>
							<td style="padding:0 20px"><span>73</span></td>
						</tr>
					</table>
				</td>
				
				<td >
					<table cellpadding="0" cellspacing="0" border="0" class="twocol" >	
											
						<tr>
							<td style="border-right: #000 1px solid; padding: 0 20px"><span>${userID}</span></td>
							<td style="padding:0 20px; width: 120px; white-space: nowrap;"><span>${userID}</span></td>						
						</tr>
					</table>
					
				</td>
				<td class="wisespace">
					<span>U</span>
				</td>
			</tr>
			
			
			<tr>
				<td class="wisespace">
					<span>03.08.2017</span>
				</td>
				
				<td>
					<table border="0" cellpadding="0" cellspacing="0" class="treecol">
						
						<tr>
							<td style="border-right: #000 1px solid; padding: 0 51px"><span>-</span></td>
							<td style="border-right: #000 1px solid; padding:0 62px"><span>63</span></td>
							<td style="padding:0 20px"><span>10</span></td>
						</tr>
					</table>
				</td>
				
				<td >
					<table cellpadding="0" cellspacing="0" border="0" class="twocol" >	
											
						<tr>
							<td style="border-right: #000 1px solid; padding: 0 20px"><span>${userID}</span></td>
							<td style="padding:0 20px; width: 120px; white-space: nowrap;"><span>${userID}</span></td>						
						</tr>
					</table>
					
				</td>
				<td class="wisespace">
					<span>U</span>
				</td>
			</tr>
			
			
			<tr>
				<td class="wisespace">
					<span>03.08.2017</span>
				</td>
				
				<td>
					<table border="0" cellpadding="0" cellspacing="0" class="treecol">
						
						<tr>
							<td style="border-right: #000 1px solid; padding: 0 46px"><span>88</span></td>
							<td style="border-right: #000 1px solid; padding:0 67px"><span>-</span></td>
							<td style="padding:0 20px"><span>98</span></td>
						</tr>
					</table>
				</td>
				
				<td >
					<table cellpadding="0" cellspacing="0" border="0" class="twocol" >	
											
						<tr>
							<td style="border-right: #000 1px solid; padding: 0 20px"><span>${userID}</span></td>
							<td style="padding:0 20px; width: 120px; white-space: nowrap;"><span>${userID}</span></td>						
						</tr>
					</table>
					
				</td>
				<td class="wisespace">
					<span>U</span>
				</td>
			</tr>
			
			<tr>
				<td class="wisespace">
					<span>04.08.17</span>
				</td>
				
				<td>
					<table border="0" cellpadding="0" cellspacing="0" class="treecol">
						
						<tr>
							<td style="border-right: #000 1px solid; padding: 0 51px"><span>-</span></td>
							<td style="border-right: #000 1px solid; padding:0 62px"><span>53</span></td>
							<td style="padding:0 20px"><span>45</span></td>
						</tr>
					</table>
				</td>
				
				<td >
					<table cellpadding="0" cellspacing="0" border="0" class="twocol" >	
											
						<tr>
							<td style="border-right: #000 1px solid; padding: 0 20px"><span>${userID}</span></td>
							<td style="padding:0 20px; width: 120px; white-space: nowrap;"><span>${userID}</span></td>						
						</tr>
					</table>
					
				</td>
				<td class="wisespace">
					<span>U</span>
				</td>
			</tr>
			
			<tr>
				<td class="wisespace">
					<span>05.08.2017</span>
				</td>
				
				<td>
					<table border="0" cellpadding="0" cellspacing="0" class="treecol">
						
						<tr>
							<td style="border-right: #000 1px solid; padding: 0 51px"><span>-</span></td>
							<td style="border-right: #000 1px solid; padding:0 62px"><span>45</span></td>
							<td style="padding:0 20px"><span>NIL</span></td>
						</tr>
					</table>
				</td>
				
				<td >
					<table cellpadding="0" cellspacing="0" border="0" class="twocol" >	
											
						<tr>
							<td style="border-right: #000 1px solid; padding: 0 20px"><span>${userID}</span></td>
							<td style="padding:0 20px; width: 120px; white-space: nowrap;"><span>${userID}</span></td>						
						</tr>
					</table>
					
				</td>
				<td class="wisespace">
					<span>U</span>
				</td>
			</tr>
			
			<tr>
				<td class="wisespace">
					<span>08.08.2017</span>
				</td>
				
				<td>
					<table border="0" cellpadding="0" cellspacing="0" class="treecol">
						
						<tr>
							<td style="border-right: #000 1px solid; padding: 0 42px"><span>163</span></td>
							<td style="border-right: #000 1px solid; padding:0 67px"><span>-</span></td>
							<td style="padding:0 20px"><span>163</span></td>
						</tr>
					</table>
				</td>
				
				<td >
					<table cellpadding="0" cellspacing="0" border="0" class="twocol" >	
											
						<tr>
							<td style="border-right: #000 1px solid; padding: 0 20px"><span>${userID}</span></td>
							<td style="padding:0 20px; width: 120px; white-space: nowrap;"><span>${userID}</span></td>						
						</tr>
					</table>
					
				</td>
				<td class="wisespace">
					<span>U</span>
				</td>
			</tr>
			
			<tr>
				<td class="wisespace">
					<span>09.08.2017</span>
				</td>
				
				<td>
					<table border="0" cellpadding="0" cellspacing="0" class="treecol">
						
						<tr>
							<td style="border-right: #000 1px solid; padding: 0 51px"><span>-</span></td>
							<td style="border-right: #000 1px solid; padding:0 58px"><span>105</span></td>
							<td style="padding:0 20px"><span>58</span></td>
						</tr>
					</table>
				</td>
				
				<td >
					<table cellpadding="0" cellspacing="0" border="0" class="twocol" >	
											
						<tr>
							<td style="border-right: #000 1px solid; padding: 0 20px"><span>${userID}</span></td>
							<td style="padding:0 20px; width: 120px; white-space: nowrap;"><span>${userID}</span></td>						
						</tr>
					</table>
					
				</td>
				<td class="wisespace">
					<span>U</span>
				</td>
			</tr>
			
			
			<tr>
				<td class="wisespace">
					<span>09.08.2017</span>
				</td>
				
				<td>
					<table border="0" cellpadding="0" cellspacing="0" class="treecol">
						
						<tr>
							<td style="border-right: #000 1px solid; padding: 0 41px"><span>136</span></td>
							<td style="border-right: #000 1px solid; padding:0 68px"><span>-</span></td>
							<td style="padding:0 20px"><span>194</span></td>
						</tr>
					</table>
				</td>
				
				<td >
					<table cellpadding="0" cellspacing="0" border="0" class="twocol" >	
											
						<tr>
							<td style="border-right: #000 1px solid; padding: 0 20px"><span>${userID}</span></td>
							<td style="padding:0 20px; width: 120px; white-space: nowrap;"><span>${userID}</span></td>						
						</tr>
					</table>
					
				</td>
				<td class="wisespace">
					<span>U</span>
				</td>
			</tr>
			
		</table>
	</td>
</tr>

</table>
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
	<!-- /#wrapper -->
	
	<!-- Bootstrap Core JavaScript -->
	<script src="./resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script src="./resources/bower_components/metisMenu/dist/metisMenu.min.js"></script>
	
	<script src="./resources/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="./resources/dist/js/sb-admin-2.js"></script>

	<!-- Page-Level Demo Scripts - Tables - Use for reference -->
	
<script type="text/javascript" src="./js/htmlInjection.js"></script>
<script type="text/javascript" src="./js/print.js"></script>

</body>

</html>