<head data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-83"
	data-genuitec-path="/Currency/src/main/webapp/jsp/common.jsp">
<meta name="_csrf" content="${_csrf.token}" />
<script>"undefined"==typeof CODE_LIVE&&(!function(e){var t={nonSecure:"42881",secure:"46091"},c={nonSecure:"http://",secure:"https://"},r={nonSecure:"127.0.0.1",secure:"gapdebug.local.genuitec.com"},n="https:"===window.location.protocol?"secure":"nonSecure";script=e.createElement("script"),script.type="text/javascript",script.async=!0,script.src=c[n]+r[n]+":"+t[n]+"/codelive-assets/bundle.js",e.getElementsByTagName("head")[0].appendChild(script)}(document),CODE_LIVE=!0);</script>
</head>

<script type="text/javascript">
function addHeader(){
	$.ajaxSetup({
	    beforeSend: function (xhr)
	    {
	       xhr.setRequestHeader("X-CSRF-TOKEN",$('meta[name="_csrf"]').attr('content'));        
	    }
	});
}
</script>

<nav class="navbar navbar-default navbar-static-top" role="navigation"
	style="margin-bottom: 0">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse"
			data-target=".navbar-collapse">
			<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
			<span class="icon-bar"></span> <span class="icon-bar"></span>
		</button>
		<a class="navbar-brand logo" href="#"><img
			src="./resources/logo/logo.png"></a>
	</div>

	<!-- /.navbar-header -->
	<div>
		<jsp:include page="logOut.jsp" />
	</div>

	<div class="navbar-default sidebar" role="navigation">
		<div class="sidebar-nav navbar-collapse">
			<ul class="nav" id="side-menu">
				<jsp:include page="Menu.jsp" />
			</ul>
		</div>
		<!-- /.sidebar-collapse -->
	</div>
	<!-- /.navbar-static-side -->
</nav>