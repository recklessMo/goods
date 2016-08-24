(function () {
    'use strict';
    angular
        .module('custom')
        .controller('StudentViewWorktableController', StudentViewWorktableController);
    StudentViewWorktableController.$inject = ['$scope', 'StudentService', 'DicService', 'SweetAlert', 'NgTableParams', 'blockUI', 'Notify'];

    function StudentViewWorktableController($scope, StudentService,DicService, SweetAlert, NgTableParams, blockUI, Notify) {

        $scope.obj = {};

        //左边侧边栏的查询条件
        $scope.tableParams = {
            page : 1,
            count : 12
        };

        $scope.gradeList = [];
        $scope.classList = [];

        //初始化选择器列表
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




        $scope.search = function() {
            $scope.studentTableParams = new NgTableParams($scope.tableParams, {
                counts: [],
                getData: function ($defer, params) {
                    blockUI.start();
                    StudentService.searchStudent(params.parameters()).success(function (data) {
                        if (data.status == 200) {
                            params.total(data.totalCount);
                            $scope.obj.totalCount = data.totalCount;
                            console.log(data);
                            $defer.resolve(data.data);
                            blockUI.stop();
                        }
                    }).error(function () {
                        SweetAlert.error("网络问题,请稍后重试!");
                        blockUI.stop();
                    });
                }
            });
        }


    }
})();