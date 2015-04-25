'use strict';

/* Services */

var services = angular.module('services', [ 'angular-hal']);

services.service('UserService', ['halClient','RestUtilsService', function (halClient, RestUtilsService) {
    this.find = function(query, param) {
        //var user = {name: name, email: "yaroslav@gmail.com"};

        return halClient.$get('http://server:9080/lingvo-movie/api/users/search').
            then(function(response) {
                return response.$get(query,param);
            }).then(function(response) {
                return RestUtilsService.resolveResponse(response, 'users', true);
            });
    };

    this.findAll = function() {
        return halClient.$get('http://server:9080/lingvo-movie/api/users').
            then(function(response) {
                return RestUtilsService.resolveResponse(response, 'users');
            });
    };
}]);


services.service('RestUtilsService', [function () {
    this.resolveResponse = function(response, entityName, single) {
        if (response.$has(entityName)) {
            return response.$get(entityName).then(function (entity) {
                if(single)
                    if(entity instanceof Array)
                        if (entity.length == 1)
                            return entity[0];
                        else
                            throw "Not a single object result";
                return entity;
            });
        }
    };
}]);

