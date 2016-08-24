(function () {
    'use strict';

    angular
        .module('custom')
        .service('PayService', PayService);

    PayService.$inject = ['$http'];
    function PayService($http) {


    }
})
();
