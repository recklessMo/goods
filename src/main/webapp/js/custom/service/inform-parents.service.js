(function () {
    'use strict';

    angular
        .module('custom')
        .service('InformService', InformService);

    InformService.$inject = ['$http'];
    function InformService($http) {



    }
})
();
