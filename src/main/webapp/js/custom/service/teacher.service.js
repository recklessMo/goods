(function () {
    'use strict';

    angular
        .module('custom')
        .service('TeacherService', TeacherService);

    TeacherService.$inject = ['$http'];
    function TeacherService($http) {

        this.loadTeachers = loadTeachers;

        function loadTeachers(obj) {
            return $http({
                method: "POST",
                url : "/v1/user/list",
                data:obj,
                timeout: 10000
            });
        }



    }
})
();
