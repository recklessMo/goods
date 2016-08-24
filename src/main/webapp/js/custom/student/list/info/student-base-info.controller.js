(function () {
    'use strict';
    angular
        .module('custom')
        .controller('StudentBaseInfoController', StudentBaseInfoController);
    StudentBaseInfoController.$inject = ['$scope','StudentService', 'SweetAlert', 'NgTableParams', 'blockUI', 'Notify'];

    function StudentBaseInfoController($scope, StudentService, SweetAlert, NgTableParams, blockUI, Notify) {

        $scope.isEdit = true;

        $scope.activate = function() {
        }

        $scope.activate();

    }
})();