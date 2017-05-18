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

        //保存学生信息
        $scope.save = function(){
            //校验
            if(!$scope.validate()){
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
                }else{
                    SweetAlert.error("服务器异常, 请稍后重试!");
                }
            }).error(function(){
                blockUI.stop();
                SweetAlert.error("网络问题, 请稍后重试!");
            });
        }

        //校验必填信息
        $scope.validate = function(){
            if(_.isNil($scope.student.name)
                || _.isNil($scope.student.gender)
                || _.isNil($scope.student.phone)
                || _.isNil($scope.student.sid)
                || _.isNil($scope.student.gradeId)
                || _.isNil($scope.student.classId)
            ) {
                SweetAlert.error("（姓名，性别，监护人电话，学号，年级，班级）为必填内容！");
                return false;
            }
            return true;
        }

        //
        $scope.today = function() {
            $scope.dt = new Date();
        };
        $scope.today();

        $scope.clear = function() {
            $scope.dt = null;
        };

        $scope.inlineOptions = {
            customClass: getDayClass,
            minDate: new Date(),
            showWeeks: true
        };

        $scope.dateOptions = {
            dateDisabled: disabled,
            formatYear: 'yy',
            maxDate: new Date(2020, 5, 22),
            minDate: new Date(),
            startingDay: 1
        };

        // Disable weekend selection
        function disabled(data) {
            var date = data.date,
                mode = data.mode;
            return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
        }

        $scope.toggleMin = function() {
            $scope.inlineOptions.minDate = $scope.inlineOptions.minDate ? null : new Date();
            $scope.dateOptions.minDate = $scope.inlineOptions.minDate;
        };

        $scope.toggleMin();

        $scope.open1 = function() {
            $scope.popup1.opened = true;
        };

        $scope.open2 = function() {
            $scope.popup2.opened = true;
        };

        $scope.setDate = function(year, month, day) {
            $scope.dt = new Date(year, month, day);
        };

        $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
        $scope.format = $scope.formats[0];
        $scope.altInputFormats = ['M!/d!/yyyy'];

        $scope.popup1 = {
            opened: false
        };

        $scope.popup2 = {
            opened: false
        };

        var tomorrow = new Date();
        tomorrow.setDate(tomorrow.getDate() + 1);
        var afterTomorrow = new Date();
        afterTomorrow.setDate(tomorrow.getDate() + 1);
        $scope.events = [
            {
                date: tomorrow,
                status: 'full'
            },
            {
                date: afterTomorrow,
                status: 'partially'
            }
        ];

        function getDayClass(data) {
            var date = data.date,
                mode = data.mode;
            if (mode === 'day') {
                var dayToCheck = new Date(date).setHours(0,0,0,0);

                for (var i = 0; i < $scope.events.length; i++) {
                    var currentDay = new Date($scope.events[i].date).setHours(0,0,0,0);

                    if (dayToCheck === currentDay) {
                        return $scope.events[i].status;
                    }
                }
            }

            return '';
        }

    }
})();