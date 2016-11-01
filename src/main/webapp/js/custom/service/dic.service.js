(function () {
    'use strict';

    angular
        .module('custom')
        .service('DicService', DicService);

    DicService.$inject = ['$http'];
    function DicService($http) {

        this.loadAllGrade = loadAllGrade;
        this.loadClassByGrade = loadClassByGrade;

        function loadAllGrade(){
            return $http({
                method: "GET",
                url: "/v1/dic/grade/list",
                timeout: 5000
            });
        }

        function loadClassByGrade(data){
            return $http({
                method: "POST",
                url: "/v1/dic/class/list",
                data: data,
                timeout: 5000
            });
        }


    }
})
();
