/**
 * Contains only client-side login that's needed for externally facing views.
 * Created by ajbrown on 1/26/14.
 */
;

var app = angular.module('kibblerExternal', []);

app.directive('kb-focus', function() {
    return function(scope, element, attrs) {
        scope.$watch(attrs.focus, function(newValue) {
            newValue && element[0].focus()
        })
    }
});

app.controller(
    'LoginCtrl',
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
