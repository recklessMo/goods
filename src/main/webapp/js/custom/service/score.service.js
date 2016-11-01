(function () {
    'use strict';

    angular
        .module('custom')
        .service('ScoreService', ScoreService);

    ScoreService.$inject = ['$http'];
    function ScoreService($http) {

        this.loadTotalScore = loadTotalScore;
        this.loadScoreList = loadScoreList;

        function loadTotalScore(data){
            return $http({
                url: "/v1/score/load",
                data: $.param({id: data}),
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
