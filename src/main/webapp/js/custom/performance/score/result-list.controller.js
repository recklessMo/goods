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
        $scope.obj = {classId: 0};
        $scope.scoreList = [];
        $scope.labelList = [];
        $scope.classList = [];

        $scope.search = function(exam){
            blockUI.start();
            $scope.obj.examId = exam.examId;
            $scope.obj.examName = exam.examName;
            ScoreService.loadScoreList({examId: exam.examId}).success(function (data) {
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

        $scope.showTables = function(){
            $scope.scoreListTableParams = new NgTableParams({page: 1, count: 10},
                {
                    counts: [],
                    dataset: $scope.scoreList
                }
            );
        }

        $scope.export = function(examId, classId){
            if(_.isUndefined(classId) || _.isUndefined(examId)){
                SweetAlert.error("请先选择考试!");
                return;
            }
            window.open("/common/file/score/export?examId=" + examId + "&classId=" + classId);
        }

    }

})();