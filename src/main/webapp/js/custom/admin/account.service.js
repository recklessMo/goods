(function () {
    'use strict';

    angular
        .module('custom')
        .service('AccountService', AccountService);

    AccountService.$inject = ['$http'];
    function AccountService($http) {

        this.loadUsers = function() {
            return $http.get("/v1/user/list");
        }

        this.loadUser = function(id){
            return $http.post("/v1/user/get", id);
        }

        this.deleteUser = function(id){
            return $http.post("v1/user/delete", id);
        }

        this.addUser = function(user){
            return $http.post("/v1/user/add", user);
        }

        this.updateUser = function(user){
            return $http.post("/v1/user/update", user);
        }

    }
})
();
