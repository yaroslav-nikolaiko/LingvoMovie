'use strict';

/* App Module */

var lingvoMovie = angular.module('lingvoMovie', ['ngRoute', 'ui.bootstrap',
    'services', 'controllers']);


lingvoMovie.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/login', {
                templateUrl: 'login.html',
                controller: 'LoginController'
            }).
            otherwise({
                templateUrl:  'demo.html'
            });
    }]);


lingvoMovie.run(function (localStorageService, UserService, $rootScope) {
    var user_id = localStorageService.get("user_id");
    if(user_id) {
        UserService.load(user_id, function(){$rootScope.loggedIn = true});
    }
});

lingvoMovie.factory('unAuthorizedInterceptor', ['$q', '$window','$location', function($q, $window, $location) {
    return {
        'responseError': function(errorResponse) {
            switch (errorResponse.status) {
                case 401:
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
    $httpProvider.defaults.withCredentials = true;
}]);



