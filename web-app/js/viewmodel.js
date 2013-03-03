(function() {

    window.Person = function( data ) {
        var self = this;

        this.id   = ko.observable('');
        this.name = ko.observable('');
        this.company = ko.observable('');
        this.address = ko.observable('');
        this.notes   = ko.observable('');
        this.phone   = ko.observable('');
        this.email   = ko.observable('');
        this.adopter = ko.observable(false);
        this.foster  = ko.observable(false);
        this.available = ko.observable(true);
        this.doNotAdopt = ko.observable(false);

        this.data = ko.computed(function() {
            var data = {};
            for( var i in self ) {
                if( i == 'data' ) { continue; }
                data[i] = self[i]();
            }
            return data;
        }, this );

        if($.isPlainObject( data ) || $.isArray( data ) ) {
            for( var i in data ) {
                if( ko.isObservable( self[i] ) ) {
                    this[i]( data[i] )
                }
            }
        }
    }

})();