<!DOCTYPE html>
<html lang="en" ng-app="kibbler">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title><g:layoutTitle default="Kibbler"/></title>
    <meta name="description" content="Kibbler is animal adoption software made simpler.">
    <meta name="author" content="Kibbler, LLC">
    <base href="${g.createLink( uri: '/dashboard/')}'"

    <r:require modules="kibblerJs,externalStyles,slicknav"/>

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
<g:render template="/layouts/navbar"/>

<div class="container animated fadeIn" id="main" style="background:#222">

    <g:layoutBody/>

</div> <!-- ./container-->

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
