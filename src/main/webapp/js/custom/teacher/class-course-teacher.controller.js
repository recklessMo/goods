(function () {
    'use strict';
    angular
        .module('custom')
        .controller('ClassCourseTeacherController', ClassCourseTeacherController);
    ClassCourseTeacherController.$inject = ['$scope', 'ClassCourseTeacherService', 'TeacherService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify'];

    function ClassCourseTeacherController($scope, ClassCourseTeacherService, TeacherService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify) {

        $scope.obj = {};
        $scope.obj.isEdit = false;

        $scope.courseList = [];
        $scope.singleClassList = [];

        $scope.userList = [];

        $scope.backupData = {};

        $scope.activate = function() {
            blockUI.start();
            TeacherService.loadTeachers({page:1, count:10000}).success(function(firstdata){
                if(firstdata.status == 200) {
                    $scope.userList = firstdata.data;
                    ClassCourseTeacherService.listClassTeacher().success(function (data) {
                        blockUI.stop();
                        if (data.status == 200) {
                            $scope.courseList = data.data.courseList;
                            $scope.backupData = JSON.stringify(data.data.singleClassCourseTeacherInfoList);
                            $scope.singleClassList = data.data.singleClassCourseTeacherInfoList;
                        }
                    }).error(function () {
                        SweetAlert.error("网络异常, 请稍后重试!");
                        blockUI.stop();
                    });
                }else{
                    SweetAlert.error("服务器异常, 请联系若水工作人员!");
                    blockUI.stop();
                }
            }).error(function(){
                SweetAlert.error("网络异常, 请稍后重试!");
                blockUI.stop();
            });
        }

        $scope.activate();


        $scope.save = function(){
            blockUI.start();
            ClassCourseTeacherService.saveClassTeacher($scope.singleClassList).success(function(data){
                blockUI.stop();
                if (data.status == 200) {
                    SweetAlert.success("保存成功!");
                    $scope.obj.isEdit = false;
                }
            }).error(function () {
                SweetAlert.error("网络异常, 请稍后重试!");
                blockUI.stop();
            });
        }

        $scope.selectUser = function(courseClass, data){
            courseClass.userName = data.userName;
        }

        $scope.cancel = function(){
            $scope.singleClassList = JSON.parse($scope.backupData);
            $scope.obj.isEdit = false;
        }


    }
})();