(function () {
    'use strict';
    angular
        .module('custom')
        .controller('EditAccountController', EditAccountController);
    EditAccountController.$inject = ['$scope', 'AccountService', 'SweetAlert', 'blockUI'];

    function EditAccountController($scope, AccountService, SweetAlert, blockUI) {

        var block = blockUI.instances.get("edit-account");

        $scope.userId = $scope.ngDialogData.id;
        $scope.type = $scope.ngDialogData.type;

        $scope.user = {};

        activate();


        function activate(){
            if($scope.type != 2) {
                block.start();
                AccountService.loadUser($scope.userId).success(function(data){
                    if(data.status == 200) {
                        $scope.user = data.data;
                    }
                    block.stop();
                }).error(function(){
                    block.start();
                    $scope.closeThisDialog('reload');
                    SweetAlert.error("网络问题, 请稍后重试!");
                });
            }
        }

        $scope.loading = false;

        $scope.save = function(user){

            if(!validate(user)){
                return;
            }

            $scope.loading = true;
            block.start();
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
            if(!user.userName || !user.pwd || !user.name){
                return false;
            }
            return true;
        }

    }
})();