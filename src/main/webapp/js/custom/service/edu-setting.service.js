(function () {
    'use strict';

    angular
        .module('custom')
        .service('SettingService', SettingService);

    SettingService.$inject = ['$http'];
    function SettingService($http) {

        this.addGrade = addGrade;
        this.addClass = addClass;
        this.deleteGrade = deleteGrade;
        this.deleteClass = deleteClass;
        this.updateGrade = updateGrade;
        this.updateClass =updateClass;
        this.listGrade = listGrade;
        this.listClass = listClass;


        function addGrade(data){
            return $http({
                method: "POST",
                url: "/v1/setting/grade/add",
                data: data,
                timeout: 5000
            });
        }

        function addClass(data){
            return $http({
                method: "POST",
                url: "/v1/setting/class/add",
                data: data,
                timeout: 5000
            })
        }

        function deleteGrade(data){
            return $http({
                method: "POST",
                url: "/v1/setting/grade/delete",
                data: data,
                timeout: 5000
            })
        }

        function deleteClass(data){
            return $http({
                method: "POST",
                url: "/v1/setting/class/delete",
                data: data,
                timeout: 5000
            })
        }

        function updateGrade(data){
            return $http({
                method: "POST",
                url: "/v1/setting/grade/update",
                data: data,
                timeout: 5000
            })
        }

        function updateClass(data){
            return $http({
                method: "POST",
                url: "/v1/setting/class/update",
                data: data,
                timeout: 5000
            })
        }


        function listGrade(data){
            return $http({
                method: "POST",
                url: "/v1/setting/grade/list",
                data: data,
                timeout: 5000
            });
        }

        function listClass(gradeId){
            return $http({
                method: "POST",
                url: "/v1/setting/class/list",
                data: gradeId,
                timeout: 5000
            })
        }


    }
})
();
