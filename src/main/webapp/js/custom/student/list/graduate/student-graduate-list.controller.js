(function () {
    'use strict';
    angular
        .module('custom')
        .controller('StudentGraduateListController', StudentGraduateListController);
    StudentGraduateListController.$inject = ['$scope', 'ngDialog', 'GraduateService', 'SweetAlert', 'NgTableParams', 'blockUI', 'Notify'];

    function StudentGraduateListController($scope, ngDialog, GraduateService, SweetAlert, NgTableParams, blockUI, Notify) {

        $scope.graduateDataList = [];

        $scope.$on('chooseSid', function (event, data) {
            $scope.sid = data;
            $scope.activate();
        });

        $scope.activate = function () {
            blockUI.start();
            GraduateService.getGraduateList({sid: $scope.sid}).success(function (data) {
                blockUI.stop();
                if (data.status == 200) {
                    $scope.graduateDataList = data.data;
                    $scope.showTables();
                } else {
                    SweetAlert.error("服务器内部错误, 请联系客服!");
                }
            }).error(function () {
                blockUI.stop();
                SweetAlert.error("网络问题,请稍后重试!");
            });
        }

        $scope.showTables = function () {
            $scope.graduateTableParams = new NgTableParams({}, {
                counts: [],
                dataset: $scope.graduateDataList
            });
        }

        //添加 & 编辑
        //type为1 代表编辑, type为2 添加
        $scope.edit = function (item, type) {
            item['sid'] = $scope.sid;
            var dialog = ngDialog.open({
                template: 'app/views/custom/student/list/graduate/student-graduate-add.html',
                controller: 'StudentGraduateAddController',
                className: 'ngdialog-theme-default custom-width-800',
                data: {data: item, type: type}
            });
            dialog.closePromise.then(function (data) {
                if (data.value != 'reload') {
                    return;
                }
                $scope.activate();
            });
        };

        //删除
        $scope.delete = function(id){
            SweetAlert.swal({
                title: '确认删除?',
                type: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#DD6B55',
                confirmButtonText: '是',
                cancelButtonText: '否',
                closeOnConfirm: true,
                closeOnCancel: true
            }, function(isConfirm){
                if (isConfirm) {
                    //这里可以进行调试,查看$scope,因为table会创建一个子scope
                    //然后子scope里面就不能用this了,因为this就指向了子scope,
                    //实际上在table的每一行里面的点击是调用了父scope的delete方法
                    blockUI.start();
                    GraduateService.deleteGraduate(id).success(function () {
                        Notify.alert("删除成功!", {status:"success", timeout: 3000});
                        $scope.activate();
                        blockUI.stop();
                    }).error(function(){
                        blockUI.stop();
                        SweetAlert.error("网络问题, 请稍后重试!");
                    });
                }
            });
        }

    }
})();