 
var countrow=0;
function doAjaxPost(str) {  
	  // get the form values  
	  var Denomination = $('#Denomination'+str).val();
	  var Bundle = $('#Bundle'+str).val();
	  var total=Denomination*100*Bundle;
	   $('#Total'+str).val(total);
	  $.ajax({  
	    type: "POST", 
	    url: "././getBinForUnprocess",
	    data: "denominations=" + Denomination + "&bundle=" + Bundle,
	    success: function(response){  
	      // we have the response  
	      $('#info').html(response);
	     /*  $('#denominations').val('');
	      $('#bundle').val(''); */
	      $('#denominations'+str).val(Denomination);
	      $('#bundle'+str).val(Bundle);
	      $('#total'+str).val(total);
	      $('#binNumber'+str).val(response);
	      // alert("Response =="+response); 
	    }, 
	    error: function(e){  
	      alert('dedectBin 1 Error: ' + e);  
	    }  
	  });  
	}  
 var unprocessedData;
 function doAjaxPost1(str) {  
	 
	   var rowCount = $('#table1 tr').length-2;
	   var Denomination = $('#Denomination'+str).val();
	   var Bundle = $('#Bundle'+str).val();
	   var total=Denomination*1000*Bundle;
	   $('#Total'+str).val(total);
	   $('#TotalWithFormatter'+str).val(total.toLocaleString('en-IN'));
	  
	   var MytotalValue=null;
	   var fetchTotalValue=null;
	 
     	for(var p=0;p<=countrow;p++){
		  var me=parseFloat($('#Total'+p).val());
		 
			 if(!isNaN(me)){
				 fetchTotalValue=$('#Total'+p).val();
				 MytotalValue+=parseFloat(fetchTotalValue);
				 $('#totalValue').val(MytotalValue.toLocaleString('en-IN'));
		     }  
		 }
   	}  
 function doAjaxPost(str) {  
	 
	   var rowCount = $('#table1 tr').length-2;
	   var Denomination = $('#Denomination'+str).val();
	   var Bundle = $('#Bundle'+str).val();
	   var total=Denomination*100*Bundle;
	   $('#Total'+str).val(total);
	   $('#TotalWithFormatter'+str).val(total.toLocaleString('en-IN'));
	  
	   var MytotalValue=null;
	   var fetchTotalValue=null;
	 
   	for(var p=0;p<=countrow;p++){
		  var me=parseFloat($('#Total'+p).val());
		 
			 if(!isNaN(me)){
				 fetchTotalValue=$('#Total'+p).val();
				 MytotalValue+=parseFloat(fetchTotalValue);
				 $('#totalValue').val(MytotalValue.toLocaleString('en-IN'));
		     }  
		 }
 	}  	
 var countrow=0;
 var rowCount=0;
 var dataId=0;	
 
 function doAjaxPostForBranch() {  
	  addHeader();
	  // get the form values  
	  var solid = $('#solId').val();
	  if(solid.length==4)
	  {
	  $.ajax({  
	    type: "POST", 
	    url: "././branchName",
	    data: "solid=" + solid,
	    success: function(response){  
	      // we have the response  
	      var res = JSON.stringify(response);
	      var newStr = res.substring(1, res.length - 1); // Remove Array Brackets
	      $('#info').html(response);
	      $('#solid').val(solid);
	      $('#branch').val(newStr);
	       // alert("Response =="+response);
	    }, 
	    error: function(e){  
	      //alert('branchName Error: ' + e);  
	    }  
	  });  
	  }
	}  

$(document).ready(function(){
	//$("#member").keyup(function(){
	$("#member").change(function() { 
		 var rowCount = ($('#table1 tr').length);
		 if(rowCount>0){
			document.getElementById("table1").innerHTML = "";
		 }
		var val = $("#member").val();
		
		 var unprocessedOrProcessed =  $('input[name=processedOrUnprocessed]:checked').val();
		if(unprocessedOrProcessed=="UNPROCESS")
			{
    if(val>0)
	{
var html='<tr class="header"><th>Denomination</th><th>Packets</th><th>Total</th><th>Bin</th></tr>' ;
	for (i = 0; i < val; i++) {
html += '<tr id="main'+i+'">'+
        
		'<td><input type="text" id="Denomination'+i+'" class="form-control input-margin" maxlength="4" minlength="1" name="Denomination" onkeyup="doAjaxPost('+i+')" value="" autofocus></td>'+
		'<td><input type="text" id="Bundle'+i+'" class="form-control input-margin" name="Bundle" value="" onkeyup="doAjaxPost('+i+')" ></td>'+
		'<td width="20%"><input type="text" id="TotalWithFormatter'+i+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
		'<td><input type="text" id="binNumber'+i+'" class="form-control input-margin" name="Bin" value="" readonly="true" ></td>'+
		'<td><input type="radio"  name="binOrBox['+i+']" value="BIN" checked="checked"></td><td>BIN</td>'+
		'<td><input type="radio"  name="binOrBox['+i+']" value="BOX"></td><td>BOX</td>'+
		'<td><input type="hidden" id="recordID'+i+'" class="form-control input-margin" name="Bin" value="" ></td>'+
		'<td class="qr-button"><input type="button" id="print'+i+'" class="btn btn-default qr-button"  name="print"  value="Save AND Print QR" onclick="SavePrint('+i+');this.disabled=true"></td>'+
		'<td><button type="button" class="delete" onclick="deleteRow('+i+')">-</button></td>'+
		'<td><input type="hidden" id="Total'+i+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
 	'</tr>';
countrow++;
} 

	$('#table1').append(html);
	$('#table1').append('<tr class="header" id=addmoreButton'+(i-1)+'><td><button type="button"  class="addmoreButton" onclick="replicateValue('+(i-1)+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button"  class="addmoreButton" onclick="addRow('+(i-1)+');doAjaxForTotal()">Add Blank Row</button></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly></td></tr>');
	}
    else
    	{
    	$('#table1').empty();
    	}
	}
	
	if(unprocessedOrProcessed=="PROCESSED")
		{
		
		if(val>0)
		{
			var html='<tr class="header"><th>Category</th><th>Denomination</th><th>Bundle</th><th>Total</th><th>Bin</th></tr>' ;
			for (i = 0; i < val; i++) {
		    html += '<tr id="main'+i+'">'+
		       '<td width="15%"><select name="Category" id="Category'+i+'" class="form-control input-margin"><option value="ISSUABLE">ISSUABLE</option><option value="FRESH">FRESH</option><option value="ATM">ATM</option><option value="SOILED">SOILED</option></select></td>'+
				'<td><input type="text" id="Denomination'+i+'" class="form-control input-margin" maxlength="4" minlength="1" name="Denomination" onkeyup="doAjaxPost1('+i+')" value="" autofocus></td>'+
				'<td><input type="text" id="Bundle'+i+'" class="form-control input-margin" name="Bundle" value="" onkeyup="doAjaxPost1('+i+')" ></td>'+
				'<td width="15%"><input type="text" id="TotalWithFormatter'+i+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
				'<td><input type="text" id="binNumber'+i+'" class="form-control input-margin" name="Bin" value="" readonly="true" ></td>'+
				'<td><input type="radio"  name="binOrBox['+i+']" value="BIN" checked="checked"></td><td>BIN</td>'+
				'<td><input type="radio"  name="binOrBox['+i+']" value="BOX"></td><td>BOX</td>'+
				'<td><input type="hidden" id="recordID'+i+'" class="form-control input-margin" name="Bin" value="" ></td>'+
				'<td class="qr-button"><input type="button" id="print'+i+'" class="btn btn-default qr-button"  name="print"  value="Save AND Print QR" onclick="SavePrint('+i+');this.disabled=true"></td>'+
				'<td><button type="button" class="delete" onclick="deleteRow('+i+')">-</button></td>'+
				'<td><input type="hidden" id="Total'+i+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
		 	'</tr>';
		countrow++;
		} 

			$('#table1').append(html);
		 $('#table1').append('<tr id=addmoreButton'+(i-1)+'><td><button type="button"  class="addmoreButton" onclick="replicateValue1('+(i-1)+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button"  class="addmoreButton" onclick="addRow1('+(i-1)+');doAjaxForTotal()">Add Blank Row</button></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly></td></tr>');
		}
		
		}
		 
    });
});


function addRow(i){
	/* countrow++;
	 var rowCount = ($('#table1 tr').length)-2; */
	 var rowCount = ($('#table1 tr').length)-2;
	 countrow=rowCount;
	jQuery('#addmoreButton'+i).remove();
	i++;
	var data = '<tr id="main'+countrow+'">'+
	'<td><input type="text" id="Denomination'+countrow+'" maxlength="4" minlength="1" class="form-control input-margin" name="Denomination" onkeyup="doAjaxPost1('+countrow+')" value="" ></td>'+
	'<td><input type="text" maxlength="45" id="Bundle'+countrow+'" class="form-control input-margin" name="Bundle" value="" onkeyup="doAjaxPost1('+countrow+')" ></td>'+
	'<td><input type="text" maxlength="45" id="TotalWithFormatter'+countrow+'"  class="form-control input-margin" name="Total" value="" readonly="true" ></td>'+
	'<td><input type="text" maxlength="45" id="binNumber'+countrow+'" class="form-control input-margin" name="Bin" value="" readonly="true" ></td>'+
	'<td><input type="radio"  name="binOrBox['+countrow+']" value="BIN" checked="checked"></td><td>BIN</td>'+
	'<td><input type="radio"  name="binOrBox['+countrow+']" value="BOX"></td><td>BOX</td>'+
	'<td><input type="hidden" id="recordID'+countrow+'" class="form-control input-margin" name="Bin" value="" ></td>'+
	'<td class="qr-button"><input type="button" id="print'+countrow+'" class="btn btn-default qr-button"  name="print"  value="Save AND Print QR" onclick="SavePrint('+countrow+');this.disabled=true; return false"></td>'+
	'<td><button type="button" onclick="deleteRow('+countrow+')">-</button></td>'+
	'<td><input type="hidden" maxlength="45" id="Total'+countrow+'"  class="form-control input-margin" name="Total" value="" readonly="true" ></td>'+
	'</tr>';
	$('#table1').append(data);
	$('#table1').append('<tr class="header" id=addmoreButton'+rowCount+'><td><button type="button" class="addmoreButton" onclick="replicateValue('+rowCount+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button" class="addmoreButton" onclick="addRow('+rowCount+');doAjaxForTotal()">Add Blank Row</button></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly></td></tr>');
	}

function addRow1(i){
    /* countrow++;
     var rowCount = ($('#table1 tr').length)-2; */
     var rowCount = ($('#table1 tr').length)-2;
     countrow=rowCount;
    jQuery('#addmoreButton'+i).remove();
    i++;
    var data = '<tr id="main'+countrow+'">'+
    '<td width="15%"><select name="Category" id="Category'+i+'" class="form-control input-margin"><option value="ISSUABLE">ISSUABLE</option><option value="FRESH">FRESH</option><option value="ATM">ATM</option><option value="SOILED">SOILED</option></select></td>'+
    '<td><input type="text" id="Denomination'+countrow+'" maxlength="4" minlength="1" class="form-control input-margin" name="Denomination" onkeyup="doAjaxPost1('+countrow+')" value="" ></td>'+
    '<td><input type="text" maxlength="45" id="Bundle'+countrow+'" class="form-control input-margin" name="Bundle" value="" onkeyup="doAjaxPost1('+countrow+')" ></td>'+
    '<td><input type="text" maxlength="45" id="TotalWithFormatter'+countrow+'"  class="form-control input-margin" name="Total" value="" readonly="true" ></td>'+
    '<td><input type="text" maxlength="45" id="binNumber'+countrow+'" class="form-control input-margin" name="Bin" value="" readonly="true" ></td>'+
    '<td><input type="radio"  name="binOrBox['+countrow+']" value="BIN" checked="checked"></td><td>BIN</td>'+
    '<td><input type="radio"  name="binOrBox['+countrow+']" value="BOX"></td><td>BOX</td>'+
    '<td><input type="hidden" id="recordID'+countrow+'" class="form-control input-margin" name="Bin" value="" ></td>'+
    '<td class="qr-button"><input type="button" id="print'+countrow+'" class="btn btn-default qr-button"  name="print"  value="Save AND Print QR" onclick="SavePrint('+countrow+');this.disabled=true; return false"></td>'+
    '<td><button type="button" onclick="deleteRow('+countrow+')">-</button></td>'+
    '<td><input type="hidden" maxlength="45" id="Total'+countrow+'"  class="form-control input-margin" name="Total" value="" readonly="true" ></td>'+
    '</tr>';
    $('#table1').append(data);
    $('#table1').append('<tr class="header" id=addmoreButton'+rowCount+'><td><button type="button" class="addmoreButton" onclick="replicateValue1('+rowCount+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button" class="addmoreButton" onclick="addRow1('+rowCount+');doAjaxForTotal()">Add Blank Row</button></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly></td></tr>');
    }









function replicateValue(i){
	
    // alert(countrow)
	//dataId = countrow-1;
    // alert("value from ID="+dataId)
    var rowCount = ($('#table1 tr').length)-2;
	countrow=rowCount;
	dataId = countrow-1;
	var denomData = jQuery('#Denomination'+dataId).val();
	var bundleData = jQuery('#Bundle'+dataId).val();
	var totalData = jQuery('#TotalWithFormatter'+dataId).val();
	
	if(typeof denomData === 'undefined'){
		denomData = "";
	}
	if(typeof bundleData === 'undefined'){
		bundleData = "";
	}
	if(typeof totalData === 'undefined'){
		totalData = "";
	}
	
	rowCount = ($('#table1 tr').length)-2;
	jQuery('#addmoreButton'+i).remove();
	i++;
	var data = '<tr id="main'+countrow+'">'+
	'<td><input type="text"  id="Denomination'+countrow+'" maxlength="4" minlength="1" class="form-control input-margin" name="Denomination"  onkeyup="doAjaxPost('+countrow+')" value="'+denomData+'" autofocus></td>'+
	'<td><input type="text" maxlength="45" id="Bundle'+countrow+'" class="form-control input-margin" name="Bundle" value="'+bundleData+'" onkeyup="doAjaxPost('+countrow+')" ></td>'+
	'<td><input type="text" maxlength="45" id="TotalWithFormatter'+countrow+'"  class="form-control input-margin" name="Total" value="'+totalData+'" readonly="true"></td>'+
	'<td><input type="text" maxlength="45" id="binNumber'+countrow+'" class="form-control input-margin" name="Bin" value="" readonly="true"></td>'+
	'<td><input type="radio"  name="binOrBox['+countrow+']" value="BIN" checked="checked"></td><td>BIN</td>'+
	'<td><input type="radio"  name="binOrBox['+countrow+']" value="BOX"></td><td>BOX</td>'+
	'<td><input type="hidden" id="recordID'+countrow+'" class="form-control input-margin" name="Bin" value="" ></td>'+
	'<td class="qr-button"><input type="button" id="print'+countrow+'" class="btn btn-default qr-button"  name="print"  value="Save AND Print QR" onclick="SavePrint('+countrow+');this.disabled=true"></td>'+
	'<td><button type="button" onclick="deleteRow('+countrow+')">-</button></td>'+
	'<td><input type="hidden" maxlength="45" id="Total'+countrow+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
	'</tr>';
	$('#table1').append(data);
	countrow++;
	$('#table1').append('<tr class="header" id=addmoreButton'+rowCount+'><td><button type="button" class="addmoreButton" onclick="replicateValue('+rowCount+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button" class="addmoreButton" onclick="addRow('+rowCount+');doAjaxForTotal()">Add Blank Row</button></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly ></td></tr>');
}

function replicateValue1(i){
    
    // alert(countrow)
    //dataId = countrow-1;
    // alert("value from ID="+dataId)
    var rowCount = ($('#table1 tr').length)-2;
    countrow=rowCount;
    dataId = countrow-1;
    var denomData = jQuery('#Denomination'+dataId).val();
    var bundleData = jQuery('#Bundle'+dataId).val();
    var totalData = jQuery('#TotalWithFormatter'+dataId).val();
    
    if(typeof denomData === 'undefined'){
        denomData = "";
    }
    if(typeof bundleData === 'undefined'){
        bundleData = "";
    }
    if(typeof totalData === 'undefined'){
        totalData = "";
    }
    
    rowCount = ($('#table1 tr').length)-2;
    jQuery('#addmoreButton'+i).remove();
    i++;
    var data = '<tr id="main'+countrow+'">'+
    '<td width="15%"><select name="Category" id="Category'+i+'" class="form-control input-margin"><option value="ISSUABLE">ISSUABLE</option><option value="FRESH">FRESH</option><option value="ATM">ATM</option><option value="SOILED">SOILED</option></select></td>'+
    '<td><input type="text"  id="Denomination'+countrow+'" maxlength="4" minlength="1" class="form-control input-margin" name="Denomination" value="'+denomData+'" onkeyup="doAjaxPost('+countrow+')" autofocus></td>'+
    '<td><input type="text" maxlength="45" id="Bundle'+countrow+'" class="form-control input-margin" name="Bundle" value="'+bundleData+'" onkeyup="doAjaxPost('+countrow+')" ></td>'+
    '<td><input type="text" maxlength="45" id="TotalWithFormatter'+countrow+'"  class="form-control input-margin" name="Total" value="'+totalData+'" readonly="true"></td>'+
    '<td><input type="text" maxlength="45" id="binNumber'+countrow+'" class="form-control input-margin" name="Bin" value="" readonly="true"></td>'+
    '<td><input type="radio"  name="binOrBox['+countrow+']" value="BIN" checked="checked"></td><td>BIN</td>'+
    '<td><input type="radio"  name="binOrBox['+countrow+']" value="BOX"></td><td>BOX</td>'+
    '<td><input type="hidden" id="recordID'+countrow+'" class="form-control input-margin" name="Bin" value="" ></td>'+
    '<td class="qr-button"><input type="button" id="print'+countrow+'" class="btn btn-default qr-button"  name="print"  value="Save AND Print QR" onclick="SavePrint('+countrow+');this.disabled=true"></td>'+
    '<td><button type="button" onclick="deleteRow('+countrow+')">-</button></td>'+
    '<td><input type="hidden" maxlength="45" id="Total'+countrow+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
    '</tr>';
    $('#table1').append(data);
    countrow++;
    $('#table1').append('<tr class="header" id=addmoreButton'+rowCount+'><td><button type="button" class="addmoreButton" onclick="replicateValue1('+rowCount+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button" class="addmoreButton" onclick="addRow1('+rowCount+');doAjaxForTotal()">Add Blank Row</button></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly ></td></tr>');
}





function deleteRow(i){

	var MytotalValue=null;
  	var fetchTotalValue=null;
	var t1=parseFloat($('#totalValue').val());
	var t2=parseFloat($('#Total'+i).val());
	
	jQuery('#main'+i).remove();
	if(i==0){
		jQuery('.header').remove();
	}
	
	for(var p=0;p<=countrow;p++){
		  var me=parseFloat($('#Total'+p).val());
			 if(!isNaN(me)){
				 fetchTotalValue=$('#Total'+p).val();
				  MytotalValue+=parseFloat(fetchTotalValue);
				$('#totalValue').val(MytotalValue.toLocaleString('en-IN'));
			                  }  }
	
	if( ($('#table1 tr').length)-2==0){$('#totalValue').val(0)}
	
};


function SavePrint(str) {
	addHeader();
	var isValid = true;
	var branchReceipt={
			"solId":$('#solId').val(),
			"denomination":$('#Denomination'+str).val(),
			"bundle":$('#Bundle'+str).val(),
			"bin":$('#binNumber'+str).val(),
			"total":$('#Total'+str).val(),
			"branch":$('#branch').val(),
			"srNumber":$('#srNumber').val(),
			"binCategoryType":$('#main'+str).find('input[type=radio]:checked').val(),
			"processedOrUnprocessed":$('input[name=processedOrUnprocessed]:checked').val(),
			"currencyType":$('#Category'+str).val(),
	}
	if($('#solId').val() == ""){
		$('#err1').show();
		isValid=false;
	}
	if($('#solId').val().length != 4){
		$('#err11').show();
		isValid=false;
	}
	if($('#Denomination'+str).val()!=2000 && $('#Denomination'+str).val()!=1000 && $('#Denomination'+str).val()!=500 && $('#Denomination'+str).val()!=200 && $('#Denomination'+str).val()!=100 && $('#Denomination'+str).val()!=50 && $('#Denomination'+str).val()!=20 && $('#Denomination'+str).val()!=10 && $('#Denomination'+str).val()!=5 && $('#Denomination'+str).val()!=2 && $('#Denomination'+str).val()!=1){
		$('#err2').show();
		isValid=false;
	}
	else if($('#Bundle'+str).val() == ""){
		$('#err3').show();
		isValid=false;
	}
	if($('#branch').val() == ""){
		$('#err4').show();
		isValid=false;
	}
	
	/*if($('#srNumber').val() == ""){
		$('#err5').show();
		isValid=false;
	}
	if(($('#srNumber').val().length)  != 11  ){
		$('#err6').show();
		isValid = false;
	}*/
	
	if(isValid){
	$.ajax({
				type : "POST",
				 contentType : 'application/json; charset=utf-8',
			      dataType : 'json',
				  url : "././QRPath",
				 data: JSON.stringify(branchReceipt),
				success : function(response) {
					var html =""
					for(var i=1; i < response.length; i++){
						html += "<textarea id=prntextarea"+i+">'"+response[i]+"'</textarea>"
						$('#prncode').append(html);
						//alert($('#prntextarea'+i).text())
						
						/*$(document).ready(function() {
							printZpl(document.getElementById("prntextarea" +i).value);
						});*/ 
						//printZpl(document.getElementById("prntextarea" +i).value);
					}
					//For bin in text Field
					var binNum=response[0];
					/*$.each(response[0],function(index,element){
						binNum+=element.bin;
						 binNum=binNum+",";
					});*/
					$('#binNumber'+str).val(binNum);
					//For Print Text
					
					/*var str2 = '';
			    	$.each(response,function(index,element){
			    		str2+= '<div><div style="width:25%;float:left;" ><img src="./files'+element.filepath+'" alt="QRPrint"></div>'
			    		str2 += "<div>Bin : "
							+ "<font size=50>"+element.bin+"</font>"
							+ "</br>"
							+ element.branch
							+ "</br>"
							+ "Sol ID :"
							+ element.solId
							+ "</br>"
							+ element.denomination
							+ "|"
							+ element.bundle
							+ "|"
							+ element.total
							+ "</div></div><BR><HR>";
			    	});
			    	//alert(str);
			    	$('#printSection').html(str2);
					$('#printSection').show();
					$.print("#printSection");
					$('#printSection').hide();
					$('#print'+str).prop('disabled', true);
					$("#member").prop('disabled', true);*/
				},
				error : function(e) {
					//alert('Error In Print, Please Try Again: ' + e);
					alert('Error: ' + e.responseJSON.message);
					jQuery('#print'+str).attr("disabled", false);
				}
			});
}
}

/*function printZpl(zpl) {
  var printWindow = window.open();
  printWindow.document.open('text/plain')
  printWindow.document.write(zpl);
  printWindow.document.close();
  printWindow.focus();
  printWindow.print();
  printWindow.close();
}*/

function refresh() {
	window.location = '././Addshrink';
}
	
function doAjaxForTotal() { 
	var j = 0;
	var myTotalValue = null;
	var noOfRows = ($('#table1 tr').length-2);
    for(var p=0; p<noOfRows; p++){
    	var Denomination = $('#Denomination'+p).val();
		var Bundle = $('#Bundle'+p).val();
		var total = Denomination*100*Bundle;
		$('#Total'+p).val(total);
    	
    	var totalValue = parseFloat($('#Total'+p).val());
    	if(noOfRows == j){
    		break;
    	}
    	if(!isNaN(totalValue) && totalValue != "" && totalValue != null && totalValue != 'undefined'){
    		myTotalValue += parseFloat(totalValue);
    		j++;
    	}else{
    		//noOfRows++;
    	}
	}
    $('#totalValue').val(myTotalValue.toLocaleString('en-IN'));
} 