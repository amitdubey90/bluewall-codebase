var app = angular.module('eatRightApp',['ui.router', 'oc.lazyLoad']);

app.config([
  '$stateProvider',
  '$urlRouterProvider',
  '$httpProvider',
  function($stateProvider, $urlRouterProvider, $httpProvider) {

    $stateProvider
    .state('welcome', {
      url: '/welcome',
      controller: 'welcomeController',
      templateUrl: 'partials/landingPage.html',
      onEnter: function(){
        var header = $('#fh5co-header');
        if (header.hasClass('navbar-fixed-top')) {
        header.removeClass('navbar-fixed-top');
        }
      },
      onExit: function(){
        var header = $('#fh5co-header')
          if (!header.hasClass('navbar-fixed-top')) {
            header.addClass('navbar-fixed-top');
          }
      }
    })
    .state('login', {
      url: '/login',
      templateUrl: 'partials/login.html',
      controller: 'loginController'
    })
    .state('userDashboard', {
      url: '/user/dashboard',
      templateUrl: 'partials/dashboard.html',
      controller: 'userDashboardController'
    })
     .state('getFoodLogForm', {
      url: '/getFoodLogForm',
      templateUrl: 'partials/logFood.html',
      controller: 'logFoodController'
    })
    .state('getFoodLogged', {
      url: '/getFoodLogged',
      templateUrl: 'partials/displayLoggedFood.html',
      controller: 'logFoodController'
    })
    .state('getActivityLogForm', {
        url: '/getActivityLogForm',
        templateUrl: 'partials/logActivity.html',
        controller: 'logActivityController'
      })
    .state('haha', {
      url: '/haha',
      templateUrl: 'partials/haha.html'
    });

    $urlRouterProvider.otherwise('/welcome');
    // to prevent server from sending pop-ups
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

}]);


