<!DOCTYPE html>
<%@page import="org.omg.PortableInterceptor.INACTIVE"%>
<%@page import="javax.persistence.criteria.CriteriaBuilder.In"%>
<%@page import="com.chest.currency.entity.model.User"%>
<%@page import="java.util.Date"%>
<html lang="en">
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.List"%>
<%@page import="com.mysema.query.Tuple"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<title>ICICI : View ORV Voucher</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript">
$(document).keydown(function(e) {
    if (e.keyCode == 80 && e.ctrlKey) {
    	$(".spaceprnt > li").addClass('spacerbox');
        //$(".spacerbox").css("margin-top","10px");
        //$(".spacerbox").css("padding-bottom","30px");
        $(".btnbox").css("display","block");
    }
});

function refresh(){
	window.location='././printAllVoucher';
}
</script>

<script src="./resources/js/jquery.js"></script>
<script type="text/javascript" src="./js/print.js"></script>

<style>
	.boxbor{ border:#000 3px solid; width: 875px; font-size:12px;}
        .boxbor_heading{ text-align: center; width: 70%;  }
        .boxbor_heading span { text-transform: uppercase;}
        .boxbor_lt, .boxbor_rt{ width: 15%;}
        .topbox_boxbor, .bg_seprate{ width: 100%;}
        .bg_seprate td{ width: 50%; padding: 8px; background: #e6e6e6;}
        .midbox_boxbor { width: 100%; border-top: #000 3px solid; border-bottom: #000 3px solid; }
        .midbox_boxbor td{ width: 10%; border:#000 1px solid; margin: 0; padding: 8px; } 
        .rt_boxbor td{ padding: 0; border:none;}
        .rt_boxbor td:first-child{ padding: 0; border-right:#000 1px solid;}
       .result_boxbor_left td {
    padding-top: 20px;
    padding-bottom: 106px;
    font-weight: bold;
}
        .result_boxbor_left{ width: 100px}
        .result_boxbor td{ vertical-align: top}
        .result_boxbor_mid{ border:#000 2px solid; }
        .result_boxbor_mid td{padding: 0px; border-bottom: 2px solid #000;}
        .result_boxbor_mid tr:last-child td{padding: 0px; border-bottom: none;}
        .result_boxbor_mid tr td:first-child{padding: 0px; width: 107px; text-align: center;}
        .result_boxbor_mid tr td:nth-child(3){ width: 103px; text-align: center;}
        .result_boxbor_mid tr td:nth-child(5){ width: 173px; text-align: center;}
        .result_boxbor_right{ border-top:#000 2px solid; width: 100%;}
        .result_boxbor{ width: 100%;}
        .result_boxbor > tbody > tr > td:nth-child(1){ width: 10%;}
        .result_boxbor > tbody > tr > td:nth-child(2){ width: 40%;}
        .result_boxbor > tbody > tr > td:nth-child(3){ width: 55%;} 
        .result_boxres_pr{ width: 100%;}
        .result_boxres_pr h2 {margin: 0; padding: 0; text-decoration: underline; font-size: 16px; margin-bottom: 10px;}
        .data_boxbox{ font-size: 10px;}
        .data_boxbox tr td:nth-child(1) { width: 97px;}
        .data_boxbox tr td:nth-child(2) { width: 158px;}
        .data_boxbox tr td:nth-child(3) { width: 97px;}
        .data_boxbox tr td:nth-child(4) { width: 158px;}
        .scond_result{ border-top:#000 1px solid;}
        .bottom_borbox{ width: 100%:}
        .bottom_borbox { width: 100%; border-top: #000 2px solid;}
        .bottom_borbox td{ vertical-align: top;}
        .conformation_boxbox { border-left: #000 2px solid; border-right: #000 2px solid; padding: 15px; text-align: center; font-weight: bold; height: 95px;}
.sigbor_box{ width: 108%; float: left;}
.sigbor_box td:nth-child(1) { width: 46%;}
.sigbor_box_botm{ width: 104%; float: left;}
.sigbor_box_botm td:first-child{ width:47%;}
.totalValueVoucher {text-align: center; padding: 6px; border: #000 2px solid; border-top: none;}

	</style>

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

<!-- DataTable -->
<script type="text/javascript" charset="utf8" src="./resources/js/jquery.dataTables.min.js"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
<script type="text/javascript" charset="utf8" src="./resources/js/dataTables.jqueryui.js"></script>
<link rel="stylesheet" type="text/css" href="./resources/css/dataTables.jqueryui.css">
<link rel="stylesheet" type="text/css" href="./resources/css/jquery.dataTables.css">
<!-- DataTable -->

<!-- Custom CSS -->
<style type="text/css">
.reducepadding td {padding: 3px 0px !important;}
.reducepadding p {margin: 9px 0px !important;}
.reducepaddingtwo p {margin: 5px 0;}
.spaceprnt {list-style-type: none; width: 100%; float: left; margin: 0; padding: 0; text-align: center;}
.spaceprnt > li {list-style-type: none; padding: 0; margin-top: 0; margin-bottom: 0; margin-left: 20px; padding-bottom: 40px; display:inline-block; text-align:left; padding-top:40px}
.btnbox {display: none; float: right; background: #337ab7; border: none; border-radius: 5px; white-space: nowrap; color: #fff; width: 112px !important; padding: 5px 0; margin-top: 10px;}
</style>
<!-- end Custom CSS -->

</head>

<body>
	<div id="wrapper">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">
			<!-- /.row -->
			<input type="button" class="btn btn-default qr-button" onclick="printDiv('printableArea')" value="Print All" />
			<div id="printableArea">
			<!-- <button type="submit" class="btnbox" value="Details" style="width: 99px;"  onclick="refresh()">Reset Layout</button> -->
			<div class="row">
			<ul class="spaceprnt">
				<%
						User user = (User) session.getAttribute("login");
						List<Tuple> listTuple = (List<Tuple>) request.getAttribute("record");
						List<String> recordTotalInWordList = (List<String>) request.getAttribute("recordTotalInWordList");
						for (int index=0;index<recordTotalInWordList.size();index++) {
							Tuple tuple = listTuple.get(index);
							String recordTotalInWord = recordTotalInWordList.get(index);
							for(int i=0;i<3;i++)
			                {
					%>
				<li>
				<table  class="boxbor" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<table class="topbox_boxbor">
							<tr>
								<td class="boxbor_lt"><img src="./resources/logo/logobw.png"></td>
								<td class="boxbor_heading"><b>ICICI Bank Limited.</b><br />
										<span><b>INTEGRATED CURRENCY MANAGEMENT CENTRE, <c:out value="${icmcName}" /></b></span></td>
								<td class="boxbor_rt"> </td>					
							</tr>
							<tr>
								<td><%=tuple.get(2, String.class)%></td>
								<td>
									<table class="bg_seprate">
										<tr><td>CIT Agency:</td><!-- <td>ISS SDB Security Services Pvt Ltd.</td> --></tr>
									</table>
								</td>
								<td></td>					
							</tr>
						</table>
					</td>	
				</tr>
				<tr>
				<td >
		<table class="midbox_boxbor" cellpadding="0" cellspacing="0"  >
				<tr>
				<c:set var="now" value="<%=new java.util.Date()%>" />
					<td>Date: <fmt:formatDate type="date"
											pattern="dd-MM-yyyy" dateStyle="short" timeStyle="short"
											value="${now}" /></td>
					<td style="background: #e6e6e6">Name of CIT Custodian: </td>
					<td>Transaction Type: Payments</td>
					<td style="background: #e6e6e6"><table  cellpadding="0" cellspacing="0" class="rt_boxbor"><tr><td><strong>Vehicle No.:</strong></td><td>&nbsp;</td></tr></table></td>
				</tr>
				<tr>
					<td>Branch Name (Solid): </td>
					<td><%=tuple.get(0, String.class)%><%="_"%><%=tuple.get(1, String.class)%></td> 
					<td style="background: #e6e6e6"><strong>No. of Boxes:</strong></td>
					<td style="background: #e6e6e6"><strong>Sack Lock Nos:</strong></td>
				</tr>
			</table>

	</td>
	</tr>
	<tr>
	<td>
		<table class="result_boxbor" cellpadding="0" cellspacing="0" > 
				<tr>
					<td>
						<table class="result_boxbor_left">
								<tr><td>Notes</td></tr>
								<tr><td>Coins</td></tr>
						</table>
					</td>	
					<td>
						<table class="result_boxbor_mid" cellpadding="0" cellspacing="0">
							<tr>
									<td><b>Deno</b></td>
									<td></td>
									<td><b>Pieces</b></td>
									<td></td>
									<td><b>Value</b></td>
							</tr>
							<tr>
									<td>2000</td>
									<td>x</td>
									<td><%=tuple.get(12, BigDecimal.class).multiply(BigDecimal.valueOf(1000))%></td>
									<td>=</td>
									<td><%=BigDecimal.valueOf(2000)
						.multiply(tuple.get(12, BigDecimal.class).multiply(BigDecimal.valueOf(1000)))%></td>
							</tr>
							<tr>
									<td>1000</td>
									<td>x</td>
									<td><%=tuple.get(3, BigDecimal.class).multiply(BigDecimal.valueOf(1000))%></td>
									<td>=</td>
									<td><%=BigDecimal.valueOf(1000)
						.multiply(tuple.get(3, BigDecimal.class).multiply(BigDecimal.valueOf(1000)))%></td>
							</tr>
							<tr>
									<td>500</td>
									<td>x</td>
									<td><%=tuple.get(4, BigDecimal.class).multiply(BigDecimal.valueOf(1000))%></td>
									<td>=</td>
									<td><%=BigDecimal.valueOf(500)
						.multiply(tuple.get(4, BigDecimal.class).multiply(BigDecimal.valueOf(1000)))%></td>
							</tr>
							
							<tr>
									<td>200</td>
									<td>x</td>
									<td><%=tuple.get(17, BigDecimal.class).multiply(BigDecimal.valueOf(1000))%></td>
									<td>=</td>
									<td><%=BigDecimal.valueOf(200)
						.multiply(tuple.get(17, BigDecimal.class).multiply(BigDecimal.valueOf(1000)))%></td>
							</tr>
							
							<tr>
									<td>100</td>
									<td>x</td>
									<td><%=tuple.get(5, BigDecimal.class).multiply(BigDecimal.valueOf(1000))%></td>
									<td>=</td>
									<td><%=BigDecimal.valueOf(100)
						.multiply(tuple.get(5, BigDecimal.class).multiply(BigDecimal.valueOf(1000)))%></td>
							</tr>
							<tr>
									<td>50</td>
									<td>x</td>
									<td><%=tuple.get(6, BigDecimal.class).multiply(BigDecimal.valueOf(1000))%></td>
									<td>=</td>
									<td><%=BigDecimal.valueOf(50)
						.multiply(tuple.get(6, BigDecimal.class).multiply(BigDecimal.valueOf(1000)))%></td>
							</tr>
							<tr>
									<td>20</td>
									<td>x</td>
									<td><%=tuple.get(7, BigDecimal.class).multiply(BigDecimal.valueOf(1000))%></td>
									<td>=</td>
									<td><%=BigDecimal.valueOf(20)
						.multiply(tuple.get(7, BigDecimal.class).multiply(BigDecimal.valueOf(1000)))%></td>
							</tr>
							<tr>
									<td>10</td>
									<td>x</td>
									<td><%=tuple.get(8, BigDecimal.class).multiply(BigDecimal.valueOf(1000))%></td>
									<td>=</td>
									<td><%=BigDecimal.valueOf(10)
						.multiply(tuple.get(8, BigDecimal.class).multiply(BigDecimal.valueOf(1000)))%></td>
							</tr>
								<tr>
									<td>5</td>
									<td>x</td>
									<td><%=tuple.get(9, BigDecimal.class).multiply(BigDecimal.valueOf(1000))%></td>
									<td>=</td>
									<td><%=BigDecimal.valueOf(5)
						.multiply(tuple.get(9, BigDecimal.class).multiply(BigDecimal.valueOf(1000)))%></td>
							</tr>
							
							<tr>
									<td>1</td>
									<td>x</td>
									<td><%=tuple.get(10, BigDecimal.class).multiply(BigDecimal.valueOf(1000))%></td>
									<td>=</td>
									<td><%=BigDecimal.valueOf(1)
						.multiply(tuple.get(10, BigDecimal.class).multiply(BigDecimal.valueOf(1000)))%></td>
							</tr>
							<tr>
									<td>1</td>
									<td>x</td>
									<td><%=tuple.get(13, BigDecimal.class).multiply(BigDecimal.valueOf(2500))%></td>
									<td>=</td>
									<td><%=BigDecimal.valueOf(1)
						.multiply(tuple.get(13, BigDecimal.class).multiply(BigDecimal.valueOf(2500)))%></td>
							</tr>
							<tr>
									<td>2</td>
									<td>x</td>
									<td><%=tuple.get(14, BigDecimal.class).multiply(BigDecimal.valueOf(2500))%></td>
									<td>=</td>
									<td><%=BigDecimal.valueOf(2)
						.multiply(tuple.get(14, BigDecimal.class).multiply(BigDecimal.valueOf(2500)))%></td>
							</tr>
							<tr>
									<td>5</td>
									<td>x</td>
									<td><%=tuple.get(15, BigDecimal.class).multiply(BigDecimal.valueOf(2500))%></td>
									<td>=</td>
									<td><%=BigDecimal.valueOf(5)
						.multiply(tuple.get(15, BigDecimal.class).multiply(BigDecimal.valueOf(2500)))%></td>
							</tr>
							<tr>
									<td>10</td>
									<td>x</td>
									<td><%=tuple.get(16, BigDecimal.class).multiply(BigDecimal.valueOf(2000))%></td>
									<td>=</td>
									<td><%=BigDecimal.valueOf(10)
						.multiply(tuple.get(16, BigDecimal.class).multiply(BigDecimal.valueOf(2000)))%></td>
							</tr>
							<tr>
									<td>0.50</td>
									<td>x</td>
									<td>0</td>
									<td>=</td>
									<td>0</td>
							</tr>
								<tr>
									<td>TOTAL:</td>
									<td>x</td>
									<td><%=tuple.get(3, BigDecimal.class).multiply(BigDecimal.valueOf(1000))
						.add(tuple.get(4, BigDecimal.class).multiply(BigDecimal.valueOf(1000)))
						.add(tuple.get(17, BigDecimal.class).multiply(BigDecimal.valueOf(1000)))
						.add(tuple.get(12, BigDecimal.class).multiply(BigDecimal.valueOf(1000)))
						.add(tuple.get(5, BigDecimal.class).multiply(BigDecimal.valueOf(1000)))
						.add(tuple.get(6, BigDecimal.class).multiply(BigDecimal.valueOf(1000)))
						.add(tuple.get(7, BigDecimal.class).multiply(BigDecimal.valueOf(1000)))
						.add(tuple.get(8, BigDecimal.class).multiply(BigDecimal.valueOf(1000)))
						.add(tuple.get(9, BigDecimal.class).multiply(BigDecimal.valueOf(2000)))
						.add(tuple.get(13, BigDecimal.class).multiply(BigDecimal.valueOf(2500)))
						.add(tuple.get(14, BigDecimal.class).multiply(BigDecimal.valueOf(2500)))
						.add(tuple.get(15, BigDecimal.class).multiply(BigDecimal.valueOf(2500)))
						.add(tuple.get(16, BigDecimal.class).multiply(BigDecimal.valueOf(2000)))%></td>
									<td>=</td>
									<td><%=BigDecimal.valueOf(2000)
						.multiply(tuple.get(12, BigDecimal.class).multiply(BigDecimal.valueOf(1000)))
						.add(BigDecimal.valueOf(1000)
								.multiply(tuple.get(3, BigDecimal.class).multiply(BigDecimal.valueOf(1000))))
						.add(BigDecimal.valueOf(500)
								.multiply(tuple.get(4, BigDecimal.class).multiply(BigDecimal.valueOf(1000))))
						.add(BigDecimal.valueOf(200)
								.multiply(tuple.get(17, BigDecimal.class).multiply(BigDecimal.valueOf(1000))))
						.add(BigDecimal.valueOf(100)
								.multiply(tuple.get(5, BigDecimal.class).multiply(BigDecimal.valueOf(1000))))
						.add(BigDecimal.valueOf(50)
								.multiply(tuple.get(6, BigDecimal.class).multiply(BigDecimal.valueOf(1000))))
						.add(BigDecimal.valueOf(20)
								.multiply(tuple.get(7, BigDecimal.class).multiply(BigDecimal.valueOf(1000))))
						.add(BigDecimal.valueOf(10)
								.multiply(tuple.get(8, BigDecimal.class).multiply(BigDecimal.valueOf(1000))))
						.add(BigDecimal.valueOf(5)
								.multiply(tuple.get(9, BigDecimal.class).multiply(BigDecimal.valueOf(1000))))
						.add(BigDecimal.valueOf(1)
								.multiply(tuple.get(13, BigDecimal.class).multiply(BigDecimal.valueOf(2500))))
						.add(BigDecimal.valueOf(2)
								.multiply(tuple.get(14, BigDecimal.class).multiply(BigDecimal.valueOf(2500))))
						.add(BigDecimal.valueOf(5)
								.multiply(tuple.get(15, BigDecimal.class).multiply(BigDecimal.valueOf(2500))))
						.add(BigDecimal.valueOf(10)
								.multiply(tuple.get(16, BigDecimal.class).multiply(BigDecimal.valueOf(2000))))%></td>
							</tr>
							
						</table>

					</td>
					<td>
					<table class="result_boxbor_right">
						<tr>
							<td> 
							<table class="result_boxres_pr">
									<tr>
									<td>
										<h2><b>Handed Over By: (ICMC Official)</b></h2>
									</td>
									</tr>
									<tr>
									<td>
										<table class="data_boxbox">
											<tr><td>Name:</td><td><c:out value="${userName}" /></td><td>Name:</td><%-- <td><c:out value="${userName}" /></td> --%></tr>
											<tr><td>Emp Id:</td><td><c:out value="${userId}" /></td><td>Emp Id:</td><%-- <td><c:out value="${userId}" /></td> --%></tr>
											<tr><td>&nbsp;</td><td></td><td></td><td></td></tr>
											<tr><td>&nbsp;</td><td></td><td></td><td></td></tr>
											<tr><td></td><td></td><td>Date:</td><td><span><c:set var="now"
														value="<%=new java.util.Date()%>" /> <fmt:formatDate
														type="date" pattern="dd-MM-yyyy" dateStyle="short"
														timeStyle="short" value="${now}" /></span></td></tr>											
										</table>
										<table class="sigbor_box">
											<tr><td>Signature & Seal</td><td></td><td></td><td></td><td>Time:</td></tr>
										</table>
										</td>						
									</tr>	
							</table>
							</td>
							
							<td>

							<table class="result_boxres_pr scond_result" cellpadding="0" cellspacing="0">
									<tr>
									<td>
										<h2><b>Handed Over By: (CIT Custodian)</b></h2>
									</td>
									</tr>
									<tr>
									<td>
										<table class="data_boxbox">
											<tr><td>Name:</td><td></td><td></td><td></td></tr>
											<tr><td>Emp Id:</td><td></td><td></td><td></td></tr>
											<tr><td>&nbsp;</td><td></td><td></td><td></td></tr>
											<tr><td>&nbsp;</td><td></td><td></td><td></td></tr>
											<tr><td></td><td></td><td>Date:</td><td></td></tr>											
										</table>
										<table class="sigbor_box">
											<tr><td>Signature & Seal</td><td>Time:</td></tr>
										</table>
										</td>	
									</tr>	
							</table>
							</td>
							
							
						</tr>
						<tr>
							<td>
							<table class="result_boxres_pr scond_result">
									<tr>
									<td>
										<h2><b>Received By: (CIT Custodian)</b></h2>
									</td>
									</tr>
									<tr>
									<td>
										<table class="data_boxbox">
											<tr><td>Name:</td><%-- <td><c:out value="${userName}" /></td> --%><td></td><td></td></tr>
											<tr><td>Emp Id:</td><%-- <td><c:out value="${userId}" /></td> --%><td></td><td></td></tr>
											<tr><td>&nbsp;</td><td></td><td></td><td></td></tr>
											<tr><td>&nbsp;</td><td></td><td></td><td></td></tr>
											<tr><td></td><td></td><td>Date:</td><td><span><c:set var="now"
														value="<%=new java.util.Date()%>" /> <fmt:formatDate
														type="date" pattern="dd-MM-yyyy" dateStyle="short"
														timeStyle="short" value="${now}" /></span></td></tr>											
										</table>
										<table class="sigbor_box">
											<tr><td>Signature & Seal</td><td>Time:</td></tr>
										</table>
										</td>						
									</tr>	
							</table>
							</td>
							
							<td>
								<table class="result_boxres_pr scond_result" cellpadding="0" cellspacing="0">
									<tr>
									<td>
										<h2><b>Received By:(Branch Official)</b></h2>
									</td>
									</tr>
									<tr>
									<td>
										<table class="data_boxbox">
											<tr><td>Name:</td><td></td><td></td><td></td></tr>
											<tr><td>Emp Id:</td><td></td><td></td><td></td></tr>
											<tr><td>&nbsp;</td><td></td><td></td><td></td></tr>
											<tr><td>&nbsp;</td><td></td><td></td><td></td></tr>
											<tr><td></td><td></td><td>Date:</td><td></td></tr>											
										</table>
										<table class="sigbor_box">
											<tr><td>Signature & Seal</td><td>Time:</td></tr>
										</table>
										</td>	
									</tr>	
							</table>
							</td>
							
						</tr>
					</table>
					</td>
				</tr>
			</table>

	</td>
	</tr>

</table>
		<div class="totalValueVoucher">
			<label>Total in Words: </label>
			<span><%= recordTotalInWord %> only</span>
		</div>
				</li>
				
				<%}} %>
				<!-- /.panel -->
			<!-- /.col-lg-12 -->
			</ul>
		</div>

	</div></div>
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
		$(document).ready(function() {
			$('#tableValue').dataTable({

				"pagingType" : "full_numbers",

			});
		});
	</script>

</body>

</html>