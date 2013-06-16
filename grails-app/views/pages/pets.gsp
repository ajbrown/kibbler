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

</head>
<body>

<header class="public-header">
    <h1>${pet.givenName}</h1>
    <p class="lead">${pet.breed} (${pet.sex})</p>
</header>

<section class="container">
    <div class="span3" id="public-pet-org-summary">
        <h3>${pet.organization.name}</h3>
        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer fringilla, nulla in pharetra pulvinar, quam mauris imperdiet ipsum, vel porta sem magna malesuada leo. In nulla turpis, fringilla nec suscipit non, cursus ac eros. Sed accumsan accumsan interdum. Nulla aliquam orci non odio aliquet pellentesque. Donec quis ligula nec leo scelerisque dapibus. Aenean eu dictum ante, ac placerat nulla. Sed vestibulum sem at ipsum faucibus, vitae lobortis orci ultrices. Duis eget blandit elit, ut facilisis sapien.</p>
    </div>
    <div class="span6" id="public-pet-main" style="">

        <div id="pet-photos-carousel" class="carousel slide" style="width:300px;margin:0 auto;">

            <div class="carousel-inner">
                <% pet.photos.eachWithIndex{ photo, i -> %>
                <div class="item ${i == 0 ? 'active' : ''}">
                    <img src="${photo.mediumUrl}" alt="">
                </div>
                <% } %>
            </div>

            <a class="carousel-control left" href="#pet-photos-carousel" data-slide="prev">&lsaquo;</a>
            <a class="carousel-control right" href="#pet-photos-carousel" data-slide="next">&rsaquo;</a>

        </div>

        <p>${pet.description}</p>

    </div>
    <div class="span2" id="public-pet-right">
        <div>
            <a href="#" class="btn">Apply to Adopt!</a>
        </div>
        <div>
            <a href="#" class="btn">Share</a>
        </div>
        <div>
            <a href="#" class="btn">Contact</a>
        </div>

        <div class="pet-options">
            <% fields = ['heartworm','housebroken','microchipped','neutered','specialNeeds'] %>
            <ul>
            <g:each in="${fields.findAll{ pet[it] }}">
                <li><b class="icon-check"></b> ${it}</li>
            </g:each>
            </ul>
        </div>
    </div>
</section>

</body>
</html>