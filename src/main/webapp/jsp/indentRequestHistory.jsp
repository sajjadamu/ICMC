<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html lang="en">

<head>
<link rel="shortcut icon" href="./resources/logo/favicon.ico"
	type="image/x-icon">
<script type="text/javascript" src="./js/jquery-1.12.0.min.js"></script>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<style>
form#ORV {
	width: 60%;
}

.frmsub input[type="button"] {
	padding: 1px 15px;
	margin-bottom: 20px;
}

.frmsub, .frmsubdel {
	margin-left: 16px;
}
</style>

<title>VMS : ORV Branch</title>

<script src="./resources/Currency/js/jquery.js"></script>
<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
<!-- Bootstrap Core CSS -->
<link
	href="./resources/bower_components/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- MetisMenu CSS -->
<link
	href="./resources/bower_components/metisMenu/dist/metisMenu.min.css"
	rel="stylesheet">

<!-- Custom CSS -->
<link href="./resources/dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Custom Fonts -->
<link
	href="./resources/bower_components/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css"
	href="./resources/dist/css/style.css">



<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css" />

<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.datetimepicker.css" />


<script type="text/javascript">
	function indentRequestHistory() {
		var fromDate = $('#fromDate').val();
		var toDate = $('#toDate').val();
		/* 	$.ajax({
		 type : "POST",
		 url : "././getHistory",
		 data : "fromDate=" + fromDate + "&toDate=" + toDate,
		 success : function(response) {
		 console.log("sajjad");
		 console.log(response);
		
		 },
		 error : function(e) {
		 alert('Region Error: ' + e);
		 }
		 }); */
		/* alert("featching Data from "+ fromDate +" To "+ toDate); */

		/* $('#divResults tr').remove();
		//$('table#test tr#3').remove();
		$('#divResults').append('<tr><td>' + " wdw" + '</td><td>' + " wdw" + '</td><td>' + " wdw" + '</td><td>' + " wdw" + '</td><td>' + " wdw" + '</td><td>' + " wdw" + '</td><td>' + " wdw" + '</td><td>' + " wdw" + '</td><td>' + " wdw" + '</td><td>' + " wdw" + '</td>/tr>');
		  alert("hello"); */

		//$('#dropDownId :selected').text();
	}
</script>

<style type="text/css">
.card {
	width: 100%;
	float: left;
	margin-top: 10px;
}

.card-body {
	-ms-flex: 1 1 auto;
	flex: 1 1 auto;
	padding: 1.25rem;
}

.card-body table {
	margin-bottom: 0;
}
</style>
</head>

<body>
	<link rel="stylesheet"
		href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

	<!-- Optional theme -->
	<link rel="stylesheet"
		href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
	<link rel="stylesheet"
		href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/css/bootstrap-datetimepicker.min.css" />

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.6/moment.min.js"></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>

<script type="text/javascript">
	function bundleDetails(str) {
		addHeaderJson();
		var srNumber = str;
		$.ajax({
			type : "POST",
			url : "././branchIndentBundleDetails",
			data : "srNumber=" + srNumber,
			success : function(response) {
				/* alert(response[0].id) */
				/* var id = response[0].id;
				alert(id) */
				
				
				var srNo = response[0].totalValueOfNotesRs200I;
				var res = JSON.stringify(response);
				
				var newStr = res.substring(1, res.length - 1); // Remove Array Brackets
				var data = newStr.split(",");
				var html = "";

				var issuable2000 = response[0].totalValueOfNotesRs2000I
				var atm2000 = response[0].totalValueOfNotesRs2000A
				var fresh2000 = response[0].totalValueOfNotesRs2000F
				var issuable500 = response[0].totalValueOfNotesRs500I
				var atm500 = response[0].totalValueOfNotesRs500A
				var fresh500 = response[0].totalValueOfNotesRs500F
				var issuable200 = response[0].totalValueOfNotesRs200I
				var atm200 = response[0].totalValueOfNotesRs200A
				var fresh200 = response[0].totalValueOfNotesRs200F
				var issuable100 = response[0].totalValueOfNotesRs100I
				var atm100 = response[0].totalValueOfNotesRs100A
				var fresh100 = response[0].totalValueOfNotesRs100F
				var issuable50 = response[0].totalValueOfNotesRs50I
				var fresh50 = response[0].totalValueOfNotesRs50F
				var issuable20 = response[0].totalValueOfNotesRs20I
				var fresh20 = response[0].totalValueOfNotesRs20F
				var issuable10 = response[0].totalValueOfNotesRs10I
				var fresh10 = response[0].totalValueOfNotesRs10F;
				var issuable5 = response[0].totalValueOfNotesRs5I;
				var fresh5 = response[0].totalValueOfNotesRs5F;
				var issuable2 = response[0].totalValueOfNotesRs2I;
				var fresh2 = response[0].totalValueOfNotesRs2F;
				var issuable1 = response[0].totalValueOfNotesRs1I;
				var fresh1 = response[0].totalValueOfNotesRs1F;
				
				var totalSum2000=1000*2000*issuable2000+1000*2000*fresh2000+1000*2000*atm2000; 
				var totalSum500=1000*500*issuable500+1000*500*fresh500+1000*500*atm500;
				var totalSum200=1000*200*issuable200+1000*200*fresh200+1000*200*atm200;
				var totalSum100=1000*100*issuable100+1000*100*fresh100+1000*100*atm100;
				var totalSum50=1000*50*issuable50+1000*50*fresh50;
				var totalSum20=1000*20*issuable20+1000*20*fresh20;
				var totalSum10=1000*10*issuable10+1000*10*fresh10;
				var totalSum5=1000*5*issuable5+1000*5*fresh5;
				var totalSum2=1000*2*issuable2+1000*2*fresh2;
				var totalSum1=1000*1*issuable1+1000*1*fresh1;
				
				var total2000 = issuable2000 + fresh2000 + atm2000;
				var total500 = issuable500 + fresh500 + atm500;
				var total200 = issuable200 + fresh200 + atm200;
				var total100 = issuable100 + fresh100 + atm100;
				var total50 = issuable50 + fresh50;
				var total20 = issuable20 + fresh20;
				var total10 = issuable10 + fresh10;
				var total5 = issuable5 + fresh5;
				var total2 = issuable2 + fresh2;
				var total1 = issuable1 + fresh1;
				
				
				var sumOfTotalBulbles= total2000 + total500 + total200 + total100 + total50 + total20 + total10 + total5 + total1;
				
				var totalSumOfAmount = totalSum2000 + totalSum500 + totalSum200 + totalSum100 + totalSum50 + totalSum20 + totalSum5 + totalSum2 + totalSum1;
				
				html += "<table style='width:100%;text-align: center;' border='1'>";
				html += "<tr><td>DENOMINATION</td> " + "<td>ISSUABLE</td> "
						+ "<td>ATM </td>" + "<td>FRESH </td>"+"<td> Bundles </td>"+"<td> Amount </td></tr>";
					
						if(issuable2000 > 0 || fresh2000 > 0 ||  atm2000 > 0 ){
				html += "<tr><td>2000 </td>" + "<td>" + issuable2000 + "</td> "
						+ "<td>" + atm2000 + "</td>" + "<td>" + fresh2000
						+ "</td>"+ "<td>" + total2000 + "</td>"+"<td>" + totalSum2000 + "</td>";
				         }
						if(issuable500 > 0 || fresh500 > 0 ||  atm500 > 0 ){
				html += "<tr><td>500 </td>" + "<td>" + issuable500 + "</td> "
						+ "<td>" + atm500 + "</td>" + "<td>" + fresh500
						+ "</td>"+ "<td>" + total500 + "</td>"+"<td>" + totalSum500 + "</td>";
						}
						
						if(issuable200 > 0 || fresh200 > 0 ||  atm200 > 0 ){
				html += "<tr><td>200 </td>" + "<td>" + issuable200 + "</td> "
						+ "<td>" + atm200 + "</td>" + "<td>" + fresh200
						+ "</td>"+ "<td>" + total200 + "</td>"+"<td>" + totalSum200 + "</td>";
						}
						
						if(issuable100 > 0 || fresh100 > 0 ||  atm100 > 0 ){
				html += "<tr><td>100 </td>" + "<td>" + issuable100 + "</td> "
						+ "<td>" + atm100 + "</td>" + "<td>" + fresh100
						+ "</td>"+ "<td>" + total100 + "</td>"+"<td>" + totalSum100 + "</td>";
						}
						
						if(issuable50 > 0 || fresh50 > 0 ){
				html += "<tr><td>50 </td>" + "<td>" + issuable50 + "</td> "
						+ "<td>" + "0" + "</td>" + "<td>" + fresh50 + "</td>"+ "<td>" + total50 + "</td>"+"<td>" + totalSum50 + "</td>";
						}
						
						if(issuable20 > 0 || fresh20 > 0  ){
				html += "<tr><td>20 </td>" + "<td>" + issuable20 + "</td> "
						+ "<td>" + "0" + "</td>" + "<td>" + fresh20 + "</td>"+ "<td>" + total20 + "</td>"+"<td>" + totalSum20 + "</td>";
						}
						if(issuable10 > 0 || fresh10 > 0  ){
				html += "<tr><td>10 </td>" + "<td>" + issuable10 + "</td> "
						+ "<td>" + "0" + "</td>" + "<td>" + fresh10 + "</td>"+ "<td>" + total10 + "</td>"+"<td>" + totalSum10 + "</td>";
						}
						if(issuable5 > 0 || fresh5 > 0 ){
				html += "<tr><td>5 </td>" + "<td>" + issuable5 + "</td> "
						+ "<td>" + "0" + "</td>" + "<td>" + fresh5 + "</td>"+ "<td>" + total5 + "</td>"+"<td>" + totalSum5 + "</td>";
						}
						if(issuable2 > 0 || fresh2 > 0  ){
				html += "<tr><td>2 </td>" + "<td>" + issuable2 + "</td> "
						+ "<td>" + "0" + "</td>" + "<td>" + fresh2 + "</td>"+ "<td>" + total2 + "</td>"+"<td>" + totalSum2 + "</td>";
						}
						if(issuable1 > 0 || fresh1 > 0 ){
				html += "<tr><td>1 </td>" + "<td>" + issuable1 + "</td> "
						+ "<td>" + "0" + "</td>" + "<td>" + fresh1 + "</td>"+ "<td>" + total1 + "</td>"+"<td>" + totalSum1 + "</td>";
						}
				html += "<div class='p-3 mb-2 bg-primary text-white text-center'>Requested Amount Details</div><div class='cal-sm text-danger'>Total Bundles = "+ sumOfTotalBulbles + "</div><div class='cal-sm text-success'>   Total Amount = "+totalSumOfAmount+"</div>";
				//html +="<div class='row'><div class='col-sm-5 text-danger'>Total Bundles = "+ sumOfTotalBulbles + "</div><div class='col-sm-7 text-success'> Total Amount = "+totalSumOfAmount+"</div></div></div>";
				/* var data = capacity - receiveBundle;
				html += "Available :" + data + "</br>"; */
				
				$('#modal-body').html(html);
				$('#myModal').modal('show');
			},
			error : function(e) {
				alert(e)
			}
		});
	}
	
	
	
	
	
	function approvedBundleDetails(str) {
		addHeaderJson();
		var srNumber = str;
		$.ajax({
			type : "POST",
			url : "././branchIndentApprovedBundleDetails",
			data : "srNumber=" + srNumber,
			success : function(response) {
				/* alert(response[0].id) */
				/* var id = response[0].id;
				alert(id) */
				
				var srNo = response[0].totalValueOfNotesRs200I;
				var res = JSON.stringify(response);
				var newStr = res.substring(1, res.length - 1); // Remove Array Brackets
				var data = newStr.split(",");
				var html = "";
				
				var issuable2000 = response[0].totalApprovedValueOfNotesRs2000I
				var atm2000 = response[0].totalApprovedValueOfNotesRs2000A
				var fresh2000 = response[0].totalApprovedValueOfNotesRs2000F
				var issuable500 = response[0].totalApprovedValueOfNotesRs500I
				var atm500 = response[0].totalApprovedValueOfNotesRs500A
				var fresh500 = response[0].totalApprovedValueOfNotesRs500F
				var issuable200 = response[0].totalApprovedValueOfNotesRs200I
				var atm200 = response[0].totalApprovedValueOfNotesRs200A
				var fresh200 = response[0].totalApprovedValueOfNotesRs200F
				var issuable100 = response[0].totalApprovedValueOfNotesRs100I
				var atm100 = response[0].totalApprovedValueOfNotesRs100A
				var fresh100 = response[0].totalApprovedValueOfNotesRs100F
				var issuable50 = response[0].totalApprovedValueOfNotesRs50I
				var fresh50 = response[0].totalApprovedValueOfNotesRs50F
				var issuable20 = response[0].totalApprovedValueOfNotesRs20I
				var fresh20 = response[0].totalApprovedValueOfNotesRs20F
				var issuable10 = response[0].totalApprovedValueOfNotesRs10I
				var fresh10 = response[0].totalApprovedValueOfNotesRs10F
				var issuable5 = response[0].totalApprovedValueOfNotesRs5I
				var fresh5 = response[0].totalApprovedValueOfNotesRs5F
				var issuable2 = response[0].totalApprovedValueOfNotesRs2I
				var fresh2 = response[0].totalApprovedValueOfNotesRs2F
				var issuable1 = response[0].totalApprovedValueOfNotesRs1I
				var fresh1 = response[0].totalApprovedValueOfNotesRs1F

				var totalSum2000=1000*2000*issuable2000+1000*2000*fresh2000+1000*2000*atm2000; 
				var totalSum500=1000*500*issuable500+1000*500*fresh500+1000*500*atm500;
				var totalSum200=1000*200*issuable200+1000*200*fresh200+1000*200*atm200;
				var totalSum100=1000*100*issuable100+1000*100*fresh100+1000*100*atm100;
				var totalSum50=1000*50*issuable50+1000*50*fresh50;
				var totalSum20=1000*20*issuable20+1000*20*fresh20;
				var totalSum10=1000*10*issuable10+1000*10*fresh10;
				var totalSum5=1000*5*issuable5+1000*5*fresh5;
				var totalSum2=1000*2*issuable2+1000*2*fresh2;
				var totalSum1=1000*1*issuable1+1000*1*fresh1;
				
				var total2000 = issuable2000 + fresh2000 + atm2000;
				var total500 = issuable500 + fresh500 + atm500;
				var total200 = issuable200 + fresh200 + atm200;
				var total100 = issuable100 + fresh100 + atm100;
				var total50 = issuable50 + fresh50;
				var total20 = issuable20 + fresh20;
				var total10 = issuable10 + fresh10;
				var total5 = issuable5 + fresh5;
				var total2 = issuable2 + fresh2;
				var total1 = issuable1 + fresh1;
				

				var sumOfTotalBulbles= total2000 + total500 + total200 + total100 + total50 + total20 + total10 + total5 + total1;
				
				var totalSumOfAmount = totalSum2000 + totalSum500 + totalSum200 + totalSum100 + totalSum50 + totalSum20 + totalSum5 + totalSum2 + totalSum1;
				
				html += "<table style='width:100%;text-align: center;' border='1'>";
				html += "<tr><td>DENOMINATION</td> " + "<td>ISSUABLE</td> "
						+ "<td>ATM </td>" + "<td>FRESH </td>"+"<td> Bundles </td>"+"<td> Amount </td></tr>";
					
						if(issuable2000 > 0 || fresh2000 > 0 ||  atm2000 > 0 ){
				html += "<tr><td>2000 </td>" + "<td>" + issuable2000 + "</td> "
						+ "<td>" + atm2000 + "</td>" + "<td>" + fresh2000
						+ "</td>"+ "<td>" + total2000 + "</td>"+"<td>" + totalSum2000 + "</td>";
				         }
						if(issuable500 > 0 || fresh500 > 0 ||  atm500 > 0 ){
				html += "<tr><td>500 </td>" + "<td>" + issuable500 + "</td> "
						+ "<td>" + atm500 + "</td>" + "<td>" + fresh500
						+ "</td>"+ "<td>" + total500 + "</td>"+"<td>" + totalSum500 + "</td>";
						}
						
						if(issuable200 > 0 || fresh200 > 0 ||  atm200 > 0 ){
				html += "<tr><td>200 </td>" + "<td>" + issuable200 + "</td> "
						+ "<td>" + atm200 + "</td>" + "<td>" + fresh200
						+ "</td>"+ "<td>" + total200 + "</td>"+"<td>" + totalSum200 + "</td>";
						}
						
						if(issuable100 > 0 || fresh100 > 0 ||  atm100 > 0 ){
				html += "<tr><td>100 </td>" + "<td>" + issuable100 + "</td> "
						+ "<td>" + atm100 + "</td>" + "<td>" + fresh100
						+ "</td>"+ "<td>" + total100 + "</td>"+"<td>" + totalSum100 + "</td>";
						}
						
						if(issuable50 > 0 || fresh50 > 0 ){
				html += "<tr><td>50 </td>" + "<td>" + issuable50 + "</td> "
						+ "<td>" + "0" + "</td>" + "<td>" + fresh50 + "</td>"+ "<td>" + total50 + "</td>"+"<td>" + totalSum50 + "</td>";
						}
						
						if(issuable20 > 0 || fresh20 > 0  ){
				html += "<tr><td>20 </td>" + "<td>" + issuable20 + "</td> "
						+ "<td>" + "0" + "</td>" + "<td>" + fresh20 + "</td>"+ "<td>" + total20 + "</td>"+"<td>" + totalSum20 + "</td>";
						}
						if(issuable10 > 0 || fresh10 > 0  ){
				html += "<tr><td>10 </td>" + "<td>" + issuable10 + "</td> "
						+ "<td>" + "0" + "</td>" + "<td>" + fresh10 + "</td>"+ "<td>" + total10 + "</td>"+"<td>" + totalSum10 + "</td>";
						}
						if(issuable5 > 0 || fresh5 > 0 ){
				html += "<tr><td>5 </td>" + "<td>" + issuable5 + "</td> "
						+ "<td>" + "0" + "</td>" + "<td>" + fresh5 + "</td>"+ "<td>" + total5 + "</td>"+"<td>" + totalSum5 + "</td>";
						}
						if(issuable2 > 0 || fresh2 > 0  ){
				html += "<tr><td>2 </td>" + "<td>" + issuable2 + "</td> "
						+ "<td>" + "0" + "</td>" + "<td>" + fresh2 + "</td>"+ "<td>" + total2 + "</td>"+"<td>" + totalSum2 + "</td>";
						}
						if(issuable1 > 0 || fresh1 > 0 ){
				html += "<tr><td>1 </td>" + "<td>" + issuable1 + "</td> "
						+ "<td>" + "0" + "</td>" + "<td>" + fresh1 + "</td>"+ "<td>" + total1 + "</td>"+"<td>" + totalSum1 + "</td>";
						}
				html += "<div class='p-3 mb-2 bg-primary text-white text-center'>Approved Amount Details</div><div class='cal-sm text-danger'>Total Bundles = "+ sumOfTotalBulbles + "</div><div class='cal-sm text-success'>   Total Amount = "+totalSumOfAmount+"</div>";
				//html +="<div class='row'><div class='col-sm-5 text-danger'>Total Bundles = "+ sumOfTotalBulbles + "</div><div class='col-sm-7 text-success'> Total Amount = "+totalSumOfAmount+"</div></div></div>";
				/* var data = capacity - receiveBundle;
				html += "Available :" + data + "</br>"; */
				
				$('#modal-body').html(html);
				$('#myModal').modal('show');
			},
			error : function(e) {
				alert(e)
			}
		})
	}
	
	
	function requestApproveDetails(str) {
		addHeaderJson();
		var srNumber = str;
		$.ajax({
			type : "POST",
			url : "././branchIndentBundleDetails",
			data : "srNumber=" + srNumber,
			success : function(response) {
				/* alert(response[0].id) */
				/* var id = response[0].id;
				alert(id) */
				
				
				var srNo = response[0].totalValueOfNotesRs200I;
				var res = JSON.stringify(response);
				
				var newStr = res.substring(1, res.length - 1); // Remove Array Brackets
				var data = newStr.split(",");
				var html = "";

				var issuable2000 = response[0].totalValueOfNotesRs2000I
				var atm2000 = response[0].totalValueOfNotesRs2000A
				var fresh2000 = response[0].totalValueOfNotesRs2000F
				var issuable500 = response[0].totalValueOfNotesRs500I
				var atm500 = response[0].totalValueOfNotesRs500A
				var fresh500 = response[0].totalValueOfNotesRs500F
				var issuable200 = response[0].totalValueOfNotesRs200I
				var atm200 = response[0].totalValueOfNotesRs200A
				var fresh200 = response[0].totalValueOfNotesRs200F
				var issuable100 = response[0].totalValueOfNotesRs100I
				var atm100 = response[0].totalValueOfNotesRs100A
				var fresh100 = response[0].totalValueOfNotesRs100F
				var issuable50 = response[0].totalValueOfNotesRs50I
				var fresh50 = response[0].totalValueOfNotesRs50F
				var issuable20 = response[0].totalValueOfNotesRs20I
				var fresh20 = response[0].totalValueOfNotesRs20F
				var issuable10 = response[0].totalValueOfNotesRs10I
				var fresh10 = response[0].totalValueOfNotesRs10F;
				var issuable5 = response[0].totalValueOfNotesRs5I;
				var fresh5 = response[0].totalValueOfNotesRs5F;
				var issuable2 = response[0].totalValueOfNotesRs2I;
				var fresh2 = response[0].totalValueOfNotesRs2F;
				var issuable1 = response[0].totalValueOfNotesRs1I;
				var fresh1 = response[0].totalValueOfNotesRs1F;
				
				var totalSum2000=1000*2000*issuable2000+1000*2000*fresh2000+1000*2000*atm2000; 
				var totalSum500=1000*500*issuable500+1000*500*fresh500+1000*500*atm500;
				var totalSum200=1000*200*issuable200+1000*200*fresh200+1000*200*atm200;
				var totalSum100=1000*100*issuable100+1000*100*fresh100+1000*100*atm100;
				var totalSum50=1000*50*issuable50+1000*50*fresh50;
				var totalSum20=1000*20*issuable20+1000*20*fresh20;
				var totalSum10=1000*10*issuable10+1000*10*fresh10;
				var totalSum5=1000*5*issuable5+1000*5*fresh5;
				var totalSum2=1000*2*issuable2+1000*2*fresh2;
				var totalSum1=1000*1*issuable1+1000*1*fresh1;
				
				var total2000 = issuable2000 + fresh2000 + atm2000;
				var total500 = issuable500 + fresh500 + atm500;
				var total200 = issuable200 + fresh200 + atm200;
				var total100 = issuable100 + fresh100 + atm100;
				var total50 = issuable50 + fresh50;
				var total20 = issuable20 + fresh20;
				var total10 = issuable10 + fresh10;
				var total5 = issuable5 + fresh5;
				var total2 = issuable2 + fresh2;
				var total1 = issuable1 + fresh1;
				
				
				var sumOfTotalBulbles= total2000 + total500 + total200 + total100 + total50 + total20 + total10 + total5 + total1;
				
				var totalSumOfAmount = totalSum2000 + totalSum500 + totalSum200 + totalSum100 + totalSum50 + totalSum20 + totalSum5 + totalSum2 + totalSum1;
				
				
				var issuable2000ra = response[0].totalApprovedValueOfNotesRs2000I
				var atm2000ra = response[0].totalApprovedValueOfNotesRs2000A
				var fresh2000ra = response[0].totalApprovedValueOfNotesRs2000F
				var issuable500ra = response[0].totalApprovedValueOfNotesRs500I
				var atm500ra = response[0].totalApprovedValueOfNotesRs500A
				var fresh500ra = response[0].totalApprovedValueOfNotesRs500F
				var issuable200ra = response[0].totalApprovedValueOfNotesRs200I
				var atm200ra = response[0].totalApprovedValueOfNotesRs200A
				var fresh200ra = response[0].totalApprovedValueOfNotesRs200F
				var issuable100ra = response[0].totalApprovedValueOfNotesRs100I
				var atm100ra = response[0].totalApprovedValueOfNotesRs100A
				var fresh100ra = response[0].totalApprovedValueOfNotesRs100F
				var issuable50ra = response[0].totalApprovedValueOfNotesRs50I
				var fresh50ra = response[0].totalApprovedValueOfNotesRs50F
				var issuable20ra = response[0].totalApprovedValueOfNotesRs20I
				var fresh20ra = response[0].totalApprovedValueOfNotesRs20F
				var issuable10ra = response[0].totalApprovedValueOfNotesRs10I
				var fresh10ra = response[0].totalApprovedValueOfNotesRs10F
				var issuable5ra = response[0].totalApprovedValueOfNotesRs5I
				var fresh5ra = response[0].totalApprovedValueOfNotesRs5F
				var issuable2ra = response[0].totalApprovedValueOfNotesRs2I
				var fresh2ra = response[0].totalApprovedValueOfNotesRs2F
				var issuable1ra = response[0].totalApprovedValueOfNotesRs1I
				var fresh1ra = response[0].totalApprovedValueOfNotesRs1F

				var totalSum2000ra=1000*2000*issuable2000ra+1000*2000*fresh2000ra+1000*2000*atm2000ra; 
				var totalSum500ra=1000*500*issuable500ra+1000*500*fresh500ra+1000*500*atm500ra;
				var totalSum200ra=1000*200*issuable200ra+1000*200*fresh200ra+1000*200*atm200ra;
				var totalSum100ra=1000*100*issuable100ra+1000*100*fresh100ra+1000*100*atm100ra;
				var totalSum50ra=1000*50*issuable50ra+1000*50*fresh50ra;
				var totalSum20ra=1000*20*issuable20ra+1000*20*fresh20ra;
				var totalSum10ra=1000*10*issuable10ra+1000*10*fresh10ra;
				var totalSum5ra=1000*5*issuable5ra+1000*5*fresh5ra;
				var totalSum2ra=1000*2*issuable2ra+1000*2*fresh2ra;
				var totalSum1ra=1000*1*issuable1ra+1000*1*fresh1ra;
				
				var total2000ra = issuable2000ra + fresh2000ra + atm2000ra;
				var total500ra = issuable500ra + fresh500ra + atm500ra;
				var total200ra = issuable200ra + fresh200ra + atm200ra;
				var total100ra = issuable100ra + fresh100ra + atm100ra;
				var total50ra = issuable50ra + fresh50ra;
				var total20ra = issuable20ra + fresh20ra;
				var total10ra = issuable10ra + fresh10ra;
				var total5ra = issuable5ra + fresh5ra;
				var total2ra = issuable2ra + fresh2ra;
				var total1ra = issuable1ra + fresh1ra;
				

				var sumOfTotalBulblesra= total2000ra + total500ra + total200ra + total100ra + total50ra + total20ra + total10ra + total5ra + total1ra;
				
				var totalSumOfAmountra = totalSum2000ra + totalSum500ra + totalSum200ra + totalSum100ra + totalSum50ra + totalSum20ra + totalSum5ra + totalSum2ra + totalSum1ra;

				
				
				html += "<table style='width:100%;text-align: center;' border='1'>";
				html += "<tr><td>DENOMINATION</td> " + "<td>ISSUABLE</td> "
						+ "<td>ATM </td>" + "<td>FRESH </td>"+"<td> Bundles </td>"+"<td> Amount </td></tr>";
					
						if(issuable2000 > 0 || fresh2000 > 0 ||  atm2000 > 0 ){
				html += "<tr><td>2000 </td>" + "<td>" + issuable2000 + "</td> "
						+ "<td>" + atm2000 + "</td>" + "<td>" + fresh2000
						+ "</td>"+ "<td>" + total2000 + "</td>"+"<td>" + totalSum2000 + "</td>";
				         }
						if(issuable500 > 0 || fresh500 > 0 ||  atm500 > 0 ){
				html += "<tr><td>500 </td>" + "<td>" + issuable500 + "</td> "
						+ "<td>" + atm500 + "</td>" + "<td>" + fresh500
						+ "</td>"+ "<td>" + total500 + "</td>"+"<td>" + totalSum500 + "</td>";
						}
						
						if(issuable200 > 0 || fresh200 > 0 ||  atm200 > 0 ){
				html += "<tr><td>200 </td>" + "<td>" + issuable200 + "</td> "
						+ "<td>" + atm200 + "</td>" + "<td>" + fresh200
						+ "</td>"+ "<td>" + total200 + "</td>"+"<td>" + totalSum200 + "</td>";
						}
						
						if(issuable100 > 0 || fresh100 > 0 ||  atm100 > 0 ){
				html += "<tr><td>100 </td>" + "<td>" + issuable100 + "</td> "
						+ "<td>" + atm100 + "</td>" + "<td>" + fresh100
						+ "</td>"+ "<td>" + total100 + "</td>"+"<td>" + totalSum100 + "</td>";
						}
						
						if(issuable50ra > 0 || fresh50ra > 0 ){
				html += "<tr><td>50 </td>" + "<td>" + issuable50ra + "</td> "
						+ "<td>" + "0" + "</td>" + "<td>" + fresh50ra + "</td>"+ "<td>" + total50ra + "</td>"+"<td>" + totalSum50ra + "</td>";
						}
						
						if(issuable20 > 0 || fresh20 > 0  ){
				html += "<tr><td>20 </td>" + "<td>" + issuable20 + "</td> "
						+ "<td>" + "0" + "</td>" + "<td>" + fresh20 + "</td>"+ "<td>" + total20 + "</td>"+"<td>" + totalSum20 + "</td>";
						}
						if(issuable10 > 0 || fresh10 > 0  ){
				html += "<tr><td>10 </td>" + "<td>" + issuable10 + "</td> "
						+ "<td>" + "0" + "</td>" + "<td>" + fresh10 + "</td>"+ "<td>" + total10 + "</td>"+"<td>" + totalSum10 + "</td>";
						}
						if(issuable5 > 0 || fresh5 > 0 ){
				html += "<tr><td>5 </td>" + "<td>" + issuable5 + "</td> "
						+ "<td>" + "0" + "</td>" + "<td>" + fresh5 + "</td>"+ "<td>" + total5 + "</td>"+"<td>" + totalSum5 + "</td>";
						}
						if(issuable2 > 0 || fresh2 > 0  ){
				html += "<tr><td>2 </td>" + "<td>" + issuable2 + "</td> "
						+ "<td>" + "0" + "</td>" + "<td>" + fresh2 + "</td>"+ "<td>" + total2 + "</td>"+"<td>" + totalSum2 + "</td>";
						}
						if(issuable1 > 0 || fresh1 > 0 ){
				html += "<tr><td>1 </td>" + "<td>" + issuable1 + "</td> "
						+ "<td>" + "0" + "</td>" + "<td>" + fresh1 + "</td>"+ "<td>" + total1 + "</td>"+"<td>" + totalSum1 + "</td>";
						}
				html += "<div class='p-3 mb-2 bg-primary text-white text-center'>Requested Amount Details</div><div class='text-danger'>Total Bundles = "+ sumOfTotalBulbles + "</div><div class='text-success'>   Total Amount = "+totalSumOfAmount+"</div>";
				
				
				
				html += "<table style='width:100%;text-align: center;' border='1'>";
				html += "<tr><td>DENOMINATION</td> " + "<td>ISSUABLE</td> "
						+ "<td>ATM </td>" + "<td>FRESH </td>"+"<td> Bundles </td>"+"<td> Amount </td></tr>";
					
						if(issuable2000ra > 0 || fresh2000ra > 0 ||  atm2000ra > 0 ){
				html += "<tr><td>2000 </td>" + "<td>" + issuable2000ra + "</td> "
						+ "<td>" + atm2000ra + "</td>" + "<td>" + fresh2000ra
						+ "</td>"+ "<td>" + total2000ra + "</td>"+"<td>" + totalSum2000ra + "</td>";
				         }
						if(issuable500ra > 0 || fresh500ra > 0 ||  atm500ra > 0 ){
				html += "<tr><td>500 </td>" + "<td>" + issuable500ra + "</td> "
						+ "<td>" + atm500ra + "</td>" + "<td>" + fresh500ra
						+ "</td>"+ "<td>" + total500ra + "</td>"+"<td>" + totalSum500ra + "</td>";
						}
						
						if(issuable200ra > 0 || fresh200ra > 0 ||  atm200ra > 0 ){
				html += "<tr><td>200 </td>" + "<td>" + issuable200ra + "</td> "
						+ "<td>" + atm200ra + "</td>" + "<td>" + fresh200ra
						+ "</td>"+ "<td>" + total200ra + "</td>"+"<td>" + totalSum200ra + "</td>";
						}
						
						if(issuable100ra > 0 || fresh100ra > 0 ||  atm100ra > 0 ){
				html += "<tr><td>100 </td>" + "<td>" + issuable100ra + "</td> "
						+ "<td>" + atm100ra + "</td>" + "<td>" + fresh100ra
						+ "</td>"+ "<td>" + total100ra + "</td>"+"<td>" + totalSum100ra + "</td>";
						}
						
						if(issuable50ra > 0 || fresh50ra > 0 ){
				html += "<tr><td>50 </td>" + "<td>" + issuable50ra + "</td> "
						+ "<td>" + "0" + "</td>" + "<td>" + fresh50ra + "</td>"+ "<td>" + total50ra + "</td>"+"<td>" + totalSum50ra + "</td>";
						}
						
						if(issuable20ra > 0 || fresh20ra > 0  ){
				html += "<tr><td>20 </td>" + "<td>" + issuable20ra + "</td> "
						+ "<td>" + "0" + "</td>" + "<td>" + fresh20ra + "</td>"+ "<td>" + total20ra + "</td>"+"<td>" + totalSum20ra + "</td>";
						}
						if(issuable10ra > 0 || fresh10ra > 0  ){
				html += "<tr><td>10 </td>" + "<td>" + issuable10ra + "</td> "
						+ "<td>" + "0" + "</td>" + "<td>" + fresh10ra + "</td>"+ "<td>" + total10ra + "</td>"+"<td>" + totalSum10ra + "</td>";
						}
						if(issuable5ra > 0 || fresh5ra > 0 ){
				html += "<tr><td>5 </td>" + "<td>" + issuable5ra + "</td> "
						+ "<td>" + "0" + "</td>" + "<td>" + fresh5ra + "</td>"+ "<td>" + total5ra + "</td>"+"<td>" + totalSum5ra + "</td>";
						}
						if(issuable2ra > 0 || fresh2ra > 0  ){
				html += "<tr><td>2 </td>" + "<td>" + issuable2ra + "</td> "
						+ "<td>" + "0" + "</td>" + "<td>" + fresh2ra + "</td>"+ "<td>" + total2ra + "</td>"+"<td>" + totalSum2ra + "</td>";
						}
						if(issuable1ra > 0 || fresh1ra > 0 ){
				html += "<tr><td>1 </td>" + "<td>" + issuable1ra + "</td> "
						+ "<td>" + "0" + "</td>" + "<td>" + fresh1ra + "</td>"+ "<td>" + total1ra + "</td>"+"<td>" + totalSum1ra + "</td>";
						}
				html += "<p> </p><div class='p-3 mb-2 bg-primary text-white text-center'>Approved Amount Details</div><div class='cal-sm text-danger'>Total Bundles = "+ sumOfTotalBulblesra + "</div><div class='cal-sm text-success'>   Total Amount = "+totalSumOfAmountra+"</div>";
				
				
				
				$('#modal-body').html(html);
				$('#myModal').modal('show');
			},
			
		});

	}
</script>
<body oncontextmenu="return false;">

	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Bundle Status</h4>
				</div>
				<div class="modal-body" id="modal-body">
					<p>This is a small modal.</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>



<body>

	<div id="wrapper">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
				
					<!-- End Sidebar scroll-->
					<!-- Bottom points-->
					<div class="sidebar-footer">
						<!-- item-->
						<a href="" class="link" data-toggle="tooltip" title="Settings"><i
							class="ti-settings"></i></a>
						<!-- item-->
						<a href="" class="link" data-toggle="tooltip" title="Email"><i
							class="mdi mdi-gmail"></i></a>
						<!-- item-->
						<a href="" class="link" data-toggle="tooltip" title="Logout"><i
							class="mdi mdi-power"></i></a>
					</div>
					<!-- End Bottom points-->


					<div class="page-wrapper">
						<!-- ============================================================== -->
						<!-- Container fluid  -->
						<!-- ============================================================== -->
						<div class="container-fluid">
							<!-- ============================================================== -->
							<!-- Bread crumb and right sidebar toggle -->
							<!-- ============================================================== -->
							<div class="row page-titles">
								<div class="col-md-5 col-8 align-self-center">
									<h3 class="text-themecolor m-b-0 m-t-0">Indent Request
										History</h3>
									<ol class="breadcrumb">
										<li class="breadcrumb-item"><a href="javascript:void(0)">Branch</a></li>
										<li class="breadcrumb-item active">Indent Request History</li>
									</ol>
								</div>

<%@ page import="com.chest.currency.entity.model.User" %>
<%    
 User user = (User) session.getAttribute("login");  
%> 
							<div class="card">
								<div class="card-body">

									<table class="table table-striped table-bordered"
										id="editable-datatable">
										<thead>
											<tr>
											    <th>Status</th>
												<th>From Date</th>
												<th>To Date</th>
												<th></th>
											</tr>
										</thead>
										<tbody>
											<form:form id="userPage" name="userPage"
												action="branchIndentHistory" method="post"
												modelAttribute="reportDate" autocomplete="off">

												<tr id="1" class="gradeX">
													<td>
														<form:select id="status" name="status" path="status"
																cssClass="form-control">
																<form:option value="">Select Status</form:option>
																<form:options name="status" items="${statusList}"
																	itemValue="status" itemLabel="status" />
															</form:select>
														</td>
													<td><form:input type="text" path="fromDate"
															id="fromDate" name="fromDate" cssClass="form-control" />
													</td>
													<td><form:input type="text" path="toDate" id="toDate"
															name="toDate" cssClass="form-control" /></td>
													<td><button type="submit" class="btn btn-default"
															value="Details" style="width: 99px;">Search</button></td>
												</tr>
											</form:form>
										</tbody>
									</table>
								</div>
							</div>










								<div class="card">
									<div class="card-body">
										<table class="table table-striped table-bordered"
											id="editable-datatable">
											<thead>
												<tr>
												    <td>SR Number</td>
												    <td>Amount Requested</td>
													<td>Amount Approved</td>
													<td>Indent Date</td>
													<td>Requested By</td>
													<td>Approved On</td>
													<td>Approved By</td>
													<td>Status</td>
													<td>Action</td>
												</tr>
											</thead>
											<tbody class="table table-striped table-bordered table-hover">
											<c:choose>
											<c:when test="${not empty history}">
											<c:forEach var="row" items="${history}" >
													<tr>
													<td>${row.srNo}</td>
													<td><a href="#"
															onclick="bundleDetails('${row.srNo}')">${row.totalValue}</a></td>
													<td><a href="#"
															onclick="approvedBundleDetails('${row.srNo}')">${row.totalValueApproved}</a></td>
													<td><fmt:formatDate pattern="yyyy-MM-dd" value="${row.insertTime.time}" /></td>
													<td><% out.print(user.getName());%></td>
													<td><fmt:formatDate pattern="yyyy-MM-dd" value="${row.approvedTime.time}" /></td>
													<td><% out.print(user.getName());%> </td>
													
													<td>${row.branchIndentStatus}</td>
													<c:set var = "status" scope = "session" value ="${row.branchIndentStatus}"/>
                                               <c:choose> 
                                                    <c:when test="${status == 'Approved'}">
                                                   <td><button type="button" class="btn btn-xs btn-success" onclick="requestApproveDetails('${row.srNo}')">view</button></td>
                                                       </c:when>
                                                   <c:otherwise>
                                                     <td><a href="././viewReqAndApprovAmount?id=${row.id}"><button type="button" class="btn btn-xs btn-danger" >edit</button></a></td>
                                                    </c:otherwise>
                                                </c:choose>										
					                               </tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
											<tr style="text-align: center;"><td colspan="9">No Record found</td></tr>
											</c:otherwise></c:choose>
											</tbody>
			
										</table>
						
									</div>
								</div>
							</div>
							<!-- /.panel-body -->
						</div>
						<!-- /.panel -->
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /#page-wrapper -->

		</div>
		<!-- /#wrapper -->
	</div>
	</div>

	<!-- jQuery -->
	<script src="./resources/bower_components/jquery/dist/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script
		src="./resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script
		src="./resources/bower_components/metisMenu/dist/metisMenu.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="./resources/dist/js/sb-admin-2.js"></script>

	<script src="./resources/js/jQuery.print.js"></script>

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