'use strict';
/**
 * Created by yaroslav on 27.06.15.
 */

services.service('DictionaryService', ['halClient','UserService', '$rootScope', function (halClient, UserService, $rootScope) {
    var self = this;
    this.currentDictionary = null;

    this.get = function(){
        if(UserService.get()==null) return [];
        return UserService.get().dictionaries;
    };

    this.getCurrent = function () {
        if(this.currentDictionary) return this.currentDictionary;
        var dictionaries = this.get();
        if(dictionaries.length>0) return this.currentDictionary=dictionaries[0];
        else return null;
    };

    this.getCurrentName = function(){
        var dictionary = this.getCurrent();
        if(dictionary) return dictionary.name;
        return null;
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
            UserService.load().then(function(){if(onSuccess) onSuccess();});

        }, function() {
            UserService.load();
        });
    };

    this.update = function(dictionaries) {
        return UserService.get().$patch('self', {}, {"dictionaries": dictionaries}).then(function (){
            return UserService.load();
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