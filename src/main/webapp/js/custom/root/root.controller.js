
(function() {
    'use strict';

    angular
        .module('custom')
        .controller('RootController', RootController);

    RootController.$inject = ['$scope', '$rootScope', 'Notify'];
    function RootController($scope, $rootScope, Notify) {

        $scope.$on("TOAST", function (event, toast) {
            Notify.alert(toast.msg, {status: toast.status, pos: 'top-center', timeout: 3000});
        });
    }
})();
