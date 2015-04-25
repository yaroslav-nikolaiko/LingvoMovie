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