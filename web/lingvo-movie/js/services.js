'use strict';

/* Services */

var services = angular.module('services', ['ngResource']);

services.service('UserService', ['$resource', function ($resource) {
    this.userResource = $resource("http://localhost:9080/lingvo-movie/api/users");

    this.get = function(name) {
        var user = {name: name, email: "yaroslav@gmail.com"};
        //var response = $resource.$get()

        var userResource = $resource("http://localhost:9080/lingvo-movie/api/users");
        console.log(userResource.get());

        return user;
    };
}]);

