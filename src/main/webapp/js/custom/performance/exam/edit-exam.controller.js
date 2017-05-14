(function () {
    'use strict';
    angular
        .module('custom')
        .controller('EditExamController', EditExamController);
    EditExamController.$inject = ['$scope', 'ExamService', 'DicService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify'];

    function EditExamController($scope, ExamService, DicService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify) {

        //考试对象
        $scope.exam = {};
        $scope.examTypeList = ['小测', '周考', '月考', '模拟', '期中', '期末'];

        $scope.gradeList = [];
        $scope.classList = [];
        $scope.courseList = [];

        //初始化选择器列表
        function initSelector(){
            blockUI.start();
            DicService.loadAllGrade().success(function(data){
                if(data.status == 200){
                    $scope.gradeList = data.data;
                    _.forEach($scope.gradeList, function(item){
                        item.classList.unshift({classId: 0, className:'全部'});
                    });
                }
                blockUI.stop();
            }).error(function(){
                SweetAlert.error("网络异常, 请稍后重试!");
                blockUI.stop();
            });

            $scope.selectGrade = function(data){
                $scope.classList = data.classList;
                $scope.exam.classId = 0;
            }

            DicService.loadCourseList().success(function(data){
                if(data.status == 200){
                    $scope.courseList = data.data;
                }
                blockUI.stop();
            }).error(function(){
                SweetAlert.error("网络异常, 请稍后重试!");
                blockUI.stop();
            })
        }

        initSelector();

        $scope.save = function(exam){
            ExamService.saveExam(exam).success(function(data){
                if(data.status == 200){
                    SweetAlert.success("添加成功!");
                    $scope.closeThisDialog('reload');
                }
                blockUI.stop();
            }).error(function(){
                SweetAlert.error("网络异常, 请稍后重试!");
                blockUI.stop();
            })
        }

    }
})();