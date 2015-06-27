'use strict';
/**
 * Created by yaroslav on 27.06.15.
 */

services.service('RestUtilsService', ['halClient', function (halClient) {
    this.entryPoint_cache;
    this.entryPoint = function() {
        if(this.entryPoint_cache) return this.entryPoint_cache;
        return this.entryPoint_cache = halClient.$get('api');
    };

    this.resolveResponse = function(response, entityName, single) {
        if (response.$has(entityName)) {
            return response.$get(entityName).then(function (entity) {
                if(single)
                    if(entity instanceof Array)
                        if (entity.length == 1)
                            return entity[0];
                        else
                            throw "Not a single object result";
                return entity;
            });
        }
    };
}]);
