(function () {
    'use strict';
    angular
        .module('custom')
        .controller('ClassScheduleCourseController', ClassScheduleCourseController);
    ClassScheduleCourseController.$inject = ['$scope', 'ClassScheduleCourseService', 'SettingService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify'];

    function ClassScheduleCourseController($scope, ClassScheduleCourseService, SettingService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify) {

        $scope.obj = {};
        $scope.obj.isEdit = false;

        $scope.scheduleList = [];
        $scope.singleClassList = [];

        $scope.courseList = [];

        $scope.backupData = {};

        $scope.activate = function() {
            blockUI.start();
            SettingService.listCourse({page:1, count:10000}).success(function(firstdata){
                if(firstdata.status == 200) {
                    $scope.courseList = firstdata.data;
                    ClassScheduleCourseService.listScheduleCourse().success(function (data) {
                        blockUI.stop();
                        if (data.status == 200) {
                            $scope.scheduleList = data.data.scheduleList;
                            $scope.backupData = JSON.stringify(data.data.singleClassScheduleCourseInfoList);
                            $scope.singleClassList = data.data.singleClassScheduleCourseInfoList;
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
            ClassScheduleCourseService.saveScheduleCourse($scope.singleClassList).success(function(data){
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

        $scope.selectCourse = function(scheduleCourse, data){
            scheduleCourse.courseName = data.courseName;
        }

        $scope.cancel = function(){
            $scope.singleClassList = JSON.parse($scope.backupData);
            $scope.obj.isEdit = false;
        }


    }
})();