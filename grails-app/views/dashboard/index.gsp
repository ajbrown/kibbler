<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title></title>
  <meta name="layout" content="main">
</head>
<body>


<ul class="nav nav-tabs">
    <li class="active"><a href="#pets" data-toggle="tab">Pets</a></li>
    <li><a href="#people" data-toggle="tab">People</a></li>
    <li><a href="#organization" data-toggle="tab">Organization</a></li>
</ul>

<div class="tab-content">

    <div class="tab-pane active row" id="pets">
        <div class="span3" style="outline: 1px solid black;">
            <ul class="nav nav-list nav-stacked" id="pets-list-nav">
                <li>
                    <a href="#" data-bind="click: pets.showCreateModal">
                        <i class="icon-plus"></i> Add New
                    </a>
                </li>
                <!-- ko foreach: pets.sortedList -->
                <li data-bind="attr: { 'data-id': id }">
                    <a href="#" data-bind="text: givenName, click: $root.pets.setActive"></a>
                </li>
                <!-- /ko -->
            </ul>
        </div>
        <section class="span9 main-section" id="pet-info-pane" data-bind="with: pets.active()">

            <div class="row">
                <div class="span9">
                    <h1 data-bind="text: givenName"></h1>

                </div>
            </div>
            <div class="row">
                <div class="span4">
                    <a class="btn btn-mini" href="#">Upload Photo</a>

                    <form id="status-info-form" data-bind="attr: { action: $root.pets.activeUrl }">
                        <fieldset>
                            <legend data-bind="text: type + ', ' + sex"></legend>
                            <label>Status</label>
                            <select name="pet-status" id="pet-status">
                                <option value="adopted">Adopted</option>
                            </select>

                            <label class="checkbox">
                                <input type="checkbox" name="heartworm"
                                       data-bind="checked: vitals.heartworm, autosave: { field: 'vitals'}">
                                Heartworm
                            </label>
                            <label class="checkbox">
                                <input type="checkbox" name="vitals.housebroken"
                                       data-bind="checked: vitals.housebroken, autosave: { field: 'vitals' }">
                                Housebroken
                            </label>
                            <label class="checkbox">
                                <input type="checkbox" name="vitals.microchipped"
                                       data-bind="checked: vitals.microchipped, autosave: { field: 'vitals' }">
                                Microchipped
                            </label>
                            <label class="checkbox">
                                <input type="checkbox" name="vitals.neutered"
                                       data-bind="checked: vitals.neutered, autosave: { field: 'vitals' }">
                                <span data-bind="text: sex == 'male' ? 'Neutered' : 'Spayed'">Spayed/Neutered</span>
                            </label>
                            <label class="checkbox">
                                <input type="checkbox" name="specialNeeds"
                                       data-bind="checked: vitals.specialNeeds">
                                Specials
                            </label>

                            <label>Notes</label>
                            <textarea name="notes"
                                      data-bind="value: notes, autosave: { trigger: 'keypress', delay: 5000 }">
                                      </textarea>

                        </fieldset>
                    </form>
                </div>
                <div class="span5" style="outline: 1px solid blue;">
                    <label for="pet-description"> Description </label><br/>
                    <textarea
                            id="pet-description"
                            name="description"
                            class="stretch-width"
                            data-bind="value: description, autosave: description"></textarea>
                </div>
            </div>

        </section>
    </div>

    <div class="tab-pane" id="people">
        <div class="span3" style="outline: 1px solid black;">
            People
        </div>
        <div class="span9" style="outline: 1px solid black;">
            Home
        </div>
    </div>

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

<r:require module="reveal"/>
<r:script>

(function() {

    var queueAutosave = function( group ) {

    }

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

    var PetsViewModel = function() {
        var self = this;

        var createModalElem = $('#modal-create-pet');
        var petsNav         = $('#pets-list-nav');

        this.list       = ko.observableArray();
        this.active     = ko.observable();
        this.activeUrl  = ko.computed( function() {
            var active = self.active();
            return SERVER_URL + '/pets' + ( active ? '/' + active.id : '');
        }, this );

        this.createName  = ko.observable();
        this.createType  = ko.observable();
        this.createOrgId = ko.observable();

        this.listSortField     = ko.observable('givenName');
        this.listSortDirection = ko.observable('asc');

        this.submitCreatePet = function() {
            var submit = {
                name: self.createName(),
                type: self.createType(),
                organizationId:  self.createOrgId()
            }

            $.post( '<g:createLink controller="pets" action="create"/>', submit, function( data ) {
                if( data ) {
                    self.list.push( data.data );
                    self.setActive( data.data );
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
            self.active( pet );
            $( '[data-id]', petsNav ).removeClass('active');
            $( '[data-id="' + pet.id + '"]', petsNav ).addClass('active');
        };

        //Construction
        $.get('<g:createLink controller="pets" action="index"/>', function( data ) {
            self.list( data.data );
        });
    };

    var DashboardViewModel = function() {
        var self = this;

        this.orgs = new OrganizationsViewModel();
        this.pets = new PetsViewModel();
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

    };

    ko.applyBindings( new DashboardViewModel() );



})();
</r:script>

</body>
</html>