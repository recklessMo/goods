(function () {
    'use strict';
    angular
        .module('custom')
        .controller('StudentExportBatchController', StudentExportBatchController);
    StudentExportBatchController.$inject = ['$scope', 'StudentService', 'DicService', 'SweetAlert', 'blockUI', 'Notify', 'NgTableParams'];

    function StudentExportBatchController($scope, StudentService, DicService, SweetAlert, blockUI, Notify, NgTableParams) {

        $scope.activate = function() {
            $scope.studentTableParams = new NgTableParams({}, {
                getData: function (params) {
                    blockUI.start();
                    return StudentService.loadStudent({page: params.page(), count: params.count()}).then(function (data) {
                        blockUI.stop();
                        var result = data.data;
                        if (result.status == 200) {
                            params.total(result.totalCount);
                            return result.data;
                        }else{
                            SweetAlert.error("服务器内部错误, 请联系客服!");
                        }
                    }, function () {
                        SweetAlert.error("网络问题,请稍后重试!");
                        blockUI.stop();
                    });
                }
            });
        }

        $scope.activate();

        $scope.export = function(){

        }

    }
})();