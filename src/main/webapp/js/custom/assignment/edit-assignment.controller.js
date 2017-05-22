(function () {
    'use strict';
    angular
        .module('custom')
        .controller('EditAssignmentController', EditAssignmentController);
    EditAssignmentController.$inject = ['$scope', 'AssignmentService', 'DicService', 'SweetAlert', 'NgTableParams', 'ngDialog', 'blockUI', 'Notify'];

    function EditAssignmentController($scope, AssignmentService, DicService, SweetAlert, NgTableParams, ngDialog, blockUI, Notify) {

        $scope.type = $scope.ngDialogData.type;
        $scope.assignment = $scope.type == 'add'? {} : $scope.ngDialogData.assignment;

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

            if(assignment.gradeId == 0 || assignment.classId == 0 ||
                _.isEmpty(assignment.content) || _.isEmpty(assignment.name)
            || _.isEmpty(assignment.submit) || assignment.courseId == 0){
                SweetAlert.error("请填写必填字段！");
                return;
            }

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