<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<table align="center">
<tr><th align="center">Notes</th></tr>
</table>
	<table border="1" align="center">
		<tr>
			<th>Denomination</th>
			<th>Currency Type</th>
			<th>Total Bundle Request</th>
			<th>Total Bundle Paid</th>
			<th>Total Bundle To be Paid</th>
		</tr>
		<tr>
			<td>2000</td>
			<td>ATM : ${total2000ATM} FRESH : ${total2000FRESH} ISSUABLE :
				${total2000ISSUABLE} SOILED : ${soiled2000}</td>
				<td>${total2000All}</td>
				<td>${total2000AllA}</td>
				<td>${bundleTobePaid2000}</td>
		</tr>

		<tr>
			<td>500</td>
			<td>ATM : ${total500ATM} FRESH : ${total500FRESH} ISSUABLE :
				${total500ISSUABLE} SOILED : ${soiled500}</td>
				<td>${total500All}</td>
				<td>${total500AllA}</td>
			    <td>${bundleTobePaid500}</td>
		</tr>


		<tr>
			<td>100</td>
			<td>ATM : ${total100ATM} FRESH : ${total100FRESH} ISSUABLE :
				${total100ISSUABLE} SOILED : ${soiled100}</td>
				<td>${total100All}</td>
				<td>${total100AllA}</td>
				<td>${bundleTobePaid100}</td>
				
		</tr>


		<tr>
			<td>50</td>
			<td>ATM : ${total50ATM} FRESH : ${total50FRESH} ISSUABLE :
				${total50ISSUABLE} SOILED : ${soiled50}</td>
				<td>${total50All}</td>
				<td>${total50AllA}</td>
				<td>${bundleTobePaid50}</td>
		</tr>

		<tr>
			<td>20</td>
			<td>ATM : ${total20ATM} FRESH : ${total20FRESH} ISSUABLE :
				${total20ISSUABLE} SOILED : ${soiled20}</td>
				<td>${total20All}</td>
				<td>${total20AllA}</td>
				<td>${bundleTobePaid20}</td>
		</tr>

		<tr>
			<td>10</td>
			<td>ATM : ${total10ATM} FRESH : ${total10FRESH} ISSUABLE :
				${total10ISSUABLE} SOILED : ${soiled10}</td>
				<td>${total10All}</td>
				<td>${total10AllA}</td>
				<td>${bundleTobePaid10}</td>
		</tr>

		<tr>
			<td>5</td>
			<td>ATM : ${total5ATM} FRESH : ${total5FRESH} ISSUABLE :
				${total5ISSUABLE} SOILED : ${soiled5}</td>
				<td>${total5All}</td>
				<td>${total5AllA}</td>
				<td>${bundleTobePaid5}</td>
		</tr>

		<tr>
			<td>1</td>
			<td>ATM : ${total1ATM} FRESH : ${total1FRESH} ISSUABLE :
				${total1ISSUABLE} SOILED : ${soiled1}</td>
				<td>${tota1All}</td>
				<td>${total1AllA}</td>
				<td>${bundleTobePaid1}</td>
		</tr>
	</table>
	
	<table align="center"><tr><th>COINS</th></tr></table>
	<Table align="center" border="1"><Tr><td>10</td><Td>${coins10}</Td><Td>${coins10}</Td><Td>${coins10Accept }</Td><td>coins10needToPaid</td></Tr>
	<Tr><td>5</td><Td>${coins5}</Td><Td>${coins5}</Td><Td>${coins5Accept }</Td><td>coins5needToPaid</td></Tr>
	<Tr><td>2</td><Td>${coins2}</Td><Td>${coins2}</Td><Td>${coins2Accept }</Td><td>coins2needToPaid</td></Tr>
	<Tr><td>1</td><Td>${coins1}</Td><Td>${coins1}</Td><Td>${coins1Accept }</Td><td>coins1needToPaid</td></Tr>
	</Table>
</body>
</html>