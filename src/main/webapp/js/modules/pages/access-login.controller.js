/**=========================================================
 * Module: access-login.js
 * Demo for login api
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('app.pages')
        .controller('LoginFormController', LoginFormController);

    LoginFormController.$inject = ['$http'];
    function LoginFormController($http) {
        var vm = this;

        activate();

        ////////////////

        function activate() {
          // bind here all data from the form
          vm.account = {};
          // place the message if something goes wrong
          vm.authMsg = '';

          vm.login = function() {
            vm.authMsg = '';

            if(vm.loginForm.$valid) {
              $http({
                  method: "POST",
                  url: "j_spring_security_check",
                  data: $.param({username: vm.account.username, password: vm.account.password}),
                  headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                }).success(function(){
                  window.location.href = "/";
                });
            }
            else {

            }
          };
        }
    }
})();
