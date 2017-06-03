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
        this.loadScoreSelf = loadScoreSelf;
        this.loadScoreRankChange = loadScoreRankChange;
        this.loadScoreAbsense = loadScoreAbsense;
        this.loadScoreTrend = loadScoreTrend;
        this.loadScoreContrast = loadScoreContrast;
        this.loadScorePoint = loadScorePoint;
        this.loadRankPoint = loadRankPoint;

        //type代表分析的维度, 学科维度或者班级维度
        function loadScoreTotalResult(examId, type, templateId){
            return $http({
                url: "/v1/analyse/total",
                data: $.param({examId: examId, type: type, templateId:templateId}),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                method: "POST",
                timeout: 10000
            });
        }

        function loadScoreContrast(data){
            return $http({
                url: "/v1/analyse/contrast",
                data: data,
                method: "POST",
                timeout: 10000
            });
        }

        function loadScoreTrend(data){
            return $http({
                url: "/v1/analyse/trend",
                data: data,
                method: "POST",
                timeout: 10000
            });
        }

        function loadScoreGapRank(examId, templateId){
            return $http({
                url: "/v1/analyse/rank",
                data: $.param({examId: examId, templateId: templateId}),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                method: "POST",
                timeout: 10000
            });
        }

        function loadScoreGapResult(examId, templateId){
            return $http({
                url: "/v1/analyse/gap",
                data: $.param({examId: examId, templateId: templateId}),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                method: "POST",
                timeout: 10000
            });
        }

        function loadScoreList(data){
            return $http({
                url: "/v1/score/list",
                data: data,
                method: "POST",
                timeout: 10000
            });
        }

        function loadScoreSelf(data){
            return $http({
                url: "/v1/analyse/self",
                data: data,
                method: "POST",
                timeout: 10000
            });
        }

        function loadScoreRankChange(data){
            return $http({
                url: "/v1/analyse/rankchange",
                data: data,
                method: "POST",
                timeout: 10000
            });
        }

        function loadScoreAbsense(data){
            return $http({
                url: "/v1/analyse/absense",
                data: data,
                method: "POST",
                timeout: 10000
            });
        }

        function loadScorePoint(data){
            return $http({
                url: "/v1/analyse/scorePoint",
                data: data,
                method: "POST",
                timeout: 10000
            });
        }

        function loadRankPoint(data){
            return $http({
                url: "/v1/analyse/rankPoint",
                data: data,
                method: "POST",
                timeout: 10000
            });
        }


    }
})
();
