(function () {
    'use strict';
    angular
        .module('custom')
        .controller('ExamController', ExamController);
    ExamController.$inject = ['$scope', 'ExamService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify'];

    function ExamController($scope, ExamService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify) {

        $scope.tableParams = {page : 1, count : 10, searchStr: null};

        $scope.activate = function() {
            $scope.examTableParams = new NgTableParams($scope.tableParams, {
                getData: function ($defer, params) {
                    blockUI.start();
                    ExamService.loadExams(params.parameters()).success(function (data) {
                        if (data.status == 200) {
                            params.total(data.totalCount);
                            console.log(data);
                            $defer.resolve(data.data);
                            blockUI.stop();
                        }
                    }).error(function () {
                        SweetAlert.error("网络问题,请稍后重试!");
                        blockUI.stop();
                    });
                }
            });
        }

        $scope.activate();

        //删除用户
        $scope.delete = function(id){
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
                    AccountService.deleteUser(id).success(function () {
                        Notify.alert("删除成功!", {status:"success", timeout: 3000});
                        $scope.userTableParams.reload();
                        blockUI.stop();
                    }).error(function(){
                        blockUI.stop();
                        Notify.alert("网络有问题,请稍后重试!", {status:"error", timeout: 3000});
                    });
                }
            });
        }

        //查看用户
        $scope.show = function (userId){
            var dialog= ngDialog.open({
                template: 'app/views/custom/admin/account/edit-account.html',
                controller: 'EditAccountController',
                className: 'ngdialog-theme-default custom-width-800',
                data : {id:userId, type:0}
            });
            dialog.closePromise.then(function(data){
                if(data.value != 'reload'){
                    return;
                }
                $scope.userTableParams.reload();
            });
        };

        //编辑用户
        $scope.edit = function(userId) {
            var dialog= ngDialog.open({
                template: 'app/views/custom/admin/account/edit-account.html',
                controller: 'EditAccountController',
                className: 'ngdialog-theme-default custom-width-800',
                data : {id:userId, type:1}
            });
            dialog.closePromise.then(function(data){
                if(data.value != 'reload'){
                    return;
                }
                $scope.userTableParams.reload();
            });
        }

        //添加用户
        $scope.add = function(userId){
            var dialog= ngDialog.open({
                template: 'app/views/custom/admin/account/edit-account.html',
                controller: 'EditAccountController',
                className: 'ngdialog-theme-default custom-width-800',
                data : {id:userId, type:2}
            })
            dialog.closePromise.then(function(data){
                if(data.value != 'reload'){
                    return;
                }
                $scope.userTableParams.reload();
            });
        }



    }
})();