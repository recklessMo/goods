(function () {
    'use strict';
    angular
        .module('custom')
        .controller('StudentWorktableController', StudentWorktableController);
    StudentWorktableController.$inject = ['$scope', 'StudentService', 'SweetAlert', 'NgTableParams', 'blockUI', 'Notify'];

    function StudentWorktableController($scope, StudentService, SweetAlert, NgTableParams, blockUI, Notify) {

        $scope.obj = {};

        //左边侧边栏的查询条件
        $scope.tableParams = {
            page : 1,
            count : 10,
            orgName:'',
            gradeName:'',
            className:'',
            gender: '',
            searchStr:''
        };

        //初始化选择器列表
        function initSelector(){

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