<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title></title>
  <meta name="layout" content="main">
</head>
<body>


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

<r:require module="reveal"/>
<r:script>
$(function() {

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

    var DashboardViewModel = function() {
        var self = this;

        this.orgs = new OrganizationsViewModel();
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

});
</r:script>

</body>
</html>