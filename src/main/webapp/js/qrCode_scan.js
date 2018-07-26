$(document).ready(function(){
	$(".msg-info").hide();
	$(".msg-success").hide();
	$(".msg-warning").hide();
	$(".msg-info").hide();
	$(".msg-error").hide();
	$(".msg-validation").hide();
}); 
function hideAllMsgDiv(){
	$(".msg-info").hide();
	$(".msg-success").hide();
	$(".msg-warning").hide();
	$(".msg-info").hide();
	$(".msg-error").hide();
	$(".msg-validation").hide();
	
}

function check_no_match_found(){
	
	var tablnObj = $("#records_table").find("tr:gt(1)");
	var tablRowLen = $(tablnObj).length;
	var totalNos_of_blank_Field =0;
	$("#records_table").find("tr:gt(1)").each(function(){
		var output = $(this).find("td:eq(6)").find("input").val();
		if(output.trim().length == 0 || output.trim() == "")
			totalNos_of_blank_Field++;
	});
	if(totalNos_of_blank_Field > 0)
		{
			$(".msg-success").text("Continue Scanning ....");
			$(".msg-success").show();
			document.getElementById("buttonID").disabled = true;				//DEActive
			$("#temp").val("");
			$("#temp").focus();
			return true;
		}
	else{																								//No Input BOX
			$(".msg-success").text("Scan completed, No further scanning is required..");
			$(".msg-success").show();
			document.getElementById("buttonID").disabled = false;				//Active
			$("#buttonID").focus();
			$("#temp").val("");
			return false;
	}
	
	//no match found
}

function comparePrice(){
		var barcodeTxt_old = $("#temp").val();
		var barcodeTxt_new = barcodeTxt_old.split("|")[0]+"|"+barcodeTxt_old.split("|")[1];
		hideAllMsgDiv();
	// Step - 2 :  If Alredy Scan all Data .....
	if(check_no_match_found() == false)
		return false;	

	
	//Step - 0 : If Invalid Scan	
		if(barcodeTxt_old.split("|")[0] == undefined  || barcodeTxt_old.split("|")[1] == undefined)
		{
			$("#temp").focus();
			$(".msg-info").text("Scanned QR Code is Invalid, please try again...");
			$(".msg-info").show();
			$("#temp").val("");
			return false;	
		}
	//Step - 1 : If Invalid Scan
		if(barcodeTxt_new.trim()=="" || barcodeTxt_new.trim().length == 0)
				{
					$("#temp").focus();
					$(".msg-error").text("Invalid QR Code...");
					$(".msg-error").show();
					$("#temp").val("");
					return false;	
				}			
		
		//Step -3 : Scan  and Put into TxtBox
		var tablnObj = $("#records_table").find("tr:gt(1)");
		var tablRowLen = $(tablnObj).length;
		var rowLoop =0;
		var counter=0;
		
		$("#records_table").find("tr:gt(1)").each(function(){
			rowLoop++;
			var category = $(this).find("td:eq(0)").text();
			var denomination = $(this).find("td:eq(2)").text();
			var outputTxt= category+"|"+denomination;
			var bundle= $(this).find("td:eq(4)").text();
			var output = $(this).find("td:eq(6)").find("input").val();
			if(barcodeTxt_new == outputTxt && (output.trim().length == 0 || output.trim() == ""))
				{
					$(this).find("td:eq(6)").find("input").val(barcodeTxt_new);
					$(this).find("td:eq(7)").find("span").text("Success");					//Status
					counter++;
					return false;
				}
		});
		hideAllMsgDiv();
		//---------------------Condition-----------------------------
		if(counter == 0)
		{
			$(".msg-error").text("No Match Found, Please try again..");
			$(".msg-error").show();
			$("#temp").focus();
			$("#temp").val("");
			document.getElementById("buttonID").disabled = true;		//BUTTON DEACTIVE
			return false;
		}
		else{
			check_no_match_found();
		}
}