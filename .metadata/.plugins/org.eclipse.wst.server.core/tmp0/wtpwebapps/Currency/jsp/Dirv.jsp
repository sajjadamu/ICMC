<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="en">

<head>
<link rel="shortcut icon" href="./resources/logo/favicon.ico"
	type="image/x-icon">
<script src="./js/jquery-1.12.0.min.js"></script>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>ICICI : Diversion</title>

<script src="./resources/js/jquery.js"></script>
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
<link href="./resources/css/calendar.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="./resources/css/jquery.datetimepicker.css" />

<script type="text/javascript">
var countrow=0;
var rowCount = 0;
var dataId=0;

$(document).ready(function(){
    /* $("#member").keyup(function(){ */
        $("#member").change(function() { 
    
             rowCount = ($('#table1 tr').length);
             if(rowCount>0){
                document.getElementById("table1").innerHTML = "";
             } 
            var val = $("#member").val();
             var unprocessedOrProcessed =  $('input[name=processedOrUnprocessed]:checked').val();
             if(unprocessedOrProcessed=="UNPROCESS")
                {
    if(val>0)
    {
var html='<tr><th>Denomination</th><th>Bundle</th><th>Total</th><th>Bin</th></tr>' ;
    for (i = 0; i < val; i++) {
html += '<tr id="main'+i+'">'+
/*         '<td width="15%"><select name="currencyType" id="currencyType'+i+'" class="form-control input-margin"><option value="ISSUABLE">ISSUABLE</option><option value="ATM">ATM</option><option value="FRESH">FRESH</option><option value="UNPROCESS">UNPROCESS</option></td>'+
 */        '<td><input type="text"  id="Denomination'+i+'" maxlength="4" minlength="1" class="form-control input-margin" name="Denomination" value="" autofocus></td>'+
        '<td><input type="text" maxlength="45" id="Bundle'+i+'" class="form-control input-margin" name="Bundle" value="" onkeyup="doAjaxPost('+i+')" ></td>'+
        '<td><input type="text" maxlength="45" id="TotalWithFormatter'+i+'"  class="form-control input-margin" name="Total" value="" autofocus readonly="true"></td>'+
        '<td><input type="text" maxlength="45" id="binNumber'+i+'" class="form-control input-margin" name="Bin" value="" readonly="true" ></td>'+
        '<td><input type="radio"  name="binOrBox['+i+']" value="PROCESSING" checked="checked"></td><td>Processing</td>'+
        '<td><input type="radio"  name="binOrBox['+i+']" value="BIN"></td><td>BIN</td>'+
        '<td><input type="radio"  name="binOrBox['+i+']" value="BOX"></td><td>BOX</td>'+
        '<td><input type="hidden" id="recordID'+i+'" class="form-control input-margin" name="Bin" value="" ></td>'+
        '<td class="qr-button"><input type="button" id="print'+i+'" class="btn btn-default qr-button"  name="print"  value="Save AND Print QR" onclick="SavePrint('+i+'); this.disabled=true"></td>'+
        '<td><button type="button" onclick="deleteRow('+i+')">-</button></td>'+
        '<td><input type="hidden" maxlength="45" id="Total'+i+'"  class="form-control input-margin" name="Total" value="" autofocus readonly="true"></td>'+
     '</tr>';
countrow++;
} 
    $('#table1').append(html);
    $('#table1').append('<tr id=addmoreButton'+(i-1)+'><td><button type="button"  class="addmoreButton" onclick="replicateValue('+(i-1)+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button"  class="addmoreButton" onclick="addRow('+(i-1)+');doAjaxForTotal()">Add Blank Row</button></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly></td></tr>');

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
                    var html='<tr><th>Category</th><th>Denomination</th><th>Bundle</th><th>Total</th><th>Bin</th></tr>' ;
                    for (i = 0; i < val; i++) {
                    html += '<tr id="main'+i+'">'+
                       '<td width="22%"><select name="Category" id="Category'+i+'" class="form-control input-margin"><option value="ISSUABLE">ISSUABLE</option><option value="FRESH">FRESH</option><option value="ATM">ATM</option></select></td>'+
                        '<td><input type="text" id="Denomination'+i+'" maxlength="4" minlength="1" class="form-control input-margin" name="Denomination" value="" autofocus></td>'+
                        '<td><input type="text" id="Bundle'+i+'" class="form-control input-margin" name="Bundle" value="" onkeyup="doAjaxPost('+i+')" ></td>'+
                        '<td><input type="text" id="TotalWithFormatter'+i+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
                        '<td><input type="text" id="binNumber'+i+'" class="form-control input-margin" name="Bin" value="" readonly="true" ></td>'+
                        '<td><input type="radio"   name="binOrBox['+i+']" value="PROCESSING" checked="checked"></td><td>Processing</td>'+
                        '<td><input type="radio"  name="binOrBox['+i+']" value="BIN"></td><td>BIN</td>'+
                        '<td><input type="radio"  name="binOrBox['+i+']" value="BOX"></td><td>BOX</td>'+
                        '<td><input type="hidden" id="recordID'+i+'" class="form-control input-margin" name="Bin" value="" ></td>'+
                        '<td class="qr-button"><input type="button" id="print'+i+'" class="btn btn-default qr-button"  name="print"  value="Save AND Print QR" onclick="SavePrint('+i+');this.disabled=true"></td>'+
                        /*'<td><button type="button" class="delete" onclick="deleteRow('+i+')">-</button></td>'+*/
                        '<td><input type="hidden" id="Total'+i+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
                     '</tr>';
                countrow++;
                } 

                    $('#table1').append(html);
        /*            $('#table1').append('<tr id=addmoreButton'+(i-1)+'><td><button type="button"  class="addmoreButton" onclick="replicateValue('+(i-1)+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button"  class="addmoreButton" onclick="addRow('+(i-1)+');doAjaxForTotal()">Add Blank Row</button></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly></td></tr>');
        */            
                }
                
                }
    });
});
    
    
    
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
     '<td><input type="text" id="Denomination'+countrow+'" maxlength="4" minlength="1" class="form-control input-margin" name="Denomination" value="'+denomData+'" autofocus></td>'+
    '<td><input type="text" maxlength="45" id="Bundle'+countrow+'" class="form-control input-margin" name="Bundle" value="'+bundleData+'" onkeyup="doAjaxPost('+countrow+')" ></td>'+
    '<td><input type="text" maxlength="45" id="TotalWithFormatter'+countrow+'"  class="form-control input-margin" name="Total" value="'+totalData+'" readonly="true"></td>'+
    '<td><input type="text" maxlength="45" id="binNumber'+countrow+'" class="form-control input-margin" name="Bin" value="" readonly="true" ></td>'+
    '<td><input type="radio"   name="binOrBox['+countrow+']" value="PROCESSING" checked="checked"></td><td>Processing</td>'+
    '<td><input type="radio"  name="binOrBox['+countrow+']" value="BIN"></td><td>BIN</td>'+
    '<td><input type="radio"  name="binOrBox['+countrow+']" value="BOX"></td><td>BOX</td>'+
    '<td><input type="hidden" id="recordID'+countrow+'" class="form-control input-margin" name="Bin" value="" ></td>'+
    '<td class="qr-button"><input type="button" id="print'+countrow+'" class="btn btn-default qr-button"  name="print"  value="Save AND Print QR" onclick="SavePrint('+countrow+');this.disabled=true"></td>'+
    '<td><button type="button" onclick="deleteRow('+countrow+')">-</button></td>'+
    '<td><input type="hidden"  id="Total'+countrow+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
    '</tr>';
    $('#table1').append(data);
    countrow++;
    $('#table1').append('<tr id=addmoreButton'+rowCount+'><td><button type="button" class="addmoreButton" onclick="replicateValue('+rowCount+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button" class="addmoreButton" onclick="addRow('+rowCount+');doAjaxForTotal()">Add Blank Row</button></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue"  value="" readonly ></td></tr>');
}
    
    
function addRow(i){
    /* countrow++;
    var rowCount = ($('#table1 tr').length)-2; */
    //countrow++;
    //alert(countrow)
     var rowCount = ($('#table1 tr').length)-2;
    countrow=rowCount;
    jQuery('#addmoreButton'+i).remove();
    i++;
    var data = '<tr id="main'+countrow+'">'+
    /* '<td width="15%"><select name="currencyType" id="currencyType'+countrow+'" class="form-control input-margin"><option value="ISSUABLE">ISSUABLE</option><option value="ATM">ATM</option><option value="FRESH">FRESH</option><option value="UNPROCESS">UNPROCESS</option></td>'+ */
    '<td><input type="text" id="Denomination'+countrow+'" maxlength="4" minlength="1" class="form-control input-margin" name="Denomination" value="" autofocus></td>'+
    '<td><input type="text" maxlength="45" id="Bundle'+countrow+'" class="form-control input-margin" name="Bundle" value="" onkeyup="doAjaxPost('+countrow+')" ></td>'+
    '<td><input type="text" maxlength="45" id="TotalWithFormatter'+countrow+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
    '<td><input type="text" maxlength="45" id="binNumber'+countrow+'" class="form-control input-margin" name="Bin" value="" readonly="true" ></td>'+
    '<td><input type="radio"   name="binOrBox['+countrow+']" value="PROCESSING" checked="checked"></td><td>Processing</td>'+
    '<td><input type="radio"  name="binOrBox['+countrow+']" value="BIN"></td><td>BIN</td>'+
    '<td><input type="radio"  name="binOrBox['+countrow+']" value="BOX"></td><td>BOX</td>'+
    '<td><input type="hidden" id="recordID'+countrow+'" class="form-control input-margin" name="Bin" value="" ></td>'+
    '<td class="qr-button"><input type="button" id="print'+countrow+'" class="btn btn-default qr-button"  name="print"  value="Save AND Print QR" onclick="SavePrint('+countrow+');this.disabled=true"></td>'+
    '<td><button type="button" onclick="deleteRow('+countrow+')">-</button></td>'+
    '<td><input type="hidden" maxlength="45" id="Total'+countrow+'"  class="form-control input-margin" name="Total" value="" readonly="true"></td>'+
    '</tr>';
    $('#table1').append(data);
    $('#table1').append('<tr id=addmoreButton'+rowCount+'><td><button type="button" class="addmoreButton" onclick="replicateValue('+rowCount+');doAjaxForTotal()">Replicate Value</button></td><td><button type="button" class="addmoreButton" onclick="addRow('+rowCount+');doAjaxForTotal()">Add Blank Row</button></td><td>Total</td><td><input class="form-control input-margin" type="text" id="totalValue" value="" readonly></td></tr>');

}


function deleteRow(i){
    var MytotalValue=null;
      var fetchTotalValue=null;
    var t1=parseFloat($('#totalValue').val());
    var t2=parseFloat($('#Total'+i).val());
    
    jQuery('#main'+i).remove();
    
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
    
	addHeaderJson();
    var dirv={
        "orderDate":$('#orderDate').val(),
        "rbiOrderNo":$('#rbiOrderNo').val(),
        "expiryDate":$('#expiryDate').val(),
        "bankName":$('#bankName').val(),
        "approvedCC":$('#approvedCC').val(),
        "location":$('#location').val(),
        "denomination":$('#Denomination'+str).val(),
        "bundle":$('#Bundle'+str).val(),
        "currencyType":$('#currencyType'+str).val(),
        "binNumber":$('#binNumber'+str).val(),
        "total":$('#Total'+str).val(),
        "binCategoryType":$('#main'+str).find('input[type=radio]:checked').val(),
        "processedOrUnprocessed":$('input[name=processedOrUnprocessed]:checked').val(),
        "currencyType":$('#Category'+str).val(),
    }
    var regexp = /[^a-zA-Z]/g;
    var notNumber=new RegExp("[^0-9]","g");
    var radioButton=$('#main'+str).find('input[type=radio]:checked').length==0;
    var isValid = true;
    

    if($('#orderDate').val() == "" )
    {
        $('#err1').show();
        isValid = false;
    }
    if($('#expiryDate').val() == "" )
    {
        $('#err3').show();
        isValid = false;
    }
    if($('#rbiOrderNo').val() == "" ||$('#rbiOrderNo').val().length>=45)
    {
        $('#err2').show();
        isValid = false;
    }
    if($('#bankName').val() == "" || $('#bankName').val().length>=45)
    {
        $('#err4').show();
        isValid = false;
    }
    if($('#approvedCC').val() == "" || $('#approvedCC').val().length >=45)
    {
        $('#err5').show();
        isValid = false;
    }
    if($('#location').val() == "" || $('#location').val().length >=45)
    {
        $('#err6').show();
        isValid = false;
    }
    if($('#Denomination'+str).val()!=2000 && $('#Denomination'+str).val()!=1000 && $('#Denomination'+str).val()!=500 && $('#Denomination'+str).val()!=200 && $('#Denomination'+str).val()!=100 && $('#Denomination'+str).val()!=50 && $('#Denomination'+str).val()!=20 && $('#Denomination'+str).val()!=10 && $('#Denomination'+str).val()!=5 && $('#Denomination'+str).val()!=2 && $('#Denomination'+str).val()!=1)
        {
        $('#err7').show();
        isValid = false;
        }
    
    else if($('#Bundle'+str).val() == "")
    {
        
        $('#err8').show();
        isValid = false;
    }
     if($('#Category'+str).val() == "")
    {
         $('#err9').show();
         isValid = false;
    } 
    if(isValid){
    
    $.ajax({
                type : "POST",
                contentType : 'application/json; charset=utf-8',
                  dataType : 'json',
                url : "././dirvQRPath",
                 data: JSON.stringify(dirv),
                success : function(response) {
                    
                    var binNum=response[0];
                     $('#binNumber'+str).val(binNum);
                     
                    //For bin in text Field
                    /* var binNum="";
                    $.each(response,function(index,element){
                        binNum+=element.binNumber;
                         binNum=binNum+",";
                    });
                    $('#binNumber'+str).val(binNum);
                    
                    //For Print Text
                    var str2 = '';
                    $.each(response,function(index,element){
                        str2+= '<div><div style="width:25%;float:left;" ><img src="./files'+element.filepath+'" alt="QRPrint"></div>'
                        str2 += "<div>Bin : "
                            +"<font size=50>"+ element.binNumber+"</font>"
                            + "</br>"
                            + "Deno. :"
                            + element.denomination
                            + "</br>"
                            + "Bundle : "
                            + element.bundle
                            + "</br>"
                            + "Total : "
                            + element.total
                            
                            + "</div></div><BR><HR>";
                    });
                    $('#printSection').html(str2);
                    $('#printSection').show();
                    $.print("#printSection");
                    $('#printSection').hide();
                    //$('#print'+str).prop('disabled', true);
                    $("#member").prop('disabled', true); */
                },
                error : function(e) {
                    //alert('Print Error: ' + e);
                    alert('Error: ' + e.responseJSON.message);
                    jQuery('#print'+str).attr("disabled", false);
                }
            });
        }
}
    
function doAjaxPost(str) { 
    
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
                                  }  }
    }  


function doAjaxForTotal() { 
    var j = 0;
    var myTotalValue = null;
    var noOfRows = ($('#table1 tr').length-2);
    for(var p=0; p<noOfRows; p++){
        var Denomination = $('#Denomination'+p).val();
        var Bundle = $('#Bundle'+p).val();
        var total = Denomination*1000*Bundle;
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


</script>



<script type="text/javascript">
function refresh() {
    window.location = '././Dirv';
}
</script>

<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>
<body oncontextmenu="return false;" data-genuitec-lp-enabled="false"
	data-genuitec-file-id="wc1-19"
	data-genuitec-path="/Currency/src/main/webapp/jsp/Dirv.jsp">
	<div id="wrapper" data-genuitec-lp-enabled="false"
		data-genuitec-file-id="wc1-19"
		data-genuitec-path="/Currency/src/main/webapp/jsp/Dirv.jsp">
		<!-- Navigation -->
		<jsp:include page="common.jsp" />

		<div id="page-wrapper">
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<ul>
								<li><a href="././viewDirv"><i class="fa fa-table fa-fw"></i>View
										Diversion</a></li>
							</ul>
							Diversion
						</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-10">
									<!--<form role="form">-->
									<form:form id="" action="" method="post" modelAttribute="user"
										autocomplete="off">

										<div class="col-lg-6 form-group">
											<label>Order Date</label>
											<form:input type="text" path="orderDate" id="orderDate"
												name="orderDate" cssClass="form-control" />
											<label id="err1" style="display: none; color: red">Select
												Order Date</label>
										</div>

										<div class="col-lg-6 form-group">
											<label>RBI Order Number</label>
											<form:input path="rbiOrderNo" id="rbiOrderNo" maxlength="45"
												cssClass="form-control" />
											<label id="err2" style="display: none; color: red">Enter
												RBI Order Number</label>
										</div>
										<div class="col-lg-6 form-group">
											<label>Expiry Date</label>
											<form:input type="text" path="expiryDate" id="expiryDate"
												name="expiryDate" cssClass="form-control" />
											<label id="err3" style="display: none; color: red">Select
												Expiry Date</label>

										</div>
										<div class="col-lg-6 form-group">
											<label>Bank Name</label>
											<form:input path="bankName" id="bankName" name="bankName"
												maxlength="45" cssClass="form-control" />
											<label id="err4" style="display: none; color: red">Enter
												Bank Name Only Character</label>
										</div>

										<div class="col-lg-6 form-group">
											<label>Branch Name</label>
											<form:input path="approvedCC" id="approvedCC"
												name="approvedCC" maxlength="45" cssClass="form-control" />
											<label id="err5" style="display: none; color: red">Enter
												Approved CC Only Character</label>
										</div>
										<div class="col-lg-6 form-group">
											<label>Location</label>
											<form:input path="location" id="location" name="location"
												maxlength="45" cssClass="form-control" />
											<label id="err6" style="display: none; color: red">Enter
												Location Only Character</label>
										</div>

										<div class="form-group">
											<label>Category</label><br>
											<form:radiobutton path="processedOrUnprocessed"
												id="processedOrUnprocessed" name="processedOrUnprocessed"
												checked="true" value="UNPROCESS" />
											<span class="deno-value"><b>UNPROCESS</b></span>
											<form:radiobutton path="processedOrUnprocessed"
												id="processedOrUnprocessed" name="processedOrUnprocessed"
												value="PROCESSED" />
											<span class="deno-value"><b>PROCESSED</b></span>
										</div>
										<div class="form-group">
											<label>Number of Shrink Wraps</label> <input type="text"
												id="member" name="member" value="" maxlength="45"
												class="form-control"><br />
											<div id="container">
												<table id="table1">
													<!-- <tr>
                                                        <td><strong>Denomination</strong></td>
                                                    <td><strong>Bundle</strong></td>
                                                     <td><strong>Category</strong></td>
                                                    <td><strong>Total</strong></td>
                                                    <td><strong>Bin</strong></td>
                                                    </tr> -->
												</table>
												<table id="table2"></table>
											</div>
										</div>
										<!-- <button type="submit" class="btn btn-default" value="Details"
                                            style="width: 99px;">Save All</button>
                                            
                                            <button type="submit" class="btn btn-default" value="Details"
                                            style="width: 99px;">Print All</button> -->

										<div align="right">
											<button type="submit" class="btn btn-default" value="Details"
												style="width: 99px;" onclick="refresh()">Refresh</button>
										</div>

										<label id="err7" style="display: none; color: red">Enter
											Valid Denomination</label>
										<label id="err8" style="display: none; color: red">Enter
											Bundle</label>
										<label id="err9" style="display: none; color: red">Enter
											Category</label>
										<label id="err11" style="display: none; color: red">Enter
											Bin</label>
									</form:form>
								</div>
								<div id="printSection" style="display: none;"></div>
								<!-- /.col-lg-6 (nested) -->
								<div class="col-lg-6"></div>
								<!-- /.col-lg-6 (nested) -->
							</div>
							<!-- /.row (nested) -->
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
        $('#orderDate').datetimepicker({
            format : 'Y-m-d',
        });
        $('#expiryDate').datetimepicker({
            format : 'Y-m-d',
        });
    </script>
	<script type="text/javascript" src="./js/htmlInjection.js"></script>
</body>

</html>
