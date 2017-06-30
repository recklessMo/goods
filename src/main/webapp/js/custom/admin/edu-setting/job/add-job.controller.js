(function () {
    'use strict';
    angular
        .module('custom')
        .controller('AddJobController', AddJobController);
    AddJobController.$inject = ['$scope', 'SettingService', 'SweetAlert', 'blockUI'];

    function AddJobController($scope, SettingService, SweetAlert, blockUI) {

        var block = blockUI.instances.get("add-job");

        $scope.job = {};

        activate();


        function activate(){
        }

        $scope.loading = false;

        $scope.save = function(job){

            if(!validate(job)){
                return;
            }

            $scope.loading = true;
            block.start();
            SettingService.addJob(job).success(function (data) {
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

        function validate(job){
            if(_.isUndefined(job.jobName) || _.isUndefined(job.jobDetail)){
                SweetAlert.error("请填写必填信息!");
                return false;
            }
            return true;
        }

    }
})();