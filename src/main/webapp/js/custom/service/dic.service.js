(function () {
    'use strict';

    angular
        .module('custom')
        .service('DicService', DicService);

    DicService.$inject = ['$http'];
    function DicService($http) {

        this.loadAllGrade = loadAllGrade;

        function loadAllGrade(){
            return $http({
                method: "GET",
                url: "/v1/dic/grade/list",
                timeout: 5000
            });
        }


    }
})
();
