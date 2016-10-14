(function () {
    'use strict';

    angular
        .module('custom')
        .service('ScoreService', ScoreService);

    ScoreService.$inject = ['$http'];
    function ScoreService($http) {

        this.loadTotalScore = loadTotalScore;

        function loadTotalScore(data){
            return $http({
                url: "/v1/score/load",
                data: $.param({id: data}),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                method: "POST",
                timeout: 5000
            });
        }

    }
})
();
