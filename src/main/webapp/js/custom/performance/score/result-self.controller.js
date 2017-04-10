(function () {
    'use strict';
    angular
        .module('custom')
        .controller('ResultSelfController', ResultSelfController);
    ResultSelfController.$inject = ['$scope', 'ScoreService', 'DicService', 'StudentService', 'ExamService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify'];

    function ResultSelfController($scope, ScoreService, DicService, StudentService, ExamService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify) {

        //start初始化年级选择器列表
        $scope.gradeList = [];
        $scope.classList = [];
        function initSelector(){
            blockUI.start();
            DicService.loadAllGrade().success(function(data){
                if(data.status == 200){
                    $scope.gradeList = data.data;
                }
                blockUI.stop();
            }).error(function(){
                SweetAlert.error("网络异常, 请稍后重试!");
                blockUI.stop();
            });

            $scope.selectGrade = function(data){
                $scope.classList = data.classList;
            }
        }
        initSelector();
        //end初始化年级选择器列表

        //左边侧边栏的查询条件
        $scope.tableParams = {
            page : 1,
            count : 12
        };

        $scope.search = function() {
            $scope.examTableParams = new NgTableParams({}, {
                counts: [],
                getData: function (params) {
                    blockUI.start();
                    $scope.tableParams.page = params.page();
                    $scope.tableParams.count = 12;
                    return ExamService.searchExamByStudent($scope.tableParams).then(function (data) {
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