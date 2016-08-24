(function () {
    'use strict';
    angular
        .module('custom')
        .controller('EditGradeController', EditGradeController);
    EditGradeController.$inject = ['$scope', 'SettingService', 'SweetAlert', 'blockUI'];

    function EditGradeController($scope, SettingService, SweetAlert, blockUI) {

        var block = blockUI.instances.get("edit-grade");

        $scope.grade = _.clone($scope.ngDialogData.grade);
        $scope.type = $scope.ngDialogData.type;

        activate();


        function activate(){
        }

        $scope.loading = false;

        $scope.save = function(grade){

            if(!validate(grade)){
                return;
            }

            $scope.loading = true;
            block.start();
            if($scope.type == 'add') {
                SettingService.addGrade(grade).success(function (data) {
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
            }else if($scope.type == 'edit'){
                SettingService.updateGrade(grade).success(function (data) {
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
                    block.stop();
                });
            }
        }

        function validate(grade){
            return true;
        }

    }
})();