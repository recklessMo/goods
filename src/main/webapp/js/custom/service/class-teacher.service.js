(function () {
    'use strict';

    angular
        .module('custom')
        .service('ClassTeacherService', ClassTeacherService);

    ClassTeacherService.$inject = ['$http'];
    function ClassTeacherService($http) {

        this.listClassTeacher = listClassTeacher;
        this.saveClassTeacher = saveClassTeacher;

        function listClassTeacher() {
            return $http({
                method: "POST",
                url : "/v1/class/teacher/list",
                timeout: 5000
            });
        }

        function saveClassTeacher(data){
            return $http({
                method: "POST",
                data: data,
                url : "/v1/class/teacher/save",
                timeout: 5000
            });
        }

    }
})
();
