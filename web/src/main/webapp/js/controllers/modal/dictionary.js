'use strict';
/**
 * Created by yaroslav on 27.06.15.
 */

controllers.controller('DictionaryModalController', function($scope,$modalInstance, LookupService, DictionaryService, UserService) {
    var self = this;
    this.init = function () {
        LookupService.lookup('language').then(function(response) {
            $scope.languages = response.data;
        });
        LookupService.lookup('level').then(function(response) {
            $scope.levels = response.data;
        });
        this.load();
    };

    this.load = function(){
        $scope.dictionaries = angular.copy(DictionaryService.get());
        var current = DictionaryService.getCurrent();
        if(current) {
            $scope.selectedID = current.id;
        }
    };

    $scope.remove = function (index) {
        if(confirm("Are you sure you want to DELETE this dictionary")){
            $scope.dictionaries.splice(index, 1);
            DictionaryService.update($scope.dictionaries);
        }
    };

    $scope.update = function (callback) {
        DictionaryService.update($scope.dictionaries).then(function(){
            DictionaryService.setCurrent($scope.selectedID);
            if(callback) callback();
        });
    };

    $scope.exit = function (){
        $modalInstance.close();
    };

    $scope.createDictionary = function() {
        DictionaryService.add($scope.dictionary, function(){
            $('#manage-dictionary').show(); $('#create-dictionary').hide();
            self.load();
        });
    };

    $scope.lookup = function(type) {
        LookupService.lookup(type).then(function(response) {
            console.log(response.data);
        });
    };

    $scope.languages = function() {
        return LookupService.lookup('language').then(function(response) {
            console.log(response.data);
            return response.data;
        });
    };


    this.init();
});
