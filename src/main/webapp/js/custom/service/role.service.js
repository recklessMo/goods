(function () {
    'use strict';

    angular
        .module('custom')
        .service('RoleService', RoleService);

    RoleService.$inject = ['$http'];
    function RoleService($http) {

        this.listRoles = listRoles;
        this.createRole = createRole;
        this.updateRole = updateRole;
        this.deleteRole = deleteRole;
        this.loadRole = loadRole;

        function listRoles(obj) {
            return $http({
                method: "POST",
                url : "/v1/role/list",
                data:obj,
                timeout: 5000
            });
        }

        function createRole(obj){
            return $http({
                method: "POST",
                url: "/v1/role/create",
                data: obj,
                timeout: 5000
            });
        }

        function updateRole(obj){
            return $http({
                method: "POST",
                url: "/v1/role/update",
                data: obj,
                timeout: 5000
            });
        }

        function deleteRole(obj){
            return $http({
                method: "POST",
                url: "/v1/role/delete",
                data: obj,
                timeout: 5000
            });
        }

        function loadRole(obj){
            return $http({
                method: "POST",
                url: "/v1/role/get",
                data: obj,
                timeout: 5000
            });
        }

    }
})
();
