(function () {
    'use strict';
    angular
        .module('custom')
        .controller('EditAssignmentController', EditAssignmentController);
    EditAssignmentController.$inject = ['$scope', 'AssignmentService', 'DicService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify'];

    function EditAssignmentController($scope, AssignmentService, DicService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify) {

        $scope.assignment = {};

        $scope.gradeList = [];
        $scope.classList = [];
        $scope.courseList = [];

        var block = blockUI.instances.get("edit-assignment");

        //初始化选择器列表
        function initSelector(){
            block.start();
            DicService.loadAllGrade().success(function(data){
                if(data.status == 200){
                    $scope.gradeList = data.data;
                }
                block.stop();
            }).error(function(){
                SweetAlert.error("网络异常, 请稍后重试!");
                block.stop();
            });

            $scope.selectGrade = function(data){
                $scope.classList = data.classList;
                $scope.assignment.classId = 0;
            }

            DicService.loadCourseList().success(function(data){
                if(data.status == 200){
                    $scope.courseList = data.data;
                }
                block.stop();
            }).error(function(){
                SweetAlert.error("网络异常, 请稍后重试!");
                block.stop();
            })

            $scope.selectCourse = function(data){
                $scope.assignment.courseName = data.courseName;
            }
        }

        initSelector();

        $scope.save = function(assignment){
            block.start();
            AssignmentService.addAssignment(assignment).success(function(data){
                if(data.status == 200){
                    Notify.alert("保存成功!", {status:"success", timeout: 3000});
                    $scope.closeThisDialog('reload');
                }
                block.stop();
            }).error(function(){
                SweetAlert.error("网络异常, 请稍后重试!");
                block.stop();
            })
        }

    }
})();