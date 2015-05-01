// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
// 'starter.controllers' is found in controllers.js
angular.module('starter', ['ionic', 'starter.controllers'])

.run(function($ionicPlatform, $rootScope) {
  $ionicPlatform.ready(function() {
    // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
    // for form inputs)
    if (window.cordova && window.cordova.plugins.Keyboard) {
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
    }
    if (window.StatusBar) {
      // org.apache.cordova.statusbar required
      StatusBar.styleDefault();
    }
  })
  $rootScope.connected = false;
  $rootScope.user = "";
})

.config(function($stateProvider, $urlRouterProvider) {
  $stateProvider

  .state('app', {
    url: "/app",
    abstract: true,
    templateUrl: "templates/menu.html",
    controller: 'AppCtrl'
  })

  .state('app.search', {
    url: "/search",
    views: {
      'menuContent': {
        templateUrl: "templates/search.html"
      }
    }
  })

  .state('app.addtodo', {
    url: "/addtodo/:todoId",
    views: {
      'menuContent': {
        templateUrl: "templates/addtodo.html",
        controller: 'TodolistCtrl'
      }
    }
  })
    .state('app.todolist', {
      url: "/todolist",
      views: {
        'menuContent': {
          templateUrl: "templates/todolist.html",
          controller: 'TodolistCtrl'
        }
      }
    })

    .state('app.donelist', {
      url: "/donelist",
      views: {
        'menuContent': {
          templateUrl: "templates/donelist.html",
          controller: 'TodolistCtrl'
        }
      }
    })

  .state('app.single', {
    url: "/todolist/:todoId",
    views: {
      'menuContent': {
        templateUrl: "templates/todo.html",
        controller: 'TodolistCtrl'
      }
    }
  });
  // if none of the above states are matched, use this as the fallback
  $urlRouterProvider.otherwise('/app/todolist');
});
