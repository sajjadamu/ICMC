function GetAssociateData1(sDate, eDate,code) {

	var url = "ajax-registration-report.jsp";
	var parameters = "startDate=" + sDate + "&endDate=" + eDate +"&code="+code;
	// alert("hii");
	var xmlhttp;
	if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp = new XMLHttpRequest();
	} else {// code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4) {
			var res = xmlhttp.responseText;
			document.getElementById("content").innerHTML = res;
		}
	};
	xmlhttp.open("POST", url + "?" + parameters, true);
	xmlhttp.send();
}

function AssociateDataSearch1() {
	var sDate = document.getElementById("from_date").value;
	var eDate = document.getElementById("to_date").value;
	var code = document.getElementById("code").value;
	GetAssociateData1(sDate, eDate,code);
}

function GetNameByCode(val) {
	// alert("val---" + val);
	var url = "ajax-registration-by-name.jsp";
	var parameters = "id=" + val;
	var xmlhttp;
	if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp = new XMLHttpRequest();
	} else {// code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4) {
			var res = xmlhttp.responseText;
			// alert(res);
			document.getElementById("associate_name").innerHTML = res;
		}
	};
	xmlhttp.open("POST", url + "?" + parameters, true);
	xmlhttp.send();
}