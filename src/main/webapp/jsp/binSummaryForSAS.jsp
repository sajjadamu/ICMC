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
<body oncontextmenu="return false;">
<table border="1" align="center" class="currency-value" style="float:none">

<h4 style="margin: -25px 0 0 0;float: left;">Vault Balance</h4>
                                    <thead>
                                       <tr style="color: #fff;" bgcolor="#053C6D">
				<td width="40%" align="center">Denomination</td>
				<td width="40%" align="center">ATM</td>
				<td width="40%" align="center">Fresh</td>
				<td width="40%" align="center">Issuable</td>
			</tr>
                                    </thead>
                                    <tbody>
                                      <c:forEach var="summaryRecord" items="${summaryRecords}">
									     <tr>
									     <td width="20%"> ${summaryRecord.key}</td>
									     
												    <td width="20%">
												   		${summaryRecord.value.ATM}
												   	</td>
												    <td width="20%">
												   		${summaryRecord.value.fresh}
												   	</td>
												   	<td width="20%">
												   		${summaryRecord.value.issuable}
												   	</td>
									</tr>
			
			 					</c:forEach>
			 					<tr><td><jsp:include page="coinsSummaryForSAS.jsp" /></td></tr>
                                    </tbody>
                                    
                                </table>
                                
<script type="text/javascript" src="./js/htmlInjection.js"></script>                                
</body>
</html>