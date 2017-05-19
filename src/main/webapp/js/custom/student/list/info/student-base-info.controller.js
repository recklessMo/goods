(function () {
    'use strict';
    angular
        .module('custom')
        .controller('StudentBaseInfoController', StudentBaseInfoController);
    StudentBaseInfoController.$inject = ['$scope', 'DicService', 'StudentService', 'SweetAlert', 'NgTableParams', 'blockUI', 'Notify'];

    function StudentBaseInfoController($scope, DicService, StudentService, SweetAlert, NgTableParams, blockUI, Notify) {

        $scope.isEdit = false;
        $scope.offlineInfo = {};

        $scope.gradeList = [];
        $scope.classList = [];
        $scope.genderList = [{name:"男", value:0}, {name:"女", value:1}];

        //初始化选择器列表
        function initSelector(){
            blockUI.start();
            DicService.loadAllGrade().success(function(data){
                if(data.status == 200){
                    $scope.gradeList = data.data;
                }
                blockUI.stop();
            }).error(function(){
                SweetAlert.error("网络异常, 请稍后重试!");
                blockUI.stop();
            });

            $scope.selectGrade = function(data){
                $scope.classList = data.classList;
                $scope.offlineInfo.classId = 0;
            }
        }

        initSelector();


        $scope.$on('chooseSid', function(event, data){
            $scope.sid = data;
            $scope.activate();
        });

        $scope.activate = function() {
            blockUI.start();
            StudentService.getSingleStudentInfo($scope.sid).success(function(data){
                blockUI.stop();
                if(data.status == 200){
                    $scope.offlineInfo = data.data;
                    $scope.isEdit = false;
                }else{
                    SweetAlert.error("服务器内部错误, 请联系客服!");
                }
            }).error(function(){
                SweetAlert.error("网络问题,请稍后重试!");
                blockUI.stop();
            });
        }

        $scope.changeStatus = function () {
            if ($scope.isEdit == true) {
                $scope.save();
            } else {
                $scope.isEdit = true;
            }
        }


        //保存
        $scope.save = function(){
            if(!$scope.isValid($scope.offlineInfo)){
                SweetAlert.error("数据不合理!");
                return;
            }

            blockUI.start();
            StudentService.saveSingleStudentInfo($scope.offlineInfo).success(function(data){
                blockUI.stop();
                if(data.status == 200){
                    $scope.isEdit = false;
                    $scope.activate();
                }else{
                    SweetAlert.error("服务器内部错误, 请联系客服!");
                }
            }).error(function(){
                SweetAlert.error("网络问题,请稍后重试!");
                blockUI.stop();
            });
        }

        $scope.cancel = function(){
            $scope.isEdit = false;
            $scope.activate();
        }

        $scope.isValid = function(data){
            return true;
        }

        //日期
        $scope.today = function() {
            $scope.dt = new Date();
        };

        $scope.clear = function() {
            $scope.dt = null;
        };

        $scope.open = function() {
            $scope.popup.opened = true;
        };

        $scope.popup = {
            opened: false
        };


    }
})();