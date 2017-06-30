(function () {
    'use strict';
    angular
        .module('custom')
        .controller('AddScheduleController', AddScheduleController);
    AddScheduleController.$inject = ['$scope', 'SettingService', 'SweetAlert', 'blockUI'];

    function AddScheduleController($scope, SettingService, SweetAlert, blockUI) {

        var block = blockUI.instances.get("add-schedule");

        $scope.schedule = _.clone($scope.ngDialogData.schedule);

        activate();


        function activate(){
        }

        $scope.loading = false;

        $scope.save = function(schedule){

            if(!validate(schedule)){
                return;
            }

            $scope.loading = true;
            block.start();
            SettingService.saveSchedule(schedule).success(function (data) {
                $scope.loading = false;
                block.stop();
                if (data.status == 200) {
                    SweetAlert.success("添加成功!");
                    $scope.closeThisDialog('reload');
                } else {
                    //更新失败的情况
                    SweetAlert.error("服务器异常,请稍后重试");
                }
            }).error(function () {
                SweetAlert.error("网络问题,稍后重试!");
                $scope.loading = false;
                block.stop();
            });
        }

        function validate(schedule){
            return true;
        }

    }
})();