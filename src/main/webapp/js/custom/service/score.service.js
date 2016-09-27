(function () {
    'use strict';

    angular
        .module('custom')
        .service('ScoreService', ScoreService);

    ScoreService.$inject = ['$http'];
    function ScoreService($http) {


    }
})
();
