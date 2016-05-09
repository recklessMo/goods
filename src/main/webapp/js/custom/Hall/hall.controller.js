(function () {
    'use strict';
    angular
        .module('custom')
        .controller('HallController', HallController);
    HallController.$inject = ['$state', '$scope'];
    function HallController($state, $scope) {
        $scope.tab = $state.params.tab;

        $scope.init = function () {
            $scope.tabs = {goods: false, truck: false};
            if ($scope.tab == 'goods') {
                $scope.tabs.goods = true;
            } else if ($scope.tab == "truck") {
                $scope.tabs.truck = true;
            }
        };

        $scope.changeTab = function (tab) {
            $state.go($state.current, angular.extend($state.params, {tab: tab}));
        };

        $scope.init();
    }
})();


