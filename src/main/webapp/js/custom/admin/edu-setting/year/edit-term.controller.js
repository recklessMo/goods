(function () {
    'use strict';
    angular
        .module('custom')
        .controller('EditTermController', EditTermController);
    EditTermController.$inject = ['$scope', 'SettingService', 'SweetAlert', 'blockUI'];

    function EditTermController($scope, SettingService, SweetAlert, blockUI) {

        var block = blockUI.instances.get("edit-term");

        $scope.type = $scope.ngDialogData.type;
        $scope.term = _.clone($scope.ngDialogData.term);
        $scope.yearId = $scope.ngDialogData.yearId;

        activate();


        function activate(){
        }

        $scope.loading = false;

        $scope.save = function(term){

            if(!validate(term)){
                return;
            }

            $scope.loading = true;
            block.start();
            if($scope.type == 'add') {
                term.yearId = $scope.yearId;
                SettingService.addTerm(term).success(function (data) {
                    $scope.loading = false;
                    block.stop();
                    if (data.status == 200) {
                        SweetAlert.success("添加成功!");
                        $scope.closeThisDialog('reload');
                    } else {
                        //更新失败的情况
                        SweetAlert.error("服务器异常,请稍后重试");
                    }
                }).error(function () {
                    SweetAlert.error("网络问题,稍后重试!");
                    $scope.loading = false;
                    block.stop();
                });
            }else if($scope.type == 'edit'){
                SettingService.updateTerm(term).success(function (data) {
                    $scope.loading = false;
                    block.stop();
                    if (data.status == 200) {
                        SweetAlert.success("修改成功!");
                        $scope.closeThisDialog('reload');
                    } else {
                        //更新失败的情况
                        SweetAlert.error("服务器异常,请稍后重试");
                    }
                }).error(function () {
                    SweetAlert.error("网络问题,稍后重试!");
                    $scope.loading = false;
                    block.stop();
                });
            }
        }

        function validate(term){
            return true;
        }

    }
})();