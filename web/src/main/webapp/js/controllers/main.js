'use strict';
/**
 * Created by yaroslav on 27.06.15.
 */

controllers.controller('RootController', function($scope, UserService,  LookupService) {
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
