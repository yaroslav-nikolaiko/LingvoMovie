'use strict';
/**
 * Created by yaroslav on 10/10/15.
 */


controllers.controller('MediaItemsController', ['$scope', 'MediaItemService', function ($scope, MediaItemService) {
    $scope.remove = function (scope) {
        scope.remove();
    };

    $scope.toggle = function (scope) {
        scope.toggle();
    };

    $scope.moveLastToTheBeginning = function () {
        var a = $scope.data.pop();
        $scope.data.splice(0, 0, a);
    };

    $scope.newSubItem = function (scope) {
        var nodeData = scope.$modelValue;
        nodeData.nodes.push({
            id: nodeData.id * 10 + nodeData.nodes.length,
            title: nodeData.title + '.' + (nodeData.nodes.length + 1),
            nodes: []
        });
    };

    $scope.collapseAll = function () {
        $scope.$broadcast('collapseAll');
    };

    $scope.expandAll = function () {
        $scope.$broadcast('expandAll');
    };


    this.notify = function(mediaItems) {
        $scope.data = mediaItems;
    };

    this.init = function(){
        MediaItemService.registerListener(this, 'MediaItemsController');
        MediaItemService.reload();
    };


    this.init();
}]);