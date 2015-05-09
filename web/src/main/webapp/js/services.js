'use strict';

/* Services */

var services = angular.module('services', [ 'angular-hal']);

services.service('UserService', ['halClient','RestUtilsService', function (halClient, RestUtilsService) {
    this.find = function(query, param) {
        //var user = {name: name, email: "yaroslav@gmail.com"};

        return halClient.$get('api/users/search').
            then(function(response) {
                return response.$get(query,param);
            }).then(function(response) {
                return RestUtilsService.resolveResponse(response, 'users', true);
            });
    };

    this.findAll = function() {
        return halClient.$get('api/users').
            then(function(response) {
                return RestUtilsService.resolveResponse(response, 'users');
            });
    };
}]);

services.service('LookupService', ['$http', 'halClient','RestUtilsService', function ($http, halClient, RestUtilsService) {
    this.lookup = function(type) {
        return RestUtilsService.entryPoint().then(function (entry) {
            return $http.get(entry.$href('lookup'), {
                params: {name: type}
            })
        });
    };
}]);


services.service('RestUtilsService', ['halClient', function (halClient) {
    this.entryPoint_cache;
    this.entryPoint = function() {
        if(this.entryPoint_cache) return this.entryPoint_cache;
        return this.entryPoint_cache = halClient.$get('api');
    };

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

