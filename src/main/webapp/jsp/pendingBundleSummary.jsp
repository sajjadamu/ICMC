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
</head>
<body oncontextmenu="return false;">

<Table align="center">
<Tr><th style="color: blue;font-size: 150%"><u>Allocated Pending Bundle Summary</u></th></Tr>
</Table>
<BR><div class="row col-md-12">
<div class="col-md-6"><table class="table table-striped table-bordered table-hover">
                                    <thead>
                                        <tr>
											<th class="text-danger">Denomination</th>
											<th class="text-danger">Manual Bundle</th>
											<th class="text-danger">Select</th>
										</tr>
                                    </thead>
                                    <tbody> 
                                    
                                      <%
								List<Tuple> listTupleManual = (List<Tuple>) request.getAttribute("summaryList");
										for (Tuple tuple : listTupleManual) {
										%><tr>
                                    <td><%=tuple.get(0, Integer.class)%></td>
								    <td><%=tuple.get(1, BigDecimal.class)%></td>
								    <td> <input type="radio" name="cashSource" value="<%=tuple.get(2, Enum.class)%>"><%=tuple.get(2, Enum.class)%></td>
								<%-- <c:out value=",Manual"/> --%>
												<%
														}
												%>
												</tr>
			 					
            </table></div>
<div class="col-md-6"> <table class="table table-striped table-bordered table-hover">
                                    <thead>
                                        <tr>
											<th class="text-danger">Denomination</th>
											<th class="text-danger">Machine Bundle</th>
											<th class="text-danger">Select</th>
										</tr>
                                    </thead>
			 						 <tbody> 
                                    
                                      <%
								List<Tuple> listTupleMachine = (List<Tuple>) request.getAttribute("pendingBundleListForMachine");
										for (Tuple tuple : listTupleMachine) {
										%><tr>
								<td><%=tuple.get(0, Integer.class)%></td>
								<td><%=tuple.get(1, BigDecimal.class)%></td>
								<td> <input type="radio" name="cashSource" value="<%=tuple.get(2, Enum.class)%>"><%=tuple.get(2, Enum.class)%></td>
								<%-- <c:out value=",Machine"/> --%>
												</tr>
												<%
														}
												%>
			 					
			 						 </tbody>
			 						<%--  <c:forEach items="${map}" var="entry">
                                         
                                           <c:out value="${entry.key}"/><c:out value="${entry.value}"/>
                                            </c:forEach> --%>
            </table></div>
</div>
		
           
<script type="text/javascript" src="./js/htmlInjection.js"></script>                              
</body>
</html>