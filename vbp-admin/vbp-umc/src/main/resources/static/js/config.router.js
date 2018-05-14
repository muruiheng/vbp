'use strict';

/**
 * Config for the router
 */
angular.module('app')
  .run(
    [          '$rootScope', '$state', '$stateParams',
      function ($rootScope,   $state,   $stateParams) {
          $rootScope.$state = $state;
          $rootScope.$stateParams = $stateParams;        
      }
    ]
  )
  .config(
    [          '$stateProvider', '$urlRouterProvider',
      function ($stateProvider,   $urlRouterProvider) {
          
          $urlRouterProvider
              .otherwise('/app/resource');
          $stateProvider
              .state('app', {
                  abstract: true,
                  url: '/app',
                  templateUrl: 'tpl/app.html'
              })
              .state('app.resource', {
            	  url: '/resource',
                  templateUrl: 'tpl/resource/main.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad ){
                          return $ocLazyLoad.load('angularBootstrapNavTree').then(
                                  function(){
                                     return $ocLazyLoad.load('js/controllers/resourceManager.js');
                                  }
                              );
                      }]
                  }
              })
              .state('app.user', {
            	  url: '/user',
                  templateUrl: 'tpl/user/main.html',
                  resolve: {
                      deps: ['$ocLazyLoad',
                        function( $ocLazyLoad ){
                          return $ocLazyLoad.load('angularBootstrapNavTree').then(
                                  function(){
                                     return $ocLazyLoad.load('js/controllers/userManager.js');
                                  }
                              );
                      }]
                  }
              })
              .state('access', {
                  url: '/access',
                  template: '<div ui-view class="fade-in-right-big smooth fullscreen"  style="background-image: url(\'img/bg0.jpg\'); background-size: cover;" ></div>'
              })
               .state('access.signin', {
                  url: '/signin',
                  templateUrl: 'tpl/login/page_signin.html',
                  resolve: {
                      deps: ['uiLoad',
                        function( uiLoad ){
                          return uiLoad.load( ['js/controllers/signin.js'] );
                      }]
                  }
              });
              
      }
    ]
  );