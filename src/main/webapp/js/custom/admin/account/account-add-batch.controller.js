(function () {
    'use strict';
    angular
        .module('custom')
        .controller('AccountAddBatchController', AccountAddBatchController);
    AccountAddBatchController.$inject = ['$scope', 'AccountService', 'DicService', 'SweetAlert', 'blockUI', 'Notify'];

    function AccountAddBatchController($scope, AccountService, DicService, SweetAlert, blockUI, Notify) {


        $scope.downloadExcel = function(){
            window.open("/common/file/account/downloadExcel");
        }

    }
})();