(function () {
    'use strict';
    angular
        .module('custom')
        .controller('StudentBaseInfoController', StudentBaseInfoController);
    StudentBaseInfoController.$inject = ['$scope','StudentService', 'SweetAlert', 'NgTableParams', 'blockUI', 'Notify'];

    function StudentBaseInfoController($scope, StudentService, SweetAlert, NgTableParams, blockUI, Notify) {

        $scope.isEdit = false;
        $scope.offlineInfo = {};

        $scope.$on('chooseSid', function(event, data){
            $scope.sid = data;
            $scope.activate();
        });

        $scope.activate = function() {
            blockUI.start();
            StudentService.getSingleStudentInfo($scope.sid).success(function(data){
                blockUI.stop();
                if(data.status == 200){
                    $scope.offlineInfo = data.data;
                }else{
                    SweetAlert.error("服务器内部错误, 请联系客服!");
                }
            }).error(function(){
                SweetAlert.error("网络问题,请稍后重试!");
                blockUI.stop();
            });
        }

        $scope.activate();

        $scope.changeStatus = function (){
            $scope.isEdit = !$scope.isEdit;
        }



    }
})();