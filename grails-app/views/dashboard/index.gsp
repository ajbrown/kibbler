<%--
  Created by IntelliJ IDEA.
  User: ajbrown
  Date: 5/12/14
  Time: 10:02 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kibbler</title>

    <meta name="description" content="Kibbler is animal rescue management software made simpler.">
    <meta name="author" content="Kibbler, LLC">

    <base href="${g.createLink( uri: '')}">

    <r:require modules="kibblerJs, kibblerStyles"/>

    <!-- Hey There!  Want a treat?  Send us an email at treat AT kibbler DOT org with your feedback about
     our user interface, and we'll send you a reward!
    -->

</head>

<body ng-conroller="MainCtrl">
    <g:render template="/layouts/navbar"/>
    <div id="main-container" class="container">
        <div class="inner-menu panel animated slideInLeft pull-left">

        </div>

        <div class="panel-thick animated slideInLeft" ng-view>

        </div>
    </div>
</body>
</html>