'use strict';

/**
 * Created by yaroslav on 27.06.15.
 */

controllers.controller('IndexPageController', function($scope,$rootScope, $uibModal, DictionaryService, UserService) {
    $scope.dictionaryDialog = function() {
        //$dialogs.create('dialogs/dictionaries.html');
        $uibModal.open({
            templateUrl: 'dialogs/dictionaries.html',
            controller: 'DictionaryModalController'
        });
    };

    $scope.DictionaryService = DictionaryService;

    //$scope.currentDictionaryName = currentDictionaryName2();
    //$scope.currentDictionaryName = function(){return DictionaryService.getCurrentName();};
    //$scope.currentDictionary = DictionaryService.currentDictionary;


    $scope.currentDictionary = function(){
        return DictionaryService.getCurrent();
    }

});