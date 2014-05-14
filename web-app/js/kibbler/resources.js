/**
 *
 * Created by ajbrown on 4/4/14.
 */
; (function( angular ) {
  "use strict";

  angular.module( 'kibbler.resources', ['ngResource', 'kibbler.config'] )

    .factory( 'User', [ '$resource', 'appConfig', function( $resource, appConfig ) {
      return $resource( appConfig.apiEndpoint + 'users/:id', {id: "@id"}, {
        current: {
          method: 'GET',
          url: appConfig.apiEndpoint + 'users/me'
        },
        resetPassword:{
          method: 'POST',
          url: appConfig.apiEndpoint + 'users/reset-password'
        },
        activate: {
          method: 'POST',
          url: appConfig.apiEndpoint + 'users/activate'
        }
      })
    }])

    .factory( 'Organization', [ '$resource', 'appConfig', function( $resource, appConfig ) {
      return $resource( appConfig.apiEndpoint + 'organizations/:id', {id: "@id"}, {

      });
    }])

    .factory( 'Pet', [ '$resource', 'appConfig', function( $resource, appConfig ) {
      return $resource( appConfig.apiEndpoint + 'pets/:id', {id: "@id"}, {
        adopt: {
          method: 'POST',
          url: appConfig.apiEndpoint + 'pets/:id/adopt'
        }
      });
    }])

    .factory( 'VerificationToken', [ '$resource', 'appConfig', function( $resource, appConfig ) {
      return $resource( appConfig.apiEndpoint + 'verification-token/:id', {id: "@id"}, {

      });
    }])


})( window.angular );
