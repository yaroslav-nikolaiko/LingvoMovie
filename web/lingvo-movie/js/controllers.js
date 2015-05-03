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
            data: {username: $scope.name, password: $scope.password}
        }).success(function (response) {
            console.log(response);
        });
    };

});