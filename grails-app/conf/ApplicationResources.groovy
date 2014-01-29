modules = {

    jquery {
        resource url: 'https://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js'
    }

    bootstrap {
        resource url: 'https://netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css'
        resource url: 'https://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js'
    }

    angularjs {
        resource url: 'https://ajax.googleapis.com/ajax/libs/angularjs/1.2.10/angular.min.js'
    }

    'angularjs-route' {
        resource url: 'https://ajax.googleapis.com/ajax/libs/angularjs/1.2.10/angular-route.min.js'
    }

    'angularjs-animate' {
        resource url: 'https://ajax.googleapis.com/ajax/libs/angularjs/1.2.10/angular-animate.min.js'
    }

    modernizr {
        resource url: 'https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.7.1/modernizr.min.js'
    }

    'font-awesome' {
        resource url: 'https://netdna.bootstrapcdn.com/font-awesome/4.0.1/css/font-awesome.css'
    }

    styling {
        resource url:'less/styles.less',attrs:[rel: "stylesheet/less", type:'css'], bundle: 'bundle_styling'
        resource url:'css/styles.css'
    }

    colorbox {
        resource url: '/js/colorbox/jquery.colorbox-min.js'
    }

    reveal {
        dependsOn "jquery"
        resource url: '/js/reveal/jquery.reveal.js'
        resource url: '/js/reveal/reveal.css'
    }

    sammy {
        dependsOn 'jquery'
        url: '/js/sammy.js'
    }

    'bootstrap-editable' {
        dependsOn 'jquery'
        resource url: 'js/bootstrap-editable/js/bootstrap-editable.min.js'
        resource url: 'js/bootstrap-editable/css/bootstrap-editable.css'
    }

    googleJs {
        url: 'https://www.google.com/jsapi'
    }

    signaturePad {
        dependsOn 'jquery'
        resource url: '/js/signature-pad/jquery.signaturepad.min.js', disposition: 'head'
        resource url: '/js/signature-pad/jquery.signaturepad.css'
    }

    jQueryVegas {
        dependsOn 'jquery'
        resource url: '/js/jquery.vegas/jquery.vegas.min.js'
        resource url: '/js/jquery.vegas/jquery.vegas.min.js'
    }

    jStorage {
        dependsOn 'jquery'
        resource url: '/js/jstorage.min.js'
    }

    jQueryTimeAgo {
        dependsOn 'jquery'
        resource url: '/js/jquery.timeago.js'
    }

    coverflow {
        dependsOn 'jquery'
        resource url:'/js/coverflow/coverflow.css'
        resource url:'/js/coverflow/coverflow.js'
    }

    flippant {
        resource url: '/js/flippant.min.js'
    }

    jSignature {
        dependsOn 'jquery'
        resource url: 'js/jSignature/jSignature.min.js'
    }

    slicknav {
        dependsOn 'jquery'
        resource url: 'js/sliknav/jquery.slicknav.min.js'
        resource url: 'js/sliknav/slicknav.css'
    }

    application {
        dependsOn 'jquery,jStorage,jQueryTimeAgo'
        resource url:'js/pretty.js'
        resource url:'js/application.js'
        resource url:'js/viewmodels.js'
    }

    externalStyles {
        dependsOn 'styling,bootstrap'
        resource url:'less/external.less',attrs:[rel: "stylesheet/less", type:'css'], bundle:'bundle_external_style'
    }

    externalApp {
        dependsOn 'angularjs,angularjs-route,angularjs-animate'
        resource url: 'js/external/app.js', bundle: 'bundle_external_angular'
    }

    petPages {
        dependsOn 'jquery'
        resource url: '/css/pet-pages.css'
    }
}