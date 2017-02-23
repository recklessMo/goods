(function () {
    'use strict';
    angular
        .module('custom')
        .controller('OrgListController', OrgListController);
    OrgListController.$inject = ['$scope', 'OrgService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify'];

    function OrgListController($scope, OrgService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify) {

        $scope.tableParams = {page : 1, count : 10, searchStr: null};

        $scope.activate = function() {
            $scope.orgTableParams = new NgTableParams({}, {
                getData: function (params) {
                    blockUI.start();
                    return OrgService.loadOrgs({page: params.page(), count: params.count()}).then(function (data) {
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


        //添加新机构
        $scope.add = function(userId){
            var dialog= ngDialog.open({
                template: 'app/views/custom/system/add-org.html',
                controller: 'AddOrgController',
                className: 'ngdialog-theme-default custom-width-800'
            })
            dialog.closePromise.then(function(data){
                if(data.value != 'reload'){
                    return;
                }
                $scope.orgTableParams.reload();
            });
        }

    }
})();