(function () {
    'use strict';
    angular
        .module('custom')
        .controller('EditExamScoreController', EditExamScoreController);
    EditExamScoreController.$inject = ['$scope', 'ExamService', 'DicService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify'];

    function EditExamScoreController($scope, ExamService, DicService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify) {

        $scope.item = $scope.ngDialogData;

        $scope.downloadExcel = function(examId){
            window.open("/common/file/score/downloadExcel?examId=" + examId);
        }

    }
})();