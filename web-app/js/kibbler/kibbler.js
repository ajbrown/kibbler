/**
 *
 *
 * Created by ajbrown on 1/29/14.
 */
; (function(angular) {

  angular.module('kibbler', ['ngRoute', 'kibbler.resources', 'kibbler.services'])


  /**
   * Configure routes
   */
    .config(['$routeProvider', function( $routeProvider) {
      $routeProvider
        .when( '/profile',          { controller: 'ProfileCtrl' } )
        .when( '/:organization',    { controller: 'OrganizationCtrl' } )
        .when( '/:organization/pets/:pet', { controller: 'PetCtrl', templateUrl: '' } )
        .when( '/:organization/people/:person', { controller: 'PersonCtrl', templateUrl: '' } )
    }])

  .run([ 'AuthService', '$sessionStorage', '$localStorage', '$log', 'User',
      function( AuthService, $sessionStorage, $localStorage, $log, User ) {

        //If we've got cached authentication details, re-use them.
        AuthService.loginRemembered( function( token ) {
          $log.debug( 'Automatically logged in as', token );
          User.current(null, function( user ) {
            $sessionStorage.user = user;
          });
        });

      }
   ])

  .controller( 'DashboardCtrl',
    [ '$scope','User','Organization', function( $scope, User, Organization ) {

        $scope.user = {};

        //Load the current user.
        userService.current().then( function(user){ $scope.user = angular.copy(user) });

    }

  ])



})(window.angular);
