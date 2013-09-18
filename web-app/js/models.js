/**
 * Created by ajbrown on 9/2/13.
 */
;
window.kibbler = {};

window.kibbler.Person = function( d ) {
    var self = this;
    var data = d || {};

    //Standard properties
    this.id         = data.id;
    this.slug       = data.slug;
    this.name       = data.name;
    this.company    = data.company;
    this.address    = data.address;
    this.notes      = data.notes;
    this.email      = data.email;
    this.adopter    = data.adopter || false;
    this.foster     = data.foster || false;
    this.available  = data.available;
    this.doNotAdopt = data.doNotAdopt || false;

    //Relations
    this._linkedAccount;

};

window.kibbler.Pet = function( d ) {
    var self = this;
    var data = d || {};

    //Standard properties
    this.id          = data.id;
    this.slug        = data.slug;
    this.name        = data.name;
    this.description = data.description;
    this.notes       = data.notes;
    this.species     = data.species;
    this.sex         = data.sex;
    this.age         = data.age;
    this.heartworm   = data.heartworm;
    this.housebroken = data.housebroken;
    this.microchipped = data.microchipped;
    this.microchipId = data.microchipId;
    this.neutered    = data.neutered;
    this.specialNeeds = data.specialNeeds || false;
    this.status      = data.status || 'available';

    //Relations
    this._adopter    = adopter || null;
    this._foster     = foster  || null;
    this.photos     = photos || [];
};
