(function () {
    'use strict';
    angular
        .module('custom')
        .controller('TemplateListController', TemplateListController);
    TemplateListController.$inject = ['$scope', 'TemplateService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify'];

    function TemplateListController($scope, TemplateService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify) {

        $scope.tableParams = {page : 1, count : 10, searchStr: null};

        $scope.activate = function() {
            $scope.templateTableParams = new NgTableParams($scope.tableParams, {
                getData: function ($defer, params) {
                    blockUI.start();
                    TemplateService.loadTemplates(params.parameters()).success(function (data) {
                        if (data.status == 200) {
                            params.total(data.totalCount);
                            console.log(data);
                            $defer.resolve(data.data);
                            blockUI.stop();
                        }
                    }).error(function () {
                        SweetAlert.error("网络问题,请稍后重试!");
                        blockUI.stop();
                    });
                }
            });
        }

        $scope.activate();

        //删除用户
        $scope.delete = function(id){
            SweetAlert.swal({
                title: '确认删除?',
                type: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#DD6B55',
                confirmButtonText: '是',
                cancelButtonText: '否',
                closeOnConfirm: true,
                closeOnCancel: true
            }, function(isConfirm){
                if (isConfirm) {
                    //这里可以进行调试,查看$scope,因为table会创建一个子scope
                    //然后子scope里面就不能用this了,因为this就指向了子scope,
                    //实际上在table的每一行里面的点击是调用了父scope的delete方法
                    blockUI.start();
                    TemplateService.deleteTemplate(id).success(function () {
                        Notify.alert("删除成功!", {status:"success", timeout: 3000});
                        $scope.templateTableParams.reload();
                        blockUI.stop();
                    }).error(function(){
                        blockUI.stop();
                        SweetAlert.error("网络问题,请稍后重试!");
                    });
                }
            });
        }

        //增加模板
        $scope.add = function() {
            var dialog= ngDialog.open({
                template: 'app/views/custom/performance/template/edit/template-edit.html',
                controller: 'TemplateEditController',
                className: 'ngdialog-theme-default max-dialog',
                data: {type: 'add'}
            });
            dialog.closePromise.then(function(data){
                if(data.value != 'reload'){
                    return;
                }
                $scope.templateTableParams.reload();
            });
        }

        //编辑模板
        $scope.edit = function(item) {
            var dialog= ngDialog.open({
                template: 'app/views/custom/performance/template/edit/template-edit.html',
                controller: 'TemplateEditController',
                className: 'ngdialog-theme-default max-dialog',
                data : {type: 'edit', data: item}
            });
            dialog.closePromise.then(function(data){
                if(data.value != 'reload'){
                    return;
                }
                $scope.templateTableParams.reload();
            });
        }


    }
})();