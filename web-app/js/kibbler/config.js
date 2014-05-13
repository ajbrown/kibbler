; (function( angular ) {
  "use strict";

  angular.module( 'kibbler.config', [] )

    //TODO some mechanism for updating these values when packaged.
    .constant( 'appConfig',  {
      apiEndpoint: 'api/v1/',
      debugEnabled: true
    })

    .config( ['appConfig', '$logProvider',
      function( appConfig, $logProvider ) {
        $logProvider.debugEnabled( !!appConfig.debugEnabled );
      }
    ])

})( window.angular );
