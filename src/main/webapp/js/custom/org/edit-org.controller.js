(function () {
    'use strict';
    angular
        .module('custom')
        .controller('EditOrgController', EditOrgController);
    EditOrgController.$inject = ['$scope', 'OrgService','DicService', 'SweetAlert', 'blockUI', 'ngDialog'];

    function EditOrgController($scope, OrgService, DicService,SweetAlert, blockUI, ngDialog) {

        $scope.org = {};
        $scope.typeList = ["小学", "初中", "高中"];

        var block = blockUI.instances.get("edit-org");

        function validate(org){
            var err = [];
            if(!_.isString(org.orgName) || _.isEmpty(org.orgName)
                || !_.isString(org.type) || _.isEmpty(org.type)
                || !_.isString(org.adminName) || _.isEmpty(org.adminName)
                || !_.isString(org.adminPhone) || _.isEmpty(org.adminPhone)
                || !_.isString(org.userName) || _.isEmpty(org.userName)
                || !_.isString(org.pwd) || _.isEmpty(org.pwd)){
                return false;
            }
            return true;
        }


        $scope.save = function(org){
            if(!validate(org)){
                SweetAlert.error("数据填写不正确！");
                return;
            }

            block.start();
            OrgService.addOrg(org).success(function(data){
                if(data.status == 200){
                    $scope.closeThisDialog("reload");
                    Notify.alert("保存成功!", {status:"success", timeout: 3000});
                }
                block.stop();
            }).error(function(){
                SweetAlert.error("网络异常, 请稍后重试!");
                block.stop();
            });

        }

    }
})();