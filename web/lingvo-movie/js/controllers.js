'use strict';

/* Controllers */

var controllers = angular.module('controllers', []);

controllers.controller('RootController', function($scope, UserService) {
    $scope.loadUser = function() {
        $scope.user = UserService.get($scope.name);
        //console.log($scope.user);
    };
});