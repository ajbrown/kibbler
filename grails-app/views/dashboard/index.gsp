<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title></title>
  <meta name="layout" content="main">
  <r:require module="bootstrap-editable"/>
</head>
<body>

<div class="row">

<ul class="nav nav-tabs" id="tabs">
    <li class="active"><a href="#pets" data-toggle="tab">Pets</a></li>
    <li><a href="#people" data-toggle="tab">People</a></li>
    <li><a href="#organization" data-toggle="tab">Organization</a></li>
</ul>

<div class="tab-content">

    <div class="tab-pane active row" id="pets">
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

        <label data-bind="visible: !orgs.active()">
            <select name="organizationId"
                    data-bind="options: orgs.list, optionsValue: 'id', optionsText: 'name', value: people.createOrgId">
            </select>
        </label>

        <button class="btn btn-primary" data-bind="click: people.create">Create</button>
    </form>

</div>

<div id="modal-create-pet" class="reveal-modal">
    <h1>Create a Pet</h1>

    <form class="form-horizontal">

        <div class="control-group">
            <label class="control-label" for="pet-create-type">Type</label>
            <div class="controls">
                <select name="type" id="pet-create-type" data-bind="value: pets.createType">
                    <option value="cat:female">Dog, Female</option>
                    <option value="cat:male">Dog, Male</option>
                    <option value="cat:female">Cat, Female</option>
                    <option value="cat:male">Cat, Male</option>
                </select>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="pet-create-name">Name</label>
            <div class="controls">
                <input id="pet-create-name" type="text" placeholder="Pet Name" data-bind="value: pets.createName">
            </div>
        </div>

        <div class="control-group" data-bind="visible: !orgs.active()">
            <label class="control-label" for="pet-create-organization">Type</label>
            <div class="controls">
                <select name="type" id="pet-create-organization"
                        data-bind="options: orgs.list, optionsValue: 'id', optionsText: 'name', value: pets.createOrgId">
                </select>
            </div>
        </div>

        <div class="control-group">
            <div class="controls">
                <button type="submit" class="btn btn-primary" data-bind="click: pets.submitCreatePet">Create</button>
            </div>
        </div>

    </form>

</div>

</div>

<r:require module="reveal"/>
<r:script>

(function() {

    var OrganizationsViewModel = function() {
        var self = this;
        var createModalElem = $('#modal-create-org');

        this.active = ko.observableArray();
        this.list = ko.observableArray();
        this.createOrgName = ko.observable('');

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

        self.showCreateModal = function() {
            self.createOrgName('');
            createModalElem.reveal();
        }

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

        this.list = ko.observableArray();

        this.createName     = ko.observable();
        this.createAdopter  = ko.observable();
        this.createFoster   = ko.observable();
        this.createOrgId    = ko.observable();

        this.create = function() {
            var submit = {
                name: self.createName(),
                adopter: self.createAdopter(),
                foster:  self.createFoster(),
                organizationId: self.createOrgId()
            };

            $.post( '<g:createLink controller="people" action="create"/>', submit, function( data ) {
                if( data ) {
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
                });
                return;
            }

            person = $.extend( person, new PersonWrapper( person ) );
            self.active( person );

            $( '[data-id]', peopleNav ).removeClass('active');
            $( '[data-id="' + person.id() + '"]', peopleNav ).addClass('active');
        };

        //Construct
        $.getJSON( '<g:createLink controller="people" action="index"/>',
                function(data) { self.list( ko.mapping.fromJS(data.data) ); }
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

        this.submitAdopt = function() {
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
            alert('Fostering.');
        }

        this.reclaim = function( pet, event ) {
            alert('Reclaiming.');
        }

        this.hold = function( pet, event ) {
            $.post( SERVER_URL + '/pets/' + pet.id() + '/hold', function() {

            });
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
                organizationId:  self.createOrgId()
            }

            $.postJSON( '<g:createLink controller="pets" action="create"/>', submit, function( data ) {
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

        this.sortedList = ko.computed( function() {
            var list  = this.list();
            var field = this.listSortField();
            list.sort( function(a,b) { return a[field] - b[field] } );
            return list;
        }, this );

        this.showCreateModal = function() {
            self.createName('');
            self.createType('');
            createModalElem.reveal();
        };

        this.setActive = function( pet, event ) {
            //If loading the active pet by id, we must load it via ajax.
            if( typeof pet == "string" ) {
                $.getJSON( SERVER_URL + '/pets/' + pet, function( data ) {
                    self.setActive( ko.mapping.fromJS( data.data ) );
                });
                return;
            }

            pet = $.extend( pet, new PetWrapper( pet ) );
            self.active( pet );
            $( '[data-id]', petsNav ).removeClass('active');
            $( '[data-id="' + pet.id() + '"]', petsNav ).addClass('active');
        };

        //Construction
        $.get('<g:createLink controller="pets" action="index"/>',
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
                self.orgs.list( data.organizations );
            }
        }

        //On Construction
        $.getJSON( '<g:createLink controller="user" action="index"/>', function(data) { self.setUser(data); } );

        Sammy( function() {
            this.get( "/kibbler/#pets/:pet", function() {
                $('#pets').tab('show')
                self.pets.setActive( this.params.pet )
            });

            this.get( "/kibbler/#people/:person", function() {
                $('#tabs a[href="#people"]').tab('show')
                self.people.setActive( this.params.person )
            });
        }).run();

    };


    $('.editable').popover({
        html : true,
        content: function() {
            var elem = $(this).attr('data-popover');
            return $('#' + elem ).html();
        }
    });

    ko.applyBindings( new DashboardViewModel() );



})();
</r:script>

</body>
</html>