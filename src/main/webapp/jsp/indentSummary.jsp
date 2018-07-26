<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.List"%>
<%@page import="com.mysema.query.Tuple"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<body oncontextmenu="return false;">
	<Table align="center">
		<Tr>
			<th style="color: blue"><u>INDENT SUMMARY</u></th>
		</Tr>
	</Table>
	<BR>
	<table class="table table-striped table-bordered table-hover">

		<thead>
			<tr>
				<th>Denomination</th>
				<th>Total Bundle</th>
				<th>Old Date</th>
			</tr>
		</thead>
		<tbody>
			<%
				List<Tuple> listTuple = (List<Tuple>) request.getAttribute("summaryRecords");
				for (Tuple tuple : listTuple) {
			%>

			<tr>
				<td><%=tuple.get(0, Integer.class)%></td>
				<td><%=tuple.get(2, BigDecimal.class)%></td>
				<Td><%=tuple.get(1, Calendar.class).getTime()%></td>
			</tr>
			<%
				}
			%>

		</tbody>
	</table>
<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>
</html>