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
                var grade = _.find($scope.gradeList, function(item){
                    return item.gradeId == $scope.offlineInfo.gradeId;
                });
                $scope.classList = grade.classList;
            }
        }


        //保存
        $scope.save = function(){
            if(!$scope.validate($scope.offlineInfo)){
                return;
            }

            blockUI.start();
            StudentService.saveSingleStudentInfo($scope.offlineInfo).success(function(data){
                blockUI.stop();
                if(data.status == 200){
                    $scope.isEdit = false;
                    $scope.activate();
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

        //校验必填信息
        $scope.validate = function(student){
            if(!_.isString(student.name) || _.isEmpty(student.name)
                || !_.isNumber(student.gender)
                || !_.isString(student.phone) || _.isEmpty(student.phone)
                || !_.isString(student.sid) || _.isEmpty(student.sid)
                || !student.gradeId
                || !student.classId
            ) {
                SweetAlert.error("（姓名，性别，监护人电话，学号，年级，班级）为必填内容！");
                return false;
            }
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