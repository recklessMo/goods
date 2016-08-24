(function () {
    'use strict';
    angular
        .module('custom')
        .controller('StudentRewardListController', StudentRewardListController);
    StudentRewardListController.$inject = ['$scope','RewardService', 'SweetAlert', 'NgTableParams', 'blockUI', 'Notify'];

    function StudentRewardListController($scope, RewardService, SweetAlert, NgTableParams, blockUI, Notify) {

        $scope.activate = function() {
        }

        $scope.activate();

    }
})();