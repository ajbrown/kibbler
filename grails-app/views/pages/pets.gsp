<%--
  Created by IntelliJ IDEA.
  User: ajbrown
  Date: 5/29/13
  Time: 10:33 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title></title>
  <meta name="layout" content="public">
  <r:require module="coverflow"/>

</head>
<body>

<header class="public-header">
    <h1>${pet.givenName}</h1>
    <p class="lead">${pet.breed} (${pet.sex})</p>
</header>

<section class="container">
    <div class="span3" id="public-pet-org-summary">
        <h4>${pet.organization.name}</h4>
        <p>${pet.organization.description}</p>
    </div>
    <div class="span6" id="public-pet-main" style="">

        <div id="pet-photos-carousel" style="width:300px;margin:0 auto;"> </div>

        <p style="margin-top: 12px;">${pet.description}</p>

    </div>
    <div class="span2" id="public-pet-right">
        <div class="">
            <ul class="nav nav-tabs nav-stacked">
                <li> <a href="#">Apply to Adopt</a> </li>
                <li> <a href="#">Share</a> </li>
                <li> <a href="#">Contact</a> </li>
            </ul>
        </div>

        <div class="pet-options">
            <% fields = ['heartworm','housebroken','microchipped','neutered','specialNeeds'] %>
            <ul class="nav">
            <g:each in="${fields.findAll{ pet[it] }}">
                <li><b class="icon-check"></b> <g:message code="pet.fields.${it}" default="${it}"/></li>
            </g:each>
            </ul>
        </div>
    </div>
</section>

<r:script>
    (function() {
        $('#pet-photos-carousel').coverflow({
           flash: '<g:createLinkTo file="js/coverflow/coverflow.swf"/>',
           backgroundcolor: 'FFFFFF',
           playlist: [
                <g:each in="${pet.photos}" status="i" var="it">
                    {"image": "${it.standardUrl}", "title" : "${it.pet.givenName}"},
                </g:each>
           ],
            coverwidth: 250,
            coverheight: 250,
            coverdepth: 200,
            covergap: 60,
            fixedsize: false,
            textoffset: 50,
            showtext: false,
            item: ${ pet.photos.size() > 2 ? '1' : '0' }
        });
    })();
</r:script>

</body>
</html>