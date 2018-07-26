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
</head>
<body oncontextmenu="return false;">

<Table align="center">
<Tr><th style="color: blue;font-size: 150%"><u>Bin Coin Summary</u></th></Tr>
</Table>
<BR>
		<table class="table table-striped table-bordered table-hover">
                                    <thead>
                                        <tr>
											<th>Denomination</th>
											<th>Number Of Bags</th>
											<th>Value</th>
										</tr>
                                    </thead>
                                    <tbody> 
                                    <%!Integer denomination;
										BigDecimal numberOfBags;
										%>
                                    
                                      <%
								List<Tuple> listTuple = (List<Tuple>) request.getAttribute("summaryListForCoins");
										for (Tuple tuple : listTuple) {
											denomination = tuple.get(1, Integer.class);
											numberOfBags = tuple.get(2, BigDecimal.class);
										%><tr>
								<td><%=tuple.get(1, Integer.class)%></td>
								<td><%=tuple.get(2, BigDecimal.class)%></td>
								<%if(denomination == 10){ %>
									<td><%=denomination*2000*numberOfBags.intValue()%></td>
								
								<%}else{ %>
									<td><%=denomination*2500*numberOfBags.intValue()%></td>
								<%} %>
								
												</tr>
												<%
														}
												%>
			 					
			 						 </tbody>
            </table>
<script type="text/javascript" src="./js/htmlInjection.js"></script>                              
</body>
</html>