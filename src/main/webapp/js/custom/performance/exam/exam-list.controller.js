(function () {
    'use strict';
    angular
        .module('custom')
        .controller('ExamListController', ExamListController);
    ExamListController.$inject = ['$scope', 'ExamService', 'DicService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify'];

    function ExamListController($scope, ExamService, DicService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify) {

        $scope.examTypeList = ['全部', '小测', '周考', '月考', '期中', '期末'];

        $scope.gradeList = [];
        $scope.classList = [];

        //初始化选择器列表
        function initSelector(){
            blockUI.start();
            DicService.loadAllGrade().success(function(data){
                if(data.status == 200){
                    $scope.gradeList = data.data;
                    _.forEach($scope.gradeList, function(item){
                        item.classList.unshift({classId: 0, className:'全部'});
                    });
                }
                blockUI.stop();
            }).error(function(){
                SweetAlert.error("网络异常, 请稍后重试!");
                blockUI.stop();
            });

            $scope.selectGrade = function(data){
                $scope.classList = data.classList;
                $scope.tableParams.classId = 0;
            }
        }

        initSelector();


        $scope.tableParams = {page : 1, count : 10, gradeId : 0 , classId: 0 , examType: "", searchStr: ""};

        $scope.activate = function() {
            $scope.examTableParams = new NgTableParams({}, {
                getData: function (params) {
                    blockUI.start();
                    $scope.tableParams.page = params.page();
                    $scope.tableParams.count = params.count();
                    return ExamService.loadExams($scope.tableParams).then(function (data) {
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
                    AccountService.deleteUser(id).success(function () {
                        Notify.alert("删除成功!", {status:"success", timeout: 3000});
                        $scope.userTableParams.reload();
                        blockUI.stop();
                    }).error(function(){
                        blockUI.stop();
                        Notify.alert("网络有问题,请稍后重试!", {status:"error", timeout: 3000});
                    });
                }
            });
        }

        $scope.add = function(userId) {
            var dialog= ngDialog.open({
                template: 'app/views/custom/performance/exam/edit-exam.html',
                controller: 'EditExamController',
                className: 'ngdialog-theme-default custom-width-800',
            });
            dialog.closePromise.then(function(data){
                if(data.value != 'reload'){
                    return;
                }
                $scope.examTableParams.reload();
            });
        }

        $scope.uploadScore = function(exam){
            var dialog= ngDialog.open({
                template: 'app/views/custom/performance/exam/edit-exam-score.html',
                controller: 'EditExamScoreController',
                className: 'ngdialog-theme-default custom-width-800',
                data : exam
            });
            dialog.closePromise.then(function(data){
                if(data.value != 'reload'){
                    return;
                }
                $scope.examTableParams.reload();
            });
        }



    }
})();