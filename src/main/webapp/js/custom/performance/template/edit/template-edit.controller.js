(function() {
    'use strict';

    angular
        .module('custom')
        .controller('TemplateEditController', TemplateEditController);

    TemplateEditController.$inject = ['$scope', 'TemplateService', '$timeout', '$resource', 'blockUI', 'SweetAlert'];
    function TemplateEditController($scope,TemplateService, $timeout, $resource, blockUI, SweetAlert) {
        var vm = this;

        activate();

        vm.label = -1;

        $scope.type = $scope.ngDialogData.type;
        //分数模板
        $scope.scoreTemplate = ($scope.type == 'edit' ? $scope.ngDialogData.data : {});


        function activate() {
            vm.my_tree_handler = function(branch) {
                vm.label = branch.label;
            };

            var settings = [
                {
                    label: '分数线设置',
                    children: [
                        {
                            label: '语文'
                        },{
                            label: '数学'
                        },{
                            label: '英语'
                        },{
                            label: '政治'
                        },{
                            label: '历史'
                        },{
                            label: '地理'
                        },{
                            label: '物理'
                        },{
                            label: '化学'
                        },{
                            label: '生物'
                        }
                    ]
                }
            ];
            vm.my_data = settings;
            vm.my_tree = {};
        }


        $scope.save = function(){
            blockUI.start();
            TemplateService.addTemplate($scope.scoreTemplate).success(function(data){
                if(data.status == 200){
                    SweetAlert.success("成功!");
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

