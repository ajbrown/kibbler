<!DOCTYPE html>
<html lang="en" ng-app="kibblerExternal">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Kibbler</title>
    <meta name="description" content="Kibbler is animal rescue management software made simpler.">
    <meta name="author" content="Kibbler, LLC">
    <base href="${g.createLink( uri: '/')}'"

    <r:require modules="externalStyles,slicknav"/>

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

<body>
    <div class="container animated fadeIn" id="main" ng-cloak ng-view>

        <g:layoutBody/>

    </div> <!-- ./container-->

<r:layoutResources/>

<g:render template="/layouts/uservoice"/>
<g:render template="/layouts/ga"/>
</body>
</html>
