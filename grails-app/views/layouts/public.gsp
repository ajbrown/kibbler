<!DOCTYPE html>
<html lang="en" ng-app="kibblerExternal">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Kibbler</title>
    <meta name="description" content="Kibbler is animal rescue management software made simpler.">
    <meta name="author" content="Kibbler, LLC">
    <base href="${g.createLink( uri: '/')}'"

    <r:require modules="kibblerStyles,slicknav"/>

    <r:script disposition="head">
    window.basePath = '${g.createLink( uri: '')}';
    <g:if env="development">
    window.developmentMode = true;
    </g:if>
    </r:script>

    <r:layoutResources />

    <!-- Hey There!  Want a treat?  Send us an email at treat AT kibbler DOT org with your feedback about
     our user interface, and we'll send you a reward!
    -->
</head>

<body class="">
    <div class="container animated fadeIn" id="main" ng-cloak ng-view>

        <g:layoutBody/>

    </div> <!-- ./container-->

<r:require module="jquery"/>
<r:script>
    (function() {
        //choose a random bg
        var bgClasses = ['bg1', 'bg2'];
        var c = bgClasses[Math.floor((Math.random()*bgClasses.length)+1)-1];
        console.log('adding class', c);
        $('body').addClass( c );
    })();
</r:script>

<r:layoutResources/>

<g:render template="/layouts/uservoice"/>
<g:render template="/layouts/ga"/>
</body>
</html>
