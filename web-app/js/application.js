/*
 decimal_sep: character used as deciaml separtor, it defaults to '.' when omitted
 thousands_sep: char used as thousands separator, it defaults to ',' when omitted
 */
Number.prototype.toMoney = function(decimals, decimal_sep, thousands_sep)
{
    var n = this,
        c = isNaN(decimals) ? 2 : Math.abs(decimals), //if decimal is zero we must take it, it means user does not want to show any decimal
        d = decimal_sep || '.', //if no decimal separator is passed we use the dot as default decimal separator (we MUST use a decimal separator)

    /*
     according to [http://stackoverflow.com/questions/411352/how-best-to-determine-if-an-argument-is-not-sent-to-the-javascript-function]
     the fastest way to check for not defined parameter is to use typeof value === 'undefined'
     rather than doing value === undefined.
     */
        t = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep, //if you don't want to use a thousands separator you can pass empty string as thousands_sep value

        negative = (n < 0);

    //extracting the absolute value of the integer part of the number and converting to string
        i = parseInt(n = Math.abs(n).toFixed(c)) + '',

        j = ((j = i.length) > 3) ? j % 3 : 0;
    return (negative ? '(' : '') + (j ? i.substr(0, j) + t : '') + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t) + (c ? d + Math.abs(n - i).toFixed(c).slice(2) : '') + (negative ? ')' : '');
}

if (typeof jQuery !== 'undefined') {
	(function($) {
		$('#spinner').ajaxStart(function() {
			$(this).fadeIn();
		}).ajaxStop(function() {
			$(this).fadeOut();
		});
	})(jQuery);
}

window.FinanceCategory = function( name, type, scope ) {
    this.name = name;
    this.type = type;
    this.scope = scope;
}

window.PersonWrapper = function( person ) {
    var it = person;
    var self = this;

    this.url = SERVER_URL + '/people/' + person.id();

    this.historyDays = ko.observable( 30 );
    this.historyDays = ko.observable();

    this.update = function( data ) {
        $.ajax( this.url, {
            async: true,
            data: ko.toJSON( data),
            contentType: 'application/json',
            dataType: 'json',
            type: 'POST'
        });
    };

    this.populateHistory = function( days ) {
        var limitDays = days || 30;
        $.getJSON( this.url + '/history', function( data ) {
            if( data.status == 200 ) {
                self.historyDays( days );
                self.historyDays( data.data );
            }
        } );
    }

    this.teamMember = ko.computed( function() {
        var linkedAccount
        if( typeof this.linkedAccount == 'function') {
            linkedAccount = this.linkedAccount()
        } else {
            linkedAccount = this.linkedAccount;
        }
        return linkedAccount ? true : false;
    }, person );

    this.addressFormatted = ko.computed( function() {
        var address = person.address();
        var output  = ''
        output += address ? address.replace("\n", "<br/>\n") + "<br/>\n" : "";

        return output;
    }, person );


    this.toggleFoster = function( p, e ) {
        var foster = p.foster();
        p.foster( !foster );
        this.update( { foster: !foster } );
    };

    this.toggleAdopter = function( p, e ) {
        var adopter = p.adopter();
        p.adopter( !adopter );
        this.update( { adopter: !adopter } );
    }

    this.toggleDoNotAdopt = function( p, e ) {
        var doNotAdopt = p.doNotAdopt();

        if( !doNotAdopt ) {
            var cont = confirm( 'Are you sure you want to add do this blah blah blah' );
            if( !cont ) {
                return
            }

            //TODO submit the do not adopt request, and update the adoption related fields.
        }

        this.update( { doNotAdopt: !doNotAdopt } );
        this.doNotAdopt( !doNotAdopt );
    }

    this.toggleTeamMember = function( p, e ) {
        var teamMember = this.teamMember();
        var name = this.name() || 'this person';
        if( teamMember ) {
            var doit = confirm( 'Yo dawg, you sure you wanna remove ' + name + ' from the organization?  You can\'t undo it brah.' );
            if( doit ) {
                alert( 'K removed' );
            }
        } else {
            alert( 'Ayo, lets add this braj to da organization.' );
            $.ajax( this.url + '/invite', {
                type: 'POST',
                contentType: 'application/json',
                async: true,
                success: function(resp) { p.linkedAccount( ko.mapping.toJS(resp.data.linkedAccount) ) }
            } );
        }
    };

    this.removeAdoption = function( pet, e ) {
        var url = SERVER_URL + '/pets/' + pet.id() + '/reclaim';

        $.ajax( url, {
            type: 'POST',
            contentType: 'application/json',
            async: true,
            success: function( resp ) { it.adopted.remove( pet ); }
        });
    }

    this.removeFoster = function( pet, e ) {
        var url = SERVER_URL + '/pets/' + pet.id() + '/reclaim';

        $.ajax( url, {
            type: 'POST',
            contentType: 'application/json',
            async: true,
            success: function( resp ) { it.fostering.remove( pet ); }
        });
    }
}

window.PetWrapper = function( pet ) {
    var self = this;
    this.url = SERVER_URL + '/pets/' + pet.id();
    this.publicUrl = SERVER_URL + '/pages/pets/' + pet.slug();

    this.update = function( data ) {
        $.ajax( this.url, {
            async: true,
            data: ko.toJSON( data),
            contentType: 'application/json',
            dataType: 'json',
            type: 'POST'
        });
    };

    this.historyDays = ko.observable();
    this.history = ko.observable();

    this.populateHistory = function( days ) {
        var limitDays = days || 30;
        $.getJSON( this.url + '/history', function( data ) {
            if( data.status == 200 ) {
                self.historyDays( days );
                self.history( data.data );
            }
        } );
    };

    this.photoList = ko.computed( function() {
        var ret = [];
        var thumbPrefix = 'http://res.cloudinary.com/hikkwdvwy/image/upload/w_50,h_50,c_thumb,g_faces,r_6/';
        var largePrefix = 'http://res.cloudinary.com/hikkwdvwy/image/upload/w_150,h_150,c_thumb,g_faces,r_10/';
        var photos = pet.photos();

        for( var i in photos ) {
            var p = {};

            p.id = photos[i].id();
            p.primary = photos[i].primary();
            p.thumbnail = thumbPrefix + photos[i].cloudinaryData.public_id() + '.' + photos[i].cloudinaryData.format();
            p.large     = largePrefix + photos[i].cloudinaryData.public_id() + '.' + photos[i].cloudinaryData.format();

            ret.push( p );
        }

        return ret;
    });

    this.thumbnailUrl = ko.computed( function() {
        var photos = pet.photos();
        var primePhoto = 0;
        var prefix = 'http://res.cloudinary.com/hikkwdvwy/image/upload/w_150,h_150,c_thumb,g_faces,r_10/';

        if( !photos || photos.length == 0 ) {
            return null;
        }

        //try to find a primary photo, otherwise just use the first.
        for( var i in photos ) {
            if( photos[i].primary() ) {
                primePhoto = i;
                break;
            }
        }

        var data = photos[primePhoto].cloudinaryData;
        return prefix + data.public_id() + '.' + data.format();
    });

    this.tinyThumbUrl = ko.computed( function() {
        var photos = pet.photos();
        var primePhoto = 0;
        var prefix = 'http://res.cloudinary.com/hikkwdvwy/image/upload/w_78,h_78,c_thumb,g_faces,r_10/';

        if( !photos || photos.length == 0 ) {
            return null;
        }

        //try to find a primary photo, otherwise just use the first.
        for( var i in photos ) {
            if( photos[i].primary() ) {
                primePhoto = i;
                break;
            }
        }

        var data = photos[primePhoto].cloudinaryData;
        return prefix + data.public_id() + '.' + data.format();
    });

    this.typeLabel = ko.computed( function() {
        var sex   = this.sex();
        var breed = this.breed();
        if( !breed ) {
            breed = this.type();
        }

        return sex + ' ' + breed;
    }, pet );

    this.statusLabel = ko.computed( function() {
        var status = this.status();
        var label = '(';
        label += status.charAt(0).toUpperCase() + status.slice(1);

        if( status == 'adopted' ) {
            if( this.adopter ) {
                label += AppService.translateMsgReferences( ' to [person:' + this.adopter.id() + ']', true );
            }

        } else if( status == 'fostered' ) {
            if( this.foster ) {
                label += AppService.translateMsgReferences( ' to [person:' + this.foster.id() + ']', true );
            }
        }

        label += ')';

        return label;
    }, pet );

    this.friendlyDescription = ko.computed( function() {
        var age = this.age();


    }, pet );
}

window.OrgWrapper = function( org ) {
    var self = this;
    console.log( org );
    var id = ko.utils.unwrapObservable( org.id );

    this.url = SERVER_URL + '/organization/' + id;

    this.historyDays = ko.observable();
    this.history = ko.observable();

    this.populateHistory = function( days ) {
        var limitDays = days || 30;
        $.getJSON( this.url + '/history', function( data ) {
            if( data.status == 200 ) {
                self.historyDays( days );
                self.history( data.data );
            }
        } );
    }
};

window.AppService = (function() {

    var models = ['people','pets','organizations'];

    this.activeOrg = null;

    this.readCache = function( what, id, ttl ) {
        if( $.inArray( what, models ) == -1 ) {
            throw new Error( what + ' is not a valid cachable model.' );
        }

        var key = what + ":" + id;

        ttl = ttl || 60000 * 15;    //15 minute default ttl
        var val = $.jStorage.get( key );
        var url = SERVER_URL + '/' + what + '/' + id;
        if( !val ) {
            var resp = JSON.parse($.ajax( { type: 'GET', url: url, async: false, dataType: 'json' }).responseText);
            if( resp && resp.status == 200 ) {
                val = resp.data;
                $.jStorage.set( key, val, {TTL: ttl} );
            }
        }
        return val;
    }

    this.preCache = function( what, obj, ttl ) {
        if( $.inArray( what, models ) == -1 ) {
            throw new Error( what + ' is not a valid cachable model.' );
        }

        ttl = ttl || 60000 * 15;    //15 minute default ttl
        $.jStorage.set( what + ":" + obj.id, obj, {TTL: ttl} );
    }

    this.unCache = function( what, id ) {
        if( $.inArray( what, models ) == -1 ) {
            throw new Error( what + ' is not a valid cachable model.' );
        }

        $.jStorage.deleteKey( what + ":" + id );
    }

    this.formatDate = function( date ) {

    };

    /**
     * Turns object references in message strings into a user friendly reference to the object.
     * For example [user:abcd1234ef12bcd] will be turned into
     * <span class="ref-label user-label" data-ref-type="user" data-ref-type="abcd1234ef12bcd">John Smith</span>".  If the second
     * link parameter equals true, <a> will be used instead of <span>
     * @param msg
     */
    this.translateMsgReferences = function( msg, link ) {
        var refs = msg.match(/\[\w+\:[0-9a-f]{24}\]/g);
        var tag = link ? 'a' : 'span';

        for( var i in refs ) {
            var args, type, id, what;

            args = refs[i].split(':');
            type = args[0].substring(1);
            id   = args[1].substring(0,24);

            switch( type ) {
                case 'pet':          what = 'pets';          break;
                case 'person':       what = 'people';        break;
                case 'organization': what = 'organizations'; break;
            }

            var obj = self.readCache( what, id );

            var url = '#' + what + '/' + id;
            var translated = link ? '<a href="' + url + '" ' : '<span';
            translated += ' class="ref-label ref-' + type + '-label"';
            translated += ' data-ref-type="' + type + '" data-ref-id="' + id + '">';

            var name;

            switch( type ) {
                case 'pet':             name = obj.name;   break;
                case 'person':          name = obj.name;        break;
                case 'organization':    name = obj.name;        break;
            }

            translated += name + '</' + tag + '>';
            msg = msg.replace( refs[i], translated );
        }

        return msg;
    }

    return {
        readCache : this.readCache,
        preCache : this.preCache,
        unCache : this.unCache,
        translateMsgReferences: this.translateMsgReferences
    }
}());