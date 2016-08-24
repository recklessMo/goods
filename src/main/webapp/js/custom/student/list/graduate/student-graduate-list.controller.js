(function () {
    'use strict';
    angular
        .module('custom')
        .controller('StudentGraduateListController', StudentGraduateListController);
    StudentGraduateListController.$inject = ['$scope','GraduateService', 'SweetAlert', 'NgTableParams', 'blockUI', 'Notify'];

    function StudentGraduateListController($scope, GraduateService, SweetAlert, NgTableParams, blockUI, Notify) {

        $scope.activate = function() {
        }

        $scope.activate();

    }
})();