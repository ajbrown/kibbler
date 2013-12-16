(function() {

    window.Pet = function( data ) {
        var self = this;

        this.id          = ko.observable('');
        this.assignedId  = ko.observable('');
        this.slug        = ko.observable('');
        this.name        = ko.observable('');
        this.description = ko.observable('');
        this.species     = ko.observable('');

        this.breed       = ko.observable();
        this.sex         = ko.observable();
        this.age         = ko.observable();
        this.weight      = ko.observable();
        this.markings    = ko.observable();

        this.adopter     = ko.observable();
        this.foster      = ko.observable();

        this.photos      = ko.observableArray();
        this.documents   = ko.observableArray();

        this.heartworm   = ko.observable();
        this.housebroken = ko.observable();
        this.microchipId = ko.observable();
        this.microchipped = ko.observable();
        this.color       = ko.observable();


    };

    window.Person = function( data ) {
        var self = this;

        this.id         = ko.observable('');
        this.name       = ko.observable('');
        this.company    = ko.observable('');
        this.address    = ko.observable('');
        this.notes      = ko.observable('');

        this.phone      = ko.observable('');
        this.email      = ko.observable('');

        this.adopter    = ko.observable(false);
        this.foster     = ko.observable(false);
        this.available  = ko.observable(true);
        this.doNotAdopt = ko.observable(false);

        this.dateCreated    = ko.observable();
        this.lastUpdated    = ko.observable();
        this.lastUpdatedBy  = ko.observable();

        this.fostering      = ko.observableArray();
        this.adopted        = ko.observableArray();
        this.organization   = ko.observableArray();

        this.adopt = function( pet ) {
            if( !typeof pet == Pet ) {

            }
        };

        this.foster = function( pet ) {
            if( !typeof pet == Pet ) {

            }
        };

        this.addToOrganization = function( org ) {
            if( !typeof org == Organization ) {

            }
        };



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