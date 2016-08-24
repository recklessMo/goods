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
        this.updateCourse = updateCourse;
        this.listCourse = listCourse;


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



    }
})
();
