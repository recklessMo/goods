(function () {
    'use strict';
    angular
        .module('custom')
        .controller('StudentExportBatchController', StudentExportBatchController);
    StudentExportBatchController.$inject = ['$scope', 'StudentService', 'DicService', 'SweetAlert', 'blockUI', 'Notify', 'NgTableParams'];

    function StudentExportBatchController($scope, StudentService, DicService, SweetAlert, blockUI, Notify, NgTableParams) {

        $scope.gradeList = [];
        $scope.classList = [];

        //初始化选择器列表
        function initSelector(){
            blockUI.start();
            DicService.loadAllGrade().success(function(data){
                if(data.status == 200){
                    $scope.gradeList = data.data;
                    //添加全部，因为bootstrap引用出了问题
                    _.forEach($scope.gradeList, function(item){
                        item.classList.unshift({classId: 0, className:'全部'});
                    });
                    $scope.gradeList.unshift({gradeId: 0, gradeName:'全部', classList:[]});
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


        $scope.tableParams = {
            gradeId: 0,
            classId: 0,
            page : 1,
            count: 10
        };

        $scope.activate = function() {
            $scope.studentTableParams = new NgTableParams({}, {
                getData: function (params) {
                    $scope.tableParams.page = params.page();
                    $scope.tableParams.count = params.count();
                    blockUI.start();
                    return StudentService.loadStudent($scope.tableParams).then(function (data) {
                        blockUI.stop();
                        var result = data.data;
                        if (result.status == 200) {
                            params.total(result.totalCount);
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

        $scope.activate();

        $scope.export = function(){
            window.open("/common/file/student/export?gradeId=" + $scope.tableParams.gradeId + "&classId=" + $scope.tableParams.classId);
        }

    }
})();