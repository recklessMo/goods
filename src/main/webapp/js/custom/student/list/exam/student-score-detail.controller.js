(function () {
    'use strict';
    angular
        .module('custom')
        .controller('StudentScoreDetailController', StudentScoreDetailController);
    StudentScoreDetailController.$inject = ['$scope','StudentService', 'ScoreService',  'SweetAlert', 'NgTableParams', 'blockUI', 'Notify'];

    function StudentScoreDetailController($scope, StudentService, ScoreService, SweetAlert, NgTableParams, blockUI, Notify) {

        $scope.item = $scope.ngDialogData;

        $scope.scoreDetailTableParams = new NgTableParams({}, {
            counts: [],
            dataset: $scope.item.courseScoreList
        })

    }
})();