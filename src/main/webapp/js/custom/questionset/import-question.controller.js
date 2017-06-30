/**
 * Created by yyq on 17/6/30.
 */
(function () {
    'use strict';
    angular
        .module('custom')
        .controller('QuestionImportController', QuestionImportController);
    QuestionImportController.$inject = ['$scope', 'StudentService', 'DicService', 'SweetAlert', 'blockUI', 'Notify'];

    function QuestionImportController($scope, StudentService, DicService, SweetAlert, blockUI, Notify) {


       // $scope.student = {};
        $scope.typeList = [{typeId:0, typeName:'选择题'}, {typeId:1, typeName:'填空题'}, {typeId:2, typeName:'主观题'}];
       // $scope.gradeList = [{gradeId:0, gradeName:'高一'}, {gradeId:1, gradeName:'高二'}, {gradeId:2, gradeName:'高三'}];
        $scope.sectionList = [{sectionId:0, sectionName:'第一章'}, {sectionId:1, sectionName:'第二章'}, {sectionId:2, sectionName:'第三章'},
                              {sectionId:3, sectionName:'第四章'}, {sectionId:4, sectionName:'第五章'}, {sectionId:5, sectionName:'第六章'}];
        $scope.genderList = [{genderId:0, genderName:'男'}, {genderId:1, genderName:'女'}];

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

        //保存习题信息
        $scope.save = function(){
            //校验
            if(!$scope.validate($scope.student)){
                //给个对话框提示
                return;
            }

            blockUI.start();
            //提交
            StudentService.addStudent($scope.student).success(function(data){
                blockUI.stop();
                if(data.status == 200){
                    SweetAlert.success("添加成功!");
                    //清空输入部分
                    $scope.student = {};
                }
            }).error(function(){
                blockUI.stop();
                SweetAlert.error("网络问题, 请稍后重试!");
            });
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