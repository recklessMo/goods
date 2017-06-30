(function () {
    'use strict';

    angular
        .module('custom')
        .service('RewardService', RewardService);

    RewardService.$inject = ['$http'];
    function RewardService($http) {

        this.getRewardList = getRewardList;
        this.addReward = addReward;
        this.updateReward = updateReward;
        this.deleteReward = deleteReward;

        function getRewardList(data) {
            return $http({
                method: "POST",
                data: data,
                timeout: 10000,
                url: "/v1/reward/list"
            });
        }

        function addReward(data) {
            return $http({
                method: "POST",
                data: data,
                timeout: 10000,
                url: "/v1/reward/add"
            });
        }

        function updateReward(data) {
            return $http({
                method: "POST",
                data: data,
                timeout: 10000,
                url: "/v1/reward/update"
            });
        }

        function deleteReward(data) {
            return $http({
                method: "POST",
                data: data,
                timeout: 10000,
                url: "/v1/reward/delete"
            });
        }


    }
})
();
