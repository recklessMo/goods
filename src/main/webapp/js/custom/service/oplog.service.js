(function () {
    'use strict';

    angular
        .module('custom')
        .service('OpLogService', OpLogService);

    OpLogService.$inject = ['$http'];
    function OpLogService($http) {

        this.loadOpList = loadOpList;

        function loadOpList(data){
            return $http({
                method : "POST",
                data: data,
                timeout: 10000,
                url: "/v1/oplog/list"
            });
        }

    }
})
();
