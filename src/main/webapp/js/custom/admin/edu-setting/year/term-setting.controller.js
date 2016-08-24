(function () {
    'use strict';
    angular
        .module('custom')
        .controller('TermSettingController', TermSettingController);
    TermSettingController.$inject = ['$scope', 'SettingService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify', '$resource'];

    function TermSettingController($scope, SettingService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify, $resource) {

        $scope.tableParams = {
            page : 1,
            count: 20
        };

        $scope.tableParams.yearId = $scope.ngDialogData.yearId;

        $scope.activate = function() {
            $scope.termTableParams = new NgTableParams($scope.tableParams, {
                getData: function($defer, params){
                    blockUI.start();
                    SettingService.listTerm(params.parameters()).success(function(data){
                        if(data.status == 200){
                            $defer.resolve(data.data);
                            params.total(data.totalCount);
                        }
                        blockUI.stop();
                    }).error(function(){
                        SweetAlert.error("网络异常, 请稍后重试!");
                        blockUI.stop();
                    });
                }
            })
        }

        $scope.activate();

        //学期操作
        $scope.addTerm = function(yearId){
            var dialog= ngDialog.open({
                template: 'app/views/custom/admin/edu-setting/year/edit-term.html',
                controller: 'EditTermController',
                className: 'ngdialog-theme-default custom-width-800',
                data : {type: 'add', yearId: yearId}
            });
            dialog.closePromise.then(function(data){
                if(data.value != 'reload'){
                    return;
                }
                $scope.termTableParams.reload();
            });
        }

        $scope.editTerm = function(term){
            var dialog= ngDialog.open({
                template: 'app/views/custom/admin/edu-setting/year/edit-term.html',
                controller: 'EditTermController',
                className: 'ngdialog-theme-default custom-width-800',
                data : {type: 'edit', term : term}
            });
            dialog.closePromise.then(function(data){
                if(data.value != 'reload'){
                    return;
                }
                $scope.termTableParams.reload();
            });
        }

    }
})();