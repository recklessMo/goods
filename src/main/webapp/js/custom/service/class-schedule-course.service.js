(function () {
    'use strict';

    angular
        .module('custom')
        .service('ClassScheduleCourseService', ClassScheduleCourseService);

    ClassScheduleCourseService.$inject = ['$http'];
    function ClassScheduleCourseService($http) {

        this.listScheduleCourse = listScheduleCourse;
        this.saveScheduleCourse = saveScheduleCourse;

        function listScheduleCourse() {
            return $http({
                method: "POST",
                url : "/v1/class/schedule/list",
                timeout: 10000
            });
        }

        function saveScheduleCourse(data){
            return $http({
                method: "POST",
                data: data,
                url : "/v1/class/schedule/save",
                timeout: 10000
            });
        }

    }
})
();
