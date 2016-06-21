(function () {
    'use strict';
    angular
        .module('custom')
        .controller('EditAccountController', EditAccountController);
    EditAccountController.$inject = ['$scope', 'AccountService', 'SweetAlert'];

    function EditAccountController($scope, AccountService, SweetAlert) {

        $scope.userId = $scope.ngDialogData.id;
        $scope.type = $scope.ngDialogData.type;

        $scope.user = {};

        activate();

        function activate(){
            if($scope.type != 2) {
                AccountService.loadUser($scope.userId).success(function(data){
                    if(data.status == 200) {
                        $scope.user = data.data;
                    }
                }).error(function(){
                    SweetAlert.error("网络问题, 请稍后重试!");
                });
            }
        }

        $scope.loading = false;

        $scope.save = function(user){

            if(!validate()){
                return;
            }

            $scope.loading = true;
            //update
            if($scope.type == 1){
                AccountService.updateUser(user).success(function(data){
                    if(data.status == 200){
                        SweetAlert.success("更新成功!");
                        $scope.closeThisDialog('reload');
                        $scope.loading = false;
                    }
                }).error(function(){
                    SweetAlert.error("网络问题,稍后重试!");
                    $scope.loading = false;
                    $scope.closeThisDialog('reload');
                });
            }else{
                AccountService.addUser(user).success(function(data){
                    if(data.status == 200){
                        SweetAlert.success("更新成功!");
                        $scope.loading = false;
                        $scope.closeThisDialog('reload');
                    }
                }).error(function(){
                    SweetAlert.error("网络问题,稍后重试!");
                    $scope.loading = false;
                    $scope.closeThisDialog('reload');
                });
            }
        }

        function validate(user){
            return true;
        }

    }
})();