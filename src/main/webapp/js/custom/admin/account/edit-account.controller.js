(function () {
    'use strict';
    angular
        .module('custom')
        .controller('EditAccountController', EditAccountController);
    EditAccountController.$inject = ['$scope', 'AccountService','DicService', 'SweetAlert', 'blockUI', 'ngDialog'];

    function EditAccountController($scope, AccountService, DicService,SweetAlert, blockUI, ngDialog) {

        var block = blockUI.instances.get("edit-account");
        $scope.userId = $scope.ngDialogData.id;
        $scope.type = $scope.ngDialogData.type;
        $scope.user = {};
        $scope.permissionList = [];
        $scope.jobList = [];
        $scope.genderList = [{name:"男", value:0}, {name:"女", value:1}];

        function init() {
            blockUI.start();
            AccountService.loadPermissions().success(function (data) {
                blockUI.stop();
                if (data.status == 200) {
                    $scope.permissionList = data.data;
                    DicService.loadJobList({page:1, count:1000}).success(function (data) {
                        if (data.status == 200) {
                            $scope.jobList = data.data;
                            activate();
                        }
                    }).error(function () {
                        SweetAlert.error("网络问题, 请稍后重试!");
                    });
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
                AccountService.loadUser($scope.userId).success(function(data){
                    if(data.status == 200) {
                        if (data.data.authorities.length > 0) {
                            var temp = data.data.authorities.split(",");
                            var res = [];
                            for (var i = 0; i < temp.length; i++) {
                                res.push(Number.parseInt(temp[i]));
                            }
                            $scope.tempUser = data.data;
                            $scope.tempUser.authorities = res;
                            $scope.user = $scope.tempUser;
                        } else {
                            $scope.user = data.data;
                        }
                    }
                    block.stop();
                }).error(function(){
                    block.start();
                    $scope.closeThisDialog('reload');
                    SweetAlert.error("网络问题, 请稍后重试!");
                });
            }

        }

        //修改密码
        $scope.changePassword = function (userId){
            var dialog= ngDialog.open({
                template: 'app/views/custom/admin/account/edit-pwd.html',
                controller: 'EditPwdController',
                className: 'ngdialog-theme-default custom-width-800',
                data : {id:userId}
            });
            dialog.closePromise.then(function(data){
            });
        };


        $scope.loading = false;
        $scope.save = function(user){
            var error = validate(user);
            if(error.length > 0){
                SweetAlert.error("请确定红色必填项已经填写!", error);
                return;
            }
            $scope.loading = true;
            block.start();
            var res;
            if(!_.isUndefined(user.authorities) && _.isArray(user.authorities)) {
                res = user.authorities.join(",");
            }
            //update
            if($scope.type == 1){
                AccountService.updateUser(user).success(function(data){
                    $scope.loading = false;
                    block.stop();
                    if(data.status == 200){
                        SweetAlert.success("更新成功!");
                        $scope.closeThisDialog('reload');
                    }else{
                        //更新失败的情况
                        SweetAlert.error("登录名重复!");
                    }
                }).error(function(){
                    SweetAlert.error("网络问题,稍后重试!");
                    $scope.loading = false;
                    $scope.closeThisDialog('reload');
                });
            }else{
                AccountService.addUser(user).success(function(data){
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
        }

        function validate(user){
            var err = [];
            if(!_.isString(user.userName) || _.isEmpty(user.userName)){
                err.push("用户名");
            }
            if(!_.isString(user.pwd) || _.isEmpty(user.pwd)){
                err.push("密码");
            }
            if(!_.isString(user.name) || _.isEmpty(user.name)){
                err.push("姓名");
            }
            if(!_.isString(user.phone) || _.isEmpty(user.phone)){
                err.push("电话号码");
            }
            if(!_.isString(user.job) || _.isEmpty(user.job)){
                err.push("职业");
            }
            if(_.isUndefined(user.gender)){
                err.push("性别");
            }
            return err;
        }

    }
})();