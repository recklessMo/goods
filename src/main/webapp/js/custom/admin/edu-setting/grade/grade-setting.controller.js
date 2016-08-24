(function () {
    'use strict';
    angular
        .module('custom')
        .controller('GradeSettingController', GradeSettingController);
    GradeSettingController.$inject = ['$scope', 'SettingService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify', '$resource'];

    function GradeSettingController($scope, SettingService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify, $resource) {

        $scope.tableParams = {
            page : 1,
            count: 20
        };

        $scope.activate = function() {
            $scope.gradeTableParams = new NgTableParams($scope.tableParams, {
                getData: function($defer, params){
                    blockUI.start();
                    SettingService.listGrade(params.parameters()).success(function(data){
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
        $scope.addGrade = function(){
            var dialog= ngDialog.open({
                template: 'app/views/custom/admin/edu-setting/grade/edit-grade.html',
                controller: 'EditGradeController',
                className: 'ngdialog-theme-default custom-width-800',
                data : {type: 'add'}
            });
            dialog.closePromise.then(function(data){
                if(data.value != 'reload'){
                    return;
                }
                $scope.gradeTableParams.reload();
            });
        }

        $scope.editGrade = function(grade){
            var dialog= ngDialog.open({
                template: 'app/views/custom/admin/edu-setting/grade/edit-grade.html',
                controller: 'EditGradeController',
                className: 'ngdialog-theme-default custom-width-800',
                data : {type: 'edit', grade : grade}
            });
            dialog.closePromise.then(function(data){
                if(data.value != 'reload'){
                    return;
                }
                $scope.gradeTableParams.reload();
            });
        }


        //显示班级
        $scope.showClass = function(gradeId){
            var dialog= ngDialog.open({
                template: 'app/views/custom/admin/edu-setting/grade/class-setting.html',
                controller: 'ClassSettingController',
                className: 'ngdialog-theme-default max-dialog',
                data : {gradeId : gradeId}
            });
            dialog.closePromise.then(function(data){
                if(data.value != 'reload'){
                    return;
                }
                $scope.gradeTableParams.reload();
            });
        }

        $scope.deleteGrade = function(id){
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
                    //这里可以进行调试,查看$scope,因为table会创建一个子scope
                    //然后子scope里面就不能用this了,因为this就指向了子scope,
                    //实际上在table的每一行里面的点击是调用了父scope的delete方法
                    blockUI.start();
                    SettingService.deleteGrade(id).success(function () {
                        Notify.alert("删除成功!", {status:"success", timeout: 3000});
                        $scope.gradeTableParams.reload();
                        blockUI.stop();
                    }).error(function(){
                        blockUI.stop();
                        Notify.alert("网络有问题,请稍后重试!", {status:"error", timeout: 3000});
                    });
                }
            });
        }

    }
})();