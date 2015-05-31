'use strict';

/* Controllers */

var controllers = angular.module('controllers', ['ui.bootstrap']);

controllers.controller('RootController', function($scope, UserService, LookupService) {
    $scope.loadUser = function() {
        UserService.find('findByName', {name : $scope.name}).then(function(user) {
            $scope.user = user;
        });
    };

    $scope.lookup = function(type) {
        LookupService.lookup(type).then(function(response) {
            console.log(response.data);
        });
    };
});

controllers.controller('IndexPageController', function($scope, $modal) {
    $scope.dictionaryDialog = function() {
        //$dialogs.create('dialogs/dictionary.html');
        $modal.open({
            templateUrl: 'dialogs/dictionary.html',
            controller: 'DictionaryModalController'
        });
    };
});

controllers.controller('DictionaryModalController', function($scope,$modalInstance, LookupService, DictionaryService, UserService) {
    var self = this;
    this.init = function () {
        LookupService.lookup('language').then(function(response) {
            $scope.languages = response.data;
        });
        LookupService.lookup('level').then(function(response) {
            $scope.levels = response.data;
        });
        this.load();
    };

    this.load = function(){
        $scope.dictionaries = angular.copy(DictionaryService.get());
    };

    $scope.remove = function (index) {
        if(confirm("Are you sure you want ro DELETE this dictionary")){
            $scope.dictionaries.splice(index, 1);
        }
    };

    $scope.update = function () {
        DictionaryService.update($scope.dictionaries);
    };

    $scope.exit = function (){
        $modalInstance.close();
    };

    $scope.createDictionary = function() {
        DictionaryService.add($scope.dictionary, function(){
            $('#manage-dictionary').show(); $('#create-dictionary').hide();
            self.load();
        });
    };

    $scope.lookup = function(type) {
        LookupService.lookup(type).then(function(response) {
            console.log(response.data);
        });
    };

    $scope.languages = function() {
        return LookupService.lookup('language').then(function(response) {
            console.log(response.data);
            return response.data;
        });
    };


    this.init();
});

//--------------------------------- Login Controller -----------------------------------

controllers.controller('LoginController', function($scope, $http, $location, $rootScope, UserService){
    $scope.init = function () {
        var pathname = window.location.href;
        if(pathname.indexOf("signup=true") > -1)  {
            $('#loginbox').hide();
            $('#signupbox').show();
        }
    };

    $scope.login = function() {
        $http({
            method: 'POST',
            url: 'api/login',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            transformRequest: function(obj) {
                var str = [];
                for(var p in obj)
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                return str.join("&");
            },
            data: {username: $scope.name, password: $scope.password}
        }).success(function (id, status, headers, config) {
            $rootScope.loggedIn = true;
            UserService.load(id);
            $location.path('/home');
        }).error(function(data, status, headers, config) {
            $rootScope.loggedIn = false;
            console.log(data);
        });

    };

    $scope.signUp = function() {
        $http.post('api/users',
            {name: $scope.name, password: $scope.password, email : $scope.email}).
            success(function(data, status, headers, config) {
                // this callback will be called asynchronously
                // when the response is available
            }).
            error(function(data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };

});