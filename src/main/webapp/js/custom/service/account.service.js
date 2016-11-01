(function () {
    'use strict';

    angular
        .module('custom')
        .service('AccountService', AccountService);

    AccountService.$inject = ['$http'];
    function AccountService($http) {

        this.loadUsers = function(obj) {
            return $http({
                method: "POST",
                url : "/v1/user/list",
                data:obj,
                timeout: 5000
            });
        }

        this.loadPermissions = function(){
            return $http({
                method: "POST",
                url : "/v1/permission/list",
                timeout: 5000
            });
        }

        this.loadUser = function(id){
            return $http({
                method:"POST",
                url: "/v1/user/get",
                data:id,
                timeout:5000
            });
        }

        this.deleteUser = function(id){
            return $http({
                method: "POST",
                url : "v1/user/delete",
                data:id,
                timeout:5000
            });
        }

        this.addUser = function(user){
            return $http({
                method: "POST",
                url : "/v1/user/add",
                data: user,
                timeout:5000
            });
        }

        this.updateUser = function(user){
            return $http({
                method: "POST",
                url: "/v1/user/update",
                data:user,
                timeout:5000
            });
        }

    }
})
();
