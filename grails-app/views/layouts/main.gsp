<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<head>
    <meta charset="utf-8">
    <title><g:layoutTitle default="Kibbler"/></title>

    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <style>
    body {
        padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
    }
    </style>

    <script>
        var SERVER_URL = '<g:createLink absolute="true" uri=""/>';
        var SERVER_PATH = '<g:createLink uri=""/>'
    </script>
    <r:require modules="bootstrap,styling,knockout"/>

    <r:layoutResources />

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->


    <!-- Fav and touch icons -->

    <g:layoutHead/>
</head>

<body>

<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container-fluid">
            <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>
            <a class="brand" href="#">Kibbler</a>
            <div class="nav-collapse collapse">
                <p class="navbar-text pull-right">
                    Logged in as <a href="#" class="navbar-link"><sec:username/></a>
                </p>
                <ul class="nav pull-right">
                    <li><a href="#dashboard">Dashboard</a></li>
                    <sec:ifAllGranted roles="ROLE_ADMIN">
                        <li><g:link controller="indexAdmin">Administration</g:link></li>
                    </sec:ifAllGranted>
                    <li><a href="#help">Help</a></li>
                </ul>
            </div><!--/.nav-collapse -->
            <div class="navbar-text dropdown pull-right" id="org-options">
                <a href="#" data-toggle="dropdown" data-bind="visible: orgs.active()">
                    <i class="icon-group"></i>
                    <span id="org-label"></span>
                    <i class="icon-double-angle-down"></i>
                </a>
                <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel" data-bind="foreach: orgs.list()">
                    <li data-bind="css: { disabled : $data == $root.orgs.active }">
                        <a tabindex="-1" href="#"
                           data-bind="text: name, attr: { href: '#organization/' + id() }"></a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>



<div class="container">

    <g:layoutBody/>

</div> <!-- /container -->




<g:javascript library="application"/>
<r:layoutResources/>

</body>
</html>
