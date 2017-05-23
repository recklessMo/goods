(function () {
    'use strict';
    angular
        .module('custom')
        .controller('InformMessageListController', InformMessageListController);
    InformMessageListController.$inject = ['$scope', 'InformService', 'DicService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify'];

    function InformMessageListController($scope, InformService, DicService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify) {

        $scope.tableParams = {page : 1, count : 10, searchStr: ""};

        $scope.activate = function() {
            $scope.informTableParams = new NgTableParams({}, {
                counts: [10],
                getData: function (params) {
                    blockUI.start();
                    $scope.tableParams.page = params.page();
                    $scope.tableParams.count = params.count();
                    return InformService.listInforms($scope.tableParams).then(function (data) {
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


        $scope.add = function(userId) {
            var dialog= ngDialog.open({
                template: 'app/views/custom/assignment/edit-assignment.html',
                controller: 'EditAssignmentController',
                className: 'ngdialog-theme-default custom-width-800',
                data : {type:'add'}
            });
            dialog.closePromise.then(function(data){
                if(data.value != 'reload'){
                    return;
                }
                $scope.assignmentTableParams.reload();
            });
        }

        $scope.show = function(item) {
            var dialog= ngDialog.open({
                template: 'app/views/custom/assignment/edit-assignment.html',
                controller: 'EditAssignmentController',
                className: 'ngdialog-theme-default custom-width-800',
                data : {assignment:item, type:0}
            });
            dialog.closePromise.then(function(data){
                if(data.value != 'reload'){
                    return;
                }
                $scope.assignmentTableParams.reload();
            });
        }

        //删除考试
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
                    AssignmentService.deleteAssignment(id).success(function () {
                        Notify.alert("删除成功!", {status:"success", timeout: 3000});
                        $scope.assignmentTableParams.reload();
                        blockUI.stop();
                    }).error(function(){
                        blockUI.stop();
                        Notify.alert("网络有问题,请稍后重试!", {status:"error", timeout: 3000});
                    });
                }
            });
        }


    }
})();