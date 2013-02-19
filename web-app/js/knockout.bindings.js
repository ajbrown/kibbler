/**
 *
 * Created with IntelliJ IDEA.
 * User: ajbrown
 * Date: 2/18/13
 * Time: 3:51 PM
 * To change this template use File | Settings | File Templates.
 */


(function() {

    var __timeouts = {};

    ko.bindingHandlers.autosave = {

        test: 'test',

        init: function( elem, value, allBindings, viewModel, bindingContext ) {

            var allData = bindingContext.$data;
            var defaults = {
                delay: 1000,
                trigger: 'change'
            }
            var _value = value();

            var timeout = null;

            var config = $.extend( {}, defaults, typeof _value =='object' ? _value : {} );
            console.log(config);
            if( !config.field ) {
                config.field = $(elem).attr('name');
            }

            var event = config.trigger + ".autosave";

            $(elem).bind( event, function() {
                var url = $(this).parents('form').attr('action');
                if( __timeouts[this] ) {
                    clearTimeout( __timeouts[this] );
                }

                var submitted = {};
                submitted[ config.field ] = allData[config.field];
                console.log( submitted );

                __timeouts[this] = setTimeout( function() {
                    $.ajax({
                       type: "POST",
                       url: url,
                       contentType: 'application/json',
                       dataType: 'json',
                       data: ko.toJSON(submitted)
                    });
                    delete __timeouts[this];
                }, config.delay );
            } );
        }
    }

})();