(function () {
    'use strict';

    angular
        .module('custom')
        .service('ScoreService', ScoreService);

    ScoreService.$inject = ['$http'];
    function ScoreService($http) {

        this.loadScoreTotalResult = loadScoreTotalResult;
        this.loadScoreGapResult = loadScoreGapResult;
        this.loadScoreList = loadScoreList;


        function loadScoreTotalResult(examId, classId){
            return $http({
                url: "/v1/analyse/total",
                data: $.param({examId: examId, classId: classId}),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                method: "POST",
                timeout: 5000
            });
        }

        function loadScoreGapResult(examId, classId){
            return $http({
                url: "/v1/analyse/gap",
                data: $.param({examId: examId, classId: classId}),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                method: "POST",
                timeout: 5000
            });
        }

        function loadScoreList(data){
            return $http({
                url: "/v1/score/list",
                data: data,
                method: "POST",
                timeout: 5000
            });
        }


    }
})
();
