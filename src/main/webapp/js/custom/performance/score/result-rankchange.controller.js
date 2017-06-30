(function () {
    'use strict';
    angular
        .module('custom')
        .controller('ResultRankChangeController', ResultRankChangeController);
    ResultRankChangeController.$inject = ['$scope', 'ScoreService', 'DicService', 'ExamService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify'];

    function ResultRankChangeController($scope, ScoreService, DicService, ExamService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify) {


        $scope.examTypeList = ['全部', '小测', '周考', '月考', '期中', '期末'];

        $scope.gradeList = [];
        $scope.classList = [];

        //初始化选择器列表
        function initSelector(){
            blockUI.start();
            DicService.loadAllGrade().success(function(data){
                if(data.status == 200){
                    $scope.gradeList = data.data;
                    _.forEach($scope.gradeList, function(item){
                        item.classList.unshift({classId: 0, className:'全部'});
                    });
                }
                blockUI.stop();
            }).error(function(){
                SweetAlert.error("网络异常, 请稍后重试!");
                blockUI.stop();
            });

            $scope.selectGrade = function(data){
                $scope.classList = data.classList;
                $scope.tableParams.classId = 0;
            }
        }

        initSelector();

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


        $scope.startAnalyse = function () {
            if ($scope.examChooseList.length != 2) {
                SweetAlert.error("请选择两场考试进行对比分析!");
                return;
            }
            blockUI.start();
            var params = [$scope.examChooseList[0].examId, $scope.examChooseList[1].examId];
            ScoreService.loadScoreRankChange(params).success(function (data) {
                blockUI.stop();
                if (data.status == 200) {
                    $scope.labelList = data.data.labelList;
                    $scope.dataList = data.data.dataList;
                    $scope.showTables();
                } else {
                    SweetAlert.error("服务器内部错误, 请联系客服!");
                }
            }).error(function () {
                SweetAlert.error("网络问题,请稍后重试!");
                blockUI.stop();
            });
        }

        //后续需要加上排序以及过滤的一系列逻辑.
        $scope.showTables = function () {
            $scope.rankListTableParams = new NgTableParams({page: 1, count: 10},
                {
                    counts: [],
                    dataset: $scope.dataList
                }
            );
        }

        $scope.export = function () {
            if ($scope.examChooseList.length != 2) {
                SweetAlert.error("请选择两场考试进行对比分析!");
                return;
            }
            window.open("/common/file/rankchange/export?first=" + $scope.examChooseList[0].examId + "&second=" + $scope.examChooseList[1].examId);

        }

    }
})();