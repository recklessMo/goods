(function () {
    'use strict';
    angular
        .module('custom')
        .controller('StudentScoreListController', StudentScoreListController);
    StudentScoreListController.$inject = ['$scope', 'ngDialog', 'StudentService', 'ScoreService',  'SweetAlert', 'NgTableParams', 'blockUI', 'Notify'];

    function StudentScoreListController($scope, ngDialog, StudentService, ScoreService, SweetAlert, NgTableParams, blockUI, Notify) {

        $scope.isEdit = false;

        $scope.$on('chooseSid', function(event, data){
            $scope.sid = data;
            $scope.activate();
        });

        $scope.scoreList = [];

        $scope.activate = function() {
            blockUI.start();
            StudentService.loadScoreListBySid($scope.sid).success(function(data){
                blockUI.stop();
                if (data.status == 200) {
                    $scope.scoreList = data.data;
                    $scope.showTables();
                }else{
                    SweetAlert.error("服务器内部错误, 请联系客服!");
                }
            }).error(function(){
                SweetAlert.error("网络问题,请稍后重试!");
                blockUI.stop();
            });
        }

        $scope.showTables  = function(){
            $scope.scoreTableParams = new NgTableParams({page: 1, count: 10}, {dataset: $scope.scoreList});
        }


        $scope.showScore = function(item){
            var dialog= ngDialog.open({
                template: 'app/views/custom/student/list/exam/student-score-detail.html',
                controller: 'StudentScoreDetailController',
                className: 'ngdialog-theme-default custom-width-800',
                data : item
            });
            dialog.closePromise.then(function(data){
                return;
            });
        }

    }
})();