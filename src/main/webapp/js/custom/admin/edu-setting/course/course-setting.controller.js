(function () {
    'use strict';
    angular
        .module('custom')
        .controller('CourseSettingController', CourseSettingController);
    CourseSettingController.$inject = ['$scope', 'SettingService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify', '$resource'];

    function CourseSettingController($scope, SettingService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify, $resource) {

        $scope.tableParams = {
            page : 1,
            count: 200
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

        $scope.deleteCourse = function(id){
            SweetAlert.swal({
                title: '确认删除?',
                type: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#DD6B55',
                confirmButtonText: '是',
                cancelButtonText: '否',
                closeOnConfirm: true,
                closeOnCancel: true
            }, function(isConfirm){
                if (isConfirm) {
                    blockUI.start();
                    SettingService.deleteCourse(id).success(function () {
                        Notify.alert("删除成功!", {status:"success", timeout: 3000});
                        $scope.courseTableParams.reload();
                        blockUI.stop();
                    }).error(function(){
                        blockUI.stop();
                        SweetAlert.error("网络问题, 请稍后重试!");
                    });
                }
            });
        }



    }
})();