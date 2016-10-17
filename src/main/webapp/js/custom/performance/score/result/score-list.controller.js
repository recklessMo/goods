(function () {
    'use strict';
    angular
        .module('custom')
        .controller('ScoreListController', ScoreListController);
    ScoreListController.$inject = ['$scope', 'ScoreService', 'SweetAlert', 'NgTableParams', 'blockUI', 'Notify'];

    function ScoreListController($scope, ScoreService, SweetAlert, NgTableParams, blockUI, Notify) {

        $scope.examId = 0;
        //当前需要显示的部分
        $scope.scoreList = [];

        //后续需要加上排序以及过滤的一系列逻辑.
        $scope.showTables = function(){
            $scope.scoreListTableParams = new NgTableParams({}, {
                counts: [],
                getData: function ($defer, params) {
                    $defer.resolve($scope.scoreList);
                }
            });
        }


        $scope.activate = function() {
            ScoreService.loadScoreList($scope.examId).success(function(data){
                blockUI.stop();
                if(data.status == 200){
                    $scope.scoreList = data.data;
                    $scope.showTables();
                }else{
                    SweetAlert.error("发生了错误! 请刷新页面!");
                }
            }).error(function(){
                blockUI.stop();
            });
        }

        $scope.activate();

    }

})();