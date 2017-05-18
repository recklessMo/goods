(function () {
    'use strict';
    angular
        .module('custom')
        .controller('EditAssignmentController', EditAssignmentController);
    EditAssignmentController.$inject = ['$scope', 'AssignmentService', 'DicService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify'];

    function EditAssignmentController($scope, AssignmentService, DicService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify) {

        $scope.gradeList = [];
        $scope.classList = [];
        $scope.courseList = [];

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

            $scope.selectCourse = function(data){
                $scope.assignment.courseName = data.courseName;
            }
        }

        initSelector();

        $scope.save = function(assignment){
            AssignmentService.addAssignment(assignment).success(function(data){
                if(data.status == 200){
                    SweetAlert.success("保存成功!");
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