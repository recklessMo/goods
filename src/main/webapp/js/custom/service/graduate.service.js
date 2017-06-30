(function () {
    'use strict';

    angular
        .module('custom')
        .service('GraduateService', GraduateService);

    GraduateService.$inject = ['$http'];
    function GraduateService($http) {

        this.getGraduateList = getGraduateList;
        this.addGraduate = addGraduate;
        this.updateGraduate = updateGraduate;
        this.deleteGraduate = deleteGraduate;

        function getGraduateList(data) {
            return $http({
                method: "POST",
                data: data,
                timeout: 10000,
                url: "/v1/graduate/list"
            });
        }

        function addGraduate(data) {
            return $http({
                method: "POST",
                data: data,
                timeout: 10000,
                url: "/v1/graduate/add"
            });
        }

        function updateGraduate(data) {
            return $http({
                method: "POST",
                data: data,
                timeout: 10000,
                url: "/v1/graduate/update"
            });
        }

        function deleteGraduate(data) {
            return $http({
                method: "POST",
                data: data,
                timeout: 10000,
                url: "/v1/graduate/delete"
            });
        }

    }
})
();
