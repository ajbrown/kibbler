modules = {
    knockout {
        dependsOn 'jquery'
        resource url: 'http://ajax.aspnetcdn.com/ajax/knockout/knockout-2.2.0.js'
        resource url: '/js/sammy.js'
    }

    'styling' {
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

    application {
        dependsOn 'jquery,knockout,sammy,jStorage,jQueryTimeAgo'
        resource url:'js/knockout.mapping.js'
        resource url:'js/knockout.bindings.js'
        resource url:'js/pretty.js'
        resource url:'js/application.js'
        resource url:'js/viewmodels.js'
    }
}