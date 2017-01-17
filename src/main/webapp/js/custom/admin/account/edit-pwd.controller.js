(function () {
    'use strict';
    angular
        .module('custom')
        .controller('EditPwdController', EditPwdController);
    EditPwdController.$inject = ['$scope', 'AccountService','DicService', 'SweetAlert', 'blockUI', 'ngDialog'];

    function EditPwdController($scope, AccountService, DicService, SweetAlert, blockUI, ngDialog) {
        $scope.userId = $scope.ngDialogData.id;
        $scope.temp = {};

        $scope.updatePwd = function() {
            if (!_.isString($scope.temp.pwd) || _.isEmpty($scope.temp.pwd)) {
                SweetAlert("未填写密码");
                return;
            }

            blockUI.start();
            AccountService.updatePwd($scope.userId, $scope.temp.pwd).success(function (data) {
                blockUI.stop();
                if (data.status == 200) {
                    SweetAlert.success("修改成功");
                    $scope.closeThisDialog("ok");
                }
            }).error(function () {
                blockUI.stop();
                SweetAlert.error("网络问题, 请稍后重试!");
            });
        }


    }
})();