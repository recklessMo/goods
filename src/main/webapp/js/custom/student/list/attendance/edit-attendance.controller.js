(function () {
    'use strict';
    angular
        .module('custom')
        .controller('EditAttendanceController', EditAttendanceController);
    EditAttendanceController.$inject = ['$scope', 'ngDialog', 'AttendanceService', 'SweetAlert', 'NgTableParams', 'blockUI', 'Notify'];

    function EditAttendanceController($scope, ngDialog, AttendanceService, SweetAlert, NgTableParams, blockUI, Notify) {

        $scope.attendance = _.clone($scope.ngDialogData.data);
        $scope.type = $scope.ngDialogData.type;

        $scope.save = function (item) {
            if (!$scope.valid(item)) {
                SweetAlert.error("数据填写不正确!");
                return;
            }

            blockUI.start();
            if ($scope.type == 2) {
                AttendanceService.addAttendance(item).success(function (data) {
                    blockUI.stop();
                    if (data.status == 200) {
                        SweetAlert.success("添加成功!");
                        $scope.closeThisDialog('reload');
                    } else {
                        SweetAlert.error("服务器内部错误, 请联系客服!");
                    }
                }).error(function () {
                    blockUI.stop();
                    SweetAlert.error("网络问题,请稍后重试!");
                });
            } else if ($scope.type == 1) {
                AttendanceService.updateAttendance(item).success(function (data) {
                    blockUI.stop();
                    if (data.status == 200) {
                        SweetAlert.success("修改成功!");
                        $scope.closeThisDialog('reload');
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