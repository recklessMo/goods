(function () {
    'use strict';
    angular
        .module('custom')
        .controller('ScoreListController', ScoreListController);
    ScoreListController.$inject = ['$scope', 'ScoreService', 'DicService', 'SweetAlert', 'NgTableParams', 'blockUI', 'Notify'];

    function ScoreListController($scope, ScoreService, DicService, SweetAlert, NgTableParams, blockUI, Notify) {

        $scope.tableParams = {examId: 1, page: 1, count : 100};
        //当前需要显示的部分
        $scope.examId = 1;
        $scope.scoreList = [];
        $scope.labelList = [];

        $scope.remoteUrlRequestFn = function(str){
            return {str: str};
        }


        $scope.search = function(){
            blockUI.start();
            ScoreService.loadScoreList($scope.tableParams).success(function (data) {
                blockUI.stop();
                if (data.status == 200) {
                    $scope.labelList = data.data.labelList;
                    $scope.scoreList = data.data.dataList;
                    $scope.showTables();
                } else {
                    SweetAlert.error("发生了错误! 请刷新页面!");
                }
            }).error(function () {
                blockUI.stop();
            });
        }


        //后续需要加上排序以及过滤的一系列逻辑.
        $scope.showTables = function(){
            $scope.scoreListTableParams = new NgTableParams({page: 1, count: 10}, {dataset: $scope.scoreList});
        }

    }

})();