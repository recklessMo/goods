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
                getData: function(params){
                    blockUI.start();
                    return SettingService.listCourse({page:params.page(), count:params.count()}).then(function(data){
                        blockUI.stop();
                        var result = data.data;
                        if(result.status == 200){
                            params.total(result.totalCount);
                            return result.data;
                        }else{
                            SweetAlert.error("服务器内部错误, 请联系客服!");
                        }
                    }, function(){
                        SweetAlert.error("网络异常, 请稍后重试!");
                        blockUI.stop();
                    });
                }
            })
        }

        $scope.activate();

        //年级操作
        $scope.importCourse = function(){
            var dialog= ngDialog.open({
                template: 'app/views/custom/admin/edu-setting/course/import-course.html',
                controller: 'ImportCourseController',
                className: 'ngdialog-theme-default custom-width-800',
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