<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>

<style>
.tbl-txt {
	text-align: right;
}
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
	
	//if(decimalNo.trim().length == 0)	
			$(tdObj).text(res);
	//else
		//	$(tdObj).text(res+"."+decimalNo);
}

function allConverter(){
	$('#converter table').find('tr').find('td:last').each(function(){ 
		converter_inNumeric($(this));
	});
}
</script>

<body oncontextmenu="return false;" onload="allConverter()">

	<Table align="center">
		<Tr>
			<th style="color: blue; font-size: 150%"><u>Bin Summary</u></th>
		</Tr>
	</Table>
	<BR>

	<div id="converter">
		<table class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					<th>DENOMINATION</th>
					<th>ATM</th>
					<th>FRESH</th>
					<th>ISSUABLE</th>
					<th>UNPROCESSED</th>
					<th>SOILED</th>
					<th>TOTAL</th>
					<th class="tbl-txt">VALUE</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="summaryRecord" items="${summaryRecords}">
					<tr>
						<td>${summaryRecord.key}</td>
						<td>${summaryRecord.value.ATM}</td>
						<td>${summaryRecord.value.fresh}</td>
						<td>${summaryRecord.value.issuable}</td>
						<td>${summaryRecord.value.unprocess}</td>
						<fmt:parseNumber var="soiled"
							value="${summaryRecord.value.soiled}" type="number" />
						<fmt:parseNumber var="mutilated"
							value="${summaryRecord.value.mutilated}" type="number" />
						<td>${soiled + mutilated}</td>
						<td>${summaryRecord.value.total}</td>
						<td class="tbl-txt">${summaryRecord.key*1000*summaryRecord.value.total}</td>
						<%-- <td>${summaryRecord.key*1000*summaryRecord.value.ATM + summaryRecord.key*1000*summaryRecord.value.fresh + 
													summaryRecord.key*1000*summaryRecord.value.issuable + summaryRecord.key*1000*summaryRecord.value.unprocess + 
													summaryRecord.key*1000*summaryRecord.value.soiled}</td> --%>
					</tr>
				</c:forEach>

				<tr>
					<td><b>Total</b></td>

					<td><c:set var="totalValue" value="${0}" /> <c:forEach
							begin="0" end="10" step="1" var="summaryRecord"
							items="${summaryRecords}">
							<c:set var="totalValue"
								value="${totalValue + summaryRecord.value.ATM}" />
						</c:forEach> <c:out value="${totalValue}" /></td>

					<td><c:set var="totalValue" value="${0}" /> <c:forEach
							begin="0" end="10" step="1" var="summaryRecord"
							items="${summaryRecords}">
							<c:set var="totalValue"
								value="${totalValue + summaryRecord.value.fresh}" />
						</c:forEach> <c:out value="${totalValue}" /></td>

					<td><c:set var="totalValue" value="${0}" /> <c:forEach
							begin="0" end="10" step="1" var="summaryRecord"
							items="${summaryRecords}">
							<c:set var="totalValue"
								value="${totalValue + summaryRecord.value.issuable}" />
						</c:forEach> <c:out value="${totalValue}" /></td>

					<td><c:set var="totalValue" value="${0}" /> <c:set
							var="totalValue" value="${0}" /> <c:forEach begin="0" end="10"
							step="1" var="summaryRecord" items="${summaryRecords}">
							<c:set var="totalValue"
								value="${totalValue + summaryRecord.value.unprocess}" />
						</c:forEach> <c:out value="${totalValue}" /></td>

					<td><c:set var="totalValue" value="${0}" /> <c:forEach
							begin="0" end="10" step="1" var="summaryRecord"
							items="${summaryRecords}">
							<c:set var="totalValue"
								value="${totalValue + summaryRecord.value.soiled}" />
						</c:forEach> <c:out value="${totalValue}" /></td>

					<td><c:set var="totalValue" value="${0}" /> <c:forEach
							begin="0" end="10" step="1" var="summaryRecord"
							items="${summaryRecords}">
							<c:set var="totalValue"
								value="${totalValue + summaryRecord.value.total}" />

						</c:forEach> <c:out value="${totalValue}" /></td>

					<td class="tbl-txt"><c:set var="totalValue" value="${0}" /> <c:forEach
							begin="0" end="10" step="1" var="summaryRecord"
							items="${summaryRecords}">
							<c:set var="totalValue"
								value="${totalValue + summaryRecord.key*1000*summaryRecord.value.total}" />
						</c:forEach> <c:out value="${totalValue}" /></td>

				</tr>
			</tbody>
		</table>
	</div>

	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>
</html>