
$(document).ready(function(){

	//######################### Name Validation##################################
		 /*$('body').on('blur', 'input', function(){
            	Global_name_Validation(this);
		});*/
	
	$('body').find("form").submit(function() {
		
			$(this).find("input[type='text']").each(function(){
				
					Global_name_Validation(this);
				
			})	;
	
	});
	
});

function sub_undefined_Validation(str)
{
if(str == undefined || str  == null)
	 {
	 	return true;
	 }
 else 
	 {
	 	return false;
	 }
}
function sub_notNull_Validation(str)
{
if(str.trim().length == 0 || str.trim()  == "")
	 {
	 	return true;
	 }
 else 
	 {
	 	return false;
	 }
}
function sub_max_Validation(str,maxLen)
{
	if(str.trim().length > maxLen)
	{
		return true;
	}
	else
	{
		return false;
	}
}
function sub_startWith_AtoZU_Validation(str)
{
	var firstChar = str.charAt(0);						//Use Y
	var allowdChar = /^[a-zA-Z_]*$/;				//Pattern
	if(allowdChar.test(firstChar)==false)
		{
			return false;
		}
}
function sub_noSpace_Validation(str)
{
var sPattn = /\s/.test(str);
if(sPattn == true)
	 {
	 	return false;
	 }
 else 
	 {
	 	return true;
	 }
}
function sub_UndrScore_Validation(str)
{
	//Check hiphen contain  all string >>>>	
	var Hpattn = /^(_)\1+$/;
	var resH = Hpattn.test(str);			//false
	 if(resH == true)
		 {
		 	return false;
		 }
	 else 
		 {
		 	return true;
		 }
}
function Global_name_Validation(elem){
		var val = $(elem).val();
		var meesage="Error : Special Character not Allowed. ";
		var flag = true;
		var maxLen =45;
		var allowdChar = /^[a-zA-Z0-9\w_]*$/;
		try {
			if(sub_notNull_Validation(val.trim()) == false){
					if(sub_undefined_Validation(val.trim()) == true)
						{
							alert("Invalid "+meesage+"");
							flag = false;
						}
					/*else if(sub_max_Validation(val.trim(),maxLen) == true)
					{
						alert(""+name+"  should be of "+maxLen+"-Charater");
						flag = false;
					}*/
					//Validation -5 : Check value start (_) udersocre  and a-z or A_Z
					else if(sub_startWith_AtoZU_Validation(val.trim()) == false)
						{
							alert(meesage);
							flag = false;
						}
					//Validation -6 : Check value contain more space ....
					else if(sub_noSpace_Validation(val.trim()) == false)
						{
							alert(meesage);
							flag = false;
						}
					//Validation -7 : Check value contain ------------------ or _______________________
					else if(sub_UndrScore_Validation(val.trim()) == false)
						{
							alert(meesage);
							flag = false;
						}
					//Validation -4 : Check value Special chaarater except _
					else if(allowdChar.test(val.trim())==false)
					{
						alert(meesage);
						flag = false;
					}
			}
			} catch (e) {
			alert("Exception  :"+e);
			flag = false;
		}

			
			if(flag == false)
			{
				/*alert("Error");*/
				return false;
			}

	
}

