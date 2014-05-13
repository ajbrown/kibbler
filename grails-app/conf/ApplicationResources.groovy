modules = {

    def angularVersion = '1.2.16'

    jquery {
        resource url: 'https://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js'
    }

    bootstrap {
        resource url: 'https://netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css'
        resource url: 'https://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js'
    }

    angularjs {
        resource url: "https://ajax.googleapis.com/ajax/libs/angularjs/${angularVersion}/angular.min.js"
    }

    'angularjs-resource' {
        resource url: "https://ajax.googleapis.com/ajax/libs/angularjs/${angularVersion}/angular-resource.min.js"
    }

    'angularjs-route' {
        resource url: "https://ajax.googleapis.com/ajax/libs/angularjs/${angularVersion}/angular-route.min.js"
    }

    'angularjs-animate' {
        resource url: "https://ajax.googleapis.com/ajax/libs/angularjs/${angularVersion}/angular-animate.min.js"
    }

    'angularjs-loggly-logger' {
        dependsOn 'angularjs'
        resource url: 'https://rawgit.com/ajbrown/angular-loggly-logger/master/angular-loggly-logger.min.js'
    }

    'angularjs-storage' {
        dependsOn 'angularjs'
        resource url: 'js/ngStorage.min.js'
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

    externalStyles {
        dependsOn 'styling,bootstrap'
        resource url:'less/external.less',attrs:[rel: "stylesheet/less", type:'css'], bundle:'bundle_external_style'
    }

    'kibblerjs-core' {
        dependsOn 'angularjs, angularjs-storage, angularjs-resource'
        resource url: 'js/kibbler/config.js',         bundle: 'kibblerjs_core'
        resource url: 'js/kibbler/resources.js',      bundle: 'kibblerjs_core'
        resource url: 'js/kibbler/services.js',       bundle: 'kibblerjs_core'
    }

    kibblerExternalJs {
        dependsOn 'kibblerjs-core, angularjs-route, angularjs-animate'
        resource url: 'js/kibbler/kibbler.external.js'
    }

    kibblerJs {
        dependsOn 'kibblerjs-core, angularjs-route, angularjs-animate'
        resource url: 'js/kibbler/kibbler.js'
    }

    petPages {
        dependsOn 'jquery'
        resource url: '/css/pet-pages.css'
    }
}