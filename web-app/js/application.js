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
    this.url = SERVER_URL + '/people/' + person.id();

    this.update = function( data ) {
        $.ajax( this.url, {
            async: true,
            data: ko.toJSON( data),
            contentType: 'application/json',
            dataType: 'json',
            type: 'POST'
        });
    };

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

    this.removeAdoption = function( id ) {
        alert( 'Removing adoption ' + id );
    }

    this.removeFoster = function( id ) {

        //TODO the reclaim event needs to be a consistent write.  Only
        $.ajax( SERVER_URL + '/pets/')
    }

}

window.PetWrapper = function( pet ) {

    this.url = SERVER_URL + '/pets/' + pet.id();

    this.update = function( data ) {
        $.ajax( this.url, {
            async: true,
            data: ko.toJSON( data),
            contentType: 'application/json',
            dataType: 'json',
            type: 'POST'
        });
    };

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

            var adopter = this.adopter;
            if( adopter ) {
                label += ' to ' + adopter.id();
            }

        } else if( status == 'fostered' ) {

            var foster = this.foster();
            if( foster ) {
                label += ' to ' + this.foster();
            }
        }

        label += ')';

        return label;
    }, pet );
}