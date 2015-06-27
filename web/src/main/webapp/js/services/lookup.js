'use strict';
/**
 * Created by yaroslav on 27.06.15.
 */

services.service('LookupService', ['$http', 'halClient','RestUtilsService', function ($http, halClient, RestUtilsService) {
    this.lookup = function(type) {
        return RestUtilsService.entryPoint().then(function (entry) {
            return $http.get(entry.$href('lookup'), {
                params: {name: type}
            })
        });
    };
}]);