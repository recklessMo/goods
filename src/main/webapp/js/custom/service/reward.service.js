(function () {
    'use strict';

    angular
        .module('custom')
        .service('RewardService', RewardService);

    RewardService.$inject = ['$http'];
    function RewardService($http) {


    }
})
();
