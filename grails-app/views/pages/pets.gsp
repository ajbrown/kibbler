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

  <r:require modules="coverflow,reveal"/>

   <style>
#wrap > .container {
    padding: 60px 15px 0;
}
.container .credit {
    margin: 20px 0;
}

#footer > .container {
    padding-left: 15px;
    padding-right: 15px;
}

code {
    font-size: 80%;
}
   </style>

</head>
<body>

<header class="public-header">
    <div>
        <h1 class="media-heading">Meet "${pet.name}."</h1>
        <ul class="nav nav-pills pull-right">
            <li> <a href="#" id="apply-button">Apply to Adopt</a> </li>
            <li> <a href="#" id="share-button">Share</a> </li>
            <li> <a href="#about-${pet.organization.slug}" id="about-button">About ${pet.organization.name}</a></li>
        </ul>
    </div>
    <p class="lead">A ${pet.age ? pet.age + " y/o" : "" } ${pet.sex.capitalize() ?: ''} ${pet.breed}.</p>
</header>


<section class="container" id="photos-${pet.slug}">

<g:if test="${pet.photos}">
        <div id="pet-photos-carousel" style="width:300px;margin:0 auto;"> </div>


    </div>
</section>
</g:if>

<section class="container" id="about-${pet.slug}" role="main">
    <h3>About ${pet.name}</h3>

    <p style="margin-top: 12px;">${pet.description}</p>

    <div class="pet-options">
        <% fields = ['heartworm','housebroken','microchipped','neutered','specialNeeds'] %>
        <ul class="nav">
            <g:each in="${fields.findAll{ pet[it] }}">
                <li><b class="icon-check"></b> <g:message code="pet.fields.${it}" default="${it}"/></li>
            </g:each>
        </ul>
    </div>
</section>

<section class="container" id="about-${pet.organization.slug}">
    <h3>About ${pet.organization.name}</h3>

    <g:if test="${pet.organization.description}">
        <p>${pet.organization.description}</p>
    </g:if>
    <g:else>
        <p>${pet.organization.name} is using Kibbler to manage their lovable animals.  </p>
    </g:else>

</section>

<!-- Pet Adoption Modal -->
<div class="reveal-modal" id="apply-adopt-modal">

    <legend data-bind="">Apply for Adoption</legend>

    <g:form
            controller="pages" action="apply" params="[slug:pet.slug]" method="post"
            name="adoption-form" class="form" role="form">

        <div class="alert" style="display:none;"></div>

        <p>To apply to adopt ${pet.name}, please fill out the information below.  You will be contacted by a
        representative shortly.</p>

        <div class="form-group">
            <label for="adoption-form-name">What's your <strong>name</strong>?</label>
            <input class="form-control" type="text" name="name" id="adoption-form-name"/>
        </div>

        <div class="form-group">
            <label for="adoption-form-name">What's your <strong>phone number</strong>?</label>
            <input class="form-control" type="text" name="phone" id="adoption-form-phone"/>
        </div>

        <div class="form-group">
            <label for="adoption-form-email">What's your <strong>email address</strong>?</label>
            <input class="form-control" type="text" name="email" id="adoption-form-email"/>
        </div>

        <hr/>

        <button type="submit" class="btn btn-primary">Submit Application</button>

        <a class="pull-right btn btn-default" onclick="$('#apply-adopt-modal').trigger('reveal:close')">Cancel</a>

        <input type="hidden" name="petId" value="${pet.id}"/>

    </g:form>
</div>

<r:script>
    (function() {

        $( '#apply-button' ).on( 'click', function( event ) {
            event.preventDefault();
            $('#apply-adopt-modal').reveal();
        });

        $( '#adoption-form' ).on( 'submit', function( event ) {
            var $this = $(this);
            event.preventDefault();
            $.post( $this.attr('action'), $this.serialize() )
                .done( function() {
                    $('#adoption-form .alert')
                        .removeClass( 'alert-error')
                        .addClass( 'alert-success' )
                        .text( 'Your application has been submitted!' )
                        .fadeIn()
                        ;

                    $('#apply-adopt-modal').delay( 5000 ).trigger( 'reveal:close' );
                })
                .fail( function() {
                    $('#adoption-form .alert')
                        .removeClass( 'alert-error' )
                        .addClass( 'alert-success')
                        .text( 'There was a problem submitting your application.  Please try again.' )
                        .fadeIn()
                        ;

                });
        });

        $('#pet-photos-carousel').coverflow({
           flash: '<g:createLinkTo file="js/coverflow/coverflow.swf"/>',
           backgroundcolor: 'FFFFFF',
           playlist: [
                <g:each in="${pet.photos}" status="i" var="it">
                    {"image": "${it.standardUrl}", "title" : "${it.pet.name}"},
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