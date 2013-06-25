<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main">
    <r:require modules="bootstrap-editable"/>
</head>
<body>

<div class="row">

    <ul class="nav nav-tabs" id="tabs">
        <li><a href="#pets" data-toggle="tab">Pets</a></li>
        <li><a href="#people" data-toggle="tab">People</a></li>
        <li><a href="#organization" data-toggle="tab">Organization</a></li>
    </ul>

    <div class="tab-content">

        <div class="tab-pane row" id="pets">
            <g:render template="pets-tab"/>
        </div>

        <!-- People Tab -->

        <div class="tab-pane row" id="people">
            <g:render template="people-tab"/>
        </div>

        <!-- Organization Tab -->

        <div class="tab-pane row" id="organization">
            <g:render template="organization-tab"/>
        </div>

    </div>



    <div id="modal-create-org" class="reveal-modal">
        <h1>Create a new Organization</h1>
        <p class="small">
            <g:message code="modal.create.organization.text"
                       default="You'll need to create a new organization before you can continue."/>
        </p>

        <form class="form-horizontal">
            <input type="text" placeholder="Organization Name" data-bind="value: orgs.createOrgName, valueUpdate:'afterkeydown'"/>
            <button class="btn btn-inverse"
                    data-bind="enable: orgs.createOrgName().trim().length > 0, click: orgs.submitCreateOrg">Create</button>
        </form>
    </div>

    <div id="modal-create-person" class="reveal-modal">
        <h1>Add a Contact</h1>

        <form class="form">
            <label class="">Name</label>
            <input type="text" name="name" data-bind="value: people.createName"/>

            <label class="checkbox">
                <input type="checkbox" name="adopter" data-bind="value: people.createAdopter"> Adopter
            </label>

            <label class="checkbox">
                <input type="checkbox" name="foster" data-bind="value: people.create"> Foster
            </label>

            <button class="btn btn-primary" data-bind="click: people.create">Create</button>
        </form>

    </div>

    <div id="modal-create-pet" class="reveal-modal">
        <h1>Create a Pet</h1>

        <form class="form" data-bind="with: createPet">

            <div class="control-group">
                <label>Species</label>
                <select name="type" id="pet-create-species" data-bind="value: species">
                    <option value=""></option>
                    <option value="DOG">Dog</option>
                    <option value="CAT">Cat</option>
                </select>
            </div>

            <div class="control-group">
                <label>Breed</label>
                <input type="text" name="breed" data-bind="value: breed, typeahead: breedSource">
            </div>


            <div class="control-group">
                <label>Gender</label>
                <label class="radio inline">
                    <input type="radio" name="gender" data-bind="checked: isFemale">
                    <g:message code="label.female" default="Female"/>
                </label>
                <label class="radio inline">
                    <input type="radio" name="gender" value="female" data-bind="checked: isMale">
                    <g:message code="label.male" default="Male"/>
                </label>
            </div>


            <div class="control-group">
                <label>Name</label>
                <input type="text" name="givenName" data-bind="value: givenName">
                <span class="help-inline">
                    <small><a href="#" data-bind="click: suggestName">Pick one for me!</a></small>
                </span>
            </div>

            <hr>
            <button class="btn btn-primary" type="submit"
                    data-bind="text: 'Add ' + givenName(), click: submit"></button>

        </form>

    </div>


</div>

<r:require module="reveal"/>
<r:script>

(function() {

    $('a[data-toggle="tab"]').on( 'shown', function(e) {
        location.href = $(e.target).attr('href');
    });

    var OrganizationsViewModel = function() {
        var self = this;
        var createModalElem = $('#modal-create-org');

        var newTransObj = { selected: ko.observable(), amount: ko.observable('0.00'), pet: ko.observable() };

        this.active = ko.observable();
        this.list = ko.observableArray();
        this.createOrgName = ko.observable('');

        this.newEntryData = ko.observable( newTransObj );

        this.submitNewEntry = function( newEntry, e ) {
            var entryData = self.newEntryData();
            var data = {
                    organizationId: self.active().id(),
                    category: entryData.selected().name,
                    amount: entryData.amount() * (entryData.selected().type == '-' ? -1 : 1),
                    pet: typeof entryData.pet != 'undefined' ? entryData.pet.id() : null
                };

            var conf = {
                type: 'POST',
                data: data,
                success: function( data ) {
                    self.active().transactions.push( data.data );
                }
            };
            $.ajax( SERVER_URL + '/organization/' + self.active().id() + '/transactions', conf );
            $('#modal-org-add-finance').trigger('reveal:close');
        };

        this.financeCategories = ko.observableArray([
            new FinanceCategory( 'Donation', '+', 'organization' ),
            new FinanceCategory( 'Vet Bill', '-', 'pet' ),
            new FinanceCategory( 'Other (Expense)', '-', 'organization' ),
            new FinanceCategory( 'Other (Income)', '+', 'organization' )
        ]);

        self.submitCreateOrg = function() {
            var name = self.createOrgName().trim();
            if( name == '' ) {
                return;
            }

            $.post( '<g:createLink controller="organization" action="create"/>', { 'name': name }, function( data ) {
                if( data ) {
                    self.list.push( data );
                    self.active( data );
                    createModalElem.trigger('reveal:close');
                } else {
                    alert( 'There was an error creating the organization.  Please try again.');
                }
            });
        };

        self.setActive = function( org ) {

            if( typeof org == 'string' ) {
                $.getJSON( SERVER_URL + '/organization/' + org, function( data ) {
                    self.setActive( ko.mapping.fromJS( data.data ) );
                    AppService.preCache( 'organization', data.data );
                });
                return;
            }

            org = $.extend( org, new OrgWrapper( org ) );
            org.populateHistory( 30 );

            self.active( org );
            $('#org-label').text( org.name() );

            $.getJSON( SERVER_URL + '/organization/' + org.id() + '/transactions', function( data ) {
                org.transactions = ko.observableArray( data.data );
                self.active( org );
                AppService.activeOrg = org.id();
                $('#org-label').text( org.name() );
            });


        }

        self.newEntry = function( org, event ) {
            self.newEntryData( newTransObj );
            $('#modal-org-add-finance').reveal();
        };

        self.showCreateModal = function() {
            self.createOrgName('');
            createModalElem.reveal();
        }

        self.displayContractTerms = function() {
            jQuery.get( SERVER_URL + '/organization/' + self.active().id() + '/terms-text', function( text ) {
                jQuery( '#contract-terms' ).val( text );
                jQuery( '#contract-info').show();
            } );
            return true;
        };

    };

    var PeopleViewModel = function() {
        var self = this;

        var createModalElem = $('#modal-create-person');
        var peopleNav       = $('#people-list-nav');

        this.active = ko.observable();

        this.activeId  = ko.computed( function() {
            var active = this.active();
            if( active ) {
                return active.id();
            }
        }, self );

        this.filterFosters     = ko.observable( false );
        this.filterAdopters    = ko.observable( false );
        this.filterDoNotAdopt  = ko.observable( false );

        this.list = ko.observableArray();


        this.listAdopters = ko.computed( function() {
            return ko.utils.arrayFilter( self.list(), function( person ) {
                return person.adopter() && !person.doNotAdopt();
            });
        }, this );

        this.listFosters = ko.computed( function() {
            return ko.utils.arrayFilter( self.list(), function( person ) {
                return person.foster() && !person.doNotAdopt();
            });
        });

        this.listFiltered = ko.computed( function() {
            var list = self.list();
            var adopters = self.filterAdopters();
            var fosters  = self.filterFosters();
            var doNotAdopts = self.filterDoNotAdopt();

            return ko.utils.arrayFilter( list, function( person ) {
                //if no filters are on, display everything
                if( !adopters && !fosters && !doNotAdopts ) { return true; }

                var keep = false;
                keep = keep || (adopters && person.adopter());
                keep = keep || (fosters && person.foster());
                keep = keep || (doNotAdopts && person.doNotAdopt());
                return keep;
            } );
        });
        this.createName     = ko.observable();
        this.createAdopter  = ko.observable();
        this.createFoster   = ko.observable();
        this.createOrgId    = ko.observable();

        this.create = function() {
            var submit = {
                name: self.createName(),
                adopter: self.createAdopter(),
                foster:  self.createFoster(),
                organizationId: AppService.activeOrg
            };

            $.post( SERVER_URL + '/people/create', submit, function( data ) {
                if( data ) {
                    AppService.preCache( 'people', data.data );
                    var person = ko.mapping.fromJS(data.data);
                    self.list.push( person );
                    self.setActive( person );
                    createModalElem.trigger('reveal:close');
                }
            });
        };

        this.editAddress = function() {
            $('#person-edit-address-modal').reveal();
        }

        this.showCreate = function() {
            self.createName('');
            self.createAdopter('');
            self.createFoster('');
            self.createAdopter('');
            createModalElem.reveal();
        }

        this.setActive = function( person, event ) {
            //If loading the active person by id, we must load it via ajax.
            if( typeof person == "string" ) {
                $.getJSON( SERVER_URL + '/people/' + person, function( data ) {
                    self.setActive( ko.mapping.fromJS( data.data ) );
                    AppService.preCache( 'people', data.data );
                });
                return;
            }

            person = $.extend( person, new PersonWrapper( person ) );
            person.populateHistory( 30 );

            self.active( person );

            $( '#tabs a[href="#people"]').tab( 'show' );
            $( '[data-id]', peopleNav ).removeClass('active');
            $( '[data-id="' + person.id() + '"]', peopleNav ).addClass('active');
        };

        //Construct
        $.getJSON( SERVER_URL + '/people',
                function(data) {
                    var items = [];
                    for( var i in data.data ) {
                        items.push( ko.mapping.fromJS( data.data[i] ) );
                    }

                    ko.utils.arrayPushAll( self.list, items );
                }
        );

    };

    var PetsViewModel = function() {
        var self = this;

        var createModalElem = $('#modal-create-pet');
        var petsNav         = $('#pets-list-nav');

        this.list       = ko.observableArray();
        this.active     = ko.observable();

        this.activeId  = ko.computed( function() {
            var active = this.active();
            if( active ) {
                return active.id();
            }
        }, self );

        this.createName  = ko.observable();
        this.createType  = ko.observable();
        this.createOrgId = ko.observable();

        this.listSortField     = ko.observable('givenName');
        this.listSortDirection = ko.observable('asc');

        this.adopt = function( pet, event ) {
            $('#pet-adopt-modal').reveal();
        }

        this.submitAdopt = function( d ) {
            var form = $('form', '#pet-adopt-modal')
            var vals = form.serializeArray();
            var url  = form.attr('action');
            var data = {}
            for( var i in vals ) {
                data[ vals[i].name ] = vals[i].value;
            }
            $.post( url, data, function( resp ) {
                    if( resp.status > 200 && resp.status < 300 ) {
                        self.setActive( ko.mapping.fromJS( resp.data) );
                        $('#pet-adopt-modal').trigger('reveal:close');
                    }
            });
        }

        this.foster = function( pet, event ) {
            $('#pet-foster-modal').reveal();
        }

        this.submitFoster = function( d ) {
            var form = $('form', '#pet-foster-modal')
            var vals = form.serializeArray();
            var url  = form.attr('action');
            var data = {}
            for( var i in vals ) {
                data[ vals[i].name ] = vals[i].value;
            }
            $.post( url, data, function( resp ) {
                if( resp.status == 200 ) {
                    self.setActive( ko.mapping.fromJS( resp.data ) );
                    $('#pet-foster-modal').trigger('reveal:close');
                } else {
                    alert('There was an error fostering this pet.');
                }
            });
        }

        this.reclaim = function( pet, event ) {
            $.post( pet.url + '/reclaim', function(resp) {
                if ( resp.status == 200 ) {
                    self.setActive( ko.mapping.fromJS( resp.data ) );
                }
            } );
        }

        this.hold = function( pet, event ) {
            $.post( SERVER_URL + '/pets/' + pet.id() + '/hold', function( resp ) {
                if ( resp.status == 200 ) {
                    self.setActive( ko.mapping.fromJS( resp.data ) );
                }
            });
        }

        this.uploadPhotos = function( pet, event ) {
            $.post( pet.url + '/photos' )
        }

        this.editBreed = function( pet, event) {
            var $this = $(event.currentTarget);
            $this.popover('show');

        };

        this.editName = function( pet, e ) {

        }

        this.submitCreatePet = function() {
            var submit = {
                name: self.createName(),
                type: self.createType(),
                organizationId:  AppService.activeOrg
            };

            $.post( SERVER_URL + '/pets/create', submit, function( data ) {
                if( data ) {
                    var pet = ko.mapping.fromJS( data.data );
                    self.list.push( pet );
                    self.setActive( pet );
                    createModalElem.trigger('reveal:close');
                } else {
                    alert( 'There was an error creating your pet.  Please try again.' );
                }
            });
        };

        this.showPhotoUpload = function( pet, event ) {
           $('#pet-photo-modal').reveal();
        }

        this.completePhotoUpload = function( data ) {
            var photos = [];
            for( var i in data.data ) {
                photos.push( ko.mapping.fromJS( data.data[i] ) );
            }
            self.active().photos( photos );
           $('#pet-photo-modal').trigger('reveal:close');

        }

        this.showCreateModal = function() {
            self.createName('');
            self.createType('');
            createModalElem.reveal();
        };

        this.setActive = function( pet, event ) {
            //If loading the active pet by id, we must load it via ajax.
            if( typeof pet == "string" ) {
                $.getJSON( SERVER_URL + '/pets/' + pet, function( data ) {
                    AppService.preCache( 'pets', data.data );
                    self.setActive( ko.mapping.fromJS( data.data ) );
                });
                return;
            }

            pet = $.extend( pet, new PetWrapper( pet ) );
            pet.populateHistory( 30 );

            self.active( pet );

            $( '#tabs a[href="#pets"]').tab( 'show' );
            $( '[data-id]', petsNav ).removeClass('active');
            $( '[data-id="' + pet.id() + '"]', petsNav ).addClass('active');
        };

        //Construction
        $.get( SERVER_URL + '/pets',
            function(data) { self.list( ko.mapping.fromJS(data.data) ); }
        );
    };

    var DashboardViewModel = function() {
        var self = this;

        this.orgs   = new OrganizationsViewModel();
        this.pets   = new PetsViewModel();
        this.people = new PeopleViewModel();

        this.user = ko.observable();

        this.setUser = function( data ) {
            self.user( data );
            if( !data.organizations || data.organizations.length == 0 ) {
                self.orgs.showCreateModal();
            } else {
                self.orgs.list( ko.mapping.fromJS( data.organizations ) );
                self.orgs.setActive( ko.mapping.fromJS( data.organizations[0].id ) )
            }
        }

        this.createPet = new CreatePetDialogue( this );


        //On Construction
        $.getJSON( '<g:createLink controller="user" action="index"/>', function(data) { self.setUser(data); } );

        Sammy( function() {

            this.get( SERVER_PATH + "/#pets/:pet", function() {
                $('#pets').tab('show')
                self.pets.setActive( this.params.pet )
            });

            this.get( SERVER_PATH + "/#pets", function() {
                $('#tabs a[href="#pets"]').tab('show')
            } );

            this.get( SERVER_PATH + "/#people", function() {
                $('#tabs a[href="#people"]').tab('show')
            } );

            this.get( SERVER_PATH + "/#people/:person", function() {
                $('#tabs a[href="#people"]').tab('show')
                self.people.setActive( this.params.person )
            });

            this.get( SERVER_PATH + "/#organization", function() {
                $('#tabs a[href="#organization"]').tab('show')
            } );

        }).run();

    };


    $('.editable').popover({
        html : true,
        content: function() {
            var elem = $(this).attr('data-popover');
            return $('#' + elem ).html();
        }
    });

    window.dashboard = new DashboardViewModel();
    ko.applyBindings( window.dashboard );



})();
</r:script>

</body>
</html>