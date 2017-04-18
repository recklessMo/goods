(function () {
    'use strict';
    angular
        .module('custom')
        .controller('StudentGraduateListController', StudentGraduateListController);
    StudentGraduateListController.$inject = ['$scope','GraduateService', 'SweetAlert', 'NgTableParams', 'blockUI', 'Notify'];

    function StudentGraduateListController($scope, GraduateService, SweetAlert, NgTableParams, blockUI, Notify) {

        $scope.graduateDataList = [];

        $scope.$on('chooseSid', function(event, data){
            $scope.sid = data;
            $scope.activate();
        });

        $scope.activate = function() {
            blockUI.start();
            GraduateService.getGraduateList({sid : $scope.sid}).success(function(data){
                blockUI.stop();
                if (data.status == 200) {
                    $scope.graduateDataList = data.data;
                    $scope.showTables();
                }else{
                    SweetAlert.error("服务器内部错误, 请联系客服!");
                }
            }).error(function(){
                blockUI.stop();
                SweetAlert.error("网络问题,请稍后重试!");
            });
        }

        $scope.showTables = function(){
            $scope.graduateTableParams = new NgTableParams({},{
                counts: [],
                dataset: $scope.graduateDataList
            });
        }

    }
})();