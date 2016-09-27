(function () {
    'use strict';
    angular
        .module('custom')
        .controller('AnalyzeScoreController', AnalyzeScoreController);
    AnalyzeScoreController.$inject = ['$scope', 'ScoreService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify'];

    function AnalyzeScoreController($scope, ScoreService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify) {

        $scope.examList = [];
        $scope.templateList = [];

        $scope.data = {};

    }

})();