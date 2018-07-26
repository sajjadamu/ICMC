<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@include file="include.jsp"%>
<html lang="en">

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<head> 
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="./resources/logo/favicon.ico" type="image/x-icon">
    
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>

    <title>ICICI : ResetPassword</title>

    <!-- Bootstrap Core CSS -->
    <link href="./resources/bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="./resources/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="./resources/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link rel="stylesheet" type="text/css" href="./resources/bower_components/font-awesome/css/font-awesome.min.css">
    
    <link rel="stylesheet" type="text/css" href="./resources/dist/css/style.css">

   
    <script src="./resources/bower_components/jquery/dist/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="./resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="./resources/bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="./resources/dist/js/sb-admin-2.js"></script>
<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script></head>

<body oncontextmenu="return false;" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-185" data-genuitec-path="/Currency/src/main/webapp/jsp/resetPassword.jsp">
    <div class="container" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-185" data-genuitec-path="/Currency/src/main/webapp/jsp/resetPassword.jsp">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading signin">
                    <img src="./resources/logo/logo.png">
                        <!--<h3 class="panel-title">Please Sign In</h3>-->
                    </div>
                    <div class="panel-body">
                 
                    			 <!--<form role="form">-->
									<form:form id="userPage" name="userPage" action="resetPassword" method="post"
										modelAttribute="passwordReset"  autocomplete="off" >
										
									 <div align="center" style="color:white ; background: green;">${successMsg}</div>
									 <div align="center" style="color:white ; background: red;">${errorMsg}</div>
										
										<div class="form-group">
											<form:password path="password" id="password" maxlength="45" name="password" cssClass="form-control" placeholder="Enter new password"/>
												
										</div>
										
										<div class="form-group">
											<form:password path="rePassword" id="repassword" name="repassword" maxlength="45" class="form-control"  placeholder="Confirm password"  />
										</div>
										
										<button type="submit" class="btn btn-lg btn-success btn-block"
											value="Details">Reset Password</button>
									</form:form>
                    </div>
                </div>
                 <a href="././login"> Click here for login</a>
                
            </div>
        </div>
    </div>
    
    
    
    <script type="text/javascript" src="./js/jquery.validate.min.js"></script>
    
    <script type="text/javascript">

    $.validator.addMethod("passRegex", function(value, element) {
	    return this.optional(element) || /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/i.test(value);
	}, "Minimum 8 characters at least 1 Alphabet, 1 Number and 1 Special Character:");
    
     $.validator.addMethod("passRegexMatch", function(value, element) {
    	var first=$("#password").val();
    	var second=$("#repassword").val();
    	
    		 if (first !== second ) {
    		        return false;
    		    } else {
    		        return true;
    		    }
    
    	
	    return this.optional(element) ;
	}, "Password Not match");
    
	 
    	 $(function() {
    	      $("form[name='userPage']").validate({
    	        rules: {
    	        	id:{
    	        		required:true,
    	        	} ,
    	        	password:{
    	        		required:true,
    	        		passRegex:true,
    	        	} ,
    	        	repassword:{
    	        		required:true,
    	        		passRegex:true,
    	        		passRegexMatch:true,
    	        	},
    	        },
    	        messages: {
    	        	id:{
    	        		required:"Please enter your ID",
    	        	} ,
    	        	password:{
    	        		required:"Please enter your Password",
    	        		passRegex:"Your password must contain minimum 8 characters having at least 1 alphabet, 1 number and 1 special character",
    	        	} ,
    	        	repassword: {
    	        		required:"Please enter your Repassword",
    	        		passRegex:"Your password must contain minimum 8 characters having at least 1 alphabet, 1 number and 1 special character",
    	        		passRegexMatch:"Your Passwords Must Match",
    	        	},
    	        },
    	        submitHandler: function(form) {
    	             form.submit();
    	        }
    	      });
    	    });
    
	</script>
<script type="text/javascript" src="./js/htmlInjection.js"></script>   
</body>

</html>