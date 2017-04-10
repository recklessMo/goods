(function () {
    'use strict';
    angular
        .module('custom')
        .controller('ResultSelfController', ResultSelfController);
    ResultSelfController.$inject = ['$scope', 'ScoreService', 'DicService', 'StudentService', 'ExamService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify'];

    function ResultSelfController($scope, ScoreService, DicService, StudentService, ExamService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify) {

        //data
        $scope.courseList = [];
        $scope.scoreList = [];

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
                    return ExamService.loadExams({
                        searchStr: $scope.tableParams.examName,
                        page: params.page(),
                        count: 10
                    }).then(function (data) {
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

        $scope.use = function (item) {
            $scope.selectedExam = item;
        }

        //控制右上角的班级列表, 文科班,理科班,单独班级,全年级等可以一起进行分析
        //控制左边栏参数填写
        //加载默认模板
        $scope.openTemplateDialog = function (type) {
            var dialog = ngDialog.open({
                template: 'app/views/custom/performance/template/template-list.html',
                controller: 'TemplateListController',
                className: 'ngdialog-theme-default max-dialog',
                data: {type: type}
            });
            dialog.closePromise.then(function (data) {
                if (!data.value.status) {
                    return;
                }
                $scope.template = data.value.value;
            });
        }

        //show 代表显示类型, type代表分析维度
        $scope.flag = {show: 0};
        //开始分析
        $scope.startAnalyse = function () {
            //判断模板是否选择, 以及考试是否选择
            if (angular.isUndefined($scope.selectedExam)) {
                SweetAlert.error("请选择考试!");
                return;
            }

            $scope.template = {id: 2};

            //if(angular.isUndefined($scope.template)){
            //    SweetAlert.error("请选择模板!")
            //    return;
            //}

            //both are ok , so we proceed .首先获取数据, 只加载一遍.
            $scope.show()
        }

        $scope.show = function () {
            $scope.flag.show = 1;
            $scope.studentTableParamsSetting.examId = $scope.selectedExam.examId;
            $scope.studentTableParamsSetting.searchStr = '';
            $scope.searchStudent();
        }


        /*******************************上面部分是公用的代码,主要负责考试选择,模板选择,年级选择************************************************************/

        $scope.studentTableParamsSetting = {
            page : 1,
            count : 12,
            examId: 0,
            searchStr: ''
        };

        $scope.searchStudent = function() {
            $scope.studentTableParams = new NgTableParams({}, {
                counts: [],
                getData: function (params) {
                    blockUI.start();
                    $scope.studentTableParamsSetting.page = params.page();
                    return StudentService.searchStudentByExam($scope.studentTableParamsSetting).then(function (data) {
                        var result = data.data;
                        blockUI.stop();
                        if (data.status == 200) {
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


    }

})();