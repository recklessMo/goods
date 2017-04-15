(function () {
    'use strict';
    angular
        .module('custom')
        .controller('ResultAbsenseController', ResultAbsenseController);
    ResultAbsenseController.$inject = ['$scope', 'ScoreService', 'ExamService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify'];

    function ResultAbsenseController($scope, ScoreService, ExamService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify) {

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


        $scope.dataList = [];

        $scope.startAnalyse = function(id){
            blockUI.start();
            ScoreService.loadScoreAbsense(id).success(function(data){
                blockUI.stop();
                if(data.status == 200){
                    $scope.dataList = data.data;
                }else{
                    SweetAlert.error("服务器内部错误, 请联系客服!");
                }
            }).error(function(){
                SweetAlert.error("网络问题,请稍后重试!");
                blockUI.stop();
            });
        }


    }
})();