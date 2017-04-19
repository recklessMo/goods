(function () {
    'use strict';
    angular
        .module('custom')
        .controller('StudentOpLogListController', StudentOpLogListController);
    StudentOpLogListController.$inject = ['$scope', 'OpLogService', 'SweetAlert', 'NgTableParams', 'blockUI', 'Notify'];

    function StudentOpLogListController($scope, OpLogService, SweetAlert, NgTableParams, blockUI, Notify) {

        $scope.opDataList = [];

        $scope.$on('chooseSid', function (event, data) {
            $scope.sid = data;
            $scope.activate();
        });

        $scope.tableParams = {};

        $scope.activate = function () {
            blockUI.start();
            $scope.opTableParams = new NgTableParams({}, {
                getData: function (params) {
                    $scope.tableParams = {
                        page: params.page(),
                        count: params.count(),
                        sid: $scope.sid
                    };
                    OpLogService.loadOpList($scope.tableParams).success(function (data) {
                        blockUI.stop();
                        if (data.status == 200) {
                            $scope.opDataList = data.data;
                            $scope.showTables();
                        } else {
                            SweetAlert.error("服务器内部错误, 请联系客服!");
                        }
                    }).error(function () {
                        blockUI.stop();
                        SweetAlert.error("网络问题,请稍后重试!");
                    });
                }
            });
        }

    }
})();