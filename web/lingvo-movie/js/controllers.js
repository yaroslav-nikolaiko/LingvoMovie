'use strict';

/* Controllers */

var controllers = angular.module('controllers', []);

controllers.controller('RootController', function($scope, UserService) {
    $scope.loadUser = function() {
        UserService.find('findByName', {name : $scope.name}).then(function(user) {
            $scope.user = user;
        });
    };
});

controllers.controller('LoginController', function($scope, $http){
    $scope.login = function() {
        $http({
            method: 'POST',
            url: 'http://localhost:9080/login',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            transformRequest: function(obj) {
                var str = [];
                for(var p in obj)
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                return str.join("&");
            },
            data: {username: $scope.name, password: $scope.password},
        }).success(function (data, status, headers, config) {
            console.log(data);
        }).error(function(data, status, headers, config) {
            console.log(data);
        });

    };

/*    $scope.login = function() {
        var data = new FormData();
        data.append( 'username',  $scope.name);
        data.append( 'password',  $scope.password);
        $http.post('http://localhost:9080/login', {username: $scope.name, password: $scope.password}, {
            headers : {
                "content-type" : "application/x-www-form-urlencoded"
            }
        }).success(function(data) {
            console.log(data);
        }).error(function(data) {
            console.log(data);
        })
    };*/

});