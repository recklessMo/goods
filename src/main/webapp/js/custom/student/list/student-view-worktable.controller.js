(function () {
    'use strict';
    angular
        .module('custom')
        .controller('StudentViewWorktableController', StudentViewWorktableController);
    StudentViewWorktableController.$inject = ['$scope', 'StudentService', 'DicService', 'SweetAlert', 'NgTableParams', 'blockUI', 'Notify'];

    function StudentViewWorktableController($scope, StudentService,DicService, SweetAlert, NgTableParams, blockUI, Notify) {

        $scope.gradeList = [];
        $scope.classList = [];

        //初始化选择器列表
        function initSelector(){
            blockUI.start();
            DicService.loadAllGrade().success(function(data){
                if(data.status == 200){
                    $scope.gradeList = data.data;
                    //添加全部，因为bootstrap引用出了问题
                    _.forEach($scope.gradeList, function(item){
                        item.classList.unshift({classId: 0, className:'全部'});
                    });
                    $scope.gradeList.unshift({gradeId: 0, gradeName:'全部', classList:[]});
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

        $scope.obj = {};

        //左边侧边栏的查询条件
        $scope.tableParams = {
            page : 1,
            count : 12
        };


        $scope.search = function() {
            $scope.studentTableParams = new NgTableParams({}, {
                counts: [],
                getData: function (params) {
                    blockUI.start();
                    $scope.tableParams.page = params.page();
                    $scope.tableParams.count = params.count();
                    return StudentService.listStudent($scope.tableParams).then(function (data) {
                        var result = data.data;
                        blockUI.stop();
                        if (result.status == 200) {
                            params.total(result.totalCount);
                            $scope.obj.totalCount = result.totalCount;
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


        $scope.getInfo = function(sid){
            //发送消息啦, 然后自组件们开始接受并且处理相关逻辑
            $scope.obj.hasChooseId = true;
            $scope.$broadcast('chooseSid', sid);
        }



    }
})();