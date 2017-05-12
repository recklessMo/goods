(function () {
    'use strict';
    angular
        .module('custom')
        .controller('EditClassController', EditClassController);
    EditClassController.$inject = ['$scope', 'SettingService', 'SweetAlert', 'blockUI', 'Notify'];

    function EditClassController($scope, SettingService, SweetAlert, blockUI, Notify) {

        var block = blockUI.instances.get("edit-class");

        $scope.group = _.clone($scope.ngDialogData.data);
        $scope.type = $scope.ngDialogData.type;
        $scope.gradeId = $scope.ngDialogData.gradeId;

        $scope.nameList = ['1班', '2班', '3班', '4班', '5班', '6班', '7班', '8班', '9班'
            , '10班', '11班', '12班', '13班', '14班', '15班', '16班', '17班', '18班', '19班',
             '20班', '21班', '22班', '23班', '24班', '25班', '26班', '27班', '28班', '29班', '30班'];
        $scope.classTypeList = ['未分科', '文科班', '理科班'];
        $scope.classLevelList = ['重点班', '普通班'];

        activate();


        function activate() {
        }

        $scope.loading = false;

        $scope.save = function (group) {
            if (!validate(group)) {
                return;
            }
            $scope.loading = true;
            block.start();
            if ($scope.type == 'add') {
                group.gradeId = $scope.gradeId;
                SettingService.addClass(group).success(function (data) {
                    $scope.loading = false;
                    block.stop();
                    if (data.status == 200) {
                        Notify.alert("添加成功!", {status:"success", timeout: 3000});
                        $scope.closeThisDialog('reload');
                    } else {
                        //更新失败的情况
                        SweetAlert.error("服务器异常,请稍后重试");
                    }
                }).error(function () {
                    SweetAlert.error("网络问题,稍后重试!");
                    $scope.loading = false;
                    $scope.closeThisDialog('reload');
                    block.stop();
                });
            } else if ($scope.type == 'edit') {
                SettingService.updateClass(group).success(function (data) {
                    $scope.loading = false;
                    block.stop();
                    if (data.status == 200) {
                        Notify.alert("修改成功!", {status:"success", timeout: 3000});
                        $scope.closeThisDialog('reload');
                    } else {
                        //更新失败的情况
                        SweetAlert.error("服务器异常,请稍后重试");
                    }
                }).error(function () {
                    SweetAlert.error("网络问题,稍后重试!");
                    $scope.loading = false;
                    $scope.closeThisDialog('reload');
                    block.stop();
                });
            }
        }

        function validate(group) {
            if(_.isUndefined(group.className) || _.isUndefined(group.charger) || _.isUndefined(group.classLevel) || _.isUndefined(group.classType)){
                SweetAlert.error("请填写必填信息!");
                return false;
            }
            return true;
        }

    }
})();