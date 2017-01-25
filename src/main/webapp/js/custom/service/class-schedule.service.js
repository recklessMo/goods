(function () {
    'use strict';

    angular
        .module('custom')
        .service('ClassScheduleService', ClassScheduleService);

    ClassScheduleService.$inject = ['$http'];
    function ClassScheduleService($http) {

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
