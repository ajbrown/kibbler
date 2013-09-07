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

    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css">

    <!-- Latest compiled and minified JavaScript -->
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>


    <script>
        var SERVER_URL = '<g:createLink absolute="true" uri=""/>';
        var SERVER_PATH = '<g:createLink uri=""/>'
    </script>
    <r:require module="petPages"/>

    <r:layoutResources />

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->


    <!-- Fav and touch icons -->

    <g:layoutHead/>
</head>

<body>


<div id="wrap">

    <!-- Begin page content -->
    <div class="container">
    <g:layoutBody/>
    </div>
</div>

<div id="footer">
    <div class="container">
        <p class="text-muted credit">Copyright &copy; 2013 Kibbler.com, LLC.  <a href="#">Get your account today.</a>
        </p>
    </div>
</div>

<r:layoutResources/>

</body>
</html>