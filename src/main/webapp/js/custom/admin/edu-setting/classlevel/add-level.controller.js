(function () {
    'use strict';
    angular
        .module('custom')
        .controller('AddClassLevelController', AddClassLevelController);
    AddClassLevelController.$inject = ['$scope', 'SettingService', 'SweetAlert', 'blockUI'];

    function AddClassLevelController($scope, SettingService, SweetAlert, blockUI) {

        $scope.classLevel = {};

        activate();


        function activate(){
        }

        $scope.loading = false;

        $scope.save = function(classLevel){

            if(!validate(classLevel)){
                return;
            }

            $scope.loading = true;
            blockUI.start();
            SettingService.addClassLevel(classLevel).success(function (data) {
                $scope.loading = false;
                blockUI.stop();
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
        }

        function validate(classLevel){
            if(_.isUndefined(classLevel.levelName) || _.isUndefined(classLevel.levelDetail)){
                SweetAlert.error("请填写必填信息!");
                return false;
            }
            return true;
        }

    }
})();