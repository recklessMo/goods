(function () {
    'use strict';
    angular
        .module('custom')
        .controller('YearSettingController', YearSettingController);
    YearSettingController.$inject = ['$scope', 'SettingService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify', '$resource'];

    function YearSettingController($scope, SettingService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify, $resource) {

        $scope.tableParams = {
            page : 1,
            count: 20
        };

        $scope.activate = function() {
            $scope.yearTableParams = new NgTableParams($scope.tableParams, {
                getData: function($defer, params){
                    blockUI.start();
                    SettingService.listYear(params.parameters()).success(function(data){
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

        //年级操作
        $scope.addYear = function(){
            var dialog= ngDialog.open({
                template: 'app/views/custom/admin/edu-setting/year/edit-year.html',
                controller: 'EditYearController',
                className: 'ngdialog-theme-default custom-width-800',
                data : {type: 'add'}
            });
            dialog.closePromise.then(function(data){
                if(data.value != 'reload'){
                    return;
                }
                $scope.yearTableParams.reload();
            });
        }

        $scope.editYear = function(year){
            var dialog= ngDialog.open({
                template: 'app/views/custom/admin/edu-setting/year/edit-year.html',
                controller: 'EditYearController',
                className: 'ngdialog-theme-default custom-width-800',
                data : {type: 'edit', year : year}
            });
            dialog.closePromise.then(function(data){
                if(data.value != 'reload'){
                    return;
                }
                $scope.yearTableParams.reload();
            });
        }


        //显示学期
        $scope.showTerm = function(yearId){
            var dialog= ngDialog.open({
                template: 'app/views/custom/admin/edu-setting/year/term-setting.html',
                controller: 'TermSettingController',
                className: 'ngdialog-theme-default max-dialog',
                data : {yearId : yearId}
            });
            dialog.closePromise.then(function(data){
                if(data.value != 'reload'){
                    return;
                }
                $scope.yearTableParams.reload();
            });
        }

    }
})();