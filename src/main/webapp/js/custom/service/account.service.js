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
                timeout: 10000
            });
        }

        this.loadPermissions = function(){
            return $http({
                method: "POST",
                url : "/v1/permission/list",
                timeout: 10000
            });
        }

        this.loadUser = function(id){
            return $http({
                method:"POST",
                url: "/v1/user/get",
                data:id,
                timeout: 10000
            });
        }

        this.deleteUser = function(id){
            return $http({
                method: "POST",
                url : "v1/user/delete",
                data:id,
                timeout: 10000
            });
        }

        this.addUser = function(user){
            return $http({
                method: "POST",
                url : "/v1/user/add",
                data: user,
                timeout: 10000
            });
        }

        this.updateUser = function(user){
            return $http({
                method: "POST",
                url: "/v1/user/update",
                data:user,
                timeout: 10000
            });
        }

        this.updatePwd = function(id, pwd){
            return $http({
                method: "POST",
                url: "/v1/user/pwd/update",
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                data: $.param({id:id, pwd:pwd}),
                timeout: 10000
            });
        }

    }
})
();
