(function () {
    'use strict';
    angular
        .module('custom')
        .controller('ClassLevelSettingController', ClassLevelSettingController);
    ClassLevelSettingController.$inject = ['$scope', 'SettingService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify', '$resource'];

    function ClassLevelSettingController($scope, SettingService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify, $resource) {

        $scope.tableParams = {
            page : 1,
            count: 20
        };

        $scope.activate = function() {
            $scope.levelTableParams = new NgTableParams({}, {
                getData: function(params){
                    blockUI.start();
                    return SettingService.listClassLevel({page: params.page(), count: params.count()}).then(function(data){
                        blockUI.stop();
                        var result = data.data;
                        if(result.status == 200){
                            params.total(result.totalCount);
                            return result.data;
                        }else{
                            SweetAlert.error("服务器内部错误, 请联系客服!");
                        }
                    }, function(){
                        blockUI.stop();
                        SweetAlert.error("网络问题, 请稍后重试!");
                    });
                }
            })
        }

        $scope.activate();

        //年级操作
        $scope.addLevel = function(){
            var dialog= ngDialog.open({
                template: 'app/views/custom/admin/edu-setting/classlevel/add-class-level.html',
                controller: 'AddClassLevelController',
                className: 'ngdialog-theme-default custom-width-800',
            });
            dialog.closePromise.then(function(data){
                if(data.value != 'reload'){
                    return;
                }
                $scope.levelTableParams.reload();
            });
        }

        $scope.deleteLevel = function(levelName){
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
                    SettingService.deleteClassLevel(levelName).success(function () {
                        Notify.alert("删除成功!", {status:"success", timeout: 3000});
                        $scope.levelTableParams.reload();
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