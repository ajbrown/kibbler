/**
 *
 *
 * Created by ajbrown on 1/29/14.
 */
; (function(angular) {

  angular.module('kibbler',
    ['ngRoute', 'angular-md5', 'kibbler.resources', 'kibbler.services'] )

    .config(['$routeProvider', '$locationProvider', function( $routeProvider, $locationProvider ) {

      $locationProvider.html5Mode( true );

      $routeProvider
        .when( '/activate/:code', {
          templateUrl: '_view/activate',
          controller: 'ActivateCtrl'
        })
        .when( '/reset-password', {
          templateUrl: '_view/reset',
          controller: 'ResetCtrl'
        })
        .when( '/sign-in', {
          templateUrl: '_view/login',
          controller: 'LoginCtrl'
        })
        .when( '/', {
          templateUrl: '_view/dashboard',
          controller: 'DashboardCtrl'
        })
        .when( '/profile', {
          controller: 'ProfileCtrl'
        })
        .when( '/organizations/:organization', {
          controller: 'OrganizationCtrl'
        })
        .when( '/organizations/:organization/pets/:pet', {
          controller: 'PetCtrl',
          templateUrl: ''
        })
        .when( '/organizations/:organization/people/:person', {
          controller: 'PersonCtrl',
          templateUrl: ''
        })
    }])

  .run([ '$rootScope', 'AuthService', '$sessionStorage', '$localStorage', '$log', '$location', 'User',
      function( $rootScope, AuthService, $sessionStorage, $localStorage, $log, $location, User ) {

        $rootScope.$session = $sessionStorage;
        $rootScope.$storage = $localStorage;

        $rootScope.$watch( '$session.user', function( user ) {
          if( !user ) {
            return;
          }

          //get the list of organizations for the user.
        });

        //If we've got cached authentication details, re-use them.
        AuthService.loginRemembered(
          function( token ) {
            User.current(null, function( user ) {
              $sessionStorage.user = user;
              $location.path( '/' );
            });
          },
          function() {
            $log.debug( 'User is not logged in -- redirecting.' )
          }
        );

        //When a user logs in, load thier list of organizations.
        $rootScope.$on( 'user.login', function( event, user ) {
          $rootScope.organizations = User
        });
        $rootScope.isLoggedIn = function() {
          return angular.isObject( $sessionStorage.user );
        }

      }
   ])

  .controller( 'MainCtrl',
    [ '$scope', 'AuthService', '$location',

      function( $scope, AuthService, $location ) {

        $scope.logout = function() {
          AuthService.logout();
          $location.path( '/sign-in' );
        }

      }
  ])

    .controller( 'ActivateCtrl',
      [ '$scope', 'VerificationToken', 'User', 'AuthService', '$location', '$log', '$routeParams',
        function( $scope, VerificationToken, User, AuthService, $location, $log, $routeParams ) {
          $scope.code = $routeParams.code;

          if( !$scope.code ) {
            $log.warn( 'No activation code detected.' );
            $location.path( '/reset-password' );
            return
          }

          //Check the verification token
          if( $scope.code ) {
            $scope.verifyToken = VerificationToken.get( {id: $scope.code} );
            $scope.tokenChecked = true;
          }

          var afterActivateLogin = function( data ) {
            $log.debug( 'Logged in as newly activated user', data );
            $scope.$emit( 'user.loggedIn', data );
            $scope.$session.user = data;
            $location.path('/');
          };

          var activateLoginFailure = function( data ) {
            $log.warn( 'Activation Failed:', data );
            $location.path( '/sign-in' );
          };

          var activateSuccess = function(  user ) {
            AuthService.login( user.email, $scope.password )
              .then( afterActivateLogin, activateLoginFailure )
          };

          var activateFailure = function( data ) {
            $scope.alert = {
              type: 'danger',
              message: 'There was a problem activating your account.  Please try again.  If you continue to have problems, please contact us immediately.'
            }
          };

          //Activate the account
          $scope.activate = function( code, name, password ) {
            User.activate( null, { code: code, name: name, password: password },
              activateSuccess, activateFailure
            );
          }
        }
      ]
    )

  /**
   * Login Controller
   */
  .controller( 'LoginCtrl',
    [ '$scope', 'AuthService', 'User', '$location', '$log',

      function( $scope, AuthService, User, $location, $log ) {

        $log.debug( 'Controller: LoginCtrl' );

        $scope.email    = '';
        $scope.password = '';
        $scope.user = {};
        $scope.loginFailure = '';
        $scope.processing = false;

        $scope.failLogin = function ( reason ) {
          $scope.loginFailure = reason;
        };

        $scope.login = function( username, password, remember ) {

          $scope.processing = true;
          $scope.loginFailure = '';

          var loginSuccess = function( token ) {
            $log.debug( 'Logged in successfully!' );
            $scope.processing = false;
            $location.path( '/' );
          };

          var loginFailure = function( response ) {
            $scope.processing = false;
            $log.debug( 'Login failed:', response );
            $scope.failLogin( response.data.error );
          };

          AuthService.login( username, password, !!remember)
            .then( loginSuccess, loginFailure );
        };
      }])

  /**
   * Reset Controller
   */
  .controller( 'ResetCtrl',
    ['$scope','$log', 'User', 'VerificationToken', '$location',
      function( $scope, $log, User, VerificationToken, $location ) {

      $scope.resetAlert = {};
      $scope.code = $location.search()['code'];
      $scope.correctCode = false;
      $scope.confirmSent = false;
      $scope.user = {};

      $scope.resetState = function() {
        $scope.resetAlert = null;
        $scope.code        = $location.search()['code'];
        $scope.correctCode = false;
        $scope.confirmSent = false;
        $scope.user = {};
      };

      /**
       * Request that a confirmation code be sent for the supplied email address.
       * @param email
       */
      $scope.requestConfirmCode = function( email ) {
        //reset the state variables, since the new confirmation code would have changed.
        $scope.resetState();

        User.resetPassword( {email: email}, function() {
          $scope.resetAlert = {
            type: 'success',
            message: 'We just sent you an email with a confirmation link.  Click the link to finish resetting your password.'
          };
          $scope.confirmSent = true;
        });
      };

    }])

    .controller( 'DashboardCtrl', ['$scope', '$location', function( $scope, $location ) {
        if( !$scope.isLoggedIn() ) {
          $location.path( '/sign-in' );
          return;
        }
    }])


})(window.angular);
