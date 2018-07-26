<%@page import="java.math.BigDecimal"%>
<%@page import="com.mysema.query.Tuple"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>

<style>
.tbl-txt {text-align: right;}
</style>

<script type="text/javascript">

function converter_inNumeric(tdObj){
	var no = $(tdObj).text();
	no = no.toString();
	var actualNo = no.split(".")[0];
	var decimalNo = "";
	decimalNo = no.split(".")[1];
	
	var lastThree = actualNo.substring(actualNo.length-3);
	var otherNumbers = actualNo.substring(0, actualNo.length-3);
	if(otherNumbers != '')
	    lastThree = ',' + lastThree;
		var res = otherNumbers.replace(/\B(?=(\d{2})+(?!\d))/g, ",") + lastThree;
		$(tdObj).text(res);
}

function allConverter(){
	$('#converter table').find('tr').find('td:last').each(function(){ 
		converter_inNumeric($(this));
	});
}
</script>

<body oncontextmenu="return false;" onload="allConverter()">

<Table align="center">
<Tr><th style="color: blue;font-size: 150%"><u>Bin Coin Summary</u></th></Tr>
</Table>
<BR>
	<div id="converter">
		<table class="table table-striped table-bordered table-hover">
                                    <thead>
                                        <tr>
											<th>DENOMINATION</th>
											<th>NUMBER OF BAGS</th>
											<th class="tbl-txt">VALUE</th>
										</tr>
                                    </thead>
                                    <tbody>
                                    	<%!Integer denomination=0;
										   BigDecimal numberOfBags= new BigDecimal(0);
										   BigDecimal totalValueFor10 = new BigDecimal(0);
										   BigDecimal totalValueForOthers = new BigDecimal(0);
										%>
										
                                       <%
										List<Tuple> listTuple = (List<Tuple>) request.getAttribute("summaryListForCoins");
										for (Tuple tuple : listTuple) {
											denomination = tuple.get(1, Integer.class);
											numberOfBags = tuple.get(2, BigDecimal.class);
										%><tr>
											<td><%=tuple.get(1, Integer.class)%></td>
											<td><%=tuple.get(2, BigDecimal.class)%></td>
											<%if(denomination == 10){ 
												totalValueFor10 = numberOfBags.multiply(new BigDecimal(denomination).multiply(new BigDecimal(2000)));
											%>
												<%-- <td class="tbl-txt"><%=denomination*2000*numberOfBags.intValue()%></td> --%>
												<td class="tbl-txt"><%=totalValueFor10.setScale(3)%></td>
											<%}else{ 
												totalValueForOthers = numberOfBags.multiply(new BigDecimal(denomination).multiply(new BigDecimal(2500)));
											%>
												<%-- <td class="tbl-txt"><%=denomination*2500*numberOfBags.intValue()%></td> --%>
											<td class="tbl-txt"><%=totalValueForOthers.setScale(3)%></td>
											<%} %>
										 </tr>
											<%
												}
											%>
			 					
			 			</tbody>
            	</table>
           </div>
<script type="text/javascript" src="./js/htmlInjection.js"></script>                               
</body>
</html>