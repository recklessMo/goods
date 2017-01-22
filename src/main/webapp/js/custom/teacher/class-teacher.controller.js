(function () {
    'use strict';
    angular
        .module('custom')
        .controller('ClassTeacherController', ClassTeacherController);
    ClassTeacherController.$inject = ['$scope', 'ClassTeacherService', 'TeacherService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify'];

    function ClassTeacherController($scope, ClassTeacherService, TeacherService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify) {

        $scope.obj = {};
        $scope.obj.isEdit = false;

        $scope.courseList = [];
        $scope.singleClassList = [];

        $scope.userList = [];

        $scope.activate = function() {
            blockUI.start();
            TeacherService.loadTeachers({page:1, count:10000}).success(function(firstdata){
                if(firstdata.status == 200) {
                    $scope.userList = firstdata.data;
                    ClassTeacherService.listClassTeacher().success(function (data) {
                        blockUI.stop();
                        if (data.status == 200) {
                            $scope.courseList = data.data.courseList;
                            $scope.singleClassList = data.data.singleClassList;
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




    }
})();