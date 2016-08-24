(function () {
    'use strict';
    angular
        .module('custom')
        .controller('EditClassController', EditClassController);
    EditClassController.$inject = ['$scope', 'SettingService', 'SweetAlert', 'blockUI'];

    function EditClassController($scope, SettingService, SweetAlert, blockUI) {

        var block = blockUI.instances.get("edit-class");

        $scope.group = _.clone($scope.ngDialogData.data);
        $scope.type = $scope.ngDialogData.type;
        $scope.gradeId = $scope.ngDialogData.gradeId;

        activate();


        function activate(){
        }

        $scope.loading = false;

        $scope.save = function(group){

            if(!validate(group)){
                return;
            }

            $scope.loading = true;
            block.start();
            if($scope.type == 'add') {
                group.gradeId = $scope.gradeId;
                SettingService.addClass(group).success(function (data) {
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
                    $scope.closeThisDialog('reload');
                    block.stop();
                });
            }else if($scope.type == 'edit'){
                SettingService.updateClass(group).success(function (data) {
                    $scope.loading = false;
                    block.stop();
                    if (data.status == 200) {
                        SweetAlert.success("修改成功!");
                        $scope.closeThisDialog('reload');
                    } else {
                        //更新失败的情况
                        SweetAlert.error("服务器异常,请稍后重试");
                    }
                }).error(function () {
                    SweetAlert.error("网络问题,稍后重试!");
                    $scope.loading = false;
                    $scope.closeThisDialog('reload');
                    block.stop();
                });
            }
        }

        function validate(group){
            return true;
        }

    }
})();