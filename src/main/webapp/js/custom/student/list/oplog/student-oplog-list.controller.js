(function () {
    'use strict';
    angular
        .module('custom')
        .controller('StudentOpLogListController', StudentOpLogListController);
    StudentOpLogListController.$inject = ['$scope','OpLogService', 'SweetAlert', 'NgTableParams', 'blockUI', 'Notify'];

    function StudentOpLogListController($scope, OpLogService, SweetAlert, NgTableParams, blockUI, Notify) {

        $scope.activate = function() {
        }

        $scope.activate();

    }
})();