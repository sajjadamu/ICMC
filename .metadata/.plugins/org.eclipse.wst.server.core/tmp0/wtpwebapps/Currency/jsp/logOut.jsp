<!DOCTYPE html>
<html lang="en">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<head>
<!-- Custom CSS -->
<link href="./resources/dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Bootstrap Core CSS -->
<link href="./resources/bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

<link rel="stylesheet" type="text/css" href="./resources/dist/css/style.css">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script></head>
<body oncontextmenu="return false;" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-160" data-genuitec-path="/Currency/src/main/webapp/jsp/logOut.jsp">

<!-- /.navbar-header -->

			<ul class="nav navbar-top-links navbar-right" id="headerRightsAction" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-160" data-genuitec-path="/Currency/src/main/webapp/jsp/logOut.jsp">
				<li>
					<!-- LoggedIn User Name & ICMC Name -->
					
					<%-- <div id="loggedInUser" style="">
						<% String loggedInUserName = (String) session.getAttribute("loggedInUserName");
						   String icmcName = (String) session.getAttribute("icmcName");		
						%>
						<%= "Welcome, "+loggedInUserName+", " %>
						<%= icmcName %>
					</div> --%>
					
					<div id="loggedInUser" style="">
						<c:out value="Welcome, ${loggedInUserName},  ${icmcName} "/>
					</div>
					
				</li>
				
				<!-- /.dropdown -->
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#"> <i class="fa fa-user fa-fw"></i>
						<i class="fa fa-caret-down"></i>
				</a>
					<ul class="dropdown-menu dropdown-user">
						<li><a href="././logout.html"><i class="fa fa-sign-out fa-fw"></i>
								Logout</a></li>
					</ul> <!-- /.dropdown-user --></li>
				<!-- /.dropdown -->
			</ul>
			<!-- /.navbar-top-links -->
<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>
</html>