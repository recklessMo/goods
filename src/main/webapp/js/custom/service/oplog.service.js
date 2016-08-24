(function () {
    'use strict';

    angular
        .module('custom')
        .service('OpLogService', OpLogService);

    OpLogService.$inject = ['$http'];
    function OpLogService($http) {


    }
})
();
