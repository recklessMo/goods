(function() {
    'use strict';

    angular
        .module('custom')
        .controller('TemplateRankEditController', TemplateRankEditController);

    TemplateRankEditController.$inject = ['$scope', 'TemplateService', '$timeout', '$resource', 'blockUI', 'SweetAlert', 'Notify'];
    function TemplateRankEditController($scope,TemplateService, $timeout, $resource, blockUI, SweetAlert, Notify) {
        //type
        $scope.type = $scope.ngDialogData.type;
        //分数模板
        $scope.scoreTemplate = ($scope.type == 'edit' ? $scope.ngDialogData.data : {});
        $scope.courseList = ["总分", "语文", "数学", "英语", "政治", "历史", "地理", "物理", "化学", "生物"];

        $scope.save = function(){
            blockUI.start();
            $scope.scoreTemplate.type = 3;
            TemplateService.addTemplate($scope.scoreTemplate).success(function(data){
                if(data.status == 200){
                    Notify.alert("保存成功!", {status: "success", timeout: 3000});
                    $scope.closeThisDialog('reload');
                    blockUI.stop();
                }
            }).error(function(){
                SweetAlert.error("网络问题,请稍后重试!");
                blockUI.stop();
            });
        }
    }
})();

