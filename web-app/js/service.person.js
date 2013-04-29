var kibbler = kibbler || {};

kibbler.personService = function() {

    var cachePrefix = "pet:";

    this.read = function( id, cache, callback ) {
        cache = typeof cache == 'undefined' ? true : cache;
        var pet = cache ? $.jStorage.get( cachePrefix + id ) : null;

        if( !pet ) {

        }
    }


}