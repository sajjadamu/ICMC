<%@page import="java.util.ArrayList"%>
<%@page import="java.lang.reflect.Array"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.chest.currency.enums.DenominationType"%>
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
<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>
<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-170"
	data-genuitec-path="/Currency/src/main/webapp/jsp/pendingBundleSummary.jsp">

	<Table align="center" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-170"
		data-genuitec-path="/Currency/src/main/webapp/jsp/pendingBundleSummary.jsp">
		<Tr>
			<th style="color: blue; font-size: 150%"><u>Allocated Bundle
					Summary</u></th>
		</Tr>
	</Table>
	<BR>
	<table class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th>Denomination</th>
				<th>Manual Pending Bundle</th>
			</tr>
		</thead>
		<tbody>

			<%
								List<Tuple> listTupleManual = (List<Tuple>) request.getAttribute("summaryList");
										for (Tuple tuple : listTupleManual) {
										%><tr>
				<td><%=tuple.get(0, Integer.class)%></td>
				<td><%=tuple.get(1, BigDecimal.class)%> <%-- <c:out value=",Manual"/> --%>

				</td>
			</tr>
			<%
														}
												%>
		
	</table>
	<table class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th>Denomination</th>
				<th>Machine Pending Bundle</th>
			</tr>
		</thead>
		<tbody>

			<%
								List<Tuple> listTupleMachine = (List<Tuple>) request.getAttribute("pendingBundleListForMachine");
										for (Tuple tuple : listTupleMachine) {
										%><tr>
				<td><%=tuple.get(0, Integer.class)%></td>
				<td><%=tuple.get(1, BigDecimal.class)%> <%-- <c:out value=",Machine"/> --%>

				</td>
			</tr>
			<%
														}
												%>

		</tbody>
		<c:forEach items="${map}" var="entry">

			<c:out value="${entry.key}" />
			<c:out value="${entry.value}" />
		</c:forEach>
	</table>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>
</html>