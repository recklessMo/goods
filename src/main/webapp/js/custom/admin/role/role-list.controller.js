(function () {
    'use strict';
    angular
        .module('custom')
        .controller('RoleListController', RoleListController);
    RoleListController.$inject = ['$scope', 'RoleService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify'];

    function RoleListController($scope, RoleService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify) {

        $scope.tableParams = {page : 1, count : 10, searchStr: null};

        $scope.activate = function() {
            $scope.roleTableParams = new NgTableParams($scope.tableParams, {
                getData: function ($defer, params) {
                    blockUI.start();
                    RoleService.listRoles(params.parameters()).success(function (data) {
                        if (data.status == 200) {
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

        //删除角色
        $scope.delete = function(id) {
            SweetAlert.swal({
                title: '确认删除?',
                type: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#DD6B55',
                confirmButtonText: '是',
                cancelButtonText: '否',
                closeOnConfirm: true,
                closeOnCancel: true
            }, function (isConfirm) {
                if (isConfirm) {
                    //这里可以进行调试,查看$scope,因为table会创建一个子scope
                    //然后子scope里面就不能用this了,因为this就指向了子scope,
                    //实际上在table的每一行里面的点击是调用了父scope的delete方法
                    blockUI.start();
                    RoleService.deleteRole(id).success(function () {
                        Notify.alert("删除成功!", {status: "success", timeout: 3000});
                        $scope.roleTableParams.reload();
                        blockUI.stop();
                    }).error(function () {
                        blockUI.stop();
                        SweetAlert.error("网络问题, 请稍后重试!");
                    });
                }
            });
        }


        //编辑角色
        $scope.edit = function (roleId) {
            var dialog = ngDialog.open({
                template: 'app/views/custom/admin/role/edit-role.html',
                controller: 'EditRoleController',
                className: 'ngdialog-theme-default custom-width-800',
                data: {id: roleId, type: 1}
            });
            dialog.closePromise.then(function (data) {
                if (data.value != 'reload') {
                    return;
                }
                $scope.roleTableParams.reload();
            });
        }

        //添加角色
        $scope.add = function (roleId) {
            var dialog = ngDialog.open({
                template: 'app/views/custom/admin/role/edit-role.html',
                controller: 'EditRoleController',
                className: 'ngdialog-theme-default custom-width-800',
                data: {id: roleId, type: 2}
            })
            dialog.closePromise.then(function (data) {
                if (data.value != 'reload') {
                    return;
                }
                $scope.roleTableParams.reload();
            });
        }
    }
})();