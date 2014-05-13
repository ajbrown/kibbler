/**
 * Contains only client-side login that's needed for externally facing views.
 * Created by ajbrown on 1/26/14.
 */
; (function( angular, window ) {
  "use strict";

  var app = angular.module('kibblerExternal', ['ngRoute', 'kibbler.services', 'kibbler.config']);

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

  app.config([ '$locationProvider',
    function( $locationProvider ) {
      $locationProvider.html5Mode( true );
    }]);

  app.controller( 'HomeCtrl', [
    '$scope',
    function( $scope ) { }
  ]);



})(window.angular, window);

