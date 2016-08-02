(function () {
    'use strict';
    angular
        .module('custom')
        .controller('StudentAddController', StudentAddController);
    StudentAddController.$inject = ['$scope','editableOptions', 'editableThemes', 'StudentService', 'SweetAlert', 'blockUI', 'Notify'];

    function StudentAddController($scope,editableOptions, editableThemes, StudentService, SweetAlert, blockUI, Notify) {

        $scope.student = {};

        $scope.init = function(){
            $scope.student = {};
            $scope.student.educations = [];
            $scope.student.relations = [];
        }

        activate();

        function activate() {

            $scope.init();

            // remove
            $scope.removeEducation= function(index) {
                $scope.student.educations.splice(index, 1);
            };

            $scope.addEducation = function() {
                $scope.insertedEducation = {
                    degree: '',
                    time: '',
                    school: '',
                    prove: '',
                    proveNumber: '',
                };
                $scope.student.educations.push($scope.insertedEducation);
            };

            // remove
            $scope.removeRelation= function(index) {
                $scope.student.relations.splice(index, 1);
            };

            $scope.addRelation = function() {
                $scope.insertedRelation = {
                    relationName: '',
                    name: '',
                    job: '',
                    contactAddress: '',
                    contactNumber: '',
                };
                $scope.student.relations.push($scope.insertedRelation);
            };


        }

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
                    $scope.init();
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