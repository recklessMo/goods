(function () {
    'use strict';
    angular
        .module('custom')
        .controller('AssignmentListController', AssignmentListController);
    AssignmentListController.$inject = ['$scope', 'AssignmentService', 'DicService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify'];

    function AssignmentListController($scope, AssignmentService, DicService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify) {

        $scope.tableParams = {page : 1, count : 10, searchStr: ""};

        $scope.activate = function() {
            $scope.assignmentTableParams = new NgTableParams({}, {
                getData: function (params) {
                    blockUI.start();
                    $scope.tableParams.page = paramsgu.page();
                    $scope.tableParams.count = params.count();
                    return AssignmentService.listAssignments($scope.tableParams).then(function (data) {
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


        $scope.add = function(userId) {
            var dialog= ngDialog.open({
                template: 'app/views/custom/performance/exam/edit-exam.html',
                controller: 'EditExamController',
                className: 'ngdialog-theme-default custom-width-800',
            });
            dialog.closePromise.then(function(data){
                if(data.value != 'reload'){
                    return;
                }
                $scope.examTableParams.reload();
            });
        }

    }
})();