(function () {
    'use strict';

    angular
        .module('custom')
        .service('OrgService', OrgService);

    OrgService.$inject = ['$http'];
    function OrgService($http) {

        this.loadOrgs = loadOrgs;
        this.addOrg = addOrg;

        function loadOrgs(data){
            return $http({
                method : "POST",
                data: data,
                timeout: 10000,
                url: "/v1/org/list"
            });
        }

        function addOrg(data){
            return $http({
                method : "POST",
                data: data,
                timeout: 10000,
                url: "/v1/org/add"
            });
        }

    }
})
();
