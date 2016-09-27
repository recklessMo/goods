(function () {
    'use strict';

    angular
        .module('custom')
        .service('ExamService', ExamService);

    ExamService.$inject = ['$http'];
    function ExamService($http) {

        this.loadExams = loadExams;

        function loadExams(data){
            return $http({
                method : "POST",
                data: data,
                timeout: 5000,
                url: "/v1/exam/list"
            });
        }

    }
})
();
