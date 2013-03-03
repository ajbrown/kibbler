/**
 *
 * Created with IntelliJ IDEA.
 * User: ajbrown
 * Date: 2/18/13
 * Time: 3:51 PM
 * To change this template use File | Settings | File Templates.
 */


(function() {

    var __autosaveTimeouts = {};
    var __autosaveDefaults = {
        event: 'change',
        delay: 1000
    }

    ko.bindingHandlers.autosave = {
        init: function( elem, value, allBindings, viewModel, bindingContext ) {

            var val = value();
            if( typeof val == 'string' ) {
                val = { field: val };
            } else if( typeof val == 'boolean' ) {
                val = { field: $(elem).attr('name') };
            }
            var config = $.extend( __autosaveDefaults, val );

            var eventFunc = function () {
                var config = $.extend( __autosaveDefaults, val );
                console.log( config );
                var href = $(elem).parents( "form").attr('action');
                var timeOutKey = href + config.field;
                var func = function() {
                    delete __autosaveTimeouts[ timeOutKey ]
                    console.log("running");
                    var data = {};
                    data[config.field] = ko.toJS( bindingContext.$data )[config.field];
                    $.ajax( href, {
                            async: true,
                            data: ko.toJSON( data),
                            contentType: 'application/json',
                            dataType: 'json',
                            type: 'POST' }
                    );
                }

                //Set a timeout to run the update function after the configured delay.  Only do one update per group and field.
                window.clearTimeout( __autosaveTimeouts[ timeOutKey ] );
               __autosaveTimeouts[ timeOutKey ] = window.setTimeout( func, config.delay );
            };

            //Register the event handler from the configuration
            ko.utils.registerEventHandler(elem, config.event, eventFunc);

            //If the element is a text area not configured for 'change', we also want to register the autosave on change to be safe.
            if( elem.nodeName == "TEXTAREA" ) {
                ko.utils.registerEventHandler( elem, "change", eventFunc );
            }

        }
    };

})();