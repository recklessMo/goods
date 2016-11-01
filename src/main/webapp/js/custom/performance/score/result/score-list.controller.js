(function () {
    'use strict';
    angular
        .module('custom')
        .controller('ScoreListController', ScoreListController);
    ScoreListController.$inject = ['$scope', 'ScoreService', 'DicService', 'SweetAlert', 'NgTableParams', 'blockUI', 'Notify'];

    function ScoreListController($scope, ScoreService, DicService, SweetAlert, NgTableParams, blockUI, Notify) {

        $scope.tableParams = {examId: 0, page: 1, count : 100};
        $scope.examId = 0;
        //当前需要显示的部分
        $scope.scoreList = [];
        //班级的列表
        $scope.classList = [{classId:0, className:"全年级"}];

        $scope.activate = function() {
            var page = {page : 1, count: 100, gradeId:1};
            DicService.loadClassByGrade(page).success(function(data){
                if(data.status == 200) {
                    $scope.classList.push.apply($scope.classList, data.data);
                    $scope.search();
                }
            }).error(function(){
                SweetAlert.error("发生了错误! 请刷新页面!");
            });
        }

        $scope.activate();

        $scope.search = function(){
            blockUI.start();
            ScoreService.loadScoreList($scope.tableParams).success(function (data) {
                blockUI.stop();
                if (data.status == 200) {
                    $scope.scoreList = data.data;
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
            $scope.scoreListTableParams = new NgTableParams({}, {
                counts: [100],
                getData: function ($defer, params) {
                    $defer.resolve($scope.scoreList);
                }
            });
        }

    }

})();