/**
 *
 * Created with IntelliJ IDEA.
 * User: ajbrown
 * Date: 6/5/13
 * Time: 7:11 PM
 * To change this template use File | Settings | File Templates.
 */

var CreatePetDialogue = function( parent ) {
    var self = this;

    this.species = ko.observable('DOG');
    this.sex = ko.observable('');
    this.breed = ko.observable('');
    this.name = ko.observable('');

    this.reset = function() {
        self.species('DOG');
        self.sex('');
        self.breed('');
        self.name('');
    }

    this.orgId = ko.computed( function() {
        var active = parent.orgs.active();
        if( active ) {
            return ko.utils.unwrapObservable( active.id );
        }
    }, this );

    this.isMale = ko.computed({
        read: function() { return this.sex() == 'male'; },
        write: function(value) { if( value ) { this.sex('male') } },
        owner: this
    });

    this.isFemale = ko.computed({
        read: function() { return this.sex() == 'female'; },
        write: function(value) { if( value ) { this.sex('female') } },
        owner: this
    });

    this.suggestName = function( pet ) {
        var species = pet && pet.species ? pet.species() : null
        var sex     = pet && pet.sex ? pet.sex() : null

        if( species && sex ) {
            var params = { species: species, sex: sex }

            $.getJSON( SERVER_URL + '/suggestions/name', params, function( data ) {
                pet.name( data.data );
            } );
        }
    }

    this.breedSource = function( query, process ) {
        var params = { "species": self.species(), "query": query };
        $.getJSON( SERVER_URL + '/suggestions/breed', params, function(data) {
            if( data.status == 200 ) {
                process( data.data )
            }
        });
    }

    this.submit = function( pet ) {
        var petData = JSON.parse( ko.mapping.toJSON( pet ) );

        $.post( SERVER_URL + '/pets/create', petData, function(data) {
            if( data.status == 200 ) {
                var resp = ko.mapping.fromJS( data.data );
                $('.reveal-modal').trigger('reveal:close');
                parent.pets.list.push( resp );
                parent.pets.setActive( resp );
            }

        }).fail( function() {
            //TODO handle failures gracefully.
            alert( 'There was a problem creating the new pet.' );
        });

    }
}