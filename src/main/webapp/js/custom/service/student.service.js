(function () {
    'use strict';

    angular
        .module('custom')
        .service('StudentService', StudentService);

    StudentService.$inject = ['$http'];
    function StudentService($http) {

        this.searchStudent = searchStudent;
        this.addStudent = addStudent;

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

    }
})
();
