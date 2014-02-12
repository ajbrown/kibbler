/**
 * Contains only client-side login that's needed for externally facing views.
 * Created by ajbrown on 1/26/14.
 */
;
var app = angular.module('kibblerExternal', ['ngRoute']);


app.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/', {
                redirectTo: '/sign-in'
            }).
            when('/reset-password', {
                templateUrl: '_view/reset',
                controller: 'ResetCtrl'
            }).
            when('/sign-in', {
                templateUrl: '_view/login',
                controller: 'LoginCtrl'
            }).
            otherwise({
                redirectTo: '/sign-in'
            });
}]);

app.config(function($logProvider){
    $logProvider.debugEnabled( window.developmentMode );
});

app.config(function($locationProvider){
   $locationProvider.html5Mode( true );
});

/**
 * Login Controller
 */
app.controller( 'LoginCtrl',
    [ '$scope', '$http', '$location', '$log', function($scope, $http, $location, $log ) {

    $scope.email    = '';
    $scope.password = '';
    $scope.user = {};
    $scope.loginFailure = '';

    $scope.failLogin = function ( reason ) {
        $scope.loginFailure = reason;
    };


    $scope.login = function( u ) {
        $log.debug( 'Attempting login with credentials', u );

        $scope.loginFailure = '';

        var data = "j_username=" + encodeURI(u.email) + "&j_password=" + encodeURI(u.password);
        var config = {
            headers: {'Content-Type': 'application/x-www-form-urlencoded', 'X-Requested-With' : 'XMLHttpRequest' }
        };


        $http.post( window.basePath + '/j_spring_security_check?ajax=true', data, config )
            .success( function( data ) {

                if( data.error ) {
                    $scope.failLogin( data.error );
                    $log.debug( "Login failed with reason: " + data.error );

                } else if( data.success ) {
                    $log.debug( "Successfully logged in as ", data.username );
                    $scope.username = data.username;

                    //TODO this needs to be implemented without global scope
                    window.location = window.basePath + '/dashboard';

                } else {
                    //Something terribly unexpected happened
                    $log.error( "Unexpected response during authentication" )
                    $scope.failLogin( "There's a problem logging in right now.  Please contact us at support@kibbler.org");
                }

            })
            .error( function( data, status ) {
                $log.warn( "There was a server issue while attempting to log in." );
                $scope.failLogin( "Error contacting the server.  Please try again." );
                return false;
            })
        ;

    };
}]);

/**
 * Reset Controller
 */
app.controller( 'ResetCtrl',
    ['$scope','$http','$log', '$window', '$location', function( $scope, $http, $log, $window, $location ) {

        var httpCfg = { headers: { 'X-Requested-With' : 'XMLHttpRequest' } };
        var resetUrl = $window.basePath + '/reset-password'

        $scope.resetAlert = {};
        $scope.code = $location.search()['code'];
        $scope.correctCode = false;
        $scope.email = $location.search()['email'];
        $scope.confirmSent = $location.search()['email'] && true;
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
            $scope.email = angular.copy( email );
            $location.search('email', email );

            $log.debug( "Sending confirmation code to ", email );

            $http.post( resetUrl, { email: email }, httpCfg )
                .success( function( data, status ) {
                    $scope.confirmSent = data.success && data.action == 'send-confirmation-code';

                    if( !$scope.confirmSent ) {
                        var problem = !data.user ? 'We couldn\'t find a user with that email.' : 'There was an error requesting a confirmation code.  Please try again.'
                        $scope.resetAlert = { type: 'warning', message: problem }
                    }

                })
                .error( function( data, status ) {
                    $scope.confirmSent = false;
                    $scope.resetAlert = {
                        type: 'danger',
                        message: 'There was an error sending your confirmation code.  Please try again, and contact us at support@kibbler.org if you still have problems.'
                    }

                    $log.warn( "An error occurred while trying sending confirmation code:", status, data );
                });
        };

        /**
         * Check the confirmation code.
         * @param email
         * @param code
         */
        $scope.checkConfirmCode = function( email, code ) {
            $log.debug( "Checking confirmation code with values", { "code": code, "email": email } );

            $location.search('email', email );
            $location.search('code', code );

            $scope.resetAlert = {};

            if( !email || !code ) {
                $scope.resetAlert = {
                    type: 'warning', message: 'You must enter your email and confirmation code.'
                }
                return;
            }

            $http.post( resetUrl, { "code": code, "email": email }, httpCfg )
                .success( function( data, status ) {
                    $scope.correctCode = data.success && data.action == 'check-confirmation-code';
                    $scope.user = data.user;

                    if( !$scope.correctCode ) {
                        $scope.resetAlert = {
                            type: 'warning',
                            message: 'The confirmation code you entered was not correct.  Double check your entry, and click "resend confirmation" if you continue to have problems.'
                        }
                    }
                })
                .error( function( data ) {
                    $scope.resetAlert = {
                        type: 'danger', message: 'There was an error confirming your confirmation code.  Please contact support@kibbler.org if this problem continues.'
                    };

                    $log.warn( "An error occurred while trying to validate a confirmation code.", status, data );
                });

        };

        $scope.resetPassword = function( email, code, password ) {
            $log.debug( "Resetting password" );

            $scope.resetAlert = {};

            $location.search( 'email', email );
            $location.search( 'code', code );

            if( !email || !code || !password ) {
                $scope.resetAlert = {
                    type: 'warning', message: 'You must enter your email, confirmation code, and password.'
                }
                return;
            }

            var data = { "email": email, "code": code, "password": password };



            $http.post( resetUrl, data, httpCfg )
                .success( function( data, status ) {
                    if( data.success && data.action == 'update-password' ) {
                        //The user should be logged in now, so we can redirect them to the login page.
                        $window.location.href = $window.basePath + '/dashboard';
                    } else {
                        $scope.resetAlert = {
                            type: 'warning',
                            message: 'We couldn\'t update your password.  Please try again.'
                        }
                    }
                   //The user should be logged
                })
                .error( function( data, status ) {
                    $scope.resetAlert = {
                        type: 'danger', message: 'There was an error contacting the server.  Please contact support@kibbler.org if this problem continues.'
                    };

                    $log.warn( "An error occurred while trying to validate a confirmation code.", status, data );
                });
        };


        //If the email and code are already set (we're following a link in the email), then we should go ahead and check.
        if( $scope.email && $scope.code ) {
            $scope.checkConfirmCode( $scope.email, $scope.code );
        }

    }
]);
