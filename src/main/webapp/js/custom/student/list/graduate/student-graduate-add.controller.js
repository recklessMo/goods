(function () {
    'use strict';
    angular
        .module('custom')
        .controller('StudentGraduateAddController', StudentGraduateAddController);
    StudentGraduateAddController.$inject = ['$scope', 'ngDialog', 'GraduateService', 'SweetAlert', 'NgTableParams', 'blockUI', 'Notify'];

    function StudentGraduateAddController($scope, ngDialog, GraduateService, SweetAlert, NgTableParams, blockUI, Notify) {

        $scope.graduate = $scope.ngDialogData.data;
        $scope.type = $scope.ngDialogData.type;

        $scope.save = function (item) {
            if (!$scope.valid(item)) {
                SweetAlert.error("数据填写不正确!");
                return;
            }

            blockUI.start();
            if ($scope.type == 2) {
                GraduateService.addGraduate(item).success(function (data) {
                    blockUI.stop();
                    if (data.status == 200) {
                        SweetAlert.success("添加成功!");
                    } else {
                        SweetAlert.error("服务器内部错误, 请联系客服!");
                    }
                }).error(function () {
                    blockUI.stop();
                    SweetAlert.error("网络问题,请稍后重试!");
                });
            } else if ($scope.type == 1) {
                GraduateService.updateGraduate(item).success(function (data) {
                    blockUI.stop();
                    if (data.status == 200) {
                        SweetAlert.success("添加成功!");
                    } else {
                        SweetAlert.error("服务器内部错误, 请联系客服!");
                    }
                }).error(function () {
                    blockUI.stop();
                    SweetAlert.error("网络问题,请稍后重试!");
                });
            }

        }

        $scope.valid = function (data) {
            return true;
        }

    }
})();