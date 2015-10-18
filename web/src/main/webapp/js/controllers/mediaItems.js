'use strict';
/**
 * Created by yaroslav on 10/10/15.
 */


controllers.controller('MediaItemsController', ['$scope', function ($scope) {
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

    $scope.data = [ {
        "title" : "HIMYM",
        "displayPath" : "HIMYM",
        "item" : null,
        "nodes" : [ {
            "title" : "season 1",
            "displayPath" : "HIMYM/season 1",
            "item" : null,
            "nodes" : [ {
                "title" : "1 episode",
                "displayPath" : "HIMYM/season 1",
                "item" : {
                    "name" : "1 episode",
                    "displayPath" : "HIMYM/season 1",
                    "metaInfo" : { }
                },
                "nodes" : [ ]
            }, {
                "title" : "episode 2",
                "displayPath" : "HIMYM/season 1",
                "item" : {
                    "name" : "episode 2",
                    "displayPath" : "HIMYM/season 1",
                    "metaInfo" : { }
                },
                "nodes" : [ ]
            }, {
                "title" : "episode 3",
                "displayPath" : "HIMYM/season 1",
                "item" : {
                    "name" : "episode 3",
                    "displayPath" : "HIMYM/season 1",
                    "metaInfo" : { }
                },
                "nodes" : [ ]
            } ]
        }, {
            "title" : "season 2",
            "displayPath" : "HIMYM/season 2",
            "item" : null,
            "nodes" : [ {
                "title" : "1 episode",
                "displayPath" : "HIMYM/season 2",
                "item" : {
                    "name" : "1 episode",
                    "displayPath" : "HIMYM/season 2",
                    "metaInfo" : { }
                },
                "nodes" : [ ]
            }, {
                "title" : "2 episode",
                "displayPath" : "HIMYM/season 2",
                "item" : {
                    "name" : "2 episode",
                    "displayPath" : "HIMYM/season 2",
                    "metaInfo" : { }
                },
                "nodes" : [ ]
            } ]
        } ]
    }, {
        "title" : "TVShows",
        "displayPath" : "TVShows",
        "item" : null,
        "nodes" : [ {
            "title" : "entertainment",
            "displayPath" : "TVShows/entertainment",
            "item" : null,
            "nodes" : [ {
                "title" : "nightShow",
                "displayPath" : "TVShows/entertainment/nightShow",
                "item" : null,
                "nodes" : [ {
                    "title" : "first",
                    "displayPath" : "TVShows/entertainment/nightShow",
                    "item" : {
                        "name" : "first",
                        "displayPath" : "TVShows/entertainment/nightShow",
                        "metaInfo" : { }
                    },
                    "nodes" : [ ]
                }, {
                    "title" : "second",
                    "displayPath" : "TVShows/entertainment/nightShow",
                    "item" : {
                        "name" : "second",
                        "displayPath" : "TVShows/entertainment/nightShow",
                        "metaInfo" : { }
                    },
                    "nodes" : [ ]
                } ]
            } ]
        } ]
    }, {
        "title" : "movies",
        "displayPath" : "movies",
        "item" : null,
        "nodes" : [ {
            "title" : "Interstellar",
            "displayPath" : "movies",
            "item" : {
                "name" : "Interstellar",
                "displayPath" : "movies",
                "metaInfo" : { }
            },
            "nodes" : [ ]
        } ]
    } ]
}]);