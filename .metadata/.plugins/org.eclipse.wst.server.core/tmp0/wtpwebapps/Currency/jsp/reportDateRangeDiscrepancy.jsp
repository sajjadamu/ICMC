<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ICICI: Report Date Range</title>
<link href="./resources/css/calendar.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.datetimepicker.css" />
<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>

<style type="text/css">
.region-con h1 {
	font-size: 22px;
	font-weight: 200;
	margin-top: 20px;
	margin-bottom: 10px;
}

.region-con-sec {
	float: left;
	width: 100%;
	border-top: 1px solid #eee;
	border-bottom: 1px solid #eee;
	padding-top: 13px;
}

.region-con-drop {
	width: 50%; /* text-align: right; */
	float: left;
	padding-right: 30px;
	box-sizing: border-box;
}

.region-con-drop span {
	font-size: 18px;
	margin-right: 18px;
}

.region-con-drop select {
	width: 30%;
	padding: 7px;
}

.region-drop {
	width: 100%;
	float: left;
}

.region-drop li {
	float: left;
	margin-right: 20px;
}

.region-drop li label {
	float: left;
	margin-right: 10px;
	line-height: 34px;
}

.region-drop span {
	float: left;
}

.DTTT.btn-group {
	width: 100%;
	float: right;
	margin: 10px 0;
}
</style>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-184"
	data-genuitec-path="/Currency/src/main/webapp/jsp/reportDateRangeDiscrepancy.jsp">

	<div class="region-con" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-184"
		data-genuitec-path="/Currency/src/main/webapp/jsp/reportDateRangeDiscrepancy.jsp">
		<form:form id="userPage" name="userPage" action="${dateRangeURL}"
			method="post" modelAttribute="reportDate" autocomplete="off">
			<div class="region-con-sec">
				<ul class="region-drop">
					<li><label>From Date</label> <span> <form:input
								type="text" path="fromDate" id="fromDate" name="fromDate"
								cssClass="form-control" />
					</span></li>
					<li><label>To Date</label> <span> <form:input
								type="text" path="toDate" id="toDate" name="toDate"
								cssClass="form-control" />
					</span></li>

					<li><label>Category</label> <span> <form:select
								path="normalOrSuspense" id="normalOrSuspense"
								name="normalOrSuspense" class="form-control deno-figure-select"
								style="width: 140px;">
								<option value="All">All</option>
								<option value="NORMAL">NORMAL</option>
								<option value="SUSPENSE">SUSPENSE</option>
							</form:select>
					</span></li>

					<li><label></label> <span>
							<button type="submit" class="btn btn-default" value="Details"
								style="width: 99px;">Search</button>
					</span></li>
				</ul>

			</div>
		</form:form>
	</div>
	<script src="./resources/js/jquery.datetimepicker.js"></script>
	<script>
		$('#fromDate').datetimepicker({
			format : 'Y-m-d',
		});
		
		$('#toDate').datetimepicker({
			format : 'Y-m-d',
		});
	</script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>
</html>