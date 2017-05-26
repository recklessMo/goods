(function () {
    'use strict';
    angular
        .module('custom')
        .controller('EditOrgController', EditOrgController);
    EditOrgController.$inject = ['$scope', 'OrgService','DicService', 'SweetAlert', 'blockUI', 'ngDialog'];

    function EditOrgController($scope, OrgService, DicService,SweetAlert, blockUI, ngDialog) {

        var block = blockUI.instances.get("edit-account");

        function validate(user){
            var err = [];
            if(!_.isString(user.userName) || _.isEmpty(user.userName)){
                err.push("用户名");
            }
            if(!_.isString(user.pwd) || _.isEmpty(user.pwd)){
                err.push("密码");
            }
            if(!_.isString(user.name) || _.isEmpty(user.name)){
                err.push("姓名");
            }
            if(!_.isString(user.phone) || _.isEmpty(user.phone)){
                err.push("电话号码");
            }
            if(!_.isString(user.job) || _.isEmpty(user.job)){
                err.push("职业");
            }
            if(_.isUndefined(user.gender)){
                err.push("性别");
            }
            return err;
        }

    }
})();