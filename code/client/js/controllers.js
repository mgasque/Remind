angular.module('starter.controllers', [])

.controller('AppCtrl', function($scope,$rootScope, $ionicModal, $interval,$timeout, $http, $ionicLoading) {
  
  /*********************************************/
  /* Form data for the login modal  */
  $scope.loginData = {};

  /* HOSTNAME  */

  //$scope.hostName = 'phdiep.rmorpheus.enseirb.fr/remindv2/';
  $scope.hostName = 'localhost:8080/remind/'



  /* login modal */
  $ionicModal.fromTemplateUrl('templates/login.html', {
    scope: $scope
  }).then(function(modal) {
    $scope.modal = modal;
  });

  $scope.closeLogin = function() {
    $scope.modal.hide();
  };

  $scope.login = function() {
    $scope.modal.show();
  };

  $scope.logged = false;
  $scope.doLogin = function() {

    $timeout(function() {
      $scope.closeLogin();
    }, 1000);

        params = '{}';
        base_url = 'http://'+$scope.hostName+'usercheck/';
        complete_url = window.base_url;
        params = '';

        test = {
            "email" : $scope.loginData.email,
            "pass": $scope.loginData.pass,
            "connected": false, 
            };

        var a = angular.fromJson(test); 

        $http.put(complete_url, a)
        .error(function(){
          console.log("marche pas");
        })
        .success(function(data,status,headers,config){
          $http.jsonp('http://'+$scope.hostName+'usercheck/' + $scope.loginData.email , {params: {callback: 'JSON_CALLBACK'}})
            .error(function() {
              alert("Wrong email / password");
            })
            .success(function(data,status,headers,config){
                console.log("je suis la");
                $rootScope.connected = true;
                $rootScope.user = $scope.loginData.email;
                $scope.logged = !$scope.logged;
              });
        });
  }


  $scope.doLogout = function() {

    $timeout(function() {
      $scope.closeLogin();
    }, 1000);

        params = '{}';
        base_url = 'http://'+$scope.hostName+'usercheck/logout';
        complete_url = window.base_url;
        params = '';
        $scope.logged = !$scope.logged;

        test = {
            "email" : $scope.loginData.email,
            "pass": $scope.loginData.pass,
            "connected": false 
            };
        console.log(test);

        var a = angular.fromJson(test); 

        $http.put(complete_url, a)
        .error(function(){
          console.log("marche pas");
        })
        .success(function(data,status,headers,config){
          console.log("marche");
          $rootScope.connected = false;
          $scope.loginData.email='';
          $scope.loginData.pass='';
          $scope.logged = !$scope.logged;
        });
  }
 $scope.register = false;

 $scope.goRegister = function(){
    $scope.register = !$scope.register;
 };

 $scope.doRegister = function(){
    params = '{}';
            base_url = 'http://'+$scope.hostName+'usercheck/register';
            complete_url = window.base_url;
            params = '';

            test = {
            "email" : $scope.loginData.email,
            "pass": $scope.loginData.pass,
            "connected": true 
            };


            var a = angular.fromJson(test); 

            $http.post(complete_url, a)
            .error(function(){
              console.log("marche pas");
            })
            .success(function(data,status,headers,config){
              if(data){
                $rootScope.connected = true;
                $rootScope.user = $scope.loginData.email;
                $scope.logged = !$scope.logged;

              }
              else{
                alert("Email already in use, please use another one");
              }
            });
  };

})
.controller('RefreshTodosCtrl', function($scope, $interval) {
  $interval(function(){
    $scope.getTodos();
  },1000);

})

.controller('RefreshTodoCtrl', function($scope, $interval) {
  $interval(function(){
    $scope.getTodoById($scope.todoId);
  },1000);

})

.controller('TodolistCtrl', function($scope, $rootScope, $stateParams,$http, $ionicLoading, $window) {

    $scope.orderByPredicate = "title";
    $scope.orderByReverse = false;
    
    // Tri par title:

    $scope.clickPredicateName = function(){
        $scope.orderByReverse = !$scope.orderByReverse;
        $scope.orderByPredicate = 'title';
    }

    $scope.clickPredicateDate = function(){
        $scope.orderByReverse = !$scope.orderByReverse;
        $scope.orderByPredicate = 'date';
    }
    

  $scope.todoId=$stateParams.todoId;

  $scope.todolist = {};
  $scope.todoSingle={}

  $scope.loadingTodos = function() {
    $ionicLoading.show({
      duration: 1000
    });
  };

  $scope.goTodo = function () {
    $window.location.href="#/app/todolist";
    //$window.location.reload();
  }

  $scope.getTodos = function() {

    params = '{}';
    base_url = 'http://'+$scope.hostName;
    param_url = 'todoget';
    complete_url = window.base_url + param_url;
    params = '';

    $http.jsonp(complete_url, {params: {callback: 'JSON_CALLBACK'}})
    .error(function() {
    })
    .success(function(data,status,headers,config){
      $scope.todolist = data.todos;
    });
  }

  $scope.getTodoById = function(id) {
    
    params = '{}';
    base_url = 'http://'+$scope.hostName+'todoget/';
    param_url =  id;
    complete_url = window.base_url + param_url;
    params = '';

    $http.jsonp(complete_url, {params: {callback: 'JSON_CALLBACK'}})
    .error(function() {
      console.log("Vous n'êtes pas connecté");
    })
    .success(function(data,status,headers,config){

      $scope.todoSingle = data.todo;
    });
  }

  $scope.postTodo = function(fields,index){

    if(fields._id){

      params = '{}';
      base_url = 'http://'+$scope.hostName+'todoput/';
      console.log(fields._id.$oid);
      param_url = fields._id.$oid;
      complete_url = window.base_url + param_url;
      params = '';

      test = {
          "_id" : fields._id,
          'title': fields.title,
          "user": $rootScope.user, 
          "isActive": true,
          "content":  fields.content,
          "date": fields.date,
          "time": fields.time,
        };


      var a = angular.fromJson(test); 

      $http.put(complete_url, a)
      .error(function(){
        console.log("marche pas");
      })
      .success(function(data,status,headers,config){
        console.log("marche");
        $scope.goTodo();
        $scope.getTodos();
      });
    }
    else{
        params = '{}';
        base_url = 'http://'+$scope.hostName+'todopost/';
        complete_url = window.base_url;
        params = '';

        test = {
            "_id" : fields._id,
            'title': fields.title,
            "user": $rootScope.user, 
            "isActive": true,
            "content":  fields.content,
            "date": fields.date,
            "time": fields.time,
          };


        var a = angular.fromJson(test); 

        $http.post(complete_url, a)
        .error(function(){
          console.log("marche pas");
        })
        .success(function(data,status,headers,config){
          console.log("marche");
          $scope.goTodo();
          $scope.getTodos();
        });
    }
}

  $scope.completeTodo = function(fields){

        params = '{}';
        base_url = 'http://'+$scope.hostName+'todoput/';
        console.log(fields._id.$oid);
        param_url = fields._id.$oid;
        complete_url = window.base_url + param_url;
        params = '';

        test = {
            "_id" : fields._id,
            'title': fields.title,
            "user": fields.user, 
            "isActive": false,
            "content":  fields.content,
            "date": fields.date,
            "time": fields.time,
          };


        var a = angular.fromJson(test); 

        $http.put(complete_url, a)
        .error(function(){
          console.log("marche pas");
        })
        .success(function(data,status,headers,config){
          console.log("marche");
        });
  }

    $scope.removeTodo = function(fields){

        params = '{}';
        base_url = 'http://'+$scope.hostName+'tododelete/';
        console.log(fields._id.$oid);
        param_url = fields._id.$oid;
        complete_url = window.base_url + param_url;
        params = '';

        test = {
            "_id" : fields._id,
            'title': fields.title,
            "user": fields.user, 
            "isActive": false,
            "content":  fields.content,
            "date": fields.date,
            "time": fields.time,
          };


        var a = angular.fromJson(test); 

        $http.delete(complete_url, a)
        .error(function(){
          console.log("marche pas");
        })
        .success(function(data,status,headers,config){
          console.log("marche");
        });
  }

});

