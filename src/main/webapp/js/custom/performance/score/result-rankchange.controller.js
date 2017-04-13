(function () {
    'use strict';
    angular
        .module('custom')
        .controller('ResultRankChangeController', ResultRankChangeController);
    ResultRankChangeController.$inject = ['$scope', 'ScoreService', 'ExamService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify'];

    function ResultRankChangeController($scope, ScoreService, ExamService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify) {

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


        $scope.examChooseList = [];

        $scope.addExam = function (data) {
            if (!_.find($scope.examChooseList, data)) {
                $scope.examChooseList.push(data);
            }
        }

        $scope.deleteExam = function (row) {
            $scope.examChooseList = _.without($scope.examChooseList, row);
        }

        //开始分析
        //数据
        $scope.rankList = [];
        $scope.labelList = [];

        $scope.startAnalyse = function(){
            if($scope.examChooseList.length != 2){
                SweetAlert.error("请选择两场考试进行对比分析!");
                return;
            }
            $scope.showTables();
        }

        //后续需要加上排序以及过滤的一系列逻辑.
        $scope.showTables = function(){
            $scope.rankListTableParams = new NgTableParams({page: 1, count: 10}, {dataset: $scope.rankList});
        }

    }
})();