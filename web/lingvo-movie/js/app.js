'use strict';

/* App Module */

var lingvoMovie = angular.module('lingvoMovie', ['ngRoute',
    'services', 'controllers']);


lingvoMovie.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/login', {
                templateUrl: 'login.html'
            }).
            otherwise({
                templateUrl:  'demo.html'
            });
    }]);

lingvoMovie.factory('unAuthorizedInterceptor', ['$q', '$window','$location', function($q, $window, $location) {
    return {
        'responseError': function(errorResponse) {
            switch (errorResponse.status) {
                case 403:
                    //$window.location = '#login';
                    $location.path('/login');
                    break;
                case 500:
                    $window.location = './500.html';
            }
            return $q.reject(errorResponse);
        }
    }
}]);

lingvoMovie.config(['$httpProvider', function($httpProvider) {
    $httpProvider.interceptors.push('unAuthorizedInterceptor');
}]);



