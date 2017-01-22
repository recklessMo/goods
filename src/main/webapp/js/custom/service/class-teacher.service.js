(function () {
    'use strict';

    angular
        .module('custom')
        .service('ClassTeacherService', ClassTeacherService);

    ClassTeacherService.$inject = ['$http'];
    function ClassTeacherService($http) {

        this.listClassTeacher = listClassTeacher;

        function listClassTeacher() {
            return $http({
                method: "POST",
                url : "/v1/class/teacher/list",
                timeout: 5000
            });
        }

    }
})
();
