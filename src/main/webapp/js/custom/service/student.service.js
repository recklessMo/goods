(function () {
    'use strict';

    angular
        .module('custom')
        .service('StudentService', StudentService);

    StudentService.$inject = ['$http'];
    function StudentService($http) {

        this.searchStudent = searchStudent;
        this.addStudent = addStudent;
        this.loadStudent = loadStudent;
        this.searchStudentByExam = searchStudentByExam;
        this.getSingleStudentInfo = getSingleStudentInfo;

        function getSingleStudentInfo(data){
            return $http({
                method: "POST",
                url : "/v1/student/get",
                data:data,
                timeout: 5000
            });
        }

        function searchStudentByExam(data){
            return $http({
                method: "POST",
                url : "/v1/student/searchByExam",
                data:data,
                timeout: 5000
            });
        }

        function searchStudent(data) {
            return $http({
                method: "POST",
                url : "/v1/student/list",
                data:data,
                timeout: 5000
            });
        }

        function addStudent(data) {
            return $http({
                    method: "POST",
                    url: "/v1/student/add",
                    data: data,
                    timeout: 5000
            });
        }

        function loadStudent(data) {
            return $http({
                method: "POST",
                url: "/v1/student/listall",
                data: data,
                timeout: 5000
            });
        }

    }
})
();
