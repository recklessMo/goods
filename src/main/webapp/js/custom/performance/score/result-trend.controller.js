(function () {
    'use strict';
    angular
        .module('custom')
        .controller('ScoreTrendController', ScoreTrendController);
    ScoreTrendController.$inject = ['$scope', 'ExamService', 'ScoreService', 'StudentService', 'DicService', 'SweetAlert', 'NgTableParams', 'blockUI', 'Notify'];

    function ScoreTrendController($scope, ExamService, ScoreService, StudentService, DicService, SweetAlert, NgTableParams, blockUI, Notify) {

        //控制左边栏是否显示
        $scope.showLeftWindow = true;
        $scope.showOrHideLeftWindow = function () {
            $scope.showLeftWindow = !$scope.showLeftWindow;
        }
        $scope.tableParams = {};
        $scope.obj = {};
        $scope.searchStudent = function () {
            $scope.studentTableParams = new NgTableParams({}, {
                counts: [],
                getData: function (params) {
                    blockUI.start();
                    $scope.tableParams.page = params.page();
                    $scope.tableParams.count = params.count();
                    return StudentService.listStudent($scope.tableParams).then(function (data) {
                        var result = data.data;
                        blockUI.stop();
                        if (result.status == 200) {
                            params.total(result.totalCount);
                            $scope.obj.totalCount = result.totalCount;
                            return result.data;
                        }else{
                            SweetAlert.error("服务器内部错误, 请联系客服!");
                        }
                    }, function () {
                        SweetAlert.error("网络问题,请稍后重试!");
                        blockUI.stop();
                    });
                }
            });
        }

        $scope.searchStudent();


        /*************************************业务逻辑****************************************/
        $scope.choosedStudent = {};
        $scope.choose = function(data){
            $scope.choosedStudent = data;
        }

        $scope.examTypeList = ['小测', '周考', '月考', '期中', '期末'];
        $scope.showTypeList = ['表格', '直方图', '折线图'];


        $scope.analyseParams = {examTypes: [], showType: '表格'};


        $scope.labelList = [];
        $scope.scoreList = [];

        $scope.startAnalyse = function(){
            $scope.analyseParams.sid = $scope.choosedStudent.sid;
            if(_.isUndefined($scope.analyseParams.sid)){
                SweetAlert.error("请选择要查询成绩的学生!");
                return;
            }
            if($scope.analyseParams.examTypes.length == 0){
                SweetAlert.error("请选择需要分析的考试类型!");
                return;
            }
            blockUI.start();
            ScoreService.loadScoreTrend($scope.analyseParams).success(function (data) {
                blockUI.stop();
                if (data.status == 200) {
                    if(_.isUndefined(data.data)){
                        Notify.alert("未找到考试数据!", {status:"success", timeout: 3000});
                    }
                    $scope.labelList = data.data.labelList;
                    $scope.scoreList = data.data.dataList;
                    $scope.showTables();
                } else {
                    SweetAlert.error("加载成绩列表发生了错误! 请刷新页面!");
                }
            }).error(function () {
                SweetAlert.error("网络问题,请稍后重试!");
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

    }

})();