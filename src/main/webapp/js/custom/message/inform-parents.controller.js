(function () {
    'use strict';
    angular
        .module('custom')
        .controller('InformParentsController', InformParentsController);
    InformParentsController.$inject = ['$scope', 'InformService', 'DicService', 'SweetAlert', 'blockUI', 'Notify'];

    function InformParentsController($scope, InformService, DicService, SweetAlert, blockUI, Notify) {

    }

})();