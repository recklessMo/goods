(function () {
    'use strict';
    angular
        .module('custom')
        .controller('AccountController', AccountController);
    AccountController.$inject = ['$scope', 'AccountService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify'];

    function AccountController($scope, AccountService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify) {

        $scope.tableParams = {searchStr: null};

        $scope.activate = function() {
            $scope.userTableParams = new NgTableParams({}, {
                getData: function (params) {
                    blockUI.start();
                    return AccountService.loadUsers({page: params.page(), count: params.count(), searchStr: $scope.tableParams.searchStr}).then(function (data) {
                        blockUI.stop();
                        var result = data.data;
                        if (result.status == 200) {
                            params.total(result.totalCount);
                            return result.data;
                        }else{
                            SweetAlert.error("服务器内部错误, 请联系客服!");
                        }
                    }, function(){
                        blockUI.stop();
                        SweetAlert.error("网络问题, 请稍后重试!");
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
                        SweetAlert.error("网络问题, 请稍后重试!");
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