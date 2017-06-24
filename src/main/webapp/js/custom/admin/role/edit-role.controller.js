(function () {
    'use strict';
    angular
        .module('custom')
        .controller('EditRoleController', EditRoleController);
    EditRoleController.$inject = ['$scope', 'RoleService', 'SweetAlert', 'blockUI'];

    function EditRoleController($scope, RoleService, SweetAlert, blockUI) {

        var block = blockUI.instances.get("edit-role");

        $scope.roleId = $scope.ngDialogData.id;
        $scope.type = $scope.ngDialogData.type;
        $scope.role = {};
        $scope.permissionList = [];
        $scope.flag = {};

        function init() {
            blockUI.start();
            RoleService.loadPermissions().success(function (data) {
                blockUI.stop();
                if (data.status == 200) {
                    $scope.permissionList = data.data;
                    /*DicService.loadJobList({page:1, count:1000}).success(function (data) {
                       if (data.status == 200) {
                            $scope.jobList = data.data;
                            activate();
                        }
                    }).error(function () {
                        SweetAlert.error("网络问题, 请稍后重试!");
                    });*/
                }
            }).error(function () {
                block.stop();
                SweetAlert.error("网络问题, 请稍后重试!");
            });
        }

        init();


        function activate(){
            if($scope.type != 2) {
                block.start();
                RoleService.loadRole($scope.roleId).success(function(data){
                    if(data.status == 200) {
                        $scope.role = data.data;
                    }
                    block.stop();
                }).error(function(){
                    block.start();
                    $scope.closeThisDialog('reload');
                    SweetAlert.error("网络问题, 请稍后重试!");
                });
            }
        }

       /* $scope.loading = false;

        $scope.save = function(role){

            if(!validate(role)){
                return;
            }

            $scope.loading = true;
            block.start();
            //update
            if($scope.type == 1){
                RoleService.updateRole(role).success(function(data){
                    $scope.loading = false;
                    block.stop();
                    if(data.status == 200){
                        SweetAlert.success("更新成功!");
                        $scope.closeThisDialog('reload');
                    }else{
                        //更新失败的情况
                        SweetAlert.error("角色名重复!");
                    }
                }).error(function(){
                    SweetAlert.error("网络问题,稍后重试!");
                    $scope.loading = false;
                    $scope.closeThisDialog('reload');
                });
            }else{
                RoleService.createRole(role).success(function(data){
                    $scope.loading = false;
                    block.stop();
                    if(data.status == 200){
                        SweetAlert.success("更新成功!");
                        $scope.closeThisDialog('reload');
                    }else{
                        //重复添加的情况
                        SweetAlert.error("登录名重复!");
                    }
                }).error(function(){
                    SweetAlert.error("网络问题,稍后重试!");
                    $scope.loading = false;
                    $scope.closeThisDialog('reload');
                });
            }
        }*/

        $scope.loading = false;
        $scope.save = function(role){
            var error = validate(role);
            if(error.length > 0){
                SweetAlert.error("请确定红色必填项已经填写!", error);
                return;
            }
            $scope.loading = true;
            block.start();
            var res;
            if(!_.isUndefined(role.authorities) && _.isArray(role.authorities)) {
                res = role.authorities.join(",");
            }
            //update
            if($scope.type == 1){
                RoleService.updateRole(role).success(function(data){
                    $scope.loading = false;
                    block.stop();
                    if(data.status == 200){
                        SweetAlert.success("更新成功!");
                        $scope.closeThisDialog('reload');
                    }else{
                        //更新失败的情况
                        SweetAlert.error("角色名重复!");
                    }
                }).error(function(){
                    SweetAlert.error("网络问题,稍后重试!");
                    $scope.loading = false;
                    $scope.closeThisDialog('reload');
                });
            }else{
                RoleService.createRole(role).success(function(data){
                    $scope.loading = false;
                    block.stop();
                    if(data.status == 200){
                        SweetAlert.success("更新成功!");
                        $scope.closeThisDialog('reload');
                    }else{
                        //重复添加的情况
                        SweetAlert.error("角色名重复!");
                    }
                }).error(function(){
                    SweetAlert.error("网络问题,稍后重试!");
                    $scope.loading = false;
                    $scope.closeThisDialog('reload');
                });
            }
        }

        function validate(role){
            return true;
        }
      /* function validate(role){
            var err = [];
            if(!_.isString(role.name) || _.isEmpty(role.name)){
                err.push("角色名");
            }
            if(!_.isString(role.detail) || _.isEmpty(role.detail)){
                err.push("角色描述");
            }
            return err;
        }*/

    }
})();