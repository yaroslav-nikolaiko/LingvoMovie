'use strict';
/**
 * Created by yaroslav on 27.06.15.
 */

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

    this.load = function(id, callback) {
        var self = this;
        id = id ? id : this.get().id;
        return RestUtilsService.entryPoint().then(function (entry) {
            return halClient.$get(entry.$href('users') + "/" + id).then(function(user) {
                self.user = user;
                if(callback) callback();
                return user;
            });
        });

        /*        return RestUtilsService.entryPoint().then(function (entry) {
         return halClient.$get(entry.$href('users') + "/" + id)}).then(function(user) {
         self.user = user;
         if(callback) callback();
         return user;
         });*/
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
