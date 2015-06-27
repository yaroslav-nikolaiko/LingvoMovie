'use strict';
/**
 * Created by yaroslav on 27.06.15.
 */

controllers.controller('LoginController', function($scope, $http, $location, $rootScope, UserService, localStorageService){
    $scope.init = function () {
        var pathname = window.location.href;
        if(pathname.indexOf("signup=true") > -1)  {
            $('#loginbox').hide();
            $('#signupbox').show();
        }
    };

    $scope.login = function() {
        $http({
            method: 'POST',
            url: 'api/login',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            transformRequest: function(obj) {
                var str = [];
                for(var p in obj)
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                return str.join("&");
            },
            data: {username: $scope.name, password: $scope.password, _spring_security_remember_me : $scope.remember_me}
        }).success(function (id, status, headers, config) {
            $rootScope.loggedIn = true;
            UserService.load(id);
            localStorageService.set("user_id", id);
            $location.path('/home');
        }).error(function(data, status, headers, config) {
            $rootScope.loggedIn = false;
            console.log(data);
        });

    };

    $scope.signUp = function() {
        $http.post('api/users',
            {name: $scope.name, password: $scope.password, email : $scope.email}).
            success(function(data, status, headers, config) {
                // this callback will be called asynchronously
                // when the response is available
            }).
            error(function(data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };

});