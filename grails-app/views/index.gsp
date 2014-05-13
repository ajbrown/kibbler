<!DOCTYPE html>
<html lang="en" ng-app="kibbler">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Animal Adoption Software (Made Simpler!) &middot; Kibbler</title>

    <base href="${g.createLink( uri: '/')}">

    <r:require modules="kibblerJs,kibblerStyles"/>

    <r:layoutResources />

    <style>
    body {
        margin-top: 60px;
    }
    </style>

</head>

<body ng-controller="MainCtrl">
    <g:render template="/layouts/navbar"/>
    <div class="container" id="main-container" class="animated slideInLeft">
        <div ng-show="$storage.user" class="inner-menu panel animated slideInLeft pull-left">
            Side Panel
        </div>

        <div ng-view/>
    </div>

    <r:require module="jquery"/>
    <r:script>
    (function() {
        //choose a random bg
        var bgClasses = ['bg1', 'bg2'];
        var c = bgClasses[Math.floor((Math.random()*bgClasses.length)+1)-1];
        $('body').addClass( c );
    })();
    </r:script>

    <r:layoutResources/>

    <g:render template="/layouts/uservoice"/>
    <g:render template="/layouts/ga"/>
</body>
</html>
