(function () {
    'use strict';
    angular
        .module('custom')
        .controller('ScorePointController', ScorePointController);
    ScorePointController.$inject = ['$scope', 'ExamService', 'ScoreService', 'DicService', 'SweetAlert', 'NgTableParams', 'blockUI', 'Notify'];

    function ScorePointController($scope, ExamService, ScoreService, DicService, SweetAlert, NgTableParams, blockUI, Notify) {

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
                    var hash = {};
                    var tempList = [];
                    _.forEach($scope.scoreList, function(item){
                        if(!hash[item.classid]){
                            hash[item.classid] = true;
                            tempList.push({classId: item.classid, className: item.classname});
                        }
                    });
                    $scope.classList = _.sortBy(tempList, ['className']);
                    $scope.classList.unshift({classId:0, className:"全部"});
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

        $scope.export = function(examId, classId){
            if(_.isUndefined(classId) || _.isUndefined(examId)){
                SweetAlert.error("请先选择考试!");
                return;
            }
            window.open("/common/file/score/export?examId=" + examId + "&classId=" + classId);
        }

    }

})();