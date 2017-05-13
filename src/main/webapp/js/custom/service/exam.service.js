(function () {
    'use strict';

    angular
        .module('custom')
        .service('ExamService', ExamService);

    ExamService.$inject = ['$http'];
    function ExamService($http) {

        this.loadExams = loadExams;
        this.saveExam = saveExam;
        this.deleteExam = deleteExam;

        function deleteExam(data){
            return $http({
                method : "POST",
                data: data,
                timeout: 10000,
                url: "/v1/exam/delete"
            });
        }

        function loadExams(data){
            return $http({
                method : "POST",
                data: data,
                timeout: 10000,
                url: "/v1/exam/list"
            });
        }

        function saveExam(data){
            return $http({
                method : "POST",
                data: data,
                timeout: 10000,
                url: "/v1/exam/save"
            });
        }

    }
})
();
