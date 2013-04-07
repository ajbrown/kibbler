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

    application {
        dependsOn 'jquery,knockout,sammy'
        resource url:'js/knockout.mapping.js'
        resource url:'js/knockout.bindings.js'
        resource url: 'js/pretty.js'
        resource url:'js/application.js'
    }
}