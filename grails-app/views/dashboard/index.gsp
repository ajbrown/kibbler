<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title></title>
  <meta name="layout" content="main">
  <r:require module="bootstrap-editable"/>
</head>
<body>

<div class="row">

<ul class="nav nav-tabs">
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
        <div class="span3">
            <ul class="nav nav-list nav-stacked" id="people-list-nav">
                <li>
                    <a href="#" data-bind="click: people.showCreate">
                        <i class="icon-plus"></i> Add New
                    </a>
                </li>
                <!-- ko foreach: people.list() -->
                <li data-bind="attr: { 'data-id': id() }, css: { active: $data == $root.people.active }">
                    <a href="#"
                       data-bind="attr: { href: '#people/' + id() }, text: name()"></a>
                </li>
                <!-- /ko -->
            </ul>
        </div>
        <section class="span9 main-section" id="person-info-pane" data-bind="with: people.active()">
            <form id="person-info-form" data-bind="attr: { action: $root.people.activeUrl }">
            <div class="row">
                <h2 data-bind="text: name()"></h2>
            </div>
            <div class="row">
                <div class="span4">
                    <span class="address" data-bind="text: address()"></span>
                    <span class="phone" data-bind="text: 'M: ' + phone()"></span>
                    <span class="email" data-bind="text: 'E: ' + email()"></span>
                </div>
                <div class="span5">
                    <div class="row">
                        <textarea class="span5" name="notes" data-bind="value: notes, autosave: 'notes'"
                                  placeholder="notes"></textarea>
                    </div>
                    <div class="row">
                        <div class="span3">

                            <label class="checkbox">
                                <input type="checkbox" name="adopter"
                                       data-bind="checked: adopter, autosave: 'adopter'"/>
                                adopter
                                <a href="#" data-bind="visible: adopter">(show history)</a>
                            </label>

                            <label class="checkbox">
                                <input type="checkbox" name="foster"
                                       value="1" data-bind="checked: foster, autosave: 'foster'"/>
                                foster
                                <a href="#" data-bind="visible: foster">(show history)</a>
                            </label>

                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="span4">
                    <label class="checkbox pull-right">
                        <input type="checkbox" name="available" data-bind="checked: available, autosave: 'available'"/>
                    Available
                    </label>
                    <h4>Fostering <a href="#" class="small">(add)</a></h4>
                    <table class="table-condensed">
                        <tr>
                            <th>Name</th>
                            <th>Since</th>
                        </tr>
                    </table>

                </div>
                <div class="span5">
                    <h4>History and Documentation  <a href="#" class="small">(add)</a></h4>
                    <table class="table-condensed">
                        <tr>
                            <th>Event</th>
                            <th>Add/Changed By</th>
                            <th>Date</th>
                            <th>Attachement</th>
                        </tr>
                    </table>
                </div>
            </div>
            </form>
        </section>
    </div>

    <!-- Organization Tab -->

    <div class="tab-pane" id="organization">
        <div class="row">
            <div class="span3" style="outline: 1px solid black;">
                Organization
            </div>
            <div class="span9" style="outline: 1px solid black;">
                Home
            </div>
        </div>
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
        this.activeUrl = ko.computed( function() {
            var active = self.active();
            return SERVER_URL + '/people' + ( active ? '/' + active.id() : '');
        }, this );
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
        this.activeUrl  = ko.computed( function() {
            var active = self.active();
            return SERVER_URL + '/pets' + ( active ? '/' + active.id() : '');
        }, this );

        this.createName  = ko.observable();
        this.createType  = ko.observable();
        this.createOrgId = ko.observable();

        this.listSortField     = ko.observable('givenName');
        this.listSortDirection = ko.observable('asc');

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

            $.post( '<g:createLink controller="pets" action="create"/>', submit, function( data ) {
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

            //add the type label computed
            if( typeof pet.typeLabel == 'undefined' ) {
                pet.typeLabel = ko.computed( function() {
                    var sex   = this.sex();
                    var breed = this.breed();
                    if( !breed ) {
                        breed = this.type();
                    }

                    return sex + ' ' + breed;
                }, pet );
            }

            if( typeof pet.statusExtra == 'undefined' ) {
                pet.statusExtra = ko.computed( function() {
                    var status = this.status();
                    if( status == 'adopted' || status == 'fostered' ) {
                        return 'to Not Mike Vick'
                    }
                }, pet );
            }

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
                self.pets.setActive( this.params.pet )
            });

            this.get( "/kibbler/#people/:person", function() {
                self.people.setActive( this.params.person )
            });
        }).run();

    };


    $('.editable').popover({
        html : true,
        content: function() {
            alert('HERE');
            var elem = $(this).attr('data-popover');
            return $('#' + elem ).html();
        }
    });

    ko.applyBindings( new DashboardViewModel() );



})();
</r:script>

</body>
</html>