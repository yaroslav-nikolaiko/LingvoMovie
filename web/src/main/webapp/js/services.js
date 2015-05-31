'use strict';

/* Services */

var services = angular.module('services', [ 'angular-hal']);

services.service('UserService', ['halClient','RestUtilsService', function (halClient, RestUtilsService) {
    this.user = null;

    this.find = function(query, param) {
        //var user = {name: name, email: "yaroslav@gmail.com"};

        return halClient.$get('api/users/search').
            then(function(response) {
                return response.$get(query,param);
            }).then(function(response) {
                return RestUtilsService.resolveResponse(response, 'users', true);
            });
    };

    this.load = function(id) {
        var self = this;
        id = id ? id : this.get().id;
        return RestUtilsService.entryPoint().then(function (entry) {
            return halClient.$get(entry.$href('users') + "/" + id).then(function(user) {
                return self.user = user;
            });
        });
    };

    this.update = function() {
        //var user = UserService.user;
        if(! this.user) return; //TODO : throw error 'You are not log in.'
        this.user.$put('self', {}, this.user);
        /*        halClient.$post(user.$href('self') + '/dictionaries/', {}, dictionary).then(function(response) {
         console.log(response);
         });*/
    };

    this.get = function() {
        if ( ! this.user) return null; //TODO : ????? throw error 'You are not log in.'
        return this.user;
    };
}]);

services.service('DictionaryService', ['halClient','UserService', '$rootScope', function (halClient, UserService, $rootScope) {
    this.currentDictionary = null;

    this.get = function(){
        if(UserService.get()==null) return [];
        return UserService.get().dictionaries;
    };

    this.getCurrent = function () {
        if(this.currentDictionary) return this.currentDictionary;
        if(this.get().length>0) return this.get()[0];
    };

    this.setCurrent = function (id) {
        var dict = this.get();
        for(var i = 0; i<dict.length; i++){
            if(id===dict[i].id) this.currentDictionary=dict[i];
        }
    };

    this.add = function (dictionary, onSuccess) {
        var dictionaries = this.get();
        dictionaries.push(dictionary);
        UserService.get().$patch('self', {}, {"dictionaries": dictionaries}).then(function (){
            UserService.load();
            if(onSuccess) onSuccess();
        });
    };

    this.update = function(dictionaries) {
        UserService.get().$patch('self', {}, {"dictionaries": dictionaries}).then(function (){
            UserService.load();
        });
    };

/*    this.remove = function(dictionary) {
        var dictionaries = this.get();
        for(var i=0; i<dictionaries.length; i++)
            if(dictionaries[i].id===dictionary.id)
                var index = i;

        if( ! index) return;
        dictionaries.splice(index, 1);

        UserService.get().$patch('self', {}, {"dictionaries": dictionaries}).then(function (){
            UserService.load();
        });
    };*/
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

