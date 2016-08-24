(function () {
    'use strict';
    angular
        .module('custom')
        .controller('StudentPayListController', StudentPayListController);
    StudentPayListController.$inject = ['$scope','PayService', 'SweetAlert', 'NgTableParams', 'blockUI', 'Notify'];

    function StudentPayListController($scope, PayService, SweetAlert, NgTableParams, blockUI, Notify) {

        $scope.activate = function() {
        }

        $scope.activate();

    }
})();