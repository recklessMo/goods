(function () {
    'use strict';

    angular
        .module('custom')
        .service('OrgService', OrgService);

    OrgService.$inject = ['$http'];
    function OrgService($http) {

        this.loadOrgs = loadOrgs;

        function loadOrgs(data){
            return $http({
                method : "POST",
                data: data,
                timeout: 5000,
                url: "/v1/org/list"
            });
        }

    }
})
();
