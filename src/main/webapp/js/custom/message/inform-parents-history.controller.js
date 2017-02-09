(function () {
    'use strict';
    angular
        .module('custom')
        .controller('InformParentsHistoryController', InformParentsHistoryController);
    InformParentsHistoryController.$inject = ['$scope', 'InformService', 'DicService', 'SweetAlert', 'blockUI', 'Notify'];

    function InformParentsHistoryController($scope, InformService, DicService, SweetAlert, blockUI, Notify) {



    }
})();