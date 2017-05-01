(function () {
    'use strict';
    angular
        .module('custom')
        .controller('ScoreListController', ScoreListController);
    ScoreListController.$inject = ['$scope', 'ExamService', 'ScoreService', 'DicService', 'SweetAlert', 'NgTableParams', 'blockUI', 'Notify'];

    function ScoreListController($scope, ExamService, ScoreService, DicService, SweetAlert, NgTableParams, blockUI, Notify) {

        //控制左边栏是否显示
        $scope.showLeftWindow = true;

        $scope.showOrHideLeftWindow = function () {
            $scope.showLeftWindow = !$scope.showLeftWindow;
        }

        //搜索考试
        $scope.tableParams = {};

        $scope.searchExam = function () {
            $scope.examTableParams = new NgTableParams({}, {
                counts: [],
                getData: function (params) {
                    blockUI.start();
                    $scope.tableParams.page = params.page();
                    $scope.tableParams.count = 10;
                    return ExamService.loadExams($scope.tableParams).then(function (data) {
                        blockUI.stop();
                        var result = data.data;
                        if (result.status == 200) {
                            params.total(result.totalCount);
                            return result.data;
                        } else {
                            SweetAlert.error("服务器内部错误, 请联系客服!");
                        }
                    }, function () {
                        SweetAlert.error("网络问题,请稍后重试!");
                        blockUI.stop();
                    });
                }
            });
        }

        $scope.searchExam();


        /**业务逻辑**/
        $scope.scoreParams = {examId: 1, page: 1, count : 100};
        $scope.scoreList = [];
        $scope.labelList = [];

        $scope.search = function(examId){
            blockUI.start();
            $scope.scoreParams.examId = examId;
            ScoreService.loadScoreList($scope.scoreParams).success(function (data) {
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
            $scope.scoreListTableParams = new NgTableParams({page: 1, count: 10},
                {
                    counts: [],
                    dataset: $scope.scoreList
                }
            );
        }

    }

})();