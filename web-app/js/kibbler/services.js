/**
 * Created by ajbrown on 2/13/14.
 */
; (function( angular ) {
  "use strict";

  angular.module( 'kibbler.services', [ 'ngStorage', 'kibbler.config', 'kibbler.resources'] )

    .service( 'AuthService', [ '$http', 'User', '$sessionStorage', '$localStorage', '$log', 'appConfig',

      function( $http, User, $sessionStorage, $localStorage, $log, appConfig ) {

        /**
         * Login with the specified username and password.  The authentication credentials will be stored in the session
         * @param username
         * @param password
         * @param remember OPTIONAL - if login is successful, the authentication details will be saved
         * @param successCallback OPTIONAL - upon successful login, the auth token will be passed to the specified method.
         * @param failureCallback OPTIONAL - upon failed login, the response will be passed to this function.
         */
        var login = function( username, password, remember ) {
          remember = !!remember;

          var loginSuccess = function( response ) {
            $log.debug( 'Auth response was:', response );

            //If remembered, store the token in local storage.
            $localStorage.token = remember? response.data.authToken : null;
            $sessionStorage.user = User.current( null );
            $sessionStorage.token = response.data.token;
            $http.defaults.headers.common['X-Auth-Token'] = response.data.authToken;
            return response.data;
          };

          var loginFailure = function( response ) {
            $log.info( 'Failed login for username' + username );
            return response;
          };

          return $http.post( appConfig.apiEndpoint + '../login', { username: username, password: password } )
                    .then( loginSuccess, loginFailure );
        };

        var loginRemembered = function(successCallback, failureCallback ) {

            var successValidate = function( token ) {
              $http.defaults.headers.common['X-Auth-Token'] = token.token;
              $sessionStorage.token = token.token;
              successCallback( token );
            };

            //If there's nothing stored in either session or local storage, then we can just call the failureCallback.
            if( !$sessionStorage.token && !$localStorage.token ) {
              failureCallback();
              return
            }

            //Attempt to login with the token currently in session storage.  If that fails, try the one in the local
            // storage.  If both fail, then fall back to the failureCallback provided.
            return validateToken( $sessionStorage.token ).then(
                function( resp ) {
                  successValidate( resp.data );
                },
                function( resp ) {
                  delete $sessionStorage.token;
                  validateToken($localStorage.token).then(
                    function (resp) {
                      successValidate(resp.data);
                    },
                    function( resp ) {
                      delete $localStorage.token;
                      failureCallback( resp );
                    }
                  )
                }
            );
        };

        /**
         * Log the current user out (completely).
         */
        var logout = function() {
          //Remove authentication info from session storage
          $sessionStorage.token = null;
          $sessionStorage.user  = null;

          //If there was anything stored in the local storage, clear it out as well.
          $localStorage.user = null;
          $localStorage.token = null;

          //Remove the auth token being used by http requests
          delete $http.defaults.headers.common['X-Auth-Token'];
        };

        /**
         * Validate a token.  If no token is specified, the token that's currently attached to the $http service will be validated.
         */
        var validateToken = function( tokenValue ) {
          tokenValue = tokenValue || $http.defaults.headers.common['X-Auth-Token'];
          return $http.post(
              appConfig.apiEndpoint + '../validate',{},
              {headers: { 'X-Auth-Token' : tokenValue }}
          );
        };

        $sessionStorage.$default( { token: null, user : null } );

        return  {
          login: login,
          logout: logout,
          validateToken: validateToken,
          loginRemembered: loginRemembered
        }
      }])

})(window.angular);