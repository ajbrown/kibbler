modules = {
    knockout {
        dependsOn 'jquery'
        resource url: 'http://ajax.aspnetcdn.com/ajax/knockout/knockout-2.2.0.js'
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

    application {
        dependsOn 'jquery,knockout'
        resource url:'js/knockout.bindings.js'
        resource url:'js/application.js'
    }
}