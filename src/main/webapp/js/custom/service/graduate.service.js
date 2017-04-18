(function () {
    'use strict';

    angular
        .module('custom')
        .service('GraduateService', GraduateService);

    GraduateService.$inject = ['$http'];
    function GraduateService($http) {

        this.getGraduateList = getGraduateList;

        function getGraduateList(){
            return $http({
                method : "POST",
                data: data,
                timeout: 5000,
                url: "/v1/graduate/list"
            });
        }

    }
})
();
