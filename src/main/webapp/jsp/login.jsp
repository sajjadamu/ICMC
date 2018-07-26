<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<head>
<meta charset="UTF-8">
<title>ICICI : Login</title>
    
<style type="text/css">
body{ margin:0; padding: 0; font-family: arial; background: #efefe0; }
	.loginheader{ /* Permalink - use to edit and share this gradient: http://colorzilla.com/gradient-editor/#ea912d+0,ac2534+100 */
background: #ea912d; /* Old browsers */
background: -moz-linear-gradient(top, #ea912d 0%, #ac2534 100%); /* FF3.6-15 */
background: -webkit-linear-gradient(top, #ea912d 0%,#ac2534 100%); /* Chrome10-25,Safari5.1-6 */
background: linear-gradient(to bottom, #ea912d 0%,#ac2534 100%); /* W3C, IE10+, FF16+, Chrome26+, Opera12+, Safari7+ */
filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#ea912d', endColorstr='#ac2534',GradientType=0 ); /* IE6-9 */ width: 100%; float: left; }
	.loginpage{ width: 100%; float: left; }
	.loginpage h1{ text-align: center; font-size: 16px; margin:20px 0; }
	.loginlogo { float: left;}
.loginrt {float: right; color: #fff; font-size: 21px; margin-top: 20px; margin-right: 20px;}
.loginmid {font-size: 15px; color: #fff; width: 40%; text-align: right; padding: 25px 0 0 0; float: left;}
.login_main{ width: 100%; float: left; }
.loginbox{ background: url("./resources/logo/logo-bg.png"); width: 327px; height: 349px; margin:0 auto; position: relative; }
.loginredtxt{ color:#800000; text-align: center; font-size: 14px; font-weight: bold;  }
.loginwrap{ list-style-type: none; }
.loginwrap li {list-style-type: none; padding: 0; padding: 10px 14px; box-sizing: border-box;}
.loginwrap label {width: 42%; float: left; text-align: right; color: #0f074f; font-weight: bold; line-height: 26px; margin-right: 10px;}
.loginwrap input[type="text"], .loginwrap input[type="password"] {width: 109px; padding: 5px 7px; border: none; box-sizing: border-box; border-radius: 5px; font-size: 12px;}
.loginwrap input[type="submit"] {border: #0f0f4f 2px solid; width: 100px; border-radius: 20px; padding: 4px 0; color: #0f0f4f; font-weight: bold; background: #ded7bd; display: inline-block; cursor: pointer;}
.loginwrap input[type="submit"]:hover{ border: #000 2px solid; color: #000; background: #fff; }
ul.loginwrap {padding-top: 30%;}
.loginsubmit{ margin-left: 20%; }
.helptxt a{ color: #000; text-decoration: underline; font-weight: 600; }
.helptxt a:hover{ text-decoration: none; color: #0f0f4f; }

}

</style>
</head>
<body>
<div id="wrapper">
	<div class="loginheader">
	<div class="login_main">
		<div class="loginlogo"><img src="./resources/logo/icicibank.gif"></div>
		<div class="loginmid">Vault Management System</div>
<div class="loginrt">  iVault</div>
	</div>
	</div>

	<div class="loginpage">
		<h1>Please enter your Employee ID (NT ID) and NT Password for login</h1>
		<div class="loginbox">
			<ul class="loginwrap">
				<form:form name='loginForm' action="login" method='POST' autocomplete="off">
                	<li><label>NT ID:</label><span><input type="text" id="username" name="username" placeholder="NT Id"></span></li>
					<li><label>NT Password:</label><span><input type="password" id="password" name="password" placeholder="NT Password"></span></li>
	                <li><input type="submit" value="Login" class="loginsubmit"></li>
					<li class="helptxt"><a href="">Help and Support</a></li>
                    <input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
                 </form:form>
			</ul>
		</div>
		<!-- <div class="loginredtxt">This Site is best viewed in Internet Explorer 5.0 & above</div> -->
		<div class="loginredtxt">This Site is Best viewed in Google Chrome and Mozilla Firefox version 3.0 and above (with resolution 1024x768).</div>
	</div>

</div>
</body>
</html>
