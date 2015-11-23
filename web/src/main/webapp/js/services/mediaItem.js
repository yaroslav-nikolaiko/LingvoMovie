'use strict';
/**
 * Created by yaroslav on 27.06.15.
 */

services.service('MediaItemService', ['halClient','DictionaryService', '$rootScope', function (halClient, DictionaryService, $rootScope) {
    var self = this;
    this.mediaItems = null;
    this.listeners = {};

    this.registerListener = function(listener, name){
        self.listeners[name]=listener;
    };

    this.get = function(callback){
        if(this.mediaItems) return this.mediaItems;

        this.reload(callback);
    };

    this.reload = function(callback){
        var dictionary = DictionaryService.getCurrent();
        if( ! dictionary) return [];

        return halClient.$get('api/dictionaries/' + dictionary.id + '/mediaItems')
            .then(function(response){
                self.mediaItems = response;
                for(var name in self.listeners) {
                    self.listeners[name].notify(self.mediaItems);
                }
                if(callback) callback(self.mediaItems);
            });
    };
}]);