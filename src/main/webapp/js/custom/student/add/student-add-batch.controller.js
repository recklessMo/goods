(function () {
    'use strict';
    angular
        .module('custom')
        .controller('StudentAddBatchController', StudentAddBatchController);
    StudentAddBatchController.$inject = ['$scope', 'StudentService', 'DicService', 'SweetAlert', 'blockUI', 'Notify'];

    function StudentAddBatchController($scope, StudentService, DicService, SweetAlert, blockUI, Notify) {


        $scope.downloadExcel = function(){
            window.open("/common/file/student/downloadExcel");
        }

    }
})();