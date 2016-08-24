(function () {
    'use strict';
    angular
        .module('custom')
        .controller('ClassSettingController', ClassSettingController);
    ClassSettingController.$inject = ['$scope', 'SettingService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify', '$resource'];

    function ClassSettingController($scope, SettingService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify, $resource) {

        $scope.tableParams = {
            page : 1,
            count: 20
        };

        $scope.tableParams.gradeId = $scope.ngDialogData.gradeId;

        $scope.activate = function() {
            $scope.classTableParams = new NgTableParams($scope.tableParams, {
                getData: function($defer, params){
                    blockUI.start();
                    SettingService.listClass(params.parameters()).success(function(data){
                        if(data.status == 200){
                            $defer.resolve(data.data);
                            params.total(data.totalCount);
                        }
                        blockUI.stop();
                    }).error(function(){
                        SweetAlert.error("网络异常, 请稍后重试!");
                        blockUI.stop();
                    });
                }
            })
        }

        $scope.activate();

        //添加班级
        $scope.addClass = function(gradeId){
            var dialog= ngDialog.open({
                template: 'app/views/custom/admin/edu-setting/grade/edit-class.html',
                controller: 'EditClassController',
                className: 'ngdialog-theme-default custom-width-800',
                data : {type: 'add', gradeId: gradeId}
            });
            dialog.closePromise.then(function(data){
                if(data.value != 'reload'){
                    return;
                }
                $scope.classTableParams.reload();
            });
        }

        $scope.editClass = function(group){
            var dialog= ngDialog.open({
                template: 'app/views/custom/admin/edu-setting/grade/edit-class.html',
                controller: 'EditClassController',
                className: 'ngdialog-theme-default custom-width-800',
                data : {type: 'edit', data : group}
            });
            dialog.closePromise.then(function(data){
                if(data.value != 'reload'){
                    return;
                }
                $scope.classTableParams.reload();
            });
        }

        $scope.deleteClass = function(id){
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
                    SettingService.deleteClass(id).success(function () {
                        Notify.alert("删除成功!", {status:"success", timeout: 3000});
                        $scope.classTableParams.reload();
                        blockUI.stop();
                    }).error(function(){
                        blockUI.stop();
                        Notify.alert("网络有问题,请稍后重试!", {status:"error", timeout: 3000});
                    });
                }
            });
        }

    }
})();