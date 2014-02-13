/**
 *
 * Created by ajbrown on 1/29/14.
 */
; var app = angular.module('kibbler', ['ngRoute']);


/**
 * Configure routes
 */
app.config(['$routeProvider', function( $routeProvider) {
    $routeProvider
        .when( '/profile',          { controller: 'ProfileCtrl' } )
        .when( '/:organization',    { controller: 'OrganizationCtrl' } )
        .when( '/:organization/pets/:pet', { controller: 'PetCtrl', templateUrl: '' } )
        .when( '/:organization/people/:person', { controller: 'PersonCtrl', templateUrl: '' } )
}]);

app.controller( 'DashboardCtrl',
    [ '$scope','UserService','OrganizationService', function( $scope, userService, orgService ) {

        $scope.user = {};

        //Load the current user.
        userService.current().then( function(user){ $scope.user = angular.copy(user) });

    }

    ]);


app.service( 'OrganizationService',
    ['$http', function( $http ) {

        var restUrl = 'api/v1/organizations';

        /**
         * A common function for promises that should just return the
         */
        var httpSuccessHandler = function( data ) { return data.data };

        /**
         * Returns a promise which will provide a list of all organizations the current user belongs to.
         */
        this.list = function() {
            return $http.get( restUrl ).then( httpSuccessHandler );
        };

        /**
         * Read a single organization.  Note that the user must have access to the organization in order to read it.
         * @param id
         * @returns {*}
         */
        this.read = function( id ) {
            return $http.get( restUrl + '/' + id ).then( httpSuccessHandler );
        };
    }

]);

app.service( 'UserService',
    ['$http', function( $http ) {
        var self = this;
        var restUrl = 'api/v1/users';

        /**
         * Read the current user from the api.
         */
        this.current = function() {
            return self.read( 'me' );
        }

        /**
         * Read a single user from the API
         * @param id
         * @returns {*}
         */
        this.read = function( id ) {
            return $http.get( restUrl + '/' + id).then( function(d){ return d.data } );
        }

        /**
         * List all organizations the specified user belongs to.
         * @param userId
         * @returns {*}
         */
        this.listOrganizations = function( userId ) {
            return $http.get( restUrl + '/' + userId + '/organizations').then( function(d) { return d.data } );
        }
    }
]);
