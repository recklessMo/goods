(function () {
    'use strict';
    angular
        .module('custom')
        .controller('ResultTotalController', ResultTotalController);
    ResultTotalController.$inject = ['$scope', 'ScoreService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify'];

    function ResultTotalController($scope, ScoreService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify) {

        //控制左边栏是否显示
        $scope.showLeftWindow = true;

        $scope.showOrHideLeftWindow = function(){
            $scope.showLeftWindow = !$scope.showLeftWindow;
        }

        //控制左边栏成绩选择


        //控制左边栏参数填写




    }

})();