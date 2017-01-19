(function () {
    'use strict';
    angular
        .module('custom')
        .controller('StudentGraduateController', StudentGraduateController);
    StudentGraduateController.$inject = ['$scope', 'StudentService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify'];

    function StudentGraduateController($scope, StudentService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify) {

        $scope.tableParams = {page : 1, count : 10, searchStr: null};

        $scope.activate = function() {
            $scope.graduateTableParams = new NgTableParams({}, {
                getData: function (params) {
                    blockUI.start();
                    return StudentService.loadGraduates({page: params.page(), count: params.count()}).then(function (data) {
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
                    StudentService.deleteGraduate(id).success(function () {
                        Notify.alert("删除成功!", {status:"success", timeout: 3000});
                        $scope.graduateTableParams.reload();
                        blockUI.stop();
                    }).error(function(){
                        blockUI.stop();
                        Notify.alert("网络有问题,请稍后重试!", {status:"error", timeout: 3000});
                    });
                }
            });
        }

        //查看/编辑 具体的毕业信息
        $scope.show = function (userId){
            var dialog= ngDialog.open({
                template: 'app/views/custom/student/other/edit-graduate.html',
                controller: 'EditGraduateController',
                className: 'ngdialog-theme-default custom-width-800',
                data : {id:userId, type:0}
            });
            dialog.closePromise.then(function(data){
                if(data.value != 'reload'){
                    return;
                }
                $scope.graduateTableParams.reload();
            });
        };



    }
})();