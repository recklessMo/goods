(function () {
    'use strict';
    angular
        .module('custom')
        .controller('CourseSettingController', CourseSettingController);
    CourseSettingController.$inject = ['$scope', 'SettingService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify', '$resource'];

    function CourseSettingController($scope, SettingService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify, $resource) {

        $scope.tableParams = {
            page : 1,
            count: 20
        };

        $scope.activate = function() {
            $scope.courseTableParams = new NgTableParams($scope.tableParams, {
                getData: function($defer, params){
                    blockUI.start();
                    SettingService.listCourse(params.parameters()).success(function(data){
                        if(data.status == 200){
                            $defer.resolve(data.data);
                            params.total(data.totalCount);
                        }
                        blockUI.stop();
                    }).error(function(){
                        SweetAlert.error("网络异常, 请稍后重试!");
                        blockUI.stop();
                    });
                }
            })
        }

        $scope.activate();

        //年级操作
        $scope.addCourse = function(){
            var dialog= ngDialog.open({
                template: 'app/views/custom/admin/edu-setting/course/edit-course.html',
                controller: 'EditCourseController',
                className: 'ngdialog-theme-default custom-width-800',
                data : {type: 'add'}
            });
            dialog.closePromise.then(function(data){
                if(data.value != 'reload'){
                    return;
                }
                $scope.courseTableParams.reload();
            });
        }

        $scope.editCourse = function(course){
            var dialog= ngDialog.open({
                template: 'app/views/custom/admin/edu-setting/course/edit-course.html',
                controller: 'EditCourseController',
                className: 'ngdialog-theme-default custom-width-800',
                data : {type: 'edit', course : course}
            });
            dialog.closePromise.then(function(data){
                if(data.value != 'reload'){
                    return;
                }
                $scope.courseTableParams.reload();
            });
        }

    }
})();