(function () {
    'use strict';
    angular
        .module('custom')
        .controller('WechatTalkController', WechatTalkController);
    WechatTalkController.$inject = ['$scope', 'WechatService', 'StudentService','DicService', 'SweetAlert', 'NgTableParams', 'blockUI', 'Notify'];

    function WechatTalkController($scope, WechatService, StudentService, DicService, SweetAlert, NgTableParams, blockUI, Notify) {

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


        $scope.tableParams = {};
        $scope.obj = {};
        $scope.searchStudent = function () {
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
                        } else {
                            SweetAlert.error("服务器内部错误, 请联系客服!");
                        }
                    }, function () {
                        SweetAlert.error("网络问题,请稍后重试!");
                        blockUI.stop();
                    });
                }
            });
        }

        $scope.searchStudent();

        var tempDate = new Date();

        //切换tab
        $scope.changeTab = function(type){
            if(type == 'recentUser'){
                $scope.wechatRecentUserTableParams = new NgTableParams({}, {
                    counts: [],
                    getData: function (params) {
                        blockUI.start();
                        return WechatService.loadWechatRecentUser({page: params.page(), count: 10}).then(function (data) {
                            blockUI.stop();
                            var result = data.data;
                            if (result.status == 200) {
                                params.total(result.totalCount);
                                return result.data;
                            }else{
                                SweetAlert.error("服务器内部错误, 请联系客服!");
                            }
                        }, function(){
                            blockUI.stop();
                            SweetAlert.error("网络问题, 请稍后重试!");
                        });
                    }
                });
            }else if(type == 'allUser'){
                $scope.wechatAllUserTableParams = new NgTableParams({}, {
                    counts: [],
                    getData: function (params) {
                        blockUI.start();
                        return WechatService.loadWechatAllUser({page: params.page(), count: 10}).then(function (data) {
                            blockUI.stop();
                            var result = data.data;
                            if (result.status == 200) {
                                params.total(result.totalCount);
                                return result.data;
                            }else{
                                SweetAlert.error("服务器内部错误, 请联系客服!");
                            }
                        }, function(){
                            blockUI.stop();
                            SweetAlert.error("网络问题, 请稍后重试!");
                        });
                    }
                });
            }
        }

        $scope.chooseUserFlag = false;
        $scope.chooseUser = {};

        //点击某个user
        $scope.clickUser = function(user){
            $scope.chooseUserFlag = true;
            $scope.chooseUser = user;
            var data = {openId: user.openId, sid: user.sid};
            if(user.openId && user.openId.length > 0){
                data.type = 'bind';
            }
            console.log(data);
            $scope.$broadcast("chooseStudent", data);
            $scope.$broadcast('chooseSid', user.sid);
        }

    }
})();