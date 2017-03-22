(function () {
    'use strict';

    angular
        .module('custom')
        .service('ScoreService', ScoreService);

    ScoreService.$inject = ['$http'];
    function ScoreService($http) {

        this.loadScoreTotalResult = loadScoreTotalResult;
        this.loadScoreGapResult = loadScoreGapResult;
        this.loadScoreGapRank = loadScoreGapRank;
        this.loadScoreList = loadScoreList;


        //type代表分析的维度, 学科维度或者班级维度
        function loadScoreTotalResult(examId, type, templateId){
            return $http({
                url: "/v1/analyse/total",
                data: $.param({examId: examId, type: type, templateId:templateId}),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                method: "POST",
                timeout: 5000
            });
        }

        function loadScoreGapRank(examId, templateId){
            return $http({
                url: "/v1/analyse/rank",
                data: $.param({examId: examId, templateId: templateId}),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                method: "POST",
                timeout: 5000
            });
        }

        function loadScoreGapResult(examId, templateId){
            return $http({
                url: "/v1/analyse/gap",
                data: $.param({examId: examId, templateId: templateId}),
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
