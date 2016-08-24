(function () {
    'use strict';
    angular
        .module('custom')
        .controller('EditCourseController', EditCourseController);
    EditCourseController.$inject = ['$scope', 'SettingService', 'SweetAlert', 'blockUI'];

    function EditCourseController($scope, SettingService, SweetAlert, blockUI) {

        var block = blockUI.instances.get("edit-course");

        $scope.course = _.clone($scope.ngDialogData.course);
        $scope.type = $scope.ngDialogData.type;

        activate();


        function activate(){
        }

        $scope.loading = false;

        $scope.save = function(course){

            if(!validate(course)){
                return;
            }

            $scope.loading = true;
            block.start();
            if($scope.type == 'add') {
                SettingService.addCourse(course).success(function (data) {
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
                SettingService.updateCourse(course).success(function (data) {
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

        function validate(course){
            return true;
        }

    }
})();