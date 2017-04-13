(function () {
    'use strict';

    angular
        .module('custom')
        .service('SettingService', SettingService);

    SettingService.$inject = ['$http'];
    function SettingService($http) {

        //年级设置
        this.addGrade = addGrade;
        this.addClass = addClass;
        this.deleteGrade = deleteGrade;
        this.deleteClass = deleteClass;
        this.updateGrade = updateGrade;
        this.updateClass =updateClass;
        this.listGrade = listGrade;
        this.listClass = listClass;

        //学年设置
        this.addYear = addYear;
        this.addTerm = addTerm;

        this.updateYear = updateYear;
        this.updateTerm = updateTerm;

        this.listYear = listYear;
        this.listTerm = listTerm;

        this.setCurrentTerm = setCurrentTerm;

        //学科设置
        this.addCourse = addCourse;
        this.importCourse = importCourse;
        this.updateCourse = updateCourse;
        this.listCourse = listCourse;
        this.listStandardCourse = listStandardCourse;
        this.deleteCourse = deleteCourse;

        //职业设置
        this.listJob = listJob;
        this.addJob = addJob;
        this.deleteJob = deleteJob;


        //schedule设置
        this.listSchedule = listSchedule;
        this.saveSchedule = saveSchedule;


        //class level设置
        this.listClassLevel = listClassLevel;
        this.addClassLevel = addClassLevel;
        this.deleteClassLevel = deleteClassLevel;

        function saveSchedule(data){
            return $http({
                method: "POST",
                url: "/v1/setting/schedule/save",
                timeout: 5000,
                data: data
            });
        }

        function listSchedule(){
            return $http({
                method: "POST",
                url: "/v1/setting/schedule/list",
                timeout: 5000
            });
        }

        function listJob(data){
            return $http({
                method: "POST",
                url: "/v1/setting/job/list",
                data: data,
                timeout: 5000
            });
        }

        function addJob(data){
            return $http({
                method: "POST",
                url: "/v1/setting/job/add",
                data: data,
                timeout: 5000
            });
        }

        function deleteJob(id){
            return $http({
                method: "POST",
                url: "/v1/setting/job/delete",
                data: id,
                timeout: 5000
            });
        }


        function addGrade(data){
            return $http({
                method: "POST",
                url: "/v1/setting/grade/add",
                data: data,
                timeout: 5000
            });
        }


        function addClass(data){
            return $http({
                method: "POST",
                url: "/v1/setting/class/add",
                data: data,
                timeout: 5000
            })
        }

        function deleteGrade(data){
            return $http({
                method: "POST",
                url: "/v1/setting/grade/delete",
                data: data,
                timeout: 5000
            })
        }

        function deleteClass(data){
            return $http({
                method: "POST",
                url: "/v1/setting/class/delete",
                data: data,
                timeout: 5000
            })
        }

        function updateGrade(data){
            return $http({
                method: "POST",
                url: "/v1/setting/grade/update",
                data: data,
                timeout: 5000
            })
        }

        function updateClass(data){
            return $http({
                method: "POST",
                url: "/v1/setting/class/update",
                data: data,
                timeout: 5000
            })
        }


        function listGrade(data){
            return $http({
                method: "POST",
                url: "/v1/setting/grade/list",
                data: data,
                timeout: 5000
            });
        }

        function listClass(gradeId){
            return $http({
                method: "POST",
                url: "/v1/setting/class/list",
                data: gradeId,
                timeout: 5000
            })
        }

        function addYear(year){
            return $http({
                method: "POST",
                url: "/v1/setting/year/add",
                data: year,
                timeout: 5000
            });
        }

        function addTerm(term){
            return $http({
                method: "POST",
                url: "/v1/setting/term/add",
                data: term,
                timeout: 5000
            });
        }

        function updateYear(year){
            return $http({
                method: "POST",
                url: "/v1/setting/year/update",
                data: year,
                timeout: 5000
            });
        }

        function updateTerm(term){
            return $http({
                method: "POST",
                url: "/v1/setting/term/update",
                data: term,
                timeout: 5000
            });
        }

        function listYear(data){
            return $http({
                method: "POST",
                url: "/v1/setting/year/list",
                data: data,
                timeout: 5000
            });
        }

        function listTerm(data){
            return $http({
                method: "POST",
                url: "/v1/setting/term/list",
                data: data,
                timeout: 5000
            });
        }

        function setCurrentTerm(data){
            return $http({
                method: "POST",
                url: "/v1/setting/term/setcurrent",
                data: data,
                timeout: 5000
            });
        }


        function addCourse(data){
            return $http({
                method : "POST",
                url: "/v1/setting/course/add",
                data: data,
                timeout: 5000
            });
        }

        function importCourse(data){
            return $http({
                method : "POST",
                url: "/v1/setting/course/import",
                data: data,
                timeout: 5000
            });
        }

        function updateCourse(data){
            return $http({
                method : "POST",
                url: "/v1/setting/course/update",
                data: data,
                timeout: 5000
            });
        }

        function listCourse(data){
            return $http({
                method : "POST",
                url: "/v1/setting/course/list",
                data: data,
                timeout: 5000
            });
        }

        function listStandardCourse(data){
            return $http({
                method : "POST",
                url: "/v1/setting/course/listStandard",
                data: data,
                timeout: 5000
            });
        }

        function deleteCourse(data){
            return $http({
                method : "POST",
                url: "/v1/setting/course/delete",
                data: data,
                timeout: 5000
            });
        }

        function listClassLevel(data){
            return $http({
                method: "POST",
                url: "/v1/setting/classlevel/list",
                data: data,
                timeout: 5000
            });
        }

        function addClassLevel(data){
            return $http({
                method: "POST",
                url: "/v1/setting/classlevel/add",
                data: data,
                timeout: 5000
            });
        }

        //两种选择
        //一种是以 text/plain 头来传输数据
        //一种是以 application/json  但是需要把data转换成为json, 如果data是复杂对象不需要, 如果只是简单对象string的话需要
        function deleteClassLevel(data){
            return $http({
                method: "POST",
                url: "/v1/setting/classlevel/delete",
                data: JSON.stringify(data),
                timeout: 5000
            });
        }

    }
})
();
