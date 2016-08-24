(function () {
    'use strict';
    angular
        .module('custom')
        .controller('StudentAddController', StudentAddController);
    StudentAddController.$inject = ['$scope', 'StudentService', 'DicService', 'SweetAlert', 'blockUI', 'Notify'];

    function StudentAddController($scope, StudentService, DicService, SweetAlert, blockUI, Notify) {


        $scope.student = {};
        $scope.gradeList = [];
        $scope.classList = [];

        $scope.init = function() {
            $scope.student = {};
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
            }
        }

        $scope.init();

        //保存学生信息
        $scope.save = function(){

            blockUI.start();

            //校验
            if(!$scope.validate()){
                //给个对话框提示
                blockUI.stop();
                return;
            }

            //提交
            StudentService.addStudent($scope.student).success(function(data){
                if(data.status == 200){
                    SweetAlert.success("添加成功!");
                    //清空输入部分
                    $scope.student = {};
                }else{
                    SweetAlert.error("服务器异常, 请稍后重试!");
                }
            }).error(function(){
                SweetAlert.error("网络问题, 请稍后重试!");
            });

            blockUI.stop();
        }

        //校验必填信息
        $scope.validate = function(){
            if(_.isNil($scope.student.name)) {
                return false;
            }
            return true;
        }
    }
})();