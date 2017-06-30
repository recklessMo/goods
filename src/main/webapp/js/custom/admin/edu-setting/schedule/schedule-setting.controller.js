(function () {
    'use strict';
    angular
        .module('custom')
        .controller('ScheduleSettingController', ScheduleSettingController);
    ScheduleSettingController.$inject = ['$scope', 'SettingService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify', '$resource'];

    function ScheduleSettingController($scope, SettingService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify, $resource) {

        $scope.tableParams = {
            page : 1,
            count: 20
        };

        $scope.activate = function() {
            $scope.scheduleTableParams = new NgTableParams({}, {
                getData: function(params){
                    blockUI.start();
                    return SettingService.listSchedule().then(function(data){
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

        $scope.editSchedule = function(schedule, type){
            var dialog= ngDialog.open({
                template: 'app/views/custom/admin/edu-setting/schedule/add-schedule.html',
                controller: 'AddScheduleController',
                className: 'ngdialog-theme-default custom-width-800',
                data : {schedule: schedule, type:type}
            });
            dialog.closePromise.then(function(data){
                if(data.value != 'reload'){
                    return;
                }
                $scope.scheduleTableParams.reload();
            });
        }

        $scope.deleteSchedule = function(id){
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
                    SettingService.deleteJob(id).success(function () {
                        Notify.alert("删除成功!", {status:"success", timeout: 3000});
                        $scope.jobTableParams.reload();
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